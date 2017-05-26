package com.lili.user.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 导入场地的记录实体
 * @author hughes
 *
 */
public class FieldFile implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer schoolid;

	private BigDecimal lge;

	private BigDecimal lae;

	private String posdesc;

	private Integer reverselim;

	private String phonenum;

	private Integer region;

	private String name;

	private String extra;

	private Date createtime;

	private String creatorName;

	private String billNo;
	
	private String schoolName;
	private String cityName;

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}		 

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(Integer schoolid) {
		this.schoolid = schoolid;
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
		this.posdesc = posdesc == null ? null : posdesc.trim();
	}

	public Integer getReverselim() {
		return reverselim;
	}

	public void setReverselim(Integer reverselim) {
		this.reverselim = reverselim;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum == null ? null : phonenum.trim();
	}

	public Integer getRegion() {
		return region;
	}

	public void setRegion(Integer region) {
		this.region = region;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra == null ? null : extra.trim();
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
