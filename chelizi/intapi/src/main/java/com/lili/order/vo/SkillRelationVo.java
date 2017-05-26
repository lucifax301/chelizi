package com.lili.order.vo;

import java.util.Date;
import java.io.Serializable;

public class SkillRelationVo implements Serializable {

/*主键*/
  private Integer sid;
/*技能标签id*/
  private Integer ctid;
/*该技能需求的城市*/
  private Integer cityId;
/*该技能的课程*/
  private Integer courseId;
/*该技能的科目*/
  private Integer subjectId;
/*删除状态：0代表未删除，1代表已删除*/
  private Integer isdel;
/*创建人id*/
  private Integer cuid;
/*更新人id*/
  private Integer muid;
/*创建时间*/
  private Date ctime;
/*更新时间*/
  private String mtime;


  public Integer getSid() {
    return this.sid;
  }
  public void setSid(Integer sid) {
    this.sid=sid;
  }
  public Integer getCtid() {
    return this.ctid;
  }
  public void setCtid(Integer ctid) {
    this.ctid=ctid;
  }
  public Integer getCityId() {
    return this.cityId;
  }
  public void setCityId(Integer cityId) {
    this.cityId=cityId;
  }
  public Integer getCourseId() {
    return this.courseId;
  }
  public void setCourseId(Integer courseId) {
    this.courseId=courseId;
  }
  public Integer getSubjectId() {
    return this.subjectId;
  }
  public void setSubjectId(Integer subjectId) {
    this.subjectId=subjectId;
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
  public String toString() {
		return "{sid="+sid+",ctid="+ctid+",cityId="+cityId+",courseId="+courseId+",subjectId="+subjectId+",isdel="+isdel+",cuid="+cuid+",muid="+muid+",ctime="+ctime+",mtime="+mtime+"}";
  }
}
