package com.lili.coach.model;

import java.util.Date;

import com.lili.cms.entity.BaseEntity;

public class CoachNBDTO extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private long coachId;
	private Date startTime;
	private Date endTime;
	
	public long getCoachId() {
		return coachId;
	}
	public void setCoachId(long coachId) {
		this.coachId = coachId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
