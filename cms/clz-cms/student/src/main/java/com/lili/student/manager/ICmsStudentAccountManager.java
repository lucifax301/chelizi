package com.lili.student.manager;

import com.lili.student.model.StudentAccount;

public interface ICmsStudentAccountManager {
	
	/**
	 * 回退金额
	 * @param money
	 * @param studentId
	 */
	public abstract void addMoneyBack(Integer money, Long studentId);
	
	/**
	 * 账户
	 * @return
	 */
	public abstract  StudentAccount getStudentIdMoney(Long studentId);

	public abstract Long getTotalMoney();

}
