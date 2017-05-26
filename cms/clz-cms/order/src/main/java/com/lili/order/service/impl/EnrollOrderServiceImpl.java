package com.lili.order.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.order.manager.IEnrollOrderManager;
import com.lili.order.service.ICmsEnrollOrderService;
import com.lili.order.vo.EnrollOrderBalanceVo;
import com.lili.order.vo.EnrollOrderVo;
import com.lili.school.manager.SchoolManager;

public class EnrollOrderServiceImpl implements ICmsEnrollOrderService {
	Logger logger = Logger.getLogger(EnrollOrderServiceImpl.class);
	
	@Autowired
	IEnrollOrderManager enrollOrderManager;
	
	@Autowired
	SchoolManager schoolManager;

	@Override
	public String query(EnrollOrderVo enrollOrderVo, Long schoolNo) {
		String resp =null;
		PagedResult<EnrollOrderVo> enrollOrderList = null;
		try {
			//enrollOrderVo.setIsdel(0);
			if( schoolNo != null && !"".equals(schoolNo)){
				enrollOrderVo.setSchoolNo(schoolNo);
			}
			enrollOrderList = enrollOrderManager.queryEnrollOrderList(enrollOrderVo);
			resp = new ResponseMessage().addResult("pageData", enrollOrderList).build();
		}
		catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public ResponseMessage detail(String orderId) {
		EnrollOrderVo enrollOrder = null;
		try {
			enrollOrder = enrollOrderManager.queryDetailInfo(orderId);
			return new ResponseMessage().addResult("pageData", enrollOrder);
		}
		catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
	}

	@Override
	public ResponseMessage update(String orderId) {
		try {
			if(orderId == null || "".equals(orderId)){
				return new ResponseMessage().addResult("pageData", orderId);
			}
			enrollOrderManager.updateEnrollOrder(orderId);
			logger.info("-------------------------------- EnrollOrderController  Update Success!");
		} 
		catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		 
		return new ResponseMessage(MessageCode.MSG_SUCCESS);
	}

	@Override
	public EnrollOrderVo queryEnrollOrderByStudentId(EnrollOrderVo enrollOrderVo) {
		EnrollOrderVo enrollOrder = null;
		try {
			enrollOrder = enrollOrderManager.queryEnrollOrderByStudentId(enrollOrderVo);
		}
		catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return enrollOrder;
	}

	/**
	 * 查询报名订单
	 */
	@Override
	public String balanceQuery(EnrollOrderBalanceVo enrollOrderBalanceVo) {
		String resp =null;
		PagedResult<EnrollOrderBalanceVo> enrollOrderList = null;
		try {
			enrollOrderBalanceVo.setOrderState(ReqConstants.ENROLL_ORDER_CHECKOUT_WAIT);//查询未结款的记录
			enrollOrderBalanceVo.setPayState(100); //查询支付成功的记录
			if(null == enrollOrderBalanceVo.getApplyexam()||enrollOrderBalanceVo.getApplyexam()<ReqConstants.PROG_STAGE_THEORY){
				enrollOrderBalanceVo.setApplyexam(ReqConstants.PROG_STAGE_TABLE_OFFICAIL);//只有分配了驾校的学员，才能把订单结算给驾校
				enrollOrderBalanceVo.setApplystate(null);
			}
			enrollOrderList = enrollOrderManager.queryEnrollOrderBalanceList(enrollOrderBalanceVo);
			resp = new ResponseMessage().addResult("pageData", enrollOrderList).build();
		}
		catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 结款报名费给驾校
	 */
	@Override
	public ResponseMessage payment(String orderId, String asker) {
		
		try {
			String[] idList  = orderId.split(",");
			for (int i =0; i< idList.length; i++) {
				ReqResult r = schoolManager.checkoutEnroll2School(idList[i], asker);
				if(r.isSuccess()){
					logger.info("************************************************** success: " + idList[i]);
				}
				else {
					return new ResponseMessage(r.getMsgInfo());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		 
		return new ResponseMessage(0, MessageCode.MSG_SUCCESS);
	}

	@Override
	public String payQuery(EnrollOrderBalanceVo enrollOrderBalanceVo) {
		String resp =null;
		PagedResult<EnrollOrderBalanceVo> enrollOrderList = null;
		try {
			enrollOrderBalanceVo.setOrderState(ReqConstants.ENROLL_ORDER_CHECKOUT_DONE); //查询已结款的记录
			enrollOrderList = enrollOrderManager.queryEnrollOrderBalanceList(enrollOrderBalanceVo);
			resp = new ResponseMessage().addResult("pageData", enrollOrderList).build();
		}
		catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public List<EnrollOrderVo> downLoadExl(EnrollOrderVo enrollOrderVo) {
		try {
			List<EnrollOrderVo> batch = enrollOrderManager.downLoadExl(enrollOrderVo);
			return batch;
		}
		catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
		}
		return null;
	}

	@Override
	public List<EnrollOrderBalanceVo> payDownLoadExl(EnrollOrderBalanceVo enrollOrderVo) {
		try {
			enrollOrderVo.setOrderState(ReqConstants.ENROLL_ORDER_CHECKOUT_DONE); //查询已结款的记录
			List<EnrollOrderBalanceVo> batch = enrollOrderManager.payDownLoadExl(enrollOrderVo);
			return batch;
		}
		catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
		}
		return null;
	}

}
