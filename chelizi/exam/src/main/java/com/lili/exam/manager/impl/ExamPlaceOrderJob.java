package com.lili.exam.manager.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.lili.common.constant.JpushConstant;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.exam.dto.ExamPlaceClass;
import com.lili.exam.dto.ExamPlaceFavor;
import com.lili.exam.dto.ExamPlaceFavorKey;
import com.lili.exam.dto.ExamPlaceOrder;
import com.lili.exam.dto.ExamPlaceOrderExample;
import com.lili.exam.manager.ExamPlaceClassManager;
import com.lili.exam.manager.ExamPlaceOrderManager;
import com.lili.exam.mapper.ExamPlaceClassMapper;
import com.lili.exam.mapper.ExamPlaceFavorMapper;
import com.lili.exam.mapper.ExamPlaceOrderMapper;
import com.lili.pay.manager.MoneyManager;

public class ExamPlaceOrderJob {
	private static Logger log = LoggerFactory.getLogger(ExamPlaceOrderJob.class);
    @Autowired
    private ExamPlaceOrderMapper examPlaceOrderMapper;
	@Autowired
	private ExamPlaceClassMapper examPlaceClassMapper;
	@Autowired
    private ExamPlaceFavorMapper examPlaceFavorMapper;
	@Autowired
	private ExamPlaceOrderManager examPlaceOrderManager;
	@Autowired
	private ExamPlaceClassManager examPlaceClassManager;
	@Autowired
	private MoneyManager moneyManager;
	@Autowired
	private RedisUtil redisUtil;
	@Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;
	@Value("${exam.refund.ratio}")
	private String exam_refund_ratio = "0.8";
    
	public void checkExamPlaceOrders(){
		List<Byte> state = new ArrayList<Byte>();
		state.add((byte) 0); //订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭
		state.add((byte) 1);
		state.add((byte) 2);
		
		//（1）扫描十分钟之外的订单。
		Date d = new Date(System.currentTimeMillis() - 1000*60*10); 
		ExamPlaceOrderExample example = new ExamPlaceOrderExample();
		example.createCriteria()
				.andStateIn(state)
				.andCtimeLessThanOrEqualTo(d);
		System.out.println("################################ timer job!!!");
		List<ExamPlaceOrder> data = examPlaceOrderMapper.selectByExample(example);
		if(null != data && data.size()>0){
			for(int i=0;i<data.size();i++){
	
				ExamPlaceOrder order = data.get(i);	
				
				System.out.println("id!!!!!!!!!!!" + order.getOrderId() + "*********" + order.getState());
				
				String lockKey = "exam_order_job_lock_" + order.getOrderId();
				if(!redisUtil.isExist(lockKey)){
					redisUtil.setAll(lockKey, order.getOrderId(),300);
					byte status = order.getState();
					if(status == 0){
						//未支付的设置为已关闭
						//（2）过期订单自动关闭。并恢复订单所占空位。恢复所占优惠。
						//（2.1）关闭订单
						order.setState((byte)5);	//订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭',
						order.setRemark("超时未支付自动关闭！");
						int favorGen = order.getFavorGen();
						order.setFavorGen(0);
						//examPlaceOrderMapper.updateByPrimaryKeySelective(order);
						updateExamPlaceOrder(order);
						//（2.2）恢复订单所占空位	为了快速扫描，可异步执行！
						//ExamPlaceClass cls = examPlaceClassMapper.selectByPrimaryKey(order.getClassId());
						ExamPlaceClass cls = getExamPlaceClassOne(order.getClassId());
						if(order.getDrtype() == 1){ //练考车型：1-C1；2-C2
							cls.setC1book(cls.getC1book() -1 );
							if(order.getCoachType() == 1){  //教练类型：1-内部教练；2-外部教练
								cls.setC1bookInner(cls.getC1bookInner() -1 );
							}else {
								cls.setC1bookOuter(cls.getC1bookOuter() -1 );
							}
						}else {
							cls.setC2book(cls.getC2book() -1 );
							if(order.getCoachType() == 1){  //教练类型：1-内部教练；2-外部教练
								cls.setC2bookInner(cls.getC2bookInner() -1 );
							}else {
								cls.setC2bookOuter(cls.getC2bookOuter() -1 );
							}
							
						}
						//examPlaceClassMapper.updateByPrimaryKeySelective(cls);
						updateExamPlaceClass(cls);
						//（2.3）恢复所占优惠
						if(order.getFavorUse() != 0){
							ExamPlaceFavorKey key = new ExamPlaceFavorKey();
							key.setPlaceId(order.getPlaceId());
							key.setUserId(order.getCoachId());
							ExamPlaceFavor record = examPlaceFavorMapper.selectByPrimaryKey(key);
							if(null != record){
								if(cls.getFavorType() == 1){
									record.setFavorOut(record.getFavorOut() + order.getFavorUse()); //恢复已使用优惠  //生成的优惠在结束后才增加，这里不需要恢复
								}else if (cls.getFavorType() == 2){
									record.setFavorOut2(record.getFavorOut2() + order.getFavorUse());
								}
								examPlaceFavorMapper.updateByPrimaryKeySelective(record);
							}
						}

					}else if(status == 1){
						//已支付的检查是否到了上课时间
						Date d0 = new Date();
						Date d1 = order.getRstart();
						if(d0.after(d1)){
							order.setState((byte)2);	//订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭',
							order.setRemark("时间到自动进入练考中！");
							//examPlaceOrderMapper.updateByPrimaryKeySelective(order);
							updateExamPlaceOrder(order);
						}
						//20161121距离订单练考一个小时时，提醒教练及时到场
						long diff = d1.getTime() - d0.getTime();
						if(diff <= 1 * 60 * 60 * 1000 && diff > (1 * 60 * 60 * 1000 - 5 * 60 * 1000)){
							String remindKey = "exam_order_job_remind_" + order.getOrderId();
							if(!redisUtil.isExist(remindKey)){
								redisUtil.setAll(remindKey, order.getOrderId(),300);
								try {
									String time = new SimpleDateFormat("MM-dd HH:mm").format(order.getRstart()) 
											+ "-"
											+ new SimpleDateFormat("HH:mm").format(order.getRend());
									// 通知教练订单及时到场
									JpushMsg jmsg = new JpushMsg();
									Map<String, String> extras = new HashMap<String, String>();
									extras.put("orderId", order.getOrderId());
									jmsg.setExtras(extras);
									jmsg.setAlter("您预约的" + order.getPlaceName() +"预约订单（" + time + "）将于1小时后生效，请提前十分钟凭相关验证信息排队入场" );
									jmsg.setUserId(order.getCoachId());
									jmsg.setOrderId(order.getOrderId());
									jmsg.setOperate(JpushConstant.OPERATE.BROADCAST);// 如果有extras，则operate要放在extra之后
									Message jpush = new Message();
									jpush.setKeys(order.getOrderId());
									jpush.setTopic(jpushProducer.getCreateTopicKey());
									jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
									jpush.setBody(SerializableUtil.serialize(jmsg));
									jpushProducer.send(jpush);
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
						
					}else if(status == 2){
						//上课中的检查是否到了下课时间
						Date d0 = new Date();
						Date d1 = order.getRend();
						if(d0.after(d1)){
							order.setState((byte)3);	//订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭',
							order.setRemark("练考时间已结束，订单已完成！");
							if (order.getFavorType() == 2) {
								int total = order.getPriceTotal();
								order.setPayTotal(total - order.getReturnTotal());
								order.setRefundTotal(order.getReturnTotal());
							}
							updateExamPlaceOrder(order);
							
							// 20161010 使用了优惠进行支付的订单，不再赠送优惠。未使用优惠的才赠送。
							if(order.getFavorUse().intValue() == 0){
								//添加优惠信息。课时满2小时送0.5小时。
								ExamPlaceFavorKey key = new ExamPlaceFavorKey();
								key.setPlaceId(order.getPlaceId());
								key.setUserId(order.getCoachId());
								ExamPlaceFavor favor = examPlaceFavorMapper.selectByPrimaryKey(key);
								
								if(null != favor){
									if(order.getFavorType() == 1){
										favor.setDuration(favor.getDuration() + order.getDuration());
										favor.setFavorOut(favor.getFavorOut() + order.getFavorGen());
										examPlaceFavorMapper.updateByPrimaryKeySelective(favor);
									} else if(order.getFavorType() == 2){
//										favor.setDuration(favor.getDuration() + order.getDuration());
//										favor.setFavorOut2(favor.getFavorOut2() + order.getFavorGen());
//										examPlaceFavorMapper.updateByPrimaryKeySelective(favor);
//										直接返金额
										moneyManager.handleExamPlaceReturnAward(order);
									}								

								} else {
									favor = new ExamPlaceFavor();
									favor.setPlaceId(order.getPlaceId());
									favor.setUserId(order.getCoachId());
									favor.setDuration(order.getDuration());
									if(order.getFavorType() == 1){
										favor.setFavorOut(order.getFavorGen());
									}else if(order.getFavorType() == 2){
										favor.setFavorOut2(order.getFavorGen());
									}
									
									examPlaceFavorMapper.insertSelective(favor);
								}
							}
							
						}
					}
				}
				
			}		
		}
		
	}
	
	//************************************************************************************************************
	// 20161115为避免服务间调用的消耗，部分代码冗余
	
	public void updateExamPlaceOrder(ExamPlaceOrder record) {
		try {
			record.setMtime(new Date());
			examPlaceOrderMapper.updateByPrimaryKeySelective(record);
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
	
	public ExamPlaceClass getExamPlaceClassOne(Integer id) {
		ExamPlaceClass cls = redisUtil.get(RedisKeys.REDISKEY.EXAM_PLACE_CLASS + id);
		if(null == cls){
			cls = examPlaceClassMapper.selectByPrimaryKey(id);
			redisUtil.setAll(RedisKeys.REDISKEY.EXAM_PLACE_CLASS + id, cls, RedisKeys.EXPIRE.WEEK);
		}
		return cls;
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
	
}





































