package com.lili.share.service;

import com.lili.common.vo.ReqResult;

/**
 * 分享接口
 * 
 * @author lzb
 *
 */
public interface IChannelService {

	/**
	 * 增加一条share/channel记录
	 * @param share
	 * @return
	 */
	ReqResult addChannelInfo(String userId, String recevieName, String sendType, String suid, String receviePhone, String userType);
	
	/**
	 * 增加一条大客户记录
	 * @param share
	 * @return
	 */
	ReqResult addBigCustomer(String name, String phone, String companyName);


}
