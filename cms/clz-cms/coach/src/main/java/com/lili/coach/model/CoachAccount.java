package com.lili.coach.model;


import java.io.Serializable;
import java.util.Date;

/**
 * 教练余额实体
 * 
 * @author yu.y
 *
 */
public class CoachAccount implements Serializable {

	private static final long serialVersionUID = -7057835461817090576L;

	private Integer money;

	private Long coachId;

	private Integer performance;
	
	private Date lastPerTime;
	
	private String passwd;
	
	private Integer leftAccount;
	
	private Integer saveAccount;
	
	private Integer saveMoney;
	
	private Integer expendAccount;
	
	private Integer expendMoney;

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Long getCoachId() {
		return coachId;
	}

	public void setCoachId(Long coachId) {
		this.coachId = coachId;
	}

	public Integer getLeftAccount() {
		return leftAccount;
	}

	public void setLeftAccount(Integer leftAccount) {
		this.leftAccount = leftAccount;
	}

	public Integer getSaveAccount() {
		return saveAccount;
	}

	public void setSaveAccount(Integer saveAccount) {
		this.saveAccount = saveAccount;
	}

	public Integer getSaveMoney() {
		return saveMoney;
	}

	public void setSaveMoney(Integer saveMoney) {
		this.saveMoney = saveMoney;
	}

	public Integer getExpendAccount() {
		return expendAccount;
	}

	public void setExpendAccount(Integer expendAccount) {
		this.expendAccount = expendAccount;
	}

	public Integer getExpendMoney() {
		return expendMoney;
	}

	public void setExpendMoney(Integer expendMoney) {
		this.expendMoney = expendMoney;
	}

	public Integer getPerformance() {
		return performance;
	}

	public void setPerformance(Integer performance) {
		this.performance = performance;
	}

	public Date getLastPerTime() {
		return lastPerTime;
	}

	public void setLastPerTime(Date lastPerTime) {
		this.lastPerTime = lastPerTime;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
