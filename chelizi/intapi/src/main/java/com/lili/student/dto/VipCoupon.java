package com.lili.student.dto;

import java.io.Serializable;
import java.util.Date;

public class VipCoupon implements Serializable {
    private Integer id;

    private String vipPackageId;

    private String couponTmpId;

    private Date expiretime;

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

    public String getCouponTmpId() {
        return couponTmpId;
    }

    public void setCouponTmpId(String couponTmpId) {
        this.couponTmpId = couponTmpId;
    }

    public Date getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(Date expiretime) {
        this.expiretime = expiretime;
    }
}