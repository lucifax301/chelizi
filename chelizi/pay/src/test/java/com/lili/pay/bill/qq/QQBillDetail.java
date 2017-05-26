/**
 * 
 */
package com.lili.pay.bill.qq;

import java.util.Date;

/**
 * @author linbo
 * QQ支付账单详情
 */
public class QQBillDetail {
	
	private Date payDate;			//支付成功时间
	private String cftOrder;		//财付通订单号
	private String mchOrder;		//商家订单号
	private String payType;			//支付类型
	private String bankOrder;		//银行订单号
	private int payMoney;			//支付金额（元）
	private String orderState;		//订单状态
	private String payDesc;			//交易说明
	
	
	/**
	 * @return the cftOrder
	 */
	public String getCftOrder() {
		return cftOrder;
	}
	/**
	 * @param cftOrder the cftOrder to set
	 */
	public void setCftOrder(String cftOrder) {
		this.cftOrder = cftOrder;
	}
	/**
	 * @return the mchOrder
	 */
	public String getMchOrder() {
		return mchOrder;
	}
	/**
	 * @param mchOrder the mchOrder to set
	 */
	public void setMchOrder(String mchOrder) {
		this.mchOrder = mchOrder;
	}
	/**
	 * @return the payType
	 */
	public String getPayType() {
		return payType;
	}
	/**
	 * @param payType the payType to set
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}
	/**
	 * @return the bankOrder
	 */
	public String getBankOrder() {
		return bankOrder;
	}
	/**
	 * @param bankOrder the bankOrder to set
	 */
	public void setBankOrder(String bankOrder) {
		this.bankOrder = bankOrder;
	}
	/**
	 * @return the orderState
	 */
	public String getOrderState() {
		return orderState;
	}
	/**
	 * @param orderState the orderState to set
	 */
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	/**
	 * @return the payDesc
	 */
	public String getPayDesc() {
		return payDesc;
	}
	/**
	 * @param payDesc the payDesc to set
	 */
	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}
	/**
	 * @return the payDate
	 */
	public Date getPayDate() {
		return payDate;
	}
	/**
	 * @param payDate the payDate to set
	 */
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	/**
	 * @return the payMoney
	 */
	public int getPayMoney() {
		return payMoney;
	}
	/**
	 * @param payMoney the payMoney to set
	 */
	public void setPayMoney(int payMoney) {
		this.payMoney = payMoney;
	}
}
