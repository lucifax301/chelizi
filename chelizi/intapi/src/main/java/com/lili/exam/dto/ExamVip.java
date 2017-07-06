package com.lili.exam.dto;

import java.io.Serializable;

public class ExamVip implements Serializable {

	private int id;
	
	private String name;
	
	private String mobile;
	
	private int c1count;
	
	private int c2count;
	
	private String school;
	
	private int schoolId;
	
	private int state;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getC1count() {
		return c1count;
	}

	public void setC1count(int c1count) {
		this.c1count = c1count;
	}

	public int getC2count() {
		return c2count;
	}

	public void setC2count(int c2count) {
		this.c2count = c2count;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	
}
