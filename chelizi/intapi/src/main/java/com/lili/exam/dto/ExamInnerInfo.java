package com.lili.exam.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExamInnerInfo implements Serializable {

	private List<ExamVipBookInfo> bookinfo;
	
	private List<String> bookcar;
	
	public ExamInnerInfo(){
		this.bookinfo=new ArrayList();
		this.bookcar=new ArrayList();
	}

	public List<ExamVipBookInfo> getBookinfo() {
		return bookinfo;
	}

	public void setBookinfo(List<ExamVipBookInfo> bookinfo) {
		this.bookinfo = bookinfo;
	}

	public List<String> getBookcar() {
		return bookcar;
	}

	public void setBookcar(List<String> bookcar) {
		this.bookcar = bookcar;
	}
	
	
}
