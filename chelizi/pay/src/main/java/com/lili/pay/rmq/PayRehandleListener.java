package com.lili.pay.rmq;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.lili.common.constant.MoneyChange;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.coupon.service.CouponService;
import com.lili.log.service.LogService;
import com.lili.log.vo.PayLogVo;
import com.lili.order.service.OrderService;
import com.lili.order.vo.OrderVo;
import com.lili.pay.manager.MoneyManager;
import com.lili.pay.vo.PayMessage;
import com.lili.pay.vo.PayVo;
import com.lili.pay.vo.PayWayType;
import com.lili.school.manager.SchoolManager;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;

/**
 * 报名支付成功状态未变监听处理
 * @author lzb
 *
 */
public class PayRehandleListener implements MessageListenerConcurrently {
	
	private Logger log = Logger.getLogger(PayRehandleListener.class);
	
    @Autowired
    private LogService logService;
    
    @Autowired
    private MoneyManager moneyManager;
    
    @Autowired
    private StudentManager studentManager;
    
    @Autowired
    private SchoolManager schoolManager;
    
    @Autowired
    private CouponService couponService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @Autowired
    private OrderService orderService;
	
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		try {
			for (MessageExt msg : msgs) {
				PayMessage payMsg = SerializableUtil.unserialize(msg.getBody());
				log.info("********************************* PayRehandleListener Start : " + payMsg);
				
		        payMsg.setRemark(PayWayType.getChinaName(payMsg.getPayWay()) + "|" + MoneyChange.SIGNUP_FEE.getDesc());
		        
		        /**
		         * 更新操作重复操作一遍，不管是否成功直接更新
		         * 插入的判断是否已存在，存在的不插，否则才插数据
		         */
		        //更改学员报名状态为报名成功
		        Student student = new Student();
		        student.setStudentId(payMsg.getUserId());
		        student.setApplyexam(ReqConstants.PROG_STAGE_USERINFO);
		        student.setApplystate(ReqConstants.STAGE_STATE_NONE+0);
		        studentManager.updateStudent(student );
		        log.info("********************************* PayRehandleListener Update Student Info Success!");
		        
		        //更改报名订单状态
		        PayVo payVo = new PayVo(payMsg.getUserId(), payMsg.getUserType(), payMsg.getPayValue(), payMsg.getPayWay(), payMsg.getPayOrderId(), 2, payMsg.getCouponId(), 0, payMsg.getRemark(),payMsg.getInsuranceId());
		        
		        // 修改报名费支付状态：0代表未支付，1代表已支付,2代表支付失败，9代表老学员自动支付'
				schoolManager.postPayState(payVo, ReqConstants.STAGE_STATE_SUCC);
				//修改保单支付状态
				if(payVo.getInsuranceId()!=null && !payVo.getInsuranceId().equals("")){
				   orderService.updatePayState(payVo.getInsuranceId(), ReqConstants.STAGE_STATE_SUCC,payVo.getPayWay());
				}
		        log.info("********************************* PayRehandleListener Update EnrollOrder Info Success!");
		        
		        //更改优惠券使用状态
		        //如果是报名订单，且有使用优惠券，则设置优惠券已使用
		        if (payMsg.getCouponId() != 0) {
		        	OrderVo orderVo = new OrderVo();
		        	orderVo.setStudentId(payMsg.getUserId());
		        	orderVo.setCoupon(payMsg.getCouponId());
		        	couponService.useCouponWithoutCheck(orderVo);
		        	log.info("********************************* PayRehandleListener Update Coupon Info Success!");
		        }
		        
		        // 记录资金流水
		        int isExit =  moneyManager.isExitMoneyRecord(payVo);
		        if (isExit == 0 ) {
		        	moneyManager.handleSignupMoneyFlow(payVo);
		        	log.info("********************************* PayRehandleListener Not Exit User Money Log, Insert Money Log Info Success!");
		        }
		        
		        //判断log表流水是否已存在，如果存在，则不记录
		        int isExitLog = logService.isExitLog(payVo.getPayOrderId());
		        if (isExitLog == 0) {
		        	PayLogVo payLogVo = new PayLogVo();
		        	payLogVo.setCouponid(payMsg.getCouponId());
		        	payLogVo.setCouponmoney(0.0);
		        	payLogVo.setOrderid(payMsg.getPayOrderId());
		        	payLogVo.setPaymoney((double) payMsg.getPayValue());
		        	payLogVo.setPaytime(payMsg.getEndTime());
		        	payLogVo.setPayway(payMsg.getPayWay());
		        	payLogVo.setWaternum(payMsg.getWaterNum());
		        	payLogVo.setStudentid(payMsg.getUserId());
		        	logService.logPay(payLogVo);
		        	log.info("********************************* PayRehandleListener Not Exit Log, Insert Log Info Success!");
		        }

		        // 去掉重复回调标记 
		        redisUtil.delete(REDISKEY.PRE_PAY_ORDER + payMsg.getPayOrderId());
		        log.info("********************************* PayRehandleListener RedisUtil Delete Success!");
			}
		} 
		catch (Exception e) {
			log.error( msgs.size() + "ShareStudentEnrollPaidListener Exception:" + e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
