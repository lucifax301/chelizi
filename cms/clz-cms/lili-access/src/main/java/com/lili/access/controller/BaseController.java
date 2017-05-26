package com.lili.access.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lili.access.util.AccessWebUtil;
import com.lili.cms.constant.Constant;
import com.lili.cms.constant.LogConstant;
import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.DevProperties;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.DateUtil;
import com.lili.cms.util.FileHandle;
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
		logCommon.setChannel(LogConstant.CHANNEL_LILI);
		logCommon.setStatus(1);
		logCommon.setUserId(AccessWebUtil.getSessionUser(request).getId()+"");
		logCommon.setUserName(AccessWebUtil.getSessionUser(request).getUserName());
		logCommon.setIp(NetworkUtil.getIpAddress(request));
		
		return logCommon;
	}

	protected String getParamStr(HttpServletRequest request,String k) throws UnsupportedEncodingException{
		return getParam(request, k);
	}
	

	protected String getParamStrOrNull(HttpServletRequest request,String k) throws UnsupportedEncodingException{
		return StringUtil.isNull(getParam(request, k))?null:getParam(request, k);
	}


	protected Integer getParamInt(HttpServletRequest request,String k) throws UnsupportedEncodingException{
		return StringUtil.isNull(request.getParameter(k))?0:Integer.valueOf(request.getParameter(k));
	}
	

	protected Integer getParamIntOrNull(HttpServletRequest request,String k) throws UnsupportedEncodingException{
		return StringUtil.isNull(request.getParameter(k))?null:Integer.valueOf(request.getParameter(k));
	}

	protected Long getParamLong(HttpServletRequest request,String k) throws UnsupportedEncodingException{
		return StringUtil.isNull(request.getParameter(k))?0:Long.valueOf(request.getParameter(k));
	}
	

	protected Long getParamLongOrNull(HttpServletRequest request,String k) throws UnsupportedEncodingException{
		return StringUtil.isNull(request.getParameter(k))?null:Long.valueOf(request.getParameter(k));
	}
	

	protected Date getParamDate(HttpServletRequest request,String k) throws UnsupportedEncodingException, ParseException{
		return StringUtil.isNull(request.getParameter(k))?DateUtil.parseDate("2099-12-31")
				:DateUtil.parseDatetime(request.getParameter(k).toString());
	}
	

	protected Date getParamDateOrNull(HttpServletRequest request,String k) throws UnsupportedEncodingException, ParseException{
		return StringUtil.isNull(request.getParameter(k))?null:DateUtil.parseDatetime(request.getParameter(k).toString());
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
//		User currentUser = AccessWebUtil.getSessionUser(request);
		params.put("cmsChannel", Constant.LILI_CHANNEL);
		
//		params.put("schoolId", currentUser.getSchoolId());

		return ReflectUtil.fillObject(object, params);
	}
	
	protected User getCurrentUser(HttpServletRequest request) throws UnsupportedEncodingException{
		return (User) WebUtil.getSessionAttObj(request, "user");
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
											+ DateUtil.getCurrentMillis() + ".xls";
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
	
	/**
	 * 获取上传的Excel文件
	 * @param request
	 * @return
	 */
	public File getUploadFile(HttpServletRequest request) {

		InputStream is = null;

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		//获得文件
		MultipartFile multipartFile = multipartRequest.getFile("filename");
		//获得文件名
		String fileName = multipartFile.getOriginalFilename();
		access.info("***************************** FileName is : " + fileName );
		//文件存放地址
		String tagFileName = request.getSession().getServletContext().getRealPath("WEB-INF");
		if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
			access.info("***************************** FileName is Error!" );
			return 	null;
		}

		String time = DateUtil.currentDatetime();
		tagFileName = tagFileName + "/uploadExcel/"+Constant.BONUS_FILE_NAME+ time + (fileName.endsWith(".xls")?".xls":".xlsx");
		access.info("************************ tagFileName :"+ tagFileName);
		//输入流
		try {
			is = multipartFile.getInputStream();
			return FileHandle.upLoadFile( is, tagFileName);
		} catch (IOException e) {
			access.error(" file import error ");
		}     finally {
			try {
				if(is != null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
