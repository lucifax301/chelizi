/**
 *
 */
package com.lili.coupon.mapper.dao;

/**
 * @author linbo
 */
public class CouponPage {
    /**
     * 页码
     */
    private int pageSize;

    /**
     * 每一页多少行数据
     */
    private int index;

    /**
     * 学员Id
     */
    private long studentId;

    /**
     * 是否已使用
     */
    private byte isUsed;

    /**
     * 是否有效
     */
    private byte isValid;

    /**
     * @param pageNum
     * @param pageSize
     * @param studentId
     * @param isUsed
     * @param isValid
     */
    public CouponPage(int pageNum, int pageSize, long studentId, boolean isUsed, boolean isValid) {
        super();
        this.setPageSize(pageSize);
        this.setIndex(pageSize * (pageNum - 1));
        this.studentId = studentId;
        this.isUsed = (byte) (isUsed == true ? 1 : 0);
        this.isValid = (byte) (isValid == true ? 1 : 0);
    }

    /**
     * @return the studentId
     */
    public long getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public byte getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(byte isUsed) {
        this.isUsed = isUsed;
    }

    public byte getIsValid() {
        return isValid;
    }

    public void setIsValid(byte isValid) {
        this.isValid = isValid;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
