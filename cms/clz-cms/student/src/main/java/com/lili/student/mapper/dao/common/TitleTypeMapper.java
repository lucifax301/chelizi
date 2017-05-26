package com.lili.student.mapper.dao.common;

import java.util.List;

import com.lili.student.model.TitleTypeVo;

public interface TitleTypeMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(TitleTypeVo record);

    int insertSelective(TitleTypeVo record);

    TitleTypeVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TitleTypeVo record);

    int updateByPrimaryKey(TitleTypeVo record);

	List<TitleTypeVo> queryTitleList(TitleTypeVo tt);
}