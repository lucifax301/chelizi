package com.lili.order.vo;

import java.util.Date;
import java.io.Serializable;

public class UnitPriceVo implements Serializable {

  private Integer upid;
  private Integer cityId;
  private Integer courseId;
  private Integer colid;
  private Integer calid;
  private Integer dftype;
  private Date tstart;
  private Date tend;
  private Integer price;
  private Integer allowance;
  private Integer verify;
  private Integer isdel=0;
  private Integer cuid;
  private Integer muid;
  private Date ctime;
  private String mtime;


  public Integer getUpid() {
    return this.upid;
  }
  public void setUpid(Integer upid) {
    this.upid=upid;
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
  public Integer getColid() {
    return this.colid;
  }
  public void setColid(Integer colid) {
    this.colid=colid;
  }
  public Integer getCalid() {
    return this.calid;
  }
  public void setCalid(Integer calid) {
    this.calid=calid;
  }
  public Integer getDftype() {
    return this.dftype;
  }
  public void setDftype(Integer dftype) {
    this.dftype=dftype;
  }
  public Date getTstart() {
    return this.tstart;
  }
  public void setTstart(Date tstart) {
    this.tstart=tstart;
  }
  public Date getTend() {
    return this.tend;
  }
  public void setTend(Date tend) {
    this.tend=tend;
  }
  public Integer getPrice() {
    return this.price;
  }
  public void setPrice(Integer price) {
    this.price=price;
  }
  public Integer getAllowance() {
    return this.allowance;
  }
  public void setAllowance(Integer allowance) {
    this.allowance=allowance;
  }
  public Integer getVerify() {
    return this.verify;
  }
  public void setVerify(Integer verify) {
    this.verify=verify;
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
		return "upid="+upid+","+"cityId="+cityId+","+"courseId="+courseId+","+"colid="+colid+","+"calid="+calid+","+"dftype="+dftype+","+"tstart="+tstart+","+"tend="+tend+","+"price="+price+","+"allowance="+allowance+","+"verify="+verify+","+"isdel="+isdel+","+"cuid="+cuid+","+"muid="+muid+","+"ctime="+ctime+","+"mtime="+mtime;
  }
}
