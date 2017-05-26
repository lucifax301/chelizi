/**
 *
 */
package com.lili.pay.purpose;

import com.lili.common.constant.OrderConstant;
import com.lili.common.constant.OrderConstant.PAYSTATE;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.log.service.LogService;
import com.lili.log.vo.PayLogVo;
import com.lili.order.service.OrderService;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.pay.manager.MoneyManager;
import com.lili.pay.vo.PayVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author linbo
 *         做支付动作
 */
public class CoursePurpose implements IPayPurpose {
    private static Logger logger = LoggerFactory.getLogger(CoursePurpose.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private LogService logService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MoneyManager moneyManager;

    @Override
    public ReqResult doPurpose(PayVo payVo, Date endTime, String waterNum, int totalFee) {
        ReqResult reqResult = ReqResult.getFailed();

        // 先取到订单
        OrderVo orderVo;
        try {
            orderVo = orderService.queryOrderById(payVo.getPayOrderId(), new OrderQuery());
            if (orderVo == null) {
                logger.error("orderVo is null, orderId:" + payVo.getPayOrderId(),
                        new NullPointerException());
                reqResult.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
                return reqResult;
            }
        } catch (Exception e) {
            logger.error("orderService cause exception, orderId:" + payVo.getPayOrderId(), e);
            return reqResult;
        }


        // 判断价格
        int realPrice = payVo.getPayValue();
        if (realPrice != totalFee) {
            reqResult.setCode(ResultCode.ERRORCODE.ORDER_MONEY_MOTIFY);
            return reqResult;
        }

        // 消耗优惠券
        double discount = 0;
        long studentCouponId = payVo.getCouponId();
        if (studentCouponId != 0) {
            discount = payVo.getDiscountMoney();
        }

        try {
            reqResult = doPayAction(orderVo, studentCouponId, payVo.getPayWay(), discount, waterNum, endTime);
        } catch (Exception e1) {
            logger.error("error === orderVo: " + orderVo + "|waterNum: " + waterNum + "payVo: " + payVo, e1);
        }

        // 去掉标记
        redisUtil.delete(REDISKEY.PRE_PAY_ORDER + payVo.getPayOrderId());

        return reqResult;
    }

    @Override
    public Object purposeAdvance(PayVo payVo, ReqResult reqResult) throws Exception {
        OrderVo orderVo = orderService.queryOrderById(payVo.getPayOrderId(), new OrderQuery());
        if (orderVo == null) {
            reqResult.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.ORDER_NOTEXIST);
            return null;
        }

        // 如果订单已经支付了
        if (orderVo.getPayId() != null && orderVo.getPayId() != 0 && orderVo.getPayState() != null
                && (orderVo.getPayState() == PAYSTATE.HASPAY || orderVo.getPayState() == PAYSTATE.AUTOPAYE)) {
            reqResult.setCode(ResultCode.ERRORCODE.PAY_ORDER_HAVE_PAY);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.PAY_ORDER_HAVE_PAY);
            return null;
        }

        // 测试支付数额
        if (orderVo.getPayTotal() != payVo.getPayValue()) {
            reqResult.setCode(ResultCode.ERRORCODE.PAY_VALUE_IS_NOT_CORRECT);
            return null;
        }
        return 0;
    }

    public ReqResult doPayAction(OrderVo orderVo, long couponId, String payType, double discount, String waterNum, Date payTime) throws Exception {
        ReqResult reqResult = ReqResult.getFailed();

        if (orderVo.getPayId() != null && orderVo.getPayId() != 0 && orderVo.getPayState() != null
                && (((orderVo.getPayState() == PAYSTATE.HASPAY) && couponId == 0) || orderVo.getPayState() == PAYSTATE.AUTOPAYE)) {

            reqResult = ReqResult.getSuccess();
            return reqResult;
        }

        int totalPay = orderVo.getPayTotal();
        String orderId = orderVo.getOrderId();
        long studentId = orderVo.getStudentId();

        // 记录日志
        PayLogVo payLogVo = new PayLogVo();
        payLogVo.setCoachid(orderVo.getCoachId());
        payLogVo.setCouponid(couponId);
        payLogVo.setCouponmoney(discount);
        payLogVo.setOrderid(orderId);
        payLogVo.setPaymoney((double) totalPay);
        payLogVo.setPaytime(payTime);
        payLogVo.setPayway(payType);
        payLogVo.setStudentid(studentId);
        payLogVo.setWaternum(waterNum);
        int payId = logService.logPay(payLogVo);// 记录支付日志


        int oldPayState = orderVo.getPayState();

        orderVo.setPayId(payId);
        orderVo.setPayTime(payTime);
        orderVo.setPayState(OrderConstant.PAYSTATE.HASPAY);
        //orderVo.setCheckOutState(OrderConstant.CHECKOUTSTATE.HAS_CHECKOUT); //在资金结算完成后，再记录结算成功

        if (orderService.updateByOrderId(orderVo, orderId) > 0) {
            // 记录资金变动
            moneyManager.handleOrderMoneyFlow(orderVo, oldPayState, payType);
        }


        // 只要日志库有了记录就没事，就可以认为成功
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);


        return reqResult;
    }


}
