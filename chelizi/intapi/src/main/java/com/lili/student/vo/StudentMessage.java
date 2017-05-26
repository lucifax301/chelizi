package com.lili.student.vo;

import java.io.Serializable;

public class StudentMessage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3530714536811456716L;
	/**
	 * 消息类型：学员注册
	 */
	public static final int MSGTYPE_STUDENT_REGISTER = 1;
	/**
	 * 消息类型：学员支付报名费
	 */
	public static final int MSGTYPE_STUDENT_ENROLL_PAID = 2;
	
	/**
	 * 消息类型：出流水号
	 */
	public static final int MSGTYPE_STUDENT_FLOW_PAID = 3;
	
	
	/**
	 * 学员ID
	 */
	private Long studentId;
	/**
	 * 学员电话
	 */
	private String phoneNum;
	

	/**
	 * 消息类型：1-新注册用户，2-用户报名付款；
	 */
	private Integer msgType;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}
	
	
	
}
