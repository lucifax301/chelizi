package com.lili.student.dao.po;

import java.util.Date;

public class RechargeGearPo{

  private Integer rgid;
  private Integer rcid;
  private Integer pmin;
  private Integer pmax;
  private Integer percent;
  private Integer money;
  private Integer vstate;
  private Integer isdel;
  private Long cuid;
  private Long muid;
  private Date ctime;
  private String mtime;
  private String couponName;
  private String couponId;
  private String couponNum;


  public Integer getRgid() {
    return this.rgid;
  }
  public void setRgid(Integer rgid) {
    this.rgid=rgid;
  }
  public Integer getRcid() {
    return this.rcid;
  }
  public void setRcid(Integer rcid) {
    this.rcid=rcid;
  }
  public Integer getPmin() {
    return this.pmin;
  }
  public void setPmin(Integer pmin) {
    this.pmin=pmin;
  }
  public Integer getPmax() {
    return this.pmax;
  }
  public void setPmax(Integer pmax) {
    this.pmax=pmax;
  }
  public Integer getPercent() {
    return this.percent;
  }
  public void setPercent(Integer percent) {
    this.percent=percent;
  }
  public Integer getMoney() {
    return this.money;
  }
  public void setMoney(Integer money) {
    this.money=money;
  }
  public Integer getVstate() {
    return this.vstate;
  }
  public void setVstate(Integer vstate) {
    this.vstate=vstate;
  }
  public Integer getIsdel() {
    return this.isdel;
  }
  public void setIsdel(Integer isdel) {
    this.isdel=isdel;
  }
  public Long getCuid() {
    return this.cuid;
  }
  public void setCuid(Long cuid) {
    this.cuid=cuid;
  }
  public Long getMuid() {
    return this.muid;
  }
  public void setMuid(Long muid) {
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
public String getCouponName() {
	return couponName;
}
public void setCouponName(String couponName) {
	this.couponName = couponName;
}
public String getCouponId() {
	return couponId;
}
public void setCouponId(String couponId) {
	this.couponId = couponId;
}
public String getCouponNum() {
	return couponNum;
}
public void setCouponNum(String couponNum) {
	this.couponNum = couponNum;
}
}
