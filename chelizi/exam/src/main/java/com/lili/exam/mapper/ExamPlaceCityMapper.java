package com.lili.exam.mapper;

import com.lili.exam.dto.ExamPlaceCity;
import com.lili.exam.dto.ExamPlaceCityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ExamPlaceCityMapper {
    int countByExample(ExamPlaceCityExample example);

    int deleteByExample(ExamPlaceCityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExamPlaceCity record);

    int insertSelective(ExamPlaceCity record);

    List<ExamPlaceCity> selectByExampleWithRowbounds(ExamPlaceCityExample example, RowBounds rowBounds);

    List<ExamPlaceCity> selectByExample(ExamPlaceCityExample example);

    ExamPlaceCity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExamPlaceCity record, @Param("example") ExamPlaceCityExample example);

    int updateByExample(@Param("record") ExamPlaceCity record, @Param("example") ExamPlaceCityExample example);

    int updateByPrimaryKeySelective(ExamPlaceCity record);

    int updateByPrimaryKey(ExamPlaceCity record);
}