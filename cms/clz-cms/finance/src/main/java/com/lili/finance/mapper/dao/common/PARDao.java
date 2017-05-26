/**
 * 
 */
package com.lili.finance.mapper.dao.common;

import java.util.List;

import com.lili.finance.model.PAR;
import com.lili.finance.model.PARBDTO;

public interface PARDao {
    
	public long insertSelective(PAR par);
    public List<PAR> findBatch(PARBDTO dto);
	public Long findYesterdayAccount();
	
}
