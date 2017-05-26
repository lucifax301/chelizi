/**
 * Date: 2015-4-3
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.common.util;

/**
 * 位操作<br>
 * 
 * @author jianlong.chen
 */
public class BitUtil
{

    /**
     * 获取指定位置的bit值<br>
     * 
     * @param source
     * @param pos
     * @return
     */
    public static byte getBit(int source, int pos)
    {
        return (byte) ((source >> pos) & 1);
    }

    /**
     * 检测指定位置是否为真(1)<br>
     * 
     * @param source
     * @param pos
     * @return
     */
    public static boolean isBitTrue(int source, int pos)
    {
        return getBit(source, pos) == 1;
    }

    /**
     * 设置指定位置为指定值<br>
     * 
     * @param source
     * @param pos
     * @param value
     * @return
     */
    public static int updateBit(int source, int pos, byte value)
    {
        int mask = 1 << pos;
        if (value > 0)
        {
            source |= mask;
        }
        else
        {
            source &= (~mask);
        }
        return source;
    }

    /**
     * 将指定位置取反<br>
     * 
     * @param source
     * @param pos
     * @return
     */
    public static int reverseBit(int source, int pos)
    {
        int mask = 1 << pos;
        return source ^ mask;
    }

    public static void main(String[] args)
    {
        System.out.println(reverseBit(10, 1));
        System.out.println(updateBit(10, 1, (byte) 0));
    }
}
