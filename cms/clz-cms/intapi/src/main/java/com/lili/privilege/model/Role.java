package com.lili.privilege.model;

import java.util.Date;
import java.util.List;

import com.lili.cms.entity.BasePagedEntity;

public class Role extends BasePagedEntity{

	private int id;
	
	private String name;
	//0--启用，1--停用
	private int enabled;
	
	private String remark;
	
	private String creator;
	
	private String updator;
	
	private Date createTime;
	
	private Date updateTime;
	
	private int privilegeCount;
	
	private int userCount;
	
	private List<RolePrivilege> rolePrivileges;
	
	private List<Privilege> privileges;
	
	private String privilegestr;
	
	private int isAdmin;
	
	private int isGroup;
	
	private int level;
	
	private String ids;
	
	private int schoolid;
	
	private int pid;
	
	
	

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(int schoolid) {
		this.schoolid = schoolid;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public int getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(int isGroup) {
		this.isGroup = isGroup;
	}

	public String getPrivilegestr() {
		return privilegestr;
	}

	public void setPrivilegestr(String privilegestr) {
		this.privilegestr = privilegestr;
	}

	public List<RolePrivilege> getRolePrivileges() {
		return rolePrivileges;
	}

	public void setRolePrivileges(List<RolePrivilege> rolePrivileges) {
		this.rolePrivileges = rolePrivileges;
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

	public int getPrivilegeCount() {
		return privilegeCount;
	}

	public void setPrivilegeCount(int privilegeCount) {
		this.privilegeCount = privilegeCount;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
