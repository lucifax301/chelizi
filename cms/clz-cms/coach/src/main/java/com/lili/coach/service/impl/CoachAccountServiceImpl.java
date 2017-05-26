package com.lili.coach.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.manager.ICoachAccountManager;
import com.lili.coach.model.CoachAccount;
import com.lili.coach.service.ICmsCoachAccountService;

public class CoachAccountServiceImpl implements ICmsCoachAccountService {
	
	@Autowired
	ICoachAccountManager coachAccountManager;

	@Override
	public void addCoachAccount(Long coachId) {
		coachAccountManager.addCoachAccount(coachId);
	}

	@Override
	public void addMoneyBack(Integer money, Long coachId) {
		coachAccountManager.addMoneyBack(money, coachId);
	}

	@Override
	public CoachAccount getCoachIdMoney(Long coachId) {
		return coachAccountManager.getCoachIdMoney(coachId);
	}

	@Override
	public CoachAccount getCoachPhoneMoney(String phoneNum) {
		return coachAccountManager.getCoachPhoneMoney(phoneNum);
	}

	@Override
	public void addMoneyList(List<CoachAccount> coachAccountList) {
		 coachAccountManager.addMoneyList(coachAccountList);
	}

	@Override
	public Long getTotalMoney() {
		return coachAccountManager.getTotalMoney();
	}

}
