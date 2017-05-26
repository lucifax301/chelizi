package com.lili.school.model;

import java.util.Date;

import org.omg.CORBA.INTERNAL;

import com.lili.cms.entity.BasePagedEntity;

public class WechatEnrollPackageBDTO extends BasePagedEntity{

	 private static final long serialVersionUID = 1L;
		
	    private Integer ttid;
	    
	    private Integer cityId;
	    
	    private Integer school_id;  //schoolId被父类占用

	    private String name;

	    private Integer marketPrice;

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

	    private String protocol;

	    private String priceDetail;

	    private String test_condition;

	    private String describtion;
	    
	    private String region;
	    
	    private String schoolName;
	    
        private String transferStyle;
	    
	    private String trainStyle;
	    
	    private String carStyle;
	    
	    private Integer hours;
	    
	    private String mailAddress;
	    
	    private String remark;
	    
	    private Integer cstate;
	    
	    private Integer ostate;
	    
	    private Integer orderNum;//报名人数
	    
	    private Integer graduateNum; //毕业人数
	    
        private Float passRate;
	    
	    private String refuse;
	    
	    private Integer cType;
	    
	    private Integer hoursPrice;

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

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getMarketPrice() {
			return marketPrice;
		}

		public void setMarketPrice(Integer marketPrice) {
			this.marketPrice = marketPrice;
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
			this.title = title;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public String getProcPic() {
			return procPic;
		}

		public void setProcPic(String procPic) {
			this.procPic = procPic;
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
			this.extra = extra;
		}

		public String getProtocol() {
			return protocol;
		}

		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}

		public String getPriceDetail() {
			return priceDetail;
		}

		public void setPriceDetail(String priceDetail) {
			this.priceDetail = priceDetail;
		}

		public String getTest_condition() {
			return test_condition;
		}

		public void setTest_condition(String test_condition) {
			this.test_condition = test_condition;
		}

		public String getDescribtion() {
			return describtion;
		}

		public void setDescribtion(String describtion) {
			this.describtion = describtion;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}

		public String getSchoolName() {
			return schoolName;
		}

		public void setSchoolName(String schoolName) {
			this.schoolName = schoolName;
		}

		public Integer getSchool_id() {
			return school_id;
		}

		public void setSchool_id(Integer school_id) {
			this.school_id = school_id;
		}

		public String getTransferStyle() {
			return transferStyle;
		}

		public void setTransferStyle(String transferStyle) {
			this.transferStyle = transferStyle;
		}

		public String getTrainStyle() {
			return trainStyle;
		}

		public void setTrainStyle(String trainStyle) {
			this.trainStyle = trainStyle;
		}

		public String getCarStyle() {
			return carStyle;
		}

		public void setCarStyle(String carStyle) {
			this.carStyle = carStyle;
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
			this.mailAddress = mailAddress;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
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

		public String getFeature() {
			return feature;
		}

		public void setFeature(String feature) {
			this.feature = feature;
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
			this.refuse = refuse;
		}

		public Integer getcType() {
			return cType;
		}

		public void setcType(Integer cType) {
			this.cType = cType;
		}

		public Integer getHoursPrice() {
			return hoursPrice;
		}

		public void setHoursPrice(Integer hoursPrice) {
			this.hoursPrice = hoursPrice;
		}

		
        
		
	    
}
