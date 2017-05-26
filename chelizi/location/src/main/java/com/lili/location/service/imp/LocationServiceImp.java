package com.lili.location.service.imp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.vo.CoachVo;
import com.lili.common.util.BeanCopy;
import com.lili.location.dao.Po.CoachPo;
import com.lili.location.dao.Po.SearchPo;
import com.lili.location.dao.service.LocationDao;
import com.lili.location.service.LocationService;
import com.lili.location.vo.CoachLoc;
import com.lili.order.vo.SearchVo;

public class LocationServiceImp implements LocationService 
{
	private static Logger logger = LoggerFactory.getLogger(LocationServiceImp.class);
    @Autowired
    private LocationDao locationDao;

    public void saveTeacher(CoachLoc coachLoc)  throws Exception
    {
        CoachPo coachPo=new CoachPo();
        coachPo.setCoachId(coachLoc.getCoachId());
        coachPo.setLoc(new Double[] {coachLoc.getLge(),coachLoc.getLae()});
        coachPo.setDir(coachLoc.getDir());
        coachPo.setLastUploadTime(System.currentTimeMillis());
        locationDao.saveTeacher(coachPo);
        logger.debug("LocationServiceImp-->saveTeacher-->"+coachPo);
    }

    public List<CoachVo> getNearBy(SearchVo search)  throws Exception
    {
        SearchPo searchPo=BeanCopy.copyAll(search,SearchPo.class);
        List<CoachVo> coachList=locationDao.getNearBy(searchPo);
        return coachList;
    }
    
    public CoachVo getCoach(long coachId) throws Exception{
    	return locationDao.getCoach(coachId);
    }
    
    public List<CoachVo> getCoaches(List<Long> coachIds) throws Exception{
    	return locationDao.getCoaches(coachIds);
    }
    
}
