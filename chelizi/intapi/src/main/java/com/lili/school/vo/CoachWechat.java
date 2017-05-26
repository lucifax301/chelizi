package com.lili.school.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 教练认证实体
 * @author yu.y
 *
 */
public class CoachWechat implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1904380337146950349L;

	/**
	 * 教练id
	 */
	private Long coachId;
	
	/**
	 * 驾校id
	 */
	private Integer schoolId;
	
	/**
	 * 驾校名称：注册教练专属
	 */
	private String schoolName;
	
	/**
	 * 教练注册所有城市
	 */
	private Integer cityId;
	
	/**
	 * 注册城市名称
	 */
	private String cityName;
	
	/**
	 * 名字
	 */
	private String name;
	
	/**
	 * 电话号码
	 */
	private String phoneNum;
	
	/**
	 * 头像
	 */
	private String headIcon;
	
	private String headIconStr;
	
    private String area;
    
    private String coachTag;
    
    private String profile;
    
    private String qrcode;
    
    private Integer checkIdState;
    
    private Integer checkDrState;
    
    private Integer coachAge;
    
    private Integer schoolAge;
    
    
    private List<WechatEnrollClass> wechatEnrollClassList;

	public Long getCoachId() {
		return coachId;
	}

	public void setCoachId(Long coachId) {
		this.coachId = coachId;
	}

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

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

	public String getHeadIcon() {
		return headIcon;
	}

	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCoachTag() {
		return coachTag;
	}

	public void setCoachTag(String coachTag) {
		this.coachTag = coachTag;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public List<WechatEnrollClass> getWechatEnrollClassList() {
		return wechatEnrollClassList;
	}

	public void setWechatEnrollClassList(List<WechatEnrollClass> wechatEnrollClassList) {
		this.wechatEnrollClassList = wechatEnrollClassList;
	}

	public String getHeadIconStr() {
		return headIconStr;
	}

	public void setHeadIconStr(String headIconStr) {
		this.headIconStr = headIconStr;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public Integer getCheckIdState() {
		return checkIdState;
	}

	public void setCheckIdState(Integer checkIdState) {
		this.checkIdState = checkIdState;
	}

	public Integer getCheckDrState() {
		return checkDrState;
	}

	public void setCheckDrState(Integer checkDrState) {
		this.checkDrState = checkDrState;
	}

	public Integer getCoachAge() {
		return coachAge;
	}

	public void setCoachAge(Integer coachAge) {
		this.coachAge = coachAge;
	}

	public Integer getSchoolAge() {
		return schoolAge;
	}

	public void setSchoolAge(Integer schoolAge) {
		this.schoolAge = schoolAge;
	}


}
