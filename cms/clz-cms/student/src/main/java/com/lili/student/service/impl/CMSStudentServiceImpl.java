package com.lili.student.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lili.cms.constant.LogConstant;
import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.logic.StudentAuthState;
import com.lili.cms.logic.StudentState;
import com.lili.cms.util.DateUtil;
import com.lili.cms.util.PicUtil;
import com.lili.cms.util.StringUtil;
import com.lili.cms.util.ValidatorUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.finance.service.ICMSCouponService;
import com.lili.finance.service.ICmsBankCardVerifyService;
import com.lili.finance.service.ICmsUserMoneyService;
import com.lili.finance.vo.BankCardVerifyVo;
import com.lili.finance.vo.StudentCouponVo;
import com.lili.finance.vo.UserMoneyVo;
import com.lili.log.model.LogCommon;
import com.lili.log.service.CMSLogCommonService;
import com.lili.order.model.STOVo;
import com.lili.order.service.CMSOrderService;
import com.lili.order.service.ICmsEnrollOrderService;
import com.lili.order.vo.EnrollOrderVo;
import com.lili.report.vo.StatisticsStudentProgress;
import com.lili.report.vo.StatisticsStudentProgressReport;
import com.lili.school.model.Car;
import com.lili.school.model.Region;
import com.lili.school.model.School;
import com.lili.school.model.SchoolBDTO;
import com.lili.school.service.CMSCarService;
import com.lili.school.service.CMSRegionService;
import com.lili.school.service.CMSSchoolService;
import com.lili.school.service.SchoolService;
import com.lili.student.manager.CMSStudentManager;
import com.lili.student.manager.StudentManager;
import com.lili.student.model.Student;
import com.lili.student.model.StudentAccount;
import com.lili.student.model.StudentAuthVo;
import com.lili.student.model.StudentBDTO;
import com.lili.student.model.StudentNBDTO;
import com.lili.student.model.StudentStateVo;
import com.lili.student.model.StudentVo;
import com.lili.student.service.CMSStudentService;
import com.lili.student.service.RechargeService;
import com.lili.student.service.StudentAuthService;
import com.lili.student.service.StudentService;
import com.lili.student.vo.VipCustomVo;

public class CMSStudentServiceImpl implements CMSStudentService{

	protected  final Logger access = Logger.getLogger(this.getClass());
	
	@Autowired
	CMSStudentManager cmsStudentManager;
    @Autowired
    StudentManager studentManager;
	@Autowired
	private StudentState studentState;
	@Autowired
	private StudentAuthState studentAuthState;
	@Autowired
	CMSOrderService cmsOrderService;
	@Autowired
	CMSCarService cmsCarService;
	@Autowired
	CMSLogCommonService cmsLogCommonService;
	
	@Autowired
	ICmsBankCardVerifyService bankCardVerifyService;
	
	@Autowired
	ICMSCouponService cmsCouponService;
	
	@Autowired
	ICmsUserMoneyService userMoneyService;
	
	@Autowired
	RedisUtil redisUtil;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	SchoolService schoolService;
	
	@Autowired
	ICmsEnrollOrderService cmsEnrollOrderService;
	
	/**
	 * APP接口
	 */
	@Autowired
	private StudentAuthService studentAuthService;
	
	@Autowired
	CMSSchoolService cschoolService;
	
	@Autowired
	CMSRegionService regionService;
	
	@Autowired
	RechargeService rechargeService;

	@Override
	public ResponseMessage findLiliBatch(StudentBDTO dto) throws Exception {
		PagedResult<StudentVo> batch = cmsStudentManager.findLiliBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage findJxBatch(StudentBDTO dto) throws Exception {
		PagedResult<StudentVo> batch = cmsStudentManager.findJxBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}
	
	@Override
	public ResponseMessage findLiliBatchTheory(StudentBDTO dto,int theoryId) throws Exception {
		PagedResult<StudentVo> batch = cmsStudentManager.findLiliBatchTheory(dto,theoryId);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage findJxBatchTheory(StudentBDTO dto,int theoryId) throws Exception {
		PagedResult<StudentVo> batch = cmsStudentManager.findJxBatchTheory(dto,theoryId);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public List<StudentVo> findExportSource(StudentBDTO dto) throws Exception {
		return cmsStudentManager.findExportBatch(dto);
	}

	@Override
	public ResponseMessage findCerBatch(StudentBDTO dto) throws Exception {
		PagedResult<StudentAuthVo> batch = cmsStudentManager.findCerBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage findLinBatch(StudentBDTO dto) throws Exception {
		PagedResult<StudentAuthVo> batch = cmsStudentManager.findLinBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage findImgById(long studentId) throws Exception {
		StudentAuthVo vo = cmsStudentManager.findImgById(studentId);
		if(vo != null){
			vo.setIdPhotoBack(PicUtil.getPicFromQN(vo.getIdPhotoBack()));
			vo.setIdPhotoFront(PicUtil.getPicFromQN(vo.getIdPhotoFront()));
			vo.setLiPhotoBack(PicUtil.getPicFromQN(vo.getLiPhotoBack()));
			vo.setLiPhotoFront(PicUtil.getPicFromQN(vo.getLiPhotoFront()));
		}
		return new ResponseMessage().addResult("vo", vo);
	}

	@Override
	public ResponseMessage findUnboundBatch(StudentBDTO dto) throws Exception {
		PagedResult<StudentVo> batch = cmsStudentManager.findUnboundBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage findUnSchoolBatch(StudentBDTO dto) throws Exception {
		PagedResult<StudentVo> batch = cmsStudentManager.findUnSchoolBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage findStudentByCoach(StudentBDTO dto) throws Exception {
		String[] stuIds = cmsStudentManager.findIdsByCoach(dto.getCoachId());
		StringBuffer ids = new StringBuffer();
		if(stuIds != null && stuIds.length > 0){
			for(String stuId : stuIds){
				ids.append(stuId + ",");
			}
		}
		if(ids.length() > 1){
			dto.setStudentIds(ids.substring(0, ids.length() - 1));
		}

		PagedResult<StudentVo> batch = cmsStudentManager.findByCoach(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}
	
	@Override
	public ResponseMessage getStudent(StudentBDTO dto) throws Exception {
		PagedResult<StudentVo> batch = cmsStudentManager.getStudent(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage findOne(StudentNBDTO dto) throws Exception {
		Student student = cmsStudentManager.findOne(dto);
		if(student != null)
			student.setHeadIcon(PicUtil.getPicFromQN(student.getHeadIcon()));
		School school=cschoolService.findSchoolById(student.getSchoolId());
		if(school!=null){
			student.setSchool(school.getName());
		}
		return new ResponseMessage().addResult("student", student);
	}

	@Override
	public StudentAuthVo findStudentAuth(long id) throws Exception {
		return cmsStudentManager.findAuthState(id);
	}

	@Override
	public Student findByPhone(String phoneNum) throws Exception {
		return cmsStudentManager.findByPhone(phoneNum);
	}

	@Override
	public ResponseMessage updateOne(LogCommon logCommon,Student student) throws Exception {

//		if(!validateNums(student.getPhoneNum(), student.getIdNumber())){
//			return new ResponseMessage("身份证号或手机号不正确");
//		}
		StudentNBDTO studentNBDTO = new StudentNBDTO();
		studentNBDTO.setStudentId(student.getStudentId());
		Student student0 = cmsStudentManager.findOne(studentNBDTO);
		if(student0 == null){
			return new ResponseMessage("更新失败,学员不存在");
		}
		List<Student> students = findByNums(student);
		if(students != null && students.size() > 0){
			return new ResponseMessage("手机号或身份证号已注册,更新失败");
		}
		
		com.lili.student.dto.Student _student = studentService.getStudent(student.getStudentId());
		
		/**
		 * record detail change 
		 */
		JSON json=getChange(student,_student);
		logCommon.setDetail(json.toJSONString());
		
		_student.setName(student.getName());
		_student.setSex(student.getSex());
		_student.setPhoneNum(student.getPhoneNum());
		_student.setIdNumber(student.getIdNumber());
		_student.setApplyCarType(String.valueOf(student.getApplyCarType()));
		_student.setDrType(student.getApplyCarType());
		
		int count = studentService.updateStudent(_student);
		
		//20161102更新学员手机号同步更新大客户表信息手机号
		if ((student.getPhoneNum() != null && !"".equals(student.getPhoneNum())) || (student.getName() != null && !"".equals(student.getName()))) {
			VipCustomVo vipCustomVo = new VipCustomVo();
			vipCustomVo.setMobile(student.getPhoneNum());
			vipCustomVo.setCname(student.getName());
			vipCustomVo.setStudentId(student.getStudentId());
			rechargeService.updateVipCustom(vipCustomVo);
		}
		
//		Long count = cmsStudentManager.updateOne(student);
		if(count <= 0){
			return new ResponseMessage("更新失败");
		}

		if(logCommon != null){
			logCommon.setRelateId(String.valueOf(student.getStudentId()));
		}
		return new ResponseMessage();
	}
	
	private JSONObject getChange(Student n,com.lili.student.dto.Student o){
		JSONObject json=new JSONObject();
		if(n.getName()!=null&&n.getName().length()>0&&!n.getName().equals(o.getName())){
			JSONObject j=new JSONObject();
			j.put("from", (o.getName()==null?"null":o.getName()));
			j.put("to", n.getName());
			json.put("name", j);
		}
		if(n.getPhoneNum()!=null&&n.getPhoneNum().length()>0&&!n.getPhoneNum().equals(o.getPhoneNum())){
			JSONObject j=new JSONObject();
			j.put("from", (o.getPhoneNum()==null?"null":o.getPhoneNum()));
			j.put("to", n.getPhoneNum());
			json.put("phoneNum", j);
		}
		if(n.getSex()!=null&&!n.getSex().equals(o.getSex())){
			JSONObject j=new JSONObject();
			j.put("from", (o.getSex()==null?"null":o.getSex()));
			j.put("to", n.getSex());
			json.put("sex", j);
		}
		if(n.getIdNumber()!=null&&n.getIdNumber().length()>0&&!n.getIdNumber().equals(o.getIdNumber())){
			JSONObject j=new JSONObject();
			j.put("from", (o.getIdNumber()==null?"null":o.getIdNumber()));
			j.put("to", n.getIdNumber());
			json.put("idNumber", j);
		}
		

		if(n.getApplyCarType()!=null&&!String.valueOf(n.getApplyCarType()).equals(o.getApplyCarType())){

			JSONObject j=new JSONObject();
			j.put("from", (o.getApplyCarType()==null?"null":o.getApplyCarType()));
			j.put("to", n.getApplyCarType());
			json.put("applyCarType", j);
		}
		if(n.getApplyexam()!=null&&n.getApplystate()!=null){
			if(n.getApplyexam()!=o.getApplyexam()||n.getApplystate()!=o.getApplystate()){
				JSONObject j=new JSONObject();
				j.put("from", o.getApplyexam()+","+o.getApplystate());
				j.put("to", n.getApplyexam()+","+n.getApplystate());
				json.put("applyexam", j);
			}
		}
		return json;
	}

	@Override
	public ResponseMessage updateAuthState(LogCommon token,int type,String ids, StudentAuthVo vo)
			throws Exception {
		String[] idsArray = ids.toString().split(",");
		List<LogCommon> logList = new ArrayList<LogCommon>();
		List<Long> idList = new ArrayList<Long>();
		boolean canReset = true;

		if(idsArray != null && idsArray.length > 0){
			for(String id : idsArray){
				idList.add(Long.valueOf(id));
				StudentAuthVo studentAuthVo = findStudentAuth(Long.parseLong(id));
				if(!studentAuthState.compareSteps(studentAuthVo.getState()+"", vo.getState()+"")){
					canReset = false;
					break;
				}

				if(token != null){
				LogCommon logCommon =  (LogCommon)token.clone();
				String operateTime = DateUtil.formatDatetime(logCommon.getOperateTime());
				logCommon.setRelateId(id);
				logCommon.setRemark(logCommon.getUserName() + " " + operateTime + " 更新了当前记录相关数据 ");
				logList.add(logCommon);
				}
			}
		}

		if(canReset){
			int count = studentAuthService.changeState(type, vo.getState(),idList , vo.getRemark());
//			if(count != null && count == 0){
//			Long count = cmsStudentManager.updateAuthState(StringUtil.recombinateStrByComma(ids),vo);
				if(count == 0){
				cmsLogCommonService.insertLogList(logList);
				return new ResponseMessage();
			}
		}	
		return new ResponseMessage("更新失败");
		
	}

	@Override
	public Long updateLinState() throws Exception {
		return cmsStudentManager.updateLinState();
	}


	private boolean validateNums(String phoneNum,String idNumber){
		return (ValidatorUtil.isMobile(phoneNum) && ValidatorUtil.isIDCard(idNumber));
	}
	
	@Override
	public ResponseMessage insertOne(LogCommon logCommon,Student student) throws Exception {

		//		if(!validateNums(student.getPhoneNum(), student.getIdNumber())){
		//			return new ResponseMessage("身份证号或手机号不正确");
		//		}

		List<Student> student0 = findByNums(student);
		if(student0 == null || student0.size() <= 0){
			student.setDrType((byte)student.getApplyCarType());


			com.lili.student.dto.Student _student = new com.lili.student.dto.Student();
			_student.setName(student.getName());
			_student.setSex(student.getSex());
			_student.setPhoneNum(student.getPhoneNum());
			_student.setIdNumber(student.getIdNumber());
			_student.setApplyCarType(String.valueOf(student.getApplyCarType()));
			_student.setDrType(student.getDrType());
			
			long id = studentManager.addStudent(_student);
			if(id <= 0){
				return new ResponseMessage("插入失败");
			}
		}else {
			return new ResponseMessage("手机号或身份证号已注册,插入失败");
		}

		if(logCommon != null){
		logCommon.setRelateId(String.valueOf(student.getStudentId()));
		}
		return new ResponseMessage();
	}

	@Override
	public ResponseMessage findCCO(long studentId) throws Exception {

		STOVo vo = cmsOrderService.findCOByStudentId(studentId);
		if(vo == null || vo.getCoachId() == 0){
			return new ResponseMessage();
//			return new ResponseMessage("未找到学员订单或未找到关联的教练");
		}
		Long time = cmsOrderService.findTimeByStudentId(studentId);
		List<Car> cars = cmsCarService.findByStudentId(studentId);
		
		if(time != null){
			vo.setTotalOrderTime(time);
		}
		StringBuffer carIds = new StringBuffer();
		StringBuffer carNos = new StringBuffer();
		
		
		if(cars != null && cars.size() > 0){
			for(int i = 0;i < cars.size();i ++){
				Car car = cars.get(i);
				carIds.append(car.getCarId() + ",");
				carNos.append(car.getCarNo() + ",");
			}
			vo.setCarId(carIds.toString().substring(0, carIds.length()-1));
			vo.setCarNo(carNos.toString().substring(0, carNos.length()-1));
		}
		vo.setCoachIcon(PicUtil.getPicFromQN(vo.getCoachIcon()));

		return new ResponseMessage().addResult("cco",vo);
	}

	@Override
	public ResponseMessage updateSCRelation(LogCommon logCommon,long o_coachId,long c_coachId, long id)
			throws Exception {

		if (o_coachId == c_coachId)
			return new ResponseMessage();
		Long count = null;
		//o_coachId为空,表示新增关系
		if(o_coachId == 0){
			count = newCSRelation(o_coachId, c_coachId, id);
		}else {
			count = oldCSRelation(o_coachId, c_coachId, id);
		}
		
		if(count == null || count <= 0){
			return new ResponseMessage("更新失败");
		}

		if(logCommon != null){
		logCommon.setRelateId(String.valueOf(id));
		String operateTime = DateUtil.formatDatetime(logCommon.getOperateTime());
		logCommon.setRemark(logCommon.getUserName() + " " + operateTime + " 更新了学员关系 ");
		}
		return new ResponseMessage();
	}

	/**
	 * 现在学员教练关系为多对多,在mycoaches关系表中status=0为正常关系,那其他的都应该是1
	 * 所以这里现将改学员在关系表中的数据都置1,
	 * @param o_coachId
	 * @param c_coachId
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private Long oldCSRelation(long o_coachId,long c_coachId, long id) throws Exception {
		//Long relation = cmsStudentManager.findSCRelation(id);
		//判断教练和学员是否之前就存在关系
		//if(relation == null || relation == 0){
		//	return 0L;
		//}
		return cancleAndUpdate(o_coachId, c_coachId, id);
	}

	/**
	 * 新增教练学员关系,但是分为数据库中已存在关系和不存在关系的情况
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	private Long newCSRelation(long o_coachId,long c_coachId, long id) throws Exception {
		Long relation = cmsStudentManager.findSCRelation(id);
		
		//判断教练和学员是否之前就存在关系
		if(relation == null || relation == 0){
			return cmsStudentManager.insertSCRelation(id, c_coachId);
		}else {
			return cancleAndUpdate(o_coachId, c_coachId, id);
		}
	}

	/**
	 * 更新学员教练关系,先要将该学员的所有关系数据都置为无效,即status=1,然后再将c_coachId的那条记录status=0
	 * @param o_coachId
	 * @param c_coachId
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private Long cancleAndUpdate(long o_coachId, long c_coachId, long id)
			throws Exception {
		Long count = cmsStudentManager.cancleSCRelation(id);
		if(count != null && count > 0){
			return cmsStudentManager.insertSCRelation(id, c_coachId);
		}
		return 0L;
	}


	@Override
	public ResponseMessage deleteSCRelation(LogCommon logCommon,long coachId, long id)
			throws Exception {
		Long count = cmsStudentManager.deleteSCRelation(id, coachId);
		if(count == null || count <= 0){
			return new ResponseMessage("关系解除失败");
		}

		if(logCommon != null){
		logCommon.setRelateId(String.valueOf(id));
		String operateTime = DateUtil.formatDatetime(logCommon.getOperateTime());
		logCommon.setRemark(logCommon.getUserName() + " " + operateTime + " 更新了学员关系 ");
		}
		return new ResponseMessage();
	}
	
	

	@Override
	public ResponseMessage insertSCRelation(long c_coachId, long id)
			throws Exception {
		if(cmsStudentManager.insertSCRelation(id, c_coachId) <= 0){
			return new ResponseMessage("关系插入失败");
		}
		return new ResponseMessage();
	}

	@Override
	public String findStudentInfo(StudentBDTO dto, String isEarning, String isBalance, String operateType, String channel) throws Exception {
		String resp = null;
		try {
			StudentAccount stuInfo = cmsStudentManager.findStudentInfo(dto);
			
			BankCardVerifyVo bankCardVerifyVo = new BankCardVerifyVo();
			bankCardVerifyVo.setUserId((int)dto.getStudentId());
			bankCardVerifyVo.setUserType(2);
			List<BankCardVerifyVo> bankList = bankCardVerifyService.queryBankBoundList(bankCardVerifyVo);//银行卡信息
			
			StudentCouponVo coupon = new StudentCouponVo();
			coupon.setStudentid(dto.getStudentId());
			coupon.setIsUsed((byte)0);
			coupon.setIsValid((byte)1);
			List<StudentCouponVo> couponList = cmsCouponService.queryCouponList(coupon);//优惠券信息
			
			UserMoneyVo userMoneyVo = new UserMoneyVo();
			userMoneyVo.setStartTime(dto.getStartTime());
			userMoneyVo.setEndTime(dto.getEndTime());
			userMoneyVo.setPageNo(dto.getPageNo());
			userMoneyVo.setPageSize(dto.getPageSize());
			userMoneyVo.setUserId(dto.getStudentId());
			userMoneyVo.setUserType(2);
			if(!"".equals(operateType) && operateType != null){
				userMoneyVo.setOperateType(Integer.valueOf(operateType));
			}
			if(isBalance != null && !"".equals(isBalance)){
				userMoneyVo.setIsBalance(Integer.valueOf(isBalance));
			}
			if(isEarning != null && !"".equals(isEarning)){
				userMoneyVo.setIsEarning(Integer.valueOf(isEarning));
			}
			
			PagedResult<UserMoneyVo> moneyList = userMoneyService.queryUserDetailList(userMoneyVo, channel);//资产交易明细
			
			Long sumMoney = userMoneyService.querySumMoneyByIsEarning(userMoneyVo, channel);
			
			resp = new ResponseMessage()
				.addResult("stuInfo", stuInfo)
				.addResult("bankList", bankList)
				.addResult("couponList", couponList)
				.addResult("sumMoney", sumMoney)
				.addResult("moneyList", moneyList)
				.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public ResponseMessage resetState(LogCommon token,int ttid,String studentIds, StudentStateVo vo)
			throws Exception {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("note", vo.getNote());
		params.put("pmid", vo.getPmid());
		params.put("ptype", vo.getPtype());
		List<LogCommon> logList = new ArrayList<LogCommon>();
		String[] idsArray = studentIds.toString().split(",");
		List<Long> studentIdList = new ArrayList<Long>();
		for(int i=0;i < idsArray.length;i ++){
			StudentNBDTO dto = new StudentNBDTO();
			dto.setStudentId(Long.parseLong(idsArray[i]));
			Student student = cmsStudentManager.findOne(dto);
			studentIdList.add(Long.parseLong(idsArray[i]));
			

			if(token != null){
				com.lili.student.dto.Student ostudent=new com.lili.student.dto.Student();
				ostudent.setApplyexam(student.getApplyexam());
				ostudent.setApplystate(student.getApplystate());
				
				Student nstudent=new Student();
				nstudent.setApplyexam(vo.getApplyexam());
				nstudent.setApplystate(vo.getApplystate());
				LogCommon logCommon = (LogCommon)token.clone();
				String operateTime = DateUtil.formatDatetime(logCommon.getOperateTime());
				logCommon.setRelateId(student.getStudentId()+"");
				logCommon.setRemark(logCommon.getUserName() + " " + operateTime + " 更新了当前记录相关数据 ");
				JSON json= getChange(nstudent, ostudent);
				logCommon.setDetail(json.toJSONString());
				logList.add(logCommon);
			}
		}
		int count = schoolService.changeState(ttid, studentIdList, vo.getApplyexam(), vo.getApplystate(), params);
		
		if(count == 0){
			cmsLogCommonService.insertLogList(logList);
			return new ResponseMessage();
		}
		return new ResponseMessage("更新失败");
		
//		List<StudentStateVo> studentStateVos = new ArrayList<StudentStateVo>();
//		boolean canContinue = true;
//
//		StringBuffer ids = new StringBuffer();
//		if(idsArray != null && idsArray.length > 0){
//			for(String studentId : idsArray){
//				StudentNBDTO dto = new StudentNBDTO();
//				dto.setStudentId(Long.parseLong(studentId));
//				Student student = cmsStudentManager.findOne(dto);
//				boolean canReset = studentState.compareSteps(student.getApplyexam() + "," + student.getApplystate()
//						, vo.getApplyexam() + "," + vo.getApplystate());
//				
//				StudentStateVo stateVo = new StudentStateVo();
//				stateVo.setApplyexam(student.getApplyexam());
//				stateVo.setApplystate(student.getApplystate());
//				stateVo.setReqApplyexam(vo.getApplyexam());
//				stateVo.setReqApplystate(vo.getApplystate());
//				stateVo.setCanReset(canReset==true?0:1);
//				stateVo.setStudentId(student.getStudentId());
//				stateVo.setStudentName(student.getName());
//				studentStateVos.add(stateVo);
//				if(!canReset){
//					canContinue = false;
//				}
//				
//				LogCommon logCommon = createLog(token);
//				String operateTime = DateUtil.formatDatetime(logCommon.getOperateTime());
//				logCommon.setRelateId(student.getStudentId()+"");
//				logCommon.setRemark(logCommon.getUserName() + " " + operateTime + " 更新了当前记录相关数据 ");
//				logList.add(logCommon);
//				ids.append("'" +studentId + "',");
//			}
//		}
//		if(ids.length() > 1 && canContinue){
//			Long count = cmsStudentManager.resetState(ids.substring(0, ids.length() - 1), vo.getApplyexam(), vo.getApplystate());
//			if(count != null && count > 0){
//				cmsLogCommonService.insertLogList(logList);
//				return new ResponseMessage();
//			}
//		}else {
//			return new ResponseMessage("更新失败").addPagedResult(studentStateVos);
//		}
//		return new ResponseMessage("更新失败");

	}
	
	@Override
	public ResponseMessage allot(String studentIdList, String region, String schoolId, String schoolName) throws Exception {
		try {
			String[] idList  = studentIdList.split(",");
			if(schoolId == null || "".equals(schoolId)) {
				return new ResponseMessage(MessageCode.MSG_FAIL);
			}
			if(region == null || "".equals(region)) {
				return new ResponseMessage(MessageCode.MSG_FAIL);
			}
			if(idList == null || idList.length == 0) {
				return new ResponseMessage(MessageCode.MSG_FAIL);
			}
			List<Student> stuList = new ArrayList<Student>();
			Student student= null;
			Long studentId=null;
			StudentNBDTO dto=null;
			EnrollOrderVo enrollOrderVo = new EnrollOrderVo();
			EnrollOrderVo enrollOrderVoCity = new EnrollOrderVo();
			List<Long> studentIds = new ArrayList<Long>();
			for (int i =0; i< idList.length; i++) {
				studentId =Long.parseLong(idList[i]);
				studentIds.add(Long.parseLong(idList[i]));
				//增加校验，学员是否已分配驾校
				dto = new StudentNBDTO();
				dto.setStudentId(studentId);
				Student studentInfo = cmsStudentManager.findOne(dto);
				if(studentInfo.getSchoolId() != 0){
					return new ResponseMessage(MessageCode.MSG_FAIL);
				}
				//判断学员分配的驾校是否跟学员报名的城市是否一致
				enrollOrderVo.setStudentId(studentId);
				enrollOrderVoCity = cmsEnrollOrderService.queryEnrollOrderByStudentId(enrollOrderVo);
				if(enrollOrderVoCity!= null && enrollOrderVoCity.getCityId()!=null){
					if(!region.equals(String.valueOf(enrollOrderVoCity.getCityId()))){
						return new ResponseMessage(601,MessageCode.STUDNENT_ALLOT_IS_NOT_SAME_CITY);
					}
				}
				student = new Student();
				student.setSchoolId(Long.parseLong(schoolId));
				student.setStudentId(studentId);
				student.setImportSrc(schoolName);
				student.setCityId(Integer.parseInt(region));
				stuList.add(student);
			}
			cmsStudentManager.updateSchoolId(stuList);
			
			Map<String, String> params = null;
			schoolService.changeState(-1, studentIds, 5, 0, params);//无流水的状态更新
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public Long updateStates(String studentIds) throws Exception {
		return cmsStudentManager.updateStates(studentIds);
	}

	@Override
	public Integer queryTotalStudent(StudentBDTO dto) throws Exception {
		return cmsStudentManager.findTotalStudent(dto);
	}

	@Override
	public List<Student> findByNums(Student student)
			throws Exception {
		return cmsStudentManager.findByNums(student);
	}

	@Override
	public List<Student> queryIsExistNum(List<Student> studentList) {
		return cmsStudentManager.queryIsExistNum(studentList);
	}
	
	@Override
	public List<Student> queryIsNotExistNum(List<Student> studentList) {
		return cmsStudentManager.queryIsNotExistNum(studentList);
	}

	@Override
	public List<Student> queryIsNotExist(List<Student> studentList) {
		return cmsStudentManager.queryIsNotExist(studentList);
	}

	@Override
	public Integer queryIsHandle(List<Student> studentList) {
		return cmsStudentManager.queryIsHandle(studentList);
	}

	@Override
	public void updateBatchFlowNo(List<Student> studentList,List<Student> notFlowStudent, LogCommon logCommon) {
		
		try {
			cmsStudentManager.updateBatchFlowNo(studentList);//流水更新
			
			List<Long> studentIds = new ArrayList<Long>();
			for(int i = 0; i < notFlowStudent.size(); i++){
				studentIds.add(notFlowStudent.get(i).getStudentId());
				access.info("********************************** notFlowStudent : " + notFlowStudent.get(i).getStudentId());
				redisUtil.delete(REDISKEY.STUDENT_INFO + notFlowStudent.get(i).getStudentId());//清理缓存
			}
			Map<String, String> params = null;
			access.info("********************************** studentIds " + studentIds);
			schoolService.changeState(-1, studentIds, 6, 100, params);//无流水的状态更新--且状态为：受理中、受理失败
			
			StringBuilder remark = new StringBuilder();
			List<LogCommon> logList = new ArrayList<LogCommon>();
			for (int i = 0; i < studentList.size(); i++) {
				remark.append(studentList.get(i).getIdNumber());
				remark.append("-");
				remark.append(studentList.get(i).getFlowNo());
				remark.append(";");
				if (i%100 == 0 && i != 0) { //每100条记录一条日志,0除外
					logList.add(addLogCommon(remark.toString(), logCommon));
					remark = new StringBuilder();
				}
				if (i == studentList.size() -1) { //最后几条
					logList.add(addLogCommon(remark.toString(), logCommon));
					remark = new StringBuilder();
				}
			}
			
			cmsLogCommonService.insertLogList(logList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public LogCommon addLogCommon(String remark,  LogCommon logCommon){
		logCommon.setMenuId(LogConstant.MENU_ID_STUDENT);
		logCommon.setMenuName(LogConstant.MENE_STUDENT);
		logCommon.setTableId(LogConstant.TABLE_ID_U_STUDENT);
		logCommon.setRelateTable(LogConstant.TABLE_U_STUDENT);
		logCommon.setStatus( LogConstant.STATUS_SUCCESS);
		logCommon.setChannel(LogConstant.CHANNEL_COACH);
		logCommon.setUrl("/student/uploadWater");
		//logCommon.setIp( MapUtil.getStringVal(map, "log_ip"));
		logCommon.setAction(0);
		logCommon.setRelateId("1");
		logCommon.setRemark(remark);
		return logCommon;
	}


	@Override
	public ReqResult findPackDetail(String userId, String userType, String ttid) {
		return schoolService.getMailDetail(userId, userType, ttid,"2");
	}

	@Override
	public Long insertStudentInfo(Student student) {
		Long result = null;
		try {
			result = cmsStudentManager.insertSelective(student);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResponseMessage lockStudent(long userId, int state, Date reviveTime,
			String note) {
		int count = studentService.lockStudent(userId, state, reviveTime, note);
		if(count > 0){
			return new ResponseMessage();
		}
		return new ResponseMessage("封号失败");
	}

	@Override
	public ResponseMessage lockStudents(String ids, int state, Date reviveTime,
			String note) throws Exception{
		String[] idArray = ids.split(",");
		if(idArray != null && idArray.length > 0){
			for(int i = 0;i < idArray.length;i ++){
				long userId = StringUtil.isNullString(idArray[i])?0L:Long.valueOf(idArray[i]);
				int count = studentService.lockStudent(userId, state, reviveTime, note);
				access.info(" lock student id : " + userId + ", result : " + count +" (0 means failed) ");
			}
		}

		return new ResponseMessage();
	}
	
	@Override
	public ResponseMessage studentProgress(StatisticsStudentProgress vo) throws Exception {
		List<StatisticsStudentProgress> list= cmsStudentManager.queryStudentProgress(vo);
		List<StatisticsStudentProgress> totalList = new ArrayList<>();
		
		StatisticsStudentProgress progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(1);progress.setApplystate(0);
		StatisticsStudentProgress progress1_0 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(1);progress.setApplystate(100);
		StatisticsStudentProgress progress1_100 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(3);progress.setApplystate(0);
		StatisticsStudentProgress progress3_0 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(4);progress.setApplystate(0);
		StatisticsStudentProgress progress4_0 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(4);progress.setApplystate(1);
		StatisticsStudentProgress progress4_1 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(4);progress.setApplystate(101);
		StatisticsStudentProgress progress4_101 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(4);progress.setApplystate(100);
		StatisticsStudentProgress progress4_100 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(5);progress.setApplystate(0);
		StatisticsStudentProgress progress5_0 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(5);progress.setApplystate(1);
		StatisticsStudentProgress progress5_1 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(6);progress.setApplystate(0);
		StatisticsStudentProgress progress6_0 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(6);progress.setApplystate(1);
		StatisticsStudentProgress progress6_1 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(6);progress.setApplystate(101);
		StatisticsStudentProgress progress6_101 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(6);progress.setApplystate(100);
		StatisticsStudentProgress progress6_100 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(101);progress.setApplystate(1);
		StatisticsStudentProgress progress101_1 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(101);progress.setApplystate(101);
		StatisticsStudentProgress progress101_101 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(101);progress.setApplystate(100);
		StatisticsStudentProgress progress101_100 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(301);progress.setApplystate(1);
		StatisticsStudentProgress progress301_1 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(301);progress.setApplystate(101);
		StatisticsStudentProgress progress301_101 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(302);progress.setApplystate(0);
		StatisticsStudentProgress progress301_100 = progress;//301_100显示为302_0，中间过度状态全部显示为最终状态
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(302);progress.setApplystate(101);
		StatisticsStudentProgress progress302_101 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(302);progress.setApplystate(100);
		StatisticsStudentProgress progress302_100 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(401);progress.setApplystate(1);
		StatisticsStudentProgress progress401_1 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(401);progress.setApplystate(101);
		StatisticsStudentProgress progress401_101 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(402);progress.setApplystate(0);
		StatisticsStudentProgress progress401_100 = progress;//401_100显示为402_0，中间过度状态全部显示为最终状态
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(402);progress.setApplystate(101);
		StatisticsStudentProgress progress402_101 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(402);progress.setApplystate(100);
		StatisticsStudentProgress progress402_100 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(501);progress.setApplystate(1);
		StatisticsStudentProgress progress501_1 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(501);progress.setApplystate(101);
		StatisticsStudentProgress progress501_101 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(501);progress.setApplystate(100);
		StatisticsStudentProgress progress501_100 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(601);progress.setApplystate(1);
		StatisticsStudentProgress progress601_1 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(601);progress.setApplystate(101);
		StatisticsStudentProgress progress601_101 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(602);progress.setApplystate(0);
		StatisticsStudentProgress progress601_100 = progress;//601_100显示为602_0，中间过度状态全部显示为最终状态
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(602);progress.setApplystate(101);
		StatisticsStudentProgress progress602_101 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(602);progress.setApplystate(100);
		StatisticsStudentProgress progress602_100 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(702);progress.setApplystate(0);
		StatisticsStudentProgress progress701_100 = progress;//701_100显示为702_0，中间过度状态全部显示为最终状态
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(702);progress.setApplystate(101);
		StatisticsStudentProgress progress702_101 = progress;
		progress = new StatisticsStudentProgress();
		progress.setCount(0);progress.setApplyexam(702);progress.setApplystate(100);
		StatisticsStudentProgress progress702_100 = progress;
		
		for (StatisticsStudentProgress ssp : list) {
			if ((ssp.getApplyexam() == 702 && ssp.getApplystate() == 100) || ssp.getApplyexam() > 702)
				progress702_100.setCount(progress702_100.getCount() + ssp.getCount());
			if (ssp.getApplyexam() == 702 && ssp.getApplystate() == 101) 
				progress702_101.setCount(progress702_101.getCount() + ssp.getCount());
			if ((ssp.getApplyexam() == 701 && ssp.getApplystate() == 100) || ssp.getApplyexam() > 701)
				progress701_100.setCount(progress701_100.getCount() + ssp.getCount());
			if ((ssp.getApplyexam() == 602 && ssp.getApplystate() == 100) || ssp.getApplyexam() > 602)
				progress602_100.setCount(progress602_100.getCount() + ssp.getCount());
			if (ssp.getApplyexam() == 602 && ssp.getApplystate() == 101)
				progress602_101.setCount(progress602_101.getCount() + ssp.getCount());
			if ((ssp.getApplyexam() == 601 && ssp.getApplystate() == 100) || ssp.getApplyexam() > 601)
				progress601_100.setCount(progress601_100.getCount() + ssp.getCount());
			if (ssp.getApplyexam() == 601 && ssp.getApplystate() == 101)
				progress601_101.setCount(progress601_101.getCount() + ssp.getCount());
			if (ssp.getApplyexam() == 601 && ssp.getApplystate() == 1)
				progress601_1.setCount(progress601_1.getCount() + ssp.getCount());
			if ((ssp.getApplyexam() == 501 && ssp.getApplystate() == 100) || ssp.getApplyexam() > 501)
				progress501_100.setCount(progress501_100.getCount() + ssp.getCount());
			if (ssp.getApplyexam() == 501 && ssp.getApplystate() == 101)
				progress501_101.setCount(progress501_101.getCount() + ssp.getCount());
			if ((ssp.getApplyexam() == 501 && ssp.getApplystate() == 1) || ssp.getApplyexam() >= 501)
				progress501_1.setCount(progress501_1.getCount() + ssp.getCount());
			if ((ssp.getApplyexam() == 402 && ssp.getApplystate() == 100) || ssp.getApplyexam() > 402)
				progress402_100.setCount(progress402_100.getCount() + ssp.getCount());
			if (ssp.getApplyexam() == 402 && ssp.getApplystate() == 101)
				progress402_101.setCount(progress402_101.getCount() + ssp.getCount());
			if ((ssp.getApplyexam() == 401 && ssp.getApplystate() == 100) || ssp.getApplyexam() > 401)
				progress401_100.setCount(progress401_100.getCount() + ssp.getCount());
			if (ssp.getApplyexam() == 401 && ssp.getApplystate() == 101)
				progress401_101.setCount(progress401_101.getCount() + ssp.getCount());
			if (ssp.getApplyexam() == 401 && ssp.getApplystate() == 1)
				progress401_1.setCount(progress401_1.getCount() + ssp.getCount());
			if ((ssp.getApplyexam() == 302 && ssp.getApplystate() == 100) || ssp.getApplyexam() > 302)
				progress302_100.setCount(progress302_100.getCount() + ssp.getCount());
			if (ssp.getApplyexam() == 302 && ssp.getApplystate() == 101)
				progress302_101.setCount(progress302_101.getCount() + ssp.getCount());
			if ((ssp.getApplyexam() == 301 && ssp.getApplystate() == 100) || ssp.getApplyexam() > 301)
				progress301_100.setCount(progress301_100.getCount() + ssp.getCount());
			if (ssp.getApplyexam() == 301 && ssp.getApplystate() == 101)
				progress301_101.setCount(progress301_101.getCount() + ssp.getCount());
			if (ssp.getApplyexam() == 301 && ssp.getApplystate() == 1)
				progress301_1.setCount(progress301_1.getCount() + ssp.getCount());
			if ((ssp.getApplyexam() == 101 && ssp.getApplystate() == 100) || ssp.getApplyexam() > 101)
				progress101_100.setCount(progress101_100.getCount() + ssp.getCount());
			if (ssp.getApplyexam() == 101 && ssp.getApplystate() == 101)
				progress101_101.setCount(progress101_101.getCount() + ssp.getCount());
			if ((ssp.getApplyexam() == 101 && ssp.getApplystate() == 1) || ssp.getApplyexam() >= 101)
				progress101_1.setCount(progress101_1.getCount() + ssp.getCount());
			if ((ssp.getApplyexam() == 6 && ssp.getApplystate() == 100) || ssp.getApplyexam() > 6)
				progress6_100.setCount(progress6_100.getCount() + ssp.getCount());
			if (ssp.getApplyexam() == 6 && ssp.getApplystate() == 101)
				progress6_101.setCount(progress6_101.getCount() + ssp.getCount());
			if (ssp.getApplyexam() == 6 && ssp.getApplystate() == 1)
				progress6_1.setCount(progress6_1.getCount() + ssp.getCount());
			if ((ssp.getApplyexam() == 6 && ssp.getApplystate() == 0) || ssp.getApplyexam() >= 6)
				progress6_0.setCount(progress6_0.getCount() + ssp.getCount());
			if ((ssp.getApplyexam() == 5 && ssp.getApplystate() == 1) || ssp.getApplyexam() > 5)
				progress5_1.setCount(progress5_1.getCount() + ssp.getCount());
			if (ssp.getApplyexam() == 5 && ssp.getApplystate() == 0)
				progress5_0.setCount(progress5_0.getCount() + ssp.getCount());
			if ((ssp.getApplyexam() == 4 && ssp.getApplystate() == 100) || ssp.getApplyexam() > 4)
				progress4_100.setCount(progress4_100.getCount() + ssp.getCount());
			if (ssp.getApplyexam() == 4 && ssp.getApplystate() == 101)
				progress4_101.setCount(progress4_101.getCount() + ssp.getCount());
			if ((ssp.getApplyexam() == 4 && ssp.getApplystate() != 0) || ssp.getApplyexam() > 4)
				progress4_1.setCount(progress4_1.getCount() + ssp.getCount());
			if (ssp.getApplyexam() == 4 && ssp.getApplystate() == 0)
				progress4_0.setCount(progress4_0.getCount() + ssp.getCount());
			if (ssp.getApplyexam() == 3 && ssp.getApplystate() == 0)
				progress3_0.setCount(progress3_0.getCount() + ssp.getCount());
			if ((ssp.getApplyexam() == 1 && ssp.getApplystate() == 100) || ssp.getApplyexam() > 1)
				progress1_100.setCount(progress1_100.getCount() + ssp.getCount());
			if ((ssp.getApplyexam() == 1 && ssp.getApplystate() == 0) || ssp.getApplyexam() < 1)
				progress1_0.setCount(progress1_0.getCount() + ssp.getCount());
		}
		
		totalList.add(progress1_0);
		totalList.add(progress1_100);
		totalList.add(progress3_0);
		totalList.add(progress4_0);
		totalList.add(progress4_1);
		totalList.add(progress4_101);
		totalList.add(progress4_100);
		totalList.add(progress5_0);
		totalList.add(progress5_1);
		totalList.add(progress6_0);
		totalList.add(progress6_1);
		totalList.add(progress6_101);
		totalList.add(progress6_100);
		totalList.add(progress101_1);
		totalList.add(progress101_101);
		totalList.add(progress101_100);
		totalList.add(progress301_1);
		totalList.add(progress301_101);
		totalList.add(progress301_100);
		totalList.add(progress302_101);
		totalList.add(progress302_100);
		totalList.add(progress401_1);
		totalList.add(progress401_101);
		totalList.add(progress401_100);
		totalList.add(progress402_101);
		totalList.add(progress402_100);
		totalList.add(progress501_1);
		totalList.add(progress501_101);
		totalList.add(progress501_100);
		totalList.add(progress601_1);
		totalList.add(progress601_101);
		totalList.add(progress601_100);
		totalList.add(progress602_101);
		totalList.add(progress602_100);
		totalList.add(progress701_100);
		totalList.add(progress702_101);
		totalList.add(progress702_100);
		ResponseMessage resp = new ResponseMessage()
		.addResult("code", 0).addResult("progressList", totalList);
		return resp;
	}
	
	@Override
	public List<StatisticsStudentProgress> studentProgressData(StatisticsStudentProgress vo) throws Exception {
		List<StatisticsStudentProgress> list= cmsStudentManager.queryStudentProgress(vo);
		
		return list;
	}
	
	@Override
	public ResponseMessage findProgressStudent(StatisticsStudentProgress vo) throws Exception {
		PagedResult<StudentVo> result= cmsStudentManager.findProgressStudent(vo);
		
		ResponseMessage resp = new ResponseMessage()
		.addResult("code", 0).addResult("pageData", result);
		return resp;
	}
	
	@Override
	public List<StudentVo> findProgressStudentData(StatisticsStudentProgress vo) throws Exception {
		List<StudentVo> list= cmsStudentManager.findProgressStudentData(vo);
		
		return list;
	}
	
	@Override
	public ResponseMessage studentProgressReport(StatisticsStudentProgress vo) throws Exception {
		List<StatisticsStudentProgress> list= cmsStudentManager.queryStudentProgressBySchool(vo);
		
		/**
		 * 4, 5, 6 and after
		 */
		Map<Long,StatisticsStudentProgressReport> data=new HashMap();
		Map<String,StatisticsStudentProgressReport> result=new HashMap();
		List<School> schools= cschoolService.findAllSchool(new SchoolBDTO());
		Region region=new Region();region.setPid(0);
		List<Region> citys=regionService.findAllCity(region);
		
		
		for(StatisticsStudentProgress progress :list){
			//忽略schoolid为空的数据
			if(progress.getSchoolId()==null||progress.getSchoolId()==0) continue;
			
			if(!data.containsKey(progress.getSchoolId())){
				StatisticsStudentProgressReport report=new StatisticsStudentProgressReport();
				report.setSchoolId(progress.getSchoolId());
				data.put(progress.getSchoolId(), report);
				
				for(School school:schools){
					
					if(school.getSchoolId().longValue() ==report.getSchoolId().longValue()){
						report.setSchool(school.getName());
						report.setCityId(school.getCityId());
						boolean findcity=false;
						for(Region city:citys){
							System.out.println("report school:"+report.getSchoolId().longValue()+" report city:"+report.getCityId()+"city:"+city.getRid().intValue());
							if(city.getRid().intValue()==report.getCityId()){
								report.setCity(city.getRegion());
								findcity=true;
								break;
							}
						}
						if(!findcity){
							System.out.println("school "+progress.getSchoolId()+" city is null");
							//data.remove(progress.getSchoolId());
						}
					}
				}
			}
			
			
			if(progress.getApplyexam()==1){//报名
				
			}else if(progress.getApplyexam()==2){//支付
				
			}else if(progress.getApplyexam()==3){//个人信息
				
			}else if(progress.getApplyexam()==4){//邮寄资料
				
			}else if(progress.getApplyexam()==5){//交表到驾校
				data.get(progress.getSchoolId()).addStep4(progress.getCount());
				
			}else if(progress.getApplyexam()==6){//递交受理
				data.get(progress.getSchoolId()).addStep4(progress.getCount());
				data.get(progress.getSchoolId()).addStep5(progress.getCount());
			}else if(progress.getApplyexam()==101){//理论课
				data.get(progress.getSchoolId()).addStep4(progress.getCount());
				data.get(progress.getSchoolId()).addStep5(progress.getCount());
				data.get(progress.getSchoolId()).addStep6(progress.getCount());
			}else if(progress.getApplyexam()==201){//模拟考试
				/*
				 * 可能ignore
				 */
				data.get(progress.getSchoolId()).addStep4(progress.getCount());
				data.get(progress.getSchoolId()).addStep5(progress.getCount());
				data.get(progress.getSchoolId()).addStep6(progress.getCount());
				data.get(progress.getSchoolId()).addStep101(progress.getCount());
			}else if(progress.getApplyexam()==301){//科目一排队
				data.get(progress.getSchoolId()).addStep4(progress.getCount());
				data.get(progress.getSchoolId()).addStep5(progress.getCount());
				data.get(progress.getSchoolId()).addStep6(progress.getCount());
				data.get(progress.getSchoolId()).addStep101(progress.getCount());
			}else if(progress.getApplyexam()==302){//科目一
				data.get(progress.getSchoolId()).addStep4(progress.getCount());
				data.get(progress.getSchoolId()).addStep5(progress.getCount());
				data.get(progress.getSchoolId()).addStep6(progress.getCount());
				data.get(progress.getSchoolId()).addStep101(progress.getCount());
				
				if(progress.getApplystate()==0){
					data.get(progress.getSchoolId()).addStep301(progress.getCount());
				}
				
			}else if(progress.getApplyexam()==401){//科目二排队
				data.get(progress.getSchoolId()).addStep4(progress.getCount());
				data.get(progress.getSchoolId()).addStep5(progress.getCount());
				data.get(progress.getSchoolId()).addStep6(progress.getCount());
				data.get(progress.getSchoolId()).addStep101(progress.getCount());
				
				data.get(progress.getSchoolId()).addStep302(progress.getCount());
			}else if(progress.getApplyexam()==402){//科目二
				data.get(progress.getSchoolId()).addStep4(progress.getCount());
				data.get(progress.getSchoolId()).addStep5(progress.getCount());
				data.get(progress.getSchoolId()).addStep6(progress.getCount());
				data.get(progress.getSchoolId()).addStep101(progress.getCount());
				
				data.get(progress.getSchoolId()).addStep302(progress.getCount());
				
				if(progress.getApplystate()==0){
					data.get(progress.getSchoolId()).addStep401(progress.getCount());
				}
			}else if(progress.getApplyexam()==501){//长考
				data.get(progress.getSchoolId()).addStep4(progress.getCount());
				data.get(progress.getSchoolId()).addStep5(progress.getCount());
				data.get(progress.getSchoolId()).addStep6(progress.getCount());
				data.get(progress.getSchoolId()).addStep101(progress.getCount());
				
				data.get(progress.getSchoolId()).addStep302(progress.getCount());
				data.get(progress.getSchoolId()).addStep402(progress.getCount());
				
				if(progress.getApplystate()==1){//已经约考长考,100是长考合格
					data.get(progress.getSchoolId()).addStep501(progress.getCount());
				}
				
			}else if(progress.getApplyexam()==601){//科目三排队
				data.get(progress.getSchoolId()).addStep4(progress.getCount());
				data.get(progress.getSchoolId()).addStep5(progress.getCount());
				data.get(progress.getSchoolId()).addStep6(progress.getCount());
				data.get(progress.getSchoolId()).addStep101(progress.getCount());
				
				data.get(progress.getSchoolId()).addStep302(progress.getCount());
				data.get(progress.getSchoolId()).addStep402(progress.getCount());
				//data.get(progress.getSchoolId()).addStep501(progress.getCount());
			}else if(progress.getApplyexam()==602){//科目三
				data.get(progress.getSchoolId()).addStep4(progress.getCount());
				data.get(progress.getSchoolId()).addStep5(progress.getCount());
				data.get(progress.getSchoolId()).addStep6(progress.getCount());
				data.get(progress.getSchoolId()).addStep101(progress.getCount());
				
				data.get(progress.getSchoolId()).addStep302(progress.getCount());
				data.get(progress.getSchoolId()).addStep402(progress.getCount());
				//data.get(progress.getSchoolId()).addStep501(progress.getCount());
				
				if(progress.getApplystate()==0){
					data.get(progress.getSchoolId()).addStep601(progress.getCount());
				}
			}else if(progress.getApplyexam()==701){//科目四排队
				data.get(progress.getSchoolId()).addStep4(progress.getCount());
				data.get(progress.getSchoolId()).addStep5(progress.getCount());
				data.get(progress.getSchoolId()).addStep6(progress.getCount());
				data.get(progress.getSchoolId()).addStep101(progress.getCount());
				
				data.get(progress.getSchoolId()).addStep302(progress.getCount());
				data.get(progress.getSchoolId()).addStep402(progress.getCount());
				//data.get(progress.getSchoolId()).addStep501(progress.getCount());
				data.get(progress.getSchoolId()).addStep602(progress.getCount());
			}else if(progress.getApplyexam()==702){//科目四
				data.get(progress.getSchoolId()).addStep4(progress.getCount());
				data.get(progress.getSchoolId()).addStep5(progress.getCount());
				data.get(progress.getSchoolId()).addStep6(progress.getCount());
				data.get(progress.getSchoolId()).addStep101(progress.getCount());
				
				data.get(progress.getSchoolId()).addStep302(progress.getCount());
				data.get(progress.getSchoolId()).addStep402(progress.getCount());
				//data.get(progress.getSchoolId()).addStep501(progress.getCount());
				data.get(progress.getSchoolId()).addStep602(progress.getCount());
				
				if(progress.getApplystate()==0){
					data.get(progress.getSchoolId()).addStep701(progress.getCount());
				}
			}else if(progress.getApplyexam()==801){//拿证
				data.get(progress.getSchoolId()).addStep4(progress.getCount());
				data.get(progress.getSchoolId()).addStep5(progress.getCount());
				data.get(progress.getSchoolId()).addStep6(progress.getCount());
				data.get(progress.getSchoolId()).addStep101(progress.getCount());
				
				data.get(progress.getSchoolId()).addStep302(progress.getCount());
				data.get(progress.getSchoolId()).addStep402(progress.getCount());
				//data.get(progress.getSchoolId()).addStep501(progress.getCount());
				data.get(progress.getSchoolId()).addStep602(progress.getCount());
				data.get(progress.getSchoolId()).addStep702(progress.getCount());
				data.get(progress.getSchoolId()).addStep801(progress.getCount());
			}
		}
		
		java.util.Iterator<StatisticsStudentProgressReport> it= data.values().iterator();
		StatisticsStudentProgressReport sum=new StatisticsStudentProgressReport();
		while(it.hasNext()){
			StatisticsStudentProgressReport report=it.next();
			if(!result.containsKey(report.getCity())){
				StatisticsStudentProgressReport cityreport=new StatisticsStudentProgressReport();
				cityreport.setCity(report.getCity());
				List ss=new ArrayList();
				ss.add(report);
				cityreport.setReports(ss);
				result.put(report.getCity(), cityreport);
			}else{
				result.get(report.getCity()).getReports().add(report);
			}
			sum.addStep4(report.getStep4());
			sum.addStep5(report.getStep5());
			sum.addStep6(report.getStep6());
			sum.addStep101(report.getStep101());
			sum.addStep301(report.getStep301());
			sum.addStep302(report.getStep302());
			sum.addStep401(report.getStep401());
			sum.addStep402(report.getStep402());
			sum.addStep501(report.getStep501());
			sum.addStep601(report.getStep601());
			sum.addStep602(report.getStep602());
			sum.addStep701(report.getStep701());
			sum.addStep702(report.getStep702());
			sum.addStep801(report.getStep801());
		}
		ResponseMessage resp = new ResponseMessage()
		.addResult("code", 0).addResult("dataList", result.values());
		resp.addResult("sum", sum);
		return resp;
	}
	
	@Override
	public ResponseMessage havePackageStudent(StudentBDTO dto) throws Exception {
		PagedResult<StudentVo> batch = cmsStudentManager.havePackageStudent(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	
}
