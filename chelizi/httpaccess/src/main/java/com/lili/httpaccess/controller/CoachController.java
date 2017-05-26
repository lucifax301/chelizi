package com.lili.httpaccess.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lili.coach.dto.Car;
import com.lili.coach.dto.CarCheck;
import com.lili.coach.dto.Coach;
import com.lili.coach.dto.ErrorAppeal;
import com.lili.coach.manager.CarManager;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.service.CoachService;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.VersionUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.logic.service.ICoachStateManager;
import com.lili.logic.service.IStudentStateManager;
import com.lili.logic.service.OrderLogic;
import com.lili.order.dto.StudentClass;
import com.lili.order.service.CancelReasonService;
import com.lili.order.service.CoachClassService;
import com.lili.order.service.OrderService;
import com.lili.order.service.PlantClassService;
import com.lili.order.service.StuCommentService;
import com.lili.order.service.StudentClassService;
import com.lili.order.vo.CancelReasonQuery;
import com.lili.order.vo.CancelReasonVo;
import com.lili.order.vo.CoachClassPriceVo;
import com.lili.order.vo.CoachClassTempVo;
import com.lili.order.vo.CoachClassVo;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.order.vo.PlantClassQuery;
import com.lili.order.vo.PlantClassVo;
import com.lili.pay.service.PayService;
import com.lili.school.service.WechatSchoolService;
import com.lili.school.vo.WechatEnrollClass;
import com.lili.school.vo.WechatMycoaches;
import com.lili.school.vo.WechatPlantClass;
import com.lili.student.dto.Mycoaches;
import com.lili.student.dto.Student;
import com.lili.student.manager.MycoachesManager;
import com.lili.student.manager.StudentManager;
import com.lili.student.service.StudentService;

@Controller
@RequestMapping("/v1/coaches")
public class CoachController {

	@Autowired
	private CoachService coachService;

	@Autowired
	private OrderLogic orderLogic;
	@Autowired
	private OrderService orderService;
	@Autowired
	private CoachClassService coachClassService;
	@Autowired
	private PlantClassService plantClassService;

	@Autowired
	private StudentService studentService;
	@Autowired
	private StudentManager studentManager;
	@Autowired
	private StuCommentService stuCommentService;
	@Autowired
	private CancelReasonService cancelReasonService;
	@Autowired
	private IStudentStateManager stateManager;
	@Autowired
	private ICoachStateManager coachStateManager;
	@Autowired
	private PayService payService;
	@Autowired
	private CarManager carManager;
	@Autowired
	private MycoachesManager mycoachesManager;
	@Autowired
    private StudentManager studentMananger;
	@Autowired
	private CoachManager coachManager;
	@Autowired
	private StudentClassService studentClassService;
	@Autowired
	private WechatSchoolService wechatSchoolService;

	private Logger log = Logger.getLogger(CoachController.class);

	/**
	 * 用户注册并登陆
	 * 
	 * @param mobile
	 *            手机号
	 * @param codeInput
	 *            用户输入的验证码
	 * @param password
	 *            密码，客户端md5加密后的字符串
	 * @param timestamp
	 *            时间戳
	 * @param sign
	 *            签名字符串
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public Object addCoachAndLogin(@RequestParam String mobile,
			@RequestParam String codeInput, @RequestParam String password,
			@RequestParam String timestamp, @RequestParam String sign,
			@RequestParam(required = false) String name) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = coachService.addCoachAndLogin(mobile, codeInput, password, name);
		} catch (Exception e) {
			log.error(
					"controller: coach post register failed=" + e.getMessage(),
					e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 用户登录
	 * 
	 * @param mobile
	 * @param password
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Object login(@RequestParam String mobile,
			@RequestParam String password, @RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();

		try {
			r = coachService.login(mobile, password);
		} catch (Exception e) {
			log.error("controller: coach post login failed=" + e.getMessage(),
					e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 用户自动登录
	 * 
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/autoLogin", method = RequestMethod.POST)
	@ResponseBody
	public Object autoLogin(@RequestParam String userId,
			@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign) {

		ReqResult r = ReqResult.getSuccess();
		// 设置登录时间
    	Date now = new Date();
    	Coach c = coachManager.getCoachInfo(Long.parseLong(userId));
    	if (c.getFirstLogin() == null)
        	c.setFirstLogin(now);
    	c.setLastLogin(now);
    	coachManager.updateCoach(c);
    	//add by devil 20160912
        coachManager.addLoginCount(c);
    	// 已在过滤器中判断是否已登录
		return r.getResult();
	}

	/**
	 * 用户注销
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param headers
     * @return
     */
	@RequestMapping(value = "/{userId}/logout", method = RequestMethod.POST)
	@ResponseBody
	public Object logout(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();

		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = coachService.logout(userId, tokenId);
		} catch (Exception e) {
			log.error("controller: coach post logout failed=" + e.getMessage(),
					e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取用户信息
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param headers
     * @return
     */
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public Object getUserInfo(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = coachService.getUserInfo(userId, tokenId);

		} catch (Exception e) {
			log.error("controller: coach post logout failed=" + e.getMessage(),
					e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}
	
	/**
	 * 导入的教练引导同意用户协议
	 * @param userId
	 * @param userType
	 * @param agreement 0-未同意；1-已同意
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/agreement", method = RequestMethod.POST)
	@ResponseBody
	public Object postAgreement(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String agreement,
			@RequestParam String timestamp,@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = coachService.postAgreement(userId, agreement);
			
		} catch (Exception e) {
			log.error("controller: coach postAgreement failed=" + e.getMessage(),
					e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}

	/**
	 * 修改用户信息
	 * 
	 * @param mobile
	 * @param password
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
	@ResponseBody
	public Object updateUser(@PathVariable String userId,
			@RequestParam String userType,
			@RequestParam(required = false) String password,
			@RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers,
			@RequestParam(required = false) String headIcon,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String sex,
			@RequestParam(required = false) String age,
			@RequestParam(required = false) String mobile,
			@RequestParam(required = false) String schoolName,
			@RequestParam(required = false) String cityId,
			@RequestParam(required = false) String cityName,
			@RequestParam(required = false) String codeInput) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			//20160511屏蔽教练端更改头像
			if(headIcon != null && !"".equals(headIcon.trim())){
				r.setCode(ResultCode.ERRORCODE.EXCEPTION);
				r.setMsgInfo("亲爱的教练，喱喱为您处理头像，该功能已经关闭");
			}else{
				r = coachService.updatePass(userId, password, headIcon, name, sex,
						age, mobile, codeInput, schoolName, cityId, cityName);
			}

		} catch (Exception e) {
			log.error("controller: coach put password failed=" + e.getMessage(),e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	@RequestMapping(value = "/checkpw", method = RequestMethod.POST)
	@ResponseBody
	public Object checkpw(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String passwd, @RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = coachService.checkpw(userId, userType, passwd);
		} catch (Exception e) {
			log.error("controller: coach checkpw failed=" + e.getMessage(),e);
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		// 已在过滤器中判断是否已登录
		return r.getResult();
	}
	
	/**
	 * 
	 * @Title: updateHeadIcon
	 * @Description:更新用户头像
	 * @param @param userId
	 * @param @param userType
	 * @param @param picPath
	 * @param @param timestamp
	 * @param @param sign
	 * @param @param headers
	 * @param @return 参数
	 * @return Object 返回类型
	 * @throws
	 */
	@RequestMapping(value = "/{userId}/headIcon", method = RequestMethod.POST)
	@ResponseBody
	public Object updateHeadIcon(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String picPath,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			//20160511屏蔽教练端更改头像
			//r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			//r.setMsgInfo("亲爱的教练，喱喱为您处理头像，该功能已经关闭");
			//20161027 注册教练可以修改头像
			r = coachService.updateHeadIcon(userId, picPath);
		} catch (Exception e) {
			log.error(
					"controller: coach update headIcon failed="
							+ e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 添加用户身份证信息
	 * 
	 * @param userId
	 *            用户id
	 * @param userType
	 * @param name
	 *            身份证姓名
	 * @param idCard
	 *            身份证号码
	 * @param picPath1
	 *            身份证正面保存地址
	 * @param picPath2
	 *            身份证背面保存地址
	 * @param timestamp
	 *            时间戳
	 * @param sign
	 *            签名字符串
	 * @return
	 */
	@RequestMapping(value = "/{userId}/idCard", method = RequestMethod.POST)
	@ResponseBody
	public Object addIdCardInfo(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String name,
			@RequestParam String idCard, @RequestParam String picPath1,
			@RequestParam String picPath2, @RequestParam String timestamp,
			@RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = coachService.addIdCardInfo(userId, name, idCard, picPath1,
					picPath2, tokenId);
		} catch (Exception e) {
			log.error("controller: coach post idcard failed=" + e.getMessage(),
					e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 存储教练的教练证信息
	 * 
	 * @param userId
	 *            用户id
	 * @param userType
	 * @param coachCardId
	 *            教练证编号
	 * @param teachArea
	 *            教学地区
	 * @param carSchool
	 *            驾校
	 * @param picPath1
	 *            驾驶证照片保存地址
	 * @param picPath2
	 *            教练证照片保存地址
	 * @param timestamp
	 *            时间戳
	 * @param sign
	 *            签名字符串
	 * @return
	 */
	@RequestMapping(value = "/{userId}/coachCard", method = RequestMethod.POST)
	@ResponseBody
	public Object addCoachCardInfo(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String coachCardId,
			@RequestParam String teachArea, @RequestParam String carSchool,
			@RequestParam String picPath1, @RequestParam String picPath2,
			@RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = coachService.addCoachCardInfo(userId, coachCardId, teachArea,
					carSchool, picPath1, picPath2, tokenId);
		} catch (Exception e) {
			log.error(
					"controller: coach post coachcard failed=" + e.getMessage(),
					e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}
	
	/**
	 * 添加教练认证信息
	 * @param userId
	 * @param userType
	 * @param coachCardId
	 * @param drType
	 * @param drPhoto
	 * @param drPhoto2
	 * @param coachCardPhoto
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/coachVerify", method = RequestMethod.POST)
	@ResponseBody
	public Object addCoachVerfyMaterial(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String coachCardId,
			@RequestParam String drType, @RequestParam String drPhoto,
			@RequestParam String drPhoto2, @RequestParam String coachCardPhoto,
			@RequestParam String timestamp, @RequestParam String sign
			) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = coachService.addCoachVerfyMaterial(userId,userType, coachCardId, drType,
					drPhoto, drPhoto2, coachCardPhoto );
		} catch (Exception e) {
			log.error(
					"controller: coach post addCoachVerfyMaterial failed=" + e.getMessage(),
					e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}

	/**
	 * 存储教练车相关信息
	 * 
	 * @param userId
	 *            用户id
	 * @param userType
	 * @param carType
	 *            车型
	 * @param licensePlateNumber
	 *            车牌号license plate number
	 * @param drivingLicense
	 *            行驶证编号//driving license
	 * @param picPath1
	 *            行驶证照片保存地址
	 * @param timestamp
	 *            时间戳
	 * @param sign
	 *            签名字符串
	 * @return
	 */
	@RequestMapping(value = "/{userId}/cars", method = RequestMethod.POST)
	@ResponseBody
	public Object addCarInfo(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String carType,
			@RequestParam String licensePlateNumber,@RequestParam(required=false) String drtype,
			@RequestParam(required=false) String drivingLicense, @RequestParam(required=false) String picPath1,
			@RequestParam(required=false) String brandName,
			@RequestParam(required=false) String operateType,@RequestParam(required=false) String carId,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = coachService.addCarInfo(userId, carType, licensePlateNumber,
					drivingLicense, picPath1, drtype, brandName, operateType, carId);
		} catch (Exception e) {
			log.error(
					"controller: coach post coachcar failed=" + e.getMessage(),
					e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取教练车
	 * 
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "/{userId}/cars", method = RequestMethod.GET)
	@ResponseBody
	public Object getCarInfo(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = coachService.getCarInfo(userId, tokenId);
		} catch (Exception e) {
			log.error("controller: coach get cars failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取教练当前业务相关信息
	 * 
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "/{userId}/today", method = RequestMethod.GET)
	@ResponseBody
	public Object getToday(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			// r = coachService.getToday(coachId, tokenId);
			r = orderLogic.getCoachStatistc(userId, null, tokenId);
		} catch (Exception e) {
			log.error("controller: coach get today failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取重点播报
	 * 
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "/{userId}/broadcast", method = RequestMethod.GET)
	@ResponseBody
	public Object getBroadcast(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = coachService.getBroadcast(userId, tokenId);
		} catch (Exception e) {
			log.error(
					"controller: coach get broadcast failed=" + e.getMessage(),
					e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 设置教练业务状态 出车/收车
	 * 
	 * @param userId
	 * @param userType
	 * @param carId
	 * @param courses
	 * @param status
	 *            1：出车;0:收车；
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "/{userId}/status", method = RequestMethod.POST)
	@ResponseBody
	public Object doStatus(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String carId,
			@RequestParam String courses, @RequestParam String status,
			@RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();

		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = coachStateManager.handleDoStatus(userId, carId, courses,
					status, tokenId);
			// r = coachService.doStatus(userId, carId, courses, status,
			// tokenId);
			/*if(r.getResult().get(ResultCode.RESULTKEY.CODE)
					.equals(ResultCode.ERRORCODE.SUCCESS)
					&& status.trim().equals("0")){
				r.setData("income","张教练您已上班了8小时，辛苦了，今天的收益是1000元!");
				
			}*/
		} catch (Exception e) {
			log.error("controller: coach do status failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取教练的状态
	 * 
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/status", method = RequestMethod.GET)
	@ResponseBody
	public Object getStatus(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			// TODO 获取当前状态
			r = coachStateManager.genStateContextDesc(userId);
			// 获取未评价的订单 放在这里或者放在获取个人信息的接口中
			Object o = orderService.getCoachWait(Long.parseLong(userId), null);
			r.setData("wait", o);
		} catch (Exception e) {
			log.error("controller: coach get status failed=" + e.getMessage(),
					e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 设置教练业务状态 上课、下课
	 * 
	 * @param userId
	 * @param userType
	 * @param orderId
	 * @param studentId
	 * @param status
	 *            2：上课	------3：下课;//已更改教练不能下课
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "/{userId}/course/status", method = RequestMethod.POST)
	@ResponseBody
	public Object doCourseStatus(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String orderId,
			@RequestParam String studentId, @RequestParam String status,
			@RequestParam(required=false) String placeLge, @RequestParam(required=false) String placeLae,
			@RequestParam(required=false) String learnAddr,
			@RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = coachStateManager.handleDoCourseStatus(userId, orderId,
					studentId, status, tokenId);
			// r = coachService.doCourseStatus(userId, orderId, studentId,
			// status,
			// tokenId);
			if (r.getResult().get(ResultCode.RESULTKEY.CODE)
					.equals(ResultCode.ERRORCODE.SUCCESS)) {
				boolean error=false;
				if(Integer.parseInt(status.trim())==ReqConstants.COACH_STATUS_ON_CLASS){
					try {
						//判断学员是否是驾校学员
						Student s = studentMananger.getStudentInfo(Long.parseLong(studentId));
						if(s.getIsImport() == (byte)1){ //如果是驾校学员，不做任何操作
							log.info("do nothings!");
						}
						else {
							//判断是否已绑定该教练
							Mycoaches record = new Mycoaches();
							record.setCoachid(Long.parseLong(userId));
							record.setStudentid(Long.parseLong(studentId));
							long isExit = mycoachesManager.selectByUserId(record);
							if(isExit > 0){
								log.info("************** is Exit Record");
							}
							else {
								record.setType(ReqConstants.MY_COACH_TYPE_SYSTEM);
								mycoachesManager.addMycoaches(record );	//现约成功后绑定我的教练
							}
						}
						
						//上课成功后，更新订单添加现约上课地点
						OrderVo orderVo = new OrderVo();
						orderVo.setOrderId(orderId);
						try{
						if(placeLge!=null)
						orderVo.setPlaceLge(Double.parseDouble(placeLge));
						if(placeLae!=null)
						orderVo.setPlaceLae(Double.parseDouble(placeLae));
						}catch(Exception ex){}
						orderVo.setLearnAddr(learnAddr);
						orderService.updateByOrderId(orderVo, orderId);
						
					} catch (Exception e) {
						error=true;
						log.error("controller: coach updateByOrderId failed=" + e.getMessage(), e);
					}
				}
				if(!error)
				stateManager.handleDoCourseStatus(userId, orderId, studentId,
						status);
			}
		} catch (Exception e) {
			log.error(
					"controller: coach do course status failed="
							+ e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 对学员评价
	 * 
	 * @param userId
	 * @param userType
	 * @param studentId
	 * @param orderId
	 * @param tags
	 * @param scores
	 * @param reason
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "/{userId}/evaluations/{studentId}", method = RequestMethod.POST)
	@ResponseBody
	public Object doEvaluation(@PathVariable String userId,
			@RequestParam String userType, @PathVariable String studentId,
			@RequestParam String orderId, @RequestParam String tags,
			@RequestParam String scores, @RequestParam String reason,
			@RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();

		String[] t = tags.split(",");
		String[] s = scores.split(",");
		if (t.length != s.length) {
			r.setCode(ResultCode.ERRORCODE.PARAMERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
			return r.getResult();
		}
		Map<Integer, Integer> m = new HashMap<Integer, Integer>();
		for (int i = 0; i < t.length; i++) {
			try {
				int a = Integer.parseInt(t[i]);
				int b = Integer.parseInt(s[i]);
				m.put(a, b);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				r.setCode(ResultCode.ERRORCODE.PARAMERROR);
				r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
				return r.getResult();
			}
		}

		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = orderLogic.coachCommentStu(userId, studentId, orderId, m,
					reason, tokenId);
		} catch (Exception e) {
			log.error(
					"controller: coach post evaluations failed="
							+ e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取学员历史打分详情
	 *
	 * @param userId
	 * @param userType
	 * @param studentId
	 * @param pageNo
	 * @param pageSize
	 * @param subjectId
	 * @param courseId
	 * @param timestamp
     * @param sign
     * @return
     */
	@RequestMapping(value = "/{userId}/evaluations/{studentId}", method = RequestMethod.GET)
	@ResponseBody
	public Object getEvaluation(@PathVariable String userId,
			@RequestParam String userType, @PathVariable String studentId,
			@RequestParam String pageNo, @RequestParam String pageSize,@RequestParam(required=false) String v,
			@RequestParam(required=false) String subjectId,@RequestParam(required=false) String courseId,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		Integer cid = null;
		Integer course = null;
		try {
			if (null != subjectId && !"".equals(subjectId.trim())) {
				cid = Integer.parseInt(subjectId.trim());
			}
			if (null != courseId && !"".equals(courseId.trim())) {
				course = Integer.parseInt(courseId.trim());
			}
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		}

		try {
			Object data = stuCommentService.getStuCommentList(
					Long.parseLong(studentId), Integer.parseInt(pageNo),
					Integer.parseInt(pageSize), course,cid, v);
			r.setData(data);
		} catch (Exception e) {
			log.error(
					"controller: coach post evaluations failed="
							+ e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 标记学员缺课
	 *
	 * @param userId
	 * @param userType
	 * @param orderId
	 * @param timestamp
	 * @param sign
     * @return
     */
	@RequestMapping(value = "/{userId}/evaluations/absence", method = RequestMethod.POST)
	@ResponseBody
	public Object doAbsence(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String orderId,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();

		try {
			r = orderService.missClass(orderId);
		} catch (Exception e) {
			log.error(
					"controller: coach post evaluations absence failed="
							+ e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取对学员评价的标签
	 * @param userId
	 * @param userType
	 * @param courseId
	 * @param timestamp
	 * @param sign
     * @param headers
     * @return
     */
	@RequestMapping(value = "/{userId}/evaluations/tags", method = RequestMethod.GET)
	@ResponseBody
	public Object getStudentTags(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String courseId,@RequestParam(required=false) String v,
			@RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();

		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			/*r = orderLogic.getCommentTag(
					String.valueOf(ReqConstants.USER_TYPE_STUDENT), courseId,
					tokenId);*/
			r = orderLogic.getStudentTag(courseId, null, null, null, v);
			
		} catch (Exception e) {
			log.error("controller: coach get tags failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取订单信息
	 * @param userId
	 * @param userType
	 * @param oStatus
	 * @param pageNo
	 * @param pageSize
	 * @param oType
	 * @param timestamp
     * @param sign
     * @return
     */
	@RequestMapping(value = "/{userId}/orders", method = RequestMethod.GET)
	@ResponseBody
	public Object getOrders(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String oStatus,
			@RequestParam String pageNo, @RequestParam String pageSize,
			@RequestParam(required=false) String oType,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			OrderQuery oq = new OrderQuery();
			oq.setPageIndex(Integer.parseInt(pageNo));
			oq.setPageSize(Integer.parseInt(pageSize));
			//所有订单都必须是有效的才返回，否则预约合并的时候可能出现状态错误！
			oq.setGroupBy(" and order_state in (1,2,3,4,5,6,7,10)  GROUP BY (CASE when ccid IS NULL then order_id else ccid end ) ");
			OrderVo ov = new OrderVo();
			try {
				int oSt = Integer.parseInt(oStatus);
				ov.setOrderState(oSt);
			} catch (Exception e) {
			}
			// 增加类型分类 0-未支付；1-现约；3-预约
			if(null != oType){
				try {
					int oTy = Integer.parseInt(oType);
					if(oTy == OrderConstant.OTYPE.BOOKORDER){
						ov.setOtype(OrderConstant.OTYPE.BOOKORDER);
					}else if(oTy == OrderConstant.OTYPE.NOWORDER){
						ov.setOtype(OrderConstant.OTYPE.NOWORDER);  
					}else if(oTy == OrderConstant.PAYSTATE.WAITPAY){
						ov.setPayState(OrderConstant.PAYSTATE.WAITPAY);
					}
				} catch (Exception e) {
				}
			}
			
			oq.setorderBy("order by pstart desc");
			ov.setCoachId(Long.parseLong(userId));
			List<OrderVo> list = orderService.queryByObjectAnd(ov, oq);

			for (OrderVo one : list) {
				if (one.getOtype() == OrderConstant.OTYPE.BOOKORDER) {
					//返回的排版只能是有效的
					PlantClassQuery query=new PlantClassQuery();
					query.setGroupBy(" and isdel!="+OrderConstant.ISDEL.DELETE);
					PlantClassVo search=new PlantClassVo();
					search.setCcid(one.getCcid());
					search.setIsdel(null);
					//search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
					List<PlantClassVo> plist = plantClassService.queryByObjectAnd(search, query);
					one.setPlantClassList(plist);
				}
			}
			r.setCode(ResultCode.ERRORCODE.SUCCESS);
			r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
			r.setData(list);
			return r.getResult();
		} catch (Exception e) {
			log.error("controller: coach get orders failed=" + e.getMessage(),
					e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取订单信息
	 * @param userId
	 * @param userType
	 * @param orderId
	 * @param timestamp
	 * @param sign
     * @return
     */
	@RequestMapping(value = "/{userId}/orders/one", method = RequestMethod.GET)
	@ResponseBody
	public Object getOrdersOne(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String orderId,
			@RequestParam String timestamp, @RequestParam String sign,@RequestParam(required = false) String v) {
		ReqResult r = ReqResult.getSuccess();

		try {
			OrderVo o = orderService.queryOrderById(orderId, new OrderQuery());
			
			if(VersionUtil.compareVersion(v, "2.1.0") >= 0){
			
				if(o!=null){
				//20161102查询排班
	            PlantClassVo search = new PlantClassVo();
	            search.setOrderId(orderId);
	            List<PlantClassVo> pList = plantClassService.queryByObjectAnd(search, new PlantClassQuery());
	            o.setPlantClassList(pList);
				}else{
					StudentClass sc= studentClassService.getStudentClass(orderId);
					
					if(sc!=null){
					OrderVo vo = new OrderVo();
	
					vo.setOrderId(sc.getOrderId());
					vo.setDirect(sc.getDirect());
					vo.setStudentId(sc.getStudentId());
					vo.setStuName(sc.getStuName());
					vo.setStuMobile(sc.getStuMobile());
					vo.setDltype(sc.getDltype().intValue());
					vo.setCourseName(sc.getCourseName());
					vo.setCstart(sc.getCstart());
					vo.setStuImg(sc.getStuImg());
					vo.setCourseId(sc.getCourseId() + "");
					vo.setCend(sc.getCend());
					vo.setClzNum(sc.getClznum().intValue());
					vo.setPrice(sc.getPrice());
					vo.setLge(sc.getLge());
					vo.setLae(sc.getLae());
					vo.setPreOrder(1);
					vo.setOtype(3);
					vo.setPreOrderState(sc.getState().intValue());
					}
				}
			}
			r.setCode(ResultCode.ERRORCODE.SUCCESS);
			r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
			r.setData(o);
		} catch (Exception e) {
			log.error(
					"controller: coach get one order failed=" + e.getMessage(),
					e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取钱包信息 总金额
	 * @param userId
	 * @param userType
	 * @param v
	 * @param timestamp
	 * @param sign
     * @return
     */
	@RequestMapping(value = "/{userId}/wallet", method = RequestMethod.GET)
	@ResponseBody
	public Object getWallet(@PathVariable String userId,
			@RequestParam String userType, @RequestParam(required=false) String v,
			@RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			
			/**
			 * 校验教练的版本是否低于2.0，教练端的微信手续费2%问题
			 */
			if (Integer.parseInt(userType) == 1) {
				if ((null != v && !"".equals(v) && (VersionUtil.compareVersion(v, "2.1.0") < 0))) { //版本低于2.1
					r.setCode(ResultCode.ERRORCODE.EXCEPTION);
					r.setMsgInfo("请更新到最新版本。");
					return r.getResult();
				}
			}
			
			r = coachService.getWallet(userId);
		} catch (Exception e) {
			log.error("controller: coach get wallet failed=" + e.getMessage(),
					e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取账单详情，余额记录
	 * @param userId
	 * @param userType
	 * @param pageNo
	 * @param pageSize
	 * @param timestamp
     * @param sign
     * @return
     */
	@RequestMapping(value = "/{userId}/wallet/bills", method = RequestMethod.GET)
	@ResponseBody
	public Object getBills(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String pageNo,
			@RequestParam String pageSize, @RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			// r = coachService.getBills(userId);
			r = payService.getMoneyLog(Long.parseLong(userId),
					Integer.parseInt(userType), Integer.parseInt(pageNo),
					Integer.parseInt(pageSize));
		} catch (Exception e) {
			log.error("controller: coach get bills failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取业绩记录详情
	 *
	 * @param userId
	 * @param userType
	 * @param pageNo
	 * @param pageSize
	 * @param timestamp
     * @param sign
     * @return
     */
	@RequestMapping(value = "/{userId}/wallet/records", method = RequestMethod.GET)
	@ResponseBody
	public Object getRecords(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String pageNo,
			@RequestParam String pageSize, @RequestParam String timestamp,

			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = payService.getPerformanceLog(Long.parseLong(userId),
					Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			// r = coachService.getRecords(userId);
		} catch (Exception e) {
			log.error("controller: coach get bills failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取消息中心消息
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param pageNo
	 * @param pageSize
	 * @param sign
     * @param time
     * @return
     */
	@RequestMapping(value = "/{userId}/messages", method = RequestMethod.GET)
	@ResponseBody
	public Object getMessages(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String pageNo, @RequestParam String pageSize,
			@RequestParam String sign, @RequestParam(required=false) String time, @RequestParam(required=false) String type) {
		ReqResult r = ReqResult.getSuccess();

		try {
			r = coachService.getMessages(userId, userType, pageNo,
					pageSize, time, type);
		} catch (Exception e) {
			log.error(
					"controller: coach get messages failed=" + e.getMessage(),
					e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}
	
	/**
	 * 获取消息中心消息2.1新版
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param pageNo
	 * @param pageSize
	 * @param sign
     * @param time
     * @return
     */
	@RequestMapping(value = "/{userId}/notice", method = RequestMethod.GET)
	@ResponseBody
	public Object getNotices(@PathVariable String userId,
			@RequestParam String type, @RequestParam String timestamp,
			@RequestParam String pageNo, @RequestParam String pageSize,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();

		try {
			if(!userId.matches("\\d+")){
	      		  r.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
			          r.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
			          return r;
	           }
			r = coachService.getNotices(userId, type, pageNo,pageSize);
		} catch (Exception e) {
			log.error(
					"controller: coach get notices failed=" + e.getMessage(),
					e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}
	
	@RequestMapping(value = "/{userId}/noticeIndex", method = RequestMethod.GET)
	@ResponseBody
	public Object getNoticeIndex(@PathVariable String userId,@RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();

		try {
			if(!userId.matches("\\d+")){
	      		  r.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
			          r.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
			          return r;
	          }
			r = coachService.getNoticeIndex(userId);
		} catch (Exception e) {
			log.error(
					"controller: coach getNoticeIndex failed=" + e.getMessage(),
					e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取五天内排班表基本情况
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
     * @return
     */
	@RequestMapping(value = "/{userId}/arrangeInfo", method = RequestMethod.GET)
	@ResponseBody
	public Object getArrangeInfo(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			//教练默认可以设置和获取未来5天的排班情况
			int days =5;
			/**
			 * date为日期 arrange为0则未开放 student如果不为0则为有约 arrange不为0且studnet为0则开放未约
			 */
			List<Map<String, Long>> date = coachClassService.getCoach5Date(
					Long.parseLong(userId), null,days);
			r.setData(date);
		} catch (Exception e) {
			log.error(
					"controller: coach get arrangeInfo failed="
							+ e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取取消排班表的理由
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
     * @return
     */
	@RequestMapping(value = "/{userId}/arrangements/cancelReason", method = RequestMethod.GET)
	@ResponseBody
	public Object getArrangeCancelReason(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			// t_cancel_reason 3代表教练取消排班的理由
			List<CancelReasonVo> o = cancelReasonService.queryByUtype(3,
					new CancelReasonQuery());
			r.setData(o);
		} catch (Exception e) {
			log.error(
					"controller: coach get arrangeInfo failed="
							+ e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 取消排班
	 *
	 * @param userId
	 * @param userType
	 * @param ccId
	 * @param rId
	 * @param reason
	 * @param timestamp
     * @param sign
     * @return
     */
	@RequestMapping(value = "/{userId}/arrangements/cancel", method = RequestMethod.POST)
	@ResponseBody
	public Object doArrangeCancel(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String ccId,
			@RequestParam String rId, @RequestParam String reason,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			// t_cancel_reason 3代表教练取消排班的理由
			CoachClassVo ccv = new CoachClassVo();
			ccv.setCoachId(Long.parseLong(userId));
			ccv.setCcid(Integer.parseInt(ccId));
			ccv.setReason(reason);
			ccv.setRid(Integer.parseInt(rId));
			/* 删除状态：0代表未删除，1代表已删除 */
			ccv.setIsdel(1);
			// 排班类型 默认预约
			ccv.setCtype(ReqConstants.BOOK_TYPE_FUTURE);
			// 删除需要判断是否已经有预约排班
			/*PlantClassVo search = new PlantClassVo();
			search.setCcid(ccv.getCcid());
			search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
			List<PlantClassVo> list = plantClassService.queryByObjectAnd(
					search, new PlantClassQuery());
			if (list != null && list.size() > 0) {
				r.setCode(ResultCode.ERRORCODE.SUCCESS);
				return r.getResult();
			}*/

			//coachClassService.saveCoachClass(ccv);
			coachClassService.arrangeClass(ccv);
		} catch (Exception e) {
			log.error(
					"controller: coach get arrangeInfo failed="
							+ e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取排班表
	 *
	 * @param userId
	 * @param userType
	 * @param date 指定日期格式yyyy-MM-dd
	 * @param timestamp
	 * @param sign
	 * @param headers
     * @return
     */
	@RequestMapping(value = "/{userId}/arrangements", method = RequestMethod.GET)
	@ResponseBody
	public Object getArrangements(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String date,
			@RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			// SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			// SimpleDateFormat sdf2 = new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// String dd = sdf2.format(new Date(Long.parseLong(date)*1000));
			// String tokenId = headers.getFirst(ReqConstants.TOKEN);
			//获取教练的所有排班，不管是否有价格，返回的数据price为null
			List<CoachClassVo> ccvs = coachClassService.queryByCoachDateAllWithNoPrice(
					new Date(Long.parseLong(date)), Long.parseLong(userId),
					OrderConstant.ISDEL.NOTDELETE, OrderConstant.OTYPE.BOOKORDER, 1, 100);

			for (int i = 0; i < ccvs.size(); i++) {
				CoachClassVo ccv = ccvs.get(i);
				Date a = (Date) ccv.getCend();
				Date b = new Date(System.currentTimeMillis());
				// 如果当前时间超过了排班时间表的结束时间，则认为这个时间表已过期，不能再操作了
				if (b.after(a)) {
					ccv.setOperate(1);
					ccvs.set(i, ccv);
				}
			}
			r.setCode(ResultCode.ERRORCODE.SUCCESS);
			r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
			r.setData(ccvs);
		} catch (Exception e) {
			log.error(
					"controller: coach get arrangement failed="
							+ e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 设置排班时间
	 *
	 * @param userId
	 * @param userType
	 * @param ccId
	 * @param carId
	 * @param driveType
	 * @param courseId
	 * @param timeId
	 * @param cStart  课程开始时间
	 * @param cEnd 课程结束时间
	 * @param maxNum 课程最大可预约人数
	 * @param cStatus 课程开启状态 0 开启 1关闭
	 * @param placeId
	 * @param placeName
	 * @param lge
     * @param lae
     * @param timestamp
     * @param sign
     * @return
     */
	@RequestMapping(value = "/{userId}/arrangements", method = RequestMethod.POST)
	@ResponseBody
	public Object setArrangements(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String ccId,
			@RequestParam String carId, @RequestParam String driveType,
			@RequestParam String courseId, @RequestParam(required = false) String timeId,
			@RequestParam String cStart, @RequestParam String cEnd,
			@RequestParam String maxNum, @RequestParam String cStatus,@RequestParam(required = false) String price,
			@RequestParam(required = false) String placeId,
			@RequestParam(required = false) String placeName,
			@RequestParam(required = false) String lge,
			@RequestParam(required = false) String lae,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			CoachClassVo ccv = new CoachClassVo();
			// 教练
			if(!"".equals(driveType) && driveType != null){
				ccv.setDltype(Integer.valueOf(driveType));
			}
			if(!"".equals(lge) && lge != null){
				ccv.setLge(Double.valueOf(lge));
			}
			if(!"".equals(lae) && lae != null){
				ccv.setLae(Double.valueOf(lae));
			}
			if (price != null && !"".equals(price)) {
				ccv.setPrice(Integer.parseInt(price));
			}
			ccv.setCoachId(Long.parseLong(userId));
			// 排班主键 不为空说明是更新这个排班
			if (StringUtil.isNotNullAndNotEmpty(ccId) && !"0".equals(ccId)) {
				ccv.setCcid(Integer.parseInt(ccId));
			}
			// 排班类型 默认预约
			ccv.setCtype(ReqConstants.BOOK_TYPE_FUTURE);
			//删除排班时，不需要设置时间
			if(Integer.parseInt(cStatus) != 1){
				// 排班时间
				Date d1 = new Date(Long.parseLong(cStart) * 1000);
				Date d2 = new Date(Long.parseLong(cEnd) * 1000);
				Date dnow = new Date();
				if(d1.before(dnow) || d2.before(dnow) || d2.before(d1)){
					r.setCode(ResultCode.ERRORCODE.CLASS_PLAN_TIME_ERROR);
					r.setMsgInfo(ResultCode.ERRORINFO.CLASS_PLAN_TIME_ERROR);
					return r.getResult();
				}
				ccv.setCstart(new Date(Long.parseLong(cStart) * 1000));
				ccv.setCend(new Date(Long.parseLong(cEnd) * 1000));
				ccv.setRstart(new Date(Long.parseLong(cStart) * 1000));
				ccv.setRend(new Date(Long.parseLong(cEnd) * 1000));
			}
			// 排班用车
			ccv.setCarId(Integer.parseInt(carId));
			Car car = carManager.getCarInfo(Integer.parseInt(carId));
			// 驾考类型
			ccv.setDltype(car.getDriveType()+0);
			// 排班训练场
			if (null != placeId && !"".equals(placeId)) {
				// 训练场id
				ccv.setPlaceId(Integer.parseInt(placeId));
			}
			if (null != placeName && !"".equals(placeName)) {
				// 训练场名称
				ccv.setPlaceName(placeName);
			}
			// 排班训练场坐标
			if (null != lge && !"".equals(lge)) {
				ccv.setLge(Double.parseDouble(lge));
			}
			if (null != lae && !"".equals(lae)) {
				ccv.setLae(Double.parseDouble(lae));
			}
			// 排班课程
			ccv.setCourseId(courseId);
			// 排班最大可约人数
			ccv.setMaxNum(Integer.parseInt(maxNum));
			// 排班状态 0代表未删除，1代表已删除
			ccv.setIsdel(Integer.parseInt(cStatus));
			// 排班时段主键，这个时段实际上与排班时间对应，客户端可以只传一种//v1.3.0更改了
			if(null != timeId && !"".equals(timeId.trim())){
				ccv.setTid(Integer.parseInt(timeId));
			}
			// 删除需要判断
			/*if (ccv.getCcid() != null
					&& ccv.getIsdel() == OrderConstant.ISDEL.DELETE) {
				PlantClassVo search = new PlantClassVo();
				search.setCcid(ccv.getCcid());
				search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
				List<PlantClassVo> list = plantClassService.queryByObjectAnd(
						search, new PlantClassQuery());
				if (list != null && list.size() > 0) {
					r.setCode(ResultCode.ERRORCODE.SUCCESS);
					return r.getResult();
				}
			}*/
			/*
			 * else{ boolean idle = false;
			 * if(StringUtil.isNotNullAndNotEmpty(ccId)&& !"0".equals(ccId)){
			 * idle = orderLogic.isCoachIdle(Long.parseLong(userId),
			 * Integer.parseInt(ccId), new Date(Long.parseLong(cStart) * 1000),
			 * new Date(Long.parseLong(cEnd) * 1000)); }else{ idle =
			 * orderLogic.isCoachIdle(Long.parseLong(userId), null, new
			 * Date(Long.parseLong(cStart) * 1000), new
			 * Date(Long.parseLong(cEnd) * 1000)); } if(!idle){
			 * r.setCode(ResultCode.ERRORCODE.SUCCESS); return r.getResult(); }
			 * 
			 * }
			 */
			// coachClassService.saveCoachClass(ccv);
			String data = coachClassService.arrangeClass(ccv);
			r.setCode(data);
		} catch (Exception e) {
			log.error(
					"controller: coach post arrangement failed="
							+ e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取教练接单范围
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param headers
     * @return
     */
	@RequestMapping(value = "/{userId}/scope", method = RequestMethod.GET)
	@ResponseBody
	public Object getScope(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = coachService.getScope(userId, tokenId);
		} catch (Exception e) {
			log.error("controller: coach get scope failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 设置教练接单范围
	 *
	 * @param userId
	 * @param userType
	 * @param distance
	 * @param timestamp
	 * @param sign
	 * @param headers
     * @return
     */
	@RequestMapping(value = "/{userId}/scope", method = RequestMethod.POST)
	@ResponseBody
	public Object setScope(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String distance,
			@RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = coachService.setScope(userId, distance, tokenId);
		} catch (Exception e) {
			log.error("controller: coach set scope failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取训练场
	 *
	 * @param userId
	 * @param userType
	 * @param keyword
	 * @param rid
	 * @param timestamp
     * @param sign
     * @return
     */
	@RequestMapping(value = "/{userId}/trfields", method = RequestMethod.GET)
	@ResponseBody
	public Object getTrfields(@PathVariable String userId,
			@RequestParam String userType,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String rid,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = coachService.getTrfields(userId, keyword, rid);
		} catch (Exception e) {
			log.error(
					"controller: coach get trfields failed=" + e.getMessage(),
					e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取我的学员信息
	 *
	 * @param userId
	 * @param userType
	 * @param studentId
	 * @param timestamp
	 * @param sign
	 * @param headers
     * @return
     */
	@RequestMapping(value = "/{userId}/students/{studentId}", method = RequestMethod.GET)
	@ResponseBody
	public Object getStudentOne(@PathVariable String userId,
			@RequestParam String userType, @PathVariable String studentId,
			@RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = coachService.getStudentOne(userId, studentId, tokenId);
		} catch (Exception e) {
			log.error(
					"controller: coach get one student failed="
							+ e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取我的学员的技能信息
	 *
	 * @param userId
	 * @param userType
	 * @param studentId
	 * @param subjectId
	 * @param courseId
	 * @param type
	 * @param timestamp
     * @param sign
     * @return
     */
	@RequestMapping(value = "/{userId}/students/{studentId}/skills", method = RequestMethod.GET)
	@ResponseBody
	public Object getStudentSkill(@PathVariable String userId,
			@RequestParam String userType, @PathVariable String studentId,
			@RequestParam String subjectId,@RequestParam(required=false) String courseId,
			@RequestParam(required=false) String type, @RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String cid = "2";
			Integer drtype =null;
			if(null == type || "0".equals(type.trim())){
				cid = subjectId.trim();
			}else {
				// 根据学员的得分决定显示哪个科目
				Student s = studentManager
						.getStudentInfo(Long.parseLong(studentId));
				int c1 = s.getCourse1() == null ? 0 : s.getCourse1();
				int c2 = s.getCourse2() == null ? 0 : s.getCourse2();
				int c3 = s.getCourse3() == null ? 0 : s.getCourse3();
				int c4 = s.getCourse4() == null ? 0 : s.getCourse4();
				if (c3 != 0) {
					cid = "3";
				} else if (c2 == 100) {
					cid = "3";
				} else {
					cid = "2";
				}
				int dr = Integer.parseInt(s.getApplyCarType());
				if(dr == ReqConstants.DRIVE_TYPE_C1|| dr== ReqConstants.DRIVE_TYPE_C2){
					drtype = dr;
				}
			}
			//如果查询课程时，科目不需要传。驾照类型也不用传，因为现在c2也能上c1的课。
			if(null != courseId && !"".equals(courseId.trim())){
				cid = "";
				drtype = null;
			}

			r = orderLogic.getStudentScore(studentId, cid, courseId,null,drtype);
			r.setData("subjectId", cid);
		} catch (Exception e) {
			log.error(
					"controller: coach get student skills failed="
							+ e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 根据教练id获取学员信息，定向学员
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param pageNo
	 * @param pageSize
     * @param headers
     * @return
     */
	@RequestMapping(value = "/{userId}/students", method = RequestMethod.GET)
	@ResponseBody
	public Object getStudents(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign, @RequestParam String pageNo,
			@RequestParam String pageSize, @RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = studentService.getStudentInfoByCoachId(userId, tokenId, pageNo,
					pageSize);
		} catch (Exception e) {
			log.error(
					"controller: student get coaches failed=" + e.getMessage(),
					e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}


	/**
	 * 获取常用车辆信息
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param seqNum
	 * @param isCommon
     * @param headers
     * @return
     */
	@RequestMapping(value = "/brandcar", method = RequestMethod.GET)
	@ResponseBody
	public Object getBrandCar(@RequestParam String userId,
			@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign, @RequestParam String seqNum, 
			@RequestParam String isCommon, @RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = coachService.getBrandCarInfo(userId, seqNum, isCommon, tokenId);
		} catch (Exception e) {
			log.error("controller: coach get cars failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}
	
	/**
	 * 获取课程价格
	 *
	 * @param userId
	 * @param userType
	 * @param date 指定日期格式yyyy-MM-dd
	 * @param timestamp
	 * @param sign
	 * @param headers
     * @return
     */
	@RequestMapping(value = "/{userId}/coursePirce", method = RequestMethod.GET)
	@ResponseBody
	public Object getCoursePrice(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String date,
			@RequestParam String courseId,@RequestParam(required = false)  String colId,@RequestParam(required = false)  String dftype,
			@RequestParam(required = false)  String ctype, @RequestParam(required = false)  String calId,
			@RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			List<CoachClassPriceVo> coachPriceList = coachClassService.queryCoachPrice(Long.parseLong(userId), new Date(Long.parseLong(date)),  courseId, colId, dftype,	 ctype,  calId); 
			r.setCode(ResultCode.ERRORCODE.SUCCESS);
			r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
			r.setData(coachPriceList);
		} 
		catch (Exception e) {
			log.error( "controller: coach get coursePirce failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}
	
	@RequestMapping(value = "/{userId}/saveTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Object saveTemplate(@PathVariable String userId,
			@RequestParam String userType, @RequestParam String list,@RequestParam String handleType,
			@RequestParam(required = false)  String tempName,@RequestParam(required = false)  String tempId,
			@RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			 
			List<CoachClassTempVo> classTempList = new ArrayList<CoachClassTempVo>();
			
			if("1".equals(handleType)){
				//校验名称是否存在
				CoachClassTempVo coachClassTempVo = new CoachClassTempVo();
				coachClassTempVo.setTempName(tempName);
				coachClassTempVo.setCoachId(Long.parseLong(userId));
				Integer isExit = coachManager.isExitClassTempName(coachClassTempVo);
				if(isExit > 0){
					r.setCode(ResultCode.ERRORCODE.TEMP_NAME_IS_EIXT);
					r.setMsgInfo(ResultCode.ERRORINFO.TEMP_NAME_IS_EIXT);
					return r.getResult();
				}
			}
			
			if (!"3".equals(handleType)) {
				Gson gson = new Gson();
				String jsonStr = gson.toJson(list);  //javabean转换成json字符串 
				log.info("********************************* jsonStr :" + jsonStr);
				jsonStr = jsonStr.replaceAll("\\\\","");//转义
				jsonStr = jsonStr.substring(1, jsonStr.length()-1);//去首位引号
				log.info("********************************* jsonStr change :" + jsonStr);
				
				//json字符串转换成javabean对象  
				List<CoachClassTempVo> classTempJsonList = gson.fromJson(jsonStr, new TypeToken<List<CoachClassTempVo>>(){}.getType());  
				log.info("********************************* classTempList  :" + classTempJsonList.size());
				for(CoachClassTempVo coachClassTempVo : classTempJsonList){  
					log.info("**************** "+ coachClassTempVo.getCourseId()+","+coachClassTempVo.getCourseId()
					+","+coachClassTempVo.getCstart()+","+coachClassTempVo.getCend()+","+coachClassTempVo.getFieldId());  
					//计算时长
					coachClassTempVo.setDuration(TimeUtil.timeSpan(new Date(Long.parseLong(coachClassTempVo.getCstart()) * 1000), 
							new Date(Long.parseLong(coachClassTempVo.getCend())* 1000),"hour"));
					if (!"".equals(coachClassTempVo.getCstart()) && coachClassTempVo.getCstart() != null) {
						coachClassTempVo.setCstart(TimeUtil.getDateFormat(new Date(Long.parseLong(coachClassTempVo.getCstart())* 1000)));
					}
					if (!"".equals(coachClassTempVo.getCend()) && coachClassTempVo.getCend() != null) {
						coachClassTempVo.setCend(TimeUtil.getDateFormat(new Date(Long.parseLong(coachClassTempVo.getCend())* 1000)));
					}
					classTempList.add(coachClassTempVo);
				}  
				BeanCopy.setListField(classTempList, "coachId", Long.parseLong(userId));
				if (!"".equals(tempId) && tempId != null) {
					BeanCopy.setListField(classTempList, "tempId", Integer.valueOf(tempId));
				}
				if (!"".equals(tempName) && tempName != null) {
					BeanCopy.setListField(classTempList, "tempName", tempName);
				}
			}
	        
			r = coachService.saveClassTemp(handleType,classTempList, tempId);
			
		} 
		catch (Exception e) {
			log.error( "controller: coach get coursePirce failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	@RequestMapping(value = "/{userId}/template", method = RequestMethod.GET)
	@ResponseBody
	public Object template(@PathVariable String userId,
			@RequestParam String userType, @RequestParam(required = false)  String tempName,@RequestParam(required = false)  String tempId,			
			@RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = coachService.queryTemplate(userId, tempId,tempName);
			r.setCode(ResultCode.ERRORCODE.SUCCESS);
			r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
		} 
		catch (Exception e) {
			log.error( "controller: coach get coursePirce failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	@RequestMapping(value = "/{userId}/addClassForTemp", method = RequestMethod.POST)
	@ResponseBody
	public Object addClassForTemp(@PathVariable String userId, @RequestParam String userType, @RequestParam String date,@RequestParam String tempId,		
			@RequestParam String timestamp, @RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = coachService.addClassForTemp(Long.parseLong(userId), new Date(Long.parseLong(date)),tempId);
		} 
		catch (Exception e) {
			log.error( "controller: coach get coursePirce failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 获取错误申诉选项数据
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/errorAppeal", method = RequestMethod.GET)
	@ResponseBody
	public Object errorAppeal(
			@RequestParam String userId,
			@RequestParam String userType,
			@RequestParam String timestamp, 
			@RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = coachService.getErrorAppealItems();
			r.setCode(ResultCode.ERRORCODE.SUCCESS);
			r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
		} 
		catch (Exception e) {
			log.error( "controller: coach get errorAppealItem failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 提交错误申诉申请
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param itemIds
	 * @param appealContent
	 * @return
	 */
	@RequestMapping(value = "/{userId}/errorAppeal", method = RequestMethod.POST)
	@ResponseBody
	public Object submitErrorAppeal(
			@PathVariable String userId, 
			@RequestParam String userType,
			@RequestParam String timestamp,
			@RequestParam String sign,
			@RequestParam String itemIds,
			@RequestParam String appealContent) {
		ReqResult r = new ReqResult();
		try {
			ErrorAppeal errorAppeal = new ErrorAppeal();
			errorAppeal.setCoachId(Long.valueOf(userId));
			errorAppeal.setItemIds(itemIds);
			errorAppeal.setAppealContent(appealContent);
			r = coachService.addErrorAppeal(errorAppeal);
		} 
		catch (Exception e) {
			log.error( "controller: coach commit errorAppeal failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
    /**
     * 上传驾照信息
     *
     * @param userId
     * @param userType
     * @param drType    准驾驶车型
     * @param drLicence 驾照编号
     * @param drPhoto2  驾驶证图片地址
     * @param timestamp
     * @param sign
     * @return
     */
    @RequestMapping(value = "/{userId}/driveCard", method = RequestMethod.POST)
    @ResponseBody
    public Object addDriveCard(@PathVariable String userId, @RequestParam String userType,@RequestParam String studentId, 
                               @RequestParam(required = false) String drType, @RequestParam(required = false) String drLicence,
                               @RequestParam(required = false) String drPhoto2,
                               @RequestParam String drPhoto, @RequestParam(required = false) String drExpires,
                               @RequestParam String timestamp, @RequestParam String sign) {
        ReqResult r = ReqResult.getSuccess();
        try {
            r = studentService.addDriveCard(studentId, drType, drLicence, drPhoto, drPhoto2, drExpires);
        } catch (Exception e) {
            log.error("controller: student post driveCard failed=" + e.getMessage(), e);
            e.printStackTrace();
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }

        return r.getResult();
    }
    
    
    /**
     * 教练获取学员的自主预约的排班信息；产品要求不会超过20条，不需要分页处理
     * @param userId
     * @param userType
     * @param timestamp
     * @param sign
     * @return
     */
	@RequestMapping(value = "/{userId}/students/class", method = RequestMethod.GET)
	@ResponseBody
	public Object getStudentsClass(
			@RequestParam String userId,
			@RequestParam String userType,
			@RequestParam String timestamp, 
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			
			r = studentClassService.getStudentsClass(userId,userType);
		} 
		catch (Exception e) {
			log.error( "controller: coach get getStudentsClass failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
		
	/**
	 * 教练设置学员自主排班订单状态
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param orderId
	 * @param state		1-教练已接单；2-教练已取消；
	 * @param trfieldId
	 * @param trfieldName
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/students/class/state", method = RequestMethod.POST)
	@ResponseBody
	public Object setStudentsClassState(
			@RequestParam String userId,
			@RequestParam String userType,
			@RequestParam String timestamp, 
			@RequestParam String orderId, 
			@RequestParam String state, 
			@RequestParam(required = false) String placeId,
			@RequestParam(required = false) String placeName,
			@RequestParam(required = false) String placeLge,
			@RequestParam(required = false) String placeLae,@RequestParam(required = false) String v,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = studentClassService.setStudentsClassState(userId,userType,orderId,state,placeId,placeName,placeLge,placeLae,v);
		} 
		catch (Exception e) {
			log.error( "controller: coach get getStudentsClass failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	
	/**
	 * 注册教练上传/更新驾驶证、行驶证、车辆信息
	 * @param userId
	 * @param userType
	 * @param drPhoto
	 * @param drPhoto2
	 * @param drivePhoto
	 * @param drivePhoto2
	 * @param carNoList
	 * @param driveTypeList
	 * @param carImg
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/addRegCoach", method = RequestMethod.POST)
    @ResponseBody
    public Object addRegCoach(@PathVariable String userId, @RequestParam String userType,@RequestParam(required = false) String cityId,
                               @RequestParam(required = false) String drPhoto, @RequestParam(required = false) String drPhoto2,
                               @RequestParam(required = false) String list, @RequestParam(required = false) String isNewDrPhoto, 
                               @RequestParam String timestamp, @RequestParam String sign) {
        ReqResult r = ReqResult.getSuccess();
        try {
        	List<CarCheck> carJsonList = null;
        	if (list != null && !"".equals(list)) {
        		Gson gson = new Gson();
        		String jsonStr = gson.toJson(list);  //javabean转换成json字符串 
        		log.info("********************************* jsonStr :" + jsonStr);
        		jsonStr = jsonStr.replaceAll("\\\\","");//转义
        		jsonStr = jsonStr.substring(1, jsonStr.length()-1);//去首位引号
        		log.info("********************************* jsonStr change :" + jsonStr);
        		
        		//json字符串转换成javabean对象  
        		carJsonList = gson.fromJson(jsonStr, new TypeToken<List<CarCheck>>(){}.getType());  
        		log.info("********************************* classTempList  :" + carJsonList.size());
        	}
			
            r = coachService.addRegCoach(userId, drPhoto, drPhoto2, isNewDrPhoto, cityId,  carJsonList);
        } catch (Exception e) {
            log.error("controller: addRegCoach post failed=" + e.getMessage(), e);
            e.printStackTrace();
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }

        return r.getResult();
    }
	

	/**
	 * 查询注册教练上传信息
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/checkInfo", method = RequestMethod.GET)
	@ResponseBody
	public Object checkInfo(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = coachService.checkInfo(userId);
		} catch (Exception e) {
			log.error("controller: addRegCoach post failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
       
	
	/**
	 * 获取注册城市信息
	 * @param cityId
	 * @param cityName
	 * @param pid
	 * @param rlevel
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getRegistCity", method = RequestMethod.GET)
	@ResponseBody
	public Object getRegistCity(@RequestParam(required = false) String cityId,@RequestParam(required = false) String cityName,
			@RequestParam(required = false) String pid,@RequestParam(required = false) String rlevel,
				@RequestParam String timestamp,@RequestParam String sign ) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = coachService.getRegistCity(cityId, cityName, pid, rlevel);
		} catch (Exception e) {
			log.error("SchoolController: get enroll list=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	
	/**
	 * 添加、编辑招生名片
	 * @param userId
	 * @param userType
	 * @param headIcon
	 * @param name
	 * @param phoneNum
	 * @param coachAge
	 * @param cityId
	 * @param area
	 * @param coachTag
	 * @param profile
	 * @param list
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/addEnrollVisCard", method = RequestMethod.POST)
    @ResponseBody
    public Object addEnrollVisCard(@PathVariable String userId, @RequestParam String userType,@RequestParam(required = false) String headIcon,
    							@RequestParam(required = false) String name,@RequestParam(required = false) String phoneNum,@RequestParam(required = false) String schoolName,
                               @RequestParam(required = false) String schoolAge, @RequestParam(required = false) String cityId,@RequestParam(required = false) String cityName,
                               @RequestParam(required = false) String area, @RequestParam(required = false) String coachTag,
                               @RequestParam(required = false) String profile, @RequestParam(required = false) String list, 
                               @RequestParam String timestamp, @RequestParam String sign) {
        ReqResult r = ReqResult.getSuccess();
        try {
        	List<WechatEnrollClass> enrollJsonList = null;
        	if (list != null && !"".equals(list)) {
        		Gson gson = new Gson();
        		String jsonStr = gson.toJson(list);  //javabean转换成json字符串 
        		log.info("********************************* jsonStr :" + jsonStr);
        		jsonStr = jsonStr.replaceAll("\\\\","");//转义
        		jsonStr = jsonStr.substring(1, jsonStr.length()-1);//去首位引号
        		log.info("********************************* jsonStr change :" + jsonStr);
        		//json字符串转换成javabean对象  
        		enrollJsonList = gson.fromJson(jsonStr, new TypeToken<List<WechatEnrollClass>>(){}.getType());  
        		log.info("********************************* classTempList  :" + enrollJsonList.size());
        	}
			
            r = wechatSchoolService.addEnrollVisCard(Long.parseLong(userId), headIcon, name,schoolName, phoneNum, schoolAge,  cityId, cityName, area, coachTag, profile, enrollJsonList);
            
        } catch (Exception e) {
            log.error("controller: addEnrollVisCard post failed=" + e.getMessage(), e);
            e.printStackTrace();
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }

        return r.getResult();
    }
	
	/**
	 * 获取招生名片
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getEnrollVisCard", method = RequestMethod.GET)
	@ResponseBody
	public Object getEnrollVisCard(@RequestParam String coachId, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = wechatSchoolService.getEnrollVisCard(Long.parseLong(coachId));
			
		} catch (Exception e) {
			log.error("controller: getEnrollVisCard post failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 批量导入学员
	 * @param userId
	 * @param userType
	 * @param list
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/inportStudent", method = RequestMethod.POST)
    @ResponseBody
    public Object inportStudent(@PathVariable String userId, @RequestParam String userType,
                              @RequestParam(required = false) String list, 
                               @RequestParam String timestamp, @RequestParam String sign) {
        ReqResult r = ReqResult.getSuccess();
        try {
        	List<WechatMycoaches> stuJsonList = null;
        	if (list != null && !"".equals(list)) {
        		Gson gson = new Gson();
        		String jsonStr = gson.toJson(list);  //javabean转换成json字符串 
        		log.info("********************************* jsonStr :" + jsonStr);
        		jsonStr = jsonStr.replaceAll("\\\\","");//转义
        		jsonStr = jsonStr.substring(1, jsonStr.length()-1);//去首位引号
        		log.info("********************************* jsonStr change :" + jsonStr);
        		//json字符串转换成javabean对象  
        		stuJsonList = gson.fromJson(jsonStr, new TypeToken<List<WechatMycoaches>>(){}.getType());  
        		log.info("********************************* classTempList  :" + stuJsonList.size());
        	}
			
            r = wechatSchoolService.inportStudent(Long.parseLong(userId),  stuJsonList);
            
        } catch (Exception e) {
            log.error("controller: inportStudent post failed=" + e.getMessage(), e);
            e.printStackTrace();
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }

        return r.getResult();
    }
	
	/**
	 * 手工导入学员
	 * @param userId
	 * @param userType
	 * @param studentName
	 * @param studentPhone
	 * @param state
	 * @param drtype
	 * @param coachRemark
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/inportStudentOne", method = RequestMethod.POST)
	@ResponseBody
	public Object inportStudentOne(@PathVariable String userId, @RequestParam String userType,@RequestParam(required = false) String studentId,
			@RequestParam(required = false) String studentName,@RequestParam(required = false) String studentPhone,
            @RequestParam(required = false) String state, @RequestParam(required = false) String drtype,
            @RequestParam(required = false) String coachRemark,  
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = wechatSchoolService.inportStudentOne(Long.parseLong(userId),  studentId, studentName, studentPhone, state, drtype, coachRemark);
			
		} catch (Exception e) {
			log.error("controller: inportStudentOne post failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 教练接受、删除学员
	 * @param userId
	 * @param userType
	 * @param studentId
	 * @param studentName
	 * @param studentPhone
	 * @param isdel
	 * @param type
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/acceptOrDelStudent", method = RequestMethod.POST)
	@ResponseBody
	public Object acceptOrDelStudent(@PathVariable String userId, @RequestParam String userType,@RequestParam String studentId,
			@RequestParam(required = false) String state, @RequestParam(required = false) String isdel, @RequestParam(required = false) String type,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = wechatSchoolService.acceptOrDelStudent(Long.parseLong(userId),  Long.parseLong(studentId), isdel, state, type);
			
		} catch (Exception e) {
			log.error("controller: inportStudentOne post failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 添加排班
	 * @param userId
	 * @param userType
	 * @param carId
	 * @param drtype
	 * @param courseId
	 * @param placeId
	 * @param placeName
	 * @param maxNum
	 * @param cStart
	 * @param timeNum
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/addWechatClass", method = RequestMethod.POST)
	@ResponseBody
	public Object addWechatClass(@PathVariable String userId, @RequestParam String userType,
			@RequestParam(required = false) String carId,@RequestParam(required = false) String drtype,
			@RequestParam(required = false) String courseId,@RequestParam(required = false) String courseName,
			@RequestParam(required = false) String placeId,@RequestParam(required = false) String placeName, @RequestParam(required = false) String maxNum, 
			@RequestParam String cStart, @RequestParam String timeNum, @RequestParam String list, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			List<WechatPlantClass> stuJsonList = null;
        	if (list != null && !"".equals(list)) {
        		Gson gson = new Gson();
        		String jsonStr = gson.toJson(list);  //javabean转换成json字符串 
        		log.info("********************************* jsonStr :" + jsonStr);
        		jsonStr = jsonStr.replaceAll("\\\\","");//转义
        		jsonStr = jsonStr.substring(1, jsonStr.length()-1);//去首位引号
        		log.info("********************************* jsonStr change :" + jsonStr);
        		//json字符串转换成javabean对象  
        		stuJsonList = gson.fromJson(jsonStr, new TypeToken<List<WechatPlantClass>>(){}.getType());  
        		log.info("********************************* classTempList  :" + stuJsonList.size());
        	}
			r = wechatSchoolService.addWechatClass(Long.parseLong(userId),  carId, drtype, courseId, courseName, placeId, placeName, maxNum, cStart, timeNum, stuJsonList);
			
		} catch (Exception e) {
			log.error("controller: addWechatClass post failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 关闭排班
	 * @param userId
	 * @param userType
	 * @param ccid
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/closeWechatClass", method = RequestMethod.POST)
	@ResponseBody
	public Object closeWechatClass(@PathVariable String userId, @RequestParam String userType,@RequestParam String ccid,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = wechatSchoolService.closeWechatClass(Long.parseLong(userId),  Integer.parseInt(ccid));
		} catch (Exception e) {
			log.error("controller: closeWechatClass post failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	//更新课程
	@RequestMapping(value = "/{userId}/updateWechatClass", method = RequestMethod.POST)
	@ResponseBody
	public Object updateWechatClass(@PathVariable String userId, @RequestParam String userType,@RequestParam String orderId,@RequestParam String ccid,
			@RequestParam(required = false) String carId,@RequestParam(required = false) String drtype,
			@RequestParam(required = false) String courseId,@RequestParam(required = false) String courseName,
			@RequestParam(required = false) String placeId,@RequestParam(required = false) String placeName, @RequestParam(required = false) String maxNum, 
			@RequestParam(required = false) String cStart, @RequestParam String timeNum, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = wechatSchoolService.updateWechatClass(Long.parseLong(userId), orderId, Integer.parseInt(ccid),  carId, drtype, courseId, courseName, placeId, placeName, maxNum, cStart, timeNum);
			
		} catch (Exception e) {
			log.error("controller: addWechatClass post failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
       
	/**
	 * 获取排班列表
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/getWechatClass", method = RequestMethod.GET)
	@ResponseBody
	public Object getWechatClass(@PathVariable String userId, @RequestParam String userType,
			@RequestParam(required = false) String pageSize,@RequestParam(required = false) String pageNo, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = wechatSchoolService.getWechatClass(Long.parseLong(userId),pageSize , pageNo);
			
		} catch (Exception e) {
			log.error("controller: getWechatClass GET failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 获取单个排班详情
	 * @param userId
	 * @param userType
	 * @param orderId
	 * @param ccid
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/getWechatClassOne", method = RequestMethod.GET)
	@ResponseBody
	public Object getWechatClassOne(@PathVariable String userId, @RequestParam String userType,
			@RequestParam(required = false) String orderId,@RequestParam(required = false) String ccid, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = wechatSchoolService.getWechatClassOne(Long.parseLong(userId), orderId , ccid);
			
		} catch (Exception e) {
			log.error("controller: getWechatClassOne GET failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	
	/**
	 * 添加、删除、更新上课学员
	 * @param userId
	 * @param userType
	 * @param ccid
	 * @param type
	 * @param studentId
	 * @param studentIdNew
	 * @param list
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/alertClassStudent", method = RequestMethod.POST)
	@ResponseBody
	public Object alertClassStudent(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String ccid, @RequestParam(required = false) String orderId,@RequestParam(required = false) String list,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			List<WechatPlantClass> stuJsonList = null;
        	if (list != null && !"".equals(list)) {
        		Gson gson = new Gson();
        		String jsonStr = gson.toJson(list);  //javabean转换成json字符串 
        		log.info("********************************* jsonStr :" + jsonStr);
        		jsonStr = jsonStr.replaceAll("\\\\","");//转义
        		jsonStr = jsonStr.substring(1, jsonStr.length()-1);//去首位引号
        		log.info("********************************* jsonStr change :" + jsonStr);
        		//json字符串转换成javabean对象  
        		stuJsonList = gson.fromJson(jsonStr, new TypeToken<List<WechatPlantClass>>(){}.getType());  
        		log.info("********************************* classTempList  :" + stuJsonList.size());
        	}
			r = wechatSchoolService.alertClassStudent(Long.parseLong(userId), Integer.parseInt(ccid) , orderId, stuJsonList);
			
		} catch (Exception e) {
			log.error("controller: getWechatClass GET failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 获取学员列表
	 * @param userId
	 * @param userType
	 * @param studentName
	 * @param state
	 * @param sortType
	 * @param pageSize
	 * @param pageNo
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/getWechatStudent", method = RequestMethod.GET)
	@ResponseBody
	public Object getWechatStudent(@PathVariable String userId, @RequestParam String userType,
			@RequestParam(required = false) String studentName,@RequestParam(required = false) String state, @RequestParam(required = false) String type, 
			@RequestParam(required = false) String sortType, @RequestParam(required = false) String channel, @RequestParam(required = false) String isNew, 
			@RequestParam(required = false) String pageSize, @RequestParam(required = false) String pageNo, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = wechatSchoolService.getWechatStudent(Long.parseLong(userId), studentName, state, sortType, type, channel,  isNew, pageSize, pageNo) ;
			
		} catch (Exception e) {
			log.error("controller: getWechatStudent GET failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 置顶学员
	 * @param userId
	 * @param userType
	 * @param studentId
	 * @param isTop
	 * @param state
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/topAndStaeStudent", method = RequestMethod.POST)
	@ResponseBody
	public Object topAndStaeStudent(@PathVariable String userId, @RequestParam String userType,
			@RequestParam(required = false) String studentId, @RequestParam(required = false) String isTop, @RequestParam(required = false) String state, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = wechatSchoolService.topAndStaeStudent(Long.parseLong(userId), Long.parseLong(studentId), isTop, state);
			
		} catch (Exception e) {
			log.error("controller: getWechatStudent GET failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 获取自己的学员报名列表
	 * @param userId
	 * @param userType
	 * @param pageNo
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/getWechatEnroll", method = RequestMethod.GET)
	@ResponseBody
	public Object getWechatEnroll(@PathVariable String userId, @RequestParam String userType,@RequestParam(required = false) String classId,
			@RequestParam(required = false) String pageNo, @RequestParam(required = false) String pageSize, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = wechatSchoolService.getWechatEnroll(Long.parseLong(userId), classId, pageSize, pageNo) ;
			
		} catch (Exception e) {
			log.error("controller: getWechatEnroll GET failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 更新教练报名学员备注
	 * @param userId
	 * @param userType
	 * @param orderId
	 * @param remark
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/updateWechatEnroll", method = RequestMethod.POST)
	@ResponseBody
	public Object updateWechatEnroll(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String orderId, @RequestParam String remark, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = wechatSchoolService.updateWechatEnroll(Long.parseLong(userId), orderId, remark) ;
			
		} catch (Exception e) {
			log.error("controller: getWechatEnroll GET failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 获取标签
	 * @param userId
	 * @param userType
	 * @param courseId
	 * @param type
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getCommetTag", method = RequestMethod.GET)
	@ResponseBody
	public Object getCommetTag(@RequestParam String userId, @RequestParam String userType,
			@RequestParam(required = false) String courseId, @RequestParam(required = false) String type, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r =  orderLogic.getCommetTag(courseId, type);
			
		} catch (Exception e) {
			log.error("controller: getCommetTag GET failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 获取学员详情
	 */
	@RequestMapping(value = "/{userId}/getEnrollStudentInfo", method = RequestMethod.GET)
	@ResponseBody
	public Object getEnrollStudentInfo(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String studentId, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r =  wechatSchoolService.getEnrollStudentInfo(Long.parseLong(userId), Long.parseLong(studentId));
			
		} catch (Exception e) {
			log.error("controller: getEnrollStudentInfo GET failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 获取学员各科目的统计数量及总数
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/getMyStudentNum", method = RequestMethod.GET)
	@ResponseBody
	public Object getMyStudentNum(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r =  wechatSchoolService.getMyStudentNum(Long.parseLong(userId));
			
		} catch (Exception e) {
			log.error("controller: getEnrollStudentInfo GET failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 获取学员的上课记录
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/getEnStudentClassHis", method = RequestMethod.GET)
	@ResponseBody
	public Object getEnStudentClassHis(@PathVariable String userId, @RequestParam String userType, @RequestParam String studentId,@RequestParam String type,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r =  wechatSchoolService.getEnStudentClassHis(Long.parseLong(userId), Long.parseLong(studentId), Integer.parseInt(type));
			
		} catch (Exception e) {
			log.error("controller: getEnrollStudentInfo GET failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 获取首页信息
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/getHomePageInfo", method = RequestMethod.GET)
	@ResponseBody
	public Object getHomePageInfo(@RequestParam String userId, @RequestParam String userType,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r =  wechatSchoolService.getHomePageInfo(Long.parseLong(userId));
			
		} catch (Exception e) {
			log.error("controller: getHomePageInfo GET failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 删除绑定学员
	 * @param userId
	 * @param userType
	 * @param studentId
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/delStudentBound", method = RequestMethod.POST)
	@ResponseBody
	public Object delStudentBound(@RequestParam String userId, @RequestParam String userType,
			@RequestParam String studentId, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r =  wechatSchoolService.delStudentBound(Long.parseLong(userId), Long.parseLong(studentId));
			
		} catch (Exception e) {
			log.error("controller: delStudentBound POST failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 暂时不用，客户端直接调用户手机发短信
	 * @param userId
	 * @param userType
	 * @param studentId
	 * @param studentPhone
	 * @param cStart
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/smsNoticeStudent", method = RequestMethod.POST)
	@ResponseBody
	public Object smsNoticeStudent(@RequestParam String userId, @RequestParam String userType,
			@RequestParam(required = false) String studentId, @RequestParam String studentPhone, @RequestParam String cStart, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r =  wechatSchoolService.smsNoticeStudent(Long.parseLong(userId),  studentId, studentPhone, cStart);
			
		} catch (Exception e) {
			log.error("controller: smsNoticeStudent POST failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 获取二维码
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getQrCode", method = RequestMethod.GET)
	@ResponseBody
	public Object getQrCode(@RequestParam String userId, @RequestParam String userType,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r =  wechatSchoolService.getQrCode(Long.parseLong(userId));
			
		} catch (Exception e) {
			log.error("controller: getQrCode POST failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
}



























