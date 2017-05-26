package com.lili.logic.service.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;

import com.lili.coach.service.CoachService;
import com.lili.coach.vo.CoachVo;
import com.lili.common.util.BeanCopy;
import com.lili.location.service.LocationService;
import com.lili.order.service.CoachClassService;
import com.lili.order.service.UnitPriceService;

@SuppressWarnings("unused")
public class Search3Step implements Callable<List<CoachVo>> {
	private Logger log = Logger.getLogger(Search3Step.class);
	
	private LocationService locationService;
	private CoachClassService coachClassService;
	private CoachService coachService;
	private UnitPriceService unitPriceService;
	
	private String studentId;
	private String lge;
	private String lae;
    private String examType;
    private String carLevel;
	private String courseId;
	private int searchStart;
	private int searchSize;
	private double distance;
	private List<CoachVo> coachList;
	private Date pstart;
	ExecutorService threadPool;
	
	public Search3Step(String studentId, String lge, String lae,
		    String examType, String carLevel,
			String courseId,int searchStart,int searchSize,double distance,List<CoachVo> coachList,
			Date pstart,ExecutorService threadPool,
			LocationService locationService,CoachClassService coachClassService,
			CoachService coachService,UnitPriceService unitPriceService){
		this.studentId=studentId;
		this.lge=lge;
		this.lae=lae;
	    this.examType=examType;
	    this.carLevel=carLevel;
		this.courseId=courseId;
		this.searchStart=searchStart;
		this.searchSize=searchSize;
		this.distance=distance;
		this.coachList=coachList;
		this.pstart=pstart;
		this.threadPool=threadPool;
		this.locationService=locationService;
		this.coachClassService=coachClassService;
		this.coachService=coachService;
		this.unitPriceService=unitPriceService;
	}
	@Override
	public List<CoachVo> call() throws Exception {
		long allStart=System.currentTimeMillis();
		List<CoachVo> list = new ArrayList<CoachVo>();
		try {
//			// 1.获取附近的教练，通过不同的距离，得到需求范围内的数量
//			SearchVo search = new SearchVo();
//			search.setLae(Double.valueOf(lae));
//			search.setLge(Double.valueOf(lge));
//			search.setPageSkip(searchStart);
//			search.setPageLimit(searchSize);
//			search.setDistance(distance);
//			long start=0;
//				//1.获取位置
//				start=System.currentTimeMillis();
//				List<CoachVo> coachs = locationService.getNearBy(search);
//				List<Long> coachIds=BeanCopy.getFieldList(coachs, "coachId");
//				if(log.isDebugEnabled()){
//					log.debug(Thread.currentThread().getId()+"Thread,"+coachIds+" order search location="+(System.currentTimeMillis()-start)+" with lge="+lge+",lae="+lae);
//				}
				List<Long> coachIds=BeanCopy.getFieldList(coachList,"coachId");
				//教练过滤
				SearchCoach searchCoach=new SearchCoach(studentId, lge, lae, examType, carLevel, courseId, coachList, pstart,threadPool,locationService, coachClassService, coachService, unitPriceService);  
				FutureTask<List<CoachVo>> coachFuture = new FutureTask<List<CoachVo>>(searchCoach);
				threadPool.execute(coachFuture);
				//根据需求先屏蔽在线状态
//				//在线状态过滤
//				SearchOnline searchOnline=new SearchOnline(studentId, lge, lae, examType, carLevel, courseId, coachIds, locationService, coachClassService, coachService, unitPriceService);  
//				FutureTask<List<Long>> onlineFuture = new FutureTask<List<Long>>(searchOnline);
//				threadPool.execute(onlineFuture);
				//排班过滤
				SearchClass searchClass=new SearchClass(studentId, lge, lae, examType, carLevel, courseId, coachIds, pstart,locationService, coachClassService, coachService, unitPriceService);  
				FutureTask<List<Long>> classFuture = new FutureTask<List<Long>>(searchClass);
				threadPool.execute(classFuture);
				//最后结果整理
//				List<Long> onlineResultList=onlineFuture.get();
				List<Long> classResult=classFuture.get();
				List<CoachVo> coachResult=coachFuture.get();
				for(CoachVo one:coachResult){
					long id=one.getCoachId().longValue();
//					if(onlineResultList.contains(id) && classResult.contains(id)){
					//排除重复
					if(classResult.contains(id)&& !list.contains(one)){
						list.add(one);
					}
				}
				if(log.isDebugEnabled()){
					log.debug(Thread.currentThread().getId()+"Thread, last with param:searchStart="+searchStart+",searchSize="+searchSize+",studentId="+studentId+",lge="+lge+",lae="+lae+",examType="+examType+",carLevel="+carLevel+",courseId="+courseId+","+BeanCopy.getFieldList(list,"coachId")+" order search="+(System.currentTimeMillis()-allStart));
				}
		} catch (Exception e) {
			try {
				log.error(Thread.currentThread().getId()+"Thread, last with param:searchStart="+searchStart+",searchSize="+searchSize+",studentId="+studentId+",lge="+lge+",lae="+lae+",examType="+examType+",carLevel="+carLevel+",courseId="+courseId+","+BeanCopy.getFieldList(list,"coachId")+" nearby order search failed=" + e.getMessage(), e);
			} catch(Exception ee){}
			//针对全面搜索,不返回异常,返回部分结果
//			result = ReqResult.getFailed();
		}
		return list;
	}
}
