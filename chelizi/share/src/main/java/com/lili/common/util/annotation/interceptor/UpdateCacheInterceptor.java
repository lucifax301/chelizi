package com.lili.common.util.annotation.interceptor;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.share.service.BaseService;
import com.lili.common.util.RedisRealTime;



public class UpdateCacheInterceptor    {
	private Logger log=Logger.getLogger(UpdateCacheInterceptor.class);
    @Autowired
    private RedisRealTime redisRealTime;
    
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Object object=joinPoint.proceed();
		try {
			redisRealTime.add(joinPoint.getArgs()[0], (BaseService)joinPoint.getThis());
		//缓存失败不能抛异常
		} catch(Exception e) {
			log.error(joinPoint.getArgs()[0]+" cache Exception:"+e.getMessage(),e);
		}
		return object;
	}
}
