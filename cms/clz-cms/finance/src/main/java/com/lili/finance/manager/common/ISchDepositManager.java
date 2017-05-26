package com.lili.finance.manager.common;

import java.util.List;
import java.util.Map;

import com.lili.cms.entity.PagedResult;
import com.lili.finance.vo.SchDeposit;

/**
 * 驾校提现
 * 
 * @author lzb
 *
 */
public interface ISchDepositManager {

	/**
	 * 驾校提现查询分页
	 * 
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public abstract PagedResult<SchDeposit> querySchDepositList(SchDeposit depositVo);

	/**
	 * 更新状态
	 * 
	 * @param record
	 * @return
	 * @return
	 */
	public abstract void batchUpdateStatusList(List<SchDeposit> depositUpdate);

	/**
	 * 出纳转账
	 * 
	 * @param depositUpdate
	 */
	public abstract void tellerTransferUpdateStatus(List<SchDeposit> depositUpdate);

	/**
	 * 单笔更新状态
	 * 
	 * @param SchDeposit
	 */
	public abstract void updateStatus(SchDeposit schDeposit);

	public abstract List<SchDeposit> queryHandleList(Map<String, Object> params);

	public abstract void sysHandleStatus(List<SchDeposit> updateDepositList);

	/**
	 * 申请提现
	 * @param schDeposit
	 */
	public abstract void insertSchDeposit(SchDeposit schDeposit);

	/**
	 * 查询单笔提现
	 * @param schDeposit
	 * @return 
	 */
	public abstract SchDeposit querySchDeposit(SchDeposit schDeposit);

	public abstract int queryLeftDepositCount(SchDeposit schDeposit);

	public abstract List<SchDeposit> queryDownLoadExcel(SchDeposit schdeposit);

	/**
	 * 提现审核分页
	 * 
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public abstract PagedResult<SchDeposit> queryBatchDepositList(SchDeposit schoolDepositVo);

	public abstract List<SchDeposit> queryDepositInfo(List<Integer> ids);

	public abstract List<SchDeposit> downLoadDepositList(SchDeposit schdeposit);

}
