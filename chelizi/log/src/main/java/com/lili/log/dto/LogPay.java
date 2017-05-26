package com.lili.log.dto;

import java.util.Date;

public class LogPay {
    private Integer payId;

    private String orderid;

    private String waternum;

    private Long studentid;

    private Long coachid;

    private String payway;

    private Double paymoney;

    private Double couponmoney;

    private Long couponid;

    private Date paytime;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public String getWaternum() {
        return waternum;
    }

    public void setWaternum(String waternum) {
        this.waternum = waternum == null ? null : waternum.trim();
    }

    public Long getStudentid() {
        return studentid;
    }

    public void setStudentid(Long studentid) {
        this.studentid = studentid;
    }

    public Long getCoachid() {
        return coachid;
    }

    public void setCoachid(Long coachid) {
        this.coachid = coachid;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway == null ? null : payway.trim();
    }

    public Double getPaymoney() {
        return paymoney;
    }

    public void setPaymoney(Double paymoney) {
        this.paymoney = paymoney;
    }

    public Double getCouponmoney() {
        return couponmoney;
    }

    public void setCouponmoney(Double couponmoney) {
        this.couponmoney = couponmoney;
    }

    public Long getCouponid() {
        return couponid;
    }

    public void setCouponid(Long couponid) {
        this.couponid = couponid;
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }

    /**
     * @return the payId
     */
    public Integer getPayId()
    {
        return payId;
    }

    /**
     * @param payId the payId to set
     */
    public void setPayId(Integer payId)
    {
        this.payId = payId;
    }
}