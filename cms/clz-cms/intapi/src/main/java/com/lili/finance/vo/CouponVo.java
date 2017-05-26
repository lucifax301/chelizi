package com.lili.finance.vo;

import java.io.Serializable;
import java.util.Date;

public class CouponVo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String coupontmpid;

    private String name;

    private String listbackimg;

    private Byte indepentuse;

    private Byte type;
    
    private String eventtopic;

    private Integer validityperiod;

    private Integer moneyvalue;

    private Integer discount;

    private Byte isexist;

    private String icon;

    private String usenote;

    private String qrcodeurl;

    private Byte verify;

    private String genrule;

    private String userule;
    
    private String genruleStr;
    
    private String useruleStr;

    private String createuser;

    private Date createtime;

    private String jpushmsg;

    private Integer smsmsgtype;

    private Integer limitvalue;

    private String couponpic;

    private String couponurl;

    private String extra;

    private Date expireTime;
    
    private Long stockId;
    
    /**
     * 已发放多少
     */
    private Integer releasedAmount;
    /**
     * 已过期多少
     */
    private Integer expiredAmount;
    /**
     * 剩余多少
     */
    private Integer leftAmount;
    /**
     * 使用了多少
     */
    private Integer haveUsed;
    /**
     * 共有多少
     */
    private Integer total;
    
    private Integer groupType;
    
    
    public String getEventtopic() {
		return eventtopic;
	}

	public void setEventtopic(String eventtopic) {
		this.eventtopic = eventtopic;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public Integer getReleasedAmount() {
		return releasedAmount;
	}

	public void setReleasedAmount(Integer releasedAmount) {
		this.releasedAmount = releasedAmount;
	}

	public Integer getExpiredAmount() {
		return expiredAmount;
	}

	public void setExpiredAmount(Integer expiredAmount) {
		this.expiredAmount = expiredAmount;
	}

	public Integer getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(Integer leftAmount) {
		this.leftAmount = leftAmount;
	}

	public Integer getHaveUsed() {
		return haveUsed;
	}

	public void setHaveUsed(Integer haveUsed) {
		this.haveUsed = haveUsed;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getCouponpic() {
        return couponpic;
    }

    public void setCouponpic(String couponpic) {
        this.couponpic = couponpic == null ? null : couponpic.trim();
    }

    public String getCoupontmpid() {
        return coupontmpid;
    }

    public void setCoupontmpid(String coupontmpid) {
        this.coupontmpid = coupontmpid == null ? null : coupontmpid.trim();
    }

    public String getCouponurl() {
        return couponurl;
    }

    public void setCouponurl(String couponurl) {
        this.couponurl = couponurl == null ? null : couponurl.trim();
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

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

    public String getGenrule() {
        return genrule;
    }

    public void setGenrule(String genrule) {
        this.genrule = genrule == null ? null : genrule.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Byte getIndepentuse() {
        return indepentuse;
    }

    public void setIndepentuse(Byte indepentuse) {
        this.indepentuse = indepentuse;
    }

    public Byte getIsexist() {
        return isexist;
    }

    public void setIsexist(Byte isexist) {
        this.isexist = isexist;
    }

    public String getJpushmsg() {
        return jpushmsg;
    }

    public void setJpushmsg(String jpushmsg) {
        this.jpushmsg = jpushmsg == null ? null : jpushmsg.trim();
    }

    public Integer getLimitvalue() {
        return limitvalue;
    }

    public void setLimitvalue(Integer limitvalue) {
        this.limitvalue = limitvalue;
    }

    public String getListbackimg() {
        return listbackimg;
    }

    public void setListbackimg(String listbackimg) {
        this.listbackimg = listbackimg == null ? null : listbackimg.trim();
    }

    public Integer getMoneyvalue() {
        return moneyvalue;
    }

    public void setMoneyvalue(Integer moneyvalue) {
        this.moneyvalue = moneyvalue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getQrcodeurl() {
        return qrcodeurl;
    }

    public void setQrcodeurl(String qrcodeurl) {
        this.qrcodeurl = qrcodeurl == null ? null : qrcodeurl.trim();
    }

    public Integer getSmsmsgtype() {
        return smsmsgtype;
    }

    public void setSmsmsgtype(Integer smsmsgtype) {
        this.smsmsgtype = smsmsgtype;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getUsenote() {
        return usenote;
    }

    public void setUsenote(String usenote) {
        this.usenote = usenote == null ? null : usenote.trim();
    }

    public String getUserule() {
        return userule;
    }

    public void setUserule(String userule) {
        this.userule = userule == null ? null : userule.trim();
    }

    public Integer getValidityperiod() {
        return validityperiod;
    }

    public void setValidityperiod(Integer validityperiod) {
        this.validityperiod = validityperiod;
    }

    public Byte getVerify() {
        return verify;
    }

    public void setVerify(Byte verify) {
        this.verify = verify;
    }

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	public String getGenruleStr() {
		return genruleStr;
	}

	public void setGenruleStr(String genruleStr) {
		this.genruleStr = genruleStr;
	}

	public String getUseruleStr() {
		return useruleStr;
	}

	public void setUseruleStr(String useruleStr) {
		this.useruleStr = useruleStr;
	}
}