package com.lili.net.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lili.common.util.StackMessagePrint;
import com.lili.net.CommonMessage;

/**
 * 用户任务
 * 
 * @author weihua.cui
 * 
 */
public class UserCmdTask extends AbstractCmdTask
{
    private static final Logger LOGGER = LoggerFactory
            .getLogger(UserCmdTask.class);

    protected AppUser appUser;

    public UserCmdTask(AbstractUserCmd cmd, CommonMessage message,
            AppUser player)
    {
        super(cmd, message);
        this.appUser = player;
    }

    @Override
    protected void execute()
    {
        try
        {
            this.cmd.execute(this.appUser, this.message);
        }
        catch (Throwable e)
        {
            LOGGER.error(StackMessagePrint.printErrorTrace(e));
        }
        finally
        {
            this.appUser.finishOneCommandTask();
        }

    }

}
