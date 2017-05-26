/**
 * Date: Mar 19, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.event;

/**
 * 事件监听器接口
 * 
 * @author jiayi.wei
 */
public interface IEventListener
{
    /**
     * 事件触发时的回调。
     * 
     * @param arg
     *            事件参数
     */
    void onEvent(EventArg arg);
}
