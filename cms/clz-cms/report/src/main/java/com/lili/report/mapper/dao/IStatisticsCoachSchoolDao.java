package com.lili.report.mapper.dao;

import java.util.List;

import com.lili.report.vo.StatisticsCoachSchool;

public interface IStatisticsCoachSchoolDao {
    int deleteByPrimaryKey(Long id);

    int insert(StatisticsCoachSchool record);

    int insertSelective(StatisticsCoachSchool record);

    StatisticsCoachSchool selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StatisticsCoachSchool record);

    int updateByPrimaryKey(StatisticsCoachSchool record);

	List<StatisticsCoachSchool> queryCoachJxList(StatisticsCoachSchool statisticsCoachSchool);

	StatisticsCoachSchool queryCoachJxSum(StatisticsCoachSchool statisticsCoachSchool);
}