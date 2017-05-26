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
import com.lili.coach.dto.CarInfo;
import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CarManager;
import com.lili.coach.manager.CoachManager;
import com.lili.common.util.DateUtil;
import com.lili.common.util.Page;
import com.lili.common.util.StringUtil;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.school.dto.EnrollLongtrain;
import com.lili.school.dto.EnrollLongtrainExample;
import com.lili.school.dto.EnrollLongtrainStudent;
import com.lili.school.dto.EnrollLongtrainStudentExample;
import com.lili.school.manager.EnrollLongtrainManager;
import com.lili.school.manager.SchoolManager;
import com.lili.school.mapper.EnrollLongtrainMapper;
import com.lili.school.mapper.EnrollLongtrainStudentMapper;
import com.lili.school.quartz.DynamicQuartz;
import com.lili.school.quartz.EnrollOnClassJob;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;

public class EnrollLongtrainManagerImpl implements EnrollLongtrainManager {
	private static Logger logger = LoggerFactory.getLogger(EnrollLongtrainManagerImpl.class);
	@Autowired
	private RedisUtil redisUtil;
    @Autowired
    CoachManager coachManager;
    @Autowired
    CarManager carManager;
    @Autowired
    EnrollLongtrainMapper enrollLongtrainMapper;
    @Autowired
    EnrollLongtrainStudentMapper enrollLongtrainStudentMapper;
	@Autowired
	private StudentManager studentManager;
	@Autowired
	private AuthcodeService authcodeService;
//	@Autowired
//	private DynamicQuartz dynamicQuartz;
	@Autowired
	private SchoolManager schoolManager;

	@Override
	public int addLongtrain(Integer schoolId, String contactMobile,
			String carNo, String classDate, String classTime,
			String classPlace, String carrys) {
		
		Coach coach = coachManager.getCoachByPhoneNum(contactMobile.trim());
		CarInfo car = carManager.getCarInfoByNo(carNo);
		if(null == coach || null == car){
			return -1;
		}
		try {
			EnrollLongtrain record = new EnrollLongtrain();
			record.setCarNo(car.getCarNo());
			record.setCarrys(carrys);
			record.setCarType(car.getCarType());
			record.setClassDate(new SimpleDateFormat("yyyy-MM-dd").parse(classDate));
			record.setClassPlace(classPlace);
			record.setClassTime(classTime);
			record.setCoachId(coach.getCoachId().intValue());
			record.setCoachIdCard(coach.getIdNumber());
			record.setCoachSex(coach.getSex().byteValue());
			record.setContactMobile(contactMobile);
			record.setContactName(coach.getName());
			record.setCtime(new Date());
			record.setDrType(car.getDriveType());
			record.setSchoolId(schoolId);
			
			enrollLongtrainMapper.insertSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public Page<EnrollLongtrain> getLongtrain(int schoolId, String state,
			String pageNo, String pageSize, String dateBegin, String dateEnd,String createBegin,String createEnd) {
		try {
			EnrollLongtrainExample example = new EnrollLongtrainExample();
			EnrollLongtrainExample.Criteria criteria = example.createCriteria();
			if(schoolId>0){
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
			int total = enrollLongtrainMapper.countByExample(example);
			example.setOrderByClause("ctime desc");
			List<EnrollLongtrain> theoryList = enrollLongtrainMapper.selectByExampleWithRowbounds(example, rowBounds);
			return new Page<EnrollLongtrain>(theoryList,pNo,pSize,total);
			
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
	public Page<EnrollLongtrain> getLongtrain(List<Integer> schoolIds, String state,
			String pageNo, String pageSize, String dateBegin, String dateEnd,String createBegin,String createEnd) {
		try {
			EnrollLongtrainExample example = new EnrollLongtrainExample();
			EnrollLongtrainExample.Criteria criteria = example.createCriteria();
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
			int total = enrollLongtrainMapper.countByExample(example);
			example.setOrderByClause("ctime desc");
			List<EnrollLongtrain> theoryList = enrollLongtrainMapper.selectByExampleWithRowbounds(example, rowBounds);
			return new Page<EnrollLongtrain>(theoryList,pNo,pSize,total);
			
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
	public EnrollLongtrain getLongtrainOne(Integer ltId) {
/*		EnrollLongtrain et =redisUtil.get(RedisKeys.REDISKEY.ENROLL_LONGTRAIN+ ltId);
		if(null == et){
			et = enrollLongtrainMapper.selectByPrimaryKey(ltId);
			if(null != et){
				redisUtil.set(RedisKeys.REDISKEY.ENROLL_LONGTRAIN+ ltId,et,RedisKeys.EXPIRE.WEEK);
			}
		}
		return et;*/
		return enrollLongtrainMapper.selectByPrimaryKey(ltId);
	}

	@Override
	public int modifyLongtrain(int schoolId, String ltId, String contactMobile,
			String carNo, String classDate, String classTime,
			String classPlace, String carrys) {
		if(schoolId == 0){
			return -1;
		}
		if(StringUtil.isNullOrEmpty(ltId)){
			return -1;
		}
		try {
			int longtrainId = Integer.parseInt(ltId);
			EnrollLongtrain old = this.getLongtrainOne(longtrainId);
			if(null == old){
				return -1;
			}
			EnrollLongtrain record = new EnrollLongtrain();
			record.setLtId(longtrainId);
			record.setSchoolId(schoolId);
			if(StringUtil.isNotNullAndNotEmpty(contactMobile) && !contactMobile.trim().equals(old.getContactMobile())){
				Coach coach = coachManager.getCoachByPhoneNum(contactMobile.trim());
				if(null == coach){
					return -1;
				}
				record.setCoachId(coach.getCoachId().intValue());
				record.setCoachIdCard(coach.getIdNumber());
				record.setCoachSex(coach.getSex().byteValue());
				record.setContactMobile(contactMobile);
				record.setContactName(coach.getName());
			}
			if(StringUtil.isNotNullAndNotEmpty(carNo) && !carNo.trim().equals(old.getCarNo())){
				CarInfo car = carManager.getCarInfoByNo(carNo);
				if(null == car){
					return -1;
				}
				record.setCarNo(car.getCarNo());
				record.setCarType(car.getCarType());
				record.setDrType(car.getDriveType());
				
			}
			if(StringUtil.isNotNullAndNotEmpty(classDate)){
				record.setClassDate(new SimpleDateFormat("yyyy-MM-dd").parse(classDate));
			}
			if(StringUtil.isNotNullAndNotEmpty(classTime)){
				record.setClassTime(classTime);
			}
			if(StringUtil.isNotNullAndNotEmpty(classPlace)){
				record.setClassPlace(classPlace);
			}
			if(StringUtil.isNotNullAndNotEmpty(carrys)){
				record.setCarrys(carrys);
			}
			
			return enrollLongtrainMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	@Override
	public int addLongtrainStudent(int schoolId, String ltId, String studentIds) {
		if( schoolId == 0){
			return -1;
		}
		if(StringUtil.isEmptyOrWhitespaceOnly(ltId)){
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
			int tId = Integer.parseInt(ltId);
			for(Student stu:stus){
				if(stu.getApplyexam() != ReqConstants.PROG_STAGE_SUBJECT_LONGTRAIN){
					hasOk = false;
					continue;
				}
				EnrollLongtrainStudent record = new EnrollLongtrainStudent();
				record.setSchoolId(schoolId);
				record.setLtId(tId);
				record.setStudentId(stu.getStudentId());
				record.setName(stu.getName());
				record.setPhoneNum(stu.getPhoneNum());
				record.setSex(stu.getSex());
				record.setDrType(stu.getDrType());
				record.setIdNumber(stu.getIdNumber());
				record.setFlowNo(stu.getFlowNo());
				record.setIsImport(stu.getIsImport());

				int res = enrollLongtrainStudentMapper.insertSelective(record);
				if(res != 1){
					total --;
					hasOk = false;
				}
			}
			this.updateEnrollLongtrain(tId,total,null);
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

	@Override
	public int deleteLongtrainStudent(int schoolId, String ltId,
			String studentIds) {
		if(StringUtil.isEmptyOrWhitespaceOnly(ltId)){
			return -1;
		}
		try {
			int tId = Integer.parseInt(ltId);
			List<Long> sids = new ArrayList<Long>();
			String[] aa = studentIds.split(",");
			for(String a:aa){
				Long b = Long.parseLong(a.trim());
				sids.add(b);
			}
			EnrollLongtrainStudentExample example = new EnrollLongtrainStudentExample();
			EnrollLongtrainStudentExample.Criteria criteria = example.createCriteria();
			criteria.andSchoolIdEqualTo(schoolId);
			criteria.andLtIdEqualTo(tId);
			criteria.andStudentIdIn(sids);
			
			int num =  enrollLongtrainStudentMapper.deleteByExample(example); //直接物理删除，避免重新加入时主键冲突;
			this.updateEnrollLongtrain(tId,- num,null); //need check
			return num;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	private void updateEnrollLongtrain(int tId,Integer totalChange,Integer failedChange) {
		if(null == totalChange && null == failedChange){
			EnrollLongtrainStudentExample example = new EnrollLongtrainStudentExample();
			EnrollLongtrainStudentExample.Criteria criteria = example.createCriteria();
			criteria.andLtIdEqualTo(tId);
			int total = enrollLongtrainStudentMapper.countByExample(example);
			criteria.andStateEqualTo(ReqConstants.THEORY_STU_ABSENCE);
			int failed = enrollLongtrainStudentMapper.countByExample(example);
			EnrollLongtrain record = new EnrollLongtrain();
			record.setLtId(tId);
			record.setTotal(total);
			record.setFailed(failed);
			enrollLongtrainMapper.updateByPrimaryKeySelective(record);
		}
		if(null != totalChange){
			EnrollLongtrain et = this.getLongtrainOne(tId);
			et.setTotal(et.getTotal() + totalChange);
			enrollLongtrainMapper.updateByPrimaryKeySelective(et);
		}
		if(null != failedChange){
			EnrollLongtrain et = this.getLongtrainOne(tId);
			et.setFailed(et.getFailed() + failedChange);
			enrollLongtrainMapper.updateByPrimaryKeySelective(et);
		}

		redisUtil.delete(RedisKeys.REDISKEY.ENROLL_LONGTRAIN+ tId);
		
	}

	@Override
	public int changeLongtrainClass(int schoolId, String ltId, String state) {
		EnrollLongtrain et = this.getLongtrainOne(Integer.parseInt(ltId));
		if(null == et || et.getSchoolId() != schoolId){
			return -1;
		}
		int status = Integer.parseInt(state);
		if(et.getState() >= status){
			return -1;//状态不允许回退 	0-未确认；1-待上课；2-已上课；3-已取消
		}
		if(status == ReqConstants.THEORY_CLASS_OPEN){ 
			//（1）设置开课，发送短信通知学员
			EnrollLongtrainStudentExample example = new EnrollLongtrainStudentExample();
			EnrollLongtrainStudentExample.Criteria criteria = example.createCriteria();
			criteria.andSchoolIdEqualTo(schoolId);
			criteria.andLtIdEqualTo(Integer.parseInt(ltId));
			List<EnrollLongtrainStudent> ets = enrollLongtrainStudentMapper.selectByExample(example);
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
			msgs.put("carrys", et.getCarrys());
			msgs.put("classPlace", et.getClassPlace());
			msgs.put("contact", et.getContactName() + " "+ et.getContactMobile());
			
			try {
				logger.debug("SHORT_MESSAGE_TYPE_LONGTRAIN_ON mobiles:" + sb.toString());
				authcodeService.sendMsg(ReqConstants.SHORT_MESSAGE_TYPE_LONGTRAIN_ON, sb.toString(), msgs);
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
				EnrollOnClassJob enrollOnClassJob = new EnrollOnClassJob(ltId,afterSecond,2);
				dynamicQuartz.addJob(enrollOnClassJob);
				
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			//（3）更改学员状态，设置为已约长考状态（501,1），APP透传课程详情   您已成功预约长考，考试日期为{1}，上车地点为{2}
			msgs.put("dstart", new SimpleDateFormat("yyyy-MM-dd").format(et.getClassDate()) + " "+et.getClassTime().split("-")[0].trim());
			List<Long> studentIds = new ArrayList<Long>();
			for(EnrollLongtrainStudent one:ets){
				studentIds.add(one.getStudentId());
			}
			schoolManager.changeState(-1, studentIds, ReqConstants.PROG_STAGE_SUBJECT_LONGTRAIN, ReqConstants.STAGE_STATE_SUBMIT, msgs);

			
			
		}else if(status == ReqConstants.THEORY_CLASS_CANCEL){
			//（1）设置取消开课，发送短信通知学员
			EnrollLongtrainStudentExample example = new EnrollLongtrainStudentExample();
			EnrollLongtrainStudentExample.Criteria criteria = example.createCriteria();
			criteria.andSchoolIdEqualTo(schoolId);
			criteria.andLtIdEqualTo(Integer.parseInt(ltId));
			List<EnrollLongtrainStudent> ets = enrollLongtrainStudentMapper.selectByExample(example);
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<ets.size();i++){
				sb.append(ets.get(i).getPhoneNum());
				if(i != ets.size() -1){
					sb.append(",");
				}
			}
			
			Map<String,String> msgs = new HashMap<String, String>();
			msgs.put("classDate",new SimpleDateFormat("yyyy-MM-dd").format(et.getClassDate()));
			msgs.put("className", "长考");

			try {
				logger.debug("SHORT_MESSAGE_TYPE_LONGTRAIN_OFF mobiles:" + sb.toString());
				authcodeService.sendMsg(ReqConstants.SHORT_MESSAGE_TYPE_LONGTRAIN_OFF, sb.toString(), msgs);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//（2）更改学员状态，设置为等待预约，APP透传课程详情
			List<Long> studentIds = new ArrayList<Long>();
			for(EnrollLongtrainStudent one:ets){
				studentIds.add(one.getStudentId());
			}
			schoolManager.changeState(-1, studentIds, ReqConstants.PROG_STAGE_SUBJECT_LONGTRAIN, ReqConstants.STAGE_STATE_NONE, msgs);
		}
		
		et.setState(status);
		enrollLongtrainMapper.updateByPrimaryKeySelective(et);
		redisUtil.delete(RedisKeys.REDISKEY.ENROLL_LONGTRAIN+ ltId);
		return 0;
	}

	@Override
	public int changeLongtrainStudentState(int schoolId, String ltId,
			String studentIds, String state) {
		if(StringUtil.isEmptyOrWhitespaceOnly(ltId)){
			return -1;
		}
		try {
			int tId = Integer.parseInt(ltId);
			byte status = Byte.parseByte(state);
			List<Long> sids = new ArrayList<Long>();
			String[] aa = studentIds.split(",");
			for(String a:aa){
				Long b = Long.parseLong(a.trim());
				sids.add(b);
			}
			EnrollLongtrainStudentExample example = new EnrollLongtrainStudentExample();
			EnrollLongtrainStudentExample.Criteria criteria = example.createCriteria();
			criteria.andSchoolIdEqualTo(schoolId);
			criteria.andLtIdEqualTo(tId);
			criteria.andStudentIdIn(sids);
			
			EnrollLongtrainStudent record = new EnrollLongtrainStudent();
			record.setState(status);
			
			int num =  enrollLongtrainStudentMapper.updateByExampleSelective(record, example);
			if(status == ReqConstants.THEORY_STU_ABSENCE){ //state 考勤状态：0-未记考勤；1-合格；2-不合格
				this.updateEnrollLongtrain(tId,null,num);
				//更改学员状态，设置为已上长考课（501,100），或者缺长考课（500,101） TODO 您缺席{1}时段的理论课，我们将重新安排
				schoolManager.changeState(-1, sids, ReqConstants.PROG_STAGE_SUBJECT_LONGTRAIN, ReqConstants.STAGE_STATE_FAIL, null);
			}else {
				//更改学员状态，设置为已上理论课（101,100）
				schoolManager.changeState(-1, sids, ReqConstants.PROG_STAGE_SUBJECT_LONGTRAIN, ReqConstants.STAGE_STATE_SUCC, null);
			}
			
			return num;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public Page<EnrollLongtrainStudent> getLongtrainStudent(int schoolId,
			String ltId, String pageNo, String pageSize, String state,
			String isImport) {
		try {
			EnrollLongtrainStudentExample example = new EnrollLongtrainStudentExample();
			EnrollLongtrainStudentExample.Criteria criteria = example.createCriteria();
			criteria.andSchoolIdEqualTo(schoolId);
			criteria.andLtIdEqualTo(Integer.parseInt(ltId));
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
			int total = enrollLongtrainStudentMapper.countByExample(example);
			List<EnrollLongtrainStudent> data = enrollLongtrainStudentMapper.selectByExampleWithRowbounds(example, rowBounds);
			
			return new Page<EnrollLongtrainStudent>(data,pNo,pSize,total);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
	
	

}













































