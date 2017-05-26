package com.lili.finance.manager.common.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.finance.manager.common.IBankCardManager;
import com.lili.finance.mapper.dao.common.BankCardVerifyDao;
import com.lili.finance.vo.BankCardVerifyVo;

public class BankCardManagerImpl implements IBankCardManager {
	
	Logger logger = Logger.getLogger(BankCardManagerImpl.class);
	
	@Autowired
	private BankCardVerifyDao bankCardVerifyDao;

	@Override
	public PagedResult<BankCardVerifyVo> queryBatchBoundList(BankCardVerifyVo bankCardVo) {
		try {
			PageUtil.startPage(bankCardVo.getPageNo(), bankCardVo.getPageSize());
			return BeanUtil.toPagedResult(bankCardVerifyDao.queryBoundInfoList(bankCardVo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	public List<BankCardVerifyVo> queryBankBoundList(BankCardVerifyVo bankCardVo) {
		List<BankCardVerifyVo> bankVoList = null;
		try {
			bankVoList =bankCardVerifyDao.queryBankInfoList(bankCardVo);
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return bankVoList;
	}

	@Override
	public List<BankCardVerifyVo> downLoadBoundList(BankCardVerifyVo bankCardVo) {
		List<BankCardVerifyVo> bankVoList = null;
		try {
			bankVoList =bankCardVerifyDao.queryBoundInfoLimitList(bankCardVo);
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return bankVoList;
	}

	@Override
	public void batchUpdateBankCardList(List<BankCardVerifyVo> updateBankCardList) {
		try {
			bankCardVerifyDao.batchUpdateStatusList(updateBankCardList);
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
	}


	@Override
	public List<BankCardVerifyVo> queryBankInfoList(String id) {
		try {
			return bankCardVerifyDao.queryBankList(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
