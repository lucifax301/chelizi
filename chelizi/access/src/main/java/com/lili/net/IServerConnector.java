/**
 * Date: May 28, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.net;

/**
 * 服务器以客户端身份连接到其他服务器的连接器
 * 
 * @author jiayi.wei
 */
public interface IServerConnector
{
    /**
     * 获取连接地址（IP或域名）
     * 
     * @return 连接地址（IP或域名）
     */
    String getAddress();

    /**
     * 获取连接端口
     * 
     * @return 连接端口
     */
    int getPort();

    /**
     * 获取最大重连次数
     * 
     * @return 最大重连次数
     */
    int getMaxReconnectTimes();

    /**
     * 设置最大重连次数
     * 
     * @param maxReconnectTimes
     *            最大重连次数
     */
    void setMaxReconnectTimes(int maxReconnectTimes);

    /**
     * 获取发生重连的次数
     * 
     * @return 发生重连的次数
     */
    int getReconnectedTimes();

    /**
     * 连接
     * 
     * @return 连接成功则返回true，否则返回false。
     */
    boolean connect();

    /**
     * 断开连接
     */
    void disconnect();

    /**
     * 是否已连接。
     * 
     * @return 已连接则返回true，否则返回false。
     */
    boolean isConnected();

    /**
     * 发送数据包
     * 
     * @param msg
     *            待发送的数据包
     */
    void send(CommonMessage msg);

    /**
     * 发送数据包
     * 
     * @param msg
     *            待发送的数据包
     * @param playerID
     *            玩家ID
     */
    void send(CommonMessage msg, int playerID);

    /**
     * 获取服务器包处理器
     * 
     * @return 服务器包处理器
     */
    IServerPacketHandler getPacketHandler();
}
