package com.lili.finance.manager.common.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.finance.manager.common.IDepositManager;
import com.lili.finance.mapper.dao.common.DepositDao;
import com.lili.finance.vo.DepositVo;


public class DepositManagerImpl implements IDepositManager {
	Logger logger = Logger.getLogger(DepositManagerImpl.class);
	
	@Autowired
	private DepositDao depositDao;

	@Override
	public PagedResult<DepositVo> queryBatchDepositList(DepositVo depositVo) {
		try {
			PageUtil.startPage(depositVo.getPageNo(), depositVo.getPageSize());
			return BeanUtil.toPagedResult(depositDao.queryBatchDepositList(depositVo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void batchUpdateStatusList(List<DepositVo> depositUpdate) {
		try {
			depositDao.updateStatusList(depositUpdate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<DepositVo> queryDepositInfo(Map<String, Object> params) {
		List<DepositVo>  depositVoList = null;
		 try {
			 depositVoList =  depositDao.queryDepositInfo(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return depositVoList;
	}

	@Override
	public List<DepositVo> queryDepositInfo(List<Integer> ids) {
		List<DepositVo>  depositVoList = null;
		 try {
			 depositVoList =  depositDao.queryDepositInfo(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return depositVoList;
	}

	@Override
	public List<DepositVo> downLoadDepositList(DepositVo depositVo) {
		List<DepositVo>  depositVoList = null;
		 try {
			 depositVoList =  depositDao.queryBatchDepositLimitList(depositVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return depositVoList;
	}

	

	@Override
	public void updateStatus(DepositVo depositVo) {
		try {
			depositDao.updateStatus(depositVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void tellerTransferUpdateStatus(List<DepositVo> depositUpdate) {
		try {
			depositDao.tellerTransferUpdateStatus(depositUpdate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<DepositVo> queryHandleList(Map<String, Object> params) {
		List<DepositVo>  depositVoList = null;
		 try {
			 depositVoList =  depositDao.queryHandleList(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return depositVoList;
	}

	@Override
	public void sysHandleStatus(List<DepositVo> updateDepositList) {
		try {
			depositDao.sysHandleStatus(updateDepositList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer queryCountMoneyLimitList(DepositVo depositVo) {
		Integer total = null;
		try {
			total =  depositDao.queryCountMoneyLimitList(depositVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

}
