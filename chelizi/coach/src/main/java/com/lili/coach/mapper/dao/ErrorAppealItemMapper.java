package com.lili.coach.mapper.dao;

import com.lili.coach.dto.ErrorAppealItem;
import com.lili.coach.dto.ErrorAppealItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ErrorAppealItemMapper {
    int countByExample(ErrorAppealItemExample example);

    int deleteByExample(ErrorAppealItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ErrorAppealItem record);

    int insertSelective(ErrorAppealItem record);

    List<ErrorAppealItem> selectByExampleWithRowbounds(ErrorAppealItemExample example, RowBounds rowBounds);

    List<ErrorAppealItem> selectByExample(ErrorAppealItemExample example);

    ErrorAppealItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ErrorAppealItem record, @Param("example") ErrorAppealItemExample example);

    int updateByExample(@Param("record") ErrorAppealItem record, @Param("example") ErrorAppealItemExample example);

    int updateByPrimaryKeySelective(ErrorAppealItem record);

    int updateByPrimaryKey(ErrorAppealItem record);
}