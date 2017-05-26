package com.lili.coach.model;

import java.io.Serializable;

/**
 * 教练余额实体
 * @author yu.y
 *
 */
public class CoachAccountVo implements Serializable{

	private static final long serialVersionUID = -7057835461817090576L;
	
	private long totalMoney;
	
	private long totalCoach;

	public long getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(long totalMoney) {
		this.totalMoney = totalMoney;
	}

	public long getTotalCoach() {
		return totalCoach;
	}

	public void setTotalCoach(long totalCoach) {
		this.totalCoach = totalCoach;
	}
	


}
