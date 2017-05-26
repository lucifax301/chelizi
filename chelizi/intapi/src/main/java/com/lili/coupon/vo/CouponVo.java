/**
 * 
 */
package com.lili.coupon.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author linbo
 *
 */
public class CouponVo implements Serializable
{
    private static final long serialVersionUID = -3341281237442382980L;

    private String coupontmpid;

    private String name;

    private String listbackimg;

    private Byte indepentuse;

    private Byte type;

    private Integer validityperiod;

    private Integer moneyvalue;

    private Integer discount;

    private Byte isexist;

    private String icon;

    private String usenote;

    private String qrcodeurl;

    private Byte verify;

    private Long couponid;

    private Long studentid;

    private Date gettime;

    private Date usetime;

    private Integer stockid;

    private Date expiretime;

    private String orderid;

    private int realValue;
    
    private Integer useNum;
    
    private Integer maxNum;
    
    private Integer isUse;

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the listbackimg
     */
    public String getListbackimg()
    {
        return listbackimg;
    }

    /**
     * @param listbackimg the listbackimg to set
     */
    public void setListbackimg(String listbackimg)
    {
        this.listbackimg = listbackimg;
    }

    /**
     * @return the indepentuse
     */
    public Byte getIndepentuse()
    {
        return indepentuse;
    }

    /**
     * @param indepentuse the indepentuse to set
     */
    public void setIndepentuse(Byte indepentuse)
    {
        this.indepentuse = indepentuse;
    }

    /**
     * @return the type
     */
    public Byte getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Byte type)
    {
        this.type = type;
    }

    /**
     * @return the validityperiod
     */
    public Integer getValidityperiod()
    {
        return validityperiod;
    }

    /**
     * @param validityperiod the validityperiod to set
     */
    public void setValidityperiod(Integer validityperiod)
    {
        this.validityperiod = validityperiod;
    }

    /**
     * @return the moneyvalue
     */
    public Integer getMoneyvalue()
    {
        return moneyvalue;
    }

    /**
     * @param moneyvalue the moneyvalue to set
     */
    public void setMoneyvalue(Integer moneyvalue)
    {
        this.moneyvalue = moneyvalue;
    }

    /**
     * @return the discount
     */
    public Integer getDiscount()
    {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(Integer discount)
    {
        this.discount = discount;
    }

    /**
     * @return the isexist
     */
    public Byte getIsexist()
    {
        return isexist;
    }

    /**
     * @param isexist the isexist to set
     */
    public void setIsexist(Byte isexist)
    {
        this.isexist = isexist;
    }

    /**
     * @return the icon
     */
    public String getIcon()
    {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    /**
     * @return the usenote
     */
    public String getUsenote()
    {
        return usenote;
    }

    /**
     * @param usenote the usenote to set
     */
    public void setUsenote(String usenote)
    {
        this.usenote = usenote;
    }

    /**
     * @return the qrcodeurl
     */
    public String getQrcodeurl()
    {
        return qrcodeurl;
    }

    /**
     * @param qrcodeurl the qrcodeurl to set
     */
    public void setQrcodeurl(String qrcodeurl)
    {
        this.qrcodeurl = qrcodeurl;
    }

    /**
     * @return the verify
     */
    public Byte getVerify()
    {
        return verify;
    }

    /**
     * @param verify the verify to set
     */
    public void setVerify(Byte verify)
    {
        this.verify = verify;
    }

    /**
     * @return the couponid
     */
    public Long getCouponid()
    {
        return couponid;
    }

    /**
     * @param couponid the couponid to set
     */
    public void setCouponid(Long couponid)
    {
        this.couponid = couponid;
    }

    /**
     * @return the studentid
     */
    public Long getStudentid()
    {
        return studentid;
    }

    /**
     * @param studentid the studentid to set
     */
    public void setStudentid(Long studentid)
    {
        this.studentid = studentid;
    }

    /**
     * @return the gettime
     */
    public Date getGettime()
    {
        return gettime;
    }

    /**
     * @param gettime the gettime to set
     */
    public void setGettime(Date gettime)
    {
        this.gettime = gettime;
    }

    /**
     * @return the usetime
     */
    public Date getUsetime()
    {
        return usetime;
    }

    /**
     * @param usetime the usetime to set
     */
    public void setUsetime(Date usetime)
    {
        this.usetime = usetime;
    }

    /**
     * @return the stockid
     */
    public Integer getStockid()
    {
        return stockid;
    }

    /**
     * @param stockid the stockid to set
     */
    public void setStockid(Integer stockid)
    {
        this.stockid = stockid;
    }

    /**
     * @return the expiretime
     */
    public Date getExpiretime()
    {
        return expiretime;
    }

    /**
     * @param expiretime the expiretime to set
     */
    public void setExpiretime(Date expiretime)
    {
        this.expiretime = expiretime;
    }

    /**
     * @return the orderid
     */
    public String getOrderid()
    {
        return orderid;
    }

    /**
     * @param orderid the orderid to set
     */
    public void setOrderid(String orderid)
    {
        this.orderid = orderid;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "CouponVo [coupontmpid=" + coupontmpid + ", name=" + name + ", listbackimg=" + listbackimg
                + ", indepentuse=" + indepentuse + ", type=" + type + ", validityperiod=" + validityperiod
                + ", moneyvalue=" + moneyvalue + ", discount=" + discount + ", isexist=" + isexist + ", icon=" + icon
                + ", usenote=" + usenote + ", qrcodeurl=" + qrcodeurl + ", verify=" + verify + ", couponid=" + couponid
                + ", studentid=" + studentid + ", gettime=" + gettime + ", usetime=" + usetime + ", stockid=" + stockid
                + ", expiretime=" + expiretime + ", orderid=" + orderid + "]";
    }

    /**
     * @return the coupontmpid
     */
    public String getCoupontmpid()
    {
        return coupontmpid;
    }

    /**
     * @param coupontmpid the coupontmpid to set
     */
    public void setCoupontmpid(String coupontmpid)
    {
        this.coupontmpid = coupontmpid;
    }

    /**
     * @return the realValue
     */
    public int getRealValue()
    {
        return realValue;
    }

    /**
     * @param realValue the realValue to set
     */
    public void setRealValue(int realValue)
    {
        this.realValue = realValue;
    }

	public Integer getUseNum() {
		return useNum;
	}

	public void setUseNum(Integer useNum) {
		this.useNum = useNum;
	}

	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public Integer getIsUse() {
		return isUse;
	}

	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}

    
}
