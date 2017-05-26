package com.lili.school.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单
 * @author yu.y
 *
 */
public class StudentWechatEnroll implements Serializable{

	
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
	
	/**
	 * 状态：0-未报名，1-有意向，2-已报名
	 */
	private Integer state;
	
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
    
    private Integer drtype;
    
    private String studentName;
    
    private Date payTime;
    
    private String studentPhone;
    
    private Integer payPrice;
    
    private Integer payState;
    
    private String payWay;
    
    private String orderId;
    
    private Integer schoolAge;
    
    private WechatEnrollClass wechatEnrollClass;


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


	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
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


	public Integer getDrtype() {
		return drtype;
	}


	public void setDrtype(Integer drtype) {
		this.drtype = drtype;
	}


	public String getStudentName() {
		return studentName;
	}


	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}


	public Date getPayTime() {
		return payTime;
	}


	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}


	public String getStudentPhone() {
		return studentPhone;
	}


	public void setStudentPhone(String studentPhone) {
		this.studentPhone = studentPhone;
	}


	public Integer getPayPrice() {
		return payPrice;
	}


	public void setPayPrice(Integer payPrice) {
		this.payPrice = payPrice;
	}


	public String getPayWay() {
		return payWay;
	}


	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}


	public Integer getPayState() {
		return payState;
	}


	public void setPayState(Integer payState) {
		this.payState = payState;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public Integer getSchoolAge() {
		return schoolAge;
	}


	public void setSchoolAge(Integer schoolAge) {
		this.schoolAge = schoolAge;
	}


	public WechatEnrollClass getWechatEnrollClass() {
		return wechatEnrollClass;
	}


	public void setWechatEnrollClass(WechatEnrollClass wechatEnrollClass) {
		this.wechatEnrollClass = wechatEnrollClass;
	}
    
    


}
