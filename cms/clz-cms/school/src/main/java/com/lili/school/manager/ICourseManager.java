package com.lili.school.manager;

import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.school.model.CourseNewDTO;

public interface ICourseManager {

	PagedResult<CourseNewDTO> queryCourseList(CourseNewDTO course);

	List<CourseNewDTO> queryCourseNewList(CourseNewDTO course);
	
	List<CourseNewDTO> queryCourseList(String course);


}
