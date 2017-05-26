package com.lili.coach.manager;

import java.util.List;

import com.lili.coach.dto.School;


public interface SchoolManager {

	/**
	 * 获取驾校列表
	 * @return
	 */
	public List<School> getSchool();

	/**
	 * 根据id获取驾校信息
	 * @param id
	 * @return
	 */
	public School getSchoolInfo(int id);

	/**
	 * 获取驾校数量
	 * @return
	 */
	public int getCount();

	/**
	 * 新增驾校信息
	 * @param school
	 * @return
	 */
	public int addSchool(School school);

	/**
	 * 更新驾校信息
	 * @param school
	 * @return
	 */
	public int updateSchool(School school);

	/**
	 * 根据id删除驾校信息
	 * @param id
	 * @return
	 */
	public int deleteSchool(int id);
}
