package com.lili.student.dto;

import java.io.Serializable;
import java.util.Date;

public class VipChargeDiscount implements Serializable{
    private Integer id;

    private String vipPackageId;

    private Integer limitMoney;

    private Integer discount;

    private Date expireTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVipPackageId() {
        return vipPackageId;
    }

    public void setVipPackageId(String vipPackageId) {
        this.vipPackageId = vipPackageId;
    }

    public Integer getLimitMoney() {
        return limitMoney;
    }

    public void setLimitMoney(Integer limitMoney) {
        this.limitMoney = limitMoney;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}