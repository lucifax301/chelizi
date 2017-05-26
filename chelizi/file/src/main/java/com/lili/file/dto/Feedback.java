package com.lili.file.dto;

import java.util.Date;

public class Feedback {
    private Integer fbid;

    private String fbtitle;

    private String fbpicture;

    private Long userid;

    private Byte usertype;

    private String username;

    private String userphone;

    private Date ctime;

    private Date mtime;

    private Byte state;

    private String extra;

    private String fbcontent;

    public Integer getFbid() {
        return fbid;
    }

    public void setFbid(Integer fbid) {
        this.fbid = fbid;
    }

    public String getFbtitle() {
        return fbtitle;
    }

    public void setFbtitle(String fbtitle) {
        this.fbtitle = fbtitle == null ? null : fbtitle.trim();
    }

    public String getFbpicture() {
        return fbpicture;
    }

    public void setFbpicture(String fbpicture) {
        this.fbpicture = fbpicture == null ? null : fbpicture.trim();
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Byte getUsertype() {
        return usertype;
    }

    public void setUsertype(Byte usertype) {
        this.usertype = usertype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone == null ? null : userphone.trim();
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

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

    public String getFbcontent() {
        return fbcontent;
    }

    public void setFbcontent(String fbcontent) {
        this.fbcontent = fbcontent == null ? null : fbcontent.trim();
    }
}