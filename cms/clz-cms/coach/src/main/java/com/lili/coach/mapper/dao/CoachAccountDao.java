package com.lili.coach.mapper.dao;

/**
 * 
 */

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lili.coach.model.CoachAccount;


public interface CoachAccountDao {
    
    /**
     * 退钱
     * @param money
     * @param coachId
     */
	public void addMoneyBack(@Param("money")Integer money, @Param("coachId")Long coachId);
	
	/**
	 * 查钱
	 * @param money
	 * @param coachId
	 */
	public CoachAccount getCoachIdMoney(Long coachId);

	public Long getTotalMoney();

	public CoachAccount getCoachPhoneMoney(String phoneNum);
	
	public void  insertSelective(CoachAccount record);

	public void addMoneyList(List<CoachAccount> coachAccountList);
}
