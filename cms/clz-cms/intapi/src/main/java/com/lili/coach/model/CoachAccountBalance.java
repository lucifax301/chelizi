package com.lili.coach.model;

import java.io.Serializable;


/**
 * 学员金额实体
 * @author yu.y
 *
 */
public class CoachAccountBalance implements Serializable{

	private static final long serialVersionUID = -1518227842539633962L;

	private Long coachId;
	
	private String name;
	
	private String accountState;
	
	private Integer balance;

	public Long getCoachId() {
		return coachId;
	}

	public void setCoachId(Long coachId) {
		this.coachId = coachId;
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
