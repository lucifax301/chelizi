package com.lili.order.model;

import com.lili.cms.entity.BaseEntity;

/**
 * 学员关联下的教练、车、订单等信息汇总的VO
 * @author MaesHughes
 *
 */
public class STOVo extends BaseEntity{

	private static final long serialVersionUID = 2323742282920471595L;
	
	private String coachName;
	private long coachId;
	private String coachIcon;
	/**
	 * 教练有多辆车则用逗号隔开
	 */
	private String carNo;
	private String carId;
	
	private String phoneNum;
	private int totalOrderNum;
	/**
	 * 学员关联教练的约课时长(已下课及已评分四种状态下)
	 */
	private long totalOrderTime;
	private int avgScore;
	/**
	 * 实付价格
	 */
	private int totalMoney;
	
	
	public String getCoachIcon() {
		return coachIcon;
	}
	public void setCoachIcon(String coachIcon) {
		this.coachIcon = coachIcon;
	}
	public long getCoachId() {
		return coachId;
	}
	public void setCoachId(long coachId) {
		this.coachId = coachId;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public int getTotalOrderNum() {
		return totalOrderNum;
	}
	public void setTotalOrderTime(int totalOrderTime) {
		this.totalOrderTime = totalOrderTime;
	}
	public int getAvgScore() {
		return avgScore;
	}
	public void setAvgScore(int avgScore) {
		this.avgScore = avgScore;
	}
	public long getTotalOrderTime() {
		return totalOrderTime;
	}
	public void setTotalOrderTime(long totalOrderTime) {
		this.totalOrderTime = totalOrderTime;
	}
	public void setTotalOrderNum(int totalOrderNum) {
		this.totalOrderNum = totalOrderNum;
	}
	public String getCoachName() {
		return coachName;
	}
	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}
	public int getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(int totalMoney) {
		this.totalMoney = totalMoney;
	}
	
	
	
}
