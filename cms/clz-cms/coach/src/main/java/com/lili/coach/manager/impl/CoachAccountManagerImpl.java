package com.lili.coach.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.manager.ICoachAccountManager;
import com.lili.coach.mapper.dao.CoachAccountDao;
import com.lili.coach.model.CoachAccount;

public class CoachAccountManagerImpl implements ICoachAccountManager {
	@Autowired
	CoachAccountDao coachAccountDao;

	@Override
	public void addCoachAccount(Long coachId) {
		CoachAccount record = new CoachAccount();
		record.setCoachId(coachId);
		record.setMoney(0);
		coachAccountDao.insertSelective(record );
	}

	@Override
	public void addMoneyBack(Integer money, Long coachId) {
		coachAccountDao.addMoneyBack(money, coachId);
	}

	@Override
	public CoachAccount getCoachIdMoney(Long coachId) {
		return coachAccountDao.getCoachIdMoney(coachId);
	}

	@Override
	public CoachAccount getCoachPhoneMoney(String phoneNum) {
		return coachAccountDao.getCoachPhoneMoney(phoneNum);
	}

	@Override
	public void addMoneyList(List<CoachAccount> coachAccountList) {
		coachAccountDao.addMoneyList(coachAccountList); 
	}

	@Override
	public Long getTotalMoney() {
		return coachAccountDao.getTotalMoney();
	}

}
