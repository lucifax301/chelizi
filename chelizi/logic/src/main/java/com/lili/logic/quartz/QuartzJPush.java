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
import com.lili.order.vo.CoachClassQuery;
import com.lili.order.vo.CoachClassVo;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.pay.service.PayService;
import com.lili.pay.service.PayServiceNew;
import com.lili.student.manager.StudentManager;

public class QuartzJPush {
	private Logger log = Logger.getLogger(QuartzJPush.class);
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
		// 1.上课1小时15分钟前提醒（定向预约,学生，+教练)
		Date now = new Date();
		String nowStamp=String.valueOf(now.getTime());
		Date start=DateUtil.dateAfterMinute(now, 75);
		Date end=DateUtil.dateAfterMilliSecond(start,60100);
		for (int i = 1; i < pageMax; i++) {
			JpushMsg jmsgStudent=null; 
		    JpushMsg jmsgCoach=null;
		    Map<String,List<OrderVo>> classMap=new HashMap<String,List<OrderVo>>();
		    int studentTimes=1;
		    int coachTimes=1;
			try {
				OrderQuery orderQuery = new OrderQuery();
				orderQuery.setPageIndex(i);
				orderQuery.setPageSize(pageSize);
				//排序是为了防止统一排班放到不同的消息中
				orderQuery.setorderBy("order by coach_id desc,ccid desc");
				OrderVo search = new OrderVo();
				search.setOtype(OrderConstant.OTYPE.BOOKORDER);
				search.setOrderState(OrderConstant.ORDERSTATE.ACCEPTORDER);
				search.setPstart(DateUtil.dateAfterMinute(now, 74));
				// 多加0.1秒防止遗漏
				search.setPend(DateUtil.dateAfterMilliSecond(search.getPstart(),60100));
				List<OrderVo> olist = null;
				try {
					olist = orderService.queryByPstart(search, orderQuery);
					if(log.isDebugEnabled()){
						log.debug(i+" time find 75fenzhong="+olist.size());
					}
				} catch (Exception e) {
					log.error(TimeUtil.getDateFormat(now)+"jpush to 2chadan:"+e.getMessage(),e);
				}
				if (olist != null) {
					for (int j=0;j<olist.size();j++) {
							OrderVo order=olist.get(j);
							//1.发送学生提醒
							String redisKey = JpushConstant.PREFIX.PUSHOBJECT+order.getStudentId()+"_"+OrderConstant.USETYPE.STUDENT+"_"+nowStamp+"_"+JpushConstant.OPERATE.STUCLASSNEAR;
							String qianRedisKey=JpushConstant.PREFIX.PUSHOBJECT+order.getStudentId()+"_"+OrderConstant.USETYPE.STUDENT+"_"+order.getOrderId()+"_"+JpushConstant.OPERATE.STUCLASSNEAR;
							if (!redisUtil.isExist(redisKey) && !redisUtil.isExist(qianRedisKey)) {
								if(jmsgStudent==null){
									String content = "您预约的课程将于1小时15分钟后" + TimeUtil.getDateFormat(search.getPend())
											+ "开始，祝您上课愉快";
									jmsgStudent=new JpushMsg();
									jmsgStudent.setAlter(content);
									jmsgStudent.addUser(order.getStudentId());
									jmsgStudent.setOperate(JpushConstant.OPERATE.STUCLASSNEAR);
									jmsgStudent.setOrderId(order.getOrderId());
									jmsgStudent.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE, JpushConstant.GENTYPE.AUTOGEN);
									jmsgStudent.getExtras().put(JpushConstant.EXTRAFIELD.PREPARE, "73");
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
								notice.setDigest("您预约的课程将于1小时15分钟后" + TimeUtil.getDateFormat(search.getPend())
										+ "开始，祝您上课愉快");
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
							String redisCoachKey = JpushConstant.PREFIX.PUSHOBJECT+order.getCoachId()+"_"+OrderConstant.USETYPE.COACH+"_"+nowStamp+"_"+JpushConstant.OPERATE.COACHPREPARE;
							String qianRedisCoachKey = JpushConstant.PREFIX.PUSHOBJECT+order.getCoachId()+"_"+OrderConstant.USETYPE.COACH+"_"+order.getOrderId()+"_"+JpushConstant.OPERATE.COACHPREPARE;
							if (!redisUtil.isExist(redisCoachKey)&& !redisUtil.isExist(qianRedisCoachKey)) {
								String ccidKey=order.getCoachId()+"_"+order.getCcid();
								boolean canPush=false;
								if(jmsgCoach==null){
									String content = "您的课程将于1小时15分钟后" + TimeUtil.getDateFormat(order.getPstart())
											+ "开始，请做好教学准备";
									jmsgCoach = new JpushMsg();
									jmsgCoach.setAlter(content);
									jmsgCoach.addUser(order.getCoachId());
									jmsgCoach.setOperate(JpushConstant.OPERATE.COACHPREPARE);
									jmsgCoach.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE, JpushConstant.GENTYPE.AUTOGEN);
									jmsgCoach.getExtras().put(JpushConstant.EXTRAFIELD.PREPARE, "73");
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
								
								Notice notice=new Notice();
								notice.setUserId(order.getCoachId());
								notice.setType((byte) 2);
								notice.setUserType((byte) 1);
								notice.setOrderId(order.getOrderId());
								notice.setTime(new Date());
								notice.setTitle("上课提醒");
								notice.setDigest("您的课程将于1小时15分钟后" + TimeUtil.getDateFormat(order.getPstart())
										+ "开始，请做好教学准备");
								notice.setContent("您的课程将于1小时15分钟后" + TimeUtil.getDateFormat(order.getPstart())
										+ "开始，请做好教学准备");
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
									String msgKey=OrderConstant.USETYPE.COACH+"_"+nowStamp+"_"+JpushConstant.OPERATE.COACHPREPARE+(coachTimes++);
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
					}
				}
				if (olist==null || olist.size() < pageSize) {
					//推送不满jpushMax条数的消息
					if(jmsgStudent!=null){
						String msgKey=OrderConstant.USETYPE.STUDENT+"_"+nowStamp+"_"+JpushConstant.OPERATE.STUCLASSNEAR+"_"+(studentTimes++);
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
						log.debug(i+" time find 75fenzhong="+olist+", buzou so beak;");
					}
					break;
			   }
			} catch (Exception e) {
				log.error(TimeUtil.getDateFormat(now)
						+ " jpush to "+OrderConstant.USETYPE.STUDENT+"_"+nowStamp+"_"+JpushConstant.OPERATE.STUCLASSNEAR+"_"+studentTimes+",coachTimes="+coachTimes+"jmsgStudent="+jmsgStudent+",jmsgCoach="+jmsgCoach, e);
			}
		}
		// 1-1.75分钟没有预约
		for (int i = 1; i < pageMax; i++) {
			List<CoachClassVo> cclist = null;
			JpushMsg jmsgCoach=null;
			int coachTimes=1;
			try {
				try {
					CoachClassVo search = new CoachClassVo();
					search.setBookNum(0);
					search.setCtype(OrderConstant.OTYPE.BOOKORDER);
					search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
					CoachClassQuery ccq = new CoachClassQuery();
					ccq.setGroupBy("and cstart between '"
							+ TimeUtil.getDateFormat(start) + "' and '"
							+ TimeUtil.getDateFormat(end)+"'");
					ccq.setPageSize(pageSize);
					cclist = coachClassService.queryByObjectAnd(search, ccq);
					if (log.isDebugEnabled()) {
						log.debug(i + " time find 75fenzhong=" + cclist.size());
					}
				} catch (Exception e) {
					log.error(
							TimeUtil.getDateFormat(now) + "jpush to 2chadan:"
									+ e.getMessage(), e);
				}
				if (cclist != null) {
					for (int j=0;j<cclist.size();j++ ) {
						CoachClassVo cc =cclist.get(j);
						String redisCoachKey = JpushConstant.PREFIX.PUSHOBJECT+cc.getCoachId()+"_"+OrderConstant.USETYPE.COACH+"_"+nowStamp+"_"+JpushConstant.OPERATE.COACHNOBOOKED;
						String qianRedisCoachKey = JpushConstant.PREFIX.PUSHOBJECT+cc.getCoachId()+"_"+OrderConstant.USETYPE.COACH+"_"+cc.getCcid()+"_"+JpushConstant.OPERATE.COACHNOBOOKED;
						if (!redisUtil.isExist(redisCoachKey)&&!redisUtil.isExist(qianRedisCoachKey)) {						
							if(jmsgCoach==null){
								String content = "您的课程将于1小时15分钟后"+ TimeUtil.getDateFormat(cc.getCstart())
										+ "上课，目前还没有学员预约，请尽快通知学员约课！";
								jmsgCoach = new JpushMsg();
								jmsgCoach.setAlter(content);
								jmsgCoach.addUser(cc.getCoachId());
								jmsgCoach.setOperate(JpushConstant.OPERATE.COACHNOBOOKED);
								jmsgCoach.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE,JpushConstant.GENTYPE.AUTOGEN);
								jmsgCoach.getExtras().put(JpushConstant.EXTRAFIELD.PREPARE, "75");
								jmsgCoach.getExtras().put(JpushConstant.EXTRAFIELD.TIMESTAMP, nowStamp);
							} else {
								jmsgCoach.addUser(cc.getCoachId());
							}
							if(jmsgCoach!=null && jmsgCoach.getUserIds().size()>=jpushMax){
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
							redisUtil.setAll(redisCoachKey, cc.getCcid(), liveSencode);
							redisUtil.setMe(JpushConstant.PREFIX.PUSHOBJECT+cc.getCcid(), cc,orderLiveSencode);
							redisUtil.setAll(qianRedisCoachKey, cc.getCcid(), liveSencode);
					}
				}
				if (cclist==null || cclist.size() < pageSize) {
					if(jmsgCoach!=null){
						String msgKey=OrderConstant.USETYPE.COACH+"_"+nowStamp+"_"+jmsgCoach.getOperate()+(coachTimes++);
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
						log.debug(i+" time find 75fenzhong no student="+cclist+", buzou so beak;");
					}
					break;
				}
			}
		 } catch(Exception e){
			 log.error(TimeUtil.getDateFormat(now)
						+ " jpush to tixing75 "+OrderConstant.USETYPE.COACH+"_"+nowStamp+"_"+JpushConstant.OPERATE.COACHNOBOOKED+"_coachTimes="+coachTimes+",jmsgStudent="+jmsgCoach, e);	
		 }
		}
		// 2.上课15分钟前提醒（定向预约,教练）
		for (int i = 1; i < pageMax; i++) { 
		    JpushMsg jmsgCoach=null;
		    Map<String,List<OrderVo>> classMap=new HashMap<String,List<OrderVo>>();
		    int coachTimes=1;
		    try {
				OrderQuery orderQuery = new OrderQuery();
				orderQuery.setPageIndex(i);
				orderQuery.setPageSize(pageSize);
				orderQuery.setorderBy("order by coach_id desc,ccid desc");
				OrderVo search = new OrderVo();
				search.setOtype(OrderConstant.OTYPE.BOOKORDER);
				search.setOrderState(OrderConstant.ORDERSTATE.ACCEPTORDER);
				search.setPstart(DateUtil.dateAfterMinute(now, 15));
				// 多加0.1秒防止遗漏
				search.setPend(DateUtil.dateAfterMilliSecond(search.getPstart(),
						60100));
				List<OrderVo> olist = null;
				try {
					olist = orderService.queryByPstart(search, orderQuery);
					if(log.isDebugEnabled()){
						log.debug(i+" time find 15fenzhong="+olist.size());
					}
				} catch (Exception e) {
					log.error(TimeUtil.getDateFormat(now)+"jpush to 1chadan:"+e.getMessage(),e);
				}
				if (olist != null) {	
				   for(int j=0;j<olist.size();j++){
							OrderVo order =olist.get(j);
							//2.发送教练信息
							String redisCoachKey = JpushConstant.PREFIX.PUSHOBJECT+order.getCoachId()+"_"+OrderConstant.USETYPE.COACH+"_"+nowStamp+"_"+JpushConstant.OPERATE.COACHCLASSNEAR;
							String qianRedisCoachKey = JpushConstant.PREFIX.PUSHOBJECT+order.getCoachId()+"_"+OrderConstant.USETYPE.COACH+"_"+order.getOrderId()+"_"+JpushConstant.OPERATE.COACHCLASSNEAR;
							if (!redisUtil.isExist(redisCoachKey)&&!redisUtil.isExist(qianRedisCoachKey)) {
								String ccidKey=order.getCoachId()+"_"+order.getCcid();
								boolean canPush=false;
								if(jmsgCoach==null){
									String content = "您的课程将于15分钟后" + TimeUtil.getDateFormat(order.getPstart())
											+ "开始，请做好教学准备";
									jmsgCoach = new JpushMsg();
									jmsgCoach.setAlter(content);
									jmsgCoach.addUser(order.getCoachId());
									jmsgCoach.setOperate(JpushConstant.OPERATE.COACHCLASSNEAR);
									jmsgCoach.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE, JpushConstant.GENTYPE.AUTOGEN);
									jmsgCoach.getExtras().put(JpushConstant.EXTRAFIELD.PREPARE, "15");
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
								
								Notice notice=new Notice();
								notice.setUserId(order.getCoachId());
								notice.setType((byte) 2);
								notice.setUserType((byte) 1);
								notice.setOrderId(order.getOrderId());
								notice.setTime(new Date());
								notice.setTitle("上课提醒");
								notice.setDigest("您的课程将于15分钟后" + TimeUtil.getDateFormat(order.getPstart())
										+ "开始，请做好教学准备");
								
					   	        notice.setContent("您的课程将于15分钟后" + TimeUtil.getDateFormat(order.getPstart())
										+ "开始，请做好教学准备");
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
									String msgKey=OrderConstant.USETYPE.COACH+"_"+nowStamp+"_"+JpushConstant.OPERATE.COACHCLASSNEAR+(coachTimes++);
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
					}
				}
				if (olist==null || olist.size() < pageSize) {
					//推送不满jpushMax条数的消息
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
						log.debug(i+" time find 75fenzhong="+olist+", buzou so beak;");
					}
					break;
			   }
			} catch (Exception e) {
				log.error(TimeUtil.getDateFormat(now)
						+ " jpush to "+JpushConstant.PREFIX.PUSHOBJECT+"_"+OrderConstant.USETYPE.COACH+"_"+nowStamp+"_"+JpushConstant.OPERATE.COACHCLASSNEAR+",jmsgCoach="+jmsgCoach, e);
			}
		}
				
		// 2-1.15分钟没有预约
		start=DateUtil.dateAfterMinute(now, 15);
		end = DateUtil.dateAfterMilliSecond(start, 60100);
		for (int i = 1; i < pageMax; i++) {
			List<CoachClassVo> cclist = null;
			JpushMsg jmsgCoach=null;
			int coachTimes=1;
			try {
				try {
					CoachClassVo search = new CoachClassVo();
					search.setBookNum(0);
					search.setCtype(OrderConstant.OTYPE.BOOKORDER);
					search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
					CoachClassQuery ccq = new CoachClassQuery();
					ccq.setGroupBy("and cstart between '"
							+ TimeUtil.getDateFormat(start) + "' and '"
							+ TimeUtil.getDateFormat(end)+"'");
					ccq.setPageSize(pageSize);
					cclist = coachClassService.queryByObjectAnd(search, ccq);
					if (log.isDebugEnabled()) {
						log.debug(i + " time find 75fenzhong=" + cclist.size());
					}
				} catch (Exception e) {
					log.error(
							TimeUtil.getDateFormat(now) + "jpush to 2chadan:"
									+ e.getMessage(), e);
				}
				if (cclist != null) {
					for (int j=0;j<cclist.size();j++ ) {
						CoachClassVo cc =cclist.get(j);
						String redisCoachKey = JpushConstant.PREFIX.PUSHOBJECT+cc.getCoachId()+"_"+OrderConstant.USETYPE.COACH+"_"+nowStamp+"_"+JpushConstant.OPERATE.COACHNOBOOKED15;
						String qianRedisCoachKey = JpushConstant.PREFIX.PUSHOBJECT+cc.getCoachId()+"_"+OrderConstant.USETYPE.COACH+"_"+cc.getCcid()+"_"+JpushConstant.OPERATE.COACHNOBOOKED15;
						if (!redisUtil.isExist(redisCoachKey)&&!redisUtil.isExist(qianRedisCoachKey)) {						
							if(jmsgCoach==null){
								String content = "您的课程将于15分钟后"+ TimeUtil.getDateFormat(cc.getCstart())
										+ "上课，目前还没有学员预约，请尽快通知学员约课！";
								jmsgCoach = new JpushMsg();
								jmsgCoach.setAlter(content);
								jmsgCoach.addUser(cc.getCoachId());
								jmsgCoach.setOperate(JpushConstant.OPERATE.COACHNOBOOKED15);
								jmsgCoach.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE,JpushConstant.GENTYPE.AUTOGEN);
								jmsgCoach.getExtras().put(JpushConstant.EXTRAFIELD.PREPARE, "15");
								jmsgCoach.getExtras().put(JpushConstant.EXTRAFIELD.TIMESTAMP, nowStamp);
							} else {
								jmsgCoach.addUser(cc.getCoachId());
							}
							if(jmsgCoach!=null && jmsgCoach.getUserIds().size()>=jpushMax){
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
							redisUtil.setAll(redisCoachKey, cc.getCcid(), liveSencode);
							redisUtil.setMe(JpushConstant.PREFIX.PUSHOBJECT+cc.getCcid(), cc,orderLiveSencode);
							redisUtil.setAll(qianRedisCoachKey, cc.getCcid(), liveSencode);
					}
				}
				if (cclist==null || cclist.size() < pageSize) {
					if(jmsgCoach!=null){
						String msgKey=OrderConstant.USETYPE.COACH+"_"+nowStamp+"_"+jmsgCoach.getOperate()+(coachTimes++);
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
						log.debug(i+" time find 15fenzhong no student="+cclist+", buzou so beak;");
					}
					break;
				}
			  }
			} catch(Exception e){
				 log.error(TimeUtil.getDateFormat(now)
						+ " jpush to tixing15 "+OrderConstant.USETYPE.COACH+"_"+nowStamp+"_"+JpushConstant.OPERATE.COACHNOBOOKED15+"_coachTimes="+coachTimes+",jmsgCoach="+jmsgCoach, e);	
			}
		}
		//2_1.上课5分钟前提醒（定向预约,学生）
		for (int i = 1; i < pageMax; i++) {
			JpushMsg jmsgStudent=null; 
		    int studentTimes=1;
			try {
				OrderQuery orderQuery = new OrderQuery();
				orderQuery.setPageIndex(i);
				orderQuery.setPageSize(pageSize);
				OrderVo search = new OrderVo();
				search.setOtype(OrderConstant.OTYPE.BOOKORDER);
				search.setOrderState(OrderConstant.ORDERSTATE.ACCEPTORDER);
				search.setPstart(DateUtil.dateAfterMinute(now, 5));
				// 多加0.1秒防止遗漏
				search.setPend(DateUtil.dateAfterMilliSecond(search.getPstart(),60100));
				List<OrderVo> olist = null;
				try {
					olist = orderService.queryByPstart(search, orderQuery);
					if(log.isDebugEnabled()){
						log.debug(i+" time find 5fenzhong="+olist.size());
					}
				} catch (Exception e) {
					log.error(TimeUtil.getDateFormat(now)+"jpush to 2chadan:"+e.getMessage(),e);
				}
				if (olist != null) {
					for (int j=0;j<olist.size();j++) {
							OrderVo order=olist.get(j);
							//1.发送学生提醒
							String redisKey = JpushConstant.PREFIX.PUSHOBJECT+order.getStudentId()+"_"+OrderConstant.USETYPE.STUDENT+"_"+nowStamp+"_"+JpushConstant.OPERATE.STUCLASSNEARFIVE;
							String qianRedisKey = JpushConstant.PREFIX.PUSHOBJECT+order.getStudentId()+"_"+OrderConstant.USETYPE.STUDENT+"_"+order.getOrderId()+"_"+JpushConstant.OPERATE.STUCLASSNEARFIVE;
							if (!redisUtil.isExist(redisKey)&&!redisUtil.isExist(qianRedisKey)) {
								if(jmsgStudent==null){
									String content = "您预约的课程将于5分钟后" + TimeUtil.getDateFormat(search.getPend())
											+ "开始，祝您上课愉快";
									jmsgStudent=new JpushMsg();
									jmsgStudent.setAlter(content);
									jmsgStudent.addUser(order.getStudentId());
									jmsgStudent.setOperate(JpushConstant.OPERATE.STUCLASSNEARFIVE);
									jmsgStudent.setOrderId(order.getOrderId());
									jmsgStudent.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE, JpushConstant.GENTYPE.AUTOGEN);
									jmsgStudent.getExtras().put(JpushConstant.EXTRAFIELD.PREPARE, "5");
									jmsgStudent.getExtras().put(JpushConstant.EXTRAFIELD.TIMESTAMP, nowStamp);
									
								} else {
									jmsgStudent.addUser(order.getStudentId());
								}
								
								Notice notice=new Notice();
								notice.setUserId(order.getStudentId());
								notice.setType((byte) 2);
								notice.setUserType((byte) 2);
								notice.setOrderId(order.getOrderId());
								notice.setTime(new Date());
								notice.setTitle("上课提醒");
								notice.setDigest("您预约的课程将于5分钟后" + TimeUtil.getDateFormat(search.getPend())
										+ "开始，祝您上课愉快");
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
					}
				}
				if (olist==null || olist.size() < pageSize) {
					//推送不满jpushMax条数的消息
					if(jmsgStudent!=null){
						String msgKey=OrderConstant.USETYPE.STUDENT+"_"+nowStamp+"_"+JpushConstant.OPERATE.STUCLASSNEAR+"_"+(studentTimes++);
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
					if(log.isDebugEnabled()){
						log.debug(i+" time find 5fenzhong xuesheng="+olist+", buzou so beak;");
					}
					break;
			   }
			} catch (Exception e) {
				log.error(TimeUtil.getDateFormat(now)
						+ " jpush to "+JpushConstant.PREFIX.PUSHOBJECT+"_"+OrderConstant.USETYPE.STUDENT+"_"+nowStamp+"_"+JpushConstant.OPERATE.STUCLASSNEARFIVE+"jmsgStudent="+jmsgStudent, e);
			}
		}
		// 3-1.上课时间到没有预约，自动关闭
				start=now;
				end = DateUtil.dateAfterMilliSecond(start, 60100);
				for (int i = 1; i < pageMax; i++) {
					List<CoachClassVo> cclist = null;
					String msgKey=null;
					try {
						try {
							CoachClassVo search = new CoachClassVo();
							search.setBookNum(0);
							search.setCtype(OrderConstant.OTYPE.BOOKORDER);
							search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
							CoachClassQuery ccq = new CoachClassQuery();
							ccq.setGroupBy("and cstart between '"
									+ TimeUtil.getDateFormat(start) + "' and '"
									+ TimeUtil.getDateFormat(end)+"'");
							ccq.setPageSize(pageSize);
							cclist = coachClassService.queryByObjectAnd(search, ccq);
							if (log.isDebugEnabled()) {
								log.debug(i + " time find shangkemeiyuyue=" + cclist.size());
							}
						} catch (Exception e) {
							log.error(
									TimeUtil.getDateFormat(now) + "jpush to shangkemeiyuyue:"
											+ e.getMessage(), e);
						}
						if (cclist != null && !cclist.isEmpty()) {
							for (int j=0;j<cclist.size();j++ ) {
								CoachClassVo cc =cclist.get(j);
								msgKey=JpushConstant.PREFIX.PUSHOBJECT+"_autoclose_ccid_"+cc.getCcid();
								if(!redisUtil.isExist(msgKey)){
									cc.setIsdel(OrderConstant.ISDEL.DELETE);
									coachClassService.updateByCcid(cc, cc.getCcid());
									redisUtil.setAll(msgKey, now,liveSencode);
								}
							}
						}
						if(cclist==null || cclist.size()<pageSize) {
							if(log.isDebugEnabled()){
								log.debug(i+" time find shangkemeiyuyue="+cclist+", buzou so beak;");
							}
							break;
						}
					} catch(Exception e){
						 log.error(TimeUtil.getDateFormat(now) + " jpush to  "+i+"shangkemeiyuyue "+msgKey);	
					}
				}
				
		
	}
}
