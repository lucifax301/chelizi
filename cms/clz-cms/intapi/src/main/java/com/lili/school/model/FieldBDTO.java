package com.lili.school.model;

import com.lili.cms.entity.BasePagedEntity;

public class FieldBDTO extends BasePagedEntity{

	private static final long serialVersionUID = 2890539604112998718L;
	
	private long fieldId;
	//训练场经度
	private Double lge;
	//训练场纬度
	private Double lae;
	private String posDesc;
	//倒车训练容纳量
	private int reverseLim;
	private String phoneNum;
	private long region;
	private String name;
	private String schoolName;
	
	private Integer isdel;
	
	private Integer trainFieldId;
	
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public long getFieldId() {
		return fieldId;
	}
	public void setFieldId(long fieldId) {
		this.fieldId = fieldId;
	}
	public Double getLge() {
		return lge;
	}
	public void setLge(Double lge) {
		this.lge = lge;
	}
	public Double getLae() {
		return lae;
	}
	public void setLae(Double lae) {
		this.lae = lae;
	}
	public String getPosDesc() {
		return posDesc;
	}
	public void setPosDesc(String posDesc) {
		this.posDesc = posDesc;
	}
	public int getReverseLim() {
		return reverseLim;
	}
	public void setReverseLim(int reverseLim) {
		this.reverseLim = reverseLim;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public long getRegion() {
		return region;
	}
	public void setRegion(long region) {
		this.region = region;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIsdel() {
		return isdel;
	}
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	public Integer getTrainFieldId() {
		return trainFieldId;
	}
	public void setTrainFieldId(Integer trainFieldId) {
		this.trainFieldId = trainFieldId;
	}
	

}
