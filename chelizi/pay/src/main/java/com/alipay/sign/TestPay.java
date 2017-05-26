/**
 * 
 */
package com.alipay.sign;

import com.alipay.config.AlipayConfig;
import com.lili.common.util.StringUtil;

/**
 * @author linbo
 *
 */
public class TestPay
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        String orderString = ZFBPayDemo.pay(StringUtil.getOrderId(), "test", "test", 0.01, AlipayConfig.callback_url, 0);
        System.out.println(orderString); 
        //String result = HttpUtil.doPost("https://openapi.alipay.com/gateway.do?", orderString, "UTF-8");
       // System.out.println(result);
    }

}
