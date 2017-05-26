package com.lili.exam.dto;

import java.io.Serializable;
import java.util.Date;

public class ExamPlaceOrder implements Serializable {
    private String orderId;

    private Integer classId;

    private Integer placeId;

    private String placeName;

    private String school;

    private Byte type;

    private Byte drtype;

    private Long coachId;

    private String coachName;

    private String coachMobile;

    private String coachImg;

    private Byte coachType;

    private String carNo;

    private String device;

    private Integer duration;

    private Integer favorUse;

    private Integer favorGen;

    private Integer favorLeft;

    private String favorInfo;

    private Integer favorType;

    private Integer delay;

    private String delayInfo;

    private Integer priceTotal;

    private Integer couponTotal;
    
    private Integer returnTotal;
    
    private Integer refundTotal;

    private Integer payTotal;

    private Date payTime;

    private String payWay;

    private Byte state;

    private Date pstart;

    private Date pend;

    private Date rstart;

    private Date rend;

    private String remark;

    private Date ctime;

    private Date mtime;

    private String extra;
    
    private Integer code;
    
    private Byte codeValid;
    
    private Date validTime; 

    private static final long serialVersionUID = 1L;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
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
        this.placeName = placeName == null ? null : placeName.trim();
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getDrtype() {
        return drtype;
    }

    public void setDrtype(Byte drtype) {
        this.drtype = drtype;
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

    public String getCoachMobile() {
        return coachMobile;
    }

    public void setCoachMobile(String coachMobile) {
        this.coachMobile = coachMobile == null ? null : coachMobile.trim();
    }

    public String getCoachImg() {
        return coachImg;
    }

    public void setCoachImg(String coachImg) {
        this.coachImg = coachImg == null ? null : coachImg.trim();
    }

    public Byte getCoachType() {
        return coachType;
    }

    public void setCoachType(Byte coachType) {
        this.coachType = coachType;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo == null ? null : carNo.trim();
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device == null ? null : device.trim();
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getFavorUse() {
        return favorUse;
    }

    public void setFavorUse(Integer favorUse) {
        this.favorUse = favorUse;
    }

    public Integer getFavorGen() {
        return favorGen;
    }

    public void setFavorGen(Integer favorGen) {
        this.favorGen = favorGen;
    }

    public Integer getFavorLeft() {
        return favorLeft;
    }

    public void setFavorLeft(Integer favorLeft) {
        this.favorLeft = favorLeft;
    }

    public String getFavorInfo() {
        return favorInfo;
    }

    public void setFavorInfo(String favorInfo) {
        this.favorInfo = favorInfo == null ? null : favorInfo.trim();
    }

    public Integer getFavorType() {
        return favorType;
    }

    public void setFavorType(Integer favorType) {
        this.favorType = favorType;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public String getDelayInfo() {
        return delayInfo;
    }

    public void setDelayInfo(String delayInfo) {
        this.delayInfo = delayInfo == null ? null : delayInfo.trim();
    }

    public Integer getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(Integer priceTotal) {
        this.priceTotal = priceTotal;
    }

    public Integer getCouponTotal() {
        return couponTotal;
    }

    public void setCouponTotal(Integer couponTotal) {
        this.couponTotal = couponTotal;
    }

    public Integer getPayTotal() {
        return payTotal;
    }

    public void setPayTotal(Integer payTotal) {
        this.payTotal = payTotal;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay == null ? null : payWay.trim();
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Byte getCodeValid() {
		return codeValid;
	}

	public void setCodeValid(Byte codeValid) {
		this.codeValid = codeValid;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public Integer getReturnTotal() {
		return returnTotal;
	}

	public void setReturnTotal(Integer returnTotal) {
		this.returnTotal = returnTotal;
	}

	public Integer getRefundTotal() {
		return refundTotal;
	}

	public void setRefundTotal(Integer refundTotal) {
		this.refundTotal = refundTotal;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", classId=").append(classId);
        sb.append(", placeId=").append(placeId);
        sb.append(", placeName=").append(placeName);
        sb.append(", school=").append(school);
        sb.append(", type=").append(type);
        sb.append(", drtype=").append(drtype);
        sb.append(", coachId=").append(coachId);
        sb.append(", coachName=").append(coachName);
        sb.append(", coachMobile=").append(coachMobile);
        sb.append(", coachImg=").append(coachImg);
        sb.append(", coachType=").append(coachType);
        sb.append(", carNo=").append(carNo);
        sb.append(", device=").append(device);
        sb.append(", duration=").append(duration);
        sb.append(", favorUse=").append(favorUse);
        sb.append(", favorGen=").append(favorGen);
        sb.append(", favorLeft=").append(favorLeft);
        sb.append(", favorInfo=").append(favorInfo);
        sb.append(", favorType=").append(favorType);
        sb.append(", delay=").append(delay);
        sb.append(", delayInfo=").append(delayInfo);
        sb.append(", priceTotal=").append(priceTotal);
        sb.append(", couponTotal=").append(couponTotal);
        sb.append(", payTotal=").append(payTotal);
        sb.append(", returnTotal=").append(returnTotal);
        sb.append(", refundTotal=").append(refundTotal);
        sb.append(", payTime=").append(payTime);
        sb.append(", payWay=").append(payWay);
        sb.append(", state=").append(state);
        sb.append(", pstart=").append(pstart);
        sb.append(", pend=").append(pend);
        sb.append(", rstart=").append(rstart);
        sb.append(", rend=").append(rend);
        sb.append(", remark=").append(remark);
        sb.append(", ctime=").append(ctime);
        sb.append(", mtime=").append(mtime);
        sb.append(", extra=").append(extra);
        sb.append(", code=").append(code);
        sb.append(", codeValid=").append(codeValid);
        sb.append(", validTime=").append(validTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}