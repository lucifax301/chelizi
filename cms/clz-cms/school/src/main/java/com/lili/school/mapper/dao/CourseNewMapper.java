package com.lili.school.mapper.dao;

import java.util.List;

import com.lili.school.model.CourseNewDTO;

public interface CourseNewMapper {
	
	int deleteByPrimaryKey(String coursenewno);

	int insert(CourseNewDTO record);

	int insertSelective(CourseNewDTO record);

	CourseNewDTO selectByPrimaryKey(String coursenewno);

	int updateByPrimaryKeySelective(CourseNewDTO record);

	int updateByPrimaryKey(CourseNewDTO record);

	List<CourseNewDTO> queryCourseNewList(CourseNewDTO course);

	List<CourseNewDTO> queryCourseList(String id);
}