package com.lili.school.vo;


import java.io.Serializable;
import java.util.Date;

public class WechatOrder  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8770801869879452903L;

	private String orderId;

    private Long coachId;

    private String coachName;

    private String coachImg;

    private String coachMobile;

    private Integer cityId;

    private Integer schoolId;

    private String courseId;

    private String courseName;

    private String learnAddr;

    private Long studentId;

    private String stuName;

    private String stuImg;

    private String stuMobile;

    private Integer dltype;

    private Double lge;

    private Double lae;

    private String stuAddr;

    private Date pstart;

    private Date pend;

    private Integer clzNum;

    private Integer orderState;

    private Date rstart;

    private Date rend;

    private Date cstart;

    private Date cend;

    private Integer needTrans;

    private Date gtime;

    private Date atime;

    private Integer otype;

    private String coorder;

    private Integer ccid;

    private Integer carId;

    private String carName;

    private String carImg;

    private String carNo;

    private Integer placeId;

    private String placeName;

    private Double placeLge;

    private Double placeLae;

    private Date operateTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Long getCoachId() {
        return coachId;
    }

    public void setCoachId(Long coachId) {
        this.coachId = coachId;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName == null ? null : coachName.trim();
    }

    public String getCoachImg() {
        return coachImg;
    }

    public void setCoachImg(String coachImg) {
        this.coachImg = coachImg == null ? null : coachImg.trim();
    }

    public String getCoachMobile() {
        return coachMobile;
    }

    public void setCoachMobile(String coachMobile) {
        this.coachMobile = coachMobile == null ? null : coachMobile.trim();
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId == null ? null : courseId.trim();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
    }

    public String getLearnAddr() {
        return learnAddr;
    }

    public void setLearnAddr(String learnAddr) {
        this.learnAddr = learnAddr == null ? null : learnAddr.trim();
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName == null ? null : stuName.trim();
    }

    public String getStuImg() {
        return stuImg;
    }

    public void setStuImg(String stuImg) {
        this.stuImg = stuImg == null ? null : stuImg.trim();
    }

    public String getStuMobile() {
        return stuMobile;
    }

    public void setStuMobile(String stuMobile) {
        this.stuMobile = stuMobile == null ? null : stuMobile.trim();
    }

    public Integer getDltype() {
        return dltype;
    }

    public void setDltype(Integer dltype) {
        this.dltype = dltype;
    }

    public Double getLge() {
        return lge;
    }

    public void setLge(Double lge) {
        this.lge = lge;
    }

    public Double getLae() {
        return lae;
    }

    public void setLae(Double lae) {
        this.lae = lae;
    }

    public String getStuAddr() {
        return stuAddr;
    }

    public void setStuAddr(String stuAddr) {
        this.stuAddr = stuAddr == null ? null : stuAddr.trim();
    }

    public Date getPstart() {
        return pstart;
    }

    public void setPstart(Date pstart) {
        this.pstart = pstart;
    }

    public Date getPend() {
        return pend;
    }

    public void setPend(Date pend) {
        this.pend = pend;
    }

    public Integer getClzNum() {
        return clzNum;
    }

    public void setClzNum(Integer clzNum) {
        this.clzNum = clzNum;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Date getRstart() {
        return rstart;
    }

    public void setRstart(Date rstart) {
        this.rstart = rstart;
    }

    public Date getRend() {
        return rend;
    }

    public void setRend(Date rend) {
        this.rend = rend;
    }

    public Date getCstart() {
        return cstart;
    }

    public void setCstart(Date cstart) {
        this.cstart = cstart;
    }

    public Date getCend() {
        return cend;
    }

    public void setCend(Date cend) {
        this.cend = cend;
    }

    public Integer getNeedTrans() {
        return needTrans;
    }

    public void setNeedTrans(Integer needTrans) {
        this.needTrans = needTrans;
    }

    public Date getGtime() {
        return gtime;
    }

    public void setGtime(Date gtime) {
        this.gtime = gtime;
    }

    public Date getAtime() {
        return atime;
    }

    public void setAtime(Date atime) {
        this.atime = atime;
    }

    public Integer getOtype() {
        return otype;
    }

    public void setOtype(Integer otype) {
        this.otype = otype;
    }

    public String getCoorder() {
        return coorder;
    }

    public void setCoorder(String coorder) {
        this.coorder = coorder == null ? null : coorder.trim();
    }

    public Integer getCcid() {
        return ccid;
    }

    public void setCcid(Integer ccid) {
        this.ccid = ccid;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName == null ? null : carName.trim();
    }

    public String getCarImg() {
        return carImg;
    }

    public void setCarImg(String carImg) {
        this.carImg = carImg == null ? null : carImg.trim();
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo == null ? null : carNo.trim();
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Double getPlaceLge() {
        return placeLge;
    }

    public void setPlaceLge(Double placeLge) {
        this.placeLge = placeLge;
    }

    public Double getPlaceLae() {
        return placeLae;
    }

    public void setPlaceLae(Double placeLae) {
        this.placeLae = placeLae;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
}