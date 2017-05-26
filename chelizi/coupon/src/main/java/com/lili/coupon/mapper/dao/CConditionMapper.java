package com.lili.coupon.mapper.dao;

import java.util.List;

import com.lili.coupon.dto.CCondition;

public interface CConditionMapper {
    int deleteByPrimaryKey(Integer conditionid);

    int insert(CCondition record);

    int insertSelective(CCondition record);

    CCondition selectByPrimaryKey(Integer conditionid);

    int updateByPrimaryKeySelective(CCondition record);

    int updateByPrimaryKey(CCondition record);

    List<CCondition> getAll();
}