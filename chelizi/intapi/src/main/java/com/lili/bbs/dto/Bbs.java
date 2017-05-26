package com.lili.bbs.dto;


import java.io.Serializable;
import java.util.Date;

import com.lili.common.util.MyRowBounds;

public class Bbs implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8653734655303867074L;

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
        this.userName = userName == null ? null : userName.trim();
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon == null ? null : headIcon.trim();
    }

    public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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
        this.topicName = topicName == null ? null : topicName.trim();
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