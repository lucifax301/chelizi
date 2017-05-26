package com.lili.order.dto;

import java.io.Serializable;
import java.util.Date;

public class StudentClassPool extends StudentClassPoolKey implements Serializable {
    private Long carId;

    private Long studentId;

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

    private static final long serialVersionUID = 1L;
    
    //补充带教学员
    private Integer bookSum;
    
    /**
	 * 年龄
	 */
	private Integer age;
	
	
	private Integer coachLevel;
	
	private String teyue;
	/**
	 * 评论数
	 */
	private Integer comment=0;
	
	

    public Integer getComment() {
		return comment;
	}

	public void setComment(Integer comment) {
		this.comment = comment;
	}

	public Integer getCoachLevel() {
		return coachLevel;
	}

	public void setCoachLevel(Integer coachLevel) {
		this.coachLevel = coachLevel;
	}

	

	public String getTeyue() {
		return teyue;
	}

	public void setTeyue(String teyue) {
		this.teyue = teyue;
	}

	public Integer getBookSum() {
		return bookSum;
	}

	public void setBookSum(Integer bookSum) {
		this.bookSum = bookSum;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

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

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo == null ? null : carNo.trim();
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
        sb.append(", carId=").append(carId);
        sb.append(", studentId=").append(studentId);
        sb.append(", coachName=").append(coachName);
        sb.append(", coachImg=").append(coachImg);
        sb.append(", coachMobile=").append(coachMobile);
        sb.append(", carNo=").append(carNo);
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
}