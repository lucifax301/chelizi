package com.lili.location.dao.Po;


public class SearchPo {
	
	// 经度
	private Double lge;
	// 纬度
	private Double lae;
	// 附近距离
	private Double distance=5d;
	//工作状态
	private Integer wstate;
	// 页码，从1开始
	private Integer pageSkip = 1;
	// 每页条数
	private Integer pageLimit = 10;

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

	public Integer getWstate() {
		return wstate;
	}

	public void setWstate(Integer wstate) {
		this.wstate = wstate;
	}

	public String toString() {
		return "searchVo={distance=" + distance + ",lge=" + lge + ",lae=" + lae
				+ ",pageSkip=" + pageSkip + ",pageLimit=" + pageLimit + "}";
	}
}
