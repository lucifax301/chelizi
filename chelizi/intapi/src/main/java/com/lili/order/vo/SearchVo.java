package com.lili.order.vo;

import java.io.Serializable;
import java.util.Date;

public class SearchVo implements Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 8036692163057876987L;
	// 经度
	private Double lge;
	// 纬度
	private Double lae;
	// 附近距离
	private Double distance;
	private Integer wstate;
	// 排序策略：距离最近，历史已选，评价最高，价格最低,车况最好等等
	private String orderBy;
	// 课程id
	private Integer courseId;
	// 驾照类型
	private Integer dlType;
	// 指定教练，多个以逗号分割
	private String coachId;
	// 计划上课时间
	private Date pstart;
	// 计划课时
	private Integer clzNum = 1;
	//忽略的前面条数
	private Integer pageSkip = 0;
	//总共取的条数
	private Integer pageLimit = 12;

	public Double getLge() {
		return lge;
	}

	public void setLge(Double lge) {
		this.lge = lge;
	}

	public Double getLae() {
		return lae;
	}

	public void setLae(Double lae) {
		this.lae = lae;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Integer getWstate() {
		return wstate;
	}

	public void setWstate(Integer wstate) {
		this.wstate = wstate;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getCoachId() {
		return coachId;
	}

	public void setCoachId(String coachId) {
		this.coachId = coachId;
	}

	public Integer getDlType() {
		return dlType;
	}

	public void setDlType(Integer dlType) {
		this.dlType = dlType;
	}

	public Integer getPageSkip() {
		return pageSkip;
	}

	public void setPageSkip(Integer pageSkip) {
		this.pageSkip = pageSkip;
	}

	public Integer getPageLimit() {
		return pageLimit;
	}

	public void setPageLimit(Integer pageLimit) {
		this.pageLimit = pageLimit;
	}

	public Date getPstart() {
		return pstart;
	}

	public void setPstart(Date pstart) {
		this.pstart = pstart;
	}

	public Integer getClzNum() {
		return clzNum;
	}

	public void setClzNum(Integer clzNum) {
		this.clzNum = clzNum;
	}

	public String toString() {
		return "searchVo={distance=" + distance + ",wstate=" + wstate + ",lge="
				+ lge + ",lae=" + lae + ",orderBy=" + orderBy + ",courseId="
				+ courseId + "coachId,=" + coachId + ",dltype=" + dlType
				+ ",pageSkip=" + pageSkip + ",pageLimit=" + pageLimit
				+ ",pstart=" + pstart + ",clzNum=" + clzNum + "}";
	}
}
