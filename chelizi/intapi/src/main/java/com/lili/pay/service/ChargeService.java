/**
 * 
 */
package com.lili.pay.service;

import java.util.Map;

import com.lili.common.vo.ReqResult;
import com.lili.pay.vo.ChargeVo;

/**
 * @author linbo
 *
 */
public interface ChargeService
{
    public ReqResult charge(ChargeVo chargeVo, String tokenId);

    public ReqResult wxChargeCallBack(String result);
    
    public ReqResult zfbChargeCallBack(Map<String, String> params);
}
