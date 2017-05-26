//package com.lili.logic.service.impl;
//
//import java.util.ArrayList;
//import java.util.Date;
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
//import com.lili.coach.manager.CarManager;
//import com.lili.coach.manager.CoachManager;
//import com.lili.coach.vo.CoachVo;
//import com.lili.common.constant.OrderConstant;
//import com.lili.common.util.BeanCopy;
//import com.lili.common.util.DateUtil;
//import com.lili.common.util.SerializableUtil;
//import com.lili.common.util.StringUtil;
//import com.lili.common.util.redis.RedisKeys.REDISKEY;
//import com.lili.common.util.redis.RedisUtil;
//import com.lili.common.vo.ReqResult;
//import com.lili.common.vo.ResultCode;
//import com.lili.location.service.LocationService;
//import com.lili.logic.service.OrderLogic;
//import com.lili.order.service.CoachClassService;
//import com.lili.order.service.CoachCommentService;
//import com.lili.order.service.CoachStatisticService;
//import com.lili.order.service.CommentTagService;
//import com.lili.order.service.OrderService;
//import com.lili.order.service.StuCommentService;
//import com.lili.order.vo.CoachClassQuery;
//import com.lili.order.vo.CoachClassVo;
//import com.lili.order.vo.CoachCommentVo;
//import com.lili.order.vo.CoachStatisticVo;
//import com.lili.order.vo.CommentTagQuery;
//import com.lili.order.vo.CommentTagVo;
//import com.lili.order.vo.DateStatisticVo;
//import com.lili.order.vo.OrderQuery;
//import com.lili.order.vo.OrderVo;
//import com.lili.order.vo.SearchVo;
//import com.lili.order.vo.StuCommentVo;
//
//public class OneOrderLogicImpl {
//	@Autowired
//	RedisUtil redisUtil;
//	private Logger log = Logger.getLogger(OneOrderLogicImpl.class);
//	// 每次最小教练显示数
//	private static final int minSize = 10;
//	// 每次最小教练显示数
//	private static final int maxTimes = 13;
//	// 实际查询分页大小
//	private static int realSize = 30;
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
//	StuCommentService stuCommentService;
//	@Autowired
//	CoachCommentService coachCommentService;
//	@Autowired
//	CommentTagService commentTageService;
//	@Resource(name="orderProducer")
//	private DefaultMQProducer orderProducer;
//	@Autowired
//	private CoachStatisticService coachStatisticService; 
//	
//
//	public ReqResult reservation(String studentId, String lge, String lae,
//			String coachStatus, String examType, String carLevel,
//			String courseId, String pageNo, String pageSize, String tokenId) {
//		ReqResult result = ReqResult.getSuccess();
//		if (!redisUtil.isExist(REDISKEY.STUDENT_TOKEN + studentId, tokenId)) {
//			result.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
//			result.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
////			return result;
//		}
//		try {
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
//			search.setPageLimit(realSize);
//			search.setDistance(nearDistance[distTimes]);
//			// 循环取教练
//			List<CoachVo> list = new ArrayList<CoachVo>();
//			Set<Long> idSet = new HashSet<Long>();
//			for (int i = 0; i < maxTimes; i++) {
//				List<CoachVo> coachs = locationService.getNearBy(search);
//				// 2.并行获取教练当前工作状态(目前在个人信息中)，个人信息，排班，价钱
//				for (CoachVo one : coachs) {
//					long coachId = one.getCoachId();
//					// b.教练排班信息
//					CoachClassVo coachClassVo = new CoachClassVo();
//					coachClassVo.setCoachId(coachId);
//					// 默认现在
//					long pstart = System.currentTimeMillis();
//					long pend = pstart + 60000 * OrderConstant.clztime;
//					coachClassVo.setCstart(new Date(pstart));
//					// 默认一节课
//					coachClassVo.setCend(new Date(pend));
//					CoachClassQuery coachClassQuery = new CoachClassQuery();
//					coachClassQuery.setPageSize(1);
//					List<CoachClassVo> coachClass = coachClassService
//							.queryBetween(coachClassVo, coachClassQuery);
//					// 去重复重
//					if ((coachClass == null || coachClass.size() == 0)
//							&& !idSet.contains(coachId)) {
//						idSet.add(coachId);
//						// 教练个人信息待获取
//						Coach info = coachManager.getCoachInfo(coachId);
//						one = BeanCopy.copy2Null(info, one);
//						// 价格获取,首先获取汽车类型
//						Car car = carManager.getCarLevelByCoachId(coachId);// 从汽车获取
//						//临时屏蔽无车数据
//						if(car==null) {
//							continue;
//						}
//						// 待完成：根据汽车进行档位和等级的过滤
//						if (!carLevel.equals(String.valueOf(car.getCarLevel()))) {
//							continue;
//						}
//						if (!examType
//								.equals(String.valueOf(car.getDriveType()))) {
//							continue;
//						}
//						//
//						int calid = car.getCarLevel();
//						int cityId = info.getCityId();
//						int colid = info.getCoachLevel();
//						int price = orderService.getCoachPrice(
//								new Date(pstart), new Date(pend), cityId,
//								courseId, colid, calid);
//						one.setPrice(price);
//						one.setCarType(car.getCarType());
//						list.add(one);
//					}
//				}
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
//					search.setPageSkip(ret);
//					search.setPageLimit(ret + realSize);
//				}
//			}
//			result.setData(list);
//		} catch (Exception e) {
//			log.error("nearby coach search failed=" + e.getMessage(), e);
//			result = ReqResult.getFailed();
//		}
//		return result;
//	}
//
//	
//	public ReqResult acceptOrder(String orderId, String coachId,
//			String studentId, String tokenId) {
//		ReqResult result = ReqResult.getSuccess();
//		if (redisUtil.isExist(REDISKEY.COACH_TOKEN + coachId, tokenId)) {
//			result.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
//			result.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
//			return result;
//		}
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
//	@SuppressWarnings("deprecation")
//	public ReqResult addOrder(String coachId, String studentId,
//			String examType, String courseId, String coursePeriod,
//			String price, String learnTime, String aboardLge, String aboardLae,
//			String aboardName, String tokenId) {
//		ReqResult result = ReqResult.getSuccess();
//		if (!redisUtil.isExist(REDISKEY.STUDENT_TOKEN + studentId, tokenId)) {
//			result.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
//			result.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
////			return result;
//		}
//		try {
//			OrderVo orderVo = new OrderVo();
//			String orderId = StringUtil.getOrderId();
//			orderVo.setOrderId(orderId);
//			orderVo.setCoachId(Long.valueOf(coachId));
//			orderVo.setStudentId(Long.valueOf(studentId));
//			orderVo.setCourseId(courseId);
//			orderVo.setDltype(Integer.valueOf(examType));
//			// 如果没有时间默认为现在。
//			if (StringUtil.isNotNullAndNotEmpty(learnTime)) {
//				orderVo.setPstart(new Date(learnTime));
//			} else {
//				orderVo.setPstart(new Date());
//			}
//			orderVo.setPend(DateUtil.dateAfterMinute(orderVo.getPstart(),
//					OrderConstant.clztime * Integer.valueOf(coursePeriod)));
//			orderVo.setClzNum(Integer.valueOf(coursePeriod));
//			orderVo.setPrice(Integer.valueOf(price));
//			orderVo.setStuAddr(aboardName);
//			orderVo.setOrderState(OrderConstant.ORDERSTATE.GIVEORDER);
//			orderVo.setGtime(new Date());
//			orderService.addOrder(orderVo);
//			result.setData(orderId);
//			//成功发送提交定订单消息
//			Message msg=new Message();
//	    	msg.setKeys(orderId);
//	    	msg.setTopic(orderProducer.getCreateTopicKey());
//	    	msg.setTags(OrderConstant.RMQTAG.COMMITORDER);
//	    	msg.setBody(SerializableUtil.serialize(orderVo));
//	    	orderProducer.send(msg);
//		} catch (Exception e) {
//			result = ReqResult.getFailed();
//			log.error(coachId+",studentId="+studentId+" add order exception:"+e.getMessage(),e);
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
//		// 需要登录
//		if ("1".equals(ucancel.trim())) {
//			if (!redisUtil.isExist(REDISKEY.COACH_TOKEN + userId, tokenId)) {
//				result.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
//				result.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
//				return result;
//			}
//		} else if ("2".equals(ucancel.trim())) {
//			if (!redisUtil.isExist(REDISKEY.STUDENT_TOKEN + userId, tokenId)) {
//				result.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
//				result.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
//				return result;
//			}
//		} else {
//			result.setCode(ResultCode.ERRORCODE.PARAMERROR);
//			result.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
//			return result;
//		}
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
//		if (!redisUtil.isExist(REDISKEY.COACH_TOKEN + coachId, tokenId)) {
//			result.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
//			result.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
//			return result;
//		}
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
//		if (!redisUtil.isExist(REDISKEY.STUDENT_TOKEN + studentId, tokenId)) {
//			result.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
//			result.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
//			return result;
//		}
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
//		if (!redisUtil.isExist(REDISKEY.STUDENT_TOKEN + studentId, tokenId)) {
//			result.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
//			result.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
//			return result;
//		}
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
//	public ReqResult bookOrder(String coachId, String studentId,
//			String examType, String courseId, String price,
//			String coachClassId, String aboardLge, String aboardLae,
//			String aboardName, String tokenId) {
//		ReqResult result = ReqResult.getSuccess();
//		// 参数判断
//		if (StringUtil.isEmptyOrWhitespaceOnly(coachId)
//				|| StringUtil.isEmptyOrWhitespaceOnly(courseId)
//				|| StringUtil.isEmptyOrWhitespaceOnly(coachClassId)
//				|| StringUtil.isEmptyOrWhitespaceOnly(price)) {
//			result.setCode(ResultCode.ERRORCODE.PARAMERROR);
//		}
//		try {
//			String orderId = StringUtil.getOrderId();
//			String code = "0";//orderService.bookOrder();
//			result.setCode(code);
//			result.setData(orderId);
//		} catch (Exception e) {
//			result = ReqResult.getFailed();
//			log.error(coachId+",studentId="+studentId+" bookOrder Exception:"+e.getMessage(),e);
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
//		// 1.登录判断
//		if (!redisUtil.isExist(REDISKEY.STUDENT_TOKEN + studentId, tokenId)) {
//			result.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
//			result.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
////			return result;
//		}
//		// 2.参数判断
//		// ...
//		try {
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
//		// 1.登录判断
//		if (!redisUtil.isExist(REDISKEY.COACH_TOKEN + coachId, tokenId)) {
//			result.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
//			result.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
////			return result;
//		}
//		// 2.参数判断
//		// ...
//		try {
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
//		List<CommentTagVo> list=commentTageService.queryByCourseId(Integer.valueOf(courseId), new CommentTagQuery());
//		Object taglist=BeanCopy.getFieldList(list, "ctid");
//		@SuppressWarnings("unchecked")
//		List<StuCommentVo> scoreList=stuCommentService.queryByTags((List<Integer>)taglist, Long.valueOf(studentId));
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
//		// 1.登录判断
//				if (!redisUtil.isExist(REDISKEY.COACH_TOKEN + coachId, tokenId)) {
//					result.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
//					result.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
////					return result;
//				}
//		try {
//			CoachStatisticVo vo=coachStatisticService.getCoachStatistc(Long.valueOf(coachId), date);
//			DateStatisticVo data=new DateStatisticVo();
//				int total=vo.getOrderAccept()+vo.getOrderRefuse();
//				data.setOrderNum(vo.getOrderAccept());
//				data.setMoney(vo.getOrderMoney());
//				if(total!=0){
//					data.setPercent(vo.getOrderAccept()*1000/total);
//				} else {
//					data.setPercent(100);
//				}
//				if(vo.getOrderComment()>0){
//					data.setScore(vo.getCommentScore()/vo.getOrderComment());
//				} else {
//					//默认5分！
//					data.setScore(5);
//				}
//			result.setData(data);
//		} catch (Exception e) {
//			result = ReqResult.getFailed();
//			log.error(coachId+",date="+date+" getCoachStatistc Exception:"+e.getMessage(),e);
//		}
//		return result;
//	}
//}
