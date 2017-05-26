package com.lili.finance.chinapay.model.bean;



/**
 * @author huang.xuting
 *
 */

public class TransactionBean {
	private String MerId;       //商户号，数字，定长15位
	private String MerDate;     //商户日期yyyyMMdd,数字，定长8位
	private String MerSeqId;    //流水号，数字，变长16位
	private String CardNo;      //收款账号，数字，变长25位
	private String UserName;    //收款人姓名，字符，变长20位
	private String OpenBank;    //开户银行名称，字符，变长50位
	private String Prov;        //省份，字符，变长20位
	private String City;        //城市，字符，变长40位
	private String TransAmt;    //金额（单位分），数字，变长12位
	private String Purpose;     //用途，字符，变长25位
	private String SubBank;     //支行名称，字符，变长80位
	private String Flag;        //付款标志，字符，定长2位
	private String Version;     //版本号，字符，变长8位
	private String ChkValue;    //交易签名，字符，定长256位
	
	//应答数据
	private String ResponseCode; //应答码
	private String CpDate;       //Cp接收到交易的日期，定长8位
	private String CpSeqId;      //Cp流水，定长6位
	private String Stat;         //交易状态码，定长1位
	private String FeeAmt;       //手续费（单位分），变长12位
	private String BackDate;     //退单日期，定长8位
	
	//批量退单查询数据
	private String FromDate;     //开始退单日期，定长8位
	private String ToDate;       //结束退单日期，变长8位
	
	//备付金余额查询数据                         
	private String MerAmt;       //备付金余额（单位分），定长15位
	
	//备付金明细查询数据
	private String Type;         //查询类别，定长2位
	
	//返回报文数据
	private String Data;         //控台返回报文
	
	private String termType;
	
	public String toString(){
		return new StringBuffer(MerId).append(MerDate)
		.append(MerSeqId).append(CardNo)
		.append(UserName).append(OpenBank)
		.append(Prov).append(City)
		.append(TransAmt).append(Purpose)
		.append(SubBank)
		.append(Flag)
		.append(Version).append(termType).toString();
	}
	
	public String transString(){
		return new StringBuffer("merId=").append(MerId).append(" & merDate=").append(MerDate)
		.append(" & merSeqId=").append(MerSeqId).append(" & cardNo=").append(CardNo)
		.append(" & usrName=").append(UserName).append(" & openBank=").append(OpenBank)
		.append(" & prov=").append(Prov).append(" & city=").append(City)
		.append(" & transAmt=").append(TransAmt).append(" & purpose=").append(Purpose)
		.append(" & subBank=").append(SubBank)
		.append(" & flag=").append(Flag)
		.append(" & version=").append(Version).append(termType).toString();
	}
	

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getChkValue() {
		return ChkValue;
	}
	public void setChkValue(String chkValue) {
		ChkValue = chkValue;
	}

	public String getCardNo() {
		return CardNo;
	}
	public void setCardNo(String cardNo) {
		CardNo = cardNo;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}

	public void setMerId(String merId) {
		MerId = merId;
	}
	public String getMerId() {
		return MerId;
	}
	public void setTransAmt(String transAmt) {
		TransAmt = transAmt;
	}
	public String getTransAmt() {
		return TransAmt;
	}

	public void setVersion(String version) {
		Version = version;
	}
	public String getVersion() {
		return Version;
	}



	public void setResponseCode(String responseCode) {
		ResponseCode = responseCode;
	}


	public String getResponseCode() {
		return ResponseCode;
	}


	public String getMerDate() {
		return MerDate;
	}


	public void setMerDate(String merDate) {
		MerDate = merDate;
	}


	public String getMerSeqId() {
		return MerSeqId;
	}


	public void setMerSeqId(String merSeqId) {
		MerSeqId = merSeqId;
	}


	public String getOpenBank() {
		return OpenBank;
	}


	public void setOpenBank(String openBank) {
		OpenBank = openBank;
	}


	public String getProv() {
		return Prov;
	}


	public void setProv(String prov) {
		Prov = prov;
	}


	public String getCity() {
		return City;
	}


	public void setCity(String city) {
		City = city;
	}


	public String getPurpose() {
		return Purpose;
	}


	public void setPurpose(String purpose) {
		Purpose = purpose;
	}


	public String getSubBank() {
		return SubBank;
	}


	public void setSubBank(String subBank) {
		SubBank = subBank;
	}


	public String getFlag() {
		return Flag;
	}


	public void setFlag(String flag) {
		Flag = flag;
	}


	public String getCpDate() {
		return CpDate;
	}


	public void setCpDate(String cpDate) {
		CpDate = cpDate;
	}


	public String getCpSeqId() {
		return CpSeqId;
	}


	public void setCpSeqId(String cpSeqId) {
		CpSeqId = cpSeqId;
	}


	public String getStat() {
		return Stat;
	}


	public void setStat(String stat) {
		Stat = stat;
	}
	public String getFeeAmt() {
		return FeeAmt;
	}


	public void setFeeAmt(String feeAmt) {
		FeeAmt = feeAmt;
	}


	public String getBackDate() {
		return BackDate;
	}


	public void setBackDate(String backDate) {
		BackDate = backDate;
	}
	public String getFromDate() {
		return FromDate;
	}

	public void setFromDate(String fromDate) {
		FromDate = fromDate;
	}

	public String getToDate() {
		return ToDate;
	}

	public void setToDate(String toDate) {
		ToDate = toDate;
	}

	public void setMerAmt(String merAmt) {
		MerAmt = merAmt;
	}

	public String getMerAmt() {
		return MerAmt;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getType() {
		return Type;
	}

	public void setData(String data) {
		Data = data;
	}

	public String getData() {
		return Data;
	}







}
