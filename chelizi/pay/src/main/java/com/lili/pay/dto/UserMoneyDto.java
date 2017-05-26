package com.lili.pay.dto;

import java.util.Date;

public class UserMoneyDto {
    private Integer id;

    private Long userid;

    private Byte usertype;

    private Byte operatetype;

    private Integer changevalue;

    private Date operatetime;

    private String payway;

    private Integer leftvalue;

    private String remark;

    private String orderId;

    private Integer status;

    private Byte isEarning;

    private Byte isBalance;

    private String tranObject;

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

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Byte getIsEarning() {
        return isEarning;
    }

    public void setIsEarning(Byte isEarning) {
        this.isEarning = isEarning;
    }

    public Byte getIsBalance() {
        return isBalance;
    }

    public void setIsBalance(Byte isBalance) {
        this.isBalance = isBalance;
    }

    public String getTranObject() {
        return tranObject;
    }

    public void setTranObject(String tranObject) {
        this.tranObject = tranObject;
    }
}