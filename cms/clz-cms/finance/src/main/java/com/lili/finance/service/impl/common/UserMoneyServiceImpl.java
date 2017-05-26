package com.lili.finance.service.impl.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.finance.manager.common.IUserMoneyManager;
import com.lili.finance.service.ICmsUserMoneyService;
import com.lili.finance.vo.MoneFreeVo;
import com.lili.finance.vo.UserMoneyVo;

public class UserMoneyServiceImpl implements ICmsUserMoneyService {
	Logger logger = Logger.getLogger(UserMoneyServiceImpl.class);
	
	@Autowired
	IUserMoneyManager userMoneyManager;

	@Override
	public PagedResult<UserMoneyVo> queryUserDetailList(UserMoneyVo userMoneyVo, String channel) {
		if("1".equals(channel)){ //费用明细
			return userMoneyManager.queryCostDetailList(userMoneyVo);
		}
		else if ("2".equals(channel)){//余额明细
			return userMoneyManager.queryUserDetailList(userMoneyVo);
		}
		return null;
		
	}

	@Override
	public void insert(UserMoneyVo userMoneyVo) {
		userMoneyManager.insertUserMoneyInfo(userMoneyVo);
	}

	@Override
	public String queryPayHistoryList(UserMoneyVo userMoneyVo) {
		String resp = null;
		try {
			PagedResult<UserMoneyVo> batchDeposit = userMoneyManager.queryPayHistoryList(userMoneyVo);
			resp = new ResponseMessage().addResult("pageData", batchDeposit).build();
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public Long querySumMoneyByIsEarning(UserMoneyVo userMoneyVo, String channel) {
		Long sumMon = null;
		try {
			if("1".equals(channel)){ //费用
				sumMon  = userMoneyManager.querySumMoneyFlowOut(userMoneyVo);
			}
			else if ("2".equals(channel)){//收入
				sumMon  = userMoneyManager.querySumMoneyFlowIn(userMoneyVo);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return sumMon;
	}

	@Override
	public PagedResult<UserMoneyVo> querytouchBalance(UserMoneyVo userMoneyVo) {
		return userMoneyManager.querytouchBalance(userMoneyVo);
	}

	@Override
	public MoneFreeVo querySumMoney(UserMoneyVo userMoneyVo) {
		MoneFreeVo moneFreeVo = null;
		try {
			moneFreeVo  = userMoneyManager.querySumMoney(userMoneyVo);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return moneFreeVo;
	}

}
