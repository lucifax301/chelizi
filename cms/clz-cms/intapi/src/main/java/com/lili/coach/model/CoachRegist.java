package com.lili.coach.model;

import com.lili.cms.entity.BaseEntity;

/**
 * 教练实体
 * @author yu.y
 *
 */
public class CoachRegist extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2638131757899439992L;

	/**
	 * 名字
	 */
	private String name;
	
	/**
	 * 电话号码
	 */
	private String phoneNum;
	
	/**
	 * 性别
	 */
	private Integer sex;
	
	/**
	 * 教练id
	 */
	private long coachId;
	
	/**
	 * 教练注册所有城市
	 */
	private Integer cityId;
	
	private Integer checkDrState;
	
	//学员人数
	private Integer studentCount;
	
	private Integer state;
	
	private String cityName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public long getCoachId() {
		return coachId;
	}

	public void setCoachId(long coachId) {
		this.coachId = coachId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getCheckDrState() {
		return checkDrState;
	}

	public void setCheckDrState(Integer checkDrState) {
		this.checkDrState = checkDrState;
	}

	public Integer getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(Integer studentCount) {
		this.studentCount = studentCount;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	

}
