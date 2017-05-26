package com.lili.student.vo;

import java.io.Serializable;
import java.util.List;

import com.lili.order.vo.CoachClassVo;

public class MycoachesVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 教练id
	 */
	private Long coachId;
	
	/**
	 * 驾校id
	 */
	private Integer schoolId;
	
	/**
	 * 教练等级
	 */
	private Integer coachLevel;
	
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
	 * 电话号码
	 */
	private String phoneNum;
	
	/**
	 * 头像
	 */
	private String headIcon;
	
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
	 * 星级评分
	 */
	private Integer starLevel;
	/**
	 * 是否开放定向预约
	 */
	private Integer isOpen;
	
	/**
	 * 导入的来源
	 */
	private String importSrc;
	
	/**
	 * 是否已绑定相关学员  0-未绑定；1-已绑定  （v1.0.1是导入的教练，与学员一定是绑定的）
	 */
	private Integer bindType =1; 
	/**
	 * 开放状态  0-暂时无开放时段；1-可以预约；2-预约成功；3-等到预约应答（目前无这种状态）
	 */
	private Integer openType =1;
	
	private Byte drType;
	
	private Integer coachAge;
	  /**
     *  带教人数
     */
    private Integer bookSum=0;
    
    private String extra;
	/**
	 * 车辆id
	 */
	private Integer carId;
	/**
	 * 车牌号
	 */
	private String carNo;
	
	private double lae;
	
	private double lge;
	
	private Integer teachStudent;
	
	private double distance=0;
	
	private Integer cityId;
	
	
	

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Integer getTeachStudent() {
		return teachStudent;
	}

	public void setTeachStudent(Integer teachStudent) {
		this.teachStudent = teachStudent;
	}

	/**
	 * 排班课程
	 */
	private List<CoachClassVo> classes;
	
	
	
	

	public double getLae() {
		return lae;
	}

	public void setLae(double lae) {
		this.lae = lae;
	}

	public double getLge() {
		return lge;
	}

	public void setLge(double lge) {
		this.lge = lge;
	}

	public List<CoachClassVo> getClasses() {
		return classes;
	}

	public void setClasses(List<CoachClassVo> classes) {
		this.classes = classes;
	}

	public Byte getDrType() {
		return drType;
	}

	public void setDrType(Byte drType) {
		this.drType = drType;
	}

	public Integer getCoachAge() {
		return coachAge;
	}

	public void setCoachAge(Integer coachAge) {
		this.coachAge = coachAge;
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

	public Integer getCoachLevel() {
		return coachLevel;
	}

	public void setCoachLevel(Integer coachLevel) {
		this.coachLevel = coachLevel;
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

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getHeadIcon() {
		return headIcon;
	}

	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
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

	public Integer getStarLevel() {
		return starLevel==null?80:starLevel;
	}

	public void setStarLevel(Integer starLevel) {
		this.starLevel = starLevel;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public Integer getBindType() {
		return bindType;
	}

	public void setBindType(Integer bindType) {
		this.bindType = bindType;
	}

	public Integer getOpenType() {
		return openType;
	}

	public void setOpenType(Integer openType) {
		this.openType = openType;
	}

	public String getImportSrc() {
		return importSrc;
	}

	public void setImportSrc(String importSrc) {
		this.importSrc = importSrc;
	}

	public Integer getBookSum() {
		return bookSum;
	}

	public void setBookSum(Integer bookSum) {
		this.bookSum = bookSum;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}


	
	

}
