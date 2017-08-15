package com.lili.access.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

import com.lili.access.util.AccessWebUtil;
import com.lili.cms.constant.Constant;
import com.lili.cms.constant.LogConstant;
import com.lili.cms.entity.DevProperties;
import com.lili.cms.util.DateUtil;
import com.lili.cms.util.NetworkUtil;
import com.lili.cms.util.ReflectUtil;
import com.lili.cms.util.StringUtil;
import com.lili.cms.util.WebUtil;
import com.lili.log.model.LogCommon;
import com.lili.user.model.User;


public class BaseController {

	protected static final Logger access = Logger.getLogger("Controller");


	@Resource(name="devProperties")
	private DevProperties devProperties;
	
	/**
	 * 数据操作时的日志记录
	 * @param request
	 * @param menuId
	 * @param action
	 * @return 数据日志实体
	 * @throws UnsupportedEncodingException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected LogCommon initLogParams(HttpServletRequest request,int menuId,int action) throws UnsupportedEncodingException, InstantiationException, IllegalAccessException {
		final LogCommon logCommon = new LogCommon();
		logCommon.setMenuId(menuId);
		logCommon.setAction(action);
		logCommon.setOperateTime(DateUtil.now());
		logCommon.setChannel(LogConstant.CHANNEL_COACH);
		logCommon.setStatus(1);
		logCommon.setUserId(AccessWebUtil.getSessionUser(request).getId()+"");
		logCommon.setUserName(AccessWebUtil.getSessionUser(request).getUserName());
		logCommon.setIp(NetworkUtil.getIpAddress(request));

		return logCommon;
	}

	protected String getParamStr(HttpServletRequest request,String k) throws UnsupportedEncodingException{
		return getParam(request, k);
	}


	protected int getParamInt(HttpServletRequest request,String k) throws UnsupportedEncodingException{
		return StringUtil.isNull(request.getParameter(k))?0:Integer.valueOf(request.getParameter(k));
	}

	protected long getParamLong(HttpServletRequest request,String k) throws UnsupportedEncodingException{
		return StringUtil.isNull(request.getParameter(k))?0:Long.valueOf(request.getParameter(k));
	}


	protected Date getParamDate(HttpServletRequest request,String k) throws UnsupportedEncodingException, ParseException{
		return StringUtil.isNull(request.getParameter(k))?DateUtil.parseDate("2099-12-31")
				:DateUtil.parseDatetime(request.getParameter(k).toString());
	}

	/**
	 * 将请求中的参数封装成对象
	 * @param request
	 * @throws UnsupportedEncodingException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	protected Object buildObject(HttpServletRequest request,Class<?> clz) throws UnsupportedEncodingException, InstantiationException, IllegalAccessException{
		Object object = clz.newInstance();

		Map<String, Object> params = new HashMap<String, Object>();
		Enumeration<?> parameterNames = request.getParameterNames();
		while(parameterNames.hasMoreElements()){
			String key = (String) parameterNames.nextElement();
			String value = getParam(request, key);
			params.put(key, value); 
		}
		User currentUser = AccessWebUtil.getSessionUser(request);
		params.put("cmsChannel", Constant.JX_CHANNEL); 
		params.put("schoolId", currentUser.getSchoolId());

		return ReflectUtil.fillObject(object, params);
	}


	/**
	 * 发送Excel文件
	 * @param response
	 * @param wb 包装好导出的数据源
	 * @param name 文件名,此方法中会自动补齐后缀
	 * @throws IOException
	 */
	protected void sendExcel(HttpServletResponse response, Workbook wb,String name) throws IOException {
		String fileName =  new String(name.getBytes("UTF-8"),"ISO8859-1")  
		+ DateUtil.getCurrentMillis() + ".xlsx";
		WebUtil.sendExcel(response, wb, fileName);
	}

	/**
	 * 以UTF-8获取request中的参数值
	 * @param request
	 * @param k
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getParam(HttpServletRequest request,String k) throws UnsupportedEncodingException{
		if(devProperties.getDev().equals("0")){
			if(!"POST".equalsIgnoreCase(request.getMethod())){
				return request.getParameter(k)==null?"":new String(request.getParameter(k).getBytes("iso8859-1"),"UTF-8");
			}else {
				return request.getParameter(k)==null?"":request.getParameter(k);
			}

		}
		return request.getParameter(k)==null?"":new String(request.getParameter(k).getBytes(request.getCharacterEncoding()),"UTF-8");

	}



}
