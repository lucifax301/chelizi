package com.lili.school.util;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

import com.lili.cms.util.WebUtil;

public class ExceptionHandler {


	protected static final Logger access = Logger.getLogger("ExceptionHandler");

	//定义一个普通方法作为增强处理方法
	public void exceptionLog(JoinPoint jp,Throwable ex)
	{
		access.error(" |||Method : " + jp.getTarget().getClass() + "." + jp.getSignature().getName() + " ,args : " + WebUtil.getArgsByArr(jp.getArgs()));
		access.error(" |||exception : " + ex.getMessage());
	}
	
	
}
