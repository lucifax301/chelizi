/**
 * Date: May 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.component;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mina组件抽象基类。
 * 
 * @author jiayi.wei
 */
public abstract class AbstractMinaComponent implements IComponent
{
    protected static final Logger LOGGER = LoggerFactory
            .getLogger(AbstractMinaComponent.class);

    /** mina组件名称 */
    public static final String NAME = "mina";

    protected IoAcceptor serverAcceptor = null;

    /**
     * 获取ClientIoHandler实例。
     * 
     * @return
     */
    protected abstract IoHandler getClientIoHandler();

    /**
     * mina初始化配置。
     * 
     * @param serverAcceptor
     */
    protected abstract void acceptorInit(IoAcceptor serverAcceptor);

    /**
     * 获取监听的端口。
     * 
     * @return
     */
    protected abstract int getPort();

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public boolean initialize()
    {
        serverAcceptor = new NioSocketAcceptor(Runtime.getRuntime()
                .availableProcessors() + 1);
        serverAcceptor.setHandler(getClientIoHandler());
        acceptorInit(serverAcceptor);
        return true;
    }

    @Override
    public boolean start()
    {
        try
        {
            int port = getPort();
            serverAcceptor.bind(new InetSocketAddress(port));
            LOGGER.info("Listening at port " + port);
        }
        catch (IOException e)
        {
            LOGGER.error("", e);
            return false;
        }

        return true;
    }

    @Override
    public void stop()
    {

    }

    @Override
    public void destroy()
    {

    }

    /* (non-Javadoc)
     * @see com.road.pitaya.component.IComponent#reload()
     */
    @Override
    public void reload()
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see com.road.pitaya.component.IComponent#getBeanName()
     */
    @Override
    public String[] getBeanName()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
