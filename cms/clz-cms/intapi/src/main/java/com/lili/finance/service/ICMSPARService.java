package com.lili.finance.service;

import com.lili.cms.entity.ResponseMessage;
import com.lili.finance.model.PAR;
import com.lili.finance.model.PARBDTO;

public interface ICMSPARService {

	public ResponseMessage findBatch(PARBDTO dto);

	public long addOne();
	
}
