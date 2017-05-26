package com.lili.school.model;

import com.lili.cms.entity.BasePagedEntity;

public class CourseNewDTO  extends BasePagedEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6164834116510522732L;

	private String coursenewNo;

    private String coursenewName;

    private Integer courseTemId;

    private String level1;

    private String level2;

    private String subject;

    private Integer subjectId;

    private String citys;

    private String descripton;

    private String content;

    private String period;

    private Integer drType;

    private String tags;

    private Integer price1;

    private Integer price2;

    private String remark;

    private String extra;

    private String otypes;

    private Integer isdel;

	public String getCoursenewNo() {
		return coursenewNo;
	}

	public void setCoursenewNo(String coursenewNo) {
		this.coursenewNo = coursenewNo;
	}

	public String getCoursenewName() {
		return coursenewName;
	}

	public void setCoursenewName(String coursenewName) {
		this.coursenewName = coursenewName;
	}

	public Integer getCourseTemId() {
		return courseTemId;
	}

	public void setCourseTemId(Integer courseTemId) {
		this.courseTemId = courseTemId;
	}

	public String getLevel1() {
		return level1;
	}

	public void setLevel1(String level1) {
		this.level1 = level1;
	}

	public String getLevel2() {
		return level2;
	}

	public void setLevel2(String level2) {
		this.level2 = level2;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getCitys() {
		return citys;
	}

	public void setCitys(String citys) {
		this.citys = citys;
	}

	public String getDescripton() {
		return descripton;
	}

	public void setDescripton(String descripton) {
		this.descripton = descripton;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Integer getDrType() {
		return drType;
	}

	public void setDrType(Integer drType) {
		this.drType = drType;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Integer getPrice1() {
		return price1;
	}

	public void setPrice1(Integer price1) {
		this.price1 = price1;
	}

	public Integer getPrice2() {
		return price2;
	}

	public void setPrice2(Integer price2) {
		this.price2 = price2;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getOtypes() {
		return otypes;
	}

	public void setOtypes(String otypes) {
		this.otypes = otypes;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}


}