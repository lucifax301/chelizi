package com.lili.privilege.dao;

import java.util.List;
import java.util.Map;

import com.lili.privilege.model.Privilege;
import com.lili.privilege.model.Role;
import com.lili.privilege.model.RolePrivilege;
import com.lili.privilege.model.RoleUser;

public interface PrivilegeDao {

	public List<Privilege> getAllPrivilege();
	
	public int insertRole(Role role) ;
	
	public int delRole(int id) ;
	
	public int updateRole(Role role) ;
	
	public int enableRole(Role role) ;
	
	public Role getRole(int id) ;
	
	public int insertRolePrivilege(List<RolePrivilege> rolePrivileges) ;
	
	public int delRolePrivilege(int id);
	
	public List<Privilege> listRolePrivilege(int id);
	
	public List<Privilege> listUserPrivilege(int id);
	
	public List<Role> listRole(Role role) ;
	
	public List<Map> listRolePrivilegeCount(int[] ids);
	
	public List<Map> listRoleUserCount(int[] ids);
	
	public int getRoleTotal(Role role);
	
	public int insertRoleUser(List<RoleUser> roleUsers) ;
	
	public int getRoleUser(RoleUser roleUser);
	
	public int delRoleUser(RoleUser roleUser) ;
	
	public List<Map> listRoleUser(RoleUser roleUser) ;
	
	public int getRoleUserTotal(RoleUser roleUser);
}
