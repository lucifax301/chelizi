package com.lili.access.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.user.model.Role;
import com.lili.user.model.RoleBDTO;
import com.lili.user.service.CMSRoleService;


@Controller
@Scope("prototype")
@RequestMapping("/role")
public class RoleController extends BaseController{

	@Autowired
	CMSRoleService cmsRoleService;
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
    @ResponseBody
    public String view(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsRoleService.findOne(getParamInt(request, "id")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}


	@RequestMapping(value="/batch", method= RequestMethod.GET)
    @ResponseBody
    public String batch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			RoleBDTO dto = (RoleBDTO) buildObject(request, RoleBDTO.class);
			return cmsRoleService.findBatch(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/add", method=RequestMethod.GET)
    @ResponseBody
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			Role ept = (Role) 
					buildObject(request, Role.class);
			return cmsRoleService.insertOne(ept).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/update", method= RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			Role role = (Role)buildObject(request, Role.class);
			return cmsRoleService.updateOne(role).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}

	@RequestMapping(value="/allot-role", method= RequestMethod.POST)
    @ResponseBody
    public String allotRole(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsRoleService.allotRole(getParamStr(request, "roleIds"), getParamInt(request, "userId")).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}

	@RequestMapping(value="/allot-permission", method= RequestMethod.POST)
    @ResponseBody
    public String allotPermission(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsRoleService.allotPermission(getParamStr(request, "permissionIds"), getParamInt(request, "roleId")).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
}
