package com.lili.order.dao.mapper;

import com.lili.order.dao.po.OrderRefundPo;

public interface OrderRefundMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderRefundPo record);

    int insertSelective(OrderRefundPo record);

    OrderRefundPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderRefundPo record);

    int updateByPrimaryKey(OrderRefundPo record);

    OrderRefundPo selectByOrderId(String orderId);
}