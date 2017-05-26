package com.lili.user.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 导入车辆的记录实体
 * @author hughes
 *
 */
public class CarFile implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String cartype;

	private Integer age;
	
	private String buyTime;

	private Byte carpower;

	private Byte drivetype;

	private String carno;

	private String coachphonenum;

	private String coachname;

	private Integer schoolid;

	private Date createtime;

	private String carengineno;

	private String carvin;

	private String extra;

    private String creatorName;

	private String billNo;
	
	private String schoolName;
	private String cityName;
	

	public String getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}

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

	public String getCartype() {
		return cartype;
	}

	public void setCartype(String cartype) {
		this.cartype = cartype == null ? null : cartype.trim();
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Byte getCarpower() {
		return carpower;
	}

	public void setCarpower(Byte carpower) {
		this.carpower = carpower;
	}

	public Byte getDrivetype() {
		return drivetype;
	}

	public void setDrivetype(Byte drivetype) {
		this.drivetype = drivetype;
	}

	public String getCarno() {
		return carno;
	}

	public void setCarno(String carno) {
		this.carno = carno == null ? null : carno.trim();
	}

	public String getCoachphonenum() {
		return coachphonenum;
	}

	public void setCoachphonenum(String coachphonenum) {
		this.coachphonenum = coachphonenum == null ? null : coachphonenum.trim();
	}

	public String getCoachname() {
		return coachname;
	}

	public void setCoachname(String coachname) {
		this.coachname = coachname == null ? null : coachname.trim();
	}

	public Integer getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(Integer schoolid) {
		this.schoolid = schoolid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getCarengineno() {
		return carengineno;
	}

	public void setCarengineno(String carengineno) {
		this.carengineno = carengineno == null ? null : carengineno.trim();
	}

	public String getCarvin() {
		return carvin;
	}

	public void setCarvin(String carvin) {
		this.carvin = carvin == null ? null : carvin.trim();
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra == null ? null : extra.trim();
	}
	
	
}
