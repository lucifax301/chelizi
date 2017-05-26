/**
 * 
 */
package com.lili.pay.service;

import java.util.Date;
import java.util.Map;

import com.lili.common.vo.ReqResult;
import com.lili.order.vo.OrderVo;

/**
 * @author linbo
 * 支付服务
 */
public interface PayService
{
    public ReqResult pay(String orderId, long couponId, long studentId, String payWay, String tokenId);
    
    public ReqResult wxPayCallBack(String result);
    
    public ReqResult zfbPayCallBack(Map<String, String> params);
    
    public ReqResult getPayConfig(String studentId, String tokenId);
    
    public ReqResult getMoneyLog(long userId, int userType, int page, int pageSize);
    
    public ReqResult getPerformanceLog(long coachId, int page,int pageSize);
    
    public ReqResult doPayAction(OrderVo orderVo, long couponId, String payType, double discount, String waterNum,
            Date payTime) throws Exception;

    public ReqResult qqPayCallBack(String result);
}
