package com.lili.exam.mapper;

import com.lili.exam.dto.ExamPlace;
import com.lili.exam.dto.ExamPlaceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ExamPlaceMapper {
    int countByExample(ExamPlaceExample example);

    int deleteByExample(ExamPlaceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExamPlace record);

    int insertSelective(ExamPlace record);

    List<ExamPlace> selectByExampleWithRowbounds(ExamPlaceExample example, RowBounds rowBounds);

    List<ExamPlace> selectByExample(ExamPlaceExample example);
    
    List<ExamPlace> selectBySPKey(String examkey);

    ExamPlace selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExamPlace record, @Param("example") ExamPlaceExample example);

    int updateByExample(@Param("record") ExamPlace record, @Param("example") ExamPlaceExample example);

    int updateByPrimaryKeySelective(ExamPlace record);

    int updateByPrimaryKey(ExamPlace record);
}