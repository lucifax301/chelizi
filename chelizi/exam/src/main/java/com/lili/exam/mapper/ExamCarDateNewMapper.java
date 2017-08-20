package com.lili.exam.mapper;

import java.util.List;

import com.lili.exam.dto.ExamCarDateNew;

public interface ExamCarDateNewMapper {
     int countExamCarDate(ExamCarDateNew examCarDate);

     int deleteExamCarDate(ExamCarDateNew examCarDate);

     int insertExamCarDate(ExamCarDateNew examCarDate);

     List<ExamCarDateNew> selectByDate(ExamCarDateNew examCarDate);
    
     ExamCarDateNew selectByPrimaryKey(ExamCarDateNew examCarDate);

     int updateExamCarDate(ExamCarDateNew examCarDate);
}