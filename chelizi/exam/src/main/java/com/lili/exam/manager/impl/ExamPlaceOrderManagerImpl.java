package com.lili.exam.manager.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.redisson.RedissonClient;
import org.redisson.core.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.lili.coach.dto.Car;
import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CarManager;
import com.lili.coach.manager.CoachManager;
import com.lili.common.constant.JpushConstant;
import com.lili.common.util.Page;
import com.lili.common.util.RandomUtil;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.exam.dto.ExamInnerInfo;
import com.lili.exam.dto.ExamPlace;
import com.lili.exam.dto.ExamPlaceClass;
import com.lili.exam.dto.ExamPlaceFavor;
import com.lili.exam.dto.ExamPlaceFavorKey;
import com.lili.exam.dto.ExamPlaceOrder;
import com.lili.exam.dto.ExamPlaceOrderExample;
import com.lili.exam.dto.ExamPlaceWhitelist;
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
import com.lili.pay.vo.PayVo;
import com.lili.pay.vo.PayWayType;

public class ExamPlaceOrderManagerImpl implements ExamPlaceOrderManager {
	private static Logger log = LoggerFactory
			.getLogger(ExamPlaceOrderManagerImpl.class);
	@Autowired
	private CoachManager coachManager;
	@Autowired
	private ExamPlaceManager examPlaceManager;
	@Autowired
	private ExamPlaceClassManager examPlaceClassManager;
	@Autowired
	private ExamPlaceClassMapper examPlaceClassMapper;
	@Autowired
	private RedissonClient redissonClient;
	@Autowired
	private ExamPlaceFavorMapper examPlaceFavorMapper;
	@Autowired
	private CarManager carManager;
	@Autowired
	private ExamPlaceOrderMapper examPlaceOrderMapper;
	@Autowired
	ExamPlaceMapper examPlaceMapper;
	@Autowired
	private MoneyManager moneyManager;
	@Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;
	@Value("${exam.innerPlace}")
	private String exam_inner_place = "1,4,5";
	@Value("${exam.refund.ratio}")
	private String exam_refund_ratio = "0.8";
	@Value("${exam.order.limit.inner}")
	private String exam_order_limit_inner = "1";
	@Value("${exam.order.limit.outer}")
	private String exam_order_limit_outer = "2";
	@Autowired
	private ExamPlaceWhitelistMapper examPlaceWhilelistMapper;
	@Autowired
	RedisUtil redisUtil;

	@Autowired
	ExamVipManagerImpl examVipManagerImpl;
	
	ExecutorService threadPool = Executors.newCachedThreadPool();

	// @Override
	// public ReqResult addExamPlaceOrder(String userId, String userType,
	// String classId, String drtype) {
	// ReqResult res = ReqResult.getSuccess();
	// List<String> orders = new ArrayList<String>();
	// List<ExamPlaceOrder> orderData = new ArrayList<ExamPlaceOrder>();
	// try {
	// RLock lock = null;
	// String[] clses = classId.split(",");
	// //（1）查询用户身份，是否是驾校内部教练
	// Coach coach = coachManager.getCoachInfo(Long.parseLong(userId));
	// for(int i=0;i<clses.length;i++){
	// try {
	// lock = redissonClient.getLock(RedisKeys.REDISKEY.LOCK_PRE + "placeOrder."
	// + clses[i]); //对同一个排班要锁定资源
	// boolean hasLock = lock.tryLock(1, 10, TimeUnit.SECONDS);//1s等待，10s超时，防止死锁
	// if (hasLock) {
	// ExamPlaceClass cls =
	// examPlaceClassMapper.selectByPrimaryKey(Integer.parseInt(clses[i]));
	// ExamPlace ep = examPlaceManager.getExamPlaceById(cls.getPlaceId());
	// String orderId = StringUtil.getOrderId();
	// // 如果是内部教练
	// boolean isInner = (null != coach.getSchoolId() && null !=
	// ep.getSchoolId() && coach.getSchoolId() == ep.getSchoolId());
	// if(isInner){
	// //（1）检查排班空位情况
	// if("1".equals(drtype.trim())){
	// if(cls.getC1() == cls.getC1book()){
	// res.setCode(ResultCode.ERRORCODE.FAILED);
	// res.setMsgInfo("排班已约满，无法再预约！");
	// return res;
	// }
	// //（2）增加位置使用
	// cls.setC1book(cls.getC1book() + 1);
	// cls.setC1bookInner(cls.getC1bookInner() +1);
	// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
	// // （3）生成订单
	// genExamPlaceOrderAsyn(orderId,ep,cls,coach,drtype,isInner);
	// }else{
	// if(cls.getC2() == cls.getC2book()){
	// res.setCode(ResultCode.ERRORCODE.FAILED);
	// res.setMsgInfo("排班已约满，无法再预约！");
	// return res;
	// }
	// //（2.1）增加位置使用
	// cls.setC2book(cls.getC2book() + 1);
	// cls.setC2bookInner(cls.getC2bookInner() +1);
	// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
	// // （3）生成订单
	// genExamPlaceOrderAsyn(orderId,ep,cls,coach,drtype,isInner);
	// }
	// }else {
	// //如果是外部教练
	// Date d0 = cls.getPstart();
	// int innerExpire = cls.getInnerExpire(); //内部失效时间，天
	// Calendar gdate = Calendar.getInstance();
	// gdate.add(gdate.DATE, innerExpire);
	// String aa = new SimpleDateFormat("yyyy-MM-dd").format(gdate.getTime()) +
	// " 23:59:59";
	// Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(aa);
	// if(d1.after(d0)){
	// //内部预留已失效
	// //（1）检查排班空位情况
	// if("1".equals(drtype.trim())){
	// if(cls.getC1() == cls.getC1book()){
	// res.setCode(ResultCode.ERRORCODE.FAILED);
	// res.setMsgInfo("排班已约满，无法再预约！");
	// return res;
	// }
	// //（2）增加位置使用
	// cls.setC1book(cls.getC1book() + 1);
	// cls.setC1bookOuter(cls.getC1bookOuter() +1);
	// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
	// // （3）生成订单
	// genExamPlaceOrderAsyn(orderId,ep,cls,coach,drtype,isInner);
	// }else{
	// if(cls.getC2() == cls.getC2book()){
	// res.setCode(ResultCode.ERRORCODE.FAILED);
	// res.setMsgInfo("排班已约满，无法再预约！");
	// return res;
	// }
	// //（2.1）增加位置使用
	// cls.setC2book(cls.getC2book() + 1);
	// cls.setC2bookOuter(cls.getC2bookOuter() +1);
	// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
	// // （3）生成订单
	// genExamPlaceOrderAsyn(orderId,ep,cls,coach,drtype,isInner);
	// }
	// }else {
	// //内部预留有效，外部教练使用外部空位
	// //（1）检查排班空位情况
	// if("1".equals(drtype.trim())){
	// if(cls.getC1outer() == cls.getC1bookOuter()){ //外部空位已排满，则不能继续排
	// res.setCode(ResultCode.ERRORCODE.FAILED);
	// res.setMsgInfo("排班已约满，无法再预约！");
	// return res;
	// }
	// //（2）增加位置使用
	// cls.setC1book(cls.getC1book() + 1);
	// cls.setC1bookOuter(cls.getC1bookOuter() +1);
	// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
	// // （3）生成订单
	// genExamPlaceOrderAsyn(orderId,ep,cls,coach,drtype,isInner);
	// }else{
	// if(cls.getC2outer() == cls.getC2bookOuter()){
	// res.setCode(ResultCode.ERRORCODE.FAILED);
	// res.setMsgInfo("排班已约满，无法再预约！");
	// return res;
	// }
	// //（2.1）增加位置使用
	// cls.setC2book(cls.getC2book() + 1);
	// cls.setC2bookOuter(cls.getC2bookOuter() +1);
	// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
	// // （3）生成订单
	// genExamPlaceOrderAsyn(orderId,ep,cls,coach,drtype,isInner);
	// }
	// }
	//
	// }
	// orders.add(orderId);
	// }else {
	// log.error("PayServiceImplNew error! studentId=" + userId +
	// " add pay LOCK ERROR.");
	// res.setCode(ResultCode.ERRORCODE.ORDER_LOCK);
	// res.setMsgInfo(ResultCode.ERRORINFO.ORDER_LOCK);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// if (lock != null) {
	// try {
	// lock.unlock();
	// } catch (Exception e) {
	// }
	// }
	// }
	//
	//
	// }
	// //res.setData(orders);
	// //20160919 客户端想直接获取订单信息，需要同步阻塞订单生成
	// while(true){
	// ExamPlaceOrderExample example = new ExamPlaceOrderExample();
	// example.createCriteria()
	// .andOrderIdIn(orders);
	// orderData = examPlaceOrderMapper.selectByExample(example); //SqlSession
	// if(null !=orderData && orderData.size() == orders.size()){
	// res.setData(orderData);
	// break;
	// }else {
	// try {
	// ExamPlaceOrder record = new ExamPlaceOrder();
	// record.setOrderId(orders.get(0));
	// examPlaceOrderMapper.updateByPrimaryKeySelective(record);
	// } catch (Exception e) {
	// //e.printStackTrace();
	// }
	// }
	// }
	//
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return res;
	// }
	//
	// /**
	// * 异步方式生成订单
	// * @param orderId
	// * @param ep
	// * @param cls
	// * @param coach
	// * @param drtype
	// * @param isInner
	// */
	// private void genExamPlaceOrderAsyn(final String orderId,final ExamPlace
	// ep, final ExamPlaceClass cls,
	// final Coach coach, final String drtype, final boolean isInner){
	// threadPool.execute(new Runnable() {
	//
	// @Override
	// public void run() {
	// try {
	// ExamPlaceOrder order = new ExamPlaceOrder();
	// //查询默认车辆信息
	// try {
	// Car car = carManager.getCarInfo(coach.getCoachCarId()); //获取车辆信息
	// if(null != car){
	// order.setCarNo(car.getCarNo());
	// }else {
	// log.error(coach.getName() + coach.getCoachId() +("教练没有车！！"));
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// order.setClassId(cls.getId());
	// order.setCoachId(coach.getCoachId());
	// order.setCoachImg(coach.getHeadIcon());
	// order.setCoachMobile(coach.getPhoneNum());
	// order.setCoachName(coach.getName());
	// order.setCtime(new Date());
	// order.setDrtype(Byte.parseByte(drtype));
	// order.setOrderId(orderId);
	// order.setPlaceId(ep.getId());
	// order.setPlaceName(ep.getName());
	// order.setSchool(ep.getSchool());
	// order.setType((byte)2); //科目类型从哪里取？？
	// order.setState((byte) 0);
	// order.setPstart(cls.getPstart());
	// order.setPend(cls.getPend());
	// order.setRstart(cls.getRstart());
	// order.setRend(cls.getRend());
	// // long diff = cls.getPend().getTime() - cls.getPstart().getTime();
	// // int hour = (int) (diff/(1000*60*60));
	// int hour = cls.getDuration();
	// order.setDuration(hour); //订单时长（小时）
	//
	// //本次订单获赠优惠 favorGen
	// //本次订单使用优惠 favorUse
	// //获赠学时剩余总计 favorLeft
	// int favorGen = (cls.getDuration()/cls.getFavorIn()) * cls.getFavorOut();
	// // 50= (2/2)*50
	// order.setFavorGen(favorGen);
	// order.setRemark("本次约考获得"+ep.getName()+"赠送"+ favorGen/100.00
	// +"个小时，获赠满"+cls.getFavorIn()+"个小时可以抵扣下次约考费用，考场资源有限，取消预约请谨慎。");
	//
	// ExamPlaceFavorKey key = new ExamPlaceFavorKey();
	// key.setPlaceId(cls.getPlaceId());
	// key.setUserId(coach.getCoachId());
	// ExamPlaceFavor favor = examPlaceFavorMapper.selectByPrimaryKey(key);
	// int favorUse = 0;
	// if(null != favor){
	// int course = favor.getFavorOut()/100;//已有的优惠课时
	// if(course >0){
	// //本次有可使用的优惠
	// favorUse = course * 100;
	// }else{
	// //本次没有可使用的优惠
	// }
	// favor.setDuration(favor.getDuration() + cls.getDuration());
	// favor.setFavorOut(favor.getFavorOut() - favorUse + favorGen);
	// //生成订单的时候先消费掉优惠。如果订单取消则再恢复。
	// examPlaceFavorMapper.updateByPrimaryKeySelective(favor);
	// order.setFavorUse(favorUse);
	// order.setFavorLeft(favor.getFavorOut());
	//
	// }else{
	// favor = new ExamPlaceFavor();
	// favor.setPlaceId(cls.getPlaceId());
	// favor.setUserId(coach.getCoachId());
	// favor.setFavorOut(favorGen); //本次获赠的学时，下次可使用，每次使用时减少。
	// favor.setDuration(cls.getDuration());//时长累计。只增不减。
	// examPlaceFavorMapper.insertSelective(favor);
	// order.setFavorUse(0);
	// order.setFavorLeft(favor.getFavorOut());
	// }
	// if(isInner){
	// order.setCoachType((byte)1); //教练类型：1-内部教练；2-外部教练
	// order.setPriceTotal(cls.getInnerPrice() * hour); //订单总价格
	// order.setCouponTotal(cls.getInnerPrice() * favorUse/100); //优惠抵扣价格
	// order.setPayTotal(cls.getInnerPrice() * (hour-favorUse/100)); //实际需要支付的价格
	// }else {
	// order.setCoachType((byte)2);
	// order.setPriceTotal(cls.getOuterPrice() * hour); //订单总价格
	// order.setCouponTotal(cls.getOuterPrice() * favorUse/100); //优惠抵扣价格
	// order.setPayTotal(cls.getOuterPrice() * (hour-favorUse/100)); //实际需要支付的价格
	// }
	//
	// examPlaceOrderMapper.insertSelective(order);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	//
	// }
	//
	// /* private int getFavorCanUse(int placeId,long userId,int duration,
	// boolean use) {
	// int discount = 0;
	// ExamPlaceFavorKey key = new ExamPlaceFavorKey();
	// key.setPlaceId(placeId);
	// key.setUserId(userId);
	// ExamPlaceFavor favor = examPlaceFavorMapper.selectByPrimaryKey(key);
	// if(null != favor && favor.getFavorOut() > 0){
	// int favorOut = favor.getFavorOut(); //课时 *100
	// if(favorOut >= duration *100){
	// discount = duration;
	// }else {
	// discount = favorOut /100;
	// }
	// if(use && discount >0){
	// favor.setFavorOut(favorOut - discount * 100);
	// examPlaceFavorMapper.updateByPrimaryKeySelective(favor);
	// }
	// }
	// return discount;
	// }*/
	// });
	// }

	// @Transactional
	@Override
	public ReqResult addExamPlaceOrder(String userId, String userType,
			String classId, String drtype, String carNo) {
		ReqResult res = ReqResult.getSuccess();
		List<ExamPlaceOrder> orderData = new ArrayList<ExamPlaceOrder>();
		// 中间生成优惠统计
		int favorGen = 0;
		int duration = 0;
		try {
			// 前置检查，如果有尚未支付的订单，则不能填写新订单
			ExamPlaceOrderExample example = new ExamPlaceOrderExample();
			example.createCriteria().andStateEqualTo((byte) 0) // '订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭',
					.andCoachIdEqualTo(Long.parseLong(userId));
			List<ExamPlaceOrder> oldData = examPlaceOrderMapper
					.selectByExample(example);
			if (null != oldData && oldData.size() > 0) {
				res.setCode(ResultCode.ERRORCODE.FAILED);
				res.setMsgInfo("您有约考场订单尚未支付，请先支付或取消！");
				return res;
			}
			String[] clses = classId.split(",");
			// （1）查询用户身份，是否是驾校内部教练
			Coach coach = coachManager.getCoachInfo(Long.parseLong(userId));
			// （2）获取车辆信息
			Car car = new Car();
			if (null == carNo || "".equals(carNo)) {
				List<Car> cars = carManager.getCarByCoachId(Long
						.parseLong(userId));
				String carNos = "";
				if (null == cars) {
					res.setCode(ResultCode.ERRORCODE.FAILED);
					res.setMsgInfo("请您选择车辆后再预约！");
					return res;
				}
				for (Car c : cars) {
					carNos = carNos + c.getCarNo();
				}
				car.setCarNo(carNos); // 20161009如果没有传车辆信息，则使用教练有的全部车辆
			} else {
				car.setCarNo(carNo);
			}
			// 如果是内部教练
			// boolean isInner = (coach.getIsImport().intValue() == 1 && null !=
			// coach.getSchoolId() && null != ep.getSchoolId() &&
			// coach.getSchoolId().intValue() == ep.getSchoolId().intValue());
			// 集团内部驾校的教练才属于内部教练
			// boolean isInner = (coach.getIsImport().intValue() == 1 &&
			// exam_inner_place.contains(coach.getSchoolId().toString()));
			// 集团内部同步的数据不准确，采用白名单的形式校验内部教练 20160929
			boolean isInner = isInWhitelist(coach.getPhoneNum(),
					coach.getSchoolId());
			boolean isC1 = "1".equals(drtype.trim());

			Integer order_limit_inner = Integer
					.parseInt(exam_order_limit_inner);
			Integer order_limit_outer = Integer
					.parseInt(exam_order_limit_outer);
			// 20161010 前置检查，内部一天限约一场；外部一天限约两场。
			if (isInner) {
				if (clses.length >= order_limit_inner + 1) {
					res.setCode(ResultCode.ERRORCODE.FAILED);
					res.setMsgInfo("教练每天限约" + order_limit_inner + "场！");
					return res;
				}
			} else {
				if (clses.length >= order_limit_outer + 1) {
					res.setCode(ResultCode.ERRORCODE.FAILED);
					res.setMsgInfo("教练每天限约" + order_limit_outer + "场！");
					return res;
				}
			}

			boolean hasPreChecked = false;

			// （3）虽然是多个不同的排班，却都是来自同一个考场的
			int placeId = 1;
			for (int i = 0; i < clses.length; i++) {
				RLock lock = null;
				try {
					lock = redissonClient.getLock(RedisKeys.REDISKEY.LOCK_PRE
							+ "placeOrder." + clses[i]); // 对同一个排班要锁定资源
					// 尝试加锁，最多等待2秒，上锁以后10秒自动解锁
					boolean hasLock = lock.tryLock(2, 10, TimeUnit.SECONDS);
					if (hasLock) {
						log.debug("Good-->got lock!" + Thread.currentThread().getName() 
								+";current classId=" + clses[i] 
										+";current userId=" + userId
										+";current time=" + System.currentTimeMillis());

						ExamPlaceClass cls = getExamPlaceClassOne(Integer
								.parseInt(clses[i])); // 20161110在资源查询之前就必须锁定！
						ExamPlace ep = getExamPlaceById(cls.getPlaceId());

						// 前置检查，如果已经预约了这个排班，则不能再约
						List<Byte> state1 = new ArrayList<Byte>();
						state1.add((byte) 4);
						state1.add((byte) 5);
						ExamPlaceOrderExample example1 = new ExamPlaceOrderExample();
						example1.createCriteria()
								.andClassIdEqualTo(Integer.parseInt(clses[i]))
								.andStateNotIn(state1) // '订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭',
								.andCoachIdEqualTo(Long.parseLong(userId));
						int oldData1 = examPlaceOrderMapper
								.countByExample(example1);
						if (oldData1 > 0) {
							res.setCode(ResultCode.ERRORCODE.FAILED);
							res.setMsgInfo("您已预约该场次，不能重复预约！");
							return res;
						}
						if (!hasPreChecked) {
							// 20161010 前置检查，内部一天限约一场；外部一天限约两场。
							ExamPlaceOrderExample example2 = new ExamPlaceOrderExample();
							String aa2 = new SimpleDateFormat("yyyy-MM-dd")
									.format(cls.getPstart()) + " 00:00:00";
							String bb2 = new SimpleDateFormat("yyyy-MM-dd")
									.format(cls.getPstart()) + " 23:59:59";
							Date date2 = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss").parse(aa2);
							Date date3 = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss").parse(bb2);
							example2.createCriteria().andStateNotIn(state1)
									// '订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭',
									.andCoachIdEqualTo(Long.parseLong(userId))
									.andPstartBetween(date2, date3);
							int todayOrderCount = examPlaceOrderMapper
									.countByExample(example2);
							if (isInner) {    
								if (todayOrderCount >= order_limit_inner) {
									res.setCode(ResultCode.ERRORCODE.FAILED);
									res.setMsgInfo("您该日预约次数已达到上限，不能再预约！");
									return res;
								}
								// 如果已经约了一场，再一次连约两场有个坑
								if (todayOrderCount + clses.length >= order_limit_inner + 1) {
									res.setCode(ResultCode.ERRORCODE.FAILED);
									res.setMsgInfo("教练每天限约" + order_limit_inner
											+ "场！");
									return res;
								}
							} else {
								if (todayOrderCount >= order_limit_outer) {
									res.setCode(ResultCode.ERRORCODE.FAILED);
									res.setMsgInfo("您该日预约次数已达到上限，不能再预约！");
									return res;
								}
								// 如果已经约了一场，再一次连约两场有个坑
								if (todayOrderCount + clses.length >= order_limit_outer + 1) {
									res.setCode(ResultCode.ERRORCODE.FAILED);
									res.setMsgInfo("教练每天限约" + order_limit_outer
											+ "场！");
									return res;
								}
							}
							hasPreChecked = true;
						}

						// redisUtil.setAll(RedisKeys.REDISKEY.LOCK_PRE +
						// "placeOrder." + clses[i],"working",120);
						String orderId = StringUtil.getOrderId();
						placeId = ep.getId();

						// 20161005 需求变更：内部教练和外部教练都受到内部预留空位的约束
						Date d0 = cls.getPstart();
						int innerExpire = cls.getInnerExpire(); // 内部失效时间，天
						Calendar gdate = Calendar.getInstance();
						gdate.add(gdate.DATE, innerExpire);
						String aa = new SimpleDateFormat("yyyy-MM-dd")
								.format(gdate.getTime()) + " 23:59:59";
						Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(aa);
						boolean isExpire = d1.after(d0);

						// 20161009 如果已经到了计划上课时间，则不允许再预约！
						// 20161122考场方要求，在实际上课时间结束前，都是允许预约的！
						Date now = new Date();
						Date endTime = cls.getRend();
						if (now.after(endTime)) {
							res.setCode(ResultCode.ERRORCODE.FAILED);
							res.setMsgInfo("该排班已过期，不能预约！");
							return res;
						}

						if (isInner) { // 内部教练
							if (isExpire) { // 内部预留已失效
								if (isC1) {
									// （1）检查排班空位情况 内部教练-预留失效-c1
									if (cls.getC1() <= cls.getC1book()) {
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("排班已约满，无法再预约！");
										return res;
									}
									// （2）增加位置使用
									cls.setC1book(cls.getC1book() + 1);
									cls.setC1bookInner(cls.getC1bookInner() + 1);
									// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
									//examPlaceClassManager.updateExamPlaceClass(cls);
									updateExamPlaceClass(cls);
									// （3）生成订单
									ExamPlaceOrder genData = genExamPlaceOrderSyn(
											orderId, ep, cls, coach, drtype,
											isInner, car, favorGen);
									favorGen = favorGen + genData.getFavorGen();
									duration = duration + genData.getDuration();
									orderData.add(genData);
								} else {
									if (cls.getC2() <= cls.getC2book()) {
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("排班已约满，无法再预约！");
										return res;
									}
									// （2.1）增加位置使用
									cls.setC2book(cls.getC2book() + 1);
									cls.setC2bookInner(cls.getC2bookInner() + 1);
									// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
									//examPlaceClassManager.updateExamPlaceClass(cls);
									updateExamPlaceClass(cls);
									// （3）生成订单
									ExamPlaceOrder genData = genExamPlaceOrderSyn(
											orderId, ep, cls, coach, drtype,
											isInner, car, favorGen);
									favorGen = favorGen + genData.getFavorGen();
									duration = duration + genData.getDuration();
									orderData.add(genData);
								}
							} else { //内部预留没实效
								if (isC1) {
									// （1）检查排班空位情况 内部教练-预留有效-c1
									if (cls.getC1inner() <= cls
											.getC1bookInner()) {
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("排班已约满，无法再预约！");
										return res;
									}
									// （2）增加位置使用
									cls.setC1book(cls.getC1book() + 1);
									cls.setC1bookInner(cls.getC1bookInner() + 1);
									// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
									//examPlaceClassManager.updateExamPlaceClass(cls);
									updateExamPlaceClass(cls);
									// （3）生成订单
									ExamPlaceOrder genData = genExamPlaceOrderSyn(
											orderId, ep, cls, coach, drtype,
											isInner, car, favorGen);
									favorGen = favorGen + genData.getFavorGen();
									duration = duration + genData.getDuration();
									orderData.add(genData);
								} else {
									if (cls.getC2inner() <= cls
											.getC2bookInner()) {
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("排班已约满，无法再预约！");
										return res;
									}
									// （2.1）增加位置使用
									cls.setC2book(cls.getC2book() + 1);
									cls.setC2bookInner(cls.getC2bookInner() + 1);
									// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
									//examPlaceClassManager.updateExamPlaceClass(cls);
									updateExamPlaceClass(cls);
									// （3）生成订单
									ExamPlaceOrder genData = genExamPlaceOrderSyn(
											orderId, ep, cls, coach, drtype,
											isInner, car, favorGen);
									favorGen = favorGen + genData.getFavorGen();
									duration = duration + genData.getDuration();
									orderData.add(genData);
								}

							}
						} else { // 外部教练
							if (isExpire) { // 内部预留已失效
								if (isC1) { // c1
									// （1）检查排班空位情况
									if (cls.getC1() <= cls.getC1book()) {
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("排班已约满，无法再预约！");
										return res;
									}
									// （2）增加位置使用
									cls.setC1book(cls.getC1book() + 1);
									cls.setC1bookOuter(cls.getC1bookOuter() + 1);
									// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
									//examPlaceClassManager.updateExamPlaceClass(cls);
									updateExamPlaceClass(cls);
									// （3）生成订单
									ExamPlaceOrder genData = genExamPlaceOrderSyn(
											orderId, ep, cls, coach, drtype,
											isInner, car, favorGen);
									favorGen = favorGen + genData.getFavorGen();
									duration = duration + genData.getDuration();
									orderData.add(genData);
								} else { // c2
									if (cls.getC2() <= cls.getC2book()) {
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("排班已约满，无法再预约！");
										return res;
									}
									// （2.1）增加位置使用
									cls.setC2book(cls.getC2book() + 1);
									cls.setC2bookOuter(cls.getC2bookOuter() + 1);
									// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
									//examPlaceClassManager.updateExamPlaceClass(cls);
									updateExamPlaceClass(cls);
									// （3）生成订单
									ExamPlaceOrder genData = genExamPlaceOrderSyn(
											orderId, ep, cls, coach, drtype,
											isInner, car, favorGen);
									favorGen = favorGen + genData.getFavorGen();
									duration = duration + genData.getDuration();
									orderData.add(genData);
								}
							} else { // 内部预留有效
								if (isC1) { // c1
									// （1）检查排班空位情况
									if (cls.getC1outer() <= cls
											.getC1bookOuter()) { // 外部空位已排满，则不能继续排
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("排班已约满，无法再预约！");
										return res;
									}
									// （2）增加位置使用
									cls.setC1book(cls.getC1book() + 1);
									cls.setC1bookOuter(cls.getC1bookOuter() + 1);
									// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
									//examPlaceClassManager.updateExamPlaceClass(cls);
									updateExamPlaceClass(cls);
									// （3）生成订单
									ExamPlaceOrder genData = genExamPlaceOrderSyn(
											orderId, ep, cls, coach, drtype,
											isInner, car, favorGen);
									favorGen = favorGen + genData.getFavorGen();
									duration = duration + genData.getDuration();
									orderData.add(genData);
								} else { // c2
									if (cls.getC2outer() <= cls
											.getC2bookOuter()) {
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("排班已约满，无法再预约！");
										return res;
									}
									// （2.1）增加位置使用
									cls.setC2book(cls.getC2book() + 1);
									cls.setC2bookOuter(cls.getC2bookOuter() + 1);
									// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
									//examPlaceClassManager.updateExamPlaceClass(cls);
									updateExamPlaceClass(cls);
									// （3）生成订单
									ExamPlaceOrder genData = genExamPlaceOrderSyn(
											orderId, ep, cls, coach, drtype,
											isInner, car, favorGen);
									favorGen = favorGen + genData.getFavorGen();
									duration = duration + genData.getDuration();
									orderData.add(genData);
								}
							}

						}
						// redisUtil.delete(RedisKeys.REDISKEY.LOCK_PRE +
						// "placeOrder." + clses[i]);
					} else {
						log.debug("Good-->got no lock!" + Thread.currentThread().getName() 
								+";current classId=" + clses[i] 
										+";current userId=" + userId
										+";current time=" + System.currentTimeMillis());
						res.setCode(ResultCode.ERRORCODE.ORDER_LOCK);
						res.setMsgInfo("当前车道已有教练正在排队，请稍后再试！");
						// 20161108 争抢坑位的时候，坑位忙则直接返回，让用户重新发起
						return res;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (lock != null) {
						try {
							lock.unlock();
							log.debug("Good-->now unlock!" + Thread.currentThread().getName() 
									+";current classId=" + clses[i] 
											+";current userId=" + userId
											+";current time=" + System.currentTimeMillis());
						} catch (Exception e) {
						}
					}
					// redisUtil.delete(RedisKeys.REDISKEY.LOCK_PRE +
					// "placeOrder." + clses[i]);
				}

			}
			// 增加本批订单所生成的优惠 //20160926改为订单完成后才赠送
			// ExamPlaceFavorKey key = new ExamPlaceFavorKey();
			// key.setPlaceId(placeId);
			// key.setUserId(coach.getCoachId());
			// ExamPlaceFavor favor =
			// examPlaceFavorMapper.selectByPrimaryKey(key);
			// if(null != favor){
			// favor.setDuration(favor.getDuration() + duration);
			// favor.setFavorOut(favor.getFavorOut() + favorGen);
			// examPlaceFavorMapper.updateByPrimaryKeySelective(favor);
			// }else {
			// favor = new ExamPlaceFavor();
			// favor.setPlaceId(placeId);
			// favor.setUserId(coach.getCoachId());
			// favor.setDuration(duration);
			// favor.setFavorOut(favorGen);
			// examPlaceFavorMapper.insertSelective(favor);
			// }

			res.setData(orderData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 提供车模式
	 */
	@Override
	public ReqResult addCarModelExamPlaceOrder(String userId, String userType,
			String classId, String drtype,String placeId) {
		ReqResult res = ReqResult.getSuccess();
		List<ExamPlaceOrder> orderData = new ArrayList<ExamPlaceOrder>();
		// 中间生成优惠统计
		int favorGen = 0;
		int duration = 0;
		try {
			// 前置检查，如果有尚未支付的订单，则不能填写新订单
			ExamPlaceOrderExample example = new ExamPlaceOrderExample();
			example.createCriteria().andStateEqualTo((byte) 0) // '订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭',
					.andCoachIdEqualTo(Long.parseLong(userId));
			List<ExamPlaceOrder> oldData = examPlaceOrderMapper
					.selectByExample(example);
			if (null != oldData && oldData.size() > 0) {
				res.setCode(ResultCode.ERRORCODE.FAILED);
				res.setMsgInfo("您有约考场订单尚未支付，请先支付或取消！");
				return res;
			}
			String[] clses = classId.split(",");
			// （1）查询用户身份，是否是驾校内部教练
			Coach coach = coachManager.getCoachInfo(Long.parseLong(userId));
			
			// 如果是内部教练
			// boolean isInner = (coach.getIsImport().intValue() == 1 && null !=
			// coach.getSchoolId() && null != ep.getSchoolId() &&
			// coach.getSchoolId().intValue() == ep.getSchoolId().intValue());
			// 集团内部驾校的教练才属于内部教练
			// boolean isInner = (coach.getIsImport().intValue() == 1 &&
			// exam_inner_place.contains(coach.getSchoolId().toString()));
			// 集团内部同步的数据不准确，采用白名单的形式校验内部教练 20160929
//			boolean isInner = isInWhitelist(coach.getPhoneNum(),
//					coach.getSchoolId());
			ExamPlace ep = getExamPlaceById(Integer.parseInt(placeId));
			ExamVipCoach examVipCoach=examVipManagerImpl.getExamVipCoach(coach.getPhoneNum(), ep.getSchoolId());
			boolean isInner=(examVipCoach!=null);
			
			boolean isC1 = "1".equals(drtype.trim());

			Integer order_limit_inner = Integer
					.parseInt(exam_order_limit_inner);
			Integer order_limit_outer = Integer
					.parseInt(exam_order_limit_outer);
			// 20161010 前置检查，内部一天限约一场；外部一天限约两场。
			if (isInner) {
				if (clses.length >= order_limit_inner + 1) {
					res.setCode(ResultCode.ERRORCODE.FAILED);
					res.setMsgInfo("教练每天限约" + order_limit_inner + "场！");
					return res;
				}
			} else {
				if (clses.length >= order_limit_outer + 1) {
					res.setCode(ResultCode.ERRORCODE.FAILED);
					res.setMsgInfo("教练每天限约" + order_limit_outer + "场！");
					return res;
				}
			}

			boolean hasPreChecked = false;

			// （3）虽然是多个不同的排班，却都是来自同一个考场的
			//int placeId = 1;
			for (int i = 0; i < clses.length; i++) {
				RLock lock = null;
				try {
					lock = redissonClient.getLock(RedisKeys.REDISKEY.LOCK_PRE
							+ "placeOrder." + clses[i]); // 对同一个排班要锁定资源
					// 尝试加锁，最多等待2秒，上锁以后10秒自动解锁
					boolean hasLock = lock.tryLock(2, 10, TimeUnit.SECONDS);
					if (hasLock) {
						log.debug("Good-->got lock!" + Thread.currentThread().getName() 
								+";current classId=" + clses[i] 
										+";current userId=" + userId
										+";current time=" + System.currentTimeMillis());

						ExamPlaceClass cls = getExamPlaceClassOne(Integer
								.parseInt(clses[i])); // 20161110在资源查询之前就必须锁定！
						//ExamPlace ep = getExamPlaceById(cls.getPlaceId());

						// 前置检查，如果已经预约了这个排班，则不能再约
						List<Byte> state1 = new ArrayList<Byte>();
						state1.add((byte) 4);
						state1.add((byte) 5);
						ExamPlaceOrderExample example1 = new ExamPlaceOrderExample();
						example1.createCriteria()
								.andClassIdEqualTo(Integer.parseInt(clses[i]))
								.andStateNotIn(state1) // '订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭',
								.andCoachIdEqualTo(Long.parseLong(userId));
						int oldData1 = examPlaceOrderMapper
								.countByExample(example1);
						if (oldData1 > 0) {
							res.setCode(ResultCode.ERRORCODE.FAILED);
							res.setMsgInfo("您已预约该场次，不能重复预约！");
							return res;
						}
						if (!hasPreChecked) {
							// 20161010 前置检查，内部一天限约一场；外部一天限约两场。
							ExamPlaceOrderExample example2 = new ExamPlaceOrderExample();
							String aa2 = new SimpleDateFormat("yyyy-MM-dd")
									.format(cls.getPstart()) + " 00:00:00";
							String bb2 = new SimpleDateFormat("yyyy-MM-dd")
									.format(cls.getPstart()) + " 23:59:59";
							Date date2 = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss").parse(aa2);
							Date date3 = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss").parse(bb2);
							example2.createCriteria().andStateNotIn(state1)
									// '订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭',
									.andCoachIdEqualTo(Long.parseLong(userId))
									.andPstartBetween(date2, date3);
							int todayOrderCount = examPlaceOrderMapper
									.countByExample(example2);
							if (isInner) {    
								if (todayOrderCount >= order_limit_inner) {
									res.setCode(ResultCode.ERRORCODE.FAILED);
									res.setMsgInfo("您该日预约次数已达到上限，不能再预约！");
									return res;
								}
								// 如果已经约了一场，再一次连约两场有个坑
								if (todayOrderCount + clses.length >= order_limit_inner + 1) {
									res.setCode(ResultCode.ERRORCODE.FAILED);
									res.setMsgInfo("教练每天限约" + order_limit_inner
											+ "场！");
									return res;
								}
							} else {
								if (todayOrderCount >= order_limit_outer) {
									res.setCode(ResultCode.ERRORCODE.FAILED);
									res.setMsgInfo("您该日预约次数已达到上限，不能再预约！");
									return res;
								}
								// 如果已经约了一场，再一次连约两场有个坑
								if (todayOrderCount + clses.length >= order_limit_outer + 1) {
									res.setCode(ResultCode.ERRORCODE.FAILED);
									res.setMsgInfo("教练每天限约" + order_limit_outer
											+ "场！");
									return res;
								}
							}
							hasPreChecked = true;
						}

						// redisUtil.setAll(RedisKeys.REDISKEY.LOCK_PRE +
						// "placeOrder." + clses[i],"working",120);
						String orderId = StringUtil.getOrderId();
						//placeId = ep.getId();

						// 20161005 需求变更：内部教练和外部教练都受到内部预留空位的约束
						Date d0 = cls.getPstart();
						int innerExpire = cls.getInnerExpire(); // 内部失效时间，天
						Calendar gdate = Calendar.getInstance();
						gdate.add(gdate.DATE, innerExpire);
						String aa = new SimpleDateFormat("yyyy-MM-dd")
								.format(gdate.getTime()) + " 23:59:59";
						Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(aa);
						boolean isExpire = d1.after(d0);

						// 20161009 如果已经到了计划上课时间，则不允许再预约！
						// 20161122考场方要求，在实际上课时间结束前，都是允许预约的！
						Date now = new Date();
						Date endTime = cls.getRend();
						if (now.after(endTime)) {
							res.setCode(ResultCode.ERRORCODE.FAILED);
							res.setMsgInfo("该排班已过期，不能预约！");
							return res;
						}
						
						if (isInner) { // 内部教练
							if (isExpire) { // 内部预留已失效
								if (isC1) {
									// （1）检查排班空位情况 内部教练-预留失效-c1
									if (cls.getC1() <= cls.getC1book()) {
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("排班已约满，无法再预约！");
										return res;
									}
									
									List<Car> allcars=carManager.getCarBySchoolId(ep.getSchoolId());;
									List<String> bookcars=null;
									List<ExamVipBookInfo> bookinfo=null;
									
									//获取排版当前的大客户预定情况
									String innerinfostr=cls.getInnerinfo();
				    				ExamInnerInfo innerinfo=null;
				    				if(innerinfostr!=null&&innerinfostr.length()>0)
				    					innerinfo= JSON.parseObject(innerinfostr, ExamInnerInfo.class);
									if(innerinfo!=null){
										bookcars=innerinfo.getBookcar();
									}else{
										//初始化一个对象进行保存
										innerinfo=new ExamInnerInfo();
									}
									//获取一辆空闲车
									Car car=pickcar(allcars,bookcars,drtype);
									
									if(car==null){
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("车辆已经约满，无法再预约！");
										return res;
									}
									
									//添加一辆预定车到列表
									innerinfo.getBookcar().add(car.getCarNo());
									cls.setInnerinfo(JSON.toJSONString(innerinfo));
									
									// （2）增加位置使用
									cls.setC1book(cls.getC1book() + 1);
									cls.setC1bookInner(cls.getC1bookInner() + 1);
									// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
									//examPlaceClassManager.updateExamPlaceClass(cls);
									updateExamPlaceClass(cls);
									// （3）生成订单
									
									ExamPlaceOrder genData = genExamPlaceOrderSyn(
											orderId, ep, cls, coach, drtype,
											isInner, car, favorGen);
									favorGen = favorGen + genData.getFavorGen();
									duration = duration + genData.getDuration();
									orderData.add(genData);
								} else {
									if (cls.getC2() <= cls.getC2book()) {
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("排班已约满，无法再预约！");
										return res;
									}
									
									List<Car> allcars=carManager.getCarBySchoolId(ep.getSchoolId());;
									List<String> bookcars=null;
									List<ExamVipBookInfo> bookinfo=null;
									
									//获取排版当前的大客户预定情况
									String innerinfostr=cls.getInnerinfo();
				    				ExamInnerInfo innerinfo=null;
				    				if(innerinfostr!=null&&innerinfostr.length()>0)
				    					innerinfo= JSON.parseObject(innerinfostr, ExamInnerInfo.class);
									if(innerinfo!=null){
										bookcars=innerinfo.getBookcar();
									}else{
										//初始化一个对象进行保存
										innerinfo=new ExamInnerInfo();
									}
									//获取一辆空闲车
									Car car=pickcar(allcars,bookcars,drtype);
									
									if(car==null){
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("车辆已经约满，无法再预约！");
										return res;
									}
									
									//添加一辆预定车到列表
									innerinfo.getBookcar().add(car.getCarNo());
									cls.setInnerinfo(JSON.toJSONString(innerinfo));
									
									// （2.1）增加位置使用
									cls.setC2book(cls.getC2book() + 1);
									cls.setC2bookInner(cls.getC2bookInner() + 1);
									// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
									//examPlaceClassManager.updateExamPlaceClass(cls);
									updateExamPlaceClass(cls);
									// （3）生成订单
									ExamPlaceOrder genData = genExamPlaceOrderSyn(
											orderId, ep, cls, coach, drtype,
											isInner, car, favorGen);
									favorGen = favorGen + genData.getFavorGen();
									duration = duration + genData.getDuration();
									orderData.add(genData);
								}
							} else { //内部预留没实效
								if (isC1) {
									
									// （1）检查排班空位情况 内部教练-预留有效-c1
									if (cls.getC1inner() <= cls
											.getC1bookInner()) {
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("排班已约满，无法再预约！");
										return res;
									}
									
									List<Car> allcars=carManager.getCarBySchoolId(ep.getSchoolId());
									List<String> bookcars=null;
									List<ExamVipBookInfo> bookinfo=null;
									
									//获取排版当前的大客户预定情况
									String innerinfostr=cls.getInnerinfo();
				    				ExamInnerInfo innerinfo=null;
				    				if(innerinfostr!=null&&innerinfostr.length()>0)
				    					innerinfo= JSON.parseObject(innerinfostr, ExamInnerInfo.class);
									if(innerinfo!=null){
										bookcars=innerinfo.getBookcar();
									}else{
										//初始化一个对象进行保存
										innerinfo=new ExamInnerInfo();
									}
									
									
									ExamVip examVip=examVipManagerImpl.getExamVipOne(examVipCoach.getVipId());
									bookinfo=innerinfo.getBookinfo();
									ExamVipBookInfo examVipBookInfo=null;
									for(ExamVipBookInfo bi:bookinfo){
										if(bi.getVipId()==examVip.getId()){
											examVipBookInfo=bi;
											break;
										}
									}
									if(examVipBookInfo==null){
										examVipBookInfo=new ExamVipBookInfo();
										examVipBookInfo.setC1(examVip.getC1count());
										examVipBookInfo.setC1book(0);
										examVipBookInfo.setC2(examVip.getC2count());
										examVipBookInfo.setC2book(0);
										innerinfo.getBookinfo().add(examVipBookInfo);
									}
									
									
									
									if(examVipBookInfo.getC1()<=examVipBookInfo.getC1book()){
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("排班已约满，无法再预约！");
										return res;
									}
									
									//获取一辆空闲车
									Car car=pickcar(allcars,bookcars,drtype);
									
									if(car==null){
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("车辆已经约满，无法再预约！");
										return res;
									}
									
									//添加一辆预定车到列表
									innerinfo.getBookcar().add(car.getCarNo());
									examVipBookInfo.setC1book(examVipBookInfo.getC1book()+1);
									
									cls.setInnerinfo(JSON.toJSONString(innerinfo));
									// （2）增加位置使用
									cls.setC1book(cls.getC1book() + 1);
									cls.setC1bookInner(cls.getC1bookInner() + 1);
									// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
									//examPlaceClassManager.updateExamPlaceClass(cls);
									updateExamPlaceClass(cls);
									// （3）生成订单
									ExamPlaceOrder genData = genExamPlaceOrderSyn(
											orderId, ep, cls, coach, drtype,
											isInner, car, favorGen);
									favorGen = favorGen + genData.getFavorGen();
									duration = duration + genData.getDuration();
									orderData.add(genData);
								} else {//c2
									if (cls.getC2inner() <= cls
											.getC2bookInner()) {
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("排班已约满，无法再预约！");
										return res;
									}
									
									List<Car> allcars=carManager.getCarBySchoolId(ep.getSchoolId());
									List<String> bookcars=null;
									List<ExamVipBookInfo> bookinfo=null;
									
									//获取排版当前的大客户预定情况
									String innerinfostr=cls.getInnerinfo();
				    				ExamInnerInfo innerinfo=null;
				    				if(innerinfostr!=null&&innerinfostr.length()>0)
				    					innerinfo= JSON.parseObject(innerinfostr, ExamInnerInfo.class);
									if(innerinfo!=null){
										bookcars=innerinfo.getBookcar();
									}else{
										//初始化一个对象进行保存
										innerinfo=new ExamInnerInfo();
									}
									
									
									ExamVip examVip=examVipManagerImpl.getExamVipOne(examVipCoach.getVipId());
									bookinfo=innerinfo.getBookinfo();
									ExamVipBookInfo examVipBookInfo=null;
									for(ExamVipBookInfo bi:bookinfo){
										if(bi.getVipId()==examVip.getId()){
											examVipBookInfo=bi;
											break;
										}
									}
									if(examVipBookInfo==null){
										examVipBookInfo=new ExamVipBookInfo();
										examVipBookInfo.setC1(examVip.getC1count());
										examVipBookInfo.setC1book(0);
										examVipBookInfo.setC2(examVip.getC2count());
										examVipBookInfo.setC2book(0);
										innerinfo.getBookinfo().add(examVipBookInfo);
									}
									
									
									
									if(examVipBookInfo.getC2()<=examVipBookInfo.getC2book()){
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("排班已约满，无法再预约！");
										return res;
									}
									
									//获取一辆空闲车
									Car car=pickcar(allcars,bookcars,drtype);
									
									if(car==null){
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("车辆已经约满，无法再预约！");
										return res;
									}
									
									//添加一辆预定车到列表
									innerinfo.getBookcar().add(car.getCarNo());
									examVipBookInfo.setC2book(examVipBookInfo.getC2book()+1);
									
									cls.setInnerinfo(JSON.toJSONString(innerinfo));
									
									// （2.1）增加位置使用
									cls.setC2book(cls.getC2book() + 1);
									cls.setC2bookInner(cls.getC2bookInner() + 1);
									// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
									//examPlaceClassManager.updateExamPlaceClass(cls);
									updateExamPlaceClass(cls);
									// （3）生成订单
									ExamPlaceOrder genData = genExamPlaceOrderSyn(
											orderId, ep, cls, coach, drtype,
											isInner, car, favorGen);
									favorGen = favorGen + genData.getFavorGen();
									duration = duration + genData.getDuration();
									orderData.add(genData);
								}

							}
						} else { // 外部教练
							if (isExpire) { // 内部预留已失效
								if (isC1) { // c1
									// （1）检查排班空位情况
									if (cls.getC1() <= cls.getC1book()) {
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("排班已约满，无法再预约！");
										return res;
									}
									
									List<Car> allcars=carManager.getCarBySchoolId(ep.getSchoolId());;
									List<String> bookcars=null;
									List<ExamVipBookInfo> bookinfo=null;
									
									//获取排版当前的大客户预定情况
									String innerinfostr=cls.getInnerinfo();
				    				ExamInnerInfo innerinfo=null;
				    				if(innerinfostr!=null&&innerinfostr.length()>0)
				    					innerinfo= JSON.parseObject(innerinfostr, ExamInnerInfo.class);
									if(innerinfo!=null){
										bookcars=innerinfo.getBookcar();
									}else{
										//初始化一个对象进行保存
										innerinfo=new ExamInnerInfo();
									}
									//获取一辆空闲车
									Car car=pickcar(allcars,bookcars,drtype);
									
									if(car==null){
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("车辆已经约满，无法再预约！");
										return res;
									}
									
									//添加一辆预定车到列表
									innerinfo.getBookcar().add(car.getCarNo());
									cls.setInnerinfo(JSON.toJSONString(innerinfo));
									
									// （2）增加位置使用
									cls.setC1book(cls.getC1book() + 1);
									cls.setC1bookOuter(cls.getC1bookOuter() + 1);
									// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
									//examPlaceClassManager.updateExamPlaceClass(cls);
									updateExamPlaceClass(cls);
									// （3）生成订单
									ExamPlaceOrder genData = genExamPlaceOrderSyn(
											orderId, ep, cls, coach, drtype,
											isInner, car, favorGen);
									favorGen = favorGen + genData.getFavorGen();
									duration = duration + genData.getDuration();
									orderData.add(genData);
								} else { // c2
									if (cls.getC2() <= cls.getC2book()) {
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("排班已约满，无法再预约！");
										return res;
									}
									
									List<Car> allcars=carManager.getCarBySchoolId(ep.getSchoolId());;
									List<String> bookcars=null;
									List<ExamVipBookInfo> bookinfo=null;
									
									//获取排版当前的大客户预定情况
									String innerinfostr=cls.getInnerinfo();
				    				ExamInnerInfo innerinfo=null;
				    				if(innerinfostr!=null&&innerinfostr.length()>0)
				    					innerinfo= JSON.parseObject(innerinfostr, ExamInnerInfo.class);
									if(innerinfo!=null){
										bookcars=innerinfo.getBookcar();
									}else{
										//初始化一个对象进行保存
										innerinfo=new ExamInnerInfo();
									}
									//获取一辆空闲车
									Car car=pickcar(allcars,bookcars,drtype);
									
									if(car==null){
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("车辆已经约满，无法再预约！");
										return res;
									}
									//添加一辆预定车到列表
									innerinfo.getBookcar().add(car.getCarNo());
									cls.setInnerinfo(JSON.toJSONString(innerinfo));
									
									// （2.1）增加位置使用
									cls.setC2book(cls.getC2book() + 1);
									cls.setC2bookOuter(cls.getC2bookOuter() + 1);
									// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
									//examPlaceClassManager.updateExamPlaceClass(cls);
									updateExamPlaceClass(cls);
									// （3）生成订单
									ExamPlaceOrder genData = genExamPlaceOrderSyn(
											orderId, ep, cls, coach, drtype,
											isInner, car, favorGen);
									favorGen = favorGen + genData.getFavorGen();
									duration = duration + genData.getDuration();
									orderData.add(genData);
								}
							} else { // 内部预留有效
								if (isC1) { // c1
									// （1）检查排班空位情况
									if (cls.getC1outer() <= cls
											.getC1bookOuter()) { // 外部空位已排满，则不能继续排
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("排班已约满，无法再预约！");
										return res;
									}
									
									List<Car> allcars=carManager.getCarBySchoolId(ep.getSchoolId());;
									List<String> bookcars=null;
									List<ExamVipBookInfo> bookinfo=null;
									
									//获取排版当前的大客户预定情况
									String innerinfostr=cls.getInnerinfo();
				    				ExamInnerInfo innerinfo=null;
				    				if(innerinfostr!=null&&innerinfostr.length()>0)
				    					innerinfo= JSON.parseObject(innerinfostr, ExamInnerInfo.class);
									if(innerinfo!=null){
										bookcars=innerinfo.getBookcar();
									}else{
										//初始化一个对象进行保存
										innerinfo=new ExamInnerInfo();
									}
									//获取一辆空闲车
									Car car=pickcar(allcars,bookcars,drtype);
									
									if(car==null){
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("车辆已经约满，无法再预约！");
										return res;
									}
									//添加一辆预定车到列表
									innerinfo.getBookcar().add(car.getCarNo());
									cls.setInnerinfo(JSON.toJSONString(innerinfo));
									
									// （2）增加位置使用
									cls.setC1book(cls.getC1book() + 1);
									cls.setC1bookOuter(cls.getC1bookOuter() + 1);
									// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
									//examPlaceClassManager.updateExamPlaceClass(cls);
									updateExamPlaceClass(cls);
									// （3）生成订单
									ExamPlaceOrder genData = genExamPlaceOrderSyn(
											orderId, ep, cls, coach, drtype,
											isInner, car, favorGen);
									favorGen = favorGen + genData.getFavorGen();
									duration = duration + genData.getDuration();
									orderData.add(genData);
								} else { // c2
									if (cls.getC2outer() <= cls
											.getC2bookOuter()) {
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("排班已约满，无法再预约！");
										return res;
									}
									
									List<Car> allcars=carManager.getCarBySchoolId(ep.getSchoolId());;
									List<String> bookcars=null;
									List<ExamVipBookInfo> bookinfo=null;
									
									//获取排版当前的大客户预定情况
									String innerinfostr=cls.getInnerinfo();
				    				ExamInnerInfo innerinfo=null;
				    				if(innerinfostr!=null&&innerinfostr.length()>0)
				    					innerinfo= JSON.parseObject(innerinfostr, ExamInnerInfo.class);
									if(innerinfo!=null){
										bookcars=innerinfo.getBookcar();
									}else{
										//初始化一个对象进行保存
										innerinfo=new ExamInnerInfo();
									}
									//获取一辆空闲车
									Car car=pickcar(allcars,bookcars,drtype);
									
									if(car==null){
										res.setCode(ResultCode.ERRORCODE.FAILED);
										res.setMsgInfo("车辆已经约满，无法再预约！");
										return res;
									}
									//添加一辆预定车到列表
									innerinfo.getBookcar().add(car.getCarNo());
									cls.setInnerinfo(JSON.toJSONString(innerinfo));
									// （2.1）增加位置使用
									cls.setC2book(cls.getC2book() + 1);
									cls.setC2bookOuter(cls.getC2bookOuter() + 1);
									// examPlaceClassMapper.updateByPrimaryKeySelective(cls);
									//examPlaceClassManager.updateExamPlaceClass(cls);
									updateExamPlaceClass(cls);
									// （3）生成订单
									ExamPlaceOrder genData = genExamPlaceOrderSyn(
											orderId, ep, cls, coach, drtype,
											isInner, car, favorGen);
									favorGen = favorGen + genData.getFavorGen();
									duration = duration + genData.getDuration();
									orderData.add(genData);
								}
							}

						}
						// redisUtil.delete(RedisKeys.REDISKEY.LOCK_PRE +
						// "placeOrder." + clses[i]);
					} else {
						log.debug("Good-->got no lock!" + Thread.currentThread().getName() 
								+";current classId=" + clses[i] 
										+";current userId=" + userId
										+";current time=" + System.currentTimeMillis());
						res.setCode(ResultCode.ERRORCODE.ORDER_LOCK);
						res.setMsgInfo("当前车道已有教练正在排队，请稍后再试！");
						// 20161108 争抢坑位的时候，坑位忙则直接返回，让用户重新发起
						return res;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (lock != null) {
						try {
							lock.unlock();
							log.debug("Good-->now unlock!" + Thread.currentThread().getName() 
									+";current classId=" + clses[i] 
											+";current userId=" + userId
											+";current time=" + System.currentTimeMillis());
						} catch (Exception e) {
						}
					}
					// redisUtil.delete(RedisKeys.REDISKEY.LOCK_PRE +
					// "placeOrder." + clses[i]);
				}

			}
			// 增加本批订单所生成的优惠 //20160926改为订单完成后才赠送
			// ExamPlaceFavorKey key = new ExamPlaceFavorKey();
			// key.setPlaceId(placeId);
			// key.setUserId(coach.getCoachId());
			// ExamPlaceFavor favor =
			// examPlaceFavorMapper.selectByPrimaryKey(key);
			// if(null != favor){
			// favor.setDuration(favor.getDuration() + duration);
			// favor.setFavorOut(favor.getFavorOut() + favorGen);
			// examPlaceFavorMapper.updateByPrimaryKeySelective(favor);
			// }else {
			// favor = new ExamPlaceFavor();
			// favor.setPlaceId(placeId);
			// favor.setUserId(coach.getCoachId());
			// favor.setDuration(duration);
			// favor.setFavorOut(favorGen);
			// examPlaceFavorMapper.insertSelective(favor);
			// }

			res.setData(orderData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private Car pickcar(List<Car> allcars,List<String> book,String drtype){
		for(Car car:allcars){
			if(car.getDriveType().intValue()!=Integer.parseInt(drtype)){//c1,c2不匹配
				continue;
			}
			boolean hasbook=false;
			if(book!=null){
				for(String carno:book){
					if(car.getCarNo().equals(carno)){
						hasbook=true;
						break;
					}
				}
			}
			
			if(!hasbook){//没有被使用
				return car;
			}
		}
		
		//没有空闲车辆
		return null;
	}

	/**
	 * 同步方式生成订单
	 * 
	 * @param orderId
	 * @param ep
	 * @param cls
	 * @param coach
	 * @param drtype
	 * @param isInner
	 * @param car
	 * @return
	 */
	private ExamPlaceOrder genExamPlaceOrderSyn(String orderId, ExamPlace ep,
			ExamPlaceClass cls, Coach coach, String drtype, boolean isInner,
			Car car, int fg) {
		try {
			ExamPlaceOrder order = new ExamPlaceOrder();
			if (null != car) {
				order.setCarNo(car.getCarNo());
			} else {
				log.error(coach.getName() + coach.getCoachId() + ("教练没有车！！"));
			}
			order.setClassId(cls.getId());
			order.setCoachId(coach.getCoachId());
			order.setCoachImg(coach.getHeadIcon());
			order.setCoachMobile(coach.getPhoneNum());
			order.setCoachName(coach.getName());
			order.setCtime(new Date());
			order.setMtime(new Date());
			order.setDrtype(Byte.parseByte(drtype));
			order.setOrderId(orderId);
			order.setPlaceId(ep.getId());
			order.setPlaceName(ep.getName());
			//获取进考场当天不重复的6位随机数
			Integer code = null;
			boolean flag = false;
			String pstart = new SimpleDateFormat("yyyy-MM-dd").format(cls.getPstart());
			while (!flag) {
				code = RandomUtil.next(100000, 999999);
				flag = redisUtil.setNX(RedisKeys.REDISKEY.EXAM_PLACE_CODE + pstart + "." + code , code, 
				(int)(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pstart + " 23:59:59").getTime() - new Date().getTime() / 1000));
			}
			order.setCode(code);
			// 20161107 驾校教练有异常数据
/*			if (null != coach.getImportSrc()
					&& !"".equals(coach.getImportSrc())) {
				order.setSchool(coach.getImportSrc());
			} else {
				if (isInner) {
					order.setSchool(getSchoolInWhitelist(coach.getPhoneNum()));
				} else {
					order.setSchool("未知驾校");
				}
			}*/
			// 20161122驾校教练很多离职和转驾校的，系统同步的数据不准确，以白名单中驾校数据为准
			if (isInner) {
				order.setSchool(getSchoolInWhitelist(coach.getPhoneNum()));
			} else if (null != coach.getImportSrc()
					&& !"".equals(coach.getImportSrc())) {
				order.setSchool(coach.getImportSrc());
			}else {
				order.setSchool("未知驾校");
			}
			
			order.setType(Byte.valueOf(ep.getType())); // 科目类型从哪里取？？
			order.setState((byte) 0);
			order.setPstart(cls.getPstart());
			order.setPend(cls.getPend());
			order.setRstart(cls.getRstart());
			order.setRend(cls.getRend());
			int hour = cls.getDuration();
			order.setDuration(hour); // 订单时长（小时）
			// 本次订单获赠优惠 favorGen
			// 本次订单使用优惠 favorUse
			// 获赠学时剩余总计 favorLeft
			int favorGen = 0;
			ExamPlaceFavorKey key = new ExamPlaceFavorKey();
			key.setPlaceId(cls.getPlaceId());
			key.setUserId(coach.getCoachId());

			int favorUse = 0;
			// 20161111 优惠方式添加返金额等
			int favorType = cls.getFavorType(); // 优惠类型：0-不优惠；1-返课时；2-返金额',
			order.setFavorType(favorType);
			if (favorType == 0) {
				// 不优惠
			} else {
				ExamPlaceFavor favor = examPlaceFavorMapper
						.selectByPrimaryKey(key); //获取考场配置的优惠信息
				if (null != favor) {
					int getFavorOut = 0; //送的学时。和金额单位一致，乘了100
					if (favorType == 1) { // 1-返课时；2-返金额',//不管是哪种优惠，单位都统一了
						getFavorOut = favor.getFavorOut(); //赠送的优惠课时

						int course = getFavorOut / 100;// 已有的优惠课时
						if (course >= 2) { // 20160922 cls.getFavorIn() FlavorIn为累计几个小时才优惠的条件 要求优惠满2小时才可以使用
							// 本次有可使用的优惠
							favorUse = cls.getFavorIn() * 100; // 每次组多抵扣2小时
							favor.setFavorOut(getFavorOut - favorUse); // 只减去已使用的。增加的在全部订单生成后再一起增加，避免同批订单消费同批生成的优惠。
							examPlaceFavorMapper
									.updateByPrimaryKeySelective(favor);
						} else {
							// 本次没有可使用的优惠
							// 20161010 只有未使用优惠券的订单，才发放优惠
							favorGen = (cls.getDuration() / cls.getFavorIn())
									* cls.getFavorOut(); // 50= (2/2)*50
						}
						order.setFavorUse(favorUse);
						order.setFavorLeft(getFavorOut + favorGen + fg); // 获赠的学时总计为库中剩余+本次生成+与同批订单生成的优惠s
					} else if (favorType == 2) {
						//在完成训练订单时返金额到教练账户
//						getFavorOut = favor.getFavorOut2();
//
//						int money = getFavorOut;// 已有的优惠金额，单位分
//						if (money >= 2) { // 20161114 按课时计算
//															// 要求优惠满400元才可以使用
//							// 本次有可使用的优惠
//							favorUse = cls.getFavorIn(); // 单位统一了，都是分
//							favor.setFavorOut(getFavorOut - favorUse); // 只减去已使用的。增加的在全部订单生成后再一起增加，避免同批订单消费同批生成的优惠。
//							examPlaceFavorMapper.updateByPrimaryKeySelective(favor);
//						} else {
//							// 本次没有可使用的优惠
//							// 20161010 只有未使用优惠券的订单，才发放优惠
//							favorGen = (cls.getDuration() * cls.getOuterPrice() / cls
//									.getFavorIn()) * cls.getFavorOut(); // 50=
//																		// (2 *
//																		// 20000/40000)*
//																		// 10000
//						}
					}
//					order.setFavorUse(favorUse);
//					order.setFavorLeft(getFavorOut + favorGen + fg); // 获赠的学时总计为库中剩余+本次生成+与同批订单生成的优惠
				} else {
					// 20161010 只有未使用优惠券的订单，才发放优惠
					favorGen = (cls.getDuration() / cls.getFavorIn())
							* cls.getFavorOut(); // 50= (2/2)*50
					order.setFavorUse(0);
					order.setFavorLeft(favorGen + fg);
				}

			}

			order.setFavorGen(favorGen);
			if (favorType == 0) {
				order.setRemark("您已预约考场成功，请准时到场。考场资源有限，取消预约请谨慎。");
			} else if (favorType == 1) {
//				order.setRemark("本次约考获得" + ep.getName() + "赠送" + favorGen
//						/ 100.00 + "个小时，获赠满" + cls.getFavorIn()
//						+ "个小时可以抵扣下次约考费用，考场资源有限，取消预约请谨慎。");
				order.setRemark("本次预约将获得该考场赠送的" + favorGen / 100.00 + "小时练考时间");
			} else if (favorType == 2) {
				//计算返金额的额度
				int returnMoney = cls.getDuration() * cls.getFavorOut();
				order.setRemark("本次预约将获得" +  returnMoney / 100.00 + "元返现，订单完成后会发放到钱包");
				order.setReturnTotal(returnMoney);
			}

			if (isInner) {
				order.setCoachType((byte) 1); // 教练类型：1-内部教练；2-外部教练
				order.setPriceTotal(cls.getInnerPrice() * hour); // 订单总价格
				order.setCouponTotal(cls.getInnerPrice() * favorUse / 100); // 优惠抵扣价格
				order.setPayTotal(cls.getInnerPrice() * (hour - favorUse / 100)); // 实际需要支付的价格
			} else {
				order.setCoachType((byte) 2);
				order.setPriceTotal(cls.getOuterPrice() * hour); // 订单总价格
				order.setCouponTotal(cls.getOuterPrice() * favorUse / 100); // 优惠抵扣价格
				order.setPayTotal(cls.getOuterPrice() * (hour - favorUse / 100)); // 实际需要支付的价格
			}
			// 20161028 为检查是否有坑位锁定失败的情况，用户订单中添加坑位票凭证
			order.setExtra(cls.getC1book() + "-" + cls.getC2book() + '-'
					+ (cls.getC1book() + cls.getC2book()));
			examPlaceOrderMapper.insertSelective(order);
			log.debug("Good-->order created!" + Thread.currentThread().getName() 
					+";current classId=" + cls.getId() 
							+";current userId=" + order.getCoachId()
							+";current time=" + System.currentTimeMillis());
			return order;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public ReqResult cancelExamPlaceClassOrder(String userId, String userType,
			String orderId) {
		ReqResult res = ReqResult.getSuccess();
		System.out.println("#################### cancel order");
		try {
			List<String> oIds = new ArrayList<String>();
			String[] orders = orderId.split(",");
			for (String a : orders) {
				oIds.add(a);
			}
			ExamPlaceOrderExample example = new ExamPlaceOrderExample();
			example.createCriteria().andOrderIdIn(oIds);

			List<ExamPlaceOrder> data = examPlaceOrderMapper
					.selectByExample(example);
			if (null != data && data.size() > 0) {
				// （2）过期订单自动关闭。并恢复订单所占空位。恢复所占优惠。
				for (int i = 0; i < data.size(); i++) {
					ExamPlaceOrder order = data.get(i);
					boolean needPayBack = false;
					double ratio = 1.0;
					if (order.getState() == 1) { // 订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭',
						// 已支付的订单，离练考时间两天之外仍可以取消，取消全额退款
						// 两天后的凌晨截止
						// Calendar gdate = Calendar.getInstance();
						// gdate.add(gdate.DATE, 2);
						// String aa = new
						// SimpleDateFormat("yyyy-MM-dd").format(gdate.getTime())
						// + " 23:59:59";
						// Date d2 = new
						// SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(aa);
						// if(order.getPstart().before(d2)){
						// res.setCode(ResultCode.ERRORCODE.EXCEPTION);
						// res.setMsgInfo("该订单已接近练考时间，不允许取消！！");
						// return res;
						// }else {
						// needPayBack = true;
						// }

						Date d0 = new Date();
						// 20161014需求变更：订单上课的前两个小时不能取消
						long diff2 = order.getPstart().getTime() - d0.getTime();
						long hour2 = diff2 / (1000 * 60 * 60);
						if (hour2 <= 2) {
							res.setCode(ResultCode.ERRORCODE.EXCEPTION);
							res.setMsgInfo("该订单已接近练考时间，不允许取消！！");
							return res;
						}

						// 20161010 需求变更：12点之前可取消第2天，12点之后取消暂扣20%手续费（外部）
						String aa = new SimpleDateFormat("yyyy-MM-dd")
								.format(order.getPstart()) + " 00:00:00";
						Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(aa);
						long diff = d1.getTime() - d0.getTime();
						long hour = diff / (1000 * 60 * 60);
						if (hour >= 12) {
							// 退款返全额
							needPayBack = true;
							ratio = 1.0;							
							order.setRefundTotal(order.getPayTotal());
							order.setRemark("订单取消成功,退款" + (order.getRefundTotal()/ 100.00) + "元");
							
						} else {
							// 退款返80%
							needPayBack = true;
							ratio = Double.parseDouble(exam_refund_ratio);
							int payTotal = order.getPayTotal();
							//退款金额
							int refundTotal = (int) (payTotal * ratio);
							order.setRefundTotal(refundTotal);
							order.setRemark("订单取消成功,退款" + (refundTotal / 100.00) + "元,收取取消订单手续费" +
									((payTotal - refundTotal) / 100.00) + "元,退款比例为:" + ratio);
						}

					} else if (order.getState() == 2 || order.getState() == 3) {
						res.setCode(ResultCode.ERRORCODE.EXCEPTION);
						res.setMsgInfo("该订单已接近练考时间，不允许取消！");
						return res;
					} else if (order.getState() == 4 || order.getState() == 5) {
						res.setCode(ResultCode.ERRORCODE.EXCEPTION);
						res.setMsgInfo("该订单已取消或已关闭，不允许重复取消！");
						return res;
					} else { //未付款
						order.setRemark("用户取消了订单！");
					}
					// （2.1）取消订单
					order.setState((byte) 4); // 订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭',
					order.setFavorGen(0);
					order.setMtime(new Date());
					//order.setPayTotal(order.getPriceTotal()Total() - order.getCouponTotal()); //更新实际支付的金额
					updateExamPlaceOrder(order);
					// （2.2）恢复订单所占空位 为了快速扫描，可异步执行！
					ExamPlaceClass cls = getExamPlaceClassOne(order.getClassId());
					if (order.getDrtype() == 1) { // 练考车型：1-C1；2-C2
						cls.setC1book(cls.getC1book() - 1);
						if (order.getCoachType() == 1) { // 教练类型：1-内部教练；2-外部教练
							cls.setC1bookInner(cls.getC1bookInner() - 1);
							
							String innerinfostr=cls.getInnerinfo();
		    				ExamInnerInfo innerinfo=null;
		    				if(innerinfostr!=null&&innerinfostr.length()>0)
		    					innerinfo= JSON.parseObject(innerinfostr, ExamInnerInfo.class);
							if(innerinfo!=null){
			    				innerinfo.getBookcar().remove(order.getCarNo());
	
								ExamPlace ep = getExamPlaceById(order.getPlaceId());
								ExamVipCoach examVipCoach=examVipManagerImpl.getExamVipCoach(order.getCoachMobile(), ep.getSchoolId());
								List<ExamVipBookInfo> bookinfo=innerinfo.getBookinfo();
								ExamVipBookInfo examVipBookInfo=null;
								for(ExamVipBookInfo bi:bookinfo){
									if(bi.getVipId()==examVipCoach.getVipId()){
										examVipBookInfo=bi;
										break;
									}
								}
								examVipBookInfo.setC1book(examVipBookInfo.getC1book()-1);
								cls.setInnerinfo(JSON.toJSONString(innerinfo));
							}
							
						} else {
							cls.setC1bookOuter(cls.getC1bookOuter() - 1);
						}
					} else {
						cls.setC2book(cls.getC2book() - 1);
						if (order.getCoachType() == 1) { // 教练类型：1-内部教练；2-外部教练
							cls.setC2bookInner(cls.getC2bookInner() - 1);
							
							String innerinfostr=cls.getInnerinfo();
		    				ExamInnerInfo innerinfo=null;
		    				if(innerinfostr!=null&&innerinfostr.length()>0)
		    					innerinfo= JSON.parseObject(innerinfostr, ExamInnerInfo.class);
		    				if(innerinfo!=null){
								innerinfo.getBookcar().remove(order.getCarNo());
	
								ExamPlace ep = getExamPlaceById(order.getPlaceId());
								ExamVipCoach examVipCoach=examVipManagerImpl.getExamVipCoach(order.getCoachMobile(), ep.getSchoolId());
								List<ExamVipBookInfo> bookinfo=innerinfo.getBookinfo();
								ExamVipBookInfo examVipBookInfo=null;
								for(ExamVipBookInfo bi:bookinfo){
									if(bi.getVipId()==examVipCoach.getVipId()){
										examVipBookInfo=bi;
										break;
									}
								}
								examVipBookInfo.setC1book(examVipBookInfo.getC2book()-1);
								
								cls.setInnerinfo(JSON.toJSONString(innerinfo));
		    				}
						} else {
							cls.setC2bookOuter(cls.getC2bookOuter() - 1);
						}

					}
					
					
					
					cls.setMtime(new Date());
					updateExamPlaceClass(cls);
					// （2.3）恢复所占优惠
					if (order.getFavorUse() != 0) {
						ExamPlaceFavorKey key = new ExamPlaceFavorKey();
						key.setPlaceId(order.getPlaceId());
						key.setUserId(order.getCoachId());
						ExamPlaceFavor record = examPlaceFavorMapper
								.selectByPrimaryKey(key);
						if (null != record) {
							record.setFavorOut(record.getFavorOut()
									+ order.getFavorUse()); // 恢复已使用优惠
															// //生成的优惠在结束后才增加，这里不需要恢复
							// record.setDuration(record.getDuration() -
							// order.getDuration()); //恢复增加的时长
							// //时长在结束后才增加，这里不需要恢复
							examPlaceFavorMapper
									.updateByPrimaryKeySelective(record);
						}
					}
					// 返款
					if (needPayBack) {
						moneyManager.handleExamPlaceRefund(order, ratio);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public List<ExamPlaceOrder> getExamPlaceOrder(String orderId) {
		List<ExamPlaceOrder> data = new ArrayList<ExamPlaceOrder>();
		try {
			if(null != orderId && !"".equals(orderId)){
				List<String> oIds = new ArrayList<String>();
				String[] orders = orderId.split(",");
				for (String a : orders) {
					oIds.add(a);
					// 20161114从缓存中一个个查
					data.add(getExamPlaceOrderOne(a));
				}
			}else {
				return null;
			}
			/*
			 * ExamPlaceOrderExample example = new ExamPlaceOrderExample();
			 * example.createCriteria().andOrderIdIn(oIds);
			 * 
			 * data = examPlaceOrderMapper.selectByExample(example);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public ExamPlaceOrder getExamPlaceOrderOne(String orderId) {
		ExamPlaceOrder order = redisUtil
				.get(RedisKeys.REDISKEY.EXAM_PLACE_ORDER + orderId);
		if (null == order) {
			order = examPlaceOrderMapper.selectByPrimaryKey(orderId);
			redisUtil.setAll(RedisKeys.REDISKEY.EXAM_PLACE_ORDER + orderId,
					order, RedisKeys.EXPIRE.WEEK);
		}
		return order;
	}

	@Override
	public void postPayState(PayVo payVo, byte stageStateSucc, Date endTime) {
		/*
		 * try { String orderId = payVo.getPayOrderId(); List<String> oIds = new
		 * ArrayList<String>(); String[] orders = orderId.split(","); for(String
		 * a:orders){ oIds.add(a); } //如果是支付成功了 if(ReqConstants.STAGE_STATE_SUCC
		 * == stageStateSucc){ ExamPlaceOrder record = new ExamPlaceOrder();
		 * record.setState((byte) 1);
		 * //订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭'
		 * record.setPayWay(payVo.getPayWay()); record.setPayTime(endTime);
		 * ExamPlaceOrderExample example = new ExamPlaceOrderExample();
		 * example.createCriteria().andOrderIdIn(oIds);
		 * examPlaceOrderMapper.updateByExampleSelective(record, example); } }
		 * catch (Exception e) { e.printStackTrace(); }
		 */

		try {
			this.postPayState(payVo.getPayOrderId(), payVo.getPayWay(),
					ReqConstants.STAGE_STATE_SUCC, endTime);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void postPayState(String orderId, String payWay,
			byte stageStateSucc, Date endTime) {
		try {
			// List<String> oIds = new ArrayList<String>();
			// String[] orders = orderId.split(",");
			// for (String a : orders) {
			// oIds.add(a);
			// }
			// 如果是支付成功了
			if (ReqConstants.STAGE_STATE_SUCC == stageStateSucc) {
				ExamPlaceOrder record = new ExamPlaceOrder();
				record.setState((byte) 1); // 订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭'
				record.setPayWay(payWay);
				record.setPayTime(endTime);
				// ExamPlaceOrderExample example = new ExamPlaceOrderExample();
				// example.createCriteria().andOrderIdIn(oIds);
				// examPlaceOrderMapper.updateByExampleSelective(record,
				// example);
				String[] orders = orderId.split(",");
				for (String id : orders) {
					record.setOrderId(id);
					updateExamPlaceOrder(record);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void postOrderState(String orderId, byte state, String remark) {
		try {
			// 如果是关闭订单
			if (state == 5) { // 订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭
				ExamPlaceOrder order = examPlaceOrderMapper
						.selectByPrimaryKey(orderId);
				// 如已支付，则退款
				if (order.getState() == 1 || order.getState() == 2) {
					moneyManager.handleExamPlaceRefund(order, 1.0); // 20161010后台关闭的订单，全额退款。
				} else if (order.getState() == 3) {
					return; // 已完成的订单不允许关闭
				}

				// （2.1）设置关闭订单
				order.setState((byte) 5); // 订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭',
				if (null != remark && !"".equals(remark.trim())) {
					order.setRemark(remark);
				} else {
					order.setRemark("客服在后台关闭了此订单！");
				}

				int favorGen = order.getFavorGen();
				order.setFavorGen(0);
				order.setMtime(new Date());
				updateExamPlaceOrder(order);
				// （2.2）恢复订单所占空位 为了快速扫描，可异步执行！
				ExamPlaceClass cls = examPlaceClassManager
						.getExamPlaceClassOne(order.getClassId());
				if (order.getDrtype() == 1) { // 练考车型：1-C1；2-C2
					cls.setC1book(cls.getC1book() - 1);
					if (order.getCoachType() == 1) { // 教练类型：1-内部教练；2-外部教练
						cls.setC1bookInner(cls.getC1bookInner() - 1);
					} else {
						cls.setC1bookOuter(cls.getC1bookOuter() - 1);
					}
				} else {
					cls.setC2book(cls.getC2book() - 1);
					if (order.getCoachType() == 1) { // 教练类型：1-内部教练；2-外部教练
						cls.setC2bookInner(cls.getC2bookInner() - 1);
					} else {
						cls.setC2bookOuter(cls.getC2bookOuter() - 1);
					}

				}
				cls.setMtime(new Date());
				updateExamPlaceClass(cls);
				// （2.3）恢复所占优惠
				if (order.getFavorUse() != 0) {
					ExamPlaceFavorKey key = new ExamPlaceFavorKey();
					key.setPlaceId(order.getPlaceId());
					key.setUserId(order.getCoachId());
					ExamPlaceFavor record = examPlaceFavorMapper
							.selectByPrimaryKey(key);
					if (null != record) {
						record.setFavorOut(record.getFavorOut()
								+ order.getFavorUse()); // 恢复已使用优惠
														// //生成的优惠在结束后才增加，这里不需要恢复
						// record.setDuration(record.getDuration() -
						// order.getDuration()); //恢复增加的时长 //时长在结束后才增加，这里不需要恢复
						examPlaceFavorMapper
								.updateByPrimaryKeySelective(record);
					}
				}
				// 通知教练订单已被关闭
				JpushMsg jmsg = new JpushMsg();
				Map<String, String> extras = new HashMap<String, String>();
				extras.put("orderId", order.getOrderId());
				extras.put("remark", remark);
				jmsg.setExtras(extras);
				jmsg.setAlter("您预约考场的订单已被关闭！如已支付，退款将返回到您的余额账户中。");
				jmsg.setUserId(order.getCoachId());
				jmsg.setOrderId(order.getOrderId());
				jmsg.setOperate(JpushConstant.OPERATE.ORDER_CLOSE);// 如果有extras，则operate要放在extra之后
				Message jpush = new Message();
				jpush.setKeys(order.getOrderId());
				jpush.setTopic(jpushProducer.getCreateTopicKey());
				jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
				jpush.setBody(SerializableUtil.serialize(jmsg));
				jpushProducer.send(jpush);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Page<ExamPlaceOrder> getOrders(String pdate, String pstart,
			String state, String drtype, String placeId, String classId,
			String coachName, String schoolName, String carNo,
			String coachMobile, String orderId, String pageNo, String pageSize) {
		try {
			ExamPlaceOrderExample example = new ExamPlaceOrderExample();
			ExamPlaceOrderExample.Criteria criteria = example.createCriteria();
			if (null != pdate && !"".equals(pdate.trim())) {
				if (null != pstart && !"".equals(pstart)) {
					Date d0 = null;
					Date d1 = null;
					if (pdate.contains(",")) {
						String p1 = pdate.split(",")[0];
						String p2 = pdate.split(",")[1];
						String t1 = pstart.split("-")[0];
						String t2 = pstart.split("-")[1];
						d0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(p1 + " " + t1);
						d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(p2 + " " + t2);
					} else {
						String t1 = pstart.split("-")[0];
						String t2 = pstart.split("-")[1];
						d0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(pdate + " " + t1);
						d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(pdate + " " + t2);
					}
					criteria.andRstartBetween(d0, d1);

				} else {
					Date d0 = null;
					Date d1 = null;
					if (pdate.contains(",")) {
						String p1 = pdate.split(",")[0];
						String p2 = pdate.split(",")[1];
						d0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(p1 + " 00:00:00");
						d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(p2 + " 23:59:59");
					} else {
						d0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(pdate + " 00:00:00");
						d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(pdate + " 23:59:59");
					}
					criteria.andRstartBetween(d0, d1);
				}

			}
			if (null != state && !"".equals(state.trim())) {
				criteria.andStateEqualTo(Byte.parseByte(state.trim()));
			}
			if (null != drtype && !"".equals(drtype.trim())) {
				criteria.andDrtypeEqualTo(Byte.parseByte(drtype));
			}
			if (null != placeId && !"".equals(placeId)) {
				criteria.andPlaceIdEqualTo(Integer.parseInt(placeId.trim()));
			}
			if (null != classId && !"".equals(classId)) {
				criteria.andClassIdEqualTo(Integer.parseInt(classId.trim()));
			}
			if (null != coachName && !"".equals(coachName.trim())) {
				criteria.andCoachNameLike("%" + coachName.trim() + "%");
			}
			if (null != schoolName && !"".equals(schoolName.trim())) {
				criteria.andSchoolLike("%" + schoolName.trim() + "%");
			}
			if (null != carNo && !"".equals(carNo.trim())) {
				criteria.andCarNoLike("%" + carNo.trim() + "%");
			}
			if (null != coachMobile && !"".equals(coachMobile.trim())) {
				criteria.andCoachMobileLike("%" + coachMobile.trim() + "%");
			}
			if (null != orderId && !"".equals(orderId.trim())) {
				criteria.andOrderIdLike("%" + orderId + "%");
			}
			//criteria.multiColumnOrClause();
			int total = examPlaceOrderMapper.countByExample(example);
			example.setOrderByClause("drtype,school,pstart desc");

			int pNo = 1;
			int pSize = 100;
			if (StringUtil.isNotNullAndNotEmpty(pageNo)
					&& StringUtil.isNotNullAndNotEmpty(pageSize)) {
				pNo = Integer.parseInt(pageNo.trim());
				if (pNo <= 0) {
					pNo = 1;
				}
				pSize = Integer.parseInt(pageSize.trim());
				if (pSize <= 0) {
					pSize = 100;
				}
			}
			RowBounds rowBounds = new RowBounds((pNo - 1) * pSize, pSize);// (offset,limit)

			List<ExamPlaceOrder> data = examPlaceOrderMapper
					.selectByExampleWithRowbounds(example, rowBounds);
			return new Page<ExamPlaceOrder>(data, pNo, pSize, total);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Page<ExamPlaceOrder> getMyExamPlaceOrder(String userId,
			String userType, String state, String pageNo, String pageSize) {
		try {
			ExamPlaceOrderExample example = new ExamPlaceOrderExample();
			ExamPlaceOrderExample.Criteria criteria = example.createCriteria();

			if (null != state && !"".equals(state.trim())) {
				criteria.andStateEqualTo(Byte.parseByte(state.trim()));
			} else {
				// 如果没有传订单状态，则查询去掉已取消的，免得太多了
				//criteria.andStateNotEqualTo((byte) 4); // '订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭',
			}
			if (null != userId && !"".equals(userId)) {
				criteria.andCoachIdEqualTo(Long.parseLong(userId));
			} else {
				return null;
			}
			int total = examPlaceOrderMapper.countByExample(example);
			example.setOrderByClause("payTime desc,rstart desc");

			int pNo = 1;
			int pSize = 100;
			if (StringUtil.isNotNullAndNotEmpty(pageNo)
					&& StringUtil.isNotNullAndNotEmpty(pageSize)) {
				pNo = Integer.parseInt(pageNo.trim());
				if (pNo <= 0) {
					pNo = 1;
				}
				pSize = Integer.parseInt(pageSize.trim());
				if (pSize <= 0) {
					pSize = 100;
				}
			}
			RowBounds rowBounds = new RowBounds((pNo - 1) * pSize, pSize);// (offset,limit)

			List<ExamPlaceOrder> data = examPlaceOrderMapper
					.selectByExampleWithRowbounds(example, rowBounds);
			return new Page<ExamPlaceOrder>(data, pNo, pSize, total);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ReqResult payExamPlaceClassOrder(String userId, String userType,
			String orderId) {
		ReqResult res = ReqResult.getSuccess();
		List<ExamPlaceOrder> orders = getExamPlaceOrder(orderId);
		if (null != orders && orders.size() > 0) {
			for (ExamPlaceOrder order : orders) {
				if (order.getState() != 0) { // 订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭',
					res.setCode(ResultCode.ERRORCODE.FAILED);
					res.setMsgInfo("订单状态错误！");
					return res;
				}
				if (order.getPayTotal() != 0) { // 订单金额不为0，需要走通用支付接口进行支付。
					res.setMsgInfo("订单预支付成功！");
					return res;
				}
			}

			this.postPayState(orderId, PayWayType.AUTOPAY,
					ReqConstants.STAGE_STATE_SUCC, new Date());
			// 20161023 需要进一步调用流水接口，记录资金流水
			moneyManager.handleExamPlaceMoneyFlow(orders);
			res.setCode(ResultCode.ERRORCODE.AUTO_PAYED);
			res.setMsgInfo(ResultCode.ERRORINFO.AUTO_PAYED);
			return res;
		} else {
			res.setCode(ResultCode.ERRORCODE.FAILED);
			res.setMsgInfo("查询无此订单！");
			return res;
		}

	}

	/**
	 * 获取用户白名单内的驾校名称（驾校科技部信息与通讯录信息有的不一致，导致用户没有驾校信息）
	 * 
	 * @param phoneNum
	 * @return
	 */
	private String getSchoolInWhitelist(String phoneNum) {
		ExamPlaceWhitelistExample example = new ExamPlaceWhitelistExample();
		example.createCriteria().andMobileLike("%" + phoneNum + "%")
				.andStateEqualTo((byte) 0);
		List<ExamPlaceWhitelist> data = examPlaceWhilelistMapper
				.selectByExample(example);
		if (null != data && data.size() > 0) {
			return data.get(0).getSchool();
		}
		return null;

	}

	public void updateExamPlaceOrder(ExamPlaceOrder record) {
		try {
			record.setMtime(new Date());
/*			redisUtil.setAll(
					RedisKeys.REDISKEY.EXAM_PLACE_ORDER + record.getOrderId(),
					record, RedisKeys.EXPIRE.WEEK); // 写表 与 写缓存 顺序是否需要调整？
*/			examPlaceOrderMapper.updateByPrimaryKeySelective(record);
			//清除订单缓存
			redisUtil.delete(RedisKeys.REDISKEY.EXAM_PLACE_ORDER + record.getOrderId());
			ExamPlaceOrder order = getExamPlaceOrderOne(record.getOrderId());
			//清除是否已约课缓存
			redisUtil.delete(RedisKeys.REDISKEY.EXAM_PLACE_CLASS_BOOK +order.getClassId() + "." 
			+ order.getCoachId() + "." + order.getDrtype());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//************************************************************************************************************
	// 20161115为避免服务间调用的消耗，部分代码冗余
	/**
	 * 用户是否在白名单内。白名单教练属于内部教练，享受内部价格
	 * 
	 * @param phoneNum
	 * @param schoolId
	 * @return
	 */
	public boolean isInWhitelist(String phoneNum, Integer schoolId) {
		Boolean check = false;
		try {
			check = redisUtil.get(REDISKEY.EXAM_PLACE_WHITE + phoneNum);
			if (null == check) {
				ExamPlaceWhitelistExample example = new ExamPlaceWhitelistExample();
				example.createCriteria().andMobileLike("%" + phoneNum + "%")
						.andStateEqualTo((byte) 0);
				int count = examPlaceWhilelistMapper.countByExample(example);
				if (count > 0) {
					check = true;
					redisUtil.setAll(REDISKEY.EXAM_PLACE_WHITE + phoneNum,
							true, RedisKeys.EXPIRE.WEEK);
				} else {
					check = false;
					redisUtil.setAll(REDISKEY.EXAM_PLACE_WHITE + phoneNum,
							false, RedisKeys.EXPIRE.WEEK);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return check;
	}

	public ExamPlaceClass getExamPlaceClassOne(Integer id) {
		ExamPlaceClass cls = redisUtil.get(RedisKeys.REDISKEY.EXAM_PLACE_CLASS
				+ id);
		if (null == cls) {
			cls = examPlaceClassMapper.selectByPrimaryKey(id);
			redisUtil.setAll(RedisKeys.REDISKEY.EXAM_PLACE_CLASS + id, cls,
					RedisKeys.EXPIRE.WEEK);
		}
		return cls;
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
	
	public int updateExamPlaceClass(ExamPlaceClass record) {
		try {
			record.setMtime(new Date());
			redisUtil.setAll(RedisKeys.REDISKEY.EXAM_PLACE_CLASS + record.getId(), record, RedisKeys.EXPIRE.WEEK); //写表 与 写缓存 顺序是否需要调整？
			examPlaceClassMapper.updateByPrimaryKeySelective(record);
			//20161114有排班更新时，清除今天的排班查询
			String dayStr = new SimpleDateFormat("yyyy-MM-dd").format(record.getPstart());
			redisUtil.delete(RedisKeys.REDISKEY.EXAM_PLACE_DAY + record.getPlaceId()+ "." + dayStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List <Integer> getExamPlaceIncome(Integer placeId) { 
		return examPlaceOrderMapper.selectIncome(placeId);
	}

	@Override
	public ReqResult validCode(Integer placeId, Integer code) {
		ReqResult result = ReqResult.getSuccess();
		result.setData("isValid", 0);
		
		ExamPlaceOrder order = new ExamPlaceOrder();
		order.setCode(code);
		order.setPlaceId(placeId);
		order = examPlaceOrderMapper.selectCode(order);
		if (order == null) {
			result.setMsgInfo("该验证码不存在");
			return result;
		} 
		Date start = order.getPstart();
		Date end = order.getPend();
		String msg = "(" + new SimpleDateFormat("MM月dd日 HH:mm").format(start) + "-" 
		+ new SimpleDateFormat("HH:mm").format(end) + "场次)";
		Date now = new Date();
		if (order.getCodeValid() == 1) {
			result.setMsgInfo("已验证" + msg);
			result.setData("validTime",order.getValidTime());
			return result;
		}
		if (now.before(new Date(start.getTime() - 1000 * 3 * 60 * 60))) { //当前时间在预约开始时间之前三小时验证有效
			result.setMsgInfo("时间未到" + msg);
		} else if (now.after(end)) { //当前时间在预约结束时间之后
			result.setMsgInfo("已过期" + msg);
		} else {
			result.setData("isValid", 1);
			ExamPlaceOrder updateBean = new ExamPlaceOrder();
			updateBean.setOrderId(order.getOrderId());
			updateBean.setCodeValid((byte)1);
			updateBean.setValidTime(new Date());
			examPlaceOrderMapper.updateByPrimaryKeySelective(updateBean);
			result.setMsgInfo("预约今天" + msg);
		}
		return result;
	}

	@Override
	public List<Integer> getExamPlaceOrder(Integer placeId) {
		return examPlaceOrderMapper.selectOrderCount(placeId);
	}

}
