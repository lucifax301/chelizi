package com.lili.student.model;

import com.lili.cms.entity.BaseEntity;

public class StudentNBDTO extends BaseEntity{

	private static final long serialVersionUID = -1559190056424412546L;

	/**
	 * 学员ID
	 */
	private long studentId;
	
	/**
	 * 身份证号
	 */
	private String idNumber;
	
	/**
	 * 学员姓名
	 */
	private String  name;
	
	
	/**
	 * 学员电话
	 */
	private String phoneNum;
	
	
	/**
	 * 申请的车牌类型
	 */
	private int applyCarType;

	/**
	 * 驾驶类型（1代表C1,2代表C2,3代表其它）
	 */
	private Integer drType;
	
	/**
	 * 车管所报名状态：0-未报名；1-已报名成功；2-报名审核中
	 */
	private Integer applyexam;
	
	/**
	 * 是否设置了支付密码- 0-未设置；1-已设置
	 */
	private Integer pwstate;
	/**
	 * 学员所属城市id
	 */
	private Integer cityId;
	
	private String coachPhoneNum;
	
	public String getCoachPhoneNum() {
		return coachPhoneNum;
	}
	public void setCoachPhoneNum(String coachPhoneNum) {
		this.coachPhoneNum = coachPhoneNum;
	}
	public long getStudentId() {
		return studentId;
	}
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
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
	public int getApplyCarType() {
		return applyCarType;
	}
	public void setApplyCarType(int applyCarType) {
		this.applyCarType = applyCarType;
	}
	public Integer getDrType() {
		return drType;
	}
	public void setDrType(Integer drType) {
		this.drType = drType;
	}
	public Integer getApplyexam() {
		return applyexam;
	}
	public void setApplyexam(Integer applyexam) {
		this.applyexam = applyexam;
	}
	public Integer getPwstate() {
		return pwstate;
	}
	public void setPwstate(Integer pwstate) {
		this.pwstate = pwstate;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	

	
}
