package com.lili.share.vo;

import java.util.Date;
import java.io.Serializable;

public class ShareUserVo implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 2753702472541639408L;
/*分享用户或用户类主键*/
  private String suid;
/*用户名称*/
  private String userName;
/*分享用户获得金额,单位分*/
  private Integer sendTotal;
/*接人人获得的优惠券*/
  private String recevieTemplate;
/*发送人的手机*/
  private String sendPhone;
/*发送人的用户类型：1代表教练，2代表学员，3管理后台用户,4校园代理，101推广渠道（大于100都插入渠道表）*/
  private Integer sendType;
/*被邀请人的用户类型：1代表教练，2代表学员*/
  private Integer regType;
/*发送分享人的原始用户id,如果为空，则为这类人的规则*/
  private Long sendUser;
/*分享页面基础路径*/
  private String shareUrl;
/*分享简单文字描述*/
  private String description;
/*活动规则*/
  private String rule;
/*分享大图路径*/
  private String bigpic;
/*分享小图路径*/
  private String smallpic;
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


  public String getSuid() {
    return this.suid;
  }
  public void setSuid(String suid) {
    this.suid=suid;
  }
  public String getUserName() {
    return this.userName;
  }
  public void setUserName(String userName) {
    this.userName=userName;
  }
  public Integer getSendTotal() {
    return this.sendTotal;
  }
  public void setSendTotal(Integer sendTotal) {
    this.sendTotal=sendTotal;
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
  public Integer getRegType() {
    return this.regType;
  }
  public void setRegType(Integer regType) {
    this.regType=regType;
  }
  public Long getSendUser() {
    return this.sendUser;
  }
  public void setSendUser(Long sendUser) {
    this.sendUser=sendUser;
  }
  public String getShareUrl() {
    return this.shareUrl;
  }
  public void setShareUrl(String shareUrl) {
    this.shareUrl=shareUrl;
  }
  public String getDescription() {
    return this.description;
  }
  public void setDescription(String description) {
    this.description=description;
  }
  public String getRule() {
    return this.rule;
  }
  public void setRule(String rule) {
    this.rule=rule;
  }
  public String getBigpic() {
    return this.bigpic;
  }
  public void setBigpic(String bigpic) {
    this.bigpic=bigpic;
  }
  public String getSmallpic() {
    return this.smallpic;
  }
  public void setSmallpic(String smallpic) {
    this.smallpic=smallpic;
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
  public String getRecevieTemplate() {
	return recevieTemplate;
}
public void setRecevieTemplate(String recevieTemplate) {
	this.recevieTemplate = recevieTemplate;
}
public String toString() {
		return "{suid="+suid+",userName="+userName+",sendTotal="+sendTotal+",recevieTemplate="+recevieTemplate+",sendPhone="+sendPhone+",sendType="+sendType+",regType="+regType+",sendUser="+sendUser+",shareUrl="+shareUrl+",description="+description+",rule="+rule+",bigpic="+bigpic+",smallpic="+smallpic+",checkState="+checkState+",isdel="+isdel+",cuid="+cuid+",muid="+muid+",ctime="+ctime+",mtime="+mtime+"}";
  }
}
