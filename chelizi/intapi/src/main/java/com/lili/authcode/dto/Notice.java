package com.lili.authcode.dto;

import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable {
    private Integer noticeId;

    private String title;

    private Byte type;

    private Date time;

    private Integer adminId;

    private Byte userType;

    private Long userId;

    private String extra;

    private Byte isdel;

    private Date etime;

    private String pic;

    private Integer clickNum;

    private String applyexam;

    private String cityId;

    private String schoolId;

    private String schoolName;

    private String userName;

    private String utype;

    private String userIdStrs;

    private String digest;

    private String orderId;

    private String content;
    
    private Integer msgType;

    private static final long serialVersionUID = 1L;

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

    public Byte getIsdel() {
        return isdel;
    }

    public void setIsdel(Byte isdel) {
        this.isdel = isdel;
    }

    public Date getEtime() {
        return etime;
    }

    public void setEtime(Date etime) {
        this.etime = etime;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public String getApplyexam() {
        return applyexam;
    }

    public void setApplyexam(String applyexam) {
        this.applyexam = applyexam == null ? null : applyexam.trim();
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId == null ? null : cityId.trim();
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype == null ? null : utype.trim();
    }

    public String getUserIdStrs() {
        return userIdStrs;
    }

    public void setUserIdStrs(String userIdStrs) {
        this.userIdStrs = userIdStrs == null ? null : userIdStrs.trim();
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest == null ? null : digest.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", noticeId=").append(noticeId);
        sb.append(", title=").append(title);
        sb.append(", type=").append(type);
        sb.append(", time=").append(time);
        sb.append(", adminId=").append(adminId);
        sb.append(", userType=").append(userType);
        sb.append(", userId=").append(userId);
        sb.append(", extra=").append(extra);
        sb.append(", isdel=").append(isdel);
        sb.append(", etime=").append(etime);
        sb.append(", pic=").append(pic);
        sb.append(", clickNum=").append(clickNum);
        sb.append(", applyexam=").append(applyexam);
        sb.append(", cityId=").append(cityId);
        sb.append(", schoolId=").append(schoolId);
        sb.append(", schoolName=").append(schoolName);
        sb.append(", userName=").append(userName);
        sb.append(", utype=").append(utype);
        sb.append(", userIdStrs=").append(userIdStrs);
        sb.append(", digest=").append(digest);
        sb.append(", orderId=").append(orderId);
        sb.append(", content=").append(content);
        sb.append(", msgType=").append(msgType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}