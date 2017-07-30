package com.lili.exam.dto;

import java.io.Serializable;
import java.util.Date;

public class ExamCarState implements Serializable {

	public static void main(String args[]){
		System.out.println(~1);
		String a1="1011101";
		String a2="0111100";
		String a3="1111111";
		System.out.println(Long.toBinaryString((Long.parseLong(a1,2)|Long.parseLong(a2,2))));
		System.out.println(Integer.parseInt(a2, 2));
		System.out.println(Integer.toBinaryString(Integer.parseInt(a2, 2)^Integer.parseInt(a3, 2)));
		System.out.println(Long.toBinaryString((Long.parseLong(a1,2)&Long.parseLong(a2,2))));
		Date now=new Date();
		System.out.println(now.getHours()+" "+now.getMinutes());
	}
	
	private String carno;
	
	private String bitmap;

	public String getCarno() {
		return carno;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

	public String getBitmap() {
		return bitmap;
	}

	public void setBitmap(String bitmap) {
		this.bitmap = bitmap;
	}
	
	
}
