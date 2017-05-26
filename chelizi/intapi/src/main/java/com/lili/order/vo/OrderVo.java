package com.lili.order.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.lili.order.dto.StudentClass;

public class OrderVo implements Serializable {

    private static final long serialVersionUID = -5257370758824612537L;
    /*订单id(全网唯一，用UID)*/
    private String orderId;
    /*教练id*/
    private Long coachId;
    /*课程id*/
    private String courseId;
    /*课时费：支付时总价之一*/
    private Integer price;
    /*上课地点*/
    private String learnAddr;
    /*学员id*/
    private Long studentId;
    /*驾照类型：1代表C1,2代表C2*/
    private Integer dltype;
    /*学员接送点经度*/
    private Double lge;
    /*学员接送点纬度*/
    private Double lae;
    /*学员接送地点*/
    private String stuAddr;
    /*计划上课时间*/
    private Date pstart;
    /*计划结束时间*/
    private Date pend;
    /*该订单约课节数*/
    private Integer clzNum;
    /*订单状态：1已下单，2已接单，3上课中，4已下课，5教练已评价，6学生已评价，7双方已评价，0已取消,9已拒单,10代表缺课*/
    private Integer orderState;
    /*实际上课开始时间*/
    private Date rstart;
    /*实际上课结束时间*/
    private Date rend;
    /*教练出发时间*/
    private Date cstart;
    /*教练回场时间*/
    private Date cend;
    /*支付状态：0代表未支付，1代表已支付,2代表支付失败，9代表老学员自动支付*/
    private Integer payState;
    /*结算状态：0代表未结算，1代表已结算*/
    private Integer checkoutState;
    /*是否需要接送：0代表不需要接送,1代表需要接送*/
    private Integer needTrans;
    /*接送状态：0未出发，1已出发，2已送回*/
    private Integer transState;
    /*支付id(t_log_pay)*/
    private Integer payId;
    /*下单时间*/
    private Date gtime;
    /*接单时间*/
    private Date atime;
    /*订单类型：1现约订单,2续课订单，3预约订单*/
    private Integer otype;
    /*续课单原单*/
    private String coorder;
    /*冗余：预约排班id*/
    private Integer ccid;
    /*单价，不作为支付使用，只用于看多节课优惠*/
    private Integer unitPrice;
    /*接送价格:支付时总价之一*/
    private Integer transPrice;
    /*训练场ID*/
    private Integer placeId;
    /*训练场经度*/
    private Double placeLge;
    /*训练场纬度*/
    private Double placeLae;
    /*接送描述*/
    private String transName;
    /*教练车*/
    private Integer carId;
    /*冗余：教练车型*/
    private String carName;
    /*冗余:汽车图标*/
    private String carImg;
    /*冗余：车牌号*/
    private String carNo;
    /*保险主键*/
    private Integer insId;
    /*保险费：支付时总价之一*/
    private Integer insPrice;
    /*冗余：保险名称*/
    private String insName;
    /*冗余：教练名称*/
    private String coachName;
    /*冗余：教练头像*/
    private String coachImg;
    /*教练电话*/
    private String coachMobile;
    /*教练评星得分*/
    private Integer coachStar;
    /*冗余：学生名称*/
    private String stuName;
    /*冗余：学生头像*/
    private String stuImg;
    /*学生电话*/
    private String stuMobile;
    /*冗余：科目名称*/
    private String courseName;
    /*教练补贴*/
    private Integer allowance;
    /*总价价钱*/
    private Integer priceTotal;
    /*实际支付金额*/
    private Integer payTotal;
    /*支付时间*/
    private Date payTime;

    private long timeLeft;
    private List<PlantClassVo> plantClassList;

    /*订单的城市id*/
    private Integer cityId;
    /*使用的优惠券id*/
    private Long coupon;
    /*冗余：优惠券抵扣金额，单位分*/
    private Integer couponTotal;
    /*冗余：优惠券名称*/
    private String couponName;

    private String courseType;
  	private Integer schoolId;
    /**
     *  带教人数
     */
    private Integer bookSum;
    
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

	public Integer getDirect() {
		return direct;
	}

	public void setDirect(Integer direct) {
		this.direct = direct;
	}

	public List<PlantClassVo> getPlantClassList() {
        return plantClassList;
    }

    public void setPlantClassList(List<PlantClassVo> plantClassList) {
        this.plantClassList = plantClassList;
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getCoachId() {
        return this.coachId;
    }

    public void setCoachId(Long coachId) {
        this.coachId = coachId;
    }

    public String getCourseId() {
        return this.courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getLearnAddr() {
        return this.learnAddr;
    }

    public void setLearnAddr(String learnAddr) {
        this.learnAddr = learnAddr;
    }

    public Long getStudentId() {
        return this.studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Integer getDltype() {
        return this.dltype;
    }

    public void setDltype(Integer dltype) {
        this.dltype = dltype;
    }

    public Double getLge() {
        return this.lge;
    }

    public void setLge(Double lge) {
        this.lge = lge;
    }

    public Double getLae() {
        return this.lae;
    }

    public void setLae(Double lae) {
        this.lae = lae;
    }

    public String getStuAddr() {
        return this.stuAddr;
    }

    public void setStuAddr(String stuAddr) {
        this.stuAddr = stuAddr;
    }

    public Date getPstart() {
        return this.pstart;
    }

    public void setPstart(Date pstart) {
        this.pstart = pstart;
    }

    public Date getPend() {
        return this.pend;
    }

    public void setPend(Date pend) {
        this.pend = pend;
    }

    public Integer getClzNum() {
        return this.clzNum;
    }

    public void setClzNum(Integer clzNum) {
        this.clzNum = clzNum;
    }

    public Integer getOrderState() {
        return this.orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Date getRstart() {
        return this.rstart;
    }

    public void setRstart(Date rstart) {
        this.rstart = rstart;
    }

    public Date getRend() {
        return this.rend;
    }

    public void setRend(Date rend) {
        this.rend = rend;
    }

    public Date getCstart() {
        return this.cstart;
    }

    public void setCstart(Date cstart) {
        this.cstart = cstart;
    }

    public Date getCend() {
        return this.cend;
    }

    public void setCend(Date cend) {
        this.cend = cend;
    }

    public Integer getPayState() {
        return this.payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
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
        this.needTrans = needTrans;
    }

    public Integer getTransState() {
        return this.transState;
    }

    public void setTransState(Integer transState) {
        this.transState = transState;
    }

    public Integer getPayId() {
        return this.payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    public Date getGtime() {
        return this.gtime;
    }

    public void setGtime(Date gtime) {
        this.gtime = gtime;
    }

    public Date getAtime() {
        return this.atime;
    }

    public void setAtime(Date atime) {
        this.atime = atime;
    }

    public Integer getOtype() {
        return this.otype;
    }

    public void setOtype(Integer otype) {
        this.otype = otype;
    }

    public String getCoorder() {
        return this.coorder;
    }

    public void setCoorder(String coorder) {
        this.coorder = coorder;
    }

    public Integer getCcid() {
        return this.ccid;
    }

    public void setCcid(Integer ccid) {
        this.ccid = ccid;
    }

    public Integer getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getTransPrice() {
        return this.transPrice;
    }

    public void setTransPrice(Integer transPrice) {
        this.transPrice = transPrice;
    }

    public Integer getPlaceId() {
        return this.placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public String getTransName() {
        return this.transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public Integer getCarId() {
        return this.carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return this.carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarImg() {
        return this.carImg;
    }

    public void setCarImg(String carImg) {
        this.carImg = carImg;
    }

    public String getCarNo() {
        return this.carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public Integer getInsId() {
        return this.insId;
    }

    public void setInsId(Integer insId) {
        this.insId = insId;
    }

    public Integer getInsPrice() {
        return this.insPrice;
    }

    public void setInsPrice(Integer insPrice) {
        this.insPrice = insPrice;
    }

    public String getInsName() {
        return this.insName;
    }

    public void setInsName(String insName) {
        this.insName = insName;
    }

    public String getCoachName() {
        return this.coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getCoachImg() {
        return this.coachImg;
    }

    public void setCoachImg(String coachImg) {
        this.coachImg = coachImg;
    }

    public String getCoachMobile() {
        return this.coachMobile;
    }

    public void setCoachMobile(String coachMobile) {
        this.coachMobile = coachMobile;
    }

    public Integer getCoachStar() {
        return this.coachStar;
    }

    public void setCoachStar(Integer coachStar) {
        this.coachStar = coachStar;
    }

    public String getStuName() {
        return this.stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuImg() {
        return this.stuImg;
    }

    public void setStuImg(String stuImg) {
        this.stuImg = stuImg;
    }

    public String getStuMobile() {
        return this.stuMobile;
    }

    public void setStuMobile(String stuMobile) {
        this.stuMobile = stuMobile;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getAllowance() {
        return this.allowance;
    }

    public void setAllowance(Integer allowance) {
        this.allowance = allowance;
    }

    public Integer getPriceTotal() {
        return this.priceTotal;
    }

    public void setPriceTotal(Integer priceTotal) {
        this.priceTotal = priceTotal;
    }

    public Integer getPayTotal() {
        return this.payTotal;
    }

    public void setPayTotal(Integer payTotal) {
        this.payTotal = payTotal;
    }

    public Date getPayTime() {
        return this.payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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

    public Integer getCityId() {
        return this.cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Long getCoupon() {
        return this.coupon;
    }

    public void setCoupon(Long coupon) {
        this.coupon = coupon;
    }

    public Integer getCouponTotal() {
        return this.couponTotal;
    }

    public void setCouponTotal(Integer couponTotal) {
        this.couponTotal = couponTotal;
    }

    public String getCouponName() {
        return this.couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCourseType() {
        return this.courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }
  public Integer getSchoolId() {
    return this.schoolId;
  }
  public void setSchoolId(Integer schoolId) {
    this.schoolId=schoolId;
  }
    public String toString() {
        return "{orderId=" + orderId + ",coachId=" + coachId + ",courseId=" + courseId + ",price=" + price + ",learnAddr=" + learnAddr + ",studentId=" + studentId + ",dltype=" + dltype + ",lge=" + lge + ",lae=" + lae + ",stuAddr=" + stuAddr + ",pstart=" + pstart + ",pend=" + pend + ",clzNum=" + clzNum + ",orderState=" + orderState + ",rstart=" + rstart + ",rend=" + rend + ",cstart=" + cstart + ",cend=" + cend + ",payState=" + payState + ",needTrans=" + needTrans + ",transState=" + transState + ",payId=" + payId + ",gtime=" + gtime + ",atime=" + atime + ",otype=" + otype + ",coorder=" + coorder + ",ccid=" + ccid + ",unitPrice=" + unitPrice + ",transPrice=" + transPrice + ",placeId=" + placeId + ",transName=" + transName + ",carId=" + carId + ",carName=" + carName + ",carImg=" + carImg + ",carNo=" + carNo + ",insId=" + insId + ",insPrice=" + insPrice + ",insName=" + insName + ",coachName=" + coachName + ",coachImg=" + coachImg + ",coachMobile=" + coachMobile + ",coachStar=" + coachStar + ",stuName=" + stuName + ",stuImg=" + stuImg + ",stuMobile=" + stuMobile + ",courseName=" + courseName + ",allowance=" + allowance + ",priceTotal=" + priceTotal + ",payTotal=" + payTotal + ",payTime=" + payTime + ",placeLge=" + placeLge + ",placeLae=" + placeLae + ",cityId=" + cityId + ",coupon=" + coupon + ",couponTotal=" + couponTotal + ",couponName=" + couponName + ",courseType=" + courseType + ",schoolId="+schoolId+"}";
    }

	public Integer getBookSum() {
		return bookSum;
	}

	public void setBookSum(Integer bookSum) {
		this.bookSum = bookSum;
	}
}
