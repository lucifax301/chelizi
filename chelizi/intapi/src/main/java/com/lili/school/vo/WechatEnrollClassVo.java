package com.lili.school.vo;


import java.io.Serializable;

public class WechatEnrollClassVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6766630316165309243L;

	private Integer classId;

    private Long coachId;

    private String className;

    private Integer drtype;

    private Integer price;

    private Integer prePrice;

    private Integer drtype2;

    private Integer price2;

    private Integer prePrice2;

    private String address;

    private String classTag;
    
    private String coachTag;

    private String classRemark;

    private String orderTag;

    private Integer schoolId;

    private Integer cityId;

    private String cityName;

    private Integer type;
    
    private String area;
    
    private String headIcon;
    
    private String schoolName;
    
    private String name;
    
	private String phoneNum;
	
    private Integer checkDrState;
    
    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Long getCoachId() {
        return coachId;
    }

    public void setCoachId(Long coachId) {
        this.coachId = coachId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public Integer getDrtype() {
        return drtype;
    }

    public void setDrtype(Integer drtype) {
        this.drtype = drtype;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(Integer prePrice) {
        this.prePrice = prePrice;
    }

    public Integer getDrtype2() {
        return drtype2;
    }

    public void setDrtype2(Integer drtype2) {
        this.drtype2 = drtype2;
    }

    public Integer getPrice2() {
        return price2;
    }

    public void setPrice2(Integer price2) {
        this.price2 = price2;
    }

    public Integer getPrePrice2() {
        return prePrice2;
    }

    public void setPrePrice2(Integer prePrice2) {
        this.prePrice2 = prePrice2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getClassTag() {
        return classTag;
    }

    public void setClassTag(String classTag) {
        this.classTag = classTag == null ? null : classTag.trim();
    }

    public String getCoachTag() {
		return coachTag;
	}

	public void setCoachTag(String coachTag) {
		this.coachTag = coachTag;
	}

	public String getClassRemark() {
        return classRemark;
    }

    public void setClassRemark(String classRemark) {
        this.classRemark = classRemark == null ? null : classRemark.trim();
    }

    public String getOrderTag() {
        return orderTag;
    }

    public void setOrderTag(String orderTag) {
        this.orderTag = orderTag == null ? null : orderTag.trim();
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getHeadIcon() {
		return headIcon;
	}

	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Integer getCheckDrState() {
		return checkDrState;
	}

	public void setCheckDrState(Integer checkDrState) {
		this.checkDrState = checkDrState;
	}
}