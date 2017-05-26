package com.lili.order.vo;

import java.util.Date;
import java.io.Serializable;

public class CommentTagVo implements Serializable {

  private Integer ctid;
  private String tag;
  private Integer courseId;
  private Integer dfType;
  private Integer subjectId;
  private Integer type;
  private Integer score;
  private Integer goodBad;
  private Integer isdel=0;
  private Integer cuid;
  private Integer muid;
  private Date ctime;
  private String mtime;
  /*评价标准-1星*/
  private String star1;
  /*评价标准-2星*/
  private String star2;
  /*评价标准-3星*/
  private String star3;
  /*评价标准-4星*/
  private String star4;
  /*评价标准-5星*/
  private String star5;
  /*冗余*/
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
  public Integer getScore() {
    return score;
  }
  public void setScore(Integer score) {
    this.score = score;
  }
  public Integer getDfType() {
    return dfType;
  }
  public void setDfType(Integer dfType) {
    this.dfType = dfType;
  }
  public Integer getSubjectId() {
    return subjectId;
  }
  public void setSubjectId(Integer subjectId) {
    this.subjectId = subjectId;
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
  public String toString() {
    return "{ctid="+ctid+",tag="+tag+",courseId="+courseId+",type="+type+",goodBad="+goodBad+",isdel="+isdel+",cuid="+cuid+",muid="+muid+",ctime="+ctime+",mtime="+mtime+",star1="+star1+",star2="+star2+",star3="+star3+",star4="+star4+",star5="+star5+",extra="+extra+"}";
  }

}
