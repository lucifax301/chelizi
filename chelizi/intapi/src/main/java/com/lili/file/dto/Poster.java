package com.lili.file.dto;

import java.io.Serializable;
import java.util.Date;

public class Poster implements Serializable {
    private Integer posterid;

    private String postername;

    private String posterpic;

    private String posterpic2;

    private String posterpic3;

    private String posterurl;

    private String posterurl2;

    private String extra;

    private Integer type;

    private Integer isdel;

    private Date ctime;

    private Date mtime;

    private static final long serialVersionUID = 1L;

    public Integer getPosterid() {
        return posterid;
    }

    public void setPosterid(Integer posterid) {
        this.posterid = posterid;
    }

    public String getPostername() {
        return postername;
    }

    public void setPostername(String postername) {
        this.postername = postername == null ? null : postername.trim();
    }

    public String getPosterpic() {
        return posterpic;
    }

    public void setPosterpic(String posterpic) {
        this.posterpic = posterpic == null ? null : posterpic.trim();
    }

    public String getPosterpic2() {
        return posterpic2;
    }

    public void setPosterpic2(String posterpic2) {
        this.posterpic2 = posterpic2 == null ? null : posterpic2.trim();
    }

    public String getPosterpic3() {
        return posterpic3;
    }

    public void setPosterpic3(String posterpic3) {
        this.posterpic3 = posterpic3 == null ? null : posterpic3.trim();
    }

    public String getPosterurl() {
        return posterurl;
    }

    public void setPosterurl(String posterurl) {
        this.posterurl = posterurl == null ? null : posterurl.trim();
    }

    public String getPosterurl2() {
        return posterurl2;
    }

    public void setPosterurl2(String posterurl2) {
        this.posterurl2 = posterurl2 == null ? null : posterurl2.trim();
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
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
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", posterid=").append(posterid);
        sb.append(", postername=").append(postername);
        sb.append(", posterpic=").append(posterpic);
        sb.append(", posterpic2=").append(posterpic2);
        sb.append(", posterpic3=").append(posterpic3);
        sb.append(", posterurl=").append(posterurl);
        sb.append(", posterurl2=").append(posterurl2);
        sb.append(", extra=").append(extra);
        sb.append(", type=").append(type);
        sb.append(", isdel=").append(isdel);
        sb.append(", ctime=").append(ctime);
        sb.append(", mtime=").append(mtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}