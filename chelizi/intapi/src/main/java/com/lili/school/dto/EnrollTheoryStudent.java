package com.lili.school.dto;

import java.io.Serializable;

public class EnrollTheoryStudent extends EnrollTheoryStudentKey implements Serializable {
    private Integer schoolId;

    private String name;

    private String phoneNum;

    private Byte sex;

    private Byte drType;

    private String idNumber;

    private String flowNo;

    private Byte isImport;

    private Byte state;

    private String remark;

    private Byte isdel;

    private String extra;

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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Byte getDrType() {
        return drType;
    }

    public void setDrType(Byte drType) {
        this.drType = drType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo == null ? null : flowNo.trim();
    }

    public Byte getIsImport() {
        return isImport;
    }

    public void setIsImport(Byte isImport) {
        this.isImport = isImport;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getIsdel() {
        return isdel;
    }

    public void setIsdel(Byte isdel) {
        this.isdel = isdel;
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
        sb.append(", schoolId=").append(schoolId);
        sb.append(", name=").append(name);
        sb.append(", phoneNum=").append(phoneNum);
        sb.append(", sex=").append(sex);
        sb.append(", drType=").append(drType);
        sb.append(", idNumber=").append(idNumber);
        sb.append(", flowNo=").append(flowNo);
        sb.append(", isImport=").append(isImport);
        sb.append(", state=").append(state);
        sb.append(", remark=").append(remark);
        sb.append(", isdel=").append(isdel);
        sb.append(", extra=").append(extra);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}