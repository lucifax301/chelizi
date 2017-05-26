package com.lili.order.vo;

import java.util.Date;
import java.io.Serializable;

public class CommonPriceVo implements Serializable {

/*通用单价表主键*/
  private Integer upid;
/*定价的城市id*/
  private Integer cityId;
/*定价科目id*/
  private Integer courseId;
/*定价的教练等级*/
  private Integer colid;
/*定价的汽车等级*/
  private Integer calid;
/*汽车档位类型*/
  private Integer dftype;
/*定价开始时间，优先级从大到小分别有3种格式，分别是：YYYY-MM-dd-hh, MM-dd-hh和ww-hh*/
  private String tstart;
/*定价开始时间，同上有3种格式,并且每条记录必须保持和tstart为同一种格式*/
  private String tend;
/*价格，务必注意单位是分*/
  private Integer price;
/*与价格对应的补贴*/
  private Integer allowance;
/*审核状态:0代表未审核，1代表已审核*/
  private Integer verify;
/*删除状态：0代表未删除，1代表已删除*/
  private Integer isdel;
/*创建人id*/
  private Integer cuid;
/*更新人id*/
  private Integer muid;
/*创建时间*/
  private Date ctime;
/*更新时间*/
  private String mtime;


  public Integer getUpid() {
    return this.upid;
  }
  public void setUpid(Integer upid) {
    this.upid=upid;
  }
  public Integer getCityId() {
    return this.cityId;
  }
  public void setCityId(Integer cityId) {
    this.cityId=cityId;
  }
  public Integer getCourseId() {
    return this.courseId;
  }
  public void setCourseId(Integer courseId) {
    this.courseId=courseId;
  }
  public Integer getColid() {
    return this.colid;
  }
  public void setColid(Integer colid) {
    this.colid=colid;
  }
  public Integer getCalid() {
    return this.calid;
  }
  public void setCalid(Integer calid) {
    this.calid=calid;
  }
  public Integer getDftype() {
    return this.dftype;
  }
  public void setDftype(Integer dftype) {
    this.dftype=dftype;
  }
  public String getTstart() {
    return this.tstart;
  }
  public void setTstart(String tstart) {
    this.tstart=tstart;
  }
  public String getTend() {
    return this.tend;
  }
  public void setTend(String tend) {
    this.tend=tend;
  }
  public Integer getPrice() {
    return this.price;
  }
  public void setPrice(Integer price) {
    this.price=price;
  }
  public Integer getAllowance() {
    return this.allowance;
  }
  public void setAllowance(Integer allowance) {
    this.allowance=allowance;
  }
  public Integer getVerify() {
    return this.verify;
  }
  public void setVerify(Integer verify) {
    this.verify=verify;
  }
  public Integer getIsdel() {
    return this.isdel;
  }
  public void setIsdel(Integer isdel) {
    this.isdel=isdel;
  }
  public Integer getCuid() {
    return this.cuid;
  }
  public void setCuid(Integer cuid) {
    this.cuid=cuid;
  }
  public Integer getMuid() {
    return this.muid;
  }
  public void setMuid(Integer muid) {
    this.muid=muid;
  }
  public Date getCtime() {
    return this.ctime;
  }
  public void setCtime(Date ctime) {
    this.ctime=ctime;
  }
  public String getMtime() {
    return this.mtime;
  }
  public void setMtime(String mtime) {
    this.mtime=mtime;
  }
  public String toString() {
		return "{upid="+upid+",cityId="+cityId+",courseId="+courseId+",colid="+colid+",calid="+calid+",dftype="+dftype+",tstart="+tstart+",tend="+tend+",price="+price+",allowance="+allowance+",verify="+verify+",isdel="+isdel+",cuid="+cuid+",muid="+muid+",ctime="+ctime+",mtime="+mtime+"}";
  }
}
