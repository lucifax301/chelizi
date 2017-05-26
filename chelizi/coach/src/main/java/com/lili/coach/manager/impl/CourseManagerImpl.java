package com.lili.coach.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.dto.Course;
import com.lili.coach.manager.CourseManager;
import com.lili.coach.mapper.dao.CourseMapper;

public class CourseManagerImpl implements CourseManager {

	@Autowired
	private CourseMapper courseMapper;
	
	@Override
	public List<Course> getCourse() {
		return courseMapper.getAll();
	}

	@Override
	public Course getCourseById(int courseId) {
		return courseMapper.selectByPrimaryKey(courseId);
	}

	@Override
	public int getCourseCount() {
		return courseMapper.countAll();
	}

	@Override
	public int addCourse(Course course) {
		return courseMapper.insert(course);
	}

	@Override
	public int updateCourse(Course course) {
		return courseMapper.updateByPrimaryKeySelective(course);
	}

	@Override
	public int deleteCourse(int courseId) {
		return courseMapper.deleteByPrimaryKey(courseId);
	}

}
