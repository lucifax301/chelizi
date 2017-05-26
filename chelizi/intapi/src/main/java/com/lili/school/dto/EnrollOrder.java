package com.lili.school.dto;

import java.io.Serializable;
import java.util.Date;

public class EnrollOrder implements Serializable {
    private String orderId;

    private Long studentId;

    private Integer ttid;

    private String driveLicense;

    private Byte dltype;

    private String name;

    private Byte cardType;

    private String cardId;

    private String cardPic1;

    private String cardPic2;

    private Integer schoolId;

    private String officeId;

    private Byte payState;

    private Date payTime;

    private Byte postState;

    private Byte testState;

    private Integer cityId;

    private String cityName;

    private Integer price;

    private String schoolName;

    private Byte isdel;

    private Date ctime;

    private Date mtime;

    private Long coupon;

    private Integer couponTotal;

    private String couponName;

    private String payway;

    private Integer payTotal;

    private String extra;

    private String checkouter;

    private Date checkoutTime;

    private Byte orderState;

    private Integer brokerage;

    private String priceDetail;
    
    private Integer channel;

    private static final long serialVersionUID = 1L;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Integer getTtid() {
        return ttid;
    }

    public void setTtid(Integer ttid) {
        this.ttid = ttid;
    }

    public String getDriveLicense() {
        return driveLicense;
    }

    public void setDriveLicense(String driveLicense) {
        this.driveLicense = driveLicense == null ? null : driveLicense.trim();
    }

    public Byte getDltype() {
        return dltype;
    }

    public void setDltype(Byte dltype) {
        this.dltype = dltype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getCardType() {
        return cardType;
    }

    public void setCardType(Byte cardType) {
        this.cardType = cardType;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    public String getCardPic1() {
        return cardPic1;
    }

    public void setCardPic1(String cardPic1) {
        this.cardPic1 = cardPic1 == null ? null : cardPic1.trim();
    }

    public String getCardPic2() {
        return cardPic2;
    }

    public void setCardPic2(String cardPic2) {
        this.cardPic2 = cardPic2 == null ? null : cardPic2.trim();
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId == null ? null : officeId.trim();
    }

    public Byte getPayState() {
        return payState;
    }

    public void setPayState(Byte payState) {
        this.payState = payState;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Byte getPostState() {
        return postState;
    }

    public void setPostState(Byte postState) {
        this.postState = postState;
    }

    public Byte getTestState() {
        return testState;
    }

    public void setTestState(Byte testState) {
        this.testState = testState;
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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    public Byte getIsdel() {
        return isdel;
    }

    public void setIsdel(Byte isdel) {
        this.isdel = isdel;
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

    public Long getCoupon() {
        return coupon;
    }

    public void setCoupon(Long coupon) {
        this.coupon = coupon;
    }

    public Integer getCouponTotal() {
        return couponTotal;
    }

    public void setCouponTotal(Integer couponTotal) {
        this.couponTotal = couponTotal;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName == null ? null : couponName.trim();
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway == null ? null : payway.trim();
    }

    public Integer getPayTotal() {
        return payTotal;
    }

    public void setPayTotal(Integer payTotal) {
        this.payTotal = payTotal;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

    public String getCheckouter() {
        return checkouter;
    }

    public void setCheckouter(String checkouter) {
        this.checkouter = checkouter == null ? null : checkouter.trim();
    }

    public Date getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(Date checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public Byte getOrderState() {
        return orderState;
    }

    public void setOrderState(Byte orderState) {
        this.orderState = orderState;
    }

    public Integer getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(Integer brokerage) {
        this.brokerage = brokerage;
    }

    public String getPriceDetail() {
        return priceDetail;
    }

    public void setPriceDetail(String priceDetail) {
        this.priceDetail = priceDetail == null ? null : priceDetail.trim();
    }

    public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", studentId=").append(studentId);
        sb.append(", ttid=").append(ttid);
        sb.append(", driveLicense=").append(driveLicense);
        sb.append(", dltype=").append(dltype);
        sb.append(", name=").append(name);
        sb.append(", cardType=").append(cardType);
        sb.append(", cardId=").append(cardId);
        sb.append(", cardPic1=").append(cardPic1);
        sb.append(", cardPic2=").append(cardPic2);
        sb.append(", schoolId=").append(schoolId);
        sb.append(", officeId=").append(officeId);
        sb.append(", payState=").append(payState);
        sb.append(", payTime=").append(payTime);
        sb.append(", postState=").append(postState);
        sb.append(", testState=").append(testState);
        sb.append(", cityId=").append(cityId);
        sb.append(", cityName=").append(cityName);
        sb.append(", price=").append(price);
        sb.append(", schoolName=").append(schoolName);
        sb.append(", isdel=").append(isdel);
        sb.append(", ctime=").append(ctime);
        sb.append(", mtime=").append(mtime);
        sb.append(", coupon=").append(coupon);
        sb.append(", couponTotal=").append(couponTotal);
        sb.append(", couponName=").append(couponName);
        sb.append(", payway=").append(payway);
        sb.append(", payTotal=").append(payTotal);
        sb.append(", extra=").append(extra);
        sb.append(", checkouter=").append(checkouter);
        sb.append(", checkoutTime=").append(checkoutTime);
        sb.append(", orderState=").append(orderState);
        sb.append(", brokerage=").append(brokerage);
        sb.append(", priceDetail=").append(priceDetail);
        sb.append(", channel=").append(channel);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}