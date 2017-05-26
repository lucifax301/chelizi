package com.lili.exchange.jspx;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.util.HtmlUtils;

public class DriverTrainingSystem {

	private final String userCode = "sgjx";
	private final String userPassword = "eqiuyq";
	
	private final String reqPath = "http://szjspx.com:8082/finger/services/DownLoadService/";
	
	private String commonRequest(String serverName, NameValuePair[] params){
		HttpClient httpClient = new HttpClient();
		String url = reqPath + serverName;
		httpClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		
		GetMethod getMethod = new GetMethod(url);
		getMethod.addRequestHeader("Connection", "close");
		getMethod.addRequestHeader("Accept", "application/json");
		
		getMethod.setQueryString(params);
		
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}
			
			String response = HtmlUtils.htmlUnescape(new String(getMethod.getResponseBody(), "UTF-8"));
			int beginIndex = response.indexOf("<ns:return>");
			int endIndex = response.indexOf("</ns:return>");
			response = response.substring(beginIndex + 11, endIndex);
			Document document = DocumentHelper.parseText(response);
			Element root = document.getRootElement();
			System.out.println(root.element("HEAD").elementText("RET_CODE"));
			System.out.println(root.element("HEAD").elementText("RET_MSG"));
			if (root.element("BODY").element("REC") != null)
				System.out.println(root.element("BODY").element("REC").asXML());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
		
		return null;
	}
	
	/*
	 * 请求前的认证
	 */
	public void register(){
		String serverName = "registerRequest";
		NameValuePair[] params = {
				new NameValuePair("userCode", userCode),
				new NameValuePair("userPassword", userPassword)
		};
		commonRequest(serverName, params);
	}
	
	/*
	 * 查询考试注册学员信息
	 */
	public void getStuInfo(){
		String serverName = "getStuInfo";
		NameValuePair[] params = {
				new NameValuePair("getType", "1"),//1:查询某一个学员，2:查询一个时间段的学员
				new NameValuePair("idCard", "422429197608167965"),
				new NameValuePair("beginTime", "20160801000000"),
				new NameValuePair("endTime", "20160802000000")
		};
		commonRequest(serverName, params);
	}
	
	/*
	 * 查询企业基本信息
	 */
	public void getBaseInfo(){
		String serverName = "getBaseInfo";
		NameValuePair[] params = {
				new NameValuePair("infoType", "CLASSROOM")//VEHICLE：教练车 COACH：教练员 PLACE:训练场 CLASSROOM：理论教室
		};
		commonRequest(serverName, params);
	}
	
	/*
	 * 查询培训信息
	 */
	public void getTrainTotal(){
		String serverName = "getTrainTotal";
		NameValuePair[] params = {
				new NameValuePair("trainType", "2"),//1：科目一 ，2：科目二 3：科目三
				new NameValuePair("beginTime", "20160801000000"),
				new NameValuePair("endTime", "20160802000000")
		};
		commonRequest(serverName, params);
	}
	
	/*
	 * 查询培训详细信息
	 */
	public void getTrainInfo(){
		String serverName = "getTrainInfo";
		NameValuePair[] params = {
				new NameValuePair("trainType", "2"),//1：科目一 ，2：科目二 3：科目三
				new NameValuePair("beginTime", "20160801000000"),
				new NameValuePair("endTime", "20160802000000")
		};
		commonRequest(serverName, params);
	}
	
	/*
	 * 查询报名受理学员信息
	 */
	public void getStuPreInfo(){
		String serverName = "getStuPreInfo";
		NameValuePair[] params = {
				new NameValuePair("getType", "2"),//1:查询某一个学员，2:查询一个时间段的学员
				new NameValuePair("idCard", "422429197608167965"),
				new NameValuePair("beginTime", "20160801000000"),
				new NameValuePair("endTime", "20160802000000")
		};
		commonRequest(serverName, params);
	}
	
	/*
	 * 查询考试信息
	 */
	public void getExamInfo(){
		String serverName = "getExamInfo";
		NameValuePair[] params = {
				new NameValuePair("trainType", "2"),//1:科目一，2:科目二3科目三，4文明考试，5长考
				new NameValuePair("beginTime", "20160801000000"),
				new NameValuePair("endTime", "20160802000000")
		};
		commonRequest(serverName, params);
	}
	
	/*
	 * 查询IC卡信息
	 */
	public void getICCardInfo(){
		String serverName = "getICCardInfo";
		NameValuePair[] params = {
				new NameValuePair("trainType", "2"),//1按卡号查询，2按时间范围查询
				new NameValuePair("cardNo", "719308"),
				new NameValuePair("beginTime", "20160801000000"),
				new NameValuePair("endTime", "20160802000000")
		};
		commonRequest(serverName, params);
	}
	
	/*
	 * 检查教练员状态
	 */
	public void checkCoachState(){
		String serverName = "checkCoachState";
		NameValuePair[] params = {
				new NameValuePair("schoolId", "001"),
				new NameValuePair("cardNo", "719308")
		};
		commonRequest(serverName, params);
	}
	
	/*
	 * 检查学员是否已报名受理
	 */
	public void checkSfzmhm(){
		String serverName = "checkSfzmhm";
		NameValuePair[] params = {
				new NameValuePair("sfzmhm", "422429197608167965")
		};
		commonRequest(serverName, params);
	}
	
	public static void main(String[] args){
		DriverTrainingSystem driverTrainingSystem = new DriverTrainingSystem();
		driverTrainingSystem.register();
		driverTrainingSystem.getExamInfo();
	}
	
}
