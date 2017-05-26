package com.lili.order.dao.po;

import java.util.Date;

public class OrderComplainPo{

  private Integer cid;
  private String orderId;
  private Long uid;
  private Integer utype;
  private String complain;
  private Date complainTime;
  private String mobile;
  private String pic;
  private Integer staffId;
  private String result;
  private Integer cstate;


  public Integer getCid() {
    return this.cid;
  }
  public void setCid(Integer cid) {
    this.cid=cid;
  }
  public String getOrderId() {
    return this.orderId;
  }
  public void setOrderId(String orderId) {
    this.orderId=orderId;
  }
  public Long getUid() {
    return this.uid;
  }
  public void setUid(Long uid) {
    this.uid=uid;
  }
  public Integer getUtype() {
    return this.utype;
  }
  public void setUtype(Integer utype) {
    this.utype=utype;
  }
  public String getComplain() {
    return this.complain;
  }
  public void setComplain(String complain) {
    this.complain=complain;
  }
  public Date getComplainTime() {
    return this.complainTime;
  }
  public void setComplainTime(Date complainTime) {
    this.complainTime=complainTime;
  }
  public String getMobile() {
    return this.mobile;
  }
  public void setMobile(String mobile) {
    this.mobile=mobile;
  }
  public String getPic() {
    return this.pic;
  }
  public void setPic(String pic) {
    this.pic=pic;
  }
  public Integer getStaffId() {
    return this.staffId;
  }
  public void setStaffId(Integer staffId) {
    this.staffId=staffId;
  }
  public String getResult() {
    return this.result;
  }
  public void setResult(String result) {
    this.result=result;
  }
  public Integer getCstate() {
    return this.cstate;
  }
  public void setCstate(Integer cstate) {
    this.cstate=cstate;
  }
}
