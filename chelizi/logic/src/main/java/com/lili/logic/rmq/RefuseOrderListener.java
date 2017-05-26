package com.lili.logic.rmq;

import java.util.Date;
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
import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CoachManager;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.RedisExpireConstant;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.order.service.CoachScoreService;
import com.lili.order.service.CoachStatisticService;
import com.lili.order.vo.CoachScoreQuery;
import com.lili.order.vo.CoachScoreVo;
import com.lili.order.vo.CoachStatisticVo;
import com.lili.order.vo.OrderVo;

public class RefuseOrderListener implements MessageListenerConcurrently {
	private Logger log = Logger.getLogger(AcceptOrderListener.class);
	@Autowired
	private CoachStatisticService coachStatisticService;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private CoachManager coachManager;

	@Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;
	@Autowired
	private CoachScoreService coachScoreService;
	
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		OrderVo order=null;
		try {
			for (MessageExt msg : msgs) {
				// 1.统计教练端流水,因为是同一个教练，此意不会存在并行的问题
				order = (OrderVo) SerializableUtil.unserialize(msg
						.getBody());
				// 0.保证消息不重复执行
				String redisKey = "refuseorder_push_" + order.getOrderId();
				if (!redisUtil.isExist(redisKey)) {
					// 1.消息推送
					JpushMsg jmsg = new JpushMsg();
					Message jpush = new Message();
					Coach coach = coachManager.getCoachInfo(order.getCoachId());
					String content = "很抱歉，" + coach.getName() + "教练暂时不能为您服务";
					jmsg.setAlter(content);
					jmsg.setUserId(order.getStudentId());
					jmsg.setOperate(JpushConstant.OPERATE.STUREFUSEORDER);
					jmsg.setOrderId(order.getOrderId());
					jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
					jpush.setKeys(order.getOrderId());
					jpush.setTopic(jpushProducer.getCreateTopicKey());
					jpush.setBody(SerializableUtil.serialize(jmsg));
					jpushProducer.send(jpush);
					// N.保证消息不重复执行
					redisUtil.set(redisKey, 1,
							RedisExpireConstant.RMQTIME.RETYSECONDS);
				}
				redisKey = "refuseorder_statistic_" + order.getOrderId();
				if (!redisUtil.isExist(redisKey)) {
					Date atime = order.getGtime();
					String date = TimeUtil.getDateFormat(atime, "yyyyMMdd");
					long coachId = order.getCoachId();
					CoachStatisticVo vo = coachStatisticService
							.getCoachStatistc(coachId, date);
					vo.setOrderRefuse(vo.getOrderRefuse() + 1);
					// 回存
					coachStatisticService.saveCoachStatistic(vo);
					// N.保证消息不重复执行
					redisUtil.set(redisKey, 1,
							RedisExpireConstant.RMQTIME.RETYSECONDS);
				}
				redisKey = "refuseorder_history_" + order.getOrderId();
				if (!redisUtil.isExist(redisKey)) {
					//可能存在并发
					CoachScoreVo cs=coachScoreService.queryCoachScoreById(order.getCoachId(),new CoachScoreQuery());
					if(cs==null){
						cs=new CoachScoreVo();
						cs.setCoachId(order.getCoachId());
					}
					cs.setRefuseOrder(cs.getRefuseOrder()+1);
					coachScoreService.saveCoachScore(cs);
					// N.保证消息不重复执行
					redisUtil.set(redisKey, 1,
							RedisExpireConstant.RMQTIME.RETYSECONDS);
				}
				// 2.后续的其他任务

			}
		} catch (Exception e) {
			log.error(
					order + "RefuseOrderListener Exception:"
							+ e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
