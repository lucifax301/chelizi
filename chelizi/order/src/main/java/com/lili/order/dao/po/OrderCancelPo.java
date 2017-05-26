package com.lili.order.dao.po;

import java.util.Date;

public class OrderCancelPo{

  private String orderId;
  private Integer ucancel;
  private Integer uduty;
  private Integer retype;
  private String reseaon;
  private Date cltime;
  private Integer pstate;


  public String getOrderId() {
    return this.orderId;
  }
  public void setOrderId(String orderId) {
    this.orderId=orderId;
  }
  public Integer getUcancel() {
    return this.ucancel;
  }
  public void setUcancel(Integer ucancel) {
    this.ucancel=ucancel;
  }
  public Integer getUduty() {
    return this.uduty;
  }
  public void setUduty(Integer uduty) {
    this.uduty=uduty;
  }
  public Integer getRetype() {
    return this.retype;
  }
  public void setRetype(Integer retype) {
    this.retype=retype;
  }
  public String getReseaon() {
    return this.reseaon;
  }
  public void setReseaon(String reseaon) {
    this.reseaon=reseaon;
  }
  public Date getCltime() {
    return this.cltime;
  }
  public void setCltime(Date cltime) {
    this.cltime=cltime;
  }
  public Integer getPstate() {
    return this.pstate;
  }
  public void setPstate(Integer pstate) {
    this.pstate=pstate;
  }
}
