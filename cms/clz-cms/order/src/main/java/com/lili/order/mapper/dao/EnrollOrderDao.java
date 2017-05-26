package com.lili.order.mapper.dao;


import java.util.List;

import com.lili.order.vo.EnrollOrderBalanceVo;
import com.lili.order.vo.EnrollOrderVo;

public interface EnrollOrderDao {
	
	EnrollOrderVo queryEnrollOrderInfo(String orderId);
	
	EnrollOrderVo queryEnrollOrderByStudentId(EnrollOrderVo enrollOrder);

	List<EnrollOrderVo>  queryEnrollOrderList(EnrollOrderVo enrollOrder);

	int insert(EnrollOrderVo enrollOrder);

	int insertSelective(EnrollOrderVo enrollOrder);

	int updateByPrimaryKeySelective(EnrollOrderVo enrollOrder);
	
	void updateByOrderId(EnrollOrderVo enrollOrder);

	int updateByPrimaryKey(EnrollOrderVo enrollOrder);

	List<EnrollOrderBalanceVo> queryEnrollOrderBalanceList(EnrollOrderBalanceVo enrollOrderBalanceVo);
}