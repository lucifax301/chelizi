/**
 * 
 */
package com.lili.student.mapper.dao;

/**
 * @author yuy
 *
 */
public class StudentPage
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
    private long coachId;
    

    /**
     * @param pageNum
     * @param pageSize
     * @param coachId
     */
    public StudentPage(int pageNum, int pageSize, long coachId)
    {
        super();
        this.setPageSize(pageSize);
        this.setPageNo(pageSize * (pageNum - 1));
        this.coachId = coachId;
    }
    
    /**
     * @return the coachId
     */
    public long getCoachId()
    {
        return coachId;
    }

    /**
     * @param coachId the coachId to set
     */
    public void setCoachId(long coachId)
    {
        this.coachId = coachId;
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
}
