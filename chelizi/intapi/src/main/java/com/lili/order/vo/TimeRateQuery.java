package com.lili.order.vo;

public class TimeRateQuery extends BaseQuery {
	public String sqlField="tpid as tpid,ptype as ptype,title as title,yint as yint,mint as mint,wstart as wstart,wend as wend,dstart as dstart,dend as dend,hstart as hstart,hend as hend,prate as prate,verify as verify,isdel as isdel,cuid as cuid,muid as muid,ctime as ctime,mtime as mtime";
	public String sqlPost;
	public String groupBy;
	public String orderBy;
	public int pageIndex=1;
	public int pageSize=10;
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
		if(sqlPost==null || sqlPost.isEmpty()) {
			sqlPost="";
			if(groupBy!=null) {
				sqlPost+=groupBy +" ";
			}
			if(orderBy!=null) {
				sqlPost+=orderBy +" ";
			}
			sqlPost+=" limit "+((pageIndex-1)*pageSize)+","+pageSize+" ";
		}
		return sqlPost;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy=groupBy;
	}

	public void setorderBy(String orderBy) {
		this.orderBy=orderBy;
	}

	public void setPageSize(int pageSize) {
		this.pageSize=pageSize;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex=pageIndex;
	}

}
