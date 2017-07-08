package com.lili.exam.dto;

import java.io.Serializable;

public class ExamVipBookInfo implements Serializable {

	private int vipId;
	
	private int c1;
	
	private int c2;
	
	private int c1book;
	
	private int c2book;

	public int getVipId() {
		return vipId;
	}

	public void setVipId(int vipId) {
		this.vipId = vipId;
	}

	public int getC1() {
		return c1;
	}

	public void setC1(int c1) {
		this.c1 = c1;
	}

	public int getC2() {
		return c2;
	}

	public void setC2(int c2) {
		this.c2 = c2;
	}

	public int getC1book() {
		return c1book;
	}

	public void setC1book(int c1book) {
		this.c1book = c1book;
	}

	public int getC2book() {
		return c2book;
	}

	public void setC2book(int c2book) {
		this.c2book = c2book;
	}
	
	
}
