package com.lili.report.manager.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.common.util.DateUtil;
import com.lili.report.manager.IStaTotalLiliManager;
import com.lili.report.mapper.dao.IStatisticsCoachDao;
import com.lili.report.mapper.dao.IStatisticsCoachSchoolDao;
import com.lili.report.mapper.dao.IStatisticsOrderDao;
import com.lili.report.mapper.dao.IStatisticsStudentDao;
import com.lili.report.mapper.dao.IStatisticsStudentSchoolDao;
import com.lili.report.mapper.dao.IStatisticsTotalLiliDao;
import com.lili.report.vo.StatisticsCoachSchool;
import com.lili.report.vo.StatisticsCoachVo;
import com.lili.report.vo.StatisticsOrderVo;
import com.lili.report.vo.StatisticsStudentProgress;
import com.lili.report.vo.StatisticsStudentSchool;
import com.lili.report.vo.StatisticsStudentVo;
import com.lili.report.vo.StatisticsTotalLiliVo;

public class StaTotalLiliManagerImpl implements IStaTotalLiliManager {
	Logger logger = Logger.getLogger(StaTotalLiliManagerImpl.class);

	@Autowired
	IStatisticsTotalLiliDao statisticsTotalLiliDao;

	@Autowired
	IStatisticsOrderDao statisticsOrderDao;

	@Autowired
	IStatisticsCoachDao statisticsCoachDao;

	@Autowired
	IStatisticsStudentDao statisticsStudentDao;
	
	@Autowired
	IStatisticsCoachSchoolDao statisticsCoachSchoolDao;
	
	@Autowired
	IStatisticsStudentSchoolDao statisticsStudentSchoolDao;

	/**
	 * 查询统计表
	 */
	@Override
	public List<StatisticsTotalLiliVo> getStatisticsTotalLiliInfo(Map<String, Object> params) {
		List<StatisticsTotalLiliVo> statList = null;
		try {
			statList = statisticsTotalLiliDao.queryStatisticsTotalLiliInfo(params);
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return statList;
	}

	/**
	 * 查询一周内统计
	 */
	@Override
	public StatisticsTotalLiliVo queryWeekInfo(Map<String, Object> params) {
		StatisticsTotalLiliVo statVo = null;
		try {
			statVo = statisticsTotalLiliDao.queryWeekInfo(params);
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return statVo;
	}

	/**
	 * 查询一个月内统计
	 */
	@Override
	public StatisticsTotalLiliVo queryMonthInfo(Map<String, Object> params) {
		StatisticsTotalLiliVo statVo = null;
		try {
			StatisticsTotalLiliVo record = new StatisticsTotalLiliVo();
			Date now = new Date();
			Integer dmonth ;
			if (DateUtil.getDay(now) == 1) { //如果是1号，取上个月数据
				dmonth = DateUtil.getMonth(now) -1;
			}
			else {
				dmonth = DateUtil.getMonth(now);
			}
			record.setDmonth(dmonth);
			statVo = statisticsTotalLiliDao.queryMonthInfo(record);
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return statVo;
	}

	@Override
	public PagedResult<StatisticsOrderVo> queryOrderList(StatisticsOrderVo statisticsOrderVo) {
		PageUtil.startPage(statisticsOrderVo.getPageNo(), statisticsOrderVo.getPageSize());
		try {
			if(statisticsOrderVo.getCityId() != null && !"".equals(statisticsOrderVo.getCityId())){
				return BeanUtil.toPagedResult(statisticsOrderDao.queryOrderList(statisticsOrderVo));
			}
			else {
				return BeanUtil.toPagedResult(statisticsOrderDao.queryOrderByDateList(statisticsOrderVo));
			}
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public StatisticsOrderVo queryOrderSum(StatisticsOrderVo statisticsOrderVo) {
		StatisticsOrderVo orderVo = null;
		try {
			orderVo =statisticsOrderDao.queryOrderSum(statisticsOrderVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderVo;
	}

	@Override
	public PagedResult<StatisticsStudentVo> queryStudentList(StatisticsStudentVo statisticsStudentVo) {
		PageUtil.startPage(statisticsStudentVo.getPageNo(), statisticsStudentVo.getPageSize());
		try {
			if(statisticsStudentVo.getCityId() != null && !"".equals(statisticsStudentVo.getCityId())){
				return BeanUtil.toPagedResult(statisticsStudentDao.queryStudentList(statisticsStudentVo));
			}
			else {
				return BeanUtil.toPagedResult(statisticsStudentDao.queryStudentByDateList(statisticsStudentVo));
			}
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public StatisticsStudentVo queryStudentSum(StatisticsStudentVo statisticsStudentVo) {
		StatisticsStudentVo studentVo = null;
		try {
			studentVo =statisticsStudentDao.queryStudentSum(statisticsStudentVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentVo;
	}

	@Override
	public PagedResult<StatisticsCoachVo> queryCoachList(StatisticsCoachVo statisticsCoachVo) {
		PageUtil.startPage(statisticsCoachVo.getPageNo(), statisticsCoachVo.getPageSize());
		try {
			if(statisticsCoachVo.getCityId() != null && !"".equals(statisticsCoachVo.getCityId())){
				return BeanUtil.toPagedResult(statisticsCoachDao.queryCoachList(statisticsCoachVo));
			}
			else {
				return BeanUtil.toPagedResult(statisticsCoachDao.queryCoachByDateList(statisticsCoachVo));
			}
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public StatisticsCoachVo queryCoachSum(StatisticsCoachVo statisticsCoachVo) {
		StatisticsCoachVo coachVo = null;
		try {
			coachVo =statisticsCoachDao.queryCoachSum(statisticsCoachVo);
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return coachVo;
	}

	@Override
	public PagedResult<StatisticsStudentSchool> queryStudentJxList(StatisticsStudentSchool statisticsStudentSchool) {
		PageUtil.startPage(statisticsStudentSchool.getPageNo(), statisticsStudentSchool.getPageSize());
		try {
			return BeanUtil.toPagedResult(statisticsStudentSchoolDao.queryStudentJxList(statisticsStudentSchool));
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public StatisticsStudentSchool queryStudentJxSum(StatisticsStudentSchool statisticsStudentSchool) {
		StatisticsStudentSchool sutdentSch = null;
		try {
			sutdentSch =statisticsStudentSchoolDao.queryStudentJxSum(statisticsStudentSchool);
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return sutdentSch;
	}

	@Override
	public PagedResult<StatisticsCoachSchool> queryCoachJxList(StatisticsCoachSchool statisticsCoachSchool) {
		PageUtil.startPage(statisticsCoachSchool.getPageNo(), statisticsCoachSchool.getPageSize());
		try {
			return BeanUtil.toPagedResult(statisticsCoachSchoolDao.queryCoachJxList(statisticsCoachSchool));
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public StatisticsCoachSchool queryCoachJxSum(StatisticsCoachSchool statisticsCoachSchool) {
		StatisticsCoachSchool coachSch = null;
		try {
			coachSch =statisticsCoachSchoolDao.queryCoachJxSum(statisticsCoachSchool);
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return coachSch;
	}

	

	
}
