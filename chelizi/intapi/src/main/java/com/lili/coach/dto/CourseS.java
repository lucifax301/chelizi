package com.lili.coach.dto;

import java.io.Serializable;

/**
 * 课程实体
 * @author yu.y
 *
 */
public class CourseS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5296984239193445676L;
	
	/**
	 * 课程id
	 */
	private Integer courseTmpId;
	
	/**
	 * 课程名称
	 */
	private String name;
	
	/**
	 * 课程描述
	 */
	private String desc;
	
	/**
	 * 课程单价
	 */
	private Integer price;
	
	/**
	 * 课程持续时间
	 */
	private Integer last;

	public Integer getCourseTmpId() {
		return courseTmpId;
	}

	public void setCourseTmpId(Integer courseTmpId) {
		this.courseTmpId = courseTmpId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getLast() {
		return last;
	}

	public void setLast(Integer last) {
		this.last = last;
	}
	
}
