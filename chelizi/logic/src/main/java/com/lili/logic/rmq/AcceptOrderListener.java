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
import com.lili.authcode.dto.Notice;
import com.lili.authcode.manager.NoticeManager;
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

public class AcceptOrderListener implements MessageListenerConcurrently {
	private Logger log = Logger.getLogger(AcceptOrderListener.class);
	@Autowired
	private CoachStatisticService coachStatisticService;
	@Resource(name = "jpushProducer")
    private DefaultMQProducer jpushProducer;
	@Autowired
	private CoachManager coachManager;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private CoachScoreService coachScoreService;
	
	@Autowired
	private NoticeManager noticeManager;
	
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		OrderVo order=null;
		try {
			for (MessageExt msg : msgs) {
				order = (OrderVo) SerializableUtil.unserialize(msg
						.getBody());
				// 0.保证消息不重复执行
				String redisKey = "acceptorder_push_" + order.getOrderId();
				if (!redisUtil.isExist(redisKey)) {
					// 1. 向学生推送接单消息
					Coach info = coachManager.getCoachInfo(order.getCoachId());
					String content = info.getName() + "教练将为您教学";
					JpushMsg jmsg = new JpushMsg();
					jmsg.setAlter(content);
					jmsg.setUserId(order.getStudentId());
					jmsg.setOperate(JpushConstant.OPERATE.STUACCEPTORDER);
					jmsg.setOrderId(order.getOrderId());
					Message jpush = new Message();
					jpush.setKeys(order.getOrderId());
					jpush.setTopic(jpushProducer.getCreateTopicKey());
					jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
					jpush.setBody(SerializableUtil.serialize(jmsg));
					jpushProducer.send(jpush);
					// N.保证消息不重复执行
					redisUtil.set(redisKey, 1,
							RedisExpireConstant.RMQTIME.RETYSECONDS);
					
					Notice notice=new Notice();
					notice.setUserId(order.getStudentId());
					notice.setType((byte) 2);
					notice.setUserType((byte) 2);
					notice.setOrderId(order.getOrderId());
					notice.setIsdel((byte)0);
					notice.setTime(new Date());
					notice.setTitle("订单消息");
					notice.setDigest(info.getName() + "教练将为您教学");
					Notice exticeNotice=noticeManager.getNoticeByOrderId(order.getOrderId(),(byte) 2);
					if(exticeNotice!=null){
						notice.setNoticeId(exticeNotice.getNoticeId());
						noticeManager.updateNotice(notice);
					}else{
						noticeManager.addNotice(notice);
					}
					
				}
				redisKey = "acceptorder_statistic_" + order.getOrderId();
				if (!redisUtil.isExist(redisKey)) {
					// 2.统计教练端流水,因为是同一个教练，此意不会存在并行的问题
					Date atime = order.getAtime();
					String date = TimeUtil.getDateFormat(atime, "yyyyMMdd");
					long coachId = order.getCoachId();
					CoachStatisticVo vo = coachStatisticService
							.getCoachStatistc(coachId, date);
					vo.setOrderAccept(vo.getOrderAccept() + 1);
					vo.setOrderMoney(vo.getOrderMoney() + order.getPriceTotal());//20161104现约的时候包含接送费也计入收入price-->priceTotal
					// 回存
					coachStatisticService.saveCoachStatistic(vo);
					// N.保证消息不重复执行
					redisUtil.set(redisKey, 1,
							RedisExpireConstant.RMQTIME.RETYSECONDS);
				}
				redisKey = "acceptorder_history_" + order.getOrderId();
				if (!redisUtil.isExist(redisKey)) {
					//可能存在并发
					CoachScoreVo cs=coachScoreService.queryCoachScoreById(order.getCoachId(),new CoachScoreQuery());
					if(cs==null){
						cs=new CoachScoreVo();
						cs.setCoachId(order.getCoachId());
					}
					cs.setAcceptOrder(cs.getAcceptOrder()+1);
					coachScoreService.saveCoachScore(cs);
					// N.保证消息不重复执行
					redisUtil.set(redisKey, 1,
							RedisExpireConstant.RMQTIME.RETYSECONDS);
				}
				// 3.后续的其他任务
			}
		} catch (Exception e) {
			log.error(
					order + "AcceptOrderListener Exception:"
							+ e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
