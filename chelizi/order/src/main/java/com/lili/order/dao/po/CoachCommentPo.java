package com.lili.order.dao.po;

import java.util.Date;

public class CoachCommentPo{

  private Integer ccid;
  private Long studentId;
  private Long coachId;
  private String orderId;
  private Integer courseId;
  private Integer score;
  private String ctid;
  private String oneWord;
  private Date cotime;
  private Integer anonymity;


  public Integer getCcid() {
    return this.ccid;
  }
  public void setCcid(Integer ccid) {
    this.ccid=ccid;
  }
  public Long getStudentId() {
    return this.studentId;
  }
  public void setStudentId(Long studentId) {
    this.studentId=studentId;
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
  public Integer getCourseId() {
    return this.courseId;
  }
  public void setCourseId(Integer courseId) {
    this.courseId=courseId;
  }
  public Integer getScore() {
    return this.score;
  }
  public void setScore(Integer score) {
    this.score=score;
  }
  public String getCtid() {
    return this.ctid;
  }
  public void setCtid(String ctid) {
    this.ctid=ctid;
  }
  public String getOneWord() {
    return this.oneWord;
  }
  public void setOneWord(String oneWord) {
    this.oneWord=oneWord;
  }
  public Date getCotime() {
    return this.cotime;
  }
  public void setCotime(Date cotime) {
    this.cotime=cotime;
  }
  public Integer getAnonymity() {
    return this.anonymity;
  }
  public void setAnonymity(Integer anonymity) {
    this.anonymity=anonymity;
  }
}
