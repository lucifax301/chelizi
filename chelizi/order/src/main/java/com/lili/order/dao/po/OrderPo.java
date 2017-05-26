package com.lili.order.dao.po;

import java.util.Date;

public class OrderPo{

  private String orderId;
  private Long coachId;
  private String courseId;
  private Integer price;
  private String learnAddr;
  private Long studentId;
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
  private Integer payState;
  private Integer checkoutState;
  private Integer needTrans;
  private Integer transState;
  private Integer payId;
  private Date gtime;
  private Date atime;
  private Integer otype;
  private String coorder;
  private Integer ccid;
  private Integer unitPrice;
  private Integer transPrice;
  private Integer placeId;
  private String transName;
  private Integer carId;
  private String carName;
  private String carImg;
  private String carNo;
  private Integer insId;
  private Integer insPrice;
  private String insName;
  private String coachName;
  private String coachImg;
  private String coachMobile;
  private Integer coachStar;
  private String stuName;
  private String stuImg;
  private String stuMobile;
  private String courseName;
  private Integer allowance;
  private Integer priceTotal;
  private Integer payTotal;
  private Date payTime;
  private Double placeLge;
  private Double placeLae;
  private Integer cityId;
  private Long coupon;
  private Integer couponTotal;
  private String couponName;
  private String courseType;
  private Integer schoolId;

  private Integer direct;
  
  private Integer preOrder;
  
  private Integer preOrderState;
  
  private Date operateTime;
  
  

  public Date getOperateTime() {
	return operateTime;
}
public void setOperateTime(Date operateTime) {
	this.operateTime = operateTime;
}
public Integer getDirect() {
	return direct;
}
public void setDirect(Integer direct) {
	this.direct = direct;
}
public Integer getPreOrder() {
	return preOrder;
}
public void setPreOrder(Integer preOrder) {
	this.preOrder = preOrder;
}
public Integer getPreOrderState() {
	return preOrderState;
}
public void setPreOrderState(Integer preOrderState) {
	this.preOrderState = preOrderState;
}
public String getOrderId() {
    return this.orderId;
  }
  public void setOrderId(String orderId) {
    this.orderId=orderId;
  }
  public Long getCoachId() {
    return this.coachId;
  }
  public void setCoachId(Long coachId) {
    this.coachId=coachId;
  }
  public String getCourseId() {
    return this.courseId;
  }
  public void setCourseId(String courseId) {
    this.courseId=courseId;
  }
  public Integer getPrice() {
    return this.price;
  }
  public void setPrice(Integer price) {
    this.price=price;
  }
  public String getLearnAddr() {
    return this.learnAddr;
  }
  public void setLearnAddr(String learnAddr) {
    this.learnAddr=learnAddr;
  }
  public Long getStudentId() {
    return this.studentId;
  }
  public void setStudentId(Long studentId) {
    this.studentId=studentId;
  }
  public Integer getDltype() {
    return this.dltype;
  }
  public void setDltype(Integer dltype) {
    this.dltype=dltype;
  }
  public Double getLge() {
    return this.lge;
  }
  public void setLge(Double lge) {
    this.lge=lge;
  }
  public Double getLae() {
    return this.lae;
  }
  public void setLae(Double lae) {
    this.lae=lae;
  }
  public String getStuAddr() {
    return this.stuAddr;
  }
  public void setStuAddr(String stuAddr) {
    this.stuAddr=stuAddr;
  }
  public Date getPstart() {
    return this.pstart;
  }
  public void setPstart(Date pstart) {
    this.pstart=pstart;
  }
  public Date getPend() {
    return this.pend;
  }
  public void setPend(Date pend) {
    this.pend=pend;
  }
  public Integer getClzNum() {
    return this.clzNum;
  }
  public void setClzNum(Integer clzNum) {
    this.clzNum=clzNum;
  }
  public Integer getOrderState() {
    return this.orderState;
  }
  public void setOrderState(Integer orderState) {
    this.orderState=orderState;
  }
  public Date getRstart() {
    return this.rstart;
  }
  public void setRstart(Date rstart) {
    this.rstart=rstart;
  }
  public Date getRend() {
    return this.rend;
  }
  public void setRend(Date rend) {
    this.rend=rend;
  }
  public Date getCstart() {
    return this.cstart;
  }
  public void setCstart(Date cstart) {
    this.cstart=cstart;
  }
  public Date getCend() {
    return this.cend;
  }
  public void setCend(Date cend) {
    this.cend=cend;
  }
  public Integer getPayState() {
    return this.payState;
  }
  public void setPayState(Integer payState) {
    this.payState=payState;
  }

  public Integer getCheckoutState() {
    return checkoutState;
  }

  public void setCheckoutState(Integer checkoutState) {
    this.checkoutState = checkoutState;
  }

  public Integer getNeedTrans() {
    return this.needTrans;
  }
  public void setNeedTrans(Integer needTrans) {
    this.needTrans=needTrans;
  }
  public Integer getTransState() {
    return this.transState;
  }
  public void setTransState(Integer transState) {
    this.transState=transState;
  }
  public Integer getPayId() {
    return this.payId;
  }
  public void setPayId(Integer payId) {
    this.payId=payId;
  }
  public Date getGtime() {
    return this.gtime;
  }
  public void setGtime(Date gtime) {
    this.gtime=gtime;
  }
  public Date getAtime() {
    return this.atime;
  }
  public void setAtime(Date atime) {
    this.atime=atime;
  }
  public Integer getOtype() {
    return this.otype;
  }
  public void setOtype(Integer otype) {
    this.otype=otype;
  }
  public String getCoorder() {
    return this.coorder;
  }
  public void setCoorder(String coorder) {
    this.coorder=coorder;
  }
  public Integer getCcid() {
    return this.ccid;
  }
  public void setCcid(Integer ccid) {
    this.ccid=ccid;
  }
  public Integer getUnitPrice() {
    return this.unitPrice;
  }
  public void setUnitPrice(Integer unitPrice) {
    this.unitPrice=unitPrice;
  }
  public Integer getTransPrice() {
    return this.transPrice;
  }
  public void setTransPrice(Integer transPrice) {
    this.transPrice=transPrice;
  }
  public Integer getPlaceId() {
    return this.placeId;
  }
  public void setPlaceId(Integer placeId) {
    this.placeId=placeId;
  }
  public String getTransName() {
    return this.transName;
  }
  public void setTransName(String transName) {
    this.transName=transName;
  }
  public Integer getCarId() {
    return this.carId;
  }
  public void setCarId(Integer carId) {
    this.carId=carId;
  }
  public String getCarName() {
    return this.carName;
  }
  public void setCarName(String carName) {
    this.carName=carName;
  }
  public String getCarImg() {
    return this.carImg;
  }
  public void setCarImg(String carImg) {
    this.carImg=carImg;
  }
  public String getCarNo() {
    return this.carNo;
  }
  public void setCarNo(String carNo) {
    this.carNo=carNo;
  }
  public Integer getInsId() {
    return this.insId;
  }
  public void setInsId(Integer insId) {
    this.insId=insId;
  }
  public Integer getInsPrice() {
    return this.insPrice;
  }
  public void setInsPrice(Integer insPrice) {
    this.insPrice=insPrice;
  }
  public String getInsName() {
    return this.insName;
  }
  public void setInsName(String insName) {
    this.insName=insName;
  }
  public String getCoachName() {
    return this.coachName;
  }
  public void setCoachName(String coachName) {
    this.coachName=coachName;
  }
  public String getCoachImg() {
    return this.coachImg;
  }
  public void setCoachImg(String coachImg) {
    this.coachImg=coachImg;
  }
  public String getCoachMobile() {
    return this.coachMobile;
  }
  public void setCoachMobile(String coachMobile) {
    this.coachMobile=coachMobile;
  }
  public Integer getCoachStar() {
    return this.coachStar;
  }
  public void setCoachStar(Integer coachStar) {
    this.coachStar=coachStar;
  }
  public String getStuName() {
    return this.stuName;
  }
  public void setStuName(String stuName) {
    this.stuName=stuName;
  }
  public String getStuImg() {
    return this.stuImg;
  }
  public void setStuImg(String stuImg) {
    this.stuImg=stuImg;
  }
  public String getStuMobile() {
    return this.stuMobile;
  }
  public void setStuMobile(String stuMobile) {
    this.stuMobile=stuMobile;
  }
  public String getCourseName() {
    return this.courseName;
  }
  public void setCourseName(String courseName) {
    this.courseName=courseName;
  }
  public Integer getAllowance() {
    return this.allowance;
  }
  public void setAllowance(Integer allowance) {
    this.allowance=allowance;
  }
  public Integer getPriceTotal() {
    return this.priceTotal;
  }
  public void setPriceTotal(Integer priceTotal) {
    this.priceTotal=priceTotal;
  }
  public Integer getPayTotal() {
    return this.payTotal;
  }
  public void setPayTotal(Integer payTotal) {
    this.payTotal=payTotal;
  }
  public Date getPayTime() {
    return this.payTime;
  }
  public void setPayTime(Date payTime) {
    this.payTime=payTime;
  }
  public Double getPlaceLge() {
    return this.placeLge;
  }
  public void setPlaceLge(Double placeLge) {
    this.placeLge=placeLge;
  }
  public Double getPlaceLae() {
    return this.placeLae;
  }
  public void setPlaceLae(Double placeLae) {
    this.placeLae=placeLae;
  }
  public Integer getCityId() {
    return this.cityId;
  }
  public void setCityId(Integer cityId) {
    this.cityId=cityId;
  }
  public Long getCoupon() {
    return this.coupon;
  }
  public void setCoupon(Long coupon) {
    this.coupon=coupon;
  }
  public Integer getCouponTotal() {
    return this.couponTotal;
  }
  public void setCouponTotal(Integer couponTotal) {
    this.couponTotal=couponTotal;
  }
  public String getCouponName() {
    return this.couponName;
  }
  public void setCouponName(String couponName) {
    this.couponName=couponName;
  }
  public String getCourseType() {
    return this.courseType;
  }
  public void setCourseType(String courseType) {
    this.courseType=courseType;
  }
  public Integer getSchoolId() {
    return this.schoolId;
  }
  public void setSchoolId(Integer schoolId) {
    this.schoolId=schoolId;
  }
}
