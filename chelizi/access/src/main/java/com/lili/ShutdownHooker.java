/**
 * Date: 2013-7-31
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili;

/**
 * 
 * @author yutao.chen
 */
public class ShutdownHooker extends Thread
{

    private LiliAccess LiliServer;

    public ShutdownHooker(LiliAccess server)
    {
        this.LiliServer = server;
    }

    /**
     * 退出回调，停止服务器
     */
    public void run()
    {
        if (LiliServer != null)
        {
            LiliServer.callBackStop();
        }
    }
}
