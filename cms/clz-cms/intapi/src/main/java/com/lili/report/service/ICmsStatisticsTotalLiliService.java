package com.lili.report.service;

import com.lili.cms.entity.ResponseMessage;
import com.lili.report.vo.StatisticsCoachSchool;
import com.lili.report.vo.StatisticsCoachVo;
import com.lili.report.vo.StatisticsOrderVo;
import com.lili.report.vo.StatisticsStudentProgress;
import com.lili.report.vo.StatisticsStudentSchool;
import com.lili.report.vo.StatisticsStudentVo;

public interface ICmsStatisticsTotalLiliService {

    public String list(String schoolId) throws Exception;
    
    public String order(StatisticsOrderVo statisticsOrderVo);
    
    public String student(StatisticsStudentVo statisticsStudentVo);
    
    public String coach(StatisticsCoachVo statisticsCoachVo);
    
    public String studentJx(StatisticsStudentSchool statisticsStudentSchool);
    
    public String coachJx(StatisticsCoachSchool statisticsCoachSchool);

	public String listJx(String schoolId);
    
	
}
