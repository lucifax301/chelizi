package com.lili.net.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lili.component.ComponentManager;
import com.lili.net.CommonMessage;
import com.lili.net.IClientConnection;


/**
 * 抽象用户命令调度器
 * 
 * @author weihua.cui
 * 
 */

public abstract class AbstractUserCmd implements ICommand
{
    protected final Logger logger = LoggerFactory.getLogger(getClass()
            .getName());

    /**
     * server excute全局调用使用(还没有玩家的)
     * 
     * @param
     */
    public void executeConnect(IClientConnection conn, CommonMessage packet)
    {

    }

    /**
     * 用户任务的调度分配， Handler调用
     */
    @Override
    public final void execute(IClientConnection conn, CommonMessage packet)
    {
        if (conn == null)
        {
            logger.error("connect null error");
        }
        AppUser player = (AppUser) conn.getHolder();
        ISequenceTask sequenceTask = (ISequenceTask) player;
        
        //System.out.println("packet111111.........");
        if (player == null)
        {
            ServerCmdTask task = new ServerCmdTask(this, packet, conn);
            // 没有创建IAppUser对象前的客户端请求
            ComponentManager.getInstance().getUserCmdThreadPool().submit(task);
        }
        else
        {
            if (sequenceTask == null)
            {
                logger.error("sequenceTask is null, userName: {}.",
                        player.toString());
                return;
            }
            else
            {
                //System.out.println("packet2222222.........");
                UserCmdTask task = new UserCmdTask(this, packet, player);
                sequenceTask.addCommandTask(task);
            }
        }
    }

    public abstract void execute(AppUser player, CommonMessage packet);

}
