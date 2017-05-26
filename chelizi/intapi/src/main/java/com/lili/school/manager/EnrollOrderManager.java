package com.lili.school.manager;

import com.lili.school.dto.EnrollOrder;

public interface EnrollOrderManager {
	public void updateEnrollOrder(EnrollOrder enrollOrder);
	
	public EnrollOrder getEnrollOrderByStudentId(long studentId);
}
