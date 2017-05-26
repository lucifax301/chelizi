/**
 * Date: May 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.net.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lili.common.util.StackMessagePrint;
import com.lili.net.CommonConst;
import com.lili.net.IServerConnector;

/**
 * 服务器连接器的处理器
 * 
 * @author jiayi.wei
 */
public class ServerConnectorIoHandler extends IoHandlerAdapter
{
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ServerConnectorIoHandler.class);

    @Override
    public void sessionCreated(IoSession session) throws Exception
    {
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception
    {
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception
    {
        IServerConnector conn = (IServerConnector) session
                .getAttribute(CommonConst.SERVER_CONNECTOR);
        conn.disconnect();
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
            throws Exception
    {
    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception
    {
        IServerConnector conn = (IServerConnector) session
                .getAttribute(CommonConst.SERVER_CONNECTOR);
        conn.getPacketHandler().process(conn, message);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception
    {
        LOGGER.error(StackMessagePrint.printErrorTrace(cause));
    }
}
