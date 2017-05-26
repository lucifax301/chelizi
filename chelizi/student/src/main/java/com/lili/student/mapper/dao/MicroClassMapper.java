package com.lili.student.mapper.dao;

import java.util.List;

import com.lili.student.dto.MicroClass;

public interface MicroClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MicroClass record);

    int insertSelective(MicroClass record);

    MicroClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MicroClass record);

    int updateByPrimaryKey(MicroClass record);

	List<MicroClass> selectByLevelTwo(MicroClass mocroClass);
}