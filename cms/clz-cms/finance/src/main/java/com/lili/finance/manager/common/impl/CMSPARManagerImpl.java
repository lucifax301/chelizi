package com.lili.finance.manager.common.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.finance.manager.common.ICMSPARManager;
import com.lili.finance.mapper.dao.common.PARDao;
import com.lili.finance.model.PAR;
import com.lili.finance.model.PARBDTO;

public class CMSPARManagerImpl implements ICMSPARManager{

	@Autowired
	PARDao parDao;
	
	@Override
	public PagedResult<PAR> findBatch(PARBDTO dto) {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult((parDao.findBatch(dto)));
	}

	@Override
	public long addOne(PAR par) {
		return parDao.insertSelective(par);
	}

	@Override
	public Long findYesterdayAccount() {
		return parDao.findYesterdayAccount();
	}

}
