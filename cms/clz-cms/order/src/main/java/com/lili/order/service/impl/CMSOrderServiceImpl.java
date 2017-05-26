package com.lili.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.constant.Constant;
import com.lili.cms.constant.LogConstant;
import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.DateUtil;
import com.lili.cms.util.StringUtil;
import com.lili.coach.service.CMSCoachService;
import com.lili.common.util.Page;
import com.lili.common.vo.ReqResult;
import com.lili.log.model.LogCommon;
import com.lili.log.service.CMSLogCommonService;
import com.lili.order.dto.InsuranceOrder;
import com.lili.order.dto.StudentClassPoolVo;
import com.lili.order.dto.StudentClassVo;
import com.lili.order.manager.CMSOrderManager;
import com.lili.order.model.CoachComment;
import com.lili.order.model.InsuranceOrderBDTO;
import com.lili.order.model.Order;
import com.lili.order.model.OrderBDTO;
import com.lili.order.model.STOVo;
import com.lili.order.model.StuComment;
import com.lili.order.service.CMSOrderService;
import com.lili.order.service.OrderRefundService;
import com.lili.order.service.OrderService;
import com.lili.order.service.StudentClassService;
import com.lili.order.vo.OrderRefundVo;
import com.lili.student.service.CMSStudentService;

public class CMSOrderServiceImpl implements CMSOrderService{
	Logger logger = Logger.getLogger(CMSOrderServiceImpl.class);

	@Autowired
	CMSOrderManager cmsOrderManager;
	
	@Autowired
	CMSStudentService cmsStudentService;
	
	@Autowired
	CMSCoachService cmsCoachService;
	
	@Autowired
	CMSLogCommonService cmsLogCommonService;
	
	@Autowired
	OrderRefundService orderRefundService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	StudentClassService studentClassServie;
    
	@Override
	public ResponseMessage findBatch(OrderBDTO dto) throws Exception {
		dto.setSubject(StringUtil.recombinateStrByComma(dto.getSubject()));
		PagedResult<Order> batch = cmsOrderManager.findBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public List<Order> getExportSource(OrderBDTO dto) throws Exception {
		return cmsOrderManager.findExportBatch(dto);
	}

	@Override
	public ResponseMessage findOne(String orderId) throws Exception {
		Order order = cmsOrderManager.findOne(orderId);
		CoachComment coachComment = cmsOrderManager.queryCoachCom(orderId);
		List<StuComment> stuComment = cmsOrderManager.queryStuCom(orderId);
		
		return new ResponseMessage().addResult("order", order)
							.addResult("coachComment", coachComment)
							.addResult("stuComment", stuComment);
	}

	@Override
	public ResponseMessage updateByIds(LogCommon logCommon,String orderIds,String coachIds,String studentIds, String remark) throws Exception {
		List<LogCommon> logList = new ArrayList<LogCommon>();
			for(String id : orderIds.split(",")){
				try {
					orderService.cmsCancelOrder(id, Long.valueOf(logCommon.getUserId()));
					
					LogCommon _logCommon = (LogCommon)logCommon.clone();
					//String operateTime = DateUtil.formatDatetime(logCommon.getOperateTime());
					_logCommon.setRelateId(id);
					_logCommon.setRemark(remark);
					logList.add(_logCommon);
				} catch (Exception e) {
					logger.error("||| exception happended when cancle order id : " + id + " exp : " + e.getMessage());
				}
			}
			
			
			cmsLogCommonService.insertLogList(logList);
			return new ResponseMessage();
	}


	@Override
	public STOVo findCOByStudentId(long studentId) throws Exception {
		return cmsOrderManager.findCOByStudentId(studentId);
	}

	@Override
	public Long findTimeByStudentId(long studentId) throws Exception {
		return cmsOrderManager.findTimeByStudentId(studentId);
	}

	@Override
	public Integer queryTotalOrder(OrderBDTO dto) throws Exception {
		return cmsOrderManager.findTotalOrder(dto);
	}

	@Override
	public ResponseMessage refund(String orderId, String asker, String refundMoney, String remark) {
		try {
			if(refundMoney != null && !"".equals(refundMoney)){
				Order order = cmsOrderManager.findOne(orderId);
				if(order.getPayState() != 1){ //未付款的驳回
					return new ResponseMessage(MessageCode.SYSTEM_NOT_PAY);
				}
				Integer money =  Integer.valueOf(refundMoney);
				if (money <=0  || money > order.getPayTotal()/100){
					return new ResponseMessage(Constant.MONEY_OVER);
				}
				
				if(Integer.valueOf(refundMoney) <= 0){
					return new ResponseMessage(MessageCode.MSG_FAIL);
				}
				ReqResult r = orderRefundService.createOrderRefund(orderId, asker, Integer.valueOf(refundMoney), remark);
				logger.info("***********************isSuccess "+r.isSuccess());
				if (!r.isSuccess()) {
					return new ResponseMessage(MessageCode.MSG_FAIL);
				}
				return new ResponseMessage(0, MessageCode.MSG_SUCCESS);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		return new ResponseMessage(0, MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage subRefund(String orderId, String asker) {
		try {
			String[] idList  = orderId.split(",");
			Integer orderList = cmsOrderManager.queryIsUnHandleList(orderId); //判断是否可以挂起  是否是0状态
				if (orderList > 0 ) {
					return new ResponseMessage(MessageCode.MSG_HANDE_CF);
				}
			OrderRefundVo orderRefundVo;
			for (int i =0; i< idList.length; i++) {
				orderRefundVo = new OrderRefundVo();
				orderRefundVo.setOrderId(idList[i]);
				ReqResult r = orderRefundService.submitOrderRefund(orderId, asker);
				if (!r.isSuccess()) {
					return new ResponseMessage(MessageCode.MSG_FAIL);
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		return new ResponseMessage(0, MessageCode.MSG_SUCCESS);
	}

	@Override
	public String queryRefund(OrderRefundVo dto) {
		String resp =null;
		PagedResult<OrderRefundVo> enrollOrderList = null;
		try {
			enrollOrderList = cmsOrderManager.queryOrderRefundList(dto);
			resp = new ResponseMessage().addResult("pageData", enrollOrderList).build();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public ResponseMessage hangUp(String orderId, String asker, Integer channel) {
		try {
			String[] idList  = orderId.split(",");
			Integer orderList = cmsOrderManager.queryIsHangUpList(orderId); //判断是否可以挂起  是否是0、2状态
			if (orderList > 0) {
				return new ResponseMessage(MessageCode.MSG_HANDE_CF);
			}
			
			OrderRefundVo orderRefundVo;
			List<LogCommon> logCommonList = new ArrayList<LogCommon>();
			LogCommon logCommon = null;
			for (int i =0; i< idList.length; i++) {
				orderRefundVo = new OrderRefundVo();
				orderRefundVo.setOrderId(idList[i]);
				ReqResult r = orderService.handUpOrder(orderId);
				if(r.isSuccess()){
					//记录日志
					logCommon = new LogCommon();
					logCommon.setMenuId(LogConstant.MENU_ID_ORDER);
					logCommon.setAction(LogConstant.ACTION_UPDATE);
					logCommon.setOperateTime(DateUtil.now());
					logCommon.setChannel(channel);
					logCommon.setStatus(1);
					logCommon.setUserId(asker);
					logCommon.setRemark("挂起订单");
				}
				else {
					return new ResponseMessage(MessageCode.MSG_FAIL);
				}
			}
			cmsLogCommonService.insertLogList(logCommonList);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		return new ResponseMessage(0, MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage getScheduleOrders(String stime,String etime, String state, String lastMinutes, String orderId,
			String stuName, String stuMobile, String pageNo, String pageSize) {
		try {
			Page<StudentClassVo> data = studentClassServie.getScheduleOrders(stime,etime,state,lastMinutes,orderId,stuName,stuMobile,pageNo,pageSize);
			
			return new ResponseMessage().addResult("pageData", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseMessage(MessageCode.MSG_FAIL);
	}

	@Override
	public ResponseMessage getScheduleCoaches(String orderId, String dltype, String isVip, String pageNo, String pageSize) {
		try {
			Page<StudentClassPoolVo> data = studentClassServie.getScheduleCoaches(orderId, dltype, isVip,pageNo,pageSize);
			
			return new ResponseMessage().addResult("pageData", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseMessage(MessageCode.MSG_FAIL);
	}

	@Override
	public ResponseMessage addScheduleCoach(String orderId, String coachId, String placeId, String placeName,
			String placeLge, String placeLae) {
		try {
			ReqResult data = studentClassServie.addScheduleCoach(orderId, coachId, placeId, placeName, placeLge, placeLae);
			return new ResponseMessage().addResult("pageData", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseMessage(MessageCode.MSG_FAIL);
	}

	@Override
	public ResponseMessage getScheduleNotice() {
		try {
			Integer data = studentClassServie.getScheduleNoticeCount();
			return new ResponseMessage().addResult("pageData", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseMessage(MessageCode.MSG_FAIL);
	}


	@Override
	public int findTeachTimeByCoachId(OrderBDTO dto) throws Exception {
		return cmsOrderManager.findTeachTimeByCoachId(dto);
	}
	
	@Override
	public ResponseMessage findInsuranceList(InsuranceOrderBDTO insuranceOrder) throws Exception{
		PagedResult<InsuranceOrderBDTO> batch = cmsOrderManager.findInsuranceList(insuranceOrder);
		return new ResponseMessage().addResult("pageData", batch);
	}
	
	@Override
	public ResponseMessage updateInsurance(InsuranceOrderBDTO insuranceOrder) throws Exception{
		ResponseMessage respone = new ResponseMessage();
		String visitRemark = insuranceOrder.getVisitRemark();
		String compensateRemark = insuranceOrder.getCompensateRemark();
		String refundRemark = insuranceOrder.getRefundRemark();
		boolean refundCheck = true;
		if (insuranceOrder.getVisitState() != null && insuranceOrder.getVisitState() == 1) {
			insuranceOrder.setVisitTime(new Date());
		} else if (insuranceOrder.getRefundState() != null && insuranceOrder.getRefundState() == 1) {
			insuranceOrder.setRefundTime(new Date());
		} else if (insuranceOrder.getCompensate() != null && insuranceOrder.getCompensate() == 1) {
			insuranceOrder.setCompensateTime(new Date());
		} 
		if (insuranceOrder.getRefundState() != null && insuranceOrder.getRefundState() == 1 
				&& insuranceOrder.getRefundPrice() != null) {
			InsuranceOrderBDTO insurance = cmsOrderManager.findInsuranceById(insuranceOrder.getInsuranceId());
			if (insuranceOrder.getRefundPrice() > insurance.getPrice()) {
				respone.setCode(-1);
				respone.setMsg("退款金额不能大于缴款金额！");
				refundCheck = false;
			}
		}
		if ((visitRemark != null && visitRemark.length() > 200) || (compensateRemark != null && compensateRemark.length() > 200)
				|| (refundRemark != null && refundRemark.length() > 200)) {
			respone.setCode(-1);
			respone.setMsg("备注过长，备注不能超过200字！");
		} else if (refundCheck)
			cmsOrderManager.updateInsurance(insuranceOrder);
		return respone;
	}
	
	@Override
	public ResponseMessage findInsuranceById(String insuranceId) throws Exception{
		InsuranceOrderBDTO bdto= cmsOrderManager.findInsuranceById(insuranceId);
		return new ResponseMessage().addResult("pageData", bdto);
	}
	
	@Override
	public List<InsuranceOrderBDTO> getInsuranceExportSource(InsuranceOrderBDTO insuranceOrderBDTO) throws Exception {
		return cmsOrderManager.getInsuranceExportSource(insuranceOrderBDTO);
	}


	
}




