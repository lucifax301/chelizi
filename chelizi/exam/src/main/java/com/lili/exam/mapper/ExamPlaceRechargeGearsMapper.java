package com.lili.exam.mapper;

import com.lili.exam.dto.ExamPlaceRechargeGears;
import com.lili.exam.dto.ExamPlaceRechargeGearsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ExamPlaceRechargeGearsMapper {
    int countByExample(ExamPlaceRechargeGearsExample example);

    int deleteByExample(ExamPlaceRechargeGearsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExamPlaceRechargeGears record);

    int insertSelective(ExamPlaceRechargeGears record);

    List<ExamPlaceRechargeGears> selectByExampleWithRowbounds(ExamPlaceRechargeGearsExample example, RowBounds rowBounds);

    List<ExamPlaceRechargeGears> selectByExample(ExamPlaceRechargeGearsExample example);

    ExamPlaceRechargeGears selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExamPlaceRechargeGears record, @Param("example") ExamPlaceRechargeGearsExample example);

    int updateByExample(@Param("record") ExamPlaceRechargeGears record, @Param("example") ExamPlaceRechargeGearsExample example);

    int updateByPrimaryKeySelective(ExamPlaceRechargeGears record);

    int updateByPrimaryKey(ExamPlaceRechargeGears record);
}