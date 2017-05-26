/**
 * 
 */
package com.lili.pay.action;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lili.authcode.service.EmailService;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.HttpUtil;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.pay.vo.PayClientVer;
import com.lili.pay.vo.PayInfoVo;
import com.lili.pay.vo.PayMessage;
import com.lili.pay.vo.PayVo;
import com.lili.pay.vo.PurposeType;
import com.lili.student.vo.RechargeRecordVo;
import com.tencent.WXPay;
import com.tencent.business.ResultListener;
import com.tencent.common.Configure;
import com.tencent.common.Signature;
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

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author linbo
 *         微信支付动作
 */
public class WXPayAction extends PayAction
{
    @Autowired
    private EmailService emailService;
    
    @Resource(name="payRehandleProducer")
    DefaultMQProducer payRehandleProducer;
    
    @PostConstruct
    public void initWXConfig()
    {
        // 初始化微信SDK
        WXPay.initSDKConfiguration("",
                wxPayConfig.getCertLocalPath(), wxPayConfig.getCertPassword(), wxPayConfig.getIp(),
                wxPayConfig.getNotifyUrl(),
                wxPayConfig.getTradeType(), wxPayConfig.isUseThreadToDoReport());
    }

    @Override
    public void doPayAction(final PayVo payVo, final ReqResult reqResult)
    {
        try
        {
            // 预处理
            Object discountAdv = getPayPurpose(payVo.getPayPurpose()).purposeAdvance(payVo, reqResult);
            if (discountAdv == null)
            {
                return;
            }
            int discount = (int) discountAdv;
            String payType = "";
            String dev_info = "";
            if (payVo.getClientVer() == PayClientVer.WEB_WX || payVo.getClientVer() == PayClientVer.WX_COACH) {
            	payType = "JSAPI";
            	dev_info = "WEB";
            }
            else {
            	payType = "APP";
            	dev_info = "APP";
            }
            String openId = "";
            if (payVo.getClientVer() == PayClientVer.WEB_WX  || payVo.getClientVer() == PayClientVer.WX_COACH )
            {
                openId = payVo.getRemark();

                if (openId == null || openId.isEmpty())
                {
                    logger.error("getOpenId error : " + payVo);
                    return;
                }
            }

            UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(payVo.getPayPurpose().getDesc(),
                    payVo.getCouponId() + ","
                            + String.valueOf(discount),
                    payVo.getPayOrderId(), payVo.getPayValue(),
                    dev_info, "127.0.0.1", TimeUtil.getDateFormat(new Date(), "yyyyMMddHHmmss"), TimeUtil.getDateFormat(
                            TimeUtil.addDate(
                                    new Date(), wxPayConfig.getOrderExpire()), "yyyyMMddHHmmss"),
                    Configure.getNotifyUrl(), wxPayConfig.getAppId(payVo.getClientVer().getType()),
                    wxPayConfig.getKey(payVo.getClientVer().getType()), payType, openId,
                    wxPayConfig.getMchId(payVo.getClientVer().getType()));
            //（1）商户后台收到用户支付单，调用微信支付统一下单接口。
            //商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易回话标识后再在APP里面调起支付。
            //sdk调用统一下单接口，生成预支付订单。
            WXPay.doUnifiedOrderBusiness(unifiedOrderReqData, wxPayConfig.getKey(payVo.getClientVer().getType()),
                    wxPayConfig.getMchId(payVo.getClientVer().getType()),
                    //商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
                    new ResultListener()
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

                            PayInfoVo payInfoVo = genPayInfoVo(unifiedOrderResData,
                                    wxPayConfig.getAppId(payVo.getClientVer().getType()),
                                    wxPayConfig.getKey(payVo.getClientVer().getType()),
                                    wxPayConfig.getMchId(payVo.getClientVer().getType()));
                            reqResult.setData(payInfoVo);

                            // 记录一个缓存到redis
                            redisUtil.set(REDISKEY.PRE_PAY_ORDER + payVo.getPayOrderId(), payVo, 24 * 60 * 60);
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
            logger.error("WXPAY|payVo:" + payVo, e);
            emailService.send("【系统】[用户支付异常啦，请抓紧时间处理！！]", "WXPayAction-->doPayAction|"+payVo+"|Exception:"+e);
        }
        return;
    }

    @Override
    public ReqResult payCallBack(Object... callbackParam)
    {
        String result = (String) callbackParam[0];
        PayCallbackResData payCallbackResData = (PayCallbackResData) Util.getObjectFromXML(
                result, PayCallbackResData.class);

        ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.FAILED);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.FAILED);

        if (payCallbackResData.getReturn_code().equals("SUCCESS"))
        {
            // 判断标记
            if (redisUtil.isExist(REDISKEY.PRE_PAY_ORDER + payCallbackResData.getOut_trade_no()))
            {
            	PayVo payVo = redisUtil.get(REDISKEY.PRE_PAY_ORDER + payCallbackResData.getOut_trade_no());
                int total_fee = (int) Double.parseDouble(payCallbackResData.getTotal_fee());
                reqResult = getPayPurpose(payVo.getPayPurpose()).doPurpose(payVo,
                        TimeUtil.parseDate(payCallbackResData.getTime_end(), "yyyyMMddHHmmss"),
                        payCallbackResData.getTransaction_id(), total_fee);
                if (reqResult.isSuccess())
                {
                	
                    genReturnXML(reqResult, "SUCCESS", "OK");
                }
                //只有成功充值的学员才充值送
            	if(reqResult.isSuccess() && payVo.getUserType()==OrderConstant.USETYPE.STUDENT && payVo.getPayPurpose() == PurposeType.CHARGE){
            	  try {
            		RechargeRecordVo record=new RechargeRecordVo();
            		record.setCharge(payVo.getPayValue());
            		record.setStudentId(payVo.getUserId());
            		record.setCuid(payVo.getUserId());
            		record.setPayTime(new Date());
            		record.setPayWay(payVo.getPayWay());
            		record.setWaterId(payCallbackResData.getTransaction_id());
            		rechargeService.recharge(record);
            	  }catch(Exception e){
            		  emailService.send("【系统】[用户微信支付异常啦，记录充值送异常，请抓紧时间处理！！]", "WXPayAction-->doPayAction --> rechargeService fail ! payVo: " + payVo);
            		  logger.error(reqResult+" with "+payVo+" Exception:"+e.getMessage(),e);
            	  }
            	} else {
            		logger.error(reqResult+" with "+payVo+" is not incorrect,so do nothing.");
            		//20160905报名回调失败处理
            		/*if (!reqResult.isSuccess() && payVo.getPayPurpose() == PurposeType.SIGNUP) {
            			try {
            				Message msg=new Message();
            				msg.setTopic(payRehandleProducer.getCreateTopicKey());
            				msg.setTags(OrderConstant.RMQTAG.STUDENT_ENROLL_PAY);
            				PayMessage pmsg = new PayMessage();
            				pmsg = BeanCopy.copyNotNull(payVo, pmsg);
            				
            				pmsg.setEndTime(TimeUtil.parseDate(payCallbackResData.getTime_end(), "yyyyMMddHHmmss"));;
            				pmsg.setWaterNum(payCallbackResData.getTransaction_id());
            				pmsg.setTotalFee(total_fee);
            				msg.setBody(SerializableUtil.serialize(pmsg));
            				payRehandleProducer.send(msg);
            				logger.debug("payErrorProducer has sent a message. MSGTYPE_STUDENT_REGISTER. payVo: "+ payVo);
            			} 
            			catch (Exception e) {
            				 emailService.send("【系统】[用户微信支付异常啦，发送MQ处理失败，请抓紧时间处理！！]", "WXPayAction-->doPayAction --> payRehandleProducer fail ! payVo: " + payVo);
            				logger.error("payErrorProducer error "+e);
            				e.printStackTrace();
            			} 
            		}*/
            	}
            }
        }
        else
        {
        	emailService.send("【系统】[用户微信支付异常啦，请抓紧时间处理！！]", "WXPayAction-->doPayAction --> payCallbackResData");
            genReturnXML(reqResult, "FAIL", payCallbackResData.getErr_code_des());
        }
        return reqResult;
    }

    public static PayInfoVo genPayInfoVo(UnifiedOrderResData unifiedOrderResData, String appId, String key, String mchId)
    {
        PayInfoVo payInfoVo = new PayInfoVo();
        payInfoVo.setNonce_str(unifiedOrderResData.getNonce_str());
        payInfoVo.setPrepay_id(unifiedOrderResData.getPrepay_id());
        payInfoVo.setAppId(appId);
        payInfoVo.setPackageString("Sign=WXPay");
        payInfoVo.setPartnerid(mchId);
        payInfoVo.setTimestamp(TimeUtil.getDateFormat(new Date(), "yyyyMMddHHmmss"));
        // 根据API给的签名规则进行签名
        String sign = Signature.getSign(payInfoVo.toMap(), key);
        payInfoVo.setSign(sign);// 把签名数据设置到Sign这个属性中
        return payInfoVo;
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

    public static String getOpenId(String code)
    {
        String openId = "";
        String appId = "wxbbde6ffce207719a";
        String secret = "f32ad5e73435181bed9e5cbbd3a0e9e8";
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", appId);
        params.put("secret", secret);
        params.put("code", code);
        params.put("grant_type", "authorization_code");

        String result = HttpUtil.doGet(url, params, "UTF-8");
        Gson gson = new Gson();
        try
        {
            OpenIdRes openIdRes = gson.fromJson(result, OpenIdRes.class);
            openId = openIdRes.getOpenid();
            return openId;
        }
        catch (JsonSyntaxException e)
        {
            logger.error("get openId error");
        }
        return openId;
    }

    public class OpenIdRes
    {
        private String access_token;
        private String expires_in;
        private String refresh_token;
        private String openid;
        private String scope;
        private String unionid;

        /**
         * @return the access_token
         */
        public String getAccess_token()
        {
            return access_token;
        }

        /**
         * @param access_token
         *            the access_token to set
         */
        public void setAccess_token(String access_token)
        {
            this.access_token = access_token;
        }

        /**
         * @return the expires_in
         */
        public String getExpires_in()
        {
            return expires_in;
        }

        /**
         * @param expires_in
         *            the expires_in to set
         */
        public void setExpires_in(String expires_in)
        {
            this.expires_in = expires_in;
        }

        /**
         * @return the refresh_token
         */
        public String getRefresh_token()
        {
            return refresh_token;
        }

        /**
         * @param refresh_token
         *            the refresh_token to set
         */
        public void setRefresh_token(String refresh_token)
        {
            this.refresh_token = refresh_token;
        }

        /**
         * @return the openid
         */
        public String getOpenid()
        {
            return openid;
        }

        /**
         * @param openid
         *            the openid to set
         */
        public void setOpenid(String openid)
        {
            this.openid = openid;
        }

        /**
         * @return the scope
         */
        public String getScope()
        {
            return scope;
        }

        /**
         * @param scope
         *            the scope to set
         */
        public void setScope(String scope)
        {
            this.scope = scope;
        }

        /**
         * @return the unionid
         */
        public String getUnionid()
        {
            return unionid;
        }

        /**
         * @param unionid
         *            the unionid to set
         */
        public void setUnionid(String unionid)
        {
            this.unionid = unionid;
        }

    }
}
