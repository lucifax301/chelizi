package com.lili.coach.vo;

import java.io.Serializable;

public class DayDetailCarVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 车辆id
	 */
	private Integer carId;
	
	/**
	 * 车辆类型
	 */
	private String carType;
	
	/**
	 * 车牌号
	 */
	private String carNo;
	
	/**
	 * 汽车等级
	 */
	private Integer carLevel;
	
	/**
	 * 驾驶类型（1代表C1,2代表C2）
	 */
	private Integer driveType;

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
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

	public Integer getCarLevel() {
		return carLevel;
	}

	public void setCarLevel(Integer carLevel) {
		this.carLevel = carLevel;
	}

	public Integer getDriveType() {
		return driveType;
	}

	public void setDriveType(Integer driveType) {
		this.driveType = driveType;
	}
	


}
