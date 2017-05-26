package com.lili.finance.service;

import java.util.List;

import com.lili.cms.entity.ResponseMessage;
import com.lili.finance.vo.DepositVo;

public interface ICmsDepositService {

	public String query(DepositVo depositVo);

	public ResponseMessage tellerTransfer(String transfer, String id);

	public ResponseMessage pass(String checker, String checkRemark, String id) ;

	public ResponseMessage reject(String checker, String checkRemark, String id, String handlerType);

	public ResponseMessage financePass(String checker, String checkRemark, String id);

	public Integer queryTotalMoney(DepositVo depositVo);

	public List<DepositVo> queryDownDepositList(DepositVo depositVo);

}
