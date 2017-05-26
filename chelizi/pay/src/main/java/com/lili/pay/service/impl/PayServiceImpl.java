/**
 *
 */
package com.lili.pay.service.impl;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.alipay.config.AlipayConfig;
import com.alipay.sign.ZFBPayDemo;
import com.alipay.util.AlipayNotify;
import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CoachManager;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.MoneyChange;
import com.lili.common.constant.OrderConstant;
import com.lili.common.constant.OrderConstant.PAYSTATE;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.common.vo.ResultCode.RESULTKEY;
import com.lili.log.service.LogService;
import com.lili.log.vo.PayLogVo;
import com.lili.order.service.OrderService;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.pay.action.WXPayAction;
import com.lili.pay.config.WXPayConfig;
import com.lili.pay.config.ZFBPayConfig;
import com.lili.pay.dto.PerformanceDto;
import com.lili.pay.dto.UserMoneyDto;
import com.lili.pay.manager.MoneyManager;
import com.lili.pay.manager.MoneyManagerImpl;
import com.lili.pay.mapper.dao.PerformanceDtoMapper;
import com.lili.pay.mapper.dao.UserMoneyDtoMapper;
import com.lili.pay.service.PayService;
import com.lili.pay.vo.*;
import com.lili.pay.wallet.MoneyPage;
import com.lili.pay.wallet.PerformancePage;
import com.lili.qqpay.sdk.QQPayBussiness;
import com.lili.qqpay.sdk.QQPayConfig;
import com.lili.qqpay.sdk.QQPayInitReq;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;
import com.tencent.WXPay;
import com.tencent.business.ResultListener;
import com.tencent.common.Configure;
import com.tencent.common.Util;
import com.tencent.protocol.callback.protocol.PayCallbackResData;
import com.tencent.protocol.callback.protocol.ReturnToWXReq;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryResData;
import com.tencent.protocol.reverse_protocol.ReverseResData;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderReqData;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderResData;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author linbo
 *         支付服务
 */
public class PayServiceImpl implements PayService
{
    private static final Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private WXPayConfig wxPayConfig;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LogService logService;

    @Autowired
    private StudentManager studentManager;

    @Autowired
    private CoachManager coachManager;

    @Autowired
    private ZFBPayConfig zfbPayConfig;

    @Autowired
    private DefaultMQProducer jpushProducer;

    @Autowired
    private UserMoneyDtoMapper userMoneyDtoMapper;

    @Autowired
    private PerformanceDtoMapper performanceDtoMapper;

    @Autowired
    private QQPayConfig qqPayConfig;

    @Autowired
    private MoneyManager moneyManager;

    @PostConstruct
    public void init()
    {
        initWXConfig();
        initZFBConfig();
    }

    private void initZFBConfig()
    {
        // 初始化支付宝配置
        AlipayConfig.setAli_public_key(zfbPayConfig.getAli_public_key());
        AlipayConfig.setInput_charset(zfbPayConfig.getInput_charset());
        AlipayConfig.setLog_path(zfbPayConfig.getLog_path());
        AlipayConfig.setPartner(zfbPayConfig.getPartner());
        AlipayConfig.setSign_type(zfbPayConfig.getSign_type());
        AlipayConfig.setCallback_url(zfbPayConfig.getCallback_url());
        AlipayConfig.setCharge_callBackUrl(zfbPayConfig.getChargeCallback_url());
    }

    private void initWXConfig()
    {
        // 初始化微信SDK
        WXPay.initSDKConfiguration("",
                wxPayConfig.getCertLocalPath(), wxPayConfig.getCertPassword(), wxPayConfig.getIp(),
                wxPayConfig.getNotifyUrl(),
                wxPayConfig.getTradeType(), wxPayConfig.isUseThreadToDoReport());
    }

    /*
     * (non-Javadoc)
     *
     * @see com.lili.pay.service.PayService#pay(java.lang.String, java.lang.String, int)
     */
    @Override
    public ReqResult pay(String orderId, long studentCouponId, long studentId, String payWay, String tokenId)
    {
        ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);

        if (!redisUtil.isExist(REDISKEY.STUDENT_TOKEN + studentId, tokenId))
        {
            reqResult.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
            return reqResult;
        }
        try
        {
            OrderVo orderVo = orderService.queryOrderById(orderId, new OrderQuery());
            if (orderVo == null)
            {
                reqResult.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
                reqResult.setMsgInfo(ResultCode.ERRORINFO.ORDER_NOTEXIST);
                return reqResult;
            }
            if (payWay != null && payWay.equals(PayWayType.WX))
            {
                wxPay(orderVo, studentCouponId, reqResult);
            }
            else if (payWay != null && payWay.equals(PayWayType.ZFB))
            {
                zfbPay(orderVo, studentCouponId, reqResult);
            }
            else if (payWay != null && payWay.equals(PayWayType.BALANCE))
            {
                balancePay(orderVo, studentCouponId, reqResult);
            }
            else if(payWay != null && payWay.equals(PayWayType.QQWALLET))
            {
                qqPay(orderVo, studentCouponId, reqResult);
            }
            else
            {
                reqResult.setCode(ResultCode.ERRORCODE.FAILED);
                reqResult.setMsgInfo(ResultCode.ERRORINFO.ORDER_PAYWAY_ISNULL);
            }
        }
        catch (Exception e)
        {
            logger.error("pay error, orderId:" + orderId + "|payWay:" + payWay + "|studentCouponId:"
                    + studentCouponId,
                    e);
        }
        return reqResult;
    }

    /**
     * @param orderVo
     * @param studentCouponId
     * @param reqResult
     */
    private void qqPay(OrderVo orderVo, long studentCouponId, ReqResult reqResult)
    {
        // 代金券能抵扣的数目
        int discount = 0;
        int price = orderVo.getPayTotal() - discount > 0 ? orderVo.getPayTotal() - discount : 0;

        String timeStart = TimeUtil.getDateFormat(new Date(), "yyyyMMddHHmmss");
        String timeEnd = TimeUtil.getDateFormat(TimeUtil.addDate(new Date(), 600000), "yyyyMMddHHmmss");
        QQPayInitReq qqPayInitReq = new QQPayInitReq(qqPayConfig.getMchID(), "喱喱学车付款", orderVo.getOrderId(), price, qqPayConfig.getCallBackUrl(), "attach", timeStart, timeEnd, qqPayConfig.getKey());
        ReqResult r = QQPayBussiness.doQQPayInit(qqPayInitReq,qqPayConfig.getInitUrl());
        reqResult.setCode((String) r.getResult().get(RESULTKEY.CODE));
        reqResult.setData(r.getResult().get(RESULTKEY.DATA));
        if (r.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS))
        {
            // 记录一个缓存到redis
            redisUtil.set(REDISKEY.PRE_PAY_ORDER + orderVo.getOrderId(), orderVo.getOrderId());
        }
    }

    /**
     * 余额支付
     * @param orderVo
     * @param studentCouponId
     * @param reqResult
     */
    private void balancePay(OrderVo orderVo, long studentCouponId, ReqResult reqResult)
    {
        // TODO Auto-generated method stub

    }

    /**
     * @param orderVo
     * @param studentCouponId
     * @param reqResult
     */
    private void zfbPay(OrderVo orderVo, long studentCouponId, ReqResult reqResult)
    {
        // 代金券能抵扣的数目
        int discount = 0;
        int price = orderVo.getPayTotal() - discount > 0 ? orderVo.getPayTotal() - discount : 0;
        String zfbOrderInfo = ZFBPayDemo.pay(orderVo.getOrderId(), "约车订单", "约车订单", (double) (price) / 100, AlipayConfig.callback_url, 0);
        reqResult.setData(zfbOrderInfo);
    }

    /**
     * 微信支付
     *
     * @param orderVo
     * @param studentCouponId
     * @param reqResult
     * @return
     */
    private void wxPay(final OrderVo orderVo, long studentCouponId, final ReqResult reqResult)
    {
        // 代金券能抵扣的数目
        int discount = 0;
        int price = orderVo.getPayTotal() - discount > 0 ? orderVo.getPayTotal() - discount : 0;

        try
        {
            UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData("喱喱学车课时费", studentCouponId + ","
                    + String.valueOf(discount),
                    orderVo.getOrderId(), price,
                    "APP", "127.0.0.1", TimeUtil.getDateFormat(new Date(), "yyyyMMddHHmmss"), TimeUtil.getDateFormat(
                            TimeUtil.addDate(
                                    new Date(), wxPayConfig.getOrderExpire()), "yyyyMMddHHmmss"), Configure.getNotifyUrl(), wxPayConfig.getAppId(0), wxPayConfig.getKey(0), Configure.getTradeType(), "", wxPayConfig.getMchId(0));
            WXPay.doUnifiedOrderBusiness(unifiedOrderReqData, wxPayConfig.getKey(0), wxPayConfig.getMchId(0), new ResultListener()
            {
                @Override
                public void onFailByReturnCodeError(UnifiedOrderResData unifiedOrderResData)
                {
                    reqResult.setCode(ResultCode.ERRORCODE.PARAMERROR);
                    reqResult.setMsgInfo(unifiedOrderResData.getErr_code_des());
                    reqResult.setMsgKey(unifiedOrderResData.getErr_code());
                }

                @Override
                public void onFailByReturnCodeFail(UnifiedOrderResData unifiedOrderResData)
                {
                    reqResult.setCode(ResultCode.ERRORCODE.PARAMERROR);
                    reqResult.setMsgInfo(unifiedOrderResData.getErr_code_des());
                    reqResult.setMsgKey(unifiedOrderResData.getErr_code());
                }

                @Override
                public void onFailBySignInvalid(UnifiedOrderResData unifiedOrderResData)
                {
                    reqResult.setCode(ResultCode.ERRORCODE.ORDER_PAY_SIGN_INVALID);
                    reqResult.setMsgInfo(unifiedOrderResData.getErr_code_des());
                    reqResult.setMsgKey(unifiedOrderResData.getErr_code());
                }

                @Override
                public void onFailByQuerySignInvalid(ScanPayQueryResData scanPayQueryResData)
                {
                    reqResult.setCode(ResultCode.ERRORCODE.ORDER_PAY_SIGN_INVALID);
                    reqResult.setMsgInfo(scanPayQueryResData.getErr_code_des());
                    reqResult.setMsgKey(scanPayQueryResData.getErr_code());
                }

                @Override
                public void onFailByReverseSignInvalid(ReverseResData reverseResData)
                {
                    reqResult.setCode(ResultCode.ERRORCODE.ORDER_PAY_SIGN_INVALID);
                    reqResult.setMsgInfo(reverseResData.getErr_code_des());
                    reqResult.setMsgKey(reverseResData.getErr_code());
                }

                @Override
                public void onFailByMoneyNotEnough(UnifiedOrderResData unifiedOrderResData)
                {
                    reqResult.setCode(ResultCode.ERRORCODE.ORDER_PAY_MONEY_NOTENOUGH);
                    reqResult.setMsgInfo(unifiedOrderResData.getErr_code_des());
                    reqResult.setMsgKey(unifiedOrderResData.getErr_code());
                }

                @Override
                public void onFail(UnifiedOrderResData unifiedOrderResData)
                {
                    reqResult.setCode(ResultCode.ERRORCODE.FAILED);
                    reqResult.setMsgInfo(unifiedOrderResData.getErr_code_des());
                    reqResult.setMsgKey(unifiedOrderResData.getErr_code());
                }

                @Override
                public void onSuccess(UnifiedOrderResData unifiedOrderResData, String prepayId)
                {
                    reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
                    reqResult.setMsgInfo(unifiedOrderResData.getErr_code_des());
                    reqResult.setMsgKey(unifiedOrderResData.getErr_code());

                    PayInfoVo payInfoVo = WXPayAction.genPayInfoVo(unifiedOrderResData, wxPayConfig.getAppId(0), wxPayConfig.getKey(0),wxPayConfig.getMchId(0));
                    reqResult.setData(payInfoVo);

                    // 记录一个缓存到redis
                    redisUtil.set(REDISKEY.PRE_PAY_ORDER + orderVo.getOrderId(), orderVo.getOrderId());
                }

                @Override
                public void onFailByNoAuth(UnifiedOrderResData unifiedOrderResData)
                {
                    logger.error("NOAuth when exe wxPay", new NullPointerException());
                }

                @Override
                public void onFailByOrderClosed(UnifiedOrderResData unifiedOrderResData)
                {
                    reqResult.setCode(ResultCode.ERRORCODE.ORDER_CLOSED);
                    reqResult.setMsgInfo(unifiedOrderResData.getErr_code_des());
                    reqResult.setMsgKey(unifiedOrderResData.getErr_code());
                }

                @Override
                public void onFailBySystemErr(UnifiedOrderResData unifiedOrderResData)
                {
                    reqResult.setCode(ResultCode.ERRORCODE.FAILED);
                    reqResult.setMsgInfo(unifiedOrderResData.getErr_code_des());
                    reqResult.setMsgKey(unifiedOrderResData.getErr_code());
                }
            });
        }
        catch (Exception e)
        {
            logger.error("WXPAY|orderVo:" + orderVo.toString() + "|studendCouponId:" + studentCouponId, e);
        }
        return;
    }


    /*
     * (non-Javadoc)
     *
     * @see com.lili.pay.service.PayService#payCallBack(java.lang.String)
     */
    @Override
    public ReqResult wxPayCallBack(String result)
    {
        PayCallbackResData payCallbackResData = (PayCallbackResData) Util.getObjectFromXML(
                result, PayCallbackResData.class);

        ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.FAILED);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.FAILED);

        if (payCallbackResData.getReturn_code().equals("SUCCESS"))
        {
            reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);

            // 判断标记
            if (redisUtil.isExist(REDISKEY.PRE_PAY_ORDER + payCallbackResData.getOut_trade_no(),
                    payCallbackResData.getOut_trade_no()))
            {
                // 先取到订单
                OrderVo orderVo;
                try
                {
                    orderVo = orderService.queryOrderById(payCallbackResData.getOut_trade_no(), new OrderQuery());
                    if (orderVo == null)
                    {
                        logger.error("orderVo is null, orderId:" + payCallbackResData.getOut_trade_no(),
                                new NullPointerException());
                        genReturnXML(reqResult, "FAIL", payCallbackResData.getErr_code_des());
                        return reqResult;
                    }
                }
                catch (Exception e)
                {
                    logger.error("orderService cause exception, orderId:" + payCallbackResData.getOut_trade_no(),
                            new NullPointerException());
                    genReturnXML(reqResult, "FAIL", payCallbackResData.getErr_code_des());
                    return reqResult;
                }

                // 消耗优惠券(取消)
                long studentCouponId = 0;
                int discount = 0;

                // 判断价格
                int price = orderVo.getPayTotal();
                double realPrice = Double.parseDouble(payCallbackResData.getTotal_fee());
                if (realPrice != price - discount)
                {
                    genReturnXML(reqResult, "FAIL", payCallbackResData.getErr_code_des());
                    return reqResult;
                }

                try
                {
                    doPayAction(orderVo, studentCouponId, PayWayType.WX, discount,
                            payCallbackResData.getTransaction_id(),
                            TimeUtil.parseDate(payCallbackResData.getTime_end(), "yyyyMMddHHmmss"));
                }
                catch (Exception e1)
                {
                    logger.error(
                            "WXPay error === orderVo:" + orderVo + "|waterNum:"
                                    + payCallbackResData.getTransaction_id(), e1);
                }

                // 返回消息到腾讯
                genReturnXML(reqResult, "SUCCESS", "OK");

                // 去掉标记
                redisUtil.delete(REDISKEY.PRE_PAY_ORDER + payCallbackResData.getOut_trade_no());

                // 推送消息
                // pushMessageToCoach(orderVo.getStudentId(), orderVo.getCoachId(), orderVo.getPrice(),
                // orderVo.getOrderId());
            }
        }
        else
        {
            genReturnXML(reqResult, "FAIL", payCallbackResData.getErr_code_des());
        }
        return reqResult;
    }

    private void addPerformance(OrderVo orderVo)
    {
        PerformanceDto performanceDto = new PerformanceDto();
        performanceDto.setCoachId(orderVo.getCoachId());
        performanceDto.setCourse(orderVo.getCourseName());
        performanceDto.setDate(new Date());
        performanceDto.setOrderid(orderVo.getOrderId());
        performanceDto.setPerformance(orderVo.getPayTotal());

        performanceDtoMapper.insert(performanceDto);
    }

    public static void genReturnXML(ReqResult reqResult, String return_code, String return_msg)
    {
        ReturnToWXReq returnToWXReq = new ReturnToWXReq(return_code, return_msg);
        // 解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

        // 将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = xStreamForRequestPostData.toXML(returnToWXReq);

        reqResult.setData(postDataXML);
    }

    @SuppressWarnings("unused")
    private void pushMessageToCoach(long studentId, long coachId, int price, String orderId)
    {
        Student student = studentManager.getStudentInfo(studentId);
        JpushMsg jpushMsg = new JpushMsg();
        jpushMsg.setUserId(coachId);
        jpushMsg.setAlter(student.getName() + "学员支付了" + price + "课时费");
        jpushMsg.setOrderId(orderId);
        jpushMsg.setOperate(JpushConstant.OPERATE.COACHRECVPAY);

        Message msg = new Message(jpushProducer.getCreateTopicKey(), JpushConstant.OPERATE.COACHRECVPAY,
                SerializableUtil.serialize(jpushMsg));
        try
        {
            jpushProducer.send(msg);
        }
        catch (MQClientException e)
        {
            logger.error("pushMsg", e);
        }
        catch (RemotingException e)
        {
            logger.error("pushMsg", e);
        }
        catch (MQBrokerException e)
        {
            logger.error("pushMsg", e);
        }
        catch (InterruptedException e)
        {
            logger.error("pushMsg", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.pay.service.PayService#zfbPayCallBack(java.util.Map)
     */
    @Override
    public ReqResult zfbPayCallBack(Map<String, String> params)
    {
        ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.FAILED);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.FAILED);

        try
        {
            if (AlipayNotify.verify(params) == true)
            {
                reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
                reqResult.setMsgInfo(ResultCode.ERRORCODE.SUCCESS);
                String status = new String(params.get("trade_status").getBytes("ISO-8859-1"), "UTF-8");
                String orderId = new String(params.get("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                logger.info("zfbPayCallBack water: " + params.get("trade_no") + " orderId:" + orderId + " status: " + status);
                long studentCouponId = 0;
                if (params.get("student_coupontId") != null)
                {
                    String couponId = new String(params.get("student_coupontId").getBytes("ISO-8859-1"), "UTF-8");
                    // 代金券能抵扣的数目
                    studentCouponId = Integer.parseInt(couponId);
                }

                String orderPrice = new String(params.get("total_fee").getBytes("ISO-8859-1"), "UTF-8");

                // 订单价格
                double orderPriceInt = Double.parseDouble(orderPrice);
                // 校验价格
                OrderVo orderVo = orderService.queryOrderById(orderId, new OrderQuery());
                if (orderVo == null)
                {
                    reqResult.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
                    reqResult.setMsgInfo(ResultCode.ERRORINFO.ORDER_NOTEXIST);
                    return reqResult;
                }
                if (status.equals("WAIT_BUYER_PAY"))
                {
                    int discount = 0;
                    int price = orderVo.getPayTotal() - discount > 0 ? orderVo.getPayTotal() - discount : 0;
                    double realPrice = (double) (price) / 100;
                    if (realPrice != orderPriceInt)
                    {
                        reqResult.setCode(ResultCode.ERRORCODE.ORDER_MONEY_MOTIFY);
                        reqResult.setMsgInfo(ResultCode.ERRORINFO.ORDER_MONEY_MOTIFY);
                        logger.error("realPrice:" + realPrice + " orderPriceInt:" + orderPriceInt);
                        return reqResult;
                    }

                    // 校验通过，添加标记
                    redisUtil.set(REDISKEY.PRE_PAY_ORDER + orderId, String.valueOf(discount));
                }
                else if (status != null && orderId != null && status.equals("TRADE_SUCCESS"))
                {
                    // 交易完成
                    if (redisUtil.isExist(REDISKEY.PRE_PAY_ORDER + orderId))
                    {
                        // 取到折扣
                        String discount = redisUtil.get(REDISKEY.PRE_PAY_ORDER + orderId);
                        double dis = 0;
                        if (discount != null)
                        {
                            dis = Double.parseDouble(discount);
                        }

                        // 消耗优惠券
//                        if (studentCouponId != 0)
//                        {
//                            couponService.useCoupon(studentCouponId, orderId);
//                        }

                        // 支付相关表
                        doPayAction(orderVo, studentCouponId, PayWayType.ZFB, dis, params.get("trade_no"), new Date());

                        // 去掉标记
                        redisUtil.delete(REDISKEY.PRE_PAY_ORDER + orderId);
                    }
                }
            }
            else
            {
                reqResult.setMsgInfo(ResultCode.ERRORCODE.ORDER_PAY_SIGN_INVALID);
            }
        }
        catch (Exception e)
        {
            logger.error("params:" + params.toString(), e);
        }
        return reqResult;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.pay.service.PayService#getPayConfig(java.lang.String)
     */
    @Override
    public ReqResult getPayConfig(String studentId, String tokenId)
    {
        ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);

        /*
         * if (!redisUtil.isExist(REDISKEY.STUDENT_TOKEN + studentId, tokenId))
         * {
         * reqResult.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
         * reqResult.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
         * return reqResult;
         * }
         * else
         */
        {
            List<PayConfigVo> payConfigList = new ArrayList<PayConfigVo>();
            // 支付宝
            PayConfigVo payConfigVo = new PayConfigVo();
            payConfigVo.setPayWay(PayWayType.ZFB);
            payConfigVo.setParam1(zfbPayConfig.getPartner());
            payConfigVo.setParam2(zfbPayConfig.getSeller());
            payConfigList.add(payConfigVo);

            // 微信
            PayConfigVo payConfigVo2 = new PayConfigVo();
            payConfigVo2.setPayWay(PayWayType.WX);
            payConfigList.add(payConfigVo2);

            reqResult.setData(payConfigList);
        }
        return reqResult;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.pay.service.PayService#getMoneyLog(long, int, int)
     */
    @Override
    public ReqResult getMoneyLog(long userId, int userType, int page, int pageSize)
    {
        ReqResult r = ReqResult.getFailed();
        MoneyPage moneyPage = new MoneyPage(pageSize, page, userId, userType);
        List<UserMoneyDto> userMoneyDtos = userMoneyDtoMapper.getUserMoneyDtoByPage(moneyPage);
        try
        {
            List<UserMoneyVo> userMoneyVos = BeanCopy.copyList(userMoneyDtos, UserMoneyVo.class, BeanCopy.COPYNOTNULL);
            r = ReqResult.getSuccess();
            r.setData(userMoneyVos);
        }
        catch (Exception e)
        {
            logger.error("getMoneyLog:" + userId + "|" + userType, e);
            return r;
        }
        return r;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.pay.service.PayService#getPerformanceLog(long, int)
     */
    @Override
    public ReqResult getPerformanceLog(long coachId, int page, int pageSize)
    {
        ReqResult r = ReqResult.getFailed();
        PerformancePage performancePage = new PerformancePage(pageSize, page, coachId);
        List<PerformanceDto> performanceDtos = performanceDtoMapper.getPerformanceDtosByPage(performancePage);
        try
        {
            List<PerformanceVo> performanceVos = BeanCopy.copyList(performanceDtos, PerformanceVo.class,
                    BeanCopy.COPYNOTNULL);
            r = ReqResult.getSuccess();
            r.setData(performanceVos);
        }
        catch (Exception e)
        {
            logger.error("getPerformanceLog:" + coachId, e);
            return r;
        }
        return r;
    }

    @Override
    public ReqResult doPayAction(OrderVo orderVo, long couponId, String payType, double discount, String waterNum,
            Date payTime) throws Exception
    {
        ReqResult reqResult = ReqResult.getFailed();

        if (orderVo.getPayId() != null && orderVo.getPayId() != 0 && orderVo.getPayState() != null
                && (orderVo.getPayState() == PAYSTATE.HASPAY || orderVo.getPayState() == PAYSTATE.AUTOPAYE))
        {
            reqResult = ReqResult.getSuccess();
            return reqResult;
        }
        // 记录日志
        PayLogVo payLogVo = new PayLogVo();
        payLogVo.setCoachid(orderVo.getCoachId());
        payLogVo.setCouponid(couponId);
        payLogVo.setCouponmoney(discount);
        payLogVo.setOrderid(orderVo.getOrderId());
        payLogVo.setPaymoney((double) orderVo.getPayTotal());
        payLogVo.setPaytime(payTime);
        payLogVo.setPayway(payType);
        payLogVo.setStudentid(orderVo.getStudentId());
        payLogVo.setWaternum(waterNum);

        // 更新order状态
        int payId = logService.logPay(payLogVo);
        orderVo.setPayId(payId);
        orderVo.setPayTime(payTime);
        orderVo.setPayTotal((int) (orderVo.getPayTotal() - discount));
        orderVo.setPayState(OrderConstant.PAYSTATE.HASPAY);

        orderService.updateByOrderId(orderVo, orderVo.getOrderId());

        // 只要日志库有了记录就没事，就可以认为成功
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);

        Coach coach = coachManager.getCoachInfo(orderVo.getCoachId());
        int leftMoney = 0;
        if (coach.getIsImport() == 1)
        {
            // 如果是导入的教练，则只加业绩
            moneyManager.addPerformanceAndMoney(orderVo.getCoachId(), orderVo.getPayTotal(), 0);

            // 插入业绩记录
            addPerformance(orderVo);
        }
        else
        {
            // 个体教练加钱
            leftMoney = moneyManager.addPerformanceAndMoney(orderVo.getCoachId(), 0, orderVo.getPayTotal());

            // 插入加钱记录
            moneyManager.addMoneyLog(orderVo.getCoachId(), MoneyManagerImpl.UserType.COACH, payType, leftMoney, orderVo.getPayTotal(), MoneyChange.CLASS_FEE, true, true,
                    orderVo.getCourseName() + "课时费", orderVo.getOrderId(), "");
        }
        // 给学员钱包也加入记录,获取学员钱包余额
        leftMoney = moneyManager.addStudentMoney(orderVo.getStudentId(), 0);
        moneyManager.addMoneyLog(orderVo.getStudentId(), MoneyManagerImpl.UserType.STUDENT, payType, leftMoney, -1 * (int) (orderVo.getPayTotal() - discount),
                MoneyChange.CLASS_FEE, false, payType.equals("balance"), orderVo.getCourseName() + "课时费", orderVo.getOrderId(), "");
        return reqResult;
    }

    /* (non-Javadoc)
     * @see com.lili.pay.service.PayService#qqPayCallBack(java.lang.String)
     */
    @Override
    public ReqResult qqPayCallBack(String result)
    {
        ReqResult reqResult = ReqResult.getFailed();
        return reqResult;
    }
}