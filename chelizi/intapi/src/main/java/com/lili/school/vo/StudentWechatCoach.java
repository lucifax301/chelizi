package com.lili.school.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 教练认证实体
 * @author yu.y
 *
 */
public class StudentWechatCoach implements Serializable{

	
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
	
	private Integer coachAge;
	
	private Integer schoolAge;
	
	private Integer payState;
	
	private Integer wxstatus;
	//关联状态：0-微信未绑；1-微信已绑定；2-微信绑定手机，3-微信已解绑
	
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
	
    private String area;
    
    private String coachTag;
    
    private String profile;
    
    private String qrcode;
    
    private String importSrc;
    
    private Integer checkDrState;
    
    private List<WechatEnrollClass> wechatEnrollClassList;
    
    private List<WechatOrder> orderList;

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

	public Integer getCoachAge() {
		return coachAge;
	}

	public void setCoachAge(Integer coachAge) {
		this.coachAge = coachAge;
	}

	public Integer getPayState() {
		return payState;
	}

	public void setPayState(Integer payState) {
		this.payState = payState;
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

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}


	public Integer getWxstatus() {
		return wxstatus;
	}

	public void setWxstatus(Integer wxstatus) {
		this.wxstatus = wxstatus;
	}

	public Integer getSchoolAge() {
		return schoolAge;
	}

	public void setSchoolAge(Integer schoolAge) {
		this.schoolAge = schoolAge;
	}

	public String getImportSrc() {
		return importSrc;
	}

	public void setImportSrc(String importSrc) {
		this.importSrc = importSrc;
	}

	public Integer getCheckDrState() {
		return checkDrState;
	}

	public void setCheckDrState(Integer checkDrState) {
		this.checkDrState = checkDrState;
	}

	public List<WechatEnrollClass> getWechatEnrollClassList() {
		return wechatEnrollClassList;
	}

	public void setWechatEnrollClassList(List<WechatEnrollClass> wechatEnrollClassList) {
		this.wechatEnrollClassList = wechatEnrollClassList;
	}

	public List<WechatOrder> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<WechatOrder> orderList) {
		this.orderList = orderList;
	}



}
