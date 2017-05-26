/**
 * All rights reserved. This material is confidential and proprietary to 7ROAD
 */
package com.lili.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dream
 * @date 2011-5-5
 * @version
 * 脏字符检测
 */
public class DirtyCharUtil
{
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DirtyCharUtil.class.getName());
    private static List<String> ContentList = new ArrayList<String>();
    private static String[] SQLKEYWORD = ";|and|1=1|exec|insert|select|delete|update|like|count|chr|mid|master|or|truncate|char|declare|join"
            .split("\\|");

    public static void illegalcharacterInit(String path)
    {
        File folder = new File(path);
        File[] fileList = folder.listFiles();
        for (File f : fileList)
        {
            if (f.isFile())
                ContentList = FileOperate.readLines(f.getPath(), "UTF-8");
            else
            {
                LOGGER.error("DirtyCharUtil:illegalcharacterInit");
            }
        }
    }

    public static boolean checkIllegalChar(String strRegName)
    {
        boolean flag = false;

        if (!strRegName.isEmpty())
        {
            flag = checkChar(strRegName);
        }

        return flag;
    }

    private static boolean checkChar(String strRegName)
    {
        boolean flag = false;
        for (String strLine : ContentList)
        {
            // 校验非法字符
            if (!strLine.startsWith("GM"))
            {
                for (char charl : strLine.toCharArray())
                {
                    if (strRegName.contains(String.valueOf(charl)))
                    {
                        flag = true;
                        break;
                    }
                }
                if (flag)
                {
                    break;
                }
            }
            else
            {
                // 校验非法词组
                String[] keyword = strLine.split("\\|");
                for (String key : keyword)
                {
                    if (strRegName.contains(key) && !key.isEmpty() && key != "")
                    {
                        flag = true;
                        break;
                    }
                }
                if (flag)
                {
                    break;
                }
            }
        }

        return flag;
    }

    public static String convertSql(String inputString)
    {
        // if (!inputString.isEmpty())
        if (!StringUtil.isNullOrEmpty(inputString))
        {
            inputString = inputString.trim().toLowerCase();
            inputString = inputString.replace("'", "''");
            inputString = inputString.replace(";--", "");
            inputString = inputString.replace("=", "");
            inputString = inputString.replace(" or", "");
            inputString = inputString.replace(" or ", "");
            inputString = inputString.replace(" and", "");
            inputString = inputString.replace("and ", "");
            if (!sqlChar(inputString))
            {
                inputString = "";
            }
        }
        return inputString;
    }

    public static boolean sqlChar(String v)
    {
        if (!v.trim().equals(""))
        {
            for (String a : SQLKEYWORD)
            {
                if (v.indexOf(a + " ") > -1 || v.indexOf(" " + a) > -1)
                {
                    return false;
                }
            }
        }
        return true;
    }
}
