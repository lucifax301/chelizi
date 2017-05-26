package com.lili.order.vo;

import java.util.Date;
import java.io.Serializable;

public class BasePriceVo implements Serializable {

  private Integer bpid;
  private Integer cityId;
  private Integer courseId;
  private Integer colid;
  private Integer price;
  private Integer allowance;
  private Integer verify;
  private Integer isdel=0;
  private Integer cuid;
  private Integer muid;
  private Date ctime;
  private String mtime;


  public Integer getBpid() {
    return this.bpid;
  }
  public void setBpid(Integer bpid) {
    this.bpid=bpid;
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
		return "bpid="+bpid+","+"cityId="+cityId+","+"courseId="+courseId+","+"colid="+colid+","+"price="+price+","+"allowance="+allowance+","+"verify="+verify+","+"isdel="+isdel+","+"cuid="+cuid+","+"muid="+muid+","+"ctime="+ctime+","+"mtime="+mtime;
  }
}
