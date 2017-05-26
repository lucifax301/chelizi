package com.lili.order.mapper.dao;

import java.util.List;

import com.lili.order.model.InsuranceOrderBDTO;
import com.lili.order.model.Order;
import com.lili.order.model.OrderBDTO;
import com.lili.order.model.STOVo;

public interface OrderMapper{

	/**
	 * 查询对应支付方式
	 * @param orderIds
	 * @return
	 * @throws Exception
	 */
	public List<Order> findPayWay(List<String> orderIds) throws Exception;

	/**
	 * 查询对应持续时间
	 * @param orderIds
	 * @return
	 * @throws Exception
	 */
	public List<Order> findSustainTime(List<String> orderIds) throws Exception;
	
	/**
	 * 获取驾校对应订单分页数据
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List<Order> findJxBatch(OrderBDTO dto) throws Exception;
	
	/**
	 * 获取所有订单数据分页数据
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List<Order> findLiliBatch(OrderBDTO dto) throws Exception;

	/**
	 * 获取需要导出的订单数据,格式和findBatch一致,只是改了筛选条件
	 * @param params 必含coachIds
	 * @return
	 */
	public List<Order> findExportBatch(OrderBDTO dto) throws Exception;


	public Order findOne(String orderId) throws Exception;

	/**
	 * 根据学员ID获取约课时长
	 * @param params
	 * @return
	 */
	public Long findTimeByStudentId(long studentId) throws Exception;

	/**
	 * 
	 * @param params 必含学员ID
	 * @return 学员详情处,关联教练的报表实体
	 */
	public STOVo findCOByStudentId(long studentId) throws Exception;

	public long updateByIds(String orderIds) throws Exception;

	public Integer findTotalOrder(OrderBDTO dto);

	public Long findBatchTotal(OrderBDTO dto);
	
	public Integer queryIsHangUpList(String orderId);
	
	public int findTeachTimeByCoachId(OrderBDTO dto);
	
	public int findInsuranceTotal(InsuranceOrderBDTO insuranceOrder);
	
	public List<InsuranceOrderBDTO> findInsuranceList(InsuranceOrderBDTO insuranceOrder);
	
	public void updateInsurance(InsuranceOrderBDTO insuranceOrder);
	
	public InsuranceOrderBDTO findInsuranceById(String insuranceId);
}
