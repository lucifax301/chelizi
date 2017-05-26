package com.lili.student.vo;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

public class RechargeGearVo implements Serializable {

/*档位主键*/
  private Integer rgid;
/*充值送方案主键*/
  private Integer rcid;
/*最低金额，单位分。如果没有则无限制*/
  private Integer pmin;
/*最高金额，单位分。如果没有则无限制*/
  private Integer pmax;
/*赠送比例,为百分比的两位整数部分*/
  private Integer percent;
/*赠送金额，单位分*/
  private Integer money;
/* 本次赠送优惠券名，多个以|线分割 */
  private String couponName;
/* 本次赠送优惠券ID*/
  private String couponId;
/* 本次赠送优惠券数量*/
  private String couponNum;
/*审核状态：0待审核，1审核通过,2审核拒绝*/
  private Integer vstate;
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
  
  private List<CouponList> couponList; 


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
  public List<CouponList> getCouponList() {
	return couponList;
}
public void setCouponList(List<CouponList> couponList) {
	this.couponList = couponList;
}
public String toString() {
		return "{rgid="+rgid+",rcid="+rcid+",pmin="+pmin+",pmax="+pmax+",percent="+percent+",money="+money+
				 ",couponName=" + couponName +",couponId=" + couponId +",couponNum=" + couponNum +
				",vstate="+vstate+",isdel="+isdel+",cuid="+cuid+",muid="+muid+",ctime="+ctime+",mtime="+mtime+"}";
  }
}
