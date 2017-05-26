package com.lili.log.mapper.dao;

import com.lili.log.dto.LogMoney;

public interface LogMoneyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LogMoney record);

    int insertSelective(LogMoney record);

    LogMoney selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LogMoney record);

    int updateByPrimaryKey(LogMoney record);
}