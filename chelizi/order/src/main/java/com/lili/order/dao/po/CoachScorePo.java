package com.lili.order.dao.po;


public class CoachScorePo{

  private Long coachId;
  private Long acceptOrder;
  private Long refuseOrder;
  private Long cancelOrder;
  private Long orderTotal;
  private Long scoreTotal;
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
}
