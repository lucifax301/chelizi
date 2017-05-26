package com.lili.student.model;

import java.io.Serializable;

/**
 * 学员金额实体
 * @author yu.y
 *
 */
public class StudentAccountBalance implements Serializable{
	
	private static final long serialVersionUID = 5265271668555194979L;

	/**
	 * 学员id
	 */
	private Long studentId;
	
	private String name;
	
	private String accountState;
	
	private Integer balance;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountState() {
		return accountState;
	}

	public void setAccountState(String accountState) {
		this.accountState = accountState;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}


}
