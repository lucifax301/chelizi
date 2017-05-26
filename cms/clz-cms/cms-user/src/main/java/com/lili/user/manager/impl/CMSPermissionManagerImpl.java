package com.lili.user.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.user.manager.CMSPermissionManager;
import com.lili.user.mapper.dao.PermissionMapper;
import com.lili.user.model.Permission;
import com.lili.user.model.PermissionBDTO;

public class CMSPermissionManagerImpl implements CMSPermissionManager{

	@Autowired
	PermissionMapper permissionMapper;
	
	@Override
	public List<Permission> getInterfaceByUserId(Long userId) {
		return permissionMapper.findInterfaceByUserId(userId);
	}

	@Override
	public List<Permission> getMenuByUserId(PermissionBDTO dto) {
		return permissionMapper.findMenuByUserId(dto);
	}

	@Override
	public List<Permission> getBtnList(PermissionBDTO dto) {
		return permissionMapper.findBtnList(dto);
	}

	@Override
	public PagedResult<Permission> findBatch(PermissionBDTO dto) {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult((permissionMapper.findBatch(dto)));
	}

	@Override
	public boolean deleteByPrimaryKey(Integer id) {
		return permissionMapper.deleteByPrimaryKey(id) > 0;
	}

	@Override
	public boolean insertOne(Permission record) {
		return permissionMapper.insert(record) > 0;
	}

	@Override
	public Permission selectByPrimaryKey(Integer id) {
		return permissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateOne(Permission record) {
		return permissionMapper.updateByPrimaryKey(record) > 0;
	}


}
