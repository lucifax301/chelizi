package com.lili.school.model;

import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

/**
 * 驾校申请帐号POJO
 * @author chenbin
 *
 */
public class SchoolAccountApply extends BasePagedEntity {

	private int id;
	
	private String schoolName;
	
	private String shortName;
	
	private String schoolAccount;
	
	private String schoolPwd;
	
	private String city;
	
	private String cityId;
	
	private String province;
	
	private String address;
	
	private String applyer;
	
	private String phoneNum;
	
	private int status;
	
	private String remark;
	
	private Date createDate;
	
	

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getSchoolAccount() {
		return schoolAccount;
	}

	public void setSchoolAccount(String schoolAccount) {
		this.schoolAccount = schoolAccount;
	}

	public String getSchoolPwd() {
		return schoolPwd;
	}

	public void setSchoolPwd(String schoolPwd) {
		this.schoolPwd = schoolPwd;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getApplyer() {
		return applyer;
	}

	public void setApplyer(String applyer) {
		this.applyer = applyer;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
