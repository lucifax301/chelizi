/**
 * 
 */
package com.lili.pay.service.impl;

import com.alipay.config.AlipayConfig;
import com.alipay.sign.ZFBPayDemo;
import com.alipay.util.AlipayNotify;
import com.lili.common.constant.MoneyChange;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.log.service.LogService;
import com.lili.log.vo.PayLogVo;
import com.lili.pay.action.WXPayAction;
import com.lili.pay.config.WXPayConfig;
import com.lili.pay.config.ZFBPayConfig;
import com.lili.pay.dto.UserMoneyDto;
import com.lili.pay.manager.MoneyManager;
import com.lili.pay.mapper.dao.UserMoneyDtoMapper;
import com.lili.pay.service.ChargeService;
import com.lili.pay.vo.ChargeVo;
import com.lili.pay.vo.PayInfoVo;
import com.lili.pay.vo.PayWayType;
import com.lili.student.manager.StudentManager;
import com.tencent.WXPay;
import com.tencent.business.ResultListener;
import com.tencent.common.Configure;
import com.tencent.common.Util;
import com.tencent.protocol.callback.protocol.PayCallbackResData;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryResData;
import com.tencent.protocol.reverse_protocol.ReverseResData;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderReqData;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderResData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * @author linbo
 * 充值
 */
public class ChargeServiceImpl implements ChargeService
{
    Logger logger = LoggerFactory.getLogger(ChargeServiceImpl.class);
    
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private StudentManager studentManager;
    @Autowired
    private UserMoneyDtoMapper userMoneyDtoMapper;
    @Autowired
    private ZFBPayConfig zfbPayConfig;
    @Autowired
    private WXPayConfig wxPayConfig;
    @Autowired
    protected LogService logService;
    @Autowired
    private MoneyManager moneyManager;

    @Override
    public ReqResult charge(ChargeVo chargeVo, String tokenId)
    {
        ReqResult reqResult = ReqResult.getFailed();
        
        try
        {
            if (chargeVo.getChargeType().equals(PayWayType.WX))
            {
                wxCharge(chargeVo, reqResult);
            }
            else if (chargeVo.getChargeType().equals(PayWayType.ZFB))
            {
                zfbCharge(chargeVo, reqResult);
            }
            else if (chargeVo.getChargeType().equals(PayWayType.YL))
            {
                ylCharge(chargeVo, reqResult);
            }
            else if(chargeVo.getChargeType().equals(PayWayType.QQWALLET))
            {
                qqWalletCharge(chargeVo, reqResult);
            }
            else
            {
                
            }
        }
        catch (Exception e)
        {
            logger.error("chargeOv:" + chargeVo, e);
        }
        return reqResult;
    }

    private void qqWalletCharge(ChargeVo chargeVo, ReqResult reqResult) {
        // TODO: 2016/五月/19  QQ钱包充值
    }

    /**
     * @param chargeVo
     */
    private void ylCharge(ChargeVo chargeVo, final ReqResult reqResult)
    {
        // TODO 2016/五月/19  银联充值
    }

    /**
     * @param chargeVo
     */
    private void zfbCharge(ChargeVo chargeVo, final ReqResult reqResult)
    {
        String zfbOrderInfo = ZFBPayDemo.pay(chargeVo.getChargeId(), "喱喱充值", "喱喱充值", ((double) (chargeVo.getChargeValue()) / 100), AlipayConfig.charge_callBackUrl, 0);
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setData(zfbOrderInfo);
        redisUtil.set(REDISKEY.PRE_PAY_ORDER + chargeVo.getChargeId(), chargeVo, 60 * 60 * 24);
    }

    /**
     * @param chargeVo
     */
    private void wxCharge(final ChargeVo chargeVo, final ReqResult reqResult)
    {
        try
        {
            UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData("喱喱充值", chargeVo.getBankCard(),
                    chargeVo.getChargeId(), chargeVo.getChargeValue(),
                    "APP", "127.0.0.1", TimeUtil.getDateFormat(new Date(), "yyyyMMddHHmmss"), TimeUtil.getDateFormat(
                            TimeUtil.addDate(
                                    new Date(), 600000), "yyyyMMddHHmmss"), wxPayConfig.getChargeNotifyUrl(), wxPayConfig.getAppId(0), wxPayConfig.getKey(0), Configure.getTradeType(), "", wxPayConfig.getMchId(0));
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
                    redisUtil.set(REDISKEY.PRE_PAY_ORDER + chargeVo.getChargeId(), chargeVo);
                }

                @Override
                public void onFailByNoAuth(UnifiedOrderResData unifiedOrderResData)
                {
                    logger.error("NOAuth when exe wxCharge", new NullPointerException());
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
            logger.error("WXCHARGE|chargeVo:" + chargeVo, e);
        }
        return;
    }
    
    @Override
    public ReqResult wxChargeCallBack(String result)
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
            if (redisUtil.isExist(REDISKEY.PRE_PAY_ORDER + payCallbackResData.getOut_trade_no()))
            {
                try
                {
                    ChargeVo chargeVo = redisUtil.get(REDISKEY.PRE_PAY_ORDER + payCallbackResData.getOut_trade_no());
                    if (chargeVo == null)
                    {
                        logger.error("chargeVo is null, chargeId:" + payCallbackResData.getOut_trade_no(),
                                new NullPointerException());
                        PayServiceImpl.genReturnXML(reqResult, "FAIL", payCallbackResData.getErr_code_des());
                        return reqResult;
                    }
                    
                    // 判断价格
                    int price = chargeVo.getChargeValue();
                    double realPrice = Double.parseDouble(payCallbackResData.getTotal_fee());
                    if (realPrice != price)
                    {
                        PayServiceImpl.genReturnXML(reqResult, "FAIL", payCallbackResData.getErr_code_des());
                        return reqResult;
                    }
                    
                    //充值
                    doCharge(chargeVo, payCallbackResData.getTransaction_id(), TimeUtil.parseDate(payCallbackResData.getTime_end(), "yyyyMMddHHmmss"));
                }
                catch (Exception e)
                {
                    logger.error("chargeId:" + payCallbackResData.getOut_trade_no(),
                            new NullPointerException());
                    PayServiceImpl.genReturnXML(reqResult, "FAIL", payCallbackResData.getErr_code_des());
                    return reqResult;
                }

                // 返回消息到腾讯
                PayServiceImpl.genReturnXML(reqResult, "SUCCESS", "OK");

                // 去掉标记
                redisUtil.delete(REDISKEY.PRE_PAY_ORDER + payCallbackResData.getOut_trade_no());

            }
        }
        else
        {
            PayServiceImpl.genReturnXML(reqResult, "FAIL", payCallbackResData.getErr_code_des());
        }
        return reqResult;
    }
    
    public ReqResult zfbChargeCallBack(Map<String, String> params)
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
                logger.info("zfbChargeCallBack water: " + params.get("trade_no") + "status: " + status);
                String chargeId = new String(params.get("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                String payment = new String(params.get("notify_time").getBytes("ISO-8859-1"), "UTF-8");
                String chargeValue = new String(params.get("total_fee").getBytes("ISO-8859-1"), "UTF-8");

                // 订单价格
                double chargePriceInt = Double.parseDouble(chargeValue) * 100;

                if (status.equals("WAIT_BUYER_PAY"))
                {
                    ChargeVo chargeVo = redisUtil.get(REDISKEY.PRE_PAY_ORDER + chargeId);
                    if (chargeVo.getChargeValue() != chargePriceInt)
                    {
                        reqResult.setCode(ResultCode.ERRORCODE.ORDER_MONEY_MOTIFY);
                        reqResult.setMsgInfo(ResultCode.ERRORINFO.ORDER_MONEY_MOTIFY);
                        return reqResult;
                    }

                }
                else if (status != null && chargeId != null && status.equals("TRADE_SUCCESS"))
                {
                    // 交易完成
                    if (redisUtil.isExist(REDISKEY.PRE_PAY_ORDER + chargeId))
                    {
                        // 取到价格
                        ChargeVo chargeVo = redisUtil.get(REDISKEY.PRE_PAY_ORDER + chargeId);
   
                        if (chargeVo != null)
                        {
                            //充值
                            doCharge(chargeVo, params.get("trade_no"),  TimeUtil.parseDate(payment, "yyyy-MM-dd HH:mm:ss"));
                            
                            //去掉标记
                            redisUtil.delete(REDISKEY.PRE_PAY_ORDER + chargeId);
                        }
                        else
                        {
                            logger.error("can not found chargeId in redis when charge callback");
                        }
                        
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
    
    /**
     * @param userId
     * @param leftMoney
     * @param price
     */
    public void addMoney(Long userId, int userType, String payType, int leftMoney, Integer price, MoneyChange moneyChange, String chargeId)
    {
        UserMoneyDto userMoneyDto = new UserMoneyDto();
        userMoneyDto.setChangevalue(price);
        userMoneyDto.setLeftvalue(leftMoney);
        userMoneyDto.setOperatetime(new Date());
        userMoneyDto.setOperatetype(moneyChange.getType());
        userMoneyDto.setPayway(payType);
        userMoneyDto.setRemark(PayWayType.getChinaName(payType) + "充值");
        userMoneyDto.setUserid(userId);
        userMoneyDto.setUsertype((byte) userType);
        userMoneyDto.setOrderId(chargeId);
        userMoneyDtoMapper.insert(userMoneyDto);
    }
    
    public void doCharge(ChargeVo chargeVo, String waterNum, Date date)
    {
        // 记录流水
        PayLogVo payLogVo = new PayLogVo();
        payLogVo.setCouponid(0);
        payLogVo.setCouponmoney(0.0);
        payLogVo.setOrderid(chargeVo.getChargeId());
        payLogVo.setPaymoney((double) chargeVo.getChargeValue());
        payLogVo.setPaytime(date);
        payLogVo.setPayway(chargeVo.getChargeType());
        payLogVo.setWaternum(waterNum);
        
        // 充值
        if (chargeVo.getUserType() == 1)
        {
            payLogVo.setCoachid(chargeVo.getUserId());
            logService.logPay(payLogVo);
            
            //如果是教练
            int leftMoney = moneyManager.addPerformanceAndMoney(chargeVo.getUserId(), 0, chargeVo.getChargeValue());
            
            //添加记录
            addMoney(chargeVo.getUserId(), chargeVo.getUserType(), chargeVo.getChargeType(), leftMoney, chargeVo.getChargeValue(), MoneyChange.CHARGE, chargeVo.getChargeId());
        }
        else if (chargeVo.getUserType() == 2)
        {
            payLogVo.setStudentid(chargeVo.getUserId());
            logService.logPay(payLogVo);
            
            //学员充值
            int leftMoney = moneyManager.addStudentMoney(chargeVo.getUserId(), chargeVo.getChargeValue());
            
            //添加记录
            addMoney(chargeVo.getUserId(), chargeVo.getUserType(), chargeVo.getChargeType(), leftMoney, chargeVo.getChargeValue(), MoneyChange.CHARGE, chargeVo.getChargeId());
        }
        else
        {
            logger.error("userType is not equal 1 or 2");
        }
    }
}
