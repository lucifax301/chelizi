/**
 * 
 */
package com.lili.finance.mapper.dao.cms;

import java.util.List;
import java.util.Map;

import com.lili.finance.vo.DepositVo;

public interface IDepositDao {
    
	/**
	 * 获取提现列表
	 */
	public List<DepositVo> queryBatchDepositList(Map<String, Object> params);
	
	/**
	 * 下载限制2W条
	 */
	public List<DepositVo> queryBatchDepositLimitList(Map<String, Object> params);
	
	/**
	 * 下载限制2W条
	 */
	public Integer queryCountMoneyLimitList(Map<String, Object> params);
	
	/**
	 * 获取提现信息
	 */
	public List<DepositVo> queryDepositInfo(Map<String, Object> params);
	
	public List<DepositVo> queryHandleList(Map<String, Object> params);
	
	/**
	 * 获取提现信息
	 */
	public List<DepositVo> queryDepositInfo(List<Integer> ids);
	
	 /**
	  * 更新状态
	  * @param record
	  * @return
	  */
	public void updateStatusList(List<DepositVo> depositUpdate);
	
	/**
	 * 出纳转账
	 * @param record
	 * @return
	 */
	public void tellerTransferUpdateStatus(List<DepositVo> depositUpdate);
	
	public void sysHandleStatus(List<DepositVo> depositUpdate);
	
	/**
	 * 单笔更新
	 * @param deposit
	 */
	public void updateByPrimaryKeySelective(DepositVo deposit);
	
	public void updateStatus(DepositVo deposit);
	
	public Long getYesterdayDeposit();
    

    
}
