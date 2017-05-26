/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lili.common.util.annotation;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.lili.common.util.RedisRealTime;




public class ComplexCachePostProcessor implements BeanPostProcessor  {
	@Autowired
	private RedisRealTime redisRealTime;
	
	
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		Method[] method=bean.getClass().getDeclaredMethods();
		for(int i=0;method!=null &&i<method.length;i++){
			ComplexCache complex=method[i].getAnnotation(ComplexCache.class);
			if(complex!=null){
				String [] timekv=complex.timekv();
				for(int j=0;j<timekv.length;){
					redisRealTime.sadd(RedisRealTime.KEYKEYPREFIX+timekv[j], timekv[j+1]);
					j=j+2;
				}
			}
		}
		return bean;
	}

	
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}
}
