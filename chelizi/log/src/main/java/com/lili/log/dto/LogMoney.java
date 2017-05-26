package com.lili.log.dto;

import java.util.Date;

public class LogMoney {
    private Integer id;

    private Long userid;

    private Byte usertype;

    private Byte operatetype;

    private Integer changevalue;

    private Date operatetime;

    private Byte payway;

    private Integer leftvalue;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Byte getUsertype() {
        return usertype;
    }

    public void setUsertype(Byte usertype) {
        this.usertype = usertype;
    }

    public Byte getOperatetype() {
        return operatetype;
    }

    public void setOperatetype(Byte operatetype) {
        this.operatetype = operatetype;
    }

    public Integer getChangevalue() {
        return changevalue;
    }

    public void setChangevalue(Integer changevalue) {
        this.changevalue = changevalue;
    }

    public Date getOperatetime() {
        return operatetime;
    }

    public void setOperatetime(Date operatetime) {
        this.operatetime = operatetime;
    }

    public Byte getPayway() {
        return payway;
    }

    public void setPayway(Byte payway) {
        this.payway = payway;
    }

    public Integer getLeftvalue() {
        return leftvalue;
    }

    public void setLeftvalue(Integer leftvalue) {
        this.leftvalue = leftvalue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}