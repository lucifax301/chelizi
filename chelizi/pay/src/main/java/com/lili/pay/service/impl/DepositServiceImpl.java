package com.lili.pay.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lili.coach.dto.Coach;
import com.lili.coach.dto.CoachAccount;
import com.lili.coach.manager.CoachManager;
import com.lili.common.constant.MoneyChange;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.DateUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.pay.config.MyRowBounds;
import com.lili.pay.dto.BankcardVerifyDto;
import com.lili.pay.dto.DepositDto;
import com.lili.pay.dto.SchDeposit;
import com.lili.pay.dto.SchoolAccount;
import com.lili.pay.dto.SchoolExtend;
import com.lili.pay.manager.MoneyManager;
import com.lili.pay.manager.MoneyManager.UserType;
import com.lili.pay.mapper.dao.BankcardVerifyDtoMapper;
import com.lili.pay.mapper.dao.DepositDtoMapper;
import com.lili.pay.mapper.dao.SchDepositMapper;
import com.lili.pay.mapper.dao.SchoolAccountMapper;
import com.lili.pay.mapper.dao.SchoolExtendMapper;
import com.lili.pay.service.IDepositService;
import com.lili.pay.vo.DepositVo;
import com.lili.student.dto.StudentAccount;
import com.lili.student.manager.StudentManager;

/**
 * 提现接口实现类
 * @author lzb
 *
 */
public class DepositServiceImpl implements IDepositService {
    Logger logger = LoggerFactory.getLogger(DepositServiceImpl.class);

    @Autowired
    private BankcardVerifyDtoMapper bankcardVerifyDtoMapper;

    @Autowired
    private DepositDtoMapper depositDtoMapper;

    @Autowired
    private CoachManager coachManager;

    @Autowired
    private StudentManager studentManager;

    @Autowired
    private SchoolExtendMapper schoolExtendMapper;

    @Autowired
    private SchoolAccountMapper schoolAccountMapper;

    @Autowired
    private SchDepositMapper schDepositMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MoneyManager moneyManager;


	@Override
	public ReqResult deposit(String pw, String money, String type, String bankCard, String userId, String userType,
			String timestamp, String sign) {
		logger.info("---------------------- Deposit Start! --------------------");
		ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);

		//校验APP请求内容
        reqResult = validParam(pw,  money,  type,  bankCard,  userId,  userType,	 timestamp);
        if (!ResultCode.ERRORCODE.SUCCESS.equals(reqResult.getResult().get("code"))){
        	return reqResult;
        }
        //缓存记录是否已存在该用户的提现记录
        if (redisUtil.isExist(REDISKEY.DEPOST_APPLY + userId, userId)) {
        	logger.info("---------------------- Deposit  Redis is Exit !DEPOSIT_IS_LIMIT! ");
            reqResult.setCode(ResultCode.ERRORCODE.DEPOSIT_IS_LIMIT);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.DEPOSIT_IS_LIMIT);
            return reqResult;
        }
        // 记录一个缓存到redis
        redisUtil.set(REDISKEY.DEPOST_APPLY + userId, userId, 120);

        Integer userIdV = Integer.valueOf(userId.trim());
        Integer userTypeV = Integer.valueOf(userType.trim());

		try {
			//是否已绑定银行卡
			BankcardVerifyDto isExitVerifyDto  = bankcardVerifyDtoMapper.queryIsExitBoundBankCard(userTypeV, userIdV, bankCard);
			logger.info("---------------------- Deposit QueryIsExitBoundBankCard Result:  " +isExitVerifyDto);
			if ( null == isExitVerifyDto) { // 用户绑定银行卡未审核通过
				reqResult.setCode(ResultCode.ERRORCODE.BANKCARD_IS_NOT_BOUND);
				reqResult.setMsgInfo(ResultCode.ERRORINFO.BANKCARD_IS_NOT_BOUND);
				//去掉标记
                redisUtil.delete(REDISKEY.DEPOST_APPLY + userId);
				return reqResult;
			}
			else {
				//提现次数查询
				String startTime = DateUtil.getStartDate() +" 00:00:00";
				String endTime =  DateUtil.getEndDate() +" 23:59:59";
				logger.info("----------------------- Query Left Deposit Count : startTime "+ startTime + ",endTime" +endTime + ", userId : "+ userId);
				int count = depositDtoMapper.queryLeftDepositCount(Long.valueOf(userId), userType, startTime, endTime);
				if(count < ReqConstants.COACH_WITHDRAW_COUNT) { //可以提现
					//预扣金额，记录提现信息，日志信息
					reqResult = handlerDeposit(pw, money, type, bankCard, userId, userType, timestamp, isExitVerifyDto);
					//提现成功，去掉标记
                    redisUtil.delete(REDISKEY.DEPOST_APPLY + userId);
				}
				else {
					reqResult.setCode(ResultCode.ERRORCODE.DEPOSIT_IS_LIMIT);
					reqResult.setMsgInfo(ResultCode.ERRORINFO.DEPOSIT_IS_LIMIT);
					return reqResult;
				}
			}
		}
		catch (Exception e) {
			logger.error("---------------------- Deposit Error! Error Message :" + e.getMessage());
			reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
            return reqResult;
		}

		return reqResult;
	}


	/**
	 * 预扣金额，记录提现信息，日志信息
	 * 事物回滚
	 * @param pw
	 * @param money
	 * @param type
	 * @param bankCard
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param isExitVerifyDto
	 */
	@Transactional
	private ReqResult handlerDeposit(String pw, String money, String type, String bankCard, String userId, String userType,
			String timestamp, BankcardVerifyDto isExitVerifyDto) {
		ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);

		try {
			logger.info("---------------------- HandlerDeposit Query Start Info! money: " +money + ", userId:" +userId
						+ ",bankCard : " + bankCard + ",userType: "+ userType);
			Integer moneyValue = Integer.valueOf(money);
			Long userIdValue = Long.valueOf(userId);
			Integer userTypeValue = Integer.valueOf(userType);
			String orderId = StringUtil.getOrderId();


			if ("2".equals(userType.trim())) {
				//按照最近的需求,学员不允许提现
				reqResult.setCode(ResultCode.ERRORCODE.DEPOSIT_IS_LIMIT);
				reqResult.setMsgInfo(ResultCode.ERRORINFO.DEPOSIT_IS_LIMIT);
				return reqResult;
			}

			if ("1".equals(userType.trim())) { //教练
				//查询教练余额表信息
				CoachAccount  coachAccountInfo = coachManager.getCoachMoney(userIdValue);
				logger.info("---------------------- Handler Deposit Query  CoachAccount Info :" +coachAccountInfo);
				if(!pw.equals(coachAccountInfo.getPasswd())){
					reqResult.setCode(ResultCode.ERRORCODE.PASSWORD_ERROR);
					reqResult.setMsgInfo(ResultCode.ERRORINFO.PASSWORD_ERROR);
					//去掉标记
	                redisUtil.delete(REDISKEY.DEPOST_APPLY + userId);
					return reqResult;
				}

				//判断金额是否充足
				if (coachAccountInfo.getMoney() >= moneyValue) {
					//预扣余额
					CoachAccount coachAccount = new CoachAccount();
					coachAccount.setMoney(coachAccountInfo.getMoney()  - moneyValue);
					coachAccount.setCoachId(userIdValue);
					coachManager.updateCoachAccount(coachAccount);
					logger.info("-------------------------Update CoachAccount ! userIdValue : " + userIdValue +",moneyValue : "+
									moneyValue + ",coachAccountInfo.getMoney() : " + coachAccountInfo.getMoney());


                    Coach coach = coachManager.getCoachInfo(userIdValue);

					addDepositRecord(orderId, bankCard, moneyValue, type, userIdValue, userTypeValue, isExitVerifyDto);

                    //日志信息记录
                    addMoneyLog(userIdValue, UserType.COACH, coachAccountInfo.getMoney() - moneyValue, moneyValue, MoneyChange.WITHDRAW, orderId, "喱喱钱包");
                }
				else { //账户余额不足
					reqResult.setCode(ResultCode.ERRORCODE.BALANCE_IS_INSUFFICIENT);
					reqResult.setMsgInfo(ResultCode.ERRORINFO.BALANCE_IS_INSUFFICIENT);
					//去掉标记
	                redisUtil.delete(REDISKEY.DEPOST_APPLY + userId);
					return reqResult;
				}
			}
			else if("2".equals(userType.trim())){ //学员
				//查询教练余额表信息
				StudentAccount  studentAccountInfo = studentManager.getStudentMoney(userIdValue);
				logger.info("---------------------- Handler Deposit Query  StudentAccount Info :" +studentAccountInfo);
				if(!pw.equals(studentAccountInfo.getPasswd())){
					reqResult.setCode(ResultCode.ERRORCODE.PASSWORD_ERROR);
					reqResult.setMsgInfo(ResultCode.ERRORINFO.PASSWORD_ERROR);
					//去掉标记
	                redisUtil.delete(REDISKEY.DEPOST_APPLY + userId);
					return reqResult;
				}

				//判断金额是否充足
				if (studentAccountInfo.getMoney() >= moneyValue){
					//预扣余额
					StudentAccount studentAccount = new StudentAccount();
					studentAccount.setMoney(studentAccountInfo.getMoney() - moneyValue);
					studentAccount.setStudentId(userIdValue);
					studentManager.updateStudentAccount(studentAccount);
					logger.info("-------------------------Update Student Account ! userIdValue : " + userIdValue +",moneyValue : "+
							moneyValue + ",studentAccountInfo.getMoney() : " + studentAccountInfo.getMoney());

                    //记录提现信息
					addDepositRecord(orderId, bankCard, moneyValue, type, userIdValue, userTypeValue, isExitVerifyDto);
                    addMoneyLog(userIdValue, MoneyManager.UserType.STUDENT, studentAccountInfo.getMoney() - moneyValue, moneyValue, MoneyChange.WITHDRAW, orderId, "喱喱钱包");
                }
				else {
					reqResult.setCode(ResultCode.ERRORCODE.BALANCE_IS_INSUFFICIENT);
					reqResult.setMsgInfo(ResultCode.ERRORINFO.BALANCE_IS_INSUFFICIENT);
					//去掉标记
	                redisUtil.delete(REDISKEY.DEPOST_APPLY + userId);
					return reqResult;
				}
			}
		}
		catch (NumberFormatException e) {
			logger.error("-----------------------Handler Deposit Error ! "  + e.getMessage());
			reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
            return reqResult;
		}

		return reqResult;
	}

	/**
	 * 校验APP请求内容
	 * @param pw
	 * @param money
	 * @param type
	 * @param bankCard
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @return
	 */
	private ReqResult validParam(String pw, String money, String type, String bankCard, String userId, String userType,
			String timestamp) {
		ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);

  		if (pw == null ||  "".equals(pw)) {
  			 reqResult.setCode(ResultCode.ERRORCODE.PASSWORD_EMPTY_ERROR);
             reqResult.setMsgInfo(ResultCode.ERRORINFO.PASSWORD_EMPTY_ERROR);
             return reqResult;
  		}
  		if (money == null ||  "".equals(money) ) {
  			reqResult.setCode(ResultCode.ERRORCODE.MONEY_IS_NULL);
  			reqResult.setMsgInfo(ResultCode.ERRORINFO.MONEY_IS_NULL);
  			return reqResult;
  		}
  		if(Integer.valueOf(money.trim()) < 1000 ) {
  			reqResult.setCode(ResultCode.ERRORCODE.MONEY_IS_LESS);
  			reqResult.setMsgInfo(ResultCode.ERRORINFO.MONEY_IS_LESS);
  			return reqResult;
  		}
  		if(Integer.valueOf(money.trim()) > 500000) {
  			reqResult.setCode(ResultCode.ERRORCODE.MONEY_IS_MORE);
  			reqResult.setMsgInfo(ResultCode.ERRORINFO.MONEY_IS_MORE);
  			return reqResult;
  		}
  		if (type == null ||  "".equals(type)) {
  			reqResult.setCode(ResultCode.ERRORCODE.CARDNAME_IS_NULL);
  			reqResult.setMsgInfo(ResultCode.ERRORINFO.CARDNAME_IS_NULL);
  			return reqResult;
  		}
  		if (bankCard == null ||  "".equals(bankCard)) {
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
  		else if("1".equals(userType)) { //20161031教练封号禁止提现操作
  			Coach coach = coachManager.getCoachInfo(Long.parseLong(userId));
  			if (coach != null && coach.getState() != 0) {
  				reqResult.setCode(ResultCode.ERRORCODE.ACCOUNT_IS_STOP);
  				reqResult.setMsgInfo(ResultCode.ERRORINFO.ACCOUNT_IS_STOP);
  				return reqResult;
  			}
  		}

		return reqResult;
	}


	/**
	 * 记录金额操作日志
	 * @param userId
	 * @param userType
	 * @param leftMoney
	 * @param price
	 * @param moneyChange
	 * @param orderId
	 */
    public void addMoneyLog(Long userId, UserType userType, int leftMoney, Integer price, MoneyChange moneyChange, String orderId, String tranObject) {
        moneyManager.addMoneyLog(userId, userType, "yinlian", leftMoney, -1 * price, moneyChange, false, true, "预扣提现金额", orderId, tranObject);
    }

	/**
     * 插入提现表
     * @param orderId
     * @param bankCard
     * @param moneyValue
     * @param typeValue
     * @param userIdValue
     * @param userTypeValue
     * @param isExitVerifyDto
     */
	private void addDepositRecord(String orderId, String bankCard, Integer moneyValue, String typeValue, Long userIdValue,
			Integer userTypeValue, BankcardVerifyDto isExitVerifyDto) {
		//记录提现信息
		DepositDto depositDto = new DepositDto();
		depositDto.setWaterid(DateUtil.getDateRandom()); //流水
		depositDto.setBankCard(bankCard);
		depositDto.setMoney(-1* moneyValue);
		depositDto.setType(typeValue);
		depositDto.setUserId(userIdValue);
		depositDto.setUserType(userTypeValue);
		depositDto.setCardName(isExitVerifyDto.getName());
		depositDto.setVerifyId(isExitVerifyDto.getId());
		depositDto.setOrderId(orderId);
		depositDto.setBankName(isExitVerifyDto.getBankName());
		depositDtoMapper.insertSelective(depositDto);
		logger.info("----------------------Add Deposit Record ! orderId: " + orderId + ",userIdValue: " + userIdValue +",userTypeValue : "
						+ userTypeValue + ",moneyValue: " + moneyValue + ",bankCard: "+ bankCard);
	}

	/**
	 * 查询提现记录
	 */
	@Override
	public ReqResult queryDepositList(String pageNo, String pageSize, String userId, String userType, String timestamp,
			String sign) {
		ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);

  		if (pageNo == null ||  "".equals(pageNo)) {
  			reqResult.setCode(ResultCode.ERRORCODE.PAGE_NO_IS_NULL);
  			reqResult.setMsgInfo(ResultCode.ERRORINFO.PAGE_NO_IS_NULL);
  			return reqResult;
  		}
  		if (pageSize == null ||  "".equals(pageSize)) {
  			reqResult.setCode(ResultCode.ERRORCODE.PAGE_SIZE_IS_NULL);
  			reqResult.setMsgInfo(ResultCode.ERRORINFO.PAGE_SIZE_IS_NULL);
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
  			MyRowBounds myRowBounds = new MyRowBounds(Integer.parseInt(pageNo),Integer.parseInt(pageSize));

  			DepositDto depositDto = new DepositDto();
			depositDto.setMyRowBounds(myRowBounds );
			depositDto.setUserId(Long.valueOf(userId));
			depositDto.setUserType(Integer.valueOf(userType));
	  		List<DepositDto>  depositList = depositDtoMapper.queryDepositList(depositDto);
	  		if(depositList.size() > 0) {
	  			List<DepositVo> depositVo = BeanCopy.copyList(depositList, DepositVo.class, BeanCopy.COPYNOTNULL);
	  			reqResult = ReqResult.getSuccess();
	  			reqResult.setData(depositVo);
	  			return reqResult;
	    	}
	    	else {
	    		reqResult.setCode(ResultCode.ERRORCODE.DEPOSIT_LIST_IS_EMPTY);
				reqResult.setMsgInfo(ResultCode.ERRORINFO.DEPOSIT_LIST_IS_EMPTY);
				return reqResult;
	    	}
        }
        catch (Exception e) {
        	logger.error("--------------------------Query Deposit List Error! " + e.getMessage());
            reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
            return reqResult;
        }
	}

	/**
	 * 判断剩余提现次数
	 */
	@Override
	public ReqResult leftDepositCount(String userId, String userType, String timestamp, String sign) {
		ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);

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
			String startTime = DateUtil.getStartDate() +" 00:00:00";
			String endTime =  DateUtil.getEndDate() +" 23:59:59";
			logger.info("----------------------- Query Left Deposit Count : startTime "+ startTime + ",endTime" +endTime + ", userId : "+ userId);
			int count = depositDtoMapper.queryLeftDepositCount(Long.valueOf(userId), userType, startTime, endTime);
			if(count < ReqConstants.COACH_WITHDRAW_COUNT){
				reqResult.setData(ReqConstants.COACH_WITHDRAW_COUNT- count);
				return reqResult;
			}
			else {
				reqResult.setCode(ResultCode.ERRORCODE.DEPOSIT_IS_LIMIT);
				reqResult.setMsgInfo(ResultCode.ERRORINFO.DEPOSIT_IS_LIMIT);
				return reqResult;
			}
		} catch (NumberFormatException e) {
			logger.error("--------------------------Left Deposit Count  Error! " + e.getMessage());
            reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
            return reqResult;
		}
	}


	/**
	 * 驾校提现
	 */
	@Override
	public ReqResult schDeposit(String passwd, String money, String schoolId, String timestamp, String sign) {
		ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);

		String keySchId = schoolId;
		SchDeposit schDepositBack  = new SchDeposit();
		try {
			 //缓存记录是否已存在该用户的提现记录,防止重复点击
	        if (redisUtil.isExist(REDISKEY.DEPOST_APPLY+".school." + keySchId, keySchId)) {
	        	logger.info("---------------------- Deposit  Redis is Exit !DEPOSIT_IS_LIMIT! ");
	            reqResult.setCode(ResultCode.ERRORCODE.DEPOSIT_IS_LIMIT);
	            reqResult.setMsgInfo(ResultCode.ERRORINFO.DEPOSIT_IS_LIMIT);
	            return reqResult;
	        }

	        // 记录一个缓存到redis
	        redisUtil.set(REDISKEY.DEPOST_APPLY +".school." + keySchId, keySchId, 120);

	        SchoolExtend schoolExtendInfo = new SchoolExtend();
			schoolExtendInfo.setSchoolId(Integer.valueOf(schoolId));
			schoolExtendInfo.setCheckStatus(2);
			schoolExtendInfo.setIsDel(2);
			SchoolExtend schoolExtend = schoolExtendMapper.queryExtendInfo(schoolExtendInfo);
			if (schoolExtend.getProtocolOpen() >= 5) { //密码是否错误5次以上
				redisUtil.delete(REDISKEY.DEPOST_APPLY +".school." + keySchId);
				reqResult.setCode(ResultCode.ERRORCODE.PW_ERROR_FIVE_OVER);
				reqResult.setMsgInfo(ResultCode.ERRORINFO.PW_ERROR_FIVE_OVER);
				return reqResult;
			}

			SchoolAccount schAccount = new SchoolAccount();
			schAccount.setSchoolId(Integer.valueOf(schoolId));
			schAccount.setPasswd(passwd);
			SchoolAccount schAccountRes = schoolAccountMapper.queryByPasswd(schAccount);//校验账户密码
			if (schAccountRes == null) {//账户密码错误
				schoolExtendInfo.setProtocolOpen(1);
				schoolExtendMapper.updatepPotocolOpen(schoolExtendInfo);
				redisUtil.delete(REDISKEY.DEPOST_APPLY +".school." + keySchId);
				reqResult.setCode(ResultCode.ERRORCODE.PASSWORD_ERROR);
				reqResult.setMsgInfo(ResultCode.ERRORINFO.PASSWORD_ERROR);
				return reqResult;
			}
			else {
				schoolExtendInfo.setProtocolOpen(0);//密码正确，解锁
				schoolExtendMapper.updatePotocolOpenInit(schoolExtendInfo);
			}

			Integer depMoney = Integer.valueOf(money) * 100;
			SchoolAccount depositSum = schoolAccountMapper.queryAccount(schAccount);
			if( depMoney < 50000 || depMoney > depositSum.getMoney()){//金额是否超出限制
				redisUtil.delete(REDISKEY.DEPOST_APPLY +".school." + keySchId);
				reqResult.setCode(ResultCode.ERRORCODE.MONEY_IS_ERROR);
	            reqResult.setMsgInfo(ResultCode.ERRORINFO.MONEY_IS_ERROR);
	            return reqResult;
			}

			//提现次数查询
			String startTime = DateUtil.getStartDate();
			String endTime =  DateUtil.getEndDate();
			SchDeposit schDeposit  = new SchDeposit();
			schDeposit.setSchoolId(Integer.valueOf(schoolId));
			schDeposit.setStartTime(startTime);
			schDeposit.setEndTime(endTime);
			int count = schDepositMapper.queryLeftDepositCount(schDeposit);
			if(count < 1) { //可以提现
				//预扣金额，记录提现信息，日志信息
				handlerDeposit(schoolId, schAccountRes, schoolExtend, depMoney);
				//提现成功，去掉标记
				redisUtil.delete(REDISKEY.DEPOST_APPLY +".school." + keySchId);
			}
			else {
				redisUtil.delete(REDISKEY.DEPOST_APPLY +".school." + keySchId);
				reqResult.setCode(ResultCode.ERRORCODE.DEPOSIT_IS_LIMIT);
				reqResult.setMsgInfo(ResultCode.ERRORINFO.DEPOSIT_IS_LIMIT);
				return reqResult;
			}

			schDepositBack.setMoney(depMoney);
			schDepositBack.setBankCard(schoolExtend.getPublicAccount());
			schDepositBack.setApplyTime(new Date());
			reqResult.setData(schDepositBack);

			return reqResult;

		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			redisUtil.delete(REDISKEY.DEPOST_APPLY +".school." + keySchId);
			reqResult.setCode(ResultCode.ERRORCODE.FAILED);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.FAILED);
			return reqResult;
		}
		catch (Exception e) {
			e.printStackTrace();
			redisUtil.delete(REDISKEY.DEPOST_APPLY +".school." + keySchId);
			reqResult.setCode(ResultCode.ERRORCODE.FAILED);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.FAILED);
			return reqResult;
		}
	}

	/**
	 * 提现功能
	 * @param schoolId
	 * @param schAccountRes
	 * @param schoolExtend
	 * @param depMoney
	 */
	@Transactional
	private void handlerDeposit(String schoolId, SchoolAccount schAccountRes, SchoolExtend schoolExtend, Integer depMoney) {
		try {
			String orderId = StringUtil.getOrderId();
			//预扣余额
			SchoolAccount schYKAccount = new SchoolAccount();
			schYKAccount.setMoney(schAccountRes.getMoney()  - depMoney);
			schYKAccount.setSchoolId(Integer.valueOf(schoolId));
			schoolAccountMapper.updateSchoolAccount(schYKAccount);

			//插入驾校提现表
			SchDeposit schDeposit = new SchDeposit();
			schDeposit.setWaterId(DateUtil.getDateRandom());
			schDeposit.setBankCard(schoolExtend.getPublicAccount());
			schDeposit.setSchoolId(Integer.valueOf(schoolId));
			schDeposit.setBankName(schoolExtend.getBankName());
			schDeposit.setCardName(schoolExtend.getMerName());
			schDeposit.setMoney(-1 * depMoney);
			schDeposit.setOrderId(orderId);
			schDepositMapper.insertSelective(schDeposit);

			//日志信息记录
            addMoneyLog(Long.valueOf(schoolId), MoneyManager.UserType.SCHOOL, schAccountRes.getMoney() - depMoney, -1 * depMoney, MoneyChange.WITHDRAW, orderId, "喱喱钱包");
        }
		catch (Exception e) {
			e.printStackTrace();
		}

	}


	@Override
	public List<DepositVo> queryHasDeposit(DepositVo depositVo) {
		try {
			DepositDto depositDto =BeanCopy.copyNotNull(depositVo, DepositDto.class);
			List<DepositDto>  depositList = depositDtoMapper.queryHasDeposit(depositDto);
			if(depositList.size() > 0) {
				List<DepositVo> depositVoList = BeanCopy.copyList(depositList, DepositVo.class, BeanCopy.COPYNOTNULL);
				return depositVoList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public void updateDepositList(List<DepositVo> depList, Integer status) {
		try {
			BeanCopy.setListField(depList, "checkStatus", status);
			depositDtoMapper.updateDepositList(depList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
