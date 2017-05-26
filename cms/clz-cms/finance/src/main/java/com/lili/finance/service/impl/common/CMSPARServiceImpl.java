package com.lili.finance.service.impl.common;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.DateUtil;
import com.lili.coach.service.ICmsCoachAccountService;
import com.lili.finance.manager.common.ICMSPARManager;
import com.lili.finance.manager.common.IUserMoneyManager;
import com.lili.finance.model.PAR;
import com.lili.finance.model.PARBDTO;
import com.lili.finance.service.ICMSPARService;
import com.lili.student.service.ICmsStudentAccountService;

public class CMSPARServiceImpl implements ICMSPARService{

	@Autowired
	ICMSPARManager cmsPARManager;
	@Autowired
	private IUserMoneyManager userMoneyDao;
	@Autowired
	private ICmsStudentAccountService studentAccountService;
	@Autowired
	private ICmsCoachAccountService coachAccountService;
	
	@Override
	public ResponseMessage findBatch(PARBDTO dto) {
		PagedResult<PAR> batch =  cmsPARManager.findBatch(dto);
		return new ResponseMessage().addPagedResult(batch);
	}

	@Override
	public long addOne() {
		Long balancePaid = userMoneyDao.queryTotalBalancePaid();
		Long recharge = userMoneyDao.queryTotalRecharge();
		Long bonus = userMoneyDao.queryTotalBonus();
		Long deposit = userMoneyDao.queryTotalDeposit();
		Long studentMoney = studentAccountService.getTotalMoney();
		Long coachMoney = coachAccountService.getTotalMoney();
		Long yesterdayAccount = cmsPARManager.findYesterdayAccount();
		

		DecimalFormat df = new DecimalFormat("0.00");//格式化小数   
		
		//状态 = (收入-支出 )-(今天的余额-昨天的余额)
		Long balance = (recharge + bonus) - (Math.abs(deposit) + Math.abs(balancePaid)) - (studentMoney + coachMoney -yesterdayAccount);
		String status = null;
		if(balance == 0){
			status = "平";
		}else {
			status =  df.format((float)balance/100); 
		}
		
		PAR par = new PAR();
		par.setBalancePaid(balancePaid);
		par.setRecharge(recharge);
		par.setBonus(bonus);
		par.setDeposit(deposit);
		par.setStudentMoney(studentMoney);
		par.setCoachMoney(coachMoney);
		par.setStatus(status);
		par.setCalTime(DateUtil.getYesterday());
		par.setAccountMoney(studentMoney + coachMoney);
		return cmsPARManager.addOne(par);
	}

}
