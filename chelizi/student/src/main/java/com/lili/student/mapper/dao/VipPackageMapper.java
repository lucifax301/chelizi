package com.lili.student.mapper.dao;


import com.lili.student.dto.VipPackage;

public interface VipPackageMapper {
    int deleteByPrimaryKey(String id);

    int insert(VipPackage record);

    int insertSelective(VipPackage record);

    VipPackage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(VipPackage record);

    int updateByPrimaryKey(VipPackage record);
}