package com.lili.report.vo;


import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

public class StatisticsStudentVo extends BasePagedEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7395805144216818197L;

	private Long id;

    private Integer hadClassNum;

    private Integer classTime;

    private Integer rClassTime;

    private Integer registerNum;

    private Integer loginNum;

    private Integer signUpNum;

    private String cityId;

    private Date dTime;

    private Date cTime;
    
    private Integer hadClassNumSum;
    
    private Integer classTimeSum;
    
    private Integer rClassTimeSum;
    
    private Integer registerNumSum;
    
    private Integer loginNumSum;
    
    private Integer signUpNumSum;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public Integer getHadClassNum() {
		return hadClassNum;
	}

	public void setHadClassNum(Integer hadClassNum) {
		this.hadClassNum = hadClassNum;
	}

	public Integer getClassTime() {
		return classTime;
	}

	public void setClassTime(Integer classTime) {
		this.classTime = classTime;
	}

	public Integer getrClassTime() {
		return rClassTime;
	}

	public void setrClassTime(Integer rClassTime) {
		this.rClassTime = rClassTime;
	}

	public Integer getRegisterNum() {
		return registerNum;
	}

	public void setRegisterNum(Integer registerNum) {
		this.registerNum = registerNum;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	public Integer getSignUpNum() {
		return signUpNum;
	}

	public void setSignUpNum(Integer signUpNum) {
		this.signUpNum = signUpNum;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Date getdTime() {
		return dTime;
	}

	public void setdTime(Date dTime) {
		this.dTime = dTime;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public Integer getHadClassNumSum() {
		return hadClassNumSum;
	}

	public void setHadClassNumSum(Integer hadClassNumSum) {
		this.hadClassNumSum = hadClassNumSum;
	}

	public Integer getClassTimeSum() {
		return classTimeSum;
	}

	public void setClassTimeSum(Integer classTimeSum) {
		this.classTimeSum = classTimeSum;
	}

	public Integer getrClassTimeSum() {
		return rClassTimeSum;
	}

	public void setrClassTimeSum(Integer rClassTimeSum) {
		this.rClassTimeSum = rClassTimeSum;
	}

	public Integer getRegisterNumSum() {
		return registerNumSum;
	}

	public void setRegisterNumSum(Integer registerNumSum) {
		this.registerNumSum = registerNumSum;
	}

	public Integer getLoginNumSum() {
		return loginNumSum;
	}

	public void setLoginNumSum(Integer loginNumSum) {
		this.loginNumSum = loginNumSum;
	}

	public Integer getSignUpNumSum() {
		return signUpNumSum;
	}

	public void setSignUpNumSum(Integer signUpNumSum) {
		this.signUpNumSum = signUpNumSum;
	}

  
}