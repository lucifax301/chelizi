package com.lili.coach.vo;

import java.io.Serializable;
import java.util.List;

public class DayDetailCoachVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 教练状态
	 */
	private Byte coachStatus;
	/**
	 * 今日订单 	todayOrdersCount
	 */
	private Integer todayOrdersCount;
	/**
	 * 派单成功率 	orderSuccessRate
	 */
	private Double orderSuccessRate;
	/**
	 * 今日流水	todayAccount
	 */
	private Integer todayAccount;
	/**
	 * 选择一辆出车
	 */
	private List<DayDetailCarVo> cars;
	/**
	 * 服务评级分数	serviceRatingScores
	 */
	private Integer serviceRatingScores;
	public Byte getCoachStatus() {
		return coachStatus;
	}
	public void setCoachStatus(Byte coachStatus) {
		this.coachStatus = coachStatus;
	}
	public Integer getTodayOrdersCount() {
		return todayOrdersCount;
	}
	public void setTodayOrdersCount(Integer todayOrdersCount) {
		this.todayOrdersCount = todayOrdersCount;
	}
	public Double getOrderSuccessRate() {
		return orderSuccessRate;
	}
	public void setOrderSuccessRate(Double orderSuccessRate) {
		this.orderSuccessRate = orderSuccessRate;
	}
	public Integer getTodayAccount() {
		return todayAccount;
	}
	public void setTodayAccount(Integer todayAccount) {
		this.todayAccount = todayAccount;
	}
	public List<DayDetailCarVo> getCars() {
		return cars;
	}
	public void setCars(List<DayDetailCarVo> cars) {
		this.cars = cars;
	}
	public Integer getServiceRatingScores() {
		return serviceRatingScores;
	}
	public void setServiceRatingScores(Integer serviceRatingScores) {
		this.serviceRatingScores = serviceRatingScores;
	}
	

	
}
