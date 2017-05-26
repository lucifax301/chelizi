package com.lili.school.model;

import com.lili.cms.entity.BaseEntity;

public class CarCheckVo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1668859565291211124L;

	private Integer carId;
	
	private String drivePhoto;
	
	private String drivePhoto2;
	
	private Integer isNewDrivePhoto;
	
	private String carNo;
	
	private Integer driveType;
	
	private Integer isNewCarDrive;
	
    private String carImg;
    
    private Integer isNewCarImg;

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public String getDrivePhoto() {
		return drivePhoto;
	}

	public void setDrivePhoto(String drivePhoto) {
		this.drivePhoto = drivePhoto;
	}

	public String getDrivePhoto2() {
		return drivePhoto2;
	}

	public void setDrivePhoto2(String drivePhoto2) {
		this.drivePhoto2 = drivePhoto2;
	}

	public Integer getIsNewDrivePhoto() {
		return isNewDrivePhoto;
	}

	public void setIsNewDrivePhoto(Integer isNewDrivePhoto) {
		this.isNewDrivePhoto = isNewDrivePhoto;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public Integer getDriveType() {
		return driveType;
	}

	public void setDriveType(Integer driveType) {
		this.driveType = driveType;
	}

	public Integer getIsNewCarDrive() {
		return isNewCarDrive;
	}

	public void setIsNewCarDrive(Integer isNewCarDrive) {
		this.isNewCarDrive = isNewCarDrive;
	}

	public String getCarImg() {
		return carImg;
	}

	public void setCarImg(String carImg) {
		this.carImg = carImg;
	}

	public Integer getIsNewCarImg() {
		return isNewCarImg;
	}

	public void setIsNewCarImg(Integer isNewCarImg) {
		this.isNewCarImg = isNewCarImg;
	}
}
