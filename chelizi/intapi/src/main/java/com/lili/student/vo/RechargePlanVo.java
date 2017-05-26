package com.lili.student.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RechargePlanVo implements Serializable {

/*充值送方案主键，请保持9位：YYmmddXXX*/
  private Integer rcid;
/*方案中文名称*/
  private String rcname;
/*客户类型：1大客户，2散户*/
  private Integer vtype;
/*方案开始时间*/
  private Date tstart;
/*方案结束时间*/
  private Date tend;
/*是否激活：0未激活，1已经激活，*/
  private Integer active;
/*生效条件：0无条件，1已经报名*/
  private Integer enroll;
/*该方案生效的城市，多个以逗号分割，未空表示不限制*/
  private String cityId;
/*冗余：该方案生效的城市，多个以逗号分割*/
  private String cityName;
/*普惠方案的当前有效版本：0无效，1有效，始终只有一个方案有效*/
  private Integer common;
/*成为大客户是否需要审核：1需要，0不需要*/
  private Integer needVerify;
/*报名后是否还可以：0不可用1可以用，*/
  private Integer regUse;
/*是否在充值时自动赠送：0不自动赠送，1自动赠送*/
  private Integer auto;
/*本方案最多赠送次数,如果为空表示没有限制*/
  private Integer maxTimes;
/*是否独立使用：1独立使用，即用了本充送就不能再使用其他充送方案，2可以和其他充送混合使用*/
  private Integer indepent;
/*审核通过推送消息*/
  private String jpush;
/*审核通过推送短信*/
  private String ems;
/*充值赠送到账推送消息*/
  private String rejpush;
/*充值赠送到账推送短信*/
  private String reems;
/*该方案审核通过后赠送的优惠券模版，多条以逗号分割*/
  private String couponTemplate;
/*该方案审核通过后赠送的优惠券数量，与模版完全一一对应，多条以逗号分割*/
  private String couponNumber;
/*服务协议*/
  private String agreement;
/*审核状态：0待审核，1审核通过,2审核拒绝*/
  private Integer vstate;
/*拒绝理由*/
  private String reason;
  
/*删除状态：0代表未删除，1代表已删除*/
  private Integer isdel;
  
  private Integer isExitRercid;
  private Integer rercid;
  private Integer regNum;
/*创建人id*/
  private Long cuid;
/*更新人id*/
  private Long muid;
/*创建时间*/
  private Date ctime;
/*更新时间*/
  private String mtime;
  /*优惠档位*/
  private List<RechargeGearVo> rechargeGearList; 
  /*大客户公司*/
  private String company;
  
  /**
   * 创建人姓名
   */
  private String createUser;

  /**
   * 更新人姓名
   */
  private String updateUser;
  
  private String name;
  
  private Integer moneyvalue;
  
  private Integer discount;

  private Date expiretime;
  
  
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
  public String getCityId() {
    return this.cityId;
  }
  public void setCityId(String cityId) {
    this.cityId=cityId;
  }
  public String getCityName() {
    return this.cityName;
  }
  public void setCityName(String cityName) {
    this.cityName=cityName;
  }
  public Integer getCommon() {
    return this.common;
  }
  public void setCommon(Integer common) {
    this.common=common;
  }
  public Integer getNeedVerify() {
    return this.needVerify;
  }
  public void setNeedVerify(Integer needVerify) {
    this.needVerify=needVerify;
  }
  public Integer getRegUse() {
    return this.regUse;
  }
  public void setRegUse(Integer regUse) {
    this.regUse=regUse;
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
  public String getAgreement() {
    return this.agreement;
  }
  public void setAgreement(String agreement) {
    this.agreement=agreement;
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
  public List<RechargeGearVo> getRechargeGearList() {
	return rechargeGearList;
}
public void setRechargeGearList(List<RechargeGearVo> rechargeGearList) {
	this.rechargeGearList = rechargeGearList;
}

public String getCompany() {
	return company;
}
public void setCompany(String company) {
	this.company = company;
}
public Integer getIsExitRercid() {
	return isExitRercid;
}
public void setIsExitRercid(Integer isExitRercid) {
	this.isExitRercid = isExitRercid;
}
public Integer getRercid() {
	return rercid;
}
public void setRercid(Integer rercid) {
	this.rercid = rercid;
}
public Integer getRegNum() {
	return regNum;
}
public void setRegNum(Integer regNum) {
	this.regNum = regNum;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Integer getMoneyvalue() {
	return moneyvalue;
}
public void setMoneyvalue(Integer moneyvalue) {
	this.moneyvalue = moneyvalue;
}
public Date getExpiretime() {
	return expiretime;
}
public void setExpiretime(Date expiretime) {
	this.expiretime = expiretime;
}
public Integer getDiscount() {
	return discount;
}
public void setDiscount(Integer discount) {
	this.discount = discount;
}
public String toString() {
		return "{rcid="+rcid+",rcname="+rcname+",vtype="+vtype+",tstart="+tstart+",tend="+tend+","
				+ "active="+active+",enroll="+enroll+",cityId="+cityId+",cityName="+cityName+",common="+
				common+",needVerify="+needVerify+",regUse="+regUse+",auto="+auto+",maxTimes="+maxTimes
				+",indepent="+indepent+",jpush="+jpush+",ems="+ems+",rejpush="+rejpush+",reems="+reems
				+",couponTemplate="+couponTemplate+",couponNumber="+couponNumber+",agreement="
				+agreement+",vstate="+vstate+",reason="+reason+",isdel="+isdel+",isExitRercid="+isExitRercid+",rercid="+rercid+",reg_num="+regNum+",cuid="+cuid+",muid="
				+muid+",ctime="+ctime+",mtime="+mtime+",gear="+rechargeGearList+"}";
  }
}
