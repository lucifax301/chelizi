/**
 *
 */
package com.lili.pay.purpose;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.constant.MoneyChange;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.common.vo.ResultCode.ERRORCODE;
import com.lili.coupon.dto.Coupon;
import com.lili.coupon.service.CouponService;
import com.lili.log.service.LogService;
import com.lili.log.vo.PayLogVo;
import com.lili.order.dto.InsuranceOrder;
import com.lili.order.service.OrderService;
import com.lili.order.vo.OrderVo;
import com.lili.pay.manager.MoneyManager;
import com.lili.pay.vo.PayVo;
import com.lili.pay.vo.PayWayType;
import com.lili.school.manager.SchoolManager;

/**
 * @author linbo
 *         报名费支付
 */
public class SignupPurpose implements IPayPurpose {
    @Autowired
    private LogService logService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SchoolManager schoolManager;
    @Autowired
    private MoneyManager moneyManager;
    @Autowired
    private CouponService couponService;
    @Autowired
    private OrderService orderService;

    @Override
    public ReqResult doPurpose(PayVo payVo, Date endTime, String waterNum, int totalFee) {
        ReqResult reqResult = ReqResult.getFailed();

        // 验证金额
        if (payVo.getPayValue() != totalFee) {
            reqResult.setCode(ResultCode.ERRORCODE.ORDER_MONEY_MOTIFY);
            return reqResult;
        }

        payVo.setRemark(PayWayType.getChinaName(payVo.getPayWay()) + "|" + MoneyChange.SIGNUP_FEE.getDesc());

        InsuranceOrder insuranceOrder = null;
        if(payVo.getInsuranceId()!=null && !payVo.getInsuranceId().equals("")){
        	insuranceOrder = orderService.searchInsuranceByOrderId(payVo.getInsuranceId());
		}
        // 记录流水
        PayLogVo payLogVo = new PayLogVo();
        payLogVo.setCouponid(payVo.getCouponId());
        payLogVo.setCouponmoney(0.0);
        payLogVo.setOrderid(payVo.getPayOrderId());
        // 增加保险单的orderId记录到t_log_pay
        if (insuranceOrder != null) payLogVo.setOrderid(payLogVo.getOrderid() + "," + payVo.getInsuranceId());
        payLogVo.setPaymoney((double) payVo.getPayValue());
        payLogVo.setPaytime(endTime);
        payLogVo.setPayway(payVo.getPayWay());
        payLogVo.setWaternum(waterNum);
        payLogVo.setStudentid(payVo.getUserId());
        logService.logPay(payLogVo);

        //到这里已经支付成功。如果是报名订单，且有使用优惠券，则设置优惠券已使用
        if (payVo.getCouponId() != 0) {
            OrderVo orderVo = new OrderVo();
            orderVo.setStudentId(payVo.getUserId());
            orderVo.setCoupon(payVo.getCouponId());
            couponService.useCouponWithoutCheck(orderVo);
        }
        
        try {
        	// 记录保单资金流水
            if (insuranceOrder != null) { 
            	PayVo payVoInsur = new PayVo(
            			payVo.getUserId(), 
            			payVo.getUserType(), 
            			payVo.getPayValue(), 
            			payVo.getPayWay(), 
            			payVo.getPayOrderId(),
            			payVo.getPayPurpose().getType(), 
            			payVo.getCouponId(), 
            			payVo.getClientVer().getType(), 
            			PayWayType.getChinaName(payVo.getPayWay()) + "|" + MoneyChange.INSURANCE_FEE.getDesc(), 
            			payVo.getInsuranceId());
            	//把合并支付的金额减去保险费，得到真正的报名费金额
            	payVo.setPayValue(payVo.getPayValue() - insuranceOrder.getPrice());
            	payVoInsur.setPayOrderId(payVo.getInsuranceId());
            	payVoInsur.setPayValue(insuranceOrder.getPrice());
            	moneyManager.handleInsuranceMoneyFlow(payVoInsur);
            	// 修改保单支付状态
            	orderService.updatePayState(payVo.getInsuranceId(), ReqConstants.STAGE_STATE_SUCC,payVo.getPayWay());
            }
            // 记录资金流水
            moneyManager.handleSignupMoneyFlow(payVo);
            
			// 修改报名费支付状态：0代表未支付，1代表已支付,2代表支付失败，9代表老学员自动支付'
			schoolManager.postPayState(payVo, ReqConstants.STAGE_STATE_SUCC);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        // 去掉标记
        redisUtil.delete(REDISKEY.PRE_PAY_ORDER + payVo.getPayOrderId());

        return ReqResult.getSuccess();
    }

    @Override
    public Object purposeAdvance(PayVo payVo, ReqResult reqResult) {
        if (payVo.getUserType() != OrderConstant.USETYPE.STUDENT) {
            reqResult.setCode(ERRORCODE.PAY_SIGNUP_IS_NOT_STUDENT);
            return null;
        }

        if (payVo.getPayValue() <= 0) {
            reqResult.setCode(ResultCode.ERRORCODE.PAY_VALUE_IS_NOT_CORRECT);
            return null;
        }

        //如果已经付过款
        int payState = schoolManager.getPayState(payVo.getPayOrderId());
        if (payState == ReqConstants.STAGE_STATE_SUCC) {
            reqResult.setCode(ResultCode.ERRORCODE.PAY_ORDER_HAVE_PAY);
            return null;
        }
        return 0;
    }

}
