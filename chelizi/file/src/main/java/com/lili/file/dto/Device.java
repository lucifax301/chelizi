package com.lili.file.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 终端实体
 * @author yu.y
 *
 */
public class Device implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2146090791711892380L;
	
	/**
	 * 终端id
	 */
	private Long id;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 用户类型
	 */
	private Integer userType;
	
	/**
	 * 系统类型 1-Android；2-ios；3-ios越狱
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
	 * 终端身份标识
	 */
	private String mac;
	
	/**
	 * 手机sim卡唯一标识
	 */
	private String imsi;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 运营商
	 */
	private String ca;
	
	/**
	 * 连网模式
	 */
	private String ac;
	
	/**
	 * 经度
	 */
	private Double lge;
	
	/**
	 * 纬度
	 */
	private Double lae;
	
	/**
	 * 本应用的包名
	 */
	private String appPackName;
	
	/**
	 * 本应用的版本号
	 */
	private String appVersion;
	
	/**
	 * 本应用的版本code
	 */
	private String appCode;
	
	/**
	 * 是否向极光注册 0-未注册，已取消注册；1-已注册
	 */
	private Integer jpush;
	
	/**
	 * 创建时间
	 */
	private Date ctime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
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

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getCa() {
		return ca;
	}

	public void setCa(String ca) {
		this.ca = ca;
	}

	public String getAc() {
		return ac;
	}

	public void setAc(String ac) {
		this.ac = ac;
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

	public String getAppPackName() {
		return appPackName;
	}

	public void setAppPackName(String appPackName) {
		this.appPackName = appPackName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public Integer getJpush() {
		return jpush;
	}

	public void setJpush(Integer jpush) {
		this.jpush = jpush;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String toString() {
		return "{userId="+userId+",osType="+osType+",deviceType="+deviceType+",jpush="+jpush+"}";
	}
}
