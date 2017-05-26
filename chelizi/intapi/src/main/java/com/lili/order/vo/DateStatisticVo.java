package com.lili.order.vo;

import java.io.Serializable;

public class DateStatisticVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4182601409678110944L;
	private int orderNum=0;
	private int money=0;
	private int score=0;
	private int percent=100;
	private int bonus=0;
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getPercent() {
		return percent;
	}
	public void setPercent(int percent) {
		this.percent = percent;
	}
	public int getBonus() {
		return bonus;
	}
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
}
