package com.lili.school.model;

import com.lili.cms.entity.BaseEntity;


/**
 * 车辆非分页查询实体,查询属性多个需使用该实体
 * @author hughes
 *
 */
public class CarNBDTO extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private Long carId;
	
	private Long studentId;
	
	private String carNo;

	private String driveNumber;
	
	/**
	 * 所属教练手机号
	 */
	private String carOwner;

	public String getCarOwner() {
		return carOwner;
	}

	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getDriveNumber() {
		return driveNumber;
	}

	public void setDriveNumber(String driveNumber) {
		this.driveNumber = driveNumber;
	}
	
	
}
