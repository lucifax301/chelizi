package com.lili.user.manager.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.user.manager.CMSRoleManager;
import com.lili.user.mapper.dao.RoleMapper;
import com.lili.user.model.Role;
import com.lili.user.model.RoleBDTO;

public class CMSRoleManagerImpl implements CMSRoleManager{

	@Autowired
	RoleMapper roleMapper;
	
	@Override
	public Role findOne(Integer id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public PagedResult<Role> findBatch(RoleBDTO dto) {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult((roleMapper.findBatch(dto)));
	}

	@Override
	public boolean insertOne(Role role) {
		return roleMapper.insert(role) > 0;
	}

	@Override
	public boolean updateOne(Role role) {
		return roleMapper.updateByPrimaryKey(role) > 0;
	}

	@Override
	public boolean allotRole(List<Long> roleIds, Integer userId) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("roleIds", roleIds);
		params.put("userId", userId);
		Integer count = roleMapper.allotRole(params);
		return (count != null && count > 0);
	}

	@Override
	public boolean allotPermission(List<Long> permissionIds, Integer roleId) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("permissionIds", permissionIds);
		params.put("roleId", roleId);
		Integer count =  roleMapper.allotPermission(params);
		return (count != null && count > 0);
	}

}
