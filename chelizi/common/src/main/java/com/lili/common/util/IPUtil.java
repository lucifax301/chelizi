/**
 * All rights reserved. This material is confidential and proprietary to 7ROAD
 */
package com.lili.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author cookie.hu
 * @date 2012-6-26
 * @version 将点分格式的IP和长整型互转
 */
public class IPUtil
{
    public static int getStringIpToInt(String ip)
    {
        InetAddress inetAddress;
        try
        {
            inetAddress = InetAddress.getByName(ip);
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
            return -1;
        }
        byte[] addrBytes;
        int addr;
        addrBytes = inetAddress.getAddress();
        addr = ((addrBytes[3] & 0xff) << 24) | ((addrBytes[2] & 0xff) << 16)
                | ((addrBytes[1] & 0xff) << 8) | (addrBytes[0] & 0xff);
        return addr;
    }

    public static String getIntIpToString(int ip)
    {
        StringBuffer sb = new StringBuffer();
        int b = (ip >> 0) & 0xff;
        sb.append(b + ".");
        b = (ip >> 8) & 0xff;
        sb.append(b + ".");
        b = (ip >> 16) & 0xff;
        sb.append(b + ".");
        b = (ip >> 24) & 0xff;
        sb.append(b);
        return sb.toString();
    }

    // 十进制ip地址转化为长整型（59.225.0.0-->1004601344L）
    public static long getStringIpToLong(String ip)
    {
        if (ip == null)
        {
            ip = "127.0.0.1";
        }
        String[] ips = ip.split("\\.");
        long num = 16777216L * Long.parseLong(ips[0]) + 65536L
                * Long.parseLong(ips[1]) + 256 * Long.parseLong(ips[2])
                + Long.parseLong(ips[3]);
        return num;
    }

    // 长整型转化为十进制ip地址（1004601344L-->59.225.0.0）
    public static String getLongIpToString(long ipLong)
    {
        long mask[] = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };
        long num = 0;
        StringBuffer ipInfo = new StringBuffer();
        for (int i = 0; i < 4; i++)
        {
            num = (ipLong & mask[i]) >> (i * 8);
            if (i > 0)
                ipInfo.insert(0, ".");
            ipInfo.insert(0, Long.toString(num, 10));
        }
        return ipInfo.toString();
    }

    // 根据IP和子网掩码获取IP网段
    public static String getIPStartAndEnd(String ip, String mask)
    {
        long s1 = getStringIpToLong(ip);
        long s2 = getStringIpToLong(mask);
        String erj = Long.toBinaryString(s2);
        long s3 = s1 & s2;
        long start = getStringIpToLong(getLongIpToString(s3));
        String wl = Long.toBinaryString(s3);
        if (wl.length() < 32)
        {
            int le = 32 - wl.length();
            for (int i = 0; i < le; i++)
            {
                wl = "0" + wl;
            }
        }
        String gbl = wl.substring(0, erj.indexOf("0"))
                + wl.substring(erj.indexOf("0"), wl.length()).replace("0", "1");
        long s4 = Long.parseLong(gbl, 2);
        long end = getStringIpToLong(getLongIpToString(s4));
        return start + "|" + end;
    }

    // 取本机IP,点分格式
    public static String getLocalHostIP()
    {
        try
        {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }

    // 取本机IP,长整型格式
    public static long getLongLocalHostIP()
    {
        try
        {
            InetAddress addr = InetAddress.getLocalHost();
            return getStringIpToLong(addr.getHostAddress());
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        return getStringIpToLong("127.0.0.1");
    }

    public static void main(String[] arg)
    {
        System.out.println(IPUtil.getIPStartAndEnd("59.224.192.0",
                "255.255.192.0"));
        System.out.println(IPUtil.getStringIpToLong("10.182.11.70"));
        System.out.println(IPUtil.getLongIpToString(1004584960L));
        System.out.println(IPUtil.getLongIpToString(1004601343L));
        System.out.println(getLocalHostIP());
        System.out.println(IPUtil.getStringIpToLong(getLocalHostIP()));
    }
}
