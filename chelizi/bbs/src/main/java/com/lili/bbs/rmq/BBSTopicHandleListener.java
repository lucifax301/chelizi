package com.lili.bbs.rmq;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.lili.bbs.dto.BbsTopic;
import com.lili.bbs.dto.BbsWeskit;
import com.lili.bbs.manager.IBBSManager;
import com.lili.bbs.vo.BbsMessage;
import com.lili.common.constant.JpushConstant;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.vo.JpushMsg;

/**
 * 异步处理保存发帖内容
 * @author lzb
 *
 */
public class BBSTopicHandleListener implements MessageListenerConcurrently {
	
	private Logger logger = Logger.getLogger(BBSTopicHandleListener.class);
	
    @Autowired
    private IBBSManager bbsManager;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @Resource(name = "jpushProducer")
  	DefaultMQProducer jpushProducer;
    
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		BbsMessage bbsMsg = null;
		try {
			for (MessageExt msg : msgs) {
				bbsMsg = SerializableUtil.unserialize(msg.getBody());
				logger.info("********************************* BBSTopicHandleListener Start : " + bbsMsg);
				if (bbsMsg != null) {
					// 用户是否是马甲，如果是马甲，发帖+1
					BbsWeskit bbsWeskit = new BbsWeskit();
					bbsWeskit.setUserId(bbsMsg.getUserId());
					bbsWeskit.setUserType(bbsMsg.getUserType());
					BbsWeskit isWeckit = bbsManager.isWeskit(bbsWeskit);
					if (isWeckit != null) {
						Integer bbsNum = isWeckit.getBbsNum();
						isWeckit.setBbsNum(bbsNum+1);
						bbsManager.updateBBSWeskit(isWeckit);
						
						if (isWeckit.getChannel() == 3) {
							bbsMsg.setChannel(3); //如果是管理员
						}
						else {
							bbsMsg.setChannel(2); //如果是马甲
						}
					}
					
					//插入主表、内容表
					bbsManager.saveBBSTopicInfo(bbsMsg);
					
					//话题帖子+1
					if (bbsMsg.getTopicId() != null && !"".equals(bbsMsg.getTopicId())) {
						BbsTopic topic = new BbsTopic();
						topic.setId(bbsMsg.getTopicId());
						bbsManager.updateTopicBbsNum(topic);
					}
					logger.info("********************************* BBSTopicHandleListener Do Success!");
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			logger.error( "*********************************BBSTopicHandleListener :" + msgs.size() + ", Exception:" + e.getMessage());
			// 推送给发帖人
			Long sizeOld = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
			logger.info("***************************************doPraiseAction sizeOld : " + sizeOld);
			
			redisUtil.zRemove(REDISKEY.BBS_STUDENT_LIST,  bbsMsg);
			
			Long sizeNew =  redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
			logger.info("***************************************doPraiseAction sizeNew : " + sizeNew);
			
			try {
				JpushMsg jmsg = new JpushMsg();
				jmsg.setAlter("发帖失败！");
				jmsg.setUserId(bbsMsg.getUserId());
				jmsg.setOrderId(bbsMsg.getId() + JpushConstant.OPERATE.BBS_DO_REALEASE);
				jmsg.setOperate(JpushConstant.OPERATE.BBS_DO_REALEASE);
				Message jpush = new Message();
				jpush.setKeys(bbsMsg.getUserId() + JpushConstant.OPERATE.BBS_DO_REALEASE);
				jpush.setTopic(jpushProducer.getCreateTopicKey());
				if (bbsMsg.getUserType() == 1) {
					jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
				}
				else {
					jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
				}
				jpush.setBody(SerializableUtil.serialize(jmsg));
				jpushProducer.send(jpush);
			} 
			catch (Exception e1) {
				logger.error( "*********************************BBSTopicHandleListener Jpush Error, Exception:" + e1.getMessage());
			} 
			
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
