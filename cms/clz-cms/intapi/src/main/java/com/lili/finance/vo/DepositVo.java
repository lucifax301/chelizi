package com.lili.finance.vo;


import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

public class DepositVo extends BasePagedEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8464750818113088615L;
	
	private Integer id;

	private String waterid;

    private Integer userType;

    private Long userId;

    private Integer money;

    private Date applyTime;

    private String type;

    private Integer verifyId;

    private String orderId;
    
    private String bankCard;

    private String bankName;

    private String cardName;

    private String checker;

    private Integer checkStatus;

    private Date checkTime;

    private String checkRemark;

    private String remark;
    
    private String mobile;
    
    private Integer accountMoney;
    
    private String idNumber;
    
    private String transfer;

    private Date transferTime;
    
    private Date bankResTime;
    
    private Integer realMoney;
    
    private String prov;
    
    private String city;
    
    private String responseCode;
    
    private String merDate;
    
    private String merSeqId;
    
    private String stat;
    
    private Integer feeAmt;
    
    private String responseRemark;
    
    private String statRemark;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWaterid() {
		return waterid;
	}

	public void setWaterid(String waterid) {
		this.waterid = waterid;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getVerifyId() {
		return verifyId;
	}

	public void setVerifyId(Integer verifyId) {
		this.verifyId = verifyId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getAccountMoney() {
		return accountMoney;
	}

	public void setAccountMoney(Integer accountMoney) {
		this.accountMoney = accountMoney;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getTransfer() {
		return transfer;
	}

	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	public Date getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}

	public Date getBankResTime() {
		return bankResTime;
	}

	public void setBankResTime(Date bankResTime) {
		this.bankResTime = bankResTime;
	}

	public Integer getRealMoney() {
		return realMoney;
	}

	public void setRealMoney(Integer realMoney) {
		this.realMoney = realMoney;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getMerDate() {
		return merDate;
	}

	public void setMerDate(String merDate) {
		this.merDate = merDate;
	}

	public String getMerSeqId() {
		return merSeqId;
	}

	public void setMerSeqId(String merSeqId) {
		this.merSeqId = merSeqId;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public Integer getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(Integer feeAmt) {
		this.feeAmt = feeAmt;
	}

	public String getResponseRemark() {
		return responseRemark;
	}

	public void setResponseRemark(String responseRemark) {
		this.responseRemark = responseRemark;
	}

	public String getStatRemark() {
		return statRemark;
	}

	public void setStatRemark(String statRemark) {
		this.statRemark = statRemark;
	}


}
