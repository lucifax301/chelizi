package com.lili.exam.mapper;

import com.lili.exam.dto.ExamPlaceFavor;
import com.lili.exam.dto.ExamPlaceFavorExample;
import com.lili.exam.dto.ExamPlaceFavorKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ExamPlaceFavorMapper {
    int countByExample(ExamPlaceFavorExample example);

    int deleteByExample(ExamPlaceFavorExample example);

    int deleteByPrimaryKey(ExamPlaceFavorKey key);

    int insert(ExamPlaceFavor record);

    int insertSelective(ExamPlaceFavor record);

    List<ExamPlaceFavor> selectByExampleWithRowbounds(ExamPlaceFavorExample example, RowBounds rowBounds);

    List<ExamPlaceFavor> selectByExample(ExamPlaceFavorExample example);

    ExamPlaceFavor selectByPrimaryKey(ExamPlaceFavorKey key);

    int updateByExampleSelective(@Param("record") ExamPlaceFavor record, @Param("example") ExamPlaceFavorExample example);

    int updateByExample(@Param("record") ExamPlaceFavor record, @Param("example") ExamPlaceFavorExample example);

    int updateByPrimaryKeySelective(ExamPlaceFavor record);

    int updateByPrimaryKey(ExamPlaceFavor record);
}