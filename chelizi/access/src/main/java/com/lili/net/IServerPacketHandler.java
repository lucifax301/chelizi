/**
 * Date: May 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.net;

/**
 * 服务器包处理器，用来处理从目标服务器接收到的包，将网络层接收到的数据包往上层传递。
 * 
 * @author jiayi.wei
 */
public interface IServerPacketHandler
{
    /**
     * 封包处理。
     * 
     * @param client
     *            连接器
     * @param packet
     *            封包
     */
    void process(IServerConnector client, Object packet);
}
