package com.lili.order.dao.po;

import java.util.Date;

public class CancelReasonPo{

  private Integer crid;
  private String reason;
  private Integer utype;
  private Integer isdel;
  private Integer cuid;
  private Integer muid;
  private Date ctime;
  private String mtime;


  public Integer getCrid() {
    return this.crid;
  }
  public void setCrid(Integer crid) {
    this.crid=crid;
  }
  public String getReason() {
    return this.reason;
  }
  public void setReason(String reason) {
    this.reason=reason;
  }
  public Integer getUtype() {
    return this.utype;
  }
  public void setUtype(Integer utype) {
    this.utype=utype;
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
