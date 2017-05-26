/**
 *
 */
package com.lili.pay.purpose;

import com.lili.common.constant.MoneyChange;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.log.service.LogService;
import com.lili.log.vo.PayLogVo;
import com.lili.pay.manager.MoneyManager;
import com.lili.pay.vo.PayVo;
import com.lili.pay.vo.PayWayType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author linbo
 *         做充值动作
 */
public class ChargePurpose implements IPayPurpose {
    private Logger logger = LoggerFactory.getLogger(ChargePurpose.class);

    @Autowired
    private LogService logService;

    @Autowired
    private MoneyManager moneyManager;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ReqResult doPurpose(PayVo payVo, Date endTime, String waterNum, int totalFee) {
        ReqResult reqResult = ReqResult.getFailed();

        // 验证金额
        if (payVo.getPayValue() != totalFee) {
            reqResult.setCode(ResultCode.ERRORCODE.ORDER_MONEY_MOTIFY);
            return reqResult;
        }

        //设置备注
        payVo.setRemark(PayWayType.getChinaName(payVo.getPayWay()) + MoneyChange.CHARGE.getDesc());

        // 记录流水
        PayLogVo payLogVo = new PayLogVo();
        payLogVo.setCouponid(payVo.getCouponId());
        payLogVo.setCouponmoney(payVo.getDiscountMoney() / 100D);
        payLogVo.setOrderid(payVo.getPayOrderId());
        payLogVo.setPaymoney((double) payVo.getPayValue());
        payLogVo.setPaytime(endTime);
        payLogVo.setPayway(payVo.getPayWay());
        payLogVo.setWaternum(waterNum);

        // 充值
        if (payVo.getUserType() == OrderConstant.USETYPE.COACH) {
            //记录流水
            payLogVo.setCoachid(payVo.getUserId());
            logService.logPay(payLogVo);

        } else if (payVo.getUserType() == OrderConstant.USETYPE.STUDENT) {

            //记录流水
            payLogVo.setStudentid(payVo.getUserId());
            logService.logPay(payLogVo);
        } else {
            logger.error("userType is not equal 1 or 2");
            return reqResult;
        }

        // 记录资金流水
        moneyManager.handleChargeMoneyFlow(payVo);

        // 去掉标记
        redisUtil.delete(REDISKEY.PRE_PAY_ORDER + payVo.getPayOrderId());
        return ReqResult.getSuccess();
    }

    @Override
    public Object purposeAdvance(PayVo payVo, ReqResult reqResult) {
        if (payVo.getPayValue() <= 0) {
            reqResult.setCode(ResultCode.ERRORCODE.PAY_VALUE_IS_NOT_CORRECT);
            return null;
        }
        return 0;
    }
}
