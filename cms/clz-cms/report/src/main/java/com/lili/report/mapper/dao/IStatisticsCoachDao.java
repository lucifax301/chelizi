package com.lili.report.mapper.dao;


import java.util.List;

import com.lili.report.vo.StatisticsCoachVo;

public interface IStatisticsCoachDao {
    int deleteByPrimaryKey(Long id);

    int insert(StatisticsCoachVo record);

    int insertSelective(StatisticsCoachVo record);

    StatisticsCoachVo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StatisticsCoachVo record);

    int updateByPrimaryKey(StatisticsCoachVo record);
    
	public List<StatisticsCoachVo> queryCoachList(StatisticsCoachVo statisticsCoach);
	
	public List<StatisticsCoachVo> queryCoachByDateList(StatisticsCoachVo statisticsCoach);

	StatisticsCoachVo queryCoachSum(StatisticsCoachVo statisticsCoach);
}