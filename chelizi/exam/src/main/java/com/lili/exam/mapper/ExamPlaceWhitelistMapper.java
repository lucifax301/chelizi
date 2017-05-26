package com.lili.exam.mapper;

import com.lili.exam.dto.ExamPlaceWhitelist;
import com.lili.exam.dto.ExamPlaceWhitelistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ExamPlaceWhitelistMapper {
    int countByExample(ExamPlaceWhitelistExample example);

    int deleteByExample(ExamPlaceWhitelistExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExamPlaceWhitelist record);

    int insertSelective(ExamPlaceWhitelist record);

    List<ExamPlaceWhitelist> selectByExampleWithRowbounds(ExamPlaceWhitelistExample example, RowBounds rowBounds);

    List<ExamPlaceWhitelist> selectByExample(ExamPlaceWhitelistExample example);

    ExamPlaceWhitelist selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExamPlaceWhitelist record, @Param("example") ExamPlaceWhitelistExample example);

    int updateByExample(@Param("record") ExamPlaceWhitelist record, @Param("example") ExamPlaceWhitelistExample example);

    int updateByPrimaryKeySelective(ExamPlaceWhitelist record);

    int updateByPrimaryKey(ExamPlaceWhitelist record);
}