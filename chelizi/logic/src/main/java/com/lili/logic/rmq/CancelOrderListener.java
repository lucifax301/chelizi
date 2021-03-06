package com.lili.logic.rmq;

import java.text.SimpleDateFormat;
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
import com.lili.common.constant.OrderConstant;
import com.lili.common.constant.RedisExpireConstant;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.file.manager.FileManager;
import com.lili.order.service.CoachScoreService;
import com.lili.order.service.CoachStatisticService;
import com.lili.order.service.OrderCancelService;
import com.lili.order.vo.CoachScoreQuery;
import com.lili.order.vo.CoachScoreVo;
import com.lili.order.vo.CoachStatisticVo;
import com.lili.order.vo.OrderVo;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;

public class CancelOrderListener implements MessageListenerConcurrently {
	private Logger log = Logger.getLogger(CancelOrderListener.class);

	@Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;
	@Autowired
	private CoachManager coachManager;
	@Autowired
	private StudentManager studentManager;
	@Autowired
	private CoachStatisticService coachStatisticService;
	@Autowired
	OrderCancelService orderCancelService;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private FileManager fileManager;
	@Autowired
	private CoachScoreService coachScoreService;
	
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		// 待优化：去重复
		OrderVo order=null;
		try {
			for (MessageExt msg : msgs) {
				// 统计教练端流水,因为是同一个教练，此意不会存在并行的问题
				order = (OrderVo) SerializableUtil.unserialize(msg
						.getBody());
                //因为有时候读取出问题，因此写死只有学员能取消订单
//				OrderCancelVo oc = orderCancelService.queryByOrderId(
//						order.getOrderId(), new OrderCancelQuery()).get(0);
				// 0.保证消息不重复执行
				String redisKey = "cancelorder_push_" + order.getOrderId();
				//无论是否接单的消息都要推送
				if (!redisUtil.isExist(redisKey)) {
						// 1.消息推送
						JpushMsg jmsg = new JpushMsg();
						Message jpush = new Message();
						//临时约定使用该字段作为用户类型
						int userType=Integer.valueOf(String.valueOf(order.getTimeLeft()));
						String course=fileManager.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname();
						if (userType == OrderConstant.USETYPE.COACH) {
							Coach coach = coachManager.getCoachInfo(order
									.getCoachId());
							String cname= coach.getName();
							String content = "很抱歉，" + cname + "教练取消了您的"+ course+ "课程";
							jmsg.setAlter(content);
							jmsg.setUserId(order.getStudentId());
							jmsg.setOperate(JpushConstant.OPERATE.STUCANCELORDER);
							jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
							jmsg.setOrderId(order.getOrderId());
							jpush.setKeys(order.getOrderId());
							jpush.setTopic(jpushProducer.getCreateTopicKey());
							jpush.setBody(SerializableUtil.serialize(jmsg));
							jpushProducer.send(jpush);
						} else if (userType == OrderConstant.USETYPE.STUDENT) {
							Student stu = studentManager.getStudentInfo(order
									.getStudentId());
							String sname=stu.getName();
							SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String ctime = null;
							if (order != null && order.getPstart() != null) {
								 ctime = formatter.format(order.getPstart());
							}
							String content = sname + "已取消了" + course + " " + ctime + "的预约订单，点击可查看详情。";
							jmsg.setAlter(content);
							jmsg.setUserId(order.getCoachId());
							jmsg.setOperate(JpushConstant.OPERATE.COACHCANCELORDER);
							jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
							jmsg.setOrderId(order.getOrderId());
							jpush.setKeys(order.getOrderId());
							jpush.setTopic(jpushProducer.getCreateTopicKey());
							jpush.setBody(SerializableUtil.serialize(jmsg));
							jpushProducer.send(jpush);
						} else {
							//推送学生
							String content = "您约的"+course+"课程被取消，请重新下单，如有疑问请联系客服";
							jmsg.setAlter(content);
							jmsg.setUserId(order.getStudentId());
							jmsg.setOperate(JpushConstant.OPERATE.STUCANCELORDER);
							jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
							jmsg.setOrderId(order.getOrderId());
							jpush.setKeys(order.getOrderId());
							jpush.setTopic(jpushProducer.getCreateTopicKey());
							jpush.setBody(SerializableUtil.serialize(jmsg));
							jpushProducer.send(jpush);
							//推送教练
							content = "您的订单已被取消，请您重新接单，如有疑问请您联系客服";
							jmsg.setAlter(content);
							jmsg.setUserId(order.getCoachId());
							jmsg.setOperate(JpushConstant.OPERATE.COACHCANCELORDER);
							jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
							jmsg.setOrderId(order.getOrderId());
							jpush.setKeys(order.getOrderId());
							jpush.setTopic(jpushProducer.getCreateTopicKey());
							jpush.setBody(SerializableUtil.serialize(jmsg));
							jpushProducer.send(jpush);							
						}
						
					// N.保证消息不重复执行
					redisUtil.set(redisKey, 1,
							RedisExpireConstant.RMQTIME.RETYSECONDS);
				}
				// 2.教练日流水统计
				redisKey = "cancelorder_statis_" + order.getOrderId();
				if (!redisUtil.isExist(redisKey)) {
					//只有已接单的才会改变教练流水
//					if(order.getAtime()!=null){
						Date atime = order.getAtime();
						if(atime==null){
							atime=order.getGtime();
						}
						String date = TimeUtil.getDateFormat(atime, "yyyyMMdd");
						long coachId = order.getCoachId();
						CoachStatisticVo vo = coachStatisticService
								.getCoachStatistc(coachId, date);
						vo.setOrderCancel(vo.getOrderCancel() + 1);
						vo.setOrderMoney(vo.getOrderMoney() -  order.getPriceTotal());//20161104现约的时候包含接送费也计入收入price-->priceTotal
						// 回存
						coachStatisticService.saveCoachStatistic(vo);
//					}
					// N.保证消息不重复执行
					redisUtil.set(redisKey, 1,
							RedisExpireConstant.RMQTIME.RETYSECONDS);
					
				}
				redisKey = "cancelorder_history_" + order.getOrderId();
				if (!redisUtil.isExist(redisKey)) {
					//可能存在并发
					//只有已接单的才会改变历史统计
					if(order.getAtime()!=null){
						CoachScoreVo cs=coachScoreService.queryCoachScoreById(order.getCoachId(),new CoachScoreQuery());
						if(cs==null){
							cs=new CoachScoreVo();
							cs.setCoachId(order.getCoachId());
						}
						cs.setCancelOrder(cs.getCancelOrder()+1);
						coachScoreService.saveCoachScore(cs);
					}
					// N.保证消息不重复执行
					redisUtil.set(redisKey, 1,
							RedisExpireConstant.RMQTIME.RETYSECONDS);
				}
				// 2.后续的其他任务

			}
		} catch (Exception e) {
			log.error(
					order + "CancelOrderListener Exception:"
							+ e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
