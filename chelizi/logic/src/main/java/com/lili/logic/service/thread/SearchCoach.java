package com.lili.logic.service.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;

import com.lili.coach.service.CoachService;
import com.lili.coach.vo.CoachVo;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.StringUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ResultCode;
import com.lili.location.service.LocationService;
import com.lili.order.service.CoachClassService;
import com.lili.order.service.UnitPriceService;

@SuppressWarnings("unused")
public class SearchCoach implements Callable<List<CoachVo>> {
	private Logger log = Logger.getLogger(SearchCoach.class);
	
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
	private List<CoachVo> coachs;
	private Date pstart;
	ExecutorService threadPool;
	
	public SearchCoach(String studentId, String lge, String lae,
		    String examType, String carLevel,
			String courseId,List<CoachVo> coachs,Date pstart,ExecutorService threadPool,
			LocationService locationService,CoachClassService coachClassService,
			CoachService coachService,UnitPriceService unitPriceService){
		this.studentId=studentId;
		this.lge=lge;
		this.lae=lae;
	    this.examType=examType;
	    this.carLevel=carLevel;
		this.courseId=courseId;
		this.coachs=coachs;
		this.pstart=pstart;
		this.threadPool=threadPool;
		this.locationService=locationService;
		this.coachClassService=coachClassService;
		this.coachService=coachService;
		this.unitPriceService=unitPriceService;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<CoachVo> call() throws Exception {
		long allStart=System.currentTimeMillis();
		List<CoachVo> list = new ArrayList<CoachVo>();
		try {
				List<Long> coachIds=BeanCopy.getFieldList(coachs, "coachId");
				List<CoachVo> coachList=(List<CoachVo>)coachService.getCoachsByIds(coachIds).getResult().get(ResultCode.RESULTKEY.DATA);
				if(coachList!=null){
					BeanCopy.copyList(coachs, coachList, BeanCopy.COPY2NULL,"coachId");
					//获取价钱
					SearchPrice searchPrice=new SearchPrice(studentId, lge, lae, examType, carLevel, courseId,coachList,pstart, locationService, coachClassService, coachService, unitPriceService);  
					FutureTask<List<CoachVo>> searchPriceFuture = new FutureTask<List<CoachVo>>(searchPrice);
					threadPool.execute(searchPriceFuture);
					for(CoachVo one:coachList){
						String dltype=String.valueOf(one.getDriveType()); 
						String calid=String.valueOf(one.getCarLevel());
						String courses=one.getCourses();
						int wstate=one.getWstate();
						//0.接单距离
						if(null != one.getAcceptOrderDis() && null!=one.getDistance()&&
								one.getAcceptOrderDis()<one.getDistance()){
							if(log.isDebugEnabled()){
								log.debug(Thread.currentThread().getId()+"Thread,"+one.getCoachId()+" order search real distance="+one.getDistance()+",and param="+one.getAcceptOrderDis());
							}
							continue;
						}
						//1.档位
						if(StringUtil.isNotNullAndNotEmpty(examType) && !examType.equals(dltype)){
							if(log.isDebugEnabled()){
								log.debug(Thread.currentThread().getId()+"Thread,"+one.getCoachId()+" order search dftype not equal="+dltype+",and param="+examType);
							}
							continue;
						}
						//2.科目
						//为空不过滤
						if(StringUtil.isNotNullAndNotEmpty(courseId)){
							if(StringUtil.isNotNullAndNotEmpty(courses)){
								List<String> clist=Arrays.asList(courses.split(","));
								if(!clist.contains(courseId)){
									if(log.isDebugEnabled()){
										log.debug(Thread.currentThread().getId()+"Thread,"+one.getCoachId()+" order search courseid not equal="+courses+",and param="+courseId);
									}
									continue;
								}
							}else {
								if(log.isDebugEnabled()){
									log.debug(Thread.currentThread().getId()+"Thread,"+one.getCoachId()+" order search courseid is null"+",and param="+courseId);
								}
								continue;
							}
						}
						//3.车型
						if(StringUtil.isNotNullAndNotEmpty(carLevel)&& !carLevel.equals(calid)){
							if(log.isDebugEnabled()){
								log.debug(Thread.currentThread().getId()+"Thread,"+one.getCoachId()+" order search carlevel not equal="+calid+",and param="+carLevel);
							}
							continue;
						}
						//4.出车
						if(wstate!=ReqConstants.COACH_STATUS_ON_WORK) {
							if(log.isDebugEnabled()){
								log.debug(Thread.currentThread().getId()+"Thread,"+one.getCoachId()+" order search coach is not work="+wstate);
							}
							continue;
						}
						//5.城市：暂不过滤
						list.add(one);
					}
					searchPriceFuture.get();
				}
				if(log.isDebugEnabled()){
					log.debug(Thread.currentThread().getId()+"Thread, coach with param:studentId="+studentId+",lge="+lge+",lae="+lae+",examType="+examType+",carLevel="+carLevel+",courseId="+courseId+","+BeanCopy.getFieldList(list,"coachId")+" order search="+(System.currentTimeMillis()-allStart)+",param="+BeanCopy.getFieldList(coachs,"coachId"));
				}
		} catch (Exception e) {
			try {
				log.error(Thread.currentThread().getId()+"Thread, coach with param:studentId="+studentId+",lge="+lge+",lae="+lae+",examType="+examType+",carLevel="+carLevel+",courseId="+courseId+","+BeanCopy.getFieldList(list,"coachId")+" nearby order search failed=" + e.getMessage(), e);
			} catch(Exception ee){}
			//针对全面搜索,不返回异常,返回部分结果
//			result = ReqResult.getFailed();
		}
		return list;
	}
}
