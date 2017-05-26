package com.lili.report.vo;

import com.lili.cms.entity.BasePagedEntity;

/**
 * 学员进度统计
 * @author devil
 *
 */
public class StatisticsStudentProgress extends BasePagedEntity{

	
	private int applyexam;
	
	private int applystate;
	
	private int count;

	
	public int getApplyexam() {
		return applyexam;
	}

	public void setApplyexam(int applyexam) {
		this.applyexam = applyexam;
	}

	public int getApplystate() {
		return applystate;
	}

	public void setApplystate(int applystate) {
		this.applystate = applystate;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
}
