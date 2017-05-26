package com.lili.report.vo;


import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

public class StatisticsOrderVo extends BasePagedEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -270313270149745638L;

	private Long id;

    private Integer classOrder;

    private Integer singupOrder;

    private Integer rClassOrder;

    private Integer rSignupOrder;

    private String cityId;

    private Date dTime;

    private Date cTime;
    
    private Integer classOrderSum;
    
    private Integer singupOrderSum;
    
    private Integer rClassOrderSum;
    
    private Integer rSignupOrderSum;
    
    private String classRRSum;
    
    private String orderRRSum;
    
    private String orderRR;
    
    private String classRR;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public Integer getClassOrder() {
		return classOrder;
	}

	public void setClassOrder(Integer classOrder) {
		this.classOrder = classOrder;
	}

	public Integer getSingupOrder() {
		return singupOrder;
	}

	public void setSingupOrder(Integer singupOrder) {
		this.singupOrder = singupOrder;
	}

	public Integer getrClassOrder() {
		return rClassOrder;
	}

	public void setrClassOrder(Integer rClassOrder) {
		this.rClassOrder = rClassOrder;
	}

	public Integer getrSignupOrder() {
		return rSignupOrder;
	}

	public void setrSignupOrder(Integer rSignupOrder) {
		this.rSignupOrder = rSignupOrder;
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

	public Integer getClassOrderSum() {
		return classOrderSum;
	}

	public void setClassOrderSum(Integer classOrderSum) {
		this.classOrderSum = classOrderSum;
	}

	public Integer getSingupOrderSum() {
		return singupOrderSum;
	}

	public void setSingupOrderSum(Integer singupOrderSum) {
		this.singupOrderSum = singupOrderSum;
	}

	public Integer getrClassOrderSum() {
		return rClassOrderSum;
	}

	public void setrClassOrderSum(Integer rClassOrderSum) {
		this.rClassOrderSum = rClassOrderSum;
	}

	public Integer getrSignupOrderSum() {
		return rSignupOrderSum;
	}

	public void setrSignupOrderSum(Integer rSignupOrderSum) {
		this.rSignupOrderSum = rSignupOrderSum;
	}

	public String getClassRRSum() {
		return classRRSum;
	}

	public void setClassRRSum(String classRRSum) {
		this.classRRSum = classRRSum + "%";
	}

	public String getOrderRRSum() {
		return orderRRSum;
	}

	public void setOrderRRSum(String orderRRSum) {
		this.orderRRSum = orderRRSum + "%";
	}

	public String getOrderRR() {
		return orderRR;
	}

	public void setOrderRR(String orderRR) {
		this.orderRR = orderRR + "%";
	}

	public String getClassRR() {
		return classRR;
	}

	public void setClassRR(String classRR) {
		this.classRR = classRR + "%";;
	}


}