package com.lili.school.dto;

import java.io.Serializable;

public class EnrollProgressTemplate implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer cpid;

    private Integer ttid;

    private Integer cityId;

    private Integer stepId;

    private Integer stepType;

    private Integer nextStep;

    private Integer retryStep;

    private String icon;

    private String title;

    private String dpage;

    private String bpage;

    private String preDoc;

    private String noneDoc;

    private String submitDoc;

    private String succDoc;

    private String failDoc;

    private Byte noneRec;

    private Byte submitRec;

    private Byte succRec;

    private Byte failRec;

    private String submitPush;

    private String succPush;

    private String failPush;

    private Byte preDisplay;

    private Integer price;

    private Byte red;

    private String recoCourse;

    private String otherCourse;

    private Integer orderBy;

    private Byte recovery;

    public Integer getCpid() {
        return cpid;
    }

    public void setCpid(Integer cpid) {
        this.cpid = cpid;
    }

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

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public Integer getStepType() {
        return stepType;
    }

    public void setStepType(Integer stepType) {
        this.stepType = stepType;
    }

    public Integer getNextStep() {
        return nextStep;
    }

    public void setNextStep(Integer nextStep) {
        this.nextStep = nextStep;
    }

    public Integer getRetryStep() {
        return retryStep;
    }

    public void setRetryStep(Integer retryStep) {
        this.retryStep = retryStep;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDpage() {
        return dpage;
    }

    public void setDpage(String dpage) {
        this.dpage = dpage == null ? null : dpage.trim();
    }

    public String getBpage() {
        return bpage;
    }

    public void setBpage(String bpage) {
        this.bpage = bpage == null ? null : bpage.trim();
    }

    public String getPreDoc() {
        return preDoc;
    }

    public void setPreDoc(String preDoc) {
        this.preDoc = preDoc == null ? null : preDoc.trim();
    }

    public String getNoneDoc() {
        return noneDoc;
    }

    public void setNoneDoc(String noneDoc) {
        this.noneDoc = noneDoc == null ? null : noneDoc.trim();
    }

    public String getSubmitDoc() {
        return submitDoc;
    }

    public void setSubmitDoc(String submitDoc) {
        this.submitDoc = submitDoc == null ? null : submitDoc.trim();
    }

    public String getSuccDoc() {
        return succDoc;
    }

    public void setSuccDoc(String succDoc) {
        this.succDoc = succDoc == null ? null : succDoc.trim();
    }

    public String getFailDoc() {
        return failDoc;
    }

    public void setFailDoc(String failDoc) {
        this.failDoc = failDoc == null ? null : failDoc.trim();
    }

    public Byte getNoneRec() {
        return noneRec;
    }

    public void setNoneRec(Byte noneRec) {
        this.noneRec = noneRec;
    }

    public Byte getSubmitRec() {
        return submitRec;
    }

    public void setSubmitRec(Byte submitRec) {
        this.submitRec = submitRec;
    }

    public Byte getSuccRec() {
        return succRec;
    }

    public void setSuccRec(Byte succRec) {
        this.succRec = succRec;
    }

    public Byte getFailRec() {
        return failRec;
    }

    public void setFailRec(Byte failRec) {
        this.failRec = failRec;
    }

    public String getSubmitPush() {
        return submitPush;
    }

    public void setSubmitPush(String submitPush) {
        this.submitPush = submitPush == null ? null : submitPush.trim();
    }

    public String getSuccPush() {
        return succPush;
    }

    public void setSuccPush(String succPush) {
        this.succPush = succPush == null ? null : succPush.trim();
    }

    public String getFailPush() {
        return failPush;
    }

    public void setFailPush(String failPush) {
        this.failPush = failPush == null ? null : failPush.trim();
    }

    public Byte getPreDisplay() {
        return preDisplay;
    }

    public void setPreDisplay(Byte preDisplay) {
        this.preDisplay = preDisplay;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Byte getRed() {
        return red;
    }

    public void setRed(Byte red) {
        this.red = red;
    }

    public String getRecoCourse() {
        return recoCourse;
    }

    public void setRecoCourse(String recoCourse) {
        this.recoCourse = recoCourse == null ? null : recoCourse.trim();
    }

    public String getOtherCourse() {
        return otherCourse;
    }

    public void setOtherCourse(String otherCourse) {
        this.otherCourse = otherCourse == null ? null : otherCourse.trim();
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Byte getRecovery() {
        return recovery;
    }

    public void setRecovery(Byte recovery) {
        this.recovery = recovery;
    }

	@Override
	public String toString() {
		return "EnrollProgressTemplate [cpid=" + cpid + ", ttid=" + ttid
				+ ", cityId=" + cityId + ", stepId=" + stepId + ", stepType="
				+ stepType + ", nextStep=" + nextStep + ", retryStep="
				+ retryStep + ", icon=" + icon + ", title=" + title
				+ ", dpage=" + dpage + ", bpage=" + bpage + ", preDoc="
				+ preDoc + ", noneDoc=" + noneDoc + ", submitDoc=" + submitDoc
				+ ", succDoc=" + succDoc + ", failDoc=" + failDoc
				+ ", noneRec=" + noneRec + ", submitRec=" + submitRec
				+ ", succRec=" + succRec + ", failRec=" + failRec
				+ ", submitPush=" + submitPush + ", succPush=" + succPush
				+ ", failPush=" + failPush + ", preDisplay=" + preDisplay
				+ ", price=" + price + ", red=" + red + ", recoCourse="
				+ recoCourse + ", otherCourse=" + otherCourse + ", orderBy="
				+ orderBy + ", recovery=" + recovery + "]";
	}
    
    
    
}