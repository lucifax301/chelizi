/**
 * Date: 2014-1-7
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 概率控制<br>
 * 
 * @author jianlong.chen
 */
public class ProbabilityUtil
{
    /**
     * 从固定概率的元素列表中选取N个元素<br>
     * 
     * @param sources
     * @param N
     *            N小于或等于 source.size()
     * @return
     */
    public static <T extends IProbabilityStack> List<T> selectN(
            List<T> sources, int N)
    {
        List<T> copy = new ArrayList<T>();
        copy.addAll(sources);// 先拷贝, 不破坏原始数据
        List<T> result = new ArrayList<T>();

        for (int i = 0; i < N; i++)
        {
            T t = selectSingle(copy);
            result.add(t);
            copy.remove(t);
        }

        return result;
    }

    /**
     * 从固定概率的元素列表中选取一个元素并返回<br>
     * 
     * @param sources
     *            元素列表
     * @return
     */
    public static <T extends IProbabilityStack> T selectSingle(List<T> sources)
    {
        int totalRandom = calcProbabilityStack(sources);
        int random = 0;
        double r = Math.random();
        random = (int) (Math.round(r * totalRandom));
        for (T t : sources)
        {
            try
            {
                if (random <= t.getProbabilityStack())
                {
                    return t;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 计算区间概率值<br>
     * e.g 比如 a b c真实概率分别为 20%, 30%, 50%, 则计算后为 [a:0-20],[b:20-50],[c:50-100]<br>
     * 即区间概率值为[a:20],[b:20+30=50],[c:20+30+50=100]<br>
     * 
     * @param entites
     * @return 所有的概率值之和
     */
    private static <T extends IProbabilityStack> int calcProbabilityStack(
            List<T> entites)
    {

        int probabilityStack = 0;
        for (IProbabilityStack entity : entites)
        {
            probabilityStack += entity.getProbability();
            entity.setProbabilityStack(probabilityStack);
        }
        return probabilityStack;
    }

}
