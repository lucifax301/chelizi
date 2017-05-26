package com.lili.sync.dto;

public class SGCar {

	private String carId;
	private String carType;
	private String carNo;
	private Byte driveType;
	private String UsePerson;
	private Integer schoolId;
	private Integer cityId;
	private Integer empId;
	
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public Byte getDriveType() {
		return driveType;
	}
	public void setDriveType(Byte driveType) {
		this.driveType = driveType;
	}
	public String getUsePerson() {
		return UsePerson;
	}
	public void setUsePerson(String usePerson) {
		UsePerson = usePerson;
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
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	
}
