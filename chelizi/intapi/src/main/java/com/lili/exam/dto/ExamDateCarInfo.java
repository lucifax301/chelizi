package com.lili.exam.dto;

import java.io.Serializable;
import java.util.List;

public class ExamDateCarInfo implements Serializable {

	private int carId;
	
	private String carno;
	
	private List<ExamPlaceClassVo> clss;

	
	
	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public String getCarno() {
		return carno;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

	public List<ExamPlaceClassVo> getClss() {
		return clss;
	}

	public void setClss(List<ExamPlaceClassVo> clss) {
		this.clss = clss;
	}
	
	
}
