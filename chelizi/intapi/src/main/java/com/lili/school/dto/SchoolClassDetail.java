package com.lili.school.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class SchoolClassDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -963821025366955757L;

	private Integer schoolId;

    private String name;

    private Integer coachCount;

    private Integer carCount;

    private Integer cityId;

    private String address;

    private String phoneNum;

    private String jstId;

    private String tell;

    private Double score;

    private String logo;

    private String icon;

    private Integer trfieldCount;

    private BigDecimal lge;

    private BigDecimal lae;

    private Integer orderNum;

    private String extra;

    private Integer storeCount;

    private Integer packageCount;

    private Integer showState;

    private String image;

    private String schoolInfo;

    private String regInfo;
	
	private List<WechatEnrollPackageWithBLOBs> wechatEnrollPackageList;

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCoachCount() {
		return coachCount;
	}

	public void setCoachCount(Integer coachCount) {
		this.coachCount = coachCount;
	}

	public Integer getCarCount() {
		return carCount;
	}

	public void setCarCount(Integer carCount) {
		this.carCount = carCount;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getJstId() {
		return jstId;
	}

	public void setJstId(String jstId) {
		this.jstId = jstId;
	}

	public String getTell() {
		return tell;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
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

	public Integer getShowState() {
		return showState;
	}

	public void setShowState(Integer showState) {
		this.showState = showState;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public List<WechatEnrollPackageWithBLOBs> getWechatEnrollPackageList() {
		return wechatEnrollPackageList;
	}

	public void setWechatEnrollPackageList(List<WechatEnrollPackageWithBLOBs> wechatEnrollPackageList) {
		this.wechatEnrollPackageList = wechatEnrollPackageList;
	}

	
}