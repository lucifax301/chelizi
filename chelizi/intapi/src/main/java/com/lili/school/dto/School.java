package com.lili.school.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class School implements Serializable {
    private Integer schoolId;

    private String name;

    private Integer coachCount;

    private Integer carCount;

    private Integer cityId;

    private String address;

    private String phoneNum;

    private String jstId;

    private String tell;

    private Double score;

    private String logo;

    private String icon;

    private Integer trfieldCount;

    private BigDecimal lge;

    private BigDecimal lae;

    private Integer orderNum;

    private String extra;

    private Integer storeCount;

    private Integer packageCount;

    private Integer showState;

    private static final long serialVersionUID = 1L;

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getCoachCount() {
        return coachCount;
    }

    public void setCoachCount(Integer coachCount) {
        this.coachCount = coachCount;
    }

    public Integer getCarCount() {
        return carCount;
    }

    public void setCarCount(Integer carCount) {
        this.carCount = carCount;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    public String getJstId() {
        return jstId;
    }

    public void setJstId(String jstId) {
        this.jstId = jstId == null ? null : jstId.trim();
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell == null ? null : tell.trim();
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Integer getTrfieldCount() {
        return trfieldCount;
    }

    public void setTrfieldCount(Integer trfieldCount) {
        this.trfieldCount = trfieldCount;
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

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

    public Integer getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(Integer storeCount) {
        this.storeCount = storeCount;
    }

    public Integer getPackageCount() {
        return packageCount;
    }

    public void setPackageCount(Integer packageCount) {
        this.packageCount = packageCount;
    }

    public Integer getShowState() {
        return showState;
    }

    public void setShowState(Integer showState) {
        this.showState = showState;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", schoolId=").append(schoolId);
        sb.append(", name=").append(name);
        sb.append(", coachCount=").append(coachCount);
        sb.append(", carCount=").append(carCount);
        sb.append(", cityId=").append(cityId);
        sb.append(", address=").append(address);
        sb.append(", phoneNum=").append(phoneNum);
        sb.append(", jstId=").append(jstId);
        sb.append(", tell=").append(tell);
        sb.append(", score=").append(score);
        sb.append(", logo=").append(logo);
        sb.append(", icon=").append(icon);
        sb.append(", trfieldCount=").append(trfieldCount);
        sb.append(", lge=").append(lge);
        sb.append(", lae=").append(lae);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", extra=").append(extra);
        sb.append(", storeCount=").append(storeCount);
        sb.append(", packageCount=").append(packageCount);
        sb.append(", showState=").append(showState);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}