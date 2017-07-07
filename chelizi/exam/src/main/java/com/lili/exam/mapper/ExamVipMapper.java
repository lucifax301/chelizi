package com.lili.exam.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.lili.exam.dto.ExamVip;

public interface ExamVipMapper {
    int countExamVip(ExamVip examvip);

    int deleteExamVip(ExamVip examvip);

    int insertExamVip(ExamVip examvip);

    List<ExamVip> select(ExamVip examvip,RowBounds rowBounds);

    ExamVip selectByPrimaryKey(ExamVip examvip);

    int updateExamVip(ExamVip examvip);
}