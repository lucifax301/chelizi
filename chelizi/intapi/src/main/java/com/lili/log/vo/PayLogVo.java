/**
 * 
 */
package com.lili.log.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author linbo
 * 
 */
public class PayLogVo implements Serializable
{
    private static final long serialVersionUID = 2050779678447781106L;

    private Integer payid;

    private String orderid;

    private String waternum;

    private Long studentid;

    private Long coachid;

    private String payway;

    private Double paymoney;

    private Double couponmoney;

    private long couponid;

    private Date paytime;

    /**
     * @return the payid
     */
    public Integer getPayid()
    {
        return payid;
    }

    /**
     * @param payid the payid to set
     */
    public void setPayid(Integer payid)
    {
        this.payid = payid;
    }

    /**
     * @return the orderid
     */
    public String getOrderid()
    {
        return orderid;
    }

    /**
     * @param orderid the orderid to set
     */
    public void setOrderid(String orderid)
    {
        this.orderid = orderid;
    }

    /**
     * @return the waternum
     */
    public String getWaternum()
    {
        return waternum;
    }

    /**
     * @param waternum the waternum to set
     */
    public void setWaternum(String waternum)
    {
        this.waternum = waternum;
    }

    /**
     * @return the studentid
     */
    public Long getStudentid()
    {
        return studentid;
    }

    /**
     * @param studentid the studentid to set
     */
    public void setStudentid(Long studentid)
    {
        this.studentid = studentid;
    }

    /**
     * @return the coachid
     */
    public Long getCoachid()
    {
        return coachid;
    }

    /**
     * @param coachid the coachid to set
     */
    public void setCoachid(Long coachid)
    {
        this.coachid = coachid;
    }

    /**
     * @return the payway
     */
    public String getPayway()
    {
        return payway;
    }

    /**
     * @param payway the payway to set
     */
    public void setPayway(String payway)
    {
        this.payway = payway;
    }

    /**
     * @return the paymoney
     */
    public Double getPaymoney()
    {
        return paymoney;
    }

    /**
     * @param paymoney the paymoney to set
     */
    public void setPaymoney(Double paymoney)
    {
        this.paymoney = paymoney;
    }

    /**
     * @return the couponmoney
     */
    public Double getCouponmoney()
    {
        return couponmoney;
    }

    /**
     * @param couponmoney the couponmoney to set
     */
    public void setCouponmoney(Double couponmoney)
    {
        this.couponmoney = couponmoney;
    }

    /**
     * @return the couponid
     */
    public long getCouponid()
    {
        return couponid;
    }

    /**
     * @param couponid the couponid to set
     */
    public void setCouponid(long couponid)
    {
        this.couponid = couponid;
    }

    /**
     * @return the paytime
     */
    public Date getPaytime()
    {
        return paytime;
    }

    /**
     * @param paytime the paytime to set
     */
    public void setPaytime(Date paytime)
    {
        this.paytime = paytime;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
}
