package com.lili.order.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.constant.Constant;
import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.order.manager.ICancelReasonManager;
import com.lili.order.service.CancelReasonService;
import com.lili.order.service.ICmsCancelReasonService;
import com.lili.order.vo.CancelReason;
import com.lili.order.vo.CancelReasonVo;

public class CancelReasonServiceImpl implements ICmsCancelReasonService {
	Logger logger = Logger.getLogger(CancelReasonServiceImpl.class);
	
	@Autowired
	ICancelReasonManager cancelReasonManager;
	
	@Autowired
	CancelReasonService cancelReasonService;

	@Override
	public String query(CancelReason cancelReason) {
		String resp =null;
		PagedResult<CancelReason> batch = null;
		try {
			batch = cancelReasonManager.queryCancelReasonList(cancelReason);
			resp = new ResponseMessage().addResult("pageData", batch).build();
		} 
		catch (Exception e) {
			logger.error("************************************ error: " + e.getMessage());
		}
		return resp;
	}

	@Override
	public ResponseMessage isUseOrDel(String checker, String remark, String id, Integer isdel) {
		try {
			String[] idList  = id.split(",");
			if (isdel == 0) {
				List<CancelReason> courseList = cancelReasonManager.queryCancelList(id);
				for (int i =0; i< courseList.size(); i++) {
					if(courseList.get(i).getIsdel() == Constant.IS_USE){
						return new ResponseMessage(MessageCode.MSG_HANDE_CF);
					}
				}
			}
			else if (isdel ==1 ) {
				List<CancelReason> courseList = cancelReasonManager.queryCancelList(id);
				for (int i =0; i< courseList.size(); i++) {
					if(courseList.get(i).getIsdel() == Constant.IS_DEL){
						return new ResponseMessage(MessageCode.MSG_HANDE_CF);
					}
				}
			}
			else {
				return new ResponseMessage(MessageCode.MSG_REQUEST);
			}
			
			CancelReasonVo cancelReasonVo;
			for (int i =0; i< idList.length; i++) {
				cancelReasonVo = new CancelReasonVo();
				cancelReasonVo.setIsdel(isdel);
				cancelReasonService.updateByCrid(cancelReasonVo, Integer.valueOf(idList[i]));	//调APP接口批量更新
			}
		} 
		catch (NumberFormatException e) {
			logger.error("********************************* error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		} 
		catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage update(CancelReasonVo cancelReasonVo) {
		//调APP接口
		try {
			cancelReasonService.updateByCrid(cancelReasonVo, cancelReasonVo.getCrid());
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("********************************* error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage addCourse(CancelReasonVo cancelReasonVo) {
		//调APP接口
		try {
			cancelReasonService.addCancelReason(cancelReasonVo);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("********************************* error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

}
