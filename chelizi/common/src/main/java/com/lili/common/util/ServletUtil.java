/**
 * Date: 2013-7-2
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author jiayi.wei
 */
public class ServletUtil
{
    /** 在HttpServletRequest获取到的 unknown IP */
    private static final String UNKNOWN = "unknown";

    /**
     * 获取客户端IP
     * 
     * @param request
     * @return
     */
    public static String getRequestIP(HttpServletRequest request)
    {
        String ip = null;

        if (request == null)
        {
            return null;
        }

        ip = request.getHeader("x-real-ip");
        if (StringUtil.isNullOrEmpty(ip) == false
                && UNKNOWN.equalsIgnoreCase(ip) == false)
        {
            return ip;
        }

        ip = request.getHeader("x-forwarded-for");
        if (StringUtil.isNullOrEmpty(ip) == false
                && UNKNOWN.equalsIgnoreCase(ip) == false)
        {
            String[] ips = ip.split(",");
            if (ips.length > 1)
            {
                // 多级反向代理处理。取X-Forwarded-For中第一个非unknown的有效IP字符串。
                for (int i = 0; i < ips.length; i++)
                {
                    if (UNKNOWN.equalsIgnoreCase(ips[i]) == false)
                    {
                        ip = ips[i];
                        break;
                    }
                }
            }

            return ip.trim();
        }

        ip = request.getHeader("Proxy-Client-IP");
        if (StringUtil.isNullOrEmpty(ip) == false
                && UNKNOWN.equalsIgnoreCase(ip) == false)
        {
            return ip;
        }

        ip = request.getHeader("WL-Proxy-Client-IP");
        if (StringUtil.isNullOrEmpty(ip) == false
                && UNKNOWN.equalsIgnoreCase(ip) == false)
        {
            return ip;
        }

        // 必须在最后啊，不然有代理的时候获取到的不是真实的客户端IP。
        ip = request.getRemoteAddr();
        if (StringUtil.isNullOrEmpty(ip) == false
                && UNKNOWN.equalsIgnoreCase(ip) == false)
        {
            return ip;
        }

        return null;
    }
}
