package com.lili.exam.manager.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.lili.coach.dto.Coach;
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
import com.lili.exam.dto.ExamVipCoach;
import com.lili.exam.manager.ExamPlaceClassManager;
import com.lili.exam.manager.ExamPlaceManager;
import com.lili.exam.manager.ExamPlaceOrderManager;
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
	ExamVipManagerImpl examVipManagerImpl;

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
					.andPstartGreaterThan(d0)
					.andStateNotEqualTo((byte) 1) //'排班状态：0-正常；1-已关闭；2-已延迟',
					.andPstartLessThan(d1);
			example.or(example.createCriteria()
					.andPlaceIdEqualTo(placeId)
					.andPendGreaterThan(d0)
					.andStateNotEqualTo((byte) 1) //'排班状态：0-正常；1-已关闭；2-已延迟',
					.andPendLessThan(d1));
			example.or(example.createCriteria()
					.andPlaceIdEqualTo(placeId)
					.andPstartLessThanOrEqualTo(d0)
					.andStateNotEqualTo((byte) 1) //'排班状态：0-正常；1-已关闭；2-已延迟',
					.andPendGreaterThanOrEqualTo(d1));
			List<ExamPlaceClass> list = examPlaceClassMapper.selectByExample(example);
			if(null != list && list.size()> 0){
				res.setCode(ResultCode.ERRORCODE.FAILED);
				res.setMsgInfo("排班时间冲突！");
				return res;
			}
			examPlaceClassMapper.insertSelective(record);
			//20161114有新增排班时，清除今天的排班查询
			String dayStr = new SimpleDateFormat("yyyy-MM-dd").format(d0);
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
							.andPstartLessThan(d1);
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
				if(null != orders && orders.size() > 0){
					for(ExamPlaceOrder order:orders){
						order.setRstart(d0);
						order.setRend(d1);
						order.setRemark(remark);
						//order.setMtime(new Date());
						updateExamPlaceOrder(order);
						
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
	        		ExamVipCoach examVipCoach=examVipManagerImpl.getExamVipCoach(coach.getPhoneNum(), ep.getSchoolId());
	        		
	        		//boolean isInner = (coach.getIsImport().intValue() == 1 && null != coach.getSchoolId() &&  null != ep.getSchoolId() && coach.getSchoolId().intValue() == ep.getSchoolId().intValue());
	        		//集团内部驾校的教练才属于内部教练
					//boolean isInner = (coach.getIsImport().intValue() == 1 && exam_inner_place.contains(coach.getSchoolId().toString()));
					// 集团内部同步的数据不准确，采用白名单的形式校验内部教练	20160929
					//boolean isInner = isInWhitelist(coach.getPhoneNum(),coach.getSchoolId());
	        		boolean isInner=(examVipCoach!=null);
	        		
					boolean isC1 = "1".equals(drtype.trim());
					byte drive = Byte.parseByte(drtype.trim());
					
	    			for(int i=0;i<classes.size();i++){
	    				ExamPlaceClass cls = classes.get(i);
	    				ExamPlaceClassVo d = new ExamPlaceClassVo();
	    				
	    				//大客户的具体约考情况
	    				String innerinfostr=cls.getInnerinfo();
	    				ExamInnerInfo innerinfo=null;
	    				if(innerinfostr!=null&&innerinfostr.length()>0)
	    					innerinfo= JSON.parse(innerinfostr, ExamInnerInfo.class);
	    				
	    				
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
	    		    			ExamVipBookInfo matchvip=null;
	    		    			if(innerinfo!=null){
	    		    				List<ExamVipBookInfo> vipbookinfo=innerinfo.getBookinfo();
            						
            						if(vipbookinfo!=null){
            							for(ExamVipBookInfo bi:vipbookinfo){
            								if(bi.getVipId()==examVipCoach.getVipId()){
            									matchvip=bi;
            									break;
            								}
            							}
            							
            						}
	    		    			}
	    		    			
	            				if(isC1){
	            					if(matchvip!=null){
            							d.setC(matchvip.getC1());
            							d.setCbook(matchvip.getC1book());
            						}else{
            							if(examVips!=null){
	            							for(ExamVip ex:examVips){
	            								if(examVipCoach.getVipId()==ex.getId()){
	            									d.setC(ex.getC1count());
	            									d.setCbook(0);
	            								}
	            							}
            							}
            						}
	            					//d.setC(cls.getC1inner());
	            					//d.setCbook(cls.getC1bookInner());
	            				}else{
	            					if(matchvip!=null){
            							d.setC(matchvip.getC2());
            							d.setCbook(matchvip.getC2book());
            						}else{
            							if(examVips!=null){
	            							for(ExamVip ex:examVips){
	            								if(examVipCoach.getVipId()==ex.getId()){
	            									d.setC(ex.getC2count());
	            									d.setCbook(0);
	            								}
	            							}
            							}
            						}
	            					
	            					//d.setC(cls.getC2inner());
	            					//d.setCbook(cls.getC2bookInner());
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