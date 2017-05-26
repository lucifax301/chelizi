package com.lili.student.mapper.dao.common;

/**
 * 
 */

import org.apache.ibatis.annotations.Param;

import com.lili.student.model.StudentAccount;


public interface StudentAccountDao {


	/**
	 * 查钱
	 * @param money
	 * @param coachId
	 */
	public StudentAccount getStudentIdMoney(Long studentId);
	

	public Long getTotalMoney();
	
    /**
     * 退钱
     * @param money
     * @param studendId
     */
	public void addMoneyBack(@Param("money") Integer money, @Param("studentId") Long studentId);
	

    
}
