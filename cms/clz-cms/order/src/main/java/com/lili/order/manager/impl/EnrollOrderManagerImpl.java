package com.lili.order.manager.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.order.manager.IEnrollOrderManager;
import com.lili.order.mapper.dao.EnrollOrderDao;
import com.lili.order.vo.EnrollOrderBalanceVo;
import com.lili.order.vo.EnrollOrderVo;

public class EnrollOrderManagerImpl implements IEnrollOrderManager {
	Logger logger = Logger.getLogger(EnrollOrderManagerImpl.class);
	
	@Autowired
	EnrollOrderDao enrollOrderDao;
	
	@Override
	public PagedResult<EnrollOrderVo> queryEnrollOrderList(EnrollOrderVo enrollOrderVo) {
		if(enrollOrderVo.getOrderby()==null){
			enrollOrderVo.setOrderby("mtime desc");
		}else{
			if(enrollOrderVo.getSort()==null) enrollOrderVo.setSort("desc");
			enrollOrderVo.setOrderby(enrollOrderVo.getOrderby()+" "+enrollOrderVo.getSort());
		}
		//PageUtil.startPage(enrollOrderVo.getPageNo(), enrollOrderVo.getPageSize());
		PageUtil.startPage(enrollOrderVo.getPageNo(), enrollOrderVo.getPageSize(),enrollOrderVo.getOrderby());
		return BeanUtil.toPagedResult(enrollOrderDao.queryEnrollOrderList(enrollOrderVo));
	}

	@Override
	public EnrollOrderVo queryDetailInfo(String orderId) {
		EnrollOrderVo enrollVo = null;
		try {
			enrollVo = enrollOrderDao.queryEnrollOrderInfo(orderId);
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		
		return enrollVo;
	}

	@Override
	public void updateEnrollOrder(String orderId) {
		EnrollOrderVo  enrollOrder = enrollOrderDao.queryEnrollOrderInfo(orderId);
		if (enrollOrder != null) {
			enrollOrderDao.updateByOrderId(enrollOrder);
		}
	}

	@Override
	public EnrollOrderVo queryEnrollOrderByStudentId(EnrollOrderVo enrollOrderVo) {
		EnrollOrderVo enrollOrderCity = null;
		try {
			enrollOrderCity = enrollOrderDao.queryEnrollOrderByStudentId(enrollOrderVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enrollOrderCity;
	}

	@Override
	public PagedResult<EnrollOrderBalanceVo> queryEnrollOrderBalanceList(EnrollOrderBalanceVo enrollOrderBalanceVo) {
		PageUtil.startPage(enrollOrderBalanceVo.getPageNo(), enrollOrderBalanceVo.getPageSize());
		return BeanUtil.toPagedResult(enrollOrderDao.queryEnrollOrderBalanceList(enrollOrderBalanceVo));
	}

	@Override
	public List<EnrollOrderVo> downLoadExl(EnrollOrderVo enrollOrderVo) {
		List<EnrollOrderVo> enrollList = null;
		try {
			enrollList = enrollOrderDao.queryEnrollOrderList(enrollOrderVo);
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return enrollList;
	}

	@Override
	public List<EnrollOrderBalanceVo> payDownLoadExl(EnrollOrderBalanceVo enrollOrderVo) {
		List<EnrollOrderBalanceVo> enrollList = null;
		try {
			enrollList = enrollOrderDao.queryEnrollOrderBalanceList(enrollOrderVo);
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return enrollList;
	}

}
