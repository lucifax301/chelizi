package com.lili.order.vo;

import java.io.Serializable;

public class CoachClassTempNameVo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5675446923563972517L;


    private Integer tempId;

    private String tempName;


	public Integer getTempId() {
		return tempId;
	}

	public void setTempId(Integer tempId) {
		this.tempId = tempId;
	}

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

}