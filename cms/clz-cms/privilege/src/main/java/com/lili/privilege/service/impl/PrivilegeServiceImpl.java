package com.lili.privilege.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lili.cms.entity.PagedResult;
import com.lili.privilege.dao.PrivilegeDao;
import com.lili.privilege.model.Privilege;
import com.lili.privilege.model.Role;
import com.lili.privilege.model.RolePrivilege;
import com.lili.privilege.model.RoleUser;
import com.lili.privilege.service.PrivilegeService;

@Service("privilegeService")
public class PrivilegeServiceImpl implements PrivilegeService {

	@Autowired
	private PrivilegeDao privilegeDao;
	
	private List<Privilege> merge(List<Privilege> privileges){
		List<Privilege> level1=new ArrayList();
		List<Privilege> level2=new ArrayList();
		List<Privilege> level3=new ArrayList();
		for(Privilege privilege:privileges){
			if(privilege.getLevel()==1){
				privilege.setPrivileges(new ArrayList());
				level1.add(privilege);
			}else if(privilege.getLevel()==2){
				privilege.setPrivileges(new ArrayList());
				level2.add(privilege);
			}else if(privilege.getLevel()==3){
				level3.add(privilege);
			}
		}
		for(Privilege p1:level1){
			for(Privilege p2:level2){
				if(p2.getPid()==p1.getId()){
					p1.getPrivileges().add(p2);
					for(Privilege p3:level3){
						if(p3.getPid()==p2.getId()){
							p2.getPrivileges().add(p3);
						}
					}
				}
			}
		}
		return level1;
	}
	
	public List<Privilege> getAllPrivilege()throws Exception{
		List<Privilege> privileges= privilegeDao.getAllPrivilege();
		
		return merge(privileges);
	}
	
	//@LiLiCache(key="${userId}",namespace="privilege",action="ADD")
	public List<Privilege> getUserPrivilege(int userId)throws Exception{
		List<Privilege> privileges= privilegeDao.listUserPrivilege(userId);
		return merge(privileges);
	}
	
	public List<Privilege> getUserPrivilegeList(int userId)throws Exception{
		List<Privilege> privileges= privilegeDao.listUserPrivilege(userId);
		return privileges;
	}
	
	@Override
	public int insertRole(Role role) throws Exception {
		String privilegestr= role.getPrivilegestr();
		if(privilegestr!=null){
			String item[]=privilegestr.split(",");
			List<RolePrivilege> privileges=new ArrayList(); 
			for(int i=0;i<item.length;i++){
				if(item[i]==null||item[i].equals("")) continue;
				RolePrivilege p=new RolePrivilege();
				p.setPrivilegeId(Integer.parseInt(item[i]));
				privileges.add(p);
			}
			role.setRolePrivileges(privileges);
		}
		privilegeDao.insertRole(role);
		int roleId=role.getId();
		List<RolePrivilege> rolePrivileges=role.getRolePrivileges();
		if(rolePrivileges!=null&&!rolePrivileges.isEmpty()){
			for(RolePrivilege item:rolePrivileges){
				item.setRoleId(roleId);
			}
		}
		return privilegeDao.insertRolePrivilege(rolePrivileges);
	}

	@Override
	public int delRole(Role role) throws Exception {
		if(role!=null){
			if(role.getIds()==null){
				privilegeDao.delRolePrivilege(role.getId());
				privilegeDao.delRole(role.getId());
				return 1;
			}else{
				String ids[]=role.getIds().split(",");
				for(int i=0;i<ids.length;i++){
					privilegeDao.delRolePrivilege(Integer.parseInt(ids[i]));
					privilegeDao.delRole(Integer.parseInt(ids[i]));
				}
			}
		}
		return 0;
	}

	@Override
	public int updateRole(Role role) throws Exception {
		String privilegestr= role.getPrivilegestr();
		if(privilegestr!=null){
			String item[]=privilegestr.split(",");
			List<RolePrivilege> privileges=new ArrayList(); 
			for(int i=0;i<item.length;i++){
				if(item[i]==null||item[i].equals("")) continue;
				RolePrivilege p=new RolePrivilege();
				p.setPrivilegeId(Integer.parseInt(item[i]));
				privileges.add(p);
			}
			role.setRolePrivileges(privileges);
		}
		
		privilegeDao.delRolePrivilege(role.getId());
		List<RolePrivilege> rolePrivileges=role.getRolePrivileges();
		if(rolePrivileges!=null&&!rolePrivileges.isEmpty()){
			for(RolePrivilege item:rolePrivileges){
				item.setRoleId(role.getId());
			}
		}
		privilegeDao.insertRolePrivilege(rolePrivileges);
		privilegeDao.updateRole(role);
		return 1;
	}

	public int enable(Role role) throws Exception{
		return privilegeDao.enableRole(role);
	}
	
	@Override
	public Role getRole(int id) throws Exception {
		Role role=privilegeDao.getRole(id);
		if(role!=null){
			List<Privilege> privileges = privilegeDao.listRolePrivilege(id);
			List<Privilege> allprivileges = privilegeDao.getAllPrivilege();
			for(Privilege oitem:allprivileges){
				for(Privilege uitem:privileges){
					if(oitem.getId()==uitem.getId()){
						oitem.setLayoutChecked(true);break;
					}
				}
			}
			
			role.setPrivileges(merge(allprivileges));
		}
		return role;
	}

	@Override
	public PagedResult<Role> listRole(Role role) throws Exception {
		
		if(role.getPageSize()==0)role.setPageSize(10);
		role.setStartIndex((role.getPageNo()-1)*role.getPageSize());
		List<Role> roles=privilegeDao.listRole(role);
		int total=privilegeDao.getRoleTotal(role);
		
		if(roles.size()>0){
			int ids[]=new int[roles.size()];
			for(int i=0;i<roles.size();i++){
				ids[i]=roles.get(i).getId();
			}
			
			List<Map> c1=privilegeDao.listRolePrivilegeCount(ids);
			List<Map> c2=privilegeDao.listRoleUserCount(ids);
			
			for(Role item:roles){
				for(Map i1:c1){
					
					if(item.getId()==(int)i1.get("role_id")){
						item.setPrivilegeCount(((Long)i1.get("count")).intValue());
						break;
					}
				}
				for(Map i2:c2){
					if(item.getId()==(int)i2.get("role_id")){
						item.setUserCount(((Long)i2.get("count")).intValue());
						break;
					}
				}
			}
		}
		PagedResult<Role> result = new PagedResult<Role>();
        result.setPageNo(role.getPageNo());
        result.setPageSize(role.getPageSize());
        result.setDataList(roles);
        result.setTotal(total);
        result.setPages((total/role.getPageSize())+1);
		return result;
	}

	@Override
	public int insertRoleUser(RoleUser roleUser) throws Exception {
		List<RoleUser> roleUsers=new ArrayList();
		if(roleUser.getIds()!=null&&roleUser.getIds().length()>0){
			String ids[]=roleUser.getIds().split(",");
			for(int i=0;i<ids.length;i++){
				if(ids[i].length()==0) continue;
				RoleUser item=new RoleUser(roleUser.getRoleId(),Integer.parseInt(ids[i]));
				if(privilegeDao.getRoleUser(item)==0)
					roleUsers.add(item);
			}
		}else{
			if(privilegeDao.getRoleUser(roleUser)==0)
			roleUsers.add(roleUser);
		}
		if(roleUsers.size()>0){
			return privilegeDao.insertRoleUser(roleUsers);
		}else
			return 0;
	}

	@Override
	public int delRoleUser(RoleUser roleUser) throws Exception {
		return privilegeDao.delRoleUser(roleUser);
	}

	@Override
	public PagedResult<Map> listRoleUser(RoleUser roleUser) throws Exception {
		
		if(roleUser.getPageSize()==0)roleUser.setPageSize(10);
		roleUser.setStartIndex((roleUser.getPageNo()-1)*roleUser.getPageSize());
		List<Map> users= privilegeDao.listRoleUser(roleUser);
		int total=privilegeDao.getRoleUserTotal(roleUser);
		PagedResult<Map> result = new PagedResult<Map>();
        result.setPageNo(roleUser.getPageNo());
        result.setPageSize(roleUser.getPageSize());
        result.setDataList(users);
        result.setTotal(total);
        result.setPages((total/roleUser.getPageSize())+1);
		return result;
		
	}

}
