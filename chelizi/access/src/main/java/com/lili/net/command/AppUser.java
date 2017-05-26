package com.lili.net.command;

import com.lili.LiliAccess;
import com.lili.coach.service.CoachService;
import com.lili.component.ComponentManager;
import com.lili.net.IClientConnection;
import com.lili.net.IConnectionHolder;

public class AppUser implements ISequenceTask, IConnectionHolder
{
    /** 命令队列 */
    private SelfDrivenTaskQueue<UserCmdTask> cmdQueue = new SelfDrivenTaskQueue<UserCmdTask>(
            ComponentManager.getInstance().getUserCmdThreadPool());

    private IClientConnection connection;
    
    private long userId;
    
    private boolean isOnline;
    
    /**
     * @param userId
     */
    public AppUser(long userId)
    {
        super();
        this.setUserId(userId);
    }

    /* (non-Javadoc)
     * @see com.lili.net.command.ISequenceTask#addCommandTask(java.lang.Runnable)
     */
    @Override
    public <T extends Runnable>void addCommandTask(T task)
    {
        cmdQueue.add((UserCmdTask) task);
    }

    /* (non-Javadoc)
     * @see com.lili.net.command.ISequenceTask#finishOneCommandTask()
     */
    @Override
    public void finishOneCommandTask()
    {
        cmdQueue.complete();
    }

    /* (non-Javadoc)
     * @see com.lili.net.IConnectionHolder#onDisconnect()
     */
    @Override
    public void onDisconnect()
    {
        setActive(false);
    }

    /* (non-Javadoc)
     * @see com.lili.net.IConnectionHolder#getClientConnection()
     */
    @Override
    public IClientConnection getClientConnection()
    {
        return connection;
    }

    /* (non-Javadoc)
     * @see com.lili.net.IConnectionHolder#setClientConnection(com.lili.net.IClientConnection)
     */
    @Override
    public void setClientConnection(IClientConnection conn)
    {
        connection = conn;
    }

    /**
     * @return the userId
     */
    public long getUserId()
    {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId)
    {
        this.userId = userId;
    }

    /* (non-Javadoc)
     * @see com.lili.net.IConnectionHolder#onIdle()
     */
    @Override
    public void onIdle()
    {
        setActive(false);
    }
    
    public void setActive(boolean isActive)
    {
        CoachService coachService = LiliAccess.getDubboContext().getBean(CoachService.class);
        coachService.setOnline(String.valueOf(userId), isActive);
        isOnline = isActive;
    }

    /**
     * @return the isOnline
     */
    public boolean isOnline()
    {
        return isOnline;
    }

    /**
     * @param isOnline the isOnline to set
     */
    public void setOnline(boolean isOnline)
    {
        this.isOnline = isOnline;
    }
}
