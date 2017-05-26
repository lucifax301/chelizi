package com.lili.configfile.vo;

import java.io.Serializable;


public class ConfigFileVo implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3969004603920869390L;

	
	// 一级目录类型：1-首页；2-理论课；3-约教练；4-我的
	private String memu; 
	
	/**
	 * 类型： 
	 * 首页：1-10；理论课：11-20；约教练：21-30；我的：31-40
	 * 1-广告；2-限时特惠；3-首页菜单；4-报名链接；5-社区
	 * 11-科目二自学技巧；12-科目二视频教程；13-科目三自学技巧；14-科目三视频教程
	 */
	private String type;
	
	/**
	 * 文件类型：1-广告图、限时活动；2-按钮；3-视频
	 */
	private String fileType; 
	
	//	序号，排序
	private String fileId; 
	
	// 表ID
	private String id;
	
	//	名称
	private String name; 
	
	//	名称/广告图片链接地址
	private String img2xUrl;
	
	private String img3xUrl;
	
	//	url链接地址
	private String url;
	
	//	时间，可以任意格式
	private String dtime;
	
	//	描述
	private String description;


	public String getMemu() {
		return memu;
	}

	public void setMemu(String memu) {
		this.memu = memu;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDtime() {
		return dtime;
	}

	public void setDtime(String dtime) {
		this.dtime = dtime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg2xUrl() {
		return img2xUrl;
	}

	public void setImg2xUrl(String img2xUrl) {
		this.img2xUrl = img2xUrl;
	}

	public String getImg3xUrl() {
		return img3xUrl;
	}

	public void setImg3xUrl(String img3xUrl) {
		this.img3xUrl = img3xUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	
}
