package com.lili.report.vo;


import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

public class StatisticsStudentSchool extends BasePagedEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 9102999259555685774L;

	private Long id;

    private Integer hadClassNum;

    private Integer classTime;

    private Integer rClassTime;

    private Integer commentNum;

    private Integer loginNum;

    private Date dTime;

    private Date cTime;
    
    private Integer hadClassNumSum;
    
    private Integer classTimeSum;
    
    private Integer rClassTimeSum;
    
    private Integer commentNumSum;
    
    private Integer loginNumSum;

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

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
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