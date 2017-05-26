package com.lili.student.mapper.dao;

import com.lili.student.dto.ExerciseCollection;
import com.lili.student.dto.ExerciseCollectionKey;

import java.util.List;

public interface ExerciseCollectionMapper {
    int deleteByPrimaryKey(ExerciseCollectionKey key);

    int insert(ExerciseCollection record);

    int insertSelective(ExerciseCollection record);

    ExerciseCollection selectByPrimaryKey(ExerciseCollectionKey key);

    List<ExerciseCollection> selectByStudentId(long studentId);

    int updateByPrimaryKeySelective(ExerciseCollection record);

    int updateByPrimaryKey(ExerciseCollection record);
}