package com.lili.coach.dto;

import java.io.Serializable;

/**
 * 训练场实体
 * @author yu.y
 *
 */
public class Trfield implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3031470519949031235L;
	
	/**
	 * 训练场id
	 */
	private Integer trainFieldId;
	
	/**
	 * 训练场名称
	 */
	private String name;
	
	/**
	 * 驾校id
	 */
	private Integer schoolId;
	
	/**
	 * 训练场经度
	 */
	private Double lge;
	
	/**
	 * 训练场纬度
	 */
	private Double lae;
	
	/**
	 * 训纬场位置描述
	 */
	private String posDesc;
	
	/**
	 * 倒车训练容纳量
	 */
	private Integer reverseLim; 
	
	/**
	 * 联系电话
	 */
	private String phoneNum;
	
	/**
	 * 所属区id
	 */
	private Integer region;
	
	/**
	 * 所属区域名称
	 */
	private String regionName;
	
	/**
	 * 训练场状态
	 * @return
	 */
	private Integer isdel;

	public Integer getTrainFieldId() {
		return trainFieldId;
	}

	public void setTrainFieldId(Integer trainFieldId) {
		this.trainFieldId = trainFieldId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
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

	public Integer getRegion() {
		return region;
	}

	public void setRegion(Integer region) {
		this.region = region;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	
	
	
}
