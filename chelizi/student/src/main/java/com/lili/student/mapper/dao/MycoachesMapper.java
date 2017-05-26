package com.lili.student.mapper.dao;


import com.lili.student.dto.Mycoaches;
import com.lili.student.dto.MycoachesExample;

public interface MycoachesMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(Mycoaches record);

	int insertSelective(Mycoaches record);


	Mycoaches selectByPrimaryKey(Integer id);


	int updateByPrimaryKeySelective(Mycoaches record);

	int updateByPrimaryKey(Mycoaches record);

	long selectByUserId(Mycoaches record);
	
    int countByExample(MycoachesExample example);
}