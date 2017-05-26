/**
 * Date: May 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.net;

/**
 * 连接持有者接口。一般由充当客户端角色的类来实现本接口，如GamePlayer。
 * 
 * @author jiayi.wei
 */
public interface IConnectionHolder
{
    /**
     * 连接关闭时的回调。
     */
    void onDisconnect();

    /**
     * 获取持有的连接。
     * 
     * @return
     */
    IClientConnection getClientConnection();

    /**
     * 设置持有的连接。
     * 
     * @param conn
     */
    void setClientConnection(IClientConnection conn);

    /**
     * 
     */
    void onIdle();

    // /**
    // * 更改登录的连接，仅用于已有在线玩家更改新会话。 conn不能为null
    // * @param conn
    // */
    // void changeClientConnection(IClientConnection conn);

}
