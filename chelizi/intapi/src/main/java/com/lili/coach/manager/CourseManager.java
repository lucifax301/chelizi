package com.lili.coach.manager;

import java.util.List;

import com.lili.coach.dto.Course;

public interface CourseManager {
	
	/**
	 * 获取课程列表
	 * @return
	 */
	public List<Course> getCourse();
	
	/**
	 * 根据id获取课程信息
	 * @param courseId
	 * @return
	 */
	public Course getCourseById(int courseId);
	
	/**
	 * 获取课程数量
	 * @return
	 */
	public int getCourseCount();
	
	/**
	 * 新增课程信息
	 * @param course
	 * @return
	 */
	public int addCourse(Course course);
	
	/**
	 * 更新课程信息
	 * @param course
	 * @return
	 */
	public int updateCourse(Course course);
	
	/**
	 * 根据id删除课程信息
	 * @param courseId
	 * @return
	 */
	public int deleteCourse(int courseId);
}
