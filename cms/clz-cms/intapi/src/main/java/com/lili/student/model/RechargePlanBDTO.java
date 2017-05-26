package com.lili.student.model;

import java.io.Serializable;
import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

public class RechargePlanBDTO extends BasePagedEntity{

  private Integer rcid;
  private String rcname;
  private Integer vtype;
  private Date tstart;
  private Date tend;
  private Integer active;
  private Integer enroll;
  private Integer auto;
  private Integer maxTimes;
  private Integer indepent;
  private String jpush;
  private String ems;
  private String rejpush;
  private String reems;
  private String couponTemplate;
  private String couponNumber;
  private Integer vstate;
  private String reason;
  private Integer isdel;
  private Long cuid;
  private Long muid;
  private Date ctime;
  private String creator;
  
  public String getCreator() {
	return creator;
}
public void setCreator(String creator) {
	this.creator = creator;
}
public Integer getRcid() {
    return this.rcid;
  }
  public void setRcid(Integer rcid) {
    this.rcid=rcid;
  }
  public String getRcname() {
    return this.rcname;
  }
  public void setRcname(String rcname) {
    this.rcname=rcname;
  }
  public Integer getVtype() {
    return this.vtype;
  }
  public void setVtype(Integer vtype) {
    this.vtype=vtype;
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
  public Integer getActive() {
    return this.active;
  }
  public void setActive(Integer active) {
    this.active=active;
  }
  public Integer getEnroll() {
    return this.enroll;
  }
  public void setEnroll(Integer enroll) {
    this.enroll=enroll;
  }
  public Integer getAuto() {
    return this.auto;
  }
  public void setAuto(Integer auto) {
    this.auto=auto;
  }
  public Integer getMaxTimes() {
    return this.maxTimes;
  }
  public void setMaxTimes(Integer maxTimes) {
    this.maxTimes=maxTimes;
  }
  public Integer getIndepent() {
    return this.indepent;
  }
  public void setIndepent(Integer indepent) {
    this.indepent=indepent;
  }
  public String getJpush() {
    return this.jpush;
  }
  public void setJpush(String jpush) {
    this.jpush=jpush;
  }
  public String getEms() {
    return this.ems;
  }
  public void setEms(String ems) {
    this.ems=ems;
  }
  public String getRejpush() {
    return this.rejpush;
  }
  public void setRejpush(String rejpush) {
    this.rejpush=rejpush;
  }
  public String getReems() {
    return this.reems;
  }
  public void setReems(String reems) {
    this.reems=reems;
  }
  public String getCouponTemplate() {
    return this.couponTemplate;
  }
  public void setCouponTemplate(String couponTemplate) {
    this.couponTemplate=couponTemplate;
  }
  public String getCouponNumber() {
    return this.couponNumber;
  }
  public void setCouponNumber(String couponNumber) {
    this.couponNumber=couponNumber;
  }
  public Integer getVstate() {
    return this.vstate;
  }
  public void setVstate(Integer vstate) {
    this.vstate=vstate;
  }
  public String getReason() {
    return this.reason;
  }
  public void setReason(String reason) {
    this.reason=reason;
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
}
