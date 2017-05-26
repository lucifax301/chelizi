package com.lili.finance.service;

import com.lili.cms.entity.ResponseMessage;
import com.lili.finance.vo.UserMoneyVo;

public interface ICmsLiliWalletService {
	
	public ResponseMessage fundCount(String month);

	public ResponseMessage balance();

	public ResponseMessage touchBalance(UserMoneyVo userMoneyVo);

	public ResponseMessage accountBalance(UserMoneyVo userMoneyVo);

	public ResponseMessage costDetail(UserMoneyVo userMoneyVo);
	
}
	
