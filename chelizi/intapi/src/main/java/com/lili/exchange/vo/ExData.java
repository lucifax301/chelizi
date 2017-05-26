package com.lili.exchange.vo;

import java.util.List;

public class ExData<T> {

	private Integer total;
	private List<T> dataArray;
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<T> getDataArray() {
		return dataArray;
	}
	public void setDataArray(List<T> dataArray) {
		this.dataArray = dataArray;
	}
	
}