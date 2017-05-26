package com.lili.school.service.impl;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.lili.authcode.dto.Notice;
import com.lili.authcode.manager.NoticeManager;
import com.lili.authcode.service.AuthcodeService;
import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CoachManager;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.OrderConstant;
import com.lili.common.constant.WechatConstant.TEMPLATEID;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.DateUtil;
import com.lili.common.util.MyRowBounds;
import com.lili.common.util.SecurityUtil;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.file.service.FileService;
import com.lili.order.service.CoachClassService;
import com.lili.order.service.CoachStatisticService;
import com.lili.order.service.OrderService;
import com.lili.order.service.PlantClassService;
import com.lili.order.vo.CoachClassQuery;
import com.lili.order.vo.CoachClassVo;
import com.lili.order.vo.CoachStatisticVo;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.order.vo.PlantClassQuery;
import com.lili.order.vo.PlantClassVo;
import com.lili.order.vo.WaitOrderVo;
import com.lili.school.dto.EnrollPurpose;
import com.lili.school.dto.WechatCoachClassDto;
import com.lili.school.dto.WechatEnrollStudentDto;
import com.lili.school.dto.WechatMycoachesDto;
import com.lili.school.dto.WechatPlantClassDto;
import com.lili.school.manager.WechatSchoolManager;
import com.lili.school.service.WechatCoachService;
import com.lili.school.service.WechatSchoolService;
import com.lili.school.vo.CoachWechat;
import com.lili.school.vo.StudentWechatCoach;
import com.lili.school.vo.StudentWechatEnroll;
import com.lili.school.vo.Template;
import com.lili.school.vo.TemplateParam;
import com.lili.school.vo.WechatCoachClass;
import com.lili.school.vo.WechatCoachMyClass;
import com.lili.school.vo.WechatEnrollClass;
import com.lili.school.vo.WechatEnrollClassVo;
import com.lili.school.vo.WechatEnrollStudent;
import com.lili.school.vo.WechatMycoaches;
import com.lili.school.vo.WechatOrder;
import com.lili.school.vo.WechatPlantClass;
import com.lili.school.vo.WechatStudent;
import com.lili.school.vo.WechatStudentCount;
import com.lili.school.vo.WechatTemplate;
import com.lili.student.manager.StudentManager;


public class WechatSchoolServiceImpl implements WechatSchoolService {

	private static Logger logger = LoggerFactory.getLogger(WechatSchoolServiceImpl.class);
	
	@Autowired
	WechatSchoolManager wechatSchoolManager;
	
	@Autowired
	CoachManager coachManager;
	
	@Autowired
	StudentManager studentManager;
	
    @Autowired
    AuthcodeService authcodeService;
    
    @Autowired
    OrderService orderService;
    
    @Autowired
    FileService fileService;

	@Autowired
	private CoachStatisticService coachStatisticService; 
	
	@Autowired
	WechatCoachService wechatCoachService;
	
	@Autowired
	private CoachClassService coachClassService;
	
	@Autowired
	private PlantClassService plantClassService;
	
	@Autowired
	RedisUtil redisUtil;
	
    @Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;
    
    @Autowired
    NoticeManager noticeManager;
    
    @Value("#{configWxCoach[wechat_page_domain_name]}")
    private String wechat_page_domain_name = "http://uatweixinjl.lilixc.com";
	
	private String passMsgId = "128930"; //注册教练审核通过2
	
	@Override
	public ReqResult addEnrollVisCard(Long coachId, String headIcon, String name, String schoolName, String phoneNum, String schoolAge,
			String cityId, String cityName,String area, String coachTag, String profile, List<WechatEnrollClass> enrollJsonList) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			Coach coach = new Coach();
			coach.setCoachId(coachId );
			boolean isUpCoach = false; //是否需要更新教练个人信息
			if (headIcon != null) {
				coach.setHeadIcon(headIcon);
				isUpCoach = true;
			}
			if (StringUtils.isNotEmpty(name)) {
				coach.setName(name);
				isUpCoach = true;
			}
			if (schoolName != null) {
				coach.setSchoolName(schoolName);
				isUpCoach = true;
			}
			if (phoneNum != null) {
				coach.setPhoneNum(phoneNum);
				isUpCoach = true;
			}
			if (StringUtils.isNotEmpty(schoolAge)) {
				coach.setSchoolAge(Integer.parseInt(schoolAge));
				isUpCoach = true;
			}
			if (StringUtils.isNotEmpty(cityId)) {
				coach.setCityId(Integer.parseInt(cityId));
				coach.setCityName(cityName);
				isUpCoach = true;
			}
			if (area != null) {
				coach.setArea(area);
				isUpCoach = true;
			}
			if (coachTag != null) {
				coach.setCoachTag(coachTag);
				isUpCoach = true;
			}
			if (profile != null) {
				coach.setProfile(profile);
				isUpCoach = true;
			}
			Coach coachInfo = coachManager.getCoachInfo(coachId);
			if (coachInfo != null && coachInfo.getQrcode() == null || "".equals(coachInfo.getQrcode())) {
				//添加教练二维码信息
				String wikiUrl = wechatCoachService.getWikiUrl(coachId);
				coach.setQrcode(wikiUrl);
				isUpCoach = true;
			}
			
			if (isUpCoach) {
				//更新教练个人信息
				coachManager.updateCoach(coach); 
				logger.info("************************************** Update Coach Info Success! coach : " + coach);
			}
			
			if (enrollJsonList != null && enrollJsonList.size() > 0) {
				if (coachInfo != null && coachInfo.getSchoolId() != null && !"".equals(coachInfo.getSchoolId())) {
					BeanCopy.setListField(enrollJsonList, "schoolId", coachInfo.getSchoolId());
				}
				BeanCopy.setListField(enrollJsonList, "coachId", coachId);
				//添加班型信息
				r = wechatSchoolManager.addEnrollVisCard(enrollJsonList);
				logger.info("************************************** addEnrollVisCard Success! enrollJsonList : " + enrollJsonList);
			}
			
		} catch (NumberFormatException e) {
			logger.error("************************************** Error addEnrollVisCard NumberFormatException : " + e.getMessage());
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("**************************************Error addEnrollVisCard Exception: " + e.getMessage());
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		
		return r;
	}
	

	@Override
	public ReqResult getEnrollVisCard(Long coachId) {
		ReqResult r = ReqResult.getSuccess();
		try {
			redisUtil.delete(REDISKEY.COACH + coachId);
			redisUtil.delete(REDISKEY.COACH_VO + coachId);
			Coach coach = coachManager.getCoachInfo(coachId);
			if (coach != null && coach.getQrcode() == null || "".equals(coach.getQrcode())) {
				//添加教练二维码信息
				String wikiUrl = wechatCoachService.getWikiUrl(coachId);
				coach.setQrcode(wikiUrl);
				coachManager.updateCoach(coach);
			}
			
			CoachWechat  coachWechat = new CoachWechat();
			coachWechat = BeanCopy.copyNotNull(coach,CoachWechat.class);
			
			//获取头像地址
			if (StringUtils.isNotEmpty(coachWechat.getHeadIcon())) {
				ReqResult reqResult = fileService.getDownUrlByKey(null, null, coachWechat.getHeadIcon(), null);
				logger.info("************************************* setHeadIconStr :" + reqResult.getResult().get(ResultCode.RESULTKEY.DATA));
				if (reqResult.isSuccess()) {
					@SuppressWarnings("unchecked")
					Map<String,String> mapR = (Map<String, String>) reqResult.getResult().get(ResultCode.RESULTKEY.DATA);
					coachWechat.setHeadIconStr(mapR.get("downUrl"));
				}
			}
			
			WechatEnrollClass wechatEnrollClass = new WechatEnrollClass();
			wechatEnrollClass.setCoachId(coachId);
			wechatEnrollClass.setIsDel(0);
			List<WechatEnrollClass> enrollList =  wechatSchoolManager.queryWechatEnrollClassList(wechatEnrollClass);
			if (enrollList != null && enrollList.size() > 0) {
				coachWechat.setWechatEnrollClassList(enrollList);
			}
			r.setData(coachWechat);
		} catch (Exception e) {
			logger.error("**************************************Error getEnrollVisCard Exception: " + e.getMessage());
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ReqResult inportStudent(Long coachId, List<WechatMycoaches> weStuJsonList) {
		ReqResult r = ReqResult.getSuccess();
		
		 try {
			r  = wechatSchoolManager.inportStudent(coachId, weStuJsonList);
		} catch (Exception e) {
			logger.error("**************************************Error inportStudent Exception: " + e.getMessage());
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ReqResult inportStudentOne(Long coachId, String studentId, String studentName, String studentPhone, String state,
			String drtype, String coachRemark) {
		ReqResult r = ReqResult.getSuccess();
			
		try {
			//判断手机号是否存在
			WechatStudent studentRecord = new WechatStudent();
			studentRecord.setPhone(studentPhone);			
			if (StringUtils.isNotEmpty(studentId)) { //更新
				//判断是否已有绑定关系
				WechatMycoachesDto record = new WechatMycoachesDto();
				record.setCoachId(coachId);
				record.setPhone(studentPhone);
				record.setIsdel(0);
				//判断是否已存在绑定关系，如存在，则跳过
				WechatMycoaches isExitMyCoach = wechatSchoolManager.queryMyWechatBoundCoach(record);
				if (isExitMyCoach != null && isExitMyCoach.getStudentId() != Long.parseLong(studentId)) {
					r.setCode(ResultCode.ERRORCODE.IS_EXIT_BOUND);
					r.setMsgInfo(ResultCode.ERRORINFO.IS_EXIT_BOUND);
					return r;
				}
				studentRecord.setStudentId(Long.parseLong(studentId));
				WechatStudent wechatStudent = wechatSchoolManager.queryWechatStudent(studentRecord);
				if (wechatStudent != null) {
					
					WechatMycoaches mycoaches = new WechatMycoaches();
					if (wechatStudent.getStudentId() != Long.parseLong(studentId)) { //更新手机号,且新手机号已存在
						mycoaches.setStudentIdNew(wechatStudent.getStudentId());
					}
					mycoaches.setStudentId(Long.parseLong(studentId));
					mycoaches.setCoachId(coachId);
					mycoaches.setName(studentName);
					mycoaches.setPhone(studentPhone);
					if (StringUtils.isNotEmpty(state)) {
						mycoaches.setState(Integer.parseInt(state));
					}
					if (StringUtils.isNotEmpty(drtype)) {
						mycoaches.setDrtype(Integer.parseInt(drtype));
					}
					mycoaches.setCoachRemark(coachRemark);
					wechatSchoolManager.updateWechatMycoaches(mycoaches);
				}
				else {
					r.setCode(ResultCode.ERRORCODE.NO_USER);
					r.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
				}
			}
			else {
				Long studentIdLong = null;
				String headIcon = "";
				WechatStudent wechatStudent = wechatSchoolManager.queryWechatStudent(studentRecord);
				if (wechatStudent != null) {
					studentIdLong = wechatStudent.getStudentId();
					headIcon = wechatStudent.getHeadIcon();
				}
				else {
					wechatSchoolManager.insertWechatStudent(studentRecord);
					WechatStudent wechatStudentIn = wechatSchoolManager.queryWechatStudent(studentRecord);
					studentIdLong = wechatStudentIn.getStudentId();
					headIcon = wechatStudentIn.getHeadIcon();
				}
				
				WechatMycoaches mycoaches = new WechatMycoaches();
				mycoaches.setStudentId(studentIdLong);
				mycoaches.setCoachId(coachId);
				mycoaches.setName(studentName);
				mycoaches.setPhone(studentPhone);
				mycoaches.setHeadIcon(headIcon);
				if (StringUtils.isNotEmpty(state)) {
					mycoaches.setState(Integer.parseInt(state));
				}
				if (StringUtils.isNotEmpty(drtype)) {
					mycoaches.setDrtype(Integer.parseInt(drtype));
				}
				mycoaches.setCoachRemark(coachRemark);
				r  = wechatSchoolManager.inportStudentOne(mycoaches);
				
			}
		} catch (NumberFormatException e) {
			logger.error("**************************************Error inportStudentOne Exception: " + e.getMessage());
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ReqResult addWechatClass(Long coachId, String carId, String drtype, String courseId,String courseName, String placeId,
			String placeName, String maxNum, String cStart, String timeNum, List<WechatPlantClass> stuJsonList) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			//一次课程最多4个学员
			if (stuJsonList != null && stuJsonList.size() > 4) {
				r.setCode(ResultCode.ERRORCODE.OVER_LIMIT_NUM);
				r.setMsgInfo(ResultCode.ERRORINFO.OVER_LIMIT_NUM);
				return r;
			}
			
			//判断教练排班是否有冲突
			WechatOrder wechatOrder = new WechatOrder();
			WechatCoachClass wechatCoachClass = new WechatCoachClass();
			wechatCoachClass.setCoachId(coachId);
			if (StringUtils.isNotEmpty(cStart)) {
				Date dStart = new Date(Long.parseLong(cStart) * 1000);
				Date dnow = new Date();
				if (dStart.before(dnow)) {
					r.setCode(ResultCode.ERRORCODE.CLASS_PLAN_TIME_ERROR);
					r.setMsgInfo(ResultCode.ERRORINFO.CLASS_PLAN_TIME_ERROR);
					return r;
				}
				//计算结束时间
				Date dEnd = TimeUtil.addSpecialCurTime(dStart, Calendar.HOUR, Integer.parseInt(timeNum));
				wechatCoachClass.setCstart(dStart);
				wechatCoachClass.setCend(dEnd);
				wechatCoachClass.setIsdel(0);
				List<WechatCoachClass> wechatCoachClassList = wechatSchoolManager.queryIsExitClass(wechatCoachClass);
				if (wechatCoachClassList != null && wechatCoachClassList.size() > 0) {
					for (WechatCoachClass coachClass : wechatCoachClassList) {
						logger.info("*************************************** " + (dStart.getTime() >= coachClass.getCstart().getTime()));
						logger.info("*************************************** " + (dEnd.getTime() <= coachClass.getCend().getTime()));
						if (dStart.getTime() >= coachClass.getCstart().getTime()  && dEnd.getTime() <= coachClass.getCend().getTime() ) {
							r.setCode(ResultCode.ERRORCODE.CLASS_PLAN_TIME_ERROR);
							r.setMsgInfo(ResultCode.ERRORINFO.CLASS_PLAN_TIME_ERROR);
							return r;
						}
						logger.info("*************************************** "  + (dStart.getTime() < coachClass.getCstart().getTime()));
						logger.info("*************************************** " + (dStart.getTime() > coachClass.getCend().getTime()));
						if (dStart.getTime() < coachClass.getCstart().getTime() && dEnd.getTime() > coachClass.getCend().getTime()) {
							r.setCode(ResultCode.ERRORCODE.CLASS_PLAN_TIME_ERROR);
							r.setMsgInfo(ResultCode.ERRORINFO.CLASS_PLAN_TIME_ERROR);
							return r;
						}
						logger.info("*************************************** "  + (dStart.getTime() > coachClass.getCstart().getTime()));
						logger.info("*************************************** " + (dStart.getTime() < coachClass.getCend().getTime()));
						if (dStart.getTime() >= coachClass.getCstart().getTime() && dStart.getTime() < coachClass.getCend().getTime()) {
							r.setCode(ResultCode.ERRORCODE.CLASS_PLAN_TIME_ERROR);
							r.setMsgInfo(ResultCode.ERRORINFO.CLASS_PLAN_TIME_ERROR);
							return r;
						}
					}
				}
				wechatOrder.setCstart(dStart);
				wechatOrder.setPstart(dStart);
				wechatOrder.setCend(dEnd);
				wechatOrder.setPend(dEnd);
			}
			
			String orderId = SecurityUtil.getUUID();
			wechatOrder.setCoachId(coachId);
			wechatOrder.setCourseId(courseId);
			wechatOrder.setCourseName(courseName);
			wechatOrder.setPlaceName(placeName);
			if (StringUtils.isNotEmpty(carId)) {
				wechatCoachClass.setCarId(Integer.parseInt(carId));
				wechatOrder.setCarId(Integer.parseInt(carId));
			}
			if (StringUtils.isNotEmpty(drtype)) {
				wechatCoachClass.setDltype(Integer.parseInt(drtype));
				wechatOrder.setDltype(Integer.parseInt(drtype));
			}
			if (StringUtils.isNotEmpty(placeId)) {
				wechatCoachClass.setPlaceId(Integer.parseInt(placeId));
				wechatOrder.setPlaceId(Integer.parseInt(placeId));
			}
			if (StringUtils.isNotEmpty(maxNum)) {
				wechatCoachClass.setMaxNum(Integer.parseInt(maxNum));
			}
			if (StringUtils.isNotEmpty(timeNum)) {
				wechatCoachClass.setTimeNum(Integer.parseInt(timeNum));
			}
			Integer bookNum = 0;
			if (stuJsonList != null && stuJsonList.size() > 0) {
				bookNum = stuJsonList.size();
			}
			wechatCoachClass.setBookNum(bookNum);
			wechatCoachClass.setCourseId(courseId);
			wechatCoachClass.setPlaceName(placeName);
			wechatCoachClass.setOrderId(orderId);
			wechatCoachClass.setCourseName(courseName);
			r  = wechatSchoolManager.addWechatClass(coachId, wechatCoachClass);
			
			if (stuJsonList != null && stuJsonList.size() > 0) {
				WechatCoachClass recordClass = new WechatCoachClass();
				recordClass.setOrderId(orderId);
				WechatCoachClass coachClass = wechatSchoolManager.queryWechatCoachClass(recordClass);
				String uorderId = null;
				StringBuilder sb = new StringBuilder();
				Coach coach = coachManager.getCoachInfo(coachId);
				List<String> mobileList = new ArrayList<String>();
				int i = 0;
				for (WechatPlantClass plantClass : stuJsonList) {
					uorderId = SecurityUtil.getUUID();
					i ++;
					//把学员插入排班表
					plantClass.setCoachId(coachId);
					plantClass.setCcid(coachClass.getCcid());
					plantClass.setOrderId(uorderId);
					int ar = wechatSchoolManager.insertWechatPlantClass(plantClass);
					logger.info("********************************** insertWechatPlantClass ar: " +ar);
					
					//插入订单表
					wechatOrder.setOrderId(uorderId);
					wechatOrder.setCcid(coachClass.getCcid());
					wechatOrder.setStudentId(plantClass.getStudentId());
					wechatOrder.setStuImg(plantClass.getStuImg());
					wechatOrder.setStuMobile(plantClass.getStuMobile());
					wechatOrder.setStuName(plantClass.getStuName());
					wechatOrder.setClzNum(Integer.parseInt(timeNum));
					int br = wechatSchoolManager.inserWechatOrder(wechatOrder);
					logger.info("********************************** inserWechatOrder br：" + br);
					
					//判断学员与教练是否存在微信绑定关系
					WechatMycoaches isExitCoach = new WechatMycoaches();
					isExitCoach.setCoachId(coachId);
					isExitCoach.setStudentId(plantClass.getStudentId());
					WechatMycoaches wechatMycoaches = wechatSchoolManager.queryMyWechatCoach(isExitCoach);
					if (wechatMycoaches != null && wechatMycoaches.getWxstatus() == 2) {
						logger.info("******************************** Student is bound Coach by wechat! Send Template Message!");
						//获取学员信息
						WechatStudent wechatStudent = new WechatStudent();
						wechatStudent.setStudentId(plantClass.getStudentId());
						WechatStudent studentInfo = wechatSchoolManager.queryWechatStudent(wechatStudent);
						
						//发送微信通知 -邀课通知
						if (studentInfo != null && StringUtils.isNotEmpty(studentInfo.getOpenId())) {
							String firstStr  =  "亲爱的" + studentInfo.getName() + "，"  + coach.getName() + "教练邀请您上" + courseName + "课！";
							sendTemplateMsg(studentInfo, coach, firstStr, wechatOrder);
						}
					}
					else {
						sb.append(plantClass.getStuName());
						if (i < stuJsonList.size()) {
							sb.append("、");
						}
						mobileList.add(plantClass.getStuMobile());
					}
				}
				if (sb.length() > 0) {
					if (sb.substring(sb.length()-1, sb.length()).equals("、")) {
						sb.deleteCharAt(sb.length()-1);
					}
					r.setCode(ResultCode.ERRORCODE.ORDER_NOT_BOUND);
					r.setMsgInfo(sb + ResultCode.ERRORINFO.ORDER_NOT_BOUND);
					r.setData(mobileList);
				}
			}
			
		} catch (Exception e) {
			logger.error("**************************************Error addWechatClass Exception: " + e.getMessage());
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}
	
	@Override
	public ReqResult updateWechatClass(Long coachId, String orderId, Integer ccid,  String carId, String drtype, String courseId, String courseName,
			String placeId, String placeName, String maxNum, String cStart, String timeNum) {
	ReqResult r = ReqResult.getSuccess();
		
		try {
			//判断教练排班是否有冲突
			WechatCoachClass wechatCoachClass = new WechatCoachClass();
			wechatCoachClass.setCoachId(coachId);
			wechatCoachClass.setCcid(ccid);
			if (StringUtils.isNotEmpty(cStart)) {
				Date dStart = new Date(Long.parseLong(cStart) * 1000);
				Date dnow = new Date();
				if (dStart.before(dnow)) {
					r.setCode(ResultCode.ERRORCODE.CLASS_PLAN_TIME_ERROR);
					r.setMsgInfo(ResultCode.ERRORINFO.CLASS_PLAN_TIME_ERROR);
					return r;
				}
				//计算结束时间
				Date dEnd = TimeUtil.addSpecialCurTime(dStart, Calendar.HOUR, Integer.parseInt(timeNum));
				wechatCoachClass.setCstart(dStart);
				wechatCoachClass.setCend(dEnd);
				wechatCoachClass.setOrderId(orderId);
				List<WechatCoachClass> wechatCoachClassList = wechatSchoolManager.queryIsExitClass(wechatCoachClass);
				if (wechatCoachClassList != null && wechatCoachClassList.size() > 0) {
					r.setCode(ResultCode.ERRORCODE.CLASS_PLAN_TIME_ERROR);
					r.setMsgInfo(ResultCode.ERRORINFO.CLASS_PLAN_TIME_ERROR);
					return r;
				}
			}
			
			WechatOrder wechatOrder = new WechatOrder();
			wechatOrder.setCcid(ccid);
			wechatOrder.setOrderId(orderId);
			wechatOrder.setCoachId(coachId);
			wechatOrder.setCourseId(courseId);
			wechatOrder.setCourseName(courseName);
			wechatOrder.setPlaceName(placeName);
			if (StringUtils.isNotEmpty(carId)) {
				wechatCoachClass.setCarId(Integer.parseInt(carId));
				wechatOrder.setCarId(Integer.parseInt(carId));
			}
			if (StringUtils.isNotEmpty(drtype)) {
				wechatCoachClass.setDltype(Integer.parseInt(drtype));
				wechatOrder.setDltype(Integer.parseInt(drtype));
			}
			if (StringUtils.isNotEmpty(placeId)) {
				wechatCoachClass.setPlaceId(Integer.parseInt(placeId));
				wechatOrder.setPlaceId(Integer.parseInt(placeId));
			}
			if (StringUtils.isNotEmpty(maxNum)) {
				wechatCoachClass.setMaxNum(Integer.parseInt(maxNum));
			}
			if (StringUtils.isNotEmpty(timeNum)) {
				wechatCoachClass.setTimeNum(Integer.parseInt(timeNum));
			}
			wechatCoachClass.setCourseId(courseId);
			wechatCoachClass.setPlaceName(placeName);
			wechatCoachClass.setOrderId(orderId);
			wechatCoachClass.setCourseName(courseName);
			//更新排班表、更新学员订单表
			r  = wechatSchoolManager.updateWechatClass(wechatOrder, wechatCoachClass);
			
			StringBuilder sb = new StringBuilder();
			WechatPlantClass record = new WechatPlantClass();
			record.setCcid(ccid);
			List<WechatPlantClass> wechatPlantClassList = wechatSchoolManager.queryWechatPlantClassList(record);
			List<String> mobileList = new ArrayList<String>();
			//判断学员与教练是否存在微信绑定关系
			int i = 0;
			for (WechatPlantClass wechatPlantClass : wechatPlantClassList) {
				i ++;
				WechatMycoaches isExitCoach = new WechatMycoaches();
				isExitCoach.setCoachId(coachId);
				isExitCoach.setStudentId(wechatPlantClass.getStudentId());
				WechatMycoaches wechatMycoaches = wechatSchoolManager.queryMyWechatCoach(isExitCoach);
				if (wechatMycoaches != null && wechatMycoaches.getIsdel() == 2) {
					logger.info("******************************** Student is bound Coach by wechat! do nothing!");
				}
				else {
					sb.append(wechatPlantClass.getStuName());
					if (i < wechatPlantClassList.size()) {
						sb.append("、");
					}
					mobileList.add(wechatPlantClass.getStuMobile());
				}
			}
			
			if (sb.length() > 0) {
				if (sb.substring(sb.length()-1, sb.length()).equals("、")) {
					sb.deleteCharAt(sb.length()-1);
				}
				r.setCode(ResultCode.ERRORCODE.ORDER_NOT_BOUND);
				r.setMsgInfo(sb + ResultCode.ERRORINFO.ORDER_NOT_BOUND);
				r.setData(mobileList);
				return r;
			}
			
		} catch (Exception e) {
			logger.error("**************************************Error addWechatClass Exception: " + e.getMessage());
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}



	@Override
	public ReqResult getWechatStudent(Long coachId, String studentName, String state, String sortType, String type, String channel, String isNew,
			String pageSize, String pageNo) {
		ReqResult r = ReqResult.getSuccess();
		 try {
			 WechatMycoachesDto myCoachDto = new WechatMycoachesDto();
			 boolean isNewStudent = false;
			 if (StringUtils.isNotEmpty(state)) {
				 myCoachDto.setState(Integer.parseInt(state));
			 }
			 if (StringUtils.isNotEmpty(sortType)) {
				 myCoachDto.setSortType(Integer.parseInt(sortType));
			 }
			 if (StringUtils.isNotEmpty(type)) {
				 myCoachDto.setType(Integer.parseInt(type));
			 }
			 if (StringUtils.isNotEmpty(channel)) {
				 myCoachDto.setChannel(Integer.parseInt(channel));
				 if ("2".equals(channel)) { //新学员
					 isNewStudent = true;
				 }
			 }
			 if (StringUtils.isNotEmpty(isNew)) {
				 myCoachDto.setIsNew(Integer.parseInt(isNew));
			 }
			 myCoachDto.setName(studentName);
			 myCoachDto.setCoachId(coachId);
			 myCoachDto.setIsdel(0);
			 if (StringUtils.isNotEmpty(pageNo) && StringUtils.isNotEmpty(pageSize)) {
				 MyRowBounds myRowBounds = new MyRowBounds(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
				 myCoachDto.setMyRowBounds(myRowBounds);
			 }
			 
			 List<WechatMycoaches> wechatStudentList  = wechatSchoolManager.queryWechatMycoachesList(myCoachDto);
			 List<WechatMycoaches> wechatStudentListNew = new ArrayList<WechatMycoaches>();
			 if (wechatStudentList != null && wechatStudentList.size() >0) {
				 for (WechatMycoaches wechatMycoaches : wechatStudentList) {
					 if (StringUtils.isNotEmpty(wechatMycoaches.getPhone()) || StringUtils.isNotEmpty(wechatMycoaches.getName())) { //过滤空手机号空姓名
						 wechatStudentListNew.add(wechatMycoaches);
					 }
				 }
				 r.setData(wechatStudentListNew);
			 }
			 //查看新学员，更新学员标识
			 if (isNewStudent){
				 WechatMycoaches wechatMycoaches = new WechatMycoaches();
				 wechatMycoaches.setCoachId(coachId);
				 wechatMycoaches.setIsNew(1);
				wechatSchoolManager.updateWechatMyCoach(wechatMycoaches );
			 }
			 
		} catch (Exception e) {
			logger.error("**************************************Error getWechatStudent Exception: " + e.getMessage());
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ReqResult getWechatEnroll(Long coachId, String classId, String pageSize, String pageNo) {
		ReqResult r = ReqResult.getSuccess();
		try {
			WechatEnrollStudentDto wechatEnrollStudentDto = new WechatEnrollStudentDto();
			 if (StringUtils.isNotEmpty(pageNo) && StringUtils.isNotEmpty(pageSize)) {
				 MyRowBounds myRowBounds = new MyRowBounds(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
				 wechatEnrollStudentDto.setMyRowBounds(myRowBounds);
			 }
			 wechatEnrollStudentDto.setCoachId(coachId);
			 wechatEnrollStudentDto.setIsdel(0);
			 if (StringUtils.isNotEmpty(classId)) {
				 wechatEnrollStudentDto.setClassId(Integer.parseInt(classId));
			 }
			 List<WechatEnrollStudent> weEnStuList = wechatSchoolManager.queryWechatEnroll(wechatEnrollStudentDto);
			 if (weEnStuList != null && weEnStuList.size() > 0) {
				 r.setData(weEnStuList);
				 boolean isExitNewStu = false; //是否存在未查看的新报名学员
				 for (WechatEnrollStudent wechatEnrollStudent : weEnStuList) {
					 if (wechatEnrollStudent.getIsNew() == 0) {
						 isExitNewStu = true;
						 break;
					 }
				 }
				 if (isExitNewStu) {
					 WechatEnrollStudent updateEnrollStudent = new WechatEnrollStudent();
					 updateEnrollStudent.setCoachId(coachId);
					 updateEnrollStudent.setIsNew(1);
					 wechatSchoolManager.updateWechatEnrollStudent(updateEnrollStudent); //更新为非新学员
				 }
			 }
		} catch (Exception e) {
			logger.error("**************************************Error getWechatEnroll Exception: " + e.getMessage());
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ReqResult getEnrollStudentInfo(Long coachId, Long studentId) {
		ReqResult r = ReqResult.getSuccess();
		try {
			WechatMycoaches wechatMycoaches = new WechatMycoaches();
			wechatMycoaches.setCoachId(coachId);
			wechatMycoaches.setStudentId(studentId);
			WechatMycoaches wechatStudentInfo = wechatSchoolManager.queryMyWechatCoach(wechatMycoaches);
			//上课次数
			WechatPlantClass record = new WechatPlantClass();
			record.setCoachId(coachId);
			record.setStudentId(studentId);
			List<WechatPlantClass> wechatPlantClassList = wechatSchoolManager.queryWechatPlantClassList(record );
			int max_num = 0;
			if (wechatPlantClassList != null && wechatPlantClassList.size() > 0) {
				//上课明细
				WechatCoachClassDto coachClassDto = new WechatCoachClassDto();
				int j=0;
				String ccidin="";
				for (; j< wechatPlantClassList.size() -1; j++) {
					ccidin += wechatPlantClassList.get(j).getCcid()+",";
				}
				ccidin+= wechatPlantClassList.get(j).getCcid() + "";
				coachClassDto.setCcidIn(ccidin);
				List<WechatCoachClass> coachClassList =  wechatSchoolManager.queryCoachClassByCcidIn(coachClassDto);
				if (coachClassList != null && coachClassList.size() > 0) {
					for (WechatCoachClass wechatCoachClass : coachClassList) {
						if (wechatCoachClass.getCstart().before(new Date())) { //比当前时间大
							max_num ++;
						}
					}
					wechatStudentInfo.setOnClassNum(max_num);//上课次数
				}
			}
			if (wechatStudentInfo != null) {
				r.setData(wechatStudentInfo);
			}
		} catch (Exception e) {
			logger.error("**************************************Error getEnrollStudentInfo Exception: " + e.getMessage());
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ReqResult delStudentBound(Long coachId, Long studentId) {
		ReqResult r = ReqResult.getSuccess();
		try {
			WechatMycoaches wechatMycoaches = new WechatMycoaches();
			wechatMycoaches.setCoachId(coachId);
			wechatMycoaches.setStudentId(studentId);
			wechatMycoaches.setIsdel(1);
			wechatSchoolManager.updateWechatMycoaches(wechatMycoaches);
		} catch (Exception e) {
			logger.error("**************************************Error delStudentBound Exception: " + e.getMessage());
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ReqResult getWechatClass(Long coachId, String pageSize, String pageNo) {
		ReqResult r = ReqResult.getSuccess();
		try {
			int days = 7;
			Date now = new Date();
			List<WechatCoachMyClass> classListNew = new ArrayList<WechatCoachMyClass>();
			for (int i = 0; i < days; i++) {
				List<WechatCoachMyClass> classList = new ArrayList<WechatCoachMyClass>();
				Date date = DateUtil.dateAfterMinute(now, 1440*i);
				List<WechatCoachClass> list = queryByCoachClassDate(date, coachId,OrderConstant.ISDEL.NOTDELETE ,OrderConstant.OTYPE.BOOKORDER,null, null);
				if (list!=null && !list.isEmpty()) {
					//赋值：排班数组
					WechatCoachMyClass wechatCoachMyClass = new WechatCoachMyClass();
					for (WechatCoachClass wechatCoachClass : list) {
						//classList = BeanCopy.copyList(list, WechatCoachMyClass.class, BeanCopy.COPY2NULL);
						wechatCoachMyClass = BeanCopy.copyNotNull(wechatCoachClass, WechatCoachMyClass.class);
						wechatCoachMyClass.setDate(date);
						WechatPlantClassDto record = new WechatPlantClassDto();
						record.setCcidin(wechatCoachClass.getCcid().toString());
						record.setCoachId(coachId);
					    List<WechatPlantClass> pcList  = wechatSchoolManager.queryWechatPlantClassDtoList(record);
					    wechatCoachMyClass.setPlantClassList(pcList);
					    classList.add(wechatCoachMyClass);
					}
				} 
				classListNew.addAll(classList);
			}
			r.setData(classListNew);
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		
		return r;
	}
		
	public List<WechatCoachClass> queryByCoachClassDate(Date date,Long coachId,Integer isDel,Integer ctype,Integer pageIndex,Integer pageSize) throws Exception {
		String justdate=TimeUtil.getDateFormat(date, "yyyy-MM-dd");
		//String key=this.getClass().getSimpleName()+"_"+justdate+"_"+coachId+"_"+isDel+"_"+ctype;
		//Date now=new Date();
		//String nowdate=TimeUtil.getDateFormat(now, "yyyy-MM-dd");
		
		List<WechatCoachClass> list = null;
		if (pageIndex==null || pageSize==null) {
			///list=redisUtil.get(key);
		}
		if (list==null) {
			WechatCoachClass wechatCoachClass = new WechatCoachClass();
			wechatCoachClass.setCoachId(coachId);
			wechatCoachClass.setCstart(TimeUtil.parseDate(justdate+" 00:00:00"));
			/*if(nowdate.equals(justdate)){//今天
				wechatCoachClass.setCstart(now);
			}*/
			wechatCoachClass.setCend(TimeUtil.parseDate(justdate+" 23:59:59"));
			wechatCoachClass.setIsdel(0);
			list = wechatSchoolManager.queryWechatClass(wechatCoachClass);
			if (list!=null && list.size() > 0) {
				//redisUtil.set(key, list, 60*5);
			}
			else {
				list = new ArrayList<WechatCoachClass>();
			}
		}
		return list;
	}
		
	
	@Override
	public ReqResult getHomePageInfo(Long coachId) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			Coach coach  = coachManager.getCoachInfo(coachId);
			if (coach != null) {
				r.setData("wstate", coach.getWstate());//出车状态
				r.setData("acceptOrderDis", coach.getAcceptOrderDis()); //接单距离
				r.setData("courses", coach.getCourses()); //可教课程
			}
			
			WaitOrderVo waitOrderVo = orderService.getCoachWait(coachId, null);
            if (waitOrderVo != null) {
            	if (waitOrderVo.getWaitComment() != null && !"".equals(waitOrderVo.getWaitComment())) {
            		r.setData("waitComment", 1);
            	}
            	else {
            		r.setData("waitComment", 0);
            	}
            }
			
			Integer totalAccount = 0; //今日收入
			int sum = coachStatisticService.getCoachBonusByDate(null, Long.valueOf(coachId));
			CoachStatisticVo vo=coachStatisticService.getCoachStatistc(Long.valueOf(coachId), null);
			totalAccount  = sum + vo.getOrderMoney();
			r.setData("totalAccount", totalAccount);
			
			//查询班型信息
			WechatEnrollClass classRecord = new WechatEnrollClass();
			classRecord.setCoachId(coachId);
			classRecord.setIsDel(0);
			List<WechatEnrollClass> enrollClassList =  wechatSchoolManager.queryWechatEnrollClassList(classRecord);
			if (enrollClassList != null && enrollClassList.size() > 0) {
				@SuppressWarnings("rawtypes")
				List<Map> classList = new ArrayList<Map>();
				Map<String, Object> classMap = null;
				for (WechatEnrollClass wechatEnrollClass : enrollClassList) {
					classMap = new HashMap<String, Object>();
					classMap.put("classId", wechatEnrollClass.getClassId());
					classMap.put("className", wechatEnrollClass.getClassName());
					//查询每个班型的报名人数及新报名人数
					WechatEnrollStudentDto wechatEnrollStudentDto = new WechatEnrollStudentDto();
					wechatEnrollStudentDto.setClassId(wechatEnrollClass.getClassId());
					//wechatEnrollStudentDto.setPayState(100);
					 List<WechatEnrollStudent> weEnStuList = wechatSchoolManager.queryWechatEnroll(wechatEnrollStudentDto);
					 if (weEnStuList != null && weEnStuList.size() > 0) {
						 int newEnroll = 0;
						 int enrollSum = 0;
						 for (WechatEnrollStudent wechatEnrollStudent : weEnStuList) {
							 if (wechatEnrollStudent.getIsNew() == 0) {
								 newEnroll ++; //新增报名意向
							 }
							 if (wechatEnrollStudent.getPayState() == 100) { //已支付
								 enrollSum ++; //报名人数
							 }
						 }
						 classMap.put("enrollSum", enrollSum);
						 classMap.put("newEnroll", newEnroll);
					 }
					 else {
						 classMap.put("enrollSum", 0);
						 classMap.put("newEnroll", 0);
					 }
					 classList.add(classMap);
				}
				r.setData("classList", classList);
			}
			
			//查询今天的上课信息 -- 微信排班
			WechatCoachClass recordClass = new WechatCoachClass();
			recordClass.setCoachId(coachId);
			recordClass.setIsdel(0);
			WechatCoachClass wechatCoachClass = wechatSchoolManager.queryNearWechatCoachClass(recordClass);
			
			//查询今天的上课信息 -- 喱喱排班
			StringBuffer sbTy = new StringBuffer();
			CoachClassQuery ccq = new CoachClassQuery();
			CoachClassVo search = new CoachClassVo();
			search.setCtype(OrderConstant.OTYPE.BOOKORDER);
			search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
			sbTy.append(" and coach_id=" + coachId +" and rstart > now() ");
			ccq.setGroupBy(sbTy.toString());
			ccq.setorderBy(" order by rstart asc ");
			List<CoachClassVo> coachClassListTy  = coachClassService.queryByObjectAnd(search, ccq);
			//微信排班 与 喱喱排班比较时间
			if (coachClassListTy != null && coachClassListTy.size() > 0  && wechatCoachClass != null) {
				if (coachClassListTy.get(0).getRstart().before(wechatCoachClass.getCstart()) || coachClassListTy.get(0).getRstart().equals(wechatCoachClass.getCstart())) {
					CoachClassVo coachClassVo = new CoachClassVo();
					coachClassVo = coachClassListTy.get(0);
					//查询学员信息
					List<PlantClassVo> plantClassList = plantClassService.queryByCcid(coachClassVo.getCcid(), new PlantClassQuery());
					if (plantClassList != null && plantClassList.size() > 0) {
						coachClassVo.setPlantClassList(plantClassList);
					}
					r.setData("coachClass", coachClassVo);
				}
				else {
					WechatCoachMyClass wechatCoachMyClass= new WechatCoachMyClass();
					wechatCoachMyClass = BeanCopy.copyAll(wechatCoachClass, WechatCoachMyClass.class);
					WechatPlantClass record = new WechatPlantClass();
					record.setCcid(wechatCoachMyClass.getCcid());
					//查询学员信息
					List<WechatPlantClass> wechatPlantClassList= wechatSchoolManager.queryWechatPlantClassList(record );
					if (wechatPlantClassList != null && wechatPlantClassList.size() > 0) {
						wechatCoachMyClass.setPlantClassList(wechatPlantClassList);
					}
					r.setData("wechatClass", wechatCoachMyClass);
				}
			}
			else if (wechatCoachClass != null && (coachClassListTy == null || coachClassListTy.size() == 0)) {
				WechatCoachMyClass wechatCoachMyClass= new WechatCoachMyClass();
				wechatCoachMyClass = BeanCopy.copyAll(wechatCoachClass, WechatCoachMyClass.class);
				WechatPlantClass record = new WechatPlantClass();
				record.setCcid(wechatCoachMyClass.getCcid());
				//查询学员信息
				List<WechatPlantClass> wechatPlantClassList= wechatSchoolManager.queryWechatPlantClassList(record );
				if (wechatPlantClassList != null && wechatPlantClassList.size() > 0) {
					wechatCoachMyClass.setPlantClassList(wechatPlantClassList);
				}
				r.setData("wechatClass", wechatCoachMyClass);
			}
			else if (coachClassListTy != null && coachClassListTy.size() > 0 && wechatCoachClass == null) {
				CoachClassVo coachClassVo = new CoachClassVo();
				coachClassVo = coachClassListTy.get(0);
				//查询学员信息
				List<PlantClassVo> plantClassList = plantClassService.queryByCcid(coachClassVo.getCcid(), new PlantClassQuery());
				if (plantClassList != null && plantClassList.size() > 0) {
					coachClassVo.setPlantClassList(plantClassList);
				}
				r.setData("coachClass", coachClassVo);
			}
			else {
				r.setData("wechatClass", null);
				r.setData("coachClass", null);
			}
			
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		
		return r;
	}
	
	

	@Override
	public ReqResult getWeChatMyCoach(String openId, String studentId) {
		ReqResult r = ReqResult.getSuccess();
		try {
			//获取学员信息
			WechatStudent wechatStudent = new WechatStudent();
			wechatStudent.setOpenId(openId);
			WechatStudent wechatStudentInfo = wechatSchoolManager.queryWechatStudent(wechatStudent);
			
			WechatMycoachesDto wechatMycoachesDto = new WechatMycoachesDto();
			StudentWechatCoach studentWechatCoach = new StudentWechatCoach();
			if (wechatStudentInfo != null) { 
				wechatMycoachesDto.setStudentId(wechatStudentInfo.getStudentId());
				wechatMycoachesDto.setIsdel(0);	//未删除
				wechatMycoachesDto.setWxstatusStr("1,2");
				//微信绑定教练只有一个
				WechatMycoaches myCoach = wechatSchoolManager.queryMyWechatBoundCoach(wechatMycoachesDto);
				if (myCoach != null) {
					//将教练信息复制到实体
					Coach coach = coachManager.getCoachInfo(myCoach.getCoachId());
					if(coach != null) { 
						studentWechatCoach  = BeanCopy.copyAll(coach, StudentWechatCoach.class);
					}
					//学员与教练的绑定关系
					studentWechatCoach.setWxstatus(myCoach.getWxstatus());
					
					//查询学员报名信息
					WechatEnrollStudent record = new WechatEnrollStudent();
					record.setStudentId(wechatStudentInfo.getStudentId());
					record.setIsdel(0);
					WechatEnrollStudent stuEnrollClass = wechatSchoolManager.queryEnrollStudentInfo(record);
					if (stuEnrollClass != null) {
						studentWechatCoach.setPayState(stuEnrollClass.getPayState());
					}
					else {
						studentWechatCoach.setPayState(0); //未报名
					}
					
					//查询教练招生班型
					WechatEnrollClass wechatEnrollClass = new WechatEnrollClass();
					wechatEnrollClass.setCoachId(myCoach.getCoachId());
					wechatEnrollClass.setIsDel(0);
					List<WechatEnrollClass> enrollList =  wechatSchoolManager.queryWechatEnrollClassList(wechatEnrollClass);
					if (enrollList != null && enrollList.size() > 0) {
						studentWechatCoach.setWechatEnrollClassList(enrollList);
					}
					
					//查询教练邀请我的课程信息
					WechatOrder order = new WechatOrder();
					order.setCoachId(myCoach.getCoachId());
					order.setStudentId(wechatStudentInfo.getStudentId());
					List<WechatOrder> orderList = wechatSchoolManager.queryWechatOrderList(order);
					List<WechatOrder> orderListNew = new ArrayList<WechatOrder>();
					if (orderList != null && orderList.size() > 0) { 
						for (WechatOrder wechatOrder : orderList) {
							if (wechatOrder.getCstart()!= null &&wechatOrder.getCend()!= null) {
								 long diff = wechatOrder.getCend().getTime() - wechatOrder.getCstart().getTime();
						         Long diffHours = diff / (60 * 60 * 1000) % 24;
								 wechatOrder.setClzNum(diffHours.intValue());
							}
							orderListNew.add(wechatOrder);
						}
						studentWechatCoach.setOrderList(orderListNew);
					}
				}
			}
			else { //微信学员表不存在该Openid
				//插入学员信息
				//wechatSchoolManager.insertWechatStudent(wechatStudent);
			}
			r.setData(studentWechatCoach);
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		
		return r;
	}
	
	@Override
	public ReqResult getWeChatOrder(String openId, String studentId) {
		ReqResult r = ReqResult.getSuccess();
		try {
			//获取学员信息
			WechatStudent wechatStudent = new WechatStudent();
			wechatStudent.setOpenId(openId);
			WechatStudent wechatStudentInfo = wechatSchoolManager.queryWechatStudent(wechatStudent);
			StudentWechatEnroll studentWechatEnroll = new StudentWechatEnroll();
			WechatEnrollStudent recordStudent = new WechatEnrollStudent();
			if (wechatStudentInfo != null) {
				recordStudent.setStudentId(wechatStudentInfo.getStudentId());
				recordStudent.setIsdel(0);
				
				WechatEnrollStudent wechatEnrollStudent = wechatSchoolManager.queryEnrollStudentInfo(recordStudent);
				if (wechatEnrollStudent != null) { //存在报名信息
					//如果报名未支付，都算失败，更新订单为已取消
					/*if (wechatEnrollStudent.getPayState() != 100) {
						WechatEnrollStudent updateEnrollStudent = new WechatEnrollStudent();
						updateEnrollStudent.setOrderId(wechatEnrollStudent.getOrderId());
						updateEnrollStudent.setIsdel(1);
						wechatSchoolManager.updateWechatEnrollStudent(updateEnrollStudent);
					}
					else {*/
						Coach coach = coachManager.getCoachInfo(wechatEnrollStudent.getCoachId());
						studentWechatEnroll = BeanCopy.copyAll(coach, StudentWechatEnroll.class);
						
						//获取班型信息
						WechatEnrollClass recordClass = new WechatEnrollClass();
						recordClass.setClassId(wechatEnrollStudent.getClassId());
						WechatEnrollClass wechatEnrollClass = wechatSchoolManager.queryWechatEnrollClass(recordClass);
						if (wechatEnrollClass != null) {
							studentWechatEnroll.setWechatEnrollClass(wechatEnrollClass);
						}
						
						//报名信息
						studentWechatEnroll.setState(wechatEnrollStudent.getPayState());
						studentWechatEnroll.setDrtype(wechatEnrollStudent.getDrtype());
						studentWechatEnroll.setStudentName(wechatEnrollStudent.getStudentName());
						studentWechatEnroll.setStudentPhone(wechatEnrollStudent.getStudentPhone());
						studentWechatEnroll.setPayPrice(wechatEnrollStudent.getPayPrice());
						studentWechatEnroll.setPayState(wechatEnrollStudent.getPayState());
						studentWechatEnroll.setPayTime(wechatEnrollStudent.getPayTime());
						studentWechatEnroll.setPayWay(wechatEnrollStudent.getPayWay());
//					}
				}
			}
			else {
				//插入学员信息
				/*wechatSchoolManager.insertWechatStudent(wechatStudent);
				wechatStudentInfo = wechatSchoolManager.queryWechatStudent(wechatStudent);
				recordStudent.setStudentId(wechatStudentInfo.getStudentId());*/
			}
			
			r.setData(studentWechatEnroll);
			
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		
		return r;
	}

	@Override
	public ReqResult subEnrollPurpose(String openId, String studentId, Integer classId, String className, String classRemark,
			String drtype, Long coachId, String coachName, String studentName, String studentPhone, String codeInput,
			String payState, String cityName, String payPrice, String payWay) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			//验证手机、验证码
			ReqResult ar = authcodeService.isCodeExist(codeInput, studentPhone, null, "2");
			if (ar.isSuccess()) {
				WechatEnrollStudent wechatEnrollStudent = new WechatEnrollStudent();
				Long studentIdLong = null;
				//获取学员信息
				WechatStudent wechatStudent = new WechatStudent();
				wechatStudent.setOpenId(openId);
				wechatStudent.setPhone(studentPhone);
				WechatStudent wechatStudentInfo = wechatSchoolManager.queryWechatStudent(wechatStudent);
				if (wechatStudentInfo != null) {
					wechatEnrollStudent.setStudentId(wechatStudentInfo.getStudentId());
					studentIdLong = wechatStudentInfo.getStudentId();
				}
				else {
					//插入学员信息
					wechatStudent.setName(studentName);
					wechatSchoolManager.insertWechatStudent(wechatStudent);
					wechatStudentInfo = wechatSchoolManager.queryWechatStudent(wechatStudent);
					wechatEnrollStudent.setStudentId(wechatStudentInfo.getStudentId());
					studentIdLong = wechatStudentInfo.getStudentId();
				}
				wechatEnrollStudent.setIsdel(0);
				String orderId = SecurityUtil.getUUID();
				//判断用户是否已报名
				WechatEnrollStudent isEnrollInfo = wechatSchoolManager.queryEnrollStudentInfo(wechatEnrollStudent);
				if (isEnrollInfo != null) {
					r.setCode(ResultCode.ERRORCODE.HAS_ENROLL_INFO);
					r.setMsgInfo("您已提交报名意向，请勿重复提交");
					return r;
					//用户不点我的订单，通过分享再次报名，这里入口做下校验
					/*if (isEnrollInfo.getPayState() == 100 || classId.equals(isEnrollInfo.getClassId()) || isEnrollInfo.getClassId() == classId) {
						r.setCode(ResultCode.ERRORCODE.HAS_ENROLL_INFO);
						r.setMsgInfo(ResultCode.ERRORINFO.HAS_ENROLL_INFO);
						return r;
					}
					else {
						WechatEnrollStudent updateEnrollStudent = new WechatEnrollStudent();
						updateEnrollStudent.setOrderId(isEnrollInfo.getOrderId());
						updateEnrollStudent.setIsdel(1);
						wechatSchoolManager.updateWechatEnrollStudent(updateEnrollStudent);
						r.setData(isEnrollInfo);
					}*/
				}
				else { //没有报名信息
					//判断用户是否已绑定该教练
					WechatMycoaches mycoaches = new WechatMycoaches();
					mycoaches.setStudentId(studentIdLong);
					mycoaches.setCoachId(coachId);
					mycoaches.setName(studentName);
					mycoaches.setPhone(studentPhone);
					mycoaches.setHeadIcon(wechatStudentInfo.getHeadIcon());
					wechatSchoolManager.inportStudentOne(mycoaches);
					
					//查询报名班级信息，补全信息
					WechatEnrollClass recordClass = new WechatEnrollClass();
					recordClass.setClassId(classId);
					WechatEnrollClass wechatEnrollClass = wechatSchoolManager.queryWechatEnrollClass(recordClass);
					if (wechatEnrollClass != null) {
						wechatEnrollStudent.setClassName(wechatEnrollClass.getClassName());
						wechatEnrollStudent.setPrice(wechatEnrollClass.getPrice());
						wechatEnrollStudent.setCityId(wechatEnrollClass.getCityId());
						wechatEnrollStudent.setCityName(wechatEnrollClass.getCityName());
					}
					
					if (StringUtils.isNotEmpty(drtype)) {
						wechatEnrollStudent.setDrtype(Integer.parseInt(drtype));
						if ("1".equals(drtype)) { //C1
							if (wechatEnrollClass.getPrePrice() != null && !"".equals(wechatEnrollClass.getPrePrice())) {
								wechatEnrollStudent.setPayPrice(wechatEnrollClass.getPrePrice());
							}
							else { //没有优惠价取原价
								if (wechatEnrollClass.getPrice() != null && !"".equals(wechatEnrollClass.getPrice())) {
									wechatEnrollStudent.setPayPrice(wechatEnrollClass.getPrice());
								}
							}
						}
						else { //C2
							if (wechatEnrollClass.getDrtype2() == null || "".equals(wechatEnrollClass.getDrtype2())) { //只有一个班型时
								if (wechatEnrollClass.getPrePrice() != null && !"".equals(wechatEnrollClass.getPrePrice())) {
									wechatEnrollStudent.setPayPrice(wechatEnrollClass.getPrePrice());
								}
								else { //没有优惠价取原价
									if (wechatEnrollClass.getPrice() != null && !"".equals(wechatEnrollClass.getPrice())) {
										wechatEnrollStudent.setPayPrice(wechatEnrollClass.getPrice());
									}
								}
							}
							else { //有2个班型时
								if (wechatEnrollClass.getPrePrice2() != null && !"".equals(wechatEnrollClass.getPrePrice2())){
									wechatEnrollStudent.setPayPrice(wechatEnrollClass.getPrePrice2());
								}
								else {
									if (wechatEnrollClass.getPrice2() != null && !"".equals(wechatEnrollClass.getPrice2())) {
										wechatEnrollStudent.setPayPrice(wechatEnrollClass.getPrice2());
									}
								}
							}
						}
					}
					else {
						if (wechatEnrollClass.getPrePrice() != null && !"".equals(wechatEnrollClass.getPrePrice())) {
							wechatEnrollStudent.setPayPrice(wechatEnrollClass.getPrePrice());
						}
						else { //没有优惠价取原价
							if (wechatEnrollClass.getPrice() != null && !"".equals(wechatEnrollClass.getPrice())) {
								wechatEnrollStudent.setPayPrice(wechatEnrollClass.getPrice());
							}
						}
					}
					wechatEnrollStudent.setCtime(new Date());
					wechatEnrollStudent.setClassId(classId);
					wechatEnrollStudent.setClassRemark(classRemark);
					wechatEnrollStudent.setCoachId(coachId);
					wechatEnrollStudent.setCoachName(coachName);
					wechatEnrollStudent.setStudentName(studentName);
					wechatEnrollStudent.setStudentPhone(studentPhone);
					wechatEnrollStudent.setOrderId(orderId);
					if(StringUtils.isNotEmpty(payState)) {
						wechatEnrollStudent.setPayState(Integer.parseInt(payState));
					}
					else {
						wechatEnrollStudent.setPayState(0);
					}
					Coach coach = coachManager.getCoachInfo(coachId);
					wechatEnrollStudent.setCityId(coach.getCityId());
					wechatEnrollStudent.setCityName(coach.getCityName());
					
					wechatSchoolManager.subEnrollPurpose(wechatEnrollStudent);
					r.setData(wechatEnrollStudent);
				}
				
				//推送微信信息
				if (StringUtils.isNotEmpty(openId)) {
					String firstStr ="";
					if ("0".equals(payState)) { //有意向
						firstStr = "亲爱的" + studentName + "，您的报名意向已提交成功！";
						sendTemplateEnrollMsg(wechatStudentInfo, firstStr , studentName, className, drtype, wechatEnrollStudent.getPayPrice(), coachName, orderId, cityName);
						
						
					}
					/*else if ("1".equals(payState)){ //报名
						firstStr = "亲爱的" + studentName + "，您的报名订单已支付成功！";
						logger.info("**************************************** wechatEnrollStudent.getPayPrice() : "+ wechatEnrollStudent.getPayPrice());
						sendTemplateEnrollMsg(wechatStudentInfo, firstStr, studentName, className, drtype, wechatEnrollStudent.getPayPrice(), coachName, orderId, cityName);
					}*/
				}
				
				//极光推送-教练
				JpushMsg jmsg = new JpushMsg();
				Message jpush = new Message();
				String message = "有新的学员报名学车，快去联系他吧!";
				jmsg.setAlter(message);
				jmsg.setUserId(coachId);
				jmsg.setOperate(JpushConstant.OPERATE.COACHWECHATENROLL);
				jmsg.setOrderId(orderId);
				jpush.setTopic(jpushProducer.getCreateTopicKey());
				jpush.setKeys(coachId+JpushConstant.OPERATE.COACHWECHATENROLL);
				jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
				jpush.setBody(SerializableUtil.serialize(jmsg));
				jpush.setKeys(orderId);
				jpushProducer.send(jpush);
				logger.info("*********************************** Send Jpush Message Info, CoachId : " +  coachId +", Message :"+ message);
				
			}
			else {
				r.setCode(ResultCode.ERRORCODE.AUTHCODE_ERROR);
				r.setMsgInfo(ResultCode.ERRORINFO.AUTHCODE_ERROR);
				return r;
			}
			
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		
		return r;
	}

	@Override
	public ReqResult getWeChatClassInfo(String openId, String studentId, Integer classId) {
		
		ReqResult r = ReqResult.getSuccess();
		try {
			//判断微信用户是否存在
			WechatStudent wechatStudent = new WechatStudent();
			wechatStudent.setOpenId(openId);
			WechatStudent wechatStudentInfo = wechatSchoolManager.queryWechatStudent(wechatStudent);
			if (wechatStudentInfo != null) {
				StudentWechatEnroll studentWechatEnroll = new StudentWechatEnroll();
				WechatEnrollStudent recordStudent = new WechatEnrollStudent();
				recordStudent.setStudentId(wechatStudentInfo.getStudentId());
				recordStudent.setClassId(classId);
				recordStudent.setIsdel(0);
				WechatMycoaches myCoaches = new WechatMycoaches();
				myCoaches.setStudentId(recordStudent.getStudentId());
				myCoaches.setIsdel(0);
				myCoaches.setWxstatus(2);
				WechatMycoaches wechatMycoaches = wechatSchoolManager.queryMyWechatCoach(myCoaches);
				if (wechatMycoaches != null) {
					Coach coach = coachManager.getCoachInfo(wechatMycoaches.getCoachId());
					studentWechatEnroll = BeanCopy.copyAll(coach, StudentWechatEnroll.class);
				}
				
				//获取班型信息
				WechatEnrollClass recordClass = new WechatEnrollClass();
				recordClass.setClassId(classId);
				WechatEnrollClass wechatEnrollClass = wechatSchoolManager.queryWechatEnrollClass(recordClass);
				if (wechatEnrollClass != null) {
					studentWechatEnroll.setWechatEnrollClass(wechatEnrollClass);
				}
				
				//报名信息
				WechatEnrollStudent wechatEnrollStudent = wechatSchoolManager.queryEnrollStudentInfo(recordStudent);
				if (wechatEnrollStudent != null) {
					studentWechatEnroll.setDrtype(wechatEnrollStudent.getDrtype());
					studentWechatEnroll.setStudentName(wechatEnrollStudent.getStudentName());
					studentWechatEnroll.setStudentPhone(wechatEnrollStudent.getStudentPhone());
					studentWechatEnroll.setPayPrice(wechatEnrollStudent.getPayPrice());
					studentWechatEnroll.setPayTime(wechatEnrollStudent.getPayTime());
					studentWechatEnroll.setPayWay(wechatEnrollStudent.getPayWay());
					studentWechatEnroll.setPayState(wechatEnrollStudent.getPayState());
					studentWechatEnroll.setOrderId(wechatEnrollStudent.getOrderId());
				}
				else {
					studentWechatEnroll.setPayState(0);
				}
				r.setData(studentWechatEnroll);
			}
			else {
				r.setCode(ResultCode.ERRORCODE.NO_USER);
				r.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
			}
			
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		
		return r;
	}
	
	/**
	 * 拒绝、接受课程
	 */
	@Override
	public ReqResult handClass(String openId,  String ccid, Integer state, String orderId) {
		ReqResult r = ReqResult.getSuccess();
		try {
			//判断微信用户是否存在
			if (StringUtils.isNotEmpty(openId)) {
				WechatStudent wechatStudent = new WechatStudent();
				wechatStudent.setOpenId(openId);
				WechatStudent studentInfo = wechatSchoolManager.queryWechatStudent(wechatStudent);
				if (studentInfo != null) {
					//判断订单状态
					WechatOrder record = new WechatOrder();
					record.setOrderId(orderId);
					WechatOrder wechatOrder = wechatSchoolManager.queryWechatOrder(record);
					if (wechatOrder == null) { //只有初始化及接受状态才可以操作
						r.setCode(ResultCode.ERRORCODE.STUDENT_STATE__NOT_SUPPORT);
						r.setMsgInfo(ResultCode.ERRORINFO.STUDENT_STATE__NOT_SUPPORT);
						return r;
					}
					
					//更新排班数据
					WechatPlantClass wechatPlantClass = new WechatPlantClass();
					wechatPlantClass.setState(state); //0代表正常，1-已接受，2-拒绝；3-取消,4-已完成；5-缺课
					if (StringUtils.isNotEmpty(ccid)) {
						wechatPlantClass.setCcid(Integer.parseInt(ccid));
					}
					wechatPlantClass.setOrderId(orderId);
					wechatPlantClass.setStudentId(studentInfo.getStudentId());
					if (state ==2 || state ==3) { //这里把关联关系删除，学员查看教练排班还是可以查到关联关系，只看教练是否关闭排班
						wechatPlantClass.setIsdel(1);
						WechatPlantClass pclassrecord= new WechatPlantClass();
						pclassrecord.setOrderId(orderId);
						WechatPlantClass pclassInfo = wechatSchoolManager.queryWechatPlantClass(pclassrecord);
						if (pclassInfo != null) {
							WechatCoachClass recordClass = new WechatCoachClass();
							recordClass.setCcid(pclassInfo.getCcid());
							WechatCoachClass wechatCoachClass = wechatSchoolManager.queryWechatCoachClass(recordClass);
							if (wechatCoachClass != null) {
								int bookNum = wechatCoachClass.getBookNum();
								if(bookNum > 0 ) {
									wechatCoachClass.setBookNum(bookNum -1);
									wechatSchoolManager.updateWechatCoachClass(wechatCoachClass);
								}
							}
						}
					}
					wechatSchoolManager.handClass(wechatPlantClass);
					
					//更新订单数据
					record.setOrderState(state);
					wechatSchoolManager.updateWechatOrder(record);
					
					Coach coach = coachManager.getCoachInfo(wechatOrder.getCoachId());
					String firstStr = "";
					JpushMsg jmsg = new JpushMsg();
					Message jpush = new Message();
					Notice notice = new Notice();
					String message = "";
					SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if (state ==2) {
						firstStr =  "亲爱的" + studentInfo.getName() + "，您已经残忍的拒绝了" +coach.getName() + "教练" + wechatOrder.getCourseName() + "课！";
						message = studentInfo.getName() + "已拒绝您安排的练车课程；该课程上课时间为" + formatter.format(wechatOrder.getCstart())+"。请尽快安排其他学员来上课吧";
						jpush.setKeys(coach.getCoachId()+JpushConstant.OPERATE.COACHWECHATREJECT);
						jmsg.setOperate(JpushConstant.OPERATE.COACHWECHATREJECT);
						notice.setMsgType(Integer.parseInt(JpushConstant.OPERATE.COACHWECHATREJECT));
						notice.setTitle("学员已拒绝上课邀请");
					}
					else if (state == 1) {
						firstStr =  "亲爱的" + studentInfo.getName() + "，您已经愉快的接受" +coach.getName() + "教练" + wechatOrder.getCourseName() + "课！";
						message = studentInfo.getName() + "已接受您安排的练车课程。上课时间为" + formatter.format(wechatOrder.getCstart());
						jpush.setKeys(coach.getCoachId()+JpushConstant.OPERATE.COACHWECHATACCESS);
						jmsg.setOperate(JpushConstant.OPERATE.COACHWECHATACCESS);
						notice.setMsgType(Integer.parseInt(JpushConstant.OPERATE.COACHWECHATACCESS));
						notice.setTitle("学员已接受上课邀请");
					}
					else if (state == 3) {
						firstStr =  "亲爱的" + studentInfo.getName() + "，您已取消了" + coach.getName() + "教练" + wechatOrder.getCourseName() + "课！";
						message =  studentInfo.getName() + "取消了上课计划；该课程上课时间为" + formatter.format(wechatOrder.getCstart()) + "。请尽快安排其他学员来上课吧。";
						jpush.setKeys(coach.getCoachId()+JpushConstant.OPERATE.COACHWECHATCANCEL);
						jmsg.setOperate(JpushConstant.OPERATE.COACHWECHATCANCEL);
						notice.setMsgType(Integer.parseInt(JpushConstant.OPERATE.COACHWECHATCANCEL));
						notice.setTitle("学员取消了排班课程");
					}
					//微信通知消息-学员
					sendTemplateMsg(wechatStudent, coach, firstStr, wechatOrder);
					
					//极光推送-教练
					jmsg.setAlter(message);
					jmsg.setUserId(coach.getCoachId());
					jmsg.setOrderId(wechatOrder.getCcid().toString());
					jpush.setTopic(jpushProducer.getCreateTopicKey());
					jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
					jpush.setBody(SerializableUtil.serialize(jmsg));
					jpush.setKeys(wechatOrder.getCcid().toString());
					jpushProducer.send(jpush);
					logger.info("*********************************** Send Jpush Message Info, CoachId : " +  coach.getCoachId() +", Message :"+ message);
					
					//推送成功后，保存到用户消息中心
					notice.setUserId(coach.getCoachId());
					notice.setUserType((byte)ReqConstants.USER_TYPE_COACH);
					notice.setType((byte) 2);  //type=2订单消息
					notice.setDigest(message);  //content改为存html内容
					notice.setTime(new Date());
					notice.setIsdel((byte)0);
					
					notice.setOrderId(wechatOrder.getCcid().toString());
					noticeManager.addNotice(notice);			
				}
				else {
					r.setCode(ResultCode.ERRORCODE.NO_USER);
					r.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
				}
			}
			else {
				r.setCode(ResultCode.ERRORCODE.NO_USER);
				r.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
			}
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}

	/**
	 * 取消关注教练
	 */
	@Override
	public ReqResult abolishCoach(String openId, String studentId, Long coachId, Integer status) {
		ReqResult r = ReqResult.getSuccess();
		try {
			//获取学员信息
			WechatStudent wechatStudent = new WechatStudent();
			wechatStudent.setOpenId(openId);
			WechatStudent wechatStudentInfo = wechatSchoolManager.queryWechatStudent(wechatStudent);
			WechatMycoaches wechatMycoaches = new WechatMycoaches();
			if (wechatStudentInfo != null) {
				wechatMycoaches.setStudentId(wechatStudentInfo.getStudentId());
				wechatMycoaches.setCoachId(coachId);
				wechatMycoaches.setWxstatus(0);//取消关联
				//wechatMycoaches.setIsdel(status);
				wechatSchoolManager.updateWechatMyCoach(wechatMycoaches);
			}
			else {
				r.setCode(ResultCode.ERRORCODE.NO_USER);
				r.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
			}
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ReqResult wechatBoundCoach(String phone, String name, Long coachId, String openId, String codeInput, Integer wxstatus) {
		ReqResult r = ReqResult.getSuccess();
		try {
			//验证手机、验证码
			ReqResult ar = authcodeService.isCodeExist(codeInput, phone, null, "2");
			if(ar.isSuccess()){
				//进到这里说明微信号是第一次进来的，微信号做校验唯一
				//判断微信号是否已存在，因学员可以取消关注教练，重新填写信息，手机号可以随便填写，导致OpenID冲突
				WechatStudent openIdStudent = new WechatStudent();
				openIdStudent.setOpenId(openId);
				WechatStudent exitOpenIdStudent = wechatSchoolManager.queryWechatStudent(openIdStudent);
				
				//判读学员手机号是否存在学员信息表
				WechatStudent phoneStudent = new WechatStudent();
				phoneStudent.setPhone(phone);
				WechatStudent exitStudent = wechatSchoolManager.queryWechatStudent(phoneStudent);
				
				if (exitOpenIdStudent != null && exitStudent !=null &&  !exitOpenIdStudent.getStudentId().equals(exitStudent.getStudentId())) {
					r.setCode(ResultCode.ERRORCODE.IS_EXIT_OPENID);
					r.setMsgInfo(ResultCode.ERRORINFO.IS_EXIT_OPENID);
					return r;
				}
				
				WechatStudent wechatStudent = new WechatStudent();
				if (exitStudent != null) { //更新学员信息
					//判断是否已有微信绑定教练
					WechatMycoaches isExitCoach = new WechatMycoaches();
					isExitCoach.setStudentId(exitStudent.getStudentId());
					isExitCoach.setWxstatus(2);
					WechatMycoaches wechatMycoaches = wechatSchoolManager.queryMyWechatCoach(isExitCoach);
					if (wechatMycoaches != null) {
						Coach coach = coachManager.getCoachInfo(wechatMycoaches.getCoachId());
						r.setCode(ResultCode.ERRORCODE.EXIST_COACH_RELATIONSHIP);
						r.setMsgInfo(ResultCode.ERRORINFO.EXIST_COACH_RELATIONSHIP.replace("{1}", coach.getName()));
						return r;
					}
					
					//更新学员信息
					wechatStudent = BeanCopy.copyAll(exitStudent, WechatStudent.class);
					wechatStudent.setOpenId(openId);
					wechatStudent.setPhone(phone);
					wechatStudent.setName(name);
					wechatSchoolManager.updateWechatStudent(wechatStudent);
				}
				else if (exitStudent == null ) { //未存信息
					WechatStudent wechatStudentInfo = new WechatStudent();
					wechatStudentInfo.setOpenId(openId);
					wechatStudentInfo.setPhone(phone);
					wechatStudentInfo.setName(name);
					wechatSchoolManager.insertWechatStudent(wechatStudentInfo);
					wechatStudent = wechatSchoolManager.queryWechatStudent(wechatStudentInfo);
				}
				
				//判断是否已跟教练绑定关系
				WechatMycoaches myCoach = new WechatMycoaches();
				myCoach.setStudentId(wechatStudent.getStudentId());
				myCoach.setCoachId(coachId);
				WechatMycoaches wechatMycoaches = wechatSchoolManager.queryMyWechatCoach(myCoach);
				
				myCoach.setWxstatus(wxstatus);
				myCoach.setChannel(2);
				myCoach.setIsdel(0);
				myCoach.setName(name);
				myCoach.setPhone(phone);
				if (wechatMycoaches != null) { //已有绑定关系，但是关系不确定
					myCoach.setId(wechatMycoaches.getId());
					if ( wechatMycoaches.getIsdel() == 1 || wechatMycoaches.getWxstatus() != 2) { //如果已经取消关联或已经删除，更新绑定关系
						wechatSchoolManager.updateWechatMyCoach(myCoach);
						return r;
					}
				}
				else { //没有绑定关系，新增绑定关系
					myCoach.setIsNew(0);
					myCoach.setCtime(new Date());
					myCoach.setType(0);
					wechatSchoolManager.insertWechatMycoaches(myCoach);
				}
			}
			else {
				r.setCode(ResultCode.ERRORCODE.AUTHCODE_ERROR);
				r.setMsgInfo(ResultCode.ERRORINFO.AUTHCODE_ERROR);
				return r;
			}
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}


	@Override
	public ReqResult smsNoticeStudent(Long userId, String studentId, String studentPhone, String cStart) {
		try {
			Coach coach = coachManager.getCoachInfo(userId);
			Map<Integer, String> msgs = new HashMap<Integer, String>();
			Date dStart = new Date(Long.parseLong(cStart) * 1000);
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String ctime = formatter.format(dStart);
			msgs.put(1, ctime);
			msgs.put(2, coach.getQrcode());
			return authcodeService.sendMsgById(passMsgId, studentPhone, msgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public ReqResult alertClassStudent(Long coachId, Integer ccid, String orderId, List<WechatPlantClass> stuJsonList) {
		ReqResult r = ReqResult.getSuccess();
		try {
			//操作包括Plantclass表 Order表及class表
			//判断学员人数是否超限,一次课程最多4个学员
			if (stuJsonList != null && stuJsonList.size() > 4) {
				r.setCode(ResultCode.ERRORCODE.OVER_LIMIT_NUM);
				r.setMsgInfo(ResultCode.ERRORINFO.OVER_LIMIT_NUM);
				return r;
			}
			
			WechatCoachClass recordClass = new WechatCoachClass();
			recordClass.setCcid(ccid);
			WechatCoachClass coachClassInfo = wechatSchoolManager.queryWechatCoachClass(recordClass);
			
			recordClass.setMaxNum(stuJsonList.size());
			recordClass.setBookNum(stuJsonList.size());
			
			//查原排班所有信息
			WechatPlantClass plantClass = new WechatPlantClass();
			plantClass.setCcid(ccid);
			plantClass.setCoachId(coachId);
			plantClass.setIsdel(0);
			List<WechatPlantClass> plantClasList = wechatSchoolManager.queryWechatPlantClassList(plantClass);
			
			String uorderId = null;
			StringBuilder sb = new StringBuilder();
			WechatOrder wechatOrder = new WechatOrder();
			wechatOrder = BeanCopy.copy2Null(coachClassInfo, wechatOrder);
			Coach coach = coachManager.getCoachInfo(coachId);
			List<String> sendMobileList = new ArrayList<String>();
			
			if (plantClasList != null && plantClasList.size() > 0) {
				List<String> mobileList = BeanCopy.getFieldList(plantClasList, "stuMobile"); //获取手机号数组
				List<WechatPlantClass> removePlantClassList = new ArrayList<WechatPlantClass>();
				removePlantClassList = BeanCopy.copyList(plantClasList, WechatPlantClass.class, BeanCopy.COPYALL);
				if (stuJsonList != null && stuJsonList.size() > 0) {
					int j = 0;
					for (WechatPlantClass pcOne :stuJsonList) {
						j ++;
						//获取学员信息
						WechatStudent wechatStudent = new WechatStudent();
						wechatStudent.setPhone(pcOne.getStuMobile());
						WechatStudent studentInfo = wechatSchoolManager.queryWechatStudent(wechatStudent );
						
						if (mobileList.contains(pcOne.getStuMobile())) { //排班表已存在该手机信息,非删除该学员，保留该学员，无须通知
							//从原排班结果集删除已存在的手机号，剩下既是要删除的学员
							mobileList.remove(pcOne.getStuMobile());
							if (removePlantClassList != null && removePlantClassList.size() > 0) {
								for (int i = 0; i < removePlantClassList.size(); i ++) {
									if(removePlantClassList.get(i).getStuMobile() != null && pcOne.getStuMobile().equals(removePlantClassList.get(i).getStuMobile())) {
										removePlantClassList.remove(i);
										break;
									}
								}
							}
						}
						else { //排班信息表不存在该手机号，增加排班信息
							uorderId = SecurityUtil.getUUID();
							if (!StringUtils.isNotEmpty(pcOne.getStuMobile())) {
								r.setCode(ResultCode.ERRORCODE.RECHARGE_NEED_MOBILE);
								r.setMsgInfo(ResultCode.ERRORINFO.RECHARGE_NEED_MOBILE);
								return r;
							}
							
							//把学员插入排班表
							pcOne.setOrderId(uorderId);
							pcOne.setCcid(ccid);
							pcOne.setCoachId(coachId);
							pcOne.setOrderId(uorderId);
							pcOne.setGtime(new Date());
							pcOne.setStudentId(studentInfo.getStudentId());
							pcOne.setStuImg(studentInfo.getHeadIcon());
							wechatSchoolManager.insertWechatPlantClass(pcOne);
							
							//插入订单表
							wechatOrder.setOrderId(uorderId);
							wechatOrder.setCcid(ccid);
							wechatOrder.setStudentId(studentInfo.getStudentId());
							wechatOrder.setStuImg(studentInfo.getHeadIcon());
							wechatOrder.setStuMobile(pcOne.getStuMobile());
							wechatOrder.setStuName(pcOne.getStuName());
							wechatSchoolManager.inserWechatOrder(wechatOrder);
							
							//判断学员与教练是否存在微信绑定关系
							WechatMycoaches isExitCoach = new WechatMycoaches();
							isExitCoach.setCoachId(coachId);
							isExitCoach.setStudentId(studentInfo.getStudentId());
							WechatMycoaches wechatMycoaches = wechatSchoolManager.queryMyWechatCoach(isExitCoach);
							if (wechatMycoaches != null && wechatMycoaches.getWxstatus() == 2) {
								logger.info("******************************** Student is bound Coach by wechat!Send Template Message!");
								//邀课通知
								if (studentInfo != null && StringUtils.isNotEmpty(studentInfo.getOpenId())) {
									String firstStr  =  "亲爱的" + studentInfo.getName() + "，" +coach.getName()+"教练邀请您上"+coachClassInfo.getCourseName()+"课！";
									sendTemplateMsg(studentInfo, coach, firstStr, wechatOrder);
								}
							}
							else {
								sb.append(pcOne.getStuName());
								if (j < stuJsonList.size()) {
									sb.append("、");
								}
								sendMobileList.add(pcOne.getStuMobile());
							}
						}
					}
					
					if (sb.length() > 0) {
						if (sb.substring(sb.length()-1, sb.length()).equals("、")) {
							sb.deleteCharAt(sb.length()-1);
						}
						r.setCode(ResultCode.ERRORCODE.ORDER_NOT_BOUND);
						r.setMsgInfo(sb + ResultCode.ERRORINFO.ORDER_NOT_BOUND);
						r.setData(sendMobileList);
					}
				}
				if (mobileList != null && mobileList.size() > 0) { //这些都是被删除的学员排班
					for (WechatPlantClass wechatPlantClass : removePlantClassList) {
						WechatOrder orderInfo = new WechatOrder();
						orderInfo.setOrderId(wechatPlantClass.getOrderId());
						WechatOrder wechatOrderInfo = wechatSchoolManager.queryWechatOrder(orderInfo);

						WechatOrder order = new WechatOrder();
						order.setOrderId(wechatPlantClass.getOrderId());
						order.setOrderState(3); //教练取消
						//删除该学员的订单order
						wechatSchoolManager.updateWechatOrder(order );
						
						//删除plant排班关系
						plantClass.setOrderId(wechatPlantClass.getOrderId());
						plantClass.setIsdel(1);
						plantClass.setState(3);
						wechatSchoolManager.updateWechatPlantClass(plantClass);
						
						//获取学员信息
						WechatStudent wechatStudent = new WechatStudent();
						wechatStudent.setStudentId(wechatPlantClass.getStudentId());
						WechatStudent studentInfo = wechatSchoolManager.queryWechatStudent(wechatStudent );
						
						//取消课程通知
						if (studentInfo != null && StringUtils.isNotEmpty(studentInfo.getOpenId())) {
							String firstStr  =  "亲爱的" + studentInfo.getName() + "，" +coach.getName()+"教练取消了您的"+coachClassInfo.getCourseName()+"课！";
							sendTemplateMsg(studentInfo, coach, firstStr, wechatOrderInfo);
						}
					}
					wechatSchoolManager.updateWechatCoachClass(recordClass);
				}
			}
			else {
				int i = 0;
				for (WechatPlantClass pcOne :stuJsonList) {
					i ++;
					uorderId = SecurityUtil.getUUID();
					
					if (!StringUtils.isNotEmpty(pcOne.getStuMobile())) {
						r.setCode(ResultCode.ERRORCODE.RECHARGE_NEED_MOBILE);
						r.setMsgInfo(ResultCode.ERRORINFO.RECHARGE_NEED_MOBILE);
						return r;
					}
					//获取学员信息
					WechatStudent wechatStudent = new WechatStudent();
					wechatStudent.setStudentId(pcOne.getStudentId());
					WechatStudent studentInfo = wechatSchoolManager.queryWechatStudent(wechatStudent);
					
					//把学员插入排班表
					pcOne.setOrderId(uorderId);
					pcOne.setCcid(ccid);
					pcOne.setCoachId(coachId);
					pcOne.setOrderId(uorderId);
					pcOne.setGtime(new Date());
					pcOne.setStudentId(studentInfo.getStudentId());
					pcOne.setStuImg(studentInfo.getHeadIcon());
					wechatSchoolManager.insertWechatPlantClass(pcOne);
					
					//插入订单表
					wechatOrder.setOrderId(uorderId);
					wechatOrder.setCcid(ccid);
					wechatOrder.setStudentId(studentInfo.getStudentId());
					wechatOrder.setStuImg(studentInfo.getHeadIcon());
					wechatOrder.setStuMobile(pcOne.getStuMobile());
					wechatOrder.setStuName(pcOne.getStuName());
					wechatOrder.setClzNum(1);
					wechatSchoolManager.inserWechatOrder(wechatOrder);
					
					//判断学员与教练是否存在微信绑定关系
					WechatMycoaches isExitCoach = new WechatMycoaches();
					isExitCoach.setCoachId(coachId);
					isExitCoach.setStudentId(studentInfo.getStudentId());
					WechatMycoaches wechatMycoaches = wechatSchoolManager.queryMyWechatCoach(isExitCoach);
					if (wechatMycoaches != null && wechatMycoaches.getWxstatus() == 2) {
						logger.info("******************************** Student is bound Coach by wechat! Send Template Message!");
						//发送微信通知
						if (studentInfo != null && StringUtils.isNotEmpty(studentInfo.getOpenId())) {
							String firstStr  =  "亲爱的" + studentInfo.getName() + "，" +coach.getName()+"教练邀请您上"+coachClassInfo.getCourseName()+"课！";
							sendTemplateMsg(studentInfo, coach, firstStr, wechatOrder);
						}
					}
					else {
						sb.append(pcOne.getStuName());
						if (i < stuJsonList.size()) {
							sb.append("、");
						}
						sendMobileList.add(pcOne.getStuMobile());
					}
				}
				
				 wechatSchoolManager.updateWechatCoachClass(recordClass);
				if (sb.length() > 0) {
					if (sb.substring(sb.length()-1, sb.length()).equals("、")) {
						sb.deleteCharAt(sb.length()-1);
					}
					r.setCode(ResultCode.ERRORCODE.ORDER_NOT_BOUND);
					r.setMsgInfo(sb + ResultCode.ERRORINFO.ORDER_NOT_BOUND);
					r.setData(sendMobileList);
				}
			}
				
		} catch (NumberFormatException e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}
	

	@Override
	public ReqResult getQrCode(Long coachId) {
		ReqResult r = ReqResult.getSuccess();
		try {
			//教练个人信息已有缓存，不需要重新缓存
			Coach coach = new Coach();
			coach.setCoachId(coachId );
			boolean isUpCoach = false; //是否需要更新教练个人信息
			Coach coachInfo = coachManager.getCoachInfo(coachId);
			if (coachInfo != null) {
				if( coachInfo.getQrcode() == null || "".equals(coachInfo.getQrcode())) {
					//添加教练二维码信息
					String wikiUrl = wechatCoachService.getWikiUrl(coachId);
					coach.setQrcode(wikiUrl);
					isUpCoach = true;
					r.setData(wikiUrl); 
				}
				else {
					r.setData(coachInfo.getQrcode());
				}
			}
			
			if (isUpCoach) {//更新教练个人信息
				coachManager.updateCoach(coach); 
				coachManager.getCoachInfo(coachId);
				logger.info("************************************** Update Coach Info Success! coach : " + coach);
			}
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		
		return r;
	}


	/**
	 * 学员提交教练分享信息
	 */
	@Override
	public ReqResult subStudentInfo(String openId, String studentId, Long coachId, String coachName, String studentName, String studentPhone, Integer state) {
		ReqResult r = ReqResult.getSuccess();
		try {
			//判读学员手机号是否存在
			WechatStudent wechatStudent = new WechatStudent();
			wechatStudent.setPhone(studentPhone);
			WechatStudent isExitPhone = wechatSchoolManager.queryWechatStudent(wechatStudent);
			WechatStudent studentInfo =  null;
			Long studentIdLong = null;
			if (isExitPhone == null) { //学员手机号不存在，增加学员信息
				wechatSchoolManager.insertWechatStudent(wechatStudent);
				
				studentInfo = wechatSchoolManager.queryWechatStudent(wechatStudent);
				studentIdLong = studentInfo.getStudentId();
			}
			else { //学员手机号已存在
				studentIdLong = isExitPhone.getStudentId();
			}
			
			//判断学员是否已有微信绑定教练
			WechatMycoachesDto wxcoach = new WechatMycoachesDto();
			wxcoach.setStudentId(studentIdLong);
			wxcoach.setIsdel(0);
			wxcoach.setWxstatusStr("1,2");
			WechatMycoaches  exitWxcoaches = wechatSchoolManager.queryMyWechatBoundCoach(wxcoach);
			if (exitWxcoaches != null ) {
				Coach coach = coachManager.getCoachInfo(exitWxcoaches.getCoachId());
				r.setCode(ResultCode.ERRORCODE.EXIST_COACH_RELATIONSHIP);
				r.setMsgInfo(ResultCode.ERRORINFO.EXIST_COACH_RELATIONSHIP.replace("{1}", coach.getName()));
				return r;
			}
			
			
			//判断是否已跟教练绑定关系
			WechatMycoaches myCoach = new WechatMycoaches();
			myCoach.setStudentId(studentIdLong);
			myCoach.setCoachId(coachId);
			WechatMycoaches wechatMycoaches = wechatSchoolManager.queryMyWechatCoach(myCoach);
			
			myCoach.setState(state);
			myCoach.setChannel(2);
			myCoach.setIsNew(0);
			myCoach.setType(0);
			myCoach.setIsdel(0);
			myCoach.setName(studentName);
			myCoach.setPhone(studentPhone);
			if (wechatMycoaches != null) { //已有绑定关系，但是关系不确定
				myCoach.setId(wechatMycoaches.getId());
				if ( wechatMycoaches.getIsdel() == 1 || !wechatMycoaches.getName().equals(studentName) || wechatMycoaches.getState() != state) { //如果已经取消关联或已经删除，更新绑定关系
					wechatMycoaches.setName(studentName);
					wechatMycoaches.setState(state);
					wechatMycoaches.setIsdel(0);
					wechatSchoolManager.updateWechatMyCoach(wechatMycoaches);
					return r;
				}
			}
			else { //没有绑定关系，新增绑定关系
				wechatSchoolManager.insertWechatMycoaches(myCoach);
			}
			
			//极光推送-教练
			JpushMsg jmsg = new JpushMsg();
			Message jpush = new Message();
			String message = "有新的学员报名学车，快去联系他吧!";
			jmsg.setAlter(message);
			jmsg.setUserId(coachId);
			jmsg.setOperate(JpushConstant.OPERATE.COACHWECHATENROLL);
			jmsg.setOrderId(coachId.toString());
			jpush.setTopic(jpushProducer.getCreateTopicKey());
			jpush.setKeys(coachId+JpushConstant.OPERATE.COACHWECHATENROLL);
			jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
			jpush.setBody(SerializableUtil.serialize(jmsg));
			jpush.setKeys(coachId.toString());
			jpushProducer.send(jpush);
			logger.info("*********************************** Send Jpush Message Info, CoachId : " +  coachId +", Message :"+ message);
			
			
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		
		return r;
	}


	@Override
	public ReqResult getWeChatStudentInfo(String openId) {
		ReqResult r = ReqResult.getSuccess();
		try {
			WechatStudent wechatStudent = new WechatStudent();
			wechatStudent.setOpenId(openId);
			WechatStudent studentInfo = wechatSchoolManager.queryWechatStudent(wechatStudent);
			r.setData(studentInfo);
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}


	@Override
	public ReqResult topAndStaeStudent(Long coachId, Long studentId, String isTop, String state) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			WechatMycoaches mycoach = new WechatMycoaches();
			mycoach.setStudentId(studentId);
			
			if (StringUtils.isNotEmpty(isTop)) { //置顶、取消置顶
				mycoach.setIstop(Integer.parseInt(isTop));
			}
			if (StringUtils.isNotEmpty(state)) { //更换科目
				mycoach.setState(Integer.parseInt(state));
			}
			wechatSchoolManager.updateWechatMycoaches(mycoach);
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		
		return r;
	}


	@Override
	public ReqResult getMyStudentNum(Long coachId) {
		ReqResult r = ReqResult.getSuccess();
		 try {
			//教练下各科目学员数量统计
			 WechatStudentCount wechatStudentCount = new WechatStudentCount();
			 WechatMycoaches wechatStudentDto = new WechatMycoaches();
			 wechatStudentDto.setCoachId(coachId);
			 for (int  i =0; i < 6; i++) {
				 wechatStudentDto.setState(i);
				 Integer count = wechatSchoolManager.countMyStudentNum(wechatStudentDto);
				 if (i == 0) {
					 wechatStudentCount.setCourse0(count);
				 }
				 else if (i == 1) {
					 wechatStudentCount.setCourse1(count);
				 }
				 else if (i == 2) {
					 wechatStudentCount.setCourse2(count);
				 }
				 else if (i == 3) {
					 wechatStudentCount.setCourse3(count);
				 }
				 else if (i == 4) {
					 wechatStudentCount.setCourse4(count);
				 }
				 else if (i == 5) {
					 wechatStudentCount.setCourse5(count);
				 }
			 }
			 //新学员人数
			 WechatMycoachesDto mycoach = new WechatMycoachesDto();
			 mycoach.setCoachId(coachId);
			 mycoach.setIsdel(0);
			 mycoach.setType(0);
			 Integer newCount =  wechatSchoolManager.countNewStudent(mycoach);
			 wechatStudentCount.setCountTotal(newCount);
			 r.setData(wechatStudentCount);
			 
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}


	@Override
	public ReqResult saveWechatUserInfo(String openId, JSONObject userInfoJson) {
		ReqResult r = ReqResult.getSuccess();
		try {
			WechatStudent record = new WechatStudent();
			record.setOpenId(openId);
			WechatStudent  isExitOpenId = wechatSchoolManager.queryWechatStudent(record);
			
			WechatStudent wechatStudent = new WechatStudent();
			if (userInfoJson != null) {
				//请求错误信息返回
				if (userInfoJson.containsKey("errcode")) {
					logger.info("********************************** errcode :" + userInfoJson.getString("errcode"));
					logger.info("********************************** errmsg :" + userInfoJson.getString("errmsg"));
				}
				else { //成功返回
					wechatStudent.setSubscribe(userInfoJson.getString("subscribe"));//用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息，只有openid和UnionID（在该公众号绑定到了微信开放平台账号时才有）
					wechatStudent.setOpenId(userInfoJson.getString("openid"));//用户的标识，对当前公众号唯一
					wechatStudent.setName(userInfoJson.getString("nickname"));//用户的昵称
					if (userInfoJson.getString("nickname") != null && !"".equals(userInfoJson.getString("nickname"))) {
						wechatStudent.setSex(Integer.parseInt(userInfoJson.getString("sex")));//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
					}
					wechatStudent.setCityName(userInfoJson.getString("city"));//用户所在城市
					wechatStudent.setHeadIcon(userInfoJson.getString("headimgurl"));//用户头像
					if (userInfoJson.getString("subscribe_time") != null && !"".equals(userInfoJson.getString("subscribe_time"))) {
						wechatStudent.setCtime(TimeUtil.parseDate(userInfoJson.getString("subscribe_time"))); //用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
					}
					wechatStudent.setUnionId(userInfoJson.getString("unionid"));//只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
					wechatStudent.setGroupid(userInfoJson.getString("groupid")); //用户所在的分组ID（暂时兼容用户分组旧接口）
					wechatStudent.setTagidList(userInfoJson.getString("tagid_list"));//用户被打上的标签ID列表
					
					//判断OpenId是否已存在，如果存在更新信息
					if (isExitOpenId != null) {
						wechatSchoolManager.updateWechatStudent(wechatStudent);
					}
					else { //如果不存在，插入一条学员记录
						//wechatSchoolManager.insertWechatStudent(wechatStudent);
					}
				}
			}
			
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		
		return r;
	}


	@Override
	public ReqResult getEnStudentClassHis(Long coachId, Long studentId, Integer type) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			@SuppressWarnings("rawtypes")
			List<Map> mapList = new ArrayList<Map>();
			if (type == 1) {
				/**
				 * 微信学员上课记录
				 */
				WechatPlantClass record = new WechatPlantClass();
				record.setCoachId(coachId);
				record.setStudentId(studentId);
				List<WechatPlantClass> wechatPlantClassList = wechatSchoolManager.queryWechatPlantClassList(record );
				int max_num = 0;
				if (wechatPlantClassList != null && wechatPlantClassList.size() > 0) {
					//上课明细
					WechatCoachClassDto coachClassDto = new WechatCoachClassDto();
					int j=0;
					String ccidin="";
					for (; j< wechatPlantClassList.size() -1; j++) {
						ccidin += wechatPlantClassList.get(j).getCcid()+",";
					}
					ccidin+= wechatPlantClassList.get(j).getCcid() + "";
					coachClassDto.setCcidIn(ccidin);
					List<WechatCoachClass> coachClassList =  wechatSchoolManager.queryCoachClassByCcidIn(coachClassDto);
					if (coachClassList != null && coachClassList.size() > 0) {
						Map<String,Object> map = null;
						for (WechatCoachClass wechatCoachClass : coachClassList) {
							map = new HashMap<String,Object>();
							if (wechatCoachClass.getCstart().before(new Date())) { //比当前时间大
								map.put("cstart", wechatCoachClass.getCstart().getTime());
								map.put("cend", wechatCoachClass.getCend().getTime());
								map.put("studentNum", wechatCoachClass.getBookNum());
								mapList.add(map);
								max_num ++;
							}
						}
						r.setData("max_num", max_num); //上课次数
						r.setData(mapList);
					}
				}
			}
			else if (type ==2) {
				/**
				 * 喱喱学员上课记录
				 */
				OrderVo search=new OrderVo();
				search.setCoachId(coachId);
				search.setStudentId(studentId);
				OrderQuery orderQuery=new OrderQuery();
				orderQuery.setorderBy(" and order_state in (4,5,6,7) order by course_id asc, rstart desc ");
				List<OrderVo> list = orderService.queryByObjectAnd(search, orderQuery);
				if (list != null && list.size() > 0) {
					Map<String,Object> map = null;
					CoachClassVo cc = null;
					for (OrderVo orderVo : list){
						map = new HashMap<String,Object>();
						cc = redisUtil.get(REDISKEY.COACH_CLASS_CCID_CACHE + orderVo.getCcid());
						if (cc == null) {
							cc = coachClassService.queryCoachClassById(orderVo.getCcid(), new CoachClassQuery());
							redisUtil.set(REDISKEY.COACH_CLASS_CCID_CACHE + orderVo.getCcid(), cc);
						}
						if(orderVo.getOtype() != null && orderVo.getOtype() ==3) {
							if ("1".equals(orderVo.getClzNum()) || orderVo.getClzNum() ==1) { //如果只有一个人上课
								if (orderVo.getPstart() != null && orderVo.getPend() != null) {
									long diff = orderVo.getPend().getTime() - orderVo.getPstart().getTime();
									logger.info("*********************************** diff : " + diff );
									long diffHours = diff / (60 * 60 * 1000) % 24;
									if (cc != null && null != cc.getBookNum()) {
										diffHours = diffHours/cc.getBookNum();
									}
									map.put("classTime", diffHours); //上课课时
								}
								else {
									map.put("classTime", orderVo.getClzNum()); //上课课时
								}
							}
							else {
								map.put("classTime", orderVo.getClzNum()); //上课课时
							}
						}
						else { //现约及续约
							map.put("classTime", orderVo.getClzNum()); //上课课时
						}
						map.put("course_id", orderVo.getCourseId()); //上课科目ID
						map.put("course_name", orderVo.getCourseName()); //上课科目
						map.put("cstart", orderVo.getPstart());
						map.put("cend", orderVo.getPend());
						if (cc != null && cc.getBookNum() != null) {
							logger.info("*************************************************** cc.getBookNum() : " + cc.getBookNum());
							map.put("bookNum", cc.getBookNum()); //预约人数
						}
						else { //现约
							map.put("bookNum", 1); //现约人数永远都是1个人
						}
						map.put("otype", orderVo.getOtype());
						map.put("orderId", orderVo.getOrderId());
						mapList.add(map);
					}
					r.setData(mapList);
				}
				
			}
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		
		return r;
	}


	@Override
	public ReqResult acceptOrDelStudent(Long coachId, Long studentId, String isdel, String state, String type) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			WechatMycoaches mycoaches = new WechatMycoaches();
			mycoaches.setCoachId(coachId);
			mycoaches.setStudentId(studentId);
			if (StringUtils.isNotEmpty(isdel)) {
				mycoaches.setIsdel(Integer.parseInt(isdel));
			}
			if (StringUtils.isNotEmpty(state)) {
				mycoaches.setState(Integer.parseInt(state));
			}
			if (StringUtils.isNotEmpty(type)) {
				mycoaches.setType(Integer.parseInt(type));
			}
			wechatSchoolManager.updateWechatMycoaches(mycoaches);
		} catch (NumberFormatException e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		
		return r;
	}

	@Override
	public ReqResult bindCoachByScan(Map<String, String> params) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			String openId = params.get("FromUserName");
			String ticket = params.get("Ticket");
			if (StringUtils.isNotEmpty(openId) && StringUtils.isNotEmpty(ticket)){
				Coach c = new Coach();
				c.setQrcode("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + URLEncoder.encode(ticket, "UTF-8"));
				List<Coach> coachs = coachManager.getCoach(c);
				if (coachs == null || coachs.size() != 1) {
					// 20161221 为了避免把错误信息返回到学员微信，只有code=0的时候才返回数据，否则不返回
					r.setCode(ResultCode.ERRORCODE.SUCCESS);
					r.setMsgInfo(ResultCode.ERRORINFO.COACH_NOT_EXIST);
					return r;
				}
				c = coachs.get(0);
				WechatStudent s = new WechatStudent();
				s.setOpenId(openId);
				s = wechatSchoolManager.queryWechatStudent(s);
				List<WechatMycoaches> mycoaches = null;
				if (s != null) {
					WechatMycoachesDto mycoachesDto = new WechatMycoachesDto();
					mycoachesDto.setStudentId(s.getStudentId());
					mycoaches = wechatSchoolManager.queryWechatMycoachesList(mycoachesDto);
				}
				if (mycoaches != null) {
					for (WechatMycoaches mycoach : mycoaches) {
						if (mycoach.getIsdel() == 0 && (mycoach.getWxstatus() == 2 || mycoach.getWxstatus() == 1)) {
							if (mycoach.getCoachId().equals(c.getCoachId())){
								// 20161221 为了避免把错误信息返回到学员微信，只有code=0的时候才返回数据，否则不返回
								r.setCode(ResultCode.ERRORCODE.SUCCESS);
								r.setMsgInfo(ResultCode.ERRORINFO.COACH_STUDENT_RELATIONSHIP_EXIST);
								return r;
							} else {
								// 20161221 为了避免把错误信息返回到学员微信，只有code=0的时候才返回数据，否则不返回
								r.setCode(ResultCode.ERRORCODE.SUCCESS);
								Coach cc = coachManager.getCoachInfo(mycoach.getCoachId());
								r.setMsgInfo(ResultCode.ERRORINFO.EXIST_COACH_RELATIONSHIP.replace("{1}", cc.getName()));
								return r;
							}
						}
					}
				}
				r.setMsgInfo("<![CDATA[亲爱的学员，要和" + c.getName() + "教练进行绑定，还需完善一下个人信息哦："
						+ "<a href=\"" + wechat_page_domain_name + "/bind?coachId=" + c.getCoachId() + "\">点击填写信息完成绑定</a>]]>");
			} else {
				r.setCode(ResultCode.ERRORCODE.FAILED);
				r.setMsgInfo(ResultCode.ERRORINFO.FAILED);
			}
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			logger.error("******************** Exception hanppen in bindCoachByScan : " + e.getMessage());
		}
		
		return r;
	}

	@Override
	public ReqResult closeWechatClass(Long coachId, Integer ccid) {
		ReqResult r = ReqResult.getSuccess();
		try {
			WechatCoachClass record = new WechatCoachClass();
			record.setCcid(ccid);
			WechatCoachClass wechatCoachClass= wechatSchoolManager.queryWechatCoachClass(record);
			if (wechatCoachClass != null && wechatCoachClass.getCstart() != null) {
				if (wechatCoachClass.getCstart().before(new Date())) {
					r.setCode(ResultCode.ERRORCODE.ORDER_NO_BOOK);
					r.setMsgInfo(ResultCode.ERRORINFO.ORDER_NO_BOOK);
					return r;
				}
			}
			else {
				r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
				r.setCode(ResultCode.ERRORCODE.PARAMERROR);
				return r;
			}
			
			//关闭排班表
			WechatCoachClass recordClass = new WechatCoachClass();
			recordClass.setCcid(ccid);
			recordClass.setCoachId(coachId);
			recordClass.setIsdel(1);
			wechatSchoolManager.updateWechatCoachClass(recordClass);
			
			WechatPlantClass plantRecord = new WechatPlantClass();
			plantRecord.setCoachId(coachId);
			plantRecord.setCcid(ccid);
			List<WechatPlantClass>  plantClassList = wechatSchoolManager.queryWechatPlantClassList(plantRecord);
			if (plantClassList != null && plantClassList.size() > 0) {
				//关闭所有排班
				WechatPlantClass plantClass = new WechatPlantClass();
				plantClass.setCoachId(coachId);
				plantClass.setCcid(ccid);
				plantClass.setIsdel(1);
				plantClass.setState(3);
				wechatSchoolManager.updateWechatPlantClass(plantClass);
				
				//关闭所有订单
				WechatOrder order = new WechatOrder();
				order.setCoachId(coachId);
				order.setCcid(ccid);
				order.setOrderState(3);
				wechatSchoolManager.updateWechatOrder(order);
				
				//向学员发送微信通知
				Coach coach = coachManager.getCoachInfo(coachId);
				for (WechatPlantClass wechatPlantClass : plantClassList) {
					//获取学员信息
					WechatStudent wechatStudent = new WechatStudent();
					wechatStudent.setStudentId(wechatPlantClass.getStudentId());
					WechatStudent studentInfo = wechatSchoolManager.queryWechatStudent(wechatStudent );
					
					order = new WechatOrder();
					order.setOrderId(wechatPlantClass.getOrderId());
					WechatOrder wechatOrder = wechatSchoolManager.queryWechatOrder(order);
					
					//发送微信通知
					if (studentInfo != null && StringUtils.isNotEmpty(studentInfo.getOpenId())) {
						String firstStr = "亲爱的" + studentInfo.getName() + "，" +coach.getName()+"教练取消了您的"+wechatCoachClass.getCourseName()+"课！";
						sendTemplateMsg(studentInfo, coach, firstStr, wechatOrder);
					}
				}
			}
			
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}
	
	

	/**
	 * 通用课程通知模板
	 * @param studentInfo
	 * @param coach
	 * @param firstStr
	 * @param wechatCoachClass
	 */
	private void sendTemplateMsg(WechatStudent studentInfo, Coach coach, String firstStr, WechatOrder wechatOrder) {
		Template template=new Template();  
		template.setTopColor("#00DD00");  
		WechatTemplate tempRecord = null;
		tempRecord = new WechatTemplate();
		tempRecord.setId(TEMPLATEID.COACH_INVITE);
		WechatTemplate wechatTemplate = wechatSchoolManager.queryWechatTemplate(tempRecord);
		if (wechatTemplate != null && wechatTemplate.getTemplateId() != null) {
			template.setTemplateId(wechatTemplate.getTemplateId());  
			template.setUrl(wechatTemplate.getUrl() + wechatOrder.getOrderId());  
		}
		template.setToUser(studentInfo.getOpenId());  
		if (template.getTemplateId() != null && !"".equals(template.getTemplateId())) {
			List<TemplateParam> paras=new ArrayList<TemplateParam>();  
			paras.add(new TemplateParam("first", firstStr,"#AAAAAA"));  
			paras.add(new TemplateParam("keyword1", coach.getName(),"#AAAAAA"));  // 教练姓名
			paras.add(new TemplateParam("keyword2", wechatOrder.getStuName(),"#AAAAAA"));  //学员姓名
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			paras.add(new TemplateParam("keyword3", formatter.format(wechatOrder.getCstart()),"#AAAAAA"));  //开始时间
			if(wechatOrder.getCstart() !=null && wechatOrder.getCend()!= null) {
				 long diff = wechatOrder.getCend().getTime() - wechatOrder.getCstart().getTime();
		         long diffHours = diff / (60 * 60 * 1000) % 24;
		         paras.add(new TemplateParam("keyword4",   diffHours + "课时", "#AAAAAA"));  //课程时长
			}
			else {
				paras.add(new TemplateParam("keyword4",  "1课时", "#AAAAAA"));  //课程时长
			}
			paras.add(new TemplateParam("remark", "训练场地址：" + wechatOrder.getPlaceName(),"#AAAAAA"));  //训练场地址
			template.setTemplateParamList(paras);  
			logger.info("******************************************** Send template Json : " + template.toJSON());
			
			boolean isSendSuc = wechatCoachService.sendTemplateMsg(template);
			if (isSendSuc) {
				logger.info("******************************************** Send template Message Success!");
			}
			else {
				logger.info("******************************************** Send template Message Fail!");
			}
		}
	}
	
	/**
	 * 报名通知
	 * @param studentInfo
	 * @param coach
	 * @param firstStr
	 * @param wechatOrder
	 */
	private void sendTemplateEnrollMsg(WechatStudent studentInfo, String firstStr, String studentName, String className, String drtype, Integer payPrice, String coachName, String orderId,String cityName) {
		Template template=new Template();  
		template.setTopColor("#00DD00");  
		WechatTemplate tempRecord = null;
		tempRecord = new WechatTemplate();
		tempRecord.setId(TEMPLATEID.ENROLL_PURPOSE_SUCCESS);
		WechatTemplate wechatTemplate = wechatSchoolManager.queryWechatTemplate(tempRecord);
		if (wechatTemplate != null && wechatTemplate.getTemplateId() != null) {
			template.setTemplateId(wechatTemplate.getTemplateId());  
			template.setUrl(wechatTemplate.getUrl());  
		}
		template.setToUser(studentInfo.getOpenId());  
		if (template.getTemplateId() != null && !"".equals(template.getTemplateId())) {
			List<TemplateParam> paras=new ArrayList<TemplateParam>();  
			paras.add(new TemplateParam("first",firstStr,"#AAAAAA"));  
			if ("2".equals(drtype)) {
				paras.add(new TemplateParam("keyword1",className + " C2" ,"#AAAAAA"));  
			}
			else {
				paras.add(new TemplateParam("keyword1",className + " C1" ,"#AAAAAA"));  
			}
			double d = (double) payPrice/100;
			paras.add(new TemplateParam("keyword2",String.valueOf(d),"#AAAAAA"));  //支付金额
			paras.add(new TemplateParam("keyword3",studentName,"#AAAAAA"));  //学员姓名
			paras.add(new TemplateParam("keyword4",studentInfo.getPhone(),"#AAAAAA"));  //学员电话
			paras.add(new TemplateParam("keyword5",orderId,"#AAAAAA"));  //订单号
			paras.add(new TemplateParam("remark","城市 : " + cityName,"#AAAAAA"));  
			
			template.setTemplateParamList(paras);  
			logger.info("******************************************** Send template Json : " + template.toJSON());
			
			boolean isSendSuc = wechatCoachService.sendTemplateMsg(template);
			if (isSendSuc) {
				logger.info("******************************************** Send template Message Success!");
			}
			else {
				logger.info("******************************************** Send template Message Fail!");
			}
		}
	}


	@Override
	public ReqResult getWeChatClass(String openId, String coachId, String ccid, String orderId) {

		ReqResult r = ReqResult.getSuccess();
		try {
			WechatOrder record = new WechatOrder();
			record.setOrderId(orderId);
			WechatOrder wechatOrder= wechatSchoolManager.queryWechatOrder(record);
			if (wechatOrder != null) {
				r.setData(wechatOrder);
			}
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}


	@Override
	public ReqResult updateWechatEnroll(Long coachId, String orderId, String remark) {
		ReqResult r = ReqResult.getSuccess();
		try {
			WechatEnrollStudent updateEnrollStudent = new WechatEnrollStudent();
			updateEnrollStudent.setOrderId(orderId);
			updateEnrollStudent.setRemark(remark);
			updateEnrollStudent.setCoachId(coachId);
			wechatSchoolManager.updateWechatEnrollStudent(updateEnrollStudent );
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}


	@Override
	public ReqResult getWechatClassOne(Long coachId, String orderId, String ccid) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			WechatCoachMyClass coachMyClass = new WechatCoachMyClass();
			WechatCoachClass coachClass = new WechatCoachClass();
			coachClass.setCoachId(coachId);
			coachClass.setOrderId(orderId);
			if (StringUtils.isNotEmpty(ccid)) { 
				coachClass.setCcid(Integer.parseInt(ccid));
			}
			WechatCoachClass wechatCoachClass = wechatSchoolManager.queryWechatCoachClass(coachClass);
			if (wechatCoachClass != null) {
				if (wechatCoachClass.getIsdel() == 1) {
					r.setCode(ResultCode.ERRORCODE.ORDER_IS_CLOSE);
					r.setMsgInfo(ResultCode.ERRORINFO.ORDER_IS_CLOSE);
					return r;
				}
				coachMyClass = BeanCopy.copyAll(wechatCoachClass, WechatCoachMyClass.class);
				WechatPlantClass record = new WechatPlantClass();
				record.setCcid(wechatCoachClass.getCcid());
				record.setIsdel(0);
				List<WechatPlantClass> plantClassList = wechatSchoolManager.queryWechatPlantClassList(record );
				if (plantClassList != null && plantClassList.size() > 0) {
					coachMyClass.setPlantClassList(plantClassList);
				}
			}
			else {
				r.setCode(ResultCode.ERRORCODE.ORDER_IS_CLOSE);
				r.setMsgInfo(ResultCode.ERRORINFO.ORDER_IS_CLOSE);
				return r;
			}
			r.setData(coachMyClass);
		} catch (NumberFormatException e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}


	@Override
	public ReqResult sendTemplateEnrollMsg(WechatEnrollStudent enrollStudent, String firstStr) {
		Template template=new Template();  
		template.setTopColor("#00DD00");  
		WechatTemplate tempRecord = null;
		tempRecord = new WechatTemplate();
		tempRecord.setId(TEMPLATEID.ENROLL_PURPOSE_SUCCESS);
		WechatTemplate wechatTemplate = wechatSchoolManager.queryWechatTemplate(tempRecord);
		if (wechatTemplate != null && wechatTemplate.getTemplateId() != null) {
			template.setTemplateId(wechatTemplate.getTemplateId());  
			template.setUrl(wechatTemplate.getUrl());  
		}
		
		WechatStudent  student = new WechatStudent();
		student.setStudentId(enrollStudent.getStudentId());
		WechatStudent wechatStudent = wechatSchoolManager.queryWechatStudent(student);
		if (wechatStudent != null) {
			template.setToUser(wechatStudent.getOpenId());  
			if (template.getTemplateId() != null && !"".equals(template.getTemplateId())) {
				List<TemplateParam> paras=new ArrayList<TemplateParam>();  
				paras.add(new TemplateParam("first",firstStr,"#AAAAAA"));  
				if (enrollStudent.getDrtype() == 2) {
					paras.add(new TemplateParam("keyword1",enrollStudent.getClassName() + " C2" ,"#AAAAAA"));  
				}
				else {
					paras.add(new TemplateParam("keyword1",enrollStudent.getClassName() + " C1" ,"#AAAAAA"));  
				}
				double d = (double) enrollStudent.getPayPrice()/100;
				paras.add(new TemplateParam("keyword2",String.valueOf(d),"#AAAAAA"));  //支付金额
				paras.add(new TemplateParam("keyword3",enrollStudent.getStudentName(),"#AAAAAA"));  //学员姓名
				paras.add(new TemplateParam("keyword4",enrollStudent.getStudentPhone(),"#AAAAAA"));  //学员电话
				paras.add(new TemplateParam("keyword5",enrollStudent.getOrderId(),"#AAAAAA"));  //订单号
				paras.add(new TemplateParam("remark","城市 : " + enrollStudent.getCityName(),"#AAAAAA"));  
				
				template.setTemplateParamList(paras);  
				logger.info("******************************************** Send template Json : " + template.toJSON());
				
				boolean isSendSuc = wechatCoachService.sendTemplateMsg(template);
				if (isSendSuc) {
					logger.info("******************************************** Send template Message Success!");
				}
				else {
					logger.info("******************************************** Send template Message Fail!");
				}
			}
		}
		return null;
	}


	@Override
	public ReqResult getSearchCoach(String openId, String studentId, String cityId,  Integer pageNo, Integer pageSize) {
		ReqResult r = ReqResult.getSuccess();
		WechatEnrollClass wechatEnrollClass = new WechatEnrollClass();
		wechatEnrollClass.setIsDel(0);
		MyRowBounds myRowBounds = new MyRowBounds(pageNo, pageSize);
		wechatEnrollClass.setMyRowBounds(myRowBounds);
		if (StringUtil.isNotNullAndNotEmpty(cityId)) {
			wechatEnrollClass.setCityId(Integer.parseInt(cityId));
		}
		List<WechatEnrollClass> enrollList =  wechatSchoolManager.queryClassGroupByCoachList(wechatEnrollClass);
		List<WechatEnrollClassVo> coachEnrollList = new ArrayList<WechatEnrollClassVo>();
		if (enrollList != null && enrollList.size() > 0) {
			try {
				coachEnrollList = BeanCopy.copyList(enrollList, WechatEnrollClassVo.class, BeanCopy.COPYNOTNULL);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//从缓存拿教练的个人信息、招生范围、城市、驾校
			Coach coach = null;
			for (WechatEnrollClassVo enrollClass : coachEnrollList) {
				coach = new Coach();
				coach = coachManager.getCoachInfo(enrollClass.getCoachId());
				WechatEnrollClass wechatEnrollClassPrice = wechatSchoolManager.getMinPrice(enrollClass);
				if (wechatEnrollClassPrice != null && wechatEnrollClassPrice.getPrice() != null) {
					int[] numbers = {wechatEnrollClassPrice.getPrice()};
					if (wechatEnrollClassPrice.getPrePrice() != null && wechatEnrollClassPrice.getPrePrice() > 0) {
						numbers=Arrays.copyOf(numbers, numbers.length+1);
						numbers[numbers.length-1]=wechatEnrollClassPrice.getPrePrice();
					}
					if (wechatEnrollClassPrice.getPrice2() != null && wechatEnrollClassPrice.getPrice2() > 0) {
						numbers=Arrays.copyOf(numbers, numbers.length+1);
						numbers[numbers.length-1]=wechatEnrollClassPrice.getPrice2();
					}
					if (wechatEnrollClassPrice.getPrePrice2() != null && wechatEnrollClassPrice.getPrePrice2() > 0) {
						numbers=Arrays.copyOf(numbers, numbers.length+1);
						numbers[numbers.length-1]=wechatEnrollClassPrice.getPrePrice2();
					}
					
					int minnum = numbers[0];
				    int size = numbers.length; // 数组大小   
				    for (int i = 0; i < size ; i++) {   
				    	if (numbers[i] < minnum) {
				    		minnum = numbers[i];
				    	}
				    }
				    enrollClass.setPrice(minnum);
				}
				
				if (coach != null) {
					enrollClass.setArea(coach.getArea());
					enrollClass.setCheckDrState(coach.getCheckDrState());
					enrollClass.setCoachTag(coach.getCoachTag());
					enrollClass.setCityId(coach.getCityId());
					enrollClass.setCityName(coach.getCityName());
					enrollClass.setHeadIcon(coach.getHeadIcon());
					enrollClass.setSchoolId(coach.getSchoolId());
					enrollClass.setSchoolName(coach.getSchoolName());
					enrollClass.setName(coach.getName());
					enrollClass.setPhoneNum(coach.getPhoneNum());
				}
			}
			r.setData(coachEnrollList);
		}
		return r;
	}


	@Override
	public ReqResult subEnrollPurposeNew(Long studentId, Integer channel, String cType,  String classId, String ttid, String schoolId,
			String coachId) {
		ReqResult r = ReqResult.getSuccess();
		
		EnrollPurpose enrollPurpose = new EnrollPurpose();
		enrollPurpose.setStudentId(studentId);
		enrollPurpose.setChannel(channel);
		if (StringUtil.isNotNullAndNotEmpty(ttid)) {
			enrollPurpose.setTtid(Integer.parseInt(ttid));
		}
		if (StringUtil.isNotNullAndNotEmpty(classId)) {
			enrollPurpose.setClassId(Integer.parseInt(classId));
		}
		if (StringUtil.isNotNullAndNotEmpty(schoolId)) {
			enrollPurpose.setSchoolId(Integer.parseInt(schoolId));
		}
		if (StringUtil.isNotNullAndNotEmpty(coachId)) {
			enrollPurpose.setCoachId(Long.parseLong(coachId));
		}
		if (StringUtil.isNotNullAndNotEmpty(cType)) {
			enrollPurpose.setCtype(Integer.parseInt(cType));
		}
		
		//去重复：是否已提交过
		EnrollPurpose isEnroll = wechatSchoolManager.getEnrollPurpose(enrollPurpose);
		if (isEnroll != null ) {
			r.setCode(ResultCode.ERRORCODE.SUB_ENROLL_PURPOSE);
			r.setMsgInfo(ResultCode.ERRORINFO.SUB_ENROLL_PURPOSE);
			return r;
		}
		
		r = wechatSchoolManager.subEnrollPurposeNew(enrollPurpose);
		
		return r;
	}

	
}
