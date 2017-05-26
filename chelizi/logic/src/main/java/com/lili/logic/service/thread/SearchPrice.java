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
public class SearchPrice implements Callable<List<CoachVo>> {
	private Logger log = Logger.getLogger(SearchPrice.class);
	
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
	private List<CoachVo> coachList;
	private Date pstart;
	
	ExecutorService threadPool = Executors.newFixedThreadPool(3);
	
	public SearchPrice(String studentId, String lge, String lae,
		    String examType, String carLevel,
			String courseId,List<CoachVo> coachList,Date pstart,
			LocationService locationService,CoachClassService coachClassService,
			CoachService coachService,UnitPriceService unitPriceService){
		this.studentId=studentId;
		this.lge=lge;
		this.lae=lae;
	    this.examType=examType;
	    this.carLevel=carLevel;
		this.courseId=courseId;
		this.coachList=coachList;
		this.pstart=pstart;
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
				//6.获取教练价格
				for(CoachVo one:coachList){
					try {
						int cityId=one.getCityId();
						int cid=Integer.valueOf(courseId);
						int colid=one.getCoachLevel();
						int calid=one.getCarLevel();
						int dftype=Integer.valueOf(examType);
						int price=unitPriceService.getCommonCoachPrice(pstart, cityId, cid, colid, calid,dftype);
					    one.setPrice(price);
					}catch(Exception e){
						//异常后使用默认价格
						if(one!=null){
							one.setPerformance(OrderConstant.defaultPrice);
						}
						log.error(one+" use default value because of Exception:"+e.getMessage(), e);
					}
				}		
				if(log.isDebugEnabled()){
					log.debug(Thread.currentThread().getId()+"Thread, price with param:studentId="+studentId+",lge="+lge+",lae="+lae+",examType="+examType+",carLevel="+carLevel+",courseId="+courseId+","+BeanCopy.getFieldList(list,"coachId")+" order search="+(System.currentTimeMillis()-allStart));
				}
		} catch (Exception e) {
			try {
				//异常，使用默认价钱
				log.error(Thread.currentThread().getId()+"Thread, price with param:studentId="+studentId+",lge="+lge+",lae="+lae+",examType="+examType+",carLevel="+carLevel+",courseId="+courseId+","+BeanCopy.getFieldList(list,"coachId")+" nearby order search failed=" + e.getMessage(), e);
			} catch(Exception ee){}
			//针对全面搜索,不返回异常,返回部分结果
//			result = ReqResult.getFailed();
		}
		return list;
	}
}
