package com.lili.pay.vo;

import java.io.Serializable;
import java.util.Date;

public class PayMessage implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -4757402114968121121L;

	//用户ID
    private long userId;
    
    //用户类型
    private int userType;
    
    //支付数值(经过计算后最终需要付款的钱)
    private int payValue;
    
    //支付渠道
    private String payWay;
    
    //支付内部订单id
    private String payOrderId;
    
    //支付目的
    private PurposeType payPurpose;
    
    //优惠券ID
    private long couponId;
    
    //已经优惠的金额
    private int discountMoney;
    
    //客户端版本
    private PayClientVer clientVer;
    
    //备注信息
    private String remark;
    
    //平安保险订单号
    private String insuranceId;
    
    private int totalFee;
    
    private Date endTime;
    
    private String waterNum;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getPayValue() {
		return payValue;
	}

	public void setPayValue(int payValue) {
		this.payValue = payValue;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

	public PurposeType getPayPurpose() {
		return payPurpose;
	}

	public void setPayPurpose(PurposeType payPurpose) {
		this.payPurpose = payPurpose;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public int getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(int discountMoney) {
		this.discountMoney = discountMoney;
	}

	public PayClientVer getClientVer() {
		return clientVer;
	}

	public void setClientVer(PayClientVer clientVer) {
		this.clientVer = clientVer;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getWaterNum() {
		return waterNum;
	}

	public void setWaterNum(String waterNum) {
		this.waterNum = waterNum;
	}

	public String getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	};
	
	
}
