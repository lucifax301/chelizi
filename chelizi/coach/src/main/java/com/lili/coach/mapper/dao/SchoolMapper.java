/**
 * 
 */
package com.lili.coach.mapper.dao;

import java.util.List;

import com.lili.coach.dto.School;

public interface SchoolMapper {

	/**
	 * 根据主键删除驾校信息
	 * @param schoolId
	 * @return
	 */
    int deleteByPrimaryKey(Integer schoolId);

    /**
     * 新增驾校信息
     * @param school
     * @return
     */
    int insert(School school);

    /**
     * 新增驾校信息
     * @param school
     * @return
     */
    int insertSelective(School school);

    /**
     * 根据id获取驾校信息
     * @param schoolId
     * @return
     */
    School selectByPrimaryKey(Integer schoolId);

    /**
     * 修改驾校信息
     * @param school
     * @return
     */
    int updateByPrimaryKeySelective(School school);

    /**
     * 修改驾校信息
     * @param school
     * @return
     */
    int updateByPrimaryKey(School school);
    
    /**
     * 获取驾校列表
     * @return
     */
    List<School> getAll();
    
    /**
     * 获取驾校数量
     * @return
     */
    int countAll();
    
}
