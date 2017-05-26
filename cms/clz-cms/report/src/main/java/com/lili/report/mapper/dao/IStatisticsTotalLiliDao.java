package com.lili.report.mapper.dao;

import java.util.List;
import java.util.Map;

import com.lili.report.vo.StatisticsTotalLiliVo;



public interface IStatisticsTotalLiliDao {
	List<StatisticsTotalLiliVo> queryStatisticsTotalLiliInfo(Map<String, Object> params);

	StatisticsTotalLiliVo queryWeekInfo(Map<String, Object> params);

	StatisticsTotalLiliVo queryMonthInfo(StatisticsTotalLiliVo record);

	int deleteByPrimaryKey(Long id);

	int insert(StatisticsTotalLiliVo record);

	int insertSelective(StatisticsTotalLiliVo record);

	StatisticsTotalLiliVo selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(StatisticsTotalLiliVo record);

	int updateByPrimaryKey(StatisticsTotalLiliVo record);
}