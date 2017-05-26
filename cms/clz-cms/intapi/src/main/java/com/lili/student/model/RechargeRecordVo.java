package com.lili.student.model;

import java.util.Date;
import java.io.Serializable;

public class RechargeRecordVo implements Serializable {

/*主键Id*/
  private Integer rrid;
/*用户id*/
  private Long studentId;
/*冗余：学员姓名*/
  private String name;
/*冗余：手机*/
  private String mobile;
/*冗余：大客户公司名称*/
  private String company;
/*冗余：客户类型：1大客户，2散户*/
  private Integer vtype;
/*本次充值金额,单位分*/
  private Integer charge;
/*本次赠送金额,单位分*/
  private Integer recharge;
  /* 本次赠送优惠券名，多个以|线分割 */
  private String couponName;
  /* 本次赠送优惠券ID*/
  private String couponId;
  /* 本次赠送优惠券数量*/
  private String couponNum;
/*交易单号*/
  private String waterId;
/*充值送方案id:如果手工通过cms调整，则该id为0*/
  private Integer rcid;
/*冗余：充值送方案名称,如果手工通过cms调整，则该为调整理由*/
  private String rcname;
/*冗余：支付方式*/
  private String payWay;
/*支付时间*/
  private Date payTime;
/*赠送资金到账时间*/
  private Date getTime;
/*财务审核状态：0待审核，1审核通过,2审核拒绝*/
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
/* 用户类型：0代表学生 1代表教练 */
  private Integer utype;
  
  private String createUser;
  private String updateUser;

  public String getCreateUser() {
	return createUser;
}
public void setCreateUser(String createUser) {
	this.createUser = createUser;
}
public String getUpdateUser() {
	return updateUser;
}
public void setUpdateUser(String updateUser) {
	this.updateUser = updateUser;
}
public Integer getRrid() {
    return this.rrid;
  }
  public void setRrid(Integer rrid) {
    this.rrid=rrid;
  }
  public Long getStudentId() {
    return this.studentId;
  }
  public void setStudentId(Long studentId) {
    this.studentId=studentId;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name=name;
  }
  public String getMobile() {
    return this.mobile;
  }
  public void setMobile(String mobile) {
    this.mobile=mobile;
  }
  public String getCompany() {
    return this.company;
  }
  public void setCompany(String company) {
    this.company=company;
  }
  public Integer getVtype() {
    return this.vtype;
  }
  public void setVtype(Integer vtype) {
    this.vtype=vtype;
  }
  public Integer getCharge() {
    return this.charge;
  }
  public void setCharge(Integer charge) {
    this.charge=charge;
  }
  public Integer getRecharge() {
    return this.recharge;
  }
  public void setRecharge(Integer recharge) {
    this.recharge=recharge;
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
public String getWaterId() {
    return this.waterId;
  }
  public void setWaterId(String waterId) {
    this.waterId=waterId;
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
  public String getPayWay() {
    return this.payWay;
  }
  public void setPayWay(String payWay) {
    this.payWay=payWay;
  }
  public Date getPayTime() {
    return this.payTime;
  }
  public void setPayTime(Date payTime) {
    this.payTime=payTime;
  }
  public Date getGetTime() {
    return this.getTime;
  }
  public void setGetTime(Date getTime) {
    this.getTime=getTime;
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
  public Integer getUtype() {
	return utype;
}
public void setUtype(Integer utype) {
	this.utype = utype;
}
public String toString() {
		return "{rrid="+rrid+",studentId="+studentId+",name="+name+",mobile="+mobile+",company="+company+",vtype="+vtype+",charge="+charge+",recharge="+recharge+ ",couponName=" + 	couponName +",couponId=" + couponId +",couponNum=" + couponNum +",waterId="+waterId+",rcid="+rcid+",rcname="+rcname+",payWay="+payWay+",payTime="+payTime+",getTime="+getTime+",vstate="+vstate+",reason="+reason+",isdel="+isdel+",cuid="+cuid+",muid="+muid+",ctime="+ctime+",mtime="+mtime+",utype="+utype+"}";
  }
}
