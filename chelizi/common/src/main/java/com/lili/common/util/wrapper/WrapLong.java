package com.lili.common.util.wrapper;

import java.io.Serializable;

public class WrapLong implements Serializable
{
    private static final long serialVersionUID = -4371153997882010181L;
    private long param = 0;

    public WrapLong()
    {
    }

    public WrapLong(long param)
    {
        this.param = param;
    }

    public long getParam()
    {
        return param;
    }

    public void setParam(long param)
    {
        this.param = param;
    }
}
