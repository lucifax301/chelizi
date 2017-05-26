package com.lili.student.model;

import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

public class HtmlObjectBDTO extends BasePagedEntity{
    private Integer hid;

    private String hname;

    private String hdescription;

    private String url;

    private String extra;

    private Date ctime;

    private Date mtime;

    private String html;

    private static final long serialVersionUID = 1L;

    public Integer getHid() {
        return hid;
    }

    public void setHid(Integer hid) {
        this.hid = hid;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname == null ? null : hname.trim();
    }

    public String getHdescription() {
        return hdescription;
    }

    public void setHdescription(String hdescription) {
        this.hdescription = hdescription == null ? null : hdescription.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
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

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html == null ? null : html.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", hid=").append(hid);
        sb.append(", hname=").append(hname);
        sb.append(", hdescription=").append(hdescription);
        sb.append(", url=").append(url);
        sb.append(", extra=").append(extra);
        sb.append(", ctime=").append(ctime);
        sb.append(", mtime=").append(mtime);
        sb.append(", html=").append(html);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}