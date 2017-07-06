package com.lili.exam.dto;

import java.io.Serializable;
import java.util.Date;

public class ExamPlaceClass implements Serializable {
    private Integer id;

    private Integer placeId;

    private Date pstart;

    private Date pend;

    private Date rstart;

    private Date rend;

    private String remark;

    private Integer duration;

    private Integer outerPrice;

    private Integer innerPrice;

    private Integer minHours;

    private Integer c1;

    private Integer c1inner;

    private Integer c1outer;

    private Integer c1book;

    private Integer c1bookInner;

    private Integer c1bookOuter;

    private Integer c2;

    private Integer c2inner;

    private Integer c2outer;

    private Integer c2book;

    private Integer c2bookInner;

    private Integer c2bookOuter;

    private Integer innerExpire;

    private Integer favorType;

    private Integer favorIn;

    private Integer favorOut;

    private Byte state;

    private Date ctime;

    private Date mtime;

    private String extra;

    private static final long serialVersionUID = 1L;
    
    private String innerinfo;
    
    

    public String getInnerinfo() {
		return innerinfo;
	}

	public void setInnerinfo(String innerinfo) {
		this.innerinfo = innerinfo;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public Date getPstart() {
        return pstart;
    }

    public void setPstart(Date pstart) {
        this.pstart = pstart;
    }

    public Date getPend() {
        return pend;
    }

    public void setPend(Date pend) {
        this.pend = pend;
    }

    public Date getRstart() {
        return rstart;
    }

    public void setRstart(Date rstart) {
        this.rstart = rstart;
    }

    public Date getRend() {
        return rend;
    }

    public void setRend(Date rend) {
        this.rend = rend;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getOuterPrice() {
        return outerPrice;
    }

    public void setOuterPrice(Integer outerPrice) {
        this.outerPrice = outerPrice;
    }

    public Integer getInnerPrice() {
        return innerPrice;
    }

    public void setInnerPrice(Integer innerPrice) {
        this.innerPrice = innerPrice;
    }

    public Integer getMinHours() {
        return minHours;
    }

    public void setMinHours(Integer minHours) {
        this.minHours = minHours;
    }

    public Integer getC1() {
        return c1;
    }

    public void setC1(Integer c1) {
        this.c1 = c1;
    }

    public Integer getC1inner() {
        return c1inner;
    }

    public void setC1inner(Integer c1inner) {
        this.c1inner = c1inner;
    }

    public Integer getC1outer() {
        return c1outer;
    }

    public void setC1outer(Integer c1outer) {
        this.c1outer = c1outer;
    }

    public Integer getC1book() {
        return c1book;
    }

    public void setC1book(Integer c1book) {
        this.c1book = c1book;
    }

    public Integer getC1bookInner() {
        return c1bookInner;
    }

    public void setC1bookInner(Integer c1bookInner) {
        this.c1bookInner = c1bookInner;
    }

    public Integer getC1bookOuter() {
        return c1bookOuter;
    }

    public void setC1bookOuter(Integer c1bookOuter) {
        this.c1bookOuter = c1bookOuter;
    }

    public Integer getC2() {
        return c2;
    }

    public void setC2(Integer c2) {
        this.c2 = c2;
    }

    public Integer getC2inner() {
        return c2inner;
    }

    public void setC2inner(Integer c2inner) {
        this.c2inner = c2inner;
    }

    public Integer getC2outer() {
        return c2outer;
    }

    public void setC2outer(Integer c2outer) {
        this.c2outer = c2outer;
    }

    public Integer getC2book() {
        return c2book;
    }

    public void setC2book(Integer c2book) {
        this.c2book = c2book;
    }

    public Integer getC2bookInner() {
        return c2bookInner;
    }

    public void setC2bookInner(Integer c2bookInner) {
        this.c2bookInner = c2bookInner;
    }

    public Integer getC2bookOuter() {
        return c2bookOuter;
    }

    public void setC2bookOuter(Integer c2bookOuter) {
        this.c2bookOuter = c2bookOuter;
    }

    public Integer getInnerExpire() {
        return innerExpire;
    }

    public void setInnerExpire(Integer innerExpire) {
        this.innerExpire = innerExpire;
    }

    public Integer getFavorType() {
        return favorType;
    }

    public void setFavorType(Integer favorType) {
        this.favorType = favorType;
    }

    public Integer getFavorIn() {
        return favorIn;
    }

    public void setFavorIn(Integer favorIn) {
        this.favorIn = favorIn;
    }

    public Integer getFavorOut() {
        return favorOut;
    }

    public void setFavorOut(Integer favorOut) {
        this.favorOut = favorOut;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
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

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", placeId=").append(placeId);
        sb.append(", pstart=").append(pstart);
        sb.append(", pend=").append(pend);
        sb.append(", rstart=").append(rstart);
        sb.append(", rend=").append(rend);
        sb.append(", remark=").append(remark);
        sb.append(", duration=").append(duration);
        sb.append(", outerPrice=").append(outerPrice);
        sb.append(", innerPrice=").append(innerPrice);
        sb.append(", minHours=").append(minHours);
        sb.append(", c1=").append(c1);
        sb.append(", c1inner=").append(c1inner);
        sb.append(", c1outer=").append(c1outer);
        sb.append(", c1book=").append(c1book);
        sb.append(", c1bookInner=").append(c1bookInner);
        sb.append(", c1bookOuter=").append(c1bookOuter);
        sb.append(", c2=").append(c2);
        sb.append(", c2inner=").append(c2inner);
        sb.append(", c2outer=").append(c2outer);
        sb.append(", c2book=").append(c2book);
        sb.append(", c2bookInner=").append(c2bookInner);
        sb.append(", c2bookOuter=").append(c2bookOuter);
        sb.append(", innerExpire=").append(innerExpire);
        sb.append(", favorType=").append(favorType);
        sb.append(", favorIn=").append(favorIn);
        sb.append(", favorOut=").append(favorOut);
        sb.append(", state=").append(state);
        sb.append(", ctime=").append(ctime);
        sb.append(", mtime=").append(mtime);
        sb.append(", extra=").append(extra);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}