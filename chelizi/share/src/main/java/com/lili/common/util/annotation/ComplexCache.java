package com.lili.common.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})   
@Retention(RetentionPolicy.RUNTIME)   
@Documented

public @interface ComplexCache {  
	/**复杂缓存本身的缓存key**/
	String   comkey() default "";
	/**根据参数生成的受影响对象时间值的key**/
	String[] paramkey() default {};
	/**影响对象的key和value**/
	String[] timekv()  default {};
	String condition() default "";
}
