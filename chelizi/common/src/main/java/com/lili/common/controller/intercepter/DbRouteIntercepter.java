package com.lili.common.controller.intercepter;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lili.common.util.ThreadTruck;

public class DbRouteIntercepter {

	private static Logger logger = Logger.getLogger(DbRouteIntercepter.class);
	
	public void controllerAspect() {
	}

	
	public void before(JoinPoint joinPoint) {
		//System.out.println("dblink已经记录下操作日志@Before 方法执行前");
		logger.debug("已经记录下操作日志@Before 方法执行前");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		//HttpSession session = request.getSession();
		
		String kc = request.getHeader("kc");
		logger.info("##########kc value:"+kc);
		
		if(kc!=null){
			ThreadTruck.put("route_db", kc);
		}
	}
	
	//@Around("controllerAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
    	logger.debug("已经记录下操作日志@Around 方法执行前");
		//System.out.println("dblink已经记录下操作日志@Around 方法执行前");
        return pjp.proceed();
       
    }

    //@After("controllerAspect()")
    public void after() {
    	logger.debug("已经记录下操作日志@After 方法执行后");
    	ThreadTruck.remove("route_db");
        //System.out.println("dblink已经记录下操作日志@After 方法执行后");
    }
}
