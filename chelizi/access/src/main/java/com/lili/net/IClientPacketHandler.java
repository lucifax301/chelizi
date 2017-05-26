/**
 * Date: May 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.net;

/**
 * 客户端封包处理器，将网络层接收到的数据包往上层传递。
 * 
 * @author jiayi.wei
 */
public interface IClientPacketHandler
{
    /**
     * 封包处理
     * 
     * @param conn
     *            连接
     * @param packet
     *            封包
     */
    void process(IClientConnection conn, Object packet);
}
