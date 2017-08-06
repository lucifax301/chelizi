package com.lili.httpaccess.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.coach.manager.CoachManager;
import com.lili.coach.manager.RegionManager;
import com.lili.common.util.Page;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.exam.dto.ExamPlace;
import com.lili.exam.dto.ExamPlaceCity;
import com.lili.exam.dto.ExamPlaceClass;
import com.lili.exam.dto.ExamPlaceClassVo;
import com.lili.exam.dto.ExamPlaceOrder;
import com.lili.exam.dto.ExamPlacePayOrder;
import com.lili.exam.dto.ExamPlaceVo;
import com.lili.exam.manager.ExamPlaceClassManager;
import com.lili.exam.manager.ExamPlaceManager;
import com.lili.exam.manager.ExamPlaceOrderManager;

@Controller
@RequestMapping("/v1/examPlace")
public class ExamPlaceController {
	private Logger log = Logger.getLogger(ExamPlaceController.class);
	@Autowired
	private ExamPlaceManager examPlaceManager;
	@Autowired
	private RegionManager regionManager;
	@Autowired
	private CoachManager coachManager;
	@Autowired
	private ExamPlaceClassManager examPlaceClassManager;
	@Autowired
	private ExamPlaceOrderManager examPlaceOrderManager;
	
	
	@RequestMapping(value = "/timestamp", method = RequestMethod.GET)
	@ResponseBody
	public Object getTime() {
		ReqResult r = new ReqResult();
		
		try {
			Object o =System.currentTimeMillis()/1000L;
			r.setCode(ResultCode.ERRORCODE.SUCCESS);
			r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
			r.setData(o);
		} catch (Exception e) {
			log.error("controller: authcode get timestamp failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 根据城市id获取考场信息
	 * @param userId
	 * @param userType
	 * @param v
	 * @param timestamp
	 * @param sign
	 * @param cityId	城市id
	 * @param type		科目：2-科目二；3-科目三
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public Object getExamPlace(
			@RequestParam String userId, 
			@RequestParam String userType,
			@RequestParam(required=false) String v,
			@RequestParam String timestamp,
			@RequestParam String sign,
			@RequestParam String cityId,
			@RequestParam String type
			) {
		ReqResult r = ReqResult.getSuccess();
		try {
			List<ExamPlaceVo> data =examPlaceManager.getExamPlaces(cityId,type);
			r.setData(data);
		} catch (Exception e) {
			log.error("controller: authcode get timestamp failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 根据特定key获取相关驾校的考场集合
	 * @param userId
	 * @param userType
	 * @param v
	 * @param timestamp
	 * @param sign
	 * @param cityId
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/sp", method = RequestMethod.GET)
	@ResponseBody
	public Object getExamPlaceBySPKey(
			@RequestParam String userId, 
			@RequestParam String userType,
			@RequestParam(required=false) String v,
			@RequestParam String timestamp,
			@RequestParam String sign,
			@RequestParam String examkey
			) {
		ReqResult r = ReqResult.getSuccess();
		try {
			List<ExamPlace> data =examPlaceManager.getExamPlaceBySPKey(examkey);
			r.setData(data);
		} catch (Exception e) {
			log.error("controller: authcode get timestamp failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 获取指定日期考场的排班信息
	 * @param userId
	 * @param userType
	 * @param v
	 * @param timestamp
	 * @param sign
	 * @param placeId	考场id
	 * @param pdate		预约日期。格式：2016-09-12
	 * @return
	 */
	@RequestMapping(value = "/class", method = RequestMethod.GET)
	@ResponseBody
	@Deprecated
	public Object getExamPlaceClass(
			@RequestParam String userId, 
			@RequestParam String userType,
			@RequestParam(required=false) String v,
			@RequestParam String timestamp,
			@RequestParam String sign,
			@RequestParam String placeId,
			@RequestParam String pdate
			) {
		ReqResult r = ReqResult.getSuccess();
		try {
			List<ExamPlaceClass> data = examPlaceClassManager.getExamPlaceClass(placeId,pdate);
			r.setData(data);
		} catch (Exception e) {
			log.error("controller: get getExamPlaceClass failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	
	/**
	 * 根据用户情况返回相应课程信息
	 * @param userId
	 * @param userType
	 * @param v
	 * @param timestamp
	 * @param sign
	 * @param placeId	考场id
	 * @param pdate		预约日期。格式：2016-09-12
	 * @param drtype	准驾车型。1-C1;2-C2
	 * @return
	 */
	@RequestMapping(value = "/class/info", method = RequestMethod.GET)
	@ResponseBody
	public Object getExamPlaceClassInfo(
			@RequestParam String userId, 
			@RequestParam String userType,
			@RequestParam(required=false) String v,
			@RequestParam String timestamp,
			@RequestParam String sign,
			@RequestParam String placeId,
			@RequestParam String pdate,
			@RequestParam String drtype
			) {
		ReqResult r = ReqResult.getSuccess();
		try {
			List<ExamPlaceClassVo> data = examPlaceClassManager.getExamPlaceClassInfo(userId,userType,placeId,pdate,drtype);
			r.setData(data);
		} catch (Exception e) {
			log.error("controller: get getExamPlaceClass failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	@RequestMapping(value = "/car/list", method = RequestMethod.GET)
	@ResponseBody
	public Object getExamPlaceClassCar(
			@RequestParam String userId, 
			@RequestParam String userType,
			@RequestParam(required=false) String v,
			@RequestParam String timestamp,
			@RequestParam String sign,
			@RequestParam String placeId,
			@RequestParam String pdate,
			@RequestParam String drtype
			) {
		ReqResult r = ReqResult.getSuccess();
		try {
			Map data = examPlaceClassManager.getExamPlaceCarClassInfo(userId, userType, placeId, pdate, drtype);
			r.setData(data);
		} catch (Exception e) {
			log.error("controller: get getExamPlaceClass failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 生成约考场订单
	 * @param userId
	 * @param userType
	 * @param v
	 * @param timestamp
	 * @param sign
	 * @param classId	排班id。多个用逗号,隔开
	 * @param drtype	准驾车型。1-C1;2-C2
	 * @return			如果是多个排班一起提交，则返回对应的多个订单号
	 */
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	@ResponseBody
	public Object addExamPlaceOrder(
			@RequestParam String userId, 
			@RequestParam String userType,
			@RequestParam(required=false) String v,
			@RequestParam String timestamp,
			@RequestParam String sign,
			@RequestParam String classId,
			@RequestParam String drtype,
			@RequestParam(required = false) String carNo,
			@RequestParam String placeId
			) {
		ReqResult r = ReqResult.getSuccess();
		try {
			if(carNo!=null)
				r = examPlaceOrderManager.addExamPlaceOrder(userId,userType,classId,drtype,carNo);
			else
				r = examPlaceOrderManager.addCarModelExamPlaceOrder(userId, userType, classId, drtype, placeId);
		} catch (Exception e) {
			log.error("controller: get getExamPlaceClass failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 教练获取我的约考场订单
	 * @param userId
	 * @param userType
	 * @param v
	 * @param timestamp
	 * @param sign
	 * @param state		0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭。查询全部传空。
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	@ResponseBody
	public Object getMyExamPlaceOrder(
			@RequestParam String userId, 
			@RequestParam String userType,
			@RequestParam(required=false) String v,
			@RequestParam String timestamp,
			@RequestParam String sign,
			@RequestParam String state,
			@RequestParam String pageNo,
			@RequestParam String pageSize,
			@RequestParam(required=false) String carmodel

			) {
		ReqResult r = ReqResult.getSuccess();
		try {
			if("1".equals(carmodel)){
				Page<ExamPlacePayOrder> data = examPlaceOrderManager.getMyExamPlacePayOrder(userId,userType,state,pageNo,pageSize);
				r.setData(data);
			}else{
				Page<ExamPlaceOrder> data = examPlaceOrderManager.getMyExamPlaceOrder(userId,userType,state,pageNo,pageSize);
				r.setData(data);
			}
		} catch (Exception e) {
			log.error("controller: get getExamPlaceClass failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 根据订单id获取订单
	 * @param orderId 订单id。多个用逗号,隔开
	 * @param userId
	 * @param userType
	 * @param v
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/order/info", method = RequestMethod.GET)
	@ResponseBody
	public Object getExamPlaceOrder(
			@RequestParam String userId, 
			@RequestParam String userType,
			@RequestParam(required=false) String v,
			@RequestParam String timestamp,
			@RequestParam String sign,
			@RequestParam String orderId
			) {
		ReqResult r = ReqResult.getSuccess();
		try {
			List<ExamPlaceOrder> data = examPlaceOrderManager.getExamPlaceOrder(orderId);
			r.setData(data);
		} catch (Exception e) {
			log.error("controller: get getExamPlaceClass failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 取消排班订单
	 * @param userId
	 * @param userType
	 * @param v
	 * @param timestamp
	 * @param sign
	 * @param orderId	订单id。多个用逗号,隔开
	 * @return
	 */
	@RequestMapping(value = "/order/cancel", method = RequestMethod.POST)
	@ResponseBody
	public Object cancelExamPlaceClassOrder(
			@RequestParam String userId, 
			@RequestParam String userType,
			@RequestParam(required=false) String v,
			@RequestParam String timestamp,
			@RequestParam String sign,
			@RequestParam String orderId,
			@RequestParam(required=false)  String carmodel
			) {
		ReqResult r = ReqResult.getSuccess();
		try {
			if("1".equals(carmodel)){
				r = examPlaceOrderManager.cancelCarExamPlaceClassOrder(userId,userType,orderId);
			}else{
				r = examPlaceOrderManager.cancelExamPlaceClassOrder(userId,userType,orderId);
			}
		} catch (Exception e) {
			log.error("controller: get cancelExamPlaceClassOrder failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 预支付排班订单（如果金额是0，则直接支付成功；否则客户端调用通用支付接口进行支付）
	 * @param userId
	 * @param userType
	 * @param v
	 * @param timestamp
	 * @param sign
	 * @param orderId	订单id。多个用逗号,隔开
	 * @return
	 */
	@RequestMapping(value = "/order/pay", method = RequestMethod.POST)
	@ResponseBody
	public Object payExamPlaceClassOrder(
			@RequestParam String userId, 
			@RequestParam String userType,
			@RequestParam(required=false) String v,
			@RequestParam String timestamp,
			@RequestParam String sign,
			@RequestParam String orderId
			) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = examPlaceOrderManager.payExamPlaceClassOrder(userId,userType,orderId);
		} catch (Exception e) {
			log.error("controller: get cancelExamPlaceClassOrder failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 获取约考场城市
	 * @param userId
	 * @param userType
	 * @param v
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/city", method = RequestMethod.GET)
	@ResponseBody
	public Object getExamPlaceCity(
			@RequestParam String userId, 
			@RequestParam String userType,
			@RequestParam(required=false) String v,
			@RequestParam String timestamp,
			@RequestParam String sign
			) {
		ReqResult r = ReqResult.getSuccess();
		try {
			List<ExamPlaceCity> data = examPlaceManager.getExamPlaceCity();
			r.setData(data);
		} catch (Exception e) {
			log.error("controller: get cancelExamPlaceClassOrder failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	
}


























