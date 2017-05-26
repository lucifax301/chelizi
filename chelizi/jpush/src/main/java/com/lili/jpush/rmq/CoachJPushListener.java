package com.lili.jpush.rmq;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.lili.common.constant.DeviceConstant;
import com.lili.common.constant.OrderConstant;
import com.lili.common.constant.RedisExpireConstant;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.file.dto.Device;
import com.lili.file.service.FileService;

public class CoachJPushListener implements MessageListenerConcurrently {
	@Value("${jpush.coachMasterSecret}")
	private String coachMasterSecret="f2ce7a37e1af60d9b2d617af";
	@Value("${jpush.coachAppKey}")
	private String coachAppKey="d581e38a392c388c8a2270dc";
	
	@Value("${jpush.env}")
	private String env="publish";
	@Autowired
	private FileService fileService;
	
	
	private Logger log = Logger.getLogger(CoachJPushListener.class);
	private JPushClient jpush;
	
	@Autowired
	private RedisUtil redisUtil;
	
	private JPushClient iosJpush;
	@Value("${ios.coachMasterSecret}")
	private String iosCoachMasterSecret="0ae96d0ad662466964782ae5";
	@Value("${ios.coachAppKey}")
	private String iosCoachAppKey="33eeccba421ca2e4f22a1288";
	
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		JpushMsg jpushMsg=null;
		Device device=null;
		Options options=Options.newBuilder().setApnsProduction(true).build(); 
		try {
			if( jpush==null ) {
				log.info(coachMasterSecret+" CoachJPushListener use key "+coachAppKey);
				jpush=new JPushClient(coachMasterSecret, coachAppKey);
			}
			if(iosJpush==null) {
				log.info(iosCoachMasterSecret+" IOS CoachJPushListener use key "+iosCoachAppKey);
				iosJpush = new JPushClient(iosCoachMasterSecret, iosCoachAppKey);
			}
			//打开根据环境进行参数配置
			//2016-06-29：熊浦荣  教练端开发证书没有上传，不能打开。
			if(env==null || !env.equals("publish") ){
				options=Options.newBuilder().setApnsProduction(false).build();
			}
			for (MessageExt msg : msgs) {
				// 1.消息推送
				jpushMsg = (JpushMsg) SerializableUtil.unserialize(msg
						.getBody());
				//屏蔽无operate码的消息发送
				if(jpushMsg.getOperate()==null){
					log.error(jpushMsg+" has null operate, so DONOT send.");
					continue;
				}
				//设置别名环境
				jpushMsg.setEnv(env);
				String key="jpush_1_"+msg.getMsgId();
				PushResult result=null;
				//防止重复发送
				if(!redisUtil.isExist(key)){
					//自动类型
					if(jpushMsg.getUserType()==null){
						jpushMsg.setUserType(OrderConstant.USETYPE.COACH);
					}
					//非批量推送才区分设备
					if(jpushMsg.getUserId()!=null) {
						device=fileService.getDevice(String.valueOf(jpushMsg.getUserId()), String.valueOf(jpushMsg.getUserType()));
					}
					IosNotification.Builder  iosBuild=IosNotification.newBuilder()
					.setAlert(jpushMsg.getAlter())
					.setBadge(1)
                    .addExtras(jpushMsg.getExtras());
                    if(StringUtil.isNotNullAndNotEmpty(jpushMsg.getSound())){
                    	iosBuild.setSound(jpushMsg.getSound());
                    //空则传默认声音
                    } else {
                    	iosBuild.setSound("default");
                    }
					//1. 不清楚设备，使用双推送
					if(device==null){
							PushPayload payload = PushPayload.newBuilder()
					                .setPlatform(Platform.all())
					                .setAudience(Audience.alias(jpushMsg.getAlias()))
					                .setMessage(Message.newBuilder()
					                        .setTitle(jpushMsg.getTitle())
					                        .setMsgContent(jpushMsg.getAlter())
					                        .addExtras(jpushMsg.getExtras())
					                        .build())
					                .setNotification(Notification.newBuilder().addPlatformNotification(iosBuild.build()).build())
					                .setOptions(options)
					                .build();
							boolean success=false;
							try {
								result=jpush.sendPush(payload);
								if(log.isDebugEnabled()){
									log.debug(jpushMsg+" has send to android,ios coach ok="+result.isResultOK());
								}
								success=true;
							} catch(Exception e) {
								log.debug(jpushMsg+" has send to android,ios coach Exception"+e.getMessage(),e);
							}
							try {
								result=iosJpush.sendPush(payload);
								if(log.isDebugEnabled()){
									log.debug(jpushMsg+" has send to android,iosOnly coach ok="+result.isResultOK());
								}
							} catch(Exception e) {
								if(!success){
									throw e;
								} else {
									log.debug(jpushMsg+" has send to android,iosOnly coach Exception"+e.getMessage(),e);
								}
							}
					//2. IOS推送	
					} else if((device.getOsType()==DeviceConstant.OSTYPE.IOS ||device.getOsType()==DeviceConstant.OSTYPE.IOSBREAKOUT) && device.getJpush()==DeviceConstant.JPUSH.REGISTER ){
						PushPayload payload = PushPayload.newBuilder()
				                .setPlatform(Platform.ios())
				                .setAudience(Audience.alias(jpushMsg.getAlias()))
				                .setNotification(Notification.newBuilder().addPlatformNotification(iosBuild.build()).build())
				                .setOptions(options)
				                .build();
						boolean success=false;
						try {
							result=jpush.sendPush(payload);
							if(log.isDebugEnabled()){
								log.debug(jpushMsg+" has just send to ios coach ok="+result.isResultOK()+" with device="+device);
							}
							success=true;
						} catch(Exception e) {
							log.debug(jpushMsg+" has send to ios coach Exception"+e.getMessage(),e);
						}
						try {
							result=iosJpush.sendPush(payload);
							if(log.isDebugEnabled()){
								log.debug(jpushMsg+" has just send to iosOnly coach ok="+result.isResultOK()+" with device="+device);
							}
						} catch(Exception e) {
							if(!success){
								throw e;
							} else {
								log.debug(jpushMsg+" has send to iosOnly coach Exception"+e.getMessage(),e);
							}
						}
					//非ios设备都采用android推送
					} else {
						PushPayload payload = PushPayload.newBuilder()
				                .setPlatform(Platform.android())
				                .setAudience(Audience.alias(jpushMsg.getAlias()))
				                .setMessage(Message.newBuilder()
				                        .setTitle(jpushMsg.getTitle())
				                        .setMsgContent(jpushMsg.getAlter())
				                        .addExtras(jpushMsg.getExtras())
				                        .build())
				                .build();
						result=jpush.sendPush(payload);
						if(log.isDebugEnabled()){
							log.debug(jpushMsg+" has just send to android coach ok="+result.isResultOK()+" with device="+device);
						}
					}
					redisUtil.set(key, 1, RedisExpireConstant.RMQTIME.RETYSECONDS);
				}
			}
		} catch (Exception e) {
				log.error(
						jpushMsg + " send to CoachJPushListener with device="+device+" Exception:"
							+ e.getMessage(), e);
//				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
