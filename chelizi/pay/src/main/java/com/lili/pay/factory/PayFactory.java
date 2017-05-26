/**
 * 
 */
package com.lili.pay.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.pay.action.PayAction;
import com.lili.pay.vo.PayWayType;

/**
 * @author linbo
 *
 */
public class PayFactory
{
    private static Logger logger = LoggerFactory.getLogger(PayFactory.class);
    
    //================发起支付=============================//
    //余额支付
    @Autowired
    private PayAction balancePayAction;
    //课时券支付
    @Autowired
    private PayAction couponPayAction;
    //QQ钱包支付
    @Autowired
    private PayAction qqPayAction;
    //微信支付
    @Autowired
    private PayAction wxPayAction;
    //银联支付
    @Autowired
    private PayAction ylPayAction;
    //支付宝支付
    @Autowired
    private PayAction zfbPayAction;
    
    /**
     * 拿到发起支付接口的接口
     * @param payWay
     * @return
     */
    public PayAction getPayAction(String payWay)
    {
        PayAction payAction = wxPayAction;
        try
        {
            switch (payWay)
            {
            case PayWayType.BALANCE:
                payAction = balancePayAction;
                break;
            case PayWayType.COUPON:
                payAction = couponPayAction;
                break;
            case PayWayType.QQWALLET:
                payAction = qqPayAction;
                break;
            case PayWayType.WX:
                payAction = wxPayAction;
                break;
            case PayWayType.YL:
                payAction = ylPayAction;
                break;
            case PayWayType.ZFB:
                payAction = zfbPayAction;
                break;
            default:
                break;
            }
            return payAction;
        }
        catch (Exception e)
        {
            logger.error("payWay:" + payWay ,e);
        }
        return payAction;
    }
}
