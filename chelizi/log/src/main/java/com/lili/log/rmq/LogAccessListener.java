package com.lili.log.rmq;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.lili.common.util.IPAddressUtils;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisUtil;
import com.lili.log.dto.LogOperate;
import com.lili.log.service.LogOperateManager;

public class LogAccessListener implements MessageListenerConcurrently {
	private Logger log = Logger.getLogger(LogAccessListener.class);
	@Autowired
	private LogOperateManager logOperateManager;
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		try {
			for(MessageExt msg : msgs){
				LogOperate ope = (LogOperate) SerializableUtil.unserialize(msg
						.getBody());
				//收到消息后，再处理，避免消息堆积在生产
//				try {
//					String ip = ope.getRemoteHost();
//					String address = redisUtil.get(RedisKeys.REDISKEY.IP_ADDRESS + ip);
//					if(null == address || "".equals(address)){
//						address = IPAddressUtils.getAddresses2(ip);
//						redisUtil.set(RedisKeys.REDISKEY.IP_ADDRESS + ip,address,60*60*24*7); //ip信息暂存7天
//					}
//					ope.setExtra(address);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
				logOperateManager.addLogOperate(ope);
				log.debug("LogAccessListener get a message. msgId="+msg.getMsgId());

			}
		} catch (Exception e) {
			log.error(
					msgs.size() + "LogAccessListener Exception:"
							+ e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
	
}
