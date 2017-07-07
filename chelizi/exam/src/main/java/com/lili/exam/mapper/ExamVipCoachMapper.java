package com.lili.exam.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.lili.exam.dto.ExamVip;
import com.lili.exam.dto.ExamVipCoach;

public interface ExamVipCoachMapper {
    int countExamVipCoach(ExamVipCoach examvip);

    int deleteExamVipCoach(ExamVipCoach examvip);

    int insertExamVipCoach(ExamVipCoach examvip);
    
    int batchInsertExamVipCoach(List<ExamVipCoach> examvips);

    List<ExamVipCoach> select(ExamVipCoach examvip,RowBounds rowBounds);

    ExamVipCoach selectByPrimaryKey(ExamVipCoach examvip);

    int updateExamVipCoach(ExamVipCoach examvip);
}