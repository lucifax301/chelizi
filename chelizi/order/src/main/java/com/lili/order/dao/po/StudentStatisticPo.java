package com.lili.order.dao.po;


public class StudentStatisticPo{

  private Integer ssid;
  private Long studentId;
  private Integer ctid;
  private Integer total;
  private Integer score;


  public Integer getSsid() {
    return this.ssid;
  }
  public void setSsid(Integer ssid) {
    this.ssid=ssid;
  }
  public Long getStudentId() {
    return this.studentId;
  }
  public void setStudentId(Long studentId) {
    this.studentId=studentId;
  }
  public Integer getCtid() {
    return this.ctid;
  }
  public void setCtid(Integer ctid) {
    this.ctid=ctid;
  }
  public Integer getTotal() {
    return this.total;
  }
  public void setTotal(Integer total) {
    this.total=total;
  }
  public Integer getScore() {
    return this.score;
  }
  public void setScore(Integer score) {
    this.score=score;
  }
}
