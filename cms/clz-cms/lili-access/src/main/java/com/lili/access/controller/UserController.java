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

import com.lili.access.util.AccessWebUtil;
import com.lili.cms.constant.Constant;
import com.lili.cms.constant.HttpConstant;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.EncryptUtil;
import com.lili.user.model.User;
import com.lili.user.model.UserBDTO;
import com.lili.user.service.CMSUserService;

@Controller
@Scope("prototype")
@RequestMapping("/user")
public class UserController extends BaseController{

	@Autowired
	private CMSUserService cmsUserService;
	

	@RequestMapping("/verify")
	@ResponseBody
    public String verify(HttpServletRequest request, HttpServletResponse response){
		final String resp = new ResponseMessage()
								.addResult("userName",  AccessWebUtil.getSessionUser(request)==null?"": AccessWebUtil.getSessionUser(request).getAccount())
								.addResult("schoolName", AccessWebUtil.getSessionAttStr(request, Constant.SCHOOL_SESSION))
								.build();
		access.debug(resp);
		return resp;
	}
	

	@RequestMapping("/verify-password")
	@ResponseBody
    public String verifyPassword(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		User user = AccessWebUtil.getSessionUser(request);
		String password = getParamStr(request, "password");
		access.info(" !!! pwd = " + password + " !!!");

		//比较密码
		if(!user.getPassword().equals(buildEncryptedPassword(password,user.getPassword()))){
			final String resp = new ResponseMessage(HttpConstant.ERROR_TIP_CODE,AccessWebUtil.ERROR_STR)
									.build();
			access.debug(resp);
			return resp;
		}
		
		final String resp = new ResponseMessage()
								.build();
		access.debug(resp);
		return resp;
	}
	

	@RequestMapping("/update-password")
	@ResponseBody
    public String updatePassword(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		User user = AccessWebUtil.getSessionUser(request);
		String password = getParamStr(request, "password");
		if(password.length() < 6 || password.length() > 16){
			final String resp = new ResponseMessage(AccessWebUtil.ERROR_STR)
									.build();
			access.debug(resp);
			return resp;
		}
		
		user.setPassword(EncryptUtil.DSHA1(password));
		return cmsUserService.updatePassword(user).build();
	}


	/**
	 * 由pwd0原始密码双重加密
	 * @param pwd0 原始密码
	 * @param pwd2 数据库存储的双重加密的密码
	 * @return
	 */
	private String buildEncryptedPassword(String pwd0,String pwd2){
		return EncryptUtil.DSHA1(pwd0, getRamdon(pwd2));
	}
	

	/**
	 * 通过数据库密码获取随机数
	 * @param enPwd 数据库存储的双重加密的密码
	 * @return
	 */
	private String getRamdon(String enPwd){
		return enPwd.substring(enPwd.length()-6, enPwd.length());
	}
	
	

	@RequestMapping(value="/add", method=RequestMethod.POST)
    @ResponseBody
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			User ept = (User) 
					buildObject(request, User.class);
			User user = AccessWebUtil.getSessionUser(request);
			ept.setCreator(user.getAccount());
			ept.setEnabled(0);
			ept.setSchoolId(0L);
			ept.setPassword(EncryptUtil.DSHA1(ept.getPassword()));
			return cmsUserService.insertOne(ept).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/update", method= RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			User user = AccessWebUtil.getSessionUser(request);
			User role = (User)buildObject(request, User.class);
			role.setUpdator(user.getAccount());
			return cmsUserService.updateOne(role).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
	/**
	 * 停用账户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cancle", method= RequestMethod.POST)
    @ResponseBody
    public String cancle(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			User user = AccessWebUtil.getSessionUser(request);
			return cmsUserService.cancle(getParamLong(request, "userId"),user.getAccount()).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
	/**
	 * 激活账户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/active", method= RequestMethod.POST)
    @ResponseBody
    public String active(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			User user = AccessWebUtil.getSessionUser(request);
			return cmsUserService.active(getParamLong(request, "userId"),user.getAccount()).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
	@RequestMapping(value="/findUser", method= RequestMethod.POST)
    @ResponseBody
    public String findUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			UserBDTO user=(UserBDTO)buildObject(request,UserBDTO.class);
			PagedResult<User> result= cmsUserService.findUser(user);
			return new ResponseMessage().addResult("pageData", result).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
	@RequestMapping(value="/findAvailUser", method= RequestMethod.POST)
    @ResponseBody
    public String findAvailUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			UserBDTO user=(UserBDTO)buildObject(request,UserBDTO.class);
			PagedResult<User> result= cmsUserService.findAvailUser(user);
			return new ResponseMessage().addResult("pageData", result).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
}
