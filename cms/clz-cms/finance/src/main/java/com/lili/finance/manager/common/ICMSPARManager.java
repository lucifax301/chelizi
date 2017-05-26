package com.lili.finance.manager.common;

import com.lili.cms.entity.PagedResult;
import com.lili.finance.model.PAR;
import com.lili.finance.model.PARBDTO;

public interface ICMSPARManager {

	public PagedResult<PAR> findBatch(PARBDTO dto);

	public long addOne(PAR par);

	public Long findYesterdayAccount();
	
}
