package com.lili.coach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 训练场历史记录实体
 * @author yu.y
 *
 */
public class Trfields implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6606511964361478231L;

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 训练场id
	 */
	private Integer trainFieldId;
	
	/**
	 * 训练场经度
	 */
	private Double lge;
	
	/**
	 * 训练场纬度
	 */
	private Double lae;
	/**
	 * 训练场位置描述
	 */
	private String posDesc;
	
	/**
	 * 系统类型：1-Android；2-ios；3-ios越狱
	 */
	private Integer osType;
	
	/**
	 * 操作系统版本
	 */
	private String osv;
	
	/**
	 * 终端型号
	 */
	private String deviceType;
	
	/**
	 * 终端身份标识
	 */
	private String imei;
	
	/**
	 * IP地址
	 */
	private String ip;
	
	/**
	 * 创建时间
	 */
	private Date ctime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTrainFieldId() {
		return trainFieldId;
	}

	public void setTrainFieldId(Integer trainFieldId) {
		this.trainFieldId = trainFieldId;
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

	public Integer getOsType() {
		return osType;
	}

	public void setOsType(Integer osType) {
		this.osType = osType;
	}

	public String getOsv() {
		return osv;
	}

	public void setOsv(String osv) {
		this.osv = osv;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getPosDesc() {
		return posDesc;
	}

	public void setPosDesc(String posDesc) {
		this.posDesc = posDesc;
	}
	
}
