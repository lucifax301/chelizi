package com.lili.student.manager;

import com.lili.student.dto.StudentAuth;

public interface StudentAuthManager {
	/**
	 * 增加认证信息
	 * @param studentAuth
	 */
	int addStudentAuth(StudentAuth studentAuth);
	
	/**
	 * 更新认证信息
	 * @param studentAuth
	 * @return
	 */
	int updateStudentAuth(StudentAuth studentAuth);
}
