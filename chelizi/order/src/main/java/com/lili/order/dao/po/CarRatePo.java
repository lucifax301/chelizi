package com.lili.order.dao.po;

import java.util.Date;

public class CarRatePo{

  private Integer cpid;
  private Integer clid;
  private Integer prate;
  private Integer verify;
  private Integer isdel;
  private Integer cuid;
  private Integer muid;
  private Date ctime;
  private String mtime;


  public Integer getCpid() {
    return this.cpid;
  }
  public void setCpid(Integer cpid) {
    this.cpid=cpid;
  }
  public Integer getClid() {
    return this.clid;
  }
  public void setClid(Integer clid) {
    this.clid=clid;
  }
  public Integer getPrate() {
    return this.prate;
  }
  public void setPrate(Integer prate) {
    this.prate=prate;
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
}
