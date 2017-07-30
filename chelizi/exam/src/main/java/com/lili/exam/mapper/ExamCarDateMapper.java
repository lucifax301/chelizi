package com.lili.exam.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.lili.exam.dto.ExamCarDate;
import com.lili.exam.dto.ExamVip;

public interface ExamCarDateMapper {
     int countExamCarDate(ExamCarDate examCarDate);

     int deleteExamCarDate(ExamCarDate examCarDate);

     int insertExamCarDate(ExamCarDate examCarDate);

     ExamCarDate selectByDate(ExamCarDate examCarDate);
    
     ExamCarDate selectByPrimaryKey(ExamCarDate examCarDate);

     int updateExamCarDate(ExamCarDate examCarDate);
}