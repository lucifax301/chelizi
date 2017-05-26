package com.lili.logic.quartz;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.vo.CoachVo;
import com.lili.common.constant.JpushConstant;
import com.lili.common.util.DateUtil;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;

/**
 * 给教练推送通知，提醒教练休息时(07:05)要出车、出车时(23:05)要收车
 * @author lzb
 */
public class QuartzClassJpush {
	private Logger log = Logger.getLogger(QuartzClassJpush.class);
	private int liveSencode = 3600;
	
	@Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
    private CoachManager coachManager;
	
	public void doPush() {
		try {
			log.info("*************************** QuartzClassJpush DoPush Start ");
			Date now = new Date();
			if (DateUtil.getHour(now) > 12) { //如果系统时间大于16时，执行收车
				doOffPush();
			}
			else {//否则出车推送
				doOnPush();
			}
		} catch (Exception e) {
			log.info("*************************** QuartzClassJpush DoPush Error : " +  e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 出车推送
	 */
	public void doOnPush() {
		String redisKey = null;
		try {
			Integer pushType = 2;
			//查询推送教练名单表、管理教练表出车状态为收车状态的教练
			List<CoachVo> coachList = coachManager.getPushCoachList(pushType);
			log.info("************************Start DoOnPush CoachList : " + coachList.size());
			// 防止重复发送
			if (coachList != null && coachList.size() > 0) {
				String content = "亲爱的教练，早上好，快去出车吧";
				for (CoachVo  coachVo: coachList){
					redisKey = "QuartzJPush_coachClass_doOnPush_coachId" + coachVo.getCoachId();
					if (!redisUtil.isExist(redisKey)) {
						JpushMsg jmsg = new JpushMsg();
						jmsg.setAlter(content);
						jmsg.setUserId(coachVo.getCoachId());
						jmsg.setOperate(JpushConstant.OPERATE.COACHCLASSON);
						//jmsg.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE, JpushConstant.GENTYPE.AUTOGEN);
						//jmsg.getExtras().put(JpushConstant.EXTRAFIELD.PREPARE, "73");
						Message jpush = new Message();
						jpush.setTopic(jpushProducer.getCreateTopicKey());
						jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
						jpush.setBody(SerializableUtil.serialize(jmsg));
						jpushProducer.send(jpush);
						redisUtil.set(redisKey, 1, liveSencode);
						log.info("************************QuartzClassJpush DoOnPush Coach Phone : " + coachVo.getPhoneNum() + ", Content : "+ content);
					}
					log.info("************************QuartzClassJpush DoOnPush Has Push! Coach Phone : " + coachVo.getPhoneNum());
				}
			}
			
		} catch (MQClientException e) {
			log.error("******************************** QuartzClassJpush DoOnPush MQClientException ： " + e.getMessage() );
			redisUtil.delete(redisKey);
			e.printStackTrace();
		} catch (RemotingException e) {
			log.error("******************************** QuartzClassJpush DoOnPush RemotingException ： " + e.getMessage() );
			redisUtil.delete(redisKey);
			e.printStackTrace();
		} catch (MQBrokerException e) {
			log.error("******************************** QuartzClassJpush DoOnPush MQBrokerException： " + e.getMessage() );
			redisUtil.delete(redisKey);
			e.printStackTrace();
		} catch (InterruptedException e) {
			log.error("******************************** QuartzClassJpush DoOnPush InterruptedException： " + e.getMessage() );
			redisUtil.delete(redisKey);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 收车推送
	 */
	public void doOffPush() {
		String redisKey = null;// 防止重复发送
		try {
			Integer pushType = 1;
			//查询推送教练名单表、管理教练表出车状态为收车状态的教练
			List<CoachVo> coachList = coachManager.getPushCoachList(pushType);
			log.info("************************Start DoOffPush CoachList : " + coachList.size());
			if (coachList != null && coachList.size() > 0) {
				String content = "亲爱的教练，工作一天，该休息了，记得收车哦";
				for (CoachVo  coachVo: coachList){
					redisKey = "QuartzJPush_coachClass_doOffPush_coachId" + coachVo.getCoachId();
					if (!redisUtil.isExist(redisKey)) {
						JpushMsg jmsg = new JpushMsg();
						jmsg.setAlter(content);
						jmsg.setUserId(coachVo.getCoachId());
						jmsg.setOperate(JpushConstant.OPERATE.COACHCLASSOFF);
						//jmsg.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE, JpushConstant.GENTYPE.AUTOGEN);
						//jmsg.getExtras().put(JpushConstant.EXTRAFIELD.PREPARE, "73");
						Message jpush = new Message();
						jpush.setTopic(jpushProducer.getCreateTopicKey());
						jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
						jpush.setBody(SerializableUtil.serialize(jmsg));
						jpushProducer.send(jpush);
						redisUtil.set(redisKey, 1, liveSencode);
						log.info("************************QuartzClassJpush DoOffPush Coach Phone : " + coachVo.getPhoneNum() + ", Content : "+ content);
					}
					log.info("************************QuartzClassJpush DoOffPush Has Push! Coach Phone : " + coachVo.getPhoneNum());
				}
			}
		} catch (MQClientException e) {
			log.error("******************************** QuartzClassJpush DoOffPush MQClientException ： " + e.getMessage() );
			redisUtil.delete(redisKey);
			e.printStackTrace();
		} catch (RemotingException e) {
			log.error("******************************** QuartzClassJpush DoOffPush RemotingException ： " + e.getMessage() );
			redisUtil.delete(redisKey);
			e.printStackTrace();
		} catch (MQBrokerException e) {
			log.error("******************************** QuartzClassJpush DoOffPush MQBrokerException： " + e.getMessage() );
			redisUtil.delete(redisKey);
			e.printStackTrace();
		} catch (InterruptedException e) {
			log.error("******************************** QuartzClassJpush DoOffPush InterruptedException： " + e.getMessage() );
			redisUtil.delete(redisKey);
			e.printStackTrace();
		}
		
	}
}
