/**
 * Date: 2014-1-20
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.net;

/**
 * 可切换的连接持有者<br>
 * 目前用于GS与FS之间的多线TCP连接抽象,设计成链表,主要便于连接之间的切换<br>
 * 
 * @author jianlong.chen
 */
public interface ILinkedConnectionOwner
{
    void setNextConnection(ILinkedConnectionOwner onwer);

    ILinkedConnectionOwner getNextConnection();

    void setPrevConnection(ILinkedConnectionOwner owener);

    ILinkedConnectionOwner getPrevConnection();

    void setId(int id);

    int getId();

    boolean isConnected();

}
