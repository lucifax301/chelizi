/**
 * Date: Mar 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类
 * 
 * @author jiayi.wei
 */
public class StringUtil
{
    /**
     * 验证email地址是否合法
     * 
     * @param address
     * @return
     */
    public static boolean isValidEmailAddress(String address)
    {
        if (address == null)
        {
            return false;
        }
        Pattern p = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(address);
        return m.find();
    }

    /**
     * 判断字符串是否为空。
     * 
     * @param src
     * @return
     */
    public static boolean isNullOrEmpty(String src)
    {
        if (src == null || src.isEmpty() || src.trim().length() <= 0)
        {
            return true;
        }

        return false;
    }

    /**
     * 判断字符串是否为空。
     * 
     * @param src
     * @return
     */
    public static boolean isNotNullAndNotEmpty(String src)
    {
        if (src != null && !src.isEmpty() && src.trim().length() > 0)
        {
            return true;
        }

        return false;
    }

    /**
     * 判断字符串是否为空或者只包含空格。
     * 
     * @param src
     * @return
     */
    public static boolean isEmptyOrWhitespaceOnly(String src)
    {
        if (isNullOrEmpty(src))
        {
            return true;
        }

        if (src.trim().length() == 0)
        {
            return true;
        }

        return false;
    }

    /**
     * 獲取字符長度
     * 
     * @param str
     * @return
     */
    public static int getStrLength(String str)
    {
        int strLength = 0;
        String chinese = "[\u4e00-\u9fa5]";

        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < str.length(); i++)
        {
            // 获取一个字符
            String temp = str.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese))
            {
                // 中文字符长度为2
                strLength += 2;
            }
            else
            {
                // 其他字符长度为1
                strLength += 1;
            }
        }
        return strLength;
    }

    public static String tuofeng(String org, boolean first)
    {
        String tuofeng = org;
        List<String> all = new ArrayList<String>();
        char[] name = org.toCharArray();
        boolean need = true;
        for (int i = 0; i < name.length; i++)
        {
            if (need)
            {
                all.add(String.valueOf(name[i]));
            }
            if (name[i] == '_')
            {
                need = true;
            }
            else
            {
                need = false;
            }
        }
        if (all.size() > 0)
        {
            if (first)
            {
                tuofeng = tuofeng.replaceFirst(all.get(0), all.get(0).toUpperCase());
            }
            for (int i = 1; i < all.size(); i++)
            {
                tuofeng = tuofeng.replace("_" + all.get(i), all.get(i).toUpperCase());
            }
        }
        return tuofeng;
    }

    public static String lowFirst(String str)
    {
        String result = str;
        String first = result.substring(0, 1);
        return result.replace(first, first.toLowerCase());
    }
//    public static String getUuid32(){
//    	return UUID.randomUUID().toString().replaceAll("-", "");
//    }
    static AtomicInteger integer=new AtomicInteger(0);
    static java.text.SimpleDateFormat format=new java.text.SimpleDateFormat("yyyyMMddHHmmss");
    public static String getOrderId(){
    	if(integer.get()>100) integer.set(0);
    	String orderid=format.format(new Date())+System.currentTimeMillis()+integer.incrementAndGet();
    	return orderid;
    	//return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    public static String listToStr(List<String> list){
		String str="";
		for(int i=0;i<list.size();i++){
			if(i==0){
				str=(String)list.get(i);
			}else{
				str+=","+(String)list.get(i);
			}
		}
		return str;
	}
    
}
