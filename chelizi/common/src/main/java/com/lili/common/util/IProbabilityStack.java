/**
 * Date: 2014-1-7
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.common.util;

/**
 * 累计概率<br>
 * 
 * @author jianlong.chen
 */
public interface IProbabilityStack
{
    /**
     * 获取默认概率<br>
     * 
     * @return
     */
    int getProbability();

    /**
     * 获取累积计算后的区间概率<br>
     * 
     * @return
     */
    int getProbabilityStack();

    /**
     * 计算累积区间概率<br>
     * 
     * @param probabilityStack
     */
    void setProbabilityStack(int probabilityStack);
}
