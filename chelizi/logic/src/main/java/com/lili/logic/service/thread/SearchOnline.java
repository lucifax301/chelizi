package com.lili.logic.service.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.lili.coach.service.CoachService;
import com.lili.location.service.LocationService;
import com.lili.order.service.CoachClassService;
import com.lili.order.service.UnitPriceService;
import com.lili.order.vo.SearchVo;

@SuppressWarnings("unused")
public class SearchOnline implements Callable<List<Long>> {
	private Logger log = Logger.getLogger(SearchOnline.class);
	
	
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
	private List<Long> coachIds;
	
	public SearchOnline(String studentId, String lge, String lae,
		    String examType, String carLevel,
			String courseId,List<Long> coachIds,
			LocationService locationService,CoachClassService coachClassService,
			CoachService coachService,UnitPriceService unitPriceService){
		this.studentId=studentId;
		this.lge=lge;
		this.lae=lae;
	    this.examType=examType;
	    this.carLevel=carLevel;
		this.courseId=courseId;
		this.coachIds=coachIds;
		this.locationService=locationService;
		this.coachClassService=coachClassService;
		this.coachService=coachService;
		this.unitPriceService=unitPriceService;
	}
	@Override
	public List<Long> call() throws Exception {
		long allStart=System.currentTimeMillis();
		List<Long> list = new ArrayList<Long>();
		try {
				//教练在线状态
				Map<Long,Boolean> online=coachService.isOnlineByIds(coachIds);
				for(Long one:coachIds){
					Boolean ol=online.get(one);
					if(ol!=null && ol.booleanValue()){
						list.add(one);
					} else {
						if(log.isDebugEnabled()){
							log.debug(Thread.currentThread().getId()+"Thread,"+one+" order search online="+online.get(one));
						}
					}
				}
				if(log.isDebugEnabled()){
					log.debug(Thread.currentThread().getId()+"Thread, online with param:studentId="+studentId+",lge="+lge+",lae="+lae+",examType="+examType+",carLevel="+carLevel+",courseId="+courseId+","+list+" order search="+(System.currentTimeMillis()-allStart));
				}
		} catch (Exception e) {
			try {
				log.error(Thread.currentThread().getId()+"Thread, online with param:studentId="+studentId+",lge="+lge+",lae="+lae+",examType="+examType+",carLevel="+carLevel+",courseId="+courseId+","+list+" nearby order search failed=" + e.getMessage(), e);
			} catch(Exception ee){}
			//针对全面搜索,不返回异常,返回部分结果
//			result = ReqResult.getFailed();
		}
		return list;
	}
}
