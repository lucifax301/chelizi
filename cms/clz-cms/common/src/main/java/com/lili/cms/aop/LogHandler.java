package com.lili.cms.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import com.lili.cms.util.DateUtil;
import com.lili.cms.util.WebUtil;

public class LogHandler {


	protected final Logger access = Logger.getLogger(this.getClass());
	
	/**
	 * 记录下方法的参数
	 * @param jp
	 */
	public void argsLog(JoinPoint jp){
		StringBuffer logStr = new StringBuffer();
		logStr.append("|||Method :").append(jp.getTarget().getClass() + "." + jp.getSignature().getName());
		logStr.append(",args:").append(WebUtil.getArgsByArr(jp.getArgs()));
		access.info(logStr.toString());
	}
	
	/**
	 * 记录下每次数据库访问的时长,切入Manager层
	 * @param jp
	 */
	public Object execTimeLog(ProceedingJoinPoint jp){
		Object rvt = null;
		try {
			long startTime = DateUtil.getCurrentMillis();
			Object[] args = jp.getArgs();
			rvt = jp.proceed(args);
			long endTime = DateUtil.getCurrentMillis();
			
			StringBuffer logStr = new StringBuffer();
			logStr.append("|||exec ").append(jp.getTarget().getClass() + "." + jp.getSignature().getName());
			logStr.append(" cost ").append((endTime - startTime)+ " ms");
			logStr.append("|||return value:").append(rvt==null?"":rvt.toString());
			
			access.info(logStr.toString());
		} catch (Throwable e) {
			access.error("|||exception when log db-exec-time: " + e.getMessage());
		}

		return rvt;
	}
	
}
