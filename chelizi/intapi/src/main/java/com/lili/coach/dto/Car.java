package com.lili.coach.dto;

import java.io.Serializable;
import java.util.Date;

public class Car implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2306954747784678117L;

	private Integer carId;

    private String carType;

    private Integer age;

    private Integer mileage;

    private Integer innerEnvir;

    private Integer performance;

    private String carNo;

    private Integer carLevel;

    private Byte driveType;

    private String driveNumber;

    private String drivePhoto;

    private String usePerson;

    private Integer schoolId;

    private Byte syncType;

    private Date syncTime;

    private Integer cityId;

    private String carOwner;

    private String carOwnerAddr;

    private String carUseType;

    private String carEngineNo;

    private String carVin;

    private Date carRegTime;

    private Date carIssueTime;

    private Integer brandId;

    private String brandName;

    private Byte carColor;

    private Byte carPower;

    private String carImg;

    private Date verifyStart;

    private Date verifyEnd;

    private Integer verifyFee;

    private Byte verifyState;

    private Byte insuranceType;

    private Integer insuranceMoney;

    private Date insuranceStart;

    private Date insuranceEnd;

    private String insuranceCo;
    
    private Integer type;

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
        this.carType = carType == null ? null : carType.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Integer getInnerEnvir() {
        return innerEnvir;
    }

    public void setInnerEnvir(Integer innerEnvir) {
        this.innerEnvir = innerEnvir;
    }

    public Integer getPerformance() {
        return performance;
    }

    public void setPerformance(Integer performance) {
        this.performance = performance;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo == null ? null : carNo.trim();
    }

    public Integer getCarLevel() {
        return carLevel;
    }

    public void setCarLevel(Integer carLevel) {
        this.carLevel = carLevel;
    }

    public Byte getDriveType() {
        return driveType;
    }

    public void setDriveType(Byte driveType) {
        this.driveType = driveType;
    }

    public String getDriveNumber() {
        return driveNumber;
    }

    public void setDriveNumber(String driveNumber) {
        this.driveNumber = driveNumber == null ? null : driveNumber.trim();
    }

    public String getDrivePhoto() {
        return drivePhoto;
    }

    public void setDrivePhoto(String drivePhoto) {
        this.drivePhoto = drivePhoto == null ? null : drivePhoto.trim();
    }

    public String getUsePerson() {
        return usePerson;
    }

    public void setUsePerson(String usePerson) {
        this.usePerson = usePerson == null ? null : usePerson.trim();
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Byte getSyncType() {
        return syncType;
    }

    public void setSyncType(Byte syncType) {
        this.syncType = syncType;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner == null ? null : carOwner.trim();
    }

    public String getCarOwnerAddr() {
        return carOwnerAddr;
    }

    public void setCarOwnerAddr(String carOwnerAddr) {
        this.carOwnerAddr = carOwnerAddr == null ? null : carOwnerAddr.trim();
    }

    public String getCarUseType() {
        return carUseType;
    }

    public void setCarUseType(String carUseType) {
        this.carUseType = carUseType == null ? null : carUseType.trim();
    }

    public String getCarEngineNo() {
        return carEngineNo;
    }

    public void setCarEngineNo(String carEngineNo) {
        this.carEngineNo = carEngineNo == null ? null : carEngineNo.trim();
    }

    public String getCarVin() {
        return carVin;
    }

    public void setCarVin(String carVin) {
        this.carVin = carVin == null ? null : carVin.trim();
    }

    public Date getCarRegTime() {
        return carRegTime;
    }

    public void setCarRegTime(Date carRegTime) {
        this.carRegTime = carRegTime;
    }

    public Date getCarIssueTime() {
        return carIssueTime;
    }

    public void setCarIssueTime(Date carIssueTime) {
        this.carIssueTime = carIssueTime;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public Byte getCarColor() {
        return carColor;
    }

    public void setCarColor(Byte carColor) {
        this.carColor = carColor;
    }

    public Byte getCarPower() {
        return carPower;
    }

    public void setCarPower(Byte carPower) {
        this.carPower = carPower;
    }

    public String getCarImg() {
        return carImg;
    }

    public void setCarImg(String carImg) {
        this.carImg = carImg == null ? null : carImg.trim();
    }

    public Date getVerifyStart() {
        return verifyStart;
    }

    public void setVerifyStart(Date verifyStart) {
        this.verifyStart = verifyStart;
    }

    public Date getVerifyEnd() {
        return verifyEnd;
    }

    public void setVerifyEnd(Date verifyEnd) {
        this.verifyEnd = verifyEnd;
    }

    public Integer getVerifyFee() {
        return verifyFee;
    }

    public void setVerifyFee(Integer verifyFee) {
        this.verifyFee = verifyFee;
    }

    public Byte getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(Byte verifyState) {
        this.verifyState = verifyState;
    }

    public Byte getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(Byte insuranceType) {
        this.insuranceType = insuranceType;
    }

    public Integer getInsuranceMoney() {
        return insuranceMoney;
    }

    public void setInsuranceMoney(Integer insuranceMoney) {
        this.insuranceMoney = insuranceMoney;
    }

    public Date getInsuranceStart() {
        return insuranceStart;
    }

    public void setInsuranceStart(Date insuranceStart) {
        this.insuranceStart = insuranceStart;
    }

    public Date getInsuranceEnd() {
        return insuranceEnd;
    }

    public void setInsuranceEnd(Date insuranceEnd) {
        this.insuranceEnd = insuranceEnd;
    }

    public String getInsuranceCo() {
        return insuranceCo;
    }

    public void setInsuranceCo(String insuranceCo) {
        this.insuranceCo = insuranceCo == null ? null : insuranceCo.trim();
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}