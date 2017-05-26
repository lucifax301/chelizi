package com.lili.logic.service.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.lili.coach.service.CoachService;
import com.lili.coach.vo.CoachVo;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.DateUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ResultCode;
import com.lili.location.service.LocationService;
import com.lili.order.service.CoachClassService;
import com.lili.order.service.UnitPriceService;
import com.lili.order.vo.CoachClassVo;
import com.lili.order.vo.SearchVo;

@SuppressWarnings("unused")
public class SearchClass implements Callable<List<Long>> {
	private Logger log = Logger.getLogger(SearchClass.class);
	
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
	private Date pstart;
	
	public SearchClass(String studentId, String lge, String lae,
		    String examType, String carLevel,
			String courseId,List<Long> coachIds,Date pstart,
			LocationService locationService,CoachClassService coachClassService,
			CoachService coachService,UnitPriceService unitPriceService){
		this.studentId=studentId;
		this.lge=lge;
		this.lae=lae;
	    this.examType=examType;
	    this.carLevel=carLevel;
		this.courseId=courseId;
		this.coachIds=coachIds;
		this.pstart=pstart;
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
				Date pend = DateUtil.dateAfterMinute(pstart, OrderConstant.clztime);
				//4.获取教练班次信息
				if(coachIds!=null && !coachIds.isEmpty()){
					//获取教练所有排班，不管是否有价格
					Map<Long,List<CoachClassVo>> ccmap=coachClassService.queryByCoachDateWitNoPrice(new Date(), coachIds);
					for(Long oneId:coachIds){
						CoachClassVo buz=coachClassService.isCoachIdle(ccmap.get(oneId), null,null,pstart, pend,false);
						if(buz==null){
								list.add(oneId);
						} else {
							if(log.isDebugEnabled()){
								log.debug(Thread.currentThread().getId()+"Thread,"+oneId+" order search coach has class="+buz);
							}
						}
					}
				}		
				if(log.isDebugEnabled()){
					log.debug(Thread.currentThread().getId()+"Thread, class with param:studentId="+studentId+",lge="+lge+",lae="+lae+",examType="+examType+",carLevel="+carLevel+",courseId="+courseId+","+BeanCopy.getFieldList(list,"coachId")+" order search="+(System.currentTimeMillis()-allStart));
				}
		} catch (Exception e) {
			try {
				log.error(Thread.currentThread().getId()+"Thread, class with param:studentId="+studentId+",lge="+lge+",lae="+lae+",examType="+examType+",carLevel="+carLevel+",courseId="+courseId+","+BeanCopy.getFieldList(list,"coachId")+" nearby order search failed=" + e.getMessage(), e);
			} catch(Exception ee){}
			//针对全面搜索,不返回异常,返回部分结果
//			result = ReqResult.getFailed();
		}
		return list;
	}
}
