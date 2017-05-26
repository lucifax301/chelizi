package com.lili.student.mapper.dao;

import com.lili.student.dto.ExerciseError;
import com.lili.student.dto.ExerciseErrorKey;

import java.util.List;

public interface ExerciseErrorMapper {
    int deleteByPrimaryKey(ExerciseErrorKey key);

    int insert(ExerciseError record);

    int insertSelective(ExerciseError record);

    ExerciseError selectByPrimaryKey(ExerciseErrorKey key);

    List<ExerciseError> selectByStudentId(long studentId);

    int updateByPrimaryKeySelective(ExerciseError record);

    int updateByPrimaryKey(ExerciseError record);
}