package com.lili.coach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 教练余额实体
 * @author yu.y
 *
 */
public class CoachAccount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7057835461817090576L;
	
	/**
	 * 教练id
	 */
	private Long coachId;
	
	/**
	 * 余额
	 */
	private Integer money;
	
	/**
	 * 业绩
	 */
	private Integer performance;
	
	/**
	 * 业绩最后更新时间
	 */
	private Date lastPerTime;
	
	/**
	 * 密码
	 */
	private String passwd;

	public Long getCoachId() {
		return coachId;
	}

	public void setCoachId(Long coachId) {
		this.coachId = coachId;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getPerformance() {
		return performance;
	}

	public void setPerformance(Integer performance) {
		this.performance = performance;
	}

    /**
     * @return the lastPerTime
     */
    public Date getLastPerTime()
    {
        return lastPerTime;
    }

    /**
     * @param lastPerTime the lastPerTime to set
     */
    public void setLastPerTime(Date lastPerTime)
    {
        this.lastPerTime = lastPerTime;
    }

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
