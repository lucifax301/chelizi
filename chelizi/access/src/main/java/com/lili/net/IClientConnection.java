/**
 * Date: May 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.net;

import org.apache.mina.core.session.IoSession;

/**
 * 客户端与服务器之间的连接。在屏蔽网络层的具体实现后，充当网络层与上层（一般是指令分发）的沟通桥梁。
 * 
 * @author jiayi.wei
 */
public interface IClientConnection
{

    /**
     * 获取客户端IP
     * 
     * @return
     */
    String getClientIP();

    /**
     * 获取数据包处理器。
     * 
     * @return
     */
    IClientPacketHandler getPacketHandler();

    /**
     * 获取连接持有者。
     * 
     * @return
     */
    IConnectionHolder getHolder();

    /**
     * 设置连接持有者。
     * 
     * @param holder
     */
    void setHolder(IConnectionHolder holder);

    /**
     * 发送数据包。
     * 
     * @param packet
     */
    void send(Object packet);

    /**
     * 连接关闭时的回调。
     */
    void onDisconnect();

    /**
     * 设置加密密钥
     * 
     * @param key
     *            加密密钥
     */
    void setEncryptionKey(int[] key);

    /**
     * 设置解密密钥
     * 
     * @param key
     *            解密密钥
     */
    void setDecryptionKey(int[] key);

    /**
     * 
     * @param immediately是否马上关闭
     */
    void closeConnection(boolean immediately);

    /**
     * 设置属性
     * 
     * @param key
     *            属性 key
     * @param value
     *            属性 value
     */
    void setAttribute(Object key, Object value);

    /**
     * 获取属性值
     * 
     * @param key
     *            属性 key
     * @return 属性 value
     */
    Object getAttribute(Object key);

    /**
     * 获取连接的session<br>
     * 
     * @Test
     * @return
     */
    IoSession getSession();
    
    /**
     * 是否是服务器断开连接
     * @return
     */
    boolean isServerClosed();
    
    /**
     * 设置是否是服务器断开
     * @param isServerClosed
     */
    void setIsServerClosed(boolean isServerClosed);
    
    /**
     * 空闲
     */
    void onIdle();
}
