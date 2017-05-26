/**
 * 
 */
package com.lili.coach.mapper.dao;

import java.util.List;

import com.lili.coach.dto.CourseS;

public interface CourseSMapper {

	/**
	 * 根据主键删除课程信息
	 * @param courseTmpId
	 * @return
	 */
    int deleteByPrimaryKey(Integer courseTmpId);

    /**
     * 新增课程信息
     * @param courseS
     * @return
     */
    int insert(CourseS courseS);

    /**
     * 新增课程信息
     * @param courseS
     * @return
     */
    int insertSelective(CourseS courseS);

    /**
     * 根据id获取课程信息
     * @param courseTmpId
     * @return
     */
    CourseS selectByPrimaryKey(Integer courseTmpId);

    /**
     * 修改课程信息
     * @param courseS
     * @return
     */
    int updateByPrimaryKeySelective(CourseS courseS);

    /**
     * 修改课程信息
     * @param courseS
     * @return
     */
    int updateByPrimaryKey(CourseS courseS);
    
    /**
     * 获取课程列表
     * @return
     */
    List<CourseS> getAll();
    
    /**
     * 获取课程数量
     * @return
     */
    int countAll();
}
