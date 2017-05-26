package com.lili.finance.service.impl.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lili.cms.constant.Constant;
import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.DateUtil;
import com.lili.cms.util.StringUtil;
import com.lili.finance.chinapay.model.bean.TransactionBean;
import com.lili.finance.chinapay.servlet.ResponseServlet;
import com.lili.finance.chinapay.servlet.TransactionServlet;
import com.lili.finance.chinapay.util.PayCode;
import com.lili.finance.manager.common.ISchDepositManager;
import com.lili.finance.manager.common.IUserMoneyManager;
import com.lili.finance.service.ICmsSchoolDepositService;
import com.lili.finance.util.ChinaPayConstant;
import com.lili.finance.vo.SchDeposit;
import com.lili.finance.vo.UserMoneyVo;
import com.lili.school.model.SchAccount;
import com.lili.school.service.CMSSchoolService;

public class SchDepositServiceImpl implements ICmsSchoolDepositService {
	Logger logger = Logger.getLogger(SchDepositServiceImpl.class);
	
	@Autowired
	ISchDepositManager schDsepositManager;
	
	@Autowired
	IUserMoneyManager userMoneyManager;
	
	@Autowired
	CMSSchoolService schoolService;
	
	
	//******************************************************
	/**
	 * 以下为车厘子端功能
	 */
	//******************************************************
	
	/**
	 * 查询驾校提现数据
	 */
	@Override
	public String queryList(SchDeposit schoolDepositVo) {
		String resp = null;
		try {
			PagedResult<SchDeposit> batchDeposit = schDsepositManager.queryBatchDepositList(schoolDepositVo);
			resp = new ResponseMessage().addResult("pageData", batchDeposit).build();
		} 
		catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		
		return resp;
	}

	  /**
     * 出纳转账
     * @param request
     * @param response
     * @return
     */
	@Transactional
	@Override
	public ResponseMessage tellerTransfer(String transfer, String id) {
		int errorId = 0;
		SchDeposit backDeposit = new SchDeposit();
		String merSeqId = null;
		try {
			String[] idList  = id.split(",");
			List<Integer> ids=new ArrayList<Integer>();
			for (String a : idList) {
				ids.add(Integer.parseInt(a));
			}
			logger.info("---------------------------------------DepositController  Pass Ids: " + ids);
			List<SchDeposit> depositList = schDsepositManager.queryDepositInfo(ids); //批量查询
			for(int i = 0; i < depositList.size(); i++){
				if(!Constant.FINANCE_PASS.equals(depositList.get(i).getCheckStatus())){
					return new ResponseMessage(MessageCode.MSG_DEPOSIT_STATUS_ERROR);
				}
			}
			
			List<SchDeposit> updateDepositList = new ArrayList<SchDeposit>();
			TransactionServlet transactionServlet = new TransactionServlet();
			ResponseServlet responseServlet = new ResponseServlet();
			TransactionBean transBean = null;
			TransactionBean resTransBean = null;
			TransactionBean transactionBean = null;
			TransactionBean resTransactionBean = null;
			String money = null;
			for (SchDeposit deposit : depositList) {
				errorId = deposit.getId();
				money = StringUtil.getTransAmt(deposit.getMoney());
				
			   //单笔发起银联付款
				merSeqId = StringUtil.getChinaPayWaterId();
				//交易验证
				transBean = new TransactionBean();
				transBean.setMerDate(DateUtil.getTodayDate());
				transBean.setMerSeqId(merSeqId);
				transBean.setCardNo(deposit.getBankCard());
				transBean.setUserName(deposit.getCardName());
				transBean.setOpenBank(deposit.getBankName());
				transBean.setProv(ChinaPayConstant.PROV);
				transBean.setCity(ChinaPayConstant.CITY);
				transBean.setTransAmt(money);
				transBean.setPurpose(ChinaPayConstant.PURPOSE);
				transBean.setSubBank("");
				transBean.setFlag(ChinaPayConstant.TREM_TYPE_PRIVATE);
				transBean.setVersion(ChinaPayConstant.VERSION);
				transBean.setTermType("");
				transactionBean = transactionServlet.getChkValue(transBean);  //验证请求内容
				
				logger.info("签名内容:"+ transactionBean.getChkValue());
				logger.info("商户号:"+ transactionBean.getMerId());
				if(transactionBean.getChkValue() == null){
					return new ResponseMessage(MessageCode.MSG_FAIL);
				}
				
				//提交付款
				resTransBean = new TransactionBean();
				resTransBean.setMerDate(DateUtil.getTodayDate());
				resTransBean.setMerSeqId(merSeqId);
				resTransBean.setCardNo(deposit.getBankCard());
				resTransBean.setUserName(deposit.getCardName());
				resTransBean.setOpenBank(deposit.getBankName());
				resTransBean.setProv(ChinaPayConstant.PROV);
				resTransBean.setCity(ChinaPayConstant.CITY);
				resTransBean.setTransAmt(money);
				resTransBean.setPurpose(ChinaPayConstant.PURPOSE);
				resTransBean.setSubBank("");
				resTransBean.setFlag(ChinaPayConstant.TREM_TYPE_PRIVATE);
				resTransBean.setVersion(ChinaPayConstant.VERSION);
				resTransBean.setTermType("");
				resTransBean.setChkValue(transactionBean.getChkValue());
				resTransBean.setMerId(transactionBean.getMerId());
				resTransactionBean = responseServlet.doPostTransaction(resTransBean);//提交付款
				
				backDeposit = new SchDeposit();
				if(PayCode.STAT_CODE_SUCCESS.equals(resTransactionBean.getStat())) {//成功
					backDeposit.setCheckStatus(Constant.DEPOSIT_SUCCESS);
					backDeposit.setProv(ChinaPayConstant.PROV);
					backDeposit.setCity(ChinaPayConstant.CITY);
					backDeposit.setResponseCode(resTransactionBean.getResponseCode());
					backDeposit.setMerDate(resTransactionBean.getMerDate());
					backDeposit.setMerSeqId(resTransactionBean.getMerSeqId());
					backDeposit.setStat(resTransactionBean.getStat());
					backDeposit.setFeeAmt(-100);
					backDeposit.setTransfer(transfer);
					backDeposit.setId(deposit.getId());
				    
				}
				else if (PayCode.STAT_CODE_BACK.equals(resTransactionBean.getStat())){//失败
					backDeposit.setCheckStatus(Constant.DEPOSIT_BANK_FAIL);
					backDeposit.setProv(ChinaPayConstant.PROV);
					backDeposit.setCity(ChinaPayConstant.CITY);
					backDeposit.setResponseCode(resTransactionBean.getResponseCode());
					backDeposit.setMerDate(resTransactionBean.getMerDate());
					backDeposit.setMerSeqId(resTransactionBean.getMerSeqId());
					backDeposit.setStat(resTransactionBean.getStat());
					backDeposit.setTransfer(transfer);
					backDeposit.setFeeAmt(0);
					backDeposit.setId(deposit.getId());
				}
				else {
					backDeposit.setCheckStatus(Constant.DEPOSIT_HANDLE);
					backDeposit.setProv(ChinaPayConstant.PROV);
					backDeposit.setCity(ChinaPayConstant.CITY);
					backDeposit.setResponseCode(resTransactionBean.getResponseCode());
					backDeposit.setMerDate(DateUtil.getTodayDate());
					backDeposit.setMerSeqId(merSeqId);
					backDeposit.setStat(resTransactionBean.getStat());
					backDeposit.setTransfer(transfer);
					backDeposit.setFeeAmt(-100);
					backDeposit.setId(deposit.getId());
				}
				updateDepositList.add(backDeposit);
			}
			schDsepositManager.tellerTransferUpdateStatus(updateDepositList);  //批量更新状态
			logger.info("---------------------------------------DepositController  Update  Status To Success Finash!");
			
			return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
		} 
		catch (Exception e) {
			if ( errorId != 0){
				backDeposit.setCheckStatus(Constant.DEPOSIT_HANDLE);
				backDeposit.setId(errorId);
				backDeposit.setTransfer(transfer);
				backDeposit.setFeeAmt(-100);
				backDeposit.setMerDate(DateUtil.getTodayDate());
				backDeposit.setMerSeqId(merSeqId);
				schDsepositManager.updateStatus(backDeposit);;  //更新状态异常
			}
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
	}

	/**
	 * 提现成功
	 */
	@Override
	public ResponseMessage pass(String checker, String checkRemark, String id) {
		try {
			String[] idList  = id.split(",");
			List<Integer> ids=new ArrayList<Integer>();
			for (String a : idList) {
				ids.add(Integer.parseInt(a));
			}
			logger.info("---------------------------------------DepositController  Pass Ids: " + ids);
			List<SchDeposit> depositList = schDsepositManager.queryDepositInfo(ids); //批量查询
			for(int i = 0; i < depositList.size(); i++){
				if (!Constant.FINANCE_PASS.equals(depositList.get(i).getCheckStatus()) && !Constant.DEPOSIT_BANK_FAIL.equals(depositList.get(i).getCheckStatus()) ) {
	    			return new ResponseMessage(MessageCode.MSG_DEPOSIT_STATUS_ERROR);
				}
			}
			
			List<SchDeposit> updateDepositList = new ArrayList<SchDeposit>();
			SchDeposit deposit;
			Integer idI;
			for (int i =0; i< idList.length; i++) {
				idI = Integer.parseInt(idList[i]);
				deposit = new SchDeposit();
				deposit.setChecker(checker);
			    deposit.setCheckStatus(Constant.DEPOSIT_SUCCESS);
			    deposit.setCheckRemark(checkRemark);
			    deposit.setId(idI);
			    updateDepositList.add(deposit);
			}
			schDsepositManager.batchUpdateStatusList(updateDepositList);  //批量更新状态
			logger.info("---------------------------------------DepositController  Update  Status To Success Finash!");
			
			return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
		} 
		catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
	}

	/**
	 * 财务拒接、提现失败
	 */
	@Override
	public ResponseMessage reject(String checker, String checkRemark, String id, String handlerType) {
		String[] idList  = id.split(",");
		logger.info("------------------------------------------idList: " + idList);
		try {
			List<Integer> ids=new ArrayList<Integer>();
			for (String a : idList) {
				ids.add(Integer.parseInt(a));
			}
			logger.info("---------------------------------------Deposit Controller  Reject Ids: " + ids);
			List<SchDeposit> depositList = schDsepositManager.queryDepositInfo(ids); //批量查询
			for(int i = 0; i < depositList.size(); i++){
				if("1".equals(handlerType)) { //提现失败
			    	if(!Constant.FINANCE_PASS.equals(depositList.get(i).getCheckStatus()) && !Constant.DEPOSIT_BANK_FAIL.equals(depositList.get(i).getCheckStatus())){
			    		logger.info("***************************************** Deposti Rejecet!");
			    		return new ResponseMessage(MessageCode.MSG_DEPOSIT_STATUS_ERROR);
			    	}
				}
				else {//财务拒绝
					if(!Constant.DEPOSIT_UN_HANDLER.equals(depositList.get(i).getCheckStatus())){
						logger.info("*****************************************Deposti Finance Rejecet!");
						return new ResponseMessage(MessageCode.MSG_DEPOSIT_STATUS_ERROR);
			    	}
				}
			}
			
			List<SchDeposit> updateDepositList = new ArrayList<SchDeposit>();
			SchDeposit deposit;
			Integer idI;
			for (int i =0; i< idList.length; i++) {
				idI = Integer.parseInt(idList[i]);
				deposit = new SchDeposit();
				deposit.setChecker(checker);
			    deposit.setCheckStatus(Constant.DEPOSIT_FAIL);
			    if("1".equals(handlerType)) {
			    	deposit.setCheckRemark("提现失败：" + checkRemark);
				}
				else {
					deposit.setCheckRemark("财务拒绝：" + checkRemark);
				}
			    deposit.setId(idI);
			    updateDepositList.add(deposit);
			}
			schDsepositManager.batchUpdateStatusList(updateDepositList);  //批量更新提现表状态
			logger.info("---------------------------------------DepositController  Update User Status Fail Success!");
			 
			int money;
			for(int i = 0; i < depositList.size(); i++){
				Integer depMoeny  = Math.abs(depositList.get(i).getMoney());
				//查金额
				SchAccount schAccount = schoolService.getSchoolIdMoney(depositList.get(i).getSchoolId());
				money  = schAccount.getMoney() + depMoeny;  
				schoolService.addMoneyBack(money, depositList.get(i).getSchoolId());//回退预扣金额
				logger.info("---------------------------------------DepositController CoachId: " + depositList.get(i).getSchoolId() + ", Old Money: "+ 
						schAccount.getMoney() + ", Add Money : " + depositList.get(i).getMoney() + ", Success!");
				//记录日志
				addMoneyLog(depositList.get(i).getSchoolId(), 3, money,  depMoeny,  depositList.get(i).getOrderId());
			}
			
			return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
		} 
		catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
	}
	
	/**
	 * 记录金额操作日志
	 * @param userId
	 * @param userType
	 * @param leftMoney
	 * @param price
	 * @param moneyChange
	 * @param orderId
	 */
    public void addMoneyLog(Long userId, int userType,int leftMoney, Integer price,  String orderId)
    {
        try {
			UserMoneyVo userMoneyDto = new UserMoneyVo();
			userMoneyDto.setChangeValue(price);
			userMoneyDto.setLeftValue(leftMoney);
			userMoneyDto.setOperateTime(new Date());
			userMoneyDto.setOperateType(1);
			userMoneyDto.setPayWay("yinlian");
			userMoneyDto.setRemark(Constant.DEPOSIT_BACK_MONEY);
			userMoneyDto.setUserId(userId);
			userMoneyDto.setUserType(userType);
			userMoneyDto.setOrderId(orderId);
			userMoneyDto.setStatus(1);
			userMoneyManager.insertUserMoneyInfo(userMoneyDto);
			logger.info("-----------------------------------------DepositController Add New Money Log Success!");
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
    }

    /**
     * 财务确认
     */
	@Override
	public ResponseMessage financePass(String checker, String checkRemark, String id) {
		try {
			String[] idList  = id.split(",");
			
			List<Integer> ids=new ArrayList<Integer>();
			for (String a : idList) {
				ids.add(Integer.parseInt(a));
			}
			logger.info("---------------------------------------DepositController  Pass Ids: " + ids);
			List<SchDeposit> depositList = schDsepositManager.queryDepositInfo(ids); //批量查询
			for(int i = 0; i < depositList.size(); i++){
				if(!Constant.DEPOSIT_UN_HANDLER.equals(depositList.get(i).getCheckStatus())){
					return new ResponseMessage(MessageCode.MSG_DEPOSIT_STATUS_ERROR);
				}
			}
			
			List<SchDeposit> updateDepositList = new ArrayList<SchDeposit>();
			SchDeposit deposit;
			Integer idI;
			for (int i =0; i< idList.length; i++) {
				idI = Integer.parseInt(idList[i]);
				deposit = new SchDeposit();
				deposit.setChecker(checker);
			    deposit.setCheckStatus(Constant.FINANCE_PASS);
			    deposit.setCheckRemark(checkRemark);
			    deposit.setId(idI);
			    updateDepositList.add(deposit);
			}
			schDsepositManager.batchUpdateStatusList(updateDepositList);  //批量更新状态
			logger.info("---------------------------------------DepositController  Update  Status To Success Finash!");
			
			return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
		} catch (NumberFormatException e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
	}
	
	@Override
	public List<SchDeposit> queryDownDepositList(SchDeposit schdeposit) {
		return schDsepositManager.downLoadDepositList(schdeposit);
	}
	
	
	
	//******************************************************
	/**
	 * 以下为驾校端功能
	 */
	//******************************************************


	/**
	 * 驾校端-查询单笔提现记录
	 */
	@Override
	public SchDeposit query(SchDeposit schDeposit) {
		return schDsepositManager.querySchDeposit(schDeposit);
	}

	/**
	 * 驾校端-查询提现记录
	 */
	@Override
	public String queryDepositList(SchDeposit schDeposit) {
		String resp = null;
		try {
			PagedResult<SchDeposit> batchDeposit =  schDsepositManager.querySchDepositList(schDeposit);
			resp = new ResponseMessage().addResult("pageData", batchDeposit).build();
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		
		return resp; 
	}

	/**
	 * 驾校端-提现
	 */
	@Override
	public ResponseMessage insertDeposit(SchDeposit schDeposit) {
		try {
			schDsepositManager.insertSchDeposit(schDeposit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	/**
	 * 驾校端-查询剩余提现次数
	 */
	@Override
	public int queryLeftDepositCount(SchDeposit schDeposit) {
		return schDsepositManager.queryLeftDepositCount(schDeposit);
	}

	/**
	 * 驾校端-下载
	 */
	@Override
	public List<SchDeposit> downLoadExcel(SchDeposit schdeposit) {
		return schDsepositManager.queryDownLoadExcel(schdeposit);
	}

}
