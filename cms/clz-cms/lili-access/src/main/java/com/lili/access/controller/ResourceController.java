package com.lili.access.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.PicUtil;
import com.lili.user.model.Permission;
import com.lili.user.model.PermissionBDTO;
import com.lili.user.service.CMSPermissionService;
import com.lili.user.service.CMSUserService;

/**
 * 资源请求控制
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/resource")
public class ResourceController extends BaseController{

	@Autowired
	private CMSUserService cmsUserService;
	@Autowired
	private CMSPermissionService cmsPermissionService;
	
	/**
	 * 获取七牛public token
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/token", method=RequestMethod.GET)
    @ResponseBody
    public String token(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return new ResponseMessage().addResult("pageData", PicUtil.getPublicToken()).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 请求菜单列,以权限做划分
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/menu-list", method=RequestMethod.GET)
	@ResponseBody
    public String menuList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			PermissionBDTO dto = (PermissionBDTO)buildObject(request, PermissionBDTO.class);
			List<Permission> permissions = cmsPermissionService.getMenuByUserId(dto);
			StringBuffer resp = buildPermissionListStr(permissions);
//			response.getWriter().print(resp);
			return new ResponseMessage().addPagedStrResult(resp.toString()).build();
			
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
	/**
	 * 权限控制到按钮级别
	 * 请求按钮列,以权限为划分
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/btn-list")
	@ResponseBody
    public String btnList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			PermissionBDTO dto = (PermissionBDTO)buildObject(request, PermissionBDTO.class);
			List<Permission> permissions = cmsPermissionService.getBtnList(dto);
			StringBuffer resp = buildPermissionListStr(permissions);
			return new ResponseMessage().addPagedStrResult(resp.toString()).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
    @ResponseBody
    public String view(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsPermissionService.findOne(getParamInt(request, "id")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}


	@RequestMapping(value="/batch", method= RequestMethod.GET)
    @ResponseBody
    public String batch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			PermissionBDTO dto = (PermissionBDTO) buildObject(request, PermissionBDTO.class);
			return cmsPermissionService.findBatch(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/add", method=RequestMethod.GET)
    @ResponseBody
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			Permission permission = (Permission) buildObject(request, Permission.class);
			return cmsPermissionService.insertOne(permission).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	

	@RequestMapping(value="/update", method= RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			Permission permission = (Permission) buildObject(request, Permission.class);
			return cmsPermissionService.updateOne(permission).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	

	private StringBuffer buildPermissionListStr(List<Permission> permissions) {
		StringBuffer sbList = new StringBuffer();
		boolean flag=false;
		for (Permission string : permissions) {
			if (flag) {
				sbList.append(",");
			}else {
				flag=true;
			}
			sbList.append(string.getId());
		}
		System.out.println("******************************* " + sbList);
		
		return sbList;
	}
}
