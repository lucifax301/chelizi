package com.lili.exam.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ExamPlacePayOrder implements Serializable {

	private String payorderId;
	
	private int placeId;
	
	private int coachId;
	
	private String coachName;
	
	private String coachMobile;
	
	private int duration;
	
	private int payTotal;
	
	private Date payTime;
	
	private Integer state;
	
	private Date ctime;
	
	private Date mtime;
	
	private Date btime;
	
	private Date etime;
	
	private List<ExamPlaceOrder> orders;
	
	
	

	public Date getBtime() {
		return btime;
	}

	public void setBtime(Date btime) {
		this.btime = btime;
	}

	public Date getEtime() {
		return etime;
	}

	public void setEtime(Date etime) {
		this.etime = etime;
	}

	public List<ExamPlaceOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<ExamPlaceOrder> orders) {
		this.orders = orders;
	}

	public String getPayorderId() {
		return payorderId;
	}

	public void setPayorderId(String payorderId) {
		this.payorderId = payorderId;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public int getCoachId() {
		return coachId;
	}

	public void setCoachId(int coachId) {
		this.coachId = coachId;
	}

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	public String getCoachMobile() {
		return coachMobile;
	}

	public void setCoachMobile(String coachMobile) {
		this.coachMobile = coachMobile;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getPayTotal() {
		return payTotal;
	}

	public void setPayTotal(int payTotal) {
		this.payTotal = payTotal;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getMtime() {
		return mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}
	
	
}
