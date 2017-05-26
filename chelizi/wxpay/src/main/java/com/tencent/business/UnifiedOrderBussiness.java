/**
 * 
 */
package com.tencent.business;

import static java.lang.Thread.sleep;

import org.slf4j.LoggerFactory;

import com.tencent.common.Log;
import com.tencent.common.Signature;
import com.tencent.common.Util;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryReqData;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryResData;
import com.tencent.protocol.reverse_protocol.ReverseReqData;
import com.tencent.protocol.reverse_protocol.ReverseResData;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderReqData;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderResData;
import com.tencent.service.ReverseService;
import com.tencent.service.ScanPayQueryService;
import com.tencent.service.UnifiedOrderService;

/**
 * @author linbo
 *
 */
public class UnifiedOrderBussiness
{
    public UnifiedOrderBussiness() throws IllegalAccessException, ClassNotFoundException, InstantiationException
    {
        unifiedOrderService = new UnifiedOrderService();
        scanPayQueryService = new ScanPayQueryService();
        reverseService = new ReverseService();
    }

    // 打log用
    private static Log log = new Log(LoggerFactory.getLogger(UnifiedOrderBussiness.class));

    // 每次调用订单查询API时的等待时间，因为当出现支付失败的时候，如果马上发起查询不一定就能查到结果，所以这里建议先等待一定时间再发起查询

    private int waitingTimeBeforePayQueryServiceInvoked = 5000;

    // 循环调用订单查询API的次数
    private int payQueryLoopInvokedCount = 3;

    // 每次调用撤销API的等待时间
    private int waitingTimeBeforeReverseServiceInvoked = 5000;

    private UnifiedOrderService unifiedOrderService;

    private ScanPayQueryService scanPayQueryService;

    private ReverseService reverseService;

    private String prepay_id = "";

    /**
     * 直接执行被扫支付业务逻辑（包含最佳实践流程）
     *
     * @param unifiedOrderReqData
     *            这个数据对象里面包含了API要求提交的各种数据字段
     * @param key 
     * @param resultListener
     *            商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @throws Exception
     */
    public void run(UnifiedOrderReqData unifiedOrderReqData, String key, String mchId, ResultListener resultListener) throws Exception
    {

        // --------------------------------------------------------------------
        // 构造请求“被扫支付API”所需要提交的数据
        // --------------------------------------------------------------------

        String outTradeNo = unifiedOrderReqData.getOut_trade_no();

        // 接受API返回
        String unifiedOrderServiceResponseString;

        long costTimeStart = System.currentTimeMillis();

        log.i("支付API返回的数据如下：");
        unifiedOrderServiceResponseString = unifiedOrderService.request(unifiedOrderReqData);

        long costTimeEnd = System.currentTimeMillis();
        long totalTimeCost = costTimeEnd - costTimeStart;
        log.i("api请求总耗时：" + totalTimeCost + "ms");

        // 打印回包数据
        log.i(unifiedOrderServiceResponseString);

        // 将从API返回的XML数据映射到Java对象
        UnifiedOrderResData unifiedOrderResData = (UnifiedOrderResData) Util.getObjectFromXML(
                unifiedOrderServiceResponseString, UnifiedOrderResData.class);

        // 异步发送统计请求
        // *

//        ReportReqData reportReqData = new ReportReqData(
//                unifiedOrderReqData.getDevice_info(),
//                Configure.UNIFIED_ORDER_API,
//                (int) (totalTimeCost),// 本次请求耗时
//                unifiedOrderResData.getReturn_code(),
//                unifiedOrderResData.getReturn_msg(),
//                unifiedOrderResData.getResult_code(),
//                unifiedOrderResData.getErr_code(),
//                unifiedOrderResData.getErr_code_des(),
//                outTradeNo,
//                unifiedOrderReqData.getSpbill_create_ip()
//                );
//        long timeAfterReport;
//        if (Configure.isUseThreadToDoReport())
//        {
//            ReporterFactory.getReporter(reportReqData).run();
//            timeAfterReport = System.currentTimeMillis();
//            log.i("pay+report总耗时（异步方式上报）：" + (timeAfterReport - costTimeStart) + "ms");
//        }
//        else
//        {
//            ReportService.request(reportReqData);
//            timeAfterReport = System.currentTimeMillis();
//            log.i("pay+report总耗时（同步方式上报）：" + (timeAfterReport - costTimeStart) + "ms");
//        }

        if (unifiedOrderResData == null || unifiedOrderResData.getReturn_code() == null)
        {
            log.e("【支付失败】支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问");
            resultListener.onFailByReturnCodeError(unifiedOrderResData);
            return;
        }

        if (unifiedOrderResData.getReturn_code().equals("FAIL"))
        {
            // 注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
            log.e("【支付失败】支付API系统返回失败，请检测Post给API的数据是否规范合法");
            resultListener.onFailByReturnCodeFail(unifiedOrderResData);
            return;
        }
        else
        {
            log.i("支付API系统成功返回数据");
            // --------------------------------------------------------------------
            // 收到API的返回数据的时候得先验证一下数据有没有被第三方篡改，确保安全
            // --------------------------------------------------------------------
            if (!Signature.checkIsSignValidFromResponseString(unifiedOrderServiceResponseString, key))
            {
                log.e("【支付失败】支付请求API返回的数据签名验证失败，有可能数据被篡改了");
                resultListener.onFailBySignInvalid(unifiedOrderResData);
                return;
            }

            // 获取错误码
            String errorCode = unifiedOrderResData.getErr_code();
            // 获取错误描述
            String errorCodeDes = unifiedOrderResData.getErr_code_des();

            if (unifiedOrderResData.getResult_code().equals("SUCCESS"))
            {

                // --------------------------------------------------------------------
                // 1)直接扣款成功
                // --------------------------------------------------------------------

                log.i("【一次性支付成功】");

                String prepayId = unifiedOrderResData.getPrepay_id();
                if (prepayId != null && !prepayId.isEmpty())
                {
                    prepay_id = prepayId;
                }

                resultListener.onSuccess(unifiedOrderResData, prepay_id);
            }
            else
            {

                // 出现业务错误
                log.i("业务返回失败");
                log.i("err_code:" + errorCode);
                log.i("err_code_des:" + errorCodeDes);

                // 业务错误时错误码有好几种，商户重点提示以下几种
                if (errorCode.equals("NOAUTH") || errorCode.equals("NOTENOUGH") || errorCode.equals("ORDERCLOSED")
                        || errorCode.equals("SYSTEMERROR"))
                {

                    // --------------------------------------------------------------------
                    // 2)扣款明确失败
                    // --------------------------------------------------------------------

                    // 对于扣款明确失败的情况直接走撤销逻辑
                    doReverseLoop(outTradeNo, unifiedOrderReqData.getAppid(), key, mchId, resultListener);

                    if (errorCode.equals("NOQUTH"))
                    {
                        log.e("【支付失败】商户无此API权限");
                        resultListener.onFailByNoAuth(unifiedOrderResData);
                    }
                    else if (errorCode.equals("NOTENOUGH"))
                    {
                        log.e("【支付失败】用户帐号余额不足");
                        resultListener.onFailByMoneyNotEnough(unifiedOrderResData);
                    }
                    else if (errorCode.equals("ORDERCLOSED"))
                    {
                        log.e("【支付失败】订单已关闭");
                        resultListener.onFailByOrderClosed(unifiedOrderResData);
                    }
                    else if (errorCode.equals("SYSTEMERROR"))
                    {
                        log.e("【支付失败】系统超时");
                        resultListener.onFailBySystemErr(unifiedOrderResData);
                    }
                }
                else if(errorCode.equals("OUT_TRADE_NO_USED"))
                {
                    log.e("【支付失败】该订单已经支付过");
                    resultListener.onFail(unifiedOrderResData);
                }
                else
                {
                    // --------------------------------------------------------------------
                    // 4)扣款未知失败
                    // --------------------------------------------------------------------

                    if (doPayQueryLoop(payQueryLoopInvokedCount, outTradeNo, unifiedOrderReqData.getAppid(), key, mchId, resultListener))
                    {
                        log.i("【支付扣款未知失败、查询到支付成功】");
                        resultListener.onSuccess(unifiedOrderResData, prepay_id);
                    }
                    else
                    {
                        log.i("【支付扣款未知失败、在一定时间内没有查询到支付成功、走撤销流程】");
                        doReverseLoop(outTradeNo, unifiedOrderReqData.getAppid(), key, mchId, resultListener);
                        resultListener.onFail(unifiedOrderResData);
                    }
                }
            }
        }
    }

    /**
     * 进行一次支付订单查询操作
     *
     * @param outTradeNo
     *            商户系统内部的订单号,32个字符内可包含字母, [确保在商户系统唯一]
     * @param resultListener
     *            商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @return 该订单是否支付成功
     * @throws Exception
     */
    private boolean doOnePayQuery(String outTradeNo, String appId, String key, String mchId, ResultListener resultListener) throws Exception
    {

        sleep(waitingTimeBeforePayQueryServiceInvoked);// 等待一定时间再进行查询，避免状态还没来得及被更新

        String payQueryServiceResponseString;

        ScanPayQueryReqData scanPayQueryReqData = new ScanPayQueryReqData("", outTradeNo, appId, key, mchId);
        payQueryServiceResponseString = scanPayQueryService.request(scanPayQueryReqData);

        log.i("支付订单查询API返回的数据如下：");
        log.i(payQueryServiceResponseString);

        // 将从API返回的XML数据映射到Java对象
        ScanPayQueryResData scanPayQueryResData = (ScanPayQueryResData) Util.getObjectFromXML(
                payQueryServiceResponseString, ScanPayQueryResData.class);
        if (scanPayQueryResData == null || scanPayQueryResData.getReturn_code() == null)
        {
            log.i("支付订单查询请求逻辑错误，请仔细检测传过去的每一个参数是否合法");
            return false;
        }

        if (scanPayQueryResData.getReturn_code().equals("FAIL"))
        {
            // 注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
            log.i("支付订单查询API系统返回失败，失败信息为：" + scanPayQueryResData.getReturn_msg());
            return false;
        }
        else
        {

            if (!Signature.checkIsSignValidFromResponseString(payQueryServiceResponseString, key))
            {
                log.e("【支付失败】支付请求API返回的数据签名验证失败，有可能数据被篡改了");
                resultListener.onFailByQuerySignInvalid(scanPayQueryResData);
                return false;
            }

            if (scanPayQueryResData.getResult_code().equals("SUCCESS"))
            {
                // 业务层成功
                if (scanPayQueryResData.getTrade_state().equals("SUCCESS"))
                {
                    // 表示查单结果为“支付成功”
                    log.i("查询到订单支付成功");
                    return true;
                }
                else
                {
                    // 支付不成功
                    log.i("查询到订单支付不成功");
                    return false;
                }
            }
            else
            {
                log.i("查询出错，错误码：" + scanPayQueryResData.getErr_code() + "     错误信息："
                        + scanPayQueryResData.getErr_code_des());
                return false;
            }

        }
    }

    /**
     * 由于有的时候是因为服务延时，所以需要商户每隔一段时间（建议5秒）后再进行查询操作，多试几次（建议3次）
     *
     * @param loopCount
     *            循环次数，至少一次
     * @param outTradeNo
     *            商户系统内部的订单号,32个字符内可包含字母, [确保在商户系统唯一]
     * @param resultListener
     *            商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @return 该订单是否支付成功
     * @throws InterruptedException
     */
    private boolean doPayQueryLoop(int loopCount, String outTradeNo, String appId, String key, String mchId, ResultListener resultListener) throws Exception
    {
        // 至少查询一次
        if (loopCount == 0)
        {
            loopCount = 1;
        }
        // 进行循环查询
        for (int i = 0; i < loopCount; i++)
        {
            if (doOnePayQuery(outTradeNo, appId, key, mchId, resultListener))
            {
                return true;
            }
        }
        return false;
    }

    // 是否需要再调一次撤销，这个值由撤销API回包的recall字段决定
    private boolean needRecallReverse = false;

    /**
     * 进行一次撤销操作
     *
     * @param outTradeNo
     *            商户系统内部的订单号,32个字符内可包含字母, [确保在商户系统唯一]
     * @param resultListener
     *            商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @return 该订单是否支付成功
     * @throws Exception
     */
    private boolean doOneReverse(String outTradeNo, String appId, String key, String mchId, ResultListener resultListener) throws Exception
    {

        sleep(waitingTimeBeforeReverseServiceInvoked);// 等待一定时间再进行查询，避免状态还没来得及被更新

        String reverseResponseString;

        ReverseReqData reverseReqData = new ReverseReqData("", outTradeNo, appId, key, mchId);
        reverseResponseString = reverseService.request(reverseReqData);

        log.i("撤销API返回的数据如下：");
        log.i(reverseResponseString);
        // 将从API返回的XML数据映射到Java对象
        ReverseResData reverseResData = (ReverseResData) Util.getObjectFromXML(reverseResponseString,
                ReverseResData.class);
        if (reverseResData == null)
        {
            log.i("支付订单撤销请求逻辑错误，请仔细检测传过去的每一个参数是否合法");
            return false;
        }
        if (reverseResData.getReturn_code().equals("FAIL"))
        {
            // 注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
            log.i("支付订单撤销API系统返回失败，失败信息为：" + reverseResData.getReturn_msg());
            return false;
        }
        else
        {

            if (!Signature.checkIsSignValidFromResponseString(reverseResponseString, key))
            {
                log.e("【支付失败】支付请求API返回的数据签名验证失败，有可能数据被篡改了");
                resultListener.onFailByReverseSignInvalid(reverseResData);
                needRecallReverse = false;// 数据被窜改了，不需要再重试
                return false;
            }

            if (reverseResData.getResult_code().equals("FAIL"))
            {
                log.i("撤销出错，错误码：" + reverseResData.getErr_code() + "     错误信息：" + reverseResData.getErr_code_des());
                if (reverseResData.getRecall().equals("Y"))
                {
                    // 表示需要重试
                    needRecallReverse = true;
                    return false;
                }
                else
                {
                    // 表示不需要重试，也可以当作是撤销成功
                    needRecallReverse = false;
                    return true;
                }
            }
            else
            {
                // 查询成功，打印交易状态
                log.i("支付订单撤销成功");
                return true;
            }
        }
    }

    /**
     * 由于有的时候是因为服务延时，所以需要商户每隔一段时间（建议5秒）后再进行查询操作，是否需要继续循环调用撤销API由撤销API回包里面的recall字段决定。
     *
     * @param outTradeNo
     *            商户系统内部的订单号,32个字符内可包含字母, [确保在商户系统唯一]
     * @param resultListener
     *            商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @throws InterruptedException
     */
    private void doReverseLoop(String outTradeNo,String appId, String key, String mchId, ResultListener resultListener) throws Exception
    {
        // 初始化这个标记
        needRecallReverse = true;
        // 进行循环撤销，直到撤销成功，或是API返回recall字段为"Y"
        while (needRecallReverse)
        {
            if (doOneReverse(outTradeNo, appId, key, mchId, resultListener))
            {
                return;
            }
        }
    }

    /**
     * 设置循环多次调用订单查询API的时间间隔
     *
     * @param duration
     *            时间间隔，默认为10秒
     */
    public void setWaitingTimeBeforePayQueryServiceInvoked(int duration)
    {
        waitingTimeBeforePayQueryServiceInvoked = duration;
    }

    /**
     * 设置循环多次调用订单查询API的次数
     *
     * @param count
     *            调用次数，默认为三次
     */
    public void setPayQueryLoopInvokedCount(int count)
    {
        payQueryLoopInvokedCount = count;
    }

    /**
     * 设置循环多次调用撤销API的时间间隔
     *
     * @param duration
     *            时间间隔，默认为5秒
     */
    public void setWaitingTimeBeforeReverseServiceInvoked(int duration)
    {
        waitingTimeBeforeReverseServiceInvoked = duration;
    }

    public void setScanPayService(UnifiedOrderService service)
    {
        unifiedOrderService = service;
    }

    public void setScanPayQueryService(ScanPayQueryService service)
    {
        scanPayQueryService = service;
    }

    public void setReverseService(ReverseService service)
    {
        reverseService = service;
    }

    /**
     * @return the prepay_id
     */
    public String getPrepay_id()
    {
        return prepay_id;
    }

    /**
     * @param prepay_id
     *            the prepay_id to set
     */
    public void setPrepay_id(String prepay_id)
    {
        this.prepay_id = prepay_id;
    }
}
