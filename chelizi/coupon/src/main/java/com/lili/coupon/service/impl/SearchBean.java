/**
 * Date: 2013-7-20
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.coupon.service.impl;

import java.util.HashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lili.common.util.ClassUtil;
import com.lili.coupon.condition.AbstractCCondition;


/**
 * 
 * @author Cookie.hu
 */
public class SearchBean
{
    private static Logger LOGGER = LoggerFactory.getLogger(SearchBean.class);

    public static HashMap<Byte, AbstractCCondition> getAllBeanClasses()
    {
        Package pack = Package.getPackage("com.lili.coupon.condition");
        Set<Class<?>> allClasses = ClassUtil.getClasses(pack);
        // handle的class，有些类有参数可能需要实时创建
        HashMap<Byte, AbstractCCondition> handlesClass = new HashMap<Byte, AbstractCCondition>();
        for (Class<?> clazz : allClasses)
        {
            try
            {
                ICondition condition = clazz.getAnnotation(ICondition.class);
                if (condition != null)
                {
                    handlesClass.put((byte) condition.type(), (AbstractCCondition) clazz.newInstance());
                }
            }
            catch (Exception e)
            {
                LOGGER.error(
                        "load command fail, command name : " + clazz.getName(),
                        e);
                e.printStackTrace();
            }
        }

        LOGGER.info("conditionCache size : " + handlesClass.size());
        return handlesClass;
    }
}
