package com.lili.sync.dto;

import java.util.Date;

public class SGCoach {

	private String coachId;
	private String idNumber;
	private String name;
	private Byte sex;
	private String hometown;
	private String phoneNum;
	private String mailBox;
	private Integer StuCoachID;
	private String coachCard;
	private Date coachCardDate;
	private String importSrc;
	private Integer schoolId;
	private Integer cityId;
	private String password;
	
	public String getCoachId() {
		return coachId;
	}
	public void setCoachId(String coachId) {
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
	public Byte getSex() {
		return sex;
	}
	public void setSex(Byte sex) {
		this.sex = sex;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getMailBox() {
		return mailBox;
	}
	public void setMailBox(String mailBox) {
		this.mailBox = mailBox;
	}
	public Integer getStuCoachID() {
		return StuCoachID;
	}
	public void setStuCoachID(Integer stuCoachID) {
		StuCoachID = stuCoachID;
	}
	public String getCoachCard() {
		return coachCard;
	}
	public void setCoachCard(String coachCard) {
		this.coachCard = coachCard;
	}
	public Date getCoachCardDate() {
		return coachCardDate;
	}
	public void setCoachCardDate(Date coachCardDate) {
		this.coachCardDate = coachCardDate;
	}
	public String getImportSrc() {
		return importSrc;
	}
	public void setImportSrc(String importSrc) {
		this.importSrc = importSrc;
	}
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getHometown() {
		return hometown;
	}
	public void setHometown(String hometown) {
		this.hometown = hometown;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
