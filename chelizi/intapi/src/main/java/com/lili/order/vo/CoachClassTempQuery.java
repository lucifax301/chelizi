package com.lili.order.vo;

import java.io.Serializable;
import java.util.Date;

public class CoachClassTempQuery implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5675446923563972517L;

	private Integer id;

    private Integer tempId;

    private String tempName;

    private Long coachId;

    private Date cstart;

    private Date cend;

    private String stime;

    private String tend;

    private Integer duration;

    private String courseId;

    private String courseName;

    private Integer maxNum;

    private Integer fieldId;
    
    private Integer coachCarId;

    private String fieldName;

    private Integer drType;
    
    /*训练场经度*/
    private Double lge;
  /*训练场纬度*/
    private Double lae;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTempId() {
		return tempId;
	}

	public void setTempId(Integer tempId) {
		this.tempId = tempId;
	}

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	public Long getCoachId() {
		return coachId;
	}

	public void setCoachId(Long coachId) {
		this.coachId = coachId;
	}

	public Date getCstart() {
		return cstart;
	}

	public void setCstart(Date cstart) {
		this.cstart = cstart;
	}

	public Date getCend() {
		return cend;
	}

	public void setCend(Date cend) {
		this.cend = cend;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getTend() {
		return tend;
	}

	public void setTend(String tend) {
		this.tend = tend;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public Integer getFieldId() {
		return fieldId;
	}

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


	public Integer getDrType() {
		return drType;
	}

	public void setDrType(Integer drType) {
		this.drType = drType;
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

	public Integer getCoachCarId() {
		return coachCarId;
	}

	public void setCoachCarId(Integer coachCarId) {
		this.coachCarId = coachCarId;
	}

}