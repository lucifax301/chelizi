package com.lili.coach.model;

import java.io.Serializable;

/**
 * 教练详情下,绑定车的信息,CCO（Coach Car Order）
 * @author maes
 *
 */
public class CCOVo implements Serializable{

	private static final long serialVersionUID = -4461543146812774859L;

	private long coachId;

	private long carId;
	
	/**
	 * 驾驶类型（1代表C1,2代表C2,3代表其它）
	 */
	private int driveType;
	
	/**
	 * 预约上课次数
	 */
	private int pOrderNum;
	/**
	 * 预约上课时长(小时)
	 */
	private int pOrderTime;
	/**
	 * 现约上课次数
	 */
	private int nOrderNum;
	/**
	 * 现约上课时长(小时)
	 */
	private int nOrderTime;
	
	private String coachName;
	
	private String carNo;
	
	/**
	 * 车类型,大众捷达之类
	 */
	private String carType;

	public long getCoachId() {
		return coachId;
	}

	public void setCoachId(long coachId) {
		this.coachId = coachId;
	}

	public long getCarId() {
		return carId;
	}

	public void setCarId(long carId) {
		this.carId = carId;
	}

	public int getDriveType() {
		return driveType;
	}

	public void setDriveType(int driveType) {
		this.driveType = driveType;
	}

	public int getpOrderNum() {
		return pOrderNum;
	}

	public void setpOrderNum(int pOrderNum) {
		this.pOrderNum = pOrderNum;
	}

	public int getpOrderTime() {
		return pOrderTime;
	}

	public void setpOrderTime(int pOrderTime) {
		this.pOrderTime = pOrderTime;
	}

	public int getnOrderNum() {
		return nOrderNum;
	}

	public void setnOrderNum(int nOrderNum) {
		this.nOrderNum = nOrderNum;
	}

	public int getnOrderTime() {
		return nOrderTime;
	}

	public void setnOrderTime(int nOrderTime) {
		this.nOrderTime = nOrderTime;
	}

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}
	
	
	
}

