package com.lili.cms.entity;


/**
 * 分页查询实体需要继承该实体
 * <p>包含pageNo  pageSize等属性
 * @author hughes
 *
 */
public class BasePagedEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;

	/**
	 * 请求第几页
	 */
	private int pageNo;
	
	/**
	 * 一页多少条数据
	 */
	private int pageSize;
	
	private int startIndex;
	
	private Long schoolNo;

	private String startTime;
	private String endTime;
	
	private String orderby;
	
	private String sort;
	
	
	
	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public Long getSchoolNo() {
		return schoolNo;
	}

	public void setSchoolNo(Long schoolNo) {
		this.schoolNo = schoolNo;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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
