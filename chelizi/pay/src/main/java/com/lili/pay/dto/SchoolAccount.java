package com.lili.pay.dto;

public class SchoolAccount {
    private Integer schoolId;

    private Integer money;

    private String passwd;

    private String mobile;

    private Integer examPlaceMax;

    private Integer examPlaceNow;

    private String extra;

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getExamPlaceMax() {
        return examPlaceMax;
    }

    public void setExamPlaceMax(Integer examPlaceMax) {
        this.examPlaceMax = examPlaceMax;
    }

    public Integer getExamPlaceNow() {
        return examPlaceNow;
    }

    public void setExamPlaceNow(Integer examPlaceNow) {
        this.examPlaceNow = examPlaceNow;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }
}