/**
 * 
 */
package com.lili.coach.mapper.dao;

import java.util.Date;

/**
 * @author yuy
 *
 */
public class NoticePage
{
    /**
     * 页码
     */
    private int pageSize;
    
    /**
     * 每一页多少行数据
     */
    private int pageNo;
    
    /**
     * 用户Id
     */
    private Long userId;
    
    /**
     * 通知对象
     */
    private Integer userType;
    
    /**
     * 消息时间
     */
    private Date time;
    

    /**
     * @param pageNum
     * @param pageSize
     * @param userId
     */
    public NoticePage(int pageNum, int pageSize, Long userId, Integer userType,String time)
    {
        super();
        this.setPageSize(pageSize);
        this.setPageNo(pageSize * (pageNum - 1));
        this.userId = userId;
        this.userType = userType;
        if(null != time && !"".equals(time.trim())){
        	try{
        		this.time = new Date(Long.parseLong(time));
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	
        }
    }
    
    /**
     * @return the userId
     */
    public long getUserId()
    {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    /**
     * @return the pageNo
     */
    public int getPageNo()
    {
        return pageNo;
    }

    /**
     * @param pageNo the pageNo to set
     */
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize()
    {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    /**
     * @return the userType
     */
	public Integer getUserType() {
		return userType;
	}

    /**
     * @param userType the userType to set
     */
	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
