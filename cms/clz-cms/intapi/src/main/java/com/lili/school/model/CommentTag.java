package com.lili.school.model;


import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

public class CommentTag  extends BasePagedEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7976875974457953675L;

	private Integer ctid;

    private String tag;

    private Integer courseId;

    private Integer type;

    private Integer goodBad;

    private Integer isdel;

    private Integer cuid;

    private Integer muid;

    private Date ctime;

    private Date mtime;

    private String star1;

    private String star2;

    private String star3;

    private String star4;

    private String star5;

    private String extra;

    public Integer getCtid() {
        return ctid;
    }

    public void setCtid(Integer ctid) {
        this.ctid = ctid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getGoodBad() {
        return goodBad;
    }

    public void setGoodBad(Integer goodBad) {
        this.goodBad = goodBad;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public Integer getCuid() {
        return cuid;
    }

    public void setCuid(Integer cuid) {
        this.cuid = cuid;
    }

    public Integer getMuid() {
        return muid;
    }

    public void setMuid(Integer muid) {
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

    public String getStar1() {
        return star1;
    }

    public void setStar1(String star1) {
        this.star1 = star1 == null ? null : star1.trim();
    }

    public String getStar2() {
        return star2;
    }

    public void setStar2(String star2) {
        this.star2 = star2 == null ? null : star2.trim();
    }

    public String getStar3() {
        return star3;
    }

    public void setStar3(String star3) {
        this.star3 = star3 == null ? null : star3.trim();
    }

    public String getStar4() {
        return star4;
    }

    public void setStar4(String star4) {
        this.star4 = star4 == null ? null : star4.trim();
    }

    public String getStar5() {
        return star5;
    }

    public void setStar5(String star5) {
        this.star5 = star5 == null ? null : star5.trim();
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }
}