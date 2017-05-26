package com.lili.log.mapper.dao;

import java.util.List;

import com.lili.log.dto.LogComplain;

public interface LogComplainMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LogComplain record);

    int insertSelective(LogComplain record);

    LogComplain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LogComplain record);

    int updateByPrimaryKey(LogComplain record);
    
    List<LogComplain> getAll(LogComplain logComplain);
}