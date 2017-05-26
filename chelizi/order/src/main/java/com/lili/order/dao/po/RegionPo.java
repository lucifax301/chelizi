package com.lili.order.dao.po;

import java.util.Date;

public class RegionPo{

  private Integer rid;
  private String region;
  private Integer rlevel;
  private Integer pid;
  private Integer isdel;
  private Integer cuid;
  private Integer muid;
  private Date ctime;
  private String mtime;


  public Integer getRid() {
    return this.rid;
  }
  public void setRid(Integer rid) {
    this.rid=rid;
  }
  public String getRegion() {
    return this.region;
  }
  public void setRegion(String region) {
    this.region=region;
  }
  public Integer getRlevel() {
    return this.rlevel;
  }
  public void setRlevel(Integer rlevel) {
    this.rlevel=rlevel;
  }
  public Integer getPid() {
    return this.pid;
  }
  public void setPid(Integer pid) {
    this.pid=pid;
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
