package com.lili.jpush.rmq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.OrderConstant;
import com.lili.common.constant.RedisExpireConstant;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.file.dto.Device;
import com.lili.file.service.FileService;

public class StuJPushListener implements MessageListenerConcurrently {
	@Value("${jpush.stuMasterSecret}")
	private String stuMasterSecret="d45c091e6fd6d7d85e016524";
	@Value("${jpush.stuAppKey}")
	private String stuAppKey="882ab536a72b77093a6f7529";
	@Value("${jpush.env}")
	private String env="publish";
	@Autowired
	private FileService fileService;
	
	private Logger log = Logger.getLogger(StuJPushListener.class);
	private JPushClient jpush;
	
	
	@Autowired
	private RedisUtil redisUtil;
	
	private JPushClient iosJpush;
	@Value("${ios.stuMasterSecret}")
	private String iosStuMasterSecret="7dd19b3a5c2b2d8ecde80542";
	@Value("${ios.stuAppKey}")
	private String iosStuAppKey="c1ce190a8a194ed1b756dec4";
	
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		JpushMsg jpushMsg=null;
		Device device=null;
		Options options=Options.newBuilder().setApnsProduction(true).build();  
		try {
			//待优化：去重复
			if( jpush==null ) {
				log.info(stuMasterSecret+" StuJPushListener use key "+stuAppKey);
				jpush = new JPushClient(stuMasterSecret, stuAppKey);
			}
			if(iosJpush==null) {
				log.info(iosStuMasterSecret+" IOS StuJPushListener use key "+iosStuAppKey);
				iosJpush = new JPushClient(iosStuMasterSecret, iosStuAppKey);
			}
			//黄铭：打开根据环境进行参数配置
			if(env==null || !env.equals("publish")){
				options=Options.newBuilder().setApnsProduction(false).build();
			}
			for (MessageExt msg : msgs) {
				jpushMsg = (JpushMsg) SerializableUtil.unserialize(msg
						.getBody());
				//屏蔽无operate码的消息发送
				if(jpushMsg.getOperate()==null){
					log.error(jpushMsg+" has null operate, so DONOT send.");
					continue;
				}
				
				//设置别名环境
				jpushMsg.setEnv(env);
				//防止重复发送
				String key="jpush_2_"+msg.getMsgId();
				PushResult result=null;
				if(!redisUtil.isExist(key)){
					//自动类型
					if(jpushMsg.getUserType()==null){
						jpushMsg.setUserType(OrderConstant.USETYPE.STUDENT);
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
							
							//所有用户发送   jmsg.setSendAll(JpushConstant.RMQTAG.JPUSH2STU);
							//极光推送 广播全量推送 Android端没区分测试环境正式环境   测试时只发ios
							if(jpushMsg.getSendAll()!=null){
								 payload=PushPayload.newBuilder()
						        .setPlatform(Platform.all())
						        .setAudience(Audience.all())
						        .setMessage(Message.newBuilder()
						                .setTitle(jpushMsg.getTitle())
						                .setMsgContent(jpushMsg.getAlter())
						                .addExtras(jpushMsg.getExtras())
						                .build())
						        .setNotification(Notification.newBuilder().addPlatformNotification(iosBuild.build()).build())
						        .setOptions(options)
						        .build();
							}
							boolean success=false;
							try {
								result=jpush.sendPush(payload);
								if(log.isDebugEnabled()){
									log.debug(jpushMsg+" has send to android,ios student ok="+result.isResultOK());
								}
								success=true;
							} catch(Exception e) {
								log.debug(jpushMsg+" has send to android,ios student Exception"+e.getMessage(),e);
							}
							try {
								result=iosJpush.sendPush(payload);
								if(log.isDebugEnabled()){
									log.debug(jpushMsg+" has send to android,iosOnly student ok="+result.isResultOK());
								}
							}catch(Exception e){
								if(!success){
									throw e;
								} else {
									log.debug(jpushMsg+" has send to android,iosOnly student Exception"+e.getMessage(),e);
								}
							}
					//2. IOS推送	
					} else if((device.getOsType()==DeviceConstant.OSTYPE.IOS ||device.getOsType()==DeviceConstant.OSTYPE.IOSBREAKOUT) && device.getJpush()==DeviceConstant.JPUSH.REGISTER){
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
								log.debug(jpushMsg+" has just send to ios student ok="+result.isResultOK()+" with device="+device);
							}
							success=true;
						} catch(Exception e){
							log.debug(jpushMsg+" has send to ios student Exception"+e.getMessage(),e);
						}
						try {
							result=iosJpush.sendPush(payload);
							if(log.isDebugEnabled()){
								log.debug(jpushMsg+" has just send to iosOnly student ok="+result.isResultOK()+" with device="+device);
							}
						} catch(Exception e){
							if(!success){
								throw e;
							} else {
								log.debug(jpushMsg+" has send to iosOnly student Exception"+e.getMessage(),e);
							}
						}
					//非ios设备都采用android推送
					} else {
						Map<String,String> extra=jpushMsg.getExtras();
						java.util.Iterator<String> it=extra.keySet().iterator();
						while(it.hasNext()){
							String ekey=it.next();
							if(extra.get(ekey)==null||extra.get(ekey).equals("null")){
								extra.put(ekey, "");
							}
						}
						
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
							log.debug(jpushMsg+" has just send to android student ok="+result.isResultOK()+" with device="+device);
						}
						
					}
					redisUtil.set(key, 1, RedisExpireConstant.RMQTIME.RETYSECONDS);
				}
			}
		} catch (Exception e) {
				log.error(
						jpushMsg + " send to StuJPushListener with device="+device+" Exception:"
							+ e.getMessage(), e);
//				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		} catch(Error ee){
			log.error(
					jpushMsg + " send to StuJPushListener with device="+device+" Exception:"
						+ ee.getMessage(), ee);
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
	
	
	public  void test(){
		JpushMsg jmsg=new JpushMsg();
		jmsg.setAlter("hahah");
		jmsg.setEnv("test");
		jmsg.setSound("sound101");
		jmsg.addUser(100000023L);
		jmsg.setOperate("101");
		jmsg.addUser(100000608L);
		List<MessageExt> list=new ArrayList<MessageExt>();
		MessageExt m=new MessageExt();
		list.add(m);
		m.setBody(SerializableUtil.serialize(jmsg));
		StuJPushListener a=new StuJPushListener();
		a.consumeMessage(list, null);
	}
}
