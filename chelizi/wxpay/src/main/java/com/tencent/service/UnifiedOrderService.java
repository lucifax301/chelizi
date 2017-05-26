/**
 * 
 */
package com.tencent.service;

import com.tencent.common.Configure;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderReqData;

/**
 * @author linbo
 *
 */
public class UnifiedOrderService extends BaseService
{

    /**
     * @param api
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public UnifiedOrderService() throws ClassNotFoundException, IllegalAccessException,
            InstantiationException
    {
        super(Configure.UNIFIED_ORDER_API);
    }


    /**
     * 请求支付服务
     * @param unifiedPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public String request(UnifiedOrderReqData unifiedPayReqData) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(unifiedPayReqData);

        return responseString;
    }
}
