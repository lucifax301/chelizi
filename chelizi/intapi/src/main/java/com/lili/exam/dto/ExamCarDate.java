package com.lili.exam.dto;

import java.io.Serializable;
import java.util.List;

public class ExamCarDate implements Serializable {

	private int id;
	
	private String date;
	
	private String carlist;
	
	private int schoolId;
	
	private List<ExamCarState> cars;
	
	

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCarlist() {
		return carlist;
	}

	public void setCarlist(String carlist) {
		this.carlist = carlist;
	}

	public List<ExamCarState> getCars() {
		return cars;
	}

	public void setCars(List<ExamCarState> cars) {
		this.cars = cars;
	}
	
	
}
