package com.lili.school.manager;

import java.util.List;
import java.util.Map;

import com.lili.common.vo.ReqResult;
import com.lili.pay.vo.PayVo;
import com.lili.school.dto.School;
import com.lili.school.dto.SchoolWithBLOBs;
import com.lili.school.dto.WechatComment;
import com.lili.school.dto.WechatEnrollOrder;
import com.lili.school.dto.WechatEnrollPackageWithBLOBs;
import com.lili.school.vo.EnrollPackageTemplateVo;

public interface SchoolManager {

	List<EnrollPackageTemplateVo> getEnrollPkgList(String userId, String userType);

	EnrollPackageTemplateVo getEnrollPkgOne(String userId, String userType, String eid);

//	int postExamReg(String userId, String userType, String subjectId,
//			String cityId, String waterNo, String name, String idcard,
//			String drtype, String examPlace,String mobile,String orderId);

	ReqResult postEnrollApply(String userId, String userType, String eid);

	ReqResult postEnrollIdcard(String userId, String userType, String picFront,
			String picBack, String idNum, String name,
			String drType, String orderId);

	ReqResult getMailDetail(String userId, String userType, String ttid,String type);

	int postMailState(String userId, String userType, String ptype, String state,String orderId);
	
	int postPayState(PayVo pay,Byte state);
	
	int postWxPayState(PayVo pay,Byte state); //微信端更改状态
	
	Byte getPayState(String orderId);
	
	Byte getWxPayState(String orderId);

	ReqResult getUserProgress(String userId, String userType, String stepId);

	ReqResult getExamPlace(String userId, String userType);

	ReqResult getEnrollOrder(String userId, String userType, String orderId);

	ReqResult getEnrollOrderByStudentId(String studentId);

	ReqResult postEnrollOrderCancel(String userId, String userType,
			String orderId);

	int changeState(int ttid, List<Long> studentIds, int stage, int state,
			Map<String, String> params);
	
	int changeState(int ttid, Long studentId, int stage, int state,
			Map<String, String> params);

	int allotSchool(int ttid, List<Long> studentIds, int schoolId);
	
	int postEnrollSource(String agent, String orderId);

	/**
	 * 结算报名费给驾校
	 * @param orderId
	 * @param checkouter
     * @return
     */
	ReqResult checkoutEnroll2School(String orderId, String checkouter);

	ReqResult getStudentProgress(String userId, String userType,
			String studentId);
	
    ReqResult getDriveSchoolList(Double lat,Double lon,String pageSize,String cityId);
    
    ReqResult getDriveSchoolById(String id,Double lat,Double lon);
    
    ReqResult getTrfieldBySchoolId(String schoolId,String size);
	
	ReqResult getGroupBySchoolId(String id);
	
	ReqResult getPackageById(String ttid);
	
	int addWechatOrder(WechatEnrollOrder wechatEnrollOrder);
	
	WechatEnrollOrder getWechatOrder(String orderId);
	
	List<WechatEnrollOrder> getWechatOrderByStudentId(String studentId);
	
	int saveWechatComment(WechatComment wechatComment);
	
	int deleteComment(String commentId);
	    
	ReqResult getCommentListBySchoolId(String schoolId,String studentId,int start,int end);
	
	 ReqResult updateCommentPraise(String commitId,String studentId);

	ReqResult getDriveSchoolListNew(Double lat, Double lon, String pageSize, String cityId);

	SchoolWithBLOBs getDriveSchoolInfo(School school);

	ReqResult postEnrollApplySchool(String userId, String userType, String eid);

	WechatEnrollPackageWithBLOBs getEnrollSchoolPkgOne(String userId, String userType, String eid);

}
