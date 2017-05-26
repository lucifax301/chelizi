package com.lili.finance.mapper.dao.common;

/**
 * 
 */

import java.util.List;

import com.lili.finance.vo.BankCardVerifyVo;



public interface BankCardVerifyDao {
    
	
	/**
	 * 获取银行卡绑定列表
	 */
	public List<BankCardVerifyVo> queryBoundInfoList(BankCardVerifyVo bankCardVerify);
	
	 /**
	  * 批量更新银行卡绑定内容
	  * @param record
	  * @return
	  */
    public void  batchUpdateStatusList(List<BankCardVerifyVo> updateBankCardList);
    
	/**
	 * 下载限制2W
	 */
	public List<BankCardVerifyVo> queryBoundInfoLimitList(BankCardVerifyVo bankCardVerify);
	
	/**
	 * 获取银行卡绑定列表
	 */
	public List<BankCardVerifyVo> queryBankInfoList(BankCardVerifyVo bankCardVerify);

	public List<BankCardVerifyVo> queryBankList(String id);
    
}
