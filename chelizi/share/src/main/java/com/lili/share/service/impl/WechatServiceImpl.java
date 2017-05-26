package com.lili.share.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lili.common.util.XmlProcessor;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.WechatXmlEntity;
import com.lili.share.dao.mapper.WechatLogMapper;
import com.lili.share.dto.WechatLog;
import com.lili.share.service.WechatService;

@Service
public class WechatServiceImpl implements WechatService {

	Logger logger = LoggerFactory.getLogger(WechatServiceImpl.class);

	@Autowired
	private WechatLogMapper wechatLogMapper;
	
	@Override
	public ReqResult wechatCallback(String result) {
		WechatXmlEntity entity = (WechatXmlEntity) XmlProcessor.getMsgEntity(result, WechatXmlEntity.class);
		logger.debug(entity.toString());
		String event = entity.getEvent();
		String eventKey = entity.getEventKey();
		String msgType = entity.getMsgType();
		WechatLog log = new WechatLog();
		log.setCtime(new Date());
		log.setEvent(event);
		log.setEventKey(eventKey);
		log.setMsgType(msgType);
		log.setOpenId(entity.getFromUserName());
		wechatLogMapper.insert(log);
		switch (event) {
		case "subscribe":		
			logger.info("***************** user {} subscribe wechat by {} **********************", entity.getToUserName(), eventKey);
			break;		
		case "unsubscribe":			
			logger.info("***************** user {} unsubscribe wechat by {} **********************", entity.getToUserName(), eventKey);
			break;			
		case "SCAN":		
			logger.info("***************** user {} scan wechat by {} **********************", entity.getToUserName(), eventKey);
			break;
		default:
			break;
		}
		ReqResult reqResult = ReqResult.getSuccess();
		return reqResult;
	}
	
	public static void main(String[] args) throws Exception {
		String xml = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[FromUser]]>"
				+ "</FromUserName><CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType>"
				+ "<Event><![CDATA[subscribe]]></Event><EventKey><![CDATA[qrscene_123123]]></EventKey><Ticket>"
				+ "<![CDATA[TICKET]]></Ticket></xml>";	
		new WechatServiceImpl().wechatCallback(xml);
	}

}
