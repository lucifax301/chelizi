package com.lili.logic.rmq;

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
import com.lili.common.constant.OrderConstant;
import com.lili.common.constant.RedisExpireConstant;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.order.service.CoachScoreService;
import com.lili.order.service.CoachStatisticService;
import com.lili.order.vo.CoachCommentVo;
import com.lili.order.vo.CoachScoreQuery;
import com.lili.order.vo.CoachScoreVo;
import com.lili.order.vo.CoachStatisticVo;

public class CommentCoachListener implements MessageListenerConcurrently {
	private Logger log = Logger.getLogger(AcceptOrderListener.class);
	@Autowired
	private CoachScoreService coachScoreService;
	@Autowired
	private CoachStatisticService coachStatisticService;
	@Autowired
	private RedisUtil redisUtil;
	@Resource(name="orderProducer")
	private DefaultMQProducer orderProducer;
	
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		// 待优化：去重复
		CoachCommentVo cc=null;
		try {
			for (MessageExt msg : msgs) {
				// 1.计算当日统计
				cc = (CoachCommentVo) SerializableUtil
						.unserialize(msg.getBody());
				// 0.保证消息不重复执行
				String redisKey = "commentorder_static_" + cc.getOrderId();
				if (!redisUtil.isExist(redisKey)) {
					String date = TimeUtil.getDateFormat(cc.getCotime(),
							"yyyyMMdd");
					CoachStatisticVo vo = coachStatisticService
							.getCoachStatistc(cc.getCoachId(), date);
					vo.setOrderComment(vo.getOrderComment() + 1);
					vo.setCommentScore(vo.getCommentScore() + cc.getScore());
					// 回存
					coachStatisticService.saveCoachStatistic(vo);
					// N.保证消息不重复执行
					redisUtil.set(redisKey, 1,
							RedisExpireConstant.RMQTIME.RETYSECONDS);
				}
				redisKey = "commentorder_score_" + cc.getOrderId();
				if (!redisUtil.isExist(redisKey)) {
					// 2.计算历史统计
					CoachScoreVo cs = coachScoreService.queryCoachScoreById(
							cc.getCoachId(), new CoachScoreQuery());
					if (cs == null) {
						cs = new CoachScoreVo();
						cs.setCoachId(cc.getCoachId());
					}
					cs.setScoreTotal(cs.getScoreTotal() + cc.getScore());
					cs.setOrderTotal(cs.getOrderTotal() + 1);
					// 回存
					coachScoreService.saveCoachScore(cs);
					// 3.消息推送
					Message comment=new Message();
					comment.setKeys(String.valueOf(cs.getCoachId()));
					comment.setTopic(orderProducer.getCreateTopicKey());
					comment.setTags(OrderConstant.RMQTAG.COACHSCROE);
					comment.setBody(SerializableUtil.serialize(cs));
				    orderProducer.send(comment);
					// N.保证消息不重复执行
					redisUtil.set(redisKey, 1,
								RedisExpireConstant.RMQTIME.RETYSECONDS);
				}
			}
		} catch (Exception e) {
			log.error(
					cc + "CommentCoachListener Exception:"
							+ e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
}
