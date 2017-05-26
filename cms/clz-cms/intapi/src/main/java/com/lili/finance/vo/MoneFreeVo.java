package com.lili.finance.vo;

import com.lili.cms.entity.BasePagedEntity;

public class MoneFreeVo extends BasePagedEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4705158092017804213L;

	private Integer income;

	private Integer courseFree;//课时费

	private Integer enrollFree;//报名费
	
	private Integer comFree;//佣金
	
	private Integer refundFree;//退款
	
	private Integer fineFree;//罚款
	
	private Integer expenseFree;//费用
	
	private Integer profitLossFree;//月盈亏

	public Integer getIncome() {
		return income;
	}

	public void setIncome(Integer income) {
		this.income = income;
	}

	public Integer getCourseFree() {
		return courseFree;
	}

	public void setCourseFree(Integer courseFree) {
		this.courseFree = courseFree;
	}

	public Integer getEnrollFree() {
		return enrollFree;
	}

	public void setEnrollFree(Integer enrollFree) {
		this.enrollFree = enrollFree;
	}

	public Integer getComFree() {
		return comFree;
	}

	public void setComFree(Integer comFree) {
		this.comFree = comFree;
	}

	public Integer getRefundFree() {
		return refundFree;
	}

	public void setRefundFree(Integer refundFree) {
		this.refundFree = refundFree;
	}

	public Integer getFineFree() {
		return fineFree;
	}

	public void setFineFree(Integer fineFree) {
		this.fineFree = fineFree;
	}

	public Integer getExpenseFree() {
		return expenseFree;
	}

	public void setExpenseFree(Integer expenseFree) {
		this.expenseFree = expenseFree;
	}

	public Integer getProfitLossFree() {
		return profitLossFree;
	}

	public void setProfitLossFree(Integer profitLossFree) {
		this.profitLossFree = profitLossFree;
	}



}
