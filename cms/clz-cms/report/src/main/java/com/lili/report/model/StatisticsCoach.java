package com.lili.report.model;


import java.util.Date;

public class StatisticsCoach {
    private Long id;

    private Integer classNum;

    private Integer maxStuNum;

    private Integer hadClassNum;

    private Integer classTime;

    private Integer rClassTIme;

    private Integer loginNum;

    private String cityId;

    private Date dTime;

    private Date cTime;
    
    private Integer classNumSum;
    
    private Integer maxStuNumSum;
    
    private Integer hadClassNumSum;
    
    private Integer classTimeSum;
    
    private Integer rClassTImeSum;
    
    private Integer loginNumSum;
    
    private Integer classRRSum;

    private String classRR;
    
    private String startTime;
    
    private String endTime;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public Integer getClassNum() {
		return classNum;
	}

	public void setClassNum(Integer classNum) {
		this.classNum = classNum;
	}

	public Integer getMaxStuNum() {
		return maxStuNum;
	}

	public void setMaxStuNum(Integer maxStuNum) {
		this.maxStuNum = maxStuNum;
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

	public Integer getrClassTIme() {
		return rClassTIme;
	}

	public void setrClassTIme(Integer rClassTIme) {
		this.rClassTIme = rClassTIme;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
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

	public Integer getClassNumSum() {
		return classNumSum;
	}

	public void setClassNumSum(Integer classNumSum) {
		this.classNumSum = classNumSum;
	}

	public Integer getMaxStuNumSum() {
		return maxStuNumSum;
	}

	public void setMaxStuNumSum(Integer maxStuNumSum) {
		this.maxStuNumSum = maxStuNumSum;
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

	public Integer getrClassTImeSum() {
		return rClassTImeSum;
	}

	public void setrClassTImeSum(Integer rClassTImeSum) {
		this.rClassTImeSum = rClassTImeSum;
	}

	public Integer getLoginNumSum() {
		return loginNumSum;
	}

	public void setLoginNumSum(Integer loginNumSum) {
		this.loginNumSum = loginNumSum;
	}

	public Integer getClassRRSum() {
		return classRRSum;
	}

	public void setClassRRSum(Integer classRRSum) {
		this.classRRSum = classRRSum;
	}

	public String getClassRR() {
		return classRR;
	}

	public void setClassRR(String classRR) {
		this.classRR = classRR + "%";
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}