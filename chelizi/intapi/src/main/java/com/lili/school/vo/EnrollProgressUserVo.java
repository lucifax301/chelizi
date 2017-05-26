package com.lili.school.vo;

import java.io.Serializable;
import java.util.Date;

public class EnrollProgressUserVo implements Serializable {
    private Integer cpid;

    private String title;

    private Long studentId;

    private Integer stepId;

    private Integer stepTimes;

    private Byte payState;

    private Date payTime;

    private Byte processState;

    private String result;

    private Byte isread;

    private Integer nextStep;

    private Byte needShow;

    private String outData;

    private String icon;

    private Integer price;

    private Integer cityId;

    private Integer ttid;

    private Integer schoolId;

    private Byte red;

    private String nextTitle;

    private String recoCourse;

    private String otherCourse;

    private String dpage;

    private String bpage;

    private Byte recovery;

    private Integer orderBy;

    private Byte isdel;

    private Long cuid;

    private Long muid;

    private Date ctime;

    private Date mtime;

	public Integer getCpid() {
		return cpid;
	}

	public void setCpid(Integer cpid) {
		this.cpid = cpid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Integer getStepId() {
		return stepId;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	public Integer getStepTimes() {
		return stepTimes;
	}

	public void setStepTimes(Integer stepTimes) {
		this.stepTimes = stepTimes;
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

	public Byte getProcessState() {
		return processState;
	}

	public void setProcessState(Byte processState) {
		this.processState = processState;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Byte getIsread() {
		return isread;
	}

	public void setIsread(Byte isread) {
		this.isread = isread;
	}

	public Integer getNextStep() {
		return nextStep;
	}

	public void setNextStep(Integer nextStep) {
		this.nextStep = nextStep;
	}

	public Byte getNeedShow() {
		return needShow;
	}

	public void setNeedShow(Byte needShow) {
		this.needShow = needShow;
	}

	public String getOutData() {
		return outData;
	}

	public void setOutData(String outData) {
		this.outData = outData;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getTtid() {
		return ttid;
	}

	public void setTtid(Integer ttid) {
		this.ttid = ttid;
	}

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public Byte getRed() {
		return red;
	}

	public void setRed(Byte red) {
		this.red = red;
	}

	public String getNextTitle() {
		return nextTitle;
	}

	public void setNextTitle(String nextTitle) {
		this.nextTitle = nextTitle;
	}

	public String getRecoCourse() {
		return recoCourse;
	}

	public void setRecoCourse(String recoCourse) {
		this.recoCourse = recoCourse;
	}

	public String getOtherCourse() {
		return otherCourse;
	}

	public void setOtherCourse(String otherCourse) {
		this.otherCourse = otherCourse;
	}

	public String getDpage() {
		return dpage;
	}

	public void setDpage(String dpage) {
		this.dpage = dpage;
	}

	public String getBpage() {
		return bpage;
	}

	public void setBpage(String bpage) {
		this.bpage = bpage;
	}

	public Byte getRecovery() {
		return recovery;
	}

	public void setRecovery(Byte recovery) {
		this.recovery = recovery;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
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

	@Override
	public String toString() {
		return "EnrollProgressUserVo [cpid=" + cpid + ", title=" + title
				+ ", studentId=" + studentId + ", stepId=" + stepId
				+ ", stepTimes=" + stepTimes + ", payState=" + payState
				+ ", payTime=" + payTime + ", processState=" + processState
				+ ", result=" + result + ", isread=" + isread + ", nextStep="
				+ nextStep + ", needShow=" + needShow + ", outData=" + outData
				+ ", icon=" + icon + ", price=" + price + ", cityId=" + cityId
				+ ", ttid=" + ttid + ", schoolId=" + schoolId + ", red=" + red
				+ ", nextTitle=" + nextTitle + ", recoCourse=" + recoCourse
				+ ", otherCourse=" + otherCourse + ", dpage=" + dpage
				+ ", bpage=" + bpage + ", recovery=" + recovery + ", orderBy="
				+ orderBy + ", isdel=" + isdel + ", cuid=" + cuid + ", muid="
				+ muid + ", ctime=" + ctime + ", mtime=" + mtime + "]";
	}
    
    
    
}
