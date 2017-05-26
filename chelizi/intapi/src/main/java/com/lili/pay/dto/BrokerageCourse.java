package com.lili.pay.dto;

public class BrokerageCourse {
    private Integer id;

    private Integer regionId;

    private Integer courseTmpId;

    private Byte dltype;

    private String dateRule;

    private Byte brokerageType;

    private Integer brokerage;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getCourseTmpId() {
        return courseTmpId;
    }

    public void setCourseTmpId(Integer courseTmpId) {
        this.courseTmpId = courseTmpId;
    }

    public Byte getDltype() {
        return dltype;
    }

    public void setDltype(Byte dltype) {
        this.dltype = dltype;
    }

    public String getDateRule() {
        return dateRule;
    }

    public void setDateRule(String dateRule) {
        this.dateRule = dateRule == null ? null : dateRule.trim();
    }

    public Byte getBrokerageType() {
        return brokerageType;
    }

    public void setBrokerageType(Byte brokerageType) {
        this.brokerageType = brokerageType;
    }

    public Integer getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(Integer brokerage) {
        this.brokerage = brokerage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}