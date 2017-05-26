package com.lili.exam.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 当日排班情况
 * @author yangpeng
 *
 */
public class ExamPlaceClassDate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date pdate;
	
	private Integer cls;
	
	private Integer day;

	public Date getPdate() {
		return pdate;
	}

	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}

	public Integer getCls() {
		return cls;
	}

	public void setCls(Integer cls) {
		this.cls = cls;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}
	
	
}
