package com.lili.common.util;

import java.io.Serializable;
import java.util.List;
/**
 * 公共基础类：分页
 * @author yangpeng
 * 
 * @param <T>
 */
public class Page<T> implements Serializable {

	private static final long serialVersionUID = 2948917877744923448L;
	
	private List<T> dataList;				// dataList result of this page
	private int pageNo;				// page number
	private int pageSize;				// result amount of this page
	private int pages;				// total page
	private int total;				// total row
	
	public Page(List<T> dataList, int pageNo, int pageSize, int pages, int total) {
		this.dataList = dataList;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.pages = pages;
		this.total = total;
	}
	
	public Page(List<T> dataList, int pageNo, int pageSize, int total) {
		this.dataList = dataList;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.total = total;
		if(total == 0){
			this.pages = 0;
		}else {
			this.pages = (total / pageSize) > 0? (total / pageSize) :1;
		}
		if (this.total > this.pageSize && this.total % this.pageSize != 0) {
			this.pages++;
		}
	}
		
	public Page() {
		super();
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
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

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public static void main(String[] args) {
		Page p= new Page(null,1,10,11);
		System.out.println(p.getPages());
		Page p1= new Page(null,1,10,4);
		System.out.println(p1.getPages());
		Page p2= new Page(null,1,10,0);
		System.out.println(p2.getPages());
	}

	@Override
	public String toString() {
		return "Page [dataList=" + dataList + ", pageNo=" + pageNo
				+ ", pageSize=" + pageSize + ", pages=" + pages + ", total="
				+ total + "]";
	}
	
	
}
