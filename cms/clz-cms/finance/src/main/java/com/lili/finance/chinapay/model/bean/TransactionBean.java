package com.lili.finance.chinapay.model.bean;



/**
 * @author huang.xuting
 *
 */

public class TransactionBean {
	private String MerId;       //�̻��ţ����֣�����15λ
	private String MerDate;     //�̻�����yyyyMMdd,���֣�����8λ
	private String MerSeqId;    //��ˮ�ţ����֣��䳤16λ
	private String CardNo;      //�տ��˺ţ����֣��䳤25λ
	private String UserName;    //�տ����������ַ����䳤20λ
	private String OpenBank;    //�����������ƣ��ַ����䳤50λ
	private String Prov;        //ʡ�ݣ��ַ����䳤20λ
	private String City;        //���У��ַ����䳤40λ
	private String TransAmt;    //����λ�֣������֣��䳤12λ
	private String Purpose;     //��;���ַ����䳤25λ
	private String SubBank;     //֧�����ƣ��ַ����䳤80λ
	private String Flag;        //�����־���ַ�������2λ
	private String Version;     //�汾�ţ��ַ����䳤8λ
	private String ChkValue;    //����ǩ�����ַ�������256λ
	
	//Ӧ������
	private String ResponseCode; //Ӧ����
	private String CpDate;       //Cp���յ����׵����ڣ�����8λ
	private String CpSeqId;      //Cp��ˮ������6λ
	private String Stat;         //����״̬�룬����1λ
	private String FeeAmt;       //�����ѣ���λ�֣����䳤12λ
	private String BackDate;     //�˵����ڣ�����8λ
	
	//�����˵���ѯ����
	private String FromDate;     //��ʼ�˵����ڣ�����8λ
	private String ToDate;       //�����˵����ڣ��䳤8λ
	
	//����������ѯ����                         
	private String MerAmt;       //����������λ�֣�������15λ
	
	//��������ϸ��ѯ����
	private String Type;         //��ѯ��𣬶���2λ
	
	//���ر�������
	private String Data;         //��̨���ر���
	
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
