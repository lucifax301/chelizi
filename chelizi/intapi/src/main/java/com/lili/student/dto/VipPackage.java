package com.lili.student.dto;

import java.io.Serializable;
import java.util.Date;

public class VipPackage implements Serializable {
    private String id;

    private Byte isvalid;

    private Byte isDisableEnrollCoupon;

    private String extra;

    private Date expiretime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Byte getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(Byte isvalid) {
        this.isvalid = isvalid;
    }

    public Byte getIsDisableEnrollCoupon() {
        return isDisableEnrollCoupon;
    }

    public void setIsDisableEnrollCoupon(Byte isDisableEnrollCoupon) {
        this.isDisableEnrollCoupon = isDisableEnrollCoupon;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Date getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(Date expiretime) {
        this.expiretime = expiretime;
    }
}