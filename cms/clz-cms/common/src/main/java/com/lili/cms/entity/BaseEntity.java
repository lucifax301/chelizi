package com.lili.cms.entity;

import java.io.Serializable;

/**
 * 与CMS系统相关的实体基类,包含业务基本属性
 * <p>驾校id等字段,可跟随传输实体传输到数据访问层,一般由系统自动加载进实体
 * @author hughes
 *
 */
public class BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 驾校id
	 */
	private Long schoolId;
	
	/**
	 * 接口类型 1---lili 2---jx
	 */
	private Integer cmsChannel;
	


	public Long getSchoolId() {
		return schoolId;
	}


	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}


	public Integer getCmsChannel() {
		return cmsChannel;
	}


	public void setCmsChannel(Integer cmsChannel) {
		this.cmsChannel = cmsChannel;
	}
	
}
