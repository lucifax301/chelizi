package com.lili.school.model;

import java.util.Date;

import com.lili.cms.entity.BaseEntity;

public class Car extends BaseEntity{


	private static final long serialVersionUID = -3077919685148077683L;

	private Long carId;

	//车类型
	private String carType;
	private String carNo;
	private String driveNumber;
	private String drivePhoto;
	private String UsePerson;
	//所属驾校
	private String schoolName;
	//所属教练名
	private String coachName;

	//车龄
	private Integer age;
	//里程
	private Integer mileage;
	//性能（学员评价）
	private Integer performance;
	//汽车等级
	private Integer carLevel;
	//驾驶类型（1代表C1,2代表C2,3代表其它）
	private Byte driveType;

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
	
	private String innerEnvir ;		
	

	public String getInnerEnvir() {
		return innerEnvir;
	}
	public void setInnerEnvir(String innerEnvir) {
		this.innerEnvir = innerEnvir;
	}
	public String getUsePerson() {
		return UsePerson;
	}
	public void setUsePerson(String usePerson) {
		UsePerson = usePerson;
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
		this.carOwner = carOwner;
	}
	public String getCarOwnerAddr() {
		return carOwnerAddr;
	}
	public void setCarOwnerAddr(String carOwnerAddr) {
		this.carOwnerAddr = carOwnerAddr;
	}
	public String getCarUseType() {
		return carUseType;
	}
	public void setCarUseType(String carUseType) {
		this.carUseType = carUseType;
	}
	public String getCarEngineNo() {
		return carEngineNo;
	}
	public void setCarEngineNo(String carEngineNo) {
		this.carEngineNo = carEngineNo;
	}
	public String getCarVin() {
		return carVin;
	}
	public void setCarVin(String carVin) {
		this.carVin = carVin;
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
		this.brandName = brandName;
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
		this.carImg = carImg;
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
		this.insuranceCo = insuranceCo;
	}
	public String getCoachName() {
		return coachName;
	}
	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Long getCarId() {
		return carId;
	}
	public void setCarId(Long carId) {
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
	public String getDriveNumber() {
		return driveNumber;
	}
	public void setDriveNumber(String driveNumber) {
		this.driveNumber = driveNumber;
	}
	public String getDrivePhoto() {
		return drivePhoto;
	}
	public void setDrivePhoto(String drivePhoto) {
		this.drivePhoto = drivePhoto;
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
	public Integer getPerformance() {
		return performance;
	}
	public void setPerformance(Integer performance) {
		this.performance = performance;
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

	public String getCarLevelEnum(Integer num){
		CarLevelEnum[] values = CarLevelEnum.values();
		for(int i = 0;i < values.length;i ++){
			if(num == values[i].num){
				return values[i].getName();
			}
		}
		return "";
	}

	public enum CarLevelEnum{
		ALL("全部",99), NORMAL("普通",1), COMFORTABLE("已支付",1), LUXURY("豪华",2);

		private CarLevelEnum(String name, int num) {
			this.name = name;
			this.num = num;
		}
		private String name;
		private int num;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}

		@Override
		public String toString() {
			return "name = " + name + ",num = " + num;
		}
	}
}
