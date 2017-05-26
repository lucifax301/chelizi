/**
 * Date: Mar 14, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * java Properties 配置封装器
 * 
 * @Deprecated 使用 GlobalConfigManager替代。
 * 
 * @author jiayi.wei
 * 
 */
@Deprecated
public class ConfigWrapper
{
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ConfigWrapper.class);
    private static Properties properties = null;

    /**
     * 初始化配置，使用ConfigWrapper前必须先初始化。
     * 
     * @param path
     *            配置文件路径
     * @return
     */
    public static boolean init(String path)
    {
        if (ConfigWrapper.isNullOrEmpty(path))
        {
            return false;
        }

        try
        {
            properties = new Properties();
            InputStream inputStream = new BufferedInputStream(
                    new FileInputStream(path));
            properties.load(inputStream);
            return true;
        }
        catch (FileNotFoundException e)
        {
            LOGGER.error("init config error.", e);
            return false;
        }
        catch (IOException e)
        {
            LOGGER.error("init config error.", e);
            return false;
        }
    }

    /**
     * 读取值
     * 
     * @param key
     * @return
     */
    public static String getValue(String key)
    {
        if (properties == null)
        {
            return null;
        }
        return properties.getProperty(key);
    }

    /**
     * 读取值
     * 
     * @param key
     * @return
     */
    public static int getInt(String key)
    {
        if (properties == null)
        {
            return Integer.MIN_VALUE;
        }
        String value = getValue(key);
        try
        {
            return Integer.parseInt(value.trim());
        }
        catch (Exception e)
        {
            LOGGER.error(e.toString());
            return Integer.MIN_VALUE;
        }
    }

    /**
     * 读取值
     * 
     * @param key
     * @return
     */
    public static long getLong(String key)
    {
        if (properties == null)
        {
            return Long.MIN_VALUE;
        }
        String value = getValue(key);
        try
        {
            return Long.parseLong(value.trim());
        }
        catch (Exception e)
        {
            LOGGER.error(e.toString());
            return Long.MIN_VALUE;
        }
    }

    /**
     * 字符串是否为空。
     * 
     * @param src
     * @return
     */
    public static boolean isNullOrEmpty(String src)
    {
        if (src == null || src.isEmpty())
        {
            return true;
        }

        return false;
    }

    public static Object setValue(String key, String value)
    {
        return properties.put(key, value);
    }

    public static boolean checkValue(String key)
    {
        return true;
        // String ips = Config.getValue("AdminIP");
        // if (ips != null && key != null && !key.isEmpty())
        // {
        // for (String s : ips.split("\\|"))
        // {
        // if (s.equals(key))
        // return true;
        // }
        // }
        // return false;
    }

}
