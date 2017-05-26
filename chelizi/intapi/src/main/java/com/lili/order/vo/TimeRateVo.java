package com.lili.order.vo;

import java.util.Date;
import java.io.Serializable;

public class TimeRateVo implements Serializable {

  private Integer tpid;
  private Integer ptype;
  private String title;
  private Integer yint;
  private Integer mint;
  private Integer wstart;
  private Integer wend;
  private Integer dstart;
  private Integer dend;
  private Integer hstart;
  private Integer hend;
  private Integer prate;
  private Integer verify;
  private Integer isdel=0;
  private Integer cuid;
  private Integer muid;
  private Date ctime;
  private String mtime;


  public Integer getTpid() {
    return this.tpid;
  }
  public void setTpid(Integer tpid) {
    this.tpid=tpid;
  }
  public Integer getPtype() {
    return this.ptype;
  }
  public void setPtype(Integer ptype) {
    this.ptype=ptype;
  }
  public String getTitle() {
    return this.title;
  }
  public void setTitle(String title) {
    this.title=title;
  }
  public Integer getYint() {
    return this.yint;
  }
  public void setYint(Integer yint) {
    this.yint=yint;
  }
  public Integer getMint() {
    return this.mint;
  }
  public void setMint(Integer mint) {
    this.mint=mint;
  }
  public Integer getWstart() {
    return this.wstart;
  }
  public void setWstart(Integer wstart) {
    this.wstart=wstart;
  }
  public Integer getWend() {
    return this.wend;
  }
  public void setWend(Integer wend) {
    this.wend=wend;
  }
  public Integer getDstart() {
    return this.dstart;
  }
  public void setDstart(Integer dstart) {
    this.dstart=dstart;
  }
  public Integer getDend() {
    return this.dend;
  }
  public void setDend(Integer dend) {
    this.dend=dend;
  }
  public Integer getHstart() {
    return this.hstart;
  }
  public void setHstart(Integer hstart) {
    this.hstart=hstart;
  }
  public Integer getHend() {
    return this.hend;
  }
  public void setHend(Integer hend) {
    this.hend=hend;
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
  public String toString() {
		return "tpid="+tpid+","+"ptype="+ptype+","+"title="+title+","+"yint="+yint+","+"mint="+mint+","+"wstart="+wstart+","+"wend="+wend+","+"dstart="+dstart+","+"dend="+dend+","+"hstart="+hstart+","+"hend="+hend+","+"prate="+prate+","+"verify="+verify+","+"isdel="+isdel+","+"cuid="+cuid+","+"muid="+muid+","+"ctime="+ctime+","+"mtime="+mtime;
  }
}
