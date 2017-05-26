package com.lili.pay.mapper.dao;

import com.lili.pay.dto.BrokerageCourse;

public interface BrokerageCourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BrokerageCourse record);

    int insertSelective(BrokerageCourse record);

    BrokerageCourse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BrokerageCourse record);

    int updateByPrimaryKey(BrokerageCourse record);

    BrokerageCourse selectRightBrokerage(BrokerageCourse record);
}