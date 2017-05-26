package com.lili.finance.service;

import com.lili.cms.entity.PagedResult;
import com.lili.finance.vo.MoneFreeVo;
import com.lili.finance.vo.UserMoneyVo;

public interface ICmsUserMoneyService {

	public  PagedResult<UserMoneyVo>  queryUserDetailList(UserMoneyVo userMoneyVo, String channel);
	
	public  String  queryPayHistoryList(UserMoneyVo userMoneyVo);

	public void insert(UserMoneyVo userMoneyVo);

	public Long querySumMoneyByIsEarning(UserMoneyVo userMoneyVo, String channel);
	
	public abstract PagedResult<UserMoneyVo> querytouchBalance(UserMoneyVo userMoneyVo);

	public MoneFreeVo querySumMoney(UserMoneyVo userMoneyVo);
}
