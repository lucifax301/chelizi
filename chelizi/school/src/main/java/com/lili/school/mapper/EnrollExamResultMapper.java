package com.lili.school.mapper;

import com.lili.school.dto.EnrollExamResult;
import com.lili.school.dto.EnrollExamResultExample;
import com.lili.school.dto.EnrollExamResultKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface EnrollExamResultMapper {
    int countByExample(EnrollExamResultExample example);

    int deleteByExample(EnrollExamResultExample example);

    int deleteByPrimaryKey(EnrollExamResultKey key);

    int insert(EnrollExamResult record);

    int insertSelective(EnrollExamResult record);

    List<EnrollExamResult> selectByExampleWithRowbounds(EnrollExamResultExample example, RowBounds rowBounds);

    List<EnrollExamResult> selectByExample(EnrollExamResultExample example);

    EnrollExamResult selectByPrimaryKey(EnrollExamResultKey key);

    int updateByExampleSelective(@Param("record") EnrollExamResult record, @Param("example") EnrollExamResultExample example);

    int updateByExample(@Param("record") EnrollExamResult record, @Param("example") EnrollExamResultExample example);

    int updateByPrimaryKeySelective(EnrollExamResult record);

    int updateByPrimaryKey(EnrollExamResult record);
}