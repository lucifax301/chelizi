package com.lili.net.command;

import com.lili.net.CommonMessage;
import com.lili.net.IClientConnection;


/**
 * 全局任务(离线用户指令)
 * 
 * @author weihua.cui
 * 
 */
public class ServerCmdTask extends AbstractCmdTask
{

    private IClientConnection connect;

    /**
     * @param cmd
     * @param packet
     */
    public ServerCmdTask(AbstractUserCmd cmd, CommonMessage message,
            IClientConnection connect)
    {
        super(cmd, message);
        this.connect = connect;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.road.ddw.commands.user.AbstractUserCmdTask#execute()
     */
    @Override
    protected void execute()
    {
        this.cmd.executeConnect(this.connect, this.message);
    }

}
