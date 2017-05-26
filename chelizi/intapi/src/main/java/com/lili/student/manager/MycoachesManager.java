package com.lili.student.manager;

import com.lili.student.dto.Mycoaches;

public interface MycoachesManager {
	int deleteByPrimaryKey(Integer id);

	int addMycoaches(Mycoaches record);

	Mycoaches getMycoachesById(Integer id);

	int updateMycoaches(Mycoaches record);

	long selectByUserId(Mycoaches record);
	
	int countById(Integer studentId,Integer coachId);
}
