package com.lili.order.service.impl;

import com.lili.common.constant.OrderConstant;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.order.dao.mapper.OrderRefundMapper;
import com.lili.order.dao.po.OrderRefundPo;
import com.lili.order.service.OrderRefundService;
import com.lili.order.service.OrderService;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.pay.manager.MoneyManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 订单退款
 * <p/>
 * Created by ZLong on 2016/六月/2.
 */
public class OrderRefundServiceImpl implements OrderRefundService {
    private Logger logger = Logger.getLogger(OrderRefundServiceImpl.class);


    @Autowired
    private OrderService orderService;

    @Autowired
    private MoneyManager moneyManager;

    @Autowired
    private OrderRefundMapper orderRefundMapper;


    @Override
    public ReqResult createOrderRefund(String orderId, String asker, int refundMoney, String remark) {

        ReqResult result = ReqResult.getFailed();

        try {
            OrderVo orderVo = orderService.queryOrderById(orderId, new OrderQuery());
            if (null != orderVo) {

                //订单状态更新为正在退款中
                orderVo.setPayState(OrderConstant.PAYSTATE.ON_REFUND);
                orderService.updateByOrderId(orderVo, orderId);

                //添加退款记录
                OrderRefundPo orderRefund = new OrderRefundPo();
                orderRefund.setOrder_id(orderId);
                orderRefund.setAsker(asker);
                orderRefund.setRefundMoney(refundMoney);
                orderRefund.setRemark(remark);

                orderRefundMapper.insertSelective(orderRefund);

                result = ReqResult.getSuccess();
            } else {
                result = ReqResult.getParamError();
            }
        } catch (Exception e) {
            logger.error("createOrderRefund fail!", e);
            result.setCode(ResultCode.ERRORCODE.EXCEPTION);
            result.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }


        return result;
    }


    @Override
    public ReqResult submitOrderRefund(String orderId, String operator) {
        ReqResult result = ReqResult.getFailed();

        try {

            OrderVo orderVo = orderService.queryOrderById(orderId, new OrderQuery());
            OrderRefundPo orderRefund = orderRefundMapper.selectByOrderId(orderId);

            if (null != orderVo && null != orderRefund) {

                //记录资金流水
                moneyManager.handleOrderRefundMoneyFlow(orderVo, orderRefund.getRefundMoney());

                //订单状态更新为已退款中
                orderVo.setPayState(OrderConstant.PAYSTATE.HAS_REFUND);
                orderService.updateByOrderId(orderVo, orderId);

                //修改退款记录
                orderRefund.setStatus((byte) 1);//表示已退款成功
                orderRefund.setOperator(operator);
                orderRefundMapper.updateByPrimaryKey(orderRefund);

                result = ReqResult.getSuccess();
            } else {
                result = ReqResult.getParamError();
            }
        } catch (Exception e) {
            logger.error("submitOrderRefund fail!", e);
            result.setCode(ResultCode.ERRORCODE.EXCEPTION);
            result.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }


        return result;
    }
}
