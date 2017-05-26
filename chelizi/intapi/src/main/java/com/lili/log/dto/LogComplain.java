package com.lili.log.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 投诉实体
 * @author yu.y
 *
 */
public class LogComplain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5260859463281373978L;
	
	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 用户类型
	 */
	private Integer userType;
	
	/**
	 * 创建时间
	 */
	private Date time;
	
	/**
	 * 投诉内容
	 */
	private String description;
	
	/**
	 * 订单id
	 */
	private String orderId;
	
	/**
	 * 投诉的对象id
	 */
	private Long targetId;
	
	/**
	 * 投诉的对象类型
	 */
	private Integer targetType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Integer getTargetType() {
		return targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
