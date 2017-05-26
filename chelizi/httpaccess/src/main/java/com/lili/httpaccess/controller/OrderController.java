package com.lili.httpaccess.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CoachManager;
import com.lili.common.util.StringUtil;
import com.lili.common.util.VersionUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.logic.service.ICoachStateManager;
import com.lili.logic.service.IStudentStateManager;
import com.lili.logic.service.OrderLogic;
import com.lili.order.dto.InsuranceOrder;
import com.lili.order.service.CancelReasonService;
import com.lili.order.service.OrderService;
import com.lili.order.vo.CancelReasonQuery;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;


@Controller
@RequestMapping("/v1/orders")
public class OrderController {
	@Autowired
	private OrderLogic orderLogic;
	@Autowired
	private IStudentStateManager stateManager;
	@Autowired
	private ICoachStateManager coachStateManager;
	@Autowired
	private CancelReasonService cancelReasonService;
	@Autowired
	private StudentManager studentManager;
	@Autowired
	private OrderService orderService;
	@Autowired
	CoachManager coachManager;
	
	private Logger log = Logger.getLogger(OrderController.class);

	/**
	 * 学员下单//现约
	 * 
	 * @param coachId
	 *            教练id
	 * @param userId
	 *            学员id
	 * @param userType      
	 * @param driveType
	 *            驾考类型：枚举 C1,C2
	 * @param courseId
	 *            科目：当前教练订授课程范围内的科目
	 * @param coursePeriod
	 *            课时：枚举 1、2、3、4
	 * @param price
	 *            金额
	 * @param learnTime
	 *            上课时间：例如现在
	 * @param aboardLge
	 *            上车地点：经度
	 * @param aboardLae
	 *            上车地点：纬度
	 * @param aboardName
	 *            上车位置名称
	 * @param timestamp
	 *            时间戳
	 * @param sign
	 *            签名
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Object addOrder(@RequestParam String coachId,
			@RequestParam String userId, @RequestParam String userType, @RequestParam String carId,  @RequestParam String driveType,
			@RequestParam String courseId, @RequestParam String clzNum,
			@RequestParam String price, @RequestParam(required=false) String transPrice,
			@RequestParam String aboardLge, @RequestParam String aboardLae,
			@RequestParam String aboardName, @RequestParam String timestamp,
			@RequestParam(required=false) String couponId, @RequestParam(required=false) String couponName,
			@RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = new ReqResult();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			OrderVo ov = new OrderVo();
			ov.setCoachId(Long.parseLong(coachId));
			ov.setStudentId(Long.parseLong(userId));
			ov.setCarId(Integer.parseInt(carId));
			ov.setCourseId(courseId);
			ov.setDltype(Integer.parseInt(driveType));
			ov.setClzNum(Integer.parseInt(clzNum));
			ov.setLge(Double.parseDouble(aboardLge));
			ov.setLae(Double.parseDouble(aboardLae));
			ov.setStuAddr(aboardName);
			ov.setPrice(Integer.parseInt(price));
			//20160830 接送费独立
			if(null != transPrice && !"".equals(transPrice)){
				ov.setTransPrice(Integer.parseInt(transPrice));
				ov.setNeedTrans(1);
			}
			if(null != couponId && !"".equals(couponId)){
				try {
					ov.setCoupon(Long.parseLong(couponId));
				} catch (Exception e) {
					log.error("controller: order post order failed, couponId type error." + e.getMessage(), e);
				}
			}
			if(null != couponName && !"".equals(couponName)){
				ov.setCouponName(couponName);
			}
			
			Coach coach = coachManager.getCoachInfo(Long.parseLong(coachId));
			if (coach != null) {
				if(!courseId.equals("5")&&!courseId.equals("15")&&!courseId.equals("2")&&!courseId.equals("12")&&!courseId.equals("4")&&!courseId.equals("14")){//不是陪驾
					if(coach.getIsImport()!=1){//新注册教练
						r.setCode(ResultCode.ERRORCODE.ORDER_NO_PRIVILEGE);
					    r.setMsgInfo(ResultCode.ERRORINFO.ORDER_NO_PRIVILEGE);
						return r.getResult();
					}
				}
			}
			r = stateManager.handleAddOrder(userId, ov, tokenId);
			//r = orderLogic.addOrder(ov, tokenId);
		} catch (Exception e) {
			log.error("controller: order post order failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}
	/**
	 * 学员下单//预约
	 * 
	 * @param coachId
	 *            教练id
	 * @param userId
	 *            学员id
	 * @param userType
	 * @param driveType
	 *            驾考类型：枚举 C1,C2
	 * @param courseId
	 *            科目：当前教练订授课程范围内的科目
	 * @param coachClassId
	 *            教练排班id
	 * @param price
	 *            金额
	 * @param learnTime
	 *            上课时间：例如现在
	 * @param aboardLge
	 *            上车地点：经度
	 * @param aboardLae
	 *            上车地点：纬度
	 * @param aboardName
	 *            上车位置名称
	 * @param timestamp
	 *            时间戳
	 * @param sign
	 *            签名
	 * @return
	 */
	@RequestMapping(value = "/book", method = RequestMethod.POST)
	public Object bookOrder(@RequestParam String coachId,
			@RequestParam String userId, @RequestParam String userType, @RequestParam String ccid,
			@RequestParam String courseId, @RequestParam String driveType,
			@RequestParam String price, @RequestParam String timestamp,
			@RequestParam(required=false) String couponId, @RequestParam(required=false) String couponName,
			@RequestParam String sign, @RequestHeader HttpHeaders headers, @RequestParam(required = false) String v) {
		ReqResult r = new ReqResult();
		try {
			
			//20151214如果已经是考试通过了，则不允许再预约
			Student s = studentManager.getStudentInfo(Long.parseLong(userId));
			
			int c1 = s.getCourse1() == null ? 0 : s.getCourse1();
			int c2 = s.getCourse2() == null ? 0 : s.getCourse2();
			int c3 = s.getCourse3() == null ? 0 : s.getCourse3();
			int c4 = s.getCourse4() == null ? 0 : s.getCourse4();
			int courseInt = Integer.parseInt(courseId.trim());
			
			
			 //20160624付费预约，只针对1.8.0及更高版本有效，只针对喱喱学员有效，喱喱学员不返回我的教练列表
    		if((null != v && !"".equals(v) && (VersionUtil.compareVersion(v, "1.7.0") > 0))) {
    			//是否有未付款订单，如有则不准许再预约
    			if (s.getIsImport() == (byte) 0) { //如果是喱喱学员
    				OrderQuery query=new OrderQuery();
    				query.setGroupBy("and pay_state=0 and order_state !=0");//未付款、未取消
    				List<OrderVo> list=orderService.queryByStudentId(Long.parseLong(userId), query);
    				if(list != null && list.size() > 0){
    					r.setCode(ResultCode.ERRORCODE.ORDER_IS_NOT_PAY);
    					r.setMsgInfo(ResultCode.ERRORINFO.ORDER_IS_NOT_PAY);
    					return r.getResult();
    				}
    				
    				if(!(VersionUtil.compareVersion(v, "2.1.0") >= 0)){
        			if (c1 < 90) {//未考过科目一
    					//如果报名无流水/新用户（无报名），只能预约科目二基础
    					if(s.getFlowNo() == null || "".equals(s.getFlowNo())) {
    						if(s.getApplyexam() > 2){//已报名无流水
    							//科目三所有
    							if (courseInt == ReqConstants.COURSE_TYPE_C1_THREE_BASIC 
        		    					||courseInt ==ReqConstants.COURSE_TYPE_C1_THREE_EXAM_SIMULATION
        		    					||courseInt ==ReqConstants.COURSE_TYPE_C1_THREE_EXAM_TRAINING
        		    					||courseInt ==ReqConstants.COURSE_TYPE_C2_THREE_BASIC
        		    					||courseInt ==ReqConstants.COURSE_TYPE_C2_THREE_EXAM_SIMULATION
        		    					||courseInt ==ReqConstants.COURSE_TYPE_C2_THREE_EXAM_TRAINING ) {
        							r.setCode(ResultCode.ERRORCODE.ORDER_IS_NOT_PASE_COURSER_ONE);
        							r.setMsgInfo(ResultCode.ERRORINFO.ORDER_IS_NOT_PASE_COURSER_ONE); //考过科目一，就可以约科目三啦
        							return r.getResult();
    							}
    							//科目二应试、模拟
    							if(courseInt == ReqConstants.COURSE_TYPE_C1_TWO_EXAM_SIMULATION 
        		    					||courseInt ==ReqConstants.COURSE_TYPE_C1_TWO_EXAM_TRAINING
        		    					||courseInt ==ReqConstants.COURSE_TYPE_C2_TWO_EXAM_SIMULATION
        		    					||courseInt ==ReqConstants.COURSE_TYPE_C2_TWO_EXAM_TRAINING) {
    								r.setCode(ResultCode.ERRORCODE.ORDER_IS_NOT_PASE);
        							r.setMsgInfo(ResultCode.ERRORINFO.ORDER_IS_NOT_PASE); //您的报名还在受理中，报名成功后就可以约啦
        							return r.getResult();
    							}
    						}
    						else {//未报名
    							if (courseInt == ReqConstants.COURSE_TYPE_C1_TWO_EXAM_SIMULATION 
        		    					||courseInt ==ReqConstants.COURSE_TYPE_C1_TWO_EXAM_TRAINING
        		    					||courseInt ==ReqConstants.COURSE_TYPE_C2_TWO_EXAM_SIMULATION
        		    					||courseInt ==ReqConstants.COURSE_TYPE_C2_TWO_EXAM_TRAINING
        		    					||courseInt == ReqConstants.COURSE_TYPE_C1_THREE_BASIC 
        		    					||courseInt ==ReqConstants.COURSE_TYPE_C1_THREE_EXAM_SIMULATION
        		    					||courseInt ==ReqConstants.COURSE_TYPE_C1_THREE_EXAM_TRAINING
        		    					||courseInt ==ReqConstants.COURSE_TYPE_C2_THREE_BASIC
        		    					||courseInt ==ReqConstants.COURSE_TYPE_C2_THREE_EXAM_SIMULATION
        		    					||courseInt ==ReqConstants.COURSE_TYPE_C2_THREE_EXAM_TRAINING ) {
    								//引导报名
    								r.setCode(ResultCode.ERRORCODE.USER_IS_NOT_BM);
        							r.setMsgInfo(ResultCode.ERRORINFO.USER_IS_NOT_BM); //请先报名
        							return r.getResult();
    							}
    						}
    					}
    					else {//如果报名有流水，只能预约科目二基础、应试、模拟
    						if (courseInt == ReqConstants.COURSE_TYPE_C1_THREE_BASIC 
    		    					||courseInt ==ReqConstants.COURSE_TYPE_C1_THREE_EXAM_SIMULATION
    		    					||courseInt ==ReqConstants.COURSE_TYPE_C1_THREE_EXAM_TRAINING
    		    					||courseInt ==ReqConstants.COURSE_TYPE_C2_THREE_BASIC
    		    					||courseInt ==ReqConstants.COURSE_TYPE_C2_THREE_EXAM_SIMULATION
    		    					||courseInt ==ReqConstants.COURSE_TYPE_C2_THREE_EXAM_TRAINING ) {
    							r.setCode(ResultCode.ERRORCODE.ORDER_IS_NOT_PASE_COURSER_ONE);
    		    				r.setMsgInfo(ResultCode.ERRORINFO.ORDER_IS_NOT_PASE_COURSER_ONE); //考过科目一，就可以约科目三啦
    		    				return r.getResult();
    						}
    					}
    				}
    				}
    			}
    			else {//驾校学员  有未付款的现约订单，也不让去预约
    				//modify by devil 20161106 现在BM班是有可能要给钱的 pay_state可能等于0
    				OrderQuery query=new OrderQuery();
    				if(VersionUtil.compareVersion(v, "2.1.0") >= 0){
    					query.setGroupBy("and pay_state=0 and order_state !=0 ");//未付款、未取消 现约
    				}else{
    					query.setGroupBy("and pay_state=0 and order_state !=0 and otype=1");//未付款、未取消 现约
    				}
    				
    				
    				List<OrderVo> list=orderService.queryByStudentId(Long.parseLong(userId), query);
    				if(list != null && list.size() > 0){
    					r.setCode(ResultCode.ERRORCODE.ORDER_IS_NOT_PAY);
    					r.setMsgInfo(ResultCode.ERRORINFO.ORDER_IS_NOT_PAY);
    					return r.getResult();
    				}
    				
    			}
    		}
			
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			OrderVo ov = new OrderVo();
			ov.setCoachId(Long.parseLong(coachId));
			ov.setStudentId(Long.parseLong(userId));
			//ov.setCarId(Integer.parseInt(carId));
			ov.setCourseId(courseId);
			ov.setDltype(Integer.parseInt(driveType));
			ov.setCcid(Integer.parseInt(ccid));
			ov.setPrice(Integer.parseInt(price));
			ov.setStuAddr("");
			//不需要进行版本号校验，付费预约增加优惠券
			if(null != couponId && !"".equals(couponId)){ 
				try {
					ov.setCoupon(Long.parseLong(couponId));
				} catch (Exception e) {
					log.error("controller: order post order failed, couponId type error." + e.getMessage(), e);
				}
			}
			if(null != couponName && !"".equals(couponName)){
				ov.setCouponName(couponName);
			}
			r = orderLogic.bookOrder(ov, tokenId);

		} catch (Exception e) {
			log.error("controller: order post book order failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}

	/**
	 * 教练接单
	 * 
	 * @param orderId
	 * @param userId
	 * @param userType
	 * @param studentId
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{orderId}/accept", method = RequestMethod.POST)
	public Object acceptOrder(@PathVariable String orderId,
			@RequestParam String userId, @RequestParam String userType, @RequestParam String studentId,
			@RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers) {
		ReqResult r = new ReqResult();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = coachStateManager.handleAcceptOrder(orderId, userId, studentId, tokenId);
			//r = orderLogic.acceptOrder(orderId, userId, studentId, tokenId);
			if (r.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS))
            {
			    stateManager.handleAcceptOrder(orderId, userId, studentId, tokenId);
            }
		} catch (Exception e) {
			log.error("controller: order post accept order failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 教练拒单
	 * @param orderId
	 * @param userId
	 * @param userType
	 * @param reason
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "/{orderId}/refuse", method = RequestMethod.POST)
	public Object refuseOrder(@PathVariable String orderId,
			@RequestParam String userId, @RequestParam String userType, @RequestParam String reason,
			@RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers) {
		ReqResult r = new ReqResult();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = coachStateManager.handleRefuseOrder(orderId, userId, reason, tokenId);
			//r = orderLogic.refuseOrder(orderId,userId, reason, tokenId);
			if (r.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS))
            {
                stateManager.handleRefuseOrder(orderId, userId, reason, tokenId);
            }
		} catch (Exception e) {
			log.error("controller: order post refuse order failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 取消订单
	 * @param orderId
	 * @param retype 取消原因类型：1.懒得等，2.订单错误，2.我时间冲突 3.对方态度不好, 4.对方有事
	 * @param reseaon 原因描述
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "/{orderId}/cancel", method = RequestMethod.POST)
	public Object cancelOrder(@PathVariable String orderId,@RequestParam String retype, @RequestParam String reason, 
			@RequestParam String userId,@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = new ReqResult();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
		
//			if (userType.equals("2")) //如果是学员取消
//            {
//			    r = stateManager.handleCancelOrder(orderId, retype, reason, userId, userType, tokenId, false);
//            }
//			else
//            {
			    r = orderLogic.cancelOrder(orderId, retype, reason,userId,userType,tokenId);
//			    r = coachStateManager.handleCancelOrder(orderId, retype, reason, userId, userType, tokenId, false);
//            }
		} catch (Exception e) {
			log.error("controller: order post cancel order failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}
	
	
	@RequestMapping(value = "/cancelReason", method = RequestMethod.GET)
	public Object getCancelReason(@RequestParam String userId,@RequestParam String userType, 
			 @RequestParam String pageNo, @RequestParam String pageSize,@RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			CancelReasonQuery crq = new CancelReasonQuery();
			crq.setPageIndex(Integer.parseInt(pageNo));
			crq.setPageSize(Integer.parseInt(pageSize));
			Object o = cancelReasonService.queryByUtype(Integer.parseInt(userType), crq);
			r.setData(o);
		} catch (Exception e) {
			log.error("controller: get cancelReasonService failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}

	/**
	 * 获取续课单价
	 * @param orderId：原单
	 * @param clzNum：续课节数,可以为空
	 * @param tokenId
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{orderId}/more/unitPrice", method = RequestMethod.GET)
	public Object moreOrderPrice(@PathVariable String orderId,
			@RequestParam String userId, @RequestParam String userType, @RequestParam String clzNum,
			@RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers) {
		ReqResult r = new ReqResult();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = orderLogic.continuePrice(orderId,userId, clzNum, tokenId);
		} catch (Exception e) {
			log.error("controller: order get unitprice failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}
	
	/**
	 * 续课
	 * @param orderId：原单
	 * @param clzNum：续课节数
	 * @param price：续课总价
	 * @param tokenId
	 * @param studentId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{orderId}/more", method = RequestMethod.POST)
	public Object moreOrder(@PathVariable String orderId,
			@RequestParam String userId, @RequestParam String userType, @RequestParam String clzNum,@RequestParam String price,
			@RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers) {
		ReqResult r = new ReqResult();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = orderLogic.continueOrder(orderId,userId, clzNum, price, tokenId);
		} catch (Exception e) {
			log.error("controller: order post more order failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 根据相关参数获取推送消息的对象体
	 * @param userId
	 * @param userType
	 * @param operate
	 * @param timestamp
	 * @param sign
	 * @return data为推送消息的对象体，目前可能为OrderVo,List<OrderVo>或者CoachClassVo
	 */
	@RequestMapping(value = "/jpushObject", method = RequestMethod.GET)
	@ResponseBody
	public Object getJpushObject(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String operate,@RequestParam String jpushStamp,
			@RequestParam String timestamp,@RequestParam String sign){
		ReqResult r = new ReqResult();
		
		try {
			r = orderLogic.getJpushObject(Long.parseLong(userId.trim()), 
					Integer.parseInt(userType.trim()), Integer.parseInt(operate.trim()), jpushStamp);
		} catch (Exception e) {
			log.error("controller: order jpushObject failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 获取接送费价格
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/transPrice", method = RequestMethod.GET)
	@ResponseBody
	public Object getTransPrice(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign){
		ReqResult r =ReqResult.getSuccess();
		try {
			// 20161221 接送费改为40元
			r.setData("4000");
			
			
		} catch (Exception e) {
			log.error("controller: order transPrice failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 平安保险订单保存
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/insurance/save", method = RequestMethod.POST)
	@ResponseBody
	public Object saveInsuranceOrder(InsuranceOrder insuranceOrder){
		ReqResult r =ReqResult.getSuccess();
		try {
			r=orderService.saveInsuranceOrder(insuranceOrder);
			
		} catch (Exception e) {
			log.error("controller: saveInsuranceOrder=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 平安保险订单查询
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/insurance/searchInsuranceById", method = RequestMethod.GET)
	@ResponseBody
	public Object searchInsuranceById(@RequestParam String userId){
		ReqResult r =ReqResult.getSuccess();
		try {
			r=orderService.searchInsuranceById(userId);
			
		} catch (Exception e) {
			log.error("controller: searchInsuranceById=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}

}
