/**
 * Date: 2014-1-4
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.common.util;

/**
 * 压力测试打印输出<br>
 * 
 * @author jianlong.chen
 */
public class PressPrint
{

    public static final void print(String content, Object... args)
    {
        System.out.printf(content + "\n", args);
    }
}
