package com.lili.access.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.lili.cms.constant.HttpConstant;
import com.lili.cms.util.WebUtil;
import com.lili.user.model.User;

public class ExceptionHandler implements HandlerExceptionResolver {

	protected  final Logger access = Logger.getLogger(this.getClass());
	
	/**
	 * 处理Controller异常：记录异常信息,并返回信息到前端
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
    	HttpSession session = request.getSession();
    	User user = (User)session.getAttribute("user");
//    	User user = new User();
//    	user.setAccount("shengang");
//    	user.setUserName("shengang");

    	if(user != null){
		StringBuffer logStr = new StringBuffer();
		logStr.append("||| User account:").append(user.getAccount());
		logStr.append(",name:").append(user.getUserName());
		logStr.append(" ||| exception : ").append(ex.getMessage());
		logStr.append("||| request url :").append(request.getRequestURI());
		try {
			logStr.append(" , params : ").append(WebUtil.getAllParams(request));
			access.error(logStr.toString());
		} catch (UnsupportedEncodingException e) {
			logStr.append("||| request encoding : ").append(request.getCharacterEncoding());
			access.error(logStr.toString());
		}
    	}
		try {
			WebUtil.replyErrorMessage(response,HttpConstant.ERROR_CODE,"操作异常,多次异常请与管理员联系");
		} catch (IOException e) {
			access.error(" exception when send error message to WEB: " + e.getMessage());
		}
		return null;
	}
	
}
