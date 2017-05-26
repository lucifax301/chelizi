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
import com.lili.exchange.vo.ExPayPostVo;
import com.lili.exchange.vo.ExPayQueryVo;

public class ExchangePayments {

	private Logger log = Logger.getLogger(ExchangePayments.class);
	private final String v = "1";
	private final String ts = "null";
	private final String sign = "null";
	private final String user = "null";
	@Value("${exchange.clientCode}")
	private String clientCode = "chelizi";
	@Value("${exchange.clientPwd}")
	private String clientPwd = "48418b79ba7cecc9241cdf1ff5378fdd";
	private final String terminalType = "WEB";
	
	private final String reqPath = "http://61.144.253.251:62026/JpggService/RestService/services/payments";
	private String getReqUri(){
		return "?v=" + v + "&ts=" + ts + "&sign=" + sign + "&user=" + user
				+ "&clientCode=" + clientCode + "&clientPwd=" + clientPwd + "&terminalType=" + terminalType;
	}
	
	/*
	 * 2.4.1添加在线支付信息
	 */
	public String postPayInfo(ExPayPostVo eppVo) {
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
			Field[] fields = eppVo.getClass().getDeclaredFields();
			for (Field field : fields){
				field.setAccessible(true);
				if (!"serialVersionUID".equals(field.getName()) && field.get(eppVo) != null){
					params.addProperty(field.getName(), field.get(eppVo).toString());
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
						log.error("************** ExchangePayments.postPayInfo : method statusCode = " + statusCode);
					break;
				} catch (Exception e) {
					count ++;
					log.error("************** ExchangePayments.postPayInfo : " + e.getMessage());
				}
			}
			result = new String(postMethod.getResponseBody(), "UTF-8");
		} catch (Exception e) {
			log.error("ExchangePayments Exception : " + e.getMessage(), e);
		} finally {
			postMethod.releaseConnection();
		}
		return result;
	}
	
	/*
	 * 2.4.2获取在线支付信息
	 */
	public String getPayInfoList(ExPayQueryVo epqVo) {
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
			Field[] fields = epqVo.getClass().getDeclaredFields();
			for (Field field : fields){
				field.setAccessible(true);
				if (!"serialVersionUID".equals(field.getName()) && field.get(epqVo) != null){
					params.add(new NameValuePair(field.getName(), field.get(epqVo).toString()));
				}
			}
			getMethod.setQueryString(params.toArray(new NameValuePair[params.size()]));
			
			//发起get请求
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.error("************** ExchangePayments.getPayInfoList : method statusCode = " + statusCode);
			}
			result = new String(getMethod.getResponseBody(), "UTF-8");
		} catch (Exception e) {
			log.error("ExchangePayments Exception : " + e.getMessage(), e);
		} finally {
			getMethod.releaseConnection();
		}
		return result;
	}
	
	/*
	 * 2.4.3根据驾培改革系统在线支付信息ID获取单个在线支付
	 */
	public String getPayInfoById(String id) {
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
				log.error("************** ExchangePayments.getPayInfoById : method statusCode = " + statusCode);
			}
			result = new String(getMethod.getResponseBody(), "UTF-8");
		} catch (Exception e) {
			log.error("ExchangePayments Exception : " + e.getMessage(), e);
		} finally {
			getMethod.releaseConnection();
		}
		
		return result;
	}
	
	/*
	 * 2.4.4根据前端应用的平台编号和在线支付信息编号获取单个在线支付信息
	 */
	public String getPayInfoByNum(String platnum, String recnum) {
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
				log.error("************** ExchangePayments.getPayInfoByNum : method statusCode = " + statusCode);
			}
			result = new String(getMethod.getResponseBody(), "UTF-8");	
		} catch (Exception e) {
			log.error("ExchangePayments Exception : " + e.getMessage(), e);
		} finally {
			getMethod.releaseConnection();
		}
		
		return result;
	}
	
//	public static void main(String[] args){
//		ExPayPostVo eppVo = new ExPayPostVo();
//		eppVo.setPlatnum("chelizi");
//		eppVo.setSchoolId("123");
//		eppVo.setSchoolName("hhh");
//		eppVo.setVehicleType("C1");
//		eppVo.setTrainningMode(1);
//		eppVo.setSubject(1);
//		eppVo.setTrainningTime(1);
//		eppVo.setTrainBeginTime("20160815123000");
//		eppVo.setTrainEndTime("20160815133000");
//		eppVo.setChargeMode(1);
//		eppVo.setPayMode(1);
//		eppVo.setCost("120");
//		eppVo.setPayType("01");
//		eppVo.setFlowCode("222");
//		eppVo.setStuIdCard("333");
//		eppVo.setStuName("学员");
//		eppVo.setCoachIdCard("444");
//		eppVo.setCoachName("教练");
//		eppVo.setVehicleNo("粤B");
//		eppVo.setTrainInfoId("30");
//		eppVo.setRecnum("3");
//		eppVo.setPayTime("20160815123000");
//		eppVo.setInputUserCode("888");
//		eppVo.setInputUserName("处理人");
//		eppVo.setInputTime("20160812100000");
//		eppVo.setTerminalType("WEB");
//		ExchangePayments exchangePayments = new ExchangePayments();
//		ExPayQueryVo epqVo = new ExPayQueryVo();
//		epqVo.setVehicleNo("粤B");
//		System.out.println(exchangePayments.postPayInfo(eppVo));
//	}
}
