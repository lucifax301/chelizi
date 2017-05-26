package com.lili.order.mapper.dao;


import java.util.List;

import com.lili.order.vo.OrderRefundVo;

public interface OrderRefundDao {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderRefundVo record);

    int insertSelective(OrderRefundVo record);

    OrderRefundVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderRefundVo record);

    int updateByPrimaryKey(OrderRefundVo record);

    OrderRefundVo selectByOrderId(String orderId);

	List<OrderRefundVo> queryOrderRefundList(OrderRefundVo dto);

	Integer queryIsUnHandleList(String orderId);
}