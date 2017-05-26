package com.lili.coach.dto;

import java.io.Serializable;

public class CarInfo implements Serializable {
    private Integer carId;

    private String carType;
    
    private String carNo;
    
    private Byte driveType;

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

	public Byte getDriveType() {
		return driveType;
	}

	public void setDriveType(Byte driveType) {
		this.driveType = driveType;
	}


    
    
    
}
