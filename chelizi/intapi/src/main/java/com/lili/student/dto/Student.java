package com.lili.student.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.crypto.Data;

public class Student implements Serializable {
    private Long studentId;

    private String idNumber;

    private String name;

    private Byte sex;

    private Short age;

    private String hometown;

    private String phoneNum;

    private String mailBox;

    private String headIcon;

    private String password;

    private Short course1;

    private Short course2;

    private Short course3;

    private Short course4;

    private Integer studyDis;

    private Date registerTime;

    private String bankcardNum;

    private String bankName;

    private String bankAccName;

    private String idPhotoFront;

    private String idPhotoBack;

    private Byte isImport;

    private String importSrc;

    private Integer stuCoachEmpID;

    private String applyCarType;

    private Byte eventState;

    private String eventDesc;

    private Byte checkIdState;

    private Byte schoolId;

    private Byte syncType;

    private Date syncTime;

    private String drLicence;

    private String drPhoto;

    private String drPhoto2;

    private Byte drType;

    private Date drExpires;

    private Byte agreement;

    private Integer applyexam;

    private Byte pwstate;

    private Integer cityId;

    private Integer applystate;

    private String applyorder;

    private Integer applyttid;

    private String flowNo;

    private String idNation;

    private Date idBirthday;

    private String idAddr;

    private Date idExpires;

    private String drNation;

    private String drAddr;

    private Date drFirst;

    private String drFileNo;

    private Byte checkDriveIdState;

    private String openId;
    
    private String extra;
    
    private Integer vipId;
    
    private Integer state;
    
    private Date reviveTime;
    /*充送方案中文id*/
    private String vipPackageId;
    /*充送方案中文名称*/
    private String rcname;
    /*充送该客户审核状态：0待审核，1审核通过,2审核拒绝，9该客户优惠套餐已过期(包含已结束和未激活)*/
    private Integer vstate;
    /*学员成为大客户的时间*/
    private Date ctime;
    /*大客户公司名称*/
    private String company;
    /*工号*/
    private String cid;
    /*报名城市名称*/
    private String enrollCity;
    
    private Date firstLogin;
    
    private Date lastLogin;

    private Integer basicHour2;
    
    private Integer testHour2;
    
    private Integer simTestHour2;
    
    private Integer basicHour3;
    
    private Integer testHour3;
    
    private Integer simTestHour3;
    
    private Integer driveHour;
    
    private Integer packageId;
    
    private Date relevanceTime;
    
    private String qqOpenId;
    
    private int bmHour;
    
    private String unionId;
    
    private String rongToken;
    
    private Integer applyChannel;
    
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

	private static final long serialVersionUID = 1L;

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
        this.idNumber = idNumber == null ? null : idNumber.trim();
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    public String getMailBox() {
        return mailBox;
    }

    public void setMailBox(String mailBox) {
        this.mailBox = mailBox == null ? null : mailBox.trim();
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon == null ? null : headIcon.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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
        this.bankcardNum = bankcardNum == null ? null : bankcardNum.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankAccName() {
        return bankAccName;
    }

    public void setBankAccName(String bankAccName) {
        this.bankAccName = bankAccName == null ? null : bankAccName.trim();
    }

    public String getIdPhotoFront() {
        return idPhotoFront;
    }

    public void setIdPhotoFront(String idPhotoFront) {
        this.idPhotoFront = idPhotoFront == null ? null : idPhotoFront.trim();
    }

    public String getIdPhotoBack() {
        return idPhotoBack;
    }

    public void setIdPhotoBack(String idPhotoBack) {
        this.idPhotoBack = idPhotoBack == null ? null : idPhotoBack.trim();
    }

    public Byte getIsImport() {
        return isImport;
    }

    public void setIsImport(Byte isImport) {
        this.isImport = isImport;
    }

    public String getImportSrc() {
        return importSrc;
    }

    public void setImportSrc(String importSrc) {
        this.importSrc = importSrc == null ? null : importSrc.trim();
    }

    public Integer getStuCoachEmpID() {
        return stuCoachEmpID;
    }

    public void setStuCoachEmpID(Integer stuCoachEmpID) {
        this.stuCoachEmpID = stuCoachEmpID;
    }

    public String getApplyCarType() {
        return applyCarType;
    }

    public void setApplyCarType(String applyCarType) {
        this.applyCarType = applyCarType == null ? null : applyCarType.trim();
    }

    public Byte getEventState() {
        return eventState;
    }

    public void setEventState(Byte eventState) {
        this.eventState = eventState;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc == null ? null : eventDesc.trim();
    }

    public Byte getCheckIdState() {
        return checkIdState;
    }

    public void setCheckIdState(Byte checkIdState) {
        this.checkIdState = checkIdState;
    }

    public Byte getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Byte schoolId) {
        this.schoolId = schoolId;
    }

    public Byte getSyncType() {
        return syncType;
    }

    public void setSyncType(Byte syncType) {
        this.syncType = syncType;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public String getDrLicence() {
        return drLicence;
    }

    public void setDrLicence(String drLicence) {
        this.drLicence = drLicence == null ? null : drLicence.trim();
    }

    public String getDrPhoto() {
        return drPhoto;
    }

    public void setDrPhoto(String drPhoto) {
        this.drPhoto = drPhoto == null ? null : drPhoto.trim();
    }

    public String getDrPhoto2() {
        return drPhoto2;
    }

    public void setDrPhoto2(String drPhoto2) {
        this.drPhoto2 = drPhoto2 == null ? null : drPhoto2.trim();
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

    public Byte getAgreement() {
        return agreement;
    }

    public void setAgreement(Byte agreement) {
        this.agreement = agreement;
    }

    public Integer getApplyexam() {
        return applyexam;
    }

    public void setApplyexam(Integer applyexam) {
        this.applyexam = applyexam;
    }

    public Byte getPwstate() {
        return pwstate;
    }

    public void setPwstate(Byte pwstate) {
        this.pwstate = pwstate;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getApplystate() {
        return applystate;
    }

    public void setApplystate(Integer applystate) {
        this.applystate = applystate;
    }

    public String getApplyorder() {
        return applyorder;
    }

    public void setApplyorder(String applyorder) {
        this.applyorder = applyorder == null ? null : applyorder.trim();
    }

    public Integer getApplyttid() {
        return applyttid;
    }

    public void setApplyttid(Integer applyttid) {
        this.applyttid = applyttid;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo == null ? null : flowNo.trim();
    }

    public String getIdNation() {
        return idNation;
    }

    public void setIdNation(String idNation) {
        this.idNation = idNation == null ? null : idNation.trim();
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
        this.idAddr = idAddr == null ? null : idAddr.trim();
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
        this.drNation = drNation == null ? null : drNation.trim();
    }

    public String getDrAddr() {
        return drAddr;
    }

    public void setDrAddr(String drAddr) {
        this.drAddr = drAddr == null ? null : drAddr.trim();
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
        this.drFileNo = drFileNo == null ? null : drFileNo.trim();
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
        this.openId = openId == null ? null : openId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", studentId=").append(studentId);
        sb.append(", idNumber=").append(idNumber);
        sb.append(", name=").append(name);
        sb.append(", sex=").append(sex);
        sb.append(", age=").append(age);
        sb.append(", hometown=").append(hometown);
        sb.append(", phoneNum=").append(phoneNum);
        sb.append(", mailBox=").append(mailBox);
        sb.append(", headIcon=").append(headIcon);
        sb.append(", password=").append(password);
        sb.append(", course1=").append(course1);
        sb.append(", course2=").append(course2);
        sb.append(", course3=").append(course3);
        sb.append(", course4=").append(course4);
        sb.append(", studyDis=").append(studyDis);
        sb.append(", registerTime=").append(registerTime);
        sb.append(", bankcardNum=").append(bankcardNum);
        sb.append(", bankName=").append(bankName);
        sb.append(", bankAccName=").append(bankAccName);
        sb.append(", idPhotoFront=").append(idPhotoFront);
        sb.append(", idPhotoBack=").append(idPhotoBack);
        sb.append(", isImport=").append(isImport);
        sb.append(", importSrc=").append(importSrc);
        sb.append(", stuCoachEmpID=").append(stuCoachEmpID);
        sb.append(", applyCarType=").append(applyCarType);
        sb.append(", eventState=").append(eventState);
        sb.append(", eventDesc=").append(eventDesc);
        sb.append(", checkIdState=").append(checkIdState);
        sb.append(", schoolId=").append(schoolId);
        sb.append(", syncType=").append(syncType);
        sb.append(", syncTime=").append(syncTime);
        sb.append(", drLicence=").append(drLicence);
        sb.append(", drPhoto=").append(drPhoto);
        sb.append(", drPhoto2=").append(drPhoto2);
        sb.append(", drType=").append(drType);
        sb.append(", drExpires=").append(drExpires);
        sb.append(", agreement=").append(agreement);
        sb.append(", applyexam=").append(applyexam);
        sb.append(", pwstate=").append(pwstate);
        sb.append(", cityId=").append(cityId);
        sb.append(", applystate=").append(applystate);
        sb.append(", applyorder=").append(applyorder);
        sb.append(", applyttid=").append(applyttid);
        sb.append(", flowNo=").append(flowNo);
        sb.append(", idNation=").append(idNation);
        sb.append(", idBirthday=").append(idBirthday);
        sb.append(", idAddr=").append(idAddr);
        sb.append(", idExpires=").append(idExpires);
        sb.append(", drNation=").append(drNation);
        sb.append(", drAddr=").append(drAddr);
        sb.append(", drFirst=").append(drFirst);
        sb.append(", drFileNo=").append(drFileNo);
        sb.append(", checkDriveIdState=").append(checkDriveIdState);
        sb.append(", openId=").append(openId);
        sb.append(", applyChannel=").append(applyChannel);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
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

	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
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

	public Integer getBasicHour2() {
		return basicHour2;
	}

	public void setBasicHour2(Integer basicHour2) {
		this.basicHour2 = basicHour2;
	}

	public Integer getTestHour2() {
		return testHour2;
	}

	public void setTestHour2(Integer testHour2) {
		this.testHour2 = testHour2;
	}

	public Integer getSimTestHour2() {
		return simTestHour2;
	}

	public void setSimTestHour2(Integer simTestHour2) {
		this.simTestHour2 = simTestHour2;
	}

	public Integer getBasicHour3() {
		return basicHour3;
	}

	public void setBasicHour3(Integer basicHour3) {
		this.basicHour3 = basicHour3;
	}

	public Integer getTestHour3() {
		return testHour3;
	}

	public void setTestHour3(Integer testHour3) {
		this.testHour3 = testHour3;
	}

	public Integer getSimTestHour3() {
		return simTestHour3;
	}

	public void setSimTestHour3(Integer simTestHour3) {
		this.simTestHour3 = simTestHour3;
	}

	public Integer getDriveHour() {
		return driveHour;
	}

	public void setDriveHour(Integer driveHour) {
		this.driveHour = driveHour;
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

	public String getQqOpenId() {
		return qqOpenId;
	}

	public void setQqOpenId(String qqOpenId) {
		this.qqOpenId = qqOpenId;
	}

	public int getBmHour() {
		return bmHour;
	}

	public void setBmHour(int bmHour) {
		this.bmHour = bmHour;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getRongToken() {
		return rongToken;
	}

	public void setRongToken(String rongToken) {
		this.rongToken = rongToken;
	}

	public Integer getApplyChannel() {
		return applyChannel;
	}

	public void setApplyChannel(Integer applyChannel) {
		this.applyChannel = applyChannel;
	}
	
}