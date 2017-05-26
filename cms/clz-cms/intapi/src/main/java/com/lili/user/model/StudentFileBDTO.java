package com.lili.user.model;

import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

/**
 * 导入学员的记录实体
 * @author hughes
 *
 */
public class StudentFileBDTO extends BasePagedEntity{

	private static final long serialVersionUID = 1L;
	  private Integer id;

	    private String flowno;

	    private String stucoachname;

	    private String idnumber;

	    private String name;

	    private Byte sex;

	    private Short age;

	    private String hometown;

	    private String phonenum;

	    private Short course1;

	    private Short course2;

	    private Short course3;

	    private Short course4;

	    private String applycartype;

	    private Integer schoolid;

	    private Byte drtype;

	    private Integer cityid;

	    private Date createtime;

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

	    public String getFlowno() {
	        return flowno;
	    }

	    public void setFlowno(String flowno) {
	        this.flowno = flowno == null ? null : flowno.trim();
	    }

	    public String getStucoachname() {
	        return stucoachname;
	    }

	    public void setStucoachname(String stucoachname) {
	        this.stucoachname = stucoachname == null ? null : stucoachname.trim();
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

	    public Short getCourse1() {
	        return course1;
	    }

	    public void setCourse1(Short course1) {
	        this.course1 = course1;
	    }

	    public Short getCourse2() {
	        return course2;
	    }

	    public void setCourse2(Short course2) {
	        this.course2 = course2;
	    }

	    public Short getCourse3() {
	        return course3;
	    }

	    public void setCourse3(Short course3) {
	        this.course3 = course3;
	    }

	    public Short getCourse4() {
	        return course4;
	    }

	    public void setCourse4(Short course4) {
	        this.course4 = course4;
	    }

	    public String getApplycartype() {
	        return applycartype;
	    }

	    public void setApplycartype(String applycartype) {
	        this.applycartype = applycartype == null ? null : applycartype.trim();
	    }

	    public Integer getSchoolid() {
	        return schoolid;
	    }

	    public void setSchoolid(Integer schoolid) {
	        this.schoolid = schoolid;
	    }

	    public Byte getDrtype() {
	        return drtype;
	    }

	    public void setDrtype(Byte drtype) {
	        this.drtype = drtype;
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

	    public String getExtra() {
	        return extra;
	    }

	    public void setExtra(String extra) {
	        this.extra = extra == null ? null : extra.trim();
	    }

}
