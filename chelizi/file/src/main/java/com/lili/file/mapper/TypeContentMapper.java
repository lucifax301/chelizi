package com.lili.file.mapper;



import java.util.List;

import com.lili.file.dto.TypeContent;

public interface TypeContentMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(TypeContent record);

    int insertSelective(TypeContent record);

    TypeContent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TypeContent record);

    int updateByPrimaryKeyWithBLOBs(TypeContent record);

    int updateByPrimaryKey(TypeContent record);

	List<TypeContent> queryContentList(TypeContent tc);

}