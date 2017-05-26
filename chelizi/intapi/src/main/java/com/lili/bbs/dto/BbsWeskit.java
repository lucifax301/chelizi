package com.lili.bbs.dto;

import java.io.Serializable;
import java.util.Date;

import com.lili.common.util.MyRowBounds;

public class BbsWeskit implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3527713814938322490L;

	private Integer id;

    private Long userId;

    private Integer userType;
    
    private String userName;

    private Integer channel;
    
    private Integer sex;

    private String headIcon;

    private String cityName;

    private Integer bbsNum;

    private Integer replyNum;

    private Integer isDel;

    private Date ctime;

    private Long cuid;

    private Date mtime;

    private Long muid;
    
    private Integer bbsId;
    
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

	public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
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
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public Integer getBbsNum() {
        return bbsNum;
    }

    public void setBbsNum(Integer bbsNum) {
        this.bbsNum = bbsNum;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
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

    public Long getCuid() {
        return cuid;
    }

    public void setCuid(Long cuid) {
        this.cuid = cuid;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public Long getMuid() {
        return muid;
    }

    public void setMuid(Long muid) {
        this.muid = muid;
    }

	public Integer getBbsId() {
		return bbsId;
	}

	public void setBbsId(Integer bbsId) {
		this.bbsId = bbsId;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

}