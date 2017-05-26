package com.lili.exam.mapper;

import com.lili.exam.dto.ExamPlaceRechargeRecord;
import com.lili.exam.dto.ExamPlaceRechargeRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ExamPlaceRechargeRecordMapper {
    int countByExample(ExamPlaceRechargeRecordExample example);

    int deleteByExample(ExamPlaceRechargeRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExamPlaceRechargeRecord record);

    int insertSelective(ExamPlaceRechargeRecord record);

    List<ExamPlaceRechargeRecord> selectByExampleWithRowbounds(ExamPlaceRechargeRecordExample example, RowBounds rowBounds);

    List<ExamPlaceRechargeRecord> selectByExample(ExamPlaceRechargeRecordExample example);

    ExamPlaceRechargeRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExamPlaceRechargeRecord record, @Param("example") ExamPlaceRechargeRecordExample example);

    int updateByExample(@Param("record") ExamPlaceRechargeRecord record, @Param("example") ExamPlaceRechargeRecordExample example);

    int updateByPrimaryKeySelective(ExamPlaceRechargeRecord record);

    int updateByPrimaryKey(ExamPlaceRechargeRecord record);
}