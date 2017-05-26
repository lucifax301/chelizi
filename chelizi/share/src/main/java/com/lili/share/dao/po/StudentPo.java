package com.lili.share.dao.po;

import java.io.Serializable;
import java.util.Date;

public class StudentPo implements Serializable{

	/**
	 * 
	 */
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
	private String  name;
	
	/**
	 * 性别
	 */
	private Integer sex;
	
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
	private Integer isImport;
	
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
	private int applyCarType;
	/**
	 * 学生当前状态 0-空闲；1-上课中
	 */
	private Integer eventState;
	/**
	 * 事件详情 可能有--订单id；
	 */
	private String eventDesc;
	
	/**
	 * 学员身份认证状态
	 */
	private Integer checkIdState;
	
	/**
	 * 驾校ID
	 */
	private Integer schoolId;
	
	/**
	 * 数据同步类型(1更新2插入)
	 */
	private Integer syncType;
	
	/**
	 * 数据最后一次同步时间
	 */
	private Date syncTime;
	
	/**
	 * 驾照编号
	 */
	private String drLicence;
	/**
	 * 驾照图片地址
	 */
	private String drPhoto;
	/**
	 * 驾驶类型（1代表C1,2代表C2,3代表其它）
	 */
	private Integer drType;
	
	/**
	 * 是否同意了用户协议 0-未同意 1-已同意
	 */
	private Integer agreement;
	/**
	 * 车管所报名状态：0-未报名；1-已报名成功；2-报名审核中
	 */
	private Integer applyexam;
	
	/**
	 * 是否设置了支付密码- 0-未设置；1-已设置
	 */
	private Integer pwstate;
	/**
	 * 学员所属城市id
	 */
	private Integer cityId;

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

	public Integer getDrType() {
		return drType;
	}

	public void setDrType(Integer drType) {
		this.drType = drType;
	}

	public Integer getEventState() {
		return eventState;
	}

	public void setEventState(Integer eventState) {
		this.eventState = eventState;
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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
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

	public Integer getIsImport() {
		return isImport;
	}

	public void setIsImport(Integer isImport) {
		this.isImport = isImport;
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


	public int getApplyCarType() {
		return applyCarType;
	}

	public void setApplyCarType(int applyCarType) {
		this.applyCarType = applyCarType;
	}

	public Integer getCheckIdState() {
		return checkIdState;
	}

	public void setCheckIdState(Integer checkIdState) {
		this.checkIdState = checkIdState;
	}

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getSyncType() {
		return syncType;
	}

	public void setSyncType(Integer syncType) {
		this.syncType = syncType;
	}

	public Date getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}

	public Integer getAgreement() {
		return agreement;
	}

	public void setAgreement(Integer agreement) {
		this.agreement = agreement;
	}

	public Integer getApplyexam() {
		return applyexam;
	}

	public void setApplyexam(Integer applyexam) {
		this.applyexam = applyexam;
	}

	public Integer getPwstate() {
		return pwstate;
	}

	public void setPwstate(Integer pwstate) {
		this.pwstate = pwstate;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
}
