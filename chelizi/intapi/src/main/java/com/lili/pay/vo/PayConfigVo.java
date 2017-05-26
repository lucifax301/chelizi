/**
 * 
 */
package com.lili.pay.vo;

import java.io.Serializable;

/**
 * @author linbo
 *
 */
public class PayConfigVo implements Serializable
{
    private static final long serialVersionUID = 4918828933592107045L;
    
    private String payWay;
    
    private String param1;
    
    private String param2;
    
    private String param3;

    /**
     * @return the payWay
     */
    public String getPayWay()
    {
        return payWay;
    }

    /**
     * @param payWay the payWay to set
     */
    public void setPayWay(String payWay)
    {
        this.payWay = payWay;
    }

    /**
     * @return the param1
     */
    public String getParam1()
    {
        return param1;
    }

    /**
     * @param param1 the param1 to set
     */
    public void setParam1(String param1)
    {
        this.param1 = param1;
    }

    /**
     * @return the param2
     */
    public String getParam2()
    {
        return param2;
    }

    /**
     * @param param2 the param2 to set
     */
    public void setParam2(String param2)
    {
        this.param2 = param2;
    }

    /**
     * @return the param3
     */
    public String getParam3()
    {
        return param3;
    }

    /**
     * @param param3 the param3 to set
     */
    public void setParam3(String param3)
    {
        this.param3 = param3;
    }
}
