package com.lili.common.util.annotation.interceptor;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.lili.common.util.RealTimeCache;
import com.lili.common.util.RedisRealTime;
import com.lili.common.util.StringUtil;
import com.lili.common.util.annotation.ComplexCache;



public class ComplexCacheInterceptor    {
    @Autowired
    RedisRealTime redisRealTime;
    
	public Object doAround(ProceedingJoinPoint joinPoint,ComplexCache complexCache) throws Throwable {
		if(complexCache!=null){
			//1.获取redis缓存
			String comKey=complexCache.comkey();
			boolean cache=true;
			if(StringUtil.isNotNullAndNotEmpty(complexCache.condition())) {
				ExpressionParser parser = new SpelExpressionParser();  
			 	EvaluationContext context = new StandardEvaluationContext();
			 	Object object[]=joinPoint.getArgs();
			 	for(int i=0;object!=null && i<object.length;i++) {
			 		context.setVariable("param"+(i+1), object[i]);
			 	}
			 	cache= parser.parseExpression(complexCache.condition()).getValue(context,Boolean.class);
			}
			if(comKey!=null && cache) {
				//追加前缀，获取具化key
				comKey=redisRealTime.getComplexKey(comKey, joinPoint.getArgs());
				@SuppressWarnings("rawtypes")
				RealTimeCache complex=redisRealTime.getComplexCache(comKey);
				//2.缓存存在，则直接返回
				if(complex!=null){
					return complex.getObject();
				//3.缓存不存在，查询数据库，并进行缓存
				} else {
					Object object=joinPoint.proceed();
					String[] paramKey=complexCache.paramkey();
					List<String> keyList=redisRealTime.getParamKey(paramKey, joinPoint.getArgs());
					redisRealTime.addComplex(keyList, comKey, object);
					return object;
				}
			} else if(!cache) {
				return joinPoint.proceed();
			} else {
				throw new Exception(joinPoint.getTarget().getClass()+"."+joinPoint.getSignature().getName()+" comkey can't be null!");
			}
		//没有注解，不需要缓存拦截
		} else {
			return joinPoint.proceed();
		}
	}
}
