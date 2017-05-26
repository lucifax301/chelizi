/**
 *
 */
package com.lili.pay.action;

import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.pay.vo.PayVo;
import com.lili.pay.vo.PurposeType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author linbo
 *         课时券支付
 */
public class CouponPayAction extends PayAction {
    @Autowired
    protected RedisUtil redisUtil;

    @Override
    public void doPayAction(PayVo payVo, ReqResult reqResult) {
        //课时券是不能用来充值和支付报名费的
        if (payVo.getPayPurpose() == PurposeType.CHARGE || payVo.getPayPurpose() == PurposeType.SIGNUP) {
            return;
        }

        //走到这一步的时候优惠券已经使用完毕，此时只需要给教练加业绩就可以
        ReqResult req = getPayPurpose(payVo.getPayPurpose())
                .doPurpose(payVo, new Date(), "coupon", payVo.getPayValue());

        reqResult.setCode((String) req.getResult().get(ResultCode.RESULTKEY.CODE));
        reqResult.setMsgInfo((String) req.getResult().get(ResultCode.RESULTKEY.MSGINFO));

    }

    @Override
    public ReqResult payCallBack(Object... callbackParam) {
        //优惠券支付没有第三方支付的回调流程
        return null;
    }

}
