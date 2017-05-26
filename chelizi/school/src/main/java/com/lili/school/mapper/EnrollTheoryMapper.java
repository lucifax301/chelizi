package com.lili.school.mapper;

import com.lili.school.dto.EnrollTheory;
import com.lili.school.dto.EnrollTheoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface EnrollTheoryMapper {
    int countByExample(EnrollTheoryExample example);

    int deleteByExample(EnrollTheoryExample example);

    int deleteByPrimaryKey(Integer theoryId);

    int insert(EnrollTheory record);

    int insertSelective(EnrollTheory record);

    List<EnrollTheory> selectByExampleWithRowbounds(EnrollTheoryExample example, RowBounds rowBounds);

    List<EnrollTheory> selectByExample(EnrollTheoryExample example);

    EnrollTheory selectByPrimaryKey(Integer theoryId);

    int updateByExampleSelective(@Param("record") EnrollTheory record, @Param("example") EnrollTheoryExample example);

    int updateByExample(@Param("record") EnrollTheory record, @Param("example") EnrollTheoryExample example);

    int updateByPrimaryKeySelective(EnrollTheory record);

    int updateByPrimaryKey(EnrollTheory record);
}