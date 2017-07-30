package com.lili.exam.dto;

import java.io.Serializable;
import java.util.Date;

public class ExamPlaceClassVo implements Serializable {
    private Integer id;

    private Integer placeId;

    private Date pstart;

    private Date pend;
    
    private Date rstart;

    private Date rend;
    
    /**
     * 根据用户类型，区分对内价格还是对外价格
     */
    private Integer price;

    /**
     * 根据用户类型，区分对内或对外的数量总数
     */
    private Integer c;

    /**
     * 根据用户类型，已约数量
     */
    private Integer cbook;
    
    /**
     * 排班状态：0-可约；1-已约；2-不可约；
     */
    private Integer state;
    
    private int used;
    
    private String bitmap;
    
    
    
    public int getUsed() {
		return used;
	}

	public void setUsed(int used) {
		this.used = used;
	}

	public String getBitmap() {
		return bitmap;
	}

	public void setBitmap(String bitmap) {
		this.bitmap = bitmap;
	}

	/**
     * 优惠方案
     */
    private Integer favorType;

    private static final long serialVersionUID = 1L;

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

    public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getC() {
		return c;
	}

	public void setC(Integer c) {
		this.c = c;
	}

	public Integer getCbook() {
		return cbook;
	}

	public void setCbook(Integer cbook) {
		this.cbook = cbook;
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
        sb.append(", favorType=").append(favorType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
	
	public Integer getFavorType() {
		return favorType;
	}

	public void setFavorType(Integer favorType) {
		this.favorType = favorType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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
}