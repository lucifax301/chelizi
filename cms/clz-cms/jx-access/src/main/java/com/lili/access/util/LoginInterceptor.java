package com.lili.access.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lili.cms.constant.Constant;
import com.lili.cms.constant.HttpConstant;
import com.lili.cms.entity.DevProperties;
import com.lili.cms.util.EncryptUtil;
import com.lili.cms.util.WebUtil;
import com.lili.user.model.Permission;
import com.lili.user.model.User;
import com.lili.user.service.CMSPermissionService;
import com.lili.user.service.CMSUserService;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private CMSUserService cmsUserService;
//	@Autowired
//	private SchoolManager schoolManager;
	@Autowired
	private CMSPermissionService cmsPermissionService;
	
	private List<String> excludedUrls;

	@Resource(name="devProperties")
	private DevProperties devProperties;

	protected final Logger access = Logger.getLogger(this.getClass());
	
	/**
	 * 返回true,则跳过拦截
	 * 返回false的时候,向前台发回消息
	 */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	String url = request.getRequestURL().toString();
    	for (String s : excludedUrls) {	
    		if (url.contains(s)) {
    			return true;
    		}
    	}


    	if(devProperties.getDev().equals("0")){
    		User user0 = cmsUserService.findByAccount("shengang");
        	HttpSession session = request.getSession();
        	session.setAttribute(Constant.USER_SESSION, user0); 
        	session.setAttribute(Constant.SCHOOL_SESSION, "深港"); 
        	return true;
    	}else {
        	return autoLogin(request, response);
		}

    }

	private boolean autoLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		access.debug("|user " + AccessWebUtil.currentUserLogInfo(request) + "|request url : " + request.getRequestURI());
    	//session中没有保存登录信息
    	if (AccessWebUtil.getSessionUser(request) == null) {

    		if(autoLogin(request)){
//    			return permissionCheck(response, request);
    			return true;
    		}else {
    			replyErrorMessage(response,HttpConstant.NEED_LOGIN_COCE,"自动登录失败");
    			return false ;
    		}

    	}else{
//			return permissionCheck(response, request);
			return true;
    	}
	}

    /**
     * 
     * @param response
     * @param url 请求url
     * @param session
     * @return
     * @throws IOException
     */
	/*private boolean permissionCheck(HttpServletResponse response,HttpServletRequest request) throws IOException {
    	String url = request.getRequestURI().substring(request.getContextPath().length(),request.getRequestURI().length());
		//从Session中取出用户所拥有的权限url列表,比对当前请求的url
		@SuppressWarnings("unchecked")
		HashMap<String, Object> urlMap = (HashMap<String, Object>)AccessWebUtil.getSessionAttObj(request,Constant.PERMISSION_URLID_SESSION);
		if(urlMap != null && urlMap.get(url.trim()) == null){
			replyErrorMessage(response,"没有操作权限");
			return false;
		}
		return true;
	}*/

	private void replyErrorMessage(HttpServletResponse response,int code,String errorMsg) throws IOException {
		response.setStatus(WebUtil.ERROR_COCE);  
		AccessWebUtil.replyErrorMessage(response,code,errorMsg);
	}

    private boolean autoLogin(HttpServletRequest request) {

    	Cookie accountCookie = AccessWebUtil.getCookieByName(request, AccessWebUtil.COOKIE_ACCOUNT_STR);
    	if(accountCookie != null){
    		String cookieValue = accountCookie.getValue();
    		String pwd1 = cookieValue.substring(0, cookieValue.indexOf("*"));
    		String userId = cookieValue.substring(cookieValue.indexOf("*") + 1, cookieValue.length());

    		User user = cmsUserService.findById(Long.valueOf(userId));

    		String ramdon = user.getPassword().substring(user.getPassword().length() - 6, user.getPassword().length());
    		String pwd2 = EncryptUtil.SHA1(pwd1 + ramdon + ramdon) + "$" + ramdon;

    		if(pwd2.equals(user.getPassword())){

    			loadSession(request, user);
    			
    			access.info(AccessWebUtil.currentUserLogInfo(request) + "' auto login success ");
    			return true;
    		}
    	}

    	return false;
    }
    


	private void loadSession(HttpServletRequest request, User user) {
//		String schoolName = schoolManager.getSchoolNameById(user.getSchoolId());
		HashMap<Object, Object> idUrlMap = new HashMap<Object, Object>();
		HashMap<String, Object> urlIdMap = new HashMap<String, Object>();
		List<Permission> permissions = cmsPermissionService.getInterfaceByUserId(user.getId());
		if(permissions != null && permissions.size() > 0){
			for (int i = 0; i < permissions.size(); i++) {
				Permission permission = permissions.get(i);
				idUrlMap.put(permission.getId(), permission.getUrl());
				urlIdMap.put(permission.getUrl(), permission.getId());
			}
		}

    	HttpSession session = request.getSession();
    	session.setAttribute(Constant.USER_SESSION, user); 
//    	session.setAttribute(Constant.SCHOOL_SESSION, schoolName); 
		session.setAttribute(Constant.PERMISSION_IDURL_SESSION, idUrlMap);
		session.setAttribute(Constant.PERMISSION_URLID_SESSION, urlIdMap);
	}

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
    
    public List<String> getExcludedUrls() {
        return excludedUrls;
    }

    public void setExcludedUrls(List<String> excludedUrls) {
        this.excludedUrls = excludedUrls;
    }
}