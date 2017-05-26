package com.lili.order.dao.po;

import java.util.Date;

public class StuCommentPo{

  private Integer scid;
  private Long studentId;
  private Long coachId;
  private String orderId;
  private Integer courseId;
  private Integer ctid;
  private Integer score;
  private String oneWord;
  private Date cotime;
  private Integer subjectId;


  public Integer getScid() {
    return this.scid;
  }
  public void setScid(Integer scid) {
    this.scid=scid;
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
  public Integer getCtid() {
    return this.ctid;
  }
  public void setCtid(Integer ctid) {
    this.ctid=ctid;
  }
  public Integer getScore() {
    return this.score;
  }
  public void setScore(Integer score) {
    this.score=score;
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
  public Integer getSubjectId() {
    return this.subjectId;
  }
  public void setSubjectId(Integer subjectId) {
    this.subjectId=subjectId;
  }
}
