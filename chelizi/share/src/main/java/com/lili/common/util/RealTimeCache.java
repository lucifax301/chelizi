package com.lili.common.util;

import java.io.Serializable;
import java.util.List;


public class RealTimeCache<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2138170243008235179L;
	private T object;
	private List<String> keyList;
	private List<Long> vaueList;
	
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}
	public List<String> getKeyList() {
		return keyList;
	}
	public void setKeyList(List<String> keyList) {
		this.keyList = keyList;
	}
	public List<Long> getVaueList() {
		return vaueList;
	}
	public void setVaueList(List<Long> vaueList) {
		this.vaueList = vaueList;
	}
}
