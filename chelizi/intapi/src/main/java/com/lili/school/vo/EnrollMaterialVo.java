package com.lili.school.vo;

import java.io.Serializable;

public class EnrollMaterialVo implements Serializable {

    private Integer tmid;

    private Integer cityId;

    private Integer ttid;

    private Byte ptype;

    private String pdesc;

    private String pmid;

    private Byte isdel;

	public Integer getTmid() {
		return tmid;
	}

	public void setTmid(Integer tmid) {
		this.tmid = tmid;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getTtid() {
		return ttid;
	}

	public void setTtid(Integer ttid) {
		this.ttid = ttid;
	}

	public Byte getPtype() {
		return ptype;
	}

	public void setPtype(Byte ptype) {
		this.ptype = ptype;
	}

	public String getPdesc() {
		return pdesc;
	}

	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}

	public String getPmid() {
		return pmid;
	}

	public void setPmid(String pmid) {
		this.pmid = pmid;
	}

	public Byte getIsdel() {
		return isdel;
	}

	public void setIsdel(Byte isdel) {
		this.isdel = isdel;
	}
    
    
	
}
