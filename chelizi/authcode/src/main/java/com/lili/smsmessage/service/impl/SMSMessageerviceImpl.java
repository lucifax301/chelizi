package com.lili.smsmessage.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.cloopen.rest.sdk.CCPRestSDK;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqConstants;

/**
 * 短信发送
 * 
 * @author yu.y
 *
 */
public class SMSMessageerviceImpl {
	private static Logger logger = LoggerFactory.getLogger(SMSMessageerviceImpl.class);
	
	
//	@Value("${authcode.env}")
//	private String authcodeEnv = "";
	@Resource(name="authcodeProp")
	private Properties authcodeProp;
	@Autowired
	RedisUtil redisUtil;

	//private final String App_ID = "aaf98f89505b6c0801505fe7ae87041d"; // 应用ID
	
	private final String App_ID = "8a48b55150b36d920150b6bb984b0806"; // 应用ID

	private final String SERVER_IP = "app.cloopen.com"; // 服务器IP

	private final String SERVER_PORT = "8883"; // 服务器端口

	private final String ACCOUNT_SID = "8a48b551505b4af001505fe1a2ca0ce8",
			ACCOUNT_TOKEN = "bdfb63826c2740b68a780c8c35441007"; // 开发者账号

	private final String URL = "http://www.mxtong.net.cn/GateWay/Services.asmx/DirectSend?"; // 消息发送接口url

	private final String UserID = "968131"; // 消息发送企业ID

	private final String Account = "admin"; // 帐号

	private final String Password = "968131"; // 密码

	private final String SendType = "1"; // 发送类别(1)

	private final String PostFixNumber = ""; // 任务扩展号!(1到9，或者为空)
	
//	private final String KL_url = "http://kltx.sms10000.com.cn/sdk/SMS?"; //宽乐通短信接口url
//	private final String KL_uid = "587"; //宽乐通帐号 	帐号:  szcczwl 密码:  Cczwl587  企业ID（二次开发使用） ：587
//	private final String KL_password = "8013f88fc16483422284a024a171524e"; //宽乐通密码		密码需MD5 32位加密
//	private final String KL_url = "http://client.sms10000.com/api/webservice?";
	private final String KL_eprId = "587";
	private final String KL_userId = "szcczwl";
	private final String KL_password = "Cczwl587";
	
	private final String Gsms_url = "http://gateway.iems.net.cn/GsmsHttp?"; //高斯通短信接口
	private final String Gsms_username = "70029:admin"; //高斯通短信帐号
	private final String Gsms_password = "81590878"; //高斯通短信密码

	/**
	 * 通用接口
	 * @param phoneNum
	 * @param templateId
	 * @param datas
	 * @return 
	 */
	public void SMSSendMessage(String phoneNum, String templateId,
			String[] datas){
		String authcodeEnv = authcodeProp.getProperty("authcode.env");
		logger.debug("SMSSendMessage authcodeEnv: " + authcodeEnv + " |phoneNum:"+phoneNum + "|datas:"+Arrays.toString(datas));
		if(authcodeEnv.equals(ReqConstants.ENV_PRODUCT)){ //生产环境才发送验证码
			String platform = authcodeProp.getProperty("authcode.platform");
			if(platform.equals("1")){
				SMSSendMessage_1(phoneNum, templateId, datas);
			}
			else if(platform.equals("2")){
				SMSSendMessage_2(phoneNum, templateId, datas);
			}
			else if(platform.equals("3")){
				SMSSendMessage_3(phoneNum, templateId, datas);
			}
		}
	}
	
	
	/**
	 * 联容云通讯
	 * 
	 * @param phoneNum
	 *            手机号码，多个的时候中间用逗号隔开 每批发送的手机号数量不得超过100个  http://www.yuntongxun.com/doc/rest/sms/3_2_2_2.html
	 * @param templateId
	 *            模版id，没有模版则填写1
	 * @param datas
	 *            需要替换的内容
	 */
	public void SMSSendMessage_1(String phoneNum, String templateId,
			String[] datas) {

		if (StringUtils.isEmpty(templateId)) {
			templateId = "1"; // 当模版为空的时候则默认填写1
		}
		
		HashMap<String, Object> result = null;
		CCPRestSDK restAPI = new CCPRestSDK();
		restAPI.init(SERVER_IP, SERVER_PORT); // 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		restAPI.setAccount(ACCOUNT_SID, ACCOUNT_TOKEN); // 初始化主帐号和主帐号TOKEN
		restAPI.setAppId(App_ID); // 初始化应用ID
		result = restAPI.sendTemplateSMS(phoneNum, templateId, datas);
		//System.out.println("sendTemplateSMS result=" + result);

	}
	
	/**
	 * 麦讯通短信发送
	 * @param content
	 * @param phones
	 * @throws UnsupportedEncodingException
	 */
	public void SendMessage(String content, String phones) throws UnsupportedEncodingException {
		
		HttpClient httpClient = new HttpClient();
		
		httpClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		PostMethod getMethod = new PostMethod(URL);
		
		//内容需要添加【喱喱学车】的签名
		String Content = java.net.URLEncoder.encode(content+"【喱喱学车】",
				"UTF-8");
		
		//设置发送时间，当前时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		NameValuePair[] data = { 
				new NameValuePair("UserID", UserID),  //用户id
				new NameValuePair("Account", Account),  //子帐号名
				new NameValuePair("Password", Password),  //帐号密码
				new NameValuePair("Phones", phones),   //手机号码，以;隔开，最后以;结尾
				new NameValuePair("SendType", SendType),  //发送类别
				new NameValuePair("SendTime", df.format(date)),  //定时时间，为空则立马发送 
				new NameValuePair("PostFixNumber", PostFixNumber),  //任何扩展号
				new NameValuePair("Content", Content) };   //发送内容
		getMethod.setRequestBody(data);
		getMethod.addRequestHeader("Connection", "close");

		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}
			byte[] responseBody = getMethod.getResponseBody();
			String str = new String(responseBody, "UTF-8");
			if (str.contains("GBK")) {
				str = str.replaceAll("GBK", "utf-8");
			}
			int beginPoint = str.indexOf("<RetCode>");
			int endPoint = str.indexOf("</RetCode>");
			String result = "RetCode=";
			System.out
					.println(result + str.substring(beginPoint + 9, endPoint));
			System.out.println(str);
		} catch (HttpException e) {
			System.out.println(1);
		} catch (IOException e) {
			System.out.println(2);
		} finally {
			getMethod.releaseConnection();
		}
	}
	
	/**
	 * 电信 嘉盈资讯
	 * @param mobiles
	 * @param templateId
	 * @param datas
	 */
	public void SMSSendMessage_2(String mobiles, String templateId,
			String[] datas) {
		Boolean hasSent = redisUtil.get(RedisKeys.REDISKEY.LOCK_PRE +mobiles);
		if(hasSent != null && hasSent){
			return;
		}
		String doc = authcodeProp.getProperty("msg."+templateId);
		String content = parseDocParams(doc, datas);
		BusinessHttpSendC.sendSms(KL_eprId, KL_userId, KL_password, content, mobiles);
		redisUtil.set(RedisKeys.REDISKEY.LOCK_PRE +mobiles,true,10); //为避免客户端快速点击多次，限制十秒内才发一次
	}
	/**
	 * 字符串变量替换
	 * @param doc
	 * @param params
	 * @return
	 */
	private String parseDocParams(String doc, String[] datas) {
		if(null == doc || !doc.contains("{")|| null == datas ||datas.length == 0){
			return doc;
		}
		for(int i=1;i<datas.length +1;i++){
			doc = doc.replaceAll("\\{".concat(String.valueOf(i)).concat("\\}"), datas[i-1]);
		}
		return doc;
	}

/*	
    *//**
    * 32位md5加密
    * @param plainText
    *            明文
    * @return 32位密文
    *//*
   public String encryptMd5(String plainText) {
       String re_md5 = new String();
       try {
           MessageDigest md = MessageDigest.getInstance("MD5");
           md.update(plainText.getBytes());
           byte b[] = md.digest();
           int i;
           StringBuffer buf = new StringBuffer("");
           for (int offset = 0; offset < b.length; offset++) {
               i = b[offset];
               if (i < 0)
                   i += 256;
               if (i < 16)
                   buf.append("0");
               buf.append(Integer.toHexString(i));
           }
           re_md5 = buf.toString();
       } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
       }
       return re_md5;
   }
	
	public void SendSMS(String content, String phones ){
		HttpClient httpClient = new HttpClient();
		
		httpClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		
		//内容需要添加【喱喱学车】的签名
		String Content= "";
		try {
			Content = java.net.URLEncoder.encode(content+"【喱喱学车】",
				"utf-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error("SMSSendMessage encode content: " + e1);
			e1.printStackTrace();
		}
		
		String timestamp = String.valueOf(System.currentTimeMillis());
		String plainText = KL_eprId+KL_userId+KL_password+timestamp;
		String key = encryptMd5(plainText);
		
		String Url = KL_url + "cmd=send&erpId=" + KL_eprId + "&userId=" + KL_userId + "&mobile=" + phones + 
				"&timestamp=" + timestamp +
				"&key=" + key +
				"&msgId=" + RandomUtil.next() + "&format=1&content=" + Content;
		
		System.out.println(Url);
		GetMethod getMethod = new GetMethod(Url);
		getMethod.addRequestHeader("Connection", "close");
		
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}
			byte[] responseBody = getMethod.getResponseBody();
			String str = new String(responseBody, "GBK");
			//截取响应body的信息
			int beginPoint = str.indexOf("");
			int endPoint = str.indexOf("");
			String result = "RetCode=";
			System.out
					.println(result + str.substring(beginPoint + 9, endPoint));
			System.out.println(str);
		} catch (HttpException e) {
			System.out.println(1);
		} catch (IOException e) {
			System.out.println(2);
		} finally {
			getMethod.releaseConnection();
		}
	}

	public static void main(String[] args) {
		SMSMessageerviceImpl sms = new SMSMessageerviceImpl();
		sms.SendSMS("这是一条测试短信。", "17093437801");
		//System.out.println(sms.encryptMd5("123456"));
	}*/
	
	/**
	 * 高斯通短信发送
	 */
	public void SMSSendMessage_3(String mobiles, String templateId,
			String[] datas) {
		Boolean hasSent = redisUtil.get(RedisKeys.REDISKEY.LOCK_PRE + mobiles);
		if(hasSent != null && hasSent){
			return;
		}
		
		HttpClient httpClient = new HttpClient();
		
		httpClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		
		String doc = authcodeProp.getProperty("msg."+templateId);
		String content = parseDocParams(doc, datas);
		
		String Content= "", timestamp = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			Content = java.net.URLEncoder.encode(content, "GBK");
			timestamp = java.net.URLEncoder.encode(df.format(date), "GBK");
		} catch (UnsupportedEncodingException e1) {
			logger.error("SMSSendMessage encode content: " + e1);
			e1.printStackTrace();
		}
		
		String Url = Gsms_url + "username=" + Gsms_username + "&password=" + Gsms_password + "&to=" + mobiles + 
				"&content=" + Content + "&presendTime=" + timestamp;
		
		System.out.println(Url);
		GetMethod getMethod = new GetMethod(Url);
		getMethod.addRequestHeader("Connection", "close");
		
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}
			byte[] responseBody = getMethod.getResponseBody();
			String str = new String(responseBody, "GBK");

			String result = "RetCode=";
			System.out
					.println(result + str);
			System.out.println(str);
		} catch (HttpException e) {
			System.out.println(1);
		} catch (IOException e) {
			System.out.println(2);
		} finally {
			getMethod.releaseConnection();
		}
		
		redisUtil.set(RedisKeys.REDISKEY.LOCK_PRE + mobiles,true,10); //为避免客户端快速点击多次，限制十秒内才发一次
	}
}
