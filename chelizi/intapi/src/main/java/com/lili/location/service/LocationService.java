package com.lili.location.service;

import java.util.List;

import com.lili.coach.vo.CoachVo;
import com.lili.location.vo.CoachLoc;
import com.lili.order.vo.SearchVo;



public interface LocationService
{
    public void saveTeacher(CoachLoc Coach) throws Exception;
    public List<CoachVo> getNearBy(SearchVo search)  throws Exception;
    public CoachVo getCoach(long coachId) throws Exception;
    public List<CoachVo> getCoaches(List<Long> coachIds) throws Exception;
}
