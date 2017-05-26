/**
 * All rights reserved. This material is confidential and proprietary to 7ROAD
 */
package com.lili.common.util.wrapper;

import java.io.Serializable;

/**
 * @author Dream
 * @date 2011-5-12
 * @version
 * 
 */
public class WrapInteger implements Serializable
{
    /**
	 * 
	 */
    private static final long serialVersionUID = -4371153997882010181L;
    private int param = 0;

    public WrapInteger()
    {
    }

    public WrapInteger(int param)
    {
        this.param = param;
    }

    public int getParam()
    {
        return param;
    }

    public void setParam(int param)
    {
        this.param = param;
    }

    @Override
    public String toString()
    {
        return String.valueOf(param);
    }
}
