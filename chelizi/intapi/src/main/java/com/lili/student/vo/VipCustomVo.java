package com.lili.student.vo;

import java.util.Date;
import java.io.Serializable;

public class VipCustomVo implements Serializable {

/*用户id,大客户必须首先成为用户*/
  private Long studentId;
/*冗余：申请人手机*/
  private String mobile;
/*冗余：申请人姓名*/
  private String cname;
/*大客户公司主键*/
  private Integer coid;
/*工号*/
  private String cid;
/*充值送套餐id*/
  private Integer rcid;
/*方案中文名称*/
  private String rcname;
/*审核通过赠送的优惠券，多张使用逗号分割。*/
  private String coupon;
/*审核通过由于优惠券失效而缺少赠送的券，多张使用逗号分割。*/
  private String couponLack;
/*审核状态：0待审核，1审核通过,2审核拒绝,9该客户优惠套餐已过期*/
  private Integer vstate;
/*拒绝理由*/
  private String reason;
/*删除状态：0代表未删除，1代表已删除*/
  private Integer isdel;
/*创建人id*/
  private Long cuid;
/*更新人id*/
  private Long muid;
/*创建时间*/
  private Date ctime;
/*更新时间*/
  private String mtime;
  /* 优惠券 */
  private String couponTmpId;
  /* 邀请码*/
  private String inviteCode;


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
  public String getCouponTmpId() {
	return couponTmpId;
}
public void setCouponTmpId(String couponTmpId) {
	this.couponTmpId = couponTmpId;
}
public String getInviteCode() {
	return inviteCode;
}
public void setInviteCode(String inviteCode) {
	this.inviteCode = inviteCode;
}
public String toString() {
		return "{studentId="+studentId+",mobile="+mobile+",cname="+cname+",coid="+coid+",cid="+cid+",rcid="+rcid+",rcname="+rcname+",coupon="+coupon+",couponLack="+couponLack+",vstate="+vstate+",reason="+reason+",isdel="+isdel+",cuid="+cuid+",muid="+muid+",ctime="+ctime+",mtime="+mtime+",couponTmpId="+couponTmpId+",inviteCode="+inviteCode+"}";
  }
}
