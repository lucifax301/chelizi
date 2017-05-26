/**
 * Date: 2013-5-31
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.common.properties;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author yutao.chen
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConfigPropertyAnnotation
{
    public String key();

    public String description();

    public Class<?> type();

    public String defaultValue();
}
