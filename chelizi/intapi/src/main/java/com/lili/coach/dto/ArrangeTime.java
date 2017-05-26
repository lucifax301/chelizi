package com.lili.coach.dto;

import java.io.Serializable;

/**
 * 排班时间实体
 * @author yu.y
 *
 */
public class ArrangeTime implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -649834521377934682L;

	/**
	 * 排班时间id
	 */
	private Integer id;
	
	/**
	 * 排班时间名称
	 */
	private String name;
	
	/**
	 * 排班开始时间
	 */
	private String start_time;
	
	/**
	 * 排班结束时间
	 */
	private String end_time;
	
	/**
	 * 排班状态 0-启用；1-关闭
	 */
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
}
