package com.lili.authcode.service;

import java.util.Map;

import com.lili.common.vo.ReqResult;

public interface AuthcodeService {

	/**
	 * 校验是否是手机号<br/>
	 * 如果请求参数reqType为1，代表新用户注册流程，需要校验是否已注册，如已注册返回错误<br/>
	 * 如果请求参数reqType为2，代表老用户找回密码流程，需要校验是否已注册，如未注册返回错误<br/>
	 * 调用短信接口向用户发送验证码，并记录手机号与验证码对应关系
	 * 
	 * @param mobile
	 *            手机号
	 * @param userType
	 *            用户类型：1:coach; 2:student
	 * @param reqType
	 *            请求类型：1:新用户注册要获取验证码; 2:已注册用户找回密码要获取验证码
	 *
	 * @return 0：已发验证码；1：手机号码错误；2：用户已注册/未注册
	 */
	public ReqResult getAuthcode(String mobile, String userType, String reqType) throws Exception;

	public ReqResult getAuthcodeOne(String mobile, String userType, String userId) throws Exception;

	public ReqResult isCodeExist(String code, String mobile, String userId,
			String userType) throws Exception;
	
	/**
	 * 短信接口，需要根据类型，选择不同的文案发送
	 * @param type	短信类型，根据不同短信从1开始重新编号，定义在ReqConstants.SHORT_MESSAGE_TYPE_*
	 * @param msgs	如果模板中有变量
	 * @return
	 * @throws Exception
	 */
	public ReqResult sendMsg(int type,String mobile,Map<String,String> msgs) throws Exception;
	
	/**
	 * 直接通过模板id来发送短信
	 * @param msgId		短信模板id
	 * @param mobile	多个手机号用逗号隔开
	 * @param msgs		如果模板中有变量，则按照顺序添加到map中，如（1，上课时间）（2，上课地点）//数据标号从1开始，与模板对应
	 * @return
	 * @throws Exception
	 */
	public ReqResult sendMsgById(String msgId,String mobile,Map<Integer,String> msgs) throws Exception;
	
	/**
	 * 获取短信模块
	 * @param prog	学员所属阶段
	 * @param state	学员所属状态
	 * @return
	 */
	public String getMsgTemplate(Integer prog, String state);
	
	public ReqResult noticeAddClickNum(String noticeId);
	
	public ReqResult getNoticeById(String noticeId);

	public ReqResult getRongToken(String userId, String userType);
	
	
	
	
	
}
