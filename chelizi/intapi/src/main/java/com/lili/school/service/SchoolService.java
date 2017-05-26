package com.lili.school.service;

import java.util.List;
import java.util.Map;

import com.lili.common.vo.ReqResult;
import com.lili.school.dto.WechatComment;
import com.lili.school.dto.WechatEnrollOrder;

public interface SchoolService {

	ReqResult getEnrollPkgList(String userId, String userType);

	ReqResult getEnrollPkgOne(String userId, String userType, String eid);

	ReqResult postEnrollApply(String userId, String userType, String eid);

	ReqResult postEnrollPay(String userId, String userType, String eid,
			String payType, String payValue);

	ReqResult postEnrollIdcard(String userId, String userType, String picFront,
			String picBack, String idNum,String name,
			String drType,String orderId);

	ReqResult getMailDetail(String userId, String userType, String ttid,String type);

	ReqResult postMailState(String userId, String userType, String ptype,
			String state,String orderId);

	ReqResult getMailState(String userId, String userType);

	ReqResult getUserProgress(String userId, String userType, String stepId);

//	ReqResult postExamReg(String userId, String userType, String subjectId,
//			String cityId, String waterNo, String name, String idcard,
//			String drtype, String examPlace,String mobile,String orderId);

	ReqResult getExamPlace(String userId, String userType);

	ReqResult getEnrollOrder(String userId, String userType, String orderId);

	ReqResult postEnrollOrderCancel(String userId, String userType,
			String orderId);

	ReqResult postEnrollApplyDelay(String userId, String userType);

	/**
	 * 更新用户状态（更改学员我的进度状态），供cms后台管理使用
	 * @param ttid			报名套餐id，对应学员表中的applyttid
	 * @param studentIds	学员id列表
	 * @param stage			学员所在阶段，对应学员表中的applyexam
	 * @param state			阶段完成状态，对应学员表中的applystate
	 * @param params		冗余参数键值对
	 * @return	0-成功；1-失败
	 */
	int changeState(int ttid,List<Long> studentIds,int stage,int state,Map<String,String> params);
	
	/**
	 * 为学员分配驾校
	 * @param ttid			报名套餐id，对应学员表中的applyttid
	 * @param studentIds	学员id列表
	 * @param schoolId		驾校id
	 * @return
	 */
	int allotSchool(int ttid,List<Long> studentIds, int schoolId);

	public ReqResult getStudentProgress(String userId, String userType,
			String studentId);
	
	ReqResult getStageDetail(String userId, String userType, String stepId);

	ReqResult getDriveSchoolList(Double lat,Double lon,String pageSize,String cityId);
	
	ReqResult getDriveSchoolById(String id,Double lat,Double lon);
	
	ReqResult getTrfieldBySchoolId(String schoolId,String size);
	
	ReqResult getGroupBySchoolId(String id);
	
	ReqResult getPackageById(String ttid);
	
	ReqResult addWechatOrder(WechatEnrollOrder wechatEnrollOrder);
	
    WechatEnrollOrder getWechatOrder(String orderId);
    
    List<WechatEnrollOrder> getWechatOrderByStudentId(String studentId);
    
    ReqResult saveWechatComment(WechatComment wechatComment);
    
    ReqResult deleteComment(String commentId);
    
    ReqResult getCommentListBySchoolId(String schoolId,String studentId,int start,int end);
    
    ReqResult updateCommentPraise(String commitId,String studentId);

	ReqResult getDriveSchoolListNew(Double lat, Double lon, String pageSize, String cityId);

	ReqResult getDriveSchoolInfo(Integer schoolId, String lon, String lat);

	ReqResult postEnrollApplySchool(String userId, String userType, String ttid);

	ReqResult getEnrollSchoolPkgOne(String userId, String userType, String ttid);
	
	
}
