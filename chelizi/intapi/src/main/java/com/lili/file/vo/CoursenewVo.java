package com.lili.file.vo;

import java.io.Serializable;

public class CoursenewVo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -723477137725100383L;

	private String coursenewno;

    private String coursenewname;

    private Integer coursetemid;

    private String level1;

    private String level2;

    private String subject;

    private Integer subjectid;

/*    private String citys;

    private String descripton;

    private String content;

    private String period;*/

    private Byte drtype;

    /*    private String tags;

    private Integer price1;

    private Integer price2;*/

    private String remark;

    private String extra;
    
    private String otypes;
    
    private String extra2;

    public String getCoursenewno() {
        return coursenewno;
    }

    public void setCoursenewno(String coursenewno) {
        this.coursenewno = coursenewno == null ? null : coursenewno.trim();
    }

    public String getCoursenewname() {
        return coursenewname;
    }

    public void setCoursenewname(String coursenewname) {
        this.coursenewname = coursenewname == null ? null : coursenewname.trim();
    }

    public Integer getCoursetemid() {
        return coursetemid;
    }

    public void setCoursetemid(Integer coursetemid) {
        this.coursetemid = coursetemid;
    }

    public String getLevel1() {
        return level1;
    }

    public void setLevel1(String level1) {
        this.level1 = level1 == null ? null : level1.trim();
    }

    public String getLevel2() {
        return level2;
    }

    public void setLevel2(String level2) {
        this.level2 = level2 == null ? null : level2.trim();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public Integer getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }

   /* public String getCitys() {
        return citys;
    }

    public void setCitys(String citys) {
        this.citys = citys == null ? null : citys.trim();
    }

    public String getDescripton() {
        return descripton;
    }

    public void setDescripton(String descripton) {
        this.descripton = descripton == null ? null : descripton.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }*/

    public Byte getDrtype() {
        return drtype;
    }

    public void setDrtype(Byte drtype) {
        this.drtype = drtype;
    }

    /*public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
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
    }*/

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

	public String getOtypes() {
		return otypes;
	}

	public void setOtypes(String otypes) {
		this.otypes = otypes;
	}

	public String getExtra2() {
		return extra2;
	}

	public void setExtra2(String extra2) {
		this.extra2 = extra2;
	}
}