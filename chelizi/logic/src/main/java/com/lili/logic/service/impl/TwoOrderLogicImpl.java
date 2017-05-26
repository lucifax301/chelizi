//package com.lili.logic.service.impl;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import javax.annotation.Resource;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
//import com.alibaba.rocketmq.common.message.Message;
//import com.lili.coach.dto.Car;
//import com.lili.coach.dto.Coach;
//import com.lili.coach.dto.CourseS;
//import com.lili.coach.manager.CarManager;
//import com.lili.coach.manager.CoachManager;
//import com.lili.coach.manager.CourseSManager;
//import com.lili.coach.service.CoachService;
//import com.lili.coach.vo.CoachVo;
//import com.lili.common.constant.OrderConstant;
//import com.lili.common.util.BeanCopy;
//import com.lili.common.util.DateUtil;
//import com.lili.common.util.SerializableUtil;
//import com.lili.common.util.StringUtil;
//import com.lili.common.util.TimeUtil;
//import com.lili.common.util.redis.RedisUtil;
//import com.lili.common.vo.ReqConstants;
//import com.lili.common.vo.ReqResult;
//import com.lili.common.vo.ResultCode;
//import com.lili.location.service.LocationService;
//import com.lili.logic.service.OrderLogic;
//import com.lili.order.service.CoachClassService;
//import com.lili.order.service.CoachCommentService;
//import com.lili.order.service.CoachScoreService;
//import com.lili.order.service.CoachStatisticService;
//import com.lili.order.service.CommentTagService;
//import com.lili.order.service.OrderService;
//import com.lili.order.service.StuCommentService;
//import com.lili.order.service.UnitPriceService;
//import com.lili.order.vo.CoachClassVo;
//import com.lili.order.vo.CoachCommentVo;
//import com.lili.order.vo.CoachScoreQuery;
//import com.lili.order.vo.CoachScoreVo;
//import com.lili.order.vo.CoachStatisticVo;
//import com.lili.order.vo.CommentTagQuery;
//import com.lili.order.vo.CommentTagVo;
//import com.lili.order.vo.DateStatisticVo;
//import com.lili.order.vo.OrderExtQuery;
//import com.lili.order.vo.OrderQuery;
//import com.lili.order.vo.OrderVo;
//import com.lili.order.vo.SearchVo;
//import com.lili.order.vo.StuCommentVo;
//import com.lili.order.vo.WaitOrderVo;
//import com.lili.student.dto.Student;
//import com.lili.student.manager.StudentManager;
//
//
//public class TwoOrderLogicImpl implements OrderLogic {
//	
//	private Logger log = Logger.getLogger(TwoOrderLogicImpl.class);
//	// 每次最小教练显示数
//	private static final int minSize = 50;
//	// 每次最小教练显示数
//	private static final int maxTimes = 3;
//	// 实际查询分页大小
//	private static int realSize = 50;
//
//	private static double[] nearDistance = new double[] { 2, 10, 50 };
//
//	@Autowired
//	private LocationService locationService;
//
//	@Autowired
//	private OrderService orderService;
//	@Autowired
//	private CoachClassService coachClassService;
//	@Autowired
//	private CoachManager coachManager;
//	@Autowired
//	private CarManager carManager;
//	@Autowired
//	private CoachService coachService;
//	@Autowired
//	StuCommentService stuCommentService;
//	@Autowired
//	CoachCommentService coachCommentService;
//	@Autowired
//	CommentTagService commentTageService;
//	@Resource(name="orderProducer")
//	private DefaultMQProducer orderProducer;
//	@Autowired
//	private CoachStatisticService coachStatisticService; 
//	@Autowired
//	private CoachScoreService coachScoreService;
//	@Autowired
//	private UnitPriceService unitPriceService;
//	@Autowired
//	RedisUtil redisUtil;
//	@Autowired
//	private StudentManager studentManager;
//	@Autowired
//	private CourseSManager courseSManager;
//	
//	public ReqResult reservation(String studentId, String lge, String lae,
//			String coachStatus, String examType, String carLevel,
//			String courseId, String pageNo, String pageSize, String tokenId) {
//		ReqResult result = ReqResult.getSuccess();
//		List<CoachVo> list = new ArrayList<CoachVo>();
//		try {
//			long allStart=System.currentTimeMillis();
//			// 0.参数验证
//			if (lae == null || lge == null) {
//				result = ReqResult.getParamError();
//				return result;
//			}
//			int distTimes = 0;
//			// 当前已查找教练数量
//			int ret = 0;
//			// 1.获取附近的教练，通过不同的距离，得到需求范围内的数量
//			SearchVo search = new SearchVo();
//			search.setLae(Double.valueOf(lae));
//			search.setLge(Double.valueOf(lge));
//			search.setPageSkip(ret);
//			search.setPageLimit(realSize);
//			search.setDistance(nearDistance[distTimes]);
//			// 默认现在
//			Date pstart = new Date();
//			Date pend = DateUtil.dateAfterMinute(pstart, OrderConstant.clztime);
//			// 循环取教练
//			Set<Long> idSet = new HashSet<Long>();
//			long start=0;
//			for (int i = 0; i < maxTimes; i++) {
//				//1.获取位置
//				start=System.currentTimeMillis();
//				List<CoachVo> coachs = locationService.getNearBy(search);
//				List<Long> tempIds=BeanCopy.getFieldList(coachs, "coachId");
//				log.debug(Thread.currentThread().getId()+"Thread"+i+","+tempIds+" order search location="+(System.currentTimeMillis()-start)+" with lge="+lge+",lae="+lae);
//				if(coachs==null ||coachs.isEmpty()){
//					break;
//				}
//				//2.获取教练信息：含档位，汽车（等级），科目，教练出车状态等过滤信息
//				start=System.currentTimeMillis();
//				List<Long> coachIds=new ArrayList<Long>();
//				@SuppressWarnings("unchecked")
//				List<CoachVo> coachList=(List<CoachVo>)coachService.getCoachsByIds(tempIds).getResult().get(ResultCode.RESULTKEY.DATA);
//				Map<Long,CoachVo> coachMap=new HashMap<Long,CoachVo>();
//				coachIds.clear();
//				if(coachList!=null){
//					BeanCopy.copyList(coachs, coachList, BeanCopy.COPY2NULL, "coachId");
//					for(CoachVo one:coachList){
//						String dltype=String.valueOf(one.getDriveType()); 
//						String calid=String.valueOf(one.getCarLevel());
//						String courses=one.getCourses();
//						int wstate=one.getWstate();
//						//1.档位
//						if(StringUtil.isNotNullAndNotEmpty(examType) && !examType.equals(dltype)){
//							log.debug(Thread.currentThread().getId()+"Thread"+i+","+one.getCoachId()+" order search dftype not equal="+dltype+",and param="+examType);
//							continue;
//						}
//						//2.科目
//						//为空不过滤
//						if(StringUtil.isNotNullAndNotEmpty(courseId)){
//							if(StringUtil.isNotNullAndNotEmpty(courses)){
//								List<String> clist=Arrays.asList(courses.split(","));
//								if(!clist.contains(courseId)){
//									log.debug(Thread.currentThread().getId()+"Thread"+i+","+one.getCoachId()+" order search courseid not equal="+courses+",and param="+courseId);
//									continue;
//								}
//							}else {
//								log.debug(Thread.currentThread().getId()+"Thread"+i+","+one.getCoachId()+" order search courseid is null"+",and param="+courseId);
//								continue;
//							}
//						}
//						//3.车型
//						if(StringUtil.isNotNullAndNotEmpty(carLevel)&& !carLevel.equals(calid)){
//							log.debug(Thread.currentThread().getId()+"Thread"+i+","+one.getCoachId()+" order search carlevel not equal="+calid+",and param="+carLevel);
//							continue;
//						}
//						//4.出车
//						if(wstate!=ReqConstants.COACH_STATUS_ON_WORK) {
//							log.debug(Thread.currentThread().getId()+"Thread"+i+","+one.getCoachId()+" order search coach is not work="+wstate);
//							continue;
//						}
//						coachIds.add(one.getCoachId());
//						coachMap.put(one.getCoachId(), one);
//					}
//				}
//				log.debug(Thread.currentThread().getId()+"Thread"+i+","+coachIds+"order search coach="+(System.currentTimeMillis()-start));
////				//3.获取教练车信息
////				start=System.currentTimeMillis();
////				
////				log.debug("order search car="+(System.currentTimeMillis()-start));
//				//4.获取教练班次信息
//				start=System.currentTimeMillis();
//				coachIds.removeAll(idSet);
//				tempIds.clear();
//				if(!coachIds.isEmpty()){
//					Map<Long,List<CoachClassVo>> ccmap=coachClassService.queryByCoachDate(new Date(), coachIds);
//					for(Long oneId:coachIds){
//						if(idSet.contains(oneId)) {
//							log.debug(Thread.currentThread().getId()+"Thread"+i+","+oneId+" order search coach oready in list.");
//						} else if(isCoachIdle(ccmap.get(oneId), null,pstart, pend)){
//								list.add(coachMap.get(oneId));
//								tempIds.add(oneId);
//								idSet.add(oneId);
//						} else {
//							log.debug(Thread.currentThread().getId()+"Thread"+i+","+oneId+" order search coach has class");
//						}
//					}
//				}
//				log.debug(Thread.currentThread().getId()+"Thread"+i+","+tempIds+"order search class="+(System.currentTimeMillis()-start));
////				//5.获取教练评价
////				start=System.currentTimeMillis();
////				Map<Long,Integer> scores=coachScoreService.queryByCoachIds(coachIds);
////				log.debug("order search comment="+(System.currentTimeMillis()-start));
//				//6.获取教练价格
//				start=System.currentTimeMillis();
//				log.debug("coachMap key=" + coachMap.keySet());
//				for(Long id:tempIds){
//					CoachVo one=coachMap.get(id);
//					log.debug(one+" coachMap value=" + id);
//					int cityId=one.getCityId();
//					int cid=Integer.valueOf(courseId);
//					int colid=one.getCoachLevel();
//					int calid=one.getCarLevel();
//					int dftype=Integer.valueOf(examType);
//					int price=unitPriceService.getCoachPrice(pstart, cityId, cid, colid, calid,dftype);
//				    one.setPrice(price);
//				}
//				log.debug(Thread.currentThread().getId()+"Thread"+i+",order search price="+(System.currentTimeMillis()-start));
////				//7.获取命中教练
////				start=System.currentTimeMillis();
////				
////				log.debug("order search result="+(System.currentTimeMillis()-start));
//				//8.重新设置搜索条件
//				start=System.currentTimeMillis();
//				if (list.size() >= minSize) {
//					break;
//				}
//				// 下一查询条件构建
//				if (coachs.size() < realSize) {
//					// 扩大距离
//					if (distTimes < nearDistance.length - 1) {
//						++distTimes;
//					}
//					search.setDistance(nearDistance[distTimes]);
//					ret += coachs.size();
//					// 下拉分页
//					search.setPageSkip(ret);
//					search.setPageLimit(ret + realSize);
//				} else {
//					// 下拉分页
//					ret += coachs.size();
//					search.setPageSkip(ret);
//					search.setPageLimit(ret + realSize);
//				}
//				log.debug(Thread.currentThread().getId()+"Thread"+i+",order search search="+(System.currentTimeMillis()-start));
//			}
//			result.setData(list);
//			log.debug(Thread.currentThread().getId()+"Thread, with param="+studentId+","+lge+","+lae+","+coachStatus+","+examType+","+carLevel+","+courseId+","+pageNo+","+pageSize+","+tokenId+list+" order search="+(System.currentTimeMillis()-allStart));
//		} catch (Exception e) {
//			log.error(Thread.currentThread().getId()+"Thread, with param="+studentId+","+lge+","+lae+","+coachStatus+","+examType+","+carLevel+","+courseId+","+pageNo+","+pageSize+","+tokenId+list+" nearby order search failed=" + e.getMessage(), e);
//			//针对全面搜索,不返回异常,返回部分结果
//			result.setData(list);
////			result = ReqResult.getFailed();
//		}
//		return result;
//	}
//
//	
//	public ReqResult acceptOrder(String orderId,String coachId,
//			String studentId,String tokenId) {
//		ReqResult result = ReqResult.getSuccess();
//		try {
//			//利用redis考虑抢单的并发，后续考虑
//			//获得订单
//			OrderVo orderVo =orderService.queryOrderById(orderId, new OrderQuery());
//			//判断订单情况：包括存在，状态等
//			if(orderVo==null){
//				result.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
//				return result;
//			}
//			if(orderVo.getOrderState()!=OrderConstant.ORDERSTATE.GIVEORDER){
//				result.setCode(ResultCode.ERRORCODE.ORDER_HASACCEPT);
//				return result;
//			}
//			//更新数据
//			orderVo.setAtime(new Date());
//			orderVo.setOrderState(OrderConstant.ORDERSTATE.ACCEPTORDER);
//			orderVo.setCoachId(Long.valueOf(coachId));
//			//回存方式接单，后续如抢单需要考虑并发
//			orderService.saveOrder(orderVo);
//			//成功发送接单消息
//			Message msg=new Message();
//	    	msg.setKeys(orderId);
//	    	msg.setTopic(orderProducer.getCreateTopicKey());
//	    	msg.setTags(OrderConstant.RMQTAG.ACCEPTORDER);
//	    	msg.setBody(SerializableUtil.serialize(orderVo));
//	    	orderProducer.send(msg);
//		} catch (Exception e) {
//			result = ReqResult.getFailed();
//			log.error(orderId+" acceptOrder Exception:"+e.getMessage(),e);
//		}
//		return result;
//	}
//	
//	@Override
//	public ReqResult addOrder(OrderVo orderVo,  String tokenId) {
//		ReqResult result = ReqResult.getSuccess();
//		try {
//			//支付限制
//			OrderExtQuery oequery=new OrderExtQuery();
//	    	oequery.setQueryExt("and pay_state in (0,2) and order_state in (1,2,3,4,5,6,7,8)");
//	    	oequery.setPageIndex(1);
//	    	OrderVo search=new OrderVo();
//	    	search.setStudentId(orderVo.getStudentId());
//	    	List<OrderVo> waitPay=orderService.queryByObject(search, oequery);
//	    	if(waitPay!=null &&!waitPay.isEmpty()){
//	    		result.setCode(ResultCode.ERRORCODE.ORDER_NO_GIVE);
//	    		return result;
//	    	}
//			//添加orderId
//			String orderId=orderVo.getOrderId();
//			if(orderId==null|| orderId.length()!=32) {
//				orderId=StringUtil.getOrderId();
//				orderVo.setOrderId(orderId);
//			}
//			// 如果没有时间默认为现在。
//			if (orderVo.getPstart() == null) {
//				orderVo.setPstart(new Date());
//			}
//			// 结束时间
//			orderVo.setPend(DateUtil.dateAfterMinute(orderVo.getPstart(),
//					OrderConstant.clztime * orderVo.getClzNum()));
//			//订单状态
//			orderVo.setOrderState(OrderConstant.ORDERSTATE.GIVEORDER);
//			orderVo.setOtype(OrderConstant.OTYPE.NOWORDER);
//			orderVo.setGtime(new Date());
//			// 班次冲突查询
//			List<CoachClassVo> list=coachClassService.queryByCoachDate(orderVo.getPstart(), orderVo.getCoachId(),OrderConstant.ISDEL.NOTDELETE ,null,1, 30);
//			boolean idle=this.isCoachIdle(list, null,orderVo.getPstart(), orderVo.getPend());
//			if(!idle) {
//				result.setCode(ResultCode.ERRORCODE.ORDER_COACH_FULL);
//				return result;
//			}
//			//1.添加教练信息
//			Coach c=coachManager.getCoachInfo(orderVo.getCoachId());
//			orderVo.setCoachImg(c.getHeadIcon());
//			orderVo.setCoachMobile(c.getPhoneNum());
//			orderVo.setCoachStar(c.getStarLevel());
//			orderVo.setCoachName(c.getName());
//			//2.添加学生信息
//			Student s=studentManager.getStudentInfo(orderVo.getStudentId());
//			orderVo.setStuImg(s.getHeadIcon());
//			orderVo.setStuMobile(s.getPhoneNum());
//			orderVo.setStuName(s.getName());
//			//3.添加汽车信息
//			Car car=carManager.getCarInfo(orderVo.getCarId());
//			orderVo.setCarName(car.getCarType());
//			orderVo.setCarImg(null);
//			orderVo.setCarNo(car.getCarNo());
//			//4.添加课程信息
//			CourseS course=courseSManager.getCourseSById(Integer.valueOf(orderVo.getCourseId()));
//			if(course!=null){
//				orderVo.setCourseName(course.getName());
//			}
//			orderService.saveOrder(orderVo);
//			result.setData(orderId);
//			//添加排班信息
//			CoachClassVo cc=new CoachClassVo();
//			cc.setCtype(OrderConstant.OTYPE.NOWORDER);
//			cc.setCoachId(orderVo.getCoachId());
//			cc.setOrderId(orderId);
//			cc.setCstart(orderVo.getPstart());
//			cc.setCend(orderVo.getPend());
//			cc.setRstart(cc.getCstart());
//			cc.setRend(cc.getCend());
//			coachClassService.addCoachClass(cc);
//			//成功发送提交定订单消息
//			Message msg=new Message();
//	    	msg.setKeys(orderId);
//	    	msg.setTopic(orderProducer.getCreateTopicKey());
//	    	msg.setTags(OrderConstant.RMQTAG.COMMITORDER);
//	    	msg.setBody(SerializableUtil.serialize(orderVo));
//	    	orderProducer.send(msg);
//		} catch (Exception e) {
//			result = ReqResult.getFailed();
//			log.error(orderVo.getCoachId()+",studentId="+orderVo.getStudentId()+" add order exception:"+e.getMessage(),e);
//		}
//		return result;
//	}
//
//	/**
//	 * 订单取消接口
//	 * 
//	 * @param orderId
//	 *            ：订单id
//	 * @param retype
//	 *            :取消原因类型：1.懒得等，2.订单错误，2.我时间冲突 3.对方态度不好, 4.对方有事
//	 * @param reseaon
//	 *            ：原因描述
//	 * @param ucancel
//	 *            ：取消发起人类型：1代表教练，2代表学员
//	 * @param tokenId
//	 * @return data=0：取消成功 data=1:取消失败 data=2:退款处理失败
//	 */
//	public ReqResult cancelOrder(String orderId, String retype, String reseaon,
//			String userId, String ucancel, String tokenId) {
//		ReqResult result = ReqResult.getSuccess();
//		try {
//			Integer intRetype = null;
//			Integer intUcancel = null;
//			if (StringUtil.isNotNullAndNotEmpty(retype)) {
//				intRetype = Integer.valueOf(retype);
//			}
//			if (StringUtil.isNotNullAndNotEmpty(ucancel)) {
//				intUcancel = Integer.valueOf(ucancel);
//			}
//			String code = orderService.cancelOrder(orderId, intRetype, reseaon,
//					intUcancel, tokenId,1);
//			result.setCode(code);
//		} catch (Exception e) {
//			result = ReqResult.getFailed();
//			log.error(orderId+" cancel Exception:"+e.getMessage(),e);
//		}
//		return result;
//	}
//
//	/**
//	 * 教练拒单
//	 * 
//	 * @param orderId
//	 *            ：订单号
//	 * @param reseaon
//	 *            ：拒单原因
//	 * @param tokenId
//	 * @return
//	 */
//	public ReqResult refuseOrder(String orderId, String coachId,
//			String reseaon, String tokenId) {
//		ReqResult result = ReqResult.getSuccess();
//		try {
//			Integer intRetype = OrderConstant.RETYPE.COACHREFUSE;
//			Integer intUcancel = OrderConstant.USETYPE.COACH;
//			String code = orderService.cancelOrder(orderId, intRetype, reseaon,
//					intUcancel, tokenId,0);
//			result.setCode(code);
//		} catch (Exception e) {
//			result = ReqResult.getFailed();
//			log.error(orderId+" refuse Exception:"+e.getMessage(),e);
//		}
//		return result;
//	}
//
//	/**
//	 * 获取续课订单单价
//	 * 
//	 * @param orderId
//	 *            ：被续课的订单号
//	 * @param clzNum
//	 *            :续课课程数量，可为空
//	 * @param tokenId
//	 * @return data=每节课单价
//	 */
//	public ReqResult continuePrice(String orderId, String studentId,
//			String clzNum, String tokenId) {
//
//		ReqResult result = ReqResult.getSuccess();
//		try {
//			// 1.获取原始订单
//			OrderVo o = orderService.queryOrderById(orderId, new OrderQuery());
//			if (o == null) {
//				result.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
//				return result;
//			}
//			long coachId = o.getCoachId();
//			Coach coach = coachManager.getCoachInfo(coachId);
//			// 价格获取,首先获取汽车类型
//			Car car = carManager.getCarLevelByCoachId(coachId);// 从汽车获取
//			int calid = car.getCarLevel();
//			int cityId = coach.getCityId();
//			int colid = coach.getCoachLevel();
//			int price = orderService.getCoachPrice(o.getPend(), o.getPend(),
//					cityId, o.getCourseId(), colid, calid);
//			result.setData(price);
//			// 2.结合原始定的那和最近时间计算价格
//		} catch (Exception e) {
//			result = ReqResult.getFailed();
//			log.error(orderId+" continuePrice Exception:"+e.getMessage(),e);
//		}
//		return result;
//	}
//
//	/**
//	 * 续课
//	 * 
//	 * @param orderId
//	 *            ：原单
//	 * @param clzNum
//	 *            ：续课节数
//	 * @param price
//	 *            ：续课总价
//	 * @param tokenId
//	 * @return
//	 */
//	public ReqResult continueOrder(String orderId, String studentId,
//			String clzNum, String price, String tokenId) {
//		ReqResult result = ReqResult.getSuccess();
//		try {
//			// 参数判断
//			if (StringUtil.isEmptyOrWhitespaceOnly(orderId)
//					|| StringUtil.isEmptyOrWhitespaceOnly(price)
//					|| StringUtil.isEmptyOrWhitespaceOnly(clzNum)) {
//				result.setCode(ResultCode.ERRORCODE.PARAMERROR);
//				return result;
//			}
//			// 1.获取原始订单
//			OrderVo o = orderService.queryOrderById(orderId, new OrderQuery());
//			if (o == null) {
//				result.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
//				return result;
//			}
//			// 2.使用原始订单信息，加新信息新增订单
//			String neworderId = StringUtil.getOrderId();
//			o.setOrderId(neworderId);
//			o.setPrice(Integer.valueOf(price));
//			o.setCoorder(orderId);
//			o.setPayState(OrderConstant.PAYSTATE.WAITPAY);
//			o.setOrderState(OrderConstant.ORDERSTATE.GIVEORDER);
//			o.setOtype(OrderConstant.OTYPE.CONTINUEORDER);
//			o.setPstart(o.getPend());
//			o.setPend(DateUtil.dateAfterMinute(o.getPend(),
//					OrderConstant.clztime * Integer.valueOf(clzNum)));
//			o.setClzNum(Integer.valueOf(clzNum));
//			o.setGtime(new Date());
//			o.setAtime(null);
//			orderService.addOrder(o);
//			result.setData(neworderId);
//			// 待实现：推送给教练
//		} catch (Exception e) {
//			result = ReqResult.getFailed();
//			log.error(orderId+" continueOrder Exception:"+e.getMessage(),e);
//		}
//		return result;
//	}
//
//	public ReqResult bookOrder(OrderVo orderVo,  String tokenId) throws Exception {
//		ReqResult result = ReqResult.getSuccess();
//		// 参数判断
//		if (orderVo.getOrderId()==null
//				|| orderVo.getCourseId()==null
//				|| orderVo.getCcid()==null||orderVo.getCcid()==0
//				|| orderVo.getPrice()==null) {
//			result.setCode(ResultCode.ERRORCODE.PARAMERROR);
//		}
//		try {
//			//添加orderId,只有没id时才添加
//			String orderId = orderVo.getOrderId();
//			if(orderId==null ||orderId.length()!=32){
//				orderId=StringUtil.getOrderId();
//				orderVo.setOrderId(orderId);
//			}
//			String code = orderService.bookOrder(orderVo);
//			result.setCode(code);
//			result.setData(orderId);
//		} catch (Exception e) {
//			result = ReqResult.getFailed();
//			log.error(orderVo.getCoachId()+",studentId="+orderVo.getStudentId()+" bookOrder Exception:"+e.getMessage(),e);
//		}
//		return result;
//	}
//
//	/**
//	 * 学生评价教练
//	 * 
//	 * @param coachId
//	 * @param studentId
//	 * @param orderId
//	 * @param score
//	 * @param tag
//	 *            ：评价标签，来自评价表浅表t_commenttag
//	 * @param tokenId
//	 * @return
//	 */
//	public ReqResult stuCommentCoach(String coachId, String studentId,
//			String orderId, String score, String tagId, String oneWord,String tokenId)
//			throws Exception {
//		ReqResult result = ReqResult.getSuccess();
//		CoachCommentVo scc = new CoachCommentVo();
//		// 2.参数判断
//		// ...
//		try {
//			//订单判断
//			OrderVo order=orderService.queryOrderById(orderId, new OrderQuery());
//			if(order==null){
//				log.error(orderId+" stuCommentCoach not exist.");
//				result.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
//				return result;
//			}
//			if(!String.valueOf(order.getCoachId()).equals(coachId)||!String.valueOf(order.getStudentId()).equals(studentId)){
//				log.error(orderId+" stuCommentCoach not equal coach="+coachId+", or student="+studentId);
//				result.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
//				return result;
//			}
//			//订单状态更新	
//			if(order.getOrderState()==OrderConstant.ORDERSTATE.COMPLETE||order.getOrderState()==OrderConstant.ORDERSTATE.COACHCOMMENT){
//				order.setOrderState(order.getOrderState()+2);
//			} else {
//				log.error(orderId+" coachCommentStu not equal coach="+coachId+", or student="+studentId);
//				result.setCode(ResultCode.ERRORCODE.ORDER_NOT_COMMENT);
//				return result;
//			}
//			orderService.saveOrder(order);
//		scc.setCoachId(Long.valueOf(coachId));
//		scc.setOrderId(orderId);
//		scc.setStudentId(Long.valueOf(studentId));
//		scc.setScore(Integer.valueOf(score));
//		scc.setCtid(tagId);
//		scc.setCotime(new Date());
//		scc.setOneWord(oneWord);
//		coachCommentService.addCoachComment(scc);
//		//评价成功后，发送消息
//		Message msg=new Message();
//    	msg.setKeys(orderId+OrderConstant.RMQTAG.COMMENTCOACH);
//    	msg.setTopic(orderProducer.getCreateTopicKey());
//    	msg.setTags(OrderConstant.RMQTAG.COMMENTCOACH);
//    	msg.setBody(SerializableUtil.serialize(scc));
//    	orderProducer.send(msg);
//		} catch (Exception e) {
//			result = ReqResult.getFailed();
//			log.error(coachId+",studentId="+studentId+" stuCommentCoach Exception:"+e.getMessage(),e);
//		}
//		return result;
//	}
//
//	/**
//	 * 教练给学生评分
//	 * 
//	 * @param coachId
//	 * @param studentId
//	 * @param orderId
//	 * @param scroces
//	 *            ：<评价标签，得分>,注意其中评价标签来自评价表浅表t_commenttag
//	 * @param tokenId
//	 * @return
//	 * @throws Exception
//	 */
//	public ReqResult coachCommentStu(String coachId, String studentId,
//			String orderId, Map<Integer, Integer> scores, String oneWord, String tokenId)
//			throws Exception {
//		ReqResult result = ReqResult.getSuccess();
//		// 2.参数判断
//		// ...
//		try {
//		//订单判断
//		OrderVo order=orderService.queryOrderById(orderId, new OrderQuery());
//		if(order==null){
//			log.error(orderId+" coachCommentStu not exist.");
//			result.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
//			return result;
//		}
//		if(!String.valueOf(order.getCoachId()).equals(coachId)||!String.valueOf(order.getStudentId()).equals(studentId)){
//			log.error(orderId+" coachCommentStu not equal coach="+coachId+", or student="+studentId);
//			result.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
//			return result;
//		}
//		//订单状态更新	
//		if(order.getOrderState()==OrderConstant.ORDERSTATE.COMPLETE||order.getOrderState()==OrderConstant.ORDERSTATE.STUCOMMENT){
//			order.setOrderState(order.getOrderState()+1);
//		} else {
//			log.error(orderId+" coachCommentStu not equal coach="+coachId+", or student="+studentId);
//			result.setCode(ResultCode.ERRORCODE.ORDER_NOT_COMMENT);
//			return result;
//		}
//		orderService.saveOrder(order);
//		
//		List<StuCommentVo> list = new ArrayList<StuCommentVo>();
//		Iterator<Integer> it = scores.keySet().iterator();
//		Date cotime = new Date();
//		while (it.hasNext()) {
//			Integer ctid = it.next();
//			StuCommentVo ccs = new StuCommentVo();
//			ccs.setCoachId(Long.valueOf(coachId));
//			ccs.setOrderId(orderId);
//			ccs.setStudentId(Long.valueOf(studentId));
//			ccs.setCtid(ctid);
//			ccs.setScore(scores.get(ctid));
//			ccs.setCotime(cotime);
//			ccs.setOneWord(oneWord);
//			list.add(ccs);
//		}
//		stuCommentService.addStuCommentList(list);
//		} catch (Exception e) {
//			result = ReqResult.getFailed();
//			log.error(coachId+",studentId="+studentId+" coachCommentStu Exception:"+e.getMessage(),e);
//		}
//		return result;
//	}
//	/**
//	 * 获取评分被评分对象的标签
//	 * @param courseId：
//	 *  1.获取教师评价标签是为平分数，此时可为空
//	 *  2.获取学生评价标签时，此参数为评价科目，此时不可为空
//	 * @param userType:获取被评价人的用户类型，务必注意不是自己，而是被自己评价的对方的用户类型 
//	 * @param tokenId
//	 * @return
//	 * @throws Exception
//	 */
//	public ReqResult getCommentTag(String userType,String courseId, String tokenId) throws Exception {
//		ReqResult result = ReqResult.getSuccess();
//		try {
//		CommentTagVo vo=new CommentTagVo();
//		
//		if(StringUtil.isNotNullAndNotEmpty(courseId)){
//			vo.setCourseId(Integer.valueOf(courseId));
//		}
//		vo.setType(Integer.valueOf(userType));
//		List<CommentTagVo> list=commentTageService.queryByObjectAnd(vo, new CommentTagQuery());
//		result.setData(list);
//		} catch (Exception e) {
//			result = ReqResult.getFailed();
//			log.error(userType+",courseId="+courseId+" getCommentTag Exception:"+e.getMessage(),e);
//		}
//		return result;
//	}
//	/**
//	 * 获取学生技能点最新评分
//	 * @param studentId
//	 * @param courseId
//	 * @param tokenId
//	 * @return
//	 * @throws Exception
//	 */
//	public ReqResult getStudentScore(String studentId,String courseId,String tokenId) throws Exception{
//		ReqResult result = ReqResult.getSuccess();
//		// 1.不需要登录判断，因为教练亦可查看		
//		//获取技能项目
//		try {
//		CommentTagVo tag=new CommentTagVo();
//		tag.setCourseId(Integer.valueOf(courseId));
//		tag.setIsdel(OrderConstant.ISDEL.NOTDELETE);
//		tag.setType(OrderConstant.USETYPE.STUDENT);
//		List<CommentTagVo> list=commentTageService.queryByObjectAnd(tag, new CommentTagQuery());
//		List<Integer> taglist=BeanCopy.getFieldList(list, "ctid");
//		List<StuCommentVo> scoreList=stuCommentService.queryByTags(taglist, Long.valueOf(studentId));
//		BeanCopy.copyList(scoreList, list, BeanCopy.COPY2NULL,"ctid");
//		result.setData(list);
//		} catch (Exception e) {
//			result = ReqResult.getFailed();
//			log.error(courseId+",studentId="+studentId+" getStudentScore Exception:"+e.getMessage(),e);
//		}
//		return result;
//	}
//	/**
//	 * 获取教练指定日期的统计
//	 * @param coachId
//	 * @param date：
//	 * 		1. 统计日期，并且格式是YYYYMMDD的8位数字.为空表示统计当日
//	 *      2. 注意统计当日这个时间必须为空，不需要客户端穿，防止时间错误，  
//	 * @param tokenId
//	 * @return
//	 * @throws Exception
//	 */
//	public ReqResult getCoachStatistc(String coachId,String date,String tokenId) throws Exception{
//		ReqResult result = ReqResult.getSuccess();
//		try {
//			CoachStatisticVo vo=coachStatisticService.getCoachStatistc(Long.valueOf(coachId), date);
//			DateStatisticVo data=new DateStatisticVo();
//			    
//				int total=vo.getOrderAccept()+vo.getOrderRefuse();
//				//总单数包括拒绝和取消单(实际含在accept中)
//				data.setOrderNum(total);
//				//当日流水已经计算好了
//				data.setMoney(vo.getOrderMoney());
//				CoachScoreVo cs=coachScoreService.queryCoachScoreById(Long.valueOf(coachId), new CoachScoreQuery());
//				if(cs==null) {
//					cs=new CoachScoreVo();
//					cs.setCoachId(Long.valueOf(coachId));
//				}
//				long all=cs.getAcceptOrder()+cs.getRefuseOrder();
//				//历史接单率
//				if(all>0){
//					data.setPercent((int)(cs.getAcceptOrder()*1000/all));
//				} else {
//					data.setPercent(1000);
//				}
//				//历史平均分
//				if(cs.getOrderTotal()>0){
//					data.setScore((int)(cs.getScoreTotal()/cs.getOrderTotal()));
//				} else {
//					//默认50分！
//					data.setScore(OrderConstant.COACHSCORE);
//				}
//			result.setData(data);
//		} catch (Exception e) {
//			result = ReqResult.getFailed();
//			log.error(coachId+",date="+date+" getCoachStatistc Exception:"+e.getMessage(),e);
//		}
//		return result;
//	}
//	/**
//	 * 判断教练是否空闲
//	 * @param list
//	 * @param start
//	 * @param end
//	 * @return
//	 * @throws Exception
//	 */
//	public boolean isCoachIdle(List<CoachClassVo> list,Integer exceptCcid,Date start,Date end) throws Exception {
//		boolean idle=true;
//		if(list==null||list.isEmpty()){
//			return idle;
//		} else {
//			for(CoachClassVo one:list){
//				//自己的班次不需要判断
//				if(exceptCcid!=null && exceptCcid.intValue()==one.getCcid()){
//					continue;
//				}
//				long woda=TimeUtil.calcDistanceMillis(one.getCend(),start);
//				//提前下课以实际下课时间为准
//				if(one.getRend()!=null){
//					woda=TimeUtil.calcDistanceMillis(one.getRend(),start);
//				}
//				long tada=TimeUtil.calcDistanceMillis(end,one.getCstart());
//				//提前上课以实际上课时间为准
//				if(one.getRstart()!=null){
//					tada=TimeUtil.calcDistanceMillis(end,one.getRstart());
//				}
//				if(woda<=0&&tada<=0){
//					idle=false;
//					break;
//				}
//			}
//		}
//		return idle;
//	}
//	/**
//	 * 
//	 * @param coachId
//	 * @param exceptCcid:例外的ccid，也就是说不需要判断的ccid，教练编辑的时候必传
//	 * @param start
//	 * @param end
//	 * @return
//	 * @throws Exception
//	 */
//	public boolean isCoachIdle(Long coachId,Integer exceptCcid,Date start,Date end) throws Exception {
//		List<CoachClassVo> list=coachClassService.queryByCoachDate(start, coachId,OrderConstant.ISDEL.NOTDELETE ,null,1, 30);
//		return this.isCoachIdle(list, exceptCcid,start, end);
//	}
//	/**
//	 * 返回给前台的教练必须完成的订单
//	 * @param coachId
//	 * @param normalOrder:注意正常订单号全部放在list里面即可
//	 * @return
//	 */
//	public WaitOrderVo getCoachWait(long coachId,WaitOrderVo normalOrder) throws Exception {
//		WaitOrderVo wait=new WaitOrderVo();
//		OrderExtQuery oequery=new OrderExtQuery();
//    	oequery.setQueryExt("and order_state in (4,6)");
//    	oequery.setorderBy("order by rstart desc");
//    	OrderVo search=new OrderVo();
//    	search.setCoachId(coachId);
//    	List<OrderVo> waitComment=orderService.queryByObject(search, oequery);
//    	if(waitComment!=null && !waitComment.isEmpty()){
//    		List<String> normalList=new ArrayList<String>();
//        	List<String> idList=new ArrayList<String>();
//        	if(normalOrder!=null && normalOrder.getWaitCommentId()!=null){
//        		normalList.addAll(normalOrder.getWaitCommentId());
//        	}
//    		for(OrderVo one:waitComment){
//    			if(!normalList.contains(one.getOrderId())){
//    				if(wait.getWaitComment()==null){
//    					wait.setWaitComment(one);
//    				} else {
//    					idList.add(one.getOrderId());
//    				}
//    			}
//    		}
//    		if(idList.isEmpty()){
//    			wait.setWaitCommentId(idList);
//    		}
//    	}
//    	return wait;
//	}
//	/**
//	 * 返回给前台的学生必须完成的订单
//	 * @param studentId
//	 * @param normalOrder:注意正常订单号全部放在list里面即可，对象字段请全部置空
//	 * @return
//	 */
//	public WaitOrderVo getStudentWait(long studentId,WaitOrderVo normalOrder) throws Exception {
//		WaitOrderVo wait=new WaitOrderVo();
//		OrderExtQuery oequery=new OrderExtQuery();
//    	oequery.setQueryExt("and pay_state in (0,2)");
//    	OrderVo search=new OrderVo();
//    	search.setStudentId(studentId);
//    	List<OrderVo> waitPay=orderService.queryByObject(search, oequery);
//    	if(waitPay!=null && !waitPay.isEmpty()){
//    		List<String> normalList=new ArrayList<String>();
//        	List<String> idList=new ArrayList<String>();
//        	if(normalOrder!=null && normalOrder.getWaitPayId()!=null){
//        		normalList.addAll(normalOrder.getWaitPayId());
//        	}
//    		for(OrderVo one:waitPay){
//    			if(!normalList.contains(one.getOrderId())){
//    				if(wait.getWaitPay()==null){
//    					wait.setWaitPay(one);
//    				} else {
//    					idList.add(one.getOrderId());
//    				}
//    			}
//    		}
//    		if(idList.isEmpty()){
//    			wait.setWaitPayId(idList);
//    		}
//    	}
//    	return wait;
//	}
//}
