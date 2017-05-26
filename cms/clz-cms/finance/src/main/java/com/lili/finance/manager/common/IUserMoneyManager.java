package com.lili.finance.manager.common;

import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.finance.vo.MoneFreeVo;
import com.lili.finance.vo.UserMoneyVo;


public interface IUserMoneyManager{


	public abstract void insertUserMoneyInfo(UserMoneyVo  userMoneyVo);
	
	public abstract void updateUserStatus(List<String> orderId);
	
	public abstract void updateUserStatusFail(List<String> orderId);
	
	public abstract PagedResult<UserMoneyVo> queryUserDetailList(UserMoneyVo  userMoneyVo);
	
	public abstract PagedResult<UserMoneyVo> queryPayHistoryList(UserMoneyVo  userMoneyVo);

	public Long queryTotalRecharge();

	public Long queryTotalBalancePaid();

	public Long queryTotalDeposit();

	public Long queryTotalBonus();

	public abstract Long querySumMoneyByIsEarning(UserMoneyVo userMoneyVo);

	public abstract PagedResult<UserMoneyVo> querytouchBalance(UserMoneyVo userMoneyVo);

	public abstract PagedResult<UserMoneyVo> queryCostDetailList(UserMoneyVo userMoneyVo);

	public abstract MoneFreeVo querySumMoney(UserMoneyVo userMoneyVo);

	/**
	 * 获取费用账户--费用合计
	 * @param userMoneyVo
	 * @return
	 */
	public abstract Long querySumMoneyFlowOut(UserMoneyVo userMoneyVo);

	/**
	 * 获取余额账户--收入合计
	 * @param userMoneyVo
	 * @return
	 */
	public abstract Long querySumMoneyFlowIn(UserMoneyVo userMoneyVo);
	
}
