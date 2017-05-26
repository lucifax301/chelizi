package com.lili.school.manager.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.school.manager.ICourseManager;
import com.lili.school.mapper.dao.CourseNewMapper;
import com.lili.school.model.CourseNewDTO;

public class CourseManagerImpl implements ICourseManager {
	Logger logger = Logger.getLogger(CourseManagerImpl.class);
	
	@Autowired
	CourseNewMapper courseNewMapper;

	@Override
	public PagedResult<CourseNewDTO> queryCourseList(CourseNewDTO course) {
		PageUtil.startPage(course.getPageNo(), course.getPageSize());
		return BeanUtil.toPagedResult(courseNewMapper.queryCourseNewList(course));
	}

	@Override
	public List<CourseNewDTO> queryCourseNewList(CourseNewDTO course) {
		List<CourseNewDTO> courseList = null;
		try {
			courseList = courseNewMapper.queryCourseNewList(course);
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		
		return courseList;
	}
	
	@Override
	public List<CourseNewDTO> queryCourseList(String id) {
		List<CourseNewDTO> courseList = null;
		try {
			courseList = courseNewMapper.queryCourseList(id);
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		
		return courseList;
	}

}
