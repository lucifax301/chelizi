package com.lili.school.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class EnrollExamPlaceVo implements Serializable {

    private Integer placeid;

    private String name;

    private Integer cityid;

    private BigDecimal lge;

    private BigDecimal lae;

    private String posdesc;

    private String phonenum;

	public Integer getPlaceid() {
		return placeid;
	}

	public void setPlaceid(Integer placeid) {
		this.placeid = placeid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public BigDecimal getLge() {
		return lge;
	}

	public void setLge(BigDecimal lge) {
		this.lge = lge;
	}

	public BigDecimal getLae() {
		return lae;
	}

	public void setLae(BigDecimal lae) {
		this.lae = lae;
	}

	public String getPosdesc() {
		return posdesc;
	}

	public void setPosdesc(String posdesc) {
		this.posdesc = posdesc;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
    
    
}
