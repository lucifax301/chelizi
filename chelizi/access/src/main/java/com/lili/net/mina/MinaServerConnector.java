/**
 * Date: May 30, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.net.mina;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lili.net.AbstractServerConnector;
import com.lili.net.CommonConst;
import com.lili.net.CommonMessage;
import com.lili.net.IServerPacketHandler;

/**
 * 基于mina实现的ServerConnector
 * 
 * @author jiayi.wei
 */
public class MinaServerConnector extends AbstractServerConnector
{
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MinaServerConnector.class);

    /** mina相关操作的锁 */
    private final ReentrantReadWriteLock minaLock = new ReentrantReadWriteLock();

    /** 连接会话 */
    private IoSession session = null;
    /** 连接 */
    private SocketConnector connector = null;
    /** 处理器 */
    private IoHandlerAdapter handler = null;
    /** 编码解码工厂 */
    private ProtocolCodecFactory factory = null;

    /**
     * 构造器
     * 
     * @param address
     * @param port
     * @param packetHandler
     * @param handler
     * @param factory
     */
    public MinaServerConnector(String address, int port,
            IServerPacketHandler packetHandler, IoHandlerAdapter handler,
            ProtocolCodecFactory factory)
    {
        super(address, port, packetHandler);
        this.handler = handler;
        this.factory = factory;
    }

    /**
     * 构造方法，IoHandlerAdapter实例将由ServerConnectorIoHandler创建。
     * 
     * @param address
     * @param port
     * @param packetHandler
     * @param factory
     */
    public MinaServerConnector(String address, int port,
            IServerPacketHandler packetHandler, ProtocolCodecFactory factory)
    {
        super(address, port, packetHandler);
        this.handler = new ServerConnectorIoHandler();
        this.factory = factory;
    }

    /**
     * 构造方法，IoHandlerAdapter实例将由ServerConnectorIoHandler创建，
     * ProtocolCodecFactory实例将由StrictCodecFactory创建。
     * 
     * @param address
     * @param port
     * @param packetHandler
     */
    public MinaServerConnector(String address, int port,
            IServerPacketHandler packetHandler)
    {
        super(address, port, packetHandler);
        this.handler = new ServerConnectorIoHandler();
        this.factory = new StrictCodecFactory();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.net.IServerConnector#connect()
     */
    @Override
    public boolean connect()
    {
        try
        {
            minaLock.writeLock().lock();

            connector = new NioSocketConnector(Runtime.getRuntime()
                    .availableProcessors() + 1);
            connector.setConnectTimeoutMillis(1000 * 8);
            connector.setHandler(handler);
            connector.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(factory));
            connector.getFilterChain().addLast(
                    "executor",
                    new ExecutorFilter(Executors
                            .newSingleThreadExecutor(new ThreadFactory()
                            {
                                public Thread newThread(Runnable r)
                                {
                                    Thread thread = new Thread(r, getAddress()
                                            + "-" + getPort() + "-thread");
                                    return thread;
                                }
                            })));

            ConnectFuture future = connector.connect(new InetSocketAddress(
                    getAddress(), getPort()));
            future.awaitUninterruptibly(1000 * 10);
            session = future.getSession();
            session.setAttribute(CommonConst.SERVER_CONNECTOR, this);
            reconnectedTimes = 0;
            return true;
        }
        catch (Exception e)
        {
            reconnectedTimes++;
            connector.dispose();
            connector = null;
            LOGGER.error(
                    "Cann't connect to address:{}, port:{}. Exception: {}",
                    new Object[]{getAddress(), getPort(), e.toString()});
            return false;
        }
        finally
        {
            minaLock.writeLock().unlock();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.net.IServerConnector#disconnect()
     */
    @Override
    public void disconnect()
    {
        if (isConnected())
        {
            session.close(true);
            session = null;
        }

        if (connector != null)
        {
            connector.dispose();
            connector = null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.net.IServerConnector#isConnected()
     */
    @Override
    public boolean isConnected()
    {
        if (session != null && session.isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.road.pitaya.net.IServerConnector#send(com.road.pitaya.net.CommonMessage
     * )
     */
    @Override
    public void send(CommonMessage msg)
    {
        if (isConnected())
        {
            session.write(msg);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.road.pitaya.net.IServerConnector#send(com.road.pitaya.net.CommonMessage
     * , int)
     */
    @Override
    public void send(CommonMessage msg, int playerID)
    {
        // msg.setPlayerId(playerID);TODO:
        send(msg);
    }

    /**
     * @Test 测试断开GS 与 FS 的连接<br>
     * @return
     */
    public IoSession getSession()
    {
        return session;
    }

}
