package com.lili.coach.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 教练实体
 * @author yu.y
 *
 */
public class CoachVo implements Serializable{
	
	private static final long serialVersionUID = 1868060372533568727L;

	/**
	 * 教练id
	 */
	private long coachId;

	private String schoolName;
	private int schoolId;
	private int studentAmount;
	
	/**
	 * 身份证号
	 */
	private String idNumber;
	
	/**
	 * 名字
	 */
	private String name;
	
	/**
	 * 性别
	 */
	private int sex;
	
	/**
	 * 年龄
	 */
	private int age;
	
	/**
	 * 电话号码
	 */
	private String phoneNum;
	
	
	private Integer driveType;
	
	/**
	 * 星级
	 */
	private int starLevel;

	/**
	 * 现约时长（分钟）
	 */
	private int curTime;

	/**
	 * 预约时长（分钟）
	 */
	private int preTime;
    
    private Integer state;
    
    private Date reviveTime;

    private String vipPackageId;
    
    private Date registerTime;
    
    private int drType;
    
    
    
	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public int getDrType() {
		return drType;
	}

	public void setDrType(int drType) {
		this.drType = drType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getReviveTime() {
		return reviveTime;
	}

	public void setReviveTime(Date reviveTime) {
		this.reviveTime = reviveTime;
	}

	public String getVipPackageId() {
		return vipPackageId;
	}

	public void setVipPackageId(String vipPackageId) {
		this.vipPackageId = vipPackageId;
	}

	public int getCurTime() {
		return curTime;
	}

	public void setCurTime(int curTime) {
		this.curTime = curTime;
	}

	public int getPreTime() {
		return preTime;
	}

	public void setPreTime(int preTime) {
		this.preTime = preTime;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public int getStudentAmount() {
		return studentAmount;
	}

	public void setStudentAmount(int studentAmount) {
		this.studentAmount = studentAmount;
	}

	public int getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(int starLevel) {
		this.starLevel = starLevel;
	}

	public Integer getDriveType() {
		return driveType;
	}

	public void setDriveType(Integer driveType) {
		this.driveType = driveType;
	}

	public long getCoachId() {
		return coachId;
	}

	public void setCoachId(long coachId) {
		this.coachId = coachId;
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

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}


}
