/**
 * 
 */
package com.lili.pay.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.lili.coach.manager.CoachManager;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.coupon.service.CouponService;
import com.lili.log.service.LogService;
import com.lili.order.service.OrderService;
import com.lili.pay.config.ExamPayConfig;
import com.lili.pay.config.WXPayConfig;
import com.lili.pay.config.ZFBPayConfig;
import com.lili.pay.mapper.dao.PerformanceDtoMapper;
import com.lili.pay.mapper.dao.UserMoneyDtoMapper;
import com.lili.pay.purpose.IPayPurpose;
import com.lili.pay.vo.PayVo;
import com.lili.pay.vo.PurposeType;
import com.lili.qqpay.sdk.QQPayConfig;
import com.lili.student.service.RechargeService;

/**
 * @author linbo
 *
 */
public abstract class PayAction
{
    protected static final Logger logger = LoggerFactory.getLogger(PayAction.class);

    @Autowired
    protected CouponService couponService;

    @Autowired
    protected OrderService orderService;

    @Autowired
    protected WXPayConfig wxPayConfig;
    @Autowired
    protected ExamPayConfig examPayConfig;

    @Autowired
    protected RedisUtil redisUtil;

    @Autowired
    protected LogService logService;

    @Autowired
    protected CoachManager coachManager;

    @Autowired
    protected ZFBPayConfig zfbPayConfig;

    @Autowired
    protected DefaultMQProducer jpushProducer;

    @Autowired
    protected UserMoneyDtoMapper userMoneyDtoMapper;

    @Autowired
    protected PerformanceDtoMapper performanceDtoMapper;

    @Autowired
    protected QQPayConfig qqPayConfig;
    
    @Autowired
    protected RechargeService rechargeService;
    
    @Resource(name="payRehandleProducer")
    DefaultMQProducer payRehandleProducer;

    /**
     * 进行下单行为时,进行付款前的预处理
     * @param payVo
     * @param reqResult
     * @throws Exception
     */
    public abstract void doPayAction(PayVo payVo, ReqResult reqResult) throws Exception;

    /**
     * 当付款结束时,进行回调处理
     * @param callbackParam
     * @return
     */
    public abstract ReqResult payCallBack(Object ... callbackParam);
    
    
    
    //==============支付成功的动作===================================//
    //支付目的为购买(报名指标)
    @Autowired
    private IPayPurpose signupPurpose;
    //支付目的为购买(微信报名指标)
    @Autowired
    private IPayPurpose wxSignupPurpose;
    //支付目的为购买(找驾校)
    @Autowired
    private IPayPurpose schoolSignupPurpose;
    //支付目的为充值
    @Autowired
    private IPayPurpose chargePurpose;
    //支付目的为支付课时费
    @Autowired
    private IPayPurpose coursePurpose;
    //微信公众号活动充值
    @Autowired
    private IPayPurpose wxActivityPurpose;
    @Autowired
    private IPayPurpose examPlacePurpose;
    @Autowired
    private IPayPurpose insurancePurpose;
    @Autowired
    private IPayPurpose wxCoachPurpose;
    /**
     * 拿到充值最终目的接口来做最终的动作
     * @param purposeType
     * @return
     */
    public IPayPurpose getPayPurpose(PurposeType purposeType)
    {
        IPayPurpose purpose = coursePurpose;
        try
        {
            switch (purposeType)
            {
            case COURSE:
                purpose = coursePurpose;
                break;
            case CHARGE:
                purpose = chargePurpose;
                break;
            case SIGNUP:
                purpose = signupPurpose;
                break;
            case WXSIGNUP:
                purpose = wxSignupPurpose;
                break;
            case WXACTIVITY:
                purpose = wxActivityPurpose;
                break;
            case EXAMPLACE:
            	purpose = examPlacePurpose;
            	break;
            case INSURANCE:
            	purpose = insurancePurpose;
            	break;
            case WXCOACH:
            	purpose = wxCoachPurpose;
            	break;
            case SCHOOLSIGNUP:
            	purpose = schoolSignupPurpose;
            	break;
            default:
                break;
            }
        }
        catch (Exception e)
        {
            logger.error("purposeType:" + purposeType, e);
        }
        return purpose;
    }
}
