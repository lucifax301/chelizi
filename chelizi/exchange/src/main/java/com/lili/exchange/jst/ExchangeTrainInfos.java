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
import com.lili.exchange.vo.ExTrainPostVo;
import com.lili.exchange.vo.ExTrainQueryVo;

public class ExchangeTrainInfos {

	private Logger log = Logger.getLogger(ExchangeTrainInfos.class);
	private final String v = "1";
	private final String ts = "null";
	private final String sign = "null";
	private final String user = "null";
	@Value("${exchange.clientCode}")
	private String clientCode = "chelizi";
	@Value("${exchange.clientPwd}")
	private String clientPwd = "48418b79ba7cecc9241cdf1ff5378fdd";
	private final String terminalType = "WEB";
	
	private final String reqPath = "http://61.144.253.251:62026/JpggService/RestService/services/trainInfos";
	private String getReqUri(){
		return "?v=" + v + "&ts=" + ts + "&sign=" + sign + "&user=" + user
				+ "&clientCode=" + clientCode + "&clientPwd=" + clientPwd + "&terminalType=" + terminalType;
	}
	/*
	 * 添加培训时长
	 */
	public String postTrainInfo(ExTrainPostVo etpVo) {
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
			Field[] fields = etpVo.getClass().getDeclaredFields();
			for (Field field : fields){
				field.setAccessible(true);
				if (!"serialVersionUID".equals(field.getName()) && field.get(etpVo) != null){
					params.addProperty(field.getName(), field.get(etpVo).toString());
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
						log.error("************** ExchangeTrainInfos.postTrainInfo : method statusCode = " + statusCode);
					break;
				} catch (Exception e) {
					count ++;
					log.error("************** ExchangeTrainInfos.postTrainInfo : " + e.getMessage());
				}
			}
			result = new String(postMethod.getResponseBody(), "UTF-8");
		} catch (Exception e) {
			log.error("ExchangeTrainInfos Exception : " + e.getMessage(), e);
		} finally {
			postMethod.releaseConnection();
		}
		
		return result;
	}
	
	/*
	 * 获取培训时长列表
	 */
	public String getTrainInfoList(ExTrainQueryVo etqVo) {
		HttpClient httpClient = new HttpClient();
		String url = reqPath;
		httpClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		
		GetMethod getMethod = new GetMethod(url);
		getMethod.addRequestHeader("Connection", "close");
		getMethod.addRequestHeader("Accept", "application/json");
		
		List<NameValuePair> params = new ArrayList<>();
		params.add(new NameValuePair("clientCode", clientCode));
		params.add(new NameValuePair("clientPwd", clientPwd));
		params.add(new NameValuePair("terminalType", terminalType));
		
		String result = null;
		try {
			//填充参数
			Field[] fields = etqVo.getClass().getDeclaredFields();
			for (Field field : fields){
				field.setAccessible(true);
				if (!"serialVersionUID".equals(field.getName()) && field.get(etqVo) != null){
					params.add(new NameValuePair(field.getName(), field.get(etqVo).toString()));
				}
			}
			getMethod.setQueryString(params.toArray(new NameValuePair[params.size()]));
			
			//发起get请求
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.error("************** ExchangeTrainInfos.getTrainInfoList : method statusCode = " + statusCode);
			}
			result = new String(getMethod.getResponseBody(), "UTF-8");
		} catch (Exception e) {
			log.error("ExchangeTrainInfos Exception : " + e.getMessage(), e);
		} finally {
			getMethod.releaseConnection();
		}
		
		return result;
	}
	
	/*
	 * 根据驾培改革系统培训时长ID获取单个培训时长
	 */
	public String getTrainInfoByID(String id) {
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
				log.error("************** ExchangeTrainInfos.getTrainInfoByID : method statusCode = " + statusCode);
			}
			result = new String(getMethod.getResponseBody(), "UTF-8");
		} catch (Exception e) {
			log.error("ExchangeTrainInfos Exception : " + e.getMessage(), e);
		} finally {
			getMethod.releaseConnection();
		}
		
		return result;
	}
	
	/*
	 * 根据前端应用平台的平台编号和预约编号获取单个培训预约信息
	 */
	public String getTrainInfoByNum(String platnum, String recnum) {
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
				log.error("************** ExchangeTrainInfos.getTrainInfoByID : method getTrainInfoByNum = " + statusCode);
			}
			result = new String(getMethod.getResponseBody(), "UTF-8");
		} catch (Exception e) {
			log.error("ExchangeTrainInfos Exception : " + e.getMessage(), e);
		} finally {
			getMethod.releaseConnection();
		}
		
		return result;
	}
	
//	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException{
//		ExchangeTrainInfos exchangeTrainInfos = new ExchangeTrainInfos();
//		ExTrainPostVo etpVo = new ExTrainPostVo();
//		etpVo.setPlatnum("chelizi");
//		etpVo.setSchoolId("111");
//		etpVo.setSchoolName("test");
//		etpVo.setFlowCode("222");
//		etpVo.setStuIdCard("333");
//		etpVo.setStuName("学员");
//		etpVo.setCoachIdCard("444");
//		etpVo.setCoachName("教练");
//		etpVo.setVehicleNo("粤A");
//		etpVo.setSimunum("555");
//		etpVo.setRecnum("6666");
//		etpVo.setSubjcode("777");
//		etpVo.setSubject("1");
//		etpVo.setPhoto1("photo1");
//		etpVo.setPhoto2("photo2");
//		etpVo.setPhoto3("photo3");
//		etpVo.setStartTime("20160812080000");
//		etpVo.setEndTime("20160812090000");
//		etpVo.setDuration("60");
//		etpVo.setMileage("100");
//		etpVo.setAvevelocity("50");
//		etpVo.setCoacmt("good");
//		etpVo.setSubsCode("30");
//		etpVo.setInputUserCode("888");
//		etpVo.setInputUserName("处理人");
//		etpVo.setInputTime("20160812100000");
//		etpVo.setTerminalType("WEB");
//		System.out.println(exchangeTrainInfos.postTrainInfo(etpVo));
//		ExTrainQueryVo etqVo = new ExTrainQueryVo();
//		etqVo.setVehicleNo("粤A");
//		String json = exchangeTrainInfos.getTrainInfoList(etqVo);
//		System.out.println(json.replace("\\", ""));
//		Gson gson = new GsonBuilder().create();
//		ExResult result = gson.fromJson(json, ExResult.class);
//		ExData<ExTrainDTO> data = gson.fromJson(result.getData().toString(), new TypeToken<ExData<ExTrainDTO>>(){}.getType());
//		System.out.println(data.getDataArray().get(0).getPlatnum());
//	}
}
