package com.lili.privilege.service;

import java.util.List;
import java.util.Map;

import com.lili.cms.entity.PagedResult;
import com.lili.privilege.model.Privilege;
import com.lili.privilege.model.Role;
import com.lili.privilege.model.RoleUser;

public interface PrivilegeService {
	
	public List<Privilege> getAllPrivilege()throws Exception;

	public int insertRole(Role role) throws Exception;
	
	public int delRole(Role role) throws Exception;
	
	public int updateRole(Role role) throws Exception;
	
	public int enable(Role role) throws Exception;
	
	public Role getRole(int id) throws Exception;
	
	public List<Privilege> getUserPrivilege(int userId)throws Exception;
	
	public PagedResult<Role> listRole(Role role) throws Exception;
	
	public int insertRoleUser(RoleUser roleUser) throws Exception;
	
	public int delRoleUser(RoleUser roleUser) throws Exception;
	
	public PagedResult<Map> listRoleUser(RoleUser roleUser) throws Exception;
	
	public List<Privilege> getUserPrivilegeList(int userId)throws Exception;
}
