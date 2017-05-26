package com.lili.student.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.student.manager.ICmsStudentAccountManager;
import com.lili.student.mapper.dao.common.StudentAccountDao;
import com.lili.student.model.StudentAccount;

public class StudentAccountManagerImpl implements ICmsStudentAccountManager {

	@Autowired
	StudentAccountDao studentAccountDao;
	
	@Override
	public void addMoneyBack(Integer money, Long studentId) {
		studentAccountDao.addMoneyBack(money, studentId);
	}

	@Override
	public StudentAccount getStudentIdMoney(Long studentId) {
		return studentAccountDao.getStudentIdMoney(studentId);
	}

	@Override
	public Long getTotalMoney() {
		return studentAccountDao.getTotalMoney();
	}

}
