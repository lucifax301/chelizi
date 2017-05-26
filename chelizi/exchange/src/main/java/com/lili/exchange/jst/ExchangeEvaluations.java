package com.lili.exchange.jst;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.google.gson.JsonObject;
import com.lili.exchange.vo.ExEvalPostVo;
import com.lili.exchange.vo.ExEvalQueryVo;

public class ExchangeEvaluations {

	private Logger log = Logger.getLogger(ExchangeEvaluations.class); 
	private final String v = "1";
	private final String ts = "null";
	private final String sign = "null";
	private final String user = "null";
	@Value("${exchange.clientCode}")
	private String clientCode = "chelizi";
	@Value("${exchange.clientPwd}")
	private String clientPwd = "48418b79ba7cecc9241cdf1ff5378fdd";
	private final String terminalType = "WEB";
	
	private final String reqPath = "http://61.144.253.251:62026/JpggService/RestService/services/evaluations";
	
	private String getReqUri(){
		return "?v=" + v + "&ts=" + ts + "&sign=" + sign + "&user=" + user
				+ "&clientCode=" + clientCode + "&clientPwd=" + clientPwd + "&terminalType=" + terminalType;
	}
	
	/*
	 * 2.3.1添加评价信息
	 */
	public String postEvaluate(ExEvalPostVo eepVo) {
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
			Field[] fields = eepVo.getClass().getDeclaredFields();
			for (Field field : fields){
				field.setAccessible(true);
				if (!"serialVersionUID".equals(field.getName()) && field.get(eepVo) != null){
					params.addProperty(field.getName(), field.get(eepVo).toString());
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
						log.error("************** ExchangeEvaluations.postEvaluate : method statusCode = " + statusCode);
					break;
				} catch (Exception e) {
					count ++;
					log.error("************** ExchangeEvaluations.postEvaluate : " + e.getMessage());
				}
			}
			result = new String(postMethod.getResponseBody(), "UTF-8");
		} catch (Exception e) {
			log.error("ExchangeEvaluations Exception : " + e.getMessage(), e);
		} finally {
			postMethod.releaseConnection();
		}
		return result;
	}
	
	/*
	 * 2.3.2获取评价信息列表
	 */
	public String getEvaluateList(ExEvalQueryVo eeqVo) {
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
			Field[] fields = eeqVo.getClass().getDeclaredFields();
			for (Field field : fields){
				field.setAccessible(true);
				if (!"serialVersionUID".equals(field.getName()) && field.get(eeqVo) != null){
					params.add(new NameValuePair(field.getName(), field.get(eeqVo).toString()));
				}
			}
			getMethod.setQueryString(params.toArray(new NameValuePair[params.size()]));
			
			//发起get请求
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.error("************** ExchangeEvaluations.getEvaluateList : method statusCode = " + statusCode);
			}
			result = new String(getMethod.getResponseBody(), "UTF-8");	
		} catch (Exception e) {
			log.error("ExchangeEvaluations Exception : " + e.getMessage(), e);
		} finally {
			getMethod.releaseConnection();
		}
		return result;
	}
	
	/*
	 * 2.3.3根据驾培改革系统评价信息ID获取单个评价信息
	 */
	public String getEvaluateById(String id) {
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
				log.error("************** ExchangeEvaluations.getEvaluateById : method statusCode = " + statusCode);
			}
			result = new String(getMethod.getResponseBody(), "UTF-8");	
		} catch (Exception e) {
			log.error("ExchangeEvaluations Exception : " + e.getMessage(), e);
		} finally {
			getMethod.releaseConnection();
		}
		return result;
	}
	
	/*
	 * 2.3.4根据前端应用的平台编号和评价信息编号获取单个评价信息
	 */
	public String getEvaluateByNum(String platnum, String recnum) {
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
				log.error("************** ExchangeEvaluations.getEvaluateByNum : method statusCode = " + statusCode);
			}
			result = new String(getMethod.getResponseBody(), "UTF-8");
		} catch (Exception e) {
			log.error("ExchangeEvaluations Exception : " + e.getMessage(), e);
		} finally {
			getMethod.releaseConnection();
		}
		return result;
	}
	
	public String test(){
		return clientCode + ":" + getReqUri();
	}
	
//	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException{
//		ExchangeEvaluations exchangeEvaluations = new ExchangeEvaluations();
//		ExEvalPostVo eepVo = new ExEvalPostVo();
//		eepVo.setPlatnum("chelizi");
//		eepVo.setSchoolId("111");
//		eepVo.setSchoolName("test");
//		eepVo.setFlowCode("222");
//		eepVo.setStuIdCard("333");
//		eepVo.setStuName("学员");
//		eepVo.setCoachIdCard("444");
//		eepVo.setCoachName("教练");
//		eepVo.setSubject(1);
//		eepVo.setType(1);
//		eepVo.setOverall(5);
//		eepVo.setTrainInfoId("6666");
//		eepVo.setPart(1);
//		eepVo.setEvaluation("20160812080000");
//		eepVo.setEvaCodes("101,102,103");
//		eepVo.setEvaNames("教学水平,教学态度,文明施教");
//		eepVo.setCodes("101,102");
//		eepVo.setNames("教学水平,教学态度");
//		eepVo.setPersonalEvel("good");
//		eepVo.setRecnum("9");
//		eepVo.setInputUserCode("888");
//		eepVo.setInputUserName("处理人");
//		eepVo.setInputTime("20160812100000");
//		eepVo.setTerminalType("WEB");
//		System.out.println(exchangeEvaluations.postEvaluate(eepVo));
//		
//	}
	
}
