package com.lili.user.service;

import com.lili.cms.entity.ResponseMessage;
import com.lili.user.model.Role;
import com.lili.user.model.RoleBDTO;

public interface CMSRoleService {

	public  ResponseMessage findOne(Integer id);

	public  ResponseMessage findBatch(RoleBDTO dto);

	public  ResponseMessage insertOne(Role role);

	public  ResponseMessage updateOne(Role role);

	/**
	 * 给用户分配角色
	 * @param roleIds 角色id集合
	 * @param userId
	 * @return
	 */
	ResponseMessage allotRole(String roleIds,Integer userId);

	/**
	 * 给角色分配权限
	 * @param permissionIds 权限id集合
	 * @param roleId
	 * @return
	 */
	ResponseMessage allotPermission(String permissionIds,Integer roleId);
	
}
