package com.lili.order.service;

import com.lili.common.vo.ReqResult;

/**
 * Created by ZLong on 2016/六月/2.
 */
public interface OrderRefundService {

    /**
     * 添加订单退款记录
     *
     * @param orderId
     * @param asker
     * @param refundMoney
     * @param remark
     * @return
     */
    ReqResult createOrderRefund(String orderId, String asker, int refundMoney, String remark);

    /**
     * 确认订单已退款
     *
     * @param orderId
     * @param operator
     * @return
     */
    ReqResult submitOrderRefund(String orderId, String operator);
}
