package com.lili.student.model;


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
	private static final long serialVersionUID = 7671315841579595233L;

	private Integer money;

	private Long studentId;

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

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
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
}
