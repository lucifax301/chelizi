package com.lili.school.vo;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class WechatCoachMyClass implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 416415982356495346L;

	private Integer ccid;
	
	private Date date;

    private Date cstart;

    private Date cend;

    private Integer timeNum;

    private String orderId;

    private Integer maxNum;

    private Integer bookNum;

    private String courseId;

    private String courseName;
    
    private String placeName;
    
    private List<WechatPlantClass> plantClassList;

	public Integer getCcid() {
		return ccid;
	}

	public void setCcid(Integer ccid) {
		this.ccid = ccid;
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

	public Integer getTimeNum() {
		return timeNum;
	}

	public void setTimeNum(Integer timeNum) {
		this.timeNum = timeNum;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public Integer getBookNum() {
		return bookNum;
	}

	public void setBookNum(Integer bookNum) {
		this.bookNum = bookNum;
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

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<WechatPlantClass> getPlantClassList() {
		return plantClassList;
	}

	public void setPlantClassList(List<WechatPlantClass> plantClassList) {
		this.plantClassList = plantClassList;
	}
    
    
}