package com.lili.access.util;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lili.cms.constant.HttpConstant;
import com.lili.cms.entity.DevProperties;
import com.lili.cms.util.WebUtil;
import com.lili.privilege.model.Privilege;
import com.lili.user.model.User;
import com.lili.user.service.CMSUserService;

/*
 * 权限拦截器
 */
public class PrivilageInterceptor extends HandlerInterceptorAdapter{

	protected final Logger access = Logger.getLogger(this.getClass());
	
	@Resource(name="devProperties")
	private DevProperties devProperties;
	
	private List<String> excludedUrls;
	
	@Autowired
	private CMSUserService cmsUserService;
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURL().toString();
		
		for (String s : excludedUrls) {	
    		if (url.contains(s)) {
    			return true;
    		}
    	}
		
		User user=AccessWebUtil.getSessionUser(request) ;
		access.debug("************************************Privilege check："+user);
		if(devProperties.getDev().equals("0")){
			return true;
		}
		if (user != null) {
			
			User exist = cmsUserService.findByAccount(user.getAccount());
			if(exist.getEnabled()!=null&&exist.getEnabled()==1){
				access.debug("***********************************account disabled:"+exist.getAccount());
				replyBlockMessage(response,HttpConstant.NO_AUTH_COCE,"帐号停用");
				return false;
			}
			
			List<Privilege> privileges= user.getPrivileges();	
			if(privileges==null) {
				replyErrorMessage(response,HttpConstant.NO_AUTH_COCE,"没有权限");
				return false;
			
			}
			for (Privilege p : privileges) {
				if (p.getAjax()!=null&&p.getAjax().length()>1) {
					String s[]=p.getAjax().split(",");
					for(int i=0;i<s.length;i++){
						
						if(s[i].length()>0&&url.endsWith(s[i])){
							access.debug("***********************************"+user.getAccount()+" has privilege for "+url);
							return true;
						}
					}
	    		}
	    	}
			access.debug("***********************************"+user.getAccount()+" has not privilege for "+url);
			replyErrorMessage(response,HttpConstant.NO_AUTH_COCE,"没有权限");
			
			return false;
    	}else{
    		replyErrorMessage(response,HttpConstant.NO_AUTH_COCE,"没有权限");
    		return false;
    	}
    	
	}
	
	private void replyErrorMessage(HttpServletResponse response,int code,String errorMsg) throws IOException {
		response.setStatus(WebUtil.SUCCESS_COCE);  
		AccessWebUtil.replyErrorMessage(response,code,errorMsg);
	}
	
	private void replyBlockMessage(HttpServletResponse response,int code,String errorMsg) throws IOException {
		response.setStatus(WebUtil.ERROR_COCE);  
		AccessWebUtil.replyErrorMessage(response,code,errorMsg);
	}
	
	public List<String> getExcludedUrls() {
        return excludedUrls;
    }

    public void setExcludedUrls(List<String> excludedUrls) {
        this.excludedUrls = excludedUrls;
    }
}
