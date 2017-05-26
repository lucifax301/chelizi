package com.lili.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.StringUtil;
import com.lili.user.manager.CMSRoleManager;
import com.lili.user.model.Role;
import com.lili.user.model.RoleBDTO;
import com.lili.user.service.CMSRoleService;

public class CMSRoleServiceImpl implements CMSRoleService{

	@Autowired
	CMSRoleManager cmsRoleManager;
	
	@Override
	public ResponseMessage findOne(Integer id) {
		Role role= cmsRoleManager.findOne(id);
		return new ResponseMessage().addResult("pageData", role);
	}

	@Override
	public ResponseMessage findBatch(RoleBDTO dto) {
		PagedResult<Role> batch = cmsRoleManager.findBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage insertOne(Role role) {
		if(cmsRoleManager.insertOne(role)){
			return new ResponseMessage();
		}
		return new ResponseMessage("插入失败");
	}

	@Override
	public ResponseMessage updateOne(Role role) {
		if(cmsRoleManager.updateOne(role)){
			return new ResponseMessage();
		}
		return new ResponseMessage("更新失败");
	}

	@Override
	public ResponseMessage allotRole(String roleIds, Integer userId) {
		List<Long> list = StringUtil.getIdListByStr(roleIds);
		if(cmsRoleManager.allotRole(list, userId)){
			return new ResponseMessage();
		}
		return new ResponseMessage("角色分配失败");
	}

	@Override
	public ResponseMessage allotPermission(String permissionIds, Integer roleId) {
		List<Long> list = StringUtil.getIdListByStr(permissionIds);
		if(cmsRoleManager.allotRole(list, roleId)){
			return new ResponseMessage();
		}
		return new ResponseMessage("权限分配失败");
	}

}
