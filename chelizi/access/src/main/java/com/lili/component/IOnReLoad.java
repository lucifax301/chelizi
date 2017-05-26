/**
 * Date: 2014-3-7
 *
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.component;

/**
 * 组件重载通知
 * @author saly.bao
 */
public interface IOnReLoad
{
    /**
     * 发送重载通知
     * @param name 重载的模版名称
     */
    public void onReLoad(String... name);
}
