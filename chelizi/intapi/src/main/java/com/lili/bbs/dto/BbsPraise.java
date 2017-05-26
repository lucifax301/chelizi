package com.lili.bbs.dto;

import java.io.Serializable;
import java.util.Date;

import com.lili.common.util.MyRowBounds;

public class BbsPraise implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5156367358893988415L;

	private Integer id;

    private Integer bbsId;

    private Long userId;

    private Integer userType;
    
    private Integer channel;

    private String userName;

    private String headIcon;

    private Long praiseUserId;

    private Integer praiseUserType;
    
    private String praiseContent;
    
    private Integer isNotice;

    private Integer isDel;

    private Date ctime;

    private Date mtime;
    
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

    public Integer getBbsId() {
        return bbsId;
    }

    public void setBbsId(Integer bbsId) {
        this.bbsId = bbsId;
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

    public Long getPraiseUserId() {
        return praiseUserId;
    }

    public void setPraiseUserId(Long praiseUserId) {
        this.praiseUserId = praiseUserId;
    }

    public Integer getPraiseUserType() {
        return praiseUserType;
    }

    public void setPraiseUserType(Integer praiseUserType) {
        this.praiseUserType = praiseUserType;
    }

    public String getPraiseContent() {
		return praiseContent;
	}

	public void setPraiseContent(String praiseContent) {
		this.praiseContent = praiseContent;
	}

	public Integer getIsNotice() {
		return isNotice;
	}

	public void setIsNotice(Integer isNotice) {
		this.isNotice = isNotice;
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

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}
}