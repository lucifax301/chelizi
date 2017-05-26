package com.lili.finance.mapper.dao.common;

/**
 * 
 */

import java.util.List;

import com.lili.finance.model.common.UserMoney;
import com.lili.finance.vo.MoneFreeVo;
import com.lili.finance.vo.UserMoneyVo;

public interface UserMoneyDao {

	public void insertUserMoneyInfo(UserMoneyVo userMoneyVo) ;
	
	public void updateUserStatus(List<String> orderId) ;
	
	public void updateUserStatusFail(List<String> orderId) ;
	
	/**
	 * 余额明细	isBalance = 1
	 * @param userMoneyVo
	 * @return
	 */
	public List<UserMoneyVo> queryUserDetailList(UserMoneyVo  userMoneyVo);
	
	public List<UserMoneyVo> queryPayHistoryList(UserMoneyVo  userMoneyVo);

	public void insertUserMoneyList(List<UserMoney> userMoneyList);

	public Long queryTotalRecharge();

	public Long queryTotalBalancePaid();

	public Long queryTotalDeposit();

	public Long queryTotalBonus();

	public Long querySumMoneyByIsEarning(UserMoneyVo userMoneyVo);

	/**
	 * 驾校查询余额明细
	 * @param userMoneyVo
	 * @return
	 */
	public List<UserMoneyVo> queryTouchBalance(UserMoneyVo userMoneyVo);

	/**
	 * 费用明细	isEarning=0，变更金额取反
	 * @param userMoneyVo
	 * @return
	 */
	public List<UserMoneyVo> queryCostDetailList(UserMoneyVo userMoneyVo);

	public MoneFreeVo querySumMoney(UserMoneyVo userMoneyVo);
	
	public Integer queryIncome(UserMoneyVo userMoneyVo);
	public Integer queryExpenseFree(UserMoneyVo userMoneyVo);
	public Integer queryCourseFree(UserMoneyVo userMoneyVo);
	public Integer queryEnrollFree(UserMoneyVo userMoneyVo);
	public Integer queryComFree(UserMoneyVo userMoneyVo);
	public Integer queryRefundFree(UserMoneyVo userMoneyVo);
	public Integer queryFineFree(UserMoneyVo userMoneyVo);

	/**
	 * 获取费用账户--费用合计
	 * @param userMoneyVo
	 * @return
	 */
	public Long querySumMoneyFlowOut(UserMoneyVo userMoneyVo);

	/**
	 * 获取余额账户--收入合计
	 * @param userMoneyVo
	 * @return
	 */
	public Long querySumMoneyFlowIn(UserMoneyVo userMoneyVo);
}
