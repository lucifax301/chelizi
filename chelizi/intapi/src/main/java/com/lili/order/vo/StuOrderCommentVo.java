package com.lili.order.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class StuOrderCommentVo implements Serializable {

  private Integer scid;
  private Long studentId;
  private Long coachId;
  private String orderId;
  private Integer courseId;
  private Integer subjectId;
  private Integer dfType;
  private List<Integer> ctids;
  private List<Integer> scores;
  private String oneWord;
  private Date cotime;
  private List<String> tags;

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
  
  public List<Integer> getCtids() {
	return ctids;
}
public void setCtids(List<Integer> ctids) {
	this.ctids = ctids;
}
public List<Integer> getScores() {
	return scores;
}
public void setScores(List<Integer> scores) {
	this.scores = scores;
}
public List<String> getTags() {
	return tags;
}
public void setTags(List<String> tags) {
	this.tags = tags;
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
	return subjectId;
}
public void setSubjectId(Integer subjectId) {
	this.subjectId = subjectId;
}

public Integer getDfType() {
	return dfType;
}
public void setDfType(Integer dfType) {
	this.dfType = dfType;
}
public String toString() {
		return "scid="+scid+","+"studentId="+studentId+","+"coachId="+coachId+","+"orderId="+orderId+","+"courseId="+courseId+",subjectId="+subjectId+",dfType="+dfType+"ctids="+ctids+","+"scores="+scores+","+"oneWord="+oneWord+","+"cotime="+cotime;
  }
}
