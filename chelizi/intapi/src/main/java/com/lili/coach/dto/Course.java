package com.lili.coach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程实体
 * @author yu.y
 *
 */
public class Course implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8914866692720262850L;
	
	/**
	 * 课程id
	 */
	private Integer courseId;
	
	/**
	 * 训练场id
	 */
	private Integer trainFieldId;
	
	/**
	 * 课程模板id
	 */
	private Integer courseTmpId;
	
	/**
	 * 订单id
	 */
	private String orderId;
	
	/**
	 * 课程开始时间
	 */
	private Date startTime;
	
	/**
	 * 课程的状态
	 */
	private Integer courseState;

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getTrainFieldId() {
		return trainFieldId;
	}

	public void setTrainFieldId(Integer trainFieldId) {
		this.trainFieldId = trainFieldId;
	}

	public Integer getCourseTmpId() {
		return courseTmpId;
	}

	public void setCourseTmpId(Integer courseTmpId) {
		this.courseTmpId = courseTmpId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getCourseState() {
		return courseState;
	}

	public void setCourseState(Integer courseState) {
		this.courseState = courseState;
	}
	
}
