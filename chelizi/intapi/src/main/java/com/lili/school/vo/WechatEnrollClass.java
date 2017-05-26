package com.lili.school.vo;


import java.io.Serializable;
import java.util.Date;

import com.lili.common.util.MyRowBounds;

public class WechatEnrollClass implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6488336331291742879L;

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

    private String classRemark;

    private String orderTag;

    private Integer schoolId;

    private Integer cityId;

    private String cityName;

    private Integer isDel;

    private Date ctime;

    private Date mtime;

    private Integer type;
    
    protected MyRowBounds myRowBounds;
    
    public MyRowBounds getMyRowBounds() {
		return myRowBounds;
	}

	public void setMyRowBounds(MyRowBounds myRowBounds) {
		this.myRowBounds = myRowBounds;
	}
    
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

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}