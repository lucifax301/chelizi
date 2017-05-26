package com.lili.pay.mapper.dao;

import com.lili.pay.dto.SchoolAccount;

public interface SchoolAccountMapper {
    int deleteByPrimaryKey(Integer schoolId);

    int insert(SchoolAccount record);

    int insertSelective(SchoolAccount record);

    SchoolAccount selectByPrimaryKey(Integer schoolId);

    int updateByPrimaryKeySelective(SchoolAccount record);

    int updateByPrimaryKey(SchoolAccount record);
    
    // 以下为手动添加
	SchoolAccount queryAccount(SchoolAccount SchoolAccount);
	
	SchoolAccount queryByPasswd(SchoolAccount record);

	void updateSchoolAccount(SchoolAccount schYKAccount);
	
}