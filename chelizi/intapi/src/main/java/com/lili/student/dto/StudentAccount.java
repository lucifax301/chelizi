package com.lili.student.dto;

import java.io.Serializable;

/**
 * 学员金额实体
 * @author yu.y
 *
 */
public class StudentAccount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8833689727496074951L;
	
	/**
	 * 学员id
	 */
	private Long studentId;
	
	/**
	 * 金额
	 */
	private Integer money;
	
	/**
	 *密码 
	 */
	private String passwd;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
