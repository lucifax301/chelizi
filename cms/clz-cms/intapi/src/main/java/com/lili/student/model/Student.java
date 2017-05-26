package com.lili.student.model;

import java.util.Date;

import com.lili.cms.entity.BaseEntity;

public class Student extends BaseEntity {

	private static final long serialVersionUID = -1559190056424412546L;

	/**
	 * 学员ID
	 */
	private Long studentId;

	/**
	 * 身份证号
	 */
	private String idNumber;

	/**
	 * 学员姓名
	 */
	private String name;

	/**
	 * 性别
	 */
	private Byte sex;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 户籍地
	 */
	private String hometown;

	/**
	 * 学员电话
	 */
	private String phoneNum;

	/**
	 * 学员邮箱
	 */
	private String mailBox;

	/**
	 * 头像
	 */
	private String headIcon;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 科目1成绩
	 */
	private Integer course1;

	/**
	 * 科目2成绩
	 */
	private Integer course2;

	/**
	 * 科目3成绩
	 */
	private Integer course3;

	/**
	 * 科目4成绩
	 */
	private Integer course4;

	/**
	 * 练车范围
	 */
	private Integer studyDis;

	/**
	 * 注册时间
	 */
	private Date registerTime;

	/**
	 * 银行帐号
	 */
	private String bankcardNum;

	/**
	 * 银行名称
	 */
	private String bankName;

	/**
	 * 银行帐号名字
	 */
	private String bankAccName;

	/**
	 * 学员身份证正面
	 */
	private String idPhotoFront;

	/**
	 * 学员身份证反面
	 */
	private String idPhotoBack;

	/**
	 * 是否是导入进来的学员
	 */
	private Byte isImport;

	/**
	 * 导入的来源
	 */
	private String importSrc;

	/**
	 * 原系统中的学生的教练ID
	 */
	private Integer StuCoachEmpID;

	/**
	 * 申请的车牌类型
	 */
	private Byte applyCarType;
	/**
	 * 学生当前状态 0-空闲；1-上课中
	 */
	private Byte eventState;
	/**
	 * 事件详情 可能有--订单id；
	 */
	private String eventDesc;

	/**
	 * 学员身份认证状态
	 */
	private Byte checkIdState;

	/**
	 * 数据同步类型(1更新2插入)
	 */
	private Byte syncType;

	/**
	 * 数据最后一次同步时间
	 */
	private Date syncTime;

	/**
	 * 驾照编号
	 */
	private String drLicence;

	/**
	 * 学员所处的阶段状态
	 */
	private Integer applystate;
	/**
	 * 驾照图片地址
	 */
	private String drPhoto;
	/**
	 * 驾驶类型（1代表C1,2代表C2,3代表其它）
	 */
	private Byte drType;

	/**
	 * 是否同意了用户协议 0-未同意 1-已同意
	 */
	private Byte agreement;
	/**
	 * 车管所报名状态：0-未报名；1-已报名成功；2-报名审核中
	 */
	private Integer applyexam;

	/**
	 * 是否设置了支付密码- 0-未设置；1-已设置
	 */
	private Byte pwstate;
	/**
	 * 学员所属城市id
	 */
	private Integer cityId;

	private String flowNo;

	private String idNation;

	private Date idBirthday;

	private String idAddr;

	private Date idExpires;

	private String drNation;

	private String drAddr;

	private Date drFirst;

	private String applyorder;

	private Integer applyttid;

	private String drFileNo;

	private Byte checkDriveIdState;

	private String openId;

	private String extra;

	private Integer vipId;

	private Integer state;

	private Date reviveTime;
	/* 充送方案中文id */
	private String vipPackageId;
	/* 充送方案中文名称 */
	private String rcname;
	/* 充送该客户审核状态：0待审核，1审核通过,2审核拒绝，9该客户优惠套餐已过期(包含已结束和未激活) */
	private Integer vstate;

	// 关联班别id
	private Integer packageId;
	// 关联班别时间
	private Date relevanceTime;

	private String school;

	/**
	 * 职业
	 */
	private String career;
	/**
	 * 跟进客服
	 */
	private String cs;

	private int loginCount;

	/**
	 * 第一次登录时间
	 */
	private Date firstLogin;

	/**
	 * 最后一次登录时间
	 */
	private Date lastLogin;

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String getCs() {
		return cs;
	}

	public void setCs(String cs) {
		this.cs = cs;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public Byte getIsImport() {
		return isImport;
	}

	public void setIsImport(Byte isImport) {
		this.isImport = isImport;
	}

	public Byte getEventState() {
		return eventState;
	}

	public void setEventState(Byte eventState) {
		this.eventState = eventState;
	}

	public Byte getCheckIdState() {
		return checkIdState;
	}

	public void setCheckIdState(Byte checkIdState) {
		this.checkIdState = checkIdState;
	}

	public Byte getSyncType() {
		return syncType;
	}

	public void setSyncType(Byte syncType) {
		this.syncType = syncType;
	}

	public String getApplyorder() {
		return applyorder;
	}

	public void setApplyorder(String applyorder) {
		this.applyorder = applyorder;
	}

	public Integer getApplyttid() {
		return applyttid;
	}

	public void setApplyttid(Integer applyttid) {
		this.applyttid = applyttid;
	}

	public Byte getApplyCarType() {
		return applyCarType;
	}

	public void setApplyCarType(Byte applyCarType) {
		this.applyCarType = applyCarType;
	}

	public String getIdNation() {
		return idNation;
	}

	public void setIdNation(String idNation) {
		this.idNation = idNation;
	}

	public Date getIdBirthday() {
		return idBirthday;
	}

	public void setIdBirthday(Date idBirthday) {
		this.idBirthday = idBirthday;
	}

	public String getIdAddr() {
		return idAddr;
	}

	public void setIdAddr(String idAddr) {
		this.idAddr = idAddr;
	}

	public Date getIdExpires() {
		return idExpires;
	}

	public void setIdExpires(Date idExpires) {
		this.idExpires = idExpires;
	}

	public String getDrNation() {
		return drNation;
	}

	public void setDrNation(String drNation) {
		this.drNation = drNation;
	}

	public String getDrAddr() {
		return drAddr;
	}

	public void setDrAddr(String drAddr) {
		this.drAddr = drAddr;
	}

	public Date getDrFirst() {
		return drFirst;
	}

	public void setDrFirst(Date drFirst) {
		this.drFirst = drFirst;
	}

	public String getDrFileNo() {
		return drFileNo;
	}

	public void setDrFileNo(String drFileNo) {
		this.drFileNo = drFileNo;
	}

	public Byte getCheckDriveIdState() {
		return checkDriveIdState;
	}

	public void setCheckDriveIdState(Byte checkDriveIdState) {
		this.checkDriveIdState = checkDriveIdState;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public Integer getVipId() {
		return vipId;
	}

	public void setVipId(Integer vipId) {
		this.vipId = vipId;
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

	public Integer getApplystate() {
		return applystate;
	}

	public void setApplystate(Integer applystate) {
		this.applystate = applystate;
	}

	public String getDrLicence() {
		return drLicence;
	}

	public void setDrLicence(String drLicence) {
		this.drLicence = drLicence;
	}

	public String getDrPhoto() {
		return drPhoto;
	}

	public void setDrPhoto(String drPhoto) {
		this.drPhoto = drPhoto;
	}

	public String getEventDesc() {
		return eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getMailBox() {
		return mailBox;
	}

	public void setMailBox(String mailBox) {
		this.mailBox = mailBox;
	}

	public String getHeadIcon() {
		return headIcon;
	}

	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getCourse1() {
		return course1;
	}

	public void setCourse1(Integer course1) {
		this.course1 = course1;
	}

	public Integer getCourse2() {
		return course2;
	}

	public void setCourse2(Integer course2) {
		this.course2 = course2;
	}

	public Integer getCourse3() {
		return course3;
	}

	public void setCourse3(Integer course3) {
		this.course3 = course3;
	}

	public Integer getCourse4() {
		return course4;
	}

	public void setCourse4(Integer course4) {
		this.course4 = course4;
	}

	public Integer getStudyDis() {
		return studyDis;
	}

	public void setStudyDis(Integer studyDis) {
		this.studyDis = studyDis;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getBankcardNum() {
		return bankcardNum;
	}

	public void setBankcardNum(String bankcardNum) {
		this.bankcardNum = bankcardNum;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccName() {
		return bankAccName;
	}

	public void setBankAccName(String bankAccName) {
		this.bankAccName = bankAccName;
	}

	public String getIdPhotoFront() {
		return idPhotoFront;
	}

	public void setIdPhotoFront(String idPhotoFront) {
		this.idPhotoFront = idPhotoFront;
	}

	public String getIdPhotoBack() {
		return idPhotoBack;
	}

	public void setIdPhotoBack(String idPhotoBack) {
		this.idPhotoBack = idPhotoBack;
	}

	public String getImportSrc() {
		return importSrc;
	}

	public void setImportSrc(String importSrc) {
		this.importSrc = importSrc;
	}

	public Integer getStuCoachEmpID() {
		return StuCoachEmpID;
	}

	public void setStuCoachEmpID(Integer stuCoachEmpID) {
		StuCoachEmpID = stuCoachEmpID;
	}

	public Date getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}

	public Byte getSex() {
		return sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public Byte getDrType() {
		return drType;
	}

	public void setDrType(Byte drType) {
		this.drType = drType;
	}

	public Byte getAgreement() {
		return agreement;
	}

	public void setAgreement(Byte agreement) {
		this.agreement = agreement;
	}

	public Byte getPwstate() {
		return pwstate;
	}

	public void setPwstate(Byte pwstate) {
		this.pwstate = pwstate;
	}

	public Integer getApplyexam() {
		return applyexam;
	}

	public void setApplyexam(Integer applyexam) {
		this.applyexam = applyexam;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
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

	public Date getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(Date firstLogin) {
		this.firstLogin = firstLogin;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

}
