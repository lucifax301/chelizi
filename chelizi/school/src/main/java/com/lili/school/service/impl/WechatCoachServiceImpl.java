package com.lili.school.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Date;

import org.redisson.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lili.authcode.service.EmailService;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.file.service.GetAndPostService;
import com.lili.school.manager.WechatSchoolManager;
import com.lili.school.service.WechatCoachService;
import com.lili.school.service.WechatSchoolService;
import com.lili.school.vo.Template;
import com.lili.school.vo.WechatStudent;
import com.lili.school.vo.WechatTemplateLog;


public class WechatCoachServiceImpl implements WechatCoachService {

	private static Logger logger = LoggerFactory.getLogger(WechatCoachServiceImpl.class);
	
	@Autowired
	WechatSchoolManager wechatSchoolManager;
	
	@Autowired
	WechatSchoolService wechatSchoolService;
	
	@Autowired
	GetAndPostService getAndPostService;
	
    @Autowired
    EmailService emailService;
	
	@Autowired
	RedisUtil redisUtil;
	
    @Autowired
    RedissonClient redissonClient;
	
	@Value("#{configWxCoach[weixin_secret]}")
	private static String secret= "d85bcd20d3fa6bacc7789e2a16bfb7ec";
	
	@Value("#{configWxCoach[weixin_appId]}")
	private static String appId= "wxbecb58fc75a2abcf";
	
	@Value("#{configWxCoach[weixin_token_url]}")
	private static String tokenUrl= "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
	
	@Value("#{configWxCoach[weixin_qrcode_url]}")
	private static String qrcodeUrl= "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token={0}";
	
	@Value("#{configWxCoach[weixin_template_send_url]}")
	private static String templateSendUrl= "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";
	
	@Value("#{configWxCoach[weixin_userInfo_url]}")
	private static String userInfoUrl= "https://api.weixin.qq.com/sns/userinfo?access_token={0}&openid={1}&lang=zh_CN";
	
	@Value("#{configWxCoach[weixin_authCode_url]}")
	private static String authCodeUrl= "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";
	

	/**
	 * 发送通知消息
	 */
	@Override
	public boolean sendTemplateMsg(Template template) {
		boolean flag = false;
		try {
			//替换token
			String requestUrl=templateSendUrl.replaceAll("\\{0\\}", getToken());
			//POST到微信服务器
			JSONObject jsonResult = JSON.parseObject(getAndPostService.sendPost(requestUrl, template.toJSON()));
			if (jsonResult != null) {
				if (jsonResult.containsKey("errcode")) {
					String errorMessage = jsonResult.getString("errmsg");
					String errorCode = jsonResult.getString("errcode");
					String msgId = jsonResult.getString("msgid");
					if ("0".equals(jsonResult.getString("errcode"))) {
						flag = true;
					} else {
						logger.error("************************************** sendTemplateMsg Send Fail! errorCode: " + errorCode + ", errorMessage: " + errorMessage);
						flag = false;
					}
					
					// 记录日志
					WechatTemplateLog wechatTemplateLog = new WechatTemplateLog();
					WechatStudent wechatStudent = new WechatStudent();
					wechatStudent.setOpenId(template.getToUser());
					WechatStudent wechatStudentInfo = wechatSchoolManager.queryWechatStudent(wechatStudent);
					if (wechatStudentInfo != null) {
						wechatTemplateLog.setStudentId(wechatStudentInfo.getStudentId());
						wechatTemplateLog.setStudentName(wechatStudentInfo.getName());
						wechatTemplateLog.setStudentPhone(wechatStudentInfo.getPhone());
					}
					wechatTemplateLog.setToUser(template.getToUser());
					wechatTemplateLog.setUrl(template.getUrl());
					wechatTemplateLog.setCtime(new Date());
					wechatTemplateLog.setErrcode(Integer.parseInt(errorCode));
					wechatTemplateLog.setErrmsg(errorMessage);
					wechatTemplateLog.setTemplateId(template.getTemplateId());
					wechatTemplateLog.setTemplateParam(template.getTemplateParamList().toString());
					if (StringUtils.isNotEmpty(msgId)) {
						wechatTemplateLog.setMsgId(Integer.parseInt(msgId));
					}
					wechatSchoolManager.insertWxTemplateLog(wechatTemplateLog);
				}
				else { //未知异常
					logger.error("************************************** sendTemplateMsg Send Fail! jsonResult: " + jsonResult.toString());
				}
			}
			else { //POST失败
				logger.error("*********************************** sendTemplateMsg Send Fail ! template :" + template);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 获取教练个人二维码
	 * 二维码是永久的，只取一次做永久缓存，同时存数据表
	 * @param token
	 * @param postStr
	 * @return
	 */
	@Override
	public String getWikiUrl(Long coachId){  
		String wikiUrl = null;
			try {
				String postStr = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": " + coachId +"}}}";		
				String token = getToken();
				String requestUrl=qrcodeUrl.replaceAll("\\{0\\}", token);
				JSONObject jsonResult = JSON.parseObject(getAndPostService.sendPost(requestUrl, postStr));
				if (jsonResult != null) {
					if (jsonResult.containsKey("ticket")) {
						String ticket = jsonResult.getString("ticket"); // 获取到ticket
						//对TICKET进行UrlEncode
						logger.info( "******************************************** ticket :" + ticket );
						ticket = java.net.URLEncoder.encode(ticket, "utf-8");
						
						//拼接URL链接请求
						wikiUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
						logger.info(" ******************************************** url :" + wikiUrl);
						
						// 通过ticket换取二维码
						//String ticketresp= HttpDataUtil.getHttpURL(wikiUrl, "GET");
						//JSONObject ticketjson=JSON.parseObject(ticketresp);
						//logger.info("******************************************** ticketjson: " + ticketjson.toString());
					}
					else {
						String errorMessage = jsonResult.getString("errmsg");  
						String errorCode = jsonResult.getString("errmsg"); 
						logger.info("获取二位码失败: errorCode : " + errorCode + ",errorMessage : " + errorMessage);  
					}
				}
				else {
					logger.info("请求二维码链接失败");
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		return wikiUrl;  
	}  
	
	/**
	 * 自定义
	 * @param token
	 * @param menuStr
	 * @return
	 */
	public boolean menuInfo(String token,String menuStr){  
		boolean flag = false;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";//删除
		requestUrl = requestUrl.replace("ACCESS_TOKEN", token);

		JSONObject jsonResult = JSON.parseObject(getAndPostService.sendPost(requestUrl, menuStr));
		if (jsonResult != null) {
			String errorMessage = jsonResult.getString("errmsg");
			if (jsonResult.containsKey("errcode")) {
				String errorCode = jsonResult.getString("errmsg");
				if ("0".equals(jsonResult.getString("errcode"))) {
					flag = true;
					// 记录日志
				} else {
					logger.info("自定义菜单创建失败, errorCode: " + errorCode + ", errorMessage: " + errorMessage);
					flag = false;
				}
			}
		}
		return flag;
	}

	@Override
	public String getToken() {
		String token = null;
		try {
			token = redisUtil.get(REDISKEY.LILI_COACH_TOKEN);
			if (token == null) {
				logger.info("******************************** tokenUrl : "+tokenUrl);
				logger.info("******************************** appId : "+appId);
				logger.info("******************************** secret : "+secret);
				String step1url=tokenUrl.replaceAll("\\{0\\}", appId).replaceAll("\\{1\\}", secret);
				JSONObject jsonResult = JSON.parseObject(getAndPostService.getHttpURL(step1url, "GET"));
				logger.info("******************************** jsonResult : "+jsonResult);
				if (null != jsonResult) {
					logger.info("*********************************** token :" + jsonResult.getString("access_token"));
					logger.info("*********************************** expires_in: " + jsonResult.getIntValue("expires_in"));
					token = jsonResult.getString("access_token");
					//token保留一小时
					//redisUtil.set(REDISKEY.LILI_COACH_TOKEN, token, 3600);
				}
				else {
					logger.error("*********************************** getToken Fail");
				}
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return token;
	}

	/**
	 * 通过授权获取微信用户信息
	 */
	@Override
	public ReqResult getLiliCoachUserInfo(String code) {
		ReqResult r = ReqResult.getSuccess();
		try {
			//通过code换取网页授权access_token
			String step1 = authCodeUrl.replaceAll("\\{0\\}", appId).replaceAll("\\{1\\}", secret).replaceAll("\\{2\\}", code);
			String response= getAndPostService.getHttpURL(step1, "GET");
			if (response != null && !"".equals(response)){
				JSONObject json= JSON.parseObject(response);
				String access_token="";
				String oppenId="";
				if (json.containsKey("access_token")) {
					access_token=json.getString("access_token");
					oppenId=json.getString("openid");
				}
				else {
					logger.error("*********************************** json : " + json);
					r.setCode(ResultCode.ERRORCODE.EXCEPTION);
					r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
					return r;
				}
				logger.info("***********************************" + access_token);
				logger.info("***********************************" + oppenId);
				
				//拉取用户信息
				String step2 = userInfoUrl.replaceAll("\\{0\\}", access_token).replaceAll("\\{1\\}", oppenId);
				String infoResponse= getAndPostService.getHttpURL(step2, "GET");
				if (StringUtils.isNotEmpty(infoResponse)) {
					JSONObject userInfoJson= JSON.parseObject(infoResponse);
					logger.info("*********************************** userInfoJson :" + userInfoJson);
					//将用户信息更新到学员信息表
					wechatSchoolService.saveWechatUserInfo(oppenId, userInfoJson);
					r.setData(userInfoJson);
				}
				else {
					logger.error("*********************************** infoResponse is null! infoResponse : " + infoResponse);
				}
			}
			else {
				logger.error("*********************************** response is null! response : " + response);
			}
			
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		} 
		catch (ProtocolException e) {
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		catch (IOException e) {
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r;
	}


}
