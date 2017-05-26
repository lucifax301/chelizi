package com.lili.coupon.dto;

import java.io.Serializable;
import java.util.Date;

public class CStock implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer stockid;

    private String eventtopic;

    private Integer total;

    private Integer haveused;

    private Date createtime;

    private String createuser;

    private Byte isexist;

    private String coupontempid;
    
    private String secCouponTempId;
    
    private String threeCouponTempId;
    
    private  String isMore;

    public Integer getStockid() {
        return stockid;
    }

    public void setStockid(Integer stockid) {
        this.stockid = stockid;
    }

    public String getEventtopic() {
        return eventtopic;
    }

    public void setEventtopic(String eventtopic) {
        this.eventtopic = eventtopic == null ? null : eventtopic.trim();
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getHaveused() {
        return haveused;
    }

    public void setHaveused(Integer haveused) {
        this.haveused = haveused;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public Byte getIsexist() {
        return isexist;
    }

    public void setIsexist(Byte isexist) {
        this.isexist = isexist;
    }

    /**
     * @return the coupontempid
     */
    public String getCoupontempid()
    {
        return coupontempid;
    }

    /**
     * @param coupontempid the coupontempid to set
     */
    public void setCoupontempid(String coupontempid)
    {
        this.coupontempid = coupontempid;
    }

	public String getSecCouponTempId() {
		return secCouponTempId;
	}

	public void setSecCouponTempId(String secCouponTempId) {
		this.secCouponTempId = secCouponTempId;
	}

	public String getIsMore() {
		return isMore;
	}

	public void setIsMore(String isMore) {
		this.isMore = isMore;
	}

	public String getThreeCouponTempId() {
		return threeCouponTempId;
	}

	public void setThreeCouponTempId(String threeCouponTempId) {
		this.threeCouponTempId = threeCouponTempId;
	}
}