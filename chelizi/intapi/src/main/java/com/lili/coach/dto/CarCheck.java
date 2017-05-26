package com.lili.coach.dto;

import java.io.Serializable;
import java.util.Date;

public class CarCheck implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -517882160125185469L;
	
	private Integer id;
	
	private Long coachId;

	private Integer carId;

    private String carNo;

    private Integer driveType;

    private Integer isNewCarDrive;
    
    private Integer type;
    
    private String drivePhoto;
    
    private String drivePhoto2;
    
    private Integer isNewDrivePhoto;

    private String carImg;
    
    private Integer isNewCarImg;
    
    private Date ctime;

    private String creator;

    private Date utime;

    private String updater;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getCoachId() {
		return coachId;
	}

	public void setCoachId(Long coachId) {
		this.coachId = coachId;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getUtime() {
		return utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

}