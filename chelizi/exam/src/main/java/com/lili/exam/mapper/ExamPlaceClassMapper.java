package com.lili.exam.mapper;

import com.lili.exam.dto.ExamPlaceClass;
import com.lili.exam.dto.ExamPlaceClassExample;
import com.lili.exam.dto.ExamPlacePayOrder;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ExamPlaceClassMapper {
    int countByExample(ExamPlaceClassExample example);

    int deleteByExample(ExamPlaceClassExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExamPlaceClass record);

    int insertSelective(ExamPlaceClass record);

    List<ExamPlaceClass> selectByExampleWithRowbounds(ExamPlaceClassExample example, RowBounds rowBounds);

    List<ExamPlaceClass> selectByExample(ExamPlaceClassExample example);

    ExamPlaceClass selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExamPlaceClass record, @Param("example") ExamPlaceClassExample example);

    int updateByExample(@Param("record") ExamPlaceClass record, @Param("example") ExamPlaceClassExample example);

    int updateByPrimaryKeySelective(ExamPlaceClass record);

    int updateByPrimaryKey(ExamPlaceClass record);
    
    
}