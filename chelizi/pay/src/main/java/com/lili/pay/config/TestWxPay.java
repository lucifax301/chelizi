/**
 * 
 */
package com.lili.pay.config;

import java.util.Date;

import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.pay.vo.ChargeVo;
import com.tencent.WXPay;
import com.tencent.business.ResultListener;
import com.tencent.common.Configure;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryResData;
import com.tencent.protocol.reverse_protocol.ReverseResData;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderReqData;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderResData;

/**
 * @author linbo
 *
 */
public class TestWxPay
{
    private static class Listeners implements ResultListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see com.tencent.business.UnifiedOrderBussiness.ResultListener#onFailByReturnCodeError(com.tencent.protocol.
         * unified_order_protocol.UnifiedOrderResData)
         */
        @Override
        public void onFailByReturnCodeError(UnifiedOrderResData unifiedOrderResData)
        {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * 
         * @see com.tencent.business.UnifiedOrderBussiness.ResultListener#onFailByReturnCodeFail(com.tencent.protocol.
         * unified_order_protocol.UnifiedOrderResData)
         */
        @Override
        public void onFailByReturnCodeFail(UnifiedOrderResData unifiedOrderResData)
        {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * 
         * @see com.tencent.business.UnifiedOrderBussiness.ResultListener#onFailBySignInvalid(com.tencent.protocol.
         * unified_order_protocol.UnifiedOrderResData)
         */
        @Override
        public void onFailBySignInvalid(UnifiedOrderResData unifiedOrderResData)
        {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * 
         * @see com.tencent.business.UnifiedOrderBussiness.ResultListener#onFailByQuerySignInvalid(com.tencent.protocol.
         * pay_query_protocol.ScanPayQueryResData)
         */
        @Override
        public void onFailByQuerySignInvalid(ScanPayQueryResData scanPayQueryResData)
        {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tencent.business.UnifiedOrderBussiness.ResultListener#onFailByReverseSignInvalid(com.tencent.protocol
         * .reverse_protocol.ReverseResData)
         */
        @Override
        public void onFailByReverseSignInvalid(ReverseResData reverseResData)
        {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * 
         * @see com.tencent.business.UnifiedOrderBussiness.ResultListener#onFailByMoneyNotEnough(com.tencent.protocol.
         * unified_order_protocol.UnifiedOrderResData)
         */
        @Override
        public void onFailByMoneyNotEnough(UnifiedOrderResData unifiedOrderResData)
        {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tencent.business.UnifiedOrderBussiness.ResultListener#onFail(com.tencent.protocol.unified_order_protocol
         * .UnifiedOrderResData)
         */
        @Override
        public void onFail(UnifiedOrderResData unifiedOrderResData)
        {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tencent.business.UnifiedOrderBussiness.ResultListener#onSuccess(com.tencent.protocol.unified_order_protocol
         * .UnifiedOrderResData, java.lang.String)
         */
        @Override
        public void onSuccess(UnifiedOrderResData unifiedOrderResData, String prepayId)
        {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * 
         * @see com.tencent.business.UnifiedOrderBussiness.ResultListener#onFailByNoAuth(com.tencent.protocol.
         * unified_order_protocol.UnifiedOrderResData)
         */
        @Override
        public void onFailByNoAuth(UnifiedOrderResData unifiedOrderResData)
        {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * 
         * @see com.tencent.business.UnifiedOrderBussiness.ResultListener#onFailByOrderClosed(com.tencent.protocol.
         * unified_order_protocol.UnifiedOrderResData)
         */
        @Override
        public void onFailByOrderClosed(UnifiedOrderResData unifiedOrderResData)
        {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * 
         * @see com.tencent.business.UnifiedOrderBussiness.ResultListener#onFailBySystemErr(com.tencent.protocol.
         * unified_order_protocol.UnifiedOrderResData)
         */
        @Override
        public void onFailBySystemErr(UnifiedOrderResData unifiedOrderReqData)
        {
            // TODO Auto-generated method stub

        }

    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // 初始化微信SDK
        WXPay.initSDKConfiguration("",
                "4324", "324", "127.0.0.1",
                "http://218.17.39.227:9066/payaccess/v1/files/wxChargeCallBack",
                "APP", true);
        
        ChargeVo chargeVo = new ChargeVo();
        String aString = new String();
        chargeVo.setBankCard(aString);
        chargeVo.setChargeId(StringUtil.getOrderId());
        chargeVo.setChargeType("weixin");
        chargeVo.setChargeValue(100);
        chargeVo.setUserId(23244234);
        chargeVo.setUserType(2);
        String appId = "wx818477ab629673e2";
        String keyString = "2619c31fb059043b075d7ccfed1655a9";
        String mchId = "1312481501";
        UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData("喱喱充值", chargeVo.getBankCard(),
                chargeVo.getChargeId(), chargeVo.getChargeValue(),
                "APP", "127.0.0.1", TimeUtil.getDateFormat(new Date(), "yyyyMMddHHmmss"), TimeUtil.getDateFormat(
                        TimeUtil.addDate(
                                new Date(), 600000), "yyyyMMddHHmmss"), Configure.getNotifyUrl(), appId, keyString, "APP", "", mchId);
        
        try
        {
            WXPay.doUnifiedOrderBusiness(unifiedOrderReqData, keyString, mchId, new Listeners());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
