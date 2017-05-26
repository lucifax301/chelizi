package com.lili.logic.quartz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.lili.authcode.dto.Notice;
import com.lili.authcode.manager.NoticeManager;
import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.manager.CourseSManager;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.OrderConstant;
import com.lili.common.constant.RedisExpireConstant;
import com.lili.common.util.DateUtil;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.event.vo.CourseStatusEventVo;
import com.lili.order.service.CoachClassService;
import com.lili.order.service.OrderService;
import com.lili.order.vo.CoachClassQuery;
import com.lili.order.vo.CoachClassVo;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.pay.service.PayService;
import com.lili.pay.service.PayServiceNew;
import com.lili.pay.vo.PayVo;
import com.lili.pay.vo.PayWayType;
import com.lili.pay.vo.PurposeType;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;

public class QuartzOutClzJPush {
	private Logger log = Logger.getLogger(QuartzOutClzJPush.class);
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
	private StudentManager studentManager;
	@Autowired
	private NoticeManager noticeManager;
	
	public void doPush() {
		Date now = new Date();
		String nowStamp=String.valueOf(now.getTime());
		// 5，6.下课时间到(学生,教练)
		Map<String,List<OrderVo>> classMap=new HashMap<String,List<OrderVo>>();
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
				orderQuery.setorderBy("order by coach_id desc,ccid desc");
				OrderVo search = new OrderVo();
				//为了提前容忍系统上次丢失，设置60s重复执行
				search.setRstart(DateUtil.dateAfterMilliSecond(now, -60000));
				//为了提前容忍系统非正分钟数，设置提前59s上课
				search.setRend(DateUtil.dateAfterMilliSecond(now, 59000));
				//已下课的不推送
				search.setOrderState(OrderConstant.ORDERSTATE.INCLASS);
				List<OrderVo> olist = null;
				try {
					olist = orderService.queryByRend(search, orderQuery);
					if(log.isDebugEnabled()){
						log.debug(i+" time find clzout="+olist.size());
					}
				} catch (Exception e) {
					log.error(TimeUtil.getDateFormat(now)+"jpush to 5,6chadan:"+e.getMessage(),e);
				}
				if (olist != null) {
						for (int j=0;j<olist.size();j++) {
							OrderVo order=olist.get(j);
							//1.发送学生提醒
							String redisKey = JpushConstant.PREFIX.PUSHOBJECT+order.getStudentId()+"_"+OrderConstant.USETYPE.STUDENT+"_"+nowStamp+"_"+JpushConstant.OPERATE.STUCLASSOUT;
							String qianRedisKey = JpushConstant.PREFIX.PUSHOBJECT+order.getStudentId()+"_"+OrderConstant.USETYPE.STUDENT+"_"+order.getOrderId()+"_"+JpushConstant.OPERATE.STUCLASSOUT;
							if (!redisUtil.isExist(redisKey)&&!redisUtil.isExist(qianRedisKey)) {
								if(jmsgStudent==null){
									String content = "您的课程训练已经完成，稍后教练会为您这次的训练情况进行评分";
									jmsgStudent=new JpushMsg();
									jmsgStudent.setAlter(content);
									jmsgStudent.addUser(order.getStudentId());
									jmsgStudent.setOperate(JpushConstant.OPERATE.STUCLASSOUT);
									jmsgStudent.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE, JpushConstant.GENTYPE.AUTOGEN);
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
								notice.setTitle("课程完成");
								notice.setDigest("您的课程训练已经完成，稍后教练会为您这次的训练情况进行评分");
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
							String redisCoachKey = JpushConstant.PREFIX.PUSHOBJECT+order.getCoachId()+"_"+OrderConstant.USETYPE.COACH+"_"+nowStamp+"_"+JpushConstant.OPERATE.COACHCLASSOUT;
							String qianRedisCoachKey = JpushConstant.PREFIX.PUSHOBJECT+order.getCoachId()+"_"+OrderConstant.USETYPE.COACH+"_"+order.getOrderId()+"_"+JpushConstant.OPERATE.COACHCLASSOUT;
							if (!redisUtil.isExist(redisCoachKey)&&!redisUtil.isExist(qianRedisCoachKey)) {
								String ccidKey=order.getCoachId()+"_"+order.getOrderId();
								if(order.getOtype()==OrderConstant.OTYPE.BOOKORDER) {
									ccidKey=order.getCoachId()+"_ccid_"+order.getCcid();
								}
								boolean canPush=false;
								if(jmsgCoach==null){
									String content = "这次课程下课时间到了，请您为学员的训练情况进行评分";
									jmsgCoach = new JpushMsg();
									jmsgCoach.setAlter(content);
									jmsgCoach.addUser(order.getCoachId());
									jmsgCoach.setOperate(JpushConstant.OPERATE.COACHCLASSOUT);
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
								
								Notice notice=new Notice();
								notice.setUserId(order.getCoachId());
								notice.setType((byte) 2);
								notice.setUserType((byte) 1);
								notice.setOrderId(order.getOrderId());
								notice.setTime(new Date());
								notice.setTitle("评分提醒");
								notice.setDigest("这次课程下课时间到了，请您为学员的训练情况进行评分");
								notice.setContent("这次课程下课时间到了，请您为学员的训练情况进行评分");
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
									if(j<(olist.size()-1) ) {
										String nextKey=olist.get(j+1).getCoachId()+"_"+olist.get(j+1).getOrderId();
										if(olist.get(j+1).getOtype()==OrderConstant.OTYPE.BOOKORDER){
											nextKey=olist.get(j+1).getCoachId()+"_ccid_"+olist.get(j+1).getCcid();
										}
										if(!ccidKey.equals(nextKey)) {
											canPush=true;
										}
									} else if(order.getOtype()!=OrderConstant.OTYPE.BOOKORDER){
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
						//自动下课
						try {
								redisKey = "QuartzJPush_autooutclz_" + order.getOrderId();
								if (!redisUtil.isExist(redisKey)) {
									order.setOrderState(OrderConstant.ORDERSTATE.COMPLETE);
									if (order.getOtype() == OrderConstant.OTYPE.NOWORDER)
                                    {
                                        Student student = new Student();
                                        student.setStudentId(order.getStudentId());
                                        student.setEventDesc("");
                                        student.setEventState((byte)ReqConstants.STUDENT_STATUS_OFF_CLASS);
                                        studentMananger.updateStudent(student);
                                        
                                        Coach coach = new Coach();
                                        coach.setCoachId(order.getCoachId());
                                        coach.setWstate(ReqConstants.COACH_STATUS_OFF_WORK);
                                        coach.setEventDesc("");
                                        coachManager.updateCoach(coach);
                                    }
									orderService.saveOrder(order);
									redisUtil.setAll(redisKey, 1, liveSencode);
								}	
								redisKey = "orderoutclass_coachpay_" + order.getOrderId();
								if (!redisUtil.isExist(redisKey)) {
									if(order.getPayState()==OrderConstant.PAYSTATE.HASPAY && order.getPayTotal()==0 && order.getCoupon()!=null && order.getCouponTotal()>0) {
										log.debug(order + " pay with coupon and pay coach now.");
										PayVo payVo=new PayVo(order.getStudentId(),OrderConstant.USETYPE.STUDENT,order.getCouponTotal(),PayWayType.COUPON,order.getOrderId(),PurposeType.COURSE.getType(),order.getCoupon(),0,"remark");
										ReqResult r=payServiceNew.pay(payVo);
										if(!"0".equals(r.getResult().get("code"))) {
											log.error(order + " pay with coupon and pay coach now ERROR="+r.getResult().get("code"));
										}
									}
									redisUtil.setAll(redisKey, 1,
											RedisExpireConstant.RMQTIME.RETYSECONDS);
								}
						} catch (Exception e) {
								log.error(TimeUtil.getDateFormat(now)
										+ " jpush to 34autoin " + order.getOrderId(), e);
						}
						//学员学时
						redisKey = "orderoutclass_stuClassHour_" + order.getOrderId();
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
						log.debug(i+" time find clzout="+olist+", buzou so beak;");
					}
					break;
			   }
			} catch (Exception e) {
				log.error(TimeUtil.getDateFormat(now)
						+ " jpush to clzout_"+nowStamp+"_"+studentTimes+",coachTimes="+coachTimes+"jmsgStudent="+jmsgStudent+",jmsgCoach="+jmsgCoach, e);
			}
		}
		//推送下课消息
		Iterator<String> coachCcidKey=classMap.keySet().iterator();
		while(coachCcidKey.hasNext()) {
			String key=coachCcidKey.next();
			String redisKey = JpushConstant.PREFIX.PUSHOBJECT+"_outclzmsg_"+key;
			if (!redisUtil.isExist(redisKey)) {
				try {
					List<OrderVo> list=classMap.get(key);
					CourseStatusEventVo statusEventVo=new CourseStatusEventVo();
			        statusEventVo.setAutoGen(true);
			        statusEventVo.setOrderList(list);
			        Message message = new Message(studentProducer.getCreateTopicKey(), "finish", SerializableUtil.serialize(statusEventVo));
			        studentProducer.send(message);
			        redisUtil.setAll(redisKey, 1,liveSencode);
				} catch(Exception e){
					log.error(redisKey+" push outclass message Exception:"+e.getMessage(),e);
				}
			}
		}
		//下课时间到了，但是还没有上课或者还处于上课状态的都需要静默下课
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
				//下课时间到2分钟后还没有触发的课程静默下课
				orderQuery.setGroupBy(" where UNIX_TIMESTAMP(rend) < "+((System.currentTimeMillis()/1000) -120)+"  and order_state in (2,3) " );
				OrderVo search = new OrderVo();
				List<OrderVo> olist = null;
				try {
					olist = orderService.queryByObjectAnd(search, orderQuery);
					if(log.isDebugEnabled()){
						log.debug(i+" time find later clzout="+olist.size());
					}
				} catch (Exception e) {
					log.error(TimeUtil.getDateFormat(now)+"jpush later clzout:"+e.getMessage(),e);
				}
				if (olist != null) {
						for (int j=0;j<olist.size();j++) {
							OrderVo order=olist.get(j);
						//自动下课
						try {
								String redisKey = "QuartzJPush_autooutclz_" + order.getOrderId();
								if (!redisUtil.isExist(redisKey)) {
									order.setOrderState(OrderConstant.ORDERSTATE.COMPLETE);
									if (order.getOtype() == OrderConstant.OTYPE.NOWORDER)
                                    {
                                        Student student = new Student();
                                        student.setStudentId(order.getStudentId());
                                        student.setEventDesc("");
                                        student.setEventState((byte)ReqConstants.STUDENT_STATUS_OFF_CLASS);
                                        studentMananger.updateStudent(student);
                                        
                                        Coach coach = new Coach();
                                        coach.setCoachId(order.getCoachId());
                                        coach.setWstate(ReqConstants.COACH_STATUS_OFF_WORK);
                                        coach.setEventDesc("");
                                        coachManager.updateCoach(coach);
                                    }
									orderService.saveOrder(order);
									redisUtil.setAll(redisKey, 1, liveSencode);
								}	
								redisKey = "orderoutclass_coachpay_" + order.getOrderId();
								if (!redisUtil.isExist(redisKey)) {
									if(order.getPayState()==OrderConstant.PAYSTATE.HASPAY && order.getPayTotal()==0 && order.getCoupon()!=null && order.getCouponTotal()>0) {
										log.debug(order + " pay with coupon and pay coach now.");
										PayVo payVo=new PayVo(order.getStudentId(),OrderConstant.USETYPE.STUDENT,order.getCouponTotal(),PayWayType.COUPON,order.getOrderId(),PurposeType.COURSE.getType(),order.getCoupon(),0,"remark");
										ReqResult r=payServiceNew.pay(payVo);
										if(!"0".equals(r.getResult().get("code"))) {
											log.error(order + " pay with coupon and pay coach now ERROR="+r.getResult().get("code"));
										}
									}
									redisUtil.setAll(redisKey, 1,
											RedisExpireConstant.RMQTIME.RETYSECONDS);
								}
						} catch (Exception e) {
								log.error(TimeUtil.getDateFormat(now)
										+ " jpush to 34autoin " + order.getOrderId(), e);
						}
					}
				}
				if (olist==null || olist.size() < pageSize) {
					if(log.isDebugEnabled()){
						log.debug(i+" time find later clzout="+olist+", buzou so beak;");
					}
					break;
			   }
			} catch (Exception e) {
				log.error(TimeUtil.getDateFormat(now)
						+ " jpush to later clzout_"+nowStamp+"_"+studentTimes+",coachTimes="+coachTimes+"jmsgStudent="+jmsgStudent+",jmsgCoach="+jmsgCoach, e);
			}
		}
	}
}
