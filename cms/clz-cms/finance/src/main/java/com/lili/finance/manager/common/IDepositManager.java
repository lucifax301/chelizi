package com.lili.finance.manager.common;

import java.util.List;
import java.util.Map;

import com.lili.cms.entity.PagedResult;
import com.lili.finance.vo.DepositVo;

/**
 * 报名订单
 * @author lzb
 *
 */
public interface IDepositManager {

	/**
	 * 分页
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public abstract PagedResult<DepositVo> queryBatchDepositList(DepositVo depositVo);
	
	/**
	 * 下载
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public abstract List<DepositVo> downLoadDepositList(DepositVo depositVo);
	
	/**
	 * 分页
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public abstract List<DepositVo> queryDepositInfo(Map<String, Object> params);
	
	/**
	 * 总金额
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public abstract Integer queryCountMoneyLimitList(DepositVo depositVo);
	
	/**
	 * 分页
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public abstract List<DepositVo> queryDepositInfo(List<Integer> ids);
	
	 /**
	  * 更新状态
	  * @param record
	 * @return 
	  * @return
	  */
   public abstract  void batchUpdateStatusList(List<DepositVo> depositUpdate);
   
   /**
    * 出纳转账
    * @param depositUpdate
    */
   public abstract  void tellerTransferUpdateStatus(List<DepositVo> depositUpdate);

	/**
	 * 单笔更新状态
	 * @param DepositVo
	 */
	public abstract  void updateStatus(DepositVo DepositVo);

	public abstract List<DepositVo> queryHandleList(Map<String, Object> params) ;

	public abstract void sysHandleStatus(List<DepositVo> updateDepositList);

}
