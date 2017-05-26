/**
 * 
 */
package com.lili.pay.wallet;

/**
 * @author linbo
 *
 */
public class MoneyPage
{
    /**
     * 页码
     */
    private int pageSize;
    
    /**
     * 每一页多少行数据
     */
    private int index;
    
    //用户ID
    private long userId;
    //用户类型
    private int userType;
    
    
    /**
     * @param pageSize
     * @param index
     * @param userId
     * @param userType
     */
    public MoneyPage(int pageSize, int pageNum, long userId, int userType)
    {
        super();
        this.pageSize = pageSize;
        this.userId = userId;
        this.userType = userType;
        this.setPageSize(pageSize);
        this.setIndex(pageSize * (pageNum - 1));
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
     * @return the index
     */
    public int getIndex()
    {
        return index;
    }
    /**
     * @param index the index to set
     */
    public void setIndex(int index)
    {
        this.index = index;
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
    public void setUserId(long userId)
    {
        this.userId = userId;
    }
    /**
     * @return the userType
     */
    public int getUserType()
    {
        return userType;
    }
    /**
     * @param userType the userType to set
     */
    public void setUserType(int userType)
    {
        this.userType = userType;
    }
    
}
