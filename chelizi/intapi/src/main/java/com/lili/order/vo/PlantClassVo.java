package com.lili.order.vo;

import java.util.Date;
import java.io.Serializable;

public class PlantClassVo implements Serializable {

	/* 订单id(全网唯一，用UID) */
	private String orderId;
	/* 排班主键 */
	private Integer ccid;
	/* 冗余：教练id */
	private Long coachId;
	/* 冗余：学员id */
	private Long studentId;
	/* 冗余：下单时间 */
	private Date gtime;
	/* 冗余：学生姓名 */
	private String stuName;
	/* 冗余：学生头像 */
	private String stuImg;
	/* 冗余：学生手机号码 */
	private String stuMobile;
	/* 取消状态：0代表正常，1代表已经取消 */
	private Integer isdel = 0;
/*预约价格，单位分*/
  private Integer price;

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getCcid() {
		return this.ccid;
	}

	public void setCcid(Integer ccid) {
		this.ccid = ccid;
	}

	public Long getCoachId() {
		return this.coachId;
	}

	public void setCoachId(Long coachId) {
		this.coachId = coachId;
	}

	public Long getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Date getGtime() {
		return this.gtime;
	}

	public void setGtime(Date gtime) {
		this.gtime = gtime;
	}

	public String getStuName() {
		return this.stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getStuImg() {
		return this.stuImg;
	}

	public void setStuImg(String stuImg) {
		this.stuImg = stuImg;
	}

	public String getStuMobile() {
		return this.stuMobile;
	}

	public void setStuMobile(String stuMobile) {
		this.stuMobile = stuMobile;
	}

	public Integer getIsdel() {
		return this.isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
  public Integer getPrice() {
    return this.price;
  }
  public void setPrice(Integer price) {
    this.price=price;
  }
  public String toString() {
		return "{orderId="+orderId+",ccid="+ccid+",coachId="+coachId+",studentId="+studentId+",gtime="+gtime+",stuName="+stuName+",stuImg="+stuImg+",stuMobile="+stuMobile+",isdel="+isdel+",price="+price+"}";
  }
}
