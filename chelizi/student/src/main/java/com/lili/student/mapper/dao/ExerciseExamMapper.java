package com.lili.student.mapper.dao;

import com.lili.student.dto.ExerciseExam;

import java.util.List;

public interface ExerciseExamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExerciseExam record);

    int insertSelective(ExerciseExam record);

    ExerciseExam selectByPrimaryKey(Integer id);

    List<ExerciseExam> selectByStudentIdWithPage(ExerciseExamPage exerciseExamPage);

    int updateByPrimaryKeySelective(ExerciseExam record);

    int updateByPrimaryKey(ExerciseExam record);

	Integer queryExerScoreSum(ExerciseExam record);
}