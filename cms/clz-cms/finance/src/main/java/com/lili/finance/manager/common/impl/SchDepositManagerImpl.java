package com.lili.finance.manager.common.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.finance.manager.common.ISchDepositManager;
import com.lili.finance.mapper.dao.common.SchDepositDao;
import com.lili.finance.vo.SchDeposit;

public class SchDepositManagerImpl implements ISchDepositManager {
	Logger logger = Logger.getLogger(SchDepositManagerImpl.class);
	
	@Autowired
	private SchDepositDao schDepositDao;

	@Override
	public PagedResult<SchDeposit> querySchDepositList(SchDeposit depositVo) {
		try {
			PageUtil.startPage(depositVo.getPageNo(), depositVo.getPageSize());
			return BeanUtil.toPagedResult(schDepositDao.queryBatchDepositList(depositVo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void batchUpdateStatusList(List<SchDeposit> depositUpdate) {
		try {
			schDepositDao.updateStatusList(depositUpdate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void tellerTransferUpdateStatus(List<SchDeposit> depositUpdate) {
		try {
			schDepositDao.tellerTransferUpdateStatus(depositUpdate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateStatus(SchDeposit schDeposit) {
		try {
			schDepositDao.updateStatus(schDeposit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 定时任务-查询银行处理中的任务 
	 */
	@Override
	public List<SchDeposit> queryHandleList(Map<String, Object> params) {
		List<SchDeposit>  depositVoList = null;
		 try {
			 depositVoList =  schDepositDao.queryHandleList(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return depositVoList;
	}

	/**
	 * 定时任务-处理银行处理中
	 */
	@Override
	public void sysHandleStatus(List<SchDeposit> updateDepositList) {
		try {
			schDepositDao.sysHandleStatus(updateDepositList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertSchDeposit(SchDeposit schDeposit) {
		try {
			schDepositDao.insertSelective(schDeposit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SchDeposit querySchDeposit(SchDeposit schDeposit) {
		try {
			return schDepositDao.querySchDeposit(schDeposit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schDeposit;
	}

	@Override
	public int queryLeftDepositCount(SchDeposit depositVo) {
		try {
			return schDepositDao.queryLeftDepositCount(depositVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<SchDeposit> queryDownLoadExcel(SchDeposit schdeposit) {
		try {
			return schDepositDao.queryBatchDepositList(schdeposit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PagedResult<SchDeposit> queryBatchDepositList(SchDeposit schoolDepositVo) {
		try {
			PageUtil.startPage(schoolDepositVo.getPageNo(), schoolDepositVo.getPageSize());
			return BeanUtil.toPagedResult(schDepositDao.queryBatchDepositList(schoolDepositVo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SchDeposit> queryDepositInfo(List<Integer> ids) {
		List<SchDeposit>  depositVoList = null;
		 try {
			 depositVoList =  schDepositDao.queryDepositInfo(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return depositVoList;
	}

	@Override
	public List<SchDeposit> downLoadDepositList(SchDeposit schdeposit) {
		List<SchDeposit>  depositVoList = null;
		 try {
			 depositVoList =  schDepositDao.queryBatchDepositLimitList(schdeposit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return depositVoList;
	}

}
