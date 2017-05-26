package com.lili.school.mapper;


import com.lili.school.dto.EnrollPurpose;

public interface EnrollPurposeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnrollPurpose record);

    int insertSelective(EnrollPurpose record);

    EnrollPurpose selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnrollPurpose record);

    int updateByPrimaryKey(EnrollPurpose record);

	EnrollPurpose getEnrollPurpose(EnrollPurpose enrollPurpose);
}