package com.lili.finance.model;

import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

/**
 * Platform account reconciliation
 * 平台账户余额对账情况  实体
 * @author MaesHughes
 *
 */
public class PARBDTO extends BasePagedEntity{

	private static final long serialVersionUID = -7008395338482709698L;

	/**
	 * 平台教练账户余额
	 */
	private long coachMoney;
	/**
	 * 平台学员账户余额
	 */
	private long studentMoney;

	/**
	 * 平台总账户余额
	 */
	private long accountMoney;
	
	/**
	 * 充值
	 */
	private long recharge;
	/**
	 * 奖金
	 */
	private long bonus;
	/**
	 * 提现
	 */
	private long deposit;
	/**
	 * 余额支付
	 */
	private long balancePaid;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 统计时间
	 */
	private Date calTime;
	
	public long getCoachMoney() {
		return coachMoney;
	}
	public void setCoachMoney(long coachMoney) {
		this.coachMoney = coachMoney;
	}
	public long getStudentMoney() {
		return studentMoney;
	}
	public void setStudentMoney(long studentMoney) {
		this.studentMoney = studentMoney;
	}
	public long getBonus() {
		return bonus;
	}
	public void setBonus(long bonus) {
		this.bonus = bonus;
	}
	public long getDeposit() {
		return deposit;
	}
	public void setDeposit(long deposit) {
		this.deposit = deposit;
	}
	public long getBalancePaid() {
		return balancePaid;
	}
	public void setBalancePaid(long balancePaid) {
		this.balancePaid = balancePaid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCalTime() {
		return calTime;
	}
	public void setCalTime(Date calTime) {
		this.calTime = calTime;
	}
	public long getRecharge() {
		return recharge;
	}
	public void setRecharge(long recharge) {
		this.recharge = recharge;
	}
	public long getAccountMoney() {
		return accountMoney;
	}
	public void setAccountMoney(long accountMoney) {
		this.accountMoney = accountMoney;
	}
	
	
}
