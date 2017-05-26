package com.lili.student.dao.po;

import java.util.Date;

public class VipCustomPo{

  private Long studentId;
  private String mobile;
  private String cname;
  private Integer coid;
  private String cid;
  private Integer rcid;
  private String rcname;
  private String coupon;
  private String couponLack;
  private Integer vstate;
  private String reason;
  private Integer isdel;
  private Long cuid;
  private Long muid;
  private Date ctime;
  private String mtime;


  public Long getStudentId() {
    return this.studentId;
  }
  public void setStudentId(Long studentId) {
    this.studentId=studentId;
  }
  public String getMobile() {
    return this.mobile;
  }
  public void setMobile(String mobile) {
    this.mobile=mobile;
  }
  public String getCname() {
    return this.cname;
  }
  public void setCname(String cname) {
    this.cname=cname;
  }
  public Integer getCoid() {
    return this.coid;
  }
  public void setCoid(Integer coid) {
    this.coid=coid;
  }
  public String getCid() {
    return this.cid;
  }
  public void setCid(String cid) {
    this.cid=cid;
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
  public String getCoupon() {
    return this.coupon;
  }
  public void setCoupon(String coupon) {
    this.coupon=coupon;
  }
  public String getCouponLack() {
    return this.couponLack;
  }
  public void setCouponLack(String couponLack) {
    this.couponLack=couponLack;
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
  public String getMtime() {
    return this.mtime;
  }
  public void setMtime(String mtime) {
    this.mtime=mtime;
  }
}
