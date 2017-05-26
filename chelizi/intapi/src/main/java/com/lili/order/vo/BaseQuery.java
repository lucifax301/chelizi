package com.lili.order.vo;

import java.io.Serializable;

public abstract class BaseQuery implements Serializable {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    public String getSqlField() {
		return " * ";
	}

	public String getSqlPost() {
		return " ";
	}

}
