package com.lili.logic.service.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

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
import com.lili.order.service.OrderService;
import com.lili.order.service.UnitPriceService;
import com.lili.order.vo.CoachClassVo;
import com.lili.order.vo.WaitOrderVo;

@SuppressWarnings("unused")
public class Search5Step implements Callable<List<CoachVo>> {
	private Logger log = Logger.getLogger(Search5Step.class);
	
	
	private LocationService locationService;
	private CoachClassService coachClassService;
	private CoachService coachService;
	private UnitPriceService unitPriceService;
	private OrderService orderService;
	
	private String studentId;
	private String lge;
	private String lae;
    private String examType;
    private String carLevel;
	private String courseId;
	private int searchStart;
	private int searchSize;
	private double distance;
	private List<CoachVo> coachs;
	private Date pstart;
	
	public Search5Step(String studentId, String lge, String lae,
		    String examType, String carLevel,
			String courseId,int searchStart,int searchSize,double distance,List<CoachVo> coachs,Date pstart,
			LocationService locationService,CoachClassService coachClassService,
			CoachService coachService,UnitPriceService unitPriceService,OrderService orderService){
		this.studentId=studentId;
		this.lge=lge;
		this.lae=lae;
	    this.examType=examType;
	    this.carLevel=carLevel;
		this.courseId=courseId;
		this.searchStart=searchStart;
		this.searchSize=searchSize;
		this.distance=distance;
		this.coachs=coachs;
		this.pstart=pstart;
		this.locationService=locationService;
		this.coachClassService=coachClassService;
		this.coachService=coachService;
		this.unitPriceService=unitPriceService;
		this.orderService=orderService;
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
			// 默认现在
			Date pend = DateUtil.dateAfterMinute(pstart, OrderConstant.clztime);
			// 循环取教练
			Set<Long> idSet = new HashSet<Long>();
			long start=0;
				//1.获取位置
				start=System.currentTimeMillis();
//				List<CoachVo> coachs = locationService.getNearBy(search);
//				List<Long> tempIds=BeanCopy.getFieldList(coachs, "coachId");
//				if(log.isDebugEnabled()){
//					log.debug(Thread.currentThread().getId()+"Thread,"+tempIds+" order search location="+(System.currentTimeMillis()-start)+" with lge="+lge+",lae="+lae);
//				}
				List<Long> tempIds=BeanCopy.getFieldList(coachs, "coachId");
				//2.获取教练信息：含档位，汽车（等级），科目，教练出车状态等过滤信息
				start=System.currentTimeMillis();
				//必须复制，不能直接等于，否则导致后面问题
				List<Long> coachIds=BeanCopy.getFieldList(coachs, "coachId");
				/*根据需求先屏蔽在线状态的过滤
//				List<Long> coachIds=new ArrayList<Long>();				//教练在线状态
//				Map<Long,Boolean> online=coachService.isOnlineByIds(tempIds);
//				for(Long one:tempIds){
//					Boolean ol=online.get(one);
//					if(ol!=null && ol.booleanValue()){
//						coachIds.add(one);
//					} else {
//						if(log.isDebugEnabled()){
//							log.debug(Thread.currentThread().getId()+"Thread,"+one+" order search online="+online.get(one));
//						}
//					}
//				}
//				if(log.isDebugEnabled()){
//					log.debug(Thread.currentThread().getId()+"Thread,"+coachIds+"order search online="+(System.currentTimeMillis()-start));
//				} 
 */
				start=System.currentTimeMillis();
				@SuppressWarnings("unchecked")
				List<CoachVo> coachList=(List<CoachVo>)coachService.getCoachsByIds(coachIds).getResult().get(ResultCode.RESULTKEY.DATA);
				Map<Long,CoachVo> coachMap=new HashMap<Long,CoachVo>();
				coachIds.clear();
				if(coachList!=null){
					BeanCopy.copyList(coachs, coachList, BeanCopy.COPY2NULL, "coachId");
					for(CoachVo one:coachList){
						String dltype=String.valueOf(one.getDriveType()); 
						String calid=String.valueOf(one.getCarLevel());
						String courses=one.getCourses();
						int wstate=one.getWstate();
						
						
						//0.接单距离
						if(one.getAcceptOrderDis()<one.getDistance()){
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
							
							if(!courseId.equals("5")&&!courseId.equals("15")&&!courseId.equals("2")&&!courseId.equals("12")&&!courseId.equals("4")&&!courseId.equals("14")){//不是陪驾
								if(one.getIsImport()!=1){//注册教练
									log.debug(Thread.currentThread().getId()+"Thread,"+one.getCoachId()+" is outside coach , can't teach lesson"+courseId);
									continue;
								}
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
						
						//检查教练是否有为评价单
//				        WaitOrderVo waitOrderVo = orderService.getCoachWait(one.getCoachId(), null);
//				        if(waitOrderVo != null && waitOrderVo.getWaitComment() != null){
//				        	continue;
//				        }
						
						//5.城市：暂不过滤
						coachIds.add(one.getCoachId());
						coachMap.put(one.getCoachId(), one);
					}
				}
				if(log.isDebugEnabled()){
					log.debug(Thread.currentThread().getId()+"Thread,"+coachIds+"order search coach="+(System.currentTimeMillis()-start));
				}
//				//3.获取教练车信息
//				start=System.currentTimeMillis();
//				
//				log.debug("order search car="+(System.currentTimeMillis()-start));
				//4.获取教练班次信息
				start=System.currentTimeMillis();
				if(idSet!=null&&!idSet.isEmpty()){
					coachIds.removeAll(idSet);
					if(log.isDebugEnabled()){
						log.debug(Thread.currentThread().getId()+"Thread,"+idSet+" remove because of duplication");
					}
				}
				tempIds.clear();
				log.debug(Thread.currentThread().getId()+"Thread,"+idSet+"================ before search bookinfo for near coach");
				if(!coachIds.isEmpty()){
					Calendar calendar=Calendar.getInstance();
					calendar.setTime(new Date());
					calendar.add(Calendar.HOUR_OF_DAY, 1);
					//获取教练的所有排班
//					Map<Long,List<CoachClassVo>> ccmap=coachClassService.queryByCoachDateWithPrice(new Date(),  coachIds);
					Map<Long,List<CoachClassVo>> ccmap=coachClassService.queryByCoachDateWithPrice(new Date(),  coachIds,3);
					for(Long oneId:coachIds){
						CoachClassVo buz=coachClassService.isCoachIdle(ccmap.get(oneId), null,null,pstart, pend,false);
						if(idSet.contains(oneId)) {
							if(log.isDebugEnabled()){
								log.debug(Thread.currentThread().getId()+"Thread,"+oneId+" order search coach oready in list.");
							}
						} else if(buz==null){
								list.add(coachMap.get(oneId));
								tempIds.add(oneId);
								idSet.add(oneId);
						} else {
							if(log.isDebugEnabled()){
								log.debug(Thread.currentThread().getId()+"Thread,"+oneId+" order search coach has class="+buz);
							}
							//add by devil 20161016
							//之后60分钟内有排班也显示出来
//							list.add(coachMap.get(oneId));
//							tempIds.add(oneId);
//							idSet.add(oneId);
						}
						java.util.Date now=new java.util.Date();
						//add by devil 20161016
						if(ccmap.get(oneId)!=null){//教练有排班
							List<CoachClassVo> ccvlist=ccmap.get(oneId);
							for(CoachClassVo ccv:ccvlist){
								if(ccv.getCtype()!=null&&ccv.getCtype()==3&& ccv.getPrice()!=null&&ccv.getPrice()>0)
								//再过滤一下排班是否符合规则？
								//Joseph_Mok 20161112 time adjustment
								//1hour 15 minute
									if(ccv.getCend().before(now)) continue;
									if (ccv.getCstart().getTime() - new Date().getTime() < (60 * 60 * 1000+900000)) {
										if(ccv.getBookNum()<ccv.getMaxNum()){	
											coachMap.get(oneId).setHasClass(1);
											list.add(coachMap.get(oneId));
											tempIds.add(oneId);
											idSet.add(oneId);
											break;
										}
									} else {
										coachMap.get(oneId).setHasClass(0);
									}
								
							}
						}else{
							coachMap.get(oneId).setHasClass(0);
						}
					}
				}
				log.debug(Thread.currentThread().getId()+"Thread,"+idSet+"====================== end search bookinfo for near coach");
				if(log.isDebugEnabled()){
					log.debug(Thread.currentThread().getId()+"Thread,"+tempIds+"order search class="+(System.currentTimeMillis()-start));
				}
//				//5.获取教练评价
//				start=System.currentTimeMillis();
//				Map<Long,Integer> scores=coachScoreService.queryByCoachIds(coachIds);
//				log.debug("order search comment="+(System.currentTimeMillis()-start));
				//6.获取教练价格
				start=System.currentTimeMillis();
				for(Long id:tempIds){
					CoachVo one=coachMap.get(id);
					try {
						int cityId=one.getCityId()!=null?one.getCityId():0;
						int cid=Integer.valueOf(courseId);
						int colid=one.getCoachLevel();
						int calid=one.getCarLevel();
						int dftype=Integer.valueOf(examType);
						int price=unitPriceService.getCommonCoachPrice(pstart, cityId, cid, colid, calid,dftype);
						one.setPrice(price);
					} catch(Exception e){
						//异常后使用默认价格
						if(one!=null){
							one.setPrice(OrderConstant.defaultPrice);
						}
						log.error(one+" use default value because of Exception:"+e.getMessage(), e);
					}
				}
				if(log.isDebugEnabled()){
					log.debug(Thread.currentThread().getId()+"Thread,order search price="+(System.currentTimeMillis()-start));
				}
//				//7.获取命中教练
//				start=System.currentTimeMillis();
//				
//				log.debug("order search result="+(System.currentTimeMillis()-start));				
				if(log.isDebugEnabled()){
					log.debug(Thread.currentThread().getId()+"Thread, last with param:searchStart="+searchStart+",searchSize="+searchSize+",studentId="+studentId+",lge="+lge+",lae="+lae+",examType="+examType+",carLevel="+carLevel+",courseId="+courseId+","+BeanCopy.getFieldList(list,"coachId")+" order search="+(System.currentTimeMillis()-allStart));
				}
		} catch (Exception e) {
			try {
				log.error(Thread.currentThread().getId()+"Thread, with param:searchStart="+searchStart+",searchSize="+searchSize+",studentId="+studentId+",lge="+lge+",lae="+lae+",examType="+examType+",carLevel="+carLevel+",courseId="+courseId+","+BeanCopy.getFieldList(list,"coachId")+" nearby order search failed=" + e.getMessage(), e);
			} catch(Exception ee){}
			//针对全面搜索,不返回异常,返回部分结果
//			result = ReqResult.getFailed();
		}
		return list;
	}
}
