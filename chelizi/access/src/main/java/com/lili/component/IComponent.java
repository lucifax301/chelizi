/**
 * Date: May 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.component;

/**
 * 组件接口。组件的生命周期：initialize->start->stop->destroy。
 * 
 * @author jiayi.wei
 */
public interface IComponent
{
    /**
     * 获取组件名称
     * 
     * @return
     */
    String getName();

    /**
     * 初始化组件
     * 
     * @param container
     *            组件父容器
     * @return
     */
    boolean initialize();

    /**
     * 启动组件
     * 
     * @return
     */
    boolean start();

    /**
     * 停止组件
     */
    void stop();

    /**
     * 销毁组件，用于释放资源。
     */
    void destroy();

    // /**
    // * 获取组件的父容器
    // *
    // * @return
    // */
    // BaseServer getContainer();
    
    /**
     * 重新加载组件
     */
    void reload();
    
    /**
     * 获取组件中静态模版类名称
     * @return
     */
    String[] getBeanName();
}
