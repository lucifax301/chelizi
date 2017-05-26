package com.lili.share.dao.po;

import com.lili.common.util.BaseQuery;

public class ChannelQuery extends BaseQuery {
	/**开始mybatis查询变量**/
	private String cid="And cid=#{param1.cid}";
	private String suid="And suid=#{param1.suid}";
	private String sendPhone="And send_phone=#{param1.sendPhone}";
	private String sendType="And send_type=#{param1.sendType}";
	private String sendUser="And send_user=#{param1.sendUser}";
	private String sendState="And send_state=#{param1.sendState}";
	private String sendTime="And send_time=#{param1.sendTime}";
	private String sendTotal="And send_total=#{param1.sendTotal}";
	private String recevieName="And recevie_name=#{param1.recevieName}";
	private String receviePhone="And recevie_phone=#{param1.receviePhone}";
	private String recevieUser="And recevie_user=#{param1.recevieUser}";
	private String recevieState="And recevie_state=#{param1.recevieState}";
	private String recevieCoupon="And recevie_coupon=#{param1.recevieCoupon}";
	private String regName="And reg_name=#{param1.regName}";
	private String regPic="And reg_pic=#{param1.regPic}";
	private String regType="And reg_type=#{param1.regType}";
	private String checkState="And check_state=#{param1.checkState}";
	private String isdel="And isdel=#{param1.isdel}";
	private String cuid="And cuid=#{param1.cuid}";
	private String muid="And muid=#{param1.muid}";
	private String ctime="And ctime=#{param1.ctime}";
	private String mtime="And mtime=#{param1.mtime}";
	/**结束mybatis查询变量**/
	private String sqlField=" cid as cid,suid as suid,send_phone as sendPhone,send_type as sendType,send_user as sendUser,send_state as sendState,send_time as sendTime,send_total as sendTotal,recevie_name as recevieName,recevie_phone as receviePhone,recevie_user as recevieUser,recevie_state as recevieState,recevie_coupon as recevieCoupon,reg_name as regName,reg_pic as regPic,reg_type as regType,check_state as checkState,isdel as isdel,cuid as cuid,muid as muid,ctime as ctime,mtime as mtime ";

	public ChannelQuery() {
		pkField="cid";
		setCacheSecond(getCacheSecond());
		projectVoKey="share.ChannelVo";
	}
	public void setSqlField(String sqlField) {
		this.sqlField=sqlField;
	}

	public String getSqlField() {
		return  sqlField;
	}

  	public String getCid() {
    		return this.cid;
  	}
  	public void setCid(String cid) {
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
  	public String getSendType() {
    		return this.sendType;
  	}
  	public void setSendType(String sendType) {
    		this.sendType=sendType;
  	}
  	public String getSendUser() {
    		return this.sendUser;
  	}
  	public void setSendUser(String sendUser) {
    		this.sendUser=sendUser;
  	}
  	public String getSendState() {
    		return this.sendState;
  	}
  	public void setSendState(String sendState) {
    		this.sendState=sendState;
  	}
  	public String getSendTime() {
    		return this.sendTime;
  	}
  	public void setSendTime(String sendTime) {
    		this.sendTime=sendTime;
  	}
  	public String getSendTotal() {
    		return this.sendTotal;
  	}
  	public void setSendTotal(String sendTotal) {
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
  	public String getRecevieUser() {
    		return this.recevieUser;
  	}
  	public void setRecevieUser(String recevieUser) {
    		this.recevieUser=recevieUser;
  	}
  	public String getRecevieState() {
    		return this.recevieState;
  	}
  	public void setRecevieState(String recevieState) {
    		this.recevieState=recevieState;
  	}
  	public String getRecevieCoupon() {
    		return this.recevieCoupon;
  	}
  	public void setRecevieCoupon(String recevieCoupon) {
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
  	public String getRegType() {
    		return this.regType;
  	}
  	public void setRegType(String regType) {
    		this.regType=regType;
  	}
  	public String getCheckState() {
    		return this.checkState;
  	}
  	public void setCheckState(String checkState) {
    		this.checkState=checkState;
  	}
  	public String getIsdel() {
    		return this.isdel;
  	}
  	public void setIsdel(String isdel) {
    		this.isdel=isdel;
  	}
  	public String getCuid() {
    		return this.cuid;
  	}
  	public void setCuid(String cuid) {
    		this.cuid=cuid;
  	}
  	public String getMuid() {
    		return this.muid;
  	}
  	public void setMuid(String muid) {
    		this.muid=muid;
  	}
  	public String getCtime() {
    		return this.ctime;
  	}
  	public void setCtime(String ctime) {
    		this.ctime=ctime;
  	}
  	public String getMtime() {
    		return this.mtime;
  	}
  	public void setMtime(String mtime) {
    		this.mtime=mtime;
  	}
}
