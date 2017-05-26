/**
 * Date: May 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.component;


import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.lili.common.util.Config;
import com.lili.net.CommonMessageClientHandler;
import com.lili.net.mina.ClientIoHandler;
import com.lili.net.mina.CommonCodecFactory;

/**
 * mina组件默认实现
 * 
 * @author jiayi.wei
 */
public class DefaultMinaComponent extends AbstractMinaComponent
{
    @Override
    protected IoHandler getClientIoHandler()
    {
        ClientIoHandler ioHandler = new ClientIoHandler();
        ioHandler.setPacketHandler(new CommonMessageClientHandler());
        return ioHandler;
    }

    @Override
    protected void acceptorInit(IoAcceptor serverAcceptor)
    {
        SocketSessionConfig config = ((NioSocketAcceptor) serverAcceptor)
                .getSessionConfig();
        config.setReceiveBufferSize(1024 * 64);
        config.setSendBufferSize(1024 * 4);
        config.setSoLinger(0);
        config.setReuseAddress(false);
        config.setTcpNoDelay(true);

        serverAcceptor.getFilterChain().addLast("logger", new LoggingFilter());
//        serverAcceptor.getFilterChain().addLast("codec",
//                new ProtocolCodecFilter(new StrictCodecFactory()));

        serverAcceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new CommonCodecFactory()));
        // Executor threadPool =
        // Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()
        // * 2 + 1 ,new NamedThreadFactory("executor-pool"));
        // serverAcceptor.getFilterChain().addLast("executor", new
        // ExecutorFilter(threadPool));
        serverAcceptor.getFilterChain().addLast(
                "executor",
                new ExecutorFilter(
                        Runtime.getRuntime().availableProcessors() + 1));
        serverAcceptor.getSessionConfig().setReadBufferSize(4096);
        serverAcceptor.getSessionConfig().setBothIdleTime(60);// 设置断开时间1分钟
    }

    @Override
    protected int getPort()
    {
        return Integer.valueOf(Config.getValue("server.tcp.port"));
    }
}
