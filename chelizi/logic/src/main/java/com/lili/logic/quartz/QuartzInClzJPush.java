package com.lili.logic.quartz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.lili.authcode.dto.Notice;
import com.lili.authcode.manager.NoticeManager;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.manager.CourseSManager;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.DateUtil;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.order.service.CoachClassService;
import com.lili.order.service.OrderService;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.pay.service.PayService;
import com.lili.pay.service.PayServiceNew;
import com.lili.student.manager.StudentManager;

public class QuartzInClzJPush {
	private Logger log = Logger.getLogger(QuartzInClzJPush.class);
	private int liveSencode = 86400;
	private int orderLiveSencode = 3600;
	//每次查询数量，发布请使用100
	private int pageSize = 100;
	private int pageMax = 1000;
	//jpush支持的一次推送的最大用户数量，发布请使用1000
	private int jpushMax=1000;
	//每班次最大可预约人数，须留余量
	private int prebookMax=8;
	
	@Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;
	@Autowired
	private OrderService orderService;
	@Autowired
	private CourseSManager courseSManager;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
    private CoachManager coachManager;
	@Autowired
    private StudentManager studentMananger;
	@Autowired
	private PayService payService;
	@Autowired
	private CoachClassService coachClassService;
	@Autowired
	private PayServiceNew payServiceNew;
	@Resource(name="studentProducer")
    DefaultMQProducer studentProducer;
	
	@Autowired
	private NoticeManager noticeManager;
	
	public void doPush() {
		Date now = new Date();
		String nowStamp=String.valueOf(now.getTime());
		// 3，4.上课时间到（学生定向预约,教练定向预约）
		for (int i = 1; i < pageMax; i++) {
			JpushMsg jmsgStudent=null; 
		    JpushMsg jmsgCoach=null;
		    Map<String,List<OrderVo>> classMap=new HashMap<String,List<OrderVo>>();
		    int studentTimes=1;
		    int coachTimes=1;
			try {
				OrderQuery orderQuery = new OrderQuery();
				//必须是第一页，因为后面有数据修改
				orderQuery.setPageIndex(1);
				orderQuery.setPageSize(pageSize);
				orderQuery.setorderBy("order by coach_id desc,ccid desc");
				OrderVo search = new OrderVo();
				search.setOtype(OrderConstant.OTYPE.BOOKORDER);
				search.setOrderState(OrderConstant.ORDERSTATE.ACCEPTORDER);
				//为了提前容忍系统上次丢失，设置60s重复执行
				search.setPstart(DateUtil.dateAfterMilliSecond(now,-60000));
				//为了提前容忍系统非正分钟数，设置提前59s上课
				search.setPend(DateUtil.dateAfterMilliSecond(now,59000));
				List<OrderVo> olist = null;
				try {
					olist = orderService.queryByPstart(search, orderQuery);
					if(log.isDebugEnabled()){
						log.debug(i+" time find clzin="+olist.size());
					}
				} catch (Exception e) {
					log.error(TimeUtil.getDateFormat(now)+"jpush to 3,4chadan:"+e.getMessage(),e);
				}
				if (olist != null) {
					for (int j=0;j<olist.size();j++) {
							OrderVo order=olist.get(j);
							//1.发送学生提醒
							String redisKey = JpushConstant.PREFIX.PUSHOBJECT+order.getStudentId()+"_"+OrderConstant.USETYPE.STUDENT+"_"+nowStamp+"_"+JpushConstant.OPERATE.STUCLASSIN;
							String qianRedisKey = JpushConstant.PREFIX.PUSHOBJECT+order.getStudentId()+"_"+OrderConstant.USETYPE.STUDENT+"_"+order.getOrderId()+"_"+JpushConstant.OPERATE.STUCLASSIN;
							if (!redisUtil.isExist(redisKey)&&!redisUtil.isExist(qianRedisKey)) {
								if(jmsgStudent==null){
									String content = "您的课程已经开始";
									jmsgStudent=new JpushMsg();
									jmsgStudent.setAlter(content);
									jmsgStudent.addUser(order.getStudentId());
									jmsgStudent.setOperate(JpushConstant.OPERATE.STUCLASSIN);
									jmsgStudent.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE, JpushConstant.GENTYPE.AUTOGEN);
									jmsgStudent.getExtras().put(JpushConstant.EXTRAFIELD.TIMESTAMP, nowStamp);
								} else {
									jmsgStudent.addUser(order.getStudentId());
								}
								//消息通知入库
								Notice notice=new Notice();
								notice.setUserId(order.getStudentId());
								notice.setType((byte) 2);
								notice.setUserType((byte) 2);
								notice.setOrderId(order.getOrderId());
								notice.setTime(new Date());
								notice.setTitle("上课提醒");
								notice.setDigest("您的课程已经开始");
								Notice exticeNotice=noticeManager.getNoticeByOrderId(order.getOrderId(),(byte) 2);
								if(exticeNotice!=null){
									notice.setNoticeId(exticeNotice.getNoticeId());
									noticeManager.updateNotice(notice);
								}else{
									noticeManager.addNotice(notice);
								}
								
								if(jmsgStudent.getUserIds().size()>=jpushMax){
									String msgKey=OrderConstant.USETYPE.STUDENT+"_"+nowStamp+"_"+jmsgStudent.getOperate()+"_"+(studentTimes++);
									Message jpush = new Message();
									jpush.setKeys(msgKey);
									jpush.setTopic(jpushProducer.getCreateTopicKey());
									jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
									jpush.setBody(SerializableUtil.serialize(jmsgStudent));
									jpushProducer.send(jpush);
									if(log.isDebugEnabled()){
										log.debug(msgKey +" has send jpush to "+jmsgStudent);
									}
									jmsgStudent=null;
								}
								redisUtil.setAll(redisKey, order.getOrderId(), liveSencode);
								redisUtil.setMe(JpushConstant.PREFIX.PUSHOBJECT+order.getOrderId(), order,orderLiveSencode);
								redisUtil.setAll(qianRedisKey, order.getOrderId(), liveSencode);
							}
							//2.发送教练信息
							String redisCoachKey = JpushConstant.PREFIX.PUSHOBJECT+order.getCoachId()+"_"+OrderConstant.USETYPE.COACH+"_"+nowStamp+"_"+JpushConstant.OPERATE.COACHCLASSIN;
							String qianRedisCoachKey = JpushConstant.PREFIX.PUSHOBJECT+order.getCoachId()+"_"+OrderConstant.USETYPE.COACH+"_"+order.getOrderId()+"_"+JpushConstant.OPERATE.COACHCLASSIN;
							if (!redisUtil.isExist(redisCoachKey)&&!redisUtil.isExist(qianRedisCoachKey)) {
								String ccidKey=order.getCoachId()+"_"+order.getCcid();
								boolean canPush=false;
								if(jmsgCoach==null){
									String content = "您的课程已经开始";
									jmsgCoach = new JpushMsg();
									jmsgCoach.setAlter(content);
									jmsgCoach.addUser(order.getCoachId());
									jmsgCoach.setOperate(JpushConstant.OPERATE.COACHCLASSIN);
									jmsgCoach.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE, JpushConstant.GENTYPE.AUTOGEN);
									jmsgCoach.getExtras().put(JpushConstant.EXTRAFIELD.TIMESTAMP, nowStamp);
									List<OrderVo> list=new ArrayList<OrderVo>();
									list.add(order);
									classMap.put(ccidKey, list);
									
								} else {
									jmsgCoach.addUser(order.getCoachId());
									if(classMap.containsKey(ccidKey)){
										classMap.get(ccidKey).add(order);
									} else {
										List<OrderVo> list=new ArrayList<OrderVo>();
										list.add(order);
										classMap.put(ccidKey, list);
									}
								}
								//消息通知入库
								Notice notice=new Notice();
								notice.setUserId(order.getCoachId());
								notice.setType((byte) 2);
								notice.setUserType((byte) 1);
								notice.setOrderId(order.getOrderId());
								notice.setTime(new Date());
								notice.setTitle("上课提醒");
								notice.setDigest("您的课程已经开始");
								notice.setContent("您的课程已经开始");
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
								
								//防止同一排班放到不同推送中
								if(jmsgCoach!=null && jmsgCoach.getUserIds().size()>=(jpushMax-prebookMax)){
									if(j<(olist.size()-1) && !(ccidKey.equals(olist.get(j+1).getCoachId()+"_"+olist.get(j+1).getCcid()))) {
										canPush=true;
									}
								}
								if(canPush){
									String msgKey=OrderConstant.USETYPE.COACH+"_"+nowStamp+"_"+jmsgCoach.getOperate()+"_"+(coachTimes++);
									Message jpush = new Message();
									jpush.setKeys(msgKey);
									jpush.setTopic(jpushProducer.getCreateTopicKey());
									jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
									jpush.setBody(SerializableUtil.serialize(jmsgCoach));
									jpushProducer.send(jpush);
									if(log.isDebugEnabled()){
										log.debug(msgKey +" has send jpush to "+jmsgCoach);
									}
									jmsgCoach=null;
								}
								redisUtil.setAll(redisCoachKey, ccidKey, liveSencode);
								redisUtil.setMe(JpushConstant.PREFIX.PUSHOBJECT+ccidKey, classMap.get(ccidKey),orderLiveSencode);
								redisUtil.setAll(qianRedisCoachKey, ccidKey, liveSencode);
						}
						//自动开始上课
						try {
								redisKey = "QuartzJPush_autoinclz_" + order.getOrderId();
								if (!redisUtil.isExist(redisKey)) {
									order.setOrderState(OrderConstant.ORDERSTATE.INCLASS);
									orderService.saveOrder(order);
									redisUtil.setAll(redisKey, 1, liveSencode);
								}	
						} catch (Exception e) {
								log.error(TimeUtil.getDateFormat(now)
										+ " jpush to 34autoin " + order.getOrderId(), e);
						}
					}
				}
				if (olist==null || olist.size() < pageSize) {
					//推送不满jpushMax条数的消息
					if(jmsgStudent!=null){
						String msgKey=OrderConstant.USETYPE.STUDENT+"_"+nowStamp+"_"+jmsgStudent.getOperate()+"_"+(studentTimes++);
						Message jpush = new Message();
						jpush.setKeys(msgKey);
						jpush.setTopic(jpushProducer.getCreateTopicKey());
						jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
						jpush.setBody(SerializableUtil.serialize(jmsgStudent));
						jpushProducer.send(jpush);
						if(log.isDebugEnabled()){
							log.debug(msgKey +" not enough has send jpush to "+jmsgStudent);
						}
						jmsgStudent=null;
					}
					if(jmsgCoach!=null){
						String msgKey=OrderConstant.USETYPE.COACH+"_"+nowStamp+"_"+jmsgCoach.getOperate()+"_"+(coachTimes++);
						Message jpush = new Message();
						jpush.setKeys(msgKey);
						jpush.setTopic(jpushProducer.getCreateTopicKey());
						jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
						jpush.setBody(SerializableUtil.serialize(jmsgCoach));
						jpushProducer.send(jpush);
						if(log.isDebugEnabled()){
							log.debug(msgKey +" not enough has send jpush to "+jmsgCoach);
						}
						jmsgCoach=null;
					}
					if(log.isDebugEnabled()){
						log.debug(i+" time find clzin="+olist+", buzou so beak;");
					}
					break;
			   }
			} catch (Exception e) {
				log.error(TimeUtil.getDateFormat(now)
						+ " jpush to clzin_"+nowStamp+"_"+studentTimes+",coachTimes="+coachTimes+"jmsgStudent="+jmsgStudent+",jmsgCoach="+jmsgCoach, e);
			}
		}
		//上课时间已到，下课时间未到的预约单自动上课
		for (int i = 1; i < pageMax; i++) {
			JpushMsg jmsgStudent=null; 
		    JpushMsg jmsgCoach=null;
		    int studentTimes=1;
		    int coachTimes=1;
			try {
				OrderQuery orderQuery = new OrderQuery();
				//必须是第一页，因为后面有数据修改
				orderQuery.setPageIndex(1);
				orderQuery.setPageSize(pageSize);
				//上课时间到2分钟后还没有触发的课程静默上课
				orderQuery.setGroupBy(" and UNIX_TIMESTAMP(pstart) < "+((System.currentTimeMillis()/1000) -120)+"  and UNIX_TIMESTAMP(pend) > "+(System.currentTimeMillis()/1000) );
				OrderVo search = new OrderVo();
				search.setOtype(OrderConstant.OTYPE.BOOKORDER);
				search.setOrderState(OrderConstant.ORDERSTATE.ACCEPTORDER);
				List<OrderVo> olist = null;
				try {
					olist = orderService.queryByObjectAnd(search, orderQuery);
					if(log.isDebugEnabled()){
						log.debug(i+" time find later clzin="+olist.size());
					}
				} catch (Exception e) {
					log.error(TimeUtil.getDateFormat(now)+"jpush to later clzin:"+e.getMessage(),e);
				}
				if (olist != null) {
					for (int j=0;j<olist.size();j++) {
							OrderVo order=olist.get(j);
						//自动开始上课
						try {
								String redisKey = "QuartzJPush_autoinclz_" + order.getOrderId();
								if (!redisUtil.isExist(redisKey)) {
									order.setOrderState(OrderConstant.ORDERSTATE.INCLASS);
									orderService.saveOrder(order);
									redisUtil.setAll(redisKey, 1, liveSencode);
								}	
						} catch (Exception e) {
								log.error(TimeUtil.getDateFormat(now)
										+ " jpush to later clzin " + order.getOrderId(), e);
						}
					}
				}
				if (olist==null || olist.size() < pageSize) {
					if(log.isDebugEnabled()){
						log.debug(i+" time find later clzin="+olist+", buzou so beak;");
					}
					break;
			   }
			} catch (Exception e) {
				log.error(TimeUtil.getDateFormat(now)
						+ " jpush to later clzin_"+nowStamp+"_"+studentTimes+",coachTimes="+coachTimes+"jmsgStudent="+jmsgStudent+",jmsgCoach="+jmsgCoach, e);
			}
		}
	}
}
