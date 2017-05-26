/**
 * Date: May 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.net.mina;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.mina.core.session.IoSession;

import com.lili.net.AbstractClientConnection;
import com.lili.net.CommonConst;
import com.lili.net.IClientPacketHandler;

/**
 * 基于mina的IClientConnection实现。
 * 
 * @author jiayi.wei
 */
public class MinaClientConnection extends AbstractClientConnection
{
    private IoSession session = null;
    
    private boolean isServerClosed = false;

    /**
     * @param packetHandler
     */
    public MinaClientConnection(IClientPacketHandler packetHandler,
            IoSession session)
    {
        super(packetHandler);
        this.session = session;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.net.IClientConnection#send(java.lang.Object)
     */
    @Override
    public void send(Object packet)
    {
        if (session != null && session.isClosing() == false && session.isConnected())
        {
            session.write(packet);
            //System.out.println("write: " + session);
        }
    }

    @Override
    public void setEncryptionKey(int[] key)
    {
        session.setAttribute(CommonConst.ENCRYPTION_KEY, key);
    }

    @Override
    public void setDecryptionKey(int[] key)
    {
        session.setAttribute(CommonConst.DECRYPTION_KEY, key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.net.IClientConnection#closeConnection()
     */
    @Override
    public void closeConnection(boolean immediately)
    {
        if (this.session != null && !session.isClosing())
        {
            session.close(immediately);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.pitaya.net.IClientConnection#getClientIP()
     */
    @Override
    public String getClientIP()
    {
        if (session.getRemoteAddress() == null)
        {
            return "127.0.0.1";
        }
        InetAddress address = ((InetSocketAddress) session.getRemoteAddress()).getAddress();
        if (address == null)
        {
            return "127.0.0.1";
        }
        return address.getHostAddress();
    }

    /*
     * @see IClientConnection#getSession()
     */
    @Override
    public IoSession getSession()
    {
        return session;
    }

    /* (non-Javadoc)
     * @see com.road.pitaya.net.IClientConnection#isServerClosed()
     */
    @Override
    public boolean isServerClosed()
    {
        return this.isServerClosed;
    }

    /* (non-Javadoc)
     * @see com.road.pitaya.net.IClientConnection#setIsServerClosed(boolean)
     */
    @Override
    public void setIsServerClosed(boolean isServerClosed)
    {
        this.isServerClosed = isServerClosed;
    }
}
