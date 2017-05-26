/**
 * 
 */
package com.lili.pay.wallet;

/**
 * @author linbo
 *
 */
public class PerformancePage
{

    /**
     * 页码
     */
    private int pageSize;
    
    /**
     * 每一页多少行数据
     */
    private int index;

    private long coachId;

    /**
     * @param pageSize
     * @param index
     * @param coachId
     */
    public PerformancePage(int pageSize, int pageNum, long coachId)
    {
        super();
        this.pageSize = pageSize;
        this.coachId = coachId;
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
    
    
}
