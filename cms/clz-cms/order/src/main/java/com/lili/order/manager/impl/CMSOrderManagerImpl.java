package com.lili.order.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.order.dto.InsuranceOrder;
import com.lili.order.manager.CMSOrderManager;
import com.lili.order.mapper.dao.CoachCommentDao;
import com.lili.order.mapper.dao.OrderMapper;
import com.lili.order.mapper.dao.OrderRefundDao;
import com.lili.order.mapper.dao.StuCommentDao;
import com.lili.order.model.CoachComment;
import com.lili.order.model.InsuranceOrderBDTO;
import com.lili.order.model.Order;
import com.lili.order.model.OrderBDTO;
import com.lili.order.model.STOVo;
import com.lili.order.model.StuComment;
import com.lili.order.vo.OrderRefundVo;

public class CMSOrderManagerImpl implements CMSOrderManager{

	@Autowired
	OrderMapper orderMapper;
	
	@Autowired
	CoachCommentDao coachCommentDao;
	
	@Autowired
	StuCommentDao stuCommentDao;
	
	@Autowired
	OrderRefundDao orderRefundDao;
	
	@Override
	public PagedResult<Order> findBatch(OrderBDTO dto) throws Exception {
		int pageNo = dto.getPageNo()<=0?PageUtil.DEFAULT_PAGE_NO:dto.getPageNo();
		int pageSize = dto.getPageSize()<=0?PageUtil.DEFAULT_PAGE_SIZE:dto.getPageSize();
		int startNum = pageNo==0?0:(pageNo-1)*pageSize;
		//这里暂时将pageNo替换成查询的起始行数
		dto.setPageNo(startNum);
		Long total = orderMapper.findBatchTotal(dto);
		List<Order> batch = null;
		if(1 == dto.getCmsChannel()){
			batch = orderMapper.findLiliBatch(dto);
		}else {
			batch = orderMapper.findJxBatch(dto);
		}
		
		List<String> orderIds = new ArrayList<String>();
		List<Order> payWayList = null;
		List<Order> susList = null;
		if(batch != null && batch.size() > 0){
			for(Order order : batch){
				orderIds.add(order.getOrderId());
			}
			/**
			 * 单独查询支付方式和持续时间,尽量避免表关联
			 */
			payWayList = orderMapper.findPayWay(orderIds);
			susList = orderMapper.findSustainTime(orderIds);

			if(payWayList != null && payWayList.size() > 0){
				for(Order order : payWayList){

						for(Order _order : batch){
							if(_order.getOrderId().equals(order.getOrderId())){
								_order.setPayWay(order.getPayWay());
								break;
							}
						}
				}
			}
			
			if(susList != null && susList.size() > 0){
				for(Order order : susList){

						for(Order _order : batch){
							if(_order.getOrderId().equals(order.getOrderId())){
								_order.setSustainTime(order.getSustainTime());
								break;
							}
						}
				}
			}
		}

		PagedResult<Order> result = new PagedResult<Order>();
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setDataList(batch);
        result.setTotal(total);
        result.setPages((total/pageSize)+1);
		return result;
	}


	@Override
	public List<Order> findExportBatch(OrderBDTO dto) throws Exception {
//		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return orderMapper.findExportBatch(dto);
	}

	@Override
	public Order findOne(String orderId) throws Exception {
		return orderMapper.findOne(orderId);
	}

	@Override
	public Long findTimeByStudentId(long studentId) throws Exception {
		return orderMapper.findTimeByStudentId(studentId);
	}

	@Override
	public STOVo findCOByStudentId(long studentId) throws Exception {
		return orderMapper.findCOByStudentId(studentId);
	}

	@Override
	public long updateByIds(String orderIds) throws Exception {
		return orderMapper.updateByIds(orderIds);
	}

	@Override
	public Integer findTotalOrder(OrderBDTO dto) throws Exception {
		return orderMapper.findTotalOrder(dto);
	}

	@Override
	public CoachComment queryCoachCom(String orderId) {
		CoachComment record = new CoachComment();
		try {
			CoachComment coachComment = new CoachComment();
			coachComment.setOrderId(orderId);
			record = coachCommentDao.queryByOrderId(coachComment );
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return record;
	}

	@Override
	public List<StuComment> queryStuCom(String orderId) {
		List<StuComment> stuList = new ArrayList<StuComment>();
		try {
			StuComment record = new StuComment();
			record.setOrderId(orderId);
			stuList = stuCommentDao.queryByOrderId(record );
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return stuList;
	}

	@Override
	public PagedResult<OrderRefundVo> queryOrderRefundList(OrderRefundVo dto) {
		try {
			PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
			return BeanUtil.toPagedResult((orderRefundDao.queryOrderRefundList(dto)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer queryIsHangUpList(String orderId) {
		Integer orderSum = null;
		try {
			orderSum = orderMapper.queryIsHangUpList(orderId);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return orderSum;
	}

	@Override
	public Integer queryIsUnHandleList(String orderId) {
		Integer orderList = null;
		try {
			orderList = orderRefundDao.queryIsUnHandleList(orderId);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return orderList;
	}


	@Override
	public int findTeachTimeByCoachId(OrderBDTO dto) {
		return orderMapper.findTeachTimeByCoachId(dto);
	}
	
	@Override
	public PagedResult<InsuranceOrderBDTO> findInsuranceList(InsuranceOrderBDTO insuranceOrder) throws Exception {
		int pageNo = insuranceOrder.getPageNo()<=0?PageUtil.DEFAULT_PAGE_NO:insuranceOrder.getPageNo();
		int pageSize = insuranceOrder.getPageSize()<=0?PageUtil.DEFAULT_PAGE_SIZE:insuranceOrder.getPageSize();
		int startNum = pageNo==0?0:(pageNo-1)*pageSize;
		insuranceOrder.setPageNo(startNum);
		insuranceOrder.setPageSize(pageSize);
		int total=orderMapper.findInsuranceTotal(insuranceOrder);
		List<InsuranceOrderBDTO> list=orderMapper.findInsuranceList(insuranceOrder);
		PagedResult<InsuranceOrderBDTO> result = new PagedResult<InsuranceOrderBDTO>();
        result.setPageNo(insuranceOrder.getPageNo());
        result.setPageSize(insuranceOrder.getPageSize());
        result.setDataList(list);
        result.setTotal(total);
        result.setPages((total/insuranceOrder.getPageSize())+1);
		return result;
	}
	
	@Override
	public void updateInsurance(InsuranceOrderBDTO insuranceOrder) throws Exception {
		orderMapper.updateInsurance(insuranceOrder);
		
	}
	
	@Override
	public InsuranceOrderBDTO findInsuranceById(String insuranceId) throws Exception {
		return orderMapper.findInsuranceById(insuranceId);
		
	}
	
	public List<InsuranceOrderBDTO> getInsuranceExportSource(InsuranceOrderBDTO insuranceOrder){
		insuranceOrder.setPageNo(0);
		insuranceOrder.setPageSize(1000);
		List<InsuranceOrderBDTO> list=orderMapper.findInsuranceList(insuranceOrder);
		return list;
	}
	

	
}
