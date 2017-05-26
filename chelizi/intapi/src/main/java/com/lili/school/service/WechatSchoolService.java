package com.lili.school.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.lili.common.vo.ReqResult;
import com.lili.school.vo.WechatEnrollClass;
import com.lili.school.vo.WechatEnrollStudent;
import com.lili.school.vo.WechatMycoaches;
import com.lili.school.vo.WechatPlantClass;

public interface WechatSchoolService {

	ReqResult addEnrollVisCard(Long coachId, String headIcon, String name, String schoolName, String phoneNum, String schoolAge,
			String cityId, String cityName, String area, String coachTag, String profile, List<WechatEnrollClass> enrollJsonList);

	ReqResult getEnrollVisCard(Long coachId);

	ReqResult inportStudent(Long coachId, List<WechatMycoaches> enrollJsonList);

	ReqResult inportStudentOne(Long coachId, String studentId, String studentName, String studentPhone, String state, String drtype,
			String coachRemark);

	ReqResult addWechatClass(Long coachId, String carId, String driveType, String courseId, String courseName, String placeId,
			String placeName, String maxNum, String cStart, String timeNum, List<WechatPlantClass> stuJsonList);

	ReqResult getWechatStudent(Long coachId, String studentName, String state, String sortType, String type, String channel, String isNew,  String pageSize,
			String pageNo);

	ReqResult getWechatEnroll(Long coachId, String classId, String pageSize, String pageNo);

	ReqResult getEnrollStudentInfo(Long coachId, Long studentId);

	ReqResult delStudentBound(Long coachId, Long studentId);

	ReqResult getWechatClass(Long coachId, String pageSize, String pageNo);

	ReqResult getWeChatMyCoach(String openId, String studentId);

	ReqResult getWeChatOrder(String openId, String studentId);

	ReqResult subEnrollPurpose(String openId, String studentId, Integer classId, String className, String classRemark, String drtype,
			Long coachId, String coachName, String studentName, String studentPhone, String codeInput, String payState, String cityName, String payPrice, String payWay);

	ReqResult getHomePageInfo(Long coachId);

	ReqResult getWeChatClassInfo(String openId, String studentId, Integer classId);

	ReqResult handClass(String openId, String ccid, Integer isdel, String orderId);

	ReqResult abolishCoach(String openId, String studentId, Long coachId, Integer status);

	ReqResult wechatBoundCoach(String phone, String name, Long coachId, String openId, String codeInput, Integer wxstatus);

	ReqResult smsNoticeStudent(Long userId, String studentId, String studentPhone, String cStart);

	ReqResult updateWechatClass(Long coachId, String orderId, Integer ccid, String carId, String drtype, String courseId, String courseName,
			String placeId, String placeName, String maxNum, String cStart, String timeNum);

	ReqResult alertClassStudent(Long coachId, Integer ccid, String orderId, List<WechatPlantClass> stuJsonList);

	ReqResult getQrCode(Long coachId);

	ReqResult subStudentInfo(String openId, String studentId, Long coachId, String coachName, String studentName,
			String studentPhone, Integer state);


	ReqResult getWeChatStudentInfo(String openId);

	ReqResult topAndStaeStudent(Long coachId, Long studentId, String isTop, String state);

	ReqResult getMyStudentNum(Long coachId);

	ReqResult saveWechatUserInfo(String openId, JSONObject userInfoJson);

	ReqResult getEnStudentClassHis(Long coachId, Long studentId, Integer type);

	ReqResult acceptOrDelStudent(Long coachId, Long studentId, String isdel, String state, String type);

	ReqResult bindCoachByScan(Map<String, String> params);

	ReqResult closeWechatClass(Long coachId, Integer ccid);
	
	ReqResult getWeChatClass(String openId, String coachId, String ccid, String orderId);

	ReqResult updateWechatEnroll(Long coachId, String orderId, String remark);

	ReqResult getWechatClassOne(Long coachId, String orderId, String ccid);

	ReqResult sendTemplateEnrollMsg(WechatEnrollStudent enrollStudent, String firstStr);

	ReqResult getSearchCoach(String openId, String studentId, String cityId, Integer pageNo, Integer pageSize);

	ReqResult subEnrollPurposeNew(Long studentId, Integer channel, String cType, String classId, String ttid, String schoolId, String coachId);

}
