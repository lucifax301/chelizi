/**
 * Date: May 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 语言包组件
 * 
 * @author jiayi.wei
 * 
 */
public final class LanguageComponent implements IComponent
{
    /** 组件名称 */
    public static final String NAME = "LanguageComponent";

    private static final Logger LOGGER = LoggerFactory
            .getLogger(LanguageComponent.class);

    private static Map<String, String> cache = new ConcurrentHashMap<String, String>();

    /**
     * 加载语言包
     * 
     * @param path
     * @return
     */
    public boolean load(String path)
    {
        try
        {
            FileInputStream fis = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String lineContent;
            while ((lineContent = bufferedReader.readLine()) != null)
            {
                int index = lineContent.indexOf("=");
                if (index > -1)
                {
                    String key = lineContent.substring(0, index);
                    String value = lineContent.substring(index + 1);
                    cache.put(key, value);
                }
            }
            bufferedReader.close();
            isr.close();
            fis.close();

            LOGGER.info("Load {} language items.", cache.size());
            return true;
        }
        catch (FileNotFoundException e)
        {
            LOGGER.error("", e);
            return false;
        }
        catch (IOException e)
        {
            LOGGER.error("", e);
            return false;
        }
    }

    /**
     * 从语言包中获取对应的值。
     * 
     * @param key
     * @param paras
     * @return
     */
    public static String getResource(String key, Object... paras)
    {
        try
        {
            String msg = cache.get(key);
            if (msg == null)
            {
                LOGGER.error("Language package does not contain key: {}", key);
                return "";
            }
            return String.format(msg, paras);
        }
        catch (Exception e)
        {
            LOGGER.error(String.format("error key : %s", key), e);
            return "";
        }
    }

    public static String getContent(String content, Object... params)
    {
        String msg = content;
        if (null != params && params.length > 0)
        {
            for (int i = 0, max = params.length; i < max; i++)
            {
                Object param = params[i];
                if (null != param)
                {
                    msg = msg.replaceAll("\\{" + i + "\\}", param.toString());
                }
            }
        }
        return msg;
    }

    /**
     * 从语言包中获取对应的值
     * 
     * @param key
     * @return
     */
    public static String getResource(String key)
    {
        try
        {
            String msg = cache.get(key);
            if (msg == null)
            {
                LOGGER.error("Language package does not contain key: " + key,
                        new NullPointerException());
                return key;
            }

            return msg;
        }
        catch (Exception e)
        {
            LOGGER.error(e.toString());
            return key;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.component.IComponent#getName()
     */
    @Override
    public String getName()
    {
        return NAME;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.component.IComponent#initialize()
     */
    @Override
    public boolean initialize()
    {
        // do nothing.
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.component.IComponent#start()
     */
    @Override
    public boolean start()
    {/*
        return load(GlobalConfigManager.getInstance().getServerConfig()
                .getLanguagePath());*/
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.component.IComponent#stop()
     */
    @Override
    public void stop()
    {
        // do nothing.
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.component.IComponent#destroy()
     */
    @Override
    public void destroy()
    {
        cache.clear();
        cache = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.component.IComponent#reload()
     */
    @Override
    public void reload()
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.component.IComponent#getBeanName()
     */
    @Override
    public String[] getBeanName()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
