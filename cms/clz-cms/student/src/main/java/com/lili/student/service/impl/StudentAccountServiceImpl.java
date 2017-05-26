package com.lili.student.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.student.manager.ICmsStudentAccountManager;
import com.lili.student.model.StudentAccount;
import com.lili.student.service.ICmsStudentAccountService;

public class StudentAccountServiceImpl implements ICmsStudentAccountService {

	@Autowired
	ICmsStudentAccountManager studentAccountManager;
	
	@Override
	public void addMoneyBack(Integer money, Long studentId) {
		studentAccountManager.addMoneyBack(money, studentId);
	}

	@Override
	public StudentAccount getStudentIdMoney(Long studentId) {
		return studentAccountManager.getStudentIdMoney(studentId);
	}

	@Override
	public Long getTotalMoney() {
		return studentAccountManager.getTotalMoney();
	}

}
