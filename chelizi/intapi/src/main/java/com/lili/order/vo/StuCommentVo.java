package com.lili.order.vo;

import java.io.Serializable;
import java.util.Date;

public class StuCommentVo implements Serializable {

  private Integer scid;
  private Long studentId;
  private Long coachId;
  private String orderId;
/*被评价的课程*/
  private Integer courseId;
  private Integer ctid;
  private Integer score;
  private String oneWord;
  private Date cotime;
/*冗余字段：被评价的科目*/
  private Integer subjectId;
  
  private String tag;


  public String getTag() {
	return tag;
}
public void setTag(String tag) {
	this.tag = tag;
}
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
  public String toString() {
		return "{scid="+scid+","+"studentId="+studentId+","+"coachId="+coachId+","+"orderId="+orderId+","+"courseId="+courseId+","+"ctid="+ctid+","+"score="+score+","+"oneWord="+oneWord+","+"cotime="+cotime+",subjectId="+subjectId+"}";
  }
}
