package com.lili.coach.model;

import com.lili.cms.entity.BasePagedEntity;

/**
 * 教练实体
 * 
 * @author yu.y
 *
 */
public class CoachBDTO extends BasePagedEntity {

	private static final long serialVersionUID = 3258929791543735074L;

	/**
	 * 教练身份认证状态（0：未认证，1：认证中，2：已认证，3：认证失败）
	 */
	private int checkIdState;

	/**
	 * 教练id
	 */
	private long coachId;

	/**
	 * 教练车id
	 */
	private int coachCarId;

	/**
	 * 驾照编号
	 */
	private String drLicence;

	/**
	 * 教练注册所有城市
	 */
	private int cityId;
	
	private String cityName;

	/**
	 * 教练等级
	 */
	private int coachLevel;

	/**
	 * 身份证号
	 */
	private String idNumber;

	/**
	 * 名字
	 */
	private String name;

	/**
	 * 性别
	 */
	private Integer sex;

	/**
	 * 电话号码
	 */
	private String phoneNum;

	private int rowNum;

	private long max_id;

	private long min_id;

	private String startTimeW;
	
	private String endTimeW;

	private Integer checkDrState;

	private String checkDrRemark;

	private Integer state;
	
	private Integer isImport;

	public String getStartTimeW() {
		return startTimeW;
	}

	public void setStartTimeW(String startTimeW) {
		this.startTimeW = startTimeW;
	}

	public String getEndTimeW() {
		return endTimeW;
	}

	public void setEndTimeW(String endTimeW) {
		this.endTimeW = endTimeW;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public long getMax_id() {
		return max_id;
	}

	public void setMax_id(long max_id) {
		this.max_id = max_id;
	}

	public long getMin_id() {
		return min_id;
	}

	public void setMin_id(long min_id) {
		this.min_id = min_id;
	}

	public int getCheckIdState() {
		return checkIdState;
	}

	public void setCheckIdState(int checkIdState) {
		this.checkIdState = checkIdState;
	}

	public long getCoachId() {
		return coachId;
	}

	public void setCoachId(long coachId) {
		this.coachId = coachId;
	}

	public int getCoachCarId() {
		return coachCarId;
	}

	public void setCoachCarId(int coachCarId) {
		this.coachCarId = coachCarId;
	}

	public String getDrLicence() {
		return drLicence;
	}

	public void setDrLicence(String drLicence) {
		this.drLicence = drLicence;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getCoachLevel() {
		return coachLevel;
	}

	public void setCoachLevel(int coachLevel) {
		this.coachLevel = coachLevel;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Integer getCheckDrState() {
		return checkDrState;
	}

	public void setCheckDrState(Integer checkDrState) {
		this.checkDrState = checkDrState;
	}

	public String getCheckDrRemark() {
		return checkDrRemark;
	}

	public void setCheckDrRemark(String checkDrRemark) {
		this.checkDrRemark = checkDrRemark;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getIsImport() {
		return isImport;
	}

	public void setIsImport(Integer isImport) {
		this.isImport = isImport;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}
