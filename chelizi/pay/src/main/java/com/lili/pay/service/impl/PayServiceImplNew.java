/**
 *
 */
package com.lili.pay.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.redisson.RedissonClient;
import org.redisson.core.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.authcode.service.EmailService;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.StringUtil;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.coupon.service.CouponService;
import com.lili.order.dto.InsuranceOrder;
import com.lili.order.service.OrderService;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.pay.action.WXPayAction;
import com.lili.pay.factory.PayFactory;
import com.lili.pay.service.PayServiceNew;
import com.lili.pay.vo.PayVo;
import com.lili.pay.vo.PayWayType;
import com.lili.pay.vo.PurposeType;
import com.lili.pay.vo.QQPayCallbackResData;
import com.lili.school.dto.WechatEnrollOrder;
import com.lili.school.manager.SchoolManager;
import com.lili.school.vo.EnrollOrderVo;
import com.lili.student.dto.Student;
import com.lili.student.dto.VipChargeDiscount;
import com.lili.student.service.StudentService;
import com.lili.student.service.StudentVipService;

/**
 * @author linbo
 *         重构一版支付
 */
public class PayServiceImplNew implements PayServiceNew {
    private Logger logger = LoggerFactory.getLogger(PayServiceImplNew.class);

    @Autowired
    private PayFactory payFactory;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    protected RedisUtil redisUtil;

    @Autowired
    private OrderService orderService;
    @Autowired
    private SchoolManager schoolManager;
    @Autowired
    private CouponService couponService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentVipService studentVipService;
    @Autowired
    private EmailService emailService;
    
    @Override
    public ReqResult pay(PayVo pay) {
        long t1 = System.currentTimeMillis();
        ReqResult r = ReqResult.getFailed();
        Long userId = pay.getUserId();
        RLock lock = null;
        try {
            lock = redissonClient.getLock(RedisKeys.REDISKEY.LOCK_PRE + "pay." + userId);
            boolean hasLock = lock.tryLock(1, 10, TimeUnit.SECONDS);//1s等待，10s超时，防止死锁

            if (hasLock) {
            	logger.debug(pay.toString());
            	
                if (pay.getPayPurpose() == PurposeType.COURSE //课程支付
                        && (pay.getPayOrderId() != null)) {

                    //20160505根据订单id，重新设置带支付金额，不相信客户端传的金额
                    OrderVo ov = orderService.queryOrderById(pay.getPayOrderId(), new OrderQuery());
                    if (null != ov && ov.getPayTotal() != null && ov.getPayTotal() != 0) {
                        pay.setCouponId(ov.getCoupon() == null ? 0 : ov.getCoupon());
                        pay.setPayValue(ov.getPayTotal() == null ? 0 : ov.getPayTotal());
                        pay.setDiscountMoney(ov.getCouponTotal());//在下订单的时候,已经计算过优惠券的优惠金额了
                    }
                } 
                else if (pay.getPayPurpose() == PurposeType.SIGNUP //报名支付
                        && (pay.getPayOrderId() != null)) {

                    //根据报名订单,重新获取支付金额等信息
                    ReqResult tmp = schoolManager.getEnrollOrder(null, null, pay.getPayOrderId());
                    if (tmp != null && tmp.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)) {
                        EnrollOrderVo eov = (EnrollOrderVo) tmp.getResult().get(ResultCode.RESULTKEY.DATA);
                        
                        int price_total = eov.getPrice();
                        //20161115报名添加平安保险
                        if(pay.getInsuranceId()!=null && !pay.getInsuranceId().equals("")){
                        	InsuranceOrder insuranceOrder=orderService.searchInsuranceByOrderId(pay.getInsuranceId());
                        	if(insuranceOrder!=null){
                        		price_total+=insuranceOrder.getPrice();
                        	}
                        }

                        if (pay.getCouponId() != 0) { //如果有优惠券，需要先减去优惠券的价格
                        	logger.debug("****************************************** Pay For CouponId :" + pay.getCouponId());
                            //参数通过vo传递
                            OrderVo orderVo = new OrderVo();
                            orderVo.setOrderId(pay.getPayOrderId());
                            orderVo.setOtype(OrderConstant.OTYPE.ENROLL_ORDER);
                            orderVo.setStudentId(pay.getUserId());
                            orderVo.setClzNum(1);//无意义，兼容之前的费用计算
                            orderVo.setPrice(price_total);
                            orderVo.setCoupon(pay.getCouponId());
                            orderVo.setCityId(eov.getCityId());

                            //检查优惠券能否使用。先检查，支付成功后再使用。
                            int money = couponService.useCoupon(pay.getCouponId(), orderVo, false);

                            // 优惠券无法使用
                            if (money < 0) {
                                r.setCode(ResultCode.ERRORCODE.ORDER_COUPON_CANNOTUSE);
                                r.setMsgInfo(ResultCode.ERRORINFO.ORDER_COUPON_CANNOTUSE);
                                return r;
                            } else {
                                logger.debug("PayServiceImplNew useCoupon OK! studentId=" + userId + ",couponId=" + pay.getCouponId());

                                if (price_total > money) {//还需要部分支付
                                    pay.setPayValue(price_total - money);
                                    pay.setDiscountMoney(money);

                                } else { // 无需付钱则设置为已经支付。//TODO need cheek
                                    pay.setPayValue(1);	//20160707因为客户端会调起支付页面，则需要用户支付1分钱，不然会报金额异常
                                    pay.setDiscountMoney(price_total -1);
                                }
                            }
                        } else {//没有使用优惠券
                            pay.setPayValue(price_total);
                        }
                    }
                } 
                else if (pay.getPayPurpose() == PurposeType.CHARGE   && (pay.getPayOrderId() != null)) { //充值支付
                    if (pay.getUserType() == OrderConstant.USETYPE.STUDENT) {
                        //检查是否有充值优惠
                        Student student = studentService.getStudent(pay.getUserId());
                        if (null != student && student.getVipId() != null) {
                            //计算是否需要送金额
                            String vipPackageId = student.getVipPackageId();
                            if (StringUtil.isNotNullAndNotEmpty(vipPackageId)) {
                                List<VipChargeDiscount> chargeDiscouts = studentVipService.getChargeDiscountListByPackageId(vipPackageId);
                                if (null != chargeDiscouts && chargeDiscouts.size() > 0) {
                                    int payMoney = pay.getPayValue();
                                    int maxDiscount = 0;
                                    for (VipChargeDiscount cd : chargeDiscouts) {
                                        if (payMoney == cd.getLimitMoney()) {
                                            maxDiscount = cd.getDiscount();
                                            break;
                                        }
                                    }
                                    pay.setDiscountMoney(maxDiscount);//充值送金额
                                }
                            }
                        }
                    }
                } 
                else if (pay.getPayPurpose() == PurposeType.WXSIGNUP //微信报名支付
                        && (pay.getPayOrderId() != null)) {
                    //根据报名订单,重新获取支付金额等信息
                	WechatEnrollOrder wOrder = schoolManager.getWechatOrder(pay.getPayOrderId());
                    if (wOrder != null) {
                        int price_total = wOrder.getPrice();
                        pay.setPayValue(price_total);
                    }
                } 
                else if (pay.getPayPurpose() == PurposeType.SCHOOLSIGNUP  && (pay.getPayOrderId() != null)) { //找驾校报名支付
                	//根据报名订单,重新获取支付金额等信息
                    ReqResult tmp = schoolManager.getEnrollOrder(null, null, pay.getPayOrderId());
                    if (tmp != null && tmp.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)) {
                        EnrollOrderVo eov = (EnrollOrderVo) tmp.getResult().get(ResultCode.RESULTKEY.DATA);
                        int price_total = eov.getPrice();
                        pay.setPayValue(price_total);
                    }
                } 
                else if (pay.getPayPurpose() == PurposeType.INSURANCE //平安保险报名支付
                        && (pay.getPayOrderId() != null)) {
                    //根据报名订单,重新获取支付金额等信息
                	InsuranceOrder order = orderService.searchInsuranceByOrderId(pay.getPayOrderId());
                    if (order != null) {
                        int price_total = order.getPrice();
                        pay.setPayValue(price_total);
                    }
                } 
                else if(pay.getPayPurpose() == PurposeType.EXAMPLACE) { 
                	//20160920约考场费用支付。多个订单合并支付时，id过长会导致第三方支付失败
                	//20161024微信商户订单号最多32个字符。为了方便财务对账，当有两个订单合并支付时，将其订单号拆分合并成32个字符。
                	String orderIdShort = pay.getPayOrderId();
                	if(pay.getPayOrderId().contains(",")){
                		String[] tmp = pay.getPayOrderId().split(",");
                		//orderIdShort = tmp[0].substring(0, 15) + "-" +tmp[1].substring(0, 15);
                		orderIdShort = tmp[0]; //20161117财务要求还是只保留一个订单号
                	}                	
                	redisUtil.set(REDISKEY.PRE_PAY_ORDER_SHORT + orderIdShort, pay.getPayOrderId(), 24 * 60 * 60 * 3);    
                	pay.setPayOrderId(orderIdShort);
                	
                } 
                else if (pay.getPayPurpose() == PurposeType.WXCOACH){ //喱喱教练公众号报名费
                	logger.info("************************************* WXCOACH pay do nothing ");
                }
                

                // 校验一下参数
                if (pay.getPayPurpose() != PurposeType.COURSE
                        && (pay.getPayOrderId() == null || pay.getPayOrderId().isEmpty())) {
                    //生成一个订单ID
                    pay.setPayOrderId(StringUtil.getOrderId());
                }

                // 客户端有时候没传remark
                if (pay.getRemark() == null || pay.getRemark().isEmpty()) {
                    pay.setRemark(pay.getPayPurpose().getDesc());
                }

                payFactory.getPayAction(pay.getPayWay()).doPayAction(pay, r);

            } else {
                logger.error("PayServiceImplNew error! studentId=" + userId + " add pay LOCK ERROR.");
                r.setCode(ResultCode.ERRORCODE.ORDER_LOCK);
                r.setMsgInfo(ResultCode.ERRORINFO.ORDER_LOCK);
            }
        } catch (Exception e) {
            logger.error("PayServiceImplNew error! payVo:" + pay + " " + e);
            //20160727 用户支付异常是大事，需要邮件给开发，及时定位
            emailService.send("【系统】[用户支付异常啦，请抓紧时间处理！！]", ""+pay+"|Exception:"+e);
        } finally {
            if (lock != null) {
                try {
                    lock.unlock();
                } catch (Exception e) {
                }
            }
        }

        long t2 = System.currentTimeMillis();
        logger.debug("PayServiceImplNew time takes " + (t2 - t1));

        return r;
    }

    public ReqResult wxPayCallback(String result) {
        ReqResult r = ReqResult.getFailed();
        try {
            r = payFactory.getPayAction(PayWayType.WX).payCallBack(result);
        } catch (Exception e) {
            logger.error("result:" + result, e);
        }
        return r;
    }

    public ReqResult zfbPayCallback(Map<String, String> params) {
        ReqResult r = ReqResult.getFailed();
        try {
            r = payFactory.getPayAction(PayWayType.ZFB).payCallBack(params);
        } catch (Exception e) {
            logger.error("result:" + params, e);
        }
        return r;
    }

    public ReqResult qqPayCallback(QQPayCallbackResData result) {
        ReqResult r = ReqResult.getFailed();
        try {
            r = payFactory.getPayAction(PayWayType.QQWALLET).payCallBack(result);
        } catch (Exception e) {
            logger.error("result:" + result, e);
        }
        return r;
    }

    public ReqResult ylPayCallback(Map<String, String> reqParam, String encoding) {
        ReqResult r = ReqResult.getFailed();
        try {
            r = payFactory.getPayAction(PayWayType.YL).payCallBack(reqParam, encoding);
        } catch (Exception e) {
            logger.error("result:" + reqParam.toString(), e);
        }
        return r;
    }

    @Override
    public String getOpenId(String code) {
        return WXPayAction.getOpenId(code);
    }
}
