package com.lili.school.manager.impl;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.lili.common.constant.JpushConstant;
import com.lili.common.util.DateUtil;
import com.lili.common.util.Page;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ReqConstants;
import com.lili.school.dto.EnrollExamReg;
import com.lili.school.dto.EnrollExamRegExample;
import com.lili.school.dto.EnrollExamResult;
import com.lili.school.dto.EnrollExamResultExample;
import com.lili.school.dto.EnrollImportRecord;
import com.lili.school.dto.EnrollImportRecordExample;
import com.lili.school.manager.EnrollSubjectManager;
import com.lili.school.manager.SchoolManager;
import com.lili.school.mapper.EnrollExamRegMapper;
import com.lili.school.mapper.EnrollExamResultMapper;
import com.lili.school.mapper.EnrollImportRecordMapper;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;


public class EnrollSubjectManagerImpl implements EnrollSubjectManager {
	@Autowired
	EnrollExamRegMapper enrollExamRegMapper;
	@Autowired
	EnrollExamResultMapper enrollExamResultMapper;
	@Autowired
	EnrollImportRecordMapper enrollImportRecordMapper;
	@Autowired
	SchoolManager schoolManager;
	@Autowired
	private StudentManager studentManager;
	
	@Autowired
	private RedisUtil redisUtil;

	@Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;
	
	@SuppressWarnings("unchecked")
	@Override
	public int changeUpdateState(Integer schoolId, String uuid, Integer state, String userName) {
		if(null != state && state == 2){ //1-导入；2-放弃导入
			redisUtil.delete(RedisKeys.REDISKEY.UPLOAD + uuid);
			return 0;
		}else if(null == uuid){
			return -1;
		}else {
			Map<String, Object> data = redisUtil.get(RedisKeys.REDISKEY.UPLOAD + uuid);
			if(null == data){
				return -1;
			}else {
				byte type = Byte.parseByte((String)data.get("type")); // 1-预约登记表 2-考试成绩表
				//添加导入记录
				EnrollImportRecord enrollImportRecord = new EnrollImportRecord();
				enrollImportRecord.setTableNo((String)data.get("tableNo"));
				enrollImportRecord.setSchoolId(schoolId);
				enrollImportRecord.setType(type);
				enrollImportRecord.setSubjectId((Integer)data.get("subjectId"));
				enrollImportRecord.setRemark((String) data.get("headDesc"));
				enrollImportRecord.setTotal((Integer)data.get("total"));
				enrollImportRecord.setValid((Integer)data.get("valid"));
				enrollImportRecord.setState(1); //'导入状态：0-未开始；1-已导入'
				enrollImportRecord.setOperator(userName);
				enrollImportRecord.setCtime(new Date());
				enrollImportRecordMapper.insertSelective(enrollImportRecord);
				
				//执行导入
				if(type == 1){
					List<EnrollExamReg> enrollExamRegList = (List<EnrollExamReg>) data.get("dataList");
					for(EnrollExamReg record:enrollExamRegList){
						if(record.getIsdel() == ReqConstants.OPERATE_DELETE){
							continue;
						}
						//实际导入时，设置数据为已导入状态
						record.setImportState(1);
						enrollExamRegMapper.insertSelective(record);
						// 同步数据到学员状态中，使用多个线程处理
						int stage = 0;
						int subject = record.getSubjectId();
						if(subject == ReqConstants.SUBJECT_TYPE_ONE){
							stage = ReqConstants.PROG_STAGE_SUBJECT_ONE_LINEUP;
						}else if(subject == ReqConstants.SUBJECT_TYPE_TWO){
							stage = ReqConstants.PROG_STAGE_SUBJECT_TWO_LINEUP;
						}else if(subject == ReqConstants.SUBJECT_TYPE_THREE){
							stage = ReqConstants.PROG_STAGE_SUBJECT_THREE_LINEUP;
						}else if(subject == ReqConstants.SUBJECT_TYPE_FOUR){
							stage = ReqConstants.PROG_STAGE_SUBJECT_FOUR_LINEUP;
						}
						// 预约详情需要传递到app
						Map<String,String> msgs = new HashMap<String, String>();
						msgs.put("examTime",new SimpleDateFormat("yyyy-MM-dd").format(record.getExamDate()) + " "+record.getExamTime());
						msgs.put("examPlace", record.getExamPlace());
						schoolManager.changeState(-1, record.getStudentId(), stage, record.getApplystate(), msgs);
						//add by devil
						if(record.getApplystate()==0)
						doStuPush(record);//增加约考成功推送
					}
				}else if(type == 2){
					List<EnrollExamResult> enrollExamResultList = (List<EnrollExamResult>) data.get("dataList");
					for(EnrollExamResult record:enrollExamResultList){
						if(record.getIsdel() == ReqConstants.OPERATE_DELETE){
							continue;
						}
						//实际导入时，设置数据为已导入状态
						record.setImportState(1);
						enrollExamResultMapper.insertSelective(record);
						// 同步数据到学员状态中，使用多个线程处理
						Student student = new Student();
						student.setStudentId(record.getStudentId());
						int stage = 0;
						int subject = record.getSubjectId();
						if(subject == ReqConstants.SUBJECT_TYPE_ONE){
							stage = ReqConstants.PROG_STAGE_SUBJECT_ONE;
							student.setCourse1(record.getScore().shortValue());
						}else if(subject == ReqConstants.SUBJECT_TYPE_TWO){
							stage = ReqConstants.PROG_STAGE_SUBJECT_TWO;
							student.setCourse2(record.getScore().shortValue());
						}else if(subject == ReqConstants.SUBJECT_TYPE_THREE){
							stage = ReqConstants.PROG_STAGE_SUBJECT_THREE;
							student.setCourse3(record.getScore().shortValue());
						}else if(subject == ReqConstants.SUBJECT_TYPE_FOUR){
							stage = ReqConstants.PROG_STAGE_SUBJECT_FOUR;
							student.setCourse4(record.getScore().shortValue());
						}
						
						// 预约详情需要传递到app 在更改状态的时候把学员成绩设置为100；或者在这里把实际成绩设置到学员主表中
						// 20160803把成绩更新到学员主表中
						studentManager.updateStudent(student);
						Map<String, String> map=new HashMap<>();
						map.put("examTime",TimeUtil.getDateFormat(record.getExamDate(), "yyyy-MM-dd"));
						schoolManager.changeState(-1, record.getStudentId(), stage, record.getApplystate(), map);
					}
				}else {
					
				}
				

			}
			
		}
		return 0;
	}
	
	/**
	 * 约考成功推送
	 * @param record
	 */
	public void doStuPush(EnrollExamReg record){
		try {
			int subject = record.getSubjectId();
			String courseName =null;
			String operate = null;
			if(subject == ReqConstants.SUBJECT_TYPE_ONE){
				courseName = "科目一";
				operate = JpushConstant.OPERATE.STUCOURSEONEYK;
			}else if(subject == ReqConstants.SUBJECT_TYPE_TWO){
				courseName = "科目二";
				operate = JpushConstant.OPERATE.STUCOURSETWOYK;
			}else if(subject == ReqConstants.SUBJECT_TYPE_THREE){
				courseName = "科目三";
				operate = JpushConstant.OPERATE.STUCOURSETHREEYK;
			}else if(subject == ReqConstants.SUBJECT_TYPE_FOUR){
				courseName = "科目四";
				operate = JpushConstant.OPERATE.STUCOURSEFOURYK;
			}
			JpushMsg jmsg = new JpushMsg();
			jmsg.setAlter("您已成功约考" + courseName + "，考试日期为" + new SimpleDateFormat("yyyy-MM-dd").format(record.getExamDate()) + " "+record.getExamTime());
			jmsg.setUserId(record.getStudentId());
			jmsg.setOperate(operate);
			Message jpush = new Message();
			jpush.setTopic(jpushProducer.getCreateTopicKey());
			jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
			jpush.setBody(SerializableUtil.serialize(jmsg));
			jpushProducer.send(jpush);
		} catch (MQClientException e) {
			e.printStackTrace();
		} catch (RemotingException e) {
			e.printStackTrace();
		} catch (MQBrokerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Page<EnrollImportRecord> getUpdateTables(int schoolId, String type,
			String pageNo, String pageSize, String dateBegin, String dateEnd) {
		try {
			EnrollImportRecordExample example = new EnrollImportRecordExample();
			EnrollImportRecordExample.Criteria criteria = example.createCriteria();
			
			criteria.andSchoolIdEqualTo(schoolId);
			criteria.andTypeEqualTo(Byte.parseByte(type));

			int pNo =1;
			int pSize = 100;
			if(StringUtil.isNotNullAndNotEmpty(pageNo) && StringUtil.isNotNullAndNotEmpty(pageSize)){
				pNo = Integer.parseInt(pageNo.trim());
				if(pNo <= 0){
					pNo =1;
				}
				pSize =	Integer.parseInt(pageSize.trim());
				if(pSize <= 0){
					pSize =100;
				}
			}
			RowBounds rowBounds = new RowBounds((pNo -1)*pSize, pSize);//(offset,limit)
			if(StringUtil.isNotNullAndNotEmpty(dateBegin) && StringUtil.isNotNullAndNotEmpty(dateEnd)){
				Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateBegin);
				Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(dateEnd);
				//sql between date does not contain the last day! so add one more day!
				Date d3 = DateUtil.getNextDay(d2);
				criteria.andCtimeBetween(d1, d3);
			}

			int total = enrollImportRecordMapper.countByExample(example);
			example.setOrderByClause("ctime desc");
			List<EnrollImportRecord> theoryList = enrollImportRecordMapper.selectByExampleWithRowbounds(example, rowBounds);
			return new Page<EnrollImportRecord>(theoryList,pNo,pSize,total);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Page getUpdateTablesOne(int schoolId, String type, String pageNo,
			String pageSize, String tableNo) {
		if(type.equals("1")){ //1-预约登记表 2-考试成绩表
			try {
				EnrollExamRegExample example = new EnrollExamRegExample();
				EnrollExamRegExample.Criteria criteria = example.createCriteria();
				
				criteria.andSchoolIdEqualTo(schoolId);
				criteria.andTableNoEqualTo(tableNo.trim());

				int pNo =1;
				int pSize = 100;
				if(StringUtil.isNotNullAndNotEmpty(pageNo) && StringUtil.isNotNullAndNotEmpty(pageSize)){
					pNo = Integer.parseInt(pageNo.trim());
					if(pNo <= 0){
						pNo =1;
					}
					pSize =	Integer.parseInt(pageSize.trim());
					if(pSize <= 0){
						pSize =100;
					}
				}
				RowBounds rowBounds = new RowBounds((pNo -1)*pSize, pSize);//(offset,limit)

				int total = enrollExamRegMapper.countByExample(example);
				example.setOrderByClause("ctime desc");
				List<EnrollExamReg> theoryList = enrollExamRegMapper.selectByExampleWithRowbounds(example, rowBounds);
				return new Page<EnrollExamReg>(theoryList,pNo,pSize,total);
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e){
				e.printStackTrace();
			}
		}else if(type.equals("2")){
			try {
				EnrollExamResultExample example = new EnrollExamResultExample();
				EnrollExamResultExample.Criteria criteria = example.createCriteria();
				
				criteria.andSchoolIdEqualTo(schoolId);
				criteria.andTableNoEqualTo(tableNo.trim());

				int pNo =1;
				int pSize = 100;
				if(StringUtil.isNotNullAndNotEmpty(pageNo) && StringUtil.isNotNullAndNotEmpty(pageSize)){
					pNo = Integer.parseInt(pageNo.trim());
					if(pNo <= 0){
						pNo =1;
					}
					pSize =	Integer.parseInt(pageSize.trim());
					if(pSize <= 0){
						pSize =100;
					}
				}
				RowBounds rowBounds = new RowBounds((pNo -1)*pSize, pSize);//(offset,limit)

				int total = enrollExamResultMapper.countByExample(example);
				example.setOrderByClause("ctime desc");
				List<EnrollExamResult> theoryList = enrollExamResultMapper.selectByExampleWithRowbounds(example, rowBounds);
				return new Page<EnrollExamResult>(theoryList,pNo,pSize,total);
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e){
				e.printStackTrace();
			}
		}else {
			return null;
		}
		
		return null;
	}

	@Override
	public EnrollImportRecord getUpdateTablesOneInfo(int schoolId,
			String tableNo) {
		EnrollImportRecord er = redisUtil.get(RedisKeys.REDISKEY.ENROLL_TABLE+ tableNo);
		if(null == er){
			er = enrollImportRecordMapper.selectByPrimaryKey(tableNo);
			if(null != er){
				redisUtil.set(RedisKeys.REDISKEY.ENROLL_TABLE+ tableNo,er,RedisKeys.EXPIRE.WEEK);
			}
		}
		return er;
	}
	
	
	
	
	
	
	
	
	
	

}




































