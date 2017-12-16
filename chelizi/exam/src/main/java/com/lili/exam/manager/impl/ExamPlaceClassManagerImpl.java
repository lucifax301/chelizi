package com.lili.exam.manager.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.aliyuncs.exceptions.ClientException;
import com.lili.coach.dto.Car;
import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CarManager;
import com.lili.coach.manager.CoachManager;
import com.lili.common.constant.JpushConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.exam.SmsUtil;
import com.lili.exam.dto.ExamCarDateNew;
import com.lili.exam.dto.ExamDateCarInfo;
import com.lili.exam.dto.ExamInnerInfo;
import com.lili.exam.dto.ExamPlace;
import com.lili.exam.dto.ExamPlaceClass;
import com.lili.exam.dto.ExamPlaceClassDate;
import com.lili.exam.dto.ExamPlaceClassExample;
import com.lili.exam.dto.ExamPlaceClassVo;
import com.lili.exam.dto.ExamPlaceFavor;
import com.lili.exam.dto.ExamPlaceFavorKey;
import com.lili.exam.dto.ExamPlaceOrder;
import com.lili.exam.dto.ExamPlaceOrderExample;
import com.lili.exam.dto.ExamPlaceWhitelistExample;
import com.lili.exam.dto.ExamVip;
import com.lili.exam.dto.ExamVipBookInfo;
//import com.lili.exam.dto.ExamVipCoach;
import com.lili.exam.manager.ExamPlaceClassManager;
import com.lili.exam.manager.ExamPlaceManager;
import com.lili.exam.manager.ExamPlaceOrderManager;
import com.lili.exam.manager.ExamVipManager;
import com.lili.exam.mapper.ExamCarDateNewMapper;
import com.lili.exam.mapper.ExamPlaceClassMapper;
import com.lili.exam.mapper.ExamPlaceFavorMapper;
import com.lili.exam.mapper.ExamPlaceMapper;
import com.lili.exam.mapper.ExamPlaceOrderMapper;
import com.lili.exam.mapper.ExamPlaceWhitelistMapper;
import com.lili.pay.manager.MoneyManager;

public class ExamPlaceClassManagerImpl implements ExamPlaceClassManager {
	private static Logger log = LoggerFactory.getLogger(ExamPlaceClassManagerImpl.class);
	@Autowired
	private ExamPlaceClassMapper examPlaceClassMapper;
	@Autowired
	private CoachManager coachManager;
	@Autowired
	private ExamPlaceManager examPlaceManager;
	@Autowired
	private ExamPlaceOrderManager examPlaceOrderManager;
	@Autowired
	private ExamPlaceOrderMapper examPlaceOrderMapper;
	@Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;
    @Autowired
    private MoneyManager moneyManager;
	@Value("${exam.innerPlace}")
	private String exam_inner_place = "1,4,5";
	@Value("${exam.innerExpireTime}")
	private String exam_inner_expire_time = "16:00:00";
	@Autowired
	private ExamPlaceWhitelistMapper examPlaceWhilelistMapper;
	@Autowired
    private ExamPlaceFavorMapper examPlaceFavorMapper;
	@Autowired
	ExamPlaceMapper examPlaceMapper;
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	ExamVipManager examVipManagerImpl;
	
	@Autowired
	ExamCarDateNewMapper examCarDateMapper;
	
	@Autowired
	private CarManager carManager;
	
	private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	
	private static SimpleDateFormat timeformat=new SimpleDateFormat("HH:mm");
	
	private static SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Override
	public ReqResult addExamPlaceClass(ExamPlaceClass record) {
		ReqResult res = ReqResult.getSuccess();
		try {
			// 检查排班冲突
			int placeId = record.getPlaceId();
			Date d0 = record.getPstart();
			Date d1 = record.getPend();
			ExamPlaceClassExample example = new ExamPlaceClassExample();
			example.createCriteria()
					.andPlaceIdEqualTo(placeId)
					.andTypeEqualTo(record.getType())
					.andPstartGreaterThan(d0)
					.andStateNotEqualTo((byte) 1) //'排班状态：0-正常；1-已关闭；2-已延迟',
					.andPstartLessThan(d1);
			example.or(example.createCriteria()
					.andPlaceIdEqualTo(placeId)
					.andTypeEqualTo(record.getType())
					.andPendGreaterThan(d0)
					.andStateNotEqualTo((byte) 1) //'排班状态：0-正常；1-已关闭；2-已延迟',
					.andPendLessThan(d1));
			example.or(example.createCriteria()
					.andPlaceIdEqualTo(placeId)
					.andTypeEqualTo(record.getType())
					.andPstartLessThanOrEqualTo(d0)
					.andStateNotEqualTo((byte) 1) //'排班状态：0-正常；1-已关闭；2-已延迟',
					.andPendGreaterThanOrEqualTo(d1));
			List<ExamPlaceClass> list = examPlaceClassMapper.selectByExample(example);
			if(null != list && list.size()> 0){
				res.setCode(ResultCode.ERRORCODE.FAILED);
				res.setMsgInfo("排班时间冲突！");
				return res;
			}
			
//			ExamPlace ep = getExamPlaceById(placeId);
//			ExamCarDate examCarDate=new ExamCarDate();
//			examCarDate.setSchoolId(ep.getSchoolId());
//			examCarDate.setDate(format.format(d0));
//			ExamCarDate eexamCarDate=examCarDateMapper.selectByDate(examCarDate);
//			//没有当天的车使用记录，插入一条
//			if(eexamCarDate==null){
//				eexamCarDate=new ExamCarDate();
//				eexamCarDate.setSchoolId(ep.getSchoolId());
//				eexamCarDate.setDate(format.format(d0));
//				
//				List<Car> allcars=carManager.getCarBySchoolId(ep.getSchoolId());
//				List<ExamCarState> ecslist=new ArrayList();
//				//0-24,每半小时一个0
//				String bitmap="000000000000000000000000000000000000000000000000";
//				for(Car car:allcars){
//					ExamCarState ecs=new ExamCarState();
//					ecs.setBitmap(bitmap);
//					ecs.setCarno(car.getCarNo());
//					ecslist.add(ecs);
//				}
//				String carliststr=JSON.toJSONString(ecslist);
//				eexamCarDate.setCarlist(carliststr);
//				examCarDateMapper.insertExamCarDate(eexamCarDate);
//			}
			
			String dayStr = new SimpleDateFormat("yyyy-MM-dd").format(d0);
			
			if(record.getType()==0){//普通排班
				List<ExamPlaceClass> classes = getExamPlaceClass(placeId+"", dayStr);
				for(ExamPlaceClass cls:classes){
					if(cls.getType()==1){
						System.out.println(cls.getId()+" "+changeClassBitmap(record)+" "+changeClassBitmap(cls)+" "+isoccur(changeClassBitmap(record),changeClassBitmap(cls)));
						boolean isoccur=isoccur(changeClassBitmap(record),changeClassBitmap(cls));
						if(isoccur){
							//重新算外部分配车数量
							ExamPlace ep = getExamPlaceById(placeId);
							List<Car> allcars=carManager.getCarBySchoolId(ep.getSchoolId());
							int c1=0;
							int c2=0;
							for(Car car:allcars){
								if(car.getDriveType()==1){
									c1++;
								}
								if(car.getDriveType()==2){
									c2++;
								}
							}
							
							record.setC1(c1-cls.getC1inner());
							record.setC2(c2-cls.getC2inner());
							record.setC1outer(c1-cls.getC1inner());
							record.setC2outer(c2-cls.getC2inner());
							break;
						}
					}
				}
			}else{//大客户排班
				List<ExamPlaceClass> classes = getExamPlaceClass(placeId+"", dayStr);
				for(ExamPlaceClass cls:classes){
					if(cls.getType()==0){
						boolean isoccur=isoccur(changeClassBitmap(record),changeClassBitmap(cls));
						if(isoccur){
							//更新交集普通排班的c1,c2,c1outer,c2outer
							ExamPlace ep = getExamPlaceById(placeId);
							List<Car> allcars=carManager.getCarBySchoolId(ep.getSchoolId());
							int c1=0;
							int c2=0;
							for(Car car:allcars){
								if(car.getDriveType()==1){
									c1++;
								}
								if(car.getDriveType()==2){
									c2++;
								}
							}
							//总量-大客户排班量
							int newc1=c1-record.getC1inner();
							int newc2=c2-record.getC2inner();
							newc1=cls.getC1()>newc1?newc1:cls.getC1();
							newc2=cls.getC2()>newc2?newc2:cls.getC2();
							cls.setC1(newc1);
							cls.setC1outer(newc1);
							cls.setC2(newc2);
							cls.setC2outer(newc2);
							examPlaceClassMapper.updateC1C2(cls);
						}
					}
				}
			}
			
			if(record.getC1()==0&&record.getC2()==0){
				return res;
			}
			
			examPlaceClassMapper.insertSelective(record);
			//20161114有新增排班时，清除今天的排班查询
			
			redisUtil.delete(RedisKeys.REDISKEY.EXAM_PLACE_DAY + placeId+ "." + dayStr);
			
		} catch (Exception e) {
			e.printStackTrace();
			res.setCode(ResultCode.ERRORCODE.EXCEPTION);
			res.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return res;
	}
	
	@Override
	public ReqResult copyExamPlaceClass(String classId, String pdate, String pstart) {
		ReqResult res = ReqResult.getSuccess();
		try {
			ExamPlaceClass cls = getExamPlaceClassOne(Integer.parseInt(classId));
			if(null == cls){
				res.setCode(ResultCode.ERRORCODE.EXCEPTION);
				res.setMsgInfo("排班不存在！");
				return res;
			}
			int placeId = cls.getPlaceId();
			String[] pdates = pdate.split(",");//2016-09-12,2016-09-13
			String[] pstarts = pstart.split(",");//08:00:00-09:00:00,10:00:00-11:00:00
			for(int i=0;i<pdates.length;i++){
				for(int j=0;j<pstarts.length;j++){
					String[] ptime = pstarts[j].split("-");
			    	Date d0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pdates[i] + " "+ ptime[0]);
			    	Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pdates[i] + " "+ ptime[1]);
			    	if(d0.after(d1)){
						res.setCode(ResultCode.ERRORCODE.EXCEPTION);
						res.setMsgInfo("预约时段设置错误！");
						return res;
			    	}
			    	long diff = d1.getTime() - d0.getTime();
			    	long hour = diff/(1000*60*60);
			    	if(hour < cls.getMinHours()){
						res.setCode(ResultCode.ERRORCODE.EXCEPTION);
						res.setMsgInfo("预约时段设置错误！");
						return res;
			    	}
					ExamPlaceClassExample example = new ExamPlaceClassExample();
					example.createCriteria()
							.andPlaceIdEqualTo(placeId)
							.andPstartGreaterThan(d0)
							.andPstartLessThan(d1).andTypeEqualTo(cls.getType());
					example.or(example.createCriteria()
							.andPlaceIdEqualTo(placeId)
							.andPendGreaterThan(d0)
							.andPendLessThan(d1));
					example.or(example.createCriteria()
							.andPlaceIdEqualTo(placeId)
							.andPstartLessThanOrEqualTo(d0)
							.andPendGreaterThanOrEqualTo(d1));
					List<ExamPlaceClass> list = examPlaceClassMapper.selectByExample(example);
					if(null != list && list.size()> 0){
						res.setCode(ResultCode.ERRORCODE.FAILED);
						res.setMsgInfo("排班时间冲突！");
						return res;
					}
			    	ExamPlaceClass record = new ExamPlaceClass();
			    	BeanCopy.copyAll(cls, record);
			    	record.setId(null);
			    	record.setPstart(d0);
			    	record.setPend(d1);
			    	record.setRstart(d0);
			    	record.setRend(d1);
			    	record.setState((byte) 0);
			    	record.setC1book(0);
			    	record.setC1bookInner(0);
			    	record.setC1bookOuter(0);
			    	record.setC2book(0);
			    	record.setC2bookInner(0);
			    	record.setC2bookOuter(0);
			    	examPlaceClassMapper.insertSelective(record);
					//20161114有新增排班时，清除今天的排班查询
					String dayStr = new SimpleDateFormat("yyyy-MM-dd").format(d0);
					redisUtil.delete(RedisKeys.REDISKEY.EXAM_PLACE_DAY + placeId+ "." + dayStr);

				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}

	private void sendDelay(Coach coach,String oldtime,String newtime){
		String message="{\"name\":\""+coach.getName()+"\", \"old\":\""+oldtime+"\",\"time\":\""+newtime+"\"}";
		try {
			SmsUtil.sendSms(coach.getPhoneNum(), "SMS_94740013", message);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void doSendDelay(Set<Long> users,ExamPlaceClass cls){
		Iterator<Long> it= users.iterator();
		Date pstart=cls.getPstart();
		Date rstart=cls.getRstart();
		while(it.hasNext()){
			long coachid=it.next();
			Coach coach = coachManager.getCoachInfo(coachid);
			sendDelay(coach,dateformat.format(pstart),dateformat.format(rstart));
		}
	}

	//SMS_94400001 name time
	
	@Override
	public ReqResult delayExamPlaceClass(String classId, String num,
			String remark) {
		ReqResult res = ReqResult.getSuccess();
		try {
			String[] clses = classId.split(","); 
			for(int i=0;i<clses.length;i++){
				ExamPlaceClass cls = getExamPlaceClassOne(Integer.parseInt(clses[i]));
				if(null == cls){
					res.setCode(ResultCode.ERRORCODE.EXCEPTION);
					res.setMsgInfo("排班不存在！");
					return res;
				}
				int number = Integer.parseInt(num.trim());
				if(number <= 0){
					res.setCode(ResultCode.ERRORCODE.EXCEPTION);
					res.setMsgInfo("延期时间错误！");
					return res;
				}
				Date d0 = new Date(cls.getRstart().getTime() + 1000*60* number); 
				Date d1 = new Date(cls.getRend().getTime() + 1000*60* number); 
				cls.setRstart(d0);
				cls.setRend(d1);
				cls.setRemark(remark);
				//cls.setMtime(new Date());
				updateExamPlaceClass(cls);
				//对于已经使用该排班生成的订单，也需要修改相应订单。
				ExamPlaceOrderExample example = new ExamPlaceOrderExample();
				List<Byte> states = new ArrayList<Byte>();
				states.add((byte) 0); //订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭'
				states.add((byte) 1);
				states.add((byte) 2); 
				example.createCriteria()
						.andClassIdEqualTo(cls.getId())
						.andStateIn(states);							
				List<ExamPlaceOrder> orders = examPlaceOrderMapper.selectByExample(example);
				Set<Long> users=new TreeSet();
				if(null != orders && orders.size() > 0){
					for(ExamPlaceOrder order:orders){
						order.setRstart(d0);
						order.setRend(d1);
						order.setRemark(remark);
						//order.setMtime(new Date());
						updateExamPlaceOrder(order);
						users.add(order.getCoachId());
						//通知教练已延班
						JpushMsg jmsg = new JpushMsg();
					    Map<String,String> extras=new HashMap<String,String>();
					    extras.put("orderId", order.getOrderId());
					    extras.put("delay", num);
					    extras.put("remark", remark);
					    jmsg.setExtras(extras);
						jmsg.setAlter("您预约考场的练考时间已被延期！");
						jmsg.setUserId(order.getCoachId());
						jmsg.setOrderId(order.getOrderId());
					    jmsg.setOperate(JpushConstant.OPERATE.ORDER_DELAY);//如果有extras，则operate要放在extra之后
						Message jpush = new Message();
						jpush.setKeys(order.getOrderId());
						jpush.setTopic(jpushProducer.getCreateTopicKey());
						jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
						jpush.setBody(SerializableUtil.serialize(jmsg));
						jpushProducer.send(jpush);
						
					}
					
				}
				
				if(users.size()>0){
					this.doSendDelay(users, cls);
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	


	@Override
	public ReqResult closeExamPlaceClass(String classId, String remark) {
		ReqResult res = ReqResult.getSuccess();
		try {
			String[] clses = classId.split(","); 
			for(int i=0;i<clses.length;i++){
				ExamPlaceClass cls = getExamPlaceClassOne(Integer.parseInt(clses[i]));
				if(null == cls){
					res.setCode(ResultCode.ERRORCODE.EXCEPTION);
					res.setMsgInfo("排班不存在！");
					return res;
				}
				Date now = new Date();
				if(cls.getRend().before(now)){
					res.setCode(ResultCode.ERRORCODE.EXCEPTION);
					res.setMsgInfo("排班已过期！");
					return res;
					
				}
				cls.setState((byte) 1);	//'排班状态：0-正常；1-已关闭；2-已延迟'
				cls.setRemark(remark);
				//cls.setMtime(new Date());
				updateExamPlaceClass(cls);
				//对于已经使用该排班生成的订单，也需要修改相应订单。
				ExamPlaceOrderExample example = new ExamPlaceOrderExample();
				List<Byte> states = new ArrayList<Byte>();
				states.add((byte) 0); //订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭'
				states.add((byte) 1);
				states.add((byte) 2); 
				example.createCriteria()
						.andClassIdEqualTo(cls.getId())
						.andStateIn(states);							
				List<ExamPlaceOrder> orders = examPlaceOrderMapper.selectByExample(example);
				if(null != orders && orders.size() > 0){
					for(ExamPlaceOrder order:orders){
						//如已支付，则退款
						if(order.getState() ==1 || order.getState() == 2){
							order.setRefundTotal(order.getPayTotal()); //全额退款
							moneyManager.handleExamPlaceRefund(order, 1.0); //20161010后台关闭的订单，全额退款。
						}
						//关闭订单
						order.setState((byte) 5); //订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭'
						order.setRemark(remark);
						order.setMtime(new Date());
						updateExamPlaceOrder(order);
						
						//通知教练订单已被关闭
						JpushMsg jmsg = new JpushMsg();
					    Map<String,String> extras=new HashMap<String,String>();
					    extras.put("orderId", order.getOrderId());
					    extras.put("remark", remark);
					    jmsg.setExtras(extras);
						jmsg.setAlter("您预约考场的订单已被关闭！如已支付，退款将返回到您的余额账户中。");
						jmsg.setUserId(order.getCoachId());
						jmsg.setOrderId(order.getOrderId());
					    jmsg.setOperate(JpushConstant.OPERATE.ORDER_CLOSE);//如果有extras，则operate要放在extra之后
						Message jpush = new Message();
						jpush.setKeys(order.getOrderId());
						jpush.setTopic(jpushProducer.getCreateTopicKey());
						jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
						jpush.setBody(SerializableUtil.serialize(jmsg));
						jpushProducer.send(jpush);
						
						//（2.3）恢复所占优惠
						if(order.getFavorUse() != 0){
							ExamPlaceFavorKey key = new ExamPlaceFavorKey();
							key.setPlaceId(order.getPlaceId());
							key.setUserId(order.getCoachId());
							ExamPlaceFavor record = examPlaceFavorMapper.selectByPrimaryKey(key);
							if(null != record){
								record.setFavorOut(record.getFavorOut() + order.getFavorUse()); //恢复已使用优惠  //生成的优惠在结束后才增加，这里不需要恢复
								//record.setDuration(record.getDuration() - order.getDuration()); //恢复增加的时长 //时长在结束后才增加，这里不需要恢复
								examPlaceFavorMapper.updateByPrimaryKeySelective(record);
							}
						}

					}
					
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private void sendClose(Coach coach,String time){
		String message="{\"name\":\""+coach.getName()+"\", \"time\":\""+time+"\"}";
		try {
			SmsUtil.sendSms(coach.getPhoneNum(), "SMS_94605111", message);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void doCloseSend(List<ExamPlaceOrder> orders){
		for(ExamPlaceOrder record:orders){
			Coach coach = coachManager.getCoachInfo(record.getCoachId());
			ExamPlaceClass cls = getExamPlaceClassOne(record.getClassId());
			Date rstart=cls.getRstart();
			sendClose(coach,dateformat.format(rstart));
		}
	}

	@Override
	public ReqResult closeCarExamPlaceClass(String classId, String remark) {
		ReqResult res = ReqResult.getSuccess();
		try {
			String[] clses = classId.split(","); 
			for(int i=0;i<clses.length;i++){
				ExamPlaceClass cls = getExamPlaceClassOne(Integer.parseInt(clses[i]));
				if(null == cls){
					res.setCode(ResultCode.ERRORCODE.EXCEPTION);
					res.setMsgInfo("排班不存在！");
					return res;
				}
				Date now = new Date();
				if(cls.getRend().before(now)){
					res.setCode(ResultCode.ERRORCODE.EXCEPTION);
					res.setMsgInfo("排班已过期！");
					return res;
					
				}
				
				
				ExamPlaceOrderExample example = new ExamPlaceOrderExample();
				List<Byte> states = new ArrayList<Byte>();
				states.add((byte) 0); //订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭'
				states.add((byte) 1);
				states.add((byte) 2); 
				example.createCriteria()
						.andClassIdEqualTo(cls.getId())
						.andStateIn(states);							
				List<ExamPlaceOrder> orders = examPlaceOrderMapper.selectByExample(example);
				/* comment 20170910
				if(orders!=null&&orders.size()>0){
					res.setCode(ResultCode.ERRORCODE.EXCEPTION);
					res.setMsgInfo("排班被使用，不能关闭！");
					return res;
				}
				*/
				
				cls.setState((byte) 1);	//'排班状态：0-正常；1-已关闭；2-已延迟'
				cls.setRemark(remark);
				//cls.setMtime(new Date());
				updateExamPlaceClass(cls);
				//对于已经使用该排班生成的订单，也需要修改相应订单。
				
				if(null != orders && orders.size() > 0){
					for(ExamPlaceOrder order:orders){
//						//如已支付，则退款
//						if(order.getState() ==1 || order.getState() == 2){
//							order.setRefundTotal(order.getPayTotal()); //全额退款
//							moneyManager.handleExamPlaceRefund(order, 1.0); //20161010后台关闭的订单，全额退款。
//						}
						//关闭订单
						order.setState((byte) 5); //订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭'
						order.setRemark(remark);
						order.setMtime(new Date());
						updateExamPlaceOrder(order);
//						
//						//通知教练订单已被关闭
//						JpushMsg jmsg = new JpushMsg();
//					    Map<String,String> extras=new HashMap<String,String>();
//					    extras.put("orderId", order.getOrderId());
//					    extras.put("remark", remark);
//					    jmsg.setExtras(extras);
//						jmsg.setAlter("您预约考场的订单已被关闭！如已支付，退款将返回到您的余额账户中。");
//						jmsg.setUserId(order.getCoachId());
//						jmsg.setOrderId(order.getOrderId());
//					    jmsg.setOperate(JpushConstant.OPERATE.ORDER_CLOSE);//如果有extras，则operate要放在extra之后
//						Message jpush = new Message();
//						jpush.setKeys(order.getOrderId());
//						jpush.setTopic(jpushProducer.getCreateTopicKey());
//						jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
//						jpush.setBody(SerializableUtil.serialize(jmsg));
//						jpushProducer.send(jpush);
//						
//						//（2.3）恢复所占优惠
//						if(order.getFavorUse() != 0){
//							ExamPlaceFavorKey key = new ExamPlaceFavorKey();
//							key.setPlaceId(order.getPlaceId());
//							key.setUserId(order.getCoachId());
//							ExamPlaceFavor record = examPlaceFavorMapper.selectByPrimaryKey(key);
//							if(null != record){
//								record.setFavorOut(record.getFavorOut() + order.getFavorUse()); //恢复已使用优惠  //生成的优惠在结束后才增加，这里不需要恢复
//								//record.setDuration(record.getDuration() - order.getDuration()); //恢复增加的时长 //时长在结束后才增加，这里不需要恢复
//								examPlaceFavorMapper.updateByPrimaryKeySelective(record);
//							}
//						}
//
					}
					
					doCloseSend(orders);
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}


	@Override
	public List<ExamPlaceClass> getExamPlaceClass(String placeId,String pdate) {
		//可以增加缓存，但在新增排班或者更改排班时，需要同时更新缓存 TODO
    	try {
    		List<ExamPlaceClass> clses = redisUtil.get(RedisKeys.REDISKEY.EXAM_PLACE_DAY + placeId+ "." + pdate);
    		if(null == clses){
    			Date d0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pdate + " 00:00:00");
    			Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pdate + " 23:59:59");
    			ExamPlaceClassExample example = new ExamPlaceClassExample();
    			example.createCriteria()
    					.andPlaceIdEqualTo(Integer.parseInt(placeId))
    					.andPstartBetween(d0, d1)
    					.andStateNotEqualTo((byte) 1);//'排班状态：0-正常；1-已关闭；2-已延迟',
//    			example.or(example.createCriteria()
//    					.andPlaceIdEqualTo(Integer.parseInt(placeId))
//    					.andPendBetween(d0, d1)
//    					.andStateNotEqualTo((byte) 1));//'排班状态：0-正常；1-已关闭；2-已延迟',
    			example.setOrderByClause("pstart asc");
    			clses = examPlaceClassMapper.selectByExample(example);
    			redisUtil.setAll(RedisKeys.REDISKEY.EXAM_PLACE_DAY + placeId+ "." + pdate, clses, RedisKeys.EXPIRE.WEEK);
    		}
    		return clses;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
	}
	
	
	@Override
	public List<ExamPlaceClass> getExamPlaceClass(String placeId,String pdate,String type) {
		//可以增加缓存，但在新增排班或者更改排班时，需要同时更新缓存 TODO
    	try {
    		List<ExamPlaceClass> clses = null;//redisUtil.get(RedisKeys.REDISKEY.EXAM_PLACE_DAY + placeId+ "."+type+"." + pdate);
    		if(null == clses){
    			Date d0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pdate + " 00:00:00");
    			Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pdate + " 23:59:59");
    			ExamPlaceClassExample example = new ExamPlaceClassExample();
    			example.createCriteria()
    					.andPlaceIdEqualTo(Integer.parseInt(placeId)).andTypeEqualTo(Integer.parseInt(type))
    					.andPstartBetween(d0, d1)
    					.andStateNotEqualTo((byte) 1);//'排班状态：0-正常；1-已关闭；2-已延迟',
//    			example.or(example.createCriteria()
//    					.andPlaceIdEqualTo(Integer.parseInt(placeId))
//    					.andPendBetween(d0, d1)
//    					.andStateNotEqualTo((byte) 1));//'排班状态：0-正常；1-已关闭；2-已延迟',
    			example.setOrderByClause("pstart asc");
    			clses = examPlaceClassMapper.selectByExample(example);
    			//redisUtil.setAll(RedisKeys.REDISKEY.EXAM_PLACE_DAY + placeId+ "." + type+"."+pdate, clses, RedisKeys.EXPIRE.WEEK);
    		}
    		return clses;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
	}
	

	@Override
	public ExamPlaceClass getExamPlaceClassOne(Integer id) {
		ExamPlaceClass cls = redisUtil.get(RedisKeys.REDISKEY.EXAM_PLACE_CLASS + id);
		if(null == cls){
			cls = examPlaceClassMapper.selectByPrimaryKey(id);
			redisUtil.setAll(RedisKeys.REDISKEY.EXAM_PLACE_CLASS + id, cls, RedisKeys.EXPIRE.WEEK);
		}
		return cls;
	}


	@Override
	public int updateExamPlaceClass(ExamPlaceClass record) {
		try {
			record.setMtime(new Date());
			examPlaceClassMapper.updateByPrimaryKeySelective(record);
			//修改部分数据  插入缓存必须重新从数据库加载一次信息
			record = examPlaceClassMapper.selectByPrimaryKey(record.getId());
			redisUtil.setAll(RedisKeys.REDISKEY.EXAM_PLACE_CLASS + record.getId(), record, RedisKeys.EXPIRE.WEEK); //写表 与 写缓存 顺序是否需要调整？
			//20161114有排班更新时，清除今天的排班查询
			String dayStr = new SimpleDateFormat("yyyy-MM-dd").format(record.getPstart());
			redisUtil.delete(RedisKeys.REDISKEY.EXAM_PLACE_DAY + record.getPlaceId()+ "." + dayStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public Map getExamPlaceCarClassInfo(String userId,
			String userType, String placeId, String pdate, String drtype){
		//获取一天对的排版信息
		Map map=new HashMap();
		List<ExamPlaceClassVo> clss=this.getExamPlaceClassInfo(userId, userType, placeId, pdate, drtype);
		if(clss==null||clss.size()==0){
			map.put("carlist", null);
			return map;
		}
		map.put("clss", clss);
		ExamPlace ep = getExamPlaceById(Integer.parseInt(placeId));
		
		Coach coach = coachManager.getCoachInfo(Long.parseLong(userId));
		//ExamVipCoach examVipCoach=examVipManagerImpl.getExamVipCoach(coach.getPhoneNum(), ep.getSchoolId());
		ExamVip examVip=new ExamVip();
		examVip.setSchoolId(ep.getSchoolId());
		List<ExamVip> examVips=examVipManagerImpl.getExamVipList(examVip);
		ExamVip evip=null;
		if(examVips!=null){
			for(ExamVip vip:examVips){
				if(coach.getPhoneNum().equals(vip.getMobile())){
					evip=vip;break;
				}
			}
		}
		
		//内部
//		if(examVipCoach!=null){
//			map.put("vip", "1");
//		}else{
//			map.put("vip", "0");
//		}
		if(evip!=null){
			map.put("vip", "1");
		}else{
			map.put("vip", "0");
		}
		
		//获取一天的车使用情况
		List<ExamCarDateNew> examCarDate=getExamCarDate(Integer.parseInt(placeId),ep.getSchoolId(),pdate);
		if(examCarDate!=null){
			
			List<ExamCarDateNew> cars=examCarDate;
			
			
			List<ExamDateCarInfo> result=new ArrayList();	
			
			List<Car> allcars=carManager.getCarBySchoolId(ep.getSchoolId());
			Date now=new Date();
			
			for(Car ocar:allcars){
				boolean hasdate=false;
				for(ExamCarDateNew car:cars){
					if(car.getCarId()==ocar.getCarId()){
						hasdate=true;break;
					}
				}
				if(!hasdate){
					ExamCarDateNew examCarDateNew=new ExamCarDateNew();
					examCarDateNew.setCarId(ocar.getCarId());
					examCarDateNew.setCarno(ocar.getCarNo());
					String bitmap="000000000000000000000000000000000000000000000000";
					examCarDateNew.setBitmap(bitmap);
					cars.add(examCarDateNew);
				}
			}
			
			for(ExamCarDateNew car:cars){
				boolean match=false;
				for(Car ocar:allcars){
					if(car.getCarId()==ocar.getCarId()){
						car.setCarno(car.getCarno());
						if(ocar.getDriveType().intValue()==Integer.parseInt(drtype)){
							match=true;
						}
						break;
					}
				}
				if(!match) continue;
				List<ExamPlaceClassVo> newclss=new ArrayList();
				
				for(ExamPlaceClassVo vo:clss){
					if(vo.getPstart().before(now)) continue;
					ExamPlaceClassVo newvo=new ExamPlaceClassVo();
					BeanUtils.copyProperties(vo, newvo);
					
					if(newvo.getUsed()==0){//此班别从数量看还可以用，判断车此时区是否可用
						changeClassBitmap(newvo);
						int used=usedcar(newvo,car);
						newvo.setUsed(used);
					}
					
					if(newvo.getState()!=2&&newvo.getUsed()==1){
						newvo.setState(2);
					}
					newclss.add(newvo);
					
				}
				ExamDateCarInfo carinfo=new ExamDateCarInfo();
				carinfo.setCarno(car.getCarno());
				carinfo.setClss(newclss);
				result.add(carinfo);
			}
			map.put("carlist", result);
		}else{
			List<ExamDateCarInfo> result=new ArrayList();
			
			List<Car> allcars=carManager.getCarBySchoolId(ep.getSchoolId());
			for(Car car:allcars){
				ExamDateCarInfo carinfo=new ExamDateCarInfo();
				carinfo.setCarno(car.getCarNo());
				result.add(carinfo);
			}
			
			map.put("carlist", result);
		}
		return map;
	}
	
	String factor="111111111111111111111111111111111111111111111111";
	
	/**
	 * 
	 * @param vo
	 * @param carState
	 * @return 1 被使用, 0 空闲没被使用
	 */
	private int usedcar(ExamPlaceClassVo vo,ExamCarDateNew carState){
		System.out.println(vo.getBitmap()+"-------"+carState.getBitmap());
		//000000000000000000000000000000000001111110000000-->111111111111111111111111110000001111111
		long bitmap=Long.parseLong(carState.getBitmap(),2)^Long.parseLong(factor,2);
		
		String newbitmap=Long.toBinaryString((Long.parseLong(vo.getBitmap(),2)&bitmap));
		while(newbitmap.length()<48){
			newbitmap="0"+newbitmap;
		}
		if(newbitmap.equals(vo.getBitmap()))
			return 0;
		return 1;
	}
	
	private boolean isoccur(String bitmap1,String bitmap2){
		if((Long.parseLong(bitmap1, 2)&Long.parseLong(bitmap2, 2))==0){//没有交集
			return false;
		}
		return true;
	}
	
	/**
	 * 转换排版的日期区间成bitmap   0000000000111100000	
	 * @param vo
	 */
	private void changeClassBitmap(ExamPlaceClassVo vo){
		Date d0=vo.getPstart();
		Date d1=vo.getPend();
		int hour=d0.getHours();
		int bindex=hour*2+1;
		int minute=d0.getMinutes();
		if(minute==30) bindex++;
		
		hour=d1.getHours();
		int eindex=hour*2+1;
		minute=d1.getMinutes();
		if(minute==30) eindex++;
		int i=1;
		StringBuilder builder=new StringBuilder();
		while(i<bindex){
			builder.append("0");
			i++;
		}
		while(i>=bindex&&i<eindex){
			builder.append("1");
			i++;
		}
		while(i<=48){
			builder.append("0");
			i++;
		}
		vo.setBitmap(builder.toString());
	}
	
	private String changeClassBitmap(ExamPlaceClass vo){
		Date d0=vo.getPstart();
		Date d1=vo.getPend();
		int hour=d0.getHours();
		int bindex=hour*2+1;
		int minute=d0.getMinutes();
		if(minute==30) bindex++;
		
		hour=d1.getHours();
		int eindex=hour*2+1;
		minute=d1.getMinutes();
		if(minute==30) eindex++;
		int i=1;
		StringBuilder builder=new StringBuilder();
		while(i<bindex){
			builder.append("0");
			i++;
		}
		while(i>=bindex&&i<eindex){
			builder.append("1");
			i++;
		}
		while(i<=48){
			builder.append("0");
			i++;
		}
		return builder.toString();
	}
	
//	private ExamCarDate getExamCarDate(Integer placeId,int schoolId,String date){
//		ExamCarDate ep = redisUtil.get(RedisKeys.REDISKEY.EXAM_PLACE_CAR_DATE + placeId+"."+date);
//		if (null == ep) {
//			ExamCarDate examCarDate=new ExamCarDate();
//			examCarDate.setSchoolId(schoolId);
//			examCarDate.setDate(date);
//			ep=examCarDateMapper.selectByDate(examCarDate);
//			redisUtil.setAll(RedisKeys.REDISKEY.EXAM_PLACE_CAR_DATE + placeId+"."+date, ep,
//					RedisKeys.EXPIRE.WEEK);
//		}
//
//		return ep;
//	}
	
	private List<ExamCarDateNew> getExamCarDate(Integer placeId,int schoolId,String date){
		ExamCarDateNew examCarDate=new ExamCarDateNew();
		examCarDate.setSchoolId(schoolId);
		examCarDate.setDate(date);
		List list=examCarDateMapper.selectByDate(examCarDate);

		return list;
	}

	@Override
	public List<ExamPlaceClassDate>  getExamPlaceClassDate(String placeId,String pdate,String days) {
		// version 1
//		Map<String, Integer> res = new HashMap<String, Integer>();
//		try {
//			String[] pdates = pdate.split(",");
//			for(int i=0;i<pdates.length;i++){
//				Date d0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pdates[i] + " 00:00:00");
//				Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pdates[i] + " 23:59:59");
//				ExamPlaceClassExample example = new ExamPlaceClassExample();
//				example.createCriteria()
//						.andPlaceIdEqualTo(Integer.parseInt(placeId))
//						.andPstartBetween(d0, d1);
//				example.or(example.createCriteria()
//						.andPlaceIdEqualTo(Integer.parseInt(placeId))
//						.andPendBetween(d0, d1));
//				int count = examPlaceClassMapper.countByExample(example);
//				res.put(pdates[i], count);
//				
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		// version 2
		List<ExamPlaceClassDate> data= new ArrayList<ExamPlaceClassDate>();
		try {
			//（1）根据days算出未来的日期
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(pdate); //初始时间
			int day = Integer.parseInt(days.trim());
			int pid = Integer.parseInt(placeId);
			for(int i=0;i<day;i++){
		    	Calendar gdate = Calendar.getInstance(); 
		    	gdate.setTime(date);
		    	gdate.add(gdate.DATE, i);
		    	Date d0 = gdate.getTime();
		    	Date d1 = new Date(d0.getTime() + (24*60*60 -1)*1000 );
		    	
				ExamPlaceClassExample example = new ExamPlaceClassExample();
				example.createCriteria()
						.andPlaceIdEqualTo(pid)
						.andStateNotEqualTo((byte) 1)  //'排班状态：0-正常；1-已关闭；2-已延迟',
						.andPstartBetween(d0, d1);
//				example.or(example.createCriteria()
//						.andPlaceIdEqualTo(pid)
//						.andStateNotEqualTo((byte) 1)  //'排班状态：0-正常；1-已关闭；2-已延迟',
//						.andPendBetween(d0, d1));
				int count = examPlaceClassMapper.countByExample(example);
				ExamPlaceClassDate a = new ExamPlaceClassDate();
				a.setPdate(d0);
				a.setCls(count);
				a.setDay(gdate.get(Calendar.DAY_OF_WEEK) -1); //0--6对于星期几
		    	data.add(a);
		    	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}

	@Override
	public List<ExamPlaceClassDate>  getExamPlaceClassDate(String placeId,String pdate,String days,String type) {
		
		
		// version 2
		List<ExamPlaceClassDate> data= new ArrayList<ExamPlaceClassDate>();
		try {
			//（1）根据days算出未来的日期
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(pdate); //初始时间
			int day = Integer.parseInt(days.trim());
			int pid = Integer.parseInt(placeId);
			for(int i=0;i<day;i++){
		    	Calendar gdate = Calendar.getInstance(); 
		    	gdate.setTime(date);
		    	gdate.add(gdate.DATE, i);
		    	Date d0 = gdate.getTime();
		    	Date d1 = new Date(d0.getTime() + (24*60*60 -1)*1000 );
		    	
				ExamPlaceClassExample example = new ExamPlaceClassExample();
				example.createCriteria()
						.andPlaceIdEqualTo(pid).andTypeEqualTo(Integer.parseInt(type))
						.andStateNotEqualTo((byte) 1)  //'排班状态：0-正常；1-已关闭；2-已延迟',
						.andPstartBetween(d0, d1);
//				example.or(example.createCriteria()
//						.andPlaceIdEqualTo(pid)
//						.andStateNotEqualTo((byte) 1)  //'排班状态：0-正常；1-已关闭；2-已延迟',
//						.andPendBetween(d0, d1));
				int count = examPlaceClassMapper.countByExample(example);
				ExamPlaceClassDate a = new ExamPlaceClassDate();
				a.setPdate(d0);
				a.setCls(count);
				a.setDay(gdate.get(Calendar.DAY_OF_WEEK) -1); //0--6对于星期几
		    	data.add(a);
		    	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}

	
	@Override
	public List<ExamPlaceClassVo> getExamPlaceClassInfo(String userId,
			String userType, String placeId, String pdate, String drtype) {
		//
		ExamPlace ep = getExamPlaceById(Integer.parseInt(placeId));
		if(ep.getServicetype()==0){
	    	try {
	    		List<ExamPlaceClass> classes = getExamPlaceClass(placeId, pdate);
	    		if(null != classes && classes.size()>0){
	    			List<ExamPlaceClassVo> data = new ArrayList<ExamPlaceClassVo>();
	        		//（1）查询用户身份，是否是驾校内部教练
	        		Coach coach = coachManager.getCoachInfo(Long.parseLong(userId));
	        		
	        		
	        		//boolean isInner = (coach.getIsImport().intValue() == 1 && null != coach.getSchoolId() &&  null != ep.getSchoolId() && coach.getSchoolId().intValue() == ep.getSchoolId().intValue());
	        		//集团内部驾校的教练才属于内部教练
					//boolean isInner = (coach.getIsImport().intValue() == 1 && exam_inner_place.contains(coach.getSchoolId().toString()));
					// 集团内部同步的数据不准确，采用白名单的形式校验内部教练	20160929
					boolean isInner = isInWhitelist(coach.getPhoneNum(),coach.getSchoolId());
					boolean isC1 = "1".equals(drtype.trim());
					byte drive = Byte.parseByte(drtype.trim());
					
	    			for(int i=0;i<classes.size();i++){
	    				ExamPlaceClass cls = classes.get(i);
	    				ExamPlaceClassVo d = new ExamPlaceClassVo();
	    				d.setId(cls.getId());
	    				d.setPlaceId(cls.getPlaceId());
	    				d.setPstart(cls.getPstart());
	    				d.setPend(cls.getPend());
	    				d.setRstart(cls.getRstart());
	    				d.setRend(cls.getRend());
	    				d.setFavorType(cls.getFavorType());
	    				// 20161005 需求变更：内部教练和外部教练都受到内部预留空位的约束
	    				Date d0 = cls.getRstart();
	    				int innerExpire = cls.getInnerExpire(); //内部失效时间，天
	    				boolean isExpire = false;//是否已失效
	    				if(innerExpire == 0){ //预留给内部失效天数=0，未设置失效天数，则不失效
	    					isExpire = false;
	    				}else {
	        		    	Calendar gdate = Calendar.getInstance(); 
	        		    	gdate.setTime(d0);
	        		    	gdate.add(gdate.DATE, -1 * innerExpire);
	        		    	String aa = new SimpleDateFormat("yyyy-MM-dd").format(gdate.getTime()) + " " + exam_inner_expire_time;
	        		    	Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(aa);
	        		    	isExpire = d1.before(new Date());  //从计划上课时间的日期-失效天数 + 失效时间点 <= 现在时间  ==> 已失效
	    				}
	    				
	    		    	if(isInner){	//内部教练-享受内部价格
	    		    		d.setPrice(cls.getInnerPrice() * cls.getDuration());
	    		    		if(isExpire){
	        		    		//内部预留已失效
	            				if(isC1){
	            					d.setC(cls.getC1());
	            					d.setCbook(cls.getC1book());
	            				}else{
	            					d.setC(cls.getC2());
	            					d.setCbook(cls.getC2book());
	            				}
	    		    		}else {
	        		    		//内部预留有效
	            				if(isC1){
	            					d.setC(cls.getC1inner());
	            					d.setCbook(cls.getC1bookInner());
	            				}else{
	            					d.setC(cls.getC2inner());
	            					d.setCbook(cls.getC2bookInner());
	            				}
	    		    		}
	    		    	}else {
	    		    		//外部教练-享受外部价格
	    		    		d.setPrice(cls.getOuterPrice() * cls.getDuration());
	    		    		if(isExpire){
	        		    		//内部预留已失效
	            				if(isC1){
	            					d.setC(cls.getC1());
	            					d.setCbook(cls.getC1book());
	            				}else{
	            					d.setC(cls.getC2());
	            					d.setCbook(cls.getC2book());
	            				}
	    		    		}else {
	        		    		//内部预留有效
	            				if(isC1){
	            					d.setC(cls.getC1outer());
	            					d.setCbook(cls.getC1bookOuter());
	            				}else{
	            					d.setC(cls.getC2outer());
	            					d.setCbook(cls.getC2bookOuter());
	            				}
	    		    		}
	    		    	}
	    		    	
	    				//是否已经约过该排班
	    				if(hasBookClass(Long.parseLong(userId),cls.getId(),drive)){
	    					d.setState(1); //排班状态：0-可约；1-已约；2-不可约；3-已过期
	    				}else {
	    					if(d.getC() == d.getCbook()){
	    						d.setState(2);
	    					}else {
	    						d.setState(0);
	    					}
	        		    	// 20161009 如果已经到了计划上课时间，则不允许再预约！
	    					// 20161122考场方要求，在实际上课时间结束前，都是允许预约的！
	        		    	Date now = new Date();
	        		    	Date endTime = cls.getRend();
	        		    	if(now.after(endTime)){
	        		    		d.setState(3);
	        		    	}
	    					
	    				}
	
	    				data.add(d);
	    			}
					
	        		return data;
	    		}else {
	    			return null;
	    		}
	
	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{//提供车模式
			try {
				ExamVip examVip=new ExamVip();
				examVip.setSchoolId(ep.getSchoolId());
				List<ExamVip> examVips=examVipManagerImpl.getExamVipList(examVip);
				
	    		List<ExamPlaceClass> classes = getExamPlaceClass(placeId, pdate);
	    		if(null != classes && classes.size()>0){
	    			List<ExamPlaceClassVo> data = new ArrayList<ExamPlaceClassVo>();
	        		//（1）查询用户身份，是否是驾校内部教练
	        		Coach coach = coachManager.getCoachInfo(Long.parseLong(userId));
	        		//ExamVipCoach examVipCoach=examVipManagerImpl.getExamVipCoach(coach.getPhoneNum(), ep.getSchoolId());
	        		ExamVip evip=null;
	        		if(examVips!=null){
	        			for(ExamVip vip:examVips){
	        				if(coach.getPhoneNum().equals(vip.getMobile())){
	        					evip=vip;break;
	        				}
	        			}
	        		}
	        		
	        		//boolean isInner=(examVipCoach!=null);
	        		boolean isInner=(evip!=null);
	        		
					boolean isC1 = "1".equals(drtype.trim());
					byte drive = Byte.parseByte(drtype.trim());
					
					List<ExamPlaceClass> vipclass=new ArrayList();
					
					for(int i=classes.size()-1;i>=0;i--){
						ExamPlaceClass cls = classes.get(i);
						if(isInner&&cls.getType()==0){//普通排班移除
							classes.remove(i);
						}
						if(!isInner&&cls.getType()==1){//移除大客户排版
							//如果是普通客户，把大客户排班放到临时list来做比较
							vipclass.add(cls);
							classes.remove(i);
						}
					}
					System.out.println("=============clsss:"+classes.size());
					
	    			for(int i=0;i<classes.size();i++){
	    				ExamPlaceClass cls = classes.get(i);
	    				ExamPlaceClassVo d = new ExamPlaceClassVo();
	    				
	    				//大客户的具体约考情况
	    				String innerinfostr=cls.getInnerinfo();
	    				ExamInnerInfo innerinfo=null;
	    				if(innerinfostr!=null&&innerinfostr.length()>0)
	    					innerinfo= JSON.parseObject(innerinfostr, ExamInnerInfo.class);
	    				
	    				d.setId(cls.getId());
	    				d.setPlaceId(cls.getPlaceId());
	    				d.setPstart(cls.getPstart());
	    				d.setPend(cls.getPend());
	    				d.setRstart(cls.getRstart());
	    				d.setRend(cls.getRend());
	    				d.setFavorType(cls.getFavorType());
	    				// 20161005 需求变更：内部教练和外部教练都受到内部预留空位的约束
	    				Date d0 = cls.getRstart();
	    				int innerExpire = cls.getInnerExpire(); //内部失效时间，天
	    				boolean isExpire = false;//是否已失效
	    				if(innerExpire == 0){ //预留给内部失效天数=0，未设置失效天数，则不失效
	    					isExpire = false;
	    				}else {
	        		    	Calendar gdate = Calendar.getInstance(); 
	        		    	gdate.setTime(d0);
	        		    	gdate.add(gdate.DATE, -1 * innerExpire);
	        		    	String aa = new SimpleDateFormat("yyyy-MM-dd").format(gdate.getTime()) + " " + exam_inner_expire_time;
	        		    	Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(aa);
	        		    	isExpire = d1.before(new Date());  //从计划上课时间的日期-失效天数 + 失效时间点 <= 现在时间  ==> 已失效
	    				}
	    				
	    		    	if(isInner){	//内部教练-享受内部价格
	    		    		d.setPrice(cls.getInnerPrice() * cls.getDuration());
	    		    		if(isExpire){
	        		    		//内部预留已失效
	            				if(isC1){
	            					d.setC(cls.getC1());
	            					d.setCbook(cls.getC1book());
	            				}else{
	            					d.setC(cls.getC2());
	            					d.setCbook(cls.getC2book());
	            				}
	            				if(d.getC().intValue()==d.getCbook().intValue()){
	            					d.setUsed(1);
	            				}
	    		    		}else {
	        		    		//内部预留有效
	    		    			ExamVipBookInfo matchvip=null;
	    		    			if(innerinfo!=null){
	    		    				List<ExamVipBookInfo> vipbookinfo=innerinfo.getBookinfo();
            						
            						if(vipbookinfo!=null){
            							for(ExamVipBookInfo bi:vipbookinfo){
            								
            								//if(bi.getVipId()==examVipCoach.getVipId()){
            								if(bi.getVipId()==evip.getId()){
            									matchvip=bi;
            									break;
            								}
            							}
            							
            						}
	    		    			}
	    		    			
	            				if(isC1){
	            					if(matchvip!=null){//排班信息里已经有这个大客户预约信息
            							d.setC(matchvip.getC1());
            							d.setCbook(matchvip.getC1book());
            						}else{
            							d.setC(0);
        								d.setCbook(0);
            						}
	            				}else{
	            					if(matchvip!=null){
            							d.setC(matchvip.getC2());
            							d.setCbook(matchvip.getC2book());
            						}else{
            							d.setC(0);
        								d.setCbook(0);
            						}
	            					
	            				}
	            				if(d.getC().intValue()==d.getCbook().intValue()){
	            					d.setUsed(1);
	            				}
	    		    		}
	    		    	}else {
	    		    		//外部教练-享受外部价格
	    		    		d.setPrice(cls.getOuterPrice() * cls.getDuration());
	    		    		if(isExpire){
	        		    		//内部预留已失效
	            				if(isC1){
	            					d.setC(cls.getC1());
	            					d.setCbook(cls.getC1book());
	            				}else{
	            					d.setC(cls.getC2());
	            					d.setCbook(cls.getC2book());
	            				}
	    		    		}else {
	        		    		//内部预留有效
	            				if(isC1){
	            					d.setC(cls.getC1outer());
	            					d.setCbook(cls.getC1bookOuter());
	            				}else{
	            					d.setC(cls.getC2outer());
	            					d.setCbook(cls.getC2bookOuter());
	            				}
	    		    		}
	    		    		if(d.getC().intValue()==d.getCbook().intValue()){
            					d.setUsed(1);
            				}
	    		    	}
	    		    	
	    				//是否已经约过该排班
	    				if(hasBookClass(Long.parseLong(userId),cls.getId(),drive)){
	    					d.setState(1); //排班状态：0-可约；1-已约；2-不可约；3-已过期
	    				}else {
	    					if(d.getC() == d.getCbook()){
	    						d.setState(2);
	    					}else {
	    						d.setState(0);
	    					}
	        		    	// 20161009 如果已经到了计划上课时间，则不允许再预约！
	    					// 20161122考场方要求，在实际上课时间结束前，都是允许预约的！
	        		    	Date now = new Date();
	        		    	Date endTime = cls.getRend();
	        		    	if(now.after(endTime)){
	        		    		d.setState(3);
	        		    	}
	    					
	    				}
	
	    				data.add(d);
	    			}
					
	        		return data;
	    		}else {
	    			return null;
	    		}
	
	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    	return null;
	}
	
	


	public boolean hasBookClass(long coachId, Integer classId,Byte drtype) {
		//约过排班后，要更新这个缓存 TODO
		Boolean check = redisUtil.get(RedisKeys.REDISKEY.EXAM_PLACE_CLASS_BOOK +classId + "." + coachId + "." + drtype);
		if(null == check){
			ExamPlaceOrderExample example = new ExamPlaceOrderExample();
			List<Byte> states = new ArrayList<Byte>();
			states.add((byte) 4); //订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭'
			states.add((byte) 5);
			example.createCriteria()
					.andClassIdEqualTo(classId)
					.andCoachIdEqualTo(coachId)
					.andDrtypeEqualTo(drtype)
					.andStateNotIn(states);							
			List<ExamPlaceOrder> orders = examPlaceOrderMapper.selectByExample(example);
			if(null != orders && orders.size()>0){
				check = true;
				redisUtil.setAll(RedisKeys.REDISKEY.EXAM_PLACE_CLASS_BOOK +classId + "." + coachId + "." + drtype, true, RedisKeys.EXPIRE.WEEK);
			}else {
				check = false;
				redisUtil.setAll(RedisKeys.REDISKEY.EXAM_PLACE_CLASS_BOOK +classId + "." + coachId + "." + drtype, false, RedisKeys.EXPIRE.WEEK);
			}
		}
		
		return check;

	}
	
	
	/**
	 * 用户是否在白名单内。白名单教练属于内部教练，享受内部价格
	 * @param phoneNum
	 * @param schoolId
	 * @return
	 */
	public boolean isInWhitelist(String phoneNum, Integer schoolId) {
		Boolean check = false;
		try {
			check = redisUtil.get(REDISKEY.EXAM_PLACE_WHITE + phoneNum);
			if(null == check){
				ExamPlaceWhitelistExample example = new ExamPlaceWhitelistExample();
				example.createCriteria()
				.andMobileLike("%" +phoneNum + "%")
				.andStateEqualTo((byte) 0);
				int count = examPlaceWhilelistMapper.countByExample(example);
				if(count > 0){
					check = true;
					redisUtil.setAll(REDISKEY.EXAM_PLACE_WHITE + phoneNum,true,RedisKeys.EXPIRE.WEEK);
				}else {
					check = false;
					redisUtil.setAll(REDISKEY.EXAM_PLACE_WHITE + phoneNum,false,RedisKeys.EXPIRE.WEEK);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return check;
	}
	
	
	//************************************************************************************************************
	// 20161115为避免服务间调用的消耗，部分代码冗余
	
	public void updateExamPlaceOrder(ExamPlaceOrder record) {
		try {
			record.setMtime(new Date());
/*			redisUtil.setAll(
					RedisKeys.REDISKEY.EXAM_PLACE_ORDER + record.getOrderId(),
					record, RedisKeys.EXPIRE.WEEK); // 写表 与 写缓存 顺序是否需要调整？
*/			examPlaceOrderMapper.updateByPrimaryKeySelective(record);
			//清除订单缓存
			redisUtil.delete(RedisKeys.REDISKEY.EXAM_PLACE_ORDER + record.getOrderId());
			//ExamPlaceOrder order = getExamPlaceOrderOne(record.getOrderId());
			//清除是否已约课缓存
			redisUtil.delete(RedisKeys.REDISKEY.EXAM_PLACE_CLASS_BOOK +record.getClassId() + "." 
			+ record.getCoachId() + "." + record.getDrtype());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ExamPlace getExamPlaceById(Integer placeId) {
		ExamPlace ep = redisUtil.get(RedisKeys.REDISKEY.EXAM_PLACE + placeId);
		if (null == ep) {
			ep = examPlaceMapper.selectByPrimaryKey(placeId);
			redisUtil.setAll(RedisKeys.REDISKEY.EXAM_PLACE + placeId, ep,
					RedisKeys.EXPIRE.WEEK);
		}

		return ep;
	}

}