package com.lili.finance.vo;

import java.io.Serializable;
import java.util.Date;

public class StudentCouponVo implements Serializable{
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
    
    private String studentName;
    
    private String name;
    
    private Integer type;
    
    private Integer moneyValue;
    
    private Integer discount;
    
    private String phoneNum;
    
    
    public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getMoneyValue() {
		return moneyValue;
	}

	public void setMoneyValue(Integer moneyValue) {
		this.moneyValue = moneyValue;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
}