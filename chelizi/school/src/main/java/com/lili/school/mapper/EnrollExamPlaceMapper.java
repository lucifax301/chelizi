package com.lili.school.mapper;

import com.lili.school.dto.EnrollExamPlace;
import com.lili.school.dto.EnrollExamPlaceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnrollExamPlaceMapper {
    int countByExample(EnrollExamPlaceExample example);

    int deleteByExample(EnrollExamPlaceExample example);

    int deleteByPrimaryKey(Integer placeid);

    int insert(EnrollExamPlace record);

    int insertSelective(EnrollExamPlace record);

    List<EnrollExamPlace> selectByExample(EnrollExamPlaceExample example);

    EnrollExamPlace selectByPrimaryKey(Integer placeid);

    int updateByExampleSelective(@Param("record") EnrollExamPlace record, @Param("example") EnrollExamPlaceExample example);

    int updateByExample(@Param("record") EnrollExamPlace record, @Param("example") EnrollExamPlaceExample example);

    int updateByPrimaryKeySelective(EnrollExamPlace record);

    int updateByPrimaryKey(EnrollExamPlace record);
}