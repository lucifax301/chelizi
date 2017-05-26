package com.lili.coach.vo;

import java.io.Serializable;

public class CoachVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1634645631759703681L;

	//-----------------------Coach去除敏感信息----------------------------
	/**
	 * 教练id
	 */
	private Long coachId;
	
	/**
	 * 驾校id
	 */
	private Integer schoolId;
	
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
	//private String password;
	
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
	//private Date registerTime;
	
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
	//private String bankcardNum;
	
	/**
	 * 银行名称
	 */
	//private String bankName;
	
	/**
	 * 银行帐户名称
	 */
	//private String bankAccName;
	
	/**
	 * 身份证正面照
	 */
	//private String idPhotoFront;
	
	/**
	 * 身份证反面照
	 */
	//private String idPhotoBack;
	
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
	//private Integer StuCoachID;
	
	/**
	 * 星级评分
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
    
    private Integer hasClass;
    
    private Integer bookSum=0;
	
    private String schoolName;
    
    private Integer checkDrState;
    
    private Integer teachStudent;
    
    
	
	public Integer getCheckDrState() {
		return checkDrState;
	}
	public void setCheckDrState(Integer checkDrState) {
		this.checkDrState = checkDrState;
	}
	public Integer getTeachStudent() {
		return teachStudent;
	}
	public void setTeachStudent(Integer teachStudent) {
		this.teachStudent = teachStudent;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Integer getBookSum() {
		return bookSum;
	}
	public void setBookSum(Integer bookSum) {
		this.bookSum = bookSum;
	}
	public Integer getHasClass() {
		return hasClass;
	}
	public void setHasClass(Integer hasClass) {
		this.hasClass = hasClass;
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
	 * 是否同意了用户协议 0-未同意 1-已同意
	 */
	private Integer agreement;
	
	/**
	 * 是否设置了支付密码- 0-未设置；1-已设置
	 */
	private Integer pwstate;
	
	//-----------------------Car去除敏感信息---------------------------------
	/**
	 * 车辆id
	 */
	private Integer carId;
	
	/**
	 * 车辆类型
	 */
	private String carType;
	
	/**
	 * 车龄
	 */
	//private Integer age;
	
	/**
	 * 里程
	 */
	private Integer mileage;
	
	/**
	 * 车内环境
	 */
	private Integer innerEnvir;
	
	/**
	 * 性能
	 */
	//private Integer performance;
	
	/**
	 * 车牌号
	 */
	private String carNo;
	
	/**
	 * 汽车等级
	 */
	private Integer carLevel;
	
	/**
	 * 驾驶类型（1代表C1,2代表C2）
	 */
	private Integer driveType;
	
	/**
	 * 行驶证编号
	 */
	private String driveNumber;
	
	/**
	 * 行驶证照片
	 */
	//private String drivePhoto;
	
	//-----------------------用于客户端界面额外需要展示的字段---------------------------------
	
	/**
	 * 教龄
	 */
	private Integer ageOfTeach;
	/**
	 * 驾校名称  同importsrc
	 */
	private String school;
	/**
	 * 课程单价
	 */
	private Integer price;
	/**
	 * 车辆位置坐标
	 */
	private Double lge;
	/**
	 * 车辆位置坐标
	 */
	private Double lae;
	/**
	 * 车辆的方向
	 */
	private Float dir;
    /**
     * 学生与教练的距离
     */
    private Double distance;
    
    private String extra;
    
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
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public Integer getMileage() {
		return mileage;
	}
	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}
	public Integer getInnerEnvir() {
		return innerEnvir;
	}
	public void setInnerEnvir(Integer innerEnvir) {
		this.innerEnvir = innerEnvir;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public Integer getCarLevel() {
		return carLevel;
	}
	public void setCarLevel(Integer carLevel) {
		this.carLevel = carLevel;
	}
	public Integer getDriveType() {
		return driveType;
	}
	public void setDriveType(Integer driveType) {
		this.driveType = driveType;
	}
	public String getDriveNumber() {
		return driveNumber;
	}
	public void setDriveNumber(String driveNumber) {
		this.driveNumber = driveNumber;
	}
	public Integer getAgeOfTeach() {
		return ageOfTeach;
	}
	public void setAgeOfTeach(Integer ageOfTeach) {
		this.ageOfTeach = ageOfTeach;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Double getLge() {
		return lge;
	}
	public void setLge(Double lge) {
		this.lge = lge;
	}
	public Double getLae() {
		return lae;
	}
	public void setLae(Double lae) {
		this.lae = lae;
	}
	public Float getDir() {
		return dir;
	}
	public void setDir(Float dir) {
		this.dir = dir;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	@Override
	public String toString() {
		return "CoachVo [coachId=" + coachId + ", schoolId=" + schoolId
				+ ", coachCarId=" + coachCarId + ", drLicence=" + drLicence
				+ ", cityId=" + cityId + ", coachLevel=" + coachLevel
				+ ", idNumber=" + idNumber + ", name=" + name + ", sex=" + sex
				+ ", age=" + age + ", hometown=" + hometown + ", phoneNum="
				+ phoneNum + ", mailBox=" + mailBox + ", headIcon=" + headIcon
				+ ", courses=" + courses + ", coursesDesc=" + coursesDesc
				+ ", acceptOrderDis=" + acceptOrderDis + ", performance="
				+ performance + ", wstate=" + wstate + ", coachCard="
				+ coachCard + ", coachCardDate=" + coachCardDate
				+ ", isImport=" + isImport + ", importSrc=" + importSrc
				+ ", starLevel=" + starLevel + ", coachCardPhoto="
				+ coachCardPhoto + ", coachDriveCardPhoto="
				+ drPhoto + ", eventDesc=" + eventDesc
				+ ", checkIdState=" + checkIdState + ", checkDriveIdState="
				+ checkDriveIdState + ", carId=" + carId + ", carType="
				+ carType + ", mileage=" + mileage + ", innerEnvir="
				+ innerEnvir + ", carNo=" + carNo + ", carLevel=" + carLevel
				+ ", driveType=" + driveType + ", driveNumber=" + driveNumber
				+ ", ageOfTeach=" + ageOfTeach + ", school=" + school
				+ ", price=" + price + ", lge=" + lge + ", lae=" + lae
				+ ", dir=" + dir + ", distance=" + distance + ", extra=" + extra+", hasClass=" + hasClass +"]";
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
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	
}
