package com.lili.order.vo;

import java.io.Serializable;

public class CoachStatisticVo implements Serializable {

  private Integer csid;
  private Long coachId;
  private Integer currDate;
  private Integer orderAccept=0;
  private Integer orderRefuse=0;
  private Integer orderCancel=0;
  private Integer orderComment=0;
  private Integer orderMoney=0;
  private Integer commentScore=0;


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
  public String toString() {
		return "csid="+csid+","+"coachId="+coachId+","+"currDate="+currDate+","+"orderAccept="+orderAccept+","+"orderRefuse="+orderRefuse+","+"orderCancel="+orderCancel+","+"orderComment="+orderComment+","+"orderMoney="+orderMoney+","+"commentScore="+commentScore;
  }
}
