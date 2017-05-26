package com.lili.student.model;

import java.util.Date;
import java.io.Serializable;

import com.lili.cms.entity.BasePagedEntity;

public class VipCompanyBDTO extends BasePagedEntity {

/*主键*/
  private Integer coid;
/*公司名称*/
  private String company;
/*客户类型：1大客户，2散户*/
  private Integer vtype;
/*公司简称*/
  private String shorter;
/*省id*/
  private Integer provinceId;
/*市id*/
  private Integer cityId;
/*省*/
  private String province;
/*市*/
  private String city;
/*公司联系人*/
  private String manger;
/*联系电话*/
  private String mobile;
/*联系座机*/
  private String phone;
/*联系邮箱*/
  private String email;
/*优惠套餐id，可选多个，以逗号隔开，该公司下的员工只能选择公司套餐之一*/
  private String rcid;
/*客户备注*/
  private String remark;
/*是否激活：0未激活，1已经激活，*/
  private Integer active;
/*大客户协议*/
  private String agreement;
/*审核状态：0待审核，1审核通过,2审核拒绝*/
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


  public Integer getCoid() {
    return this.coid;
  }
  public void setCoid(Integer coid) {
    this.coid=coid;
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
  public String getShorter() {
    return this.shorter;
  }
  public void setShorter(String shorter) {
    this.shorter=shorter;
  }
  public Integer getProvinceId() {
    return this.provinceId;
  }
  public void setProvinceId(Integer provinceId) {
    this.provinceId=provinceId;
  }
  public Integer getCityId() {
    return this.cityId;
  }
  public void setCityId(Integer cityId) {
    this.cityId=cityId;
  }
  public String getProvince() {
    return this.province;
  }
  public void setProvince(String province) {
    this.province=province;
  }
  public String getCity() {
    return this.city;
  }
  public void setCity(String city) {
    this.city=city;
  }
  public String getManger() {
    return this.manger;
  }
  public void setManger(String manger) {
    this.manger=manger;
  }
  public String getMobile() {
    return this.mobile;
  }
  public void setMobile(String mobile) {
    this.mobile=mobile;
  }
  public String getPhone() {
    return this.phone;
  }
  public void setPhone(String phone) {
    this.phone=phone;
  }
  public String getEmail() {
    return this.email;
  }
  public void setEmail(String email) {
    this.email=email;
  }
  public String getRcid() {
    return this.rcid;
  }
  public void setRcid(String rcid) {
    this.rcid=rcid;
  }
  public String getRemark() {
    return this.remark;
  }
  public void setRemark(String remark) {
    this.remark=remark;
  }
  public Integer getActive() {
    return this.active;
  }
  public void setActive(Integer active) {
    this.active=active;
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
  public String toString() {
		return "{coid="+coid+",company="+company+",vtype="+vtype+",shorter="+shorter+",provinceId="+provinceId+",cityId="+cityId+",province="+province+",city="+city+",manger="+manger+",mobile="+mobile+",phone="+phone+",email="+email+",rcid="+rcid+",remark="+remark+",active="+active+",agreement="+agreement+",vstate="+vstate+",reason="+reason+",isdel="+isdel+",cuid="+cuid+",muid="+muid+",ctime="+ctime+",mtime="+mtime+"}";
  }
}
