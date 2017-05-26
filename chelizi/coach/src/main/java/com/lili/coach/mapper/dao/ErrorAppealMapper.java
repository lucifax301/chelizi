package com.lili.coach.mapper.dao;

import com.lili.coach.dto.ErrorAppeal;
import com.lili.coach.dto.ErrorAppealExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ErrorAppealMapper {
    int countByExample(ErrorAppealExample example);

    int deleteByExample(ErrorAppealExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ErrorAppeal record);

    int insertSelective(ErrorAppeal record);

    List<ErrorAppeal> selectByExampleWithRowbounds(ErrorAppealExample example, RowBounds rowBounds);

    List<ErrorAppeal> selectByExample(ErrorAppealExample example);

    ErrorAppeal selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ErrorAppeal record, @Param("example") ErrorAppealExample example);

    int updateByExample(@Param("record") ErrorAppeal record, @Param("example") ErrorAppealExample example);

    int updateByPrimaryKeySelective(ErrorAppeal record);

    int updateByPrimaryKey(ErrorAppeal record);
}