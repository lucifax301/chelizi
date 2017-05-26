package com.lili.common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置文件管理模块
 * 
 */
public class Config
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class
            .getName());

    private static Properties properties = null;

    private static ReadWriteLock lock = new ReentrantReadWriteLock(true);

    private static String Path;

    public static boolean initConfig(String path)
    {
        if (path == null || path.equals(""))
        {
            return false;
        }

        Path = path;

        if (properties == null)
        {
            return loadProperties(path);
        }

        return true;
    }

    public static boolean refreshProperties()
    {
        if (Path == null || Path.isEmpty())
            return false;

        try
        {
            Properties temp = new Properties();
            InputStream inputStream = new BufferedInputStream(
                    new FileInputStream(Path));
            temp.load(inputStream);

            lock.writeLock().lock();
            properties = temp;
        }
        catch (FileNotFoundException e)
        {
            LOGGER.error("Config:refreshProperties", e);
            return false;
        }
        catch (IOException e)
        {
            LOGGER.error("Config:refreshProperties", e);
            return false;
        }
        finally
        {
            lock.writeLock().unlock();
        }
        return true;
    }

    private static boolean loadProperties(String path)
    {
        try
        {
            lock.writeLock().lock();
            properties = new Properties();
            InputStream inputStream = new BufferedInputStream(
                    new FileInputStream(path));
            properties.load(inputStream);
            return true;
        }
        catch (FileNotFoundException e)
        {
            LOGGER.error("Config:loadProperties", e);
            return false;
        }
        catch (IOException e)
        {
            LOGGER.error("Config:loadProperties", e);
            return false;
        }
        finally
        {
            lock.writeLock().unlock();
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
        String value = properties.getProperty(key);
        if (null == value)
            return null;
        try
        {
            return new String(value.getBytes("ISO8859-1"), "utf-8");
            //return new String(value.getBytes("utf-8"), "utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            LOGGER.error("Config:getValue", e);
            return null;
        }
    }

    /**
     * 设置属性值
     * 
     * @param key
     *            属性字段
     * @param value
     *            属性值
     * @return
     */
    public static Object setValue(String key, String value)
    {
        return properties.put(key, value);
    }

    /**
     * 检查值是否合法
     * 
     * @param key
     * @return
     */
    public static boolean checkValue(String key)
    {
        String ips = Config.getValue("AdminIP");
        if (ips != null && key != null && !key.isEmpty())
        {
            for (String s : ips.split("\\|"))
            {
                if (s.equals(key))
                    return true;
            }
        }
        return false;
    }
}
