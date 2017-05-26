package com.lili.finance.manager.common;

import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.finance.vo.BankCardVerifyVo;

public interface IBankCardManager {
	/**
	 * 分页
	 * 
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public abstract PagedResult<BankCardVerifyVo> queryBatchBoundList(BankCardVerifyVo bankCardVo);

	/**
	 * download
	 * 
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public abstract List<BankCardVerifyVo> downLoadBoundList(BankCardVerifyVo bankCardVo);

	/**
	 * 批量更新银行卡绑定内容
	 * 
	 * @param record
	 * @return
	 * @return
	 */
	public abstract void batchUpdateBankCardList(List<BankCardVerifyVo> updateBankCardList);

	public abstract List<BankCardVerifyVo> queryBankBoundList(BankCardVerifyVo bankCardVo);

	public abstract List<BankCardVerifyVo> queryBankInfoList(String id);
}
