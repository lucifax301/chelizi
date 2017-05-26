package com.lili.order.vo;

import java.util.Date;
import java.io.Serializable;

public class CancelReasonVo implements Serializable {

/*取消原因主键*/
  private Integer crid;
/*取消原因*/
  private String reason;
/*标签类型：1代表教练取消的原因，2代表学员取消的原因*/
  private Integer utype;
/*删除状态：0代表未删除，1代表已删除*/
  private Integer isdel=0;
/*创建人id*/
  private Integer cuid;
/*更新人id*/
  private Integer muid;
/*创建时间*/
  private Date ctime;
/*更新时间*/
  private String mtime;


  public Integer getCrid() {
    return this.crid;
  }
  public void setCrid(Integer crid) {
    this.crid=crid;
  }
  public String getReason() {
    return this.reason;
  }
  public void setReason(String reason) {
    this.reason=reason;
  }
  public Integer getUtype() {
    return this.utype;
  }
  public void setUtype(Integer utype) {
    this.utype=utype;
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
		return "{crid="+crid+",reason="+reason+",utype="+utype+",isdel="+isdel+",cuid="+cuid+",muid="+muid+",ctime="+ctime+",mtime="+mtime+"}";
  }
}
