package com.lili.finance.manager.common.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.finance.manager.common.IUserMoneyManager;
import com.lili.finance.mapper.dao.common.UserMoneyDao;
import com.lili.finance.vo.MoneFreeVo;
import com.lili.finance.vo.UserMoneyVo;

public class UserMoneyManagerImpl implements IUserMoneyManager {
	Logger logger = Logger.getLogger(UserMoneyManagerImpl.class);
	
	@Autowired
	UserMoneyDao userMoneyDao;

	@Override
	public void insertUserMoneyInfo(UserMoneyVo  userMoneyVo) {
		try {
			userMoneyDao.insertUserMoneyInfo(userMoneyVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateUserStatus(List<String> orderId) {
		try {
			userMoneyDao.updateUserStatus(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateUserStatusFail(List<String> orderId) {
		try {
			userMoneyDao.updateUserStatusFail(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public PagedResult<UserMoneyVo> queryUserDetailList(UserMoneyVo  userMoneyVo) {
		try {
			PageUtil.startPage(userMoneyVo.getPageNo(), userMoneyVo.getPageSize());
			return BeanUtil.toPagedResult(userMoneyDao.queryUserDetailList(userMoneyVo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long queryTotalRecharge() {
		try {
			return userMoneyDao.queryTotalRecharge();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long queryTotalBalancePaid() {
		try {
			return userMoneyDao.queryTotalBalancePaid();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long queryTotalDeposit() {
		try {
			return userMoneyDao.queryTotalDeposit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long queryTotalBonus() {
		try {
			return userMoneyDao.queryTotalBonus();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PagedResult<UserMoneyVo> queryPayHistoryList(UserMoneyVo userMoneyVo) {
		try {
			PageUtil.startPage(userMoneyVo.getPageNo(), userMoneyVo.getPageSize());
			return BeanUtil.toPagedResult(userMoneyDao.queryPayHistoryList(userMoneyVo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long querySumMoneyByIsEarning(UserMoneyVo userMoneyVo) {
		try {
			return userMoneyDao.querySumMoneyByIsEarning(userMoneyVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PagedResult<UserMoneyVo> querytouchBalance(UserMoneyVo userMoneyVo) {
		try {
			PageUtil.startPage(userMoneyVo.getPageNo(), userMoneyVo.getPageSize());
			return BeanUtil.toPagedResult(userMoneyDao.queryTouchBalance(userMoneyVo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PagedResult<UserMoneyVo> queryCostDetailList(UserMoneyVo userMoneyVo) {
		try {
			PageUtil.startPage(userMoneyVo.getPageNo(), userMoneyVo.getPageSize());
			return BeanUtil.toPagedResult(userMoneyDao.queryCostDetailList(userMoneyVo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public MoneFreeVo querySumMoney(UserMoneyVo userMoneyVo) {
		MoneFreeVo moneFreeVo = new MoneFreeVo();
		try {
			moneFreeVo.setIncome(userMoneyDao.queryIncome(userMoneyVo));
			moneFreeVo.setExpenseFree(userMoneyDao.queryExpenseFree(userMoneyVo));
			moneFreeVo.setCourseFree(userMoneyDao.queryCourseFree(userMoneyVo));
			moneFreeVo.setEnrollFree(userMoneyDao.queryEnrollFree(userMoneyVo));
			moneFreeVo.setComFree(userMoneyDao.queryComFree(userMoneyVo));
			moneFreeVo.setRefundFree(userMoneyDao.queryRefundFree(userMoneyVo));
			moneFreeVo.setFineFree(userMoneyDao.queryFineFree(userMoneyVo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return moneFreeVo;
	}

	@Override
	public Long querySumMoneyFlowOut(UserMoneyVo userMoneyVo) {
		try {
			return userMoneyDao.querySumMoneyFlowOut(userMoneyVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long querySumMoneyFlowIn(UserMoneyVo userMoneyVo) {
		try {
			return userMoneyDao.querySumMoneyFlowIn(userMoneyVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

}
