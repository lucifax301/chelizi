package com.lili.report.mapper.dao;


import java.util.List;

import com.lili.report.vo.StatisticsOrderVo;

public interface IStatisticsOrderDao {
    int deleteByPrimaryKey(Long id);

    int insert(StatisticsOrderVo record);

    int insertSelective(StatisticsOrderVo record);

    StatisticsOrderVo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StatisticsOrderVo record);

    int updateByPrimaryKey(StatisticsOrderVo record);

	public List<StatisticsOrderVo> queryOrderList(StatisticsOrderVo statisticsOrder);
	
	public List<StatisticsOrderVo> queryOrderByDateList(StatisticsOrderVo statisticsOrder);

	StatisticsOrderVo queryOrderSum(StatisticsOrderVo statisticsOrder);
}