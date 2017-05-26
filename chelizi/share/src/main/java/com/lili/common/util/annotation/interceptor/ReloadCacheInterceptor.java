package com.lili.common.util.annotation.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.share.service.BaseService;
import com.lili.common.util.RedisRealTime;



public class ReloadCacheInterceptor    {
    @Autowired
    RedisRealTime redisRealTime;
    
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Object object=joinPoint.proceed();
		//缓存失败必须抛异常
		redisRealTime.reaload(object, (BaseService)joinPoint.getThis());
		return object; 
	}
}
