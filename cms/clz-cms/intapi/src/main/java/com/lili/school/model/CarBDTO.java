package com.lili.school.model;

import com.lili.cms.entity.BasePagedEntity;


/**
 * 车辆分页查询实体
 * @author hughes
 *
 */
public class CarBDTO extends BasePagedEntity{

	private static final long serialVersionUID = 1L;

	private long carId;
	
	
	//车类型
	private String carType;

	private String carNo;
	
	//汽车等级
	private Integer carLevel;
	
	private String exportIds;
	private String carIds;
	
	
	public String getCarIds() {
		return carIds;
	}
	public void setCarIds(String carIds) {
		this.carIds = carIds;
	}
	public String getExportIds() {
		return exportIds;
	}
	public void setExportIds(String exportIds) {
		this.exportIds = exportIds;
	}
	public long getCarId() {
		return carId;
	}
	public void setCarId(long carId) {
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
	
	
}
