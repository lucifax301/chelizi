package com.lili.httpaccess.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lili.common.util.HttpDataUtil;
import com.lili.common.util.XmlProcessor;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.school.service.WechatCoachService;
import com.lili.school.service.WechatSchoolService;
import com.lili.thirdparty.service.ThirdpartyService;

/**
 * 调用第三方接口
 * @author xxx
 *
 */
@Controller
@RequestMapping("/v1/thirdparty")
public class ThirdpartyInterfaceController {   

	private Logger log = LoggerFactory.getLogger(ThirdpartyInterfaceController.class);
	
	@Value("#{config[weixin_secret]}")
	private String secret="f32ad5e73435181bed9e5cbbd3a0e9e8";
	@Value("#{config[weixin_appId]}")
	private String appId="wxbbde6ffce207719a";
	//喱喱教练公众号，服务器配置的token
	private String token_JL = "252a0774ae639b9cc465eb15d14b3635";
	@Autowired
	private ThirdpartyService service;
	
	@Autowired
	WechatSchoolService wechatSchoolService;
	
	@Autowired
	WechatCoachService wechatCoachService;
	
	@RequestMapping(value = "/weixintoken", method = RequestMethod.POST)
    @ResponseBody
    public Map getWeixinToken(@RequestParam String noncestr, @RequestParam String timestamp,
            @RequestParam String url){
		ReqResult result= service.getWeixinToken(noncestr,timestamp,url);
		return result.getResult();
	}
	
	/**
	 * 获取微信用户信息
	 *  
	 * @return
	 * @throws IOException 
	 * @throws ProtocolException 
	 * @throws MalformedURLException 
	 */
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	@ResponseBody
	public Object getUserInfo(@RequestParam String code) throws Exception {
		
		//通过code换取网页授权access_token
		String tokenUrl="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
		String response= HttpDataUtil.getHttpURL(tokenUrl, "GET");
		JSONObject json= JSON.parseObject(response);
		String access_token="";
		String oppenId="";
		if(json.containsKey("access_token")){
			access_token=json.getString("access_token");
			oppenId=json.getString("openid");
		}
		System.out.println("access_token==========="+access_token);
		System.out.println("oppenId==========="+oppenId);
		
		
		//拉取用户信息
    	String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+oppenId+"&lang=zh_CN" ;
    	String infoResponse= HttpDataUtil.getHttpURL(userInfoUrl, "GET");
		JSONObject infoJson= JSON.parseObject(infoResponse);
		ReqResult r = ReqResult.getSuccess();
		r.setData(infoJson);
		return r;
	}
	
	/**
	 * 喱喱教练-获取微信用户信息
	 *  
	 * @return
	 * @throws IOException 
	 * @throws ProtocolException 
	 * @throws MalformedURLException 
	 */
	@RequestMapping(value = "/getLiliCoachUserInfo", method = RequestMethod.GET)
	@ResponseBody
	public Object getLiliCoachUserInfo(@RequestParam String code) {
		
		ReqResult r = ReqResult.getSuccess();
		
		try {
			r = wechatCoachService.getLiliCoachUserInfo(code);
		} catch (Exception e) {
			log.error("************************************ getLiliCoachUserInfo Error : " + e.getMessage());
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		
		return r;
	}
	
	/*public static void main(String arg[]){
		ThirdpartyInterfaceController th=new ThirdpartyInterfaceController();
		try {
			th.getUserInfo("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	/*
	 * 微信开发者认证
	 */
	@RequestMapping(value = "/wechatCallBack", produces = "text/html", method = RequestMethod.GET)
	@ResponseBody
	public Object wechatCallBackGet(@RequestParam(required = false) String signature, @RequestParam(required = false) String timestamp, 
			@RequestParam(required = false) String nonce, @RequestParam(required = false) String echostr) {
		ReqResult r = ReqResult.getFailed();
		
		try {
			if (signature != null) {
				String[] encryptArray = {token_JL, timestamp, nonce};
				Arrays.sort(encryptArray);
				String encryptStr = "";
				for (String element : encryptArray) {
					encryptStr = encryptStr + element;
				}
				if (DigestUtils.shaHex(encryptStr).equalsIgnoreCase(signature)) 
					return Long.parseLong(echostr);
			}
		} catch (Exception e) {
			log.error("************************************ wechatCallBack Error : " + e.getMessage());
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r.getResult();
	}
	
	/*
	 * 接收微信事务消息
	 */
	@RequestMapping(value = "/wechatCallBack", produces = "application/xml", method = RequestMethod.POST)
	@ResponseBody
	public String wechatCallBack(@RequestBody String result, HttpServletResponse response) {
		ReqResult r = ReqResult.getFailed();
		response.setContentType("application/xml;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		StringBuffer out = new StringBuffer();
		
		try {
			Map<String, String> body = XmlProcessor.getMsgMap(result);
			if (body != null) {
				if (body.get("MsgType").equals("event")){
					if (body.get("Event").equalsIgnoreCase("subscribe") || body.get("Event").equalsIgnoreCase("SCAN")){
						r = wechatSchoolService.bindCoachByScan(body);
						if (r.isSuccess()) {
							out.append("<xml>");
							out.append("<ToUserName>" + body.get("FromUserName")+ "</ToUserName>");
							out.append("<FromUserName>lili_jiaolian</FromUserName>");
							out.append("<CreateTime>" + body.get("CreateTime") + "</CreateTime>");
							out.append("<MsgType>text</MsgType>");
							out.append("<Content>" + r.getResult().get(ResultCode.RESULTKEY.MSGINFO) + "</Content>");
							out.append("</xml>");
						}
					}
				}
			} 
			
			log.info(" ****************** body : \n" + result);
		} catch (Exception e) {
			log.error("************************************ wechatCallBack Error : " + e.getMessage());
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return out.length()>0?out.toString():"success";
	}
	
}
