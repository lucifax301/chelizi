package com.lili.report.vo;


import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

public class StatisticsCoachSchool extends BasePagedEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7547747110284501777L;

	private Long id;

    private Integer classNum;

    private Integer classTime;

    private Integer rClassTIme;

    private Integer loginNum;

    private Integer commentNum;

    private Date dTime;

    private Date cTime;
    
    private Integer classNumSum;
    
    private Integer classTimeSum;
    
    private Integer rClassTImeSum;
    
    private Integer commentNumSum;
    
    private Integer loginNumSum;

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

	public Integer getClassTime() {
		return classTime;
	}

	public void setClassTime(Integer classTime) {
		this.classTime = classTime;
	}

	public Integer getRClassTIme() {
		return rClassTIme;
	}

	public void setRClassTIme(Integer rClassTIme) {
		this.rClassTIme = rClassTIme;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Date getDTime() {
		return dTime;
	}

	public void setDTime(Date dTime) {
		this.dTime = dTime;
	}

	public Date getCTime() {
		return cTime;
	}

	public void setCTime(Date cTime) {
		this.cTime = cTime;
	}

	public Integer getClassNumSum() {
		return classNumSum;
	}

	public void setClassNumSum(Integer classNumSum) {
		this.classNumSum = classNumSum;
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

	public Integer getCommentNumSum() {
		return commentNumSum;
	}

	public void setCommentNumSum(Integer commentNumSum) {
		this.commentNumSum = commentNumSum;
	}

	public Integer getLoginNumSum() {
		return loginNumSum;
	}

	public void setLoginNumSum(Integer loginNumSum) {
		this.loginNumSum = loginNumSum;
	}
}