package com.lili.user.manager;

import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.user.model.Role;
import com.lili.user.model.RoleBDTO;

public interface CMSRoleManager {

	Role findOne(Integer id);
	PagedResult<Role> findBatch(RoleBDTO dto);
	boolean insertOne(Role role);
	boolean updateOne(Role role);

	/**
	 * 给用户分配角色
	 * @param roleIds 角色id集合
	 * @param userId
	 * @return
	 */
	boolean allotRole(List<Long> roleIds,Integer userId);

	/**
	 * 给角色分配权限
	 * @param permissionIds 权限id集合
	 * @param roleId
	 * @return
	 */
	boolean allotPermission(List<Long> permissionIds,Integer roleId);
}
