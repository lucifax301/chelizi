/**
 * 
 */
package com.lili.pay.action;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.lili.authcode.service.EmailService;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.pay.vo.PayMessage;
import com.lili.pay.vo.PayVo;
import com.lili.pay.vo.PurposeType;
import com.lili.pay.vo.QQPayCallbackResData;
import com.lili.qqpay.sdk.QQPayBussiness;
import com.lili.qqpay.sdk.QQPayInitReq;
import com.lili.student.vo.RechargeRecordVo;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author linbo
 *
 */
public class QQPayAction extends PayAction
{
    @Autowired
    private EmailService emailService;
    
    @Resource(name="payRehandleProducer")
    DefaultMQProducer payRehandleProducer;
    
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

            String timeStart = TimeUtil.getDateFormat(new Date(), "yyyyMMddHHmmss");
            String timeEnd = TimeUtil.getDateFormat(TimeUtil.addDate(new Date(), 600000), "yyyyMMddHHmmss");
            QQPayInitReq qqPayInitReq = new QQPayInitReq(qqPayConfig.getMchID(), payVo.getPayPurpose().getDesc(),
                    payVo.getPayOrderId(), payVo.getPayValue(), qqPayConfig.getCallBackUrl(), "attach", timeStart,
                    timeEnd, qqPayConfig.getKey());
            ReqResult r = QQPayBussiness.doQQPayInit(qqPayInitReq, qqPayConfig.getInitUrl());
            if (r.isSuccess())
            {
                reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
                reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
                reqResult.setData(r.getResult().get(ResultCode.RESULTKEY.MSGINFO));

                redisUtil.set(REDISKEY.PRE_PAY_ORDER + payVo.getPayOrderId(), payVo, 24 * 60 * 60);
            }
            logger.error("QQPayAction init result: " + r.getResult().get(ResultCode.RESULTKEY.MSGINFO));
        }
        catch (Exception e)
        {
            logger.error("doPayAction: " + payVo, e);
            emailService.send("【系统】[用户支付异常啦，请抓紧时间处理！！]", "QQPayAction-->doPayAction|"+payVo+"|Exception:"+e);
        }
    }

    @Override
    public ReqResult payCallBack(Object... callbackParam)
    {
        QQPayCallbackResData payCallbackResData = (QQPayCallbackResData) callbackParam[0];

        ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.FAILED);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.FAILED);

        try
        {
            if (payCallbackResData.getPay_result() == 0)
            {
                if(checkResponseSign(payCallbackResData, qqPayConfig.getKey()))
                {
                    logger.error("qqPayCallBack checkSign error: " + payCallbackResData);
                    return reqResult;
                }
                // 判断标记
                if (redisUtil.isExist(REDISKEY.PRE_PAY_ORDER + payCallbackResData.getSp_billno())) {
                    PayVo payVo = redisUtil.get(REDISKEY.PRE_PAY_ORDER + payCallbackResData.getSp_billno());

                    int total_fee = payCallbackResData.getTotal_fee();
                    reqResult = getPayPurpose(payVo.getPayPurpose())
                            .doPurpose(payVo, TimeUtil.parseDate(payCallbackResData.getTime_end(), "yyyyMMddHHmmss"), payCallbackResData.getTransaction_id(), total_fee);
                    
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
                		   emailService.send("【系统】[用户财付通支付异常啦，记录充值送异常，请抓紧时间处理！！]", "QQPayAction-->doPayAction -->  rechargeService fail ! payVo: " + payVo);
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
                				emailService.send("【系统】[用户财付通支付异常啦，发送MQ处理失败，请抓紧时间处理！！]", "QQPayAction-->doPayAction -->  payRehandleProducer fail ! payVo: " + payVo);
                				logger.error("payErrorProducer error "+e);
                				e.printStackTrace();
                			} 
                		}*/
                	}
                	
                    if (reqResult.isSuccess()) {
                        reqResult.setData("success");
                        return reqResult;
                    }
                    
                }
            }
        }
        catch (Exception e)
        {
           logger.error("QQPayCallBack: " + payCallbackResData ,e);
        }
        reqResult.setData("fail");
        return reqResult;
    }

    private boolean checkResponseSign(QQPayCallbackResData resp, String key)
    {
        String signResp = resp.getSign();
        resp.setSign("");
        String checkSign = QQPayBussiness.getSign(resp.toMap(), key);
        return signResp.equals(checkSign);
    }
}
