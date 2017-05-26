/**
 * Date: 2013-10-22
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.net;

/**
 * 
 * @author Cookie.hu
 */
public class TestPacket
{
    /**
     * 计算校验和
     * 
     * @param data
     *            完整的消息数据，包括包头和包体，计算将从第7个字节开始。
     * @return
     */
    private static short calcChecksum(byte[] data)
    {
        int val = 0x77;
        int i = 6;
        int size = data.length;
        while (i < size)
        {
            val += (data[i++] & 0xFF);
        }
        return (short) (val & 0x7F7F);
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        byte[] a = { 0x71, (byte) 0xAB, 0x00, 0x1a, 0x06, 0x4c, 0x41, 0x00,
                0x08, 0x01, 0x10, (byte) 0xe0, (byte) 0xb6, (byte) 0xe1, 0x2c,
                (byte) 0xc3, 0x6a, 0x36, (byte) 0xd2, 0x6c, 0x0c, (byte) 0xa0,
                (byte) 0xcf, 0x7b, 0x30, (byte) 0xbd };

        System.out.println(calcChecksum(a));

    }
}
