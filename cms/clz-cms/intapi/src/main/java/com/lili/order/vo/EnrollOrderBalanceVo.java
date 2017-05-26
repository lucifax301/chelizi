package com.lili.order.vo;


import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

public class EnrollOrderBalanceVo extends BasePagedEntity{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4434578729665196246L;

	private String orderId;

    private Long studentId;

    private String name;

    private Integer payState;

    private Integer postState;
    
    private Integer applyexam;
    
    private Integer applystate;

    private Integer price;

    private String schoolName;
    
    private String phoneNum;
    
    private Date checkoutTime;
    
    private String checkouter;
    
    private String changeValue;
    
    private Byte orderState;
    
    private Integer brokerage;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPayState() {
		return payState;
	}

	public void setPayState(Integer payState) {
		this.payState = payState;
	}

	public Integer getPostState() {
		return postState;
	}

	public void setPostState(Integer postState) {
		this.postState = postState;
	}

	public Integer getApplyexam() {
		return applyexam;
	}

	public void setApplyexam(Integer applyexam) {
		this.applyexam = applyexam;
	}

	public Integer getApplystate() {
		return applystate;
	}

	public void setApplystate(Integer applystate) {
		this.applystate = applystate;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getCheckouter() {
		return checkouter;
	}

	public void setCheckouter(String checkouter) {
		this.checkouter = checkouter;
	}

	public String getChangeValue() {
		return changeValue;
	}

	public void setChangeValue(String changeValue) {
		this.changeValue = changeValue;
	}


	public Byte getOrderState() {
		return orderState;
	}

	public void setOrderState(Byte orderState) {
		this.orderState = orderState;
	}

	public Integer getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(Integer brokerage) {
		this.brokerage = brokerage;
	}

	public Date getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(Date checkoutTime) {
		this.checkoutTime = checkoutTime;
	}
    
}