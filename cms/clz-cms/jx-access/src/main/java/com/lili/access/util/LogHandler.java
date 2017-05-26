package com.lili.access.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

import com.lili.cms.util.WebUtil;
import com.lili.user.model.User;

public class LogHandler {

	protected  final Logger access = Logger.getLogger(this.getClass());
	

	public void logArgs(JoinPoint jp)
	{
		Object[] args = jp.getArgs();
		HttpServletRequest request = (HttpServletRequest)args[0];
//		HttpServletResponse response = (HttpServletResponse)args[1];
//    	HttpSession session = request.getSession();
//    	User user = (User)session.getAttribute("user");

    	User user = new User();
    	user.setAccount("shengang");
    	user.setUserName("shengang");
    	
		StringBuffer logStr = new StringBuffer();
		logStr.append("||| User account:").append(user.getAccount());
		logStr.append(",user name:").append(user.getUserName());
		logStr.append("|||request url :").append(request.getRequestURI());
		logStr.append(",args :").append(WebUtil.getArgsByArr(jp.getArgs()));
		access.info(logStr.toString()); 
		
	}
	
}
