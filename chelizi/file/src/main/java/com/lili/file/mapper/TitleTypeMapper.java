package com.lili.file.mapper;


import java.util.List;

import com.lili.file.dto.TitleType;

public interface TitleTypeMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(TitleType record);

    int insertSelective(TitleType record);

    TitleType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TitleType record);

    int updateByPrimaryKey(TitleType record);

	List<TitleType> queryTitleList(TitleType tt);

}