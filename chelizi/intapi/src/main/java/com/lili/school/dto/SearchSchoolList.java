package com.lili.school.dto;

import java.io.Serializable;
import java.util.List;

public class SearchSchoolList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6644576284194048226L;
	
	private List<School> schHasClassList;
	
	private List<School> schNoClassList;

	public List<School> getSchHasClassList() {
		return schHasClassList;
	}

	public void setSchHasClassList(List<School> schHasClassList) {
		this.schHasClassList = schHasClassList;
	}

	public List<School> getSchNoClassList() {
		return schNoClassList;
	}

	public void setSchNoClassList(List<School> schNoClassList) {
		this.schNoClassList = schNoClassList;
	}

	
}