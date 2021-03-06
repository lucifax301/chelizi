package com.lili.exam.dto;

import java.io.Serializable;
import java.util.Date;

public class ExamVip implements Serializable {

	private int id;
	
	private String name;
	
	private String mobile;
	
	private int c1count;
	
	private int c2count;
	
	private String school;
	
	private int schoolId;
	
	private int state;
	
	private Date ctime;
	
	private String extra;
	
	private Date mtime;
	
	

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public Date getMtime() {
		return mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

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
