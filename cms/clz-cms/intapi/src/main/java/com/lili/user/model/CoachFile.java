package com.lili.user.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 导入教练的记录实体
 * @author hughes
 *
 */
public class CoachFile implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String idnumber;

	private String name;

	private Byte sex;

	private Short age;

	private String hometown;

	private String phonenum;

	private String coachcard;

	private Integer schoolid;

	private Integer cityid;

	private Date createtime;

	private String coachcarddate;

	private String extra;

	private String creatorName;

	private String billNo;
	
	private String schoolName;
	private String cityName;

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber == null ? null : idnumber.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Byte getSex() {
		return sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public Short getAge() {
		return age;
	}

	public void setAge(Short age) {
		this.age = age;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown == null ? null : hometown.trim();
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum == null ? null : phonenum.trim();
	}

	public String getCoachcard() {
		return coachcard;
	}

	public void setCoachcard(String coachcard) {
		this.coachcard = coachcard == null ? null : coachcard.trim();
	}

	public Integer getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(Integer schoolid) {
		this.schoolid = schoolid;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public String getCoachcarddate() {
		return coachcarddate;
	}

	public void setCoachcarddate(String coachcarddate) {
		this.coachcarddate = coachcarddate;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra == null ? null : extra.trim();
	}

	@Override
	public String toString() {
		return "CoachFile [id=" + id + ", idnumber=" + idnumber + ", name="
				+ name + ", sex=" + sex + ", age=" + age + ", hometown="
				+ hometown + ", phonenum=" + phonenum + ", coachcard="
				+ coachcard + ", schoolid=" + schoolid + ", cityid=" + cityid
				+ ", createtime=" + createtime + ", coachcarddate="
				+ coachcarddate + ", extra=" + extra + ", creatorName="
				+ creatorName + ", billNo=" + billNo + "]";
	}
}
