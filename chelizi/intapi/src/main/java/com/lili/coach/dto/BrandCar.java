package com.lili.coach.dto;

import java.io.Serializable;

/**
 * 车辆信息表
 * @author yu.y
 *
 */
public class BrandCar implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3585700161796129358L;

	/**
	 * 主键id
	 */
    private Integer seqid;

    /**
     * 品牌编码
     */
    private String brandno;

    /**
     * 品牌名称
     */
    private String brandname;

    /**
     * 汽车编码
     */
    private String carno;

    /**
     * 汽车名称
     */
    private String carname;

    /**
     * 是否常用 0否 1是
     */
    private Byte iscommon;

    /**
     * 常用车辆排序
     */
    private Integer seqnum;

    public Integer getSeqid() {
        return seqid;
    }

    public void setSeqid(Integer seqid) {
        this.seqid = seqid;
    }

    public String getBrandno() {
        return brandno;
    }

    public void setBrandno(String brandno) {
        this.brandno = brandno == null ? null : brandno.trim();
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname == null ? null : brandname.trim();
    }

    public String getCarno() {
        return carno;
    }

    public void setCarno(String carno) {
        this.carno = carno == null ? null : carno.trim();
    }

    public String getCarname() {
        return carname;
    }

    public void setCarname(String carname) {
        this.carname = carname == null ? null : carname.trim();
    }

    public Byte getIscommon() {
        return iscommon;
    }

    public void setIscommon(Byte iscommon) {
        this.iscommon = iscommon;
    }

    public Integer getSeqnum() {
        return seqnum;
    }

    public void setSeqnum(Integer seqnum) {
        this.seqnum = seqnum;
    }
}