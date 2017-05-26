package com.lili.pay.mapper.dao;

import com.lili.pay.dto.BrokerageEnroll;

public interface BrokerageEnrollMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BrokerageEnroll record);

    int insertSelective(BrokerageEnroll record);

    BrokerageEnroll selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BrokerageEnroll record);

    int updateByPrimaryKey(BrokerageEnroll record);

    BrokerageEnroll selectRightBrokerage(BrokerageEnroll record);
}