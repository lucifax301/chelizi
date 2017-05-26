package com.lili.net.command;

import com.lili.net.CommonMessage;
import com.lili.net.IClientConnection;

/**
 * 
 * @author weihua.cui
 * 
 */
public interface ICommand
{
    public abstract void execute(IClientConnection session, CommonMessage packet)
            throws Exception;
}
