package com.lili.order.vo;

public class CoachClassQuery extends BaseQuery {
	private String sqlField="ccid as ccid,ctype as ctype,coach_id as coachId,cstart as cstart,cend as cend,rstart as rstart,rend as rend,order_id as orderId,max_num as maxNum,book_num as bookNum,car_id as carId,car_name as carName,car_img as carImg,car_no as carNo,course_id as courseId,course_name as courseName,place_id as placeId,place_name as placeName,price as price,dltype as dltype,tid as tid,isdel as isdel,lge as lge,lae as lae,rid as rid,reason as reason";
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
