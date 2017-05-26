package com.lili.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.user.manager.CMSPermissionManager;
import com.lili.user.model.Permission;
import com.lili.user.model.PermissionBDTO;
import com.lili.user.service.CMSPermissionService;

public class CMSPermissionServiceImpl implements CMSPermissionService{

	@Autowired
	CMSPermissionManager cmsPermissionManager;
	
	@Override
	public List<Permission> getInterfaceByUserId(Long userId) {
		return  cmsPermissionManager.getInterfaceByUserId(userId);
	}

	@Override
	public List<Permission> getMenuByUserId(PermissionBDTO dto) {
		return cmsPermissionManager.getMenuByUserId(dto);
	}

	@Override
	public List<Permission> getBtnList(PermissionBDTO dto) {
		return cmsPermissionManager.getBtnList(dto);
	}

	@Override
	public ResponseMessage findBatch(PermissionBDTO dto) {
		PagedResult<Permission> batch = cmsPermissionManager.findBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage insertOne(Permission record) {
		if(cmsPermissionManager.insertOne(record)){
			return new ResponseMessage();
		}
		return new ResponseMessage("插入失败");
	}

	@Override
	public ResponseMessage updateOne(Permission record) {
		if(cmsPermissionManager.updateOne(record)){
			return new ResponseMessage();
		}
		return new ResponseMessage("更新失败");
	}

	@Override
	public ResponseMessage findOne(Integer id) {
		return new ResponseMessage().addResult("pageData", cmsPermissionManager.selectByPrimaryKey(id));
	}


}
