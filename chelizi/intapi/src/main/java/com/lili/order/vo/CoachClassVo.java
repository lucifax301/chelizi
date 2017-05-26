package com.lili.order.vo;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

public class CoachClassVo implements Serializable {

/*排班主键*/
  private Integer ccid;
/*班次类型：1现约，3预约*/
  private Integer ctype;
/*教练id*/
  private Long coachId;
/*课程教练开始时间,注意该时间包括余量时间*/
  private Date cstart;
/*课程结束时间,注意该时间包括余量时间*/
  private Date cend;
/*预约课程时间开始时间 不包含余量*/
  private Date rstart;
/*课程实际结束时间,不包括余量时间*/
  private Date rend;
/*该课程的订单号,预约课程可不输入订单号*/
  private String orderId;
/*预约类型的最大可预约人数*/
  private Integer maxNum;
/*当前已预约人数*/
  private Integer bookNum;
/*预约汽车id*/
  private Integer carId;
/*冗余：预约汽车名称*/
  private String carName;
/*冗余:汽车图标*/
  private String carImg;
/*冗余：车牌号*/
  private String carNo;
/*预约科目id*/
  private String courseId;
/*冗余：预约科目id*/
  private String courseName;
/*预约训练场id*/
  private Integer placeId;
/*冗余：训练场名称*/
  private String placeName;
/*预约价格*/
  private Integer price;
/*驾照类型*/
  private Integer dltype;
/*排班时段主键*/
  private Integer tid;
/*删除状态：0代表未删除，1代表已删除*/
  private Integer isdel=0;
  /*是否可以操作： 0-可操作；1-不可操作*/
  private Integer operate =0;
  /*训练场经度*/
  private Double lge;
/*训练场纬度*/
  private Double lae;
/*排班取消原因id,没有可不填写*/
  private Integer rid;
/*排班取消原因*/
  private String reason;
  private List<PlantClassVo> plantClassList;

  public Integer getCcid() {
    return this.ccid;
  }
  public void setCcid(Integer ccid) {
    this.ccid=ccid;
  }
  public Integer getCtype() {
    return this.ctype;
  }
  public void setCtype(Integer ctype) {
    this.ctype=ctype;
  }
  public Long getCoachId() {
    return this.coachId;
  }
  public void setCoachId(Long coachId) {
    this.coachId=coachId;
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
  public String getOrderId() {
    return this.orderId;
  }
  public void setOrderId(String orderId) {
    this.orderId=orderId;
  }
  public Integer getMaxNum() {
    return this.maxNum;
  }
  public void setMaxNum(Integer maxNum) {
    this.maxNum=maxNum;
  }
  public Integer getBookNum() {
    return this.bookNum;
  }
  public void setBookNum(Integer bookNum) {
    this.bookNum=bookNum;
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
  public String getCourseId() {
    return this.courseId;
  }
  public void setCourseId(String courseId) {
    this.courseId=courseId;
  }
  public String getCourseName() {
    return this.courseName;
  }
  public void setCourseName(String courseName) {
    this.courseName=courseName;
  }
  public Integer getPlaceId() {
    return this.placeId;
  }
  public void setPlaceId(Integer placeId) {
    this.placeId=placeId;
  }
  public String getPlaceName() {
    return this.placeName;
  }
  public void setPlaceName(String placeName) {
    this.placeName=placeName;
  }
  public Integer getPrice() {
    return this.price;
  }
  public void setPrice(Integer price) {
    this.price=price;
  }
  public Integer getDltype() {
    return this.dltype;
  }
  public void setDltype(Integer dltype) {
    this.dltype=dltype;
  }
  public Integer getTid() {
    return this.tid;
  }
  public void setTid(Integer tid) {
    this.tid=tid;
  }
  public Integer getIsdel() {
    return this.isdel;
  }
  public void setIsdel(Integer isdel) {
    this.isdel=isdel;
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
  public Integer getRid() {
    return this.rid;
  }
  public void setRid(Integer rid) {
    this.rid=rid;
  }
  public String getReason() {
    return this.reason;
  }
  public void setReason(String reason) {
    this.reason=reason;
  }
  
  public List<PlantClassVo> getPlantClassList() {
	return plantClassList;
}
public void setPlantClassList(List<PlantClassVo> plantClassList) {
	this.plantClassList = plantClassList;
}
public String toString() {
		return "{ccid="+ccid+",ctype="+ctype+",coachId="+coachId+",cstart="+cstart+",cend="+cend+",rstart="+rstart+",rend="+rend+",orderId="+orderId+",maxNum="+maxNum+",bookNum="+bookNum+",carId="+carId+",carName="+carName+",carImg="+carImg+",carNo="+carNo+",courseId="+courseId+",courseName="+courseName+",placeId="+placeId+",placeName="+placeName+",price="+price+",dltype="+dltype+",tid="+tid+",isdel="+isdel+",lge="+lge+",lae="+lae+"}";
  }
public Integer getOperate() {
	return operate;
}
public void setOperate(Integer operate) {
	this.operate = operate;
}
}
