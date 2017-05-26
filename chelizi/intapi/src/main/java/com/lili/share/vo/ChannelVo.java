package com.lili.share.vo;


import java.util.Date;
import java.io.Serializable;

public class ChannelVo implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = -964254584003087566L;
/*主键*/
  private Integer cid;
/*分享用户或用户类*/
  private String suid;
/*发送人的手机*/
  private String sendPhone;
/*发送人的用户类型：1代表教练，2代表学员，3管理后台用户,4校园代理，5推广渠道*/
  private Integer sendType;
/*发送分享人的id*/
  private Long sendUser;
/*发送人状态，1未获得奖金，2已获得奖金，3已入账奖金*/
  private Integer sendState;
/*支付给分享人时间*/
  private Date sendTime;
/*支付分享人金额，单位分*/
  private Integer sendTotal;
/*接收人姓名*/
  private String recevieName;
/*接收人手机,不必须唯一，以最近一个作为有效*/
  private String receviePhone;
/*接人注册后的注册id*/
  private Long recevieUser;
/*接受状态：1已关联，2已注册，3已报名*/
  private Integer recevieState;
/*接人人获得的优惠券*/
  private Long recevieCoupon;
/*注册人的名称*/
  private String regName;
/*注册人的头像*/
  private String regPic;
/*注册用户类型：1代表教练，2代表学员*/
  private Integer regType;
/*审核状态：1待审核，2审核通过*/
  private Integer checkState;
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


  public Integer getCid() {
    return this.cid;
  }
  public void setCid(Integer cid) {
    this.cid=cid;
  }
  public String getSuid() {
    return this.suid;
  }
  public void setSuid(String suid) {
    this.suid=suid;
  }
  public String getSendPhone() {
    return this.sendPhone;
  }
  public void setSendPhone(String sendPhone) {
    this.sendPhone=sendPhone;
  }
  public Integer getSendType() {
    return this.sendType;
  }
  public void setSendType(Integer sendType) {
    this.sendType=sendType;
  }
  public Long getSendUser() {
    return this.sendUser;
  }
  public void setSendUser(Long sendUser) {
    this.sendUser=sendUser;
  }
  public Integer getSendState() {
    return this.sendState;
  }
  public void setSendState(Integer sendState) {
    this.sendState=sendState;
  }
  public Date getSendTime() {
    return this.sendTime;
  }
  public void setSendTime(Date sendTime) {
    this.sendTime=sendTime;
  }
  public Integer getSendTotal() {
    return this.sendTotal;
  }
  public void setSendTotal(Integer sendTotal) {
    this.sendTotal=sendTotal;
  }
  public String getRecevieName() {
    return this.recevieName;
  }
  public void setRecevieName(String recevieName) {
    this.recevieName=recevieName;
  }
  public String getReceviePhone() {
    return this.receviePhone;
  }
  public void setReceviePhone(String receviePhone) {
    this.receviePhone=receviePhone;
  }
  public Long getRecevieUser() {
    return this.recevieUser;
  }
  public void setRecevieUser(Long recevieUser) {
    this.recevieUser=recevieUser;
  }
  public Integer getRecevieState() {
    return this.recevieState;
  }
  public void setRecevieState(Integer recevieState) {
    this.recevieState=recevieState;
  }
  public Long getRecevieCoupon() {
    return this.recevieCoupon;
  }
  public void setRecevieCoupon(Long recevieCoupon) {
    this.recevieCoupon=recevieCoupon;
  }
  public String getRegName() {
    return this.regName;
  }
  public void setRegName(String regName) {
    this.regName=regName;
  }
  public String getRegPic() {
    return this.regPic;
  }
  public void setRegPic(String regPic) {
    this.regPic=regPic;
  }
  public Integer getRegType() {
    return this.regType;
  }
  public void setRegType(Integer regType) {
    this.regType=regType;
  }
  public Integer getCheckState() {
    return this.checkState;
  }
  public void setCheckState(Integer checkState) {
    this.checkState=checkState;
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
		return "{cid="+cid+",suid="+suid+",sendPhone="+sendPhone+",sendType="+sendType+",sendUser="+sendUser+",sendState="+sendState+",sendTime="+sendTime+",sendTotal="+sendTotal+",recevieName="+recevieName+",receviePhone="+receviePhone+",recevieUser="+recevieUser+",recevieState="+recevieState+",recevieCoupon="+recevieCoupon+",regName="+regName+",regPic="+regPic+",regType="+regType+",checkState="+checkState+",isdel="+isdel+",cuid="+cuid+",muid="+muid+",ctime="+ctime+",mtime="+mtime+"}";
  }
}
