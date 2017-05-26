package com.lili.school.model;


import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

public class PosterDTO  extends BasePagedEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2428682371416684261L;

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
}