/**
 * 
 */
package com.lili.pay.dto;

/**
 * @author linbo
 *
 */
public class SpPayParamMap
{
    private Long couponId;
    private Integer couponMoney;
    private String orderId;
    private Integer payMoney;
    private String payWay;
    private String waterNum;
    private Integer result;
    /**
     * @return the couponId
     */
    public Long getCouponId()
    {
        return couponId;
    }
    /**
     * @param couponId the couponId to set
     */
    public void setCouponId(Long couponId)
    {
        this.couponId = couponId;
    }
    /**
     * @return the couponMoney
     */
    public Integer getCouponMoney()
    {
        return couponMoney;
    }
    /**
     * @param couponMoney the couponMoney to set
     */
    public void setCouponMoney(Integer couponMoney)
    {
        this.couponMoney = couponMoney;
    }
    /**
     * @return the orderId
     */
    public String getOrderId()
    {
        return orderId;
    }
    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    /**
     * @return the payMoney
     */
    public Integer getPayMoney()
    {
        return payMoney;
    }
    /**
     * @param payMoney the payMoney to set
     */
    public void setPayMoney(Integer payMoney)
    {
        this.payMoney = payMoney;
    }
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
     * @return the waterNum
     */
    public String getWaterNum()
    {
        return waterNum;
    }
    /**
     * @param waterNum the waterNum to set
     */
    public void setWaterNum(String waterNum)
    {
        this.waterNum = waterNum;
    }
    /**
     * @return the result
     */
    public Integer getResult()
    {
        return result;
    }
    /**
     * @param result the result to set
     */
    public void setResult(Integer result)
    {
        this.result = result;
    }
    
    
}
