package com.lili.logic.rmq;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.aspectj.weaver.ast.And;
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
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.StringUtil;
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
import com.lili.pay.manager.MoneyManager;
import com.lili.pay.service.PayServiceNew;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;

public class OrderOutClassListener implements MessageListenerConcurrently {
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
	private PayServiceNew payServiceNew;
	@Autowired
	private MoneyManager moneyManager;
	@Autowired
	private StudentManager studentManager;
	@Autowired
	private NoticeManager noticeManager;

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		OrderVo order = null;
		try {
			for (MessageExt msg : msgs) {
				CourseStatusEventVo event = (CourseStatusEventVo) SerializableUtil.unserialize(msg.getBody());
				// 只处理手工下课
				if (!event.isAutoGen()) {
					String orderId = event.getOrderId();
					order = orderService.queryOrderById(orderId, new OrderQuery());
					// 1. 向学生推送下课消息
					String redisKey = "orderoutclass_push_" + orderId;
					if (!redisUtil.isExist(redisKey)) {
						// if(event.getTargetType()==OrderConstant.USETYPE.STUDENT)
						// {
						{
							String content = "您的" + fileManager
									.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname()
									+ "课程训练已经完成，稍后教练会为您这次的训练情况进行评分";
							JpushMsg jmsg = new JpushMsg();
							jmsg.setAlter(content);
							jmsg.setUserId(order.getStudentId());
							jmsg.setOperate(JpushConstant.OPERATE.STUCLASSOUT);
							jmsg.setOrderId(order.getOrderId());
							jmsg.getExtras().put(JpushConstant.EXTRAFIELD.PAYSTATE,
									String.valueOf(order.getPayState()));
							Message jpush = new Message();
							jpush.setKeys(order.getOrderId());
							jpush.setTopic(jpushProducer.getCreateTopicKey());
							jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
							jpush.setBody(SerializableUtil.serialize(jmsg));
							jpushProducer.send(jpush);
							
							Notice notice=new Notice();
							notice.setUserId(order.getStudentId());
							notice.setType((byte) 2);
							notice.setUserType((byte) 2);
							notice.setOrderId(order.getOrderId());
							notice.setTime(new Date());
							notice.setTitle("课程完成");
							notice.setDigest("您的" + fileManager
									.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname()
									+ "课程训练已经完成，稍后教练会为您这次的训练情况进行评分");
							Notice exticeNotice=noticeManager.getNoticeByOrderId(order.getOrderId(),(byte) 2);
							if(exticeNotice!=null){
								notice.setNoticeId(exticeNotice.getNoticeId());
								noticeManager.updateNotice(notice);
							}else{
								noticeManager.addNotice(notice);
							}
						}
						// } else {
						{
							String content = "这次" + fileManager
									.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname()
									+ "课程下课时间到了，请您为学员的训练情况进行评分";
							JpushMsg jmsg = new JpushMsg();
							jmsg.setAlter(content);
							jmsg.setUserId(order.getCoachId());
							jmsg.setOperate(JpushConstant.OPERATE.COACHCLASSOUT);
							jmsg.setOrderId(order.getOrderId());
							Message jpush = new Message();
							jpush.setKeys(order.getOrderId());
							jpush.setTopic(jpushProducer.getCreateTopicKey());
							jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
							jpush.setBody(SerializableUtil.serialize(jmsg));
							jpushProducer.send(jpush);
							
							Notice notice=new Notice();
							notice.setUserId(order.getCoachId());
							notice.setType((byte) 2);
							notice.setUserType((byte) 1);
							notice.setIsdel((byte)0);
							notice.setOrderId(order.getOrderId());
							notice.setTime(new Date());
							notice.setTitle("评分提醒");
							notice.setDigest(content);
							notice.setContent(content);
				            Map<String, String> extra = new HashMap<>();
				            extra.put("\"orderId\"", "\"" + order.getOrderId() + "\"");
					        notice.setExtra(extra.toString().replace("=", ":"));
							Notice exticeNotice=noticeManager.getNoticeByOrderId(order.getOrderId(),(byte) 1);
							if(exticeNotice!=null){
								notice.setNoticeId(exticeNotice.getNoticeId());
								noticeManager.updateNotice(notice);
							}else{
								noticeManager.addNotice(notice);
							}
						}
						// }
						// 3.后续的其他任务

						// N.保证消息不重复执行
						redisUtil.set(redisKey, 1, RedisExpireConstant.RMQTIME.RETYSECONDS);
					}
					// 2.更新订单
					redisKey = "orderoutclass_order_" + orderId;
					if (!redisUtil.isExist(redisKey)) {

						// 1.查询并更新订单信息
						order.setOrderState(OrderConstant.ORDERSTATE.COMPLETE);
						order.setRend(event.getTime());
						orderService.saveOrder(order);
						// N.保证消息不重复执行
						redisUtil.set(redisKey, 1, RedisExpireConstant.RMQTIME.RETYSECONDS);
					}
					// 3.更新排班
					redisKey = "orderoutclass_class_" + orderId;
					if (!redisUtil.isExist(redisKey)) {
						CoachClassVo search = new CoachClassVo();
						search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
						// 预约排班，人数如果没有全部下课则不更新排班时间
						if (order.getOtype() == OrderConstant.OTYPE.BOOKORDER && order.getCcid() != null) {
							OrderVo os = new OrderVo();
							os.setCcid(order.getCcid());
							os.setOrderState(OrderConstant.ORDERSTATE.INCLASS);
							List<OrderVo> clzList = orderService.queryByObjectAnd(os, new OrderQuery());
							// 自己的排班不算
							if (clzList == null || clzList.isEmpty() || (clzList.size() == 1
									&& order.getOrderId().equals(clzList.get(0).getOrderId()))) {
								log.debug(order + " has empty coach class, so set over." + clzList);
								search.setCcid(order.getCcid());
							} else {
								log.debug(clzList + " has coach class, so NOT set over." + order);
								search = null;
							}
						} else {
							search.setOrderId(orderId);
						}
						List<CoachClassVo> list = null;
						if (search != null) {
							list = coachClassService.queryByObjectAnd(search, new CoachClassQuery());
						}
						if (list != null && !list.isEmpty()) {
							BeanCopy.setListField(list, "rend", event.getTime());
							coachClassService.saveCoachClassList(list);
						}
						// N.保证消息不重复执行
						redisUtil.set(redisKey, 1, RedisExpireConstant.RMQTIME.RETYSECONDS);
					}
					// 4. 教练业绩
					redisKey = "orderoutclass_coachpay_" + orderId;
					if (!redisUtil.isExist(redisKey)) {
						if (order.getPayState() == OrderConstant.PAYSTATE.HASPAY && order.getPayTotal() == 0
								&& order.getCoupon() != null && order.getCouponTotal() > 0) {
							log.debug(order + " pay with coupon and pay coach now.");
							/*
							 * PayVo payVo=new
							 * PayVo(order.getStudentId(),OrderConstant.USETYPE.
							 * STUDENT,order.getCouponTotal(),PayWayType.COUPON,
							 * order.getOrderId(),PurposeType.COURSE.getType(),
							 * order.getCoupon(),0,"remark"); ReqResult
							 * r=payServiceNew.pay(payVo);
							 * if(!"0".equals(r.getResult().get("code"))) {
							 * log.error(order +
							 * " pay with coupon and pay coach now ERROR="
							 * +r.getResult().get("code")); }
							 */
							// 20160720 优惠券全额抵扣的情况，更新资金结算账户。
							moneyManager.handleOrderHasPayedMoneyFlow(order);
						}
						redisUtil.set(redisKey, 1, RedisExpireConstant.RMQTIME.RETYSECONDS);
					}
					// 5. 学员的学时
					redisKey = "orderoutclass_stuClassHour_" + orderId;
					if (!redisUtil.isExist(redisKey)){
						if (StringUtil.isNotNullAndNotEmpty(order.getCourseId())) {
							Student student = studentManager.getStudentInfo(order.getStudentId());
							Long classHour = (order.getPend().getTime() - order.getPstart().getTime()) / (1000 * 60);
							if (order.getOtype() == 3){
								List<CoachClassVo> ccvs = coachClassService.queryByCcid(order.getCcid(), new CoachClassQuery());
								if (ccvs != null && ccvs.size() == 1 && ccvs.get(0).getMaxNum() > 0)
									classHour = classHour / ccvs.get(0).getMaxNum();
							}
							if (student != null && classHour > 0) {
								switch (Integer.valueOf(order.getCourseId())) {
								case 1:
								case 11:
									student.setBasicHour2(student.getBasicHour2() + classHour.intValue());
									studentManager.updateStudent(student);
									break;
								case 2:
								case 12:
									student.setSimTestHour2(student.getSimTestHour2() + classHour.intValue());
									studentManager.updateStudent(student);
									break;
								case 3:
								case 13:
									student.setBasicHour3(student.getBasicHour3() + classHour.intValue());
									studentManager.updateStudent(student);
									break;
								case 4:
								case 14:
									student.setSimTestHour3(student.getSimTestHour3() + classHour.intValue());
									studentManager.updateStudent(student);
									break;
								case 5:
								case 15:
									student.setDriveHour(student.getDriveHour() + classHour.intValue());
									studentManager.updateStudent(student);
									break;
								case 6:
								case 16:
									student.setTestHour2(student.getTestHour2() + classHour.intValue());
									studentManager.updateStudent(student);
									break;
								case 7:
								case 17:
									student.setTestHour3(student.getTestHour3() + classHour.intValue());
									studentManager.updateStudent(student);
									break;
								default:
									break;
								}
							}
						}
						redisUtil.set(redisKey, 1, RedisExpireConstant.RMQTIME.RETYSECONDS);
					}
				}
			}
		} catch (Exception e) {
			log.error(msgs.size() + "OrderOutClassListener Exception:" + e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
