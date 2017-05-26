package com.lili.report.vo;


import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

/**
 * 教务报表
 * @author Administrator
 *
 */
public class StatisticsTotalLiliVo extends BasePagedEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1550806349738440200L;

	private Long id;

    private Integer instantOrderMoney;

    private Integer planOrderMoney;

    private Integer signupOrderMoney;

    private Integer totalMoney;

    private Integer instantOrderNum;

    private Integer instantPayOrderNum;

    private Integer planOrderNum;

    private Integer planPayOrderNum;

    private Integer registerStuNum;

    private Integer signupStuNum;

    private Integer activityStuNum;

    private Integer loginStuNum;

    private Integer loginCoachNum;

    private Integer planClassCoachNum;

    private Integer hadPlanClassCoachNum;

    private Integer planClassTime;

    private Integer rPlanClassTime;

    private Integer lessThreeNum;

    private Integer commentOrderNum;

    private Integer dyear;

    private Integer dmonth;

    private Date dtime;

    private Date ctime;
    
    private String lessThreeRatio;
    
    private String fullClass;
    
    private String dateStr;
    
    private Integer instantPayClassTime;
    
    private Integer planPayClassTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInstantordermoney() {
        return instantOrderMoney;
    }

    public void setInstantordermoney(Integer instantordermoney) {
        this.instantOrderMoney = instantordermoney;
    }

   

    public Integer getDyear() {
        return dyear;
    }

    public void setDyear(Integer dyear) {
        this.dyear = dyear;
    }

    public Integer getDmonth() {
        return dmonth;
    }

    public void setDmonth(Integer dmonth) {
        this.dmonth = dmonth;
    }

    public Date getDtime() {
        return dtime;
    }

    public void setDtime(Date dtime) {
        this.dtime = dtime;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

	public String getLessThreeRatio() {
		return lessThreeRatio;
	}

	public void setLessThreeRatio(String lessThreeRatio) {
		this.lessThreeRatio = lessThreeRatio;
	}

	public String getFullClass() {
		return fullClass;
	}

	public void setFullClass(String fullClass) {
		this.fullClass = fullClass;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public Integer getInstantOrderMoney() {
		return instantOrderMoney;
	}

	public void setInstantOrderMoney(Integer instantOrderMoney) {
		this.instantOrderMoney = instantOrderMoney;
	}

	public Integer getPlanOrderMoney() {
		return planOrderMoney;
	}

	public void setPlanOrderMoney(Integer planOrderMoney) {
		this.planOrderMoney = planOrderMoney;
	}

	public Integer getSignupOrderMoney() {
		return signupOrderMoney;
	}

	public void setSignupOrderMoney(Integer signupOrderMoney) {
		this.signupOrderMoney = signupOrderMoney;
	}

	public Integer getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Integer totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getInstantOrderNum() {
		return instantOrderNum;
	}

	public void setInstantOrderNum(Integer instantOrderNum) {
		this.instantOrderNum = instantOrderNum;
	}

	public Integer getInstantPayOrderNum() {
		return instantPayOrderNum;
	}

	public void setInstantPayOrderNum(Integer instantPayOrderNum) {
		this.instantPayOrderNum = instantPayOrderNum;
	}

	public Integer getPlanOrderNum() {
		return planOrderNum;
	}

	public void setPlanOrderNum(Integer planOrderNum) {
		this.planOrderNum = planOrderNum;
	}

	public Integer getPlanPayOrderNum() {
		return planPayOrderNum;
	}

	public void setPlanPayOrderNum(Integer planPayOrderNum) {
		this.planPayOrderNum = planPayOrderNum;
	}

	public Integer getRegisterStuNum() {
		return registerStuNum;
	}

	public void setRegisterStuNum(Integer registerStuNum) {
		this.registerStuNum = registerStuNum;
	}

	public Integer getSignupStuNum() {
		return signupStuNum;
	}

	public void setSignupStuNum(Integer signupStuNum) {
		this.signupStuNum = signupStuNum;
	}

	public Integer getActivityStuNum() {
		return activityStuNum;
	}

	public void setActivityStuNum(Integer activityStuNum) {
		this.activityStuNum = activityStuNum;
	}

	public Integer getLoginStuNum() {
		return loginStuNum;
	}

	public void setLoginStuNum(Integer loginStuNum) {
		this.loginStuNum = loginStuNum;
	}

	public Integer getLoginCoachNum() {
		return loginCoachNum;
	}

	public void setLoginCoachNum(Integer loginCoachNum) {
		this.loginCoachNum = loginCoachNum;
	}

	public Integer getPlanClassCoachNum() {
		return planClassCoachNum;
	}

	public void setPlanClassCoachNum(Integer planClassCoachNum) {
		this.planClassCoachNum = planClassCoachNum;
	}

	public Integer getHadPlanClassCoachNum() {
		return hadPlanClassCoachNum;
	}

	public void setHadPlanClassCoachNum(Integer hadPlanClassCoachNum) {
		this.hadPlanClassCoachNum = hadPlanClassCoachNum;
	}

	public Integer getPlanClassTime() {
		return planClassTime;
	}

	public void setPlanClassTime(Integer planClassTime) {
		this.planClassTime = planClassTime;
	}

	public Integer getrPlanClassTime() {
		return rPlanClassTime;
	}

	public void setrPlanClassTime(Integer rPlanClassTime) {
		this.rPlanClassTime = rPlanClassTime;
	}

	public Integer getLessThreeNum() {
		return lessThreeNum;
	}

	public void setLessThreeNum(Integer lessThreeNum) {
		this.lessThreeNum = lessThreeNum;
	}

	public Integer getCommentOrderNum() {
		return commentOrderNum;
	}

	public void setCommentOrderNum(Integer commentOrderNum) {
		this.commentOrderNum = commentOrderNum;
	}

	public Integer getInstantPayClassTime() {
		return instantPayClassTime;
	}

	public void setInstantPayClassTime(Integer instantPayClassTime) {
		this.instantPayClassTime = instantPayClassTime;
	}

	public Integer getPlanPayClassTime() {
		return planPayClassTime;
	}

	public void setPlanPayClassTime(Integer planPayClassTime) {
		this.planPayClassTime = planPayClassTime;
	}

}