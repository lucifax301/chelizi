/**
 * Date: May 30, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.net;

/**
 * 连接器抽象基类
 * 
 * @author jiayi.wei
 */
public abstract class AbstractServerConnector implements IServerConnector
{
    /** 默认的最大重连次数 */
    public static final int DEFAULT_MAX_RECONNECT_TIMES = 30;

    /** 发生重连的次数 */
    protected int reconnectedTimes;

    /** 连接地址（IP或域名） */
    private String address;
    /** 连接端口 */
    private int port;
    /** 最大重连次数 */
    private int maxReconnectTimes;
    /** 服务器包处理器 */
    private IServerPacketHandler packetHandler = null;

    /**
     * 构造方法
     * 
     * @param address
     *            连接地址（IP或域名）
     * @param port
     *            连接端口
     * @param packetHandler
     *            服务器包处理器
     */
    public AbstractServerConnector(String address, int port,
            IServerPacketHandler packetHandler)
    {
        this.address = address;
        this.port = port;
        this.maxReconnectTimes = DEFAULT_MAX_RECONNECT_TIMES;
        this.reconnectedTimes = 0;
        this.packetHandler = packetHandler;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.net.IServerConnector#getAddress()
     */
    @Override
    public String getAddress()
    {
        return address;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.net.IServerConnector#getPort()
     */
    @Override
    public int getPort()
    {
        return port;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.net.IServerConnector#getMaxReconnectTimes()
     */
    @Override
    public int getMaxReconnectTimes()
    {
        return maxReconnectTimes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.net.IServerConnector#setMaxReconnectTimes(int)
     */
    @Override
    public void setMaxReconnectTimes(int maxReconnectTimes)
    {
        this.maxReconnectTimes = maxReconnectTimes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.net.IServerConnector#getReconnectedTimes()
     */
    @Override
    public int getReconnectedTimes()
    {
        return reconnectedTimes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.net.IServerConnector#getPacketHandler()
     */
    @Override
    public IServerPacketHandler getPacketHandler()
    {
        return packetHandler;
    }
}
