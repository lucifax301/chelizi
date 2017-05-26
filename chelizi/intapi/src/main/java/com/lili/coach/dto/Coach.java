package com.lili.coach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 教练实体
 * @author yu.y
 *
 */
public class Coach implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3258929791543735074L;
	
	/**
	 * 教练id
	 */
	private Long coachId;
	
	/**
	 * 驾校id
	 */
	private Integer schoolId;
	
	/**
	 * 驾校名称：注册教练专属
	 */
	private String schoolName;
	
	/**
	 * 教练车id
	 */
	private Integer coachCarId;
	
	/**
	 * 驾照编号
	 */
	private String drLicence;
	
	/**
	 * 教练注册所有城市
	 */
	private Integer cityId;
	
	/**
	 * 注册城市名称
	 */
	private String cityName;
	
	/**
	 * 教练等级
	 */
	private Integer coachLevel;
	
	/**
	 * 身份证号
	 */
	private String idNumber;
	
	/**
	 * 名字
	 */
	private String name;
	
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
	 * 电话号码
	 */
	private String phoneNum;
	
	/**
	 * 邮箱
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
	 * 可教的课程id
	 */
	private String courses;
	
	/**
	 * 可教的课程描述
	 */
	private String coursesDesc;
	
	/**
	 * 接单距离
	 */
	private Integer acceptOrderDis;
	
	/**
	 * 业绩
	 */
	private Integer performance;
	
	/**
	 * 教练状态
	 */
	private Integer wstate;
	
	/**
	 * 注册时间
	 */
	private Date registerTime;
	
	/**
	 * 教练证号码
	 */
	private String coachCard;
	
	/**
	 * 教练证有效期
	 */
	private String coachCardDate;
	
	/**
	 * 银行账号
	 */
	private String bankcardNum;
	
	/**
	 * 银行名称
	 */
	private String bankName;
	
	/**
	 * 银行帐户名称
	 */
	private String bankAccName;
	
	/**
	 * 身份证正面照
	 */
	private String idPhotoFront;
	
	/**
	 * 身份证反面照
	 */
	private String idPhotoBack;
	
	/**
	 * 是否是导入进来的教练
	 */
	private Integer isImport;
	
	/**
	 * 导入的来源
	 */
	private String importSrc;
	
	/**
	 * 原系统中教练ID
	 */
	private Integer StuCoachID;
	
	/**
	 * 星级评分,默认为80分
	 */
	private Integer starLevel;
	/**
	 * 教练证照片路径
	 */
	private String coachCardPhoto;
	/**
	 * 教练驾驶证
	 */
    private String drPhoto;

    private String drPhoto2;
    
    /**
     *  带教人数
     */
    private Integer bookSum;
    
    
	/**
	 * 事件详情
	 */
	private String eventDesc;
	
	/**
	 * 教练身份认证状态
	 */
	private Integer checkIdState;
	
	/**
	 * 教练驾驶证认证状态
	 */
	private Integer checkDriveIdState;
	
	/**
	 * 数据同步类型(1更新2插入)
	 */
	private Integer syncType;
	
	/**
	 * 数据最后一次同步时间
	 */
	private Date syncTime;
	
	/**
	 * 是否同意了用户协议 0-未同意 1-已同意
	 */
	private Integer agreement;
	
	/**
	 * 是否设置了支付密码- 0-未设置；1-已设置
	 */
	private Integer pwstate;
	
    private String idNation;

    private Date idBirthday;

    private String idAddr;

    private Date idExpires;

    private String drNation;

    private String drAddr;

    private Date drFirst;

    private Byte drType;

    private Date drExpires;

    private String drFileNo;

    private Byte coachCardType;

    private String coachCardAuth;
    
    private Integer coachAge;
    
    private Integer schoolAge;
    
    private Integer state;
    
    private Date reviveTime;
    
    private String extra;
    
    private Date firstLogin;
    
    private Date lastLogin;
    
    private Integer isNewDrPhoto;
    
    private Integer checkDrState;
    
    private String checkDrRemark;
    
    private Integer teachStudent;
    
    private String area;
    
    private String coachTag;
    
    private String profile;
    
    private String qrcode;
    
    private String rongToken;
    

	public Integer getTeachStudent() {
		return teachStudent;
	}

	public void setTeachStudent(Integer teachStudent) {
		this.teachStudent = teachStudent;
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

	public Byte getDrType() {
		return drType;
	}

	public void setDrType(Byte drType) {
		this.drType = drType;
	}

	public Date getDrExpires() {
		return drExpires;
	}

	public void setDrExpires(Date drExpires) {
		this.drExpires = drExpires;
	}

	public String getDrFileNo() {
		return drFileNo;
	}

	public void setDrFileNo(String drFileNo) {
		this.drFileNo = drFileNo;
	}

	public Byte getCoachCardType() {
		return coachCardType;
	}

	public void setCoachCardType(Byte coachCardType) {
		this.coachCardType = coachCardType;
	}

	public String getCoachCardAuth() {
		return coachCardAuth;
	}

	public void setCoachCardAuth(String coachCardAuth) {
		this.coachCardAuth = coachCardAuth;
	}

	public Long getCoachId() {
		return coachId;
	}

	public void setCoachId(Long coachId) {
		this.coachId = coachId;
	}

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getCoachCarId() {
		return coachCarId;
	}

	public void setCoachCarId(Integer coachCarId) {
		this.coachCarId = coachCarId;
	}

	public String getDrLicence() {
		return drLicence;
	}

	public void setDrLicence(String drLicence) {
		this.drLicence = drLicence;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getCoachLevel() {
		return coachLevel;
	}

	public void setCoachLevel(Integer coachLevel) {
		this.coachLevel = coachLevel;
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

	public String getCourses() {
		return courses;
	}

	public void setCourses(String courses) {
		this.courses = courses;
	}

	public String getCoursesDesc() {
		return coursesDesc;
	}

	public void setCoursesDesc(String coursesDesc) {
		this.coursesDesc = coursesDesc;
	}

	public Integer getAcceptOrderDis() {
		return acceptOrderDis;
	}

	public void setAcceptOrderDis(Integer acceptOrderDis) {
		this.acceptOrderDis = acceptOrderDis;
	}

	public Integer getPerformance() {
		return performance;
	}

	public void setPerformance(Integer performance) {
		this.performance = performance;
	}

	public Integer getWstate() {
		return wstate;
	}

	public void setWstate(Integer wstate) {
		this.wstate = wstate;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getCoachCard() {
		return coachCard;
	}

	public void setCoachCard(String coachCard) {
		this.coachCard = coachCard;
	}

	public String getCoachCardDate() {
		return coachCardDate;
	}

	public void setCoachCardDate(String coachCardDate) {
		this.coachCardDate = coachCardDate;
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

	public Integer getStuCoachID() {
		return StuCoachID;
	}

	public void setStuCoachID(Integer stuCoachID) {
		StuCoachID = stuCoachID;
	}

	public Integer getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(Integer starLevel) {
		this.starLevel = starLevel;
	}

	public String getCoachCardPhoto() {
		return coachCardPhoto;
	}

	public void setCoachCardPhoto(String coachCardPhoto) {
		this.coachCardPhoto = coachCardPhoto;
	}

	public String getDrPhoto() {
		return drPhoto;
	}

	public void setDrPhoto(String drPhoto) {
		this.drPhoto = drPhoto;
	}

	public String getDrPhoto2() {
		return drPhoto2;
	}

	public void setDrPhoto2(String drPhoto2) {
		this.drPhoto2 = drPhoto2;
	}

	public Integer getIsNewDrPhoto() {
		return isNewDrPhoto;
	}

	public void setIsNewDrPhoto(Integer isNewDrPhoto) {
		this.isNewDrPhoto = isNewDrPhoto;
	}

	public String getEventDesc() {
		return eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

	public Integer getCheckIdState() {
		return checkIdState;
	}

	public void setCheckIdState(Integer checkIdState) {
		this.checkIdState = checkIdState;
	}

	public Integer getCheckDriveIdState() {
		return checkDriveIdState;
	}

	public void setCheckDriveIdState(Integer checkDriveIdState) {
		this.checkDriveIdState = checkDriveIdState;
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

	public Integer getPwstate() {
		return pwstate;
	}

	public void setPwstate(Integer pwstate) {
		this.pwstate = pwstate;
	}

	public Integer getCoachAge() {
		return coachAge;
	}

	public void setCoachAge(Integer coachAge) {
		this.coachAge = coachAge;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public Integer getBookSum() {
		return bookSum;
	}

	public void setBookSum(Integer bookSum) {
		this.bookSum = bookSum;
	}

	public Integer getCheckDrState() {
		return checkDrState;
	}

	public void setCheckDrState(Integer checkDrState) {
		this.checkDrState = checkDrState;
	}

	public String getCheckDrRemark() {
		return checkDrRemark;
	}

	public void setCheckDrRemark(String checkDrRemark) {
		this.checkDrRemark = checkDrRemark;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCoachTag() {
		return coachTag;
	}

	public void setCoachTag(String coachTag) {
		this.coachTag = coachTag;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public Integer getSchoolAge() {
		return schoolAge;
	}

	public void setSchoolAge(Integer schoolAge) {
		this.schoolAge = schoolAge;
	}

	public String getRongToken() {
		return rongToken;
	}

	public void setRongToken(String rongToken) {
		this.rongToken = rongToken;
	}


}
