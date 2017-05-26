package com.lili.component;

import com.lili.net.command.ICode;


public class CommandComponent extends AbstractCommandComponent<ICode>
{
    @Override
    public String getCommandPacketName()
    {
        return "com.lili.command"; // TODO modify to fix
    }

    @Override
    public Class<ICode> getAnnotationClass()
    {
        return ICode.class;
    }

    @Override
    public Short getNodeType(ICode annotation)
    {
        return annotation.code();
    }

}
