package com.lili.order.dao.po;

import java.util.Date;

public class CoachAllowancePo{

  private Integer caid;
  private Long coachId;
  private String orderId;
  private Integer allowance;
  private Integer astate;
  private Date atime;


  public Integer getCaid() {
    return this.caid;
  }
  public void setCaid(Integer caid) {
    this.caid=caid;
  }
  public Long getCoachId() {
    return this.coachId;
  }
  public void setCoachId(Long coachId) {
    this.coachId=coachId;
  }
  public String getOrderId() {
    return this.orderId;
  }
  public void setOrderId(String orderId) {
    this.orderId=orderId;
  }
  public Integer getAllowance() {
    return this.allowance;
  }
  public void setAllowance(Integer allowance) {
    this.allowance=allowance;
  }
  public Integer getAstate() {
    return this.astate;
  }
  public void setAstate(Integer astate) {
    this.astate=astate;
  }
  public Date getAtime() {
    return this.atime;
  }
  public void setAtime(Date atime) {
    this.atime=atime;
  }
}
