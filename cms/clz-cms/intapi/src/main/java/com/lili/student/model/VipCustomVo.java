package com.lili.student.model;

import java.util.Date;
import java.io.Serializable;

import com.lili.cms.entity.BasePagedEntity;

public class VipCustomVo implements Serializable{

	/*用户id,大客户必须首先成为用户*/
	private Long studentId;
	/*冗余：申请人手机*/
	private String mobile;
	/*冗余：申请人姓名*/
	private String cname;
	/*大客户公司主键*/
	private Integer coid;
	/*工号*/
	private String cid;
	/*充值送套餐id*/
	private Integer rcid;
	/*审核通过赠送的优惠券，多张使用逗号分割。*/
	private String coupon;
	/*审核通过由于优惠券失效而缺少赠送的券，多张使用逗号分割。*/
	private String couponLack;
	/*审核状态：0待审核，1审核通过,2审核拒绝,9该客户优惠套餐已过期*/
	private Integer vstate;
	/*拒绝理由*/
	private String reason;
	/*删除状态：0代表未删除，1代表已删除*/
	private Integer isdel;
	/*创建人id*/
	private Long cuid;
	/*更新人id*/
	private Long muid;
	/*创建时间*/
	private Date ctime;
	/*更新时间*/
	private String mtime;

	private String studentName;
	private String studentPhoneNum;
	private int sex;
	private String schoolName;
	/**
	 * 学员所处的阶段
	 */
	private Integer applyexam;

	/**
	 * 学员所处的阶段状态
	 */
	private Integer applystate;

	/**
	 * 申请的车牌类型(1代表C1,2代表C2,3代表其它)
	 */
	private int applyCarType;
	/**
	 * 身份证号
	 */
	private String idNumber;

	private String flowNo;

	/**
	 * 用户状态(0-正常；1-临时封号；2-永久封号)
	 */
	private Integer state;

	  private String createUser;
	  private String updateUser;

	  public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}


	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentPhoneNum() {
		return studentPhoneNum;
	}
	public void setStudentPhoneNum(String studentPhoneNum) {
		this.studentPhoneNum = studentPhoneNum;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Integer getApplyexam() {
		return applyexam;
	}
	public void setApplyexam(Integer applyexam) {
		this.applyexam = applyexam;
	}
	public Integer getApplystate() {
		return applystate;
	}
	public void setApplystate(Integer applystate) {
		this.applystate = applystate;
	}
	public int getApplyCarType() {
		return applyCarType;
	}
	public void setApplyCarType(int applyCarType) {
		this.applyCarType = applyCarType;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getFlowNo() {
		return flowNo;
	}
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getStudentId() {
		return this.studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId=studentId;
	}
	public String getMobile() {
		return this.mobile;
	}
	public void setMobile(String mobile) {
		this.mobile=mobile;
	}
	public String getCname() {
		return this.cname;
	}
	public void setCname(String cname) {
		this.cname=cname;
	}
	public Integer getCoid() {
		return this.coid;
	}
	public void setCoid(Integer coid) {
		this.coid=coid;
	}
	public String getCid() {
		return this.cid;
	}
	public void setCid(String cid) {
		this.cid=cid;
	}
	public Integer getRcid() {
		return this.rcid;
	}
	public void setRcid(Integer rcid) {
		this.rcid=rcid;
	}
	public String getCoupon() {
		return this.coupon;
	}
	public void setCoupon(String coupon) {
		this.coupon=coupon;
	}
	public String getCouponLack() {
		return this.couponLack;
	}
	public void setCouponLack(String couponLack) {
		this.couponLack=couponLack;
	}
	public Integer getVstate() {
		return this.vstate;
	}
	public void setVstate(Integer vstate) {
		this.vstate=vstate;
	}
	public String getReason() {
		return this.reason;
	}
	public void setReason(String reason) {
		this.reason=reason;
	}
	public Integer getIsdel() {
		return this.isdel;
	}
	public void setIsdel(Integer isdel) {
		this.isdel=isdel;
	}
	public Long getCuid() {
		return this.cuid;
	}
	public void setCuid(Long cuid) {
		this.cuid=cuid;
	}
	public Long getMuid() {
		return this.muid;
	}
	public void setMuid(Long muid) {
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
		return "{studentId="+studentId+",mobile="+mobile+",cname="+cname+",coid="+coid+",cid="+cid+",rcid="+rcid+",coupon="+coupon+",couponLack="+couponLack+",vstate="+vstate+",reason="+reason+",isdel="+isdel+",cuid="+cuid+",muid="+muid+",ctime="+ctime+",mtime="+mtime+"}";
	}
}
