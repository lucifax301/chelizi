package com.lili.student.model;

import java.io.Serializable;
import java.util.Date;

public class StudentAuthVo implements Serializable{

	private static final long serialVersionUID = 7095699333204473715L;

	private long id;

	private long studentId;
	/**
	 * 认证类别  0---实名认证  1---驾照认证
	 */
	private int type;
	/**
	 * 0---未审核 1---审核中 2---审核未通过 3---已认证 4---已过期 5---已吊销
	 */
	private int state;
	/**
	 * 申请的车牌类型
	 */
	private int applyCarType;
	/**
	 * 实名认证---认证名
	 */
	private String authName;
	/**
	 * 操作人
	 */
	private String operateUser;
	/**
	 * 驾照认证---档案编号
	 */
	private String fileNo;

	private String remark;

	private String studentName;

	private String studentPhoneNum;

	private String studentIDNumber;

	/**
	 * 学员身份证正面
	 */
	private String idPhotoFront;
	
	/**
	 * 学员身份证反面
	 */
	private String idPhotoBack;

	/**
	 * 学员驾照正面
	 */
	private String liPhotoFront;
	
	/**
	 * 学员驾照反面
	 */
	private String liPhotoBack;


	private Date createTime;

	private Date updateTime;
	/**
	 * 证件截止期
	 */
	private Date deadTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getStudentId() {
		return studentId;
	}
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	public String getOperateUser() {
		return operateUser;
	}
	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}
	public String getFileNo() {
		return fileNo;
	}
	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentPhoneNum() {
		return studentPhoneNum;
	}
	public void setStudentPhoneNum(String studentPhoneNum) {
		this.studentPhoneNum = studentPhoneNum;
	}
	public String getStudentIDNumber() {
		return studentIDNumber;
	}
	public void setStudentIDNumber(String studentIDNumber) {
		this.studentIDNumber = studentIDNumber;
	}
	public String getIdPhotoFront() {
		return idPhotoFront;
	}
	public void setIdPhotoFront(String idPhotoFront) {
		this.idPhotoFront = idPhotoFront;
	}
	public String getIdPhotoBack() {
		return idPhotoBack;
	}
	public void setIdPhotoBack(String idPhotoBack) {
		this.idPhotoBack = idPhotoBack;
	}
	public String getLiPhotoFront() {
		return liPhotoFront;
	}
	public void setLiPhotoFront(String liPhotoFront) {
		this.liPhotoFront = liPhotoFront;
	}
	public String getLiPhotoBack() {
		return liPhotoBack;
	}
	public void setLiPhotoBack(String liPhotoBack) {
		this.liPhotoBack = liPhotoBack;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getDeadTime() {
		return deadTime;
	}
	public void setDeadTime(Date deadTime) {
		this.deadTime = deadTime;
	}
	public int getApplyCarType() {
		return applyCarType;
	}
	public void setApplyCarType(int applyCarType) {
		this.applyCarType = applyCarType;
	}
	
	
}
