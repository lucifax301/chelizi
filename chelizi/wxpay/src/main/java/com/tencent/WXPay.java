package com.tencent;

import com.tencent.business.DownloadBillBusiness;
import com.tencent.business.RefundBusiness;
import com.tencent.business.RefundQueryBusiness;
import com.tencent.business.ResultListener;
import com.tencent.business.ScanPayBusiness;
import com.tencent.business.UnifiedOrderBussiness;
import com.tencent.common.Configure;
import com.tencent.protocol.downloadbill_protocol.DownloadBillReqData;
import com.tencent.protocol.pay_protocol.ScanPayReqData;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryReqData;
import com.tencent.protocol.refund_protocol.RefundReqData;
import com.tencent.protocol.refund_query_protocol.RefundQueryReqData;
import com.tencent.protocol.reverse_protocol.ReverseReqData;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderReqData;
import com.tencent.service.*;

/**
 * SDK总入口
 */
public class WXPay
{

    /**
     * 初始化SDK依赖的几个关键配置
     * 
     * @param key
     *            签名算法需要用到的秘钥
     * @param appID
     *            公众账号ID
     * @param mchID
     *            商户ID
     * @param sdbMchID
     *            子商户ID，受理模式必填
     * @param certLocalPath
     *            HTTP证书在服务器中的路径，用来加载证书用
     * @param certPassword
     *            HTTP证书的密码，默认等于MCHID
     */
    public static void initSDKConfiguration(String sdbMchID,
            String certLocalPath, String certPassword, String ip, String notifyUrl, String tradeType,
            boolean useThreadToDoReport)
    {
        Configure.setSubMchID(sdbMchID);
        Configure.setCertLocalPath(certLocalPath);
        Configure.setCertPassword(certPassword);
        Configure.setIp(ip);
        Configure.setNotifyUrl(notifyUrl);
        Configure.setTradeType(tradeType);
        Configure.setUseThreadToDoReport(useThreadToDoReport);
    }

    /**
     * 请求支付服务
     * 
     * @param scanPayReqData
     *            这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public static String requestScanPayService(ScanPayReqData scanPayReqData) throws Exception
    {
        return new ScanPayService().request(scanPayReqData);
    }

    /**
     * 请求支付查询服务
     * 
     * @param scanPayQueryReqData
     *            这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public static String requestScanPayQueryService(ScanPayQueryReqData scanPayQueryReqData) throws Exception
    {
        return new ScanPayQueryService().request(scanPayQueryReqData);
    }

    /**
     * 请求退款服务
     * 
     * @param refundReqData
     *            这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public static String requestRefundService(RefundReqData refundReqData) throws Exception
    {
        return new RefundService().request(refundReqData);
    }

    /**
     * 请求退款查询服务
     * 
     * @param refundQueryReqData
     *            这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public static String requestRefundQueryService(RefundQueryReqData refundQueryReqData) throws Exception
    {
        return new RefundQueryService().request(refundQueryReqData);
    }

    /**
     * 请求撤销服务
     * 
     * @param reverseReqData
     *            这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public static String requestReverseService(ReverseReqData reverseReqData) throws Exception
    {
        return new ReverseService().request(reverseReqData);
    }

    /**
     * 请求对账单下载服务
     * 
     * @param downloadBillReqData
     *            这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public static String requestDownloadBillService(DownloadBillReqData downloadBillReqData) throws Exception
    {
        return new DownloadBillService().request(downloadBillReqData);
    }

    /**
     * 直接执行被扫支付业务逻辑（包含最佳实践流程）
     * 
     * @param scanPayReqData
     *            这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener
     *            商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @throws Exception
     */
    public static void doScanPayBusiness(ScanPayReqData scanPayReqData, String key, String mchId,ScanPayBusiness.ResultListener resultListener)
            throws Exception
    {
        new ScanPayBusiness().run(scanPayReqData, key, mchId, resultListener);
    }

    /**
     * 调用退款业务逻辑
     * 
     * @param refundReqData
     *            这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener
     *            业务逻辑可能走到的结果分支，需要商户处理
     * @throws Exception
     */
    public static void doRefundBusiness(RefundReqData refundReqData, String key,String mchId, RefundBusiness.ResultListener resultListener)
            throws Exception
    {
        new RefundBusiness().run(refundReqData, key, mchId, resultListener);
    }

    /**
     * 运行退款查询的业务逻辑
     * 
     * @param refundQueryReqData
     *            这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener
     *            商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @throws Exception
     */
    public static void doRefundQueryBusiness(RefundQueryReqData refundQueryReqData, String key,String mchId, 
            RefundQueryBusiness.ResultListener resultListener) throws Exception
    {
        new RefundQueryBusiness().run(refundQueryReqData, key, mchId, resultListener);
    }

    /**
     * 请求对账单下载服务
     * 
     * @param downloadBillReqData
     *            这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener
     *            商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @return API返回的XML数据
     * @throws Exception
     */
    public static void doDownloadBillBusiness(DownloadBillReqData downloadBillReqData, String key, String mchId, 
            DownloadBillBusiness.ResultListener resultListener) throws Exception
    {
        new DownloadBillBusiness().run(downloadBillReqData, key, mchId, resultListener);
    }

    /**
     * 统一下单服务
     * 
     * @param unifiedOrderReqData
     *            这个数据对象里面包含了API要求提交的各种数据字段
     * @param key 
     * @param resultListener
     *            商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @throws Exception
     */
    public static void doUnifiedOrderBusiness(UnifiedOrderReqData unifiedOrderReqData,
            String key, String mchId, ResultListener resultListener) throws Exception
    {
        new UnifiedOrderBussiness().run(unifiedOrderReqData, key, mchId, resultListener);
    }
}
