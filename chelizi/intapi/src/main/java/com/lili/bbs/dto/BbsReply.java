package com.lili.bbs.dto;

import java.io.Serializable;
import java.util.Date;

import com.lili.common.util.MyRowBounds;

public class BbsReply implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -347664113812213194L;

	private Integer id;

    private Integer bbsId;

    private Long userId;

    private Integer userType;
    
    private Integer channel;

    private String userName;

    private String headIcon;
    
	private Integer cityId;
	
	private String cityName;

    private Integer byreId;

    private Long byreUserId;

    private String byreUserName;

    private Integer byreUserType;

    private String byreContent;

    private String replyContent;
    
    private String content;

    private Integer status;
    
    private Integer isNotice;
    
    private Integer praise;

    private Long bbsUserId;

    private Integer bbsUserType;

    private String bbsUserName;
    
    private Date ctime;

    private Long cuid;

    private Date mtime;

    private Long muid;

    private Integer isDel;
    
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

	public Integer getByreId() {
        return byreId;
    }

    public void setByreId(Integer byreId) {
        this.byreId = byreId;
    }

    public Long getByreUserId() {
        return byreUserId;
    }

    public void setByreUserId(Long byreUserId) {
        this.byreUserId = byreUserId;
    }

    public String getByreUserName() {
        return byreUserName;
    }

    public void setByreUserName(String byreUserName) {
        this.byreUserName = byreUserName == null ? null : byreUserName.trim();
    }

    public Integer getByreUserType() {
        return byreUserType;
    }

    public void setByreUserType(Integer byreUserType) {
        this.byreUserType = byreUserType;
    }

    public String getByreContent() {
        return byreContent;
    }

    public void setByreContent(String byreContent) {
        this.byreContent = byreContent == null ? null : byreContent.trim();
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent == null ? null : replyContent.trim();
    }

    public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsNotice() {
		return isNotice;
	}

	public void setIsNotice(Integer isNotice) {
		this.isNotice = isNotice;
	}

	public Integer getPraise() {
		return praise;
	}

	public void setPraise(Integer praise) {
		this.praise = praise;
	}

    public Long getBbsUserId() {
		return bbsUserId;
	}

	public void setBbsUserId(Long bbsUserId) {
		this.bbsUserId = bbsUserId;
	}

	public Integer getBbsUserType() {
		return bbsUserType;
	}

	public void setBbsUserType(Integer bbsUserType) {
		this.bbsUserType = bbsUserType;
	}

	public String getBbsUserName() {
		return bbsUserName;
	}

	public void setBbsUserName(String bbsUserName) {
		this.bbsUserName = bbsUserName;
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

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

}