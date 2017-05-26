package com.lili.access.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.cms.constant.Constant;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.EncryptUtil;
import com.lili.cms.util.WebUtil;
import com.lili.privilege.model.Privilege;
import com.lili.privilege.service.PrivilegeService;
import com.lili.school.service.CMSSchoolService;
import com.lili.user.model.Permission;
import com.lili.user.model.User;
import com.lili.user.service.CMSPermissionService;
import com.lili.user.service.CMSUserService;

@Controller
@Scope("prototype")
@RequestMapping("/login")
public class LoginController extends BaseController{
	
	@Autowired
	private CMSUserService cmsUserService;
	@Autowired
	private CMSSchoolService cmsSchoolService;
	@Autowired
	private CMSPermissionService cmsPermissionService;
	@Autowired
	private PrivilegeService privilegeService;
	
	@RequestMapping(value="/check", method=RequestMethod.POST)
	@ResponseBody
    public String check(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			final String _account = getParamStr(request, "account");
			access.info(" account: " + _account + " try to login");
			
			User user0 = cmsUserService.findByAccount(_account);
			if(user0 == null){
				access.info("login:failed" +  "|" + "account:" + _account + " does not exists");

				return errorMessage(response);
			}else {
				return userLoginCheck(request, response, user0);
			}
		} catch (UnsupportedEncodingException e) {
			return new ResponseMessage().build();
		}
    }


	@RequestMapping(value="/logout", method=RequestMethod.POST)
	@ResponseBody
    public String logout(HttpServletRequest request, HttpServletResponse response){
    	HttpSession session = request.getSession();
		session.removeAttribute(Constant.USER_SESSION);
		session.removeAttribute(Constant.SCHOOL_SESSION);
		
		Cookie loginCookie = new Cookie(WebUtil.COOKIE_ACCOUNT_STR, Constant.BLANK_STR);
		loginCookie.setMaxAge(WebUtil.COOKIES_LOGOUT_SECONDS);
		loginCookie.setPath(WebUtil.getContextPath(request) + "/");
		response.addCookie(loginCookie);

		final String resp = new ResponseMessage()
								.build();
		access.debug(resp);
		return resp;
	}

	private String userLoginCheck(HttpServletRequest request,
			HttpServletResponse response, User user0) throws UnsupportedEncodingException {
		if(user0.getSchoolId() != 0){
			access.info("login:failed" +  "|" + "account:" +  getParamStr(request, "account") + " belongs to jx");
			return errorMessage(response);
		}
		if(user0.getEnabled()!=null&&user0.getEnabled()==1){
			access.info("login:failed" +  "|" + "account:" +  getParamStr(request, "account") + " disabled");
			return errorMessage(response);
		}
		//经过双重加密的密码
		String finalPassword = user0.getPassword();
		String originPassword = getParamStr(request, "password");
		
		//比较密码
		if(finalPassword.equals(buildEncryptedPassword(originPassword,finalPassword))){
			access.info("login:success" +  "|" + "userName:" + user0.getUserName());
			
			storeLoginStatus(request, response, user0);
			final String resp = new ResponseMessage()
									.build();
			access.debug(resp);
			return resp;
		}else {
			access.info("login:failed" +  "|" + "account:" + getParamStr(request, "account") + " ,wrong password");
			return errorMessage(response);
		}
	}


	private String errorMessage(HttpServletResponse response) {
		response.setStatus(WebUtil.ERROR_COCE);  
		final String resp = new ResponseMessage(WebUtil.ERROR_STR)
								.build();
		access.debug(resp);
		return resp;
	}
	

	/**
	 * 登录成功后,将登录信息存储在Session和Cookies中
	 * @param request
	 * @param response
	 * @param user
	 * @throws UnsupportedEncodingException 
	 */
	private void storeLoginStatus(HttpServletRequest request,
			HttpServletResponse response, User user) throws UnsupportedEncodingException {
		
		Cookie loginCookie = new Cookie(WebUtil.COOKIE_ACCOUNT_STR, getPassword(request,user.getPassword(),user.getId()));
		loginCookie.setMaxAge(WebUtil.COOKIES_EXPIRY_SECONDS);
		loginCookie.setPath(WebUtil.getContextPath(request) + "/");
		response.addCookie(loginCookie);
		
		try {
			loadSession(request, user);
		} catch (Exception e) {
			access.error(" exception when loadSession , e : " + e.getMessage());
		}
	}


	private void loadSession(HttpServletRequest request, User user) throws Exception {
		String schoolName = cmsSchoolService.findSchoolNameById(user.getSchoolId());
		HashMap<Object, Object> idUrlMap = new HashMap<Object, Object>();
		HashMap<String, Object> urlIdMap = new HashMap<String, Object>();
		List<Permission> permissions = cmsPermissionService.getInterfaceByUserId(user.getId());
		List<Privilege> privileges= privilegeService.getUserPrivilegeList((int)user.getId());
		user.setPrivileges(privileges);
		if(permissions != null && permissions.size() > 0){
			for (int i = 0; i < permissions.size(); i++) {
				Permission permission = permissions.get(i);
				idUrlMap.put(permission.getId(), permission.getUrl());
				urlIdMap.put(permission.getUrl(), permission.getId());
			}
		}

    	HttpSession session = request.getSession();
    	session.setAttribute(Constant.USER_SESSION, user); 
    	session.setAttribute(Constant.SCHOOL_SESSION, schoolName); 
		session.setAttribute(Constant.PERMISSION_IDURL_SESSION, idUrlMap);
		session.setAttribute(Constant.PERMISSION_URLID_SESSION, urlIdMap);
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
	
	/**
	 * SHA1 加密一次密码,并返回给Cookies
	 * @param pwd
	 * @param id
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private String getPassword(HttpServletRequest request,String pwd,long id) throws UnsupportedEncodingException{
		String ramdon = pwd.substring(pwd.length()-6, pwd.length());
		String orignPwd = getParamStr(request, "password");
		return EncryptUtil.SHA1(orignPwd + ramdon) + "*" + id;
	}
	
}
