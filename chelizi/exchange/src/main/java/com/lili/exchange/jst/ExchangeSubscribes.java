package com.lili.exchange.jst;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.google.gson.JsonObject;
import com.lili.exchange.vo.ExSubPostVo;
import com.lili.exchange.vo.ExSubQueryVo;

public class ExchangeSubscribes {

	private Logger log = Logger.getLogger(ExchangeSubscribes.class);
	private final String v = "1";
	private final String ts = "null";
	private final String sign = "null";
	private String user = "null";
	@Value("${exchange.clientCode}")
	private String clientCode = "chelizi";
	@Value("${exchange.clientPwd}")
	private final String clientPwd = "48418b79ba7cecc9241cdf1ff5378fdd";
	private final String terminalType = "WEB";
	
	private final String reqPath = "http://61.144.253.251:62026/JpggService/RestService/services/subscribes";
	private String getReqUri(){
		return "?v=" + v + "&ts=" + ts + "&sign=" + sign + "&user=" + user
				+ "&clientCode=" + clientCode + "&clientPwd=" + clientPwd + "&terminalType=" + terminalType;
	}
	/*
	 * 添加预约信息
	 */
	public String postBookInfo(ExSubPostVo espVo) {
		HttpClient httpClient = new HttpClient();
		String url = reqPath + getReqUri();
		httpClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		
		PostMethod postMethod = new PostMethod(url);
		postMethod.addRequestHeader("Connection", "close");
		postMethod.addRequestHeader("Accept", "application/json");
		
		String result = null;
		JsonObject params = new JsonObject();
		int count = 0;
		try {
			//填充参数
			Field[] fields = espVo.getClass().getDeclaredFields();
			for (Field field : fields){
				field.setAccessible(true);
				if (!"serialVersionUID".equals(field.getName()) && field.get(espVo) != null){
					params.addProperty(field.getName(), field.get(espVo).toString());
				}
			}
			if (!params.has("terminalType"))
				params.addProperty("terminalType", terminalType);
			
			//发送post请求
			RequestEntity paramsEntity = new StringRequestEntity(params.toString(), "application/json", "UTF-8");
			postMethod.setRequestEntity(paramsEntity);
			while (count < 3) {
				try {
					int statusCode = httpClient.executeMethod(postMethod);
					if (statusCode != HttpStatus.SC_OK)
						log.error("************** ExchangeSubscribes.postBookInfo : method statusCode = " + statusCode);
					break;
				} catch (Exception e) {
					count ++;
					log.error("************** ExchangeEvaluations.postBookInfo : " + e.getMessage());
				}
			}
			result = new String(postMethod.getResponseBody(), "UTF-8");
		} catch (Exception e) {
			log.error("ExchangeSubscribes Exception : " + e.getMessage(), e);
		} finally {
			postMethod.releaseConnection();
		}
		return result;
	}
	
	/*
	 * 获取预约信息列表
	 */
	public String getBookInfoList(ExSubQueryVo esqVo) {
		HttpClient httpClient = new HttpClient();
		String url = reqPath;
		httpClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		
		GetMethod getMethod = new GetMethod(url);
		getMethod.addRequestHeader("Connection", "close");
		getMethod.addRequestHeader("Accept", "application/json");
		
		String result = null;
		List<NameValuePair> params = new ArrayList<>();
		params.add(new NameValuePair("clientCode", clientCode));
		params.add(new NameValuePair("clientPwd", clientPwd));
		params.add(new NameValuePair("terminalType", terminalType));
		
		try {
			//填充参数
			Field[] fields = esqVo.getClass().getDeclaredFields();
			for (Field field : fields){
				field.setAccessible(true);
				if (!"serialVersionUID".equals(field.getName()) && field.get(esqVo) != null){
					params.add(new NameValuePair(field.getName(), field.get(esqVo).toString()));
				}
			}
			getMethod.setQueryString(params.toArray(new NameValuePair[params.size()]));
			
			//发起get请求
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.error("************** ExchangeSubscribes.getBookInfoList : method statusCode = " + statusCode);
			}
			result = new String(getMethod.getResponseBody(), "UTF-8");
		} catch (Exception e) {
			log.error("ExchangeSubscribes Exception : " + e.getMessage(), e);
		} finally {
			getMethod.releaseConnection();
		}
		
		return result;
	}
	
	/*
	 * 取消预约信息，remark可以为null，其它字段为必填
	 */
	public String putBookInfoCancel(String platnum, String recnum, String cancelTime,
			String cancelUserCode, String cancelUserName, String remark) {
		HttpClient httpClient = new HttpClient();
		String url = reqPath + getReqUri();
		httpClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		
		PutMethod putMethod = new PutMethod(url);
		putMethod.addRequestHeader("Connection", "close");
		putMethod.addRequestHeader("Accept", "application/json");
		
		String result = null;
		JsonObject params = new JsonObject();
		params.addProperty("platnum", platnum);
		params.addProperty("recnum", recnum);
		params.addProperty("cancelTime", cancelTime);
		params.addProperty("cancelUserCode", cancelUserCode);
		params.addProperty("cancelUserName", cancelUserName);
		params.addProperty("remark", remark);
		
		int count = 0;
		try {
			RequestEntity paramsEntity = new StringRequestEntity(params.toString(), "application/json", "UTF-8");
			putMethod.setRequestEntity(paramsEntity);
			while (count < 3) {
				try {
					int statusCode = httpClient.executeMethod(putMethod);
					if (statusCode != HttpStatus.SC_OK)
						log.error("************** ExchangeSubscribes.putBookInfoCancel : method statusCode = " + statusCode);
					break;
				} catch (Exception e) {
					count ++;
					log.error("************** ExchangeEvaluations.putBookInfoCancel : " + e.getMessage());
				}
			}
			result = new String(putMethod.getResponseBody(), "UTF-8");
			System.out.println("put method : " + result);
		} catch (Exception e) {
			log.error("ExchangeSubscribes Exception : " + e.getMessage(), e);
		} finally {
			putMethod.releaseConnection();
		}
		return result;
	}
	
	/*
	 * 根据驾培改革系统的培训预约信息id获取单个预约信息
	 */
	public String getBookInfoByID(String id) {
		HttpClient httpClient = new HttpClient();
		String url = reqPath + "/" + id + getReqUri();
		httpClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
				
		GetMethod getMethod = new GetMethod(url);
		getMethod.addRequestHeader("Connection", "close");
		getMethod.addRequestHeader("Accept", "application/json");
		
		String result = null;
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.error("************** ExchangeSubscribes.getBookInfoByID : method statusCode = " + statusCode);
			}
			result = new String(getMethod.getResponseBody(), "UTF-8");
		} catch (Exception e) {
			log.error("ExchangeSubscribes Exception : " + e.getMessage(), e);
		} finally {
			getMethod.releaseConnection();
		}
		
		return result;
	}
	
	/*
	 * 根据前端应用平台的平台编号和预约编号获取单个培训预约信息
	 */
	public String getBookInfoByNum(String platnum, String recnum) {
		HttpClient httpClient = new HttpClient();
		String url = reqPath + "/search" + getReqUri();
		httpClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		
		GetMethod getMethod = new GetMethod(url);
		getMethod.addRequestHeader("Connection", "close");
		getMethod.addRequestHeader("Accept", "application/json");
		
		String result = null;
		NameValuePair[] params = {
				new NameValuePair("clientCode", clientCode),
				new NameValuePair("clientPwd", clientPwd),
				new NameValuePair("terminalType", terminalType),
				new NameValuePair("platnum", platnum),
				new NameValuePair("recnum", recnum)
		};
		getMethod.setQueryString(params);
		
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.error("Method failed: "
						+ getMethod.getStatusLine());
			}
			result = new String(getMethod.getResponseBody(), "UTF-8");
		} catch (Exception e) {
			log.error("ExchangeSubscribes Exception : " + e.getMessage(), e);
		} finally {
			getMethod.releaseConnection();
		}
		
		return result;
	}
	
//	public static void main(String[] args){
//		ExchangeSubscribes exchangeSubscribes = new ExchangeSubscribes();
//		ExSubPostVo espVo = new ExSubPostVo();
//		espVo.setPlatnum("chelizi");
//		espVo.setSchoolId("123");
//		espVo.setSchoolName("hhh");
//		espVo.setFlowCode("123");
//		espVo.setStuIdCard("123");
//		espVo.setStuName("fff");
//		espVo.setCoachIdCard("123");
//		espVo.setCoachName("ddd");
//		espVo.setVehicleNo("粤B");
//		espVo.setRecnum("6");
//		espVo.setSubjcode("222");
//		espVo.setSubject("1");
//		espVo.setStartTime("20160807120000");
//		espVo.setEndTime("20160807123000");
//		espVo.setDuration("30");
//		espVo.setInputUserCode("321");
//		espVo.setInputUserName("sss");
//		espVo.setInputTime("20160807123000");
//		espVo.setTerminalType("WEB");
//		ExSubQueryVo esqVo = new ExSubQueryVo();
//		esqVo.setVehicleNo("粤B1234学");
//		String jsonResult = exchangeSubscribes.postBookInfo(espVo);
//	}
}
