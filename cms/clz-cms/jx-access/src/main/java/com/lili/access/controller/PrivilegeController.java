package com.lili.access.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lili.access.util.AccessWebUtil;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.LogUtil;
import com.lili.privilege.model.Privilege;
import com.lili.privilege.model.Role;
import com.lili.privilege.model.RoleUser;
import com.lili.privilege.service.PrivilegeService;
import com.lili.user.model.User;

/*
 * 用户权限
 */
@Controller
@RequestMapping("/privilege")
public class PrivilegeController extends BaseController {

	Logger logger = Logger.getLogger(PrivilegeController.class);
	
	@Autowired
	private PrivilegeService privilegeService;
	
	@RequestMapping(value="/allprivilege", method=RequestMethod.GET)
    @ResponseBody
    public String allprivilege(Role role,HttpServletRequest request,HttpServletResponse response) {
		ResponseMessage<List<Privilege>> message=new ResponseMessage();
		try{
			List<Privilege> privileges=privilegeService.getAllPrivilege();
			message.setData(privileges);
		}catch(Exception ex){
			logger.warn(LogUtil.getStackMsg(ex));
			message.setMsg("error");
			message.setCode(400);
		}
		logger.debug("*******************************"+message);
		return JSON.toJSONString(message,SerializerFeature.DisableCircularReferenceDetect);
		//return message;
	}
	
	@RequestMapping(value="/addRole", method=RequestMethod.POST)
    @ResponseBody
    //public ResponseMessage addRole(Role role,HttpServletRequest request,HttpServletResponse response) {
    public String addRole(Role role,HttpServletRequest request,HttpServletResponse response) {	
		ResponseMessage message=new ResponseMessage();
		try{
			User user = AccessWebUtil.getSessionUser(request);
			role.setCreator(user.getAccount());
			int result=privilegeService.insertRole(role);
		}catch(Exception ex){
			logger.warn(LogUtil.getStackMsg(ex));
			message.setMsg("error");
			message.setCode(400);
		}
		//return message;
		return JSON.toJSONString(message);
	}
	
	@RequestMapping(value="/updateRole", method=RequestMethod.POST)
    @ResponseBody
    public String updateRole(Role role,HttpServletRequest request,HttpServletResponse response) {
		ResponseMessage message=new ResponseMessage();
		try{
			User user = AccessWebUtil.getSessionUser(request);
			role.setUpdator(user.getAccount());
			role.setUpdateTime(new java.util.Date());
			int result=privilegeService.updateRole(role);
		}catch(Exception ex){
			logger.warn(LogUtil.getStackMsg(ex));
			message.setMsg("error");
			message.setCode(400);
		}
		//return message;
		return JSON.toJSONString(message);
	}
	
	@RequestMapping(value="/delRole", method=RequestMethod.POST)
    @ResponseBody
    public String delRole(Role role,HttpServletRequest request,HttpServletResponse response) {
		ResponseMessage message=new ResponseMessage();
		try{
			int result=privilegeService.delRole(role);
		}catch(Exception ex){
			logger.warn(LogUtil.getStackMsg(ex));
			message.setMsg("error");
			message.setCode(400);
		}
		//return message;
		return JSON.toJSONString(message);
	}
	
	@RequestMapping(value="/activeRole", method=RequestMethod.GET)
    @ResponseBody
    public String activeRole(Role role,HttpServletRequest request,HttpServletResponse response) {
		ResponseMessage message=new ResponseMessage();
		try{
			role.setEnabled(0);
			User user = AccessWebUtil.getSessionUser(request);
			role.setUpdator(user.getAccount());
			role.setUpdateTime(new java.util.Date());
			int result=privilegeService.enable(role);
		}catch(Exception ex){
			logger.warn(LogUtil.getStackMsg(ex));
			message.setMsg("error");
			message.setCode(400);
		}
		//return message;
		return JSON.toJSONString(message);
	}
	
	@RequestMapping(value="/inactiveRole", method=RequestMethod.GET)
    @ResponseBody
    public String inactiveRole(Role role,HttpServletRequest request,HttpServletResponse response) {
		ResponseMessage message=new ResponseMessage();
		try{
			role.setEnabled(1);
			User user = AccessWebUtil.getSessionUser(request);
			role.setUpdator(user.getAccount());
			role.setUpdateTime(new java.util.Date());
			int result=privilegeService.enable(role);
		}catch(Exception ex){
			logger.warn(LogUtil.getStackMsg(ex));
			message.setMsg("error");
			message.setCode(400);
		}
		//return message;
		return JSON.toJSONString(message);
	}
	
	@RequestMapping(value="/getRole", method=RequestMethod.GET)
    @ResponseBody
    public String getRole(Role role,HttpServletRequest request,HttpServletResponse response) {
		ResponseMessage<Role> message=new ResponseMessage();
		try{
			Role roledata=privilegeService.getRole(role.getId());
			message.setData(roledata);
		}catch(Exception ex){
			logger.warn(LogUtil.getStackMsg(ex));
			message.setMsg("error");
			message.setCode(400);
		}
		//return message;
		return JSON.toJSONString(message);
	}
	
	@RequestMapping(value="/getUserPrivilege", method=RequestMethod.GET)
    @ResponseBody
    public String getUserPrivilege(Role role,HttpServletRequest request,HttpServletResponse response) {
		ResponseMessage<List<Privilege>> message=new ResponseMessage();
		User user = AccessWebUtil.getSessionUser(request);
		try{
			List<Privilege> privileges=privilegeService.getUserPrivilege((int)user.getId());
			message.setData(privileges);
		}catch(Exception ex){
			logger.warn(LogUtil.getStackMsg(ex));
			message.setMsg("error");
			message.setCode(400);
		}
		//return message;
		return JSON.toJSONString(message);
	}
	
	@RequestMapping(value="/listRole", method=RequestMethod.POST)
    @ResponseBody
    public String listRole(Role role,HttpServletRequest request,HttpServletResponse response) {
		ResponseMessage<PagedResult<Role>> message=new ResponseMessage();
		try{
			PagedResult<Role> data=privilegeService.listRole(role);
			message.setData(data);
		}catch(Exception ex){
			logger.warn(LogUtil.getStackMsg(ex));
			message.setMsg("error");
			message.setCode(400);
		}
		//return message;
		return JSON.toJSONString(message);
	}
	
	@RequestMapping(value="/addRoleUser", method=RequestMethod.POST)
    @ResponseBody
    public String addRoleUser(RoleUser roleUser,HttpServletRequest request,HttpServletResponse response) {
		ResponseMessage message=new ResponseMessage();
		try{
			int result=privilegeService.insertRoleUser(roleUser);
		}catch(Exception ex){
			logger.warn(LogUtil.getStackMsg(ex));
			message.setMsg("error");
			message.setCode(400);
		}
		//return message;
		return JSON.toJSONString(message);
	}
	
	@RequestMapping(value="/delRoleUser", method=RequestMethod.POST)
    @ResponseBody
    public String delRoleUser(RoleUser roleUser,HttpServletRequest request,HttpServletResponse response) {
		ResponseMessage message=new ResponseMessage();
		try{
			int result=privilegeService.delRoleUser(roleUser);
		}catch(Exception ex){
			logger.warn(LogUtil.getStackMsg(ex));
			message.setMsg("error");
			message.setCode(400);
		}
		//return message;
		return JSON.toJSONString(message);
	}
	
	@RequestMapping(value="/listRoleUser", method=RequestMethod.POST)
    @ResponseBody
    public String listRoleUser(RoleUser roleUser,HttpServletRequest request,HttpServletResponse response) {
		ResponseMessage<PagedResult<Map>> message=new ResponseMessage();
		try{
			PagedResult<Map> data=privilegeService.listRoleUser(roleUser);
			message.setData(data);
		}catch(Exception ex){
			logger.warn(LogUtil.getStackMsg(ex));
			message.setMsg("error");
			message.setCode(400);
		}
		//return message;
		return JSON.toJSONString(message);
	}
	
	/**
	 * 获取用户权限列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getUserMenu", method=RequestMethod.GET)
    @ResponseBody
    public String getUserMenu(HttpServletRequest request,HttpServletResponse response) {
		ResponseMessage<String> message=new ResponseMessage();
		User user = AccessWebUtil.getSessionUser(request);
		try{
			List<Privilege> privileges=privilegeService.getUserPrivilege((int)user.getId());
			//StringBuilder builder=new StringBuilder();
			JSONObject menujson=buildJson(privileges);
			message.setData(menujson.toJSONString());
		}catch(Exception ex){
			logger.warn(LogUtil.getStackMsg(ex));
			message.setMsg("error");
			message.setCode(400);
		}
		//return message;
		return JSON.toJSONString(message);
	}
	
	private JSONObject buildJson(List<Privilege> privileges){
		JSONObject menujson=new JSONObject();
		JSONObject parjson=new JSONObject();
		JSONArray listjson=new JSONArray();
		JSONArray btnjson=new JSONArray();
		for(Privilege p1:privileges){
			JSONObject json=new JSONObject();
			json.put("name", p1.getName());
			json.put("icon", p1.getIcon());
			json.put("src", p1.getUrl());
			json.put("menuOrder", p1.getMenuOrder());
			json.put("id", p1.getId());
			
			List<Privilege> level2=p1.getPrivileges();
			if(level2!=null&&level2.size()>0){
				parjson.put(p1.getId()+"", json);
				for(Privilege p2:level2){
					JSONObject level2json=new JSONObject();
					level2json.put("id", p2.getId());
					level2json.put("parenName", p2.getPid());
					level2json.put("name", p2.getName());
					level2json.put("icon", p2.getIcon());
					level2json.put("src", p2.getUrl());
					level2json.put("menuOrder", p2.getMenuOrder());
					listjson.add(level2json);
					
					List<Privilege> level3=p2.getPrivileges();
					if(level3!=null&&level3.size()>0){
						for(Privilege p3:level3){
							JSONObject level3json=new JSONObject();
							level3json.put("id", p3.getId());
							level3json.put("name", p3.getName());
							btnjson.add(level3json);
						}
					}
				}
			}else{
				listjson.add(json);
			}
		}
		menujson.put("par", parjson);
		menujson.put("list", listjson);
		menujson.put("btn", btnjson);
		return menujson;
	}
}

