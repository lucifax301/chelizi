package com.lili.order.dto;

import java.io.Serializable;
import java.util.Date;

public class StudentClassPoolVo implements Serializable {
    private String orderId;

    private Long coachId;
    
    private Long carId;

    private String coachName;

    private String coachImg;

    private String coachMobile;

    private String carNo;
    
    private String schoolName;

    private Integer starLevel;

    private Double distance;

    private Integer placeId;

    private String placeName;

    private Double placeLge;

    private Double placeLae;

    private Byte state;

    private Date ctime;

    private Date mtime;

    private String extra;
	/**
	 * 教练性别
	 */
	private Integer gender;
	/**
	 * 教练驾考类别
	 */
	private Integer dltype;
	/**
	 * 教练状态，是否出车
	 */
	private Integer wstate;
	/**
	 * 是否特约教练
	 */
	private Integer isVip;

    private static final long serialVersionUID = 1L;

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName == null ? null : coachName.trim();
    }

    public String getCoachImg() {
        return coachImg;
    }

    public void setCoachImg(String coachImg) {
        this.coachImg = coachImg == null ? null : coachImg.trim();
    }

    public String getCoachMobile() {
        return coachMobile;
    }

    public void setCoachMobile(String coachMobile) {
        this.coachMobile = coachMobile == null ? null : coachMobile.trim();
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    public Integer getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(Integer starLevel) {
        this.starLevel = starLevel;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName == null ? null : placeName.trim();
    }

    public Double getPlaceLge() {
        return placeLge;
    }

    public void setPlaceLge(Double placeLge) {
        this.placeLge = placeLge;
    }

    public Double getPlaceLae() {
        return placeLae;
    }

    public void setPlaceLae(Double placeLae) {
        this.placeLae = placeLae;
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
        sb.append(", coachName=").append(coachName);
        sb.append(", coachImg=").append(coachImg);
        sb.append(", coachMobile=").append(coachMobile);
        sb.append(", schoolName=").append(schoolName);
        sb.append(", starLevel=").append(starLevel);
        sb.append(", distance=").append(distance);
        sb.append(", placeId=").append(placeId);
        sb.append(", placeName=").append(placeName);
        sb.append(", placeLge=").append(placeLge);
        sb.append(", placeLae=").append(placeLae);
        sb.append(", state=").append(state);
        sb.append(", ctime=").append(ctime);
        sb.append(", mtime=").append(mtime);
        sb.append(", extra=").append(extra);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getDltype() {
		return dltype;
	}

	public void setDltype(Integer dltype) {
		this.dltype = dltype;
	}

	public Integer getWstate() {
		return wstate;
	}

	public void setWstate(Integer wstate) {
		this.wstate = wstate;
	}

	public Integer getIsVip() {
		return isVip;
	}

	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getCoachId() {
		return coachId;
	}

	public void setCoachId(Long coachId) {
		this.coachId = coachId;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	
}