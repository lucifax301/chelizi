package com.lili.common.util;

import java.io.Serializable;
public class BaseQuery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -273502340318825336L;
	private String sqlPost;
	private boolean  paging=false;
	private int pageIndex=1;
	private int pageSize=10;
	private String queryExt;
	private String groupBy;
	private String orderBy;
	private int cacheSecond=172800;
	protected String pkField;
	protected String projectVoKey;
	protected boolean realCache=true;

	public void setSqlPost(String sqlPost) {
		this.sqlPost=sqlPost;
	}

	public String getSqlPost() {
		String sqlPostTemp=sqlPost;
		if(sqlPost==null || sqlPost.isEmpty()) {
			sqlPostTemp=" ";
			if(groupBy!=null) {
				sqlPostTemp+=groupBy +" ";
			}
			if(orderBy!=null) {
				sqlPostTemp+=orderBy +" ";
			}
			if(getPaging()){
				sqlPostTemp+=" limit "+((getPageIndex()-1)*getPageSize())+","+getPageSize()+" ";
			}
		}
		if(sqlPostTemp.indexOf(";")>=0||sqlPostTemp.indexOf(" select ")>=0||	sqlPostTemp.indexOf(" from ")>=0||sqlPostTemp.indexOf(" update ")>=0||sqlPostTemp.indexOf(" delete ")>=0) {
			sqlPostTemp=" ";
		}
		return sqlPostTemp;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy=groupBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy=orderBy;
	}

	public boolean getPaging() {
		return this.paging;
	}

	public void setPageSize(Integer pageSize) {
		if(pageSize!=null && pageSize>0){
			this.pageSize=pageSize;
			this.paging=true;
		}
	}

	public void setPageIndex(Integer pageIndex) {
		if(pageIndex!=null && pageIndex>0){
			this.pageIndex=pageIndex;
			this.paging=true;
		}
	}
	public int getPageSize() {
		return this.pageSize;
	}

	public int getPageIndex() {
		return this.pageIndex;
	}

	public void setQueryExt(String queryExt) {
		if(queryExt==null||queryExt.indexOf(";")>=0||queryExt.indexOf("select")>=0||queryExt.indexOf("from")>=0) {
			this.queryExt=" ";
		} else {
			this.queryExt=queryExt;
		}
	}

	public String getQueryExt() {
		return this.queryExt;
	}

	public void setCacheSecond(int cacheSecond) {
		this.cacheSecond=cacheSecond;
	}

	public int getCacheSecond() {
		return this.cacheSecond;
	}
  	public String getPkField(){
  		return this.pkField;
  	};
  	public boolean getRealCache(){
  		return this.realCache;
  	};
  	public String getProjectVoKey(){
  		return this.projectVoKey;
  	};
}
