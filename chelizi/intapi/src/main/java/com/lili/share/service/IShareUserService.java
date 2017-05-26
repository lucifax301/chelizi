package com.lili.share.service;

import com.lili.common.vo.ReqResult;

/**
 * 分享接口
 * 
 * @author lzb
 *
 */
public interface IShareUserService {

	/**
	 * 获取分享链接
	 * @param userId
	 * @param userType
	 * @return
	 */
	ReqResult getShareUser(String userId, String userType);

	/**
	 * 校园推广拉取链接
	 * @param suid
	 * @param receviePhone
	 * @return
	 */
	ReqResult getSchoolShareUser(String suid, String sendPhone);


}
