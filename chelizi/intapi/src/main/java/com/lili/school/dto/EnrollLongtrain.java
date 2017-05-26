package com.lili.school.dto;

import java.io.Serializable;
import java.util.Date;

public class EnrollLongtrain implements Serializable {
    private Integer ltId;

    private Integer schoolId;

    private Date classDate;

    private String classTime;

    private String classPlace;

    private Integer coachId;

    private Byte coachSex;

    private String coachIdCard;

    private String contactName;

    private String contactMobile;

    private String carNo;

    private String carType;

    private Byte drType;

    private String carrys;

    private Integer total;

    private Integer failed;

    private Integer state;

    private Date ctime;

    private Date mtime;

    private String extra;

    private static final long serialVersionUID = 1L;

    public Integer getLtId() {
        return ltId;
    }

    public void setLtId(Integer ltId) {
        this.ltId = ltId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Date getClassDate() {
        return classDate;
    }

    public void setClassDate(Date classDate) {
        this.classDate = classDate;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime == null ? null : classTime.trim();
    }

    public String getClassPlace() {
        return classPlace;
    }

    public void setClassPlace(String classPlace) {
        this.classPlace = classPlace == null ? null : classPlace.trim();
    }

    public Integer getCoachId() {
        return coachId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }

    public Byte getCoachSex() {
        return coachSex;
    }

    public void setCoachSex(Byte coachSex) {
        this.coachSex = coachSex;
    }

    public String getCoachIdCard() {
        return coachIdCard;
    }

    public void setCoachIdCard(String coachIdCard) {
        this.coachIdCard = coachIdCard == null ? null : coachIdCard.trim();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile == null ? null : contactMobile.trim();
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo == null ? null : carNo.trim();
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType == null ? null : carType.trim();
    }

    public Byte getDrType() {
        return drType;
    }

    public void setDrType(Byte drType) {
        this.drType = drType;
    }

    public String getCarrys() {
        return carrys;
    }

    public void setCarrys(String carrys) {
        this.carrys = carrys == null ? null : carrys.trim();
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getFailed() {
        return failed;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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
        sb.append(", ltId=").append(ltId);
        sb.append(", schoolId=").append(schoolId);
        sb.append(", classDate=").append(classDate);
        sb.append(", classTime=").append(classTime);
        sb.append(", classPlace=").append(classPlace);
        sb.append(", coachId=").append(coachId);
        sb.append(", coachSex=").append(coachSex);
        sb.append(", coachIdCard=").append(coachIdCard);
        sb.append(", contactName=").append(contactName);
        sb.append(", contactMobile=").append(contactMobile);
        sb.append(", carNo=").append(carNo);
        sb.append(", carType=").append(carType);
        sb.append(", drType=").append(drType);
        sb.append(", carrys=").append(carrys);
        sb.append(", total=").append(total);
        sb.append(", failed=").append(failed);
        sb.append(", state=").append(state);
        sb.append(", ctime=").append(ctime);
        sb.append(", mtime=").append(mtime);
        sb.append(", extra=").append(extra);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}