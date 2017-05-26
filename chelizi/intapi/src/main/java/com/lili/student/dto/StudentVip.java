package com.lili.student.dto;

import java.io.Serializable;
import java.util.Date;

public class StudentVip implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer vipId;
    private String vipName;
    private String vipNickName;
    private String vipCustomerManager;
    private String vipContactPhone;
    private String citys;
    private String operator;
    private Date ctime;
    private Date mtime;
    private String extra;
    private String chargeDiscountTmpId;

    public String getChargeDiscountTmpId() {
        return chargeDiscountTmpId;
    }

    public void setChargeDiscountTmpId(String chargeDiscountTmpId) {
        this.chargeDiscountTmpId = chargeDiscountTmpId;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName == null ? null : vipName.trim();
    }

    public String getVipNickName() {
        return vipNickName;
    }

    public void setVipNickName(String vipNickName) {
        this.vipNickName = vipNickName == null ? null : vipNickName.trim();
    }

    public String getVipCustomerManager() {
        return vipCustomerManager;
    }

    public void setVipCustomerManager(String vipCustomerManager) {
        this.vipCustomerManager = vipCustomerManager == null ? null : vipCustomerManager.trim();
    }

    public String getVipContactPhone() {
        return vipContactPhone;
    }

    public void setVipContactPhone(String vipContactPhone) {
        this.vipContactPhone = vipContactPhone == null ? null : vipContactPhone.trim();
    }

    public String getCitys() {
        return citys;
    }

    public void setCitys(String citys) {
        this.citys = citys == null ? null : citys.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
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
        sb.append(", vipId=").append(vipId);
        sb.append(", vipName=").append(vipName);
        sb.append(", vipNickName=").append(vipNickName);
        sb.append(", vipCustomerManager=").append(vipCustomerManager);
        sb.append(", vipContactPhone=").append(vipContactPhone);
        sb.append(", citys=").append(citys);
        sb.append(", operator=").append(operator);
        sb.append(", ctime=").append(ctime);
        sb.append(", mtime=").append(mtime);
        sb.append(", extra=").append(extra);
        sb.append(", chargeDiscountTmpId=").append(chargeDiscountTmpId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}