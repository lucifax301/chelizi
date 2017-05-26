package com.lili.student.model;

import com.lili.cms.entity.BaseEntity;

public class StudentStateVo extends BaseEntity{

	private static final long serialVersionUID = 3870176242808117096L;


	/**
	 * 学员所处的阶段
	 */
	private Integer applyexam;
	
	/**
	 * 学员所处的阶段状态
	 */
	private Integer applystate;
	/**
	 * 请求重置到阶段
	 */
	private Integer reqApplyexam;
	
	/**
	 * 身份信息id
	 */
	private String ptype;
	
	/**
	 * 缺失的报名资料条目id，多个用逗号隔开
	 */
	private String pmid;
	
	/**
	 * 缺失资料说明
	 */
	private String note;
	
	/**
	 * 请求重置到阶段状态
	 */
	private Integer reqApplystate;

	/**
	 * 学员ID
	 */
	private Long studentId;
	
	private String studentName;
	
	/**
	 * 0---可以重置状态,1---不可以重置状态
	 */
	private int canReset;

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public String getPmid() {
		return pmid;
	}

	public void setPmid(String pmid) {
		this.pmid = pmid;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getApplyexam() {
		return applyexam;
	}

	public void setApplyexam(Integer applyexam) {
		this.applyexam = applyexam;
	}

	public Integer getApplystate() {
		return applystate;
	}

	public void setApplystate(Integer applystate) {
		this.applystate = applystate;
	}

	public Integer getReqApplyexam() {
		return reqApplyexam;
	}

	public void setReqApplyexam(Integer reqApplyexam) {
		this.reqApplyexam = reqApplyexam;
	}

	public Integer getReqApplystate() {
		return reqApplystate;
	}

	public void setReqApplystate(Integer reqApplystate) {
		this.reqApplystate = reqApplystate;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getCanReset() {
		return canReset;
	}

	public void setCanReset(int canReset) {
		this.canReset = canReset;
	}
	
}
