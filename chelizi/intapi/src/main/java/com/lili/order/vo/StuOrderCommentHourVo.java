package com.lili.order.vo;

import java.util.Date;
import java.util.List;

public class StuOrderCommentHourVo extends StuOrderCommentVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer scid;
	private Long studentId;
	private Long coachId;
	private String orderId;
	private Integer courseId;
	private Integer subjectId;
	private Integer dfType;
	private List<Integer> ctids;
	private List<Integer> scores;
	private String oneWord;
	private Date cotime;
	private List<String> tags;
	private Integer classHour;

	public Integer getScid() {
		return scid;
	}

	public void setScid(Integer scid) {
		this.scid = scid;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getCoachId() {
		return coachId;
	}

	public void setCoachId(Long coachId) {
		this.coachId = coachId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getDfType() {
		return dfType;
	}

	public void setDfType(Integer dfType) {
		this.dfType = dfType;
	}

	public List<Integer> getCtids() {
		return ctids;
	}

	public void setCtids(List<Integer> ctids) {
		this.ctids = ctids;
	}

	public List<Integer> getScores() {
		return scores;
	}

	public void setScores(List<Integer> scores) {
		this.scores = scores;
	}

	public String getOneWord() {
		return oneWord;
	}

	public void setOneWord(String oneWord) {
		this.oneWord = oneWord;
	}

	public Date getCotime() {
		return cotime;
	}

	public void setCotime(Date cotime) {
		this.cotime = cotime;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Integer getClassHour() {
		return classHour;
	}

	public void setClassHour(Integer classHour) {
		this.classHour = classHour;
	}

}
