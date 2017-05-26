package com.lili.order.dao.po;

import java.util.Date;

public class PlantClassPo{

  private String orderId;
  private Integer ccid;
  private Long coachId;
  private Long studentId;
  private Date gtime;
  private String stuName;
  private String stuImg;
  private String stuMobile;
  private Integer isdel;
  private Integer price;


  public String getOrderId() {
    return this.orderId;
  }
  public void setOrderId(String orderId) {
    this.orderId=orderId;
  }
  public Integer getCcid() {
    return this.ccid;
  }
  public void setCcid(Integer ccid) {
    this.ccid=ccid;
  }
  public Long getCoachId() {
    return this.coachId;
  }
  public void setCoachId(Long coachId) {
    this.coachId=coachId;
  }
  public Long getStudentId() {
    return this.studentId;
  }
  public void setStudentId(Long studentId) {
    this.studentId=studentId;
  }
  public Date getGtime() {
    return this.gtime;
  }
  public void setGtime(Date gtime) {
    this.gtime=gtime;
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
  public Integer getIsdel() {
    return this.isdel;
  }
  public void setIsdel(Integer isdel) {
    this.isdel=isdel;
  }
  public Integer getPrice() {
    return this.price;
  }
  public void setPrice(Integer price) {
    this.price=price;
  }
}
