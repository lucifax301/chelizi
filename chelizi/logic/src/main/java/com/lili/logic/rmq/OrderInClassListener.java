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
import com.lili.coach.manager.CoachManager;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.OrderConstant;
import com.lili.common.constant.RedisExpireConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.DateUtil;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.event.vo.CourseStatusEventVo;
import com.lili.file.manager.FileManager;
import com.lili.order.service.CoachClassService;
import com.lili.order.service.OrderService;
import com.lili.order.vo.CoachClassQuery;
import com.lili.order.vo.CoachClassVo;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;

public class OrderInClassListener implements MessageListenerConcurrently {
	private Logger log = Logger.getLogger(AcceptOrderListener.class);
	@Autowired
	private OrderService orderService;
	@Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;
	@Autowired
	private CoachManager coachManager;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private FileManager fileManager;
	@Autowired
	private CoachClassService coachClassService;
	
	@Autowired
	private NoticeManager noticeManager;
	
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		OrderVo order = null;
		try {

			for (MessageExt msg : msgs) {
				CourseStatusEventVo event = (CourseStatusEventVo) SerializableUtil
						.unserialize(msg.getBody());
				String orderId = event.getOrderId();
				// 0.保证消息不重复执行
				String redisKey = "orderinclass_order_" + orderId;
				order = orderService.queryOrderById(orderId,
						new OrderQuery());
				if (!redisUtil.isExist(redisKey)) {

					// 1.查询并更新订单信息
					order.setOrderState(OrderConstant.ORDERSTATE.INCLASS);
					order.setRstart(event.getTime());
					if (order.getOtype() != OrderConstant.OTYPE.BOOKORDER) {
						order.setRend(DateUtil.dateAfterMinute(event.getTime(),
								OrderConstant.clztime * order.getClzNum()));
					}
					orderService.saveOrder(order);
					// N.保证消息不重复执行
					redisUtil.set(redisKey, 1,
							RedisExpireConstant.RMQTIME.RETYSECONDS);
				}
				redisKey = "orderinclass_push_" + orderId;
				if (!redisUtil.isExist(redisKey)) {
					// 2. 向学生推送上课消息
					String content = "您的"
							+ fileManager.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname()
							+ "课程已经开始";
					JpushMsg jmsg = new JpushMsg();
					jmsg.setAlter(content);
					jmsg.setUserId(order.getStudentId());
					jmsg.setOperate(JpushConstant.OPERATE.STUCLASSIN);
					jmsg.setOrderId(order.getOrderId());
					jmsg.getExtras().put("payState", String.valueOf(order.getPayState()));
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
					notice.setTime(new Date());
					notice.setTitle("上课提醒");
					notice.setDigest("您的"
							+ fileManager.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname()
							+ "课程已经开始");
					Notice exticeNotice=noticeManager.getNoticeByOrderId(order.getOrderId(),(byte) 2);
					if(exticeNotice!=null){
						notice.setNoticeId(exticeNotice.getNoticeId());
						noticeManager.updateNotice(notice);
					}else{
						noticeManager.addNotice(notice);
					}
				}
				//3.更新排班
				redisKey = "orderinclass_class_" + orderId;
				if (!redisUtil.isExist(redisKey)) {
					CoachClassVo search=new CoachClassVo();
					search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
					if(order.getOtype()==OrderConstant.OTYPE.BOOKORDER && order.getCcid()!=null){
						search.setCcid(order.getCcid());
					} else {
						search.setOrderId(orderId);
					}
					List<CoachClassVo> list=coachClassService.queryByObjectAnd(search, new CoachClassQuery());
					if(list!=null && !list.isEmpty()){
						BeanCopy.setListField(list, "rstart", order.getRstart());
						BeanCopy.setListField(list, "rend", order.getRend());
						coachClassService.saveCoachClassList(list);
					}
					// N.保证消息不重复执行
					redisUtil.set(redisKey, 1,
							RedisExpireConstant.RMQTIME.RETYSECONDS);
				}
			}
		} catch (Exception e) {
			log.error(
					order + "OrderInClassListener Exception:"
							+ e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
