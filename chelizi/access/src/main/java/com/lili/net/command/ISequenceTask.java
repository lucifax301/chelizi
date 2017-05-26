/**
 * Date: May 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.net.command;

/**
 * 
 * @author jiayi.wei
 */
public interface ISequenceTask
{
    <T extends Runnable> void addCommandTask(T task);

    void finishOneCommandTask();
}
