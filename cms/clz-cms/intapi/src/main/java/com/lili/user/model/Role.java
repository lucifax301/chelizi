package com.lili.user.model;

import java.io.Serializable;

/**
 * 角色
 * @author hughes
 *
 */
public class Role implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	/**
	 * 部门下的层级
	 */
	private Integer level;
	
	/**
	 * 是否是部门节点 0---不是 1---是
	 */
	private Integer isGroup;
	
	/**
	 * 是否是管理员 0---不是 1---是
	 */
	private Integer isAdmin;
	
	/**
	 * 是否启用,0--启用，1--停用
	 */
	private Integer enabled;

	/**
	 * 父角色id
	 */
	private Long pid;
	
	/**
	 * 角色所属驾校id
	 */
	private Integer schoolId;
	
	/**
	 * 角色名称
	 */
	private String name;
	
	/**
	 * 备注
	 */
	private String remark;
	
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getIsGroup() {
		return isGroup;
	}
	public void setIsGroup(Integer isGroup) {
		this.isGroup = isGroup;
	}
	public Integer getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
