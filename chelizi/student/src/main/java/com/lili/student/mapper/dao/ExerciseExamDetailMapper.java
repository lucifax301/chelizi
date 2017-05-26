package com.lili.student.mapper.dao;

import com.lili.student.dto.ExerciseExamDetail;
import com.lili.student.dto.ExerciseExamDetailKey;

import java.util.List;

public interface ExerciseExamDetailMapper {
    int deleteByPrimaryKey(ExerciseExamDetailKey key);

    int insert(ExerciseExamDetail record);

    int insertSelective(ExerciseExamDetail record);

    ExerciseExamDetail selectByPrimaryKey(ExerciseExamDetailKey key);

    List<ExerciseExamDetail> selectByExerciseExamId(Integer exerciseExamId);

    int countExerciseExamDetailAnsStaByStudentIdAndAnsSta(ExerciseExamAnsSta exerciseExamAnsSta);

    int updateByPrimaryKeySelective(ExerciseExamDetail record);

    int updateByPrimaryKey(ExerciseExamDetail record);
}