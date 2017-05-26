package com.lili.order.vo;

import java.util.Date;
import java.io.Serializable;

public class RegionVo implements Serializable {

  private Integer rid;
  private String region;
  private Integer rlevel;
  private Integer pid;
  private Integer isdel=0;
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
  public String toString() {
		return "rid="+rid+","+"region="+region+","+"rlevel="+rlevel+","+"pid="+pid+","+"isdel="+isdel+","+"cuid="+cuid+","+"muid="+muid+","+"ctime="+ctime+","+"mtime="+mtime;
  }
}
