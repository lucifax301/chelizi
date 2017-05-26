/**
 * Date: May 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.net.mina;

import java.io.IOException;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lili.common.util.StackMessagePrint;
import com.lili.net.CommonConst;
import com.lili.net.IClientConnection;
import com.lili.net.IClientPacketHandler;

/**
 * 与客户端交互的处理器
 * 
 * @author jiayi.wei
 */
public class ClientIoHandler extends IoHandlerAdapter
{
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ClientIoHandler.class);

    private IClientPacketHandler handler = null;

    @Override
    public void sessionCreated(IoSession session) throws Exception
    {
        //System.out.println("create: " + session);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception
    {
        IClientConnection conn = new MinaClientConnection(handler, session);
        session.setAttribute(CommonConst.CLIENT_CONNECTION, conn);
        //System.out.println("opened: " + session);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception
    {
        IClientConnection conn = (IClientConnection) session
                .getAttribute(CommonConst.CLIENT_CONNECTION);
        //System.out.println("disconncet: " + session);
        
        if (!conn.isServerClosed())
            conn.onDisconnect();
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
            throws Exception
    {
        IClientConnection conn = (IClientConnection) session
                .getAttribute(CommonConst.CLIENT_CONNECTION);
        //System.out.println("disconncet: " + session);
        
        if (!conn.isServerClosed())
        {
            conn.onIdle();
            conn.closeConnection(true);
            conn.setHolder(null);
        }
    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception
    {
        IClientConnection conn = (IClientConnection) session
                .getAttribute(CommonConst.CLIENT_CONNECTION);
        //System.out.println("received: " + session);
        conn.getPacketHandler().process(conn, message);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception
    {
        if (!(cause instanceof IOException))
        {
            LOGGER.error(StackMessagePrint.printErrorTrace(cause));
        }
        //IClientConnection conn = (IClientConnection) session
        //        .getAttribute(CommonConst.CLIENT_CONNECTION);
        // 在sessionClosed中已经触发，这里就不用触发了。
        // conn.onDisconnect();
    }

    /**
     * 设置IClientPacketHandler，创建mina组件的时候设定。
     * 
     * @param handler
     */
    public void setPacketHandler(IClientPacketHandler handler)
    {
        this.handler = handler;
    }
}
