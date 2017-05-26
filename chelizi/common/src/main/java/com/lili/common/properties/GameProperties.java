/**
 * \ * Date: 2013-5-31
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.common.properties;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 参数配置
 * 
 * @author yutao.chen
 */
public class GameProperties
{
    private static final Logger LOGGER = LoggerFactory
            .getLogger(GameProperties.class.getName());
    @ConfigPropertyAnnotation(key = "multiPveCount", defaultValue = "3", description = "多人副本收益次数", type = Integer.class)
    public static int MULTI_PVE_COUNT;

    @ConfigPropertyAnnotation(key = "WorldGameId", defaultValue = "10057,10061,10062", description = "场景服上大地图ID(目前包括远古地宫，通天塔一层，通到塔二层)", type = String.class)
    public static String WORLDGAME_ID;
    
    static
    {
        refresh();
    }

    /**
     * 刷新所有属性字段配置
     */
    public static void refresh()
    {
        load(GameProperties.class);
    }

    // 从数据库加载所有属性字段配置，如未配置则使用其默认值
    private static void load(Class<?> type)
    {
        Field[] allFields = type.getFields();
       /* Map<String, ServerConfigBean> allConfigs = SystemBussiness
                .getServerConfigList();
        Map<String, SiteconfigBean> siteConfigs = new HashMap<>();
        try
        {
            List<SiteconfigBean> scbList = SystemBussiness
                    .getBeanList(SiteconfigBean.class);
            if (null != scbList)
            {
                for (SiteconfigBean scb : scbList)
                {
                    siteConfigs.put(scb.getSitename(), scb);
                }
            }
        }
        catch (PersistenceException e1)
        {
            e1.printStackTrace();
        }
        for (Field field : allFields)
        {
            ConfigPropertyAnnotation annotation = field
                    .getAnnotation(ConfigPropertyAnnotation.class);

            if (annotation != null)
            {
                ServerConfigBean configInfo = allConfigs.get(annotation.key());
                String valueString = annotation.defaultValue();

                if (configInfo != null)
                {
                    valueString = configInfo.getValue();
                }
                else
                {
                    SiteconfigBean scbBean = siteConfigs.get(annotation.key());
                    if (scbBean != null)
                    {
                        valueString = "" + scbBean.getTimeDealy();
                    }
                    else
                    {
                        LOGGER.error("Cannot find server property "
                                + annotation.key() + ",keep it default value!");
                    }
                }

                try
                {
                    field.set(null, parseValue(annotation.type(), valueString));
                }
                catch (Exception e)
                {
                    LOGGER.error(field.getName() + "fail to load GameProperty",
                            e);
                }
            }
        }*/
    }

    /**
     * 解析指定值到指定类型
     * 
     * @param fieldType
     *            字段类型
     * @param value
     *            以字符串表示的待转换值
     * @return 转换后的结果
     */
    private static Object parseValue(Class<?> fieldType, String value)
    {
        Object fieldValue = null;
        value = value.trim();

        try
        {
            if (fieldType == Integer.class)
            {
                fieldValue = Integer.parseInt(value);
            }
            else if (fieldType == boolean.class)
            {
                fieldValue = Boolean.parseBoolean(value);
            }
            else if (fieldType == Date.class)
            {
                fieldValue = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                        .parse(value);
            }
            else if (fieldType == Double.class)
            {
                fieldValue = Double.parseDouble(value);
            }
            else if (fieldType == Float.class)
            {
                fieldValue = Float.parseFloat(value);
            }
            else
            {
                fieldValue = value;
            }
            return fieldValue;
        }
        catch (Exception e)
        {
            LOGGER.error("Exception : ServerProperties Load: ", e);
            return null;
        }
    }

}
