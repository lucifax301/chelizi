package com.lili.school.model;

import com.lili.cms.entity.BaseEntity;

public class Field extends BaseEntity{

	private static final long serialVersionUID = 2890539604112998718L;
	
	private long fieldId;
	//训练场经度
	private Double lge;
	//训练场纬度
	private Double lae;
	private String posDesc;
	//倒车训练容纳量
	private Integer reverseLim;
	private String phoneNum;
	private Long region;
	private String name;
	private String schoolName;
	
	private Integer trainFieldId;
	
	private String city;
	
	private Integer isdel;
	
	
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
	public Integer getReverseLim() {
		return reverseLim;
	}
	public void setReverseLim(Integer reverseLim) {
		this.reverseLim = reverseLim;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public Long getRegion() {
		return region;
	}
	public void setRegion(Long region) {
		this.region = region;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTrainFieldId() {
		return trainFieldId;
	}
	public void setTrainFieldId(Integer trainFieldId) {
		this.trainFieldId = trainFieldId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getIsdel() {
		return isdel;
	}
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	

}
