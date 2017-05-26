package com.lili.exam.mapper;

import com.lili.exam.dto.ExamPlaceRechargeSchool;
import com.lili.exam.dto.ExamPlaceRechargeSchoolExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ExamPlaceRechargeSchoolMapper {
    int countByExample(ExamPlaceRechargeSchoolExample example);

    int deleteByExample(ExamPlaceRechargeSchoolExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExamPlaceRechargeSchool record);

    int insertSelective(ExamPlaceRechargeSchool record);

    List<ExamPlaceRechargeSchool> selectByExampleWithRowbounds(ExamPlaceRechargeSchoolExample example, RowBounds rowBounds);

    List<ExamPlaceRechargeSchool> selectByExample(ExamPlaceRechargeSchoolExample example);

    ExamPlaceRechargeSchool selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExamPlaceRechargeSchool record, @Param("example") ExamPlaceRechargeSchoolExample example);

    int updateByExample(@Param("record") ExamPlaceRechargeSchool record, @Param("example") ExamPlaceRechargeSchoolExample example);

    int updateByPrimaryKeySelective(ExamPlaceRechargeSchool record);

    int updateByPrimaryKey(ExamPlaceRechargeSchool record);
}