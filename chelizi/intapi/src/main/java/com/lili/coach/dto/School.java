package com.lili.coach.dto;

import java.io.Serializable;

/**
 * 驾校实体
 * @author yu.y
 *
 */
public class School implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -462575708316312275L;
	
	/**
	 * 驾校id
	 */
	private Integer schoolId;
	
	/**
	 * 驾校名称
	 */
	private String name;
	
	/**
	 * 图像
	 */
	private String image;
	
	/**
	 * 教练数目
	 */
	private Integer coachCount;
	
	/**
	 * 教练车数目
	 */
	private Integer carCount;
	/**
	 * 驾校所在城市id
	 */
	private Integer cityId;
	
	private String address;
	private String tell;
	
	private String phoneNum;

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	

}
