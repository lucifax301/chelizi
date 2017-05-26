package com.lili.student.model;

import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

public class StudentBDTO extends BasePagedEntity{

	private static final long serialVersionUID = -1559190056424412546L;

	/**
	 * 学员ID
	 */
	private long studentId;
	private long coachId;
	
	/**
	 * 身份证号
	 */
	private String idNumber;
	
	/**
	 * 学员姓名
	 */
	private String  name;
	
	
	/**
	 * 学员电话
	 */
	private String phoneNum;
	
	
	/**
	 * 申请的车牌类型
	 */
	private int applyCarType;

	/**
	 * 驾驶类型（1代表C1,2代表C2,3代表其它）
	 */
	private Integer drType;
	
	/**
	 * 车管所报名状态：0-未报名；1-已报名成功；2-报名审核中
	 */
	private Integer applyexam;
	private Integer applystate;
	
	private Integer schoolType;
	
	/**
	 * 是否设置了支付密码- 0-未设置；1-已设置
	 */
	private Integer pwstate;
	/**
	 * 学员认证状态
	 */
	private Integer state;
	/**
	 * 学员所属城市id
	 */
	private Integer cityId;
	
	
	private int studentRowNum;
	private long max_id;
	private long min_id;
	
	private String studentIds;

	private Integer applyttid;
	
	//关联班别id
	private Integer packageId;
	//关联班别时间
	private Date relevanceTime;
	
	private Integer searchType;
	
	private int theoryId;
	
	
	
	public int getTheoryId() {
		return theoryId;
	}
	public void setTheoryId(int theoryId) {
		this.theoryId = theoryId;
	}
	/**
	 * =isImport
	 */
	private int type;
	
	public Integer getApplyttid() {
		return applyttid;
	}
	public void setApplyttid(Integer applyttid) {
		this.applyttid = applyttid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getCoachId() {
		return coachId;
	}
	public void setCoachId(long coachId) {
		this.coachId = coachId;
	}
	public String getStudentIds() {
		return studentIds;
	}
	public void setStudentIds(String studentIds) {
		this.studentIds = studentIds;
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
	public int getStudentRowNum() {
		return studentRowNum;
	}
	public void setStudentRowNum(int studentRowNum) {
		this.studentRowNum = studentRowNum;
	}
	public Integer getApplystate() {
		return applystate;
	}
	public void setApplystate(Integer applystate) {
		this.applystate = applystate;
	}
	public Integer getSchoolType() {
		return schoolType;
	}
	public void setSchoolType(Integer schoolType) {
		this.schoolType = schoolType;
	}
	public long getStudentId() {
		return studentId;
	}
	public void setStudentId(long studentId) {
		this.studentId = studentId;
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
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public int getApplyCarType() {
		return applyCarType;
	}
	public void setApplyCarType(int applyCarType) {
		this.applyCarType = applyCarType;
	}
	public Integer getDrType() {
		return drType;
	}
	public void setDrType(Integer drType) {
		this.drType = drType;
	}
	public Integer getApplyexam() {
		return applyexam;
	}
	public void setApplyexam(Integer applyexam) {
		this.applyexam = applyexam;
	}
	public Integer getPwstate() {
		return pwstate;
	}
	public void setPwstate(Integer pwstate) {
		this.pwstate = pwstate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getPackageId() {
		return packageId;
	}
	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}
	public Date getRelevanceTime() {
		return relevanceTime;
	}
	public void setRelevanceTime(Date relevanceTime) {
		this.relevanceTime = relevanceTime;
	}
	public Integer getSearchType() {
		return searchType;
	}
	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}
	
	
}
