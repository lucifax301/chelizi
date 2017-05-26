package com.lili.cms.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;

import com.lili.cms.constant.HttpConstant;
import com.lili.cms.entity.ResponseMessage;

public class WebUtil {


	public static final String BLANK_STR = "";
	public static final int ERROR_COCE = 401;
	public static final int SUCCESS_COCE = 200;
	public static final String ERROR_STR = "fail";
	public static final String SUCCESS_STR = "success";
	public static final String RESPONSE_STATUS = "success";
	//Cookies过期时间
	public static final int COOKIES_EXPIRY_SECONDS = 259200;
	public static final int COOKIES_LOGOUT_SECONDS = 0;
	

	public static final String COOKIE_ACCOUNT_STR = "lilixc";
	

	public static final String REQUEST_GET = "GET";
	public static final String REQUEST_POST = "POST";
	

	public static final String UTF_8 = "UTF-8";
	
	/**
	 * 获取项目路径
	 * @param request
	 * @return
	 */
	public static String getContextPath(HttpServletRequest request){
		return request.getSession().getServletContext().getContextPath();
	}
	
	public static String cookiesInfo(HttpServletRequest request){
	    Cookie[] cookies = request.getCookies();
	    StringBuffer cookiesStr = new StringBuffer();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	        	cookiesStr.append(" cookie name = " + cookie.getName() + ",cookie value = " + cookie.getValue()+"--------\n");
	        }
	    }
	    return cookiesStr.toString();
	}
	
	/**
	 * 根据名字获取cookie
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request,String name){
	    Map<String,Cookie> cookieMap = readCookieMap(request);
	    if(cookieMap.containsKey(name)){
	        Cookie cookie = (Cookie)cookieMap.get(name);
	        return cookie;
	    }else{
	        return null;
	    }   
	}

	public static void sendErrorMessage(HttpServletResponse response, String json) throws IOException {
			response.setStatus(HttpConstant.ERROR_CODE);
			sendMessage(response, json);
	}
	
	public static void sendMessage(HttpServletResponse response, String dataStr) throws IOException {
		response.setContentType("application/json; charset=utf-8");
//		response.setContentType("text/html; charset=utf-8");
		PrintWriter printer = response.getWriter();
			try {
				printer.print(dataStr);
				printer.flush();
				printer.close();
			} catch (Exception e) {
				
			} finally {
					if(printer != null) {
						printer.close();
					}
			}
	}

	public static void sendExcel(HttpServletResponse response, Workbook wb,String fileName) throws IOException {
		OutputStream os = null;
		try {
			os = response.getOutputStream();  
			response.setHeader("Content-disposition", "attachment;filename=" +fileName);  
			response.setContentType("application/vnd.ms-excel");  
			wb.write(os);  
			os.flush();  
			os.close();
		} catch (Exception e) {
			
		}  finally {
			try {
				if(os != null) {
					os.close();
				}
			} 
			catch (IOException e) {
				throw new IOException(e.getMessage());
			}
		}
	}
	 
	public static String getSessionAttStr(HttpServletRequest request,String key){
		HttpSession session = request.getSession();
		return session.getAttribute(key)==null?"":session.getAttribute(key).toString();
	}
	 
	public static Object getSessionAttObj(HttpServletRequest request,String key){
		HttpSession session = request.getSession();
		return session.getAttribute(key)==null?"":session.getAttribute(key);
	}
	

	/**
	 * 请求异常
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public static void replyErrorMessage(HttpServletResponse response,int code,String msg) throws IOException {
		sendMessage(response, new ResponseMessage(msg).build());
	}
	
	/**
	 * 将cookie封装到Map里面
	 * @param request
	 * @return
	 */
	private static Map<String,Cookie> readCookieMap(HttpServletRequest request){  
	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	    Cookie[] cookies = request.getCookies();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}
	
	/**
	 * 获取请求客户端的IP地址
	 * @param request
	 * @return
	 */
	protected static String getRemortIP(HttpServletRequest request) {
		return NetworkUtil.getIpAddress(request);
	}
	
	/**
	 * 传入request,获取请求中的所有参数并封装在Map中返回
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public static Map<String, String> getAllParams(HttpServletRequest request) throws UnsupportedEncodingException{
		Map<String, String> params = new HashMap<String, String>();
		Enumeration parameterNames = request.getParameterNames();
		HashMap paramMap = (HashMap<?, ?>) request.getParameterMap();

		while(parameterNames.hasMoreElements()){
			String key = (String) parameterNames.nextElement();
			String value = getParam(request, key);
			params.put(key, value);
		}
		return params;
	}
	
	
	/**
	 * 以UTF-8获取request中的参数值
	 * @param request
	 * @param k
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getParam(HttpServletRequest request,String k) throws UnsupportedEncodingException{
//		System.out.println("********************************* " + request.getCharacterEncoding());
		return request.getParameter(k)==null?"":new String(request.getParameter(k).getBytes(request.getCharacterEncoding()),"UTF-8");
		
//		if(!"POST".equalsIgnoreCase(request.getMethod())){
//				return request.getParameter(k)==null?"":new String(request.getParameter(k).getBytes("iso8859-1"),"UTF-8");
//		}else {
//				return request.getParameter(k)==null?"":request.getParameter(k);
//		}
	}
	

    /**
     * 将字符串数组转换成String输出
     * @param arr
     * @return
     */
	public static String getArgsByArr(Object[] arr){
		final StringBuffer str = new StringBuffer();
		if(arr != null && arr.length > 0){
			for(int i = 0;i < arr.length;i ++){
				str.append("arg " + i + " = " +arr[i].toString() + " ;");
			}
		}
		return str.toString();
	}

}
