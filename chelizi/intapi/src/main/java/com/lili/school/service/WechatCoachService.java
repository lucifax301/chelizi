package com.lili.school.service;

import com.lili.common.vo.ReqResult;
import com.lili.school.vo.Template;

/**
 * 喱喱教练公众号相关操作
 * @author lzb
 *
 */
public interface WechatCoachService {

	/**
	 * 发送通知消息
	 * @param template
	 * @return
	 */
	boolean sendTemplateMsg(Template template);

	/**
	 * 获取永久二维码
	 * @param coachId
	 * @return
	 */
	String getWikiUrl(Long coachId);
	
	/**
	 * 获取token
	 * @return
	 */
	String getToken();
	
	/**
	 * 通过授权获取微信用户信息
	 */
	ReqResult getLiliCoachUserInfo(String code);

	
}
