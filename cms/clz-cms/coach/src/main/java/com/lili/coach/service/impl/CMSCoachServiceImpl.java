package com.lili.coach.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.BeanCopy;
import com.lili.cms.util.DateUtil;
import com.lili.cms.util.PicUtil;
import com.lili.cms.util.StringUtil;
import com.lili.cms.util.ValidatorUtil;
import com.lili.coach.dto.CarCheck;
import com.lili.coach.dto.CoachCheck;
import com.lili.coach.manager.CMSCoachManager;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.model.CCOVo;
import com.lili.coach.model.Coach;
import com.lili.coach.model.CoachAccount;
import com.lili.coach.model.CoachBDTO;
import com.lili.coach.model.CoachRegist;
import com.lili.coach.model.CoachVo;
import com.lili.coach.service.CMSCoachService;
import com.lili.coach.service.CoachService;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.finance.service.ICmsBankCardVerifyService;
import com.lili.finance.service.ICmsUserMoneyService;
import com.lili.finance.vo.BankCardVerifyVo;
import com.lili.finance.vo.UserMoneyVo;
import com.lili.log.model.LogCommon;
import com.lili.order.model.OrderBDTO;
import com.lili.order.service.CMSOrderService;
import com.lili.school.model.Region;
import com.lili.school.model.School;
import com.lili.school.service.CMSCarService;
import com.lili.school.service.CMSRegionService;
import com.lili.school.service.CMSSchoolService;
import com.lili.student.model.StudentNBDTO;
import com.lili.student.model.StudentVo;

public class CMSCoachServiceImpl implements CMSCoachService{

	protected  final Logger access = Logger.getLogger(this.getClass());
	
	@Autowired
	CMSCoachManager cmsCoachManager;
	
    @Autowired
    CoachManager coachManager;
    
	@Autowired
	CoachService coachService;
	
	@Autowired
	ICmsBankCardVerifyService bankCardVerifyService;
	
	@Autowired
	ICmsUserMoneyService userMoneyService;
	
	@Autowired
	CMSSchoolService cmsSchoolService;
	
	@Autowired
	CMSRegionService cmsRegionService;

	@Autowired
	CMSOrderService cmsOrderService;
	
	@Autowired
	CMSCarService cmsCarService;
	
	@Override
	public ResponseMessage findOne(long coachId) throws Exception {
		Coach coach = cmsCoachManager.findOne(coachId);
		if(coach != null){
			coach.setHeadIcon(PicUtil.getPicFromQN(coach.getHeadIcon()));
			School school=cmsSchoolService.findSchoolById(coach.getSchoolId());
			coach.setSchoolName(school.getName());
			Region region=cmsRegionService.findRegion(coach.getCityId());
			if(region!=null)
				coach.setCity(region.getRegion());
			
			getStatInfo(coach);
			
		}
		return new ResponseMessage().addResult("coach", coach);
	}
	
	/**
	 * 教练统计信息
	 * @param coach
	 * @throws Exception
	 */
	private void getStatInfo(Coach coach) throws Exception{
		if(coach.getLastCalculateDate()==null){
			//计算今天之前的带教时长
			java.text.SimpleDateFormat format=new java.text.SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar= Calendar.getInstance();
			
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date endTime=calendar.getTime();
			OrderBDTO bdto=new OrderBDTO();
			bdto.setCoachId(coach.getCoachId());
			bdto.setEndTime(format.format(endTime));
			int teachTime= cmsOrderService.findTeachTimeByCoachId(bdto);
			coach.setTeachTime(teachTime);
			
			//计算今天之前的排班次数，课时
			CoachBDTO dto=new CoachBDTO();
			dto.setCoachId(coach.getCoachId());
			dto.setEndTime(format.format(endTime));
			int classTime= cmsCoachManager.getCoachClassTime(dto);
			int classCount=cmsCoachManager.getCoachClassCount(dto);
			coach.setClassCount(classCount);
			coach.setClassTime(classTime);
			coach.setWorkTime(coach.getTeachTime()+coach.getListenTime());
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			coach.setLastCalculateDate(calendar.getTime());//昨天0时0分0秒
			cmsCoachManager.addCoachStatTime(coach);
			
			int student=cmsCoachManager.getCoachStudentCount(dto);
			coach.setStudentCount(student);
		}else{
			Calendar calendar= Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			java.text.SimpleDateFormat format=new java.text.SimpleDateFormat("yyyy-MM-dd");
			if(format.format(coach.getLastCalculateDate()).equals(format.format(calendar.getTime()))){//已经计算过今天以前的数据
				//计算今天的
				coach.setWorkTime(coach.getTeachTime()+coach.getListenTime());
			}else{
				//计算空缺时间的带教时长，并累加到教练的信息里
				//calendar.getTime()==昨天
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				Date endTime=calendar.getTime();//今天0时0分0秒
				
				calendar.setTime(coach.getLastCalculateDate());
				calendar.add(Calendar.DAY_OF_YEAR, 1);//上次计算时间点的后一天开始计算
				Date beginTime=calendar.getTime();
				OrderBDTO bdto=new OrderBDTO();
				bdto.setCoachId(coach.getCoachId());
				bdto.setEndTime(format.format(endTime));
				bdto.setStartTime(format.format(beginTime));
				int teachTime= cmsOrderService.findTeachTimeByCoachId(bdto);
				int currentTeachTime=coach.getTeachTime();
				coach.setTeachTime(teachTime);
				
				
				//计算空缺时间的排班次数，课时
				CoachBDTO dto=new CoachBDTO();
				dto.setCoachId(coach.getCoachId());
				dto.setEndTime(format.format(endTime));
				dto.setStartTime(format.format(beginTime));
				int classTime= cmsCoachManager.getCoachClassTime(dto);
				int classCount=cmsCoachManager.getCoachClassCount(dto);
				
				int currentClassTime=coach.getClassTime();
				int currentClassCount=coach.getClassCount();
				coach.setClassCount(classCount);
				coach.setClassTime(classTime);
				
				calendar.setTime(endTime);
				calendar.add(Calendar.DAY_OF_YEAR, -1);
				coach.setLastCalculateDate(endTime);
				
				cmsCoachManager.addCoachStatTime(coach);
				
				coach.setTeachTime(currentTeachTime+teachTime);
				coach.setClassCount(currentClassCount+classCount);
				coach.setClassTime(currentClassTime+classTime);
				coach.setWorkTime(coach.getTeachTime()+coach.getListenTime());
				int student=cmsCoachManager.getCoachStudentCount(dto);
				coach.setStudentCount(student);
			}
		}
	}
	

	@Override
	public ResponseMessage findAccountVo() throws Exception {
		return null;
	}

	@Override
	public ResponseMessage findBatch(CoachBDTO dto) throws Exception {
		PagedResult<CoachVo> batch = cmsCoachManager.findBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage findCoachCar(long coachId) throws Exception {
		List<CCOVo> coachCar = cmsCoachManager.findCoachCar(coachId);
		return new ResponseMessage().addResult("coachCar", coachCar);
	}

	@Override
	public Coach findByPhone(String phoneNum) throws Exception {
		return cmsCoachManager.findByPhone(phoneNum);
	}

	private boolean validateNums(String phoneNum,String idNumber){
		return (ValidatorUtil.isMobile(phoneNum) && ValidatorUtil.isIDCard(idNumber));
	}
	
	@Override
	public ResponseMessage updateOne(LogCommon logCommon,Coach coach) throws Exception {
//		if(!validateNums(coach.getPhoneNum(), coach.getIdNumber())){
//			return new ResponseMessage("身份证号或手机号不正确");
//		}
		
		List<Coach> coach0 = cmsCoachManager.findByNums(coach);
		if(coach0 != null && coach0.size() > 0){
			return new ResponseMessage("身份证号或手机号已存在");
		}

		Coach coach1 = cmsCoachManager.findOne(coach.getCoachId());
		coach1.setPhoneNum(coach.getPhoneNum());
		coach1.setSex(coach.getSex());
		coach1.setName(coach.getName());
		coach1.setIdNumber(coach.getIdNumber());
		coach1.setSchoolId(coach.getSchoolId());
		com.lili.coach.dto.Coach _coach = BeanCopy.copy2Null(coach1,new com.lili.coach.dto.Coach());
		if(coachService.updateCoach(_coach) <= 0){
			return new ResponseMessage("更新失败");
		}
		if(logCommon != null)
			logCommon.setRelateId(String.valueOf(coach.getCoachId()));
		return new ResponseMessage();
	}

	@Override
	public ResponseMessage deleteCCRelation(LogCommon logCommon,long coachId, long carId)
			throws Exception {
		if(cmsCoachManager.deleteCCRelation(coachId, carId) > 0){
			if(logCommon != null){
	        logCommon.setRelateId(String.valueOf(coachId));
			String operateTime = DateUtil.formatDatetime(logCommon.getOperateTime());
			logCommon.setRemark(logCommon.getUserName() + " " + operateTime + " 更新了教练关系 ");
			}
			return new ResponseMessage();
		}
		return new ResponseMessage("关系删除失败");
	}

	/**
	 * 教练和车的关系更新
	 * 1.在关系中间表中,同一辆车和同一个教练只会有一条对应记录
	 * 2.更新前先判断关系是否存在,不存在则插入
	 */
	@Override
	public ResponseMessage updateCCRelation(LogCommon logCommon,long c_carId, long o_carId,long coachId) throws Exception {
		Integer count = cmsCoachManager.findCCRelation(coachId, c_carId);
		//更新前,先判断车和教练的关系是否已经存在,不论关系当前是否是有效状态
		if(count == null || count == 0){
			Long _count = updateWhenCCNotExist(c_carId, o_carId, coachId);
			if(_count == null || _count <= 0){
				return new ResponseMessage("更新失败");
			}
		}else{
			Long _count = updateWhenCCExist(c_carId, o_carId, coachId);
			if(_count == null || _count <= 0){
				return new ResponseMessage("更新失败");
			}
		}

		if(logCommon != null){
        logCommon.setRelateId(String.valueOf(coachId));
		String operateTime = DateUtil.formatDatetime(logCommon.getOperateTime());
		logCommon.setRemark(logCommon.getUserName() + " " + operateTime + " 更新了教练关系 ");
		}
		return new ResponseMessage();
	}
	

	private Long updateWhenCCExist(long c_carId, long o_carId,long coachId) throws Exception {
		if(o_carId == 0){
			Long updateCount = cmsCoachManager.updateOCCRelation(c_carId,coachId);
			return updateCount;
		}else{
			Long updateCount = cmsCoachManager.updateOCCRelation(c_carId,coachId);
			cmsCoachManager.deleteCCRelation(coachId,o_carId);
			return updateCount;
		}
		
	}

	private Long updateWhenCCNotExist(long c_carId, long o_carId,long coachId) throws Exception {
		if(o_carId == 0){
			Long updateCount = cmsCoachManager.insertCCRelation(c_carId, coachId);
			
			return updateCount;
		}else {
			Long updateCount = cmsCoachManager.updateCCRelation(c_carId, o_carId, coachId);
			return updateCount;
		}
	}

	@Override
	public ResponseMessage insertOne(LogCommon logCommon,Coach coach) throws Exception {
//		if(!validateNums(coach.getPhoneNum(), coach.getIdNumber())){
//			return new ResponseMessage("身份证号或手机号不正确");
//		}

		List<Coach> coach0 = cmsCoachManager.findByNums(coach);
		if(coach0 != null && coach0.size() > 0){
			return new ResponseMessage("身份证号或手机号已存在");
		}
        
		com.lili.coach.dto.Coach _coach = BeanCopy.copy2Null(coach,new com.lili.coach.dto.Coach());
		if(coachManager.addCoach(_coach) <= 0){
			return new ResponseMessage("插入失败");
		}else {
			if(logCommon != null){
	        logCommon.setRelateId(String.valueOf(coach.getCoachId()));
			}
			return new ResponseMessage();
		}
	}

	@Override
	public ResponseMessage insertCSRelation(LogCommon logCommon,String studentIds, long coachId)
			throws Exception {
		List<StudentVo> list = new ArrayList<StudentVo>();
		String[] studentIdArray = (StringUtil.formatString(studentIds)).split(",");
		if(studentIdArray != null && studentIdArray.length > 0){
			for(int i = 0 ; i < studentIdArray.length;i ++){
				StudentVo studentVo = new StudentVo();
				studentVo.setCoachId(coachId);
				studentVo.setStudentId(Long.valueOf(studentIdArray[i]));
				list.add(studentVo);
			}
		}
		
		if(cmsCoachManager.insertCSRelation(list) <= 0){
			return new ResponseMessage("插入失败");
		}else {
			if(logCommon != null){
	        logCommon.setRelateId(String.valueOf(coachId));
			String operateTime = DateUtil.formatDatetime(logCommon.getOperateTime());
			logCommon.setRemark(logCommon.getUserName() + " " + operateTime + " 绑定了教练关系 ");
			}
			return new ResponseMessage();
		}
	}

	@Override
	public String findCoachInfo(CoachBDTO dto, String operateType) throws Exception {
		String resp = null;
		try {
			CoachAccount CoachInfo = cmsCoachManager.findCoachInfo(dto);
			
			BankCardVerifyVo bankCardVerifyVo = new BankCardVerifyVo();
			bankCardVerifyVo.setUserId((int)dto.getCoachId());
			bankCardVerifyVo.setUserType(1);
			List<BankCardVerifyVo> bankList = bankCardVerifyService.queryBankBoundList(bankCardVerifyVo);//银行卡信息
			
			UserMoneyVo userMoneyVo = new UserMoneyVo();
			userMoneyVo.setStartTime(dto.getStartTime());
			userMoneyVo.setEndTime(dto.getEndTime());
			userMoneyVo.setPageNo(dto.getPageNo());
			userMoneyVo.setPageSize(dto.getPageSize());
			userMoneyVo.setUserId(dto.getCoachId());
			userMoneyVo.setUserType(1);
			if(!"".equals(operateType) && operateType != null){
				userMoneyVo.setOperateType(Integer.valueOf(operateType));
			}
			PagedResult<UserMoneyVo> moneyList = userMoneyService.queryUserDetailList(userMoneyVo, "2");//资产交易明细
			
			resp = new ResponseMessage()
				.addResult("CoachInfo", CoachInfo)
				.addResult("bankList", bankList)
				.addResult("moneyList", moneyList)
				.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public List<CoachVo> getExportSource(CoachBDTO dto) throws Exception {
		return cmsCoachManager.findExportBatch(dto);
	}

	@Override
	public Long updateStates(String coachIds) throws Exception {
		return cmsCoachManager.updateStates(coachIds);
	}

	@Override
	public Integer queryTotalCoach(CoachBDTO dto) throws Exception {
		return cmsCoachManager.findTotalCoach(dto);
	}

	@Override
	public Long insertCoachInfo(Coach coach) {
		Long result = null;
		try {
			result = cmsCoachManager.insertSelective(coach);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public ResponseMessage lockCoach(long userId, int state, Date reviveTime, String note) {
		int count = coachService.lockCoach(userId, state, reviveTime, note);
		if(count > 0){
			return new ResponseMessage();
		}
		return new ResponseMessage("封号失败");
	}

	@Override
	public ResponseMessage lockCoaches(String ids, int state, Date reviveTime,
			String note) {
		String[] idArray = ids.split(",");
		if(idArray != null && idArray.length > 0){
			for(int i = 0;i < idArray.length;i ++){
				long userId = StringUtil.isNullString(idArray[i])?0L:Long.valueOf(idArray[i]);
				int count = coachService.lockCoach(userId, state, reviveTime, note);
				access.info(" lock coach id : " + userId + ", result : " + count +" (0 means failed) ");
			}
		}
		return new ResponseMessage();
	}

	@Override
	public Long delSC(StudentNBDTO dto) throws Exception {
		return cmsCoachManager.delSC(dto);
	}

	@Override
	public Long insertSC(StudentNBDTO dto) throws Exception {
		return cmsCoachManager.insertSC(dto);
	}

	@Override
	public Long updateSC(StudentNBDTO dto) throws Exception {
		return cmsCoachManager.updateSC(dto);
	}

	@Override
	public List<Coach> findByNums(Coach coach) throws Exception {
		return cmsCoachManager.findByNums(coach);
	}

	@Override
	public String regCoachQuery(CoachBDTO dto) {
		String resp = null;
		PagedResult<CoachRegist> coachRegList = cmsCoachManager.queryRegCoachList(dto);
		resp = new ResponseMessage().addResult("pageData", coachRegList).build();
		return resp;
	}

	@Override
	public String queryRegCoachDetail(CoachBDTO dto) {
		String resp = null;
		CoachCheck coachCheck = new CoachCheck();
		
		try {
			//查询教练信息
			ReqResult reqResult = coachService.checkInfo(String.valueOf(dto.getCoachId()));
			if(reqResult.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){
				resp = new ResponseMessage().addResult("pageData", reqResult.getResult().get("data")).build();
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL).build();
		}
		
		return resp;
	}

	@Override
	public String checkRegCoach(CoachBDTO dto, String verifier) {
		try {
			//判断状态是否已审核
			Coach coachInfo = cmsCoachManager.queryRegCoachDetail(dto);
			if (coachInfo.getCheckDrState() == 2) {
				return new ResponseMessage(MessageCode.MSG_DEPOSIT_STATUS_ERROR).build();
			}
		
			ReqResult result =  coachService.carCheckPass(dto.getCoachId(), dto.getCheckDrState(), verifier);
			if(result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){
				com.lili.coach.dto.Coach coach = new com.lili.coach.dto.Coach();
				coach.setCoachId(dto.getCoachId());
				coach.setCheckDrState(dto.getCheckDrState());
				coach.setCheckDrRemark(dto.getCheckDrRemark());
				coach.setIsNewDrPhoto(0);
				coachService.updateCoach(coach);
			}
			else {
				return new ResponseMessage(ResultCode.getCodeInfo(result.getResult().get(ResultCode.RESULTKEY.CODE).toString())).build();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL).build();
		}
		
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS).build();
	}

	@Override
	public String updateRegCheck(String userId, String drPhoto, String drPhoto2, String isNewDrPhoto, String cityName, List<CarCheck> carJsonList) {
		try {
			if (cityName != null && !"".equals(cityName)) {
				Region region = cmsRegionService.findByName(cityName);
				if (region == null ) {
					return new ResponseMessage("该城市不存在").build();
				}
				ReqResult result =  coachService.addRegCoach(userId, drPhoto, drPhoto2,isNewDrPhoto,region.getRid().toString(), carJsonList);
				if(result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){
					return new ResponseMessage().build();
				}
			}
			else {
				ReqResult result =  coachService.addRegCoach(userId, drPhoto, drPhoto2,isNewDrPhoto,null, carJsonList);
				if(result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){
					return new ResponseMessage().build();
				}

			}
			return new ResponseMessage("更新失败").build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseMessage("更新失败").build();
	}

	@Override
	public ResponseMessage getCoach(CoachBDTO dto) throws Exception {
		PagedResult<Coach> batch = cmsCoachManager.getCoach(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	

}
