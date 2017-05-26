package com.lili.coach.manager;

import java.util.List;
import java.util.Map;

import com.lili.coach.dto.CourseS;

public interface CourseSManager {
	
	/**
	 * 获取课程列表
	 * @return
	 */
	public List<CourseS> getCourseS();
	
	/**
	 * 根据id获取课程信息
	 * @param courseTmpId
	 * @return
	 */
	public CourseS getCourseSById(int courseTmpId);
	
	/**
	 * 获取课程数量
	 * @return
	 */
	public int getCourseSCount();
	
	/**
	 * 新增课程信息
	 * @param courseS
	 * @return
	 */
	public int addCourseS(CourseS courseS);
	
	/**
	 * 更新课程信息
	 * @param courseS
	 * @return
	 */
	public int updateCourseS(CourseS courseS);
	
	/**
	 * 根据id删除课程信息
	 * @param courseTmpId
	 * @return
	 */
	public int deleteCourseS(int courseTmpId);
	
	/**
	 * 取得所有的课程
	 * @return
	 */
	public Map<Integer, CourseS> getAllCourseMap();
}
