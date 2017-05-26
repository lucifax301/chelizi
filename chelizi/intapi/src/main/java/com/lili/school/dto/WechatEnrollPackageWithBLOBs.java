package com.lili.school.dto;

import java.io.Serializable;

public class WechatEnrollPackageWithBLOBs extends WechatEnrollPackage implements Serializable {
    private String protocol;

    private String priceDetail;

    private String test_condition;

    private String describtion;

    private String remark;

    private static final long serialVersionUID = 1L;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol == null ? null : protocol.trim();
    }

    public String getPriceDetail() {
        return priceDetail;
    }

    public void setPriceDetail(String priceDetail) {
        this.priceDetail = priceDetail == null ? null : priceDetail.trim();
    }

    public String getTest_condition() {
        return test_condition;
    }

    public void setTest_condition(String test_condition) {
        this.test_condition = test_condition == null ? null : test_condition.trim();
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion == null ? null : describtion.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", protocol=").append(protocol);
        sb.append(", priceDetail=").append(priceDetail);
        sb.append(", test_condition=").append(test_condition);
        sb.append(", describtion=").append(describtion);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}