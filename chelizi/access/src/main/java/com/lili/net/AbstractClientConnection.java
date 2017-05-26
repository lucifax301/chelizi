/**
 * Date: May 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.net;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IClientConnection抽象基类，所有的IClientConnection实现类必须从此类继承。
 * 
 * @author jiayi.wei
 */
public abstract class AbstractClientConnection implements IClientConnection
{
    /** 连接持有者 */
    private IConnectionHolder holder = null;
    /** 客户端封包处理器 */
    private IClientPacketHandler packetHandler = null;
    /** 扩展属性表 */
    private Map<Object, Object> attributes = null;

    /**
     * 构造方法
     * 
     * @param packetHandler
     *            客户端封包处理器
     */
    public AbstractClientConnection(IClientPacketHandler packetHandler)
    {
        this.packetHandler = packetHandler;
        this.attributes = new ConcurrentHashMap<Object, Object>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.net.IClientConnection#getPacketHandler()
     */
    @Override
    public IClientPacketHandler getPacketHandler()
    {
        return packetHandler;
    }

    @Override
    public IConnectionHolder getHolder()
    {
        return holder;
    }

    @Override
    public void setHolder(IConnectionHolder holder)
    {
        this.holder = holder;
        if (this.holder != null)
        {
            this.holder.setClientConnection(this);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.net.IClientConnection#onDisconnect()
     */
    @Override
    public void onDisconnect()
    {
        if (holder != null)
        {
            holder.onDisconnect();
        }
    }

    @Override
    public void setAttribute(Object key, Object value)
    {
        attributes.put(key, value);
    }

    @Override
    public Object getAttribute(Object key)
    {
        return attributes.get(key);
    }
    
    /**
     * 空闲
     */
    @Override
    public void onIdle()
    {
        if (holder != null)
        {
            holder.onIdle();
        }
    }
}
