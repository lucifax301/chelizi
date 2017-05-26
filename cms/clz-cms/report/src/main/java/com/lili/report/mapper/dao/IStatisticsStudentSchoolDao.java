package com.lili.report.mapper.dao;

import java.util.List;

import com.lili.report.vo.StatisticsStudentSchool;

public interface IStatisticsStudentSchoolDao {
	public int deleteByPrimaryKey(Long id);

	public int insert(StatisticsStudentSchool record);

	public int insertSelective(StatisticsStudentSchool record);

	public StatisticsStudentSchool selectByPrimaryKey(Long id);

	public int updateByPrimaryKeySelective(StatisticsStudentSchool record);

	public int updateByPrimaryKey(StatisticsStudentSchool record);

	public List<StatisticsStudentSchool> queryStudentJxList(StatisticsStudentSchool statisticsStudentSchool);

	public StatisticsStudentSchool queryStudentJxSum(StatisticsStudentSchool statisticsStudentSchool);
}