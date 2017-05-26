package com.lili.configfile.vo;

import java.io.Serializable;


public class AdFileVo implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -3939470127358205573L;

	
	// 一级目录类型：1-首页；2-理论课；3-约教练；4-我的
	private String serialId; 
	
	//	时间，可以任意格式
	private String dtime;
	
	//	描述
	private String description;

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public String getDtime() {
		return dtime;
	}

	public void setDtime(String dtime) {
		this.dtime = dtime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}




	
}
