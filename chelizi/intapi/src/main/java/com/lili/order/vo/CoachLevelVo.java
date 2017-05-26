package com.lili.order.vo;

import java.util.Date;
import java.io.Serializable;

public class CoachLevelVo implements Serializable {

  private Integer colid;
  private String name;
  private Integer prate;
  private Integer isdel=0;
  private Integer cuid;
  private Integer muid;
  private Date ctime;
  private String mtime;


  public Integer getColid() {
    return this.colid;
  }
  public void setColid(Integer colid) {
    this.colid=colid;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name=name;
  }
  public Integer getPrate() {
    return this.prate;
  }
  public void setPrate(Integer prate) {
    this.prate=prate;
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
		return "colid="+colid+","+"name="+name+","+"prate="+prate+","+"isdel="+isdel+","+"cuid="+cuid+","+"muid="+muid+","+"ctime="+ctime+","+"mtime="+mtime;
  }
}
