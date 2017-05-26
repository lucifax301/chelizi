/**
 * 
 */
package com.lili.pay.action;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alipay.config.AlipayConfig;
import com.alipay.sign.ZFBPayDemo;
import com.alipay.util.AlipayNotify;
import com.lili.authcode.service.EmailService;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.common.vo.ResultCode.ERRORCODE;
import com.lili.pay.vo.PayMessage;
import com.lili.pay.vo.PayVo;
import com.lili.pay.vo.PurposeType;
import com.lili.student.vo.RechargeRecordVo;

/**
 * @author linbo
 *         支付宝支付动作
 */
public class ZFBPayAction extends PayAction
{
    @Autowired
    private EmailService emailService;
    
    private static Logger logger = LoggerFactory.getLogger(ZFBPayAction.class);
    
    @Resource(name="payRehandleProducer")
    DefaultMQProducer payRehandleProducer;
    
    @PostConstruct
    public void initZFBConfig()
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

    @Override
    public void doPayAction(PayVo payVo, ReqResult reqResult)
    {
        try
        {
            Object discountAdv = getPayPurpose(payVo.getPayPurpose()).purposeAdvance(payVo, reqResult);
            if (discountAdv == null)
            {
                return;
            }
            String zfbOrderInfo = ZFBPayDemo.pay(payVo.getPayOrderId(), payVo.getPayPurpose().getDesc(),
                    payVo.getRemark(), (double) (payVo.getPayValue()) / 100, AlipayConfig.callback_url,
                    payVo.getClientVer().getType());
            reqResult.setCode(ERRORCODE.SUCCESS);
            reqResult.setData(zfbOrderInfo);
            redisUtil.set(REDISKEY.PRE_PAY_ORDER + payVo.getPayOrderId(), payVo, 24 * 60 * 60 * 3);
        }
        catch (Exception e)
        {
            logger.error("payVo:" + payVo, e);
            emailService.send("【系统】[用户支付异常啦，请抓紧时间处理！！]", "ZFBPayAction-->doPayAction|"+payVo+"|Exception:"+e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.pay.action.IPayAction#payCallBack(java.lang.Object)
     */
    @Override
    public ReqResult payCallBack(Object ... callbackParam)
    {
        ReqResult reqResult = ReqResult.getFailed();

        @SuppressWarnings("unchecked")
        Map<String, String> params = (Map<String, String>) callbackParam[0];
        try
        {
            if (AlipayNotify.verify(params))
            {
                reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
                reqResult.setMsgInfo(ResultCode.ERRORCODE.SUCCESS);
                String status = new String(params.get("trade_status").getBytes("ISO-8859-1"), "UTF-8");
                String orderId = new String(params.get("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                String payment = new String(params.get("notify_time").getBytes("ISO-8859-1"), "UTF-8");
                logger.info("zfbPayCallBack water: " + params.get("trade_no") + " orderId: " + orderId + " status: " + status);
                
                String orderPrice = new String(params.get("total_fee").getBytes("ISO-8859-1"), "UTF-8");

                // 订单价格
                double orderPriceInt = Double.parseDouble(orderPrice);
 
                PayVo payVo = redisUtil.get(REDISKEY.PRE_PAY_ORDER + orderId);
                if (payVo != null && status.equals("WAIT_BUYER_PAY"))
                {
                    double realPrice = (double) (payVo.getPayValue()) / 100;
                    if (realPrice != orderPriceInt)
                    {
                        reqResult.setCode(ResultCode.ERRORCODE.ORDER_MONEY_MOTIFY);
                        reqResult.setMsgInfo(ResultCode.ERRORINFO.ORDER_MONEY_MOTIFY);
                        logger.error("realPrice:" + realPrice + " orderPriceInt:" + orderPriceInt);
                        return reqResult;
                    }
                }
                else if (payVo != null && status != null && orderId != null && status.equals("TRADE_SUCCESS"))
                {
                    reqResult = getPayPurpose(payVo.getPayPurpose()).doPurpose(payVo, TimeUtil.parseDate(payment, "yyyy-MM-dd HH:mm:ss"), params.get("trade_no"), (int)(orderPriceInt * 100));
                    //只有成功充值的学员才充值送
                	if(reqResult.isSuccess() && payVo.getUserType()==OrderConstant.USETYPE.STUDENT && payVo.getPayPurpose() == PurposeType.CHARGE){
                	   try {
                		RechargeRecordVo record=new RechargeRecordVo();
                		record.setCharge(payVo.getPayValue());
                		record.setStudentId(payVo.getUserId());
                		record.setCuid(payVo.getUserId());
                		record.setPayTime(new Date());
                		record.setPayWay(payVo.getPayWay());
                		record.setWaterId(params.get("trade_no"));
                		rechargeService.recharge(record);
                	  }catch(Exception e){
                		  emailService.send("【系统】[用户支付宝充值异常啦，记录充值送异常，请抓紧时间处理！！]", "ZFBPayAction-->doPayAction-->rechargeService Fail！payVo： " + payVo);
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
                				
                				pmsg.setEndTime(TimeUtil.parseDate(payment, "yyyy-MM-dd HH:mm:ss"));;
                				pmsg.setWaterNum(params.get("trade_no"));
                				pmsg.setTotalFee((int)(orderPriceInt * 100));
                				msg.setBody(SerializableUtil.serialize(pmsg));
                				payRehandleProducer.send(msg);
                				logger.debug("payErrorProducer has sent a message. MSGTYPE_STUDENT_REGISTER. payVo: "+ payVo);
                			} 
                			catch (Exception e) {
                				emailService.send("【系统】[用户支付宝支付异常啦，发送MQ处理失败，请抓紧时间处理！！]", "ZFBPayAction-->doPayAction-->payRehandleProducer Fail!payVo: "+ payVo);
                				logger.error("payErrorProducer error "+e);
                				e.printStackTrace();
                			} 
                		}*/
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

}
