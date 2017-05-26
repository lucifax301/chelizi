package com.lili.school.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.authcode.service.AuthcodeService;
import com.lili.cms.constant.Constant;
import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.DateUtil;
import com.lili.cms.util.EncryptUtil;
import com.lili.coach.dto.CarInfo;
import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CarManager;
import com.lili.coach.manager.CoachManager;
import com.lili.common.util.Page;
import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.finance.service.ICmsSchoolDepositService;
import com.lili.finance.service.ICmsUserMoneyService;
import com.lili.finance.vo.MoneFreeVo;
import com.lili.finance.vo.SchDeposit;
import com.lili.finance.vo.UserMoneyVo;
import com.lili.log.model.LogCommon;
import com.lili.pay.service.IDepositService;
import com.lili.school.dto.EnrollExamReg;
import com.lili.school.dto.EnrollExamResult;
import com.lili.school.dto.EnrollImportRecord;
import com.lili.school.dto.EnrollLongtrain;
import com.lili.school.dto.EnrollLongtrainStudent;
import com.lili.school.dto.EnrollTheory;
import com.lili.school.dto.EnrollTheoryStudent;
import com.lili.school.manager.CMSSchoolManager;
import com.lili.school.manager.EnrollLongtrainManager;
import com.lili.school.manager.EnrollSubjectManager;
import com.lili.school.manager.EnrollTheoryManager;
import com.lili.school.manager.SchAccountManager;
import com.lili.school.model.SchAccount;
import com.lili.school.model.School;
import com.lili.school.model.SchoolAccountApply;
import com.lili.school.model.SchoolBDTO;
import com.lili.school.model.SchoolExtend;
import com.lili.school.model.WechatEnrollPackage;
import com.lili.school.model.WechatEnrollPackageBDTO;
import com.lili.school.service.CMSSchoolService;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;
import com.lili.user.model.User;
import com.lili.user.service.CMSUserService;

public class CMSSchoolServiceImpl implements CMSSchoolService{

	Logger logger = Logger.getLogger(CMSSchoolServiceImpl.class);
	
	@Autowired
	CMSSchoolManager cmsSchoolManager;
	
	@Autowired
	SchAccountManager schAccountManager;
	
	@Autowired
	ICmsSchoolDepositService schoolDepositService;
	
	@Autowired
	ICmsUserMoneyService userMoneyService;
	
	@Autowired
	IDepositService depositService;
	
    @Autowired
    AuthcodeService authcodeService;
    
    @Autowired
    EnrollTheoryManager enrollTheoryManager;
    
    @Autowired
    CoachManager coachManager;
    
    @Autowired
    CarManager carManager;
    @Autowired
    EnrollLongtrainManager enrollLongtrainManager;
    
	@Autowired
	StudentManager studentManager;
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	EnrollSubjectManager enrollSubjectManager;
	@Autowired
	CMSUserService cmsUserService;
	
	ExecutorService threadPool = Executors.newCachedThreadPool();


    /**
     * ************************************************************
     * 							车厘子端功能
     * ************************************************************
     */
	@Override
	public String findSchoolNameById(long schoolId) throws Exception {
		return cmsSchoolManager.findSchoolNameById(schoolId);
	}

	@Override
	public ResponseMessage findSchoolList(SchoolBDTO dto) throws Exception {
		PagedResult<School> batch = cmsSchoolManager.findSchoolList(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage addSchool(LogCommon logCommon, SchoolBDTO dto) {
		try {
			cmsSchoolManager.addSchool(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage updateSchool(LogCommon logCommon, SchoolBDTO dto) {
		try {
			cmsSchoolManager.updateSchool(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage findSchoolArray(SchoolBDTO dto) throws Exception {
		List<School> batch = cmsSchoolManager.findSchoolArray(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}
	
	@Override
	public SchAccount getSchoolIdMoney(long schoolId) {
		return schAccountManager.getSchoolIdMoney(schoolId);
	}

	@Override
	public void addMoneyBack(int money, long schoolId) {
		try {
			schAccountManager.addMoneyBack(money, schoolId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ResponseMessage queryBurse(SchoolExtend dto) {
		PagedResult<SchoolExtend> batch = null;
		try {
			batch = schAccountManager.queryBurse(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseMessage().addResult("pageData", batch);
	}

	/**
	 * 审核通过
	 */
	@Override
	public ResponseMessage checkPass(String checker, String remark, String id, String schoolNos) {
		try {
			String[] idList  = id.split(",");
			String[] scList = schoolNos.split(",");
			List<SchoolExtend> schoolList = schAccountManager.querySchoolBurseList(id);
			for (int i =0; i< schoolList.size(); i++) {
				if(schoolList.get(i).getCheckStatus() != 1){
					return new ResponseMessage(MessageCode.MSG_HANDE_CF);
				}
			}
			
			List<SchoolExtend> updateSchoolExtendList = new ArrayList<SchoolExtend>();
			SchoolExtend schoolExtend;
			Long ids;
			long schoolId;
			for (int i =0; i< idList.length; i++) {
				ids = Long.parseLong(idList[i]);
				schoolId = Long.parseLong(scList[i]);
				schoolExtend = new SchoolExtend();
				schoolExtend.setChecker(checker);
				schoolExtend.setCheckStatus(2);//审核通过
				schoolExtend.setChangeRemark(remark);
				schoolExtend.setIsDel(2);//启用
				schoolExtend.setId(ids);
				schoolExtend.setIsChange(5); //关闭变更信息
				schoolExtend.setSchoolId(schoolId);
			    updateSchoolExtendList.add(schoolExtend);
			}
			//批量更新状态为审核通过，并且启用
			//同时更新原审核不通过的为不启用
			schAccountManager.batchUpdateSchoolExtendList(updateSchoolExtendList, 3);

			logger.info("-------------------------------- BoundBankCardController Pass Update Success!");
		} 
		catch (NumberFormatException e) {
			logger.error("********************************* error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		} 
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}
	
	/**
	 * 审核不通过
	 */
	@Override
	public ResponseMessage checkReject(String checker, String remark, String id) {
		try {
			String[] idList  = id.split(",");
			List<SchoolExtend> schoolList = schAccountManager.querySchoolBurseList(id);
			for (int i =0; i< schoolList.size(); i++) {
				if(schoolList.get(i).getCheckStatus() != 1){
					return new ResponseMessage(MessageCode.MSG_HANDE_CF);
				}
			}
			
			List<SchoolExtend> updateSchoolExtendList = new ArrayList<SchoolExtend>();
			SchoolExtend schoolExtend;
			Long ids;
			for (int i =0; i< idList.length; i++) {
				ids = Long.parseLong(idList[i]);
				schoolExtend = new SchoolExtend();
				schoolExtend.setChecker(checker);
				schoolExtend.setCheckStatus(3);//审核不通过
				schoolExtend.setChangeRemark(remark);
				schoolExtend.setIsDel(2);//启用
				schoolExtend.setId(ids);
				schoolExtend.setIsChange(5); //关闭变更信息
			    updateSchoolExtendList.add(schoolExtend);
			}
			//批量更新状态为审核通过，并且启用
			schAccountManager.batchUpdateSchoolExtendList(updateSchoolExtendList, 0);

			logger.info("-------------------------------- BoundBankCardController Pass Update Success!");
		} 
		catch (NumberFormatException e) {
			logger.error("********************************* error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		} 
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	/**
	 * 同意变更
	 */
	@Override
	public ResponseMessage agreeChange(String checker, String remark, String id, String schoolNos) {
		try {
			String[] idList  = id.split(",");
			String[] scList = schoolNos.split(",");
			List<SchoolExtend> schoolList = schAccountManager.querySchoolBurseList(id);
			for (int i =0; i< schoolList.size(); i++) {
				if(schoolList.get(i).getCheckStatus() != 1){
					return new ResponseMessage(MessageCode.MSG_HANDE_CF);
				}
			}
			
			List<SchoolExtend> updateSchoolExtendList = new ArrayList<SchoolExtend>();
			SchoolExtend schoolExtend;
			Long ids;
			long schoolId;
			for (int i =0; i< idList.length; i++) {
				ids = Long.parseLong(idList[i]);
				schoolId = Long.parseLong(scList[i]);
				schoolExtend = new SchoolExtend();
				schoolExtend.setChecker(checker);
				schoolExtend.setCheckStatus(4);//同意变更
				schoolExtend.setChangeRemark(remark);
				schoolExtend.setIsDel(2);//启用
				schoolExtend.setId(ids);
				schoolExtend.setIsChange(5); //关闭变更信息
				schoolExtend.setSchoolId(schoolId);
			    updateSchoolExtendList.add(schoolExtend);
			}
			//更新原审核通过的为不启用
			//批量更新状态为审核通过，并且启用
			schAccountManager.batchUpdateSchoolExtendList(updateSchoolExtendList, 1);

			logger.info("-------------------------------- BoundBankCardController Pass Update Success!");
		} 
		catch (NumberFormatException e) {
			logger.error("********************************* error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		} 
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	/**
	 * 拒接变更
	 */
	@Override
	public ResponseMessage refuseChange(String checker, String remark, String id) {
		try {
			String[] idList  = id.split(",");
			List<SchoolExtend> schoolList = schAccountManager.querySchoolBurseList(id);
			for (int i =0; i< schoolList.size(); i++) {
				if(schoolList.get(i).getCheckStatus() != 1){
					return new ResponseMessage(MessageCode.MSG_HANDE_CF);
				}
			}
			
			List<SchoolExtend> updateSchoolExtendList = new ArrayList<SchoolExtend>();
			SchoolExtend schoolExtend;
			Long ids;
			for (int i =0; i< idList.length; i++) {
				ids = Long.parseLong(idList[i]);
				schoolExtend = new SchoolExtend();
				schoolExtend.setChecker(checker);
				schoolExtend.setCheckStatus(5);//拒接变更
				schoolExtend.setChangeRemark(remark);
				schoolExtend.setIsDel(1);//不启用
				schoolExtend.setId(ids);
				schoolExtend.setIsChange(5); //关闭变更信息
			    updateSchoolExtendList.add(schoolExtend);
			}
			//批量更新状态为拒接变更，并且不启用
			schAccountManager.batchUpdateSchoolExtendList(updateSchoolExtendList, 0);

			logger.info("-------------------------------- BoundBankCardController Pass Update Success!");
		} 
		catch (NumberFormatException e) {
			logger.error("********************************* error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		} 
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}
	
	
	//  ************************************************************
	 /**
	 * 							驾校端功能
	 */
	 // ************************************************************

	/**
	 * 申请开通驾校钱包
	 */
	@Override
	public ResponseMessage applyAccount(SchAccount schAccount, SchoolExtend schoolExtend) throws Exception {
		try {
			if("1".equals(schoolExtend.getType())){ //申请
				//验证手机、验证码
				ReqResult r = authcodeService.isCodeExist(schAccount.getCodeInput(), schAccount.getMobile(), String.valueOf(schAccount.getSchoolId()), "5");
				if(r.isSuccess()){
					//如果验证成功，则插入记录
					schAccountManager.applyAccount(schAccount, schoolExtend);
					//判断是否有原被驳回记录，更新为不启用
				}
				else{
					return new ResponseMessage(601,MessageCode.MSG_CODEINPUT_FAIL);
				}
			}
			else if("2".equals(schoolExtend.getType())){//变更商户资料
				SchoolExtend schoolExtendRes = new SchoolExtend();
				schoolExtendRes.setCheckStatus(2);
				//schoolExtendRes.setIsChange(2);
				schoolExtendRes.setSchoolId(schoolExtend.getSchoolId());
				SchoolExtend isChange = schAccountManager.queryExtendInfo(schoolExtendRes);
			/*	if( isChange != null){
					return new ResponseMessage(601,MessageCode.SCHOOL_INFO_IS_CHANGE);
				}*/
				
				schoolExtend.setCheckStatus(1);
				schoolExtend.setProtocolOpen(isChange.getProtocolOpen());
				schoolExtend.setIsDel(1);//不启用
				schoolExtend.setSign(2);//变更
				schAccountManager.changeSchoolInfo(schoolExtend); //插入一条记录
				
				schoolExtend.setIsChange(2);
				schAccountManager.updateIsChange(schoolExtend);//更新旧的记录是否有改变为2
			}
		} catch (Exception e) {
			logger.error("**************************************** applyAccount Error:" + e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	/**
	 * 查询驾校钱包信息	
	 */
	@Override
	public String queryAccount(SchoolExtend schoolExtend, String ymDate) throws Exception {
		String resp = null;
		try {
			SchAccount dto = new SchAccount();
			dto.setSchoolId(schoolExtend.getSchoolId());
			SchAccount schAccount = schAccountManager.queryBySchoolId(dto);
			
			SchoolExtend schoolExtendRes = null;
			schoolExtend.setIsDel(2);
			schoolExtendRes = schAccountManager.queryExtendInfo(schoolExtend);//是否有审核通过的账户信息
			MoneFreeVo moneFreeVo = null;
			if (schoolExtendRes != null && schAccount  != null) {
				schoolExtendRes.setMobile(schAccount.getMobile());
				schoolExtendRes.setMoney(schAccount.getMoney());
				
				//月度统计
				UserMoneyVo userMoneyVo = new UserMoneyVo();
				userMoneyVo.setUserId(schoolExtend.getSchoolId());
				userMoneyVo.setUserType(3);
				if(ymDate != null && !"".equals(ymDate)) {
					userMoneyVo.setOperateTime(TimeUtil.parseDate(ymDate, "yyyy-MM-dd hh:mm:ss"));
				}
				moneFreeVo = userMoneyService.querySumMoney(userMoneyVo);
				moneFreeVo.setProfitLossFree(moneFreeVo.getIncome() - moneFreeVo.getExpenseFree()); //月盈亏
				
			}
			resp = new ResponseMessage().addResult("pageData", schoolExtendRes)
					.addResult("moneFree", moneFreeVo).build();
			
		} catch (Exception e) {
			logger.error("*********************************queryAccount error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 申请提现
	 */
	@Override
	public String deposit(Long schoolId, String money, String passwd) {
		String keySchId = String.valueOf(schoolId);
		String resp = null;
		try {
			String sign = null;
			String timestamp = null;
			depositService.schDeposit(passwd, money, keySchId, timestamp, sign);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
			
		return resp;
	}

	
	/**
	 * 查询提现记录
	 */
	@Override
	public String queryDepositList(SchoolExtend schoolExtend) {
		String resp = null;
		try {
			SchDeposit schDeposit = new SchDeposit();
			schDeposit.setSchoolId(schoolExtend.getSchoolId());
			schDeposit.setStartTime(schoolExtend.getStartTime());
			schDeposit.setEndTime(schoolExtend.getEndTime());
			schDeposit.setCheckStatus(schoolExtend.getCheckStatus());
			resp = schoolDepositService.queryDepositList(schDeposit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 查询单笔提现详情
	 */
	@Override
	public String queryDeposit(SchoolExtend schoolExtend) {
		String resp = null;
		SchDeposit query;
		try {
			SchDeposit schDeposit = new SchDeposit();
			schDeposit.setSchoolId(schoolExtend.getSchoolId());
			schDeposit.setWaterId(schoolExtend.getWaterId());
			query = schoolDepositService.query(schDeposit);
			resp = new ResponseMessage().addResult("pageData", query).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 找回密码
	 */
	@Override
	public ResponseMessage queryPasswd(SchAccount dto) {
		try {
			//验证手机、验证码
			ReqResult r = authcodeService.isCodeExist(dto.getCodeInput(), dto.getMobile(), String.valueOf(dto.getSchoolId()), "5");
			if(r.isSuccess()){
				//如果验证成功，则更新密码
				schAccountManager.updatePasswd(dto);
				
				SchoolExtend schoolExtendInfo = new SchoolExtend();
				schoolExtendInfo.setSchoolId(dto.getSchoolId());
				schoolExtendInfo .setProtocolOpen(0);
				schAccountManager.updatePotocolOpenInit(schoolExtendInfo);
			}
			else{
				return new ResponseMessage(601,MessageCode.MSG_CODEINPUT_FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	/**
	 * 修改密码
	 */
	@Override
	public ResponseMessage alertPasswd(SchAccount dto) {
		try {
			SchAccount schAccount  = new SchAccount();
			schAccount.setSchoolId(dto.getSchoolId());
			schAccount.setPasswd(dto.getPasswdOld());
			SchAccount schAccountRes = schAccountManager.queryByPasswd(schAccount);//校验账户密码
			if(schAccountRes == null){//账户密码错误
				return new ResponseMessage(601,MessageCode.MSG_ACCOUNT_FAIL);
			}
			schAccountManager.updatePasswd(dto);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	/**
	 * 修改手机号
	 */
	@Override
	public ResponseMessage alertMobile(SchAccount dto) {
		try {
			//验证手机、验证码
			ReqResult r = authcodeService.isCodeExist(dto.getCodeInput(), dto.getMobile(), String.valueOf(dto.getSchoolId()), "5");
			if(r.isSuccess()){
				//如果验证成功，则更新手机号
				schAccountManager.updateMobile(dto);
			}
			else{
				return new ResponseMessage(601,MessageCode.MSG_CODEINPUT_FAIL);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		
		
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	/**
	 * 获取短信验证码
	 */
	@Override
	public ResponseMessage one(String mobile, String reqType) {
		try {
			ReqResult r = authcodeService.getAuthcodeOne(mobile, "5", reqType);
			logger.info("************************************ do authcode end!" + r.getResult());
			logger.info("************************************ do authcode end!" + r.isSuccess());
			if(r.isSuccess()){
				return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
			}
			else {
				return new ResponseMessage(MessageCode.MSG_FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
	}

	/**
	 * 校验短信验证码
	 */
	@Override
	public ResponseMessage verify(String mobile, String codeInput, Long schoolId) {
		try {
			ReqResult r = authcodeService.isCodeExist(codeInput, mobile, String.valueOf(schoolId), "5");
			if(r.isSuccess()){
				return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
			}
			else {
				return new ResponseMessage(601,MessageCode.MSG_CODEINPUT_FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseMessage(601,MessageCode.MSG_CODEINPUT_FAIL);
		}
	}

	/**
	 * 提现校验
	 */
	@Override
	public ResponseMessage depositVal(SchoolExtend schoolExtend) {
		try {
			schoolExtend.setCheckStatus(2);
			SchoolExtend depositSum = schAccountManager.queryExtendInfo(schoolExtend);//查询账户信息
			if(depositSum.getProtocolOpen() >= 5){//密码错误5次以上
				return  new ResponseMessage().addResult("pageData", "1");
			}
			
			//提现次数查询
			String startTime = DateUtil.getStartDate();
			String endTime =  DateUtil.getEndDate();
			logger.info("*******************************SchoolId: "+schoolExtend.getSchoolId()+ ",startTime:" +startTime+ ",endTime:"+ endTime);
			SchDeposit schDeposit  = new SchDeposit();
			schDeposit.setSchoolId(schoolExtend.getSchoolId());
			schDeposit.setStartTime(startTime);
			schDeposit.setEndTime(endTime);
			int count = schoolDepositService.queryLeftDepositCount(schDeposit);
			if(count < Constant.SCHOOL_WITHDRAW_COUNT) { //可以提现
				return  new ResponseMessage().addResult("pageData", "0");
			}
			else {
				return  new ResponseMessage().addResult("pageData", "2");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return  new ResponseMessage().addResult("pageData", "0");
	}

	/**
	 * 关闭提示
	 */
	@Override
	public ResponseMessage closeRemark(SchoolExtend schoolExtend) {
		try {
			schoolExtend.setIsChange(5);
			schAccountManager.closeRemark(schoolExtend);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	/**
	 * 下载提现记录
	 */
	@Override
	public List<SchDeposit> downLoadExcel(SchDeposit schdeposit) {
		return schoolDepositService.downLoadExcel(schdeposit);
	}

	@Override
	public Long querySchoolMoney() {
		try {
			return schAccountManager.querySchoolMoney();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long queryLiliWalletMoney() {
		try {
			return schAccountManager.queryLiliWalletMoney();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResponseMessage touchBalance(UserMoneyVo userMoneyVo) {
		PagedResult<UserMoneyVo> userMoney =  userMoneyService.querytouchBalance(userMoneyVo);
		return new ResponseMessage().addResult("pageData", userMoney);
	}

	@Override
	public ResponseMessage accountBalance(UserMoneyVo userMoneyVo) {
		userMoneyVo.setIsEarning(1);
		Long expense = userMoneyService.querySumMoneyByIsEarning(userMoneyVo,"2"); //收入
		PagedResult<UserMoneyVo> userMoney =  userMoneyService.querytouchBalance(userMoneyVo);
		return new ResponseMessage().addResult("pageData", userMoney).addResult("expense", expense);
	}

	@Override
	public ResponseMessage costDetail(UserMoneyVo userMoneyVo) {
		userMoneyVo.setIsEarning(0);
		Long expense = userMoneyService.querySumMoneyByIsEarning(userMoneyVo,"1"); //费用
		//PagedResult<UserMoneyVo> userMoney =  userMoneyService.querytouchBalance(userMoneyVo);
		PagedResult<UserMoneyVo> userMoney =  userMoneyService.queryUserDetailList(userMoneyVo,"1");
		return new ResponseMessage().addResult("pageData", userMoney).addResult("expense", expense);
	}

	@Override
	public ResponseMessage getTheoryBySchoolId(Long schoolId, String state, String pageNo, String pageSize, String dateBegin,
			String dateEnd,String createBegin,String createEnd) {
		Page<EnrollTheory> theoryList = enrollTheoryManager.getTheoryBySchoolId(schoolId.intValue(),state,pageNo,pageSize,dateBegin,dateEnd,createBegin,createEnd);
		return new ResponseMessage().addResult("pageData", theoryList);
	}
	
	@Override
	public ResponseMessage getTheoryBySchoolId(List<Integer> schoolIds, String state, String pageNo, String pageSize, String dateBegin,
			String dateEnd,String createBegin,String createEnd) {
		Page<EnrollTheory> theoryList = enrollTheoryManager.getTheoryBySchoolId(schoolIds,state,pageNo,pageSize,dateBegin,dateEnd,createBegin,createEnd);
		return new ResponseMessage().addResult("pageData", theoryList);
	}

	@Override
	public ResponseMessage addTheory(Long schoolId, String classDate, String classTime, String className,
			String cardDesc, String contactName, String contactMobile, String classPlace) {
		int result = enrollTheoryManager.addTheory(schoolId.intValue(), classDate, classTime, className, cardDesc, contactName, contactMobile, classPlace);
		return new ResponseMessage().addResult("pageData", result);
	}

	@Override
	public ResponseMessage modifyTheory(String theoryId, Long schoolId, String classDate, String classTime,
			String className, String cardDesc, String contactName, String contactMobile, String classPlace) {
		int result = enrollTheoryManager.modifyTheory(theoryId,schoolId.intValue(), classDate, classTime, className, cardDesc, contactName, contactMobile, classPlace);
		return new ResponseMessage().addResult("pageData", result);
	}

	@Override
	public ResponseMessage addTheoryStudent(Long schoolId, String theoryId, String studentIds) {
		int result = enrollTheoryManager.addTheoryStudent(schoolId.intValue(), theoryId, studentIds);
		return new ResponseMessage().addResult("pageData", result);
	}

	@Override
	public ResponseMessage deleteTheoryStudent(Long schoolId, String theoryId, String studentIds) {
		int result = enrollTheoryManager.deleteTheoryStudent(schoolId.intValue(), theoryId, studentIds);
		return new ResponseMessage().addResult("pageData", result);
	}

	@Override
	public ResponseMessage getTheoryStudent(Long schoolId, String theoryId, String pageNo, String pageSize,
			String state, String isImport) {
		Page<EnrollTheoryStudent> theoryList = enrollTheoryManager.getTheoryStudent(schoolId.intValue(), theoryId, pageNo, pageSize, state, isImport);
		return new ResponseMessage().addResult("pageData", theoryList);
	}
	

	@Override
	public Page<EnrollTheoryStudent> getTheoryStudentPage(Long schoolId, String theoryId, String pageNo,
			String pageSize, String state, String isImport) {
		return enrollTheoryManager.getTheoryStudent(schoolId.intValue(), theoryId, pageNo, pageSize, state, isImport);
	}

	@Override
	public ResponseMessage getTheoryMsgInfo(String state) {
		String msgInfo = authcodeService.getMsgTemplate(ReqConstants.PROG_STAGE_THEORY,state);
		return new ResponseMessage().addResult("pageData", msgInfo);
	}

	@Override
	public ResponseMessage changeTheoryClass(Long schoolId, String theoryId, String state) {
		int result = enrollTheoryManager.changeTheoryClass(schoolId.intValue(), theoryId, state);
		return new ResponseMessage().addResult("pageData", result);
	}

	@Override
	public ResponseMessage changeTheoryStudentState(Long schoolId, String theoryId, String studentIds, String state) {
		int result = enrollTheoryManager.changeTheoryStudentState(schoolId.intValue(), theoryId, studentIds, state);
		return new ResponseMessage().addResult("pageData", result);
	}

	@Override
	public ResponseMessage getTheoryByTheoryId(Long schoolId, String theoryId) {
		EnrollTheory theory = enrollTheoryManager.getEnrollTheoryByTheoryId(Integer.parseInt(theoryId));
		return new ResponseMessage().addResult("pageData", theory);
	}
	
	@Override
	public EnrollTheory getTheoryByTheoryId(String theoryId){
		EnrollTheory theory = enrollTheoryManager.getEnrollTheoryByTheoryId(Integer.parseInt(theoryId));
		return theory;
	}

	@Override
	public ResponseMessage getLongtrainCoach(Long schoolId, String contactMobile) {
		Coach coach = coachManager.getCoachByPhoneNum(contactMobile.trim());
		return new ResponseMessage().addResult("pageData", coach);
	}

	@Override
	public ResponseMessage getLongtrainCar(Long schoolId, String carNo) {
		CarInfo ci = carManager.getCarInfoByNo(carNo);
		return new ResponseMessage().addResult("pageData", ci);
	}

	@Override
	public ResponseMessage addLongtrain(Long schoolId, String contactMobile, String carNo, String classDate,
			String classTime, String classPlace, String carrys) {
		int result = enrollLongtrainManager.addLongtrain(schoolId.intValue(),contactMobile,carNo, classDate, classTime, classPlace,carrys);
		return new ResponseMessage().addResult("pageData", result);
	}

	@Override
	public ResponseMessage getLongtrain(Long schoolId, String state, String pageNo, String pageSize, String dateBegin,
			String dateEnd,String createBegin,String createEnd) {
		Page<EnrollLongtrain> data = enrollLongtrainManager.getLongtrain(schoolId.intValue(),state,pageNo,pageSize,dateBegin,dateEnd,createBegin,createEnd);
		return new ResponseMessage().addResult("pageData", data);
	}
	
	@Override
	public ResponseMessage getLongtrain(List<Integer> schoolIds, String state, String pageNo, String pageSize, String dateBegin,
			String dateEnd,String createBegin,String createEnd) {
		Page<EnrollLongtrain> data = enrollLongtrainManager.getLongtrain(schoolIds,state,pageNo,pageSize,dateBegin,dateEnd,createBegin,createEnd);
		return new ResponseMessage().addResult("pageData", data);
	}

	@Override
	public ResponseMessage getLongtrainOne(Long schoolId, String ltId) {
		EnrollLongtrain data = enrollLongtrainManager.getLongtrainOne(Integer.parseInt(ltId));
		return new ResponseMessage().addResult("pageData", data);
	}

	@Override
	public ResponseMessage modifyLongtrain(Long schoolId, String ltId, String contactMobile, String carNo,
			String classDate, String classTime, String classPlace, String carrys) {
		int result = enrollLongtrainManager.modifyLongtrain(schoolId.intValue(),ltId,contactMobile,carNo, classDate, classTime, classPlace,carrys);
		return new ResponseMessage().addResult("pageData", result);
	}

	@Override
	public ResponseMessage addLongtrainStudent(Long schoolId, String ltId, String studentIds) {
		int result = enrollLongtrainManager.addLongtrainStudent(schoolId.intValue(), ltId, studentIds);
		return new ResponseMessage().addResult("pageData", result);
	}

	@Override
	public ResponseMessage deleteLongtrainStudent(Long schoolId, String ltId, String studentIds) {
		int result = enrollLongtrainManager.deleteLongtrainStudent(schoolId.intValue(), ltId, studentIds);
		return new ResponseMessage().addResult("pageData", result);
	}

	@Override
	public ResponseMessage getLongtrainMsgInfo(String state) {
		String msgInfo = authcodeService.getMsgTemplate(ReqConstants.PROG_STAGE_SUBJECT_LONGTRAIN,state);
		return new ResponseMessage().addResult("pageData", msgInfo);
	}

	@Override
	public ResponseMessage changeLongtrainClass(Long schoolId, String ltId, String state) {
		int result = enrollLongtrainManager.changeLongtrainClass(schoolId.intValue(), ltId, state);
		return new ResponseMessage().addResult("pageData", result);
	}

	@Override
	public ResponseMessage changeLongtrainStudentState(Long schoolId, String ltId, String studentIds, String state) {
		int result = enrollLongtrainManager.changeLongtrainStudentState(schoolId.intValue(), ltId, studentIds, state);
		return new ResponseMessage().addResult("pageData", result);
	}

	@Override
	public ResponseMessage getLongtrainStudent(Long schoolId, String ltId, String pageNo, String pageSize, String state,
			String isImport) {
		if(schoolId==null||schoolId.intValue()==0){
			EnrollLongtrain train=enrollLongtrainManager.getLongtrainOne(Integer.parseInt(ltId));
			if(train!=null){
				schoolId=Long.valueOf(train.getSchoolId());
			}
		}
		Page<EnrollLongtrainStudent> data = enrollLongtrainManager.getLongtrainStudent(schoolId.intValue(), ltId, pageNo, pageSize, state, isImport);
		return new ResponseMessage().addResult("pageData", data);
	}
	
	@Override
	public Page<EnrollLongtrainStudent> getLongtrainStudentPage(Long schoolId, String ltId, String pageNo,
			String pageSize, String state, String isImport) {
		return enrollLongtrainManager.getLongtrainStudent(schoolId.intValue(), ltId, pageNo, pageSize, state, isImport);
	}

	@Override
	public ResponseMessage upload(final File outfile, final String fileName, final Long schoolId, String creator, final String type) {
		final String uuid = StringUtil.getOrderId(); //表编号
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				Map<String,Object> result = new HashMap<>();
				  String resp = null;
				  // 解析文件
				  //创建Excel工作簿对象	
				  Workbook wb = null; 
				  boolean isE2007 = false;
				  if (fileName .endsWith("xlsx")) {
						isE2007  = true;  
				  }
				  FileInputStream fis = null;
		        //根据文件格式(2003或者2007)来初始化  
		        try {
		      	 fis = new FileInputStream(outfile);
					if(isE2007)  {
					    wb = new XSSFWorkbook(fis);  
					  }
					  else  {
					  	wb = new HSSFWorkbook(fis);  
					  }

					if(type.trim().equals("1")){ // 1-预约登记表 2-考试成绩表
						String tableNo = "YK-"+ uuid;
						  EnrollExamReg enrollExamReg = null;
						  List<EnrollExamReg> enrollExamRegList = new ArrayList<EnrollExamReg>();
						  int valid = 0;
						  String headDesc = "";
						  int subjectId = 0;
						  Sheet sheet = wb.getSheetAt(0);     //获得第一个表单  
						  Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
						  while (rows.hasNext()) { 
						      Row row = rows.next();  //获得行数据  

					    	  int rowNo = row.getRowNum();
						      logger.debug("Row #" + rowNo);  //获得行号从0开始  
						      
						      if(rowNo == 0){ //第一行获得标题--科目一预约登记查询
						    	  Cell cell = row.getCell(0);
						    	  String cellValue = cell.getStringCellValue().trim();
						    	  headDesc = cellValue;
						    	  if(cellValue.contains("一")){
						    		  subjectId = ReqConstants.SUBJECT_TYPE_ONE;
						    	  }else if(cellValue.contains("二")){
						    		  subjectId = ReqConstants.SUBJECT_TYPE_TWO;
						    	  }
						    	  else if(cellValue.contains("安全文明")){ //科目三安全文明预约登记查询
						    		  subjectId = ReqConstants.SUBJECT_TYPE_FOUR;
						    	  }
						    	  else if(cellValue.contains("三")){
						    		  subjectId = ReqConstants.SUBJECT_TYPE_THREE;
						    	  }else if(cellValue.contains("四")){
						    		  subjectId = ReqConstants.SUBJECT_TYPE_FOUR;
						    	  }else {
						    		  subjectId =  -1; //未知科目
						    	  }
						    	  
						      }else if (rowNo >= 2){ //数据从第三行开始
						    	  //如果第一个单元格数据为空，则认为这是一条无效数据，跳过
						    	  if(null == row.getCell(0)){
						    		  continue;
						    	  }
						    	  String test = row.getCell(0).getStringCellValue().trim();
						    	  if(StringUtil.isEmptyOrWhitespaceOnly(test)){
						    		  continue;
						    	  }
						    	  
						    	  Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  
						    	  
						    	  enrollExamReg = new EnrollExamReg();
						    	  enrollExamReg.setSchoolId(schoolId.intValue());
						    	  enrollExamReg.setTableNo(tableNo);
						    	  enrollExamReg.setCtime(new Date());
						    	  enrollExamReg.setSubjectId(subjectId);
						    	  
						    	  while (cells.hasNext()) {  
							          Cell cell = cells.next(); 
							          int columnNo = cell.getColumnIndex();
							          String cellValue = cell.getStringCellValue().trim();
							          logger.debug("Cell #" + columnNo + " value #" + cellValue);  
							          
							          switch (columnNo){
							          case 0:
							        	  try {
											enrollExamReg.setFlowNo(Long.parseLong(cellValue));
										} catch (Exception e1) {
											e1.printStackTrace();
										}
							        	  break;
							          case 1:
							        	  enrollExamReg.setIdNumber(cellValue);
							        	  Student s = studentManager.getStudentByIdNumber(cellValue);
							        	  if(null == s){
							        		  enrollExamReg.setIsdel(ReqConstants.OPERATE_DELETE);//用户不存在，无效数据
							        	  }else {
							        		  enrollExamReg.setIsdel(ReqConstants.OPERATE_DELETE_NO);//用户存在，有效数据
							        		  enrollExamReg.setStudentId(s.getStudentId());
							        		  valid ++;
							        	  }
							        	  break;
							          case 2:
							        	  try {
											enrollExamReg.setName(cellValue);
										} catch (Exception e3) {
											e3.printStackTrace();
										}
							        	  break;
							          case 3:
							        	  try {
											enrollExamReg.setSchoolName(cellValue);
										} catch (Exception e3) {
											e3.printStackTrace();
										}
							        	  break;
							          case 4:
							        	  if(cellValue.equalsIgnoreCase("C1")){
							        		  enrollExamReg.setDrType((byte)ReqConstants.DRIVE_TYPE_C1);
							        	  }else{
							        		  enrollExamReg.setDrType((byte)ReqConstants.DRIVE_TYPE_C2);
							        	  }
							        	  break;
							        	  
							          case 5:
							        	  if(cellValue.contains("正常")){
							        		  enrollExamReg.setRegistState(0);
							        	  }else if(cellValue.contains("滚动")){
							        		  enrollExamReg.setRegistState(1);
							        	  }else if(cellValue.contains("取消")){
							        		  enrollExamReg.setRegistState(2);
							        	  }else {
							        		  enrollExamReg.setRegistState(-1); //未识别的状态
							        	  }
							        	  break;
							        	  
							          case 6:
							        	  try {
											enrollExamReg.setRegistTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cellValue) );
										} catch (ParseException e) {
											e.printStackTrace();
										}
							        	  break;
							          case 7:
							        	  try {
											enrollExamReg.setRegistPeriod(new SimpleDateFormat("yyyy-MM-dd").parse(cellValue) );
										} catch (ParseException e) {
											e.printStackTrace();
										}
							        	  break;
							          case 8:
							        	  try {
											enrollExamReg.setWaitDays(Integer.parseInt(cellValue));
										} catch (Exception e1) {
											e1.printStackTrace();
										}
							        	  break;
							          case 9:
							        	  if(cellValue.contains("等待")){ //等待排队
							        		  enrollExamReg.setBookResult(0);
							        	  }else if(cellValue.contains("已排")){
							        		  enrollExamReg.setBookResult(1);
							        	  }else if(cellValue.contains("没排")){
							        		  enrollExamReg.setBookResult(2);
							        	  }else {
							        		  enrollExamReg.setBookResult(-1); //未识别的状态
							        	  }
							        	  break;
							          case 10:
							        	  try {
											enrollExamReg.setExamPlace(cellValue);
										} catch (Exception e2) {
											e2.printStackTrace();
										}
							        	  break;
							          case 11:
							        	  try {
											enrollExamReg.setExamDate(new SimpleDateFormat("yyyy-MM-dd").parse(cellValue) );
										} catch (Exception e) {
											e.printStackTrace();
										}
							        	  break;
							          case 12:
							        	  try {
											enrollExamReg.setExamOrder(cellValue);
										} catch (Exception e1) {
											e1.printStackTrace();
										}
							        	  break;
							          case 13:
							        	  try {
											enrollExamReg.setExamTime(cellValue);
										} catch (Exception e1) {
											e1.printStackTrace();
										}
							        	  break;
							          case 14:
							        	  try {
											enrollExamReg.setQueueTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cellValue) );
										} catch (ParseException e) {
											e.printStackTrace();
										}
							        	  break;
							          default:
							        		  break;
							        	  
							          } 
							      }
						    	// ----------解析学员状态----------
								int regist = enrollExamReg.getRegistState();
								int book = enrollExamReg.getBookResult();
								int applystate = 0;
								if(	(regist == 0 && book == 0)
										||(regist == 1 && book == 0)
										||(regist == 1 && book == 2)){
									applystate = ReqConstants.STAGE_STATE_SUBMIT;
								}else if((regist == 0 && book == 1)
										||(regist == 1 && book == 1)){
									applystate = ReqConstants.STAGE_STATE_SUCC;
								}else if(regist == 0 && book == 2)
										{
									applystate = ReqConstants.STAGE_STATE_FAIL;
								}else if(regist == 2){
									applystate = ReqConstants.STAGE_STATE_NONE;
								}
								enrollExamReg.setImportState(0); //默认导入状态未开始
						    	enrollExamReg.setApplystate(applystate);
								enrollExamRegList.add(enrollExamReg);
						    	  
						      }
						}
						   
						  
						if(null != enrollExamRegList && enrollExamRegList.size() > 0){
							result.put("dataList", enrollExamRegList);
							result.put("total", enrollExamRegList.size());
							result.put("valid", valid);
							result.put("headDesc", headDesc);
							result.put("type", type);
							result.put("uuid", uuid);
							result.put("tableNo", tableNo);
							result.put("subjectId", subjectId);
							redisUtil.set(RedisKeys.REDISKEY.UPLOAD + uuid, result, RedisKeys.EXPIRE.HOUR);
						}
						
						
					}else if(type.trim().equals("2")){ // 1-预约登记表 2-考试成绩表
						String tableNo = "CJ-"+ uuid;
						  EnrollExamResult enrollExamResult = null;
						  List<EnrollExamResult> enrollExamResultList = new ArrayList<EnrollExamResult>();
						  int valid = 0;
						  String headDesc = "";
						  int subjectId = 0;
						  Sheet sheet = wb.getSheetAt(0);     //获得第一个表单  
						  Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
						  while (rows.hasNext()) { 
						      Row row = rows.next();  //获得行数据  

					    	  int rowNo = row.getRowNum();
						      logger.debug("Row #" + rowNo);  //获得行号从0开始  
						      
						      if(rowNo == 0){ //第一行获得标题--科目一预约登记查询
						    	  Cell cell = row.getCell(0);
						    	  String cellValue = cell.getStringCellValue().trim();
						    	  headDesc = cellValue;
						    	  if(cellValue.contains("一")){
						    		  subjectId = ReqConstants.SUBJECT_TYPE_ONE;
						    	  }else if(cellValue.contains("二")){
						    		  subjectId = ReqConstants.SUBJECT_TYPE_TWO;
						    	  }else if(cellValue.contains("三")){
						    		  subjectId = ReqConstants.SUBJECT_TYPE_THREE;
						    	  }else if(cellValue.contains("四")){
						    		  subjectId = ReqConstants.SUBJECT_TYPE_FOUR;
						    	  }else {
						    		  subjectId =  -1; //未知科目
						    	  }
						    	  
						      }else if (rowNo >= 2){ //数据从第三行开始
						    	  //如果第一个单元格数据为空，则认为这是一条无效数据，跳过
						    	  if(null == row.getCell(0)){
						    		  continue;
						    	  }
						    	  String test = row.getCell(0).getStringCellValue().trim();
						    	  if(StringUtil.isEmptyOrWhitespaceOnly(test)){
						    		  continue;
						    	  }
						    	  
						    	  Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  
						    	  
						    	  enrollExamResult = new EnrollExamResult();
						    	  enrollExamResult.setSchoolId(schoolId.intValue());
						    	  enrollExamResult.setTableNo(tableNo);
						    	  enrollExamResult.setCtime(new Date());
						    	  enrollExamResult.setSubjectId(subjectId);
						    	  
						    	  while (cells.hasNext()) {  
							          Cell cell = cells.next(); 
							          int columnNo = cell.getColumnIndex();
							          String cellValue = cell.getStringCellValue().trim();
							          logger.debug("Cell #" + columnNo + " value #" + cellValue);  
							          
							          switch (columnNo){
							          case 0:
							        	  enrollExamResult.setIdNumber(cellValue);
							        	  Student s = studentManager.getStudentByIdNumber(cellValue);
							        	  if(null == s){
							        		  enrollExamResult.setIsdel(ReqConstants.OPERATE_DELETE);//用户不存在，无效数据
							        	  }else {
							        		  enrollExamResult.setIsdel(ReqConstants.OPERATE_DELETE_NO);//用户存在，有效数据
							        		  enrollExamResult.setStudentId(s.getStudentId());
							        		  valid ++;
							        	  }
							        	  break;
							          case 1:
							        	  try {
											enrollExamResult.setName(cellValue);
										} catch (Exception e1) {
											e1.printStackTrace();
										}
							        	  break;
							          case 2:
							        	  try {
											enrollExamResult.setFlowNo(Long.parseLong(cellValue));
										} catch (Exception e1) {
											e1.printStackTrace();
										}
							        	  break;
							          case 3:
							        	  try {
											enrollExamResult.setSchoolName(cellValue);
										} catch (Exception e1) {
											e1.printStackTrace();
										}
							        	  break;
							          case 4:
							        	  if(subjectId == ReqConstants.SUBJECT_TYPE_TWO || subjectId == ReqConstants.SUBJECT_TYPE_THREE){
							        		//科二与科三的考试日期格式不同
								        	  try {
													enrollExamResult.setExamDate(new SimpleDateFormat("yyyy-MM-dd").parse(cellValue) );
												} catch (ParseException e) {
													e.printStackTrace();
												}
							        	  }else{
								        	  try {
													enrollExamResult.setExamDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cellValue) );
												} catch (ParseException e) {
													e.printStackTrace();
												}
							        	  }
							        	  break;
							        	  
							          case 5:
							        	  if(subjectId == ReqConstants.SUBJECT_TYPE_TWO || subjectId == ReqConstants.SUBJECT_TYPE_THREE){
							        		//科二与科三导出的数据表多了两列无效数据，需要跳过
							        	  }else{
							        		  enrollExamResult.setScore(Integer.parseInt(cellValue));
							        	  }
							        	  break;
							        	  
							          case 6:
							        	  if(subjectId == ReqConstants.SUBJECT_TYPE_TWO || subjectId == ReqConstants.SUBJECT_TYPE_THREE){
							        		  //科二与科三导出的数据表多了两列无效数据，需要跳过
							        	  }else{
								        	  if(cellValue.equalsIgnoreCase("合格")){
								        		  enrollExamResult.setApplystate(100);
								        	  }else if(cellValue.equalsIgnoreCase("不合格")){
								        		  enrollExamResult.setApplystate(101);
								        	  }else {
								        		  enrollExamResult.setApplystate(-1); //未识别的状态
								        	  }
							        	  }
							        	  break;
							          case 7:
							        	  if(subjectId == ReqConstants.SUBJECT_TYPE_TWO || subjectId == ReqConstants.SUBJECT_TYPE_THREE){
							        		  //科二与科三导出的数据表多了两列无效数据，需要跳过
							        		  enrollExamResult.setScore(Integer.parseInt(cellValue));
							        	  }else{
							        	  }
							        	  break;
							          case 8:
							        	  if(subjectId == ReqConstants.SUBJECT_TYPE_TWO || subjectId == ReqConstants.SUBJECT_TYPE_THREE){
							        		  //科二与科三导出的数据表多了两列无效数据，需要跳过
								        	  if(cellValue.equalsIgnoreCase("合格")){
								        		  enrollExamResult.setApplystate(100);
								        	  }else if(cellValue.equalsIgnoreCase("不合格")){
								        		  enrollExamResult.setApplystate(101);
								        	  }else {
								        		  enrollExamResult.setApplystate(-1); //未识别的状态
								        	  }
							        	  }else{
							        	  }
							        	  break;


							          default:
							        		  break;
							        	  
							          } 
							      }
						    	  // ---
						    	  enrollExamResult.setImportState(0);
							    	//20160810数据排重，存在有的学员考了两次，第一次考试失败后，有再考一次的机会。数据中有两条记录，但以第二次考试的成绩为准。
							    	boolean hasRecord = false;
						    	  for(int m=0; m < enrollExamResultList.size(); m++){
							    		EnrollExamResult en = enrollExamResultList.get(m);
							    		if(en.getFlowNo().longValue() == enrollExamResult.getFlowNo().longValue()){
							    			hasRecord = true;
							    			if(en.getExamDate().before(enrollExamResult.getExamDate())){ //如果已有数据时间比新数据早，去掉老数据。
							    				enrollExamResultList.remove(m);
							    				enrollExamResultList.add(enrollExamResult);
							    				break;
							    			}else {
							    				//如果已有数据比较新，则不增加这条记录。
							    				break;
							    			}
							    		}
							    		
							    	}
						    	  if(!hasRecord){
						    		  enrollExamResultList.add(enrollExamResult);
						    	  }
						    	  
						      }
						}
						if(null != enrollExamResultList && enrollExamResultList.size() > 0){
							result.put("dataList", enrollExamResultList);
							result.put("total", enrollExamResultList.size());
							result.put("valid", valid);
							result.put("headDesc", headDesc);
							result.put("type", type);
							result.put("uuid", uuid);
							result.put("tableNo", tableNo);
							result.put("subjectId", subjectId);
							redisUtil.set(RedisKeys.REDISKEY.UPLOAD + uuid, result, RedisKeys.EXPIRE.HOUR);
						}
					}
					  
					logger.info("*********************************uploadExcelDetail Start!");
					if (outfile.exists()) {  // 不存在返回 false  
					     if (outfile.isFile()) {   // 判断是否为文件  
					         outfile.delete();
					     } 
					}
					fis.close();
				} catch (FileNotFoundException e) {
					logger.error("*********************************** error: " + e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					logger.error("*********************************** error: " + e.getMessage());
					e.printStackTrace();
				} finally {
			      	try {
							if(fis != null){
								fis.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
			      }
				
			}
		});

        return new ResponseMessage().addResult("pageData", uuid);
	}
	
	@Override
	public ResponseMessage uploadNew(final byte[] b, final String fileName, final Long schoolId, String creator, final String type) {
		final String uuid = StringUtil.getOrderId(); //表编号
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				Map<String,Object> result = new HashMap<>();
				  String resp = null;
				  // 解析文件
				  //创建Excel工作簿对象	
				  Workbook wb = null; 
				  boolean isE2007 = false;
				  if (fileName .endsWith("xlsx")) {
						isE2007  = true;  
				  }
				  InputStream fis =  new ByteArrayInputStream(b);
		        //根据文件格式(2003或者2007)来初始化  
		        try {
		      	 
					if(isE2007)  {
					    wb = new XSSFWorkbook(fis);  
					  }
					  else  {
					  	wb = new HSSFWorkbook(fis);  
					  }

					if(type.trim().equals("1")){ // 1-预约登记表 2-考试成绩表
						String tableNo = "YK-"+ uuid;
						  EnrollExamReg enrollExamReg = null;
						  List<EnrollExamReg> enrollExamRegList = new ArrayList<EnrollExamReg>();
						  int valid = 0;
						  String headDesc = "";
//						  int subjectId = 0;
						  Sheet sheet = wb.getSheetAt(0);     //获得第一个表单  
						  Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
						  Map<String,String> flowmap=new HashMap();	
						  while (rows.hasNext()) { 
						      Row row = rows.next();  //获得行数据  

					    	  int rowNo = row.getRowNum();
						      logger.debug("Row #" + rowNo);  //获得行号从0开始  
						      
						      if(rowNo == 0){ //第一行获得标题--科目一预约登记查询
						    	  Cell cell = row.getCell(0);
						    	  String cellValue = cell.getStringCellValue().trim();
						    	  headDesc = cellValue;
						    	  
						      }else if (rowNo >= 2){ //数据从第三行开始
						    	  //如果第一个单元格数据为空，则认为这是一条无效数据，跳过
						    	  if(null == row.getCell(0)){
						    		  continue;
						    	  }
						    	  String test = row.getCell(0).getStringCellValue().trim();
						    	  if(StringUtil.isEmptyOrWhitespaceOnly(test)){
						    		  continue;
						    	  }
						    	  
						    	  Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  
						    	  
						    	  enrollExamReg = new EnrollExamReg();
						    	  enrollExamReg.setSchoolId(schoolId.intValue());
						    	  enrollExamReg.setTableNo(tableNo);
						    	  enrollExamReg.setCtime(new Date());
						    	  
						    	  /**
						    	   * /**
    
								    public final static int CELL_TYPE_NUMERIC = 0;
								
								    
								    public final static int CELL_TYPE_STRING = 1;
								
								    
								    public final static int CELL_TYPE_FORMULA = 2;
								
								    
								    public final static int CELL_TYPE_BLANK = 3;
								
								    
								    public final static int CELL_TYPE_BOOLEAN = 4;
								
								    
								    public final static int CELL_TYPE_ERROR = 5;
						    	   */
						    	  while (cells.hasNext()) {  
							          Cell cell = cells.next(); 
							          int columnNo = cell.getColumnIndex();int a=Cell.CELL_TYPE_BOOLEAN;
							          logger.debug("type # "+cell.getCellType()+" "+columnNo);
							          String cellValue="";
							          if(cell.getCellType()!=Cell.CELL_TYPE_STRING&&cell.getCellType()!=Cell.CELL_TYPE_BLANK){
							        	  enrollExamReg.setExtra("第"+(columnNo+1)+"列数据格式错误");
							          }else{
							        	  cellValue = cell.getStringCellValue().trim();
							          }
							          logger.debug("Cell #" + columnNo + " value #" + cellValue);  
							          
							          switch (columnNo){
							          case 0:
							        	  int subjectId=0;
								    	  
								    	  if(cellValue.contains("一")){
								    		  subjectId = ReqConstants.SUBJECT_TYPE_ONE;
								    	  }else if(cellValue.contains("二")){
								    		  subjectId = ReqConstants.SUBJECT_TYPE_TWO;
								    	  }
								    	  else if(cellValue.contains("安全文明")){ //科目三安全文明预约登记查询
								    		  subjectId = ReqConstants.SUBJECT_TYPE_FOUR;
								    	  }
								    	  else if(cellValue.contains("三")){
								    		  subjectId = ReqConstants.SUBJECT_TYPE_THREE;
								    	  }else if(cellValue.contains("四")){
								    		  subjectId = ReqConstants.SUBJECT_TYPE_FOUR;
								    	  }else {
								    		  subjectId =  -1; //未知科目
								    		  enrollExamReg.setIsdel(ReqConstants.OPERATE_DELETE);
								    		  enrollExamReg.setExtra("科目错误");
								    	  }
								    	  enrollExamReg.setSubjectId(subjectId);
								    	  enrollExamReg.setExamPlace(cellValue);
							        	  break;
							          case 1:
							        	  try {
												enrollExamReg.setExamDate(new SimpleDateFormat("yyyy-MM-dd").parse(cellValue) );
											} catch (Exception e) {
												enrollExamReg.setIsdel(ReqConstants.OPERATE_DELETE);
								        		enrollExamReg.setExtra("考试日期数据错误");
												e.printStackTrace();
											}
							        	  
							        	  break;
							          case 2:
							        	  try {
												enrollExamReg.setExamTime(cellValue);
											} catch (Exception e1) {
												e1.printStackTrace();
											}
							        	  break;
							          case 3:
							        	  try {
											enrollExamReg.setSchoolName(cellValue);
										} catch (Exception e3) {
											e3.printStackTrace();
										}
							        	  break;
							          case 4:
							        	  try {
												enrollExamReg.setName(cellValue);
											} catch (Exception e3) {
												e3.printStackTrace();
											}
							        	  
							        	  break;
							        	  
							          case 5:
							        	  try {
												enrollExamReg.setFlowNo(Long.parseLong(cellValue));
												if(flowmap.containsKey(cellValue)){
													enrollExamReg.setExtra("流水号重复");
													enrollExamReg.setIsdel(ReqConstants.OPERATE_DELETE);
												}else{
													flowmap.put(cellValue, cellValue);
												}
											} catch (Exception e1) {
												e1.printStackTrace();
											}
							        	  
							        	  break;
							        	  
							          case 6:
	
								        	  enrollExamReg.setIdNumber(cellValue);
								        	  Student s = studentManager.getStudentByIdNumber(cellValue);
								        	  if(null == s){
								        		  enrollExamReg.setIsdel(ReqConstants.OPERATE_DELETE);//用户不存在，无效数据
								        		  enrollExamReg.setExtra("用户不存在");
								        	  }else {
								        		  enrollExamReg.setStudentId(s.getStudentId());
								        		  if(enrollExamReg.getIsdel()==null||enrollExamReg.getIsdel()==0){
								        		  enrollExamReg.setIsdel(ReqConstants.OPERATE_DELETE_NO);//用户存在，有效数据
								        		  valid ++;
								        		  }
								        	  }
								        	  
							        	 
							        	  break;
							          case 7:

							        	  if(cellValue.contains("不符合")){
							        		  enrollExamReg.setBookResult(2);
							        	  }else if(cellValue.contains("符合")){
							        		  enrollExamReg.setBookResult(1);
							        	  }else {
							        		  enrollExamReg.setBookResult(-1); //未识别的状态
							        		  enrollExamReg.setIsdel(ReqConstants.OPERATE_DELETE);
							        		  enrollExamReg.setExtra("条件数据错误");
							        	  }
						        	  	
							        	  break;
							          case 8:
							        	 
							        	  break;
							          case 9:
							        	  try {
												enrollExamReg.setRegistTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cellValue) );
											} catch (ParseException e) {
												enrollExamReg.setIsdel(ReqConstants.OPERATE_DELETE);
								        		enrollExamReg.setExtra("预约时间内容错误");
												e.printStackTrace();
											}
							        	  break;
							          case 10:
							        	  
							        	  break;
							          case 11:
							        	  
							        	  break;
							          case 12:
							        	  
							        	  break;
							          case 13:
							        	  
							        	  break;
							          case 14:
							        	  
							        	  break;
							          default:
							        		  break;
							        	  
							          } 
							      }
						    	// ----------解析学员状态----------
								//int regist = enrollExamReg.getRegistState();
								int book = enrollExamReg.getBookResult();
								int applystate = 0;
								if(book == 1){
									applystate = ReqConstants.STAGE_STATE_SUCC;
								}else if(book == 2){
									applystate = ReqConstants.STAGE_STATE_FAIL;
								}
								enrollExamReg.setImportState(0); //默认导入状态未开始
						    	enrollExamReg.setApplystate(applystate);
								enrollExamRegList.add(enrollExamReg);
						    	  
						      }
						}  
						  
						if(null != enrollExamRegList && enrollExamRegList.size() > 0){
							result.put("dataList", enrollExamRegList);
							result.put("total", enrollExamRegList.size());
							result.put("valid", valid);
							result.put("headDesc", headDesc);
							result.put("type", type);
							result.put("uuid", uuid);
							result.put("tableNo", tableNo);
							//result.put("subjectId", subjectId);
							redisUtil.set(RedisKeys.REDISKEY.UPLOAD + uuid, result, RedisKeys.EXPIRE.HOUR);
						}
						
						
					}else if(type.trim().equals("2")){ // 1-预约登记表 2-考试成绩表
						String tableNo = "CJ-"+ uuid;
						  EnrollExamResult enrollExamResult = null;
						  List<EnrollExamResult> enrollExamResultList = new ArrayList<EnrollExamResult>();
						  int valid = 0;
						  String headDesc = "";
						  int subjectId = 0;
						  Sheet sheet = wb.getSheetAt(0);     //获得第一个表单  
						  Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
						  while (rows.hasNext()) { 
						      Row row = rows.next();  //获得行数据  

					    	  int rowNo = row.getRowNum();
						      logger.debug("Row #" + rowNo);  //获得行号从0开始  
						      
						      if(rowNo == 0){ //第一行获得标题--科目一预约登记查询
						    	  Cell cell = row.getCell(0);
						    	  String cellValue = cell.getStringCellValue().trim();
						    	  headDesc = cellValue;
						    	  if(cellValue.contains("一")){
						    		  subjectId = ReqConstants.SUBJECT_TYPE_ONE;
						    	  }else if(cellValue.contains("二")){
						    		  subjectId = ReqConstants.SUBJECT_TYPE_TWO;
						    	  }else if(cellValue.contains("三")){
						    		  subjectId = ReqConstants.SUBJECT_TYPE_THREE;
						    	  }else if(cellValue.contains("四")){
						    		  subjectId = ReqConstants.SUBJECT_TYPE_FOUR;
						    	  }else {
						    		  subjectId =  -1; //未知科目
						    	  }
						    	  
						      }else if (rowNo >= 2){ //数据从第三行开始
						    	  //如果第一个单元格数据为空，则认为这是一条无效数据，跳过
						    	  if(null == row.getCell(0)){
						    		  continue;
						    	  }
						    	  String test = row.getCell(0).getStringCellValue().trim();
						    	  if(StringUtil.isEmptyOrWhitespaceOnly(test)){
						    		  continue;
						    	  }
						    	  
						    	  Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  
						    	  
						    	  enrollExamResult = new EnrollExamResult();
						    	  enrollExamResult.setSchoolId(schoolId.intValue());
						    	  enrollExamResult.setTableNo(tableNo);
						    	  enrollExamResult.setCtime(new Date());
						    	  enrollExamResult.setSubjectId(subjectId);
						    	  
						    	  while (cells.hasNext()) {  
							          Cell cell = cells.next(); 
							          int columnNo = cell.getColumnIndex();
							          String cellValue = cell.getStringCellValue().trim();
							          logger.debug("Cell #" + columnNo + " value #" + cellValue);  
							          
							          switch (columnNo){
							          case 0:
							        	  enrollExamResult.setIdNumber(cellValue);
							        	  Student s = studentManager.getStudentByIdNumber(cellValue);
							        	  if(null == s){
							        		  enrollExamResult.setIsdel(ReqConstants.OPERATE_DELETE);//用户不存在，无效数据
							        	  }else {
							        		  enrollExamResult.setIsdel(ReqConstants.OPERATE_DELETE_NO);//用户存在，有效数据
							        		  enrollExamResult.setStudentId(s.getStudentId());
							        		  valid ++;
							        	  }
							        	  break;
							          case 1:
							        	  try {
											enrollExamResult.setName(cellValue);
										} catch (Exception e1) {
											e1.printStackTrace();
										}
							        	  break;
							          case 2:
							        	  try {
											enrollExamResult.setFlowNo(Long.parseLong(cellValue));
										} catch (Exception e1) {
											e1.printStackTrace();
										}
							        	  break;
							          case 3:
							        	  try {
											enrollExamResult.setSchoolName(cellValue);
										} catch (Exception e1) {
											e1.printStackTrace();
										}
							        	  break;
							          case 4:
							        	  if(subjectId == ReqConstants.SUBJECT_TYPE_TWO || subjectId == ReqConstants.SUBJECT_TYPE_THREE){
							        		//科二与科三的考试日期格式不同
								        	  try {
													enrollExamResult.setExamDate(new SimpleDateFormat("yyyy-MM-dd").parse(cellValue) );
												} catch (ParseException e) {
													e.printStackTrace();
												}
							        	  }else{
								        	  try {
													enrollExamResult.setExamDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cellValue) );
												} catch (ParseException e) {
													e.printStackTrace();
												}
							        	  }
							        	  break;
							        	  
							          case 5:
							        	  if(subjectId == ReqConstants.SUBJECT_TYPE_TWO || subjectId == ReqConstants.SUBJECT_TYPE_THREE){
							        		//科二与科三导出的数据表多了两列无效数据，需要跳过
							        	  }else{
							        		  enrollExamResult.setScore(Integer.parseInt(cellValue));
							        	  }
							        	  break;
							        	  
							          case 6:
							        	  if(subjectId == ReqConstants.SUBJECT_TYPE_TWO || subjectId == ReqConstants.SUBJECT_TYPE_THREE){
							        		  //科二与科三导出的数据表多了两列无效数据，需要跳过
							        	  }else{
								        	  if(cellValue.equalsIgnoreCase("合格")){
								        		  enrollExamResult.setApplystate(100);
								        	  }else if(cellValue.equalsIgnoreCase("不合格")){
								        		  enrollExamResult.setApplystate(101);
								        	  }else {
								        		  enrollExamResult.setApplystate(-1); //未识别的状态
								        	  }
							        	  }
							        	  break;
							          case 7:
							        	  if(subjectId == ReqConstants.SUBJECT_TYPE_TWO || subjectId == ReqConstants.SUBJECT_TYPE_THREE){
							        		  //科二与科三导出的数据表多了两列无效数据，需要跳过
							        		  enrollExamResult.setScore(Integer.parseInt(cellValue));
							        	  }else{
							        	  }
							        	  break;
							          case 8:
							        	  if(subjectId == ReqConstants.SUBJECT_TYPE_TWO || subjectId == ReqConstants.SUBJECT_TYPE_THREE){
							        		  //科二与科三导出的数据表多了两列无效数据，需要跳过
								        	  if(cellValue.equalsIgnoreCase("合格")){
								        		  enrollExamResult.setApplystate(100);
								        	  }else if(cellValue.equalsIgnoreCase("不合格")){
								        		  enrollExamResult.setApplystate(101);
								        	  }else {
								        		  enrollExamResult.setApplystate(-1); //未识别的状态
								        	  }
							        	  }else{
							        	  }
							        	  break;


							          default:
							        		  break;
							        	  
							          } 
							      }
						    	  // ---
						    	  enrollExamResult.setImportState(0);
							    	//20160810数据排重，存在有的学员考了两次，第一次考试失败后，有再考一次的机会。数据中有两条记录，但以第二次考试的成绩为准。
							    	boolean hasRecord = false;
						    	  for(int m=0; m < enrollExamResultList.size(); m++){
							    		EnrollExamResult en = enrollExamResultList.get(m);
							    		if(en.getFlowNo().longValue() == enrollExamResult.getFlowNo().longValue()){
							    			hasRecord = true;
							    			if(en.getExamDate().before(enrollExamResult.getExamDate())){ //如果已有数据时间比新数据早，去掉老数据。
							    				enrollExamResultList.remove(m);
							    				enrollExamResultList.add(enrollExamResult);
							    				break;
							    			}else {
							    				//如果已有数据比较新，则不增加这条记录。
							    				break;
							    			}
							    		}
							    		
							    	}
						    	  if(!hasRecord){
						    		  enrollExamResultList.add(enrollExamResult);
						    	  }
						    	  
						      }
						}
						if(null != enrollExamResultList && enrollExamResultList.size() > 0){
							result.put("dataList", enrollExamResultList);
							result.put("total", enrollExamResultList.size());
							result.put("valid", valid);
							result.put("headDesc", headDesc);
							result.put("type", type);
							result.put("uuid", uuid);
							result.put("tableNo", tableNo);
							result.put("subjectId", subjectId);
							redisUtil.set(RedisKeys.REDISKEY.UPLOAD + uuid, result, RedisKeys.EXPIRE.HOUR);
						}
					}
					  
					logger.info("*********************************uploadExcelDetail Start!");
					
				} catch (Exception e) {
					logger.error("*********************************** error: " + e.getMessage());
					e.printStackTrace();
				} finally {
			      }
				
			}
		});

        return new ResponseMessage().addResult("pageData", uuid);
	}

	@Override
	public ResponseMessage getUpdateResult(Long schoolId, String uuid) {
		Map<String,Object> result = redisUtil.get(RedisKeys.REDISKEY.UPLOAD + uuid);
		return new ResponseMessage().addResult("pageData", result); //
	}

	@Override
	public ResponseMessage changeUpdateState(Long schoolId, String uuid, String state,String userName) {
		int result = enrollSubjectManager.changeUpdateState(schoolId.intValue(), uuid, Integer.parseInt(state),userName);
		return new ResponseMessage().addResult("pageData", result);
	}

	@Override
	public ResponseMessage getUpdateTables(Long schoolId, String type, String pageNo, String pageSize, String dateBegin,
			String dateEnd) {
		Page<EnrollImportRecord> data = enrollSubjectManager.getUpdateTables(schoolId.intValue(),type,pageNo,pageSize,dateBegin,dateEnd);
		return new ResponseMessage().addResult("pageData", data);
	}

	@Override
	public ResponseMessage getUpdateTablesOne(Long schoolId, String type, String pageNo, String pageSize,
			String tableNo) {
		Page data = enrollSubjectManager.getUpdateTablesOne(schoolId.intValue(),type,pageNo,pageSize,tableNo);
		return new ResponseMessage().addResult("pageData", data);
	}

	@Override
	public ResponseMessage getUpdateTablesOneInfo(Long schoolId, String tableNo) {
		EnrollImportRecord data = enrollSubjectManager.getUpdateTablesOneInfo(schoolId.intValue(),tableNo);
		return new ResponseMessage().addResult("pageData", data);
	}

	@Override
	public Long findSchoolIdByName(String schoolName)throws Exception {
		return cmsSchoolManager.findSchoolIdByName(schoolName);
	}
	
	@Override
	public ResponseMessage findWxSchoolList(SchoolBDTO school) throws Exception {
		PagedResult<SchoolBDTO> batch = cmsSchoolManager.findWxSchoolList(school);
		return new ResponseMessage().addResult("pageData", batch);
	}
	
	@Override
	public ResponseMessage findWxSchoolById(int schoolId) throws Exception {
		SchoolBDTO school = cmsSchoolManager.findWxSchoolById(schoolId);
		return new ResponseMessage().addResult("pageData", school);
	}
	
	@Override
	public ResponseMessage addWxSchool(SchoolBDTO dto) {
		try {
			cmsSchoolManager.addWxSchool(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage updateWxSchool(SchoolBDTO dto) {
		try {
			SchoolBDTO school=cmsSchoolManager.findWxSchoolById(Integer.parseInt(dto.getSchoolId().toString()));
			if(school==null){
				cmsSchoolManager.addWxSchool(dto);
			}else{
				cmsSchoolManager.updateWxSchool(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}
	
	@Override
	public ResponseMessage findPackageList(WechatEnrollPackageBDTO wBdto) throws Exception {
		PagedResult<WechatEnrollPackage> batch = cmsSchoolManager.findPackageList(wBdto);
		return new ResponseMessage().addResult("pageData", batch);
	}
	
	@Override
	public WechatEnrollPackage findPackageById(String ttid) throws Exception {
		WechatEnrollPackage wechatEnrollPackage= cmsSchoolManager.findPackageById(ttid);
		return wechatEnrollPackage;
	}
	
	
	@Override
	public ResponseMessage addPackage(WechatEnrollPackageBDTO dto) {
		try {
			cmsSchoolManager.addPackage(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage updatePackage(WechatEnrollPackageBDTO dto) {
		try {
			cmsSchoolManager.updatePackage(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}
	
	@Override
	public ResponseMessage addPackageStudent(int studentId,int ttid){
		try {
			cmsSchoolManager.addPackageStudent(studentId,ttid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}
	

	@Override
	public ResponseMessage applySchoolAccount(SchoolAccountApply apply)
			throws Exception {
		String pwd=EncryptUtil.DSHA1(apply.getSchoolPwd());
		apply.setSchoolPwd(pwd);
		cmsSchoolManager.applySchoolAccount(apply);
		return new  ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage findApplySchool(SchoolAccountApply apply)
			throws Exception {
		List<SchoolAccountApply>  data= cmsSchoolManager.findSchoolApply(apply);
		
		PagedResult<SchoolAccountApply> result = new PagedResult<SchoolAccountApply>();
        result.setPageNo(apply.getPageNo());
        result.setPageSize(apply.getPageSize());
        result.setDataList(data);
        int total=cmsSchoolManager.findApplyTotal(apply);
        result.setTotal(total);
        result.setPages((total/apply.getPageSize())+1);
		
		ResponseMessage<List<SchoolAccountApply>> response=new ResponseMessage();
		//response.setData(data);
		response.setCode(0);
		response.setMsg(MessageCode.MSG_SUCCESS);
		response.addResult("pageData", result);
		return response;
	}

	
	
	@Override
	public School findSchoolById(long schoolId) throws Exception {
		return cmsSchoolManager.findSchoolById(schoolId);
	}

	@Override
	public ResponseMessage auditSchool(SchoolAccountApply apply)
			throws Exception {
		
		SchoolAccountApply exist=cmsSchoolManager.getApply(apply);
		
		if(exist==null){
			return new  ResponseMessage(600,MessageCode.MSG_DATA_NONEXIST);
		}
		
		if(exist!=null&&exist.getStatus()==1){
			return new  ResponseMessage(600,MessageCode.MSG_AUDIT_STATUS);
		}
		
		User euser= cmsUserService.findByAccount(apply.getSchoolAccount());
		if(euser!=null){
			return new  ResponseMessage(600,MessageCode.MSG_USER_EXIST);
		}
		
		apply.setStatus(1);
		cmsSchoolManager.updateSchoolApply(apply);
		if(apply.getSchoolId()>0){//已经存在驾校
			User user=new User();
			user.setAccount(exist.getSchoolAccount());
			user.setPassword(exist.getSchoolPwd());
			user.setSchoolId(apply.getSchoolId());
			user.setUserName(exist.getShortName());
			cmsUserService.insertOne(user);
		}else{
			SchoolBDTO dto=new SchoolBDTO();
			dto.setAddress(exist.getAddress());
			dto.setName(exist.getSchoolName());
			dto.setPhoneNum(exist.getPhoneNum());
			cmsSchoolManager.addSchool(dto);
			
			User user=new User();
			user.setAccount(exist.getSchoolAccount());
			user.setPassword(exist.getSchoolPwd());
			user.setSchoolId(dto.getSchoolId());
			user.setUserName(exist.getShortName());
			cmsUserService.insertOne(user);
		}
		
		return new  ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}
	@Override
	public ResponseMessage updatePackageState(WechatEnrollPackageBDTO dto,String ttids) {
		try {
			cmsSchoolManager.updatePackageState(dto,ttids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}
	
	@Override
	public ResponseMessage unauditSchool(SchoolAccountApply apply)
			throws Exception {
		apply.setStatus(2);
		cmsSchoolManager.updateSchoolApply(apply);
		return new  ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}
	
	@Override
	public List<School> findAllSchool(SchoolBDTO dto) throws Exception{
		return cmsSchoolManager.findAllSchool(dto);
	}
	
}
