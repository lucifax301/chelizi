package com.lili.school.mapper;

import com.lili.school.dto.EnrollLongtrainStudent;
import com.lili.school.dto.EnrollLongtrainStudentExample;
import com.lili.school.dto.EnrollLongtrainStudentKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface EnrollLongtrainStudentMapper {
    int countByExample(EnrollLongtrainStudentExample example);

    int deleteByExample(EnrollLongtrainStudentExample example);

    int deleteByPrimaryKey(EnrollLongtrainStudentKey key);

    int insert(EnrollLongtrainStudent record);

    int insertSelective(EnrollLongtrainStudent record);

    List<EnrollLongtrainStudent> selectByExampleWithRowbounds(EnrollLongtrainStudentExample example, RowBounds rowBounds);

    List<EnrollLongtrainStudent> selectByExample(EnrollLongtrainStudentExample example);

    EnrollLongtrainStudent selectByPrimaryKey(EnrollLongtrainStudentKey key);

    int updateByExampleSelective(@Param("record") EnrollLongtrainStudent record, @Param("example") EnrollLongtrainStudentExample example);

    int updateByExample(@Param("record") EnrollLongtrainStudent record, @Param("example") EnrollLongtrainStudentExample example);

    int updateByPrimaryKeySelective(EnrollLongtrainStudent record);

    int updateByPrimaryKey(EnrollLongtrainStudent record);
}