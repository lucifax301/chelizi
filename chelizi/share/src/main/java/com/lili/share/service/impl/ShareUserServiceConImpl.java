package com.lili.share.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.share.dao.mapper.ShareUserMapper;
import com.lili.share.dao.po.ShareUserPo;
import com.lili.share.service.IShareUserService;

public class ShareUserServiceConImpl implements IShareUserService {

	Logger logger = LoggerFactory.getLogger(ShareUserServiceConImpl.class);


	@Autowired
	private ShareUserMapper shareUserMapper;


	/**
	 * 获得链接对象，关键是suid和sendType及userId
	 */
	@Override
	public ReqResult getShareUser(String userId, String userType) {
		ReqResult reqResult = new ReqResult();
		reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
		reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
		
		logger.info("*********************getShareUser* userId=" + userId + ",userType=" + userType );
		if (userId == null || "".equals(userId)) {
			reqResult.setCode(ResultCode.ERRORCODE.USERID_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.USERID_IS_NULL);
			return reqResult;
		}
		if (userType == null || "".equals(userType)) {
			reqResult.setCode(ResultCode.ERRORCODE.USERTYPE_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.USERTYPE_IS_NULL);
			return reqResult;
		}
		
		Integer sendType = Integer.valueOf(userType);
		ShareUserPo shareUserVo = shareUserMapper.queryByUserId(sendType);
		if(shareUserVo == null){
			logger.info("**********************shareUserVo is Null! ");
			reqResult.setCode(ResultCode.ERRORCODE.PARAMERROR);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
            return reqResult;
		}
		StringBuffer shareUrl = new StringBuffer();
		shareUrl.append(shareUserVo.getShareUrl());
		shareUrl.append("?");
		shareUrl.append("suid=");
		shareUrl.append(shareUserVo.getSuid());
		shareUrl.append("&userType=");
		shareUrl.append(shareUserVo.getSendType());
		shareUrl.append("&sendUser=");
		shareUrl.append(userId);
		logger.info("**************************************** shareUrl = " + shareUrl);
		
		reqResult.setData("shareUrl",shareUrl);

		return reqResult;
	}


	/**
	 * 校园推广拉取链接
	 */
	@Override
	public ReqResult getSchoolShareUser(String suid, String sendPhone) {
		ReqResult reqResult = new ReqResult();
		reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
		reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
		
		logger.info("*********************getSchoolShareUser* suid=" + suid + ",sendPhone=" + sendPhone );
		if (suid == null || "".equals(suid)) {
			reqResult.setCode(ResultCode.ERRORCODE.USERID_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.USERID_IS_NULL);
			return reqResult;
		}
		if (sendPhone == null || "".equals(sendPhone)) {
			reqResult.setCode(ResultCode.ERRORCODE.USERTYPE_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.USERTYPE_IS_NULL);
			return reqResult;
		}
		
		StringBuffer shareUrl = new StringBuffer();
		//首先判断手机号是否用关联其他邀请码
		ShareUserPo isShare = shareUserMapper.queryByExitPhone(sendPhone);
		if(isShare != null){
			logger.info("**********************getSchoolShareUser sendPhone is Register! ");
			reqResult.setCode(ResultCode.ERRORCODE.MOBILE_EXIST);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.MOBILE_EXIST);
            return reqResult;
		}
		
		ShareUserPo shareUserVo = shareUserMapper.queryById(suid); //根据suid查询链接
		if (shareUserVo == null) {
			logger.info("**********************getSchoolShareUser suid is Null! ");
			reqResult.setCode(ResultCode.ERRORCODE.AUTHCODE_ERROR);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.AUTHCODE_ERROR);
            return reqResult;
		}
		else {
			String queryPhone = shareUserVo.getSendPhone(); //查询手机号
			if ("".equals(queryPhone) || queryPhone == null) {
				ShareUserPo shareUserPo = new ShareUserPo();
				shareUserPo.setSendPhone(sendPhone);
				shareUserPo.setCheckState(2);
				shareUserMapper.updateBySuid(shareUserPo, suid);//更新手机号、状态
				
				shareUrl.append(shareUserVo.getShareUrl());
				shareUrl.append("?");
				shareUrl.append("suid=");
				shareUrl.append(suid);
				shareUrl.append("&sendType=");
				shareUrl.append(shareUserVo.getSendType());
				shareUrl.append("&sendPhone=");
				shareUrl.append(sendPhone);
				logger.info("**************************************** shareUrl = " + shareUrl);
				reqResult.setData("shareUrl",shareUrl);
				
				return reqResult;
			}
			else {
				if (queryPhone.equals(sendPhone)) { //校验拉取手机号与表是否一致
					shareUrl.append(shareUserVo.getShareUrl());
					shareUrl.append("?");
					shareUrl.append("suid=");
					shareUrl.append(suid);
					shareUrl.append("&sendType=");
					shareUrl.append(shareUserVo.getSendType());
					shareUrl.append("&sendPhone=");
					shareUrl.append(sendPhone);
					logger.info("**************************************** shareUrl = " + shareUrl);
					reqResult.setData("shareUrl",shareUrl);
					
					return reqResult;
				}
				else {
					logger.info("**********************getSchoolShareUser Phone is Not Right! ");
					reqResult.setCode(ResultCode.ERRORCODE.MOBILE_NUMBER_ERROR);
		            reqResult.setMsgInfo(ResultCode.ERRORINFO.MOBILE_NUMBER_ERROR);
		            return reqResult;
				}
			}
		}
	}

}
