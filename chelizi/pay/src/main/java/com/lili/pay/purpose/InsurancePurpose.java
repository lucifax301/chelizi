package com.lili.pay.purpose;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.constant.MoneyChange;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.common.vo.ResultCode.ERRORCODE;
import com.lili.log.service.LogService;
import com.lili.log.vo.PayLogVo;
import com.lili.order.dto.InsuranceOrder;
import com.lili.order.service.OrderService;
import com.lili.pay.manager.MoneyManager;
import com.lili.pay.vo.PayVo;
import com.lili.pay.vo.PayWayType;

public class InsurancePurpose implements IPayPurpose {
    @Autowired
    private LogService logService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MoneyManager moneyManager;
    
    @Override
    public ReqResult doPurpose(PayVo payVo, Date endTime, String waterNum, int totalFee) {
        ReqResult reqResult = ReqResult.getFailed();

        // 验证金额
        if (payVo.getPayValue() != totalFee) {
            reqResult.setCode(ResultCode.ERRORCODE.ORDER_MONEY_MOTIFY);
            return reqResult;
        }

        payVo.setRemark(PayWayType.getChinaName(payVo.getPayWay()) + "|" + MoneyChange.SIGNUP_FEE.getDesc());

        // 记录流水
        PayLogVo payLogVo = new PayLogVo();
        payLogVo.setCouponid(payVo.getCouponId());
        payLogVo.setCouponmoney(0.0);
        payLogVo.setOrderid(payVo.getPayOrderId());
        payLogVo.setPaymoney((double) payVo.getPayValue());
        payLogVo.setPaytime(endTime);
        payLogVo.setPayway(payVo.getPayWay());
        payLogVo.setWaternum(waterNum);
        payLogVo.setStudentid(payVo.getUserId());
        logService.logPay(payLogVo);

     // 记录资金流水
        moneyManager.handleInsuranceMoneyFlow(payVo);
        try {
			// 修改保险费支付状态
        	orderService.updatePayState(payVo.getPayOrderId(),ReqConstants.STAGE_STATE_SUCC,payVo.getPayWay());
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
        InsuranceOrder order =orderService.searchInsuranceByOrderId(payVo.getPayOrderId());
        if (order.getPayState() == ReqConstants.STAGE_STATE_SUCC) {
            reqResult.setCode(ResultCode.ERRORCODE.PAY_ORDER_HAVE_PAY);
            return null;
        }
        return 0;
    }

}
