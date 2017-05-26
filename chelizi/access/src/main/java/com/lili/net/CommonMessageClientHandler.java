package com.lili.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lili.component.AbstractCommandComponent;
import com.lili.component.ComponentManager;
import com.lili.net.command.ICommand;

public class CommonMessageClientHandler implements IClientPacketHandler
{
    private static Logger LOGGER = LoggerFactory
            .getLogger(CommonMessageClientHandler.class);

    @Override
    public void process(IClientConnection conn, Object packet)
    {
        short code = ((CommonMessage) packet).getCode();
        // 从组件管理器中间调用
        AbstractCommandComponent<?> cm = (AbstractCommandComponent<?>) ComponentManager
                .getInstance().getComponent(AbstractCommandComponent.NAME);
        if (cm == null)
        {
            LOGGER.error("CommandModule not found");
            return;
        }

        ICommand cmd = cm.getCommand(code);
        if (cmd == null)
        {
            LOGGER.error(" Can not found code = " + code + ",drop this packet.");
            return;
        }

        try
        {
            cmd.execute(conn, (CommonMessage) packet);
        }
        catch (Exception e)
        {
            LOGGER.error("", e);
        }

    }
}
