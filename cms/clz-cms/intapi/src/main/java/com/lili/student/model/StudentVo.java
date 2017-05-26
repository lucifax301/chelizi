package com.lili.student.model;

import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

public class StudentVo extends BasePagedEntity{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 学员ID
	 */
	private Long studentId;

	private Long cityId;
	
	private String cityName;
	/**
	 * 教练ID
	 */
	private Long coachId;
	
	/**
	 * 身份证号
	 */
	private String idNumber;
	
	/**
	 * 学员姓名
	 */
	private String  name;
	
	/**
	 * 性别(0,女；1，男；2，未知)
	 */
	private Byte sex;
	
	/**
	 * 年龄
	 */
	private Integer age;
	
	/**
	 * 学员电话
	 */
	private String phoneNum;
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
	private String applyCarType;
	
	private String coachName;
	private String schoolName;
	
	private Integer applyttid;
	
	/**
	 * 查询驾校分配情况时的报名包名
	 */
	private String packageName;
	
	private String region;

    private Byte isImport;

	private Date ctime;

	/**
	 * 原本用来记录从驾校导入的时间,目前还用作喱喱学员分配驾校的时间
	 */
	private Date syncTime;
	
	private String flowNo;
    
    private Integer state;
    
    private Date reviveTime;
    
    private Integer vipId;

    /*充送方案中文id*/
    private String vipPackageId;
    /*充送方案中文名称*/
    private String rcname;
    /*充送该客户审核状态：0待审核，1审核通过,2审核拒绝，9该客户优惠套餐已过期(包含已结束和未激活)*/
    private Integer vstate;
    
    /*大客户公司名称*/
    private String company; 
    
    private String cid;
    
    private String enrollCity;
    
	//关联班别id
	private Integer packageId;
	//关联班别时间
	private Date relevanceTime;
    
	public Integer getVipId() {
		return vipId;
	}
	public void setVipId(Integer vipId) {
		this.vipId = vipId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Long getCoachId() {
		return coachId;
	}
	public void setCoachId(Long coachId) {
		this.coachId = coachId;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Byte getSex() {
		return sex;
	}
	public void setSex(Byte sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
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
	public String getApplyCarType() {
		return applyCarType;
	}
	public void setApplyCarType(String applyCarType) {
		this.applyCarType = applyCarType;
	}
	public String getCoachName() {
		return coachName;
	}
	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Integer getApplyttid() {
		return applyttid;
	}
	public void setApplyttid(Integer applyttid) {
		this.applyttid = applyttid;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public Byte getIsImport() {
		return isImport;
	}
	public void setIsImport(Byte isImport) {
		this.isImport = isImport;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Date getSyncTime() {
		return syncTime;
	}
	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
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
	public Date getReviveTime() {
		return reviveTime;
	}
	public void setReviveTime(Date reviveTime) {
		this.reviveTime = reviveTime;
	}
	public String getVipPackageId() {
		return vipPackageId;
	}
	public void setVipPackageId(String vipPackageId) {
		this.vipPackageId = vipPackageId;
	}
	public String getRcname() {
		return rcname;
	}
	public void setRcname(String rcname) {
		this.rcname = rcname;
	}
	public Integer getVstate() {
		return vstate;
	}
	public void setVstate(Integer vstate) {
		this.vstate = vstate;
	}
	
	public enum vStateEnum{  
		/**
		 * 0待审核，1审核通过,2审核拒绝，9该客户优惠套餐已过期
		 */
		unaudit("待审核",0),passed("审核通过",1),refused("审核拒绝",2),notover("审核但未激活",8),over("该客户优惠套餐已过期",9);
		
        private String name;
        private int index;

        private vStateEnum(String name, int index) {
            this.name = name;
            this.index = index;
        }
        // 普通方法
        public static String getName(int index) {
            for (vStateEnum c : vStateEnum.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }
        
        // get set 方法
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
	}

	public enum StateEnum{  
		/**
		 * 0-正常；1-临时封号；2-永久封号
		 */
		normal("正常",0),passed("临时封号",1),refused("永久封号",2);
		
        private String name;
        private int index;

        private StateEnum(String name, int index) {
            this.name = name;
            this.index = index;
        }
        // 普通方法
        public static String getName(int index) {
            for (StateEnum c : StateEnum.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }
        
        // get set 方法
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
	}

	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getEnrollCity() {
		return enrollCity;
	}
	public void setEnrollCity(String enrollCity) {
		this.enrollCity = enrollCity;
	}
	
	public Integer getPackageId() {
		return packageId;
	}
	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}
	public Date getRelevanceTime() {
		return relevanceTime;
	}
	public void setRelevanceTime(Date relevanceTime) {
		this.relevanceTime = relevanceTime;
	}
	
	
}
