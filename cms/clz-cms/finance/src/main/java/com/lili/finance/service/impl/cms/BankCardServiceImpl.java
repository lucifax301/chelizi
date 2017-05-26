package com.lili.finance.service.impl.cms;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.constant.Constant;
import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.finance.manager.common.IBankCardManager;
import com.lili.finance.service.ICmsBankCardVerifyService;
import com.lili.finance.vo.BankCardVerifyVo;

public class BankCardServiceImpl implements ICmsBankCardVerifyService {
	Logger logger = Logger.getLogger(BankCardServiceImpl.class);
	
	@Autowired
	IBankCardManager bankCardManager;

	/**
	 * 查询
	 */
	@Override
	public String query(BankCardVerifyVo bankCardVo) {
		String resp =null;
		PagedResult<BankCardVerifyVo> batch = null;
		try {
			logger.info("************************* bankCardVo " + bankCardVo.getStatus());
			batch = bankCardManager.queryBatchBoundList(bankCardVo);
			resp = new ResponseMessage().addResult("pageData", batch).build();
		} 
		catch (Exception e) {
			logger.error("************************************ error: " + e.getMessage());
		}
		return resp;
	}

	/**
	 * 有效
	 */
	@Override
	public ResponseMessage pass(String checker, String remark, String id) {
		try {
			String[] idList  = id.split(",");
			List<BankCardVerifyVo> bankList = bankCardManager.queryBankInfoList(id);
			for (int i =0; i< bankList.size(); i++) {
				if(bankList.get(i).getStatus() == Constant.BANK_YX){
					return new ResponseMessage(MessageCode.MSG_HANDE_CF);
				}
			}
			
			List<BankCardVerifyVo> updateBankCardList = new ArrayList<BankCardVerifyVo>();
			BankCardVerifyVo bankCardVerify;
			Integer idI;
			for (int i =0; i< idList.length; i++) {
				idI = Integer.parseInt(idList[i]);
				bankCardVerify = new BankCardVerifyVo();
				bankCardVerify.setChecker(checker);
				bankCardVerify.setStatus(Constant.BOUND_BANK_CARD_SUCCESS);
				bankCardVerify.setRemark(remark);
				bankCardVerify.setId(idI);
			    updateBankCardList.add(bankCardVerify);
			}
			bankCardManager.batchUpdateBankCardList(updateBankCardList);
			logger.info("-------------------------------- BoundBankCardController Pass Update Success!");
		} 
		catch (NumberFormatException e) {
			logger.error("********************************* error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		} 
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	/**
	 * 无效
	 */
	@Override
	public ResponseMessage reject(String checker, String remark, String id) {
		try {
			String[] idList  = id.split(",");
			List<BankCardVerifyVo> bankList = bankCardManager.queryBankInfoList(id);
			for (int i =0; i< bankList.size(); i++) {
				if(bankList.get(i).getStatus() == Constant.BANK_WX){
					return new ResponseMessage(MessageCode.MSG_HANDE_CF);
				}
			}
			List<BankCardVerifyVo> updateBankCardList = new ArrayList<BankCardVerifyVo>();
			BankCardVerifyVo bankCardVerify;
			Integer idI;
			for (int i =0; i< idList.length; i++) {
				idI = Integer.parseInt(idList[i]);
				bankCardVerify = new BankCardVerifyVo();
				bankCardVerify.setChecker(checker);
				bankCardVerify.setStatus(Constant.BOUND_BANK_CARD_FAIL);
				bankCardVerify.setRemark(remark);
				bankCardVerify.setId(idI);
			    updateBankCardList.add(bankCardVerify);
			}
			bankCardManager.batchUpdateBankCardList(updateBankCardList);
			logger.info("-------------------------------- BoundBankCardController Reject Update Success!");
		}
		catch (NumberFormatException e) {
			logger.error("********************************* error："+e.getMessage());
			//操作失败暂不记录日志
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public List<BankCardVerifyVo> downLoad(BankCardVerifyVo bankCardVo) {
			return bankCardManager.downLoadBoundList(bankCardVo);
	}

	/**
	 * 下载EXCEL
	 * @param request
	 * @return
	 */
	@Override
	public List<BankCardVerifyVo> downLoadExcel(BankCardVerifyVo bankCardVo) {
		try {
			List<BankCardVerifyVo> batch = bankCardManager.downLoadBoundList(bankCardVo);
			return batch;
		}
		catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
		}
		return null;
	}

	@Override
	public List<BankCardVerifyVo> queryBankBoundList(BankCardVerifyVo bankCardVo) {
		return bankCardManager.queryBankBoundList(bankCardVo);
	}

}
