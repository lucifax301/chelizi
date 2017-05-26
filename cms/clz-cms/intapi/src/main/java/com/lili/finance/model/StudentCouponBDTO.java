package com.lili.finance.model;

import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

public class StudentCouponBDTO  extends BasePagedEntity{
	private static final long serialVersionUID = 1L;

	 private Long couponid;

	    private Long studentid;

	    private Date gettime;

	    private Date usetime;

	    private String coupontmpid;

	    private Integer stockid;

	    private Byte isUsed;

	    private Byte isValid;

	    private Date expiretime;

	    private String orderid;
	    
	    /**
	     * 筛选,使用状态：1--未使用,2--已使用,3---已过期,4--已作废
	     */
	    private Integer type;

	    public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public Long getCouponid() {
	        return couponid;
	    }

	    public void setCouponid(Long couponid) {
	        this.couponid = couponid;
	    }

	    public Long getStudentid() {
	        return studentid;
	    }

	    public void setStudentid(Long studentid) {
	        this.studentid = studentid;
	    }

	    public Date getGettime() {
	        return gettime;
	    }

	    public void setGettime(Date gettime) {
	        this.gettime = gettime;
	    }

	    public Date getUsetime() {
	        return usetime;
	    }

	    public void setUsetime(Date usetime) {
	        this.usetime = usetime;
	    }

	    public Integer getStockid() {
	        return stockid;
	    }

	    public void setStockid(Integer stockid) {
	        this.stockid = stockid;
	    }

	    public Byte getIsUsed() {
	        return isUsed;
	    }

	    public void setIsUsed(Byte isUsed) {
	        this.isUsed = isUsed;
	    }

	    public Byte getIsValid() {
	        return isValid;
	    }

	    public void setIsValid(Byte isValid) {
	        this.isValid = isValid;
	    }

	    public Date getExpiretime() {
	        return expiretime;
	    }

	    public void setExpiretime(Date expiretime) {
	        this.expiretime = expiretime;
	    }

	    public String getOrderid() {
	        return orderid;
	    }

	    public void setOrderid(String orderid) {
	        this.orderid = orderid == null ? null : orderid.trim();
	    }

	    public String getCoupontmpid() {
	        return coupontmpid;
	    }

	    public void setCoupontmpid(String coupontmpid) {
	        this.coupontmpid = coupontmpid;
	    }
}