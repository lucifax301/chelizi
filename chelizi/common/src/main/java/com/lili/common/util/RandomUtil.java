/**
 * Date: 2013-9-27
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 随机数工具类（非线程安全）
 * 
 * @author yutao.chen
 */
public class RandomUtil
{
    private final static Random random = new Random();

    public static int next()
    {
        return random.nextInt();
    }

    public static int next(int maxValue)
    {
        return random.nextInt(maxValue);
    }

    /**
     * 随机区间值，如 min=1 maxValue=5 随机，其结果值不包括5
     * 
     * @param minValue
     *            开始值
     * @param maxValue
     *            结束值
     * @return
     */
    public static int next(int minValue, int maxValue)
    {
        if (minValue < maxValue)
        {
            return random.nextInt(maxValue - minValue) + minValue;
        }
        return minValue;
    }

    /**
     * 返回是否在随机范围内
     * 
     * @param value
     * @param maxValue
     * @return
     */
    public static boolean inRandom(int value, int maxValue)
    {
        int ran = random.nextInt(maxValue);
        return ran <= value;
    }
    
    /**
     * 返回是否在随机范围内
     * 
     * @param value
     * @param maxValue
     * @return
     */
    public static List<Integer> nextNoRepeat(int value, int maxValue,int count)
    {
        List<Integer> list = new ArrayList<Integer>();
        if(maxValue - value <= count)
            return list;
        while(list.size() < count)
        {
            int ran = random.nextInt(maxValue);
            if(list.contains(ran) == false)
                list.add(ran);
        }
        return list;
    }
}
