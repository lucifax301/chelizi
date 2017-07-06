package com.lili.exam.manager;

import java.util.List;

import com.lili.exam.dto.ExamVip;
import com.lili.exam.dto.ExamVipCoach;

public interface ExamVipManager {

	int addExamVip(ExamVip examVip);
	
	int updateExamVip(ExamVip examVip);
	
	List<ExamVip> getExamVip(ExamVip examVip);
	
	int addExamVipCoach(ExamVipCoach examVipCoach);
	
	int updateExamVipCoach(ExamVipCoach examVipCoach);
	
	int delExamVipCoach(ExamVipCoach examVipCoach);
}
