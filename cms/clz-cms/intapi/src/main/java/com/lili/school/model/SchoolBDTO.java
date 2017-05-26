package com.lili.school.model;

import java.math.BigDecimal;

import com.lili.cms.entity.BasePagedEntity;

public class SchoolBDTO extends BasePagedEntity{

	private static final long serialVersionUID = 8036363778122006545L;
	
	private int coachCount;
	private int carCount;
	private String name;
	//驾校图标地址
	private String image;
	
	private int cityId;
	
	private String region;
	
	private int studentCount;
	
	private int fieldCount;
	
	private String address;
	
	private String phoneNum;
	
     /**驾校点评合并表**/
	
	private String tell;
	
	private Integer score;

	private String logo;

	private String icon;

	private Integer trfieldCount;

	private BigDecimal lge;

	private BigDecimal lae;

	private Integer orderNum;

	private String extra;

	private String schoolInfo;

	private String regInfo;
	
	private Integer storeCount;
	
	private Integer packageCount;
	
	private Integer showState;
	
	public int getCoachCount() {
		return coachCount;
	}
	public void setCoachCount(int coachCount) {
		this.coachCount = coachCount;
	}
	public int getCarCount() {
		return carCount;
	}
	public void setCarCount(int carCount) {
		this.carCount = carCount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getStudentCount() {
		return studentCount;
	}
	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
	}
	public int getFieldCount() {
		return fieldCount;
	}
	public void setFieldCount(int fieldCount) {
		this.fieldCount = fieldCount;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTell() {
		return tell;
	}
	public void setTell(String tell) {
		this.tell = tell;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getTrfieldCount() {
		return trfieldCount;
	}
	public void setTrfieldCount(Integer trfieldCount) {
		this.trfieldCount = trfieldCount;
	}
	public BigDecimal getLge() {
		return lge;
	}
	public void setLge(BigDecimal lge) {
		this.lge = lge;
	}
	public BigDecimal getLae() {
		return lae;
	}
	public void setLae(BigDecimal lae) {
		this.lae = lae;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public String getSchoolInfo() {
		return schoolInfo;
	}
	public void setSchoolInfo(String schoolInfo) {
		this.schoolInfo = schoolInfo;
	}
	public String getRegInfo() {
		return regInfo;
	}
	public void setRegInfo(String regInfo) {
		this.regInfo = regInfo;
	}
	public Integer getStoreCount() {
		return storeCount;
	}
	public void setStoreCount(Integer storeCount) {
		this.storeCount = storeCount;
	}
	public Integer getPackageCount() {
		return packageCount;
	}
	public void setPackageCount(Integer packageCount) {
		this.packageCount = packageCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getShowState() {
		return showState;
	}
	public void setShowState(Integer showState) {
		this.showState = showState;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

}
