package com.lili.student.mapper.dao.common;


import java.util.List;

import com.lili.student.model.TypeContentVo;

public interface TypeContentMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(TypeContentVo record);

    int insertSelective(TypeContentVo record);

    TypeContentVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TypeContentVo record);

    int updateByPrimaryKeyWithBLOBs(TypeContentVo record);

    int updateByPrimaryKey(TypeContentVo record);

	List<TypeContentVo> queryContentList(TypeContentVo tc);

	int deleteByTypeId(TypeContentVo tcv);
}