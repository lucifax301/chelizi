package com.lili.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented 
@Inherited
public @interface LiLiCache {

	/**
     * 缓存库 namespace
     * @return 
     */
    String namespace() default "";
    
    /**
     * 缓存key
     * @return 
     */
    String key() default "";
    
    String action() default "";
}
