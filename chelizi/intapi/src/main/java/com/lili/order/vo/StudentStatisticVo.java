package com.lili.order.vo;

import java.io.Serializable;

public class StudentStatisticVo implements Serializable {

/*主键*/
  private Integer ssid;
/*学生id*/
  private Long studentId;
/*评价标签id*/
  private Integer ctid;
/*该标签评价总次数*/
  private Integer total;
/*该标签总得分*/
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
  public String toString() {
		return "{ssid="+ssid+",studentId="+studentId+",ctid="+ctid+",total="+total+",score="+score+"}";
  }
}
