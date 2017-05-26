package com.lili.order.service;

import java.util.List;

import com.lili.cms.entity.ResponseMessage;
import com.lili.log.model.LogCommon;
import com.lili.order.dto.InsuranceOrder;
import com.lili.order.model.InsuranceOrderBDTO;
import com.lili.order.model.Order;
import com.lili.order.model.OrderBDTO;
import com.lili.order.model.STOVo;
import com.lili.order.vo.OrderRefundVo;


public  interface CMSOrderService   {

	public   ResponseMessage findBatch(OrderBDTO dto) throws Exception;

	public   ResponseMessage findOne(String orderId) throws Exception;

	public  ResponseMessage updateByIds(LogCommon logCommon,String orderIds,String coachIds,String studentIds, String remark) throws Exception;

	public  List<Order> getExportSource(OrderBDTO dto) throws Exception;
	
	public  Integer queryTotalOrder(OrderBDTO dto) throws Exception;

	public STOVo findCOByStudentId(long studentId) throws Exception;

	public Long findTimeByStudentId(long studentId) throws Exception;

	public ResponseMessage hangUp(String orderId, String asker, Integer channel);

	public ResponseMessage refund(String orderId, String asker, String refundMoney, String remark);

	public ResponseMessage subRefund(String orderId, String asker);

	public String queryRefund(OrderRefundVo dto);

	/**
	 * CMS-订单调度-查询订单列表
	 * @param ctime
	 * @param state
	 * @param lastMinutes
	 * @param orderId
	 * @param stuName
	 * @param stuMobile
	 * @param pageSize 
	 * @param pageNo 
	 * @return
	 */
	public ResponseMessage getScheduleOrders(String stime,String etime, String state, String lastMinutes, String orderId,
			String stuName, String stuMobile, String pageNo, String pageSize);
	
	/**
	 * CMS订单调度-获取可供调度的教练
	 * @param orderId
	 * @param dltype
	 * @param isVip
	 * @return
	 */
	public ResponseMessage getScheduleCoaches(String orderId, String dltype, String isVip, String pageNo, String pageSize);
	
	/**
	 * CMS订单调度-设置接单调度教练
	 * @param orderId
	 * @param coachId
	 * @param placeId
	 * @param placeName
	 * @param placeLge
	 * @param placeLae
	 * @return
	 */
	public ResponseMessage addScheduleCoach(String orderId, String coachId, String placeId,String placeName,String placeLge,String placeLae);
	
	/**
	 * 获取教练带教时长
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int findTeachTimeByCoachId(OrderBDTO dto) throws Exception;
	/**
	 * CMS订单调度-获取需要调度的学员数量。订单正常待接单中，且无教练接单。
	 * @return
	 */
	public ResponseMessage getScheduleNotice();
	
	public ResponseMessage findInsuranceList(InsuranceOrderBDTO insuranceOrder) throws Exception;
	
	public ResponseMessage updateInsurance(InsuranceOrderBDTO insuranceOrder) throws Exception;
	
	public ResponseMessage findInsuranceById(String insuranceId) throws Exception;

	public  List<InsuranceOrderBDTO> getInsuranceExportSource(InsuranceOrderBDTO dto) throws Exception;

}













