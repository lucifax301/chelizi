/**
 * All rights reserved. This material is confidential and proprietary to 7ROAD
 */
package com.lili.common.util.wrapper;

import java.io.Serializable;

/**
 * @author : Cookie
 * @date : 2011-5-12
 * @version
 * 
 */
public class WrapDouble implements Serializable
{
    /**
	 * 
	 */
    private static final long serialVersionUID = 4921098982402354198L;
    private double value;

    public WrapDouble(double value)
    {
        super();
        this.value = value;
    }

    /**
     * @return the value
     */
    public double getValue()
    {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(double value)
    {
        this.value = value;
    }

}
