package com.lili.school.dto;

import java.io.Serializable;

public class SchoolWithBLOBs extends School implements Serializable {
    private String image;

    private String schoolInfo;

    private String regInfo;

    private static final long serialVersionUID = 1L;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getSchoolInfo() {
        return schoolInfo;
    }

    public void setSchoolInfo(String schoolInfo) {
        this.schoolInfo = schoolInfo == null ? null : schoolInfo.trim();
    }

    public String getRegInfo() {
        return regInfo;
    }

    public void setRegInfo(String regInfo) {
        this.regInfo = regInfo == null ? null : regInfo.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", image=").append(image);
        sb.append(", schoolInfo=").append(schoolInfo);
        sb.append(", regInfo=").append(regInfo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}