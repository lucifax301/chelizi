package com.lili.school.vo;

import java.io.Serializable;
import java.util.Date;

public class EnrollProgressUserBriefVo implements Serializable {
    private Integer cpid;

    private String title;

    private Long studentId;

    private Integer stepId;

    private Byte processState;

    private String result;

    private Byte needShow;

    private String icon;

    private Integer cityId;

    private Integer ttid;

    private Date ctime;

	public Integer getCpid() {
		return cpid;
	}

	public void setCpid(Integer cpid) {
		this.cpid = cpid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Integer getStepId() {
		return stepId;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	public Byte getProcessState() {
		return processState;
	}

	public void setProcessState(Byte processState) {
		this.processState = processState;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public Byte getNeedShow() {
		return needShow;
	}

	public void setNeedShow(Byte needShow) {
		this.needShow = needShow;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getTtid() {
		return ttid;
	}

	public void setTtid(Integer ttid) {
		this.ttid = ttid;
	}
	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
   
    
    
}
