package com.lili.net.command;

import com.lili.net.CommonMessage;

/**
 * 用户指令任务封装器
 * 
 * @author weihua.cui
 */
public abstract class AbstractCmdTask implements Runnable
{
    /**
     * 指令
     */
    protected AbstractUserCmd cmd;

    /**
     * 通用消息
     */
    protected CommonMessage message;

    public AbstractCmdTask(AbstractUserCmd cmd, CommonMessage message)
    {
        this.cmd = cmd;
        this.message = message;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        this.execute();
    }

    protected abstract void execute();
}
