package com.lili.school.dto;

import java.io.Serializable;
import java.util.Date;

public class EnrollExamReg extends EnrollExamRegKey implements Serializable {
    private Integer schoolId;

    private Long studentId;

    private String idNumber;

    private String name;

    private String schoolName;

    private Byte drType;

    private Integer registState;

    private Date registTime;

    private Date registPeriod;

    private Integer waitDays;

    private Integer bookResult;

    private String examPlace;

    private Date examDate;

    private String examTime;

    private String examOrder;

    private Date queueTime;

    private Byte isdel;

    private Integer applystate;

    private Integer importState;

    private Integer subjectId;

    private Date ctime;

    private Date mtime;

    private String extra;

    private static final long serialVersionUID = 1L;

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    public Byte getDrType() {
        return drType;
    }

    public void setDrType(Byte drType) {
        this.drType = drType;
    }

    public Integer getRegistState() {
        return registState;
    }

    public void setRegistState(Integer registState) {
        this.registState = registState;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    public Date getRegistPeriod() {
        return registPeriod;
    }

    public void setRegistPeriod(Date registPeriod) {
        this.registPeriod = registPeriod;
    }

    public Integer getWaitDays() {
        return waitDays;
    }

    public void setWaitDays(Integer waitDays) {
        this.waitDays = waitDays;
    }

    public Integer getBookResult() {
        return bookResult;
    }

    public void setBookResult(Integer bookResult) {
        this.bookResult = bookResult;
    }

    public String getExamPlace() {
        return examPlace;
    }

    public void setExamPlace(String examPlace) {
        this.examPlace = examPlace == null ? null : examPlace.trim();
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime == null ? null : examTime.trim();
    }

    public String getExamOrder() {
        return examOrder;
    }

    public void setExamOrder(String examOrder) {
        this.examOrder = examOrder == null ? null : examOrder.trim();
    }

    public Date getQueueTime() {
        return queueTime;
    }

    public void setQueueTime(Date queueTime) {
        this.queueTime = queueTime;
    }

    public Byte getIsdel() {
        return isdel;
    }

    public void setIsdel(Byte isdel) {
        this.isdel = isdel;
    }

    public Integer getApplystate() {
        return applystate;
    }

    public void setApplystate(Integer applystate) {
        this.applystate = applystate;
    }

    public Integer getImportState() {
        return importState;
    }

    public void setImportState(Integer importState) {
        this.importState = importState;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
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
        sb.append(", schoolId=").append(schoolId);
        sb.append(", studentId=").append(studentId);
        sb.append(", idNumber=").append(idNumber);
        sb.append(", name=").append(name);
        sb.append(", schoolName=").append(schoolName);
        sb.append(", drType=").append(drType);
        sb.append(", registState=").append(registState);
        sb.append(", registTime=").append(registTime);
        sb.append(", registPeriod=").append(registPeriod);
        sb.append(", waitDays=").append(waitDays);
        sb.append(", bookResult=").append(bookResult);
        sb.append(", examPlace=").append(examPlace);
        sb.append(", examDate=").append(examDate);
        sb.append(", examTime=").append(examTime);
        sb.append(", examOrder=").append(examOrder);
        sb.append(", queueTime=").append(queueTime);
        sb.append(", isdel=").append(isdel);
        sb.append(", applystate=").append(applystate);
        sb.append(", importState=").append(importState);
        sb.append(", subjectId=").append(subjectId);
        sb.append(", ctime=").append(ctime);
        sb.append(", mtime=").append(mtime);
        sb.append(", extra=").append(extra);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}