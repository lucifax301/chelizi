package com.lili.common.util;

import java.util.Random;

public class ThreadSafeRandom
{

    private Random random = new Random();

    public int next()
    {
        synchronized (this)
        {
            return random.nextInt();
        }
    }

    public int next(int maxValue)
    {
        synchronized (this)
        {
            return random.nextInt(maxValue);
        }
    }

    public static void main(String[] args)
    {
        ThreadSafeRandom ts = new ThreadSafeRandom();
        for (int i = 0; i < 100; i++)
        {
            System.out.print(ts.next(1) + ",");
        }
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
    public int next(int minValue, int maxValue)
    {
        synchronized (this)
        {
            if (minValue < maxValue)
            {
                return random.nextInt(maxValue - minValue) + minValue;
            }
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
    public boolean inRandom(int value, int maxValue)
    {
        synchronized (this)
        {
            int ran = random.nextInt(maxValue);
            return ran <= value;
        }
    }
}
