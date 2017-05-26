package com.lili.coach.manager;

import java.util.List;

import com.lili.coach.model.CoachAccount;

public interface ICoachAccountManager{

	public abstract void addCoachAccount( Long coachId);
	
	public abstract void addMoneyBack(Integer money, Long coachId);
	
	public abstract void addMoneyList(List<CoachAccount> coachAccountList);
	
	public abstract  CoachAccount getCoachIdMoney(Long coachId);

	public abstract CoachAccount getCoachPhoneMoney(String phoneNum);

	public Long getTotalMoney();

}
