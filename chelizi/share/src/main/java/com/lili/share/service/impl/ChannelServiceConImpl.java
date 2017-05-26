package com.lili.share.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.constant.MoneyChange;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.coupon.service.CouponService;
import com.lili.pay.manager.MoneyManager;
import com.lili.share.dao.mapper.BigCustomerMapper;
import com.lili.share.dao.mapper.ChannelMapper;
import com.lili.share.dao.mapper.ShareMapper;
import com.lili.share.dao.mapper.ShareUserMapper;
import com.lili.share.dao.po.BigCustomer;
import com.lili.share.dao.po.ChannelPo;
import com.lili.share.dao.po.SharePo;
import com.lili.share.dao.po.ShareUserPo;
import com.lili.share.service.IChannelService;
import com.lili.student.dto.Student;
import com.lili.student.dto.StudentAccount;
import com.lili.student.manager.StudentManager;

public class ChannelServiceConImpl implements IChannelService {
	 Logger logger = LoggerFactory.getLogger(ChannelServiceConImpl.class);
	 
    @Autowired
    private ShareMapper shareMapper;
    
    @Autowired
    private ShareUserMapper shareUserMapper;
    
    @Autowired
    private ChannelMapper channelMapper;
    
    @Autowired
    private BigCustomerMapper bigCustomerMapper;
    
	@Autowired
	private StudentManager studentManager;
	
    @Autowired
    private MoneyManager moneyManager;
    
    @Autowired
    CouponService couponService;
	
	private final String SUID = "eed8490442a611e696afd89d672a2800"; //学员推荐分享主键
	
	private final String COUPONTMPID = "68f6c9f2ca83492faa6a591cf24a3403";//100元报名券

	@Override
	public ReqResult addChannelInfo(String userId, String recevieName, String sendType, String suid, String receviePhone, String userType) {
		ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
        
        logger.info("******************** recevieName = " + recevieName +",sendType = " + sendType+ ",suid=" 
        		+ suid+",receviePhone=" + receviePhone + ",userType="+ userType);
		//校验APP请求内容
        if (sendType == null ||  "".equals(sendType)) {
        	logger.info("******************** sendType Is Null");
			reqResult.setCode(ResultCode.ERRORCODE.PARAMERROR);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
            return reqResult;
		}
        if (suid == null ||  "".equals(suid)) {
        	logger.info("******************** suid Is Null");
        	reqResult.setCode(ResultCode.ERRORCODE.PARAMERROR);
        	reqResult.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
        	return reqResult;
        }
        if (receviePhone == null ||  "".equals(receviePhone)) {
        	logger.info("******************** receviePhone Is Null");
        	reqResult.setCode(ResultCode.ERRORCODE.PARAMERROR);
        	reqResult.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
        	return reqResult;
        }
        int sentTypeInt = Integer.valueOf(sendType);
        
        
		if (sentTypeInt <= 100) {//写入channel
			logger.info("*********************************sentType < 100,Insert Share!");
		    if (userId == null ||  "".equals(userId)) {
		    	logger.info("******************** userId Is Null");
	        	reqResult.setCode(ResultCode.ERRORCODE.PARAMERROR);
	        	reqResult.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
	        	return reqResult;
	        }
			//首先需要表判断该接收的手机号码是否存在(share表)
			SharePo isExitPhone = shareMapper.queryReceviePhoneIsExit(receviePhone);
			logger.info("*********************************queryReceviePhoneIsExit Success");
			if (isExitPhone == null) {
				if("4".equals(sendType) || "5".equals(sendType)) { //4-校园代理，5-西安等 userId为手机号
					if (userId.equals(receviePhone)) {
						reqResult.setCode(ResultCode.ERRORCODE.RECEIVE_EQUAL_SEND);
			        	reqResult.setMsgInfo(ResultCode.ERRORINFO.RECEIVE_EQUAL_SEND);
			        	return reqResult;
					}
				}
				if ("2".equals(sendType)) { //2-学员推荐 userId为学员ID
					Student sendStu = studentManager.getStudentInfo(Long.parseLong(userId));
					if (sendStu != null && receviePhone.equals(sendStu.getPhoneNum())) {
						reqResult.setCode(ResultCode.ERRORCODE.RECEIVE_EQUAL_SEND);
			        	reqResult.setMsgInfo(ResultCode.ERRORINFO.RECEIVE_EQUAL_SEND);
			        	return reqResult;
					}
				}
				
				Student student = studentManager.getStudentByPhoneNum(receviePhone);
				boolean register = false;
				if(student != null){
					register = ((student.getApplyexam()==2 && student.getApplystate()==100 )||student.getApplyexam()>2); //其次需要判断该手机号码是否已报名(学员表)
				}
				ShareUserPo  shareUserPo = shareUserMapper.queryById(suid); //从分享模板表获取优惠券模板
				logger.info("*********************************queryByPhone Success" + student);
				if (student != null && register) {//学员注册并且报名都不可以领取
					reqResult.setCode(ResultCode.ERRORCODE.IS_LILI_STUDENT); 
		            reqResult.setMsgInfo(ResultCode.ERRORINFO.IS_LILI_STUDENT);
		            return reqResult;
				}
				else { 
					SharePo sharePo = new SharePo();
					sharePo.setSuid(suid);
					sharePo.setSendType(Integer.valueOf(sendType));
					sharePo.setRecevieName(recevieName);
					sharePo.setReceviePhone(receviePhone);
					sharePo.setSendUser(Long.parseLong(userId));
					sharePo.setRecevieTemplate(shareUserPo.getRecevieTemplate());
					sharePo.setSendTotal(shareUserPo.getSendTotal());
					shareMapper.add(sharePo);//插入一条记录到share表
					
					//判断学员是否已注册，如果是已注册的学员，直接发钱，未注册的走监听，不在这里处理
					if (student != null) {
						//学员推荐注册后B获取100元礼金，可用于报名，课时等
						if(SUID.equals(suid)){ //分享主键为学员分享
							
							//送钱改为送报名券
			            	ReqResult r =  couponService.obtainCouponByPhone(receviePhone, COUPONTMPID);
			            	logger.info("******************************************* Send Coupon to Student Account,phone : " + receviePhone + ", Result : " + r.getMsgInfo());
			            	
							/*StudentAccount studentAccountIU = new StudentAccount();
							StudentAccount studentAccount = studentManager.getStudentMoney(student.getStudentId());
							if (studentAccount != null) {
								studentAccountIU.setMoney(studentAccount.getMoney() + 10000);
								studentAccountIU.setStudentId(student.getStudentId());
								studentManager.updateStudentAccount(studentAccountIU);
								logger.info("******************************************* Update 100 For Student Account,StudentId : " + student.getStudentId());
								moneyManager.addMoneyLog(student.getStudentId(), MoneyManager.UserType.STUDENT, "system", studentAccount.getMoney() + 10000, 10000,  MoneyChange.AWARD, true, true, "分享奖励", "", "喱喱钱包");
							}
							else {
								studentAccountIU.setMoney(10000);
								studentAccountIU.setStudentId(student.getStudentId());
								studentManager.insertStudentAccount(studentAccountIU);
								logger.info("******************************************* Add 100 For Student Account,StudentId : " + student.getStudentId());
								moneyManager.addMoneyLog(student.getStudentId(), MoneyManager.UserType.STUDENT, "system", 0, 10000,  MoneyChange.AWARD, true, true, "分享奖励", "", "喱喱钱包");
							}*/
						}
					}
				}
			}
			else {
				reqResult.setCode(ResultCode.ERRORCODE.HAS_REGIST_PHONE); 
	            reqResult.setMsgInfo(ResultCode.ERRORINFO.HAS_REGIST_PHONE);
	            return reqResult;
			}
			
		}
		else {//写入channel表
			logger.info("*********************************sentType > 100,Insert Channel!");
			 if (recevieName == null ||  "".equals(recevieName)) {
				logger.info("******************** recevieName Is Null");
	        	reqResult.setCode(ResultCode.ERRORCODE.PARAMERROR);
	        	reqResult.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
	        	return reqResult;
	        }
			//首先需要表判断该接收的手机号码是否存在(channel表)
			ChannelPo isExitChannelPhone = channelMapper.queryReceviePhoneIsExit(receviePhone);
			logger.info("********************************ChannelPo *queryReceviePhoneIsExit Success");
			if (isExitChannelPhone == null) {
				ChannelPo channelPo = new ChannelPo();
				channelPo.setSuid(suid);
				channelPo.setSendType(Integer.valueOf(sendType));
				channelPo.setRecevieName(recevieName);
				channelPo.setReceviePhone(receviePhone);
				channelMapper.add(channelPo) ;//插入一条记录到channel表
			}
			else {
				reqResult.setCode(ResultCode.ERRORCODE.HAS_REGIST_PHONE); 
	            reqResult.setMsgInfo(ResultCode.ERRORINFO.HAS_REGIST_PHONE);
	            return reqResult;
			}
		}
		
		return reqResult;
	}

	@Override
	public ReqResult addBigCustomer(String name, String phone, String companyName) {
		ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
        
        logger.info("******************** name = " + name +",phone = " + phone+ ",companyName=" + companyName);
		//校验APP请求内容
        if (name == null ||  "".equals(name)) {
        	logger.info("******************** name Is Null");
			reqResult.setCode(ResultCode.ERRORCODE.PARAMERROR);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
            return reqResult;
		}
        if (phone == null ||  "".equals(phone)) {
        	logger.info("******************** phone Is Null");
        	reqResult.setCode(ResultCode.ERRORCODE.PARAMERROR);
        	reqResult.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
        	return reqResult;
        }
        if (companyName == null ||  "".equals(companyName)) {
        	logger.info("******************** companyName Is Null");
        	reqResult.setCode(ResultCode.ERRORCODE.PARAMERROR);
        	reqResult.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
        	return reqResult;
        }
        
		//首先需要表判断该手机号码是否存在
    	BigCustomer isExitPhone = bigCustomerMapper.selectByPrimaryKey(phone);
		if (isExitPhone == null) {
			BigCustomer bigCustomer = new BigCustomer();
			bigCustomer.setPhone(phone);
			bigCustomer.setName(name);
			bigCustomer.setCompanyName(companyName);
			bigCustomerMapper.insertSelective(bigCustomer);//插入一条记录
		}
		else {
			reqResult.setCode(ResultCode.ERRORCODE.HAS_REGIST_PHONE); 
            reqResult.setMsgInfo(ResultCode.ERRORINFO.HAS_REGIST_PHONE);
            return reqResult;
		}
		
		return reqResult;
	}
	
	
}
