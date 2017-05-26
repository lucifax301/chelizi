package com.lili.order.dao.po;

import java.util.Date;

public class CommentTagPo{

  private Integer ctid;
  private String tag;
  private Integer courseId;
  private Integer dfType;
  private Integer type;
  private Integer goodBad;
  private Integer isdel;
  private Integer cuid;
  private Integer muid;
  private Date ctime;
  private String mtime;
  private String star1;
  private String star2;
  private String star3;
  private String star4;
  private String star5;
  private String extra;


  public Integer getCtid() {
    return this.ctid;
  }
  public void setCtid(Integer ctid) {
    this.ctid=ctid;
  }
  public String getTag() {
    return this.tag;
  }
  public void setTag(String tag) {
    this.tag=tag;
  }
  public Integer getCourseId() {
    return this.courseId;
  }
  public void setCourseId(Integer courseId) {
    this.courseId=courseId;
  }
  public Integer getType() {
    return this.type;
  }
  public void setType(Integer type) {
    this.type=type;
  }
  public Integer getGoodBad() {
    return this.goodBad;
  }
  public void setGoodBad(Integer goodBad) {
    this.goodBad=goodBad;
  }
  public Integer getIsdel() {
    return this.isdel;
  }
  public void setIsdel(Integer isdel) {
    this.isdel=isdel;
  }
  public Integer getCuid() {
    return this.cuid;
  }
  public void setCuid(Integer cuid) {
    this.cuid=cuid;
  }
  public Integer getMuid() {
    return this.muid;
  }
  public void setMuid(Integer muid) {
    this.muid=muid;
  }
  public Date getCtime() {
    return this.ctime;
  }
  public void setCtime(Date ctime) {
    this.ctime=ctime;
  }
  public String getMtime() {
    return this.mtime;
  }
  public void setMtime(String mtime) {
    this.mtime=mtime;
  }
public Integer getDfType() {
	return dfType;
}
public void setDfType(Integer dfType) {
	this.dfType = dfType;
}
 public String getStar1() {
    return this.star1;
  }
  public void setStar1(String star1) {
    this.star1=star1;
  }
  public String getStar2() {
    return this.star2;
  }
  public void setStar2(String star2) {
    this.star2=star2;
  }
  public String getStar3() {
    return this.star3;
  }
  public void setStar3(String star3) {
    this.star3=star3;
  }
  public String getStar4() {
    return this.star4;
  }
  public void setStar4(String star4) {
    this.star4=star4;
  }
  public String getStar5() {
    return this.star5;
  }
  public void setStar5(String star5) {
    this.star5=star5;
  }
  public String getExtra() {
    return this.extra;
  }
  public void setExtra(String extra) {
    this.extra=extra;
  }
  
}
