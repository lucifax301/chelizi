package com.lili.school.manager.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.authcode.service.AuthcodeService;
import com.lili.common.util.DateUtil;
import com.lili.common.util.MobileUtil;
import com.lili.common.util.Page;
import com.lili.common.util.StringUtil;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.school.dto.EnrollTheory;
import com.lili.school.dto.EnrollTheoryExample;
import com.lili.school.dto.EnrollTheoryStudent;
import com.lili.school.dto.EnrollTheoryStudentExample;
import com.lili.school.manager.EnrollTheoryManager;
import com.lili.school.manager.SchoolManager;
import com.lili.school.mapper.EnrollTheoryMapper;
import com.lili.school.mapper.EnrollTheoryStudentMapper;
import com.lili.school.quartz.DynamicQuartz;
import com.lili.school.quartz.EnrollOnClassJob;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;


public class EnrollTheoryManagerImpl implements EnrollTheoryManager {
	private static Logger logger = LoggerFactory.getLogger(EnrollTheoryManagerImpl.class);
	@Autowired
	private EnrollTheoryMapper enrollTheoryMapper;
	@Autowired
	private EnrollTheoryStudentMapper enrollTheoryStudentMapper;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private StudentManager studentManager;
	@Autowired
	private AuthcodeService authcodeService;
//	@Autowired
//	private DynamicQuartz dynamicQuartz;
	@Autowired
	private SchoolManager schoolManager;
	
	@Override
	public EnrollTheory getEnrollTheoryByTheoryId(int theoryId) {
/*		EnrollTheory et =redisUtil.get(RedisKeys.REDISKEY.ENROLL_THEORY+ theoryId);
		if(null == et){
			et = enrollTheoryMapper.selectByPrimaryKey(theoryId);
			if(null != et){
				redisUtil.set(RedisKeys.REDISKEY.ENROLL_THEORY+ theoryId,et,RedisKeys.EXPIRE.WEEK);
			}
		}
		return et;*/
		return enrollTheoryMapper.selectByPrimaryKey(theoryId);
	}
	
	@Override
	public Page<EnrollTheory> getTheoryBySchoolId(Integer schoolId, String state,
			String pageNo, String pageSize, String dateBegin, String dateEnd,String createBegin,String createEnd) {

		try {
			EnrollTheoryExample example = new EnrollTheoryExample();
			EnrollTheoryExample.Criteria criteria = example.createCriteria();
			if(schoolId.intValue()>0){
				criteria.andSchoolIdEqualTo(schoolId);
			}else{
				criteria.andSchoolIdIsNotNull();
			}
			if(StringUtil.isNotNullAndNotEmpty(state)){
				criteria.andStateEqualTo(Integer.parseInt(state.trim()));
			}
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
			if(StringUtil.isNotNullAndNotEmpty(createBegin) && StringUtil.isNotNullAndNotEmpty(createEnd)){
				Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(createBegin);
				Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(createEnd);
				//sql between date does not contain the last day! so add one more day!
				Date d3 = DateUtil.getNextDay(d2);
				criteria.andCtimeBetween(d1, d3);
			}
			int total = enrollTheoryMapper.countByExample(example);
			example.setOrderByClause("ctime desc");
			List<EnrollTheory> theoryList = enrollTheoryMapper.selectByExampleWithRowbounds(example, rowBounds);
			return new Page(theoryList,pNo,pSize,total);
			
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
	public Page<EnrollTheory> getTheoryBySchoolId(List<Integer> schoolIds, String state,
			String pageNo, String pageSize, String dateBegin, String dateEnd,String createBegin,String createEnd) {

		try {
			EnrollTheoryExample example = new EnrollTheoryExample();
			EnrollTheoryExample.Criteria criteria = example.createCriteria();
			
			if(schoolIds.size()>0){
				criteria.andSchoolIdIn(schoolIds);
			}
			
			if(StringUtil.isNotNullAndNotEmpty(state)){
				criteria.andStateEqualTo(Integer.parseInt(state.trim()));
			}
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
			if(StringUtil.isNotNullAndNotEmpty(createBegin) && StringUtil.isNotNullAndNotEmpty(createEnd)){
				Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(createBegin);
				Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(createEnd);
				//sql between date does not contain the last day! so add one more day!
				Date d3 = DateUtil.getNextDay(d2);
				criteria.andCtimeBetween(d1, d3);
			}
			int total = enrollTheoryMapper.countByExample(example);
			example.setOrderByClause("ctime desc");
			List<EnrollTheory> theoryList = enrollTheoryMapper.selectByExampleWithRowbounds(example, rowBounds);
			return new Page(theoryList,pNo,pSize,total);
			
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
	public int addTheory(Integer schoolId, String classDate, String classTime,
			String className, String cardDesc, String contactName,
			String contactMobile, String classPlace) {
		if(null == schoolId || schoolId == 0){
			return -1; //学校不能为空
		}
		if(!MobileUtil.isMobile(contactMobile)){
			return -1; //电话号码错误
		}
		try {
			EnrollTheory record = new EnrollTheory();
			record.setSchoolId(schoolId);
			record.setClassDate(new SimpleDateFormat("yyyy-MM-dd").parse(classDate));
			record.setClassTime(classTime);
			record.setClassName(className);
			record.setCardDesc(cardDesc);
			record.setContactName(contactName);
			record.setContactMobile(contactMobile.trim());
			record.setClassPlace(classPlace);
			record.setCtime(new Date());
			return enrollTheoryMapper.insertSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	@Override
	public int modifyTheory(String theoryId, Integer schoolId, String classDate,
			String classTime, String className, String cardDesc,
			String contactName, String contactMobile, String classPlace) {
		if(null == schoolId || schoolId == 0){
			return -1;
		}
		if(StringUtil.isNullOrEmpty(theoryId)){
			return -1;
		}
		try {
			EnrollTheory record = new EnrollTheory();
			record.setTheoryId(Integer.parseInt(theoryId));
			record.setSchoolId(schoolId);
			if(StringUtil.isNotNullAndNotEmpty(classDate)){
				record.setClassDate(new SimpleDateFormat("yyyy-MM-dd").parse(classDate));
			}
			if(StringUtil.isNotNullAndNotEmpty(classTime)){
				record.setClassTime(classTime);
			}
			if(StringUtil.isNotNullAndNotEmpty(className)){
				record.setClassName(className);
			}
			if(StringUtil.isNotNullAndNotEmpty(cardDesc)){
				record.setCardDesc(cardDesc);
			}
			if(StringUtil.isNotNullAndNotEmpty(contactName)){
				record.setContactName(contactName);
			}
			if(StringUtil.isNotNullAndNotEmpty(contactMobile)){
				record.setContactMobile(contactMobile);
			}
			if(StringUtil.isNotNullAndNotEmpty(classPlace)){
				record.setClassPlace(classPlace);
			}
			
			return enrollTheoryMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	@Override
	public int addTheoryStudent(Integer schoolId, String theoryId,
			String studentIds) {
		if(null == schoolId || schoolId == 0){
			return -1;
		}
		if(StringUtil.isEmptyOrWhitespaceOnly(theoryId)){
			return -1;
		}
		try {
			boolean hasOk = true;
			List<Long> sids = new ArrayList<Long>();
			String[] aa = studentIds.split(",");
			for(String a:aa){
				Long b = Long.parseLong(a.trim());
				sids.add(b);
			}
			int total = aa.length;
			List<Student> stus = studentManager.getStudentsByIds(sids);
			int tId = Integer.parseInt(theoryId);
			for(Student stu:stus){
				if(stu.getApplyexam() != ReqConstants.PROG_STAGE_THEORY){
					hasOk = false;
					continue;
				}
				EnrollTheoryStudent record = new EnrollTheoryStudent();
				record.setSchoolId(schoolId);
				record.setTheoryId(tId);
				record.setStudentId(stu.getStudentId());
				record.setName(stu.getName());
				record.setPhoneNum(stu.getPhoneNum());
				record.setSex(stu.getSex());
				record.setDrType(stu.getDrType());
				record.setIdNumber(stu.getIdNumber());
				record.setFlowNo(stu.getFlowNo());
				record.setIsImport(stu.getIsImport());

				int res = enrollTheoryStudentMapper.insertSelective(record);
				if(res != 1){
					total --;
					hasOk = false;
				}
			}
			this.updateEnrollTheory(tId,total,null);
			if(hasOk){
				return 0;
			}else {
				return -1;
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	/**
	 * 更新理论课人员数量
	 * @param tId
	 */
	private void updateEnrollTheory(int tId,Integer totalChange,Integer absentChange) {
		if(null == totalChange && null == absentChange){
			EnrollTheoryStudentExample example = new EnrollTheoryStudentExample();
			EnrollTheoryStudentExample.Criteria criteria = example.createCriteria();
			criteria.andTheoryIdEqualTo(tId);
			int total = enrollTheoryStudentMapper.countByExample(example);
			criteria.andStateEqualTo(ReqConstants.THEORY_STU_ABSENCE);
			int absent = enrollTheoryStudentMapper.countByExample(example);
			EnrollTheory record = new EnrollTheory();
			record.setTheoryId(tId);
			record.setTotal(total);
			record.setAbsence(absent);
			enrollTheoryMapper.updateByPrimaryKeySelective(record);
		}
		if(null != totalChange){
			EnrollTheory et = this.getEnrollTheoryByTheoryId(tId);
			et.setTotal(et.getTotal() + totalChange);
			enrollTheoryMapper.updateByPrimaryKeySelective(et);
		}
		if(null != absentChange){
			EnrollTheory et = this.getEnrollTheoryByTheoryId(tId);
			et.setAbsence(et.getAbsence() + absentChange);
			enrollTheoryMapper.updateByPrimaryKeySelective(et);
		}

		redisUtil.delete(RedisKeys.REDISKEY.ENROLL_THEORY+ tId);
	}

	@Override
	public int deleteTheoryStudent(Integer schoolId, String theoryId,
			String studentIds) {
		if(StringUtil.isEmptyOrWhitespaceOnly(theoryId)){
			return -1;
		}
		try {
			int tId = Integer.parseInt(theoryId);
			List<Long> sids = new ArrayList<Long>();
			String[] aa = studentIds.split(",");
			for(String a:aa){
				Long b = Long.parseLong(a.trim());
				sids.add(b);
			}
			EnrollTheoryStudentExample example = new EnrollTheoryStudentExample();
			EnrollTheoryStudentExample.Criteria criteria = example.createCriteria();
			criteria.andSchoolIdEqualTo(schoolId);
			criteria.andTheoryIdEqualTo(tId);
			criteria.andStudentIdIn(sids);
			
			int num =  enrollTheoryStudentMapper.deleteByExample(example); //直接物理删除，避免重新加入时主键冲突;
			this.updateEnrollTheory(tId,- num,null);
			return num;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public Page<EnrollTheoryStudent> getTheoryStudent(Integer schoolId,
			String theoryId, String pageNo, String pageSize, String state,
			String isImport) {
		try {
			EnrollTheoryStudentExample example = new EnrollTheoryStudentExample();
			EnrollTheoryStudentExample.Criteria criteria = example.createCriteria();
			criteria.andSchoolIdEqualTo(schoolId);
			criteria.andTheoryIdEqualTo(Integer.parseInt(theoryId));
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
			if(StringUtil.isNotNullAndNotEmpty(isImport)){
				criteria.andIsImportEqualTo(Byte.parseByte(isImport));
			}
			if(StringUtil.isNotNullAndNotEmpty(state)){
				criteria.andStateEqualTo(Byte.parseByte(state));
			}
			int total = enrollTheoryStudentMapper.countByExample(example);
			List<EnrollTheoryStudent> data = enrollTheoryStudentMapper.selectByExampleWithRowbounds(example, rowBounds);
			
			return new Page<EnrollTheoryStudent>(data,pNo,pSize,total);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int changeTheoryClass(int schoolId, String theoryId, String state) {
		EnrollTheory et = this.getEnrollTheoryByTheoryId(Integer.parseInt(theoryId));
		if(null == et || et.getSchoolId() != schoolId){
			return -1;
		}
		int status = Integer.parseInt(state);
		if(et.getState() >= status){
			return -1;//状态不允许回退 	0-未确认；1-待上课；2-已上课；3-已取消
		}
		if(status == ReqConstants.THEORY_CLASS_OPEN){ 
			//（1）设置开课，发送短信通知学员
			EnrollTheoryStudentExample example = new EnrollTheoryStudentExample();
			EnrollTheoryStudentExample.Criteria criteria = example.createCriteria();
			criteria.andSchoolIdEqualTo(schoolId);
			criteria.andTheoryIdEqualTo(Integer.parseInt(theoryId));
			List<EnrollTheoryStudent> ets = enrollTheoryStudentMapper.selectByExample(example);
			if(null == ets || ets.size() == 0){ //没有学员不能开课
				return -1;
			}
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<ets.size();i++){
				sb.append(ets.get(i).getPhoneNum());
				if(i != ets.size() -1){
					sb.append(",");
				}
			}
			Map<String,String> msgs = new HashMap<String, String>();
			String cd = new SimpleDateFormat("yyyy-MM-dd").format(et.getClassDate()) +" "+ et.getClassTime().split("-")[0];
			msgs.put("classDate",cd);
			msgs.put("cardDesc", et.getCardDesc());
			msgs.put("classPlace", et.getClassPlace());
			msgs.put("className", et.getClassName());
			msgs.put("classTime", et.getClassTime());
			msgs.put("contact", et.getContactName() + " "+ et.getContactMobile());
			
			try {
				logger.debug("SHORT_MESSAGE_TYPE_THEORY_ON mobiles:" + sb.toString());
				authcodeService.sendMsg(ReqConstants.SHORT_MESSAGE_TYPE_THEORY_ON, sb.toString(), msgs);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//（2）添加定时任务，到时间更改开课状态为上课
/*			try {
				Date dstart = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(
				new SimpleDateFormat("yyyy-MM-dd").format(et.getClassDate()) + " "+et.getClassTime().split("-")[0].trim());
				long after = dstart.getTime();
				long now = new Date().getTime();
				int afterSecond = (int) ((after - now)/1000);
				EnrollOnClassJob enrollOnClassJob = new EnrollOnClassJob(theoryId,afterSecond,1);
				dynamicQuartz.addJob(enrollOnClassJob);
				
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			
			//（3）更改学员状态，设置为已约理论课状态（101,1），APP透传课程详情  已安排您在{1}进行理论课培训，地址为{2}
			msgs.put("dstart", new SimpleDateFormat("yyyy-MM-dd").format(et.getClassDate()) + " "+et.getClassTime().split("-")[0].trim());
			List<Long> studentIds = new ArrayList<Long>();
			for(EnrollTheoryStudent one:ets){
				studentIds.add(one.getStudentId());
			}
			schoolManager.changeState(-1, studentIds, ReqConstants.PROG_STAGE_THEORY, ReqConstants.STAGE_STATE_SUBMIT, msgs);
			
			
		}else if(status == ReqConstants.THEORY_CLASS_CANCEL){
			//（1）设置取消开课，发送短信通知学员
			EnrollTheoryStudentExample example = new EnrollTheoryStudentExample();
			EnrollTheoryStudentExample.Criteria criteria = example.createCriteria();
			criteria.andSchoolIdEqualTo(schoolId);
			criteria.andTheoryIdEqualTo(Integer.parseInt(theoryId));
			List<EnrollTheoryStudent> ets = enrollTheoryStudentMapper.selectByExample(example);
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<ets.size();i++){
				sb.append(ets.get(i).getPhoneNum());
				if(i != ets.size() -1){
					sb.append(",");
				}
			}
			Map<String,String> msgs = new HashMap<String, String>();
			msgs.put("classDate",new SimpleDateFormat("yyyy-MM-dd").format(et.getClassDate()));
			msgs.put("className", et.getClassName());

			try {
				logger.debug("SHORT_MESSAGE_TYPE_THEORY_OFF mobiles:" + sb.toString());
				authcodeService.sendMsg(ReqConstants.SHORT_MESSAGE_TYPE_THEORY_OFF, sb.toString(), msgs);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//（2）更改学员状态，设置为等待预约理论课（101,0），APP透传课程详情
			List<Long> studentIds = new ArrayList<Long>();
			for(EnrollTheoryStudent one:ets){
				studentIds.add(one.getStudentId());
			}
			schoolManager.changeState(-1, studentIds, ReqConstants.PROG_STAGE_THEORY, ReqConstants.STAGE_STATE_NONE, msgs);

		}
		
		//更新课程状态
		et.setState(status);
		enrollTheoryMapper.updateByPrimaryKeySelective(et);
		redisUtil.delete(RedisKeys.REDISKEY.ENROLL_THEORY+ theoryId);
		return 0;
	}

	@Override
	public int changeTheoryStudentState(int schoolId, String theoryId,
			String studentIds, String state) {
		if(StringUtil.isEmptyOrWhitespaceOnly(theoryId)){
			return -1;
		}
		try {
			int tId = Integer.parseInt(theoryId);
			byte status = Byte.parseByte(state);
			List<Long> sids = new ArrayList<Long>();
			String[] aa = studentIds.split(",");
			for(String a:aa){
				Long b = Long.parseLong(a.trim());
				sids.add(b);
			}
			EnrollTheoryStudentExample example = new EnrollTheoryStudentExample();
			EnrollTheoryStudentExample.Criteria criteria = example.createCriteria();
			criteria.andSchoolIdEqualTo(schoolId);
			criteria.andTheoryIdEqualTo(tId);
			criteria.andStudentIdIn(sids);
			
			EnrollTheoryStudent record = new EnrollTheoryStudent();
			record.setState(status);
			
			int num =  enrollTheoryStudentMapper.updateByExampleSelective(record, example);
			if(status == ReqConstants.THEORY_STU_ABSENCE){ //state 考勤状态：0-未记考勤；1-出勤；2-缺勤
				this.updateEnrollTheory(tId,null,num);
				EnrollTheory et = this.getEnrollTheoryByTheoryId(Integer.parseInt(theoryId));
				//更改学员状态，设置为缺理论课（100,101） 您缺席{1}时段的理论课，我们将重新安排
				Map<String,String> msgs = new HashMap<String, String>();
				msgs.put("dstart", new SimpleDateFormat("yyyy-MM-dd").format(et.getClassDate()) + " "+et.getClassTime().split("-")[0].trim());
				schoolManager.changeState(-1, sids, ReqConstants.PROG_STAGE_THEORY, ReqConstants.STAGE_STATE_FAIL, msgs);
			}else {
				//更改学员状态，设置为已上理论课（101,100）
				schoolManager.changeState(-1, sids, ReqConstants.PROG_STAGE_THEORY, ReqConstants.STAGE_STATE_SUCC, null);
			}

			
			return num;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	
	
	
	
	
	
	
}



























