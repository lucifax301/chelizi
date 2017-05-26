package com.lili.bbs.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.lili.bbs.dto.BbsPraise;
import com.lili.bbs.dto.BbsReply;

public class BbsDetailMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7960161660816118204L;

	private Integer id;

	private Long userId;

	private Integer userType;

	private String userName;

	private String headIcon;
	
	private Integer channel;

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

	private Integer status;

	private String title;

	private String content;
	
	private String pic;

	private String ext;
	
	private Date ctime;

	private Integer topLevel;

	private Integer isTopGroup;

	private Integer isTopGlobal;

	private List<BbsPraise> praiseList;
	
	private List<BbsReply> replyList;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
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

	public Integer getInform() {
		return inform;
	}

	public void setInform(Integer inform) {
		this.inform = inform;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public List<BbsPraise> getPraiseList() {
		return praiseList;
	}

	public void setPraiseList(List<BbsPraise> praiseList) {
		this.praiseList = praiseList;
	}

	public List<BbsReply> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<BbsReply> replyList) {
		this.replyList = replyList;
	}
}