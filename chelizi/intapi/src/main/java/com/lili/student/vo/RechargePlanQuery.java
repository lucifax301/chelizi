package com.lili.student.vo;

public class RechargePlanQuery extends BaseQuery {
	private String sqlField="rcid as rcid,rcname as rcname,vtype as vtype,tstart as tstart,tend as tend,active as active,enroll as enroll,city_id as cityId,city_name as cityName,common as common,need_verify as needVerify,reg_use as regUse,auto as auto,max_times as maxTimes,indepent as indepent,jpush as jpush,ems as ems,rejpush as rejpush,reems as reems,coupon_template as couponTemplate,coupon_number as couponNumber,agreement as agreement,vstate as vstate,reason as reason,isdel as isdel,reg_num as regNum,isExit_rercid as isExitRercid,rercid as rercid,cuid as cuid,muid as muid,ctime as ctime,mtime as mtime";
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
