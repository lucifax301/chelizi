package com.lili.report.mapper.dao;


import java.util.List;

import com.lili.report.model.StatisticsStudent;
import com.lili.report.vo.StatisticsStudentProgress;
import com.lili.report.vo.StatisticsStudentVo;

public interface IStatisticsStudentDao {
	public int deleteByPrimaryKey(Long id);

    public int insert(StatisticsStudentVo record);

    public int insertSelective(StatisticsStudentVo record);

    public StatisticsStudentVo selectByPrimaryKey(Long id);

    public int updateByPrimaryKeySelective(StatisticsStudentVo record);

    public int updateByPrimaryKey(StatisticsStudent record);

	public List<StatisticsStudentVo> queryStudentList(StatisticsStudentVo statisticsStudent);
	
	public List<StatisticsStudentVo> queryStudentByDateList(StatisticsStudentVo statisticsStudent);

	public StatisticsStudentVo queryStudentSum(StatisticsStudentVo statisticsStudent);
	
	
}