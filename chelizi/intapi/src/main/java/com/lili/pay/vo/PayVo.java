/**
 *
 */
package com.lili.pay.vo;

import java.io.Serializable;

/**
 * @author linbo
 *         统一整理支付
 */
public class PayVo implements Serializable {
    private static final long serialVersionUID = -4118061263751669421L;

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
    //平安保险订单id
    private String insuranceId;
    /**
     * @param userId
     * @param userType
     * @param payValue
     * @param payWay
     * @param payOrderId
     * @param payPurpose
     * @param couponId
     */
    public PayVo(long userId, int userType, int payValue, String payWay, String payOrderId, int payPurpose,
            long couponId, int clientVer, String remark) {
	   super();
	   this.userId = userId;
	   this.userType = userType;
	   this.payValue = payValue;
	   this.payWay = payWay;
	   this.payOrderId = payOrderId;
	   setPayPurpose(payPurpose);
	   setClientVer(clientVer);
	   this.couponId = couponId;
	   this.remark = remark;
    }
    
    public PayVo(long userId, int userType, int payValue, String payWay, String payOrderId, int payPurpose,
                 long couponId, int clientVer, String remark,String insuranceId) {
        super();
        this.userId = userId;
        this.userType = userType;
        this.payValue = payValue;
        this.payWay = payWay;
        this.payOrderId = payOrderId;
        setPayPurpose(payPurpose);
        setClientVer(clientVer);
        this.couponId = couponId;
        this.remark = remark;
        this.insuranceId=insuranceId;
    }

    public int getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(int discountMoney) {
        this.discountMoney = discountMoney;
    }

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return the userType
     */
    public int getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(int userType) {
        this.userType = userType;
    }

    /**
     * @return the payValue
     */
    public int getPayValue() {
        return payValue;
    }

    /**
     * @param payValue the payValue to set
     */
    public void setPayValue(int payValue) {
        this.payValue = payValue;
    }

    /**
     * @return the payWay
     */
    public String getPayWay() {
        return payWay;
    }

    /**
     * @param payWay the payWay to set
     */
    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    /**
     * @return the payOrderId
     */
    public String getPayOrderId() {
        return payOrderId;
    }

    /**
     * @param payOrderId the payOrderId to set
     */
    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    /**
     * @return the payGoal
     */
    public PurposeType getPayPurpose() {
        return payPurpose;
    }

    /**
     * @param payPurpose the payGoal to set
     */
    public void setPayPurpose(int payPurpose) {
        this.payPurpose = PurposeType.parse(payPurpose);
    }

    /**
     * @return the couponId
     */
    public long getCouponId() {
        return couponId;
    }

    /**
     * @param couponId the couponId to set
     */
    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }

    /**
     * @return the clientVer
     */
    public PayClientVer getClientVer() {
        return clientVer;
    }

    /**
     * @param clientVer the clientVer to set
     */
    public void setClientVer(int clientVer) {
        this.clientVer = PayClientVer.parse(clientVer);
    }

    /**
     * @return the mark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param mark the mark to set
     */
    public void setRemark(String mark) {
        this.remark = mark;
    }
    
    

    public String getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}

	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PayVo [userId=" + userId + ", userType=" + userType + ", payValue=" + payValue + ", payWay=" + payWay
                + ", payOrderId=" + payOrderId + ", payPurpose=" + payPurpose + ", couponId=" + couponId
                + ", clientVer=" + clientVer + ", remark=" + remark + ", insuranceId=" + insuranceId + "]";
    }

}
