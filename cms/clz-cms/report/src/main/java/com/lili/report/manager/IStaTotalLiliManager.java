package com.lili.report.manager;


import java.util.List;
import java.util.Map;

import com.lili.cms.entity.PagedResult;
import com.lili.report.vo.StatisticsCoachSchool;
import com.lili.report.vo.StatisticsCoachVo;
import com.lili.report.vo.StatisticsOrderVo;
import com.lili.report.vo.StatisticsStudentProgress;
import com.lili.report.vo.StatisticsStudentSchool;
import com.lili.report.vo.StatisticsStudentVo;
import com.lili.report.vo.StatisticsTotalLiliVo;

/**
 * 教务统计
 * 
 * @author lzb
 *
 */
public interface IStaTotalLiliManager {

	public abstract List<StatisticsTotalLiliVo> getStatisticsTotalLiliInfo(Map<String, Object> params);

	public abstract StatisticsTotalLiliVo queryWeekInfo(Map<String, Object> params);

	public abstract StatisticsTotalLiliVo queryMonthInfo(Map<String, Object> params);

	public abstract PagedResult<StatisticsOrderVo> queryOrderList(StatisticsOrderVo statisticsOrderVo);

	public abstract StatisticsOrderVo queryOrderSum(StatisticsOrderVo statisticsOrderVo);

	public abstract PagedResult<StatisticsStudentVo> queryStudentList(StatisticsStudentVo statisticsOrderVo);

	public abstract StatisticsStudentVo queryStudentSum(StatisticsStudentVo statisticsOrderVo);

	public abstract PagedResult<StatisticsCoachVo> queryCoachList(StatisticsCoachVo statisticsCoachVo);

	public abstract StatisticsCoachVo queryCoachSum(StatisticsCoachVo statisticsCoachVo);

	public abstract PagedResult<StatisticsStudentSchool> queryStudentJxList(StatisticsStudentSchool statisticsStudentSchool);

	public abstract StatisticsStudentSchool queryStudentJxSum(StatisticsStudentSchool statisticsStudentSchool);

	public abstract PagedResult<StatisticsCoachSchool> queryCoachJxList(StatisticsCoachSchool statisticsCoachSchool);

	public abstract StatisticsCoachSchool queryCoachJxSum(StatisticsCoachSchool statisticsCoachSchool);
	
	
}
