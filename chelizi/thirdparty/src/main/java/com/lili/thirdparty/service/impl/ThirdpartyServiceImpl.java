package com.lili.thirdparty.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lili.common.util.EncryptUtil;
import com.lili.common.util.HttpDataUtil;
import com.lili.common.util.LogUtil;
import com.lili.common.vo.ReqResult;
import com.lili.thirdparty.service.ThirdpartyService;

@Service("thirdpartService")
public class ThirdpartyServiceImpl implements ThirdpartyService{

	@Value("#{config[weixin_appid]}")
	String weixinAppid;
	
	@Value("#{config[weixin_secret]}")
	String weixinSecret;
	
	@Value("#{config[weixin_token_url]}")
	String weixinTokenUrl;
	
	@Value("#{config[weixin_ticket_url]}")
	String weixinTicketUrl;
	
	private static Logger log = LoggerFactory.getLogger(ThirdpartyServiceImpl.class);
	
	@Override
	public ReqResult getWeixinToken(String noncestr,String timestamp,String url) {
//		weixinAppid="wxbbde6ffce207719a";
//		weixinSecret="f32ad5e73435181bed9e5cbbd3a0e9e8";
//		weixinTokenUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
//		weixinTicketUrl="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi";
		String step1url=weixinTokenUrl.replaceAll("\\{0\\}", weixinAppid).replaceAll("\\{1\\}", weixinSecret);
		ReqResult result=new ReqResult();
		try{
			log.debug("****************************request url is:"+step1url);
			String response= HttpDataUtil.getHttpURL(step1url, "GET");
			log.debug("****************************request response is:"+response);
			JSONObject json= JSON.parseObject(response);
			String token="";
			String ticket="";
			String sign="";
			if(json.containsKey("access_token")){
				token=json.getString("access_token");
				
				String step2url=weixinTicketUrl.replaceAll("\\{0\\}", token);
				log.debug("****************************request url is:"+step2url);
				String ticketresp= HttpDataUtil.getHttpURL(step2url, "GET");
				log.debug("****************************request url is:"+ticketresp);
				JSONObject ticketjson=JSON.parseObject(ticketresp);
				if(ticketjson.containsKey("errcode")){
					if("0".equals(ticketjson.getString("errcode"))){
						if(ticketjson.containsKey("ticket")){
							ticket=ticketjson.getString("ticket");
							
							String source="jsapi_ticket="+ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
							sign=EncryptUtil.SHA1(source);
						}
					}
				}
			}
			result.setCode("0");
			result.setData("token", token);
			result.setData("ticket", ticket);
			result.setData("sign", sign);
		}catch(Exception ex){
			log.warn("error happen****************"+LogUtil.getStackMsg(ex));
			result.setCode("-1");
			result.setData("");
		}
		return result;
	}
	
//	public static void main(String args[]){
//		ThirdpartyServiceImpl service=new ThirdpartyServiceImpl();
//		ReqResult result=service.getWeixinToken();
//		System.out.println(result.getResult());
//	}
}
