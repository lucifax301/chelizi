/**
 * All rights reserved. This material is confidential and proprietary to 7ROAD
 */
package com.lili.common.util.wrapper;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tracy
 * @date 2011-10-10
 * @version
 */
public class WrapDate implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -3944679908773697635L;

    private Date param;

    public WrapDate()
    {
        super();
        this.param = new Date();
    }

    public WrapDate(Date date)
    {
        this.param = date;
    }

    public Date getParam()
    {
        return param;
    }

    public void setParam(Date param)
    {
        this.param = param;
    }

}
