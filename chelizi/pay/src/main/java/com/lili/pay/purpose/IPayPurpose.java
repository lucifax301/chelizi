/**
 * 
 */
package com.lili.pay.purpose;

import com.lili.common.vo.ReqResult;
import com.lili.pay.vo.PayVo;

import java.util.Date;

/**
 * @author linbo
 * 支付回调
 */
public interface IPayPurpose
{
    /**
     * 支付前预处理
     * @param payVo
     * @param reqResult
     * @return
     */
    Object purposeAdvance(final PayVo payVo, final ReqResult reqResult) throws Exception;
    
    /**
     * 支付结果回调处理
     * @param payVo
     * @param endTime
     * @param totalFee
     * @param waterNum 
     * @return
     */
    ReqResult doPurpose(PayVo payVo, Date endTime, String waterNum, int totalFee);
}
