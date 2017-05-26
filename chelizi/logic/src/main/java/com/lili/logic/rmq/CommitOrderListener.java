package com.lili.logic.rmq;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.lili.authcode.service.AuthcodeService;
import com.lili.coach.manager.CoachManager;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.OrderConstant;
import com.lili.common.constant.RedisExpireConstant;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ReqConstants;
import com.lili.file.manager.FileManager;
import com.lili.order.service.CoachScoreService;
import com.lili.order.service.CoachStatisticService;
import com.lili.order.vo.CoachScoreQuery;
import com.lili.order.vo.CoachScoreVo;
import com.lili.order.vo.CoachStatisticVo;
import com.lili.order.vo.OrderVo;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;

public class CommitOrderListener implements MessageListenerConcurrently {
	private Logger log = Logger.getLogger(CommitOrderListener.class);

	//@Value("$redirect.mobile")
	//private String redirectMobile;
	@Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;
	@Autowired
	private CoachManager coachManager;
	@Autowired
	private StudentManager studentMananger;
	@Autowired
	private CoachStatisticService coachStatisticService;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private FileManager fileManager;
	@Autowired
	AuthcodeService authcodeService;
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
				//1.db修改
				String redisKey = "commitorder_db_" + order.getOrderId();
				if (!redisUtil.isExist(redisKey)) {

					// 2.统计教练端流水,因为是同一个教练，此意不会存在并行的问题
					Date gtime = order.getGtime();
					String date = TimeUtil.getDateFormat(gtime, "yyyyMMdd");
					long coachId = order.getCoachId();
					CoachStatisticVo vo = coachStatisticService
							.getCoachStatistc(coachId, date);
					vo.setOrderAccept(vo.getOrderAccept() + 1);
					vo.setOrderMoney(vo.getOrderMoney() +  order.getPriceTotal());//20161104现约的时候包含接送费也计入收入price-->priceTotal
					// 回存
					coachStatisticService.saveCoachStatistic(vo);
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
				//2.短信发送
				redisKey = "commitorder_txtmsg_" + order.getOrderId();
				if (!redisUtil.isExist(redisKey)) {
				    	Map<String,String> shortMsgs = new HashMap<String, String>();
				    	shortMsgs.put("money", String.valueOf(order.getPriceTotal()/100)); //由分转为元
				    	shortMsgs.put("name", order.getStuName());
				    	shortMsgs.put("mobile", order.getStuMobile());
				    	Date d = order.getPstart();
				    	SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
				    	String t = f.format(d);
				    	shortMsgs.put("time", t);
				    	shortMsgs.put("course", order.getCourseName());
				    	//1. 正式环境发送到教练手机
				    	//if(redirectMobile==null){
				    		authcodeService.sendMsg(1, order.getCoachMobile(), shortMsgs);
				    	//2.测试环境短信发到指定教练
				    	//} else if(redirectMobile!=null && redirectMobile.length()==11){
				    	//	authcodeService.sendMsg(1, redirectMobile, shortMsgs);
				    	//3.其他情况(即配置了不正确的手机号码)不发送短信
				    	//} 
				    	// N.保证消息不重复执行
						redisUtil.set(redisKey, 1,
								RedisExpireConstant.RMQTIME.RETYSECONDS);
			    }
				//3.jpush推送
				redisKey = "commitorder_push_" + order.getOrderId();
				if (!redisUtil.isExist(redisKey)) {
					// 1. 教练推送提交订单信息
					JpushMsg jmsg = new JpushMsg();
					Student info = studentMananger.getStudentInfo(order
							.getStudentId());
					
					if (order.getOtype() == OrderConstant.OTYPE.BOOKORDER) {
						String name=info.getName();
						String time=TimeUtil.getDateFormat(order.getPstart());
						String cname=fileManager.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname();
						String content = name + "学员预约了您在"+ time+ "的" + cname+ "课程";
						jmsg.setAlter(content);
						jmsg.setOperate(JpushConstant.OPERATE.COACHBOOKORDER);
						
						//订单消息中心通知
						if (null != order.getAllowance() && order.getAllowance().intValue() > 0){
							Notice notice = new Notice();
							Map<String, String> extra = new HashMap<>();
							notice.setTitle("恭喜您，订单确认成功");
							notice.setDigest(info.getName() + "的约课订单已确认，点击可查看。");
							notice.setContent(info.getName() + "的约课订单已确认，点击可查看。");
							notice.setType((byte)2);
							notice.setTime(new Date());
							notice.setUserType((byte)ReqConstants.USER_TYPE_COACH);
							notice.setUserId(order.getCoachId());
							notice.setOrderId(order.getOrderId());
							extra.put("\"orderId\"", "\"" + order.getOrderId() + "\"");
							extra.put("\"type\"", "\"3\"");
							notice.setExtra(extra.toString().replace("=", ":"));
							noticeManager.addNotice(notice);
						}
					} else {
						String name=info.getName();
						String time=TimeUtil.getDateFormat(order.getPstart());
						String cname=fileManager.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname();
						String content = name + "学员现约了您在"+ time+ "的" + cname+ "课程";
						jmsg.setAlter(content);
						jmsg.setOperate(JpushConstant.OPERATE.COACHHASORDER);
						jmsg.setSound(JpushConstant.SOUNDPREFIX+jmsg.getOperate()+JpushConstant.SOUNDPOST);
						
						//订单消息中心通知
						Notice notice = new Notice();
						Map<String, String> extra = new HashMap<>();
						notice.setTitle("您有新的订单");
						notice.setDigest(info.getName() + "邀请您为他上课，快去联系学员吧。");
						notice.setContent(info.getName() + "邀请您为他上课，快去联系学员吧。");
						notice.setType((byte)2);
						notice.setTime(new Date());
						notice.setUserType((byte)ReqConstants.USER_TYPE_COACH);
						notice.setUserId(order.getCoachId());
						notice.setOrderId(order.getOrderId());
						extra.put("\"orderId\"", "\"" + order.getOrderId() + "\"");
						extra.put("\"type\"", "\"1\"");
						notice.setExtra(extra.toString().replace("=", ":"));
						noticeManager.addNotice(notice);
					}
					jmsg.setUserId(order.getCoachId());
					jmsg.setOrderId(order.getOrderId());
					Message jpush = new Message();
					jpush.setKeys(order.getOrderId());
					jpush.setTopic(jpushProducer.getCreateTopicKey());
					jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
					jpush.setBody(SerializableUtil.serialize(jmsg));
					jpushProducer.send(jpush);
					// N.保证消息不重复执行
					redisUtil.set(redisKey, 1,
							RedisExpireConstant.RMQTIME.RETYSECONDS);
				}
				
			}
		} catch (Exception e) {
			log.error(
					order + " CommitOrderListener Exception:"
							+ e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
