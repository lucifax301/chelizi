package com.lili.exam.manager;

import java.util.List;

import com.lili.common.util.Page;
import com.lili.exam.dto.ExamVip;
import com.lili.exam.dto.ExamVipCoach;

public interface ExamVipManager {

	int addExamVip(ExamVip examVip);
	
	int updateExamVip(ExamVip examVip);
	
	Page<ExamVip> getExamVip(ExamVip examVip,String pageNo, String pageSize);
	
	List<ExamVip> getExamVipList(ExamVip examVip,String pageNo, String pageSize);
	
	ExamVip getExamVipOne(Integer id);
	
	public int countExamVip(ExamVip examVip);
	
	public List<ExamVip> getExamVipList(ExamVip examVip);
	
	
	
	int addExamVipCoach(ExamVipCoach examVipCoach);
	
	int updateExamVipCoach(ExamVipCoach examVipCoach);
	
	int delExamVipCoach(ExamVipCoach examVipCoach);
	
	ExamVipCoach getExamVipCoach(Integer id);
	
	Page<ExamVipCoach> getExamVipCoach(ExamVipCoach examVipCoach,String pageNo, String pageSize);
}