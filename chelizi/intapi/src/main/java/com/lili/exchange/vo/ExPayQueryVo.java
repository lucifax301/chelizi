package com.lili.exchange.vo;

import java.io.Serializable;

public class ExPayQueryVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String platnum;
	private String schoolId;
	private String flowCode;
	private String stuIdCard;
	private String coachIdCard;
	private String vehicleNo;
	private String trainningMode;
	private String beginTime;
	private String endTime;
	private String pageNo;
	private String pageSize;
	private String sort;
	private String order;
	
	public String getPlatnum() {
		return platnum;
	}
	public void setPlatnum(String platnum) {
		this.platnum = platnum;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getFlowCode() {
		return flowCode;
	}
	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}
	public String getStuIdCard() {
		return stuIdCard;
	}
	public void setStuIdCard(String stuIdCard) {
		this.stuIdCard = stuIdCard;
	}
	public String getCoachIdCard() {
		return coachIdCard;
	}
	public void setCoachIdCard(String coachIdCard) {
		this.coachIdCard = coachIdCard;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getTrainningMode() {
		return trainningMode;
	}
	public void setTrainningMode(String trainningMode) {
		this.trainningMode = trainningMode;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
}
