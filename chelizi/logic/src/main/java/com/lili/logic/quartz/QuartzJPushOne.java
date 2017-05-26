package com.lili.logic.quartz;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.lili.authcode.dto.Notice;
import com.lili.authcode.manager.NoticeManager;
import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CoachManager;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.DateUtil;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ReqConstants;
import com.lili.file.manager.FileManager;
import com.lili.order.service.CoachClassService;
import com.lili.order.service.OrderService;
import com.lili.order.vo.CoachClassQuery;
import com.lili.order.vo.CoachClassVo;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.school.manager.WechatSchoolManager;
import com.lili.school.vo.WechatCoachClass;
import com.lili.school.vo.WechatPlantClass;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;

public class QuartzJPushOne {
	private Logger log = Logger.getLogger(QuartzJPushOne.class);
	private int pageSize = 100;
	private int liveSencode = 86400;

	@Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;
	@Autowired
	private OrderService orderService;
	@Autowired
	private FileManager fileManager;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
    private CoachManager coachManager;
	@Autowired
    private StudentManager studentMananger;
	@Autowired
	private CoachClassService coachClassService;
	
	@Autowired
	private NoticeManager noticeManager;
	
	@Autowired
	private WechatSchoolManager wechatSchoolManager;
	
	public void doPush() {
		// 1.上课1小时15分钟前提醒（定向预约,学生，+教练)
		Date now = new Date();
		Date start=DateUtil.dateAfterMinute(now, 75);
		Date end=DateUtil.dateAfterMilliSecond(start,60100);
		Map<Integer,OrderVo> ccidMap=new HashMap<Integer,OrderVo>();
		for (int i = 1; i < 1000; i++) {
			OrderQuery orderQuery = new OrderQuery();
			orderQuery.setPageIndex(i);
			orderQuery.setPageSize(pageSize);
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
				for (OrderVo order : olist) {
					try {
						//1.发送学生提醒
						String redisKey = "QuartzJPush_stunear75_" + order.getOrderId();
						if (!redisUtil.isExist(redisKey)) {
							String content = "您预约的"
									+ fileManager.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname()
									+ "课程将于1小时15分钟后" + TimeUtil.getDateFormat(search.getPend())
									+ "开始，祝您上课愉快";
							JpushMsg jmsg = new JpushMsg();
							jmsg.setAlter(content);
							jmsg.setUserId(order.getStudentId());
							jmsg.setOperate(JpushConstant.OPERATE.STUCLASSNEAR);
							jmsg.setOrderId(order.getOrderId());
							jmsg.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE, JpushConstant.GENTYPE.AUTOGEN);
							jmsg.getExtras().put(JpushConstant.EXTRAFIELD.PREPARE, "73");
							Message jpush = new Message();
							jpush.setKeys(order.getOrderId());
							jpush.setTopic(jpushProducer.getCreateTopicKey());
							jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
							jpush.setBody(SerializableUtil.serialize(jmsg));
							jpushProducer.send(jpush);
							redisUtil.set(redisKey, 1, liveSencode);
							
							Notice notice=new Notice();
							notice.setUserId(order.getStudentId());
							notice.setType((byte) 2);
							notice.setUserType((byte) 2);
							notice.setOrderId(order.getOrderId());
							notice.setTime(new Date());
							notice.setTitle("上课提醒");
							notice.setDigest("您预约的"
									+ fileManager.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname()
									+ "课程将于1小时15分钟后" + TimeUtil.getDateFormat(search.getPend())
									+ "开始，祝您上课愉快");
							Notice exticeNotice=noticeManager.getNoticeByOrderId(order.getOrderId(),(byte) 2);
							if(exticeNotice!=null){
								notice.setNoticeId(exticeNotice.getNoticeId());
								noticeManager.updateNotice(notice);
							}else{
								noticeManager.addNotice(notice);
							}
						}
						//2.教练提醒只能放在最后，防止破坏数据
						if(ccidMap.containsKey(order.getCcid())) {
							OrderVo o=ccidMap.get(order.getCcid());
							o.setOrderId(o.getOrderId()+","+order.getOrderId());
						} else {
							ccidMap.put(order.getCcid(),order);
						}
					} catch (Exception e) {
						log.error(TimeUtil.getDateFormat(now)
								+ " jpush to 1xiaoshi " + order.getOrderId(), e);
					}
				}
			}
			if (olist==null || olist.size() < pageSize) {
				if(log.isDebugEnabled()){
					log.debug(i+" time find 75fenzhong="+olist.size()+", buzou so beak;");
				}
				break;
			}
		}
		Iterator<Integer> it=ccidMap.keySet().iterator();
		while(it.hasNext()){
			Integer ccid=it.next();
			OrderVo order=ccidMap.get(ccid);
			try {
				// 防止重复发送
				String redisKey = "QuartzJPush_coachprepare_ccid" + ccid;
				if (!redisUtil.isExist(redisKey)) {
					String content = "您的"+ fileManager.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname()
							+ "课程将于1小时15分钟后" + TimeUtil.getDateFormat(order.getPstart())
							+ "开始，请做好教学准备";
					JpushMsg jmsg = new JpushMsg();
					jmsg.setAlter(content);
					jmsg.setUserId(order.getCoachId());
					jmsg.setOperate(JpushConstant.OPERATE.COACHPREPARE);
					jmsg.setOrderId(order.getOrderId());
					jmsg.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE, JpushConstant.GENTYPE.AUTOGEN);
					jmsg.getExtras().put(JpushConstant.EXTRAFIELD.PREPARE, "73");
					Message jpush = new Message();
					jpush.setKeys(order.getOrderId());
					jpush.setTopic(jpushProducer.getCreateTopicKey());
					jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
					jpush.setBody(SerializableUtil.serialize(jmsg));
					jpushProducer.send(jpush);
					redisUtil.set(redisKey, 1, liveSencode);
					
					Notice notice=new Notice();
					notice.setUserId(order.getCoachId());
					notice.setType((byte) 2);
					notice.setUserType((byte) 1);
					notice.setOrderId(order.getOrderId());
					notice.setTime(new Date());
					notice.setTitle("上课提醒");
					notice.setDigest("您的"+ fileManager.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname()
							+ "课程将于1小时15分钟后" + TimeUtil.getDateFormat(order.getPstart())
							+ "开始，请做好教学准备");
					 notice.setContent("您的"+ fileManager.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname()
							+ "课程将于1小时15分钟后" + TimeUtil.getDateFormat(order.getPstart())
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
				}
			} catch (Exception e) {
				log.error(TimeUtil.getDateFormat(now)
						+ " jpush to pepare coach " + order.getOrderId(), e);
			}
		}
		// 1-1.75分钟没有预约
		for (int i = 1; i < 1000; i++) {
			List<CoachClassVo> cclist = null;
			try {
				CoachClassVo search = new CoachClassVo();
				search.setBookNum(0);
				search.setCtype(OrderConstant.OTYPE.BOOKORDER);
				search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
				CoachClassQuery ccq = new CoachClassQuery();
				ccq.setGroupBy("and cstart between "
						+ TimeUtil.getDateFormat(start) + " and "
						+ TimeUtil.getDateFormat(end));
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
				for (CoachClassVo cc : cclist) {
					try {
						String redisKey = "QuartzJPush_coachnostudent_ccid"
								+ cc.getCcid();
						if (!redisUtil.isExist(redisKey)) {
							String content = "您的"
									+ fileManager.getCoursenewBycourseid(Integer.valueOf(cc.getCourseId())).getCoursenewname()
									+ "课程将于1小时15分钟后"
									+ TimeUtil.getDateFormat(cc.getCstart())
									+ "上课，目前还没有学员预约，请尽快通知学员约课！";
							JpushMsg jmsg = new JpushMsg();
							jmsg.setAlter(content);
							jmsg.setUserId(cc.getCoachId());
							jmsg.setOperate(JpushConstant.OPERATE.COACHNOBOOKED);
							jmsg.getExtras().put(
									JpushConstant.EXTRAFIELD.COACHCLASSID,
									String.valueOf(cc.getCcid()));
							jmsg.getExtras().put(
									JpushConstant.EXTRAFIELD.GENTYPE,
									JpushConstant.GENTYPE.AUTOGEN);
							jmsg.getExtras().put(
									JpushConstant.EXTRAFIELD.PREPARE, "75");
							Message jpush = new Message();
							jpush.setKeys(JpushConstant.EXTRAFIELD.COACHCLASSID
									+ "_" + cc.getCcid());
							jpush.setTopic(jpushProducer.getCreateTopicKey());
							jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
							jpush.setBody(SerializableUtil.serialize(jmsg));
							jpushProducer.send(jpush);
							redisUtil.set(redisKey, 1, liveSencode);
						}
					} catch (Exception e) {
						log.error(TimeUtil.getDateFormat(now)
								+ " jpush to 1_1xiaoshi ccid=" + cc.getCcid(),
								e);
					}
				}
			}
			if (cclist==null || cclist.size() < pageSize) {
				if(log.isDebugEnabled()){
					log.debug(i+" time find 75fenzhong no student="+cclist+", buzou so beak;");
				}
				break;
			}
		}
		// 2.上课15分钟前提醒（定向预约,教练）
		ccidMap.clear();
		for (int i = 1; i < 1000; i++) {
			OrderQuery orderQuery = new OrderQuery();
			orderQuery.setPageIndex(i);
			orderQuery.setPageSize(pageSize);
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
				for (OrderVo order : olist) {
					if(ccidMap.containsKey(order.getCcid())) {
						OrderVo o=ccidMap.get(order.getCcid());
						o.setOrderId(o.getOrderId()+","+order.getOrderId());
					} else {
						ccidMap.put(order.getCcid(),order);
					}
				}
			}
			if (olist==null ||olist.size() < pageSize) {
				if(log.isDebugEnabled()){
					log.debug(i+" time find 15fenzhong="+olist.size()+", buzou so beak;");
				}
				break;
			}
		}
		it=ccidMap.keySet().iterator();
		while(it.hasNext()){
			Integer ccid=it.next();
			OrderVo order=ccidMap.get(ccid);
			try {
				// 防止重复发送
				String redisKey = "QuartzJPush_coachnear_ccid" + ccid;
				if (redisUtil.isExist(redisKey)) {
					continue;
				}
				String content = "您的"+ fileManager.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname()
						+ "课程将于15分钟后" + TimeUtil.getDateFormat(order.getPstart())
						+ "开始，请做好教学准备";
				JpushMsg jmsg = new JpushMsg();
				jmsg.setAlter(content);
				jmsg.setUserId(order.getCoachId());
				jmsg.setOperate(JpushConstant.OPERATE.COACHCLASSNEAR);
				jmsg.setOrderId(order.getOrderId());
				jmsg.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE, JpushConstant.GENTYPE.AUTOGEN);
				jmsg.getExtras().put(JpushConstant.EXTRAFIELD.PREPARE, "15");
				Message jpush = new Message();
				jpush.setKeys(order.getOrderId());
				jpush.setTopic(jpushProducer.getCreateTopicKey());
				jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
				jpush.setBody(SerializableUtil.serialize(jmsg));
				jpushProducer.send(jpush);
				redisUtil.set(redisKey, 1, liveSencode);
				
				Notice notice=new Notice();
				notice.setUserId(order.getCoachId());
				notice.setType((byte) 2);
				notice.setUserType((byte) 1);
				notice.setOrderId(order.getOrderId());
				notice.setTime(new Date());
				notice.setTitle("上课提醒");
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
				
			} catch (Exception e) {
				log.error(TimeUtil.getDateFormat(now)
						+ " jpush to 2xiaoshi " + order.getOrderId(), e);
			}
		}
		
		// 2-1.15分钟没有预约
		start=DateUtil.dateAfterMinute(now, 15);
		end = DateUtil.dateAfterMilliSecond(start, 60100);
		for (int i = 1; i < 1000; i++) {
			List<CoachClassVo> cclist = null;
			try {
				CoachClassVo search = new CoachClassVo();
				search.setBookNum(0);
				search.setCtype(OrderConstant.OTYPE.BOOKORDER);
				search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
				CoachClassQuery ccq = new CoachClassQuery();
				ccq.setGroupBy("and cstart between "
						+ TimeUtil.getDateFormat(start) + " and "
						+ TimeUtil.getDateFormat(end));
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
				for (CoachClassVo cc : cclist) {
					try {
						String redisKey = "QuartzJPush_coachnostudent_ccid"
								+ cc.getCcid();
						if (!redisUtil.isExist(redisKey)) {
							String content = "您的"
									+ fileManager.getCoursenewBycourseid(Integer.valueOf(cc.getCourseId())).getCoursenewname()+ "课程将于15分钟后"
									+ TimeUtil.getDateFormat(cc.getCstart())
									+ "上课，目前还没有学员预约，请尽快通知学员约课！";
							JpushMsg jmsg = new JpushMsg();
							jmsg.setAlter(content);
							jmsg.setUserId(cc.getCoachId());
							jmsg.setOperate(JpushConstant.OPERATE.COACHNOBOOKED);
							jmsg.getExtras().put(
									JpushConstant.EXTRAFIELD.COACHCLASSID,
									String.valueOf(cc.getCcid()));
							jmsg.getExtras().put(
									JpushConstant.EXTRAFIELD.GENTYPE,
									JpushConstant.GENTYPE.AUTOGEN);
							jmsg.getExtras().put(
									JpushConstant.EXTRAFIELD.PREPARE, "15");
							Message jpush = new Message();
							jpush.setKeys(JpushConstant.EXTRAFIELD.COACHCLASSID
									+ "_" + cc.getCcid());
							jpush.setTopic(jpushProducer.getCreateTopicKey());
							jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
							jpush.setBody(SerializableUtil.serialize(jmsg));
							jpushProducer.send(jpush);
							redisUtil.set(redisKey, 1, liveSencode);
						}
					} catch (Exception e) {
						log.error(TimeUtil.getDateFormat(now)
								+ " jpush to 2_1xiaoshi_15fenzhong no student ccid=" + cc.getCcid(),
								e);
					}
				}
			}
			if (cclist==null || cclist.size() < pageSize) {
				if(log.isDebugEnabled()){
					log.debug(i+" time find 15fenzhong no student="+cclist+", buzou so beak;");
				}
				break;
			}
		}
		ccidMap.clear();
		//2_1.上课5分钟前提醒（定向预约,学生）
		for (int i = 1; i < 1000; i++) {
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
				for (OrderVo order : olist) {
					try {
						String redisKey = "QuartzJPush_stunear5_" + order.getOrderId();
						if (!redisUtil.isExist(redisKey)) {
							String content = "您预约的"
									+ fileManager.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname()
									+ "课程将于5分钟后" + TimeUtil.getDateFormat(search.getPend())
									+ "开始，祝您上课愉快";
							JpushMsg jmsg = new JpushMsg();
							jmsg.setAlter(content);
							jmsg.setUserId(order.getStudentId());
							jmsg.setOperate(JpushConstant.OPERATE.STUCLASSNEARFIVE);
							jmsg.setOrderId(order.getOrderId());
							jmsg.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE, JpushConstant.GENTYPE.AUTOGEN);
							jmsg.getExtras().put(JpushConstant.EXTRAFIELD.PREPARE, "5");
							Message jpush = new Message();
							jpush.setKeys(order.getOrderId());
							jpush.setTopic(jpushProducer.getCreateTopicKey());
							jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
							jpush.setBody(SerializableUtil.serialize(jmsg));
							jpushProducer.send(jpush);
							redisUtil.set(redisKey, 1, liveSencode);
							
							Notice notice=new Notice();
							notice.setUserId(order.getStudentId());
							notice.setType((byte) 2);
							notice.setUserType((byte) 2);
							notice.setOrderId(order.getOrderId());
							notice.setTime(new Date());
							notice.setTitle("上课提醒");
							notice.setDigest(content);
							notice.setContent(content);
							Notice exticeNotice=noticeManager.getNoticeByOrderId(order.getOrderId(),(byte) 2);
							if(exticeNotice!=null){
								notice.setNoticeId(exticeNotice.getNoticeId());
								noticeManager.updateNotice(notice);
							}else{
								noticeManager.addNotice(notice);
							}
							
						}
					} catch (Exception e) {
						log.error(TimeUtil.getDateFormat(now)
								+ " jpush to 1xiaoshi " + order.getOrderId(), e);
					}
				}
			}
			if (olist==null || olist.size() < pageSize) {
				if(log.isDebugEnabled()){
					log.debug(i+" time find 5fenzhong="+olist.size()+", buzou so beak;");
				}
				break;
			}
		}
		ccidMap.clear();
		// 3，4.上课时间到（学生定向预约,教练定向预约）
		for (int i = 1; i < 1000; i++) {
			OrderQuery orderQuery = new OrderQuery();
			//必须是第一页，因为后面有数据修改
			orderQuery.setPageIndex(1);
			orderQuery.setPageSize(pageSize);
			OrderVo search = new OrderVo();
			search.setOtype(OrderConstant.OTYPE.BOOKORDER);
			search.setOrderState(OrderConstant.ORDERSTATE.ACCEPTORDER);
			search.setPstart(DateUtil.dateAfterMinute(now, -1));
			// 多加0.1秒防止遗漏
			search.setPend(DateUtil.dateAfterMilliSecond(search.getPstart(),
					60100));
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
				for (OrderVo order : olist) {
					try {
						// 防止重复发送
						String redisKey = "QuartzJPush_stuclzin_" + order.getOrderId();
						if (!redisUtil.isExist(redisKey)) {
							
							String content = "您的"+ fileManager.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname()
									+ "课程已经开始";
							JpushMsg jmsg = new JpushMsg();
							jmsg.setAlter(content);
							jmsg.setUserId(order.getStudentId());
							jmsg.setOperate(JpushConstant.OPERATE.STUCLASSIN);
							jmsg.setOrderId(order.getOrderId());
							jmsg.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE, JpushConstant.GENTYPE.AUTOGEN);
							Message jpush = new Message();
							jpush.setKeys(order.getOrderId());
							jpush.setTopic(jpushProducer.getCreateTopicKey());
							jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
							jpush.setBody(SerializableUtil.serialize(jmsg));
							jpushProducer.send(jpush);
							redisUtil.set(redisKey, 1, liveSencode);
							
							Notice notice=new Notice();
							notice.setUserId(order.getStudentId());
							notice.setType((byte) 2);
							notice.setUserType((byte) 2);
							notice.setOrderId(order.getOrderId());
							notice.setTime(new Date());
							notice.setTitle("上课提醒");
							notice.setDigest("您的"+ fileManager.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname()
									+ "课程已经开始");
							Notice exticeNotice=noticeManager.getNoticeByOrderId(order.getOrderId(),(byte) 2);
							if(exticeNotice!=null){
								notice.setNoticeId(exticeNotice.getNoticeId());
								noticeManager.updateNotice(notice);
							}else{
								noticeManager.addNotice(notice);
							}
						}
					} catch (Exception e) {
						log.error(TimeUtil.getDateFormat(now)
								+ " jpush to 3xiaoshi " + order.getOrderId(), e);
					}
					
					//自动开始上课
					try {
						String redisKey = "QuartzJPush_autoinclz_" + order.getOrderId();
						if (!redisUtil.isExist(redisKey)) {
							order.setOrderState(OrderConstant.ORDERSTATE.INCLASS);
							orderService.saveOrder(order);
							redisUtil.set(redisKey, 1, liveSencode);
						}	
					} catch (Exception e) {
						log.error(TimeUtil.getDateFormat(now)
								+ " jpush to 34autoin " + order.getOrderId(), e);
					}
					//只能放在最后，防止破坏数据
					if(ccidMap.containsKey(order.getCcid())){
						OrderVo o=ccidMap.get(order.getCcid());
						o.setOrderId(o.getOrderId()+","+order.getOrderId());
					} else {
						ccidMap.put(order.getCcid(), order);
					}
				}
			}
			if (olist==null ||olist.size() < pageSize) {
				if(log.isDebugEnabled()){
					log.debug(i+" time find clzin="+olist.size()+", buzou so beak;");
				}
				break;
			}
		}
		it=ccidMap.keySet().iterator();
		while(it.hasNext()){
			Integer ccid=it.next();
			OrderVo order=ccidMap.get(ccid);
			try {
				String redisKey = "QuartzJPush_coachclzin_ccid" + ccid;
				if (!redisUtil.isExist(redisKey)) {
					
					String content = "您的"+ fileManager.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname()
							+ "课程已经开始";
					JpushMsg jmsg = new JpushMsg();
					jmsg.setAlter(content);
					jmsg.setUserId(order.getCoachId());
					jmsg.setOperate(JpushConstant.OPERATE.COACHCLASSIN);
					jmsg.setOrderId(order.getOrderId());
					jmsg.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE, JpushConstant.GENTYPE.AUTOGEN);
					Message jpush = new Message();
					jpush.setKeys(order.getOrderId());
					jpush.setTopic(jpushProducer.getCreateTopicKey());
					jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
					jpush.setBody(SerializableUtil.serialize(jmsg));
					jpushProducer.send(jpush);
					redisUtil.set(redisKey, 1, liveSencode);
					
					Notice notice=new Notice();
					notice.setUserId(order.getCoachId());
					notice.setType((byte) 2);
					notice.setUserType((byte) 1);
					notice.setOrderId(order.getOrderId());
					notice.setTime(new Date());
					notice.setTitle("上课提醒");
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
			} catch (Exception e) {
				log.error(TimeUtil.getDateFormat(now)
						+ " jpush to 4xiaoshi " + order.getOrderId(), e);
			}
		}
		ccidMap.clear();
		
		// 5，6.下课时间到(学生,教练)
		for (int i = 1; i < 1000; i++) {
			OrderQuery orderQuery = new OrderQuery();
			//必须是第一页，因为后面有数据修改
			orderQuery.setPageIndex(1);
			orderQuery.setPageSize(pageSize);
			OrderVo search = new OrderVo();
			// 多加0.1秒防止遗漏
			search.setRstart(now);
			search.setRend(DateUtil.dateAfterMilliSecond(now, 60100));
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
				for (OrderVo order : olist) {
					try {
						// 防止重复发送
						String redisKey = "QuartzJPush_stuclzout_" + order.getOrderId();
						if (!redisUtil.isExist(redisKey)) {
							String content = "您的"+ fileManager.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname()
									+ "课程训练已经完成，稍后教练会为您这次的训练情况进行评分";
							JpushMsg jmsg = new JpushMsg();
							jmsg.setAlter(content);
							jmsg.setUserId(order.getStudentId());
							jmsg.setOperate(JpushConstant.OPERATE.STUCLASSOUT);
							jmsg.setOrderId(order.getOrderId());
							jmsg.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE, JpushConstant.GENTYPE.AUTOGEN);
							jmsg.getExtras().put(JpushConstant.EXTRAFIELD.PAYSTATE, String.valueOf(order.getPayState()));
							Message jpush = new Message();
							jpush.setKeys(order.getOrderId());
							jpush.setTopic(jpushProducer.getCreateTopicKey());
							jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
							jpush.setBody(SerializableUtil.serialize(jmsg));
							jpushProducer.send(jpush);
							redisUtil.set(redisKey, 1, liveSencode);
							
							Notice notice=new Notice();
							notice.setUserId(order.getStudentId());
							notice.setType((byte) 2);
							notice.setUserType((byte) 2);
							notice.setOrderId(order.getOrderId());
							notice.setTime(new Date());
							notice.setTitle("课程完成");
							notice.setDigest("您的"+ fileManager.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname()
									+ "课程训练已经完成，稍后教练会为您这次的训练情况进行评分");
							Notice exticeNotice=noticeManager.getNoticeByOrderId(order.getOrderId(),(byte) 2);
							if(exticeNotice!=null){
								notice.setNoticeId(exticeNotice.getNoticeId());
								noticeManager.updateNotice(notice);
							}else{
								noticeManager.addNotice(notice);
							}
							
						}
					} catch (Exception e) {
						log.error(TimeUtil.getDateFormat(now)
								+ " jpush to 5xiaoshi " + order.getOrderId(), e);
					}
					
					//自动开始下课
					try {
						String redisKey = "QuartzJPush_autoutclz_" + order.getOrderId();
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
							redisUtil.set(redisKey, 1, liveSencode);
						}	
					} catch (Exception e) {
						log.error(TimeUtil.getDateFormat(now)
								+ " jpush to 56autoout " + order.getOrderId(), e);
					}
					//教练普通消息，直接发送
					if(order.getOtype()!=OrderConstant.OTYPE.BOOKORDER){
						try {
							String redisKey = "QuartzJPush_coachclzout_" + order.getOrderId();
							if (!redisUtil.isExist(redisKey)) {
								String content = "这次"+ fileManager.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname()
										+ "课程下课时间到了，请您为学员的训练情况进行评分";
								JpushMsg jmsg = new JpushMsg();
								jmsg.setAlter(content);
								jmsg.setUserId(order.getCoachId());
								jmsg.setOperate(JpushConstant.OPERATE.COACHCLASSOUT);
								jmsg.setOrderId(order.getOrderId());
								jmsg.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE, JpushConstant.GENTYPE.AUTOGEN);
								Message jpush = new Message();
								jpush.setKeys(order.getOrderId());
								jpush.setTopic(jpushProducer.getCreateTopicKey());
								jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
								jpush.setBody(SerializableUtil.serialize(jmsg));
								jpushProducer.send(jpush);
								redisUtil.set(redisKey, 1, liveSencode);
								
								Notice notice=new Notice();
								notice.setUserId(order.getCoachId());
								notice.setType((byte) 2);
								notice.setUserType((byte) 1);
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
						} catch (Exception e) {
							log.error(TimeUtil.getDateFormat(now)
									+ " jpush to 6xiaoshi " + order.getOrderId(), e);
						}
					//教练预约消息，只能放在最后，防止破坏数据
					} else if(ccidMap.containsKey(order.getCcid())){
						OrderVo o=ccidMap.get(order.getCcid());
						o.setOrderId(o.getOrderId()+","+order.getOrderId());
					} else {
						ccidMap.put(order.getCcid(), order);
					}
				}
			}
			if (olist==null ||olist.size() < pageSize) {
				if(log.isDebugEnabled()){
					log.debug(i+" time find clzout="+olist.size()+", buzou so beak;");
				}
				break;
			}
		}
		it=ccidMap.keySet().iterator();
		while(it.hasNext()){
			Integer ccid=it.next();
			OrderVo order=ccidMap.get(ccid);
			try {
				String redisKey = "QuartzJPush_coachclzout_ccid" + ccid;
				if (!redisUtil.isExist(redisKey)) {
					String content = "这次"+ fileManager.getCoursenewBycourseid(Integer.valueOf(order.getCourseId())).getCoursenewname()
							+ "课程下课时间到了，请您为学员的训练情况进行评分";
					JpushMsg jmsg = new JpushMsg();
					jmsg.setAlter(content);
					jmsg.setUserId(order.getCoachId());
					jmsg.setOperate(JpushConstant.OPERATE.COACHCLASSOUT);
					jmsg.setOrderId(order.getOrderId());
					jmsg.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE, JpushConstant.GENTYPE.AUTOGEN);
					Message jpush = new Message();
					jpush.setKeys(order.getOrderId());
					jpush.setTopic(jpushProducer.getCreateTopicKey());
					jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
					jpush.setBody(SerializableUtil.serialize(jmsg));
					jpushProducer.send(jpush);
					redisUtil.set(redisKey, 1, liveSencode);
					
					Notice notice=new Notice();
					notice.setUserId(order.getCoachId());
					notice.setType((byte) 2);
					notice.setUserType((byte) 1);
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
			} catch (Exception e) {
				log.error(TimeUtil.getDateFormat(now)
						+ " jpush to 6xiaoshi " + order.getOrderId(), e);
			}
		}
		ccidMap.clear();
		
		// 1.上课1小时前提醒（微信排班,教练)
		try {
			WechatCoachClass classRecord = new WechatCoachClass();
			classRecord.setCstart(DateUtil.dateAfterMinute(now, 60));
			classRecord.setIsdel(0);
			classRecord.setCend(DateUtil.dateAfterMilliSecond(classRecord.getCstart(),60100));
			List<WechatCoachClass> wechatOrderList = wechatSchoolManager.queryWechatClass(classRecord);
			if (wechatOrderList != null && wechatOrderList.size() > 0) {
				//遍历学员是否有未接受的
				for (WechatCoachClass wechatCoachClass : wechatOrderList) {
					String redisKey = "QuartzJPush_coachWechatClass_ccid" + wechatCoachClass.getCcid();
					if (!redisUtil.isExist(redisKey)) {
						WechatPlantClass record = new WechatPlantClass();
						record.setCcid(wechatCoachClass.getCcid());
						record.setIsdel(0);
						List<WechatPlantClass> wechatPlantClassList = wechatSchoolManager.queryWechatPlantClassList(record);
						int noAcceptClass = 0;
						for (WechatPlantClass plantClassOne : wechatPlantClassList) {
							if (plantClassOne.getState() == 0) { //未接受 0代表正常，1-已接受，2-拒绝；3-取消,4-已完成；5-缺课
								noAcceptClass ++;
							}
						}
						String content = null;
						JpushMsg jmsg = new JpushMsg();
						Notice notice = new Notice();
						if (noAcceptClass > 0) {
							content = "还有" + noAcceptClass + "名学员没有确认是否来上课，请快去联系他们来上课吧";
							jmsg.setOperate(JpushConstant.OPERATE.COACHWECHATCLASS);
							notice.setMsgType(Integer.parseInt(JpushConstant.OPERATE.COACHWECHATCLASS));
							notice.setTitle("你的排班课程将在1小时后开始");
						}
						else {
							content = "您安排的课程学员已全部确认会前来上课，请提前做好上课准备。";
							jmsg.setOperate(JpushConstant.OPERATE.COACHWECHATCLASSUN);
							notice.setMsgType(Integer.parseInt(JpushConstant.OPERATE.COACHWECHATCLASSUN));
							notice.setTitle("你的排班课程将在1小时后开始");
						}
						
						jmsg.setAlter(content);
						jmsg.setUserId(wechatCoachClass.getCoachId());
						jmsg.setOrderId(wechatCoachClass.getCcid().toString());
						jmsg.getExtras().put(JpushConstant.EXTRAFIELD.GENTYPE, JpushConstant.GENTYPE.AUTOGEN);
						Message jpush = new Message();
						jpush.setKeys(wechatCoachClass.getCcid().toString());
						jpush.setTopic(jpushProducer.getCreateTopicKey());
						jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
						jpush.setBody(SerializableUtil.serialize(jmsg));
						jpushProducer.send(jpush);
						
						redisUtil.set(redisKey, 1, liveSencode);
						
						
						//推送成功后，保存到用户消息中心
						notice.setUserId(wechatCoachClass.getCoachId());
						notice.setUserType((byte)ReqConstants.USER_TYPE_COACH);
						notice.setType((byte) 2);  //type=2订单消息
						notice.setDigest(content);  //content改为存html内容
						notice.setTime(new Date());
						notice.setIsdel((byte)0);
						notice.setOrderId(wechatCoachClass.getCcid().toString());
						noticeManager.addNotice(notice);			
					}
					
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (MQClientException e) {
			e.printStackTrace();
		} catch (RemotingException e) {
			e.printStackTrace();
		} catch (MQBrokerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
