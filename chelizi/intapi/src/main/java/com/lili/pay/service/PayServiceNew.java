/**
 *
 */
package com.lili.pay.service;

import com.lili.common.vo.ReqResult;
import com.lili.pay.vo.PayVo;
import com.lili.pay.vo.QQPayCallbackResData;

import java.util.Map;

/**
 * @author linbo
 *         新版支付接口
 */
public interface PayServiceNew {

    ReqResult pay(PayVo pay);

    ReqResult wxPayCallback(String result);

    ReqResult zfbPayCallback(Map<String, String> params);

    ReqResult qqPayCallback(QQPayCallbackResData qqPayCallbackResData);

    ReqResult ylPayCallback(Map<String, String> reqParam, String encoding);

    String getOpenId(String code);
}
