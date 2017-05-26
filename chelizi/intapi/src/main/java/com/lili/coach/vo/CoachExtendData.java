package com.lili.coach.vo;

import java.io.Serializable;

public class CoachExtendData implements Serializable {

	private long coachId;

	// 收车次数
	private int inCount;
	// 出车次数
	private int outCount;
	// 排班次数
	private int classCount;
	// 排班课时（小时）
	private int classTime;
	// 在线时长
	private int onlineTime;
	// 听单时长(分钟)
	private int listenTime;
	// 带教时长
	private int teachTime;
	// 工作时长
	private int workTime;
	// 学员人数
	private int studentCount;

	/**
	 * 暂时无法获取
	 */
	private int loginCount;

	public long getCoachId() {
		return coachId;
	}

	public void setCoachId(long coachId) {
		this.coachId = coachId;
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
	
	
}
