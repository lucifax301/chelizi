package com.lili.school.mapper;

import com.lili.school.dto.EnrollExamReg;
import com.lili.school.dto.EnrollExamRegExample;
import com.lili.school.dto.EnrollExamRegKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface EnrollExamRegMapper {
    int countByExample(EnrollExamRegExample example);

    int deleteByExample(EnrollExamRegExample example);

    int deleteByPrimaryKey(EnrollExamRegKey key);

    int insert(EnrollExamReg record);

    int insertSelective(EnrollExamReg record);

    List<EnrollExamReg> selectByExampleWithRowbounds(EnrollExamRegExample example, RowBounds rowBounds);

    List<EnrollExamReg> selectByExample(EnrollExamRegExample example);

    EnrollExamReg selectByPrimaryKey(EnrollExamRegKey key);

    int updateByExampleSelective(@Param("record") EnrollExamReg record, @Param("example") EnrollExamRegExample example);

    int updateByExample(@Param("record") EnrollExamReg record, @Param("example") EnrollExamRegExample example);

    int updateByPrimaryKeySelective(EnrollExamReg record);

    int updateByPrimaryKey(EnrollExamReg record);
}