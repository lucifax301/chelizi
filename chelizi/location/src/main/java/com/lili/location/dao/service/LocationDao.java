package com.lili.location.dao.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.lili.coach.vo.CoachVo;
import com.lili.common.util.BeanCopy;
import com.lili.location.dao.Po.CoachPo;
import com.lili.location.dao.Po.SearchPo;

@Repository
public class LocationDao 
{
    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveTeacher(CoachPo teacher)
    {
        mongoTemplate.save(teacher);
    }

    public List<CoachVo> getNearBy(SearchPo search) throws Exception
    {
        Criteria criteria = new Criteria();
        Double distance=search.getDistance();
        //默认5公里
        if(distance==null){
        	distance=5d;
        }
        //暂时不处理工作状态
//        Integer wstate=search.getWstate();
//        if(wstate!=null) {
//            criteria.andOperator(Criteria.where("wstate").is(wstate));//普通查询条件
//        }
        NearQuery near= NearQuery.near(new Point(search.getLge(),search.getLae())).maxDistance(distance, Metrics.KILOMETERS);
        Query queryCondition = new Query(criteria);
        queryCondition.skip(search.getPageSkip());//起始页
        queryCondition.limit(search.getPageLimit());//每页限制条数
        //60分钟内有上报坐标的数据
        queryCondition.addCriteria(Criteria.where("lastUploadTime").gt(
				System.currentTimeMillis()-1000*60*60*24*7*2));
        near.query(queryCondition);
        GeoResults<CoachPo>  test = mongoTemplate.geoNear(near, CoachPo.class);
        List<GeoResult<CoachPo>> testList = test.getContent();
        List<CoachVo> finded=new ArrayList<CoachVo>();
        for(GeoResult<CoachPo> one:testList) {
        	CoachPo po=one.getContent();
        	po.setDistance(one.getDistance().getValue()*1000);
        	CoachVo vo=BeanCopy.copyAll(po,CoachVo.class);
        	vo.setLge(po.getLoc()[0]);
        	vo.setLae(po.getLoc()[1]);
            finded.add(vo);
        }
        return finded;
    }
    
    public List<CoachVo> getNearByInArrange(SearchPo search) throws Exception
    {
        Criteria criteria = new Criteria();
        Double distance=search.getDistance();
        //默认5公里
        if(distance==null){
        	distance=5d;
        }
        //暂时不处理工作状态
//        Integer wstate=search.getWstate();
//        if(wstate!=null) {
//            criteria.andOperator(Criteria.where("wstate").is(wstate));//普通查询条件
//        }
        NearQuery near= NearQuery.near(new Point(search.getLge(),search.getLae())).maxDistance(distance, Metrics.KILOMETERS);
        Query queryCondition = new Query(criteria);
        queryCondition.skip(search.getPageSkip());//起始页
        queryCondition.limit(search.getPageLimit());//每页限制条数
        //60分钟内有上报坐标的数据
        queryCondition.addCriteria(Criteria.where("lastUploadTime").gt(
				System.currentTimeMillis()-1000*60*60));
        near.query(queryCondition);
        GeoResults<CoachPo>  test = mongoTemplate.geoNear(near, CoachPo.class);
        List<GeoResult<CoachPo>> testList = test.getContent();
        List<CoachVo> finded=new ArrayList<CoachVo>();
        for(GeoResult<CoachPo> one:testList) {
        	CoachPo po=one.getContent();
        	po.setDistance(one.getDistance().getValue()*1000);
        	CoachVo vo=BeanCopy.copyAll(po,CoachVo.class);
        	vo.setLge(po.getLoc()[0]);
        	vo.setLae(po.getLoc()[1]);
            finded.add(vo);
        }
        return finded;
    }
    
    public CoachVo getCoach(long coachId) throws Exception
    {
    	CoachPo po =mongoTemplate.findById(coachId, CoachPo.class);
        if(po!=null){
        CoachVo vo=BeanCopy.copyAll(po,CoachVo.class);
    	vo.setLge(po.getLoc()[0]);
    	vo.setLae(po.getLoc()[1]);
        return vo;
        }
        return null;
    }
    
    public List<CoachVo> getCoaches(List<Long> coachIds) throws Exception
    {
    	Query queryCondition = new Query();
    	
    	queryCondition.addCriteria(Criteria.where("_id").in(coachIds));
    	List<CoachPo> pos= mongoTemplate.find(queryCondition, CoachPo.class);
    	List<CoachVo> finded=new ArrayList<CoachVo>();
        for(CoachPo po:pos){
	        if(po!=null){
		        CoachVo vo=BeanCopy.copyAll(po,CoachVo.class);
		    	vo.setLge(po.getLoc()[0]);
		    	vo.setLae(po.getLoc()[1]);
		    	finded.add(vo);
	        }
        }
        return finded;
    }
}
