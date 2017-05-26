package com.lili.coach.model;

import java.util.Date;

import com.lili.cms.entity.BaseEntity;

/**
 * 教练实体
 * @author yu.y
 *
 */
public class Coach extends BaseEntity{

	private static final long serialVersionUID = 3258929791543735074L;
	
	/**
	 * 教练身份认证状态（0：未认证，1：认证中，2：已认证，3：认证失败）
	 */
	private int checkIdState;
	
	/**
	 * 教练id
	 */
	private long coachId;
	
	
	/**
	 * 教练车id
	 */
	private int coachCarId;
	
	/**
	 * 驾照编号
	 */
	private String drLicence;
	
	/**
	 * 教练注册所有城市
	 */
	private int cityId;
	
	/**
	 * 教练等级
	 */
	private int coachLevel;
	
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
	private int sex;
	
	/**
	 * 年龄
	 */
	private int age;
	
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
	private int acceptOrderDis;
	
	/**
	 * 业绩
	 */
	private int performance;
	
	/**
	 * 教练状态
	 */
	private int wstate;
	
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
	private int isImport;
	
	/**
	 * 导入的来源
	 */
	private String importSrc;
	
	private Integer driveType;
	
	//学校名称
	private String schoolName;
	//carType+carNo(车类型加车牌号)
	private String carInfo;
	
	private String city;
	
	private int drType;
	
	private int starLevel;
	
	private Date firstLogin;
	
	private Date lastLogin;
	
	private Date lastCalculateDate;
	
	//收车次数
	private int inCount;
	//出车次数
	private int outCount;
	//排班次数
	private int classCount;
	//排班课时（小时）
	private int classTime;
	//在线时长
	private int onlineTime;
	//听单时长(分钟)
	private int listenTime;
	//带教时长
	private int teachTime;
	//工作时长
	private int workTime;
	//学员人数
	private int studentCount;
	
	/**
	 * 暂时无法准确获取
	 */
	private int loginCount;
	
	private String drPhoto;
	
	private String drPhoto2;
	
	private Integer isNewDrPhoto;
	
	private Integer checkDrState;
	
	private String checkDrRemark;
	
	
	
	public Date getLastCalculateDate() {
		return lastCalculateDate;
	}

	public void setLastCalculateDate(Date lastCalculateDate) {
		this.lastCalculateDate = lastCalculateDate;
	}

	public int getInCount() {
		return inCount;
	}

	public void setInCount(int inCount) {
		this.inCount = inCount;
	}

	public int getOutCount() {
		return outCount;
	}

	public void setOutCount(int outCount) {
		this.outCount = outCount;
	}

	public int getClassCount() {
		return classCount;
	}

	public void setClassCount(int classCount) {
		this.classCount = classCount;
	}

	public int getClassTime() {
		return classTime;
	}

	public void setClassTime(int classTime) {
		this.classTime = classTime;
	}

	public int getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(int onlineTime) {
		this.onlineTime = onlineTime;
	}

	public int getListenTime() {
		return listenTime;
	}

	public void setListenTime(int listenTime) {
		this.listenTime = listenTime;
	}

	public int getTeachTime() {
		return teachTime;
	}

	public void setTeachTime(int teachTime) {
		this.teachTime = teachTime;
	}

	public int getWorkTime() {
		return workTime;
	}

	public void setWorkTime(int workTime) {
		this.workTime = workTime;
	}

	public int getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
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

	public int getDrType() {
		return drType;
	}

	public void setDrType(int drType) {
		this.drType = drType;
	}

	public int getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(int starLevel) {
		this.starLevel = starLevel;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(String carInfo) {
		this.carInfo = carInfo;
	}

	public Integer getDriveType() {
		return driveType;
	}

	public void setDriveType(Integer driveType) {
		this.driveType = driveType;
	}

	public int getCheckIdState() {
		return checkIdState;
	}

	public void setCheckIdState(int checkIdState) {
		this.checkIdState = checkIdState;
	}

	/**
	 * 原系统中教练ID
	 */
	private int StuCoachID;

	public long getCoachId() {
		return coachId;
	}

	public void setCoachId(long coachId) {
		this.coachId = coachId;
	}

	public int getCoachCarId() {
		return coachCarId;
	}

	public void setCoachCarId(int coachCarId) {
		this.coachCarId = coachCarId;
	}

	public String getDrLicence() {
		return drLicence;
	}

	public void setDrLicence(String drLicence) {
		this.drLicence = drLicence;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getCoachLevel() {
		return coachLevel;
	}

	public void setCoachLevel(int coachLevel) {
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

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
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

	public int getAcceptOrderDis() {
		return acceptOrderDis;
	}

	public void setAcceptOrderDis(int acceptOrderDis) {
		this.acceptOrderDis = acceptOrderDis;
	}

	public int getPerformance() {
		return performance;
	}

	public void setPerformance(int performance) {
		this.performance = performance;
	}

	public int getWstate() {
		return wstate;
	}

	public void setWstate(int wstate) {
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

	public int getIsImport() {
		return isImport;
	}

	public void setIsImport(int isImport) {
		this.isImport = isImport;
	}

	public String getImportSrc() {
		return importSrc;
	}

	public void setImportSrc(String importSrc) {
		this.importSrc = importSrc;
	}

	public int getStuCoachID() {
		return StuCoachID;
	}

	public void setStuCoachID(int stuCoachID) {
		StuCoachID = stuCoachID;
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

}
