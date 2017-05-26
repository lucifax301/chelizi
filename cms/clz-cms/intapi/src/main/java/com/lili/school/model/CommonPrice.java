package com.lili.school.model;


import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

public class CommonPrice extends BasePagedEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2217662103316217649L;

	private Integer upid;

    private Integer cityId;

    private Integer courseId;

    private Integer colid;

    private Integer calid;

    private Integer dftype;

    private String tstart;

    private String tend;

    private Integer price;

    private Integer allowance;

    private Integer verify;

    private Integer isdel;

    private Integer cuid;

    private Integer muid;

    private Date ctime;

    private Date mtime;

    public Integer getUpid() {
        return upid;
    }

    public void setUpid(Integer upid) {
        this.upid = upid;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getColid() {
        return colid;
    }

    public void setColid(Integer colid) {
        this.colid = colid;
    }

    public Integer getCalid() {
        return calid;
    }

    public void setCalid(Integer calid) {
        this.calid = calid;
    }

    public Integer getDftype() {
        return dftype;
    }

    public void setDftype(Integer dftype) {
        this.dftype = dftype;
    }

    public String getTstart() {
        return tstart;
    }

    public void setTstart(String tstart) {
        this.tstart = tstart == null ? null : tstart.trim();
    }

    public String getTend() {
        return tend;
    }

    public void setTend(String tend) {
        this.tend = tend == null ? null : tend.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAllowance() {
        return allowance;
    }

    public void setAllowance(Integer allowance) {
        this.allowance = allowance;
    }

    public Integer getVerify() {
        return verify;
    }

    public void setVerify(Integer verify) {
        this.verify = verify;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public Integer getCuid() {
        return cuid;
    }

    public void setCuid(Integer cuid) {
        this.cuid = cuid;
    }

    public Integer getMuid() {
        return muid;
    }

    public void setMuid(Integer muid) {
        this.muid = muid;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }
}