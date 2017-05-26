package com.lili.coach.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 教练、车辆中间表
 * @author yu.y
 *
 */
public class CoachCar implements Serializable{

	/**
	 * 
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2739189538793431384L;

	/**
	 * 教练id
	 */
	private Long coachId;
	
	/**
	 * 车辆id
	 */
	private Integer carId;
	
	/**
	 * 添加日期
	 */
	private Date addTime;
	
	/**
	 * 是否存在
	 */
	private Integer isExist;
	
	/**
	 * 数据同步类型(1更新2插入)
	 */
	private Integer syncType;
	
	/**
	 * 数据最后一次同步时间
	 */
	private Date syncTime;
	
	public Long getCoachId() {
		return coachId;
	}

	public void setCoachId(Long id) {
		this.coachId = id;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getIsExist() {
		return isExist;
	}

	public void setIsExist(Integer isExist) {
		this.isExist = isExist;
	}

	public Integer getSyncType() {
		return syncType;
	}

	public void setSyncType(Integer syncType) {
		this.syncType = syncType;
	}

	public Date getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}

}
