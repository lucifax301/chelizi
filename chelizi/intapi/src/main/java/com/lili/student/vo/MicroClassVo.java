package com.lili.student.vo;


import java.io.Serializable;

public class MicroClassVo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1433288148038083159L;

	private Integer id;

    private Integer fileType;

    private Integer levelOne;

    private String oneName;

    private Integer levelTwo;

    private String twoName;
    
    private String twoImg;

    private String levelThree;

    private String threeName;
    
    private String threeImg;

    private String microId;

    private String microName;

    private String microTime;

    private String version;

    private String versionVest;

    private String filePath;

    private String microRemark;

    private String url;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public Integer getLevelOne() {
		return levelOne;
	}

	public void setLevelOne(Integer levelOne) {
		this.levelOne = levelOne;
	}

	public String getOneName() {
		return oneName;
	}

	public void setOneName(String oneName) {
		this.oneName = oneName;
	}

	public Integer getLevelTwo() {
		return levelTwo;
	}

	public void setLevelTwo(Integer levelTwo) {
		this.levelTwo = levelTwo;
	}

	public String getTwoName() {
		return twoName;
	}

	public void setTwoName(String twoName) {
		this.twoName = twoName;
	}

	public String getLevelThree() {
		return levelThree;
	}

	public void setLevelThree(String levelThree) {
		this.levelThree = levelThree;
	}

	public String getThreeName() {
		return threeName;
	}

	public void setThreeName(String threeName) {
		this.threeName = threeName;
	}

	public String getMicroId() {
		return microId;
	}

	public void setMicroId(String microId) {
		this.microId = microId;
	}

	public String getMicroName() {
		return microName;
	}

	public void setMicroName(String microName) {
		this.microName = microName;
	}

	public String getMicroTime() {
		return microTime;
	}

	public void setMicroTime(String microTime) {
		this.microTime = microTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersionVest() {
		return versionVest;
	}

	public void setVersionVest(String versionVest) {
		this.versionVest = versionVest;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getMicroRemark() {
		return microRemark;
	}

	public void setMicroRemark(String microRemark) {
		this.microRemark = microRemark;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTwoImg() {
		return twoImg;
	}

	public void setTwoImg(String twoImg) {
		this.twoImg = twoImg;
	}

	public String getThreeImg() {
		return threeImg;
	}

	public void setThreeImg(String threeImg) {
		this.threeImg = threeImg;
	}

}