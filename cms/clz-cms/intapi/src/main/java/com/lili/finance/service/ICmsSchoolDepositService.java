package com.lili.finance.service;

import java.util.List;

import com.lili.cms.entity.ResponseMessage;
import com.lili.finance.vo.SchDeposit;

public interface ICmsSchoolDepositService {

	public ResponseMessage tellerTransfer(String transfer, String id);

	public ResponseMessage pass(String checker, String checkRemark, String id) ;

	public ResponseMessage reject(String checker, String checkRemark, String id, String handlerType);

	public ResponseMessage financePass(String checker, String checkRemark, String id);

	//public Integer queryTotalMoney(SchDeposit schDeposit);

	public SchDeposit query(SchDeposit schDeposit);
	
	public String queryDepositList(SchDeposit schDeposit);

	public ResponseMessage insertDeposit(SchDeposit schDeposit);

	public int queryLeftDepositCount(SchDeposit schDeposit );

	public List<SchDeposit> downLoadExcel(SchDeposit schdeposit);
	
	public List<SchDeposit> queryDownDepositList(SchDeposit schdeposit);

	public String queryList(SchDeposit schoolDepositVo);
}
