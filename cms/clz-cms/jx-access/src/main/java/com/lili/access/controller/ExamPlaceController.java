package com.lili.access.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.lili.cms.constant.Constant;
import com.lili.cms.entity.ResponseMessage;
import com.lili.coach.dto.Region;
import com.lili.coach.dto.School;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.manager.RegionManager;
import com.lili.common.util.Page;
import com.lili.common.vo.ReqResult;
import com.lili.exam.dto.ExamPlace;
import com.lili.exam.dto.ExamPlaceClass;
import com.lili.exam.dto.ExamPlaceClassDate;
import com.lili.exam.dto.ExamPlaceOrder;
import com.lili.exam.dto.ExamPlaceRechargeGears;
import com.lili.exam.dto.ExamPlaceRechargeSchool;
import com.lili.exam.dto.ExamPlaceWhitelist;
import com.lili.exam.manager.ExamPlaceClassManager;
import com.lili.exam.manager.ExamPlaceManager;
import com.lili.exam.manager.ExamPlaceOrderManager;
import com.lili.exam.manager.ExamPlaceRechargeManager;
import com.lili.exam.manager.ExamPlaceWhitelistManager;
import com.lili.user.model.User;

@Controller
@Scope("prototype")
@RequestMapping("/examPlace")
public class ExamPlaceController extends BaseController{
	
	Logger log = Logger.getLogger(ExamPlaceController.class);
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
	@Autowired
	private ExamPlaceWhitelistManager examPlaceWhitelistManager;
	@Autowired
	private ExamPlaceRechargeManager examPlaceRechargeManager;
	
	
	/**
	 * 测试，获取服务器时间
	 * @return
	 */
	@RequestMapping(value = "/timestamp", method = RequestMethod.GET)
	@ResponseBody
	public Object getTime() {
		ResponseMessage res = new ResponseMessage<>();
		try {
			Object o =System.currentTimeMillis()/1000L;
			res.addResult("pageData", o);
		} catch (Exception e) {
			log.error("controller: authcode get timestamp failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		
		return res.build();
	}

	
	/**
	 * 添加考场信息
	 * @param name	考场名称
	 * @param type	考场类型：2-科目二；3-科目三；多个用逗号隔开
	 * @param area	面积
	 * @param mobile	联系电话
	 * @param cityId	所属城市id;如深圳：100100
	 * @param schoolId  所属驾校id；如深圳广仁：5
	 * @param address	地址
	 * @param state		状态：0-启用；1-禁用
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public Object addExamPlaces(@RequestParam String name,@RequestParam String type,
			@RequestParam String area, @RequestParam String mobile,
			@RequestParam String cityId, @RequestParam String schoolId,
			@RequestParam String address, @RequestParam String state
			) {
		ResponseMessage res = new ResponseMessage<>();
		try {
			ExamPlace ep = new ExamPlace();
			ep.setName(name.trim());
			ep.setType(type.trim());
			ep.setArea(Integer.parseInt(area));
			ep.setMobile(mobile.trim());
			ep.setAddress(address.trim());
			ep.setState(Byte.parseByte(state.trim()));
			ep.setCityId(Integer.parseInt(cityId));
			Region region = regionManager.getRegionInfo(Integer.parseInt(cityId));
			ep.setCity(region.getRegion());
			ep.setSchoolId(Integer.parseInt(schoolId));
			School school = coachManager.getSchool(Integer.parseInt(schoolId));
			ep.setSchool(school.getName());
			ep.setCtime(new Date());
			int result = examPlaceManager.addExamPlace(ep);
			res.addResult("pageData", result);
		} catch (Exception e) {
			log.error("controller: ExamPlaceController getExamPlaces failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
	}
	
	
	/**
	 * 分页获取考场信息
	 * @param name		考场名称
	 * @param cityId	所属城市id
	 * @param schoolId	所属驾校id
	 * @param pageNo	分页第几页
	 * @param pageSize	分页每页大小
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public Object getExamPlaces(HttpServletRequest request, HttpServletResponse response) {
		
		ResponseMessage res = new ResponseMessage<>();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(Constant.USER_SESSION);
		Long schoolId =  user.getSchoolId();
		try {
			Page<ExamPlace> pageData = examPlaceManager.getExamPlaces(null, null, String.valueOf(schoolId), null, null);
			res.addResult("pageData", pageData);
		} catch (Exception e) {
			log.error("controller: ExamPlaceController getExamPlaces failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
	}
	
	/**
	 * 更新考场信息
	 * @param placeId	考场id
	 * @param name	考场名称
	 * @param type	考场类型：2-科目二；3-科目三；多个用逗号隔开
	 * @param area	面积
	 * @param mobile	联系电话
	 * @param cityId	所属城市id;如深圳：100100
	 * @param schoolId  所属驾校id；如深圳广仁：5
	 * @param address	地址
	 * @param state		状态：0-启用；1-禁用
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Object updateExamPlaces(@RequestParam String name,@RequestParam String type,
			@RequestParam String area, @RequestParam String mobile,
			@RequestParam String cityId, @RequestParam String schoolId,
			@RequestParam String address, @RequestParam String state,@RequestParam String placeId
			) {
		ResponseMessage res = new ResponseMessage<>();
		try {
			ExamPlace ep = new ExamPlace();
			ep.setName(name.trim());
			ep.setType(type.trim());
			ep.setArea(Integer.parseInt(area));
			ep.setMobile(mobile.trim());
			ep.setAddress(address.trim());
			ep.setState(Byte.parseByte(state.trim()));
			ep.setCityId(Integer.parseInt(cityId));
			Region region = regionManager.getRegionInfo(Integer.parseInt(cityId));
			ep.setCity(region.getRegion());
			ep.setSchoolId(Integer.parseInt(schoolId));
			School school = coachManager.getSchool(Integer.parseInt(schoolId));
			ep.setSchool(school.getName());
			ep.setId(Integer.parseInt(placeId.trim()));
			int result = examPlaceManager.updateExamPlace(ep);
			res.addResult("pageData", result);
		} catch (Exception e) {
			log.error("controller: ExamPlaceController update failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
	}
	
	/**
	 * 排班管理--新增排班
	 * @param placeId	考场id
	 * @param pdate		预约日期。格式：2016-09-12。多个用逗号,隔开
	 * @param pstart	预约时间段。格式：08:00:00-09:00:00,10:00:00-11:00:00。多个用逗号,隔开
	 * @param outerPrice	外部价格。单位分
	 * @param innerPrice	内部价格。单位分
	 * @param minHours		最小预约时间段。整型。
	 * @param c1			最小时间段内c1车辆数目
	 * @param c2			最小时间段内c2车辆数目
	 * @param c1inner		c1中预留给内部
	 * @param c2inner		c2中预留给内部
	 * @param innerExpire	预留给内部失效时间。整型。单位天
	 * @param favorType		优惠配置：1-返课时；2-返金额
	 * @param favorIn		优惠配置累计条件：整型。单位小时
	 * @param favorOut		优惠配置返还设置：整型。单位学时或元
	 * @return
	 */
	@RequestMapping(value = "/class", method = RequestMethod.POST)
	@ResponseBody
	public Object addExamPlaceClass(
			@RequestParam String placeId,
			@RequestParam String pdate,
			@RequestParam String pstart,
			@RequestParam String outerPrice, 
			@RequestParam String innerPrice,
			@RequestParam String minHours, 
			@RequestParam String c1,
			@RequestParam String c2, 
			@RequestParam String c1inner,
			@RequestParam String c2inner,
			@RequestParam String innerExpire,
			@RequestParam String favorType, 
			@RequestParam String favorIn,
			@RequestParam String favorOut
			) {
		ResponseMessage res = new ResponseMessage<>();
		try {
			int intC1 = Integer.parseInt(c1.trim());
			int intC2 = Integer.parseInt(c2.trim());
			int intC1inner = Integer.parseInt(c1inner.trim());
			int intC2inner = Integer.parseInt(c2inner.trim());
			if(intC1 < intC1inner || intC2< intC2inner || intC1inner<0 || intC2inner<0 ){
				res.addResult("pageData", null);
				res.setCode(-1);
				res.setMsg("车辆数量设置错误！");
				return res.build();
			}
			int intMinHours = Integer.parseInt(minHours.trim());
			String[] pdates = pdate.split(",");//2016-09-12,2016-09-13
			String[] pstarts = pstart.split(",");//08:00:00-09:00:00,10:00:00-11:00:00
			for(int i=0;i<pdates.length;i++){
				for(int j=0;j<pstarts.length;j++){
					String[] ptime = pstarts[j].split("-");
			    	Date d0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pdates[i] + " "+ ptime[0]);
			    	Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pdates[i] + " "+ ptime[1]);
			    	if(d0.after(d1)){
						res.addResult("pageData", null);
						res.setCode(-1);
						res.setMsg("预约时段设置错误！");
						return res.build();
			    	}
			    	long diff = d1.getTime() - d0.getTime();
			    	long hour = diff/(1000*60*60);
			    	if(hour < intMinHours){
						res.addResult("pageData", null);
						res.setCode(-1);
						res.setMsg("预约时段设置错误！");
						return res.build();
			    	}
			    	
					ExamPlaceClass record = new ExamPlaceClass();
					record.setPlaceId(Integer.parseInt(placeId.trim()));
					record.setOuterPrice(Integer.parseInt(outerPrice.trim()));
					record.setInnerPrice(Integer.parseInt(innerPrice.trim()));
					record.setMinHours(intMinHours);
					record.setC1(intC1);
					record.setC2(intC2);
					record.setC1inner(intC1inner);
					record.setC2inner(intC2inner);
					record.setC1book(0);
					record.setC2book(0);
					record.setC1outer(intC1 - intC1inner);
					record.setC2outer(intC2 - intC2inner);
					record.setInnerExpire(Integer.parseInt(innerExpire.trim()));
					record.setFavorType(Integer.parseInt(favorType.trim()));
					record.setFavorIn(Integer.parseInt(favorIn.trim()));
					record.setFavorOut(Integer.parseInt(favorOut.trim()));
					record.setCtime(new Date());
					record.setPstart(d0);
					record.setPend(d1);
					record.setRstart(d0);
					record.setRend(d1);
					record.setDuration((int) hour);
					
					ReqResult rr = examPlaceClassManager.addExamPlaceClass(record);//改为放在managerImpl中可以减少对dubbo服务的调用次数
					if(! rr.isSuccess()){
						res.addResult("pageData", null);
						res.setCode(-1);
						res.setMsg(rr.getMsgInfo());
						return res.build();
					}

				}
				
			}

		} catch (Exception e) {
			log.error("controller: ExamPlaceController addExamPlaceClass failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
	}
	
	
	/**
	 * 排班管理--更新排班
	 * @param classId	排班id
	 * @param outerPrice	外部价格。单位分
	 * @param innerPrice	内部价格。单位分
	 * @param c1			最小时间段内c1车辆数目
	 * @param c2			最小时间段内c2车辆数目
	 * @param c1inner		c1中预留给内部
	 * @param c2inner		c2中预留给内部
	 * @param innerExpire	预留给内部失效时间。整型。单位天
	 * @param favorType		优惠配置：1-返课时；2-返金额
	 * @param favorIn		优惠配置累计条件：整型。单位小时
	 * @param favorOut		优惠配置返还设置：整型。单位学时或元
	 * @return
	 */
	@RequestMapping(value = "/class/update", method = RequestMethod.POST)
	@ResponseBody
	public Object updateExamPlaceClass(
			@RequestParam String pstart,
			@RequestParam String placeId,
			@RequestParam String classId,
			@RequestParam String outerPrice, 
			@RequestParam String innerPrice,
			@RequestParam String c1,
			@RequestParam String c2, 
			@RequestParam String c1inner,
			@RequestParam String c2inner,
			@RequestParam String innerExpire,
			@RequestParam String favorType, 
			@RequestParam String favorIn,
			@RequestParam String favorOut
			) {
		ResponseMessage res = new ResponseMessage<>();
		try {
			int intC1 = Integer.parseInt(c1.trim());
			int intC2 = Integer.parseInt(c2.trim());
			int intC1inner = Integer.parseInt(c1inner.trim());
			int intC2inner = Integer.parseInt(c2inner.trim());
			if(intC1 < intC1inner || intC2< intC2inner || intC1inner<0 || intC2inner<0 ){
				res.addResult("pageData", null);
				res.setCode(-1);
				res.setMsg("车辆数量设置错误！");
				return res.build();
			}
			ExamPlaceClass record = new ExamPlaceClass();
			record.setPstart(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pstart));
			record.setPlaceId(Integer.parseInt(placeId));
			record.setId(Integer.parseInt(classId));
			record.setOuterPrice(Integer.parseInt(outerPrice.trim()));
			record.setInnerPrice(Integer.parseInt(innerPrice.trim()));
			record.setC1(intC1);
			record.setC2(intC2);
			record.setC1inner(intC1inner);
			record.setC2inner(intC2inner);
			//record.setC1book(0);
			//record.setC2book(0);
			record.setC1outer(intC1 - intC1inner);
			record.setC2outer(intC2 - intC2inner);
			record.setInnerExpire(Integer.parseInt(innerExpire.trim()));
			record.setFavorType(Integer.parseInt(favorType.trim()));
			record.setFavorIn(Integer.parseInt(favorIn.trim()));
			record.setFavorOut(Integer.parseInt(favorOut.trim()));
			
			int rr = examPlaceClassManager.updateExamPlaceClass(record);
			
		} catch (Exception e) {
			log.error("controller: ExamPlaceController updateExamPlaceClass failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
	}
	
	/**
	 * 复制排班
	 * @param classId	排班id
	 * @param pdate		预约日期。格式：2016-09-12。多个用逗号,隔开
	 * @param pstart	预约时间段。格式：08:00:00-09:00:00,10:00:00-11:00:00。多个用逗号,隔开
	 * @return
	 */
	@RequestMapping(value = "/class/copy", method = RequestMethod.POST)
	@ResponseBody
	public Object copyExamPlaceClass(
			@RequestParam String classId,
			@RequestParam String pdate,
			@RequestParam String pstart
			) {
		ResponseMessage res = new ResponseMessage<>();
		try {
			ReqResult rr = examPlaceClassManager.copyExamPlaceClass(classId,pdate,pstart);
			if(! rr.isSuccess()){
				res.addResult("pageData", null);
				res.setCode(-1);
				res.setMsg(rr.getMsgInfo());
				return res.build();
			}
			
		} catch (Exception e) {
			log.error("controller: ExamPlaceController updateExamPlaceClass failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
	}
	
	/**
	 * 设置延班
	 * @param classId	排班id。多个用逗号隔开
	 * @param num		延期时间（分钟）
	 * @param remark	备注
	 * @return
	 */
	@RequestMapping(value = "/class/delay", method = RequestMethod.POST)
	@ResponseBody
	public Object delayExamPlaceClass(
			@RequestParam String classId,
			@RequestParam String num,
			@RequestParam String remark
			) {
		ResponseMessage res = new ResponseMessage<>();
		try {
			ReqResult rr = examPlaceClassManager.delayExamPlaceClass(classId,num,remark);
			if(! rr.isSuccess()){
				res.addResult("pageData", null);
				res.setCode(-1);
				res.setMsg(rr.getMsgInfo());
				return res.build();
			}
			
		} catch (Exception e) {
			log.error("controller: ExamPlaceController updateExamPlaceClass failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
	}
	
	/**
	 * 关闭排班
	 * @param classId	排班id。多个用逗号隔开
	 * @param remark	备注
	 * @return
	 */
	@RequestMapping(value = "/class/close", method = RequestMethod.POST)
	@ResponseBody
	public Object closeExamPlaceClass(
			@RequestParam String classId,
			@RequestParam String remark
			) {
		ResponseMessage res = new ResponseMessage<>();
		try {
			ReqResult rr = examPlaceClassManager.closeExamPlaceClass(classId,remark);
			if(! rr.isSuccess()){
				res.addResult("pageData", null);
				res.setCode(-1);
				res.setMsg(rr.getMsgInfo());
				return res.build();
			}
			
		} catch (Exception e) {
			log.error("controller: ExamPlaceController updateExamPlaceClass failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
	}
	
	
	
	/**
	 * 排班管理--获取某一天的排班
	 * @param placeId	考场id
	 * @param pdate		日期。格式：2016-09-12
	 * @return
	 */
	@RequestMapping(value = "/class", method = RequestMethod.GET)
	@ResponseBody
	public Object getExamPlaceClass(
			@RequestParam String placeId,
			@RequestParam String pdate
			){
		ResponseMessage res = new ResponseMessage<>();
		try {
			List<ExamPlaceClass> data = examPlaceClassManager.getExamPlaceClass(placeId, pdate);
			res.addResult("pageData", data);
			
		} catch (Exception e) {
			log.error("controller: ExamPlaceController getExamPlaceClass failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
		
	}
	
	/**
	 * 根据排班id获取排班信息
	 * @param classId
	 * @return
	 */
	@RequestMapping(value = "/class/one", method = RequestMethod.GET)
	@ResponseBody
	public Object getExamPlaceClassOne(
			@RequestParam String classId
			){
		ResponseMessage res = new ResponseMessage<>();
		try {
			ExamPlaceClass data = examPlaceClassManager.getExamPlaceClassOne(Integer.parseInt(classId));
			res.addResult("pageData", data);
			
		} catch (Exception e) {
			log.error("controller: ExamPlaceController getExamPlaceClass failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
		
	}
	
	/**
	 * 排班管理--获取指定日期的排班情况
	 * @param placeId	考场id
	 * @param pdate		日期。格式：2016-09-12。
	 * @param days		个数。日期后的天数。
	 * @return
	 */
	@RequestMapping(value = "/class/date", method = RequestMethod.GET)
	@ResponseBody
	public Object getExamPlaceClassDate(
			@RequestParam String placeId,
			@RequestParam String pdate,
			@RequestParam String days
			){
		ResponseMessage res = new ResponseMessage<>();
		try {
			List<ExamPlaceClassDate> data = examPlaceClassManager.getExamPlaceClassDate(placeId, pdate,days);
			res.addResult("pageData", data);
			
		} catch (Exception e) {
			log.error("controller: ExamPlaceController getExamPlaceClassDate failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
		
	}
	
	/**
	 * 分页获取排班订单
	 * @param pdate			日期。格式：2016-09-12。查询全部则传空。查询时间段则用逗号分隔
	 * @param state			订单状态。0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭'。查询全部则传空。
	 * @param placeId		考场id。查询全部则传空。
	 * @param coachName		模糊搜索：教练姓名
	 * @param schoolName	模糊搜索：驾校名称
	 * @param carNo			模糊搜索：车牌号
	 * @param coachMobile	模糊搜索：教练手机号
	 * @param orderId		模糊搜索：订单号
	 * @param pageNo		分页：第几页
	 * @param pageSize		分页：每页数量
	 * @return
	 */
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	@ResponseBody
	public Object getExamPlaceOrder(
			@RequestParam String pdate,
			@RequestParam String pstart,
			@RequestParam String state,
			@RequestParam String drtype,
			@RequestParam String placeId,
			@RequestParam String classId,
			@RequestParam String coachName,
			@RequestParam String schoolName,
			@RequestParam String carNo,
			@RequestParam String coachMobile,
			@RequestParam String orderId,
			@RequestParam String pageNo,
			@RequestParam String pageSize
			
			){
		ResponseMessage res = new ResponseMessage<>();
		try {
			Page<ExamPlaceOrder> data = examPlaceOrderManager.getOrders(pdate,pstart,state,drtype,placeId,classId,coachName,schoolName,carNo,coachMobile,orderId, pageNo,pageSize);
			res.addResult("pageData", data);
			
		} catch (Exception e) {
			log.error("controller: ExamPlaceController getExamPlaceOrder failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
		
	}
	
	/**
	 * 根据订单id获取订单
	 * @param orderId 订单id。多个用逗号,隔开
	 * @return
	 */
	@RequestMapping(value = "/order/info", method = RequestMethod.GET)
	@ResponseBody
	public Object getExamPlaceOrderInfo(
			@RequestParam String orderId
			
			){
		ResponseMessage res = new ResponseMessage<>();
		try {
			List<ExamPlaceOrder> data = examPlaceOrderManager.getExamPlaceOrder(orderId);
			res.addResult("pageData", data);
			
		} catch (Exception e) {
			log.error("controller: ExamPlaceController getExamPlaceOrderOne failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
		
	}
	
	/**
	 * 关闭订单
	 * @param orderId	订单id
	 * @param state		状态：5
	 * @param remark	备注
	 * @return
	 */
	@RequestMapping(value = "/order/update", method = RequestMethod.POST)
	@ResponseBody
	public Object updateExamPlaceOrder(
			@RequestParam String orderId,
			@RequestParam String state,
			@RequestParam String remark
			
			){
		ResponseMessage res = new ResponseMessage<>();
		try {
			examPlaceOrderManager.postOrderState(orderId, Byte.parseByte(state), remark);
			
		} catch (Exception e) {
			log.error("controller: ExamPlaceController getExamPlaceOrderOne failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
		
	}

	
	
	/**
	 * 获取约考场白名单
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/whitelist", method = RequestMethod.POST)
	@ResponseBody
	public Object getWhitelist(HttpServletRequest request, HttpServletResponse response){
		ResponseMessage res = new ResponseMessage<>();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(Constant.USER_SESSION);
		Long schoolId =  user.getSchoolId();
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");
		String state = request.getParameter("state");
		String ctime = request.getParameter("ctime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		Byte stat = null;
		if(null != state){
			stat = Byte.parseByte(state);
		}
		Integer pNo = 1;
		Integer pSize = 100;
		if(null != pageNo){
			pNo = Integer.parseInt(pageNo);
		}
		if(null != pageSize){
			pSize = Integer.parseInt(pageSize);
		}
		
		try {
			Page<ExamPlaceWhitelist> data = examPlaceWhitelistManager.getWhitelist(schoolId.intValue(),name,mobile,stat,ctime,pNo,pSize);
			res.addResult("pageData", data);
			
		} catch (Exception e) {
			log.error("controller: ExamPlaceController getExamPlaceOrderOne failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
		
	}
	/**
	 * 添加白名单教练
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/whitelist/add", method = RequestMethod.POST)
	@ResponseBody
	public Object addWhitelist(HttpServletRequest request, HttpServletResponse response){
		ResponseMessage res = new ResponseMessage<>();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(Constant.USER_SESSION);
		Long schoolId =  user.getSchoolId();
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");
		
		try {
			Integer data = examPlaceWhitelistManager.addWhitelist(schoolId.intValue(), "", name, mobile);
			res.addResult("pageData", data);
			
		} catch (Exception e) {
			log.error("controller: ExamPlaceController getExamPlaceOrderOne failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
		
	}
	
	/**
	 * 修改白名单
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/whitelist/modify", method = RequestMethod.POST)
	@ResponseBody
	public Object modifyWhitelist(HttpServletRequest request, HttpServletResponse response){
		ResponseMessage res = new ResponseMessage<>();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(Constant.USER_SESSION);
		Long schoolId =  user.getSchoolId();
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");
		
		try {
			Integer data = examPlaceWhitelistManager.modifyWhitelist(Integer.parseInt(id), name, mobile);
			res.addResult("pageData", data);
			
		} catch (Exception e) {
			log.error("controller: ExamPlaceController getExamPlaceOrderOne failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
		
	}
	
	/**
	 * 删掉白名单教练
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/whitelist/del", method = RequestMethod.POST)
	@ResponseBody
	public Object delWhitelist(HttpServletRequest request, HttpServletResponse response){
		ResponseMessage res = new ResponseMessage<>();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(Constant.USER_SESSION);
		Long schoolId =  user.getSchoolId();
		String id = request.getParameter("id");
		
		try {
			Integer data = examPlaceWhitelistManager.delWhitelist(Integer.parseInt(id));
			res.addResult("pageData", data);
			
		} catch (Exception e) {
			log.error("controller: ExamPlaceController getExamPlaceOrderOne failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
		
	}
	
	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	@ResponseBody
	public Object addRecharge(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage res = new ResponseMessage<>();
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String muser = user.getAccount();
			String name = request.getParameter("name");
			String gears = request.getParameter("gears");
			String info = request.getParameter("info");
			
			Gson gson = new Gson();
			String jsonStr = gson.toJson(gears);  //javabean转换成json字符串 
			log.info("********************************* jsonStr :" + jsonStr);
			jsonStr = jsonStr.replaceAll("\\\\","");//转义
			jsonStr = jsonStr.substring(1, jsonStr.length()-1);//去首位引号
			log.info("********************************* jsonStr change :" + jsonStr);
			//json字符串转换成javabean对象  
			List<ExamPlaceRechargeGears> rechargeGearList = gson.fromJson(jsonStr, new TypeToken<List<ExamPlaceRechargeGears>>(){}.getType());  
			log.info("********************** rechargeGearList : " + rechargeGearList);

			Integer data = examPlaceRechargeManager.addRecharge(name, rechargeGearList, info, muser);
			res.addResult("pageData", data);

		} catch (Exception e) {
			log.error("controller: ExamPlaceController recharge failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();

	}
	
	@RequestMapping(value = "/recharge/school", method = RequestMethod.POST)
	@ResponseBody
	public Object addRechargeSchool(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage res = new ResponseMessage<>();
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			//Long schoolId = user.getSchoolId();
			String muser = user.getAccount();
			String rid = request.getParameter("rid");
			String schoolId = request.getParameter("school");
			
			ExamPlaceRechargeSchool data = examPlaceRechargeManager.addRechargeSchool(rid, schoolId, muser);
			res.addResult("pageData", data);
			
		} catch (Exception e) {
			log.error("controller: ExamPlaceController addRechargeSchool failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
		
	}
	
	/**
	 * 获取考场收益
	 * @param placeId
	 * @return
	 */
	@RequestMapping(value = "/income", method = RequestMethod.GET)
	@ResponseBody
	public Object income(@RequestParam Integer placeId) {
		ResponseMessage<Object> res = new ResponseMessage<>();
		try {
			List<Integer> list = examPlaceOrderManager.getExamPlaceIncome(placeId);
			res.addResult("yestIncome", list.get(0));
			res.addResult("totalIncome", list.get(1));
		} catch (Exception e) {
			log.error("controller: ExamPlaceController income failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
	}
	
	/**
	 * 获取考场订单数
	 * @param placeId
	 * @return
	 */
	@RequestMapping(value = "/orderCount", method = RequestMethod.GET)
	@ResponseBody
	public Object orderCount(@RequestParam Integer placeId) {
		ResponseMessage<Object> res = new ResponseMessage<>();
		try {
			List<Integer> list = examPlaceOrderManager.getExamPlaceOrder(placeId);
			res.addResult("total", list.get(0));
			res.addResult("valid", list.get(1));
		} catch (Exception e) {
			log.error("controller: ExamPlaceController orderCount failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
	}
	
	/**
	 * 校验验证码
	 * @param placeId
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/validCode", method = RequestMethod.POST)
	@ResponseBody
	public Object validCode(@RequestParam Integer placeId,@RequestParam Integer code) {
		ResponseMessage<Object> res = new ResponseMessage<>();
		try {
			ReqResult rr = examPlaceOrderManager.validCode(placeId, code);
			res.setResult(rr.getResult());
		} catch (Exception e) {
			log.error("controller: ExamPlaceController validCode failed=" + e.getMessage(), e);
			e.printStackTrace();
		}
		return res.build();
	}

}





































