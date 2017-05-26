package com.lili.finance.quartz;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.constant.Constant;
import com.lili.cms.util.DateUtil;
import com.lili.finance.chinapay.model.bean.TransactionBean;
import com.lili.finance.chinapay.servlet.QueryReturnServlet;
import com.lili.finance.chinapay.servlet.QuerySendServlet;
import com.lili.finance.chinapay.util.PayCode;
import com.lili.finance.manager.common.IDepositManager;
import com.lili.finance.util.ChinaPayConstant;
import com.lili.finance.vo.DepositVo;


/**
 * 提现定时修改银联处理中状态任务
 * @author lzb
 *
 */
public class QueryChinaPayJob {

	 Logger logger = Logger.getLogger(QueryChinaPayJob.class);
	 
	@Autowired
	IDepositManager depositManager;
	
	public void  work() {
		logger.info("****************************Start Task For QueryChinaPayJob***************************");
		Map<String, Object> params = new HashMap<String, Object>();
		List<DepositVo> depositList = depositManager.queryHandleList(params );
		if(depositList.size() > 0){
			handleChinaPay(depositList);
		}
		logger.info("****************************End Task For QueryChinaPayJob***************************");
	}


	public void handleChinaPay(List<DepositVo> depositList) {
		TransactionBean transactionBean = new TransactionBean();
		TransactionBean transactionBeanSend = new TransactionBean();
		TransactionBean transactionBeanBack = new TransactionBean();
		TransactionBean resTransactionBean = new TransactionBean();
		QuerySendServlet querySendServlet = new QuerySendServlet();
		QueryReturnServlet queryReturnServlet = new QueryReturnServlet();
		DepositVo backVoDeposit = new DepositVo();
		List<DepositVo> updateDepositList = new ArrayList<DepositVo>();
		String merSeqId = null;
	
		for (DepositVo depositVo : depositList) {
			
			try {
				merSeqId = depositVo.getMerSeqId();
				logger.info("-----------------------------------------------merSeqId- " + merSeqId);
				if(depositVo.getMerDate() == null || "".equals(depositVo.getMerDate())){
					transactionBeanSend.setMerDate(DateUtil.getTodayDate());
				}
				else {
					transactionBeanSend.setMerDate(depositVo.getMerDate());
				}
				transactionBeanSend.setMerSeqId(merSeqId);
				transactionBeanSend.setVersion(ChinaPayConstant.VERSION);
				transactionBean = querySendServlet.getChkValue(transactionBeanSend);  //验证请求内容
				logger.info("签名内容:"+ transactionBean.getChkValue());
				logger.info("商户号:"+ transactionBean.getMerId());
				if(transactionBean.getChkValue() == null){
					logger.info("************************************ 商户号 is null, Base64 Fail!");
					continue;
				}
				
				transactionBeanBack.setMerId(transactionBean.getMerId());
				transactionBeanBack.setMerDate(transactionBean.getMerDate());
				transactionBeanBack.setMerSeqId(transactionBean.getMerSeqId());
				transactionBeanBack.setVersion(transactionBean.getVersion());
				transactionBeanBack.setChkValue(transactionBean.getChkValue());
				resTransactionBean  = queryReturnServlet.doPostTransaction(transactionBeanBack);
				logger.info("*************************** QueryReturnServlet Success! ");
				
				if (PayCode.STAT_CODE_SUCCESS.equals(resTransactionBean.getStat())) {//成功
					backVoDeposit.setCheckStatus(Constant.DEPOSIT_SUCCESS);
					backVoDeposit.setResponseCode(resTransactionBean.getResponseCode());
					backVoDeposit.setStat(resTransactionBean.getStat());
					backVoDeposit.setId(depositVo.getId());
				}
				else if (PayCode.STAT_CODE_BACK.equals(resTransactionBean.getStat())){//失败
					backVoDeposit.setCheckStatus(Constant.DEPOSIT_BANK_FAIL);
					backVoDeposit.setResponseCode(resTransactionBean.getResponseCode());
					backVoDeposit.setStat(resTransactionBean.getStat());
					backVoDeposit.setRemark(resTransactionBean.getData().substring(0, 3));
					backVoDeposit.setId(depositVo.getId());
				}
				else {
					backVoDeposit.setCheckStatus(Constant.DEPOSIT_HANDLE);
					backVoDeposit.setResponseCode(resTransactionBean.getResponseCode());
					backVoDeposit.setStat(resTransactionBean.getStat());
					backVoDeposit.setRemark(resTransactionBean.getData().substring(0, 3));
					backVoDeposit.setId(depositVo.getId());
				}
				updateDepositList.add(backVoDeposit);
			}
			catch (ServletException e) {
				logger.error("*************************** ServletException: " +  e.getMessage());
				e.printStackTrace();
			} 
			catch (IOException e) {
				logger.error("*************************** IOException: " +  e.getMessage());
				e.printStackTrace();
			} 
		}
		depositManager.sysHandleStatus(updateDepositList);
		logger.info("*************************** System HandleStatus Success! ");
		
	}
	
}
