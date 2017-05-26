package com.lili.httpaccess.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.authcode.service.AuthcodeService;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;

@Controller
@RequestMapping("/v1/authcodes")
public class AuthcodeController {
	@Autowired
	private AuthcodeService authcodeService;

	private Logger log = Logger.getLogger(AuthcodeController.class);
	/**
	 * 校验是否是手机号 如果请求参数reqType为0，代表新用户注册流程，需要校验是否已注册，如已注册返回错误
	 * 如果请求参数reqType为1，代表老用户找回密码流程，需要校验是否已注册，如未注册返回错误
	 * 调用短信接口向用户发送验证码，并记录手机号与验证码对应关系
	 * 
	 * @param mobile
	 *            手机号
	 * @param userType
	 *            用户类型：1:coach; 2:student
	 * @param reqType
	 *            请求类型：0:新用户注册要获取验证码; 1:已注册用户找回密码要获取验证码
	 * @param timestamp
	 *            时间戳
	 * @param sign
	 *            签名字符串
	 * @return 
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public Object getAuthcode(@RequestParam String mobile,
			@RequestParam String userType, @RequestParam String reqType,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();

		try {
			r = authcodeService.getAuthcode(mobile, userType, reqType);
		} catch (Exception e) {
			log.error("controller: authcode get authcode failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}
	
	@RequestMapping(value = "/timestamp", method = RequestMethod.GET)
	@ResponseBody
	public Object getTime() {
		ReqResult r = new ReqResult();
		
		try {
			Object o =System.currentTimeMillis()/1000L;
			r.setCode(ResultCode.ERRORCODE.SUCCESS);
			r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
			r.setData(o);
		} catch (Exception e) {
			log.error("controller: authcode get timestamp failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 登录后获取验证码
	 * @param mobile
	 * @param userType
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/one", method = RequestMethod.GET)
	@ResponseBody
	public Object getAuthcodeOne(@RequestParam String mobile,
			@RequestParam String userType, @RequestParam String userId ,@RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = new ReqResult();

		try {
			r = authcodeService.getAuthcodeOne(mobile, userType, userId);
		} catch (Exception e) {
			log.error("controller: authcode get authcode failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}
	
	/**
	 * 判断验证码的正确性
	 * @param codeInput
	 * @param mobile
	 * @param userId
	 * @param userType
	 * @return
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	@ResponseBody
	public Object isCodeExist(@RequestParam String codeInput,
			@RequestParam String mobile, @RequestParam String userId,
			@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign){
		ReqResult r = new ReqResult();
		
		try {
			r = authcodeService.isCodeExist(codeInput, mobile, userId, userType);
		} catch (Exception e) {
			log.error("controller: isCodeExist get authcode failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 消息通知统计点击量
	 */
	@RequestMapping(value = "/noticeAddClickNum", method = RequestMethod.GET)
	@ResponseBody
	public Object noticeAddClickNum(@RequestParam String noticeId){
		ReqResult r = new ReqResult();
		
		try {
			r = authcodeService.noticeAddClickNum(noticeId);
		} catch (Exception e) {
			log.error("controller: noticeAddClickNum failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	/**
	 * 通过id获取消息通知
	 */
	@RequestMapping(value = "/getNoticeById", method = RequestMethod.GET)
	@ResponseBody
	public Object getNoticeById(@RequestParam String noticeId){
		ReqResult r = new ReqResult();
		
		try {
			r = authcodeService.getNoticeById(noticeId);
		} catch (Exception e) {
			log.error("controller: getNoticeById failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	
	// ---------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------
	// 融云通讯IM
	@RequestMapping(value = "/rong/token", method = RequestMethod.GET)
	@ResponseBody
	public Object getRongToken(@RequestParam String userId, @RequestParam String userType ,
			@RequestParam String timestamp,	@RequestParam String sign){
		ReqResult r = new ReqResult();
		try {
			r = authcodeService.getRongToken(userId,userType);
		} catch (Exception e) {
			log.error("controller: getRongToken failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	
	
	
	
}




















