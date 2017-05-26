/**
 * 
 */
package com.lili.pay.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.pay.mapper.dao.BankcardVerifyDtoMapper;

public class BankManagerImpl implements BankManager {
	 Logger logger = LoggerFactory.getLogger(BankManagerImpl.class);
	 
    @Autowired
    private BankcardVerifyDtoMapper bankcardVerifyDtoMapper;
    
    
    /**
     * 查询用户绑定了多少张银行卡
     * @param userId
     * @param userType
     * @return
     */
	@Override
    public int queryBankSize(Long userId, Integer userType)
    {
        int bankSize = 0;
		try {
			logger.info("**************************** Query Bank Size Start ! userId: " + userId + ",userType: "+ userType);
			bankSize = bankcardVerifyDtoMapper.queryBankSize(userId, userType);
			logger.info("**************************** Query Bank Size End! bankSize:" + bankSize);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("******************************* Error Message: " + e.getMessage());
		}
        return bankSize;
    }
    
}
