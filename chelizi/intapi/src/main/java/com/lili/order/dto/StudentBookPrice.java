package com.lili.order.dto;

import java.io.Serializable;

public class StudentBookPrice implements Serializable {
    private Integer id;

    private Integer regionId;

    private Integer courseTmpId;

    private Byte dltype;

    private String dateRule;

    private Integer price;

    private String description;

    private static final long serialVersionUID = 1L;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", regionId=").append(regionId);
        sb.append(", courseTmpId=").append(courseTmpId);
        sb.append(", dltype=").append(dltype);
        sb.append(", dateRule=").append(dateRule);
        sb.append(", price=").append(price);
        sb.append(", description=").append(description);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}