/**
 * 
 */
package com.tencent.business;

import com.tencent.protocol.pay_query_protocol.ScanPayQueryResData;
import com.tencent.protocol.reverse_protocol.ReverseResData;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderResData;

/**
 * @author linbo
 *
 */
public interface ResultListener
{
    // API返回ReturnCode不合法，支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问
    void onFailByReturnCodeError(UnifiedOrderResData unifiedOrderResData);

    // API返回ReturnCode为FAIL，支付API系统返回失败，请检测Post给API的数据是否规范合法
    void onFailByReturnCodeFail(UnifiedOrderResData unifiedOrderResData);

    // 支付请求API返回的数据签名验证失败，有可能数据被篡改了
    void onFailBySignInvalid(UnifiedOrderResData unifiedOrderResData);

    // 查询请求API返回的数据签名验证失败，有可能数据被篡改了
    void onFailByQuerySignInvalid(ScanPayQueryResData scanPayQueryResData);

    // 撤销请求API返回的数据签名验证失败，有可能数据被篡改了
    void onFailByReverseSignInvalid(ReverseResData reverseResData);

    // 用户余额不足，换其他卡支付或是用现金支付
    void onFailByMoneyNotEnough(UnifiedOrderResData unifiedOrderResData);

    // 支付失败
    void onFail(UnifiedOrderResData unifiedOrderResData);

    // 支付成功
    void onSuccess(UnifiedOrderResData unifiedOrderResData, String prepayId);

    // 没有权限
    void onFailByNoAuth(UnifiedOrderResData unifiedOrderResData);

    // 订单已关闭
    void onFailByOrderClosed(UnifiedOrderResData unifiedOrderResData);

    // 系统超时
    void onFailBySystemErr(UnifiedOrderResData unifiedOrderReqData);

}
