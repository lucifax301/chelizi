package com.lili.coach.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lili.coach.dto.TrfieldRaw;
import com.lili.coach.dto.TrfieldRawExample;

public interface TrfieldRawMapper {
    int countByExample(TrfieldRawExample example);

    int deleteByExample(TrfieldRawExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TrfieldRaw record);

    int insertSelective(TrfieldRaw record);

    List<TrfieldRaw> selectByExample(TrfieldRawExample example);

    TrfieldRaw selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TrfieldRaw record, @Param("example") TrfieldRawExample example);

    int updateByExample(@Param("record") TrfieldRaw record, @Param("example") TrfieldRawExample example);

    int updateByPrimaryKeySelective(TrfieldRaw record);

    int updateByPrimaryKey(TrfieldRaw record);
}