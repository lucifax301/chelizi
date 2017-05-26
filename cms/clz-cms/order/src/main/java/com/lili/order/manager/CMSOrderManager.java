package com.lili.order.manager;

import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.order.dto.InsuranceOrder;
import com.lili.order.model.CoachComment;
import com.lili.order.model.InsuranceOrderBDTO;
import com.lili.order.model.Order;
import com.lili.order.model.OrderBDTO;
import com.lili.order.model.STOVo;
import com.lili.order.model.StuComment;
import com.lili.order.vo.OrderRefundVo;

public interface CMSOrderManager {

	  public PagedResult<Order> findBatch(OrderBDTO dto) throws Exception;

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
	  
	  public Integer findTotalOrder(OrderBDTO dto) throws Exception;

	public CoachComment queryCoachCom(String orderId);

	public List<StuComment> queryStuCom(String orderId);

	public PagedResult<OrderRefundVo> queryOrderRefundList(OrderRefundVo dto);

	public Integer queryIsHangUpList(String orderId);
	
	public Integer queryIsUnHandleList(String orderId);
	  
	public int findTeachTimeByCoachId(OrderBDTO dto);  
	
	public PagedResult<InsuranceOrderBDTO>  findInsuranceList(InsuranceOrderBDTO insuranceOrder) throws Exception;
	
	public void updateInsurance(InsuranceOrderBDTO insuranceOrder) throws Exception;
	
	public InsuranceOrderBDTO findInsuranceById(String insuranceId) throws Exception;
	
	public List<InsuranceOrderBDTO> getInsuranceExportSource(InsuranceOrderBDTO insuranceOrderBDTO) throws Exception;
}
