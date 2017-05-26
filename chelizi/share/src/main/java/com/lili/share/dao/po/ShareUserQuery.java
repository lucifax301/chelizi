package com.lili.share.dao.po;

import com.lili.common.util.BaseQuery;

public class ShareUserQuery extends BaseQuery {
	/**开始mybatis查询变量**/
	private String suid="And suid=#{param1.suid}";
	private String userName="And userName=#{param1.userName}";
	private String sendTotal="And send_total=#{param1.sendTotal}";
	private String recevieTemplate="And recevie_template=#{param1.recevieTemplate}";
	private String sendPhone="And send_phone=#{param1.sendPhone}";
	private String sendType="And send_type=#{param1.sendType}";
	private String regType="And reg_type=#{param1.regType}";
	private String sendUser="And send_user=#{param1.sendUser}";
	private String shareUrl="And shareUrl=#{param1.shareUrl}";
	private String description="And description=#{param1.description}";
	private String rule="And rule=#{param1.rule}";
	private String bigpic="And bigpic=#{param1.bigpic}";
	private String smallpic="And smallpic=#{param1.smallpic}";
	private String checkState="And check_state=#{param1.checkState}";
	private String isdel="And isdel=#{param1.isdel}";
	private String cuid="And cuid=#{param1.cuid}";
	private String muid="And muid=#{param1.muid}";
	private String ctime="And ctime=#{param1.ctime}";
	private String mtime="And mtime=#{param1.mtime}";
	/**结束mybatis查询变量**/
	private String sqlField=" suid as suid,userName as userName,send_total as sendTotal,recevie_template as recevieTemplate,send_phone as sendPhone,send_type as sendType,reg_type as regType,send_user as sendUser,shareUrl as shareUrl,description as description,rule as rule,bigpic as bigpic,smallpic as smallpic,check_state as checkState,isdel as isdel,cuid as cuid,muid as muid,ctime as ctime,mtime as mtime ";

	public ShareUserQuery() {
		pkField="suid";
		setCacheSecond(getCacheSecond());
		projectVoKey="share.ShareUserVo";
	}
	public void setSqlField(String sqlField) {
		this.sqlField=sqlField;
	}

	public String getSqlField() {
		return  sqlField;
	}

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
  	public String getSendTotal() {
    		return this.sendTotal;
  	}
  	public void setSendTotal(String sendTotal) {
    		this.sendTotal=sendTotal;
  	}
  	public String getRecevieTemplate() {
    		return this.recevieTemplate;
  	}
  	public void setRecevieTemplate(String recevieTemplate) {
    		this.recevieTemplate=recevieTemplate;
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
  	public String getRegType() {
    		return this.regType;
  	}
  	public void setRegType(String regType) {
    		this.regType=regType;
  	}
  	public String getSendUser() {
    		return this.sendUser;
  	}
  	public void setSendUser(String sendUser) {
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
