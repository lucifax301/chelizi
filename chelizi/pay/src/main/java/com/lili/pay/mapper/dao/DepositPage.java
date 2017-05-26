/**
 * 
 */
package com.lili.pay.mapper.dao;

/**
 * @author yuy
 *
 */
public class DepositPage
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
     * 教练Id
     */
    private long userId;
    
    private int userType;
    

    /**
     * @param pageNo
     * @param pageSize
     * @param userId
     */
    public DepositPage(int pageNo, int pageSize, long userId, int userType)
    {
        super();
        this.setPageSize(pageSize);
        this.setPageNo(pageSize * (pageNo - 1));
        this.userId = userId;
        this.userType = userType;
    }
    
    /**
     * @return the coachId
     */
    public long getUserId()
    {
        return userId;
    }

    /**
     * @param userId the coachId to set
     */
    public void setUserId(long userId)
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

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}
}
