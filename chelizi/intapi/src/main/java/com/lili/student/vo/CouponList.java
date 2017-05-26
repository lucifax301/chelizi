package com.lili.student.vo;

import java.io.Serializable;

public class CouponList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7377932752999099003L;

	/* 本次赠送优惠券名，多个以|线分割 */
	private String couponName;
	/* 本次赠送优惠券ID */
	private String couponId;
	/* 本次赠送优惠券数量 */
	private String couponNum;

	private Integer moneyValue;
	
	private Integer discount;
	
	private Integer type;
	
	private Integer limitValue;

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getCouponNum() {
		return couponNum;
	}

	public void setCouponNum(String couponNum) {
		this.couponNum = couponNum;
	}

	public Integer getMoneyValue() {
		return moneyValue;
	}

	public void setMoneyValue(Integer moneyValue) {
		this.moneyValue = moneyValue;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getLimitValue() {
		return limitValue;
	}

	public void setLimitValue(Integer limitValue) {
		this.limitValue = limitValue;
	}
}
