package com.lili.school.vo;


import java.io.Serializable;

public class WechatStudentCount implements Serializable {



    /**
	 * 
	 */
	private static final long serialVersionUID = 3819567074248809263L;

	private Integer course0;
    
    private Integer course1;
    
    private Integer course2;
    
    private Integer course3;
    
    private Integer course4;
    
    private Integer course5;
    
    private Integer countTotal;

	public Integer getCourse0() {
		return course0;
	}

	public void setCourse0(Integer course0) {
		this.course0 = course0;
	}

	public Integer getCourse1() {
		return course1;
	}

	public void setCourse1(Integer course1) {
		this.course1 = course1;
	}

	public Integer getCourse2() {
		return course2;
	}

	public void setCourse2(Integer course2) {
		this.course2 = course2;
	}

	public Integer getCourse3() {
		return course3;
	}

	public void setCourse3(Integer course3) {
		this.course3 = course3;
	}

	public Integer getCourse4() {
		return course4;
	}

	public void setCourse4(Integer course4) {
		this.course4 = course4;
	}

	public Integer getCourse5() {
		return course5;
	}

	public void setCourse5(Integer course5) {
		this.course5 = course5;
	}

	public Integer getCountTotal() {
		return countTotal;
	}

	public void setCountTotal(Integer countTotal) {
		this.countTotal = countTotal;
	}


}