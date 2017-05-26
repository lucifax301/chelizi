package com.lili.school.dto;

import java.io.Serializable;
import java.util.Date;

import com.lili.common.util.MyRowBounds;

public class WechatPlantClassDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9219021034904285954L;

	private String orderId;

    private Integer ccid;

    private Long coachId;

    private Long studentId;

    private Date gtime;

    private String stuName;

    private String stuImg;

    private String stuMobile;

    private Integer state;
    
    private Integer isdel;

    private Integer price;
    
    private String ccidin;

    protected MyRowBounds myRowBounds;
    
    public MyRowBounds getMyRowBounds() {
		return myRowBounds;
	}

	public void setMyRowBounds(MyRowBounds myRowBounds) {
		this.myRowBounds = myRowBounds;
	}

	
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getCcid() {
        return ccid;
    }

    public void setCcid(Integer ccid) {
        this.ccid = ccid;
    }

    public Long getCoachId() {
        return coachId;
    }

    public void setCoachId(Long coachId) {
        this.coachId = coachId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Date getGtime() {
        return gtime;
    }

    public void setGtime(Date gtime) {
        this.gtime = gtime;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName == null ? null : stuName.trim();
    }

    public String getStuImg() {
        return stuImg;
    }

    public void setStuImg(String stuImg) {
        this.stuImg = stuImg == null ? null : stuImg.trim();
    }

    public String getStuMobile() {
        return stuMobile;
    }

    public void setStuMobile(String stuMobile) {
        this.stuMobile = stuMobile == null ? null : stuMobile.trim();
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

	public String getCcidin() {
		return ccidin;
	}

	public void setCcidin(String ccidin) {
		this.ccidin = ccidin;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}