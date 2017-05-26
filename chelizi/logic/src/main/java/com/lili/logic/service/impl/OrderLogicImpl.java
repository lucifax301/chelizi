package com.lili.logic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.redisson.RedissonClient;
import org.redisson.core.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.lili.coach.dto.Car;
import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CarManager;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.manager.CourseSManager;
import com.lili.coach.service.CoachService;
import com.lili.coach.vo.CoachVo;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.OrderConstant;
import com.lili.common.constant.RedisExpireConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.DateUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.VersionUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.location.service.LocationService;
import com.lili.logic.service.OrderLogic;
import com.lili.logic.service.thread.Search3Step;
import com.lili.logic.service.thread.Search5Step;
import com.lili.order.service.CoachClassService;
import com.lili.order.service.CoachCommentService;
import com.lili.order.service.CoachScoreService;
import com.lili.order.service.CoachStatisticService;
import com.lili.order.service.CommentTagService;
import com.lili.order.service.OrderService;
import com.lili.order.service.SkillRelationService;
import com.lili.order.service.StuCommentService;
import com.lili.order.service.StudentStatisticService;
import com.lili.order.service.UnitPriceService;
import com.lili.order.vo.CoachClassQuery;
import com.lili.order.vo.CoachClassVo;
import com.lili.order.vo.CoachScoreQuery;
import com.lili.order.vo.CoachScoreVo;
import com.lili.order.vo.CoachStatisticVo;
import com.lili.order.vo.CommentTagQuery;
import com.lili.order.vo.CommentTagVo;
import com.lili.order.vo.DateStatisticVo;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.order.vo.SearchVo;
import com.lili.order.vo.SkillRelationQuery;
import com.lili.order.vo.SkillRelationVo;
import com.lili.order.vo.StuOrderCommentVo;
import com.lili.order.vo.StudentStatisticQuery;
import com.lili.order.vo.StudentStatisticVo;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;


public class OrderLogicImpl implements OrderLogic {
	
	private Logger log = Logger.getLogger(OrderLogicImpl.class);
	// 每次查询的分页大小
	@Value("${search.findSize}")
	private int realSize=15;
	// 每次教练最少显示数
	@Value("${search.minSize}")
	private int minSize=10;
	// 每次教练最多显示数
	@Value("${search.maxSize}")
	private int maxSize=20;
	// 每次教练查找次数
	@Value("${search.maxTimes}")
	private int maxTimes=3;
	//每次教练查的最大距离
	@Value("${search.maxDistance}")
	private double maxDistance=7;
	@Value("${search.strategy}")
	private int strategy=5;
	@Value("${search.maxThread}")
	private int maxThread=100;
	
	@Autowired
	private LocationService locationService;

	@Autowired
	private OrderService orderService;
	@Autowired
	private CoachClassService coachClassService;
	@Autowired
	private CoachManager coachManager;
	@Autowired
	private CarManager carManager;
	@Autowired
	private CoachService coachService;
	@Autowired
	StuCommentService stuCommentService;
	@Autowired
	SkillRelationService skillRelationService;
	@Autowired
	CoachCommentService coachCommentService;
	@Autowired
	CommentTagService commentTageService;
	@Resource(name="orderProducer")
	private DefaultMQProducer orderProducer;
	@Autowired
	private CoachStatisticService coachStatisticService; 
	@Autowired
	private CoachScoreService coachScoreService;
	@Autowired
	private UnitPriceService unitPriceService;
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	private StudentManager studentManager;
	@Autowired
	private CourseSManager courseSManager;
	@Autowired
	private StudentStatisticService studentStatisticService;
	//线程池
	ExecutorService threadPool = Executors.newFixedThreadPool(maxThread*maxTimes);
	//分布式锁
	@Autowired
	private RedissonClient redissonClient;
	
	public static final String LOCKPRE="rdlock_";
	
	public ReqResult reservation(String studentId, String lge, String lae,
			String coachStatus, String examType, String carLevel,
			String courseId, String pageNo, String pageSize, String tokenId) {
		ReqResult result = ReqResult.getSuccess();
		long allStart=System.currentTimeMillis();
		List<CoachVo> list = new ArrayList<CoachVo>();
		Map<Integer,FutureTask<List<CoachVo>>> map=new HashMap<Integer,FutureTask<List<CoachVo>>>();
		Date pstart=new Date();
		try {
			//获取所有附近教练
			SearchVo search = new SearchVo();
			search.setLae(Double.valueOf(lae));
			search.setLge(Double.valueOf(lge));
			search.setPageSkip(0);
			search.setPageLimit(realSize*maxTimes);
			search.setDistance(maxDistance);
			List<CoachVo> coachs = locationService.getNearBy(search);
			if(log.isDebugEnabled()){
				log.debug(Thread.currentThread().getId()+"Thread, "+BeanCopy.getFieldList(coachs, "coachId")+"order search location="+(System.currentTimeMillis()-allStart)+" with lge="+lge+",lae="+lae+",distance="+maxDistance+",limit="+search.getPageLimit());
			}
			if(coachs!=null&&coachs.size()>0) {
				for(int i=0;i<maxTimes;i++){
					try {
						int start=realSize*i;
						int end=realSize*(i+1);
						List<CoachVo> coachList=new ArrayList<CoachVo>();
						if(coachs.size()>start){
						    end = end > coachs.size() ? coachs.size() : end;
							coachList=coachs.subList(start, end);
						} else {
							break;
						}
						int searchStart=realSize*i;
						int searchSize=realSize*(i+1);
						if((strategy==3)|| (strategy==4 && i%2==0) || (strategy==6 && i%2==1)) {
							Search3Step search3Step=new Search3Step(studentId, lge, lae, examType, carLevel, courseId, searchStart, searchSize, maxDistance, coachList,pstart, threadPool, locationService, coachClassService, coachService, unitPriceService);  
							FutureTask<List<CoachVo>> future = new FutureTask<List<CoachVo>>(search3Step);
							threadPool.execute(future);
							map.put(i, future);
						} else {
							Search5Step search5Step=new Search5Step(studentId, lge, lae, examType, carLevel, courseId, searchStart, searchSize, maxDistance,coachList,pstart, locationService, coachClassService, coachService, unitPriceService,orderService);  
							FutureTask<List<CoachVo>> future = new FutureTask<List<CoachVo>>(search5Step);
							threadPool.execute(future);
							map.put(i, future);
						}
					} catch(Exception e){
						log.error(i+" order search start Exception:"+e.getMessage(),e);
					}
				}
				for(int i=0;i<maxTimes;i++){
					FutureTask<List<CoachVo>> future=map.get(i);
					if(future!=null){
						list.addAll(future.get());
					}
				}
			}
			if(log.isInfoEnabled()){
				log.info(Thread.currentThread().getId()+"Thread,all with param:"+BeanCopy.getFieldList(list,"coachId")+"studentId="+studentId+",lge="+lge+",lae="+lae+",examType="+examType+",carLevel="+carLevel+",courseId="+courseId+", order search="+(System.currentTimeMillis()-allStart));
			}
			
			//add by devil 当天有排班的教练
			//coachClassService.queryByCoachDate(pstart,);
			
			//至少10个不可调小
			if(list.size()>maxSize&& list.size()>10){
				List<CoachVo> r=list.subList(0, maxSize);
				result.setData(r);
				if(log.isInfoEnabled()){
					log.info("last all with maxSize="+maxSize+",and realSize="+list.size()+", last size="+r.size());
				}
			} else {
				result.setData(list);
			}
		} catch(Exception e){
			//不定目标查找只打错误日志，不返回错误码，有部分结果亦返回
			try{
				log.error(BeanCopy.getFieldList(list,"coachId")+" order search Exception:"+e.getMessage(),e);
			} catch(Exception ex){}
		}
		return result;
	}

	
	public ReqResult acceptOrder(String orderId,String coachId,
			String studentId,String tokenId) {
		ReqResult result = ReqResult.getSuccess();
		try {
			//利用redis考虑抢单的并发，后续考虑
			//获得订单
			OrderVo orderVo =orderService.queryOrderById(orderId, new OrderQuery());
			
			if(orderVo==null||!String.valueOf(orderVo.getCoachId()).equals(coachId)||!String.valueOf(orderVo.getStudentId()).equals(studentId)){
				log.error(orderId+" is not old exists="+orderVo+", so can't accept.");
				result.setCode(ResultCode.ERRORCODE.ORDER_NOT_OPERATE);
				return result;
			}
			if(orderVo.getOrderState()!=OrderConstant.ORDERSTATE.ACCEPTORDER || orderVo.getAtime()!=null){
				log.error(orderId+" is not accept state="+orderVo+", so can't accept.");
				result.setCode(ResultCode.ERRORCODE.ORDER_HASACCEPT);
				return result;
			}
			orderVo.setOrderState(OrderConstant.ORDERSTATE.ACCEPTORDER);
//			StateManager stateManager=managerFactory.produce(orderVo);
			String code=orderService.handleAccept(orderVo);
			result.setCode(code);
			/**自动接单，不需要超时
			//接单成功
			if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
				//接单成功需要删除超时定时任务
				AcceptTimeOutJob dynamicJob=new AcceptTimeOutJob(orderId);
				dynamicQuartz.removeJob(dynamicJob);
			}
			*/
		} catch (Exception e) {
			result = ReqResult.getFailed();
			log.error(orderId+" acceptOrder Exception:"+e.getMessage(),e);
		}
		return result;
	}
	
	@Override
	public ReqResult addOrder(OrderVo orderVo,  String tokenId) {
		ReqResult result = ReqResult.getSuccess();
		RLock lock=null;
		try {
			//现约订单锁定教练即可，一个教练同一时间只能接一个单.
			lock=redissonClient.getLock(LOCKPRE+"coachid-"+orderVo.getCoachId());
			//1s等待，10s超时，防止死锁
			boolean hasLock=lock.tryLock(1,10,TimeUnit.SECONDS);
			//锁获取成功，则往下走
			if(hasLock) {
				//添加orderId
				String orderId=orderVo.getOrderId();
				if(orderId==null|| orderId.length()<32) {
					orderId=StringUtil.getOrderId();
					orderVo.setOrderId(orderId);
				}
				//订单状态
				orderVo.setOrderState(OrderConstant.ORDERSTATE.GIVEORDER);
				orderVo.setOtype(OrderConstant.OTYPE.NOWORDER);
	//			StateManager stateManager=managerFactory.produce(orderVo);
				String code=orderService.handlePlaceNow(orderVo);
				result.setCode(code);
				/**自动接单，不需要超时
				//下单成功
				if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
					result.setData(orderId);
					//订单成功需要增加超时定时任务
					AcceptTimeOutJob dynamicJob=new AcceptTimeOutJob(orderId);
					dynamicQuartz.addJob(dynamicJob);
				}
				**/
			//锁获取失败，返回错误
			} else {
				log.error(orderVo.getCoachId()+",studentId="+orderVo.getStudentId()+" add order LOCK ERROR.");
				result.setCode(ResultCode.ERRORCODE.ORDER_LOCK);
				return result;
			}
		} catch (Exception e) {
			result = ReqResult.getFailed();
			log.error(orderVo.getCoachId()+",studentId="+orderVo.getStudentId()+" add order exception:"+e.getMessage(),e);
		//最终释放锁
		} finally {
			if(lock!=null) {
				try {
					lock.unlock();
				} catch(Exception e){}
			}
		}
		return result;
	}

	/**
	 * 订单取消接口
	 * 
	 * @param orderId
	 *            ：订单id
	 * @param retype
	 *            :取消原因类型：1.懒得等，2.订单错误，2.我时间冲突 3.对方态度不好, 4.对方有事
	 * @param reseaon
	 *            ：原因描述
	 * @param ucancel
	 *            ：取消发起人类型：1代表教练，2代表学员
	 * @param tokenId
	 * @return data=0：取消成功 data=1:取消失败 data=2:退款处理失败
	 */
	public ReqResult cancelOrder(String orderId, String retype, String reseaon,
			String userId, String ucancel, String tokenId) {
		ReqResult result = ReqResult.getSuccess();
		try {
			Integer intRetype = null;
			Integer intUcancel = null;
			if (StringUtil.isNotNullAndNotEmpty(retype)) {
				intRetype = Integer.valueOf(retype);
			}
			if (StringUtil.isNotNullAndNotEmpty(ucancel)) {
				intUcancel = Integer.valueOf(ucancel);
			}
			String code = orderService.cancelOrder(orderId, intRetype, reseaon,
					intUcancel, tokenId,1);
			result.setCode(code);
		} catch (Exception e) {
			result = ReqResult.getFailed();
			log.error(orderId+" cancel Exception:"+e.getMessage(),e);
		}
		return result;
	}

	/**
	 * 教练拒单
	 * 
	 * @param orderId
	 *            ：订单号
	 * @param reseaon
	 *            ：拒单原因
	 * @param tokenId
	 * @return
	 */
	public ReqResult refuseOrder(String orderId, String coachId,
			String reseaon, String tokenId) {
		ReqResult result = ReqResult.getSuccess();
		try {
			Integer intRetype = OrderConstant.RETYPE.COACHREFUSE;
			Integer intUcancel = OrderConstant.USETYPE.COACH;
			String code = orderService.cancelOrder(orderId, intRetype, reseaon,
					intUcancel, tokenId,0);
			result.setCode(code);
		} catch (Exception e) {
			result = ReqResult.getFailed();
			log.error(orderId+" refuse Exception:"+e.getMessage(),e);
		}
		return result;
	}

	/**
	 * 获取续课订单单价
	 * 
	 * @param orderId
	 *            ：被续课的订单号
	 * @param clzNum
	 *            :续课课程数量，可为空
	 * @param tokenId
	 * @return data=每节课单价
	 */
	public ReqResult continuePrice(String orderId, String studentId,
			String clzNum, String tokenId) {

		ReqResult result = ReqResult.getSuccess();
		try {
			// 1.获取原始订单
			OrderVo o = orderService.queryOrderById(orderId, new OrderQuery());
			if (o == null) {
				result.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
				return result;
			}
			long coachId = o.getCoachId();
			Coach coach = coachManager.getCoachInfo(coachId);
			// 价格获取,首先获取汽车类型
			Car car = carManager.getCarLevelByCoachId(coachId);// 从汽车获取
			int calid = car.getCarLevel();
			int cityId = coach.getCityId();
			int colid = coach.getCoachLevel();
			int price = orderService.getCoachPrice(o.getPend(), o.getPend(),
					cityId, o.getCourseId(), colid, calid);
			result.setData(price);
			// 2.结合原始定的那和最近时间计算价格
		} catch (Exception e) {
			result = ReqResult.getFailed();
			log.error(orderId+" continuePrice Exception:"+e.getMessage(),e);
		}
		return result;
	}

	/**
	 * 续课
	 * 
	 * @param orderId
	 *            ：原单
	 * @param clzNum
	 *            ：续课节数
	 * @param price
	 *            ：续课总价
	 * @param tokenId
	 * @return
	 */
	public ReqResult continueOrder(String orderId, String studentId,
			String clzNum, String price, String tokenId) {
		ReqResult result = ReqResult.getSuccess();
		try {
			// 参数判断
			if (StringUtil.isEmptyOrWhitespaceOnly(orderId)
					|| StringUtil.isEmptyOrWhitespaceOnly(price)
					|| StringUtil.isEmptyOrWhitespaceOnly(clzNum)) {
				result.setCode(ResultCode.ERRORCODE.PARAMERROR);
				return result;
			}
			// 1.获取原始订单
			OrderVo o = orderService.queryOrderById(orderId, new OrderQuery());
			if (o == null) {
				result.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
				return result;
			}
			// 2.使用原始订单信息，加新信息新增订单
			String neworderId = StringUtil.getOrderId();
			o.setOrderId(neworderId);
			o.setPrice(Integer.valueOf(price));
			o.setCoorder(orderId);
			o.setPayState(OrderConstant.PAYSTATE.WAITPAY);
			o.setOrderState(OrderConstant.ORDERSTATE.GIVEORDER);
			o.setOtype(OrderConstant.OTYPE.CONTINUEORDER);
			o.setPstart(o.getPend());
			o.setPend(DateUtil.dateAfterMinute(o.getPend(),
					OrderConstant.clztime * Integer.valueOf(clzNum)));
			o.setClzNum(Integer.valueOf(clzNum));
			o.setGtime(new Date());
			o.setAtime(null);
			orderService.addOrder(o);
			result.setData(neworderId);
			// 待实现：推送给教练
		} catch (Exception e) {
			result = ReqResult.getFailed();
			log.error(orderId+" continueOrder Exception:"+e.getMessage(),e);
		}
		return result;
	}

	public ReqResult bookOrder(OrderVo orderVo,  String tokenId) throws Exception {
		ReqResult result = ReqResult.getSuccess();
		// 参数判断
		if (orderVo.getOrderId()==null
				|| orderVo.getCourseId()==null
				|| orderVo.getCcid()==null||orderVo.getCcid()==0
				|| orderVo.getPrice()==null) {
			result.setCode(ResultCode.ERRORCODE.PARAMERROR);
		}
		RLock lock=null;
		try {
			//预约订单锁定学员即可，一个学员同一时间只能参加一个排班.
			lock=redissonClient.getLock(LOCKPRE+"studentId-"+orderVo.getStudentId());
			//1s等待，10s超时，防止死锁
			boolean hasLock=lock.tryLock(1,10,TimeUnit.SECONDS);
			//锁获取成功，则往下走
			if(hasLock) {
				//添加orderId,只有没id时才添加
				String orderId = orderVo.getOrderId();
				if(orderId==null ||orderId.length()<32){
					orderId=StringUtil.getOrderId();
					orderVo.setOrderId(orderId);
				}
				//订单状态
				orderVo.setOrderState(OrderConstant.ORDERSTATE.GIVEORDER);
				orderVo.setOtype(OrderConstant.OTYPE.BOOKORDER);
	//			StateManager stateManager=managerFactory.produce(orderVo);
				String code=orderService.bookOrder(orderVo);
				result.setCode(code);
				result.setData(orderId);
			//锁获取失败，返回错误
			} else {
				log.error(orderVo.getCoachId()+",studentId="+orderVo.getStudentId()+" bookOrder Lock ERROR.");
				result.setCode(ResultCode.ERRORCODE.ORDER_LOCK);
				return result;
			}
		} catch (Exception e) {
			result = ReqResult.getFailed();
			log.error(orderVo.getCoachId()+",studentId="+orderVo.getStudentId()+" bookOrder Exception:"+e.getMessage(),e);
		//最终释放锁
		} finally {
			if(lock!=null) {
				try {
					lock.unlock();
				} catch(Exception e){}
			}
		} 
		return result;
	}

	/**
	 * 学生评价教练
	 * 
	 * @param coachId
	 * @param studentId
	 * @param orderId
	 * @param score
	 * @param tag
	 *            ：评价标签，来自评价表浅表t_commenttag
	 * @param tokenId
	 * @return
	 */
	public ReqResult stuCommentCoach(String coachId, String studentId,
			String orderId, String score, String tagId, String oneWord,String tokenId, String anonymity)
			throws Exception {
		ReqResult result = ReqResult.getSuccess();
		try {
			String code=orderService.stuCommentCoach(coachId, studentId, orderId, score, tagId, oneWord, anonymity);
			result.setCode(code);
		} catch (Exception e) {
			result.setCode(ResultCode.ERRORCODE.FAILED);
			log.error(coachId+",studentId="+studentId+" stuCommentCoach Exception:"+e.getMessage(),e);
		}
		return result;
	}

	/**
	 * 教练给学生评分
	 * 
	 * @param coachId
	 * @param studentId
	 * @param orderId
	 * @param scroces
	 *            ：<评价标签，得分>,注意其中评价标签来自评价表浅表t_commenttag
	 * @param tokenId
	 * @return
	 * @throws Exception
	 */
	public ReqResult coachCommentStu(String coachId, String studentId,
			String orderId, Map<Integer, Integer> scores, String oneWord, String tokenId)
			throws Exception {
		ReqResult result = ReqResult.getSuccess();
		try {
			String code=orderService.coachCommentStu(coachId, studentId, orderId, scores, oneWord);
			result.setCode(code);
		} catch (Exception e) {
			result.setCode(ResultCode.ERRORCODE.FAILED);
			log.error(coachId+",studentId="+studentId+" coachCommentStu Exception:"+e.getMessage(),e);
		}	
		return result;
	}
	/**
	 * 获取评分被评分对象的标签
	 * @param courseId：
	 *  1.获取教师评价标签是为平分数，此时可为空
	 *  2.获取学生评价标签时，此参数为评价科目，此时不可为空
	 * @param userType:获取被评价人的用户类型，务必注意不是自己，而是被自己评价的对方的用户类型 
	 * @param tokenId
	 * @return
	 * @throws Exception
	 */
	public ReqResult getCommentTag(String userType,String courseId, String tokenId) throws Exception {
		ReqResult result = ReqResult.getSuccess();
		try {
		CommentTagVo vo=new CommentTagVo();
		
		if(StringUtil.isNotNullAndNotEmpty(courseId)){
			vo.setCourseId(Integer.valueOf(courseId));
		}
		vo.setType(Integer.valueOf(userType));
		CommentTagQuery query=new CommentTagQuery();
		query.setPageSize(500);
		List<CommentTagVo> list=commentTageService.queryByObjectAnd(vo, query);
		result.setData(list);
		} catch (Exception e) {
			result = ReqResult.getFailed();
			log.error(userType+",courseId="+courseId+" getCommentTag Exception:"+e.getMessage(),e);
		}
		return result;
	}
	/**
	 * 获取学生的技能标签
	 * @param courseId：课程，多个需以逗号分割
	 * @param subjectId：科目，多个需以逗号分割
	 * @param cityId：城市id，暂时可以为null
	 * @param dftype：档位信息，暂时可以为null
	 * @return
	 * @throws Exception
	 */
	public ReqResult getStudentTag(String courseId, String subjectId,Integer cityId,Integer dftype, String v) throws Exception {
		ReqResult result = ReqResult.getSuccess();
		try {
				List<CommentTagVo> list=getStuCommentTag(courseId, subjectId, cityId, dftype,  v);
			    result.setData(list);
		} catch (Exception e) {
			result = ReqResult.getFailed();
			log.error(",courseId="+courseId+",subjectId="+subjectId+",cityId="+cityId+",dftype="+dftype+" getStudentTag Exception:"+e.getMessage(),e);
		}
		return result;
	}
	private List<CommentTagVo> getStuCommentTag(String courseId, String subjectId,Integer cityId,Integer dftype, String v) throws Exception{
		//1.获取ctid
		SkillRelationVo search=new SkillRelationVo();
		SkillRelationQuery skillRelationQuery=new SkillRelationQuery();
		String groupby="";
		if(StringUtil.isNotNullAndNotEmpty(subjectId)){
			//获取技能点所属的科目
			if(subjectId.lastIndexOf(",")>0){
				groupby+=" and subjectId in ("+subjectId+") ";
			} else {
				search.setSubjectId(Integer.valueOf(subjectId));
			}
		}
		if(StringUtil.isNotNullAndNotEmpty(courseId)){
			if(courseId.lastIndexOf(",")>0){
				groupby+=" and courseId in ("+courseId+") ";
			} else {
				search.setCourseId(Integer.valueOf(courseId));
			}
		}
		search.setCityId(cityId);
		skillRelationQuery.setPageSize(500);
		skillRelationQuery.setGroupBy(groupby);
		List<SkillRelationVo> sr=skillRelationService.queryByObjectAnd(search, skillRelationQuery);
		String idin="and ctid in (";
		int i=0;
		for(;i<sr.size()-1;i++){
			idin+=sr.get(i).getCtid()+",";
		}
		idin+=sr.get(i).getCtid()+")";
		//2.获取标签
		CommentTagVo vo=new CommentTagVo();
		vo.setCourseId(dftype);
		vo.setType(OrderConstant.USETYPE.STUDENT);
		CommentTagQuery query=new CommentTagQuery();
		query.setPageSize(500);
		query.setGroupBy(idin);
		query.setorderBy("order by ctid asc");
		List<CommentTagVo> list=commentTageService.queryByObjectAnd(vo, query);
		Map<Integer,SkillRelationVo> map=BeanCopy.getFromList(sr, "ctid");
		for(CommentTagVo one:list){
			one.setCourseId(map.get(one.getCtid()).getCourseId());
			one.setSubjectId(map.get(one.getCtid()).getSubjectId());
		}
		//1.8.1增加教练评价学员标签，标签类型为3
		if((null != v && !"".equals(v) && (VersionUtil.compareVersion(v, "1.8.0") > 0))) { 
			CommentTagVo vo2=new CommentTagVo();
			vo2.setCourseId(1);
			vo2.setType(3);
			CommentTagQuery query2=new CommentTagQuery();
			query2.setPageSize(500);
			query2.setorderBy("order by ctid asc");
			List<CommentTagVo> list2=commentTageService.queryByObjectAnd(vo2, query2);
			if(list2 != null) {
				for(CommentTagVo commentTagVo : list2){
					list.add(commentTagVo);
				}
			}
		}
		if(log.isDebugEnabled()){
			log.debug("courseId="+courseId+", subjectId="+subjectId+",cityId="+cityId+",dftype="+dftype+",result="+list);
		}
		return list;
	}
	/**
	 * 获取学生技能点综合评分
	 * @param studentId
	 * @param subjectId:科目id，可传null
	 * @param courseId:课程id，可传null
	 * @param sort:分类方式：1代表以课程分类，其他代表以科目分类
	 * @return
	 * @throws Exception
	 */
	public ReqResult getStudentScore(String studentId,String subjectId,String courseId,String sort,Integer dltype) throws Exception{
		ReqResult result = ReqResult.getSuccess();
		// 1.不需要登录判断，因为教练亦可查看		
		//获取技能项目
		try {
			List<CommentTagVo> list=this.getStuCommentTag(courseId, subjectId, null, dltype, null);
			StudentStatisticQuery sq=new StudentStatisticQuery();
			String idin="and ctid in (";
			int i=0;
			for(;i<list.size()-1;i++){
				idin+=list.get(i).getCtid()+",";
			}
			idin+=list.get(i).getCtid()+")";
			sq.setGroupBy(idin);
			sq.setorderBy("order by ctid asc");
			StudentStatisticVo vo=new StudentStatisticVo();
			vo.setStudentId(Long.valueOf(studentId));
			List<StudentStatisticVo> ssList=studentStatisticService.queryByObjectAnd(vo, sq);
			Map<Integer,StudentStatisticVo> map=new HashMap<Integer,StudentStatisticVo>();
			for(int j=0;ssList!=null && j<ssList.size();j++){
				map.put(ssList.get(j).getCtid(), ssList.get(j));
			}
			Map<Integer, StuOrderCommentVo> resultMap=new HashMap<Integer, StuOrderCommentVo>();
			List<StuOrderCommentVo> resultList=new ArrayList<StuOrderCommentVo>(); 
			for(CommentTagVo one:list){
				if(map.containsKey(one.getCtid())){
					StudentStatisticVo ss=map.get(one.getCtid());
					one.setScore(ss.getScore()/ss.getTotal());
				} else {
					one.setScore(0);
				}
				//计算最终结果:1代表以课程分类
				if(sort!=null && sort.equals("1") ){
					if(resultMap.containsKey(one.getCourseId())){
						StuOrderCommentVo s=resultMap.get(one.getCourseId());
						s.getCtids().add(one.getCtid());
						s.getScores().add(one.getScore());
						s.getTags().add(one.getTag());
						
					} else {
						StuOrderCommentVo s=new StuOrderCommentVo();
						BeanCopy.copyAll(one, s);
						List<Integer> ctids=new ArrayList<Integer>();
						List<Integer> scores=new ArrayList<Integer>();
						List<String> tags=new ArrayList<String>();
						ctids.add(one.getCtid());
						scores.add(one.getScore());
						tags.add(one.getTag());
						s.setCtids(ctids);
						s.setScores(scores);
						s.setTags(tags);
						resultMap.put(one.getCourseId(),s);
						resultList.add(s);
					}
				//默认以科目分类
				} else {
					if(resultMap.containsKey(one.getSubjectId())){
						StuOrderCommentVo s=resultMap.get(one.getSubjectId());
						s.getCtids().add(one.getCtid());
						s.getScores().add(one.getScore());
						s.getTags().add(one.getTag());
						
					} else {
						StuOrderCommentVo s=new StuOrderCommentVo();
						BeanCopy.copyAll(one, s);
						List<Integer> ctids=new ArrayList<Integer>();
						List<Integer> scores=new ArrayList<Integer>();
						List<String> tags=new ArrayList<String>();
						ctids.add(one.getCtid());
						scores.add(one.getScore());
						tags.add(one.getTag());
						s.setCtids(ctids);
						s.setScores(scores);
						s.setTags(tags);
						resultMap.put(one.getSubjectId(),s);
						resultList.add(s);
					}
				}
			}
			if(log.isDebugEnabled()){
				log.error("studentId="+studentId+",subjectId="+subjectId+",courseId="+courseId+",result="+resultList);
			}
			result.setData(resultList);
			if (courseId != null && !courseId.trim().equals("0") && !courseId.trim().equals("")){
				Student studentInfo = studentManager.getStudentInfo(Long.valueOf(studentId));
				switch (Integer.valueOf(courseId)) {
				case 1:
				case 11:
					result.setData("classHour", studentInfo.getBasicHour2());break;
				case 2:
				case 12:
					result.setData("classHour", studentInfo.getSimTestHour2());break;
				case 3:
				case 13:
					result.setData("classHour", studentInfo.getBasicHour3());break;
				case 4:
				case 14:
					result.setData("classHour", studentInfo.getSimTestHour3());break;
				case 5:
				case 15:
					result.setData("classHour", studentInfo.getDriveHour());break;
				case 6:
				case 16:
					result.setData("classHour", studentInfo.getTestHour2());break;
				case 7:
				case 17:
					result.setData("classHour", studentInfo.getTestHour3());break;
				default:
					break;
				}
				
			}
		} catch (Exception e) {
			result = ReqResult.getFailed();
			log.error("studentId="+studentId+",subjectId="+subjectId+",courseId="+courseId+" getStudentScore Exception:"+e.getMessage(),e);
		}
		return result;
	}
	/**
	 * 获取教练指定日期的统计
	 * @param coachId
	 * @param date：
	 * 		1. 统计日期，并且格式是YYYYMMDD的8位数字.为空表示统计当日
	 *      2. 注意统计当日这个时间必须为空，不需要客户端穿，防止时间错误，  
	 * @param tokenId
	 * @return
	 * @throws Exception
	 */
	public ReqResult getCoachStatistc(String coachId,String date,String tokenId) throws Exception{
		ReqResult result = ReqResult.getSuccess();
		try {
			CoachStatisticVo vo=coachStatisticService.getCoachStatistc(Long.valueOf(coachId), date);
			DateStatisticVo data=new DateStatisticVo();
			    
				int total=vo.getOrderAccept()+vo.getOrderRefuse();
				//总单数包括拒绝和取消单(实际含在accept中)
				data.setOrderNum(total);
				//当日流水已经计算好了
				data.setMoney(vo.getOrderMoney());
				CoachScoreVo cs=coachScoreService.queryCoachScoreById(Long.valueOf(coachId), new CoachScoreQuery());
				if(cs==null) {
					cs=new CoachScoreVo();
					cs.setCoachId(Long.valueOf(coachId));
				}
				long all=cs.getAcceptOrder()+cs.getRefuseOrder();
				//历史接单率
				if(all>0){
					data.setPercent((int)(cs.getAcceptOrder()*1000/all));
				} else {
					data.setPercent(1000);
				}
				//历史平均分
				/*if(cs.getOrderTotal()>0){
					data.setScore((int)(cs.getScoreTotal()/cs.getOrderTotal()));
				} else {
					//默认50分！
					data.setScore(OrderConstant.COACHSCORE);
				}*/
				int sum = coachStatisticService.getCoachBonusByDate(date, Long.valueOf(coachId));
				data.setBonus(sum);
				Coach coach = coachManager.getCoachInfo(Long.parseLong(coachId));
				data.setScore(coach.getStarLevel());
			result.setData(data);
		} catch (Exception e) {
			result = ReqResult.getFailed();
			log.error(coachId+",date="+date+" getCoachStatistc Exception:"+e.getMessage(),e);
		}
		return result;
	}
	/**
	 * 根据相关参数获取推送消息的对象体
	 * @param userId
	 * @param userType
	 * @param operate
	 * @param timeStamp
	 * @return data为推送消息的对象体，目前可能为OrderVo,List<OrderVo>或者CoachClassVo
	 */
	public ReqResult getJpushObject(long userId,int userType,int operate,String timeStamp) {
		String keyKey=JpushConstant.PREFIX.PUSHOBJECT+userId+"_"+userType+"_"+timeStamp+"_"+operate;
		ReqResult result = ReqResult.getSuccess();
		try {
			String key=redisUtil.get(keyKey);
			if(key==null){
				log.error(keyKey+" getJpushObject key emtype="+key);
				return ReqResult.getFailed();
			}
			Object data=redisUtil.get(JpushConstant.PREFIX.PUSHOBJECT+key);
			//灰度专用,请注意如果推送有增加或修改，这里亦需要修改
			if(data==null){
				//1.否则是ccid,且operate=JpushConstant.OPERATE.COACHPREPARE,JpushConstant.OPERATE.COACHCLASSNEAR，则为List<OrderVo>
				if(JpushConstant.OPERATE.COACHPREPARE.equals(String.valueOf(operate)) || JpushConstant.OPERATE.COACHCLASSNEAR.equals(String.valueOf(operate))){
					OrderVo search=new OrderVo();
					search.setCcid(Integer.valueOf(key));
					search.setOrderState(OrderConstant.ORDERSTATE.ACCEPTORDER);
					OrderQuery orderQuery=new OrderQuery();
					List<OrderVo> list=orderService.queryByObjectAnd(search, orderQuery);
					if(list!=null&& !list.isEmpty()){
						redisUtil.setMe(JpushConstant.PREFIX.PUSHOBJECT+key, list, RedisExpireConstant.RMQTIME.RETYSECONDS);
						data=list;
					} else {
						log.error(JpushConstant.PREFIX.PUSHOBJECT+key+" get orderlist empty.");
					}
				//2.否则是ccid,且operate=JpushConstant.OPERATE.COACHCLASSIN，则为List<OrderVo>
				} else if(JpushConstant.OPERATE.COACHCLASSIN.equals(String.valueOf(operate))){
					OrderVo search=new OrderVo();
					search.setCcid(Integer.valueOf(key));
					search.setOrderState(OrderConstant.ORDERSTATE.INCLASS);
					OrderQuery orderQuery=new OrderQuery();
					List<OrderVo> list=orderService.queryByObjectAnd(search, orderQuery);
					if(list!=null&& !list.isEmpty()){
						redisUtil.setMe(JpushConstant.PREFIX.PUSHOBJECT+key, list, RedisExpireConstant.RMQTIME.RETYSECONDS);
						data=list;
					} else {
						log.error(JpushConstant.PREFIX.PUSHOBJECT+key+" get orderlist empty.");
					}
				//3.否则是ccid,且operate=JpushConstant.OPERATE.COACHCLASSOUT，则为List<OrderVo>
				}  else if(JpushConstant.OPERATE.COACHCLASSOUT.equals(String.valueOf(operate))){
					OrderVo search=new OrderVo();
					search.setCcid(Integer.valueOf(key));
					search.setOrderState(OrderConstant.ORDERSTATE.COMPLETE);
					OrderQuery orderQuery=new OrderQuery();
					List<OrderVo> list=orderService.queryByObjectAnd(search, orderQuery);
					if(list!=null&& !list.isEmpty()){
						redisUtil.setMe(JpushConstant.PREFIX.PUSHOBJECT+key, list, RedisExpireConstant.RMQTIME.RETYSECONDS);
						data=list;
					} else {
						log.error(JpushConstant.PREFIX.PUSHOBJECT+key+" get orderlist empty.");
					}
				//4.其余ccid的operate=JpushConstant.OPERATE.COACHNOBOOKED或者JpushConstant.OPERATE.COACHNOBOOKED15，则为排班
				}  else if(JpushConstant.OPERATE.COACHNOBOOKED.equals(String.valueOf(operate)) || JpushConstant.OPERATE.COACHNOBOOKED15.equals(String.valueOf(operate))){
					List<CoachClassVo> cc=coachClassService.queryByCcid(Integer.valueOf(key), new CoachClassQuery());
					if(cc!=null && !cc.isEmpty()) {
						redisUtil.setMe(JpushConstant.PREFIX.PUSHOBJECT+key, cc.get(0), RedisExpireConstant.RMQTIME.RETYSECONDS);
						data=cc.get(0);
					} else {
						log.error(JpushConstant.PREFIX.PUSHOBJECT+key+" get coachClass empty.");
					}	
				//5.其他都是orderId，那么一定是orderVo
				} else {
					List<OrderVo> list=orderService.queryByOrderId(key, new OrderQuery());
					if(list!=null&& !list.isEmpty()){
						redisUtil.setMe(JpushConstant.PREFIX.PUSHOBJECT+key, list.get(0), RedisExpireConstant.RMQTIME.RETYSECONDS);
						data=list.get(0);
					} else {
						log.error(JpushConstant.PREFIX.PUSHOBJECT+key+" get ORDER empty.");
					}	
				}
			}
			//灰度专用结束
			if(data==null){
				log.error(keyKey+" getJpushObject key="+key+",but data empty="+data);
				return ReqResult.getFailed();
			}
			result.setData(data);
		} catch(Exception e){
			log.error(keyKey+" getJpushObject Exception="+e.getMessage(),e);
			return ReqResult.getFailed();
		}
		return result;
	}


	@Override
	public ReqResult getCommetTag(String courseId, String type) {
		ReqResult r = ReqResult.getSuccess();
		try {
			CommentTagVo vo=new CommentTagVo();
			if (StringUtils.isNotEmpty(courseId)) {
				vo.setCourseId(Integer.parseInt(courseId));
			}
			if (StringUtils.isNotEmpty(type)) {
				vo.setType(Integer.parseInt(type));
			}
			CommentTagQuery query=new CommentTagQuery();
			query.setPageSize(500);
			query.setorderBy("order by ctid asc");
			List<CommentTagVo> list=commentTageService.queryByObjectAnd(vo, query);
			if (list != null && list.size() > 0){
				r.setData(list);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return r;
	}
}
