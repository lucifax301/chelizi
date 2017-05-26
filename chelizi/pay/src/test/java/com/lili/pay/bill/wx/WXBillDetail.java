/**
 * 
 */
package com.lili.pay.bill.wx;

import java.util.Date;

/**
 * @author linbo
 *
 */
public class WXBillDetail
{
	private Date tradetime; 		//交易时间
	private String ghid;			//公众号id
	private String mchid;			//商户号id
	private String submch;			//子商户号id
	private String deviceid;		//设备号
	private String wxorder;			//微信订单号
	private String bzorder;			//商户订单号
	private String openid;			//用户标识
	private String tradetype;		//交易类型
	private String tradestatus;		//交易状态
	private String bank;			//付款银行
	private String currency;		//货币种类
	private int totalmoney;			//总金额
	private int redpacketmoney;		//企业红包金额
	private String productname; 	//商品名称
	private String bzdatapacket; 	//商户数据包
	private int fee;				//手续费
	private String rate;			//费率
	
	/**
	 * @return the ghid
	 */
	public String getGhid() {
		return ghid;
	}
	/**
	 * @param ghid the ghid to set
	 */
	public void setGhid(String ghid) {
		this.ghid = ghid;
	}
	/**
	 * @return the mchid
	 */
	public String getMchid() {
		return mchid;
	}
	/**
	 * @param mchid the mchid to set
	 */
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}
	/**
	 * @return the submch
	 */
	public String getSubmch() {
		return submch;
	}
	/**
	 * @param submch the submch to set
	 */
	public void setSubmch(String submch) {
		this.submch = submch;
	}
	/**
	 * @return the deviceid
	 */
	public String getDeviceid() {
		return deviceid;
	}
	/**
	 * @param deviceid the deviceid to set
	 */
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	/**
	 * @return the wxorder
	 */
	public String getWxorder() {
		return wxorder;
	}
	/**
	 * @param wxorder the wxorder to set
	 */
	public void setWxorder(String wxorder) {
		this.wxorder = wxorder;
	}
	/**
	 * @return the bzorder
	 */
	public String getBzorder() {
		return bzorder;
	}
	/**
	 * @param bzorder the bzorder to set
	 */
	public void setBzorder(String bzorder) {
		this.bzorder = bzorder;
	}
	/**
	 * @return the openid
	 */
	public String getOpenid() {
		return openid;
	}
	/**
	 * @param openid the openid to set
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	/**
	 * @return the tradetype
	 */
	public String getTradetype() {
		return tradetype;
	}
	/**
	 * @param tradetype the tradetype to set
	 */
	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}
	/**
	 * @return the tradestatus
	 */
	public String getTradestatus() {
		return tradestatus;
	}
	/**
	 * @param tradestatus the tradestatus to set
	 */
	public void setTradestatus(String tradestatus) {
		this.tradestatus = tradestatus;
	}
	/**
	 * @return the bank
	 */
	public String getBank() {
		return bank;
	}
	/**
	 * @param bank the bank to set
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
	/**
	 * @return the productname
	 */
	public String getProductname() {
		return productname;
	}
	/**
	 * @param productname the productname to set
	 */
	public void setProductname(String productname) {
		this.productname = productname;
	}
	/**
	 * @return the bzdatapacket
	 */
	public String getBzdatapacket() {
		return bzdatapacket;
	}
	/**
	 * @param bzdatapacket the bzdatapacket to set
	 */
	public void setBzdatapacket(String bzdatapacket) {
		this.bzdatapacket = bzdatapacket;
	}
	
	/**
	 * @return the rate
	 */
	public String getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}
	/**
	 * @return the tradetime
	 */
	public Date getTradetime() {
		return tradetime;
	}
	/**
	 * @param tradetime the tradetime to set
	 */
	public void setTradetime(Date tradetime) {
		this.tradetime = tradetime;
	}
	/**
	 * @return the totalmoney
	 */
	public int getTotalmoney() {
		return totalmoney;
	}
	/**
	 * @param totalmoney the totalmoney to set
	 */
	public void setTotalmoney(int totalmoney) {
		this.totalmoney = totalmoney;
	}
	/**
	 * @return the redpacketmoney
	 */
	public int getRedpacketmoney() {
		return redpacketmoney;
	}
	/**
	 * @param redpacketmoney the redpacketmoney to set
	 */
	public void setRedpacketmoney(int redpacketmoney) {
		this.redpacketmoney = redpacketmoney;
	}
	/**
	 * @return the fee
	 */
	public int getFee() {
		return fee;
	}
	/**
	 * @param fee the fee to set
	 */
	public void setFee(int fee) {
		this.fee = fee;
	}
	
}
