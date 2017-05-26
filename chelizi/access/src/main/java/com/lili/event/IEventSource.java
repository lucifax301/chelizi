/**
 * Date: Aug 19, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.event;

/**
 * 事件源。同一事件类型，支持多个监听者，并且监听者对象可以相同。
 * 
 * @author jiayi.wei
 */
public interface IEventSource
{

    /**
     * 将监听者加入到指定事件类型的监听队列中。
     * 
     * @param eventType
     *            事件类型
     * @param listener
     *            监听者
     */
    public void addListener(int eventType, IEventListener listener);

    /**
     * 从指定事件类型的监听队列中移除指定的监听者。
     * 
     * @param eventType
     *            事件类型
     * @param listener
     *            监听者
     */
    public void removeListener(int eventType, IEventListener listener);

    /**
     * 通知监听者发生了事件。事件源由事件参数arg指定。
     * 
     * @param arg
     *            事件参数
     */
    public void notifyListeners(EventArg arg);

    /**
     * 通知监听者发生了事件。事件源为当前this对象。
     * 
     * @param eventType
     *            事件类型
     */
    public void notifyListeners(int eventType);
}
