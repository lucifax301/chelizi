package com.lili.student.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.student.dto.StudentAuth;
import com.lili.student.manager.StudentAuthManager;
import com.lili.student.mapper.dao.StudentAuthMapper;

public class StudentAuthManagerImpl implements StudentAuthManager {
	@Autowired
	private StudentAuthMapper studentAuthMapper;

	@Override
	public int addStudentAuth(StudentAuth studentAuth) {
		return studentAuthMapper.insertSelective(studentAuth);
	}

	@Override
	public int updateStudentAuth(StudentAuth studentAuth) {
		return studentAuthMapper.updateByPrimaryKeySelective(studentAuth);
	}

	
}
