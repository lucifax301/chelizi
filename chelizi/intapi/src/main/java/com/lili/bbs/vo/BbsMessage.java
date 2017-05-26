package com.lili.bbs.vo;

import java.io.Serializable;
import java.util.Date;

import com.lili.common.util.MyRowBounds;

public class BbsMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -392060891586741076L;

	private Integer id;

	private Long userId;

	private Integer userType;

	private String userName;

	private String headIcon;

	private Integer classify;

	private Integer cityId;

	private String cityName;

	private Integer topicId;

	private String topicName;

	private Integer titleType;

	private Integer praise;

	private Integer isPraise;

	private Integer reply;

	private Integer isInform;

	private Integer inform;

	private Integer topLevel;

	private Integer isTopGroup;

	private Integer isTopGlobal;

	private Integer status;

	private Integer channel;

	private Integer isDel;

	private Date ctime;

	private Long cuid;

	private Date mtime;

	private Long muid;

	private String title;

	private String content;

	private String pic;

	private String ext;

	private String startTime;

	private String endTime;

	private MyRowBounds myRowBounds;

	public MyRowBounds getMyRowBounds() {
		return myRowBounds;
	}

	public void setMyRowBounds(MyRowBounds myRowBounds) {
		this.myRowBounds = myRowBounds;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHeadIcon() {
		return headIcon;
	}

	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}

	public Integer getClassify() {
		return classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public Integer getTitleType() {
		return titleType;
	}

	public void setTitleType(Integer titleType) {
		this.titleType = titleType;
	}

	public Integer getPraise() {
		return praise;
	}

	public void setPraise(Integer praise) {
		this.praise = praise;
	}

	public Integer getIsPraise() {
		return isPraise;
	}

	public void setIsPraise(Integer isPraise) {
		this.isPraise = isPraise;
	}

	public Integer getReply() {
		return reply;
	}

	public void setReply(Integer reply) {
		this.reply = reply;
	}

	public Integer getIsInform() {
		return isInform;
	}

	public void setIsInform(Integer isInform) {
		this.isInform = isInform;
	}

	public Integer getInform() {
		return inform;
	}

	public void setInform(Integer inform) {
		this.inform = inform;
	}

	public Integer getTopLevel() {
		return topLevel;
	}

	public void setTopLevel(Integer topLevel) {
		this.topLevel = topLevel;
	}

	public Integer getIsTopGroup() {
		return isTopGroup;
	}

	public void setIsTopGroup(Integer isTopGroup) {
		this.isTopGroup = isTopGroup;
	}

	public Integer getIsTopGlobal() {
		return isTopGlobal;
	}

	public void setIsTopGlobal(Integer isTopGlobal) {
		this.isTopGlobal = isTopGlobal;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

}