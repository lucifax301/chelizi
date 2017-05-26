package com.lili.order.manager.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.order.manager.ICancelReasonManager;
import com.lili.order.mapper.dao.CancelReasonDao;
import com.lili.order.vo.CancelReason;

public class CancelReasonMangerImpl implements ICancelReasonManager {
	Logger logger = Logger.getLogger(CancelReasonMangerImpl.class);
	
	@Autowired
	CancelReasonDao cancelReasonDao;

	@Override
	public PagedResult<CancelReason> queryCancelReasonList(CancelReason cancelReason) {
		PageUtil.startPage(cancelReason.getPageNo(), cancelReason.getPageSize());
		return BeanUtil.toPagedResult(cancelReasonDao.queryCancelReasonList(cancelReason));
	}

	@Override
	public List<CancelReason> queryCancelList(String id) {
		List<CancelReason> cancelReasonList = null;
		try {
			cancelReasonList = cancelReasonDao.queryCancelList(id);
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		
		return cancelReasonList;
	}

}
