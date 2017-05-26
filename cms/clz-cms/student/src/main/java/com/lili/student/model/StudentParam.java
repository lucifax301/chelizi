package com.lili.student.model;

import com.lili.cms.entity.BaseEntity;

public class StudentParam extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private long id;
	private long c_coachId;
	private long o_coachId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getC_coachId() {
		return c_coachId;
	}
	public void setC_coachId(long c_coachId) {
		this.c_coachId = c_coachId;
	}
	public long getO_coachId() {
		return o_coachId;
	}
	public void setO_coachId(long o_coachId) {
		this.o_coachId = o_coachId;
	}
	
	
}
