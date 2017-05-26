package com.lili.order.dao.po;


public class CoachStatisticPo{

  private Integer csid;
  private Long coachId;
  private Integer currDate;
  private Integer orderAccept;
  private Integer orderRefuse;
  private Integer orderCancel;
  private Integer orderComment;
  private Integer orderMoney;
  private Integer commentScore;


  public Integer getCsid() {
    return this.csid;
  }
  public void setCsid(Integer csid) {
    this.csid=csid;
  }
  public Long getCoachId() {
    return this.coachId;
  }
  public void setCoachId(Long coachId) {
    this.coachId=coachId;
  }
  public Integer getCurrDate() {
    return this.currDate;
  }
  public void setCurrDate(Integer currDate) {
    this.currDate=currDate;
  }
  public Integer getOrderAccept() {
    return this.orderAccept;
  }
  public void setOrderAccept(Integer orderAccept) {
    this.orderAccept=orderAccept;
  }
  public Integer getOrderRefuse() {
    return this.orderRefuse;
  }
  public void setOrderRefuse(Integer orderRefuse) {
    this.orderRefuse=orderRefuse;
  }
  public Integer getOrderCancel() {
    return this.orderCancel;
  }
  public void setOrderCancel(Integer orderCancel) {
    this.orderCancel=orderCancel;
  }
  public Integer getOrderComment() {
    return this.orderComment;
  }
  public void setOrderComment(Integer orderComment) {
    this.orderComment=orderComment;
  }
  public Integer getOrderMoney() {
    return this.orderMoney;
  }
  public void setOrderMoney(Integer orderMoney) {
    this.orderMoney=orderMoney;
  }
  public Integer getCommentScore() {
    return this.commentScore;
  }
  public void setCommentScore(Integer commentScore) {
    this.commentScore=commentScore;
  }
}
