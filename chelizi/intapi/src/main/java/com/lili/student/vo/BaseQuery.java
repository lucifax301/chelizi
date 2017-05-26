package com.lili.student.vo;

import java.io.Serializable;
public abstract class BaseQuery implements Serializable {
	public String getSqlField() {
		return " * ";
	}

	public String getSqlPost() {
		return " ";
	}

}
