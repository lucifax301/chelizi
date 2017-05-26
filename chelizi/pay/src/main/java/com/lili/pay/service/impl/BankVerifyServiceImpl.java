package com.lili.pay.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.dto.Coach;
import com.lili.coach.dto.CoachAccount;
import com.lili.coach.manager.CoachManager;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.pay.config.BankInfo;
import com.lili.pay.dto.BankcardVerifyDto;
import com.lili.pay.mapper.dao.BankcardVerifyDtoMapper;
import com.lili.pay.service.IBankVerifyService;
import com.lili.pay.vo.BankcardVerifyVo;
import com.lili.student.dto.Student;
import com.lili.student.dto.StudentAccount;
import com.lili.student.manager.StudentManager;

/**
 * 银行卡绑定接口实现类
 * @author lzb
 *
 */
public class BankVerifyServiceImpl implements IBankVerifyService {
	 Logger logger = LoggerFactory.getLogger(BankVerifyServiceImpl.class);
	
    @Autowired
    private BankcardVerifyDtoMapper bankcardVerifyDtoMapper;
    
    @Autowired
    private CoachManager coachManager;
    
    @Autowired
    private StudentManager studentManager;

    @Autowired
    protected RedisUtil redisUtil;

    /**
     * 申请绑定银行卡
     */
	@Override
	public ReqResult boundBankCard(String bankName, String bankCard, String name, String mobile, String userId,
			String userType, String timestamp, String sign) {
		logger.info("---------------------- BoundBankCard Start! --------------------");
		ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);

		//校验APP请求内容
        reqResult = validParam(bankName, bankCard, name, mobile, userId, userType, timestamp);
        if (!ResultCode.ERRORCODE.SUCCESS.equals(reqResult.getResult().get("code"))){
        	return reqResult;
        }
        
        Long userIdV = Long.valueOf(userId.trim());
        Integer userTypeV = Integer.valueOf(userType.trim());
        String bankCardTrim = bankCard.replaceAll(" ", "");
        BankcardVerifyDto verifyInfo = new BankcardVerifyDto();
        verifyInfo.setBankName(bankName);
        verifyInfo.setBankNo(bankCardTrim);
        verifyInfo.setName(name);
        verifyInfo.setMobile(mobile);
        verifyInfo.setStatus(1);
		//是否存在该记录
		logger.info("---------------------- BoundBankCard Is Exit Record : userId : " + userIdV + ",userTypeV : " + userTypeV +"--------------------");
        try {
        	BankcardVerifyDto isExitVerifyDto  = bankcardVerifyDtoMapper.queryIsExitBoundRecord(userTypeV, userIdV, bankCardTrim);
        	logger.info("------------------------------------------ IsExitVerifyDto :  "+ isExitVerifyDto);
        	Integer isDel = 0;
			if (isExitVerifyDto != null) {
				if(isDel.equals(isExitVerifyDto.getIsDel())) { //如果状态是未删除
					reqResult.setCode(ResultCode.ERRORCODE.IS_BOUND_CARD);
					reqResult.setMsgInfo(ResultCode.ERRORINFO.IS_BOUND_CARD);
					return reqResult;
				}
				else { //如果状态删除，更新原有记录
					logger.info("------------------------------------- BoundBankCard Is Not Valid : userId : " + userIdV + ",userTypeV : " + userTypeV +", Update New Record! ");
					verifyInfo.setUserId(userIdV);
					verifyInfo.setUserType(userTypeV);
					verifyInfo.setIsDel(0);
					bankcardVerifyDtoMapper.updateByUserId(verifyInfo );
				}
			}
			else {
				logger.info("BoundBankCard Is Not Exit : userId : " + userIdV + ",userTypeV : " + userTypeV +", Insert New Record! ");
				verifyInfo.setUserId(userIdV);
				verifyInfo.setUserType(userTypeV);
				bankcardVerifyDtoMapper.insertSelective(verifyInfo);
				
			}
		} catch (Exception e) {
			logger.error("---------------------- boundBankCard error :" +e.getMessage());
			reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
            return reqResult;
		}
		
		return reqResult;
	}
	
	@Override
	public ReqResult validBindBank(String bankCard, String userId, String userType) {
		logger.info("---------------------- BoundBankCard Start! --------------------");
		ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);

        Long userIdV = Long.valueOf(userId.trim());
        Integer userTypeV = Integer.valueOf(userType.trim());
        String bankCardTrim = bankCard.replaceAll(" ", "");
        try {
        	//是否存在该记录
        	BankcardVerifyDto isExitVerifyDto  = bankcardVerifyDtoMapper.queryIsExitBoundRecord(userTypeV, userIdV, bankCardTrim);
        	logger.info("------------------------------------------ IsExitVerifyDto :  "+ isExitVerifyDto);
        	Integer isDel = 0;
			if (isExitVerifyDto != null) {
				if(isDel.equals(isExitVerifyDto.getIsDel())) { //如果状态是未删除
					reqResult.setCode(ResultCode.ERRORCODE.IS_BOUND_CARD);
					reqResult.setMsgInfo(ResultCode.ERRORINFO.IS_BOUND_CARD);
					return reqResult;
				}
			}
		} catch (Exception e) {
			logger.error("---------------------- boundBankCard error :" +e.getMessage());
			reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
            return reqResult;
		}
		
		return reqResult;
	}
	
	/**
	 * 校验APP请求内容
	 * @param bankName
	 * @param bankCard
	 * @param name
	 * @param mobile
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @return
	 */
	public ReqResult validParam(String bankName, String bankCard, String name, String mobile, String userId,
			String userType, String timestamp){
		ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);

		//校验APP请求内容
		if (bankName == null ||  "".equals(bankName)) {
			 reqResult.setCode(ResultCode.ERRORCODE.BANKNAME_IS_NULL);
             reqResult.setMsgInfo(ResultCode.ERRORINFO.BANKNAME_IS_NULL);
             return reqResult;
		}
		if (bankCard == null ||  "".equals(bankCard)) {
			reqResult.setCode(ResultCode.ERRORCODE.BANKCARD_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.BANKCARD_IS_NULL);
			return reqResult;
		}
		if (name == null ||  "".equals(name)) {
			reqResult.setCode(ResultCode.ERRORCODE.CARDNAME_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.CARDNAME_IS_NULL);
			return reqResult;
		}
		if (userId == null ||  "".equals(userId)) {
			reqResult.setCode(ResultCode.ERRORCODE.USERID_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.USERID_IS_NULL);
			return reqResult;
		}
		if (userType == null ||  "".equals(userType)) {
			reqResult.setCode(ResultCode.ERRORCODE.USERTYPE_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.USERTYPE_IS_NULL);
			return reqResult;
		}
		if (timestamp == null ||  "".equals(timestamp)) {
			reqResult.setCode(ResultCode.ERRORCODE.TIME_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.TIME_IS_NULL);
			return reqResult;
		}
		
		return reqResult;
	}

	/**
	 * 查询绑定银行卡
	 */
	@Override
	public ReqResult queryBoundBankCard(String userId, String userType, String timestamp, String sign) {
		ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
        
       //校验APP请求内容
    	if (userId == null ||  "".equals(userId)) {
			reqResult.setCode(ResultCode.ERRORCODE.USERID_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.USERID_IS_NULL);
			return reqResult;
		}
		if (userType == null ||  "".equals(userType)) {
			reqResult.setCode(ResultCode.ERRORCODE.USERTYPE_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.USERTYPE_IS_NULL);
			return reqResult;
		}
		Integer userIdV = Integer.valueOf(userId);
		Integer userTypeV = Integer.valueOf(userType);
		
		logger.info("---------------------- queryBoundBankCard userIdV : " + userId + ", userType : "+ userType);
        
    	try {
			List<BankcardVerifyDto>  bankCardList = bankcardVerifyDtoMapper.queryBoundBankCardList(userIdV, userTypeV);
			if (bankCardList.size() > 0){
				logger.info("---------------------- queryBoundBankCard bankCardList --------------------" + bankCardList.toString());
				List<BankcardVerifyVo> depositVo = BeanCopy.copyList(bankCardList, BankcardVerifyVo.class, BeanCopy.COPYNOTNULL);
				reqResult = ReqResult.getSuccess();
				reqResult.setData(depositVo);
			}
			else {
				logger.info("---------------------- queryBoundBankCard bankCardList Is Null !--------------------" );
				//reqResult.setCode(ResultCode.ERRORCODE.IS_NOT_BOUND_CARD);
				//reqResult.setMsgInfo(ResultCode.ERRORINFO.IS_NOT_BOUND_CARD);
				return reqResult;
			}
		} catch (Exception e) {
			logger.error("---------------------- queryBoundBankCard error --------------------" + e.getMessage());
			reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
            return reqResult;
		}
    	
		return reqResult;
	}

	/**
	 * 解除已绑定银行卡
	 */
	@Override
	public ReqResult removeBoundBankCard(String pw, String bankCard, String userId, String userType, String timestamp,
			String sign) {
		ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
        
       //校验APP请求内容
        if (pw == null ||  "".equals(pw)) {
			 reqResult.setCode(ResultCode.ERRORCODE.PASSWORD_EMPTY_ERROR);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.PASSWORD_EMPTY_ERROR);
            return reqResult;
		}
		if (bankCard == null ||  "".equals(bankCard)) {
			reqResult.setCode(ResultCode.ERRORCODE.BANKCARD_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.BANKCARD_IS_NULL);
			return reqResult;
		}
        if (userId == null ||  "".equals(userId)) {
			reqResult.setCode(ResultCode.ERRORCODE.USERID_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.USERID_IS_NULL);
			return reqResult;
		}
		if (userType == null ||  "".equals(userType)) {
			reqResult.setCode(ResultCode.ERRORCODE.USERTYPE_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.USERTYPE_IS_NULL);
			return reqResult;
		}
		
        //校验用户密码
        try {
			long checkIsCorrectPasswd;
			long userIdLong = Long.parseLong(userId);
			 String bankCardTrim = bankCard.replaceAll(" ", "");
			logger.info("---------------------- queryBoundBankCard userIdV : " + userId + ", pw : "+ pw);
			if ("1".equals(userType)) {
				checkIsCorrectPasswd = coachManager.checkCoachCorrectPw(pw, userIdLong);
			}
			else if ("2".equals(userType)) {
				checkIsCorrectPasswd = studentManager.checkStudentCorrectPw(pw,userIdLong);
			}
			else {
				reqResult.setCode(ResultCode.ERRORCODE.USERTYPE_IS_ERROR);
				reqResult.setMsgInfo(ResultCode.ERRORINFO.USERTYPE_IS_ERROR);
				return reqResult;
			}
			if (checkIsCorrectPasswd > 0){
				BankcardVerifyDto record = new BankcardVerifyDto();
				record.setUserId(Long.valueOf(userId));
				record.setUserType(Integer.valueOf(userType));
				record.setBankNo(bankCardTrim);
				record.setIsDel(1);
				bankcardVerifyDtoMapper.updateByUserId(record );
			}
			else {
				reqResult.setCode(ResultCode.ERRORCODE.PASSWORD_ERROR);
				reqResult.setMsgInfo(ResultCode.ERRORINFO.PASSWORD_ERROR);
				return reqResult;
			}
		} catch (NumberFormatException e) {
			logger.error("---------------------- removeBoundBankCard error --------------------" + e.getMessage());
			reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
            return reqResult;
		}
        
		return reqResult;
	}

	@Override
	public ReqResult setPassword(String pw, String userId, String userType, String reqType, String timestamp, String sign) {
		ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
        
       //校验APP请求内容
        if (pw == null ||  "".equals(pw)) {
			 reqResult.setCode(ResultCode.ERRORCODE.PASSWORD_EMPTY_ERROR);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.PASSWORD_EMPTY_ERROR);
            return reqResult;
		}
        if (userId == null ||  "".equals(userId)) {
			reqResult.setCode(ResultCode.ERRORCODE.USERID_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.USERID_IS_NULL);
			return reqResult;
		}
		if (userType == null ||  "".equals(userType)) {
			reqResult.setCode(ResultCode.ERRORCODE.USERTYPE_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.USERTYPE_IS_NULL);
			return reqResult;
		}
		if(!"0".equals(reqType) && !"1".equals(reqType)) { 
			reqResult.setCode(ResultCode.ERRORCODE.PARAMERROR);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
			return reqResult;
		}
		
        try {
			//设置用户密码
			long userIdLong =  Long.parseLong(userId);
			if("0".equals(reqType)) { //设置密码
				if ("1".equals(userType)) {
					logger.info("-------------------------Setting Coach : "+ userIdLong + " Password Start ! ");
					CoachAccount coachAccountInfo= coachManager.getCoachMoney(userIdLong);
					if (coachAccountInfo == null) { //判断教练账户表是否已存在用户及密码	
						CoachAccount c = new CoachAccount();
						c.setCoachId(userIdLong);
						c.setPasswd(pw);
						c.setMoney(0);
						coachManager.addCoachAccount(c);
					}
					else {
						coachManager.updateCoachPassword(pw, userIdLong);
					}
					Coach coach = new Coach();
					coach.setCoachId(userIdLong);
					coach.setPwstate(1);
					coachManager.updateCoach(coach);
					logger.info("-------------------------Setting Coach : "+ userIdLong + " Password Success !");
				}
				else if ("2".equals(userType)) {
					logger.info("-------------------------Setting Student : "+ userIdLong + " Password Start ! ");
					StudentAccount studentAccountInfo= studentManager.getStudentMoney(userIdLong);
					if (studentAccountInfo == null) { //判断学员账户表是否已存在用户及密码	
						StudentAccount sa = new StudentAccount();
						sa.setStudentId(userIdLong);
						sa.setPasswd(pw);
						sa.setMoney(0);
						studentManager.insertStudentAccount(sa);
						
					}
					else {
						studentManager.updateStudentPassword(pw, userIdLong);
					}
					Student student = new Student();
					student.setStudentId(userIdLong);
					student.setPwstate((byte)1);
					studentManager.updateStudent(student);
					logger.info("-------------------------Setting Student : "+ userIdLong + " Password Success !");
				}
				else {
					reqResult.setCode(ResultCode.ERRORCODE.USERTYPE_IS_ERROR);
					reqResult.setMsgInfo(ResultCode.ERRORINFO.USERTYPE_IS_ERROR);
					return reqResult;
				}
				
			}
			else if ("1".equals(reqType)) { //更新密码
				if ("1".equals(userType)) {
					logger.info("-------------------------Setting Coach : "+ userIdLong + " Password Start ! ");
					coachManager.updateCoachPassword(pw, userIdLong);
					logger.info("-------------------------Setting Coach : "+ userIdLong + " Password Success !");
				}
				else if ("2".equals(userType)) {
					logger.info("-------------------------Setting Student : "+ userIdLong + " Password Start ! ");
					studentManager.updateStudentPassword(pw, userIdLong);
					logger.info("-------------------------Setting Student : "+ userIdLong + " Password Success !");
				}
				else {
					reqResult.setCode(ResultCode.ERRORCODE.USERTYPE_IS_ERROR);
					reqResult.setMsgInfo(ResultCode.ERRORINFO.USERTYPE_IS_ERROR);
					return reqResult;
				}
			}
		} catch (NumberFormatException e) {
			logger.error("---------------------- setPassword error --------------------" + e.getMessage());
			reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
            return reqResult;
		}
        
		return reqResult;
	}
	
	@Override
	public ReqResult validPassword(String pw, String userId, String userType, String timestamp, String sign) {
		ReqResult reqResult = new ReqResult();
		reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
		reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
		
		//校验APP请求内容
		if (pw == null ||  "".equals(pw)) {
			reqResult.setCode(ResultCode.ERRORCODE.PASSWORD_EMPTY_ERROR);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.PASSWORD_EMPTY_ERROR);
			return reqResult;
		}
		if (userId == null ||  "".equals(userId)) {
			reqResult.setCode(ResultCode.ERRORCODE.USERID_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.USERID_IS_NULL);
			return reqResult;
		}
		if (userType == null ||  "".equals(userType)) {
			reqResult.setCode(ResultCode.ERRORCODE.USERTYPE_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.USERTYPE_IS_NULL);
			return reqResult;
		}
		
		try {
			//校验用户密码
			long userIdLong =  Long.parseLong(userId);
			if ("1".equals(userType)) {
				logger.info("-------------------------ValidPassword Query Coach : "+ userIdLong);
				CoachAccount coachAccountInfo= coachManager.getCoachMoney(userIdLong);
				if (coachAccountInfo == null) { //判断教练账户表是否已存在用户及密码	
					logger.info("-------------------------ValidPassword Query Coach  Info is null! Student : "+ userIdLong );
					reqResult.setCode(ResultCode.ERRORCODE.PASSWORD_IS_NULL);
					reqResult.setMsgInfo(ResultCode.ERRORINFO.PASSWORD_IS_NULL);
					return reqResult;
				}
				else {
					if(pw.equals(coachAccountInfo.getPasswd())){
						logger.info("-------------------------ValidPassword Valid Coach  Password  Correct! CoachId: "+ userIdLong );
						reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
						reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
						redisUtil.set(REDISKEY.USER_CHECK_PW + userId, userType, 60);
						return reqResult;
					}
					else {
						reqResult.setCode(ResultCode.ERRORCODE.PASSWORD_ERROR);
						reqResult.setMsgInfo(ResultCode.ERRORINFO.PASSWORD_ERROR);
						return reqResult;
					}
				}
			}
			else if ("2".equals(userType)) {
				logger.info("-------------------------ValidPassword Query Student : "+ userIdLong);
				StudentAccount studentAccountInfo= studentManager.getStudentMoney(userIdLong);
				if (studentAccountInfo == null) { //判断学员账户表是否已存在用户及密码	
					logger.info("-------------------------ValidPassword Query Student  Info is null! Student : "+ userIdLong );
					reqResult.setCode(ResultCode.ERRORCODE.PASSWORD_IS_NULL);
					reqResult.setMsgInfo(ResultCode.ERRORINFO.PASSWORD_IS_NULL);
					return reqResult;
				}
				else {
					if(pw.equals(studentAccountInfo.getPasswd())){
						logger.info("-------------------------ValidPassword Valid Student  Password  Correct! StudentId: "+ userIdLong );
						reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
						reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
						redisUtil.set(REDISKEY.USER_CHECK_PW + userId, userType, 60);
						return reqResult;
					}
					else {
						reqResult.setCode(ResultCode.ERRORCODE.PASSWORD_ERROR);
						reqResult.setMsgInfo(ResultCode.ERRORINFO.PASSWORD_ERROR);
						return reqResult;
					}
				}
			}
			else {
				reqResult.setCode(ResultCode.ERRORCODE.USERTYPE_IS_ERROR);
				reqResult.setMsgInfo(ResultCode.ERRORINFO.USERTYPE_IS_ERROR);
				return reqResult;
			}
		} catch (NumberFormatException e) {
			logger.error("---------------------- validPassword error --------------------" + e.getMessage());
			reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
            return reqResult;
		}
		
	}

	@Override
	public ReqResult getBankInfo(String bankCard) {
		ReqResult reqResult = new ReqResult();
		reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
		reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
		String bankCardStr = bankCard.replaceAll(" ", "");
		
		//校验APP请求内容
		if (bankCardStr == null ||  "".equals(bankCardStr)) {
			reqResult.setCode(ResultCode.ERRORCODE.BANKCARD_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.BANKCARD_IS_NULL);
			return reqResult;
		}
		if(bankCardStr.length() > 19 || bankCardStr.length() <15){
			reqResult.setCode(ResultCode.ERRORCODE.BANKCARD_IS_ERROR);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.BANKCARD_IS_ERROR);
			return reqResult;
		}
		
		String name = null;
		 if (bankCardStr.startsWith("62")) {  
			   Boolean isRightCard =  BankInfo.checkBankCard(bankCardStr);
			   if(isRightCard){
				   name = BankInfo.getNameOfBank(bankCardStr); // 获取银行卡的信息  
				   if("".equals(name)){
					   reqResult.setCode(ResultCode.ERRORCODE.BANKCARD_IS_ERROR);
					   reqResult.setMsgInfo(ResultCode.ERRORINFO.BANKCARD_IS_ERROR);
					   return reqResult;
				   }
				   name = name.substring(0, name.indexOf("·"));
				   reqResult.setData(name);
			   }
			   else {
				   reqResult.setCode(ResultCode.ERRORCODE.BANKCARD_IS_ERROR);
				   reqResult.setMsgInfo(ResultCode.ERRORINFO.BANKCARD_IS_ERROR);
				   return reqResult;
			   }
	      } 
		 else {  
	        	reqResult.setCode(ResultCode.ERRORCODE.BANKCARD_IS_ERROR);
				reqResult.setMsgInfo(ResultCode.ERRORINFO.BANKCARD_IS_ERROR);
				return reqResult;
	      }  
	      
		return reqResult;
	}

}
