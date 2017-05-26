/**
 * 
 */
package com.lili.coach.mapper.dao;

import java.util.List;

import com.lili.coach.dto.Course;

public interface CourseMapper {

	/**
	 * 根据主键删除课程信息
	 * @param courseId
	 * @return
	 */
    int deleteByPrimaryKey(Integer courseId);

    /**
     * 新增课程信息
     * @param course
     * @return
     */
    int insert(Course course);

    /**
     * 新增课程信息
     * @param course
     * @return
     */
    int insertSelective(Course course);

    /**
     * 根据id获取课程信息
     * @param courseId
     * @return
     */
    Course selectByPrimaryKey(Integer courseId);

    /**
     * 修改课程信息
     * @param course
     * @return
     */
    int updateByPrimaryKeySelective(Course course);

    /**
     * 修改课程信息
     * @param course
     * @return
     */
    int updateByPrimaryKey(Course course);
    
    /**
     * 获取课程列表
     * @return
     */
    List<Course> getAll();
    
    /**
     * 获取课程数量
     * @return
     */
    int countAll();
}
