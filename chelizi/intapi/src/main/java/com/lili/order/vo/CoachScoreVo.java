package com.lili.order.vo;

import java.io.Serializable;

public class CoachScoreVo implements Serializable {

  private Long coachId;
  private Long acceptOrder=0l;
  private Long refuseOrder=0l;
  private Long cancelOrder=0l;
  private Long orderTotal=0l;
  private Long scoreTotal=0l;
  private Integer company;


  public Long getCoachId() {
    return this.coachId;
  }
  public void setCoachId(Long coachId) {
    this.coachId=coachId;
  }
  public Long getAcceptOrder() {
    return this.acceptOrder;
  }
  public void setAcceptOrder(Long acceptOrder) {
    this.acceptOrder=acceptOrder;
  }
  public Long getRefuseOrder() {
    return this.refuseOrder;
  }
  public void setRefuseOrder(Long refuseOrder) {
    this.refuseOrder=refuseOrder;
  }
  public Long getCancelOrder() {
    return this.cancelOrder;
  }
  public void setCancelOrder(Long cancelOrder) {
    this.cancelOrder=cancelOrder;
  }
  public Long getOrderTotal() {
    return this.orderTotal;
  }
  public void setOrderTotal(Long orderTotal) {
    this.orderTotal=orderTotal;
  }
  public Long getScoreTotal() {
    return this.scoreTotal;
  }
  public void setScoreTotal(Long scoreTotal) {
    this.scoreTotal=scoreTotal;
  }
  public Integer getCompany() {
    return this.company;
  }
  public void setCompany(Integer company) {
    this.company=company;
  }
  public String toString() {
		return "coachId="+coachId+","+"acceptOrder="+acceptOrder+","+"refuseOrder="+refuseOrder+","+"cancelOrder="+cancelOrder+","+"orderTotal="+orderTotal+","+"scoreTotal="+scoreTotal+","+"company="+company;
  }
}
