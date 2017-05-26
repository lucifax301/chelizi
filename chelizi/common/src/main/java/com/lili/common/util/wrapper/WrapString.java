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
public class WrapString implements Serializable
{
    /**
	 * 
	 */
    private static final long serialVersionUID = 8885056758279441468L;
    private String param;

    public WrapString()
    {
    }

    public WrapString(String str)
    {
        param = str;
    }

    public String getParam()
    {
        return param;
    }

    public void setParam(String param)
    {
        this.param = param;
    }

}
