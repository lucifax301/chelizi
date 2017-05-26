package com.lili.school.dto;

import java.io.Serializable;
import java.util.Date;

public class WechatEnrollPackage implements Serializable {
    private Integer ttid;

    private Integer cityId;

    private Integer schoolId;

    private String name;

    private Integer marketPrice;

    private Integer hoursPrice;

    private Integer price;

    private String title;

    private String icon;

    private String procPic;

    private Integer firstStep;

    private Byte isdel;

    private Long cuid;

    private Long muid;

    private Date ctime;

    private Date mtime;

    private String extra;

    private String feature;

    private String transferStyle;

    private String trainStyle;

    private String carStyle;

    private Integer hours;

    private String mailAddress;

    private Integer cstate;

    private Integer ostate;

    private Integer orderNum;

    private Integer graduateNum;

    private Float passRate;

    private String refuse;

    private Integer cType;

    private static final long serialVersionUID = 1L;

    public Integer getTtid() {
        return ttid;
    }

    public void setTtid(Integer ttid) {
        this.ttid = ttid;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Integer marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Integer getHoursPrice() {
        return hoursPrice;
    }

    public void setHoursPrice(Integer hoursPrice) {
        this.hoursPrice = hoursPrice;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getProcPic() {
        return procPic;
    }

    public void setProcPic(String procPic) {
        this.procPic = procPic == null ? null : procPic.trim();
    }

    public Integer getFirstStep() {
        return firstStep;
    }

    public void setFirstStep(Integer firstStep) {
        this.firstStep = firstStep;
    }

    public Byte getIsdel() {
        return isdel;
    }

    public void setIsdel(Byte isdel) {
        this.isdel = isdel;
    }

    public Long getCuid() {
        return cuid;
    }

    public void setCuid(Long cuid) {
        this.cuid = cuid;
    }

    public Long getMuid() {
        return muid;
    }

    public void setMuid(Long muid) {
        this.muid = muid;
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

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature == null ? null : feature.trim();
    }

    public String getTransferStyle() {
        return transferStyle;
    }

    public void setTransferStyle(String transferStyle) {
        this.transferStyle = transferStyle == null ? null : transferStyle.trim();
    }

    public String getTrainStyle() {
        return trainStyle;
    }

    public void setTrainStyle(String trainStyle) {
        this.trainStyle = trainStyle == null ? null : trainStyle.trim();
    }

    public String getCarStyle() {
        return carStyle;
    }

    public void setCarStyle(String carStyle) {
        this.carStyle = carStyle == null ? null : carStyle.trim();
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress == null ? null : mailAddress.trim();
    }

    public Integer getCstate() {
        return cstate;
    }

    public void setCstate(Integer cstate) {
        this.cstate = cstate;
    }

    public Integer getOstate() {
        return ostate;
    }

    public void setOstate(Integer ostate) {
        this.ostate = ostate;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getGraduateNum() {
        return graduateNum;
    }

    public void setGraduateNum(Integer graduateNum) {
        this.graduateNum = graduateNum;
    }

    public Float getPassRate() {
        return passRate;
    }

    public void setPassRate(Float passRate) {
        this.passRate = passRate;
    }

    public String getRefuse() {
        return refuse;
    }

    public void setRefuse(String refuse) {
        this.refuse = refuse == null ? null : refuse.trim();
    }

    public Integer getcType() {
        return cType;
    }

    public void setcType(Integer cType) {
        this.cType = cType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ttid=").append(ttid);
        sb.append(", cityId=").append(cityId);
        sb.append(", schoolId=").append(schoolId);
        sb.append(", name=").append(name);
        sb.append(", marketPrice=").append(marketPrice);
        sb.append(", hoursPrice=").append(hoursPrice);
        sb.append(", price=").append(price);
        sb.append(", title=").append(title);
        sb.append(", icon=").append(icon);
        sb.append(", procPic=").append(procPic);
        sb.append(", firstStep=").append(firstStep);
        sb.append(", isdel=").append(isdel);
        sb.append(", cuid=").append(cuid);
        sb.append(", muid=").append(muid);
        sb.append(", ctime=").append(ctime);
        sb.append(", mtime=").append(mtime);
        sb.append(", extra=").append(extra);
        sb.append(", feature=").append(feature);
        sb.append(", transferStyle=").append(transferStyle);
        sb.append(", trainStyle=").append(trainStyle);
        sb.append(", carStyle=").append(carStyle);
        sb.append(", hours=").append(hours);
        sb.append(", mailAddress=").append(mailAddress);
        sb.append(", cstate=").append(cstate);
        sb.append(", ostate=").append(ostate);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", graduateNum=").append(graduateNum);
        sb.append(", passRate=").append(passRate);
        sb.append(", refuse=").append(refuse);
        sb.append(", cType=").append(cType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}