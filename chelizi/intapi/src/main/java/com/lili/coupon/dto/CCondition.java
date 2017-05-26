package com.lili.coupon.dto;

import java.io.Serializable;
import java.util.Date;

public class CCondition implements Serializable {
    private Integer conditionid;

    private Byte type;

    private String param1;

    private String param2;

    private String descri;

    private Date createTime;

    private String coupontmpid;

    public Integer getConditionid() {
        return conditionid;
    }

    public void setConditionid(Integer conditionid) {
        this.conditionid = conditionid;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1 == null ? null : param1.trim();
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2 == null ? null : param2.trim();
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri == null ? null : descri.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCoupontmpid() {
        return coupontmpid;
    }

    public void setCoupontmpid(String coupontmpid) {
        this.coupontmpid = coupontmpid;
    }
}