package com.lili.order.dto;

import java.io.Serializable;
import java.util.Date;

public class StudentClass implements Serializable {
    private Integer id;

    private String orderId;

    private Long studentId;

    private String stuName;

    private String stuImg;

    private String stuMobile;

    private Byte dltype;

    private Integer courseId;

    private String courseName;

    private Date cstart;

    private Date cend;

    private Byte clznum;

    private Integer price;

    private Double lge;

    private Double lae;

    private String placeInfo;

    private Integer regionId;

    private Byte state;

    private Date ctime;

    private Date mtime;

    private String extra;
    
    private Integer direct;

    private static final long serialVersionUID = 1L;
    //0没有支付，1支付
    private Integer payState=0;
    
    private Long directCoachId;
    
    private String carNo;
    
    private String dataVersion;
    
    

    

	public String getDataVersion() {
		return dataVersion;
	}

	public void setDataVersion(String dataVersion) {
		this.dataVersion = dataVersion;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public Long getDirectCoachId() {
		return directCoachId;
	}

	public void setDirectCoachId(Long directCoachId) {
		this.directCoachId = directCoachId;
	}

	public Integer getPayState() {
		return payState;
	}

	public void setPayState(Integer payState) {
		this.payState = payState;
	}

	public Integer getDirect() {
		return direct;
	}

	public void setDirect(Integer direct) {
		this.direct = direct;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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

    public Byte getDltype() {
        return dltype;
    }

    public void setDltype(Byte dltype) {
        this.dltype = dltype;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
    }

    public Date getCstart() {
        return cstart;
    }

    public void setCstart(Date cstart) {
        this.cstart = cstart;
    }

    public Date getCend() {
        return cend;
    }

    public void setCend(Date cend) {
        this.cend = cend;
    }

    public Byte getClznum() {
        return clznum;
    }

    public void setClznum(Byte clznum) {
        this.clznum = clznum;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Double getLge() {
        return lge;
    }

    public void setLge(Double lge) {
        this.lge = lge;
    }

    public Double getLae() {
        return lae;
    }

    public void setLae(Double lae) {
        this.lae = lae;
    }

    public String getPlaceInfo() {
        return placeInfo;
    }

    public void setPlaceInfo(String placeInfo) {
        this.placeInfo = placeInfo == null ? null : placeInfo.trim();
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
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
        sb.append(", orderId=").append(orderId);
        sb.append(", studentId=").append(studentId);
        sb.append(", stuName=").append(stuName);
        sb.append(", stuImg=").append(stuImg);
        sb.append(", stuMobile=").append(stuMobile);
        sb.append(", dltype=").append(dltype);
        sb.append(", courseId=").append(courseId);
        sb.append(", courseName=").append(courseName);
        sb.append(", cstart=").append(cstart);
        sb.append(", cend=").append(cend);
        sb.append(", clznum=").append(clznum);
        sb.append(", price=").append(price);
        sb.append(", lge=").append(lge);
        sb.append(", lae=").append(lae);
        sb.append(", placeInfo=").append(placeInfo);
        sb.append(", regionId=").append(regionId);
        sb.append(", state=").append(state);
        sb.append(", ctime=").append(ctime);
        sb.append(", mtime=").append(mtime);
        sb.append(", extra=").append(extra);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}