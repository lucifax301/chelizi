package com.lili.exam.mapper;

import com.lili.exam.dto.ExamPlaceRecharge;
import com.lili.exam.dto.ExamPlaceRechargeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ExamPlaceRechargeMapper {
    int countByExample(ExamPlaceRechargeExample example);

    int deleteByExample(ExamPlaceRechargeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExamPlaceRecharge record);

    int insertSelective(ExamPlaceRecharge record);

    List<ExamPlaceRecharge> selectByExampleWithRowbounds(ExamPlaceRechargeExample example, RowBounds rowBounds);

    List<ExamPlaceRecharge> selectByExample(ExamPlaceRechargeExample example);

    ExamPlaceRecharge selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExamPlaceRecharge record, @Param("example") ExamPlaceRechargeExample example);

    int updateByExample(@Param("record") ExamPlaceRecharge record, @Param("example") ExamPlaceRechargeExample example);

    int updateByPrimaryKeySelective(ExamPlaceRecharge record);

    int updateByPrimaryKey(ExamPlaceRecharge record);
}