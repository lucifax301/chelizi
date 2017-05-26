package com.lili.bbs.dto;

import java.io.Serializable;
import java.util.Date;

import com.lili.common.util.MyRowBounds;

public class BbsBlacklist implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4867790687748457916L;

	private Integer id;

    private Long userId;

    private Integer userType;
    
    private String phoneNum;
    
    private String name;

    private Date muteTime;

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

    public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getMuteTime() {
        return muteTime;
    }

    public void setMuteTime(Date muteTime) {
        this.muteTime = muteTime;
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
}