package com.lili.order.dto;

import java.io.Serializable;
import java.util.Date;

public class InsuranceOrder implements Serializable {
    private String insuranceId;

    private Long studentId;

    private String name;

    private String phoneNum;

    private Integer cityId;

    private String cityName;

    private Integer price;

    private Integer year;

    private String insuranceNumber;

    private Date payTime;

    private Date effectTime;

    private Date invalidTime;

    private Date operationTime;

    private Date compensateTime;

    private Integer compensate;

    private String compensateRemark;

    private Integer payState;

    private String payWay;

    private Date refundTime;

    private Integer refundPrice;

    private String refundRemark;

    private Date visitTime;

    private Integer visitState;

    private String visitRemark;

    private Integer refundState;

    private static final long serialVersionUID = 1L;

    public String getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        this.insuranceId = insuranceId == null ? null : insuranceId.trim();
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber == null ? null : insuranceNumber.trim();
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Date getCompensateTime() {
        return compensateTime;
    }

    public void setCompensateTime(Date compensateTime) {
        this.compensateTime = compensateTime;
    }

    public Integer getCompensate() {
        return compensate;
    }

    public void setCompensate(Integer compensate) {
        this.compensate = compensate;
    }

    public String getCompensateRemark() {
        return compensateRemark;
    }

    public void setCompensateRemark(String compensateRemark) {
        this.compensateRemark = compensateRemark == null ? null : compensateRemark.trim();
    }

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay == null ? null : payWay.trim();
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public Integer getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(Integer refundPrice) {
        this.refundPrice = refundPrice;
    }

    public String getRefundRemark() {
        return refundRemark;
    }

    public void setRefundRemark(String refundRemark) {
        this.refundRemark = refundRemark == null ? null : refundRemark.trim();
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public Integer getVisitState() {
        return visitState;
    }

    public void setVisitState(Integer visitState) {
        this.visitState = visitState;
    }

    public String getVisitRemark() {
        return visitRemark;
    }

    public void setVisitRemark(String visitRemark) {
        this.visitRemark = visitRemark == null ? null : visitRemark.trim();
    }

    public Integer getRefundState() {
        return refundState;
    }

    public void setRefundState(Integer refundState) {
        this.refundState = refundState;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", insuranceId=").append(insuranceId);
        sb.append(", studentId=").append(studentId);
        sb.append(", name=").append(name);
        sb.append(", phoneNum=").append(phoneNum);
        sb.append(", cityId=").append(cityId);
        sb.append(", cityName=").append(cityName);
        sb.append(", price=").append(price);
        sb.append(", year=").append(year);
        sb.append(", insuranceNumber=").append(insuranceNumber);
        sb.append(", payTime=").append(payTime);
        sb.append(", effectTime=").append(effectTime);
        sb.append(", invalidTime=").append(invalidTime);
        sb.append(", operationTime=").append(operationTime);
        sb.append(", compensateTime=").append(compensateTime);
        sb.append(", compensate=").append(compensate);
        sb.append(", compensateRemark=").append(compensateRemark);
        sb.append(", payState=").append(payState);
        sb.append(", payWay=").append(payWay);
        sb.append(", refundTime=").append(refundTime);
        sb.append(", refundPrice=").append(refundPrice);
        sb.append(", refundRemark=").append(refundRemark);
        sb.append(", visitTime=").append(visitTime);
        sb.append(", visitState=").append(visitState);
        sb.append(", visitRemark=").append(visitRemark);
        sb.append(", refundState=").append(refundState);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}