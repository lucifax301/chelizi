package com.lili.pay.dto;

public class BrokerageEnroll {
    private Integer id;

    private Integer regionId;

    private Integer schoolId;

    private Byte brokerageType;

    private Integer brokerage;

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

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
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
}