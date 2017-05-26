package com.lili.finance.service.impl.common;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.coach.service.ICmsCoachAccountService;
import com.lili.finance.manager.common.IUserMoneyManager;
import com.lili.finance.service.ICmsLiliWalletService;
import com.lili.finance.vo.UserMoneyVo;
import com.lili.school.service.CMSSchoolService;
import com.lili.student.service.ICmsStudentAccountService;

public class LiliWalletServiceImpl implements ICmsLiliWalletService {
	private final Logger logger = Logger.getLogger(LiliWalletServiceImpl.class);
	
	@Autowired
	IUserMoneyManager userMoneyManager;
	
	@Autowired
	ICmsCoachAccountService coachAccountService;
	
	@Autowired
	ICmsStudentAccountService studentAccountService;
	
	@Autowired
	CMSSchoolService cmsSchoolService;

	/**
	 * 月统计
	 */
	@Override
	public ResponseMessage fundCount(String month) {
		try {
			UserMoneyVo  userMoneyVo = new UserMoneyVo();
			userMoneyVo.setIsEarning(1); //是否为收入：0=支出，1=收入
			userMoneyVo.setUserType(4);  //用户类型(1教练，2学员，3驾校，4喱喱账户)
			if(null != month && !"".equals(month.trim())){
				userMoneyVo.setYearMonth(month.trim());
			}
			Long income = userMoneyManager.querySumMoneyByIsEarning(userMoneyVo);//收入
			userMoneyVo.setIsEarning(0);
			Long expense = userMoneyManager.querySumMoneyByIsEarning(userMoneyVo); //费用
			Long profitLoss = income - expense; //月盈亏
			
			return new ResponseMessage().addResult("income", income)
															.addResult("expense", expense)
															.addResult("profitLoss", profitLoss);
		} 
		catch (Exception e) {
			logger.error("******************************** fundCount error: " + e.getMessage());
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
	}
	

	/**
	 * 账户余额
	 */
	@Override
	public ResponseMessage balance() {
		try {
			Long coachMoney = coachAccountService.getTotalMoney(); //教练总余额
			Long studentMoney = studentAccountService.getTotalMoney();//学员总余额
			Long schoolMoney = cmsSchoolService.querySchoolMoney();//驾校总余额
			Long liliMoney = cmsSchoolService.queryLiliWalletMoney();//喱喱总余额
			logger.info("************************************** coachMoney: " + coachMoney +",studentMoney: " + studentMoney +",schoolMoney: " + schoolMoney+",liliMoney: " + liliMoney);
			Long totalMoney = coachMoney + studentMoney + schoolMoney + liliMoney;//平台总余额
			
			//百分比
			DecimalFormat df = new DecimalFormat("#.0");  
			String coachPer =  df.format((double)coachMoney / (double)totalMoney * 100);
			String studentPer =df.format((double) studentMoney /(double) totalMoney* 100);
			String schoolPer =df.format((double)schoolMoney /(double) totalMoney* 100);
			String liliPer = df.format((double)liliMoney / (double)totalMoney* 100);
			
			String totalmoney = String.valueOf(totalMoney /1000000 );
			
			return new ResponseMessage().addResult("coachMoney", coachMoney)
															.addResult("studentMoney", studentMoney)
															.addResult("schoolMoney", schoolMoney)
															.addResult("liliMoney", liliMoney)
															.addResult("coachPer", coachPer)
															.addResult("studentPer", studentPer)
															.addResult("schoolPer", schoolPer)
															.addResult("liliPer", liliPer)
															.addResult("totalMoney", totalmoney);
		} 
		catch (Exception e) {
			logger.error("******************************** balance error: " + e.getMessage());
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
	}

	@Override
	public ResponseMessage touchBalance(UserMoneyVo userMoneyVo) {
		try {
			userMoneyVo.setUserType(4);
			userMoneyVo.setIsBalance(1);
			PagedResult<UserMoneyVo>  touchBalance = userMoneyManager.querytouchBalance(userMoneyVo);//余额
			
			return new ResponseMessage().addResult("pageData", touchBalance);
		} 
		catch (Exception e) {
			logger.error("******************************** fundCount error: " + e.getMessage());
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
	}

	@Override
	public ResponseMessage accountBalance(UserMoneyVo userMoneyVo) {
		try {
			userMoneyVo.setUserType(4);
			userMoneyVo.setIsEarning(1);
			PagedResult<UserMoneyVo>  touchBalance = userMoneyManager.querytouchBalance(userMoneyVo);//收入
			
			Long expense = userMoneyManager.querySumMoneyByIsEarning(userMoneyVo); //费用
			
			return new ResponseMessage().addResult("pageData", touchBalance)
															.addResult("expense", expense);
		} 
		catch (Exception e) {
			logger.error("******************************** fundCount error: " + e.getMessage());
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
	}

	@Override
	public ResponseMessage costDetail(UserMoneyVo userMoneyVo) {
		try {
			userMoneyVo.setUserType(4);
			userMoneyVo.setIsEarning(0);
			//费用流水
			//PagedResult<UserMoneyVo>  touchBalance = userMoneyManager.querytouchBalance(userMoneyVo);//支出
			PagedResult<UserMoneyVo>  touchBalance = userMoneyManager.queryCostDetailList(userMoneyVo);
			//费用总和
			//Long expense = userMoneyManager.querySumMoneyByIsEarning(userMoneyVo); //费用
			Long expense = userMoneyManager.querySumMoneyFlowOut(userMoneyVo);
			
			return new ResponseMessage().addResult("pageData", touchBalance)
														.addResult("expense", expense);
		} 
		catch (Exception e) {
			logger.error("******************************** fundCount error: " + e.getMessage());
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
	}

}
