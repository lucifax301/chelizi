package com.lili.school.dto;

import java.io.Serializable;
import java.util.Date;

public class EnrollPackageTemplate implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer ttid;

    private Integer cityId;

    private String name;

    private Integer price;

    private String title;

    private String icon;

    private Byte selfTest;

    private String procPic;

    private Integer orderBy;

    private Integer firstStep;

    private Byte isdel;

    private Long cuid;

    private Long muid;

    private Date ctime;

    private Date mtime;

    private String tmptotalprice;

    private String tmpcourse;

    private String tmpcourseprice;

    private String tmpestimate;

    private String tmpnote;

    private String extra;

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
        this.name = name == null ? null : name.trim();
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

    public Byte getSelfTest() {
        return selfTest;
    }

    public void setSelfTest(Byte selfTest) {
        this.selfTest = selfTest;
    }

    public String getProcPic() {
        return procPic;
    }

    public void setProcPic(String procPic) {
        this.procPic = procPic == null ? null : procPic.trim();
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
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

    public String getTmptotalprice() {
        return tmptotalprice;
    }

    public void setTmptotalprice(String tmptotalprice) {
        this.tmptotalprice = tmptotalprice == null ? null : tmptotalprice.trim();
    }

    public String getTmpcourse() {
        return tmpcourse;
    }

    public void setTmpcourse(String tmpcourse) {
        this.tmpcourse = tmpcourse == null ? null : tmpcourse.trim();
    }

    public String getTmpcourseprice() {
        return tmpcourseprice;
    }

    public void setTmpcourseprice(String tmpcourseprice) {
        this.tmpcourseprice = tmpcourseprice == null ? null : tmpcourseprice.trim();
    }

    public String getTmpestimate() {
        return tmpestimate;
    }

    public void setTmpestimate(String tmpestimate) {
        this.tmpestimate = tmpestimate == null ? null : tmpestimate.trim();
    }

    public String getTmpnote() {
        return tmpnote;
    }

    public void setTmpnote(String tmpnote) {
        this.tmpnote = tmpnote == null ? null : tmpnote.trim();
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }
}