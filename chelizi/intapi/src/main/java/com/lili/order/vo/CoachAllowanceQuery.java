package com.lili.order.vo;

public class CoachAllowanceQuery extends BaseQuery {
	private String sqlField="caid as caid,coach_id as coachId,order_id as orderId,allowance as allowance,astate as astate,atime as atime";
	private String sqlPost;
	private String groupBy;
	private String orderBy;
	private int pageIndex=1;
	private int pageSize=10;
	private boolean paging=false;
	public void setSqlField(String sqlField) {
		this.sqlField=sqlField;
	}

	public String getSqlField() {
		return  sqlField;
	}

	public void setSqlPost(String sqlPost) {
		this.sqlPost=sqlPost;
	}

	public String getSqlPost() {
		String sqlPostTemp=sqlPost;
		if(sqlPost==null || sqlPost.isEmpty()) {
			sqlPostTemp="";
			if(groupBy!=null) {
				sqlPostTemp+=groupBy +" ";
			}
			if(orderBy!=null) {
				sqlPostTemp+=orderBy +" ";
			}
			if(paging) {
				sqlPostTemp+=" limit "+((pageIndex-1)*pageSize)+","+pageSize+" ";
			}
		}
		if(sqlPostTemp.indexOf(";")>=0||sqlPostTemp.indexOf("select")>=0||sqlPostTemp.indexOf("from")>=0) {
			sqlPostTemp=" ";
		}
		return sqlPostTemp;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy=groupBy;
	}

	public void setorderBy(String orderBy) {
		this.orderBy=orderBy;
	}

	public void setPageSize(int pageSize) {
		this.pageSize=pageSize;
		this.paging=true;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex=pageIndex;
		this.paging=true;
	}

}
