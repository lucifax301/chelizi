package com.lili.privilege.model;

import java.io.Serializable;

public class RolePrivilege implements Serializable{

	private int roleId;
	
	private int privilegeId;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(int privilegeId) {
		this.privilegeId = privilegeId;
	}
	
	
}
