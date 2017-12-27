package com.lili.pay.purpose;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.constant.MoneyChange;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.exam.dto.ExamPlaceOrder;
import com.lili.exam.dto.ExamPlacePayOrder;
import com.lili.exam.manager.ExamPlaceOrderManager;
import com.lili.log.service.LogService;
import com.lili.log.vo.PayLogVo;
import com.lili.pay.manager.MoneyManager;
import com.lili.pay.vo.PayVo;
import com.lili.pay.vo.PayWayType;

/**
 * 支付约考场费用
 * @author yangpeng
 *
 */
public class ExamPlacePurpose implements IPayPurpose {
	private static Logger log = LoggerFactory.getLogger(ExamPlacePurpose.class);
	@Autowired
	ExamPlaceOrderManager examPlaceOrderManager;
    @Autowired
    private LogService logService;
    @Autowired
    private MoneyManager moneyManager;
    @Autowired
    private RedisUtil redisUtil;

	@Override
	public Object purposeAdvance(PayVo payVo, ReqResult reqResult)
			throws Exception {
        if (payVo.getPayValue() <= 0) {
            reqResult.setCode(ResultCode.ERRORCODE.PAY_VALUE_IS_NOT_CORRECT);
            return null;
        }
        String orderId = redisUtil.get(REDISKEY.PRE_PAY_ORDER_SHORT + payVo.getPayOrderId());
		// 支付预处理，重新确定订单价格等信息。约考场订单，可能多个订单一起支付。
        //comment 20171227
		//List<ExamPlaceOrder> orders = examPlaceOrderManager.getExamPlaceOrder(orderId);
		
		//modify by devil 20171227
		//之前的orderid是exam orderid
		//现在改成大支付订单
		List<ExamPlacePayOrder> payorder=examPlaceOrderManager.getExamPlacePayOrder(orderId);
		if(payorder!=null&&payorder.size()>0){
			if(payorder.size()>1){
				throw new RuntimeException("payorder size large than 1:"+orderId);
			}
			int priceAll=payorder.get(0).getPayTotal();
			payVo.setPayValue(priceAll);
			payVo.setDiscountMoney(0);
			return 0;
		}
		
		//comment 20171227
//		if(null != orders && orders.size() >0){
//			int priceAll = 0;
//			int couponAll = 0;
//			for(ExamPlaceOrder order:orders){
//				priceAll = priceAll + order.getPayTotal().intValue();
//				couponAll = couponAll + order.getCouponTotal().intValue();
//			}
//			payVo.setPayValue(priceAll);
//			payVo.setDiscountMoney(couponAll);
//			return 0;
//		}
		
		return null;
	}

	@Override
	public ReqResult doPurpose(PayVo payVo, Date endTime, String waterNum,
			int totalFee) {
		ReqResult reqResult = ReqResult.getFailed();
        // 验证金额
        if (payVo.getPayValue() != totalFee) {
            reqResult.setCode(ResultCode.ERRORCODE.ORDER_MONEY_MOTIFY);
            return reqResult;
        }
        
        String orderIdShort = payVo.getPayOrderId();
        String orderId = redisUtil.get(REDISKEY.PRE_PAY_ORDER_SHORT + payVo.getPayOrderId());
        payVo.setPayOrderId(orderId);
        redisUtil.delete(REDISKEY.PRE_PAY_ORDER_SHORT + orderIdShort);
        
        payVo.setRemark(PayWayType.getChinaName(payVo.getPayWay()) + "|" + MoneyChange.EXAMPLACE_FEE.getDesc());

        // 记录流水
        PayLogVo payLogVo = new PayLogVo();
        payLogVo.setCouponid(payVo.getCouponId());
        payLogVo.setCouponmoney(0.0);
        payLogVo.setOrderid(payVo.getPayOrderId());//如果多个订单一起支付，这里是用逗号隔开的
        payLogVo.setPaymoney((double) payVo.getPayValue());
        payLogVo.setPaytime(endTime);
        payLogVo.setPayway(payVo.getPayWay());
        payLogVo.setWaternum(waterNum);
        payLogVo.setStudentid(payVo.getUserId());
        logService.logPay(payLogVo);

        try {
			// 标记订单已支付
        	examPlaceOrderManager.postPayState(payVo, ReqConstants.STAGE_STATE_SUCC,endTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        // 记录资金流水
        moneyManager.handleExamPlaceMoneyFlow(payVo); //如果是合并支付，则只记录了整体的
        
        // 去掉标记
        redisUtil.delete(REDISKEY.PRE_PAY_ORDER + payVo.getPayOrderId());

        return ReqResult.getSuccess();
	}

}





























