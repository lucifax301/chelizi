package com.lili.finance.model.common;


import java.util.Date;

public class UserMoney {


	private Integer id;

	private Long userId;

	private Integer userType;

	private Integer operateType;

	private Integer changeValue;

	private Date operateTime;

	private String payWay;

	private Integer leftValue;

	private String remark;

	private String orderId;

	private Integer status;
	
	private Integer saveValue;
	
	private Integer expendValue;
	
	private Integer isEarning;
	
	private Integer isBalance;
	
    private String tranObject;

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

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public Integer getChangeValue() {
		return changeValue;
	}

	public void setChangeValue(Integer changeValue) {
		this.changeValue = changeValue;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public Integer getLeftValue() {
		return leftValue;
	}

	public void setLeftValue(Integer leftValue) {
		this.leftValue = leftValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSaveValue() {
		return saveValue;
	}

	public void setSaveValue(Integer saveValue) {
		this.saveValue = saveValue;
	}

	public Integer getExpendValue() {
		return expendValue;
	}

	public void setExpendValue(Integer expendValue) {
		this.expendValue = expendValue;
	}

	public Integer getIsEarning() {
		return isEarning;
	}

	public void setIsEarning(Integer isEarning) {
		this.isEarning = isEarning;
	}

	public Integer getIsBalance() {
		return isBalance;
	}

	public void setIsBalance(Integer isBalance) {
		this.isBalance = isBalance;
	}

	public String getTranObject() {
		return tranObject;
	}

	public void setTranObject(String tranObject) {
		this.tranObject = tranObject;
	}

}
