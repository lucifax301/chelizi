package com.lili.exam.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ExamPlace implements Serializable {
    private Integer id;

    private String name;

    private String type;

    private Integer area;

    private String contact;

    private String mobile;

    private String address;

    private BigDecimal lge;

    private BigDecimal lae;

    private String city;

    private Integer cityId;

    private String school;

    private Integer schoolId;

    private Integer days;

    private Byte state;

    private Date ctime;

    private Date mtime;

    private String extra;
    
    private int servicetype;

    private static final long serialVersionUID = 1L;

    
    
    public int getServicetype() {
		return servicetype;
	}

	public void setServicetype(int servicetype) {
		this.servicetype = servicetype;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public BigDecimal getLge() {
        return lge;
    }

    public void setLge(BigDecimal lge) {
        this.lge = lge;
    }

    public BigDecimal getLae() {
        return lae;
    }

    public void setLae(BigDecimal lae) {
        this.lae = lae;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
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
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", area=").append(area);
        sb.append(", contact=").append(contact);
        sb.append(", mobile=").append(mobile);
        sb.append(", address=").append(address);
        sb.append(", lge=").append(lge);
        sb.append(", lae=").append(lae);
        sb.append(", city=").append(city);
        sb.append(", cityId=").append(cityId);
        sb.append(", school=").append(school);
        sb.append(", schoolId=").append(schoolId);
        sb.append(", days=").append(days);
        sb.append(", state=").append(state);
        sb.append(", ctime=").append(ctime);
        sb.append(", mtime=").append(mtime);
        sb.append(", extra=").append(extra);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}